package com.sunrise.jdl.generator.ui;

import com.sunrise.jdl.generator.actions.Action;

import java.util.List;

/**
 * Класс используется для записи данных в json-фррмате в файл
 */
public class ProjectionInfo extends RegistryItem{


    /**
     * Список полей
     */
    private List<BaseField> fields;

    /**
     * Список действий которые можно производить с объектом
     */
    private List<Action> actions;

    private List<ProjectionFilter> filters;


    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public List<BaseField> getFields() {
        return fields;
    }

    public void setFields(List<BaseField> fields) {
        this.fields = fields;
    }

    public List<ProjectionFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<ProjectionFilter> filters) {
        this.filters = filters;
    }
}
