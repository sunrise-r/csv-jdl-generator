package com.sunrise.jdl.generator.service.jdl;

import com.google.common.collect.Maps;
import com.sun.istack.internal.NotNull;
import com.sunrise.jdl.generator.entities.RawData;
import com.sunrise.jdl.generator.service.JDLFieldsType;

import java.util.Map;

public class CsvJdlUtils {

    Map<String, String> jdlTypeDictionary = Maps.newHashMap();

    public CsvJdlUtils() {
        jdlTypeDictionary.put("число", JDLFieldsType.Integer.toString());
        jdlTypeDictionary.put("дата/время", JDLFieldsType.ZonedDateTime.toString());
        jdlTypeDictionary.put("дробное", JDLFieldsType.BigDecimal.toString());
        jdlTypeDictionary.put("булев", JDLFieldsType.Boolean.toString());
        jdlTypeDictionary.put("булевое", JDLFieldsType.Boolean.toString());
        jdlTypeDictionary.put("строка", JDLFieldsType.String.toString());
    }

    public boolean isJdlField(@NotNull RawData field) {
        return jdlTypeDictionary.containsKey(field.getFieldType().toLowerCase());
    }

    public String getFieldType(@NotNull String csvFieldType) {
        return jdlTypeDictionary.get(csvFieldType);
    }

    public boolean isList(@NotNull String csvType) {
        return csvType.toLowerCase().startsWith("список");
    }

    public String getListType(@NotNull String csvType) {
        return csvType.replace("Список<", "")
                .replace("список<", "")
                .replace(">", "");
    }

    public String getListWithEntityType(@NotNull String csvType) {
        return String.format("List<%s>", getListType(csvType));
    }
}
