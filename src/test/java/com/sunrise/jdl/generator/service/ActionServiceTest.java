package com.sunrise.jdl.generator.service;

import com.sunrise.jdl.generator.actions.Action;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;

public class ActionServiceTest {

    @Test
    public void testReadDataFromCSV() throws FileNotFoundException {
        /**
         * Файл с тестовыми данными
         */
        File source = new File("/home/gena/Work/EDOPartner/actionCsv/action.csv");

        Collection<Action> actions = new ActionService().readDataFromCSV(new FileInputStream(source));
        Assert.assertEquals(actions.size(), 12);
        Assert.assertEquals(actions.iterator().next().getStyle(), "operationBtn");
    }
}
