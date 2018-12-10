package com.sunrise.jdl.generator.service.jdl;

import com.google.common.collect.Lists;
import com.sunrise.jdl.generator.entities.JdlField;
import com.sunrise.jdl.generator.entities.RawData;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class JdlBuilder {

    private final RawDataAnalizeService rawDataAnalizeService;

    private final RawCsvDataReader rawCsvDataReader;

    private final JdlFieldBuilder jdlFieldBuilder;

    public JdlBuilder(RawDataAnalizeService rawDataAnalizeService, RawCsvDataReader rawCsvDataReader, JdlFieldBuilder jdlFieldBuilder) {
        this.rawDataAnalizeService = rawDataAnalizeService;
        this.rawCsvDataReader = rawCsvDataReader;
        this.jdlFieldBuilder = jdlFieldBuilder;
    }

    public void generateJdl(InputStream inputStream) throws IOException {
        List<JdlEntity> jdlEntities = Lists.newArrayList();
        List<RawData> rawData = rawCsvDataReader.getRawData(inputStream);
        Map<String, List<RawData>> stringListMap = rawDataAnalizeService.groupByEntities(rawData);
        for (String entityName : stringListMap.keySet()) {
            JdlEntity jdlEntity = new JdlEntity();
            jdlEntity.setEntityName(entityName);
            jdlEntity.setFields(generateJdlFields(stringListMap.get(jdlEntity)));
        }
    }

    private List<JdlField> generateJdlFields(List<RawData> rawData) {
        List<JdlField> fields = Lists.newArrayList();
        for (RawData r : rawData) {
            if (!jdlFieldBuilder.isJdlField(r)) {
                continue;
            }
            JdlField jdlField = new JdlField();
            jdlField.setFieldLength(jdlFieldBuilder.parseFieldLength(r));
            jdlField.setFieldName(jdlFieldBuilder.parseFieldName(r));
            jdlField.setFieldType(jdlFieldBuilder.parseFieldType(r));
            jdlField.setValidation(jdlFieldBuilder.parseFieldValidation(r));
            fields.add(jdlField);
        }
        return fields;
    }

}
