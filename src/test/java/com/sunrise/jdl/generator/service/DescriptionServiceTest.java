package com.sunrise.jdl.generator.service;

import com.sunrise.jdl.generator.entities.Entity;
import com.sunrise.jdl.generator.entities.Field;
import junit.framework.TestCase;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by igorch on 16.06.18.
 */
public class DescriptionServiceTest extends TestCase {

    @Test
    public void testGetEntitiesDescription() throws Exception {
        final String gateway = "partnerGateway";
        final String ms = "partnerDocuments";
        DescriptionServiceSettings settings = new DescriptionServiceSettings(gateway, ms);
        DescriptionService descriptionService = new DescriptionService(settings);
        Entity en = new Entity("ClassName", generateFields(), "testLabel", "testTitle");
        Map<String, String> result = descriptionService.getEntitiesDescription(Arrays.asList(en));
        assertTrue(result.containsKey(ms + en.getClassName()+".json"));
        assertEquals(IOUtils.toString(this.getClass().getClassLoader().getResourceAsStream("target.json")),result.get(ms + en.getClassName()+".json"));
    }

    private List<Field> generateFields() {
        Field fld = new Field("testType", "testName", "10", true, false, "testFieldLabel");
        Field fld2 = new Field("testType2", "testName2", "10", true, false, "testFieldLabel2");
        return Arrays.asList(fld, fld2);
    }
}