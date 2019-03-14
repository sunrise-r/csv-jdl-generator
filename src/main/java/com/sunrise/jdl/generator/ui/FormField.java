package com.sunrise.jdl.generator.ui;

public class FormField extends BaseField {

    private String lookupSource;

    private String lookupView;

    private String referenceProjectionCode;

    private String presentationCode;

    private String fieldLength;

    private String fieldType;

    private boolean required;

    private boolean jdlType;

    public FormField lookup(String entityName) {
        this.lookupSource = String.format("lookup%sSourceListProjection", entityName);
        this.lookupView = String.format("lookup%sViewListProjection", entityName);
        return this;
    }

    public FormField referenceProjectionCode(String referenceProjectionCode) {
        this.referenceProjectionCode = referenceProjectionCode;
        return this;
    }

    public FormField presentationCode(String presentationCode) {
        this.presentationCode = presentationCode;
        return this;
    }

    public FormField length(String length) {
        this.fieldLength = length;
        return this;
    }

    public FormField fieldType(String fieldType) {
        this.fieldType = fieldType;
        return this;
    }

    public FormField required(boolean required) {
        this.required = required;
        return this;
    }

    public FormField jdlType(boolean jdlType) {
        this.jdlType = jdlType;
        return this;
    }

    public String getLookupSource() {
        return lookupSource;
    }

    public void setLookupSource(String lookupSource) {
        this.lookupSource = lookupSource;
    }

    public String getLookupView() {
        return lookupView;
    }

    public void setLookupView(String lookupView) {
        this.lookupView = lookupView;
    }

    public String getPresentationCode() {
        return presentationCode;
    }

    public void setPresentationCode(String presentationCode) {
        this.presentationCode = presentationCode;
    }

    public String getFieldLength() {
        return fieldLength;
    }

    public void setFieldLength(String fieldLength) {
        this.fieldLength = fieldLength;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isJdlType() {
        return jdlType;
    }

    public void setJdlType(boolean jdlType) {
        this.jdlType = jdlType;
    }

    public String getReferenceProjectionCode() {
        return referenceProjectionCode;
    }

    public void setReferenceProjectionCode(String referenceProjectionCode) {
        this.referenceProjectionCode = referenceProjectionCode;
    }
}
