package com.sunrise.jdl.generator.service;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 * Тестируем вывод различных опций в jdl файл
 * Created by igorch on 12.06.18.
 */
public class OptionsTest {

    @Test
    public void paginationOptionsTest() throws IOException {
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
    public void mapstructOptionsTest() throws IOException {
        Settings settings = new Settings();
        settings.setUseMapStruct(true);
        String result = getResultString(settings);
        Assert.assertTrue(result.contains("dto * with mapstruct"));
    }

    @Test
    public void generateServiciesTest() throws IOException {
        Settings settings = new Settings();
        settings.setGenerateServiciesFor("all");
        String result = getResultString(settings);
        Assert.assertTrue(result.contains("service all with serviceImpl"));

        settings.setExceptServiceGenerationFor("me");
        result = getResultString(settings);
        Assert.assertTrue(result.contains("service all with serviceImpl except me"));

    }

    private String getResultOutput(Settings.PaginationType toUse) throws IOException {
        Settings settings = new Settings();
        settings.setPaginationType(toUse);
        return getResultString(settings);
    }

    private String getResultString(Settings settings) throws IOException {
        EntitiesService entitiesService = new EntitiesService(settings);
        StringWriter writer = new StringWriter();
        entitiesService.writeEntities(new ArrayList<>(0), writer);
        return writer.toString();
    }


}
