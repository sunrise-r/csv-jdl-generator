package com.sunrise.jdl.generator.service;

import org.junit.Assert;
import org.junit.Assert.*;
import org.junit.Test;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EntityTypeServiceTest {

    private EntityTypeService entityTypeService = new EntityTypeService();

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


}
