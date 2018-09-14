package com.sunrise.jdl.generator.service;

import com.sunrise.jdl.generator.entities.Entity;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.*;

public class EntityTypeService {

    public static final int TYPE_NAME = 1;
    public static final int SUBTYPE_NAME = 3;

    public Map<String, List<String>> readCsv(InputStream resource) {
        Map<String, List<String>> result = new HashMap<>();
        try {
            Reader in = new InputStreamReader(resource);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
            String lastType = "";
            for (CSVRecord record : records) {
                String fieldType = record.get(TYPE_NAME).trim();
                String fieldSubtype = record.get(SUBTYPE_NAME).trim();
                if(!fieldSubtype.isEmpty()) {
                    if (fieldType.equals("")) {
                        if(lastType.equals(""))
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

    public Map<String, List<Entity>> mergeTypesWithThemSubtypes(Collection<Entity> entities, Map<String,List<String>> typesMap) throws Exception{
        Map<String, List<Entity>> result = new HashMap<>();
        LinkedList<String> childNames = new LinkedList<>();
        LinkedList<Entity> childes = new LinkedList<>();
        LinkedList<String> parents = new LinkedList<>();


        for (Map.Entry<String, List<String>> pair : typesMap.entrySet()) {
            childNames.addAll(pair.getValue());
            for (String child : pair.getValue()) {
                parents.add(pair.getKey());
            }
            result.put(pair.getKey(),new ArrayList<>());
        }
        for (String childName : childNames) {
            boolean broken = false;
            for (Entity entity : entities) {
                if(entity.getClassName().equals(childName)){
                    childes.add(entity);
                    broken = true;
                    break;
                }
            }
            if(!broken) {
                throw new Exception("Child entity " + childName + " has no pair to merge!");
            }
        }

        for(int i = 0; i < parents.size(); i++) {
            result.get(parents.get(i)).add(childes.get(i));
        }

        return result;
    }

}
