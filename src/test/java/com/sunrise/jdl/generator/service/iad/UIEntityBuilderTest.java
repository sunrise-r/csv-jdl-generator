package com.sunrise.jdl.generator.service.iad;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class UIEntityBuilderTest {

    @Test
    public void uiEntityBuilderCreatesEntities() throws IOException {
        UIEntityBuilder uiEntityBuilder = new UIEntityBuilder();
        List<UIEntity> entities = uiEntityBuilder.createEntities(this.getClass().getResource("/chat.csv").getPath());
        Assert.assertEquals(3, entities.size());

        UIEntity entity = entities.get(0);
        Assert.assertEquals("User", entity.getName());
        List<UIField> fields = entity.getFields();
        Assert.assertEquals(4, entity.getFields().size());

        Assert.assertEquals("firstName", fields.get(0).getName());
        Assert.assertEquals("LastName", fields.get(1).getName());
        Assert.assertEquals("messages", fields.get(2).getName());
        Assert.assertEquals("profile", fields.get(3).getName());

        Assert.assertEquals("String", fields.get(0).getType());
        Assert.assertEquals("String", fields.get(1).getType());
        Assert.assertEquals("List<Message>", fields.get(2).getType());
        Assert.assertEquals("Profile", fields.get(3).getType());

        entity = entities.get(1);
        Assert.assertEquals("Message", entity.getName());
        fields = entity.getFields();
        Assert.assertEquals(3, entity.getFields().size());

        Assert.assertEquals("text", fields.get(0).getName());
        Assert.assertEquals("date", fields.get(1).getName());
        Assert.assertEquals("number", fields.get(2).getName());

        Assert.assertEquals("String", fields.get(0).getType());
        Assert.assertEquals("ZonedDateTime", fields.get(1).getType());
        Assert.assertEquals("Integer", fields.get(2).getType());
    }
}
