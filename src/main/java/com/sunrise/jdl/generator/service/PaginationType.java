package com.sunrise.jdl.generator.service;

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
