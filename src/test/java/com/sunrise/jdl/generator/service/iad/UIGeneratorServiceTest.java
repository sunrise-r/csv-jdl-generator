package com.sunrise.jdl.generator.service.iad;

import com.sunrise.jdl.generator.ui.RegistryItem;
import com.sunrise.jdl.generator.ui.UIGenerateParameters;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UIGeneratorServiceTest {

    private UIGeneratorService uiGeneratorService = new UIGeneratorService();

    @Test
    public void createPresentationFor() {
        String registryName = "registry";
        String entityName = "entity";
        UIGenerateParameters params = new UIGenerateParameters();
        params.setPluralTranslations(false);
        params.setTranslationPath("");
        params.setTitlePath("partnerGatewayApp.partnerDocuments.<ENTITY_NAME>.home.title");
        RegistryItem item = uiGeneratorService.createPresentationFor(entityName, registryName, params);
        assertEquals(entityName + "Presentation", item.getCode());
        assertEquals("partnerGatewayApp.partnerDocuments.entity.home.title", item.getLabel());
        assertEquals(registryName, item.getParentCode());
    }
}