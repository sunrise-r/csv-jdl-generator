package com.sunrise.jdl.generator.service;

import com.sunrise.jdl.generator.entities.Entity;
import com.sunrise.jdl.generator.entities.Field;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.*;

public class EntityTypeService {

    public static final int TYPE_NAME = 1;
    public static final int SUBTYPE_NAME = 3;


    /**
     * Гененирует карту со связями НазваниеРодителя - Список<Названия потомков> из CSV
     *
     * @param resource CSV-файл, в котором описаны отношения между сущностями
     * @return Карта с описанием связей, но самих сущностей нет
     */
    public Map<String, List<String>> readCsv(InputStream resource) {
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

    public void countFieldsFrequency(Map<String, List<Entity>> parentWithChild) {
        for (Map.Entry<String, List<Entity>> relation : parentWithChild.entrySet()) {
            Map<String, Integer> fieldsFrequency = new HashMap<>();
            int entityNumber = 0;
            for (Entity entity : relation.getValue()) {
                entityNumber = entity.getFields().size();
                for (Field field : entity.getFields()) {
                    if (fieldsFrequency.containsKey(field.getFieldName())) {
                        fieldsFrequency.put(field.getFieldName() + " " + field.getFieldType(), fieldsFrequency.get(field.getFieldName()) + 1);
                    } else {
                        fieldsFrequency.put(field.getFieldName() + " " + field.getFieldType(), 1);
                    }
                }
            }
            checkFieldFrequency(fieldsFrequency, entityNumber);
        }
    }

    private int checkFieldFrequency(Map<String, Integer> fieldsFrequency, int entityNumber) {
        Iterator<Map.Entry<String, Integer>> iterator = fieldsFrequency.entrySet().iterator();
        int numberRemovedFields = 0;
        while (iterator.hasNext()) {
            int fieldFrequency = iterator.next().getValue();
            if (fieldFrequency < entityNumber) {
                iterator.remove();
                numberRemovedFields++;
            }
        }
        return numberRemovedFields;
    }


}
