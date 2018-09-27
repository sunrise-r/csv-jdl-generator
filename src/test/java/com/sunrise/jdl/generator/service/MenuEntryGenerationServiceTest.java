package com.sunrise.jdl.generator.service;

import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class MenuEntryGenerationServiceTest {

    private CSVEntityTypeReader csvEntityTypeReader = new CSVEntityTypeReader();
    private EntitiesService entitiesService = new EntitiesService(new Settings());

    @Test
    public void generateScriptTest() {
        MenuEntryGenerationService a = new MenuEntryGenerationService();
        InputStream stream = this.getClass().getResourceAsStream("/queryBuilder.csv");

        List<String> l = a.generateQueries(stream);

        l.forEach(x -> System.out.println("INSERT INTO menu_item " + x));


    }

}