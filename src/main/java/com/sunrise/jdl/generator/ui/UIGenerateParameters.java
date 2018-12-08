package com.sunrise.jdl.generator.ui;

import com.google.common.collect.Lists;
import com.sunrise.jdl.generator.entities.Field;
import com.sunrise.jdl.generator.entities.FieldBuilder;

import java.util.List;

/**
 * Параметры генерации инфомрации об ui интерфейсе
 */
public class UIGenerateParameters {

    private String translationPath;

    private String registryCode;

    private List<ProjectionParameter> projectionsInfoes = Lists.newArrayList();

    private boolean useEntityName;

    private String microservice;

    private boolean pluralTranslations;

    private boolean pluralPresentations;

    private boolean pluralSearchURL;

    private List<FieldBuilder> additionalFields = Lists.newArrayList();

    private String additionalFieldsTranslationPath;

    private String titlePath;

    public String getTitlePath() {
        return titlePath;
    }

    public void setTitlePath(String titlePath) {
        this.titlePath = titlePath;
    }

    public String getAdditionalFieldsTranslationPath() {
        return additionalFieldsTranslationPath;
    }

    public void setAdditionalFieldsTranslationPath(String additionalFieldsTranslationPath) {
        this.additionalFieldsTranslationPath = additionalFieldsTranslationPath;
    }

    public List<FieldBuilder> getAdditionalFields() {
        return additionalFields;
    }

    public void setAdditionalFields(List<FieldBuilder> additionalFields) {
        this.additionalFields = additionalFields;
    }

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
