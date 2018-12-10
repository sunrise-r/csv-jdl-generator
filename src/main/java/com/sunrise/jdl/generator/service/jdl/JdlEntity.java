package com.sunrise.jdl.generator.service.jdl;

import com.sunrise.jdl.generator.entities.JdlField;

import java.util.List;

/**
 * entity <entity name> {
 * <field name> <type> [<validation>*]
 * }
 * <p>
 * <entity name> is the name of the entity,
 * <field name> the name of one field of the entity,
 * <type> the JHipster supported type of the field,
 * and as an option <validation> the validations for the field.
 */
public class JdlEntity {

    /**
     * Name of entity
     */
    private String entityName;

    /**
     * Fields of entity
     */
    private List<JdlField> fields;

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public List<JdlField> getFields() {
        return fields;
    }

    public void setFields(List<JdlField> fields) {
        this.fields = fields;
    }
}
