package com.sunrise.jdl.generator.ui;

import com.sunrise.jdl.generator.entities.BasicEntity;
import com.sunrise.jdl.generator.entities.Field;

import java.util.List;

public class FormProjection {

    private final String code;

    private final String title;

    private final String parentCode;

    private final List<Field> fields;

    private final String className;

    public FormProjection(String code, String title, String parentCode, List<Field> fields) {
        this.code = code;
        this.title = title;
        this.parentCode = parentCode;
        this.fields = fields;
        this.className = code;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getClassName() {
        return className;
    }

    public String getParentCode() {
        return parentCode;
    }

    public List<Field> getFields() {
        return fields;
    }
}
