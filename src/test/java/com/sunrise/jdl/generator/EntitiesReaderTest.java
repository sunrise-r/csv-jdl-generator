package com.sunrise.jdl.generator;

import com.sunrise.jdl.generator.entities.Entity;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class EntitiesReaderTest {

    /**
     * TODO я думаю нужен тест на небольшом колличенстве данных, 1-2 сущности, что бы проверить, что все поля устонавливаются правильно.
     */
    @Test
    public void testReadAll() {
        ArrayList<InputStream> streams = new ArrayList<>(2);
        streams.add(this.getClass().getResourceAsStream("/dictionary.csv"));
        streams.add(this.getClass().getResourceAsStream("/data.csv"));
        EntitiesReader reader = new EntitiesReader(streams);
        List<Entity> result = reader.readAll();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.size() > 0);
    }
}
