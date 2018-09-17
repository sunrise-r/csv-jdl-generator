package com.sunrise.jdl.generator.forJson;

import com.sunrise.jdl.generator.entities.Field;

public class BaseField {

    private String name;
    private String code;
    private boolean sorting = true;
    private boolean searching = true;
    private String displayFormat;

    public BaseField(Field field) {
        this.name = field.getFieldLabel();
        this.code = field.getFieldName();
        this.displayFormat = displayFormat;
    }

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

    public boolean isSorting() {
        return sorting;
    }

    public void setSorting(boolean sorting) {
        this.sorting = sorting;
    }

    public boolean isSearching() {
        return searching;
    }

    public void setSearching(boolean searching) {
        this.searching = searching;
    }

    public String getDisplayFormat() {
        return displayFormat;
    }

    public void setDisplayFormat(String displayFormat) {
        this.displayFormat = displayFormat;
    }
}
