package com.sunrise.jdl.generator.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * JDL Generation config file
 */
public class JdlConfig {

    /**
     * List entities that will be ignored while generating
     */
    private List<String> ignoreEntities = new ArrayList<>();

    /**
     * Fields that will be ignored during generation
     */
    private List<String> ignoreFields = new ArrayList<>();

    /**
     * Generate use map struct option in jdl
     */
    private boolean mapStruct = true;

    /**
     * Paginate type for JDL File. Possible values are: pager, pagination, infinite-scroll"
     */
    private String paginateType = "pagination";

    /**
     * For what entities service generation needed. List of entities or 'all'
     */
    //TODO: change type to LIST.
    private String generateServiceFor = "all";

    /**
     * For what entities service generation is not needed. List of entities
     */
    //TODO: change type to LIST
    private String exceptServiceGeneration = null;

    /**
     * name of microservice that will hold the entities. Can be null for monolith application
     */
    private String microservice = null;

    /**
     * Name of gateway application
     */
    private String gateway = null;

    /**
     * source folder with csv files
     */
    private String sourceFolder;

    /**
     * Target jhFile
     */
    private String targetFile = "entities.jh";

    /**
     * Path where generated i18n resources will be saved
     */
    private String targetResourceFolder;

    public List<String> getIgnoreEntities() {
        return ignoreEntities;
    }

    public void setIgnoreEntities(List<String> ignoreEntities) {
        this.ignoreEntities = ignoreEntities;
    }

    public boolean isMapStruct() {
        return mapStruct;
    }

    public void setMapStruct(boolean mapStruct) {
        this.mapStruct = mapStruct;
    }

    public String getPaginateType() {
        return paginateType;
    }

    public void setPaginateType(String paginateType) {
        this.paginateType = paginateType;
    }

    public String getGenerateServiceFor() {
        return generateServiceFor;
    }

    public void setGenerateServiceFor(String generateServiceFor) {
        this.generateServiceFor = generateServiceFor;
    }

    public String getExceptServiceGeneration() {
        return exceptServiceGeneration;
    }

    public void setExceptServiceGeneration(String exceptServiceGeneration) {
        this.exceptServiceGeneration = exceptServiceGeneration;
    }

    public String getMicroservice() {
        return microservice;
    }

    public void setMicroservice(String microservice) {
        this.microservice = microservice;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getSourceFolder() {
        return sourceFolder;
    }

    public void setSourceFolder(String sourceFolder) {
        this.sourceFolder = sourceFolder;
    }

    public String getTargetFile() {
        return targetFile;
    }

    public void setTargetFile(String targetFile) {
        this.targetFile = targetFile;
    }

    public String getTargetResourceFolder() {
        return targetResourceFolder;
    }

    public void setTargetResourceFolder(String targetResourceFolder) {
        this.targetResourceFolder = targetResourceFolder;
    }

    public List<String> getIgnoreFields() {
        return ignoreFields;
    }

    public void setIgnoreFields(List<String> ignoreFields) {
        this.ignoreFields = ignoreFields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JdlConfig jdlConfig = (JdlConfig) o;
        return mapStruct == jdlConfig.mapStruct &&
                Objects.equals(ignoreEntities, jdlConfig.ignoreEntities) &&
                Objects.equals(ignoreFields, jdlConfig.ignoreFields) &&
                Objects.equals(paginateType, jdlConfig.paginateType) &&
                Objects.equals(generateServiceFor, jdlConfig.generateServiceFor) &&
                Objects.equals(exceptServiceGeneration, jdlConfig.exceptServiceGeneration) &&
                Objects.equals(microservice, jdlConfig.microservice) &&
                Objects.equals(gateway, jdlConfig.gateway) &&
                Objects.equals(sourceFolder, jdlConfig.sourceFolder) &&
                Objects.equals(targetFile, jdlConfig.targetFile) &&
                Objects.equals(targetResourceFolder, jdlConfig.targetResourceFolder);
    }

    @Override
    public int hashCode() {

        return Objects.hash(ignoreEntities, ignoreFields, mapStruct, paginateType, generateServiceFor, exceptServiceGeneration, microservice, gateway, sourceFolder, targetFile, targetResourceFolder);
    }

    @Override
    public String toString() {
        return "JdlConfig{" +
                "ignoreEntities=" + ignoreEntities +
                ", ignoreFields=" + ignoreFields +
                ", mapStruct=" + mapStruct +
                ", paginateType='" + paginateType + '\'' +
                ", generateServiceFor='" + generateServiceFor + '\'' +
                ", exceptServiceGeneration='" + exceptServiceGeneration + '\'' +
                ", microservice='" + microservice + '\'' +
                ", gateway='" + gateway + '\'' +
                ", sourceFolder='" + sourceFolder + '\'' +
                ", targetFile='" + targetFile + '\'' +
                ", targetResourceFolder='" + targetResourceFolder + '\'' +
                '}';
    }
}
