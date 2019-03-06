package com.sunrise.jdl.generator.service.iad;

public class UIField {

    private String type;

    private String name;

    private String length;

    private boolean required;

    private String label;

    private String lookupViewProjectionCode;

    private String lookupSourceProjectionCode;

    private String presentationCode;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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

    public String getPresentationCode() {
        return presentationCode;
    }

    public void setPresentationCode(String presentationCode) {
        this.presentationCode = presentationCode;
    }
}
