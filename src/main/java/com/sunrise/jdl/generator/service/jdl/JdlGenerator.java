package com.sunrise.jdl.generator.service.jdl;

import com.sunrise.jdl.generator.config.JdlConfig;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JdlGenerator {

    private final JdlBuilder jdlBuilder;
    private final JdlWriter jdlWriter;

    public JdlGenerator() throws IOException {
        jdlWriter = new JdlWriter();
        CsvJdlUtils cvsJdlUtils = new CsvJdlUtils();
        RawCsvDataReader rawCsvDataReader = new RawCsvDataReader();
        JdlFieldBuilder jdlFieldBuilder = new JdlFieldBuilder(cvsJdlUtils);
        RawDataAnalyticsService rawDataAnalyticsService = new RawDataAnalyticsService(cvsJdlUtils);
        jdlBuilder = new JdlBuilder(rawDataAnalyticsService, rawCsvDataReader, jdlFieldBuilder, cvsJdlUtils);
    }


    public void generateJdl(JdlConfig jdlConfig) throws IOException, TemplateException {
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
        JdlData data = new JdlData();
        data.setJdlRelations(allRelations);
        data.setJdlEntities(allEntities);
        jdlWriter.renderJdl(data, new FileOutputStream(targetFile));
    }
}
