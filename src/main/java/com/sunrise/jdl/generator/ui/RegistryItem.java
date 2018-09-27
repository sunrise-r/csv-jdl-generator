package com.sunrise.jdl.generator.ui;

public class RegistryItem {

    /**
     * Имя
     */
    private String name;

    /**
     * Код
     */
    private String code;

    /**
     * Код родителя
     */
    private String parentCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
