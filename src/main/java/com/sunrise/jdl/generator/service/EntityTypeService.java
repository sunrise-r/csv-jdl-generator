package com.sunrise.jdl.generator.service;

import com.sunrise.jdl.generator.entities.Entity;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

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

    /**
     * Сгруппировать сущности относительно родителькой группы.
     *
     * @param entities Список доступных сущностей
     * @param typesMap Словарь где ключ - название группы сущности, а значение название сущностей которые входят в эту группу
     * @return Список сущностей сгруппированный по ггруппам из @typesMap
     */
    public Map<String, List<Entity>> mergeTypesWithThemSubtypes(Collection<Entity> entities, Map<String, List<String>> typesMap) {
        Map<String, List<Entity>> result = typesMap.keySet().stream().collect(Collectors.toMap(x -> x, x -> new LinkedList<>()));
        Map<String, Entity> entityMap = entities.stream().collect(Collectors.toMap(x -> x.getClassName(), x -> x));
        for (String group : typesMap.keySet()) {
            for (String entityName : typesMap.get(group)) {
                result.get(group).add(entityMap.get(entityName));
            }
        }
        return result;
    }

}
