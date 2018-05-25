package com.sunrise.jdl.generator;

import com.sunrise.jdl.generator.entities.Entity;
import com.sunrise.jdl.generator.entities.Relation;
import org.apache.commons.cli.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {

        Options options = new Options();
        options.addOption("sourceFolder", true, "set source folder with csv files");
        options.addOption("help", false, "show this help");
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
            EntitiesService entitiesService = new EntitiesService(resources);
            List<Entity> entities = entitiesService.readAll();
            int numberOfCorrection = entitiesService.correctsFieldsType(entities);
            int numberOfStructure = entitiesService.createStructure(entities);

            //TODO:  вывод должен быть в файл
            //TODO: название файла и путь до файла в который выводятся данные стоит указывать в аргументах
            System.out.printf("Количество корректировок полей %d\n", numberOfCorrection);
            System.out.println("Количество созданных структур " + numberOfStructure);


            try (BufferedWriter writer = new BufferedWriter(new FileWriter("result.txt", false))) {
                for (int i = 0; i < entities.size(); i++) {
                    entitiesService.writeEntityToFile(entities.get(i), writer);
                    writer.write("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
