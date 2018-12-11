package com.sunrise.jdl.generator.service.jdl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sunrise.jdl.generator.entities.JdlField;
import com.sunrise.jdl.generator.entities.RawData;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class JdlBuilder {

    private final RawDataAnaliseService rawDataAnaliseService;

    private final RawCsvDataReader rawCsvDataReader;

    private final JdlFieldBuilder jdlFieldBuilder;

    private final CsvJdlUtils csvJdlUtils;

    public JdlBuilder(RawDataAnaliseService rawDataAnaliseService, RawCsvDataReader rawCsvDataReader, JdlFieldBuilder jdlFieldBuilder, CsvJdlUtils csvJdlUtils) {
        this.rawDataAnaliseService = rawDataAnaliseService;
        this.rawCsvDataReader = rawCsvDataReader;
        this.jdlFieldBuilder = jdlFieldBuilder;
        this.csvJdlUtils = csvJdlUtils;
    }

    public void generateJdl(InputStream inputStream) throws IOException {
        List<JdlEntity> jdlEntities = Lists.newArrayList();
        List<RawData> rawData = rawCsvDataReader.getRawData(inputStream);
        Map<String, List<RawData>> stringListMap = rawDataAnaliseService.groupByEntities(rawData);
        Map<String, List<JdlRelation>> relations = Maps.newHashMap();
        for (String entityName : stringListMap.keySet()) {
            JdlEntity jdlEntity = new JdlEntity();
            jdlEntity.setEntityName(entityName);
            final List<RawData> entityFieldsData = stringListMap.get(jdlEntity);
            jdlEntity.setFields(generateJdlFields(entityFieldsData));
            jdlEntities.add(jdlEntity);
            relations.put(entityName, rawDataAnaliseService.findRelationFields(entityFieldsData).map(d -> this.createRelation(entityName, d)).collect(Collectors.toList()));
        }

        //Find all relation wich has references to each other
        List<JdlRelation> finalRelation = Lists.newArrayList();
        for (String entityName : relations.keySet()) {
            for (JdlRelation jdlRelation : relations.get(entityName)) {
                String targetEntity = jdlRelation.getTarget().getEntity();
                List<JdlRelation> targetRelations = relations.get(targetEntity);
                List<JdlRelation> intersected = targetRelations.stream()
                        .filter(r -> r.getTarget().getEntity().equals(entityName))
                        .collect(Collectors.toList());
                if (intersected.size() > 1) {
                    System.out.println("Entities contains more then one relation between two entities, currently this is not support");
                    intersected.stream().forEach(i -> {
                        System.out.println(i.toString());
                    });
                }
                Optional<JdlRelation> first = intersected.stream().findFirst();
                if (first.isPresent()) {
                    JdlRelation jdlTargetRelation = first.get();
                    if (jdlTargetRelation.getRelationType().equals(JdlRelation.RelationType.ONE_TO_MANY) && jdlRelation.getRelationType().equals(JdlRelation.RelationType.ONE_TO_MANY)) {
                        jdlRelation.setRelationType(JdlRelation.RelationType.MANY_TO_MANY);
                    } else if (jdlTargetRelation.getRelationType().equals(JdlRelation.RelationType.ONE_TO_ONE) && jdlRelation.getRelationType().equals(JdlRelation.RelationType.ONE_TO_MANY)) {
                        jdlRelation.setRelationType(JdlRelation.RelationType.MANY_TO_ONE);
                    }
                    jdlRelation.getTarget().setField(jdlTargetRelation.getSource().getField());
                }
                targetRelations.removeAll(intersected);
                finalRelation.add(jdlRelation);
            }
        }
    }

    private JdlRelation createRelation(String entityName, RawData rawData) {
        JdlRelation relation = new JdlRelation();
        JdlRelation.EntityRelationDescription source = new JdlRelation.EntityRelationDescription();
        source.setEntity(entityName);
        source.setField(jdlFieldBuilder.parseFieldName(rawData));

        JdlRelation.EntityRelationDescription target = new JdlRelation.EntityRelationDescription();
        target.setEntity(jdlFieldBuilder.parseFieldType(rawData));
        relation.setSource(source);
        relation.setTarget(target);
        if (csvJdlUtils.isList(rawData.getFieldType())) {
            relation.setRelationType(JdlRelation.RelationType.ONE_TO_MANY);
        } else {
            relation.setRelationType(JdlRelation.RelationType.ONE_TO_ONE);
        }
        return relation;
    }

    private List<JdlField> generateJdlFields(List<RawData> rawData) {
        List<JdlField> fields = Lists.newArrayList();
        rawDataAnaliseService.findPrimitiveTypes(rawData).forEach(r -> {
            JdlField jdlField = new JdlField();
            jdlField.setFieldLength(jdlFieldBuilder.parseFieldLength(r));
            jdlField.setFieldName(jdlFieldBuilder.parseFieldName(r));
            jdlField.setFieldType(jdlFieldBuilder.parseFieldType(r));
            jdlField.setValidation(jdlFieldBuilder.parseFieldValidation(r));
            fields.add(jdlField);
        });
        return fields;
    }
}
