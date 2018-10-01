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

    private String searchUrl;

    /**
     * Список фильров которые применяются к проекции.
     * Проекция может описывать определенный набор данных для. Для получения этого набора данных испольузеются фильтры.
     */
    private List<ProjectionFilter> filters;

    public String getSearchUrl() {
        return searchUrl;
    }

    public void setSearchUrl(String searchUrl) {
        this.searchUrl = searchUrl;
    }

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
