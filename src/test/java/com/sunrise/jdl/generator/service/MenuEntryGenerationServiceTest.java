package com.sunrise.jdl.generator.service;


import com.sunrise.jdl.generator.entities.Entity;
import com.sunrise.jdl.generator.entities.ModuleInfo;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class MenuEntryGenerationServiceTest {

    private CSVEntityTypeReader csvEntityTypeReader = new CSVEntityTypeReader();

    private EntitiesService entitiesService = new EntitiesService(new Settings());

    @Test
    public void generateScriptTest() {
        MenuEntryGenerationService a = new MenuEntryGenerationService();
        InputStream stream = this.getClass().getResourceAsStream("/entityTypes.csv");
        Map<ModuleInfo, List<String>> types = csvEntityTypeReader.readFullTypes(stream);

        for (Map.Entry<ModuleInfo, List<String>> pair : types.entrySet()) {
            for (String subtypeName : pair.getValue()) {
                // parentColumnName, urlColumnName, codeColumnName, labelColumnName, roleColumnName
                String.format(
                        a.getCurrentTemplate(),

                        pair.getKey().getClassName(),
                        "/partner-documents/" + pair.getKey().getClassName() + "/" + subtypeName,
                        subtypeName,
                        "ROLE_" + pair.getKey().getClassName().toUpperCase() + "_" + subtypeName,
                        pair.getKey().getModuleName().toUpperCase()
                );
            }
        }
    }

}