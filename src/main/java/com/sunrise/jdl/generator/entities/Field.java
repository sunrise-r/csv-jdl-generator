package com.sunrise.jdl.generator.entities;

/**
 * Класс с описанием поля класса.
 * TODO: не плохо бы добавить описания из документации, пускай и на английском
 */
public class Field {

    /**
     * Тип поля
     */
    private String fieldType;

    /**
     * Название поля
     */
    private String fieldName;

    /**
     * Длинна поля.
     */
    private String fieldLength;

    /**
     * Если поле является сущностью - отмечаем его true.
     */
    private boolean entity;

    /**
     * Конструктор
     *
     * @param fieldType   тип поля
     * @param fieldName   название поля
     * @param fieldLength длинна поля
     */
    public Field(String fieldType, String fieldName, String fieldLength) {
        this.fieldType = fieldType;
        this.fieldName = fieldName;
        this.fieldLength = fieldLength;
        if (fieldType.contains("Список")) {         // TODO может быть это условие лучше вынести в метод коррекции полей?
            this.entity = true;
        }
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldLength() {
        return fieldLength;
    }

    public void setFieldLength(String fieldLength) {
        this.fieldLength = fieldLength;
    }

    public boolean isEntity() {
        return entity;
    }

    /**
     * Возвращает описание поля в формте jdl
     *
     * @return
     */
    @Override
    public String toString() {
//        if (fieldLength.equals("")) {
//            return fieldName + " " + fieldType + ",\n";
//        } else {
//            return fieldName + " " + fieldType + "(" + fieldLength + ")" + ",\n";
//        }
        return fieldName + " " + fieldType + ",\n";
    }
}
