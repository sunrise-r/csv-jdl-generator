package com.sunrise.jdl.generator.service;

import com.sunrise.jdl.generator.entities.Entity;
import com.sunrise.jdl.generator.entities.Field;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class EntitiesServiceTest {

    private EntitiesService entitiesService = new EntitiesService(new Settings());

    /**
     * TODO я думаю нужен тест на небольшом колличенстве данных, 1-2 сущности, что бы проверить, что все поля устонавливаются правильно.
     */
    @Test
    public void testReadAll() {
        ArrayList<InputStream> streams = new ArrayList<>(2);
        streams.add(this.getClass().getResourceAsStream("/dictionary.csv"));
        streams.add(this.getClass().getResourceAsStream("/data.csv"));
        List<Entity> result = entitiesService.readAll(streams);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.size() > 0);
    }

    /**
     * На примере чтения сущности ContactDataCorrection и DriverLicense
     */
    @Test
    public void validationOfFieldsTypeReading() {
        ArrayList<InputStream> streams = new ArrayList<>(1);
        streams.add(this.getClass().getResourceAsStream("/twoEntities.csv"));
        List<Entity> result = entitiesService.readAll(streams);
        Entity contacts = result.get(0);
        List<Field> contactsFields = contacts.getFields();

        Assert.assertNotNull(result);
        Assert.assertEquals("Address", contactsFields.get(0).getFieldType());
        Assert.assertEquals("Address", contactsFields.get(1).getFieldType());
        Assert.assertEquals("Address", contactsFields.get(2).getFieldType());
        Assert.assertEquals("PhoneNumber", contactsFields.get(3).getFieldType());
        Assert.assertEquals("EmailAddress", contactsFields.get(4).getFieldType());
        Assert.assertEquals("String", contactsFields.get(5).getFieldType());
        Assert.assertEquals("SocialNetwork", contactsFields.get(6).getFieldType());
        Assert.assertEquals("Список<Document>", contactsFields.get(7).getFieldType());

        Entity license = result.get(1);
        List<Field> licenseFields = license.getFields();

        Assert.assertEquals("Instant", licenseFields.get(0).getFieldType());
        Assert.assertEquals("String", licenseFields.get(1).getFieldType());
        Assert.assertEquals("String", licenseFields.get(2).getFieldType());
        Assert.assertEquals("String", licenseFields.get(3).getFieldType());
        Assert.assertEquals("String", licenseFields.get(4).getFieldType());
        Assert.assertEquals("Instant", licenseFields.get(5).getFieldType());
        Assert.assertEquals("Instant", licenseFields.get(6).getFieldType());
        Assert.assertEquals("String", licenseFields.get(7).getFieldType());
        Assert.assertEquals("Employee", licenseFields.get(8).getFieldType());
        Assert.assertEquals("Group", licenseFields.get(9).getFieldType());
        Assert.assertEquals("Список<Document>", licenseFields.get(10).getFieldType());
    }

    @Test
    public void testCorrectFieldsType() {
        ArrayList<InputStream> streams = new ArrayList<>(1);
        streams.add(this.getClass().getResourceAsStream("/twoEntities.csv"));
        List<Entity> result = entitiesService.readAll(streams);
        Entity contacts = result.get(0);
        List<Field> contactsFields = contacts.getFields();
        
        Assert.assertNotNull(result);
        Assert.assertEquals("Address", contactsFields.get(0).getFieldType());
        Assert.assertEquals("Address", contactsFields.get(1).getFieldType());
        Assert.assertEquals("Address", contactsFields.get(2).getFieldType());
        Assert.assertEquals("PhoneNumber", contactsFields.get(3).getFieldType());
        Assert.assertEquals("EmailAddress", contactsFields.get(4).getFieldType());
        Assert.assertEquals("String", contactsFields.get(5).getFieldType());
        Assert.assertEquals("SocialNetwork", contactsFields.get(6).getFieldType());
        Assert.assertEquals("Список<Document>", contactsFields.get(7).getFieldType());

        Entity license = result.get(1);
        List<Field> licenseFields = license.getFields();

        Assert.assertEquals("Instant", licenseFields.get(0).getFieldType());
        Assert.assertEquals("String", licenseFields.get(1).getFieldType());
        Assert.assertEquals("String", licenseFields.get(2).getFieldType());
        Assert.assertEquals("String", licenseFields.get(3).getFieldType());
        Assert.assertEquals("String", licenseFields.get(4).getFieldType());
        Assert.assertEquals("Instant", licenseFields.get(5).getFieldType());
        Assert.assertEquals("Instant", licenseFields.get(6).getFieldType());
        Assert.assertEquals("String", licenseFields.get(7).getFieldType());
        Assert.assertEquals("Employee", licenseFields.get(8).getFieldType());
        Assert.assertEquals("Group", licenseFields.get(9).getFieldType());
        Assert.assertEquals("Список<Document>", licenseFields.get(10).getFieldType());

    }

    /**
     * Проверка корректности записи сущности, ее полей и структуры в файл
     */
    @Test
    public void testWriteToFile() {
        ArrayList<InputStream> streams = new ArrayList<>(2);
        streams.add(this.getClass().getResourceAsStream("/twoEntities.csv"));
        List<Entity> result = entitiesService.readAll(streams);
        int numberOfStructure = entitiesService.createStructures(result);
        Assert.assertEquals(10, numberOfStructure);
    }
}
