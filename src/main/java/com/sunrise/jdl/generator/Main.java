package com.sunrise.jdl.generator;

import com.sunrise.jdl.generator.config.GeneratorConfig;
import com.sunrise.jdl.generator.entities.Entity;
import com.sunrise.jdl.generator.entities.Field;
import com.sunrise.jdl.generator.entities.FieldBuilder;
import com.sunrise.jdl.generator.entities.ResultWithWarnings;
import com.sunrise.jdl.generator.service.EntitiesService;
import com.sunrise.jdl.generator.service.EntityTypeService;
import com.sunrise.jdl.generator.service.Settings;
import com.sunrise.jdl.generator.service.iad.TemplateService;
import com.sunrise.jdl.generator.ui.TemplateProjection;
import com.sunrise.jdl.generator.ui.UIGenerateParameters;
import freemarker.template.TemplateException;
import org.apache.commons.cli.*;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Main {
    private static final String HELP = "help";
    private static final String JDL_CONFIG_FILE = "jdlConfigFile";
    private static final String GID_CONFIG_FILE = "gidConfigFile";
    // TODO: 29.10.18 Сделать шаблоны для всех проекций и удалить всё, что больше не пригодится
    private static EntityTypeService entityTypeService = new EntityTypeService();

    public static void main(String[] args) throws IOException, TemplateException {
        Options options = new Options();
        options.addOption(JDL_CONFIG_FILE, true, "JDL generation config file.");
        options.addOption(GID_CONFIG_FILE, true, "UI generation config file.");
        options.addOption(HELP, false, "show this help");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println("Parsing failed.  Reason: " + e.getMessage());
            return;
        }

        if (cmd == null) {
            System.err.println("Parsing failed.  Reason: cmd is null");
            return;
        }

        if (cmd.hasOption(HELP)) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("jdlGenerator", options);
            return;
        }

        File configFile = null;
        // Load config file
        if (cmd.hasOption(JDL_CONFIG_FILE)) {
            configFile = new File(cmd.getOptionValue(JDL_CONFIG_FILE));
        }
        if (cmd.hasOption(GID_CONFIG_FILE)) {
            configFile = new File(cmd.getOptionValue(GID_CONFIG_FILE));
        }
        if (configFile == null) {
            System.err.println("No config file specified for generation");
            return;
        }

        Yaml yaml = new Yaml();
        FileInputStream inputStream = new FileInputStream(configFile);

        if (cmd.hasOption(JDL_CONFIG_FILE)) {
            GeneratorConfig generatorConfig = yaml.load(inputStream);
            JdlGeneratorService jdlGeneratorService = new JdlGeneratorService(generatorConfig.getJdlConfig());
            jdlGeneratorService.generate();
        }
        if (cmd.hasOption(GID_CONFIG_FILE)) {
            UIGenerateParameters generateParameters = yaml.load(inputStream);
            gidGenerator(generateParameters);
        }
    }

    private static void gidGenerator(UIGenerateParameters generateParameters) throws IOException {
        if (generateParameters.getTemplatesDir() == null) {
            System.err.println("Не указана папка с шаблонами в конфигурационном файле!");
            return;
        }

        // Получение шаблонов
        File[] templateFiles = new File(generateParameters.getTemplatesDir()).listFiles();
        List<TemplateProjection> templates = TemplateService.getInstance().loadTemplateProjections(templateFiles);

        File entitiesFile = new File(generateParameters.getEntitiesPath());
        File relationsFile = new File(generateParameters.getRelationsPath());
        File actionsFile = new File(generateParameters.getActionsPath());

        if (!entitiesFile.isFile()) {
            throw new FileNotFoundException(entitiesFile.getAbsolutePath());
        }
        if (!relationsFile.isFile()) {
            throw new FileNotFoundException(relationsFile.getAbsolutePath());
        }
        if (!actionsFile.isFile()) {
            throw new FileNotFoundException(actionsFile.getAbsolutePath());
        }

        EntitiesService entitiesService = new EntitiesService(new Settings());
        Collection<Entity> entities = entitiesService.readDataFromCSV(new FileInputStream(entitiesFile));
        for (FieldBuilder fb : generateParameters.getAdditionalFields())
            entities.forEach((x) -> x.getFields().add(fb.build()));
        Map<String, List<String>> relations = entityTypeService.readCsv(new FileInputStream(relationsFile));
        ResultWithWarnings<Map<String, List<Entity>>> entitiesHierarchy = entityTypeService.mergeTypesWithThemSubtypes(entities, relations);
        entitiesHierarchy.warnings.forEach(x -> System.out.println("WARNING: " + x));
        Map<String, Set<Field>> baseDataWithBaseFields = entityTypeService.prepareDataForParentEntity(entitiesHierarchy.result);
        InputStream actionsStream = new FileInputStream(actionsFile);
        entityTypeService.generateEntitiesPresentations(actionsStream, generateParameters.getTemplatesDir(), baseDataWithBaseFields, entitiesHierarchy.result, generateParameters, templates, entities);
    }

}
