package com.sunrise.jdl.generator;

import com.sunrise.jdl.generator.entities.Entity;
import org.apache.commons.cli.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main {

    public static void main(String[] args) {

        Options options = new Options();
        options.addOption("sourceFolder", true, "set source folder with csv files");
        options.addOption("help", false, "show this help");
        options.addOption("targetFile", true, "file with results");
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

        if (cmd.hasOption("help")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("jdlGenerator", options);
        }

        if (cmd.hasOption("sourceFolder")) {
            File directory = new File(cmd.getOptionValue("sourceFolder"));
            File[] files = directory.listFiles();
            List<InputStream> resources = new ArrayList<InputStream>(files.length);
            for (File f : files) {
                try {
                    resources.add(new FileInputStream(f));
                } catch (FileNotFoundException e) {
                    System.err.println("Failed to read file .  Reason: " + e.getMessage());
                }
            }

            File targetFile;
            if (cmd.hasOption("targetFile")) {
                targetFile = new File(cmd.getOptionValue("targetFile"));
            } else {
                targetFile = new File("result.txt");
            }



            EntitiesService entitiesService = new EntitiesService(resources);
            List<Entity> entities = entitiesService.readAll();
            int numberOfCorrection = entitiesService.correctsFieldsType(entities);
            entitiesService.checkIsFieldAnEntity(entities);
            int numberOfCreatedStrucure = entitiesService.createStructure(entities);

            System.out.printf("Количество корректировок полей %d\n", numberOfCorrection);
            System.out.println("Количество созданных структур " + numberOfCreatedStrucure);


            try (BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile, false))) {
               entitiesService.writeEntityToFile(entities, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
