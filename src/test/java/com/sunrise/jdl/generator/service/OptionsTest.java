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



    private String getResultOutput(Settings.PaginationType toUse) throws IOException {
        Settings settings = new Settings();
        settings.setPaginationType(toUse);
        EntitiesService entitiesService = new EntitiesService(settings);
        StringWriter writer = new StringWriter();
        entitiesService.writeEntities(new ArrayList<>(0), writer);
        return writer.toString();
    }


}
