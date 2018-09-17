package com.sunrise.jdl.generator.service;

import com.sunrise.jdl.generator.entities.Entity;
import com.sunrise.jdl.generator.entities.ResultWithWarnings;
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
    public void mergeTypesTest(){
        InputStream stream= this.getClass().getResourceAsStream("/entityTypes.csv");
        Map<String,List<String>> types = entityTypeService.readCsv(stream);
        ArrayList<InputStream> streams = new ArrayList<>(1);
        streams.add(this.getClass().getResourceAsStream("/entitiesForTheEntityTypesTest.csv"));
        Collection<Entity> entities = entitiesService.readAll(streams);
        ResultWithWarnings<Map<String, List<Entity>>> resultWithWarnings = entityTypeService.mergeTypesWithThemSubtypes(entities, types);

        Assert.assertEquals(resultWithWarnings.warnings.size(),0);
        Map<String, List<Entity>> result =  resultWithWarnings.result;
        List<Entity> entities1 = result.get("Account");
        List<Entity> entities2 = result.get("Agreement");
        List<Entity> entities3 = result.get("Profile");
        Assert.assertEquals(entities1.size(),2);
        Assert.assertEquals(entities2.size(),1);
        Assert.assertEquals(entities3.size(),3);
        Assert.assertEquals(entities1.get(0).getClassName(),"AccountLocalCertificate");
        Assert.assertEquals(entities1.get(1).getClassName(),"AccountCloudCertificate");
        Assert.assertEquals(entities2.get(0).getClassName(),"Agreement");
        Assert.assertEquals(entities3.get(0).getClassName(),"BuisnessCard");
        Assert.assertEquals(entities3.get(1).getClassName(),"Records");
        Assert.assertEquals(entities3.get(2).getClassName(),"ProfileRegistrationData");
    }

    @Test
    public void mergeWarningsTest(){
        InputStream stream= this.getClass().getResourceAsStream("/entityTypes.csv");
        Map<String,List<String>> types = entityTypeService.readCsv(stream);
        ArrayList<InputStream> streams = new ArrayList<>(1);
        streams.add(this.getClass().getResourceAsStream("/entitiesForTheEntityTypesTest.csv"));
        Collection<Entity> entities = entitiesService.readAll(streams);
        types.get("Account").set(0,"WRONG");
        ResultWithWarnings<Map<String, List<Entity>>> resultWithWarnings = entityTypeService.mergeTypesWithThemSubtypes(entities, types);
        Assert.assertEquals(resultWithWarnings.warnings.size(),1);
    }
}
