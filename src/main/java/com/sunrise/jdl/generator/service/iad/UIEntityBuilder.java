package com.sunrise.jdl.generator.service.iad;

import com.sunrise.jdl.generator.entities.Entity;
import com.sunrise.jdl.generator.entities.RawData;
import com.sunrise.jdl.generator.service.jdl.CsvJdlUtils;
import com.sunrise.jdl.generator.service.jdl.RawCsvDataReader;
import com.sunrise.jdl.generator.service.jdl.RawDataAnalyticsService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UIEntityBuilder {
    private final CsvJdlUtils csvJdlUtils = new CsvJdlUtils();

    public List<UIEntity> createEntities(String entitiesFile) throws IOException {
        List<UIEntity> entities = new ArrayList<>();
        List<RawData> rawDataList = new RawCsvDataReader().getRawData(new FileInputStream(entitiesFile));
        Map<String, List<RawData>> rawDataMap = new RawDataAnalyticsService(new CsvJdlUtils()).groupByEntities(rawDataList);
        for (String entityName : rawDataMap.keySet()) {
            UIEntity entity = new UIEntity();
            entity.setName(entityName);
            List<RawData> rawFieldData = rawDataMap.get(entityName);
            entity.setLabel(rawFieldData.get(0).getEntityLabel());
            List<UIField> fields = new ArrayList<>();
            for (RawData rawData : rawFieldData) {
                UIField field = new UIField();
                field.setName(rawData.getFieldName());
                field.setLabel(rawData.getFieldLabel());
                String fieldType = rawData.getFieldType();
                if (csvJdlUtils.isList(fieldType)) {
                    field.setType(csvJdlUtils.getListType(fieldType));
                } else {
                    field.setType(csvJdlUtils.isJdlField(rawData) ? csvJdlUtils.getFieldType(fieldType) : fieldType);
                }
                field.setLength(rawData.getFieldLength());
                field.setRequired(rawData.getFieldRequired() != null && !rawData.getFieldRequired().isEmpty());
                fields.add(field);
            }
            entity.setFields(fields);
            entities.add(entity);
        }
        return entities;
    }
}
