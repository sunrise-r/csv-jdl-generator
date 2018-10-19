package com.sunrise.jdl.generator.entities;

import java.util.Objects;

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
     * Длина поля.
     */
    private String fieldLength;

    /**
     * Если тип поля поддерживается в JDL - отмечаем его true.
     * При создании Field данное поле в конструкторе устанавливается false.
     */
    private boolean JdlType;

    /**
     * Является ли поле обязательным
     */
    private boolean required;

    /**
     * Метка поля. Название поля, которое должно отображаться пользователю
     */
    private String fieldLabel;

    /**
     * Конструктор
     *  @param fieldType   тип поля
     * @param fieldName   название поля
     * @param fieldLength длина поля
     * @param jdlType     является ли поле сопоставимым с типом JDL
     * @param required    является ли поле обязательным
     * @param fieldLabel метка поля
     */
    public Field(String fieldType, String fieldName, String fieldLength, boolean jdlType, boolean required, String fieldLabel) {
        this.fieldType = fieldType;
        this.fieldName = fieldName;
        this.fieldLength = fieldLength;
        this.JdlType = jdlType;
        this.required = required;
        this.fieldLabel = fieldLabel;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public boolean isJdlType() {
        return JdlType;
    }

    public Field fieldType(String fieldType) {
        this.fieldType = fieldType;
        return this;
    }

    public String getFieldLength() {
        return fieldLength;
    }

    public boolean isRequired() {
        return required;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setFieldLength(String fieldLength) {
        this.fieldLength = fieldLength;
    }

    public void setJdlType(boolean jdlType) {
        JdlType = jdlType;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    /**
     * Возвращает строковое представление в формате jdl.
     *
     * @return string in the jdl-format
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(fieldName);
        sb.append(" ");
        sb.append(fieldType);
        if (required) {
            sb.append(" ");
            sb.append("required");
        }
        if (fieldLength != null && fieldLength.length() > 0 && fieldType.equals("String")) {
            sb.append(" ");
            sb.append("maxlength(");
            sb.append(fieldLength);
            sb.append(")");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return Objects.equals(fieldType, field.fieldType) &&
                Objects.equals(fieldName, field.fieldName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldType, fieldName);
    }

    public String getFieldLabel() {
        return fieldLabel;
    }

    public Field clone() {
        return new Field(fieldType, fieldName, fieldLength, JdlType, required, fieldLabel);
    }
}
