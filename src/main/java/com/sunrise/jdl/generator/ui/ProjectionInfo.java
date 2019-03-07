package com.sunrise.jdl.generator.ui;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sunrise.jdl.generator.actions.Action;
import com.sunrise.jdl.generator.service.iad.ProjectionType;

import java.util.List;

/**
 * Класс используется для записи данных в json-фррмате в файл
 */
public class ProjectionInfo extends RegistryItem {

    /**
     * Список полей
     */
    private List<BaseField> fields;

    @JsonIgnore
    private ProjectionType projectionType;

    /**
     * Порядок для сортировки
     */
    private int order;

    /**
     * Список действий которые можно производить с объектом
     */
    private List<Action> actions;

    /**
     * URL  ресусра для поиска данных для проекции
     */
    private String searchUrl;

    /**
     * Список фильров которые применяются к проекции.
     * Проекция может описывать определенный набор данных для. Для получения этого набора данных испольузеются фильтры.
     */
    private List<ProjectionFilter> filters;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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

    public void setSearchUrl(String searchUrl) {
        this.searchUrl = searchUrl;
    }

    public String getSearchUrl() {
        return searchUrl;
    }

    public ProjectionType getProjectionType() {
        return projectionType;
    }

    public void setProjectionType(ProjectionType projectionType) {
        this.projectionType = projectionType;
    }
}
