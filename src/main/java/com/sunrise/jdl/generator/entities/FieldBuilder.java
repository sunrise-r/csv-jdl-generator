package com.sunrise.jdl.generator.entities;

public class FieldBuilder {
    private String fieldType;
    private String fieldName;
    private String fieldLength;
    private boolean jdlType;
    private boolean required;
    private String fieldLabel;

    public Field build(){
        return new Field(fieldType, fieldName, fieldLength, jdlType, required, fieldLabel);
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

    public boolean getJdlType() {
        return jdlType;
    }

    public void setJdlType(boolean jdlType) {
        this.jdlType = jdlType;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getFieldLabel() {
        return fieldLabel;
    }

    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }
}
