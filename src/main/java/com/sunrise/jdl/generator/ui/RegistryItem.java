package com.sunrise.jdl.generator.ui;

public class RegistryItem {

    /**
     * Имя
     */
    private String label;

    /**
     * Код
     */
    private String code;

    /**
     * Код родителя
     */
    private String parentCode;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
}
