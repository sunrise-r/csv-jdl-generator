package com.sunrise.jdl.generator.service;

import com.sunrise.jdl.generator.entities.Entity;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.*;

public class EntityTypeServiceTest {

    private EntityTypeService entityTypeService = new EntityTypeService();

    private EntitiesService entitiesService = new EntitiesService(new Settings());

    @Test
    public void ValidationOfReading(){
        InputStream stream= this.getClass().getResourceAsStream("/entityTypes.csv");
        Map<String,List<String>> result = entityTypeService.readCsv(stream);
        Assert.assertEquals(result.size(),3);
        Assert.assertEquals(result.get("Account").size(),2);
        Assert.assertEquals(result.get("Account").get(0),"AccountLocalCertificate");
        Assert.assertEquals(result.get("Account").get(1),"AccountCloudCertificate");
        Assert.assertEquals(result.get("Agreement").size(),1);
        Assert.assertEquals(result.get("Profile").size(),3);
    }

    @Test
    public void mergeTypesTest() throws Exception {
        InputStream stream= this.getClass().getResourceAsStream("/entityTypes.csv");
        Map<String,List<String>> types = entityTypeService.readCsv(stream);
        ArrayList<InputStream> streams = new ArrayList<>(1);
        streams.add(this.getClass().getResourceAsStream("/entitiesForTheEntityTypesTest.csv"));
        Collection<Entity> entities = entitiesService.readAll(streams);

        Map<String, List<Entity>> result = entityTypeService.mergeTypesWithThemSubtypes(entities, types);
        Assert.assertEquals(result.size(),3);
        Assert.assertEquals(result.get("Account").size(),2);
        Assert.assertEquals(result.get("Agreement").size(),1);
        Assert.assertEquals(result.get("Profile").size(),3);
    }


}
