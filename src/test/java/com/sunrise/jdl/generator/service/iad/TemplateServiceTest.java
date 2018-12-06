package com.sunrise.jdl.generator.service.iad;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TemplateServiceTest {

    TemplateService templateService = TemplateService.getInstance();

    @Test
    public void fillWithData() {
        Map<String,Object> data = new HashMap<>();
        assertEquals(templateService.fillTemplateWithData("Test string",data),"Test string");
        data.put("TEST_DATA","AAA");
        data.put("TEST_DATA2","BBB");
        assertEquals("AAA",templateService.fillTemplateWithData("${TEST_DATA}",data));
        assertEquals("BBBTest AAAstringBBB",templateService.fillTemplateWithData("${TEST_DATA2}Test ${TEST_DATA}string${TEST_DATA2}",data));
    }

    @Test
    public void fillWithFormattedData() {
        Map<String,Object> data = new HashMap<>();
        data.put("TEST_PHRASE","SimpleThing");
        assertEquals("SimpleThing",templateService.fillTemplateWithData("${TEST_PHRASE()}",data));
        assertEquals("SimpleThings",templateService.fillTemplateWithData("${TEST_PHRASE(plural)}",data));
        assertEquals("simple-things",templateService.fillTemplateWithData("${TEST_PHRASE(plural,lowerHyphenCase)}",data));
        assertEquals("simpleThings",templateService.fillTemplateWithData("${TEST_PHRASE(plural,lowerCamelCase)}",data));
        assertEquals("simpleThing",templateService.fillTemplateWithData("${TEST_PHRASE(lowerCamelCase)}",data));

        data.put("TEST_LIST", Lists.newArrayList());
        assertEquals("[]", templateService.fillTemplateWithData("${TEST_LIST}", data));
        assertNull(templateService.fillTemplateWithData("${TEST_LIST(plural)}", data));
    }


}
