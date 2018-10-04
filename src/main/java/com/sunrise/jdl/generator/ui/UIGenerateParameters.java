package com.sunrise.jdl.generator.ui;

import java.util.List;

/**
 * Параметры генерации инфомрации об ui интерфейсе
 */
public class UIGenerateParameters {

    private String translationPath;

    private String registryCode;

    private List<ProjectionParameter> projectionsInfoes;

    private boolean useEntityName;

    private String microservice;

    private boolean pluralTranslations;

    private boolean pluralPresentations;

    private boolean pluralSearchURL;

    public boolean isPluralSearchURL() {
        return pluralSearchURL;
    }

    public void setPluralSearchURL(boolean pluralSearchURL) {
        this.pluralSearchURL = pluralSearchURL;
    }

    public boolean isPluralTranslations() {
        return pluralTranslations;
    }

    public void setPluralTranslations(boolean pluralTranslations) {
        this.pluralTranslations = pluralTranslations;
    }

    public boolean isPluralPresentations() {
        return pluralPresentations;
    }

    public void setPluralPresentations(boolean pluralPresentations) {
        this.pluralPresentations = pluralPresentations;
    }

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

    public String getTranslationPath() {
        return translationPath;
    }

    public void setTranslationPath(String translationPath) {
        this.translationPath = translationPath;
    }

    public boolean isUseEntityName() {
        return useEntityName;
    }

    public void setUseEntityName(boolean useEntityName) {
        this.useEntityName = useEntityName;
    }

    public String getMicroservice() {
        return microservice;
    }

    public void setMicroservice(String microservice) {
        this.microservice = microservice;
    }
}
