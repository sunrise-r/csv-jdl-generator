package com.sunrise.jdl.generator;

import com.sunrise.jdl.generator.entities.Entity;
import com.sunrise.jdl.generator.service.EntitiesService;
import com.sunrise.jdl.generator.service.Settings;
import org.apache.commons.cli.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    private static EntitiesService entitiesService = null;

    public static void main(String[] args) {

        Options options = new Options();
        options.addOption(SOURCE_FOLDER, true, "set source folder with csv files");
        options.addOption(HELP, false, "show this help");
        options.addOption(TARGET_FILE, true, "file with results");
        options.addOption(IGNORE_ENTITIES, true, "set entities that will be ignored while generating");
        options.addOption(IGNORE_FIELDS, true, "set entities that will be ignored while generating");
        options.addOption(PAGINATE_TYPE, true, "set type of pagination for entities, default is pagination. Possible values are: pager, pagination, infinite-scroll");
        options.addOption(MAPSTRUCT, false, "enables using dto's with mapstruct");
        options.addOption(GENERATTE_SERVICE_FOR, true, "for what entities service generation neeed. List of entities or 'all'");
        options.addOption(EXECPT_SERVICE_GENERATION, true, "for what entities service generation is not needed. List of entities");
        options.addOption(MICROSERVICE,true,"set name of microservice that will hold the entities");
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        final Settings settings = new Settings();

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

        if(cmd.hasOption(GENERATTE_SERVICE_FOR)){
            settings.setGenerateServiciesFor(cmd.getOptionValue(GENERATTE_SERVICE_FOR));
        }

        if(cmd.hasOption(EXECPT_SERVICE_GENERATION)){
            settings.setExceptServiceGenerationFor(cmd.getOptionValue(EXECPT_SERVICE_GENERATION));
        }

        if(cmd.hasOption(MICROSERVICE)){
            settings.setMicroserviceName(cmd.getOptionValue(MICROSERVICE));
        }

        if (cmd.hasOption(SOURCE_FOLDER)) {
            entitiesService = new EntitiesService(settings);
            File directory = new File(cmd.getOptionValue(SOURCE_FOLDER));
            File[] files = directory.listFiles();
            List<InputStream> resources = new ArrayList<InputStream>(files.length);
            for (File f : files) {
                if (!f.getName().endsWith(".csv")) {
                    continue;
                }
                try {
                    resources.add(new FileInputStream(f));
                } catch (FileNotFoundException e) {
                    System.err.println("Failed to read file .  Reason: " + e.getMessage());
                }
            }

            File targetFile;
            if (cmd.hasOption(TARGET_FILE)) {
                targetFile = new File(cmd.getOptionValue(TARGET_FILE));
            } else {
                targetFile = new File("result.jh");
            }

            List<Entity> entities = entitiesService.readAll(resources);

            //entitiesService.checkIsFieldSupportedInJDL(entities);
            int numberOfCreatedStrucure = entitiesService.createStructures(entities);

            System.out.println("Количество созданных структур " + numberOfCreatedStrucure);


            try (BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile, false))) {
                entitiesService.writeEntities(entities, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
