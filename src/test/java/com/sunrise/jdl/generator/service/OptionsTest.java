package com.sunrise.jdl.generator.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 * Тестируем вывод различных опций в jdl файл
 * Created by igorch on 12.06.18.
 */
public class OptionsTest {

    @Test
    public void paginationOptionsTest(){
        Settings.PaginationType toUse = Settings.PaginationType.PAGINATION;
        String result = getResultOutput(toUse);
        Assert.assertTrue(result.contains("paginate * with pagination"));

        toUse = Settings.PaginationType.INFINITE_SCROLL;
        result = getResultOutput(toUse);
        Assert.assertTrue(result.contains("paginate * with infinite-scroll"));

        toUse = Settings.PaginationType.PAGER;
        result = getResultOutput(toUse);
        Assert.assertTrue(result.contains("paginate * with pager"));
    }

    @Test
    public void mapstructOptionsTest(){
        Settings settings = new Settings();
        settings.setUseMapStruct(true);
        String result = getResultString(settings);
        Assert.assertTrue(result.contains("dto * with mapstruct"));
    }

    @Test
    public void generateServiciesTest(){
        Settings settings = new Settings();
        settings.setGenerateServiciesFor("all");
        String result = getResultString(settings);
        Assert.assertTrue(result.contains("service all with serviceClass"));

        settings.setExceptServiceGenerationFor("me");
        result = getResultString(settings);
        Assert.assertTrue(result.contains("service all with serviceClass except me"));
    }

    @Test
    public void microserviceTest(){
        Settings settings = new Settings();
        settings.setMicroserviceName("me");
        Assert.assertTrue(getResultString(settings).contains("microservice * with me"));
    }

    private String getResultOutput(Settings.PaginationType toUse){
        Settings settings = new Settings();
        settings.setPaginationType(toUse);
        return getResultString(settings);
    }

    private String getResultString(Settings settings) {
        EntitiesService entitiesService = new EntitiesService(settings);
        StringWriter writer = new StringWriter();
        try {
            entitiesService.writeEntities(new ArrayList<>(0), writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return writer.toString();
    }


}
