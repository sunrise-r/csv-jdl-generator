package com.sunrise.jdl.generator.entities;

public class JdlField {
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
     * Validation Paramters
     */
    private String validation;

    public JdlField() {
    }

    public JdlField(String fieldType, String fieldLength, String fieldName) {
        this.fieldType = fieldType;
        this.fieldLength = fieldLength;
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldLength() {
        return fieldLength;
    }

    public void setFieldLength(String fieldLength) {
        this.fieldLength = fieldLength;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }
}
