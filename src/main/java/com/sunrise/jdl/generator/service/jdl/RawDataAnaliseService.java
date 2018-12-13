package com.sunrise.jdl.generator.service.jdl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sunrise.jdl.generator.entities.RawData;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Analize raw data and create models for generating entities
 */
public class RawDataAnaliseService {

    private final CsvJdlUtils csvJdlUtils;

    public RawDataAnaliseService(CsvJdlUtils csvJdlUtils) {
        this.csvJdlUtils = csvJdlUtils;
    }

    /**
     * Group array of data from CSV file by entity names
     *
     * @param rawDataList raw data from csv file
     * @return
     */
    public Map<String, List<RawData>> groupByEntities(List<RawData> rawDataList) {
        Map<String, List<RawData>> result = Maps.newHashMap();
        String currentEntity = null;
        for (RawData rawData : rawDataList) {
            if (rawData.getEntityName() != null && rawData.getEntityName().length() > 0) {
                currentEntity = rawData.getEntityName();
                if (currentEntity != null) {
                    result.put(currentEntity, Lists.newArrayList());
                }
            }
            if (result.containsKey(currentEntity)) {
                result.get(currentEntity).add(rawData);
            }
        }
        return result;
    }

    public Stream<RawData> findRelationFields(List<RawData> rawData) {
        return rawData.stream().filter(d -> !csvJdlUtils.isJdlField(d));
    }

    public Stream<RawData> findPrimitiveTypes(List<RawData> rawData) {
        return rawData.stream().filter(csvJdlUtils::isJdlField);
    }


}
