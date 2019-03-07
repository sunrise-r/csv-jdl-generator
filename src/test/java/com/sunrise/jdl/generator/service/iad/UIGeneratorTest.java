package com.sunrise.jdl.generator.service.iad;

import com.sunrise.jdl.generator.actions.Action;
import com.sunrise.jdl.generator.service.ActionService;
import com.sunrise.jdl.generator.ui.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UIGeneratorTest {

    private final UIGenerator uiGenerator = new UIGenerator();
    private final ActionService actionService = new ActionService();

    private List<UIEntity> entities;
    private Collection<Action> actions;
    private ProjectionParameter projectionParameter;


    @Before
    public void setupEntities() throws IOException {
        UIEntityBuilder uiEntityBuilder = new UIEntityBuilder();
        entities = uiEntityBuilder.createEntities(this.getClass().getResource("/chat.csv").getPath());
        actions = actionService.readDataFromCSV(this.getClass().getResourceAsStream("/action.csv"));
        projectionParameter = new ProjectionParameter();
        projectionParameter.setName("");
    }

    @Test
    public void generatesListProjection() {
        UIEntity entity = entities.get(1);
        ProjectionInfo projectionInfo = uiGenerator.toProjectionInfo(entity, actions, "customRegistry", projectionParameter, ProjectionType.List);
        Assert.assertEquals(3, projectionInfo.getFields().size());
        Assert.assertEquals(12, projectionInfo.getActions().size());
        Assert.assertEquals("messageListProjection", projectionInfo.getCode());

        BaseField baseField = projectionInfo.getFields().get(0);
        Assert.assertEquals("text", baseField.getCode());
        Assert.assertEquals("Текст", baseField.getName());
        Assert.assertEquals("String", baseField.getDisplayFormat());

        baseField = projectionInfo.getFields().get(1);
        Assert.assertEquals("date", baseField.getCode());
        Assert.assertEquals("Дата", baseField.getName());
        Assert.assertEquals("ZonedDateTime", baseField.getDisplayFormat());

        baseField = projectionInfo.getFields().get(2);
        Assert.assertEquals("number", baseField.getCode());
        Assert.assertEquals("Номер", baseField.getName());
        Assert.assertEquals("Integer", baseField.getDisplayFormat());
    }

    @Test
    public void generatesFormProjection() {
        UIEntity entity = entities.get(0);
        ProjectionInfo projectionInfo = uiGenerator.toProjectionInfo(entity, actions, "customRegistry", projectionParameter, ProjectionType.Form);

        Assert.assertEquals(4, projectionInfo.getFields().size());
        Assert.assertEquals(12, projectionInfo.getActions().size());
        Assert.assertEquals("userFormProjection", projectionInfo.getCode());

        FormField formField = (FormField) projectionInfo.getFields().get(0);
        Assert.assertEquals("firstName", formField.getCode());
        Assert.assertEquals("20", formField.getFieldLength());
        Assert.assertTrue(formField.isJdlType());
        Assert.assertTrue(formField.isRequired());
        Assert.assertNull(formField.getLookupSource());
        Assert.assertNull(formField.getLookupView());
        Assert.assertEquals("String", formField.getFieldType());

        formField = (FormField) projectionInfo.getFields().get(1);
        Assert.assertEquals("lastName", formField.getCode());
        Assert.assertEquals("20", formField.getFieldLength());
        Assert.assertTrue(formField.isJdlType());
        Assert.assertFalse(formField.isRequired());
        Assert.assertNull(formField.getLookupSource());
        Assert.assertNull(formField.getLookupView());
        Assert.assertEquals("String", formField.getFieldType());

        formField = (FormField) projectionInfo.getFields().get(2);
        Assert.assertEquals("messages", formField.getCode());
        Assert.assertEquals("", formField.getFieldLength());
        Assert.assertFalse(formField.isJdlType());
        Assert.assertFalse(formField.isRequired());
        Assert.assertEquals("lookupMessageSourceListProjection", formField.getLookupSource());
        Assert.assertEquals("lookupMessageViewListProjection", formField.getLookupView());
        Assert.assertEquals("List", formField.getFieldType());
        Assert.assertEquals("Message", formField.getPresentationCode());

        formField = (FormField) projectionInfo.getFields().get(3);
        Assert.assertEquals("profile", formField.getCode());
        Assert.assertEquals("", formField.getFieldLength());
        Assert.assertFalse(formField.isJdlType());
        Assert.assertFalse(formField.isRequired());
        Assert.assertEquals("lookupProfileSourceListProjection", formField.getLookupSource());
        Assert.assertEquals("lookupProfileViewListProjection", formField.getLookupView());
        Assert.assertEquals("Entity", formField.getFieldType());
        Assert.assertNull(formField.getPresentationCode());
    }

    @Test
    public void generatesLookupProjections() {
        UIEntity entity = entities.get(2);
        ProjectionInfo projectionInfo = uiGenerator.toProjectionInfo(entity, actions, "customRegistry", projectionParameter, ProjectionType.LookupSource);

        Assert.assertEquals(2, projectionInfo.getFields().size());
        Assert.assertEquals(12, projectionInfo.getActions().size());
        Assert.assertEquals("profileLookupSourceProjection", projectionInfo.getCode());

        BaseField baseField = projectionInfo.getFields().get(0);
        Assert.assertEquals("login", baseField.getCode());
        Assert.assertEquals("Логин", baseField.getName());
        Assert.assertEquals("String", baseField.getDisplayFormat());

        baseField = projectionInfo.getFields().get(1);
        Assert.assertEquals("password", baseField.getCode());
        Assert.assertEquals("Пароль", baseField.getName());
        Assert.assertEquals("String", baseField.getDisplayFormat());
    }
}
