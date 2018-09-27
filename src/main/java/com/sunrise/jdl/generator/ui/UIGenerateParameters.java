package com.sunrise.jdl.generator.ui;

import java.util.List;

/**
 * Параметры генерации инфомрации об ui интерфейсе
 */
public class UIGenerateParameters {

    private String registryCode;

    private List<String> projectionsTypes;

    public String getRegistryCode() {
        return registryCode;
    }

    public void setRegistryCode(String registryCode) {
        this.registryCode = registryCode;
    }

    public List<String> getProjectionsTypes() {
        return projectionsTypes;
    }

    public void setProjectionsTypes(List<String> projectionsTypes) {
        this.projectionsTypes = projectionsTypes;
    }
}
