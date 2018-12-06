package com.sunrise.jdl.generator.service;

import com.sunrise.jdl.generator.actions.Action;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;

public class ActionServiceTest {

    @Test
    public void testReadDataFromCSV() throws FileNotFoundException {
        /**
         * Файл с тестовыми данными
         */
        InputStream stream= this.getClass().getResourceAsStream("/action.csv");
//        File source = new File("\\action.csv");

        Collection<Action> actions = new ActionService().readDataFromCSV(stream);
        Assert.assertEquals(actions.size(), 12);
        Assert.assertEquals(actions.iterator().next().getStyle(), "newBtn");
    }
}
