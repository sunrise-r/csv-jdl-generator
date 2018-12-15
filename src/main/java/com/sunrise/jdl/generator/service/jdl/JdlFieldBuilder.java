package com.sunrise.jdl.generator.service.jdl;

import com.sun.istack.internal.NotNull;
import com.sunrise.jdl.generator.entities.RawData;

public class JdlFieldBuilder {

    private final CsvJdlUtils csvJdlUtils;

    public JdlFieldBuilder(CsvJdlUtils csvJdlUtils) {
        this.csvJdlUtils = csvJdlUtils;
    }

    public String parseFieldLength(@NotNull RawData field) {
        return field.getFieldLength();
    }

    public String parseFieldName(@NotNull RawData field) {
        return field.getFieldName();
    }

    public String parseFieldType(@NotNull RawData field) {
        if (csvJdlUtils.isJdlField(field)) {
            return csvJdlUtils.getFieldType(field.getFieldType().toLowerCase());
        } else {
            if (csvJdlUtils.isList(field.getFieldType())) {
                return csvJdlUtils.getListType(field.getFieldType());
            }
            return field.getFieldType();
        }
    }

    public String parseFieldValidation(@NotNull RawData field) {
        String validation = null;
        if (field.getFieldRequired() != null && field.getFieldRequired().length() > 0) {
            validation = "required";
        }
        return validation;
    }
}
