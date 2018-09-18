package com.sunrise.jdl.generator.service;

import com.sunrise.jdl.generator.entities.ModuleInfo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVEntityTypeReader {

    public static final int MODULE_LABEL = 0;
    public static final int TYPE_NAME = 1;
    public static final int SUBTYPE_NAME = 3;
    public static final int MODULE_NAME = 7;

    public Map<String, List<String>> readDataFromCSV(InputStream resource) {
        Map<String, List<String>> result = new HashMap<>();
        try {
            Reader in = new InputStreamReader(resource);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
            String lastType = "";
            for (CSVRecord record : records) {
                String fieldType = record.get(TYPE_NAME).trim();
                String fieldSubtype = record.get(SUBTYPE_NAME).trim();
                if (!fieldSubtype.isEmpty()) {
                    if (fieldType.equals("")) {
                        if (lastType.equals(""))
                            throw new IOException();
                    } else {
                        lastType = fieldType;
                        result.put(fieldType, new ArrayList<>());
                    }
                    result.get(lastType).add(fieldSubtype);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Can't find file");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IO exception");
            e.printStackTrace();
        }
        return result;
    }


    public Map<ModuleInfo, List<String>> readWithModules(InputStream resource) {
        Map<ModuleInfo, List<String>> result = new HashMap<>();
        try {
            Reader in = new InputStreamReader(resource);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
            ModuleInfo lastModule = null;
            boolean skipTitles = true;
            for (CSVRecord record : records) {
                String fieldType = record.get(TYPE_NAME).trim();
                String fieldModuleName = record.get(MODULE_NAME).trim();
                String fieldLabel = record.get(MODULE_LABEL).trim();
                if (!fieldType.isEmpty()) {
                    if (!fieldModuleName.isEmpty()) {
                        if(skipTitles) {
                            skipTitles = false;
                            continue;
                        }
                        lastModule = new ModuleInfo(fieldLabel, fieldModuleName);
                        result.put(lastModule, new ArrayList<>());
                    }
                    result.get(lastModule).add(fieldType);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Can't find file");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IO exception");
            e.printStackTrace();
        }
        return result;
    }

}
