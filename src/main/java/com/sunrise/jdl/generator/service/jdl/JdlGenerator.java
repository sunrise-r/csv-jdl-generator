package com.sunrise.jdl.generator.service.jdl;

import com.google.common.collect.Maps;
import com.sunrise.jdl.generator.config.JdlConfig;
import com.sunrise.jdl.generator.entities.RawData;
import com.sunrise.jdl.generator.service.common.GeneratorWriter;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JdlGenerator {

    private final JdlBuilder jdlBuilder;
    private final GeneratorWriter generatorWriter;

    public JdlGenerator(GeneratorWriter writer) throws IOException {
        generatorWriter = writer;
        CsvJdlUtils cvsJdlUtils = new CsvJdlUtils();
        RawCsvDataReader rawCsvDataReader = new RawCsvDataReader();
        JdlFieldBuilder jdlFieldBuilder = new JdlFieldBuilder(cvsJdlUtils);
        RawDataAnalyticsService rawDataAnalyticsService = new RawDataAnalyticsService(cvsJdlUtils);
        jdlBuilder = new JdlBuilder(rawDataAnalyticsService, rawCsvDataReader, jdlFieldBuilder, cvsJdlUtils);
    }


    public JdlData generateJdl(JdlConfig jdlConfig) throws IOException, TemplateException {
        File directory = new File(jdlConfig.getSourceFolder());
        File[] files = directory.listFiles();
        List<InputStream> resources = new ArrayList<InputStream>(files.length);
        for (File f : files) {
            if (f.getName().endsWith(".csv")) {
                try {
                    resources.add(new FileInputStream(f));
                } catch (FileNotFoundException e) {
                    System.err.println("Failed to read file .  Reason: " + e.getMessage());
                }
            }
        }

        File targetFile = new File(jdlConfig.getTargetFile());
        List<JdlData> jdls = resources.stream().map(jdlBuilder::generateJdl).collect(Collectors.toList());
        List<JdlRelation> allRelations = jdls.stream().flatMap(j -> j.getJdlRelations().stream()).collect(Collectors.toList());
        List<JdlEntity> allEntities = jdls.stream().flatMap(j -> j.getJdlEntities().stream()).collect(Collectors.toList());
        Map<String, List<RawData>> allGroupedRawData = Maps.newHashMap();
        jdls.stream().map(JdlData::getGroupedByEntityName).forEach(allGroupedRawData::putAll);

        JdlData data = new JdlData();
        data.setJdlRelations(allRelations);
        data.setJdlEntities(allEntities);
        data.setGroupedRelations(groupRelations(data.getJdlRelations()));
        data.setGroupedByEntityName(allGroupedRawData);
        generatorWriter.renderJdl(jdlConfig, data, new FileOutputStream(targetFile));
        return data;
    }

    private Map<RelationType, List<JdlRelation>> groupRelations(List<JdlRelation> jdlRelations) {
        return jdlRelations.stream().collect(Collectors.groupingBy(x -> x.getRelationType()));
    }

}
