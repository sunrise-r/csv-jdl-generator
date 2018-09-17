package com.sunrise.jdl.generator.service;

import com.sunrise.jdl.generator.entities.Entity;
import com.sunrise.jdl.generator.entities.Field;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Сервис чтения сущности из CSV файла
 */
public class CSVEntityReader {

    /**
     * Константые поля содержат номера ячеек в электронной таблице,
     * из которых берутся соотвествующие значения.
     */
    public static final int CLASSNAME = 1;
    public static final int ENTITY_LABEL = 2;
    public static final int ENTITY_TITLE = 3;
    public static final int FIELDNAME = 4;
    public static final int FIELD_LABEL = 5;

    public static final int FIELDTYPE = 7;
    public static final int FIELDSIZE = 8;
    public static final int FIELD_REQUIRED = 10;
    private final Set<String> fieldsToIngore;
    private final Set<String> entitiesToIngore;
    private final Set<String> convertableToJdlTypes;


    public CSVEntityReader(Set<String> fieldsToIngore, Set<String> entitiesToIngore, Set<String> convertableToJdlTypes) {
        this.fieldsToIngore = fieldsToIngore;
        this.entitiesToIngore = entitiesToIngore;
        this.convertableToJdlTypes = convertableToJdlTypes;
    }

    /**
     * Метод читает сущности из передаваемого .csv файла
     *
     * @param stream Поток с данными о сущностях
     * @return entitiesToIngore Список сущностей сформированных на основе потока данных
     */
    public java.util.Collection<Entity> readDataFromCSV(InputStream stream) {
        Map<String, Entity> toReturn = new LinkedHashMap<>();
        try {
            Reader in = new InputStreamReader(stream);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
            String className = "";
            for (CSVRecord record : records) {
                String possibleClassName = record.get(CLASSNAME).trim();
                String fieldName = record.get(FIELDNAME).trim();
                String fieldType = record.get(FIELDTYPE).trim();
                String fieldLength = record.get(FIELDSIZE).trim();
                String entityLabel = record.get(ENTITY_LABEL).trim();
                String required = record.get(FIELD_REQUIRED).trim();
                String fieldLabel = record.get(FIELD_LABEL).trim();
                String entityTitle = record.get(ENTITY_TITLE).trim();

                if (!possibleClassName.equals("") && !possibleClassName.contains("П") && !possibleClassName.isEmpty() && !entitiesToIngore.contains(possibleClassName)) {
                    className = possibleClassName;
                    Field field = new Field(convertFieldType(fieldType), fieldName, fieldLength, isFieldOfJdlType(fieldType), isRequired(required), fieldLabel);
                    ArrayList<Field> arrayList = new ArrayList<>();
                    if (!fieldsToIngore.contains(fieldName)) {
                        arrayList.add(field);
                    }
                    Entity entity = new Entity(className, arrayList, entityLabel, entityTitle);
                    toReturn.put(className, entity);
                } else if (possibleClassName.equals("") && toReturn.size() > 0 && !fieldsToIngore.contains(fieldName)) {
                    toReturn.get(className).getFields().add(new Field(convertFieldType(fieldType), fieldName, fieldLength, isFieldOfJdlType(fieldType), isRequired(required), fieldLabel));
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
     * Конвертировать тип из формата описания в JDL
     *
     * @param source Исходные данные для конвертации
     * @return В случае если нелзья сконвертировать, возвращает исходыне данные.
     */
    private String convertFieldType(String source) {
        String check = source.toLowerCase();
        if (!isFieldOfJdlType(check)) {
            return source;
        }
        if ("строка".equals(check)) {
            return EntitiesService.JDLFieldsType.String.toString();
        }
        if ("дата/время".equals(check)) {
            return EntitiesService.JDLFieldsType.ZonedDateTime.toString();

        }
        if ("число".equals(check)) {
            return EntitiesService.JDLFieldsType.Integer.toString();
        }
        if ("дробное".equals(check)) {
            return EntitiesService.JDLFieldsType.BigDecimal.toString();
        }
        if ("булев".equals(check)) {
            return EntitiesService.JDLFieldsType.Boolean.toString();
        }


        throw new RuntimeException("Неудалось распарсить исходыне данные в JDL тип=" + check);
    }

    /**
     * По значение колонки required опреелеяет является ли поле обязательным.
     *
     * @param required Значение поля required
     * @return Истина если поле является обязательным, иначе ложь.
     */
    private boolean isRequired(String required) {
        return required != null && (required.length() > 0);
    }

    /**
     * Возвращает истину если @fieldType может быть преобразован к типу jdl
     *
     * @param fieldType Тип который надо проверить
     * @return Истина если можно привести к формату jdl иначе ложь
     */
    private boolean isFieldOfJdlType(String fieldType) {
        return convertableToJdlTypes.contains(fieldType.toLowerCase());
    }

}
