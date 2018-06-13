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
    private boolean userMapStruct = false;

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

    public boolean isUserMapStruct() {
        return userMapStruct;
    }

    public void setUserMapStruct(boolean userMapStruct) {
        this.userMapStruct = userMapStruct;
    }

    /**
     * Список с поддерживаемыми видами пейджинации.
     */
    public enum PaginationType {

        /**
         * Обычная пейджинация через пейджер
         */
        PAGINATION("pagination"),

        /**
         * Пейджинация через бесконечный скролл.
         */
        INFINITE_SCROLL("infinite-scroll"),

        /**
         * Из документации не понятно, что будет если выбрать эту опцию
         */
        PAGER("pager");

        private final String jdlValue;

        PaginationType(String jdlValue) {

            this.jdlValue = jdlValue;
        }

        @Override
        public String toString() {
            return jdlValue;
        }

        public static PaginationType fromString(String paginate) {
            for (PaginationType b : PaginationType.values()) {
                if (b.jdlValue.equalsIgnoreCase(paginate)) {
                    return b;
                }
            }
            throw new RuntimeException(String.format("Failed to parse %s to available jdl paginate values", paginate));
        }
    }

}
