package com.sunrise.jdl.generator;

import com.sunrise.jdl.generator.actions.Action;
import com.sunrise.jdl.generator.entities.Field;
import com.sunrise.jdl.generator.entities.LinkedField;
import com.sunrise.jdl.generator.ui.ProjectionInfo;

import java.util.List;

public class FixMethods {

    public static void fixListProjections(ProjectionInfo projectionInfo) {
        if (projectionInfo.getCode() == null) return;
        if (projectionInfo.getCode().equals("newListProjection")) {
            Action edit = new Action();
            edit.setCode("update");
            edit.setDisplayType("tableMenu");
            edit.setGroup("edit");
            edit.setStyle("editBtn");
            projectionInfo.getActions().set(1,edit);
            projectionInfo.getActions().get(2).setCode("delete");
        }
        if (projectionInfo.getCode().equals("archiveListProjection")) {
            Action fromArchive = new Action();
            fromArchive.setCode("fromArchive");
            fromArchive.setDisplayType("tableMenu");
            fromArchive.setGroup("connect");
            fromArchive.setStyle("fromArchiveBtn");
            projectionInfo.getActions().set(3,fromArchive);
        }
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
                    fields.set(fields.indexOf(field), new LinkedField(field, "lookupViewListProjection", "lookupSourceListProjection"));
                });
    }

}
