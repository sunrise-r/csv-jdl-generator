package com.sunrise.jdl.generator.service.jdl;

import com.google.common.collect.Maps;
import com.sunrise.jdl.generator.entities.RawData;
import com.sunrise.jdl.generator.service.JDLFieldsType;

import java.util.Map;

public class JdlFieldBuilder {

    Map<String, String> dictionary = Maps.newHashMap();

    public JdlFieldBuilder() {
        dictionary.put("число", JDLFieldsType.Integer.toString());
        dictionary.put("дата/время", JDLFieldsType.ZonedDateTime.toString());
        dictionary.put("дробное", JDLFieldsType.BigDecimal.toString());
        dictionary.put("булев", JDLFieldsType.Boolean.toString());
        dictionary.put("булевое", JDLFieldsType.Boolean.toString());
        dictionary.put("строка", JDLFieldsType.String.toString());
    }

    public boolean isJdlField(RawData field) {
        return dictionary.containsKey(field.getFieldType().toLowerCase());
    }

    public String parseFieldLength(RawData field) {
        return field.getFieldLength();
    }

    public String parseFieldName(RawData field) {
        return field.getFieldName();
    }

    public String parseFieldType(RawData field) {
        return dictionary.get(field.getFieldType().toLowerCase());
    }

    public String parseFieldValidation(RawData field) {
        String validation = null;
        if (field.getFieldRequired() != null && field.getFieldRequired().length() > 0) {
            validation = "required";
        }
        return validation;
    }


}
