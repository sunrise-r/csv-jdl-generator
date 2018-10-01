package com.sunrise.jdl.generator.service;

/**
 * Перечисление содержит поддерживаемые в JDL типы полей.
 */
public enum JDLFieldsType {

    String("String"),

    Integer("Integer"),

    Long("Long"),

    Float("Float"),

    Double("Double"),

    BigDecimal("BigDecimal"),

    LocalDate("LocalDate"),

    Instant("Instant"),

    ZonedDateTime("ZonedDateTime"),

    Boolean("Boolean"),

    Enumeration("Enumeration"),

    Blob("Blob");

    /**
     * Строковое представление типа в формате JDL
     */
    private String type;

    /**
     * Конструктор
     *
     * @param type
     */
    JDLFieldsType(java.lang.String type) {
        this.type = type;
    }


    @Override
    public java.lang.String toString() {
        return type;
    }
}
