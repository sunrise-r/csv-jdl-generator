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
    private final String fieldName;

    /**
     * Длинна поля.
     */
    private final String fieldLength;

    /**
     * Если тип поля поддерживается в JDL - отмечаем его true.
     * При создании Field данное поле в конструкторе устанавливается false.
     */
    private final boolean JdlType;

    /**
     * Конструктор
     *
     * @param fieldType   тип поля
     * @param fieldName   название поля
     * @param fieldLength длинна поля
     * @param jdlType     является ли поле сопоставимым с типом JDL
     */
    public Field(String fieldType, String fieldName, String fieldLength, boolean jdlType) {
        this.fieldType = fieldType;
        this.fieldName = fieldName;
        this.fieldLength = fieldLength;
        this.JdlType = jdlType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public boolean isJdlType() {
        return JdlType;
    }

    /**
     * Возвращает строковое представление в формте jdl.
     *
     * @return string in jdl-format
     */
    @Override
    public String toString() {
        return fieldName + " " + fieldType;
    }
}
