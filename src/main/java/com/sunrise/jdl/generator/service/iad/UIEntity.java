package com.sunrise.jdl.generator.service.iad;

import java.util.List;

public class UIEntity {

    String name;

    String label;

    List<UIField> fields;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<UIField> getFields() {
        return fields;
    }

    public void setFields(List<UIField> fields) {
        this.fields = fields;
    }
}
