package com.sunrise.jdl.generator;

import com.sunrise.jdl.generator.entities.Entity;
import com.sunrise.jdl.generator.entities.Field;
import com.sunrise.jdl.generator.entities.Relation;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Считывает Entity, корректириреут их поля, создает их структуру,
 * записывает Entity и их структуру в файл
 */
public class EntitiesService {

    /**
     * Константые поля содержат номера ячеек в электронной таблице,
     * из которых беруться соотвествующие значения.
     */
    public static final int CLASSNAME = 1;
    public static final int FIELDNAME = 2;
    public static final int FIELDTYPE = 5;
    public static final int FIELDSIZE = 6;
    private List<InputStream> resources;


    public EntitiesService(List<InputStream> resources) {
        this.resources = resources;
    }

    public List<Entity> readAll() {
        ArrayList<Entity> entities = new ArrayList<Entity>();
        for (InputStream st : resources) {
            entities.addAll(readDataFromCSV(st));
        }
        return entities;
    }

    private java.util.Collection<Entity> readDataFromCSV(InputStream stream) {
        Map<String, Entity> toReturn = new LinkedHashMap<String, Entity>();
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

    /**
     * Перегруженный вариант correctsFieldsType(Entity entity).
     * Принимает List<Entity> и корректирует у всех entity поля.
     *
     * @param entities
     * @return total number of correction for all entities
     */
    public int correctsFieldsType(List<Entity> entities) {
        int sumOfCorrection = 0;
        for (Entity entity : entities) {
            sumOfCorrection += correctsFieldsType(entity);
        }
        return sumOfCorrection;
    }

    /**
     * Метод корректирует тип полей у отдельной сущности в соотвествии с требованиями jdl.
     *
     * @param entity
     * @return number of corrections
     */
    public int correctsFieldsType(Entity entity) {
        int numberOfCorrection = 0;
        for (Field field : entity.getFields()) {
            String fieldType = field.getFieldType();
            if (fieldType.contains("Строка")) {
                field.setFieldType("String");
                numberOfCorrection++;
            }
            if (fieldType.contains("Дата")) {
                field.setFieldType("Instant");
                numberOfCorrection++;
            }
            if (fieldType.contains("Число")) {
                field.setFieldType("Long");
                numberOfCorrection++;
            }
        }
        return numberOfCorrection;
    }

    /**
     * Для каждой entity из entities вызывается метод createStructure(Entity entity)
     *
     * @param entities
     * @return total number of created structure
     */
    public int createStructure(List<Entity> entities) {
        int totalCreatedStructure = 0;
        for (Entity entity : entities) {
            totalCreatedStructure += this.createStructure(entity);
        }
        return totalCreatedStructure;
    }

    /**
     * Если сущность содержит в fields Список, метод создает объект Relation и
     * добавляет его в поле relations у Entity.
     * Метод возращает количество считанных структур.
     *
     * @param entity
     * @return number of created structure
     */
    public int createStructure(Entity entity) {
        int count = 0;
        ArrayList<Field> fields = entity.getFields();
        for (Field field : fields) {
            if (!field.isNotEntity() && field.getFieldType().contains("Список")) {
                count++;
                Relation relation = new Relation(entity, field, Relation.RelationType.OneToMany);
                entity.getRelations().add(relation);
            } else if (!field.isNotEntity()){
                Relation relation = new Relation(entity, field, Relation.RelationType.OneToOne);
                entity.getRelations().add(relation);
                count++;
            }
        }
        return count;
    }

    /**
     * Для каждой entity из enities вызывается метод writeEntityToFile(Entity entity, BufferedWriter writer)
     *
     * @param entities
     * @param writer
     * @return
     */
    public void writeEntityToFile(List<Entity> entities, BufferedWriter writer) throws IOException {
        for (Entity entity : entities) {
            writeEntityToFile(entity, writer);
        }
    }

    /**
     * Метод пишет сущности с полями и структурами в файл
     *
     * @param entity
     * @param writer
     * @throws IOException
     */
    public void writeEntityToFile(Entity entity, BufferedWriter writer) throws IOException {
        writer.write(entity.toString() + "\n");
        ArrayList<Relation> relations = entity.getRelations();
        if (relations.size() > 0) {
            for (Relation relation : relations) {
                writer.write(relation.toString() + "\n");
            }
        }
    }

    public int checkIsFieldAnEntity(List<Entity> entities) {
        int totalNumberOfCorrecion = 0;
        for (Entity entity : entities) {
         totalNumberOfCorrecion += checkIsFieldAnEntity(entity);
        }
        return totalNumberOfCorrecion;
    }


    public int checkIsFieldAnEntity(Entity entity) {
        int numberOfEntity = 0;
        ArrayList<Field> fields = entity.getFields();
        JDLFieldstype[] values = JDLFieldstype.values();
        for (Field field:fields) {
            for (JDLFieldstype value : values) {
                if (field.getFieldType().equals(value.toString())) {
                    field.setNotEntity(true);
                    numberOfEntity++;
                }
            }
        }
        return numberOfEntity;
    }

    public enum JDLFieldstype {
        String("String"), Integer("Integer"), Long("Long"), Float("Float"), Double("Double"),
        BigDecimal("BigDecimal"), LocalDate("LocalDate"), Instant("Instant"),
        ZonedDateTime("ZonedDateTime"), Boolean("Boolean"), Enumeration("Enumeration"),
        Blob("Blob");

        private String type;

        JDLFieldstype(java.lang.String type) {
            this.type = type;
        }

        @Override
        public java.lang.String toString() {
            return type;
        }
    }
}
