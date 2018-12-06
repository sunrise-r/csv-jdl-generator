package com.sunrise.jdl.generator.entities;

public class LinkedField extends Field {

    private String lookupViewProjectionCode;

    private String lookupSourceProjectionCode;

    public LinkedField(String fieldType, String fieldName, String fieldLength, boolean jdlType, boolean required, String fieldLabel, boolean hidden, String lookupViewProjectionCode, String lookupSourceProjectionCode) {
        super(fieldType, fieldName, fieldLength, jdlType, required, fieldLabel, hidden);
        this.lookupViewProjectionCode = lookupViewProjectionCode;
        this.lookupSourceProjectionCode = lookupSourceProjectionCode;
    }

    public LinkedField(Field field, String lookupViewProjectionCode, String lookupSourceProjectionCode) {
        super(field.getFieldType(), field.getFieldName(), field.getFieldLength(), field.isJdlType(), field.isRequired(), field.getFieldLabel(), field.isHidden());
        this.lookupViewProjectionCode = lookupViewProjectionCode;
        this.lookupSourceProjectionCode = lookupSourceProjectionCode;
    }

    public String getLookupViewProjectionCode() {
        return lookupViewProjectionCode;
    }

    public void setLookupViewProjectionCode(String lookupViewProjectionCode) {
        this.lookupViewProjectionCode = lookupViewProjectionCode;
    }

    public String getLookupSourceProjectionCode() {
        return lookupSourceProjectionCode;
    }

    public void setLookupSourceProjectionCode(String lookupSourceProjectionCode) {
        this.lookupSourceProjectionCode = lookupSourceProjectionCode;
    }
}
