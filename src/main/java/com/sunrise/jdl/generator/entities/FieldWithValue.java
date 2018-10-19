package com.sunrise.jdl.generator.entities;

public class FieldWithValue extends Field {

    private String defaultValue;

    private FieldInputType fieldInputType;

    public FieldInputType getFieldInputType() {
        return fieldInputType;
    }

    public void setFieldInputType(FieldInputType fieldInputType) {
        this.fieldInputType = fieldInputType;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * Конструктор
     *  @param fieldType    тип поля
     * @param fieldName    название поля
     * @param fieldLength  длина поля
     * @param jdlType      является ли поле сопоставимым с типом JDL
     * @param required     является ли поле обязательным
     * @param fieldLabel   метка поля
     * @param defaultValue предустановленное значение
     * @param fieldInputType
     */
    public FieldWithValue(String fieldType, String fieldName, String fieldLength, boolean jdlType, boolean required, String fieldLabel, String defaultValue, FieldInputType fieldInputType) {
        super(fieldType, fieldName, fieldLength, jdlType, required, fieldLabel);
        this.defaultValue = defaultValue;
        this.fieldInputType = fieldInputType;
    }

    public enum FieldInputType {
        DISABLED, READONLY, WRITABLE
    }
}
