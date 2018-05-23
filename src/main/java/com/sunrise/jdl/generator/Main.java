package com.sunrise.jdl.generator;

import com.sunrise.jdl.generator.EntitiesReader;
import com.sunrise.jdl.generator.entities.Entity;
import com.sunrise.jdl.generator.entities.Field;
import org.apache.commons.cli.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static Map<String, Entity> entities = new LinkedHashMap<String, Entity>();

    public static void main(String[] args) {

        Options options = new Options();
        options.addOption("sourceFolder", false, "set source folder with cvs files");
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
            formatter.printHelp("jtlGenerator", options);
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
            EntitiesReader reader = new EntitiesReader(resources);
            List<Entity> entities = reader.readAll();

            //TODO:  переделать на использование toString
            //TODO:  вывод должен быть в файл
            //TODO: название файла и путь до файла в который выводяться данные стоит указывать в аргументах
            for (Entity entity : entities) {
                System.out.println("class " + entity.getClassName() + "{");
                for (Field field : entity.getFields()) {
                    System.out.println("    " + field.getFieldType() + " " + field.getFieldName() + "(" + field.getFieldLength() + ");");
                }
                System.out.println("}");
            }
        }
    }
}
