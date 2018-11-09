package com.sunrise.jdl.generator;

import com.sunrise.jdl.generator.entities.Field;
import com.sunrise.jdl.generator.entities.LinkedField;
import com.sunrise.jdl.generator.ui.ProjectionInfo;

import java.util.List;

public class FixMethods {

    public static void fixListProjections(ProjectionInfo projectionInfo) {
        if (projectionInfo.getCode() == null) return;
        projectionInfo.setCode(projectionInfo.getCode().replaceAll("ListProjection", ""));
    }

    public static void fixFormProjectionFields(List<Field> fields) {
        if (fields == null) return;
        fields.forEach(field -> {
            String type = field.getFieldType();
            if (type.equalsIgnoreCase("list") || type.equalsIgnoreCase("entity"))
                if (field.getFieldName().equalsIgnoreCase("additionalDocuments"))
                    fields.set(fields.indexOf(field), new LinkedField(field, "additionalDocumentsLookupViewProjection", "additionalDocumentsLookupSourceProjection"));
                else
                    fields.set(fields.indexOf(field), new LinkedField(field, "lookupSourceProjection", "lookupViewProjection"));
                });
    }

}
