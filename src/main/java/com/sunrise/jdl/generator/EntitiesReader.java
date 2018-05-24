package com.sunrise.jdl.generator;

import com.sunrise.jdl.generator.entities.Entity;
import com.sunrise.jdl.generator.entities.Field;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Читает данные из указанной исходной дирректории
 */
public class EntitiesReader {

    public static final int CLASSNAME = 1;
    public static final int FIELDNAME = 2;
    public static final int FIELDTYPE = 5;
    public static final int FIELDSIZE = 6;
    private List<InputStream> resources;

    public EntitiesReader(List<InputStream> resources) {
        this.resources = resources;
    }

    public List<Entity> readAll() {
        ArrayList<Entity> entities = new ArrayList<Entity>();
        for(InputStream st: resources){
            entities.addAll(readDataFromCSV(st));
        }
        return entities;
    }

    private java.util.Collection<Entity> readDataFromCSV(InputStream stream) {
        Map<String,Entity> toReturn = new LinkedHashMap<String,Entity>();
        try {
            Reader in = new InputStreamReader(stream);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
            String className = "";
            for (CSVRecord record : records) {
                String s1 = record.get(CLASSNAME);
                String fieldName = record.get(FIELDNAME);
                String fieldType = record.get(FIELDTYPE);
                String fieldLength = record.get(FIELDSIZE);

                if (!s1.equals("") && !s1.contains("П")) {
                    className = s1;
                    Field field = new Field(fieldType, fieldName, fieldLength);
                    ArrayList<Field> arrayList = new ArrayList<Field>();
                    arrayList.add(field);
                    Entity entity = new Entity(className, arrayList);
                    toReturn.put(className, entity);
                } else if (s1.equals("") && toReturn.size() > 0) {
                    toReturn.get(className).getFields().add(new Field(fieldType, fieldName, fieldLength));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Can't find file");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IO exception");
            e.printStackTrace();
        }
        return toReturn.values();
    }

}
