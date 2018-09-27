package com.sunrise.jdl.generator.ui;

import java.util.List;

/**
 * Параметры генерации инфомрации об ui интерфейсе
 */
public class UIGenerateParameters {

    private String registryCode;

    private List<ProjectionParameter> projectionsInfoes;

    public String getRegistryCode() {
        return registryCode;
    }

    public void setRegistryCode(String registryCode) {
        this.registryCode = registryCode;
    }

    public List<ProjectionParameter> getProjectionsInfoes() {
        return projectionsInfoes;
    }

    public void setProjectionsInfoes(List<ProjectionParameter> projectionsInfoes) {
        this.projectionsInfoes = projectionsInfoes;
    }
}
