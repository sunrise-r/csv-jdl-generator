package com.sunrise.jdl.generator.service;

import java.util.ArrayList;
import java.util.List;

/**
 * Опции генерации jdl файла для EntitiesService.
 * Используется для того, что бы инкапсулировать возможные настройки генерации описания данных.
 * <p>
 * Created by igorch on 12.06.18.
 */
public class Settings {

    /**
     * Список названий сущностей которые генератор будет игнорировать
     */
    private final List<String> entitiesToIngore = new ArrayList<>();

    /**
     * Список названий полей которые генератор проигнорирует.
     */
    private final List<String> fieldsToIngore = new ArrayList<>();

    /**
     * Нужно ли генерировать DTO объекты с ипользованием MapStruct
     */
    private boolean useMapStruct = false;

    /**
     * Список сущностей для которых необходимо генерировать сервисы
     */
    private String generateServiciesFor;

    /**
     * Список сущностей для которых ненужно генерировать сервисы
     */
    private String exceptServiceGenerationFor;

    /**
     * Название микросервиса для которого будут генерироваться сущности
     */
    private String microserviceName;

    private PaginationType paginationType = PaginationType.PAGINATION;

    public List<String> getEntitiesToIngore() {
        return entitiesToIngore;
    }

    public List<String> getFieldsToIngore() {
        return fieldsToIngore;
    }

    public PaginationType getPaginationType() {
        return paginationType;
    }

    public void setPaginationType(PaginationType paginationType) {
        this.paginationType = paginationType;
    }

    public boolean isUseMapStruct() {
        return useMapStruct;
    }

    public void setUseMapStruct(boolean useMapStruct) {
        this.useMapStruct = useMapStruct;
    }

    public String getGenerateServiciesFor() {
        return generateServiciesFor;
    }

    public void setGenerateServiciesFor(String generateServiciesFor) {
        this.generateServiciesFor = generateServiciesFor;
    }

    public String getExceptServiceGenerationFor() {
        return exceptServiceGenerationFor;
    }

    public void setExceptServiceGenerationFor(String exceptServiceGenerationFor) {
        this.exceptServiceGenerationFor = exceptServiceGenerationFor;
    }

    public String getMicroserviceName() {
        return microserviceName;
    }

    public void setMicroserviceName(String microserviceName) {
        this.microserviceName = microserviceName;
    }

}
