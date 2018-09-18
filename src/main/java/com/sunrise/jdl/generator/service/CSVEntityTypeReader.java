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

    public static final int TYPE_NAME = 1;
    public static final int SUBTYPE_NAME = 3;
    public static final int TYPE_NAME_ENG = 7;

    public Map<String, List<String>> readDataFromCSV(InputStream resource){
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
                        result.get(lastType).add(fieldSubtype);
                    } else {
                        lastType = fieldType;
                        result.put(fieldType, new ArrayList<>());
                        result.get(fieldType).add(fieldSubtype);
                    }
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

    public Map<ModuleInfo, List<String>> readFullTypes(InputStream resource) {
        Map<ModuleInfo, List<String>> result = new HashMap<>();





        try {
            Reader in = new InputStreamReader(resource);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
            ModuleInfo lastType = null;
            for (CSVRecord record : records) {
                String fieldType = record.get(TYPE_NAME).trim();
                String fieldSubtype = record.get(SUBTYPE_NAME).trim();
                String fieldModule = record.get(TYPE_NAME_ENG).trim();
                if (!fieldSubtype.isEmpty()) {
                    if (fieldType.equals("")) {
                        if (lastType == null)
                            throw new IOException(); // Доделать
//                        result.get(lastType).add(fieldSubtype);
                    } else {
//                        lastType = fieldType;
//                        result.put( //, new ArrayList<>());
//                        result.get(fieldType).add(fieldSubtype);
                    }
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
