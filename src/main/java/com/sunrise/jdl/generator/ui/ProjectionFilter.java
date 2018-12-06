package com.sunrise.jdl.generator.ui;

import java.util.List;

/**
 * Параметры фильтра для проекции
 */
public class ProjectionFilter {

    /**
     * Название фильтра
     */
    private String field;

    /**
     * Значения фильтров
     */
    private List<String> values;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
