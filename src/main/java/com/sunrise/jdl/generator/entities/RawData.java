package com.sunrise.jdl.generator.entities;

import com.google.common.base.Objects;

/**
 * Raw data loaded from csv file
 */
public class RawData {

    /**
     * Entity name
     */
    private String entityName;

    /**
     * Entity label
     */
    private String entityLabel;

    /**
     * Entity plural Label
     */
    private String entityPluralLabel;

    /**
     * Field Name
     */
    private String fieldName;

    /**
     * Field label
     */
    private String fieldLabel;

    /**
     * Field description
     */
    private String fieldDescription;

    /**
     * Field type
     */
    private String fieldType;

    /**
     * Field length
     */
    private String fieldLength;

    /**
     * Field mask
     */
    private String fieldMask;

    /**
     * Field required
     */
    private String fieldRequired;

    /**
     * Field comment
     */
    private String fieldComment;

    /**
     * Field validation
     */
    private String fieldValidation;

    /**
     * Field input type
     */
    private String fieldInputType;

    /**
     * Field input mask
     */
    private String fieldInputMask;

    @Override
    public String toString() {
        return "RawData{" +
                "entityName='" + entityName + '\'' +
                ", entityLabel='" + entityLabel + '\'' +
                ", entityPluralLabel='" + entityPluralLabel + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", fieldLabel='" + fieldLabel + '\'' +
                ", fieldDescription='" + fieldDescription + '\'' +
                ", fieldType='" + fieldType + '\'' +
                ", fieldLength='" + fieldLength + '\'' +
                ", fieldMask='" + fieldMask + '\'' +
                ", fieldRequired='" + fieldRequired + '\'' +
                ", fieldComment='" + fieldComment + '\'' +
                ", fieldValidation='" + fieldValidation + '\'' +
                ", fieldInputType='" + fieldInputType + '\'' +
                ", fieldInputMask='" + fieldInputMask + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RawData data = (RawData) o;
        return Objects.equal(entityName, data.entityName) &&
                Objects.equal(entityLabel, data.entityLabel) &&
                Objects.equal(entityPluralLabel, data.entityPluralLabel) &&
                Objects.equal(fieldName, data.fieldName) &&
                Objects.equal(fieldLabel, data.fieldLabel) &&
                Objects.equal(fieldDescription, data.fieldDescription) &&
                Objects.equal(fieldType, data.fieldType) &&
                Objects.equal(fieldLength, data.fieldLength) &&
                Objects.equal(fieldMask, data.fieldMask) &&
                Objects.equal(fieldRequired, data.fieldRequired) &&
                Objects.equal(fieldComment, data.fieldComment) &&
                Objects.equal(fieldValidation, data.fieldValidation) &&
                Objects.equal(fieldInputType, data.fieldInputType) &&
                Objects.equal(fieldInputMask, data.fieldInputMask);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(entityName, entityLabel, entityPluralLabel, fieldName, fieldLabel, fieldDescription, fieldType, fieldLength, fieldMask, fieldRequired, fieldComment, fieldValidation, fieldInputType, fieldInputMask);
    }

    public String getEntityName() {

        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityLabel() {
        return entityLabel;
    }

    public void setEntityLabel(String entityLabel) {
        this.entityLabel = entityLabel;
    }

    public String getEntityPluralLabel() {
        return entityPluralLabel;
    }

    public void setEntityPluralLabel(String entityPluralLabel) {
        this.entityPluralLabel = entityPluralLabel;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldLabel() {
        return fieldLabel;
    }

    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }

    public void setFieldDescription(String fieldDescription) {
        this.fieldDescription = fieldDescription;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldLength() {
        return fieldLength;
    }

    public void setFieldLength(String fieldLength) {
        this.fieldLength = fieldLength;
    }

    public String getFieldMask() {
        return fieldMask;
    }

    public void setFieldMask(String fieldMask) {
        this.fieldMask = fieldMask;
    }

    public String getFieldRequired() {
        return fieldRequired;
    }

    public void setFieldRequired(String fieldRequired) {
        this.fieldRequired = fieldRequired;
    }

    public String getFieldComment() {
        return fieldComment;
    }

    public void setFieldComment(String fieldComment) {
        this.fieldComment = fieldComment;
    }

    public String getFieldValidation() {
        return fieldValidation;
    }

    public void setFieldValidation(String fieldValidation) {
        this.fieldValidation = fieldValidation;
    }

    public String getFieldInputType() {
        return fieldInputType;
    }

    public void setFieldInputType(String fieldInputType) {
        this.fieldInputType = fieldInputType;
    }

    public String getFieldInputMask() {
        return fieldInputMask;
    }

    public void setFieldInputMask(String fieldInputMask) {
        this.fieldInputMask = fieldInputMask;
    }

    public RawData entityName(String entityName) {
        this.entityName = entityName;
        return this;
    }

    public RawData entityLabel(String entityLabel) {
        this.entityLabel = entityLabel;
        return this;
    }

    public RawData entityPlularLabel(String entityPluralLabel) {
        this.entityPluralLabel = entityPluralLabel;
        return this;
    }

    public RawData fieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public RawData fieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
        return this;
    }

    public RawData fieldType(String fieldType) {
        this.fieldType = fieldType;
        return this;
    }

    public RawData fieldLength(String fieldLength) {
        this.fieldLength = fieldLength;
        return this;
    }

    public RawData fieldRequired(String fieldRequired) {
        this.fieldRequired = fieldRequired;
        return this;
    }

    public RawData fieldInptuType(String fieldInptuType) {
        this.fieldInputType = fieldInptuType;
        return this;
    }

    public RawData fieldDescription(String fieldDescription) {
        this.fieldDescription = fieldDescription;
        return this;
    }

    public RawData fieldInputMask(String fieldInputMask) {
        this.fieldInputMask = fieldInputMask;
        return this;
    }

    public RawData fieldComment(String fieldComment) {
        this.fieldComment = fieldComment;
        return this;
    }

    public RawData fieldValidation(String fieldValidation) {
        this.fieldValidation = fieldValidation;
        return this;
    }
}
