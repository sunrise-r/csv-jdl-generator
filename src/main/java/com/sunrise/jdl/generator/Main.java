package com.sunrise.jdl.generator;

import com.sunrise.jdl.generator.entities.Entity;
import com.sunrise.jdl.generator.entities.Field;
import com.sunrise.jdl.generator.entities.Relation;
import com.sunrise.jdl.generator.entities.ResultWithWarnings;
import com.sunrise.jdl.generator.service.*;
import org.apache.commons.cli.*;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;


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
    private static EntitiesService entitiesService = null;
    private static EntityTypeService entityTypeService = new EntityTypeService();

    public static void main(String[] args) throws IOException {

        Options options = new Options();
        options.addOption(SOURCE_FOLDER, true, "set source folder with csv files");
        options.addOption(HELP, false, "show this help");
        options.addOption(TARGET_FILE, true, "file with results");
        options.addOption(IGNORE_ENTITIES, true, "set entities that will be ignored while generating");
        options.addOption(IGNORE_FIELDS, true, "set entities that will be ignored while generating");
        options.addOption(PAGINATE_TYPE, true, "set type of pagination for entities, default is pagination. Possible values are: pager, pagination, infinite-scroll");
        options.addOption(MAPSTRUCT, false, "enables using dto's with mapstruct");
        options.addOption(GENERATTE_SERVICE_FOR, true, "for what entities service generation needed. List of entities or 'all'");
        options.addOption(EXECPT_SERVICE_GENERATION, true, "for what entities service generation is not needed. List of entities");
        options.addOption(MICROSERVICE, true, "set name of microservice that will hold the entities");
        options.addOption(TARGET_RESOURCE_FOLDER, true, "set path where resource files will be generated");
        options.addOption(GATEWAY_NAME, true, "set name of gateway");

        options.addOption(JDL_GENERATION, false, "set the \"JDL-Generation\" operation type");
        options.addOption(GID_GENERATION, false, "set the \"Generate Interface Description\" operation type");
        options.addOption(GID_ENTITIES, true, "path to the 'entities' csv file");
        options.addOption(GID_RELATIONS, true, "path to the 'relations' csv file");
        options.addOption(GID_ACTIONS, true, "path to the 'actions' csv file");


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

        if (cmd.hasOption(HELP)) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("jdlGenerator", options);
        }

        if (cmd.hasOption(GID_GENERATION)) {
            try {
                gidGenerator(cmd);
            } catch (FileNotFoundException e) {
                System.err.println("File not found!");
            }

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
        if (!(cmd.hasOption(GID_ENTITIES) && cmd.hasOption(GID_RELATIONS)
                && cmd.hasOption(GID_ACTIONS) && cmd.hasOption(TARGET_RESOURCE_FOLDER))) {
            System.err.println("No args for the '" + GID_ENTITIES + "' or '" + GID_RELATIONS + "' or '"
                    + GID_ACTIONS + "' or '" + TARGET_RESOURCE_FOLDER + "' param");
        }

        File entitiesFile = new File(cmd.getOptionValue(GID_ENTITIES));
        File relationsFile = new File(cmd.getOptionValue(GID_RELATIONS));
        File actionsFile = new File(cmd.getOptionValue(GID_ACTIONS));

        if (!entitiesFile.isFile() || !relationsFile.isFile() || !actionsFile.isFile()) {
            throw new FileNotFoundException();
        }


        EntitiesService entitiesService = new EntitiesService(new Settings());
        Collection<Entity> entities = entitiesService.readDataFromCSV(new FileInputStream(entitiesFile));

        Map<String, List<String>> relations = entityTypeService.readCsv(new FileInputStream(relationsFile)); // Добавить опцию пути
        ResultWithWarnings<Map<String, List<Entity>>> entitiesHierarchy = entityTypeService.mergeTypesWithThemSubtypes(entities, relations);
        entitiesHierarchy.warnings.forEach(x -> System.out.println("WARNING: " + x));
        Map<String, Set<Field>> baseDataWithBaseFields = entityTypeService.prepareDataForParentEntity(entitiesHierarchy.result);
        File file = new File(cmd.getOptionValue(GID_ACTIONS));
        InputStream actionsStream = new FileInputStream(file);
        entityTypeService.generateEntitiesPresentations(actionsStream, cmd.getOptionValue(TARGET_RESOURCE_FOLDER), baseDataWithBaseFields, "defaultRegistry");

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
