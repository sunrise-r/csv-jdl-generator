package com.sunrise.jdl.generator.ui;

import com.sunrise.jdl.generator.entities.TemplateKey;

import java.util.ArrayList;
import java.util.List;

public class TemplateProjection {

    private String code;

    private List<TemplateKey> keys = new ArrayList<>();

    public TemplateProjection(String code, List<TemplateKey> keys) {
        this.code = code;
        this.keys = keys;
    }

    public TemplateProjection() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<TemplateKey> getKeys() {
        return keys;
    }

    public void setKeys(List<TemplateKey> keys) {
        this.keys = keys;
    }
}

