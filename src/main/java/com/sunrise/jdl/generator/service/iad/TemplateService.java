package com.sunrise.jdl.generator.service.iad;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunrise.jdl.generator.entities.TemplateKey;
import com.sunrise.jdl.generator.ui.TemplateProjection;
import org.atteo.evo.inflector.English;

import java.io.File;
import java.io.IOException;
import java.time.format.FormatStyle;
import java.util.*;
import com.google.common.base.CaseFormat;

public class TemplateService {

    private static TemplateService instance;

    private TemplateService() {
    }

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
                        projection.put(key.getKey(), fillTemplateWithData((String) key.getValue(), data));
                    else
                        System.out.println(String.format("WARNING: Ошибка в типе ключа \"%s\" для шаблона проекции \"%s\".", key.getKey(), templateProjection.getCode()));
                    break;

                case "Link":
                    Object value = data.get(key.getValue());
                    projection.put(key.getKey(), value);
                    break;
                case "Custom":
                    projection.put(key.getKey(), key.getValue());
                    break;
            }
        }
        return projection;
    }


    /**
     * Если в value есть ${EXAMPLE}, то метод берёт из data по ключу "EXAMPLE" значение и
     * подставляет в строку. Также можно указывать форматирование строки в круглых скобках
     * ${EXAMPLE(plural,camelCase)}.
     * Ключевое слово plural сделает текст во множественном числе.
     * Ключевые слова upperCamelCase, snakeCase, UpperSnakeCase и подобные им,
     * будут применять форматирование к данным, согласно этому формату.
     *
     * Примечания:
     * 1) Все пробелы внутри ${} будут проигнорированы.
     * 2) Нельзя использовать эти символы не для служебных целей    ,&{}()
     * 3) Все данные, к которым может быть применено форматирование, должны быть в UpperCamelCase
     *
     * @param value Строка, которую нужно заполнить данными там, где написано ${}
     * @param data  Данные, которыми нужно заполнить строку в формате UpperCamelCase
     * @return Строка, в которой все ${} заменены пришедшими данными
     */
    public String fillTemplateWithData(String value, Map<String, Object> data) {
        while (true) {
            int start = value.indexOf("${");
            if (start == -1) break;
            int finish = value.indexOf("}");
            if (start > finish) {
                System.out.println("Warning: Ошибка в строке " + value);
                return null;
            }
            String dataQuery = value.substring(start + 2, finish).replaceAll(" ", "");

            //Форматирование внутри скобок ()
            Set<String> formatters = new HashSet<>();
            while (dataQuery.contains(")")) {
                int formatStarts = dataQuery.indexOf('(') + 1;
                int formatEnds = dataQuery.contains(",") ? dataQuery.indexOf(',') : dataQuery.indexOf(')');
                if (formatStarts > formatEnds) {
                    System.out.println("Warning: Ошибка в форматировании строки " + value);
                    return null;
                }
                formatters.add(dataQuery.substring(formatStarts, formatEnds));
                dataQuery = dataQuery.substring(0, formatStarts) + dataQuery.substring(formatEnds+1);
            }
            Object dataByQuery = data.get(dataQuery.replaceAll("\\(",""));
            if(dataByQuery instanceof String) {
                if(formatters.contains("plural"))
                    dataByQuery = English.plural((String) dataByQuery);
                if(formatters.contains("lowerHyphenCase"))
                    dataByQuery = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, (String) dataByQuery);
                if(formatters.contains("lowerCamelCase"))
                    dataByQuery = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, (String) dataByQuery);
            } else {
                if(!formatters.isEmpty()){
                    System.out.println("WARNING: Невозможно применить строковое форматирование не к строковым данным в запросе " + value );
                    return null;
                }
            }


            value = value.substring(0, start) + dataByQuery + value.substring(finish + 1);
        }
        return value;
    }

    public static TemplateService getInstance() {
        if (instance == null) instance = new TemplateService();
        return instance;
    }

}
