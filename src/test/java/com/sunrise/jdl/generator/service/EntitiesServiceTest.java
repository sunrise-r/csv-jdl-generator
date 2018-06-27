package com.sunrise.jdl.generator.service;

import com.sunrise.jdl.generator.entities.Entity;
import com.sunrise.jdl.generator.entities.Field;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static junit.framework.TestCase.assertTrue;


public class EntitiesServiceTest {

    private EntitiesService entitiesService = new EntitiesService(new Settings());

    /**
     * На примере чтения сущности ContactDataCorrection и DriverLicense
     */
    @Test
    public void validationOfFieldsTypeReading() {
        ArrayList<InputStream> streams = new ArrayList<>(1);
        streams.add(this.getClass().getResourceAsStream("/twoEntities.csv"));
        Collection<Entity> result = entitiesService.readAll(streams);
        Entity contacts = result.stream().findFirst().get();
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

        Entity license = result.stream().skip(1).findFirst().get();
        List<Field> licenseFields = license.getFields();

        Assert.assertEquals("ZonedDateTime", licenseFields.get(0).getFieldType());
        Assert.assertEquals("String", licenseFields.get(1).getFieldType());
        Assert.assertEquals("String", licenseFields.get(2).getFieldType());
        Assert.assertEquals("String", licenseFields.get(3).getFieldType());
        Assert.assertEquals("String", licenseFields.get(4).getFieldType());
        Assert.assertEquals("ZonedDateTime", licenseFields.get(5).getFieldType());
        Assert.assertEquals("ZonedDateTime", licenseFields.get(6).getFieldType());
        Assert.assertEquals("String", licenseFields.get(7).getFieldType());
        Assert.assertEquals("Employee", licenseFields.get(8).getFieldType());
        Assert.assertEquals("Group", licenseFields.get(9).getFieldType());
        Assert.assertEquals("Список<Document>", licenseFields.get(10).getFieldType());
    }

    @Test
    public void testCorrectFieldsType() {
        ArrayList<InputStream> streams = new ArrayList<>(1);
        streams.add(this.getClass().getResourceAsStream("/twoEntities.csv"));
        Collection<Entity> result = entitiesService.readAll(streams);
        Entity contacts = result.stream().findAny().get();
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

        Entity license = result.stream().skip(1).findFirst().get();
        List<Field> licenseFields = license.getFields();

        Assert.assertEquals("ZonedDateTime", licenseFields.get(0).getFieldType());
        Assert.assertEquals("String", licenseFields.get(1).getFieldType());
        Assert.assertEquals("String", licenseFields.get(2).getFieldType());
        Assert.assertEquals("String", licenseFields.get(3).getFieldType());
        Assert.assertEquals("String", licenseFields.get(4).getFieldType());
        Assert.assertEquals("ZonedDateTime", licenseFields.get(5).getFieldType());
        Assert.assertEquals("ZonedDateTime", licenseFields.get(6).getFieldType());
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
        Collection<Entity> result = entitiesService.readAll(streams);
        int numberOfStructure = entitiesService.createStructures(result);
        Assert.assertEquals(10, numberOfStructure);
    }
}
