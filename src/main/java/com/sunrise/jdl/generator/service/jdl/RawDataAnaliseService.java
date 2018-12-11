package com.sunrise.jdl.generator.service.jdl;

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
        return null;
    }

    public Stream<RawData> findRelationFields(List<RawData> rawData) {
        return rawData.stream().filter(d -> !csvJdlUtils.isJdlField(d));
    }

    public Stream<RawData> findPrimitiveTypes(List<RawData> rawData) {
        return rawData.stream().filter(csvJdlUtils::isJdlField);
    }


}
