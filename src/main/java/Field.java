public class Field {
    private String fieldType;
    private String fieldName;
    private String fieldLength;

    public Field(String fieldType, String fieldName, String fieldLength) {
        this.fieldType = fieldType;
        this.fieldName = fieldName;
        this.fieldLength = fieldLength;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
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
}
