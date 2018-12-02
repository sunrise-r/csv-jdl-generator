package com.sunrise.jdl.generator;

import com.sunrise.jdl.generator.config.GeneratorConfig;
import com.sunrise.jdl.generator.entities.*;
import com.sunrise.jdl.generator.service.*;
import com.sunrise.jdl.generator.service.iad.TemplateService;
import com.sunrise.jdl.generator.service.iad.UIGeneratorService;
import com.sunrise.jdl.generator.ui.TemplateProjection;
import com.sunrise.jdl.generator.ui.UIGenerateParameters;
import org.apache.commons.cli.*;
import org.apache.commons.io.FileUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


public class Main {

    public static final String PAGINATE_TYPE = "paginateType";
    public static final String IGNORE_FIELDS = "ignoreFields";
    public static final String IGNORE_ENTITIES = "ignoreEntities";
    public static final String HELP = "help";
    public static final String SOURCE_FOLDER = "sourceFolder";
    public static final String TARGET_FILE = "targetFile";
    public static final String MAPSTRUCT = "mapstruct";
    private static final String GENERATTE_SERVICE_FOR = "generateServiceFor";
    private static final String EXECPT_SERVICE_GENERATION = "exceptServiceGeneration";
    private static final String MICROSERVICE = "microservice";
    private static final String GATEWAY_NAME = "gateway";
    private static final String TARGET_RESOURCE_FOLDER = "targetResourceFolder";
    private static final String JDL_GENERATION = "jdlGeneration";
    private static final String GID_GENERATION = "jsonGeneration";
    private static final String GID_ENTITIES = "entities";
    private static final String GID_RELATIONS = "relations";
    private static final String GID_ACTIONS = "actions";
    private static final String GID_CONFIG_FILE = "gidConfigFile";
    public static final String CONFIG_FILE = "configFile";
    // TODO: 29.10.18 Сделать шаблоны для всех проекций и удалить всё, что больше не пригодится
    private static EntitiesService entitiesService = null;
    private static final String PROJECTION_TEMPLATES_FOLDER = "templates";
    private static EntityTypeService entityTypeService = new EntityTypeService();
    private static GeneratorConfig generatorConfig;

    public static void main(String[] args) throws IOException {

        Options options = new Options();
        options.addOption(CONFIG_FILE, true, "Generation config file.");
        options.addOption(SOURCE_FOLDER, true, "set source folder with csv files"); //to config
        options.addOption(HELP, false, "show this help");
        options.addOption(TARGET_FILE, true, "file with results"); //to config
        options.addOption(IGNORE_ENTITIES, true, "set entities that will be ignored while generating"); //to config file
        options.addOption(IGNORE_FIELDS, true, "set entities that will be ignored while generating"); //to config file
        options.addOption(PAGINATE_TYPE, true, "set type of pagination for entities, default is pagination. Possible values are: pager, pagination, infinite-scroll"); //to config file
        options.addOption(MAPSTRUCT, false, "enables using dto's with mapstruct"); //to config file
        options.addOption(GENERATTE_SERVICE_FOR, true, "for what entities service generation needed. List of entities or 'all'"); //to config file
        options.addOption(EXECPT_SERVICE_GENERATION, true, "for what entities service generation is not needed. List of entities"); //to Config file
        options.addOption(MICROSERVICE, true, "set name of microservice that will hold the entities"); // to Config file
        options.addOption(TARGET_RESOURCE_FOLDER, true, "set path where resource files will be generated"); //to Config file
        options.addOption(GATEWAY_NAME, true, "set name of gateway"); // to Config file

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
            System.err.println("No config file spcified for generation");
        } else {
            Yaml yaml = new Yaml();
            InputStream inputStream = Yaml.class
                    .getClassLoader()
                    .getResourceAsStream(cmd.getOptionValue(CONFIG_FILE));
            generatorConfig = yaml.load(inputStream);
        }

        if (cmd.hasOption(HELP)) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("jdlGenerator", options);
        }

        if (cmd.hasOption(GID_GENERATION)) {
            gidGenerator(cmd);

        } else if (cmd.hasOption(JDL_GENERATION)) {
            jdlGenerator(cmd);
        } else {
            System.err.println("No operation selected!");
        }

    }

    private static void saveFile(String path, String fileName, String content) {
        try {
            FileUtils.writeStringToFile(new File(path, fileName), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    private static void jdlGenerator(CommandLine cmd) {
        final Settings settings = new Settings();

        if (cmd.hasOption(IGNORE_ENTITIES)) {
            String ens = cmd.getOptionValue(IGNORE_ENTITIES);
            settings.getEntitiesToIngore().addAll(Arrays.stream(ens.split(",")).map((s) -> s.trim()).collect(Collectors.toList()));
        }
        if (cmd.hasOption(IGNORE_FIELDS)) {
            settings.getFieldsToIngore().addAll(Arrays.stream(cmd.getOptionValue(IGNORE_FIELDS).split(",")).map((s) -> s.trim()).collect(Collectors.toList()));
        }

        if (cmd.hasOption(PAGINATE_TYPE)) {
            settings.setPaginationType(Settings.PaginationType.fromString(cmd.getOptionValue(PAGINATE_TYPE)));
        }

        if (cmd.hasOption(MAPSTRUCT)) {
            settings.setUseMapStruct(true);
        }

        if (cmd.hasOption(GENERATTE_SERVICE_FOR)) {
            settings.setGenerateServiciesFor(cmd.getOptionValue(GENERATTE_SERVICE_FOR));
        }

        if (cmd.hasOption(EXECPT_SERVICE_GENERATION)) {
            settings.setExceptServiceGenerationFor(cmd.getOptionValue(EXECPT_SERVICE_GENERATION));
        }

        if (cmd.hasOption(MICROSERVICE)) {
            settings.setMicroserviceName(cmd.getOptionValue(MICROSERVICE));
        }

        if (cmd.hasOption(SOURCE_FOLDER)) {
            entitiesService = new EntitiesService(settings);
            File directory = new File(cmd.getOptionValue(SOURCE_FOLDER));
            File[] files = directory.listFiles();
            List<InputStream> resources = new ArrayList<InputStream>(files.length);
            for (File f : files) {
                if (f.getName().endsWith(".csv")) {
                    try {
                        resources.add(new FileInputStream(f));
                    } catch (FileNotFoundException e) {
                        System.err.println("Failed to read file .  Reason: " + e.getMessage());
                    }
                }
            }

            File targetFile;
            if (cmd.hasOption(TARGET_FILE)) {
                targetFile = new File(cmd.getOptionValue(TARGET_FILE));
            } else {
                targetFile = new File("result.jh");
            }

            Collection<Entity> entities = entitiesService.readAll(resources);
            int numberOfCreatedStrucure = entitiesService.createStructures(entities);

            Set<String> names = entities.stream().map(e -> e.getClassName()).collect(Collectors.toSet());
            List<Relation> relations = entities.stream().map(e -> e.getRelations()).flatMap(Collection::stream).collect(Collectors.toList());
            relations.stream()
                    .filter(r -> !names.contains(r.getEntityTo())).forEach(r -> System.out.println("Unable to find entity for the relation=" + r.getEntityTo()));
            System.out.println("Количество созданных структур " + entities.size());

            entitiesService.checkRelations(entities).entrySet().stream().forEach(e -> {
                System.out.println(String.format("Для сущности %s в списке отношений присуствуют несуществующие сущности", e.getKey().getClassName()));
                e.getValue().stream().forEach(r -> {
                    System.out.println(String.format("Не существует сущности: %s", r.getEntityTo()));
                });
            });

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile, false))) {
                entitiesService.writeEntities(entities, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (cmd.hasOption(TARGET_RESOURCE_FOLDER) && cmd.hasOption(GATEWAY_NAME)) {
                final String path = cmd.getOptionValue(TARGET_RESOURCE_FOLDER);
                DescriptionServiceSettings descriptionSettings = new DescriptionServiceSettings(cmd.getOptionValue(GATEWAY_NAME), cmd.getOptionValue(MICROSERVICE));
                DescriptionService descriptionService = new DescriptionService(descriptionSettings);
                descriptionService.getEntitiesDescription(entities).entrySet().stream().forEach(key -> saveFile(path, key.getKey(), key.getValue()));
            }
        }
    }

}
