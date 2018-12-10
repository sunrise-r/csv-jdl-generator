package com.sunrise.jdl.generator.service.jdl;

import com.google.common.collect.Lists;
import com.sunrise.jdl.generator.entities.RawData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Raw CVS data reader. Reads value from cvs file.
 */
public class RawCsvDataReader {

    private static final int CLASS_NAME = 1;
    private static final int ENTITY_LABEL = 2;
    private static final int ENTITY_PLURAL_LABEL = 3;
    private static final int FIELD_NAME = 4;
    private static final int FIELD_LABEL = 5;
    private static final int FIELD_DESCRIPTION = 6;
    private static final int FIELD_TYPE = 7;
    private static final int FIELD_LENGTH = 8;
    private static final int FIELD_INPUTMASK=9;
    private static final int FIELD_REQUIRED = 10;
    private static final int FIELD_COMMENT = 11;
    private static final int FIELD_VALIDATIOM = 12;
    private static final int FIELD_INPUT_TYPE = 13;
    
    public List<RawData> getRawData(InputStream stream) throws IOException {
        List<RawData> rawDatas = Lists.newArrayList();
        for (CSVRecord record : CSVFormat.EXCEL.parse(new InputStreamReader(stream))) {
            RawData rawData = new RawData()
                    .entityName(record.get(CLASS_NAME).trim())
                    .entityLabel(record.get(ENTITY_LABEL).trim())
                    .entityPlularLabel(record.get(ENTITY_PLURAL_LABEL).trim())
                    .fieldName(record.get(FIELD_NAME).trim())
                    .fieldLabel(record.get(FIELD_LABEL).trim())
                    .fieldDescription(record.get(FIELD_DESCRIPTION).trim())
                    .fieldType(record.get(FIELD_TYPE).trim())
                    .fieldLength(record.get(FIELD_LENGTH).trim())
                    .fieldRequired(record.get(FIELD_REQUIRED).trim())
                    .fieldInptuType(record.get(FIELD_INPUT_TYPE).trim())
                    .fieldInputMask(record.get(FIELD_INPUTMASK).trim())
                    .fieldComment(record.get(FIELD_COMMENT).trim())
                    .fieldValidation(record.get(FIELD_VALIDATIOM).trim());


            rawDatas.add(rawData);
        }
        return rawDatas;
    }
}
