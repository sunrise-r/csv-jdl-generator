package com.sunrise.jdl.generator.service.jdl;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class JdlBuilderTest {

    private JdlBuilder jdlBuilder;
    private RawDataAnalyticsService rawDataAnalyticsService;
    private RawCsvDataReader rawCsvDataReader;
    private JdlFieldBuilder jdlFieldBuilder;
    private CsvJdlUtils cvsJdlUtils;

    @Before
    public void setUp() {
        cvsJdlUtils = new CsvJdlUtils();
        rawCsvDataReader = new RawCsvDataReader();
        jdlFieldBuilder = new JdlFieldBuilder(cvsJdlUtils);
        rawDataAnalyticsService = new RawDataAnalyticsService(cvsJdlUtils);
        jdlBuilder = new JdlBuilder(rawDataAnalyticsService, rawCsvDataReader, jdlFieldBuilder, cvsJdlUtils);
    }

    @Test
    public void generateJdl() throws IOException {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("test.csv");
        JdlData jdlData = jdlBuilder.generateJdl(stream);
        assertEquals(2, jdlData.getJdlEntities().size());
        assertEquals(1, jdlData.getJdlRelations().size());
        JdlRelation jdlRelation = jdlData.getJdlRelations().get(0);
        assertEquals(JdlRelation.RelationType.MANY_TO_ONE, jdlRelation.getRelationType());
        assertEquals("Author", jdlRelation.getTarget().getEntity());
        assertEquals("books", jdlRelation.getTarget().getField());
        assertEquals("Book", jdlRelation.getSource().getEntity());
        assertEquals("author", jdlRelation.getSource().getField());
    }
}