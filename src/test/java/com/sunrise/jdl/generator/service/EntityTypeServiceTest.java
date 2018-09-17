package com.sunrise.jdl.generator.service;

import com.sunrise.jdl.generator.entities.Entity;
import com.sunrise.jdl.generator.entities.Field;
import com.sunrise.jdl.generator.entities.ResultWithWarnings;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.*;

public class EntityTypeServiceTest {

    private EntityTypeService entityTypeService = new EntityTypeService();

    private EntitiesService entitiesService = new EntitiesService(new Settings());

    @Test
    public void ValidationOfReading() {
        InputStream stream = this.getClass().getResourceAsStream("/entityTypes.csv");
        Map<String, List<String>> result = entityTypeService.readCsv(stream);
        Assert.assertEquals(result.size(), 3);
        Assert.assertEquals(result.get("Account").size(), 2);
        Assert.assertEquals(result.get("Account").get(0), "AccountLocalCertificate");
        Assert.assertEquals(result.get("Account").get(1), "AccountCloudCertificate");
        Assert.assertEquals(result.get("Agreement").size(), 1);
        Assert.assertEquals(result.get("Profile").size(), 3);
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
    public void testPrepareDataForParent() {
        Field field1 = new Field("String", "field1", "", true, true, "поле1");
        Field field2 = new Field("String", "field2", "10", true, true, "поле2");
        Field field3 = new Field("String", "field3", "10", true, true, "поле3");
        Field field4 = new Field("String", "field4", "", true, true, "поле4");
        Field field5 = new Field("String", "field5", "20", true, true, "поле5");
        Entity entity1 = new Entity("Entity1", Arrays.asList(field1, field2, field3, field4, field5), "Label1", "Title1");
        Entity entity2 = new Entity("Entity1", Arrays.asList(field1, field2, field4, field5), "Label2", "Title2");
        Entity entity3 = new Entity("Entity3", Arrays.asList(field1, field3, field4, field5), "Label3", "Title3");
        Entity entity4 = new Entity("Entity4", Arrays.asList(field1, field4), "Label4", "Title4");
        Entity entity5 = new Entity("Entity4", Arrays.asList(field1, field5), "Label5", "Title5");
        Map<String, List<Entity>> parentsInitData = new HashMap<>();

        parentsInitData.put("parent1", Arrays.asList(entity1, entity2, entity3, entity4));
        parentsInitData.put("parent2", Arrays.asList(entity1, entity2, entity3, entity5));

        EntityTypeService service = new EntityTypeService();
        Map<String, Set<Field>> parents = service.prepareDataForParentEntity(parentsInitData);

        Assert.assertEquals(parents.size(), 2);
        Assert.assertTrue(parents.containsKey("parent1"));
        Assert.assertTrue(parents.containsKey("parent2"));
        Assert.assertEquals(parents.get("parent1").size(), 2);
        Assert.assertTrue(parents.get("parent1").contains(field1));
        Assert.assertTrue(parents.get("parent1").contains(field4));
        Assert.assertFalse(parents.get("parent1").contains(field2));
        Assert.assertFalse(parents.get("parent1").contains(field3));
        Assert.assertFalse(parents.get("parent1").contains(field5));

        Assert.assertEquals(parents.get("parent2").size(), 2);
        Assert.assertTrue(parents.get("parent2").contains(field1));
        Assert.assertTrue(parents.get("parent2").contains(field5));
        Assert.assertFalse(parents.get("parent2").contains(field2));
        Assert.assertFalse(parents.get("parent2").contains(field3));
        Assert.assertFalse(parents.get("parent2").contains(field4));
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
