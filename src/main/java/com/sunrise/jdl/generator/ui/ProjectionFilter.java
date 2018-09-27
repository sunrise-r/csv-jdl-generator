package com.sunrise.jdl.generator.ui;

import java.util.List;

/**
 * Параметры фильтра для проекции
 */
public class ProjectionFilter {

    /**
     * Название фильтра
     */
    private String name;

    /**
     * Значения фильтров
     */
    private List<String> values;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
