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
import com.sunrise.jdl.generator.service.iad.UIGeneratorService;
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
    public static final String HELP = "help";
    private static final String TARGET_RESOURCE_FOLDER = "targetResourceFolder";
    private static final String JDL_GENERATION = "jdlGeneration";
    private static final String GID_GENERATION = "jsonGeneration";
    private static final String GID_ENTITIES = "entities";
    private static final String GID_RELATIONS = "relations";
    private static final String GID_ACTIONS = "actions";
    private static final String GID_CONFIG_FILE = "gidConfigFile";
    public static final String CONFIG_FILE = "configFile";
    // TODO: 29.10.18 Сделать шаблоны для всех проекций и удалить всё, что больше не пригодится
    private static final String PROJECTION_TEMPLATES_FOLDER = "templates";
    private static EntityTypeService entityTypeService = new EntityTypeService();
    private static GeneratorConfig generatorConfig;

    public static void main(String[] args) throws IOException, TemplateException {

        Options options = new Options();
        options.addOption(CONFIG_FILE, true, "Generation config file.");
        options.addOption(HELP, false, "show this help");
        options.addOption(JDL_GENERATION, false, "set the \"JDL-Generation\" operation type");
        options.addOption(GID_GENERATION, false, "set the \"Generate Interface Description\" operation type");
        options.addOption(GID_ENTITIES, true, "path to the 'entities' csv file");
        options.addOption(GID_RELATIONS, true, "path to the 'relations' csv file");
        options.addOption(GID_ACTIONS, true, "path to the 'actions' csv file");
        options.addOption(GID_CONFIG_FILE, true, "gid config file path");
        options.addOption(PROJECTION_TEMPLATES_FOLDER, true, "path to the folder of the templates");


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
        }

        // Load config file
        if (!cmd.hasOption(CONFIG_FILE)) {
            System.err.println("No config file specified for generation");
        } else {
            Yaml yaml = new Yaml();
            File file = new File(cmd.getOptionValue(CONFIG_FILE));
            String parent = file.getAbsoluteFile().getParent();
            //System.setProperty("user.dir", parent);
            FileInputStream inputStream = new FileInputStream(file);
            generatorConfig = yaml.load(inputStream);
        }

        if (cmd.hasOption(HELP)) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("jdlGenerator", options);
        }

        if (cmd.hasOption(GID_GENERATION)) {
            gidGenerator(cmd);

        } else if (cmd.hasOption(JDL_GENERATION)) {
            JdlGeneratorService jdlGeneratorService = new JdlGeneratorService(generatorConfig.getJdlConfig());
            jdlGeneratorService.generate();
        } else {
            System.err.println("No operation selected!");
        }

    }

    private static void gidGenerator(CommandLine cmd) throws IOException {
        if (!cmd.hasOption(PROJECTION_TEMPLATES_FOLDER)) {
            System.err.println("Не указана папка с шаблонами!");
            return;
        }

        // Получение шаблонов
        File[] templateFiles = new File(cmd.getOptionValue(PROJECTION_TEMPLATES_FOLDER)).listFiles();
        List<TemplateProjection> templates = TemplateService.getInstance().loadTemplateProjections(templateFiles);

        UIGeneratorService generatorService = new UIGeneratorService();

        File entitiesFile = new File(cmd.getOptionValue(GID_ENTITIES));
        File relationsFile = new File(cmd.getOptionValue(GID_RELATIONS));
        File actionsFile = new File(cmd.getOptionValue(GID_ACTIONS));

        if (!entitiesFile.isFile()) {
            throw new FileNotFoundException(entitiesFile.getAbsolutePath());
        }
        if (!relationsFile.isFile()) {
            throw new FileNotFoundException(relationsFile.getAbsolutePath());
        }

        if (!actionsFile.isFile()) {
            throw new FileNotFoundException(actionsFile.getAbsolutePath());
        }

        UIGenerateParameters parameters = generatorService.loadConfig(cmd.getOptionValue(GID_CONFIG_FILE));
        EntitiesService entitiesService = new EntitiesService(new Settings());
        Collection<Entity> entities = entitiesService.readDataFromCSV(new FileInputStream(entitiesFile));
        for (FieldBuilder fb : parameters.getAdditionalFields())
            entities.forEach((x) -> x.getFields().add(fb.build()));
        Map<String, List<String>> relations = entityTypeService.readCsv(new FileInputStream(relationsFile));
        ResultWithWarnings<Map<String, List<Entity>>> entitiesHierarchy = entityTypeService.mergeTypesWithThemSubtypes(entities, relations);
        entitiesHierarchy.warnings.forEach(x -> System.out.println("WARNING: " + x));
        Map<String, Set<Field>> baseDataWithBaseFields = entityTypeService.prepareDataForParentEntity(entitiesHierarchy.result);
        File file = new File(cmd.getOptionValue(GID_ACTIONS));
        InputStream actionsStream = new FileInputStream(file);
        entityTypeService.generateEntitiesPresentations(actionsStream, cmd.getOptionValue(TARGET_RESOURCE_FOLDER), baseDataWithBaseFields, entitiesHierarchy.result, parameters, templates, entities);
    }

}
