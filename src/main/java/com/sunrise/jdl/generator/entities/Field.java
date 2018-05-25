package com.sunrise.jdl.generator.entities;

/**
 * Класс с описанием полей сущности.
 * Types of supported in JDL fields:
 * String: A Java String. Its default size depends on the underlying backend (if you use JPA, it’s 255 by default), but you can change it using the validation rules (putting a max size of 1024, for example).
 * Integer: A Java Integer.
 * Long: A Java Long.
 * Float: A Java Float.
 * Double: A Java Double.
 * BigDecimal: A java.math.BigDecimal object, used when you want exact mathematic calculations (often used for financial operations).
 * LocalDate: A java.time.LocalDate object, used to correctly manage dates in Java.
 * Instant: A java.time.Instant object, used to represent a timestamp, an instantaneous point on the time-line.
 * ZonedDateTime: A java.time.ZonedDateTime object, used to represent a local date-time in a given timezone (typically a calendar appointment). Note that time zones are neither supported by the REST nor by the persistence layers so you should most probably use Instant instead.
 * Boolean: A Java Boolean.
 * Enumeration: A Java Enumeration object. When this type is selected, the sub-generator will ask you what values you want in your enumeration, and it will create a specific enum class to store them.
 * Blob: A Blob object, used to store some binary data. When this type is selected, the sub-generator will ask you if you want to store generic binary data, an image object, or a CLOB (long text). Images will be handled specifically on the Angular side, so they can be displayed to the end-user.
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
     * Если поле является сущностью(оодержит слово "Список") - отмечаем его true.
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
        if (fieldType.contains("Список")) {  // TODO может быть это условие лучше вынести в метод коррекции полей или в отдельный метод?
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
     * @return string for jdl
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
