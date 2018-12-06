package com.sunrise.jdl.generator.ui;

import java.util.List;

/**
 * ProjectionGenerationParameters
 */
public class ProjectionParameter {
    /**
     * Name of projections
     */
    private String name;

    /**
     * Order of projection
     */
    private int order;

    /**
     * List of filters for projection
     */
    private List<ProjectionFilter> filters;



    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProjectionFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<ProjectionFilter> filters) {
        this.filters = filters;
    }
}
