package com.sunrise.jdl.generator.service.iad;

import com.sunrise.jdl.generator.ui.RegistryItem;
import org.junit.Test;

import static org.junit.Assert.*;

public class UIGeneratorServiceTest {

    UIGeneratorService uiGeneratorService = new UIGeneratorService();

    @Test
    public void createPresenationFor() {
        String registryName = "registry";
        String entityName="entity";
        RegistryItem item = uiGeneratorService.createPresenationFor(entityName,registryName);
        assertEquals(entityName+"Presentation",item.getCode());
        assertEquals(entityName+"Presentation",item.getName());
        assertEquals(registryName,item.getParentCode());
    }
}