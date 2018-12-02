package com.sunrise.jdl.generator.config;

import java.util.List;

public class JdlConfig {

    /**
     * List entities that will be ignored while generating
     */
    private List<String> ignoreEntities;

    /**
     * Generate use map struct option in jdl
     */
    private boolean mapStruct;

    /**
     * Paginate type for JDL File. Possible values are: pager, pagination, infinite-scroll"
     */
    private String paginateType;

    /**
     * For what entities service generation needed. List of entities or 'all'
     */
    private String generateServiceFor;

    /**
     * For what entities service generation is not needed. List of entities
     */
    private String exceptServiceGeneration;

    /**
     * name of microservice that will hold the entities. Can be null for monolith application
     */
    private String microservice;

    /**
     * Name of gateway application
     */
    private String gateway;

    /**
     * source folder with csv files
     */
    private String sourceFolder;

    /**
     * Target jhFile
     */
    private String targetFile;

    /**
     * Path where genereted resources will be saved
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
}
