package com.sunrise.jdl.generator.service.iad;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunrise.jdl.generator.entities.TemplateKey;
import com.sunrise.jdl.generator.ui.TemplateProjection;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateService {

    public TemplateProjection loadTemplateProjections(File template) throws IOException {
        return new ObjectMapper().readValue(template, TemplateProjection.class);
    }

    public List<TemplateProjection> loadTemplateProjections(File[] templates) throws IOException {
        List<TemplateProjection> templateProjections = new ArrayList<>();
        for (File f : templates) {
            templateProjections.add(loadTemplateProjections(f));
        }
        return templateProjections;
    }

    public Map<String, Object> toProjections(TemplateProjection templateProjection, Map<String, Object> data) {
        Map<String, Object> projection = new HashMap<>();
        projection.put("code", templateProjection.getCode());
        for (TemplateKey key : templateProjection.getKeys()) {
            switch (key.getType()) {
                case "String":
                    if (key.getValue() instanceof String)
                        projection.put(key.getKey(), fillWithData((String) key.getValue(), data));
                    else
                        System.out.println(String.format("WARNING: Ошибка в типе ключа \"%s\" для шаблона проекции \"%s\".", key.getKey(), templateProjection.getCode()));
                    break;

                case "Link":
                    Object value = data.get(key.getValue());
                    projection.put(key.getKey(),value);
                    break;
                case "Custom":
                    projection.put(key.getKey(),key.getValue());
                    break;
            }
        }

        return projection;
    }


    private String fillWithData(String value, Map<String, Object> data) {
        while (true) {
            int start = value.indexOf("${");
            if(start == -1) break;
            int finish = value.indexOf("}");
            if(start > finish) {
                System.out.println("Warning: Ошибка в строке " + value);
                break;
            }
            value = value.substring(0,start) + data.get(value.substring(start+2,finish)) + value.substring(finish+1);
        }
        return value;
    }

    public static TemplateService getInstance() {
        if (instance == null) instance = new TemplateService();
        return instance;
    }

    private TemplateService() {
    }

    private static TemplateService instance;

}
