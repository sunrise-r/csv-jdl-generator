package com.sunrise.jdl.generator.service.i18n;

import com.google.common.base.CaseFormat;
import com.sunrise.jdl.generator.entities.RawData;
import com.sunrise.jdl.generator.service.common.GeneratorWriter;
import com.sunrise.jdl.generator.service.jdl.JdlEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class I18NService {

    private final GeneratorWriter writer;

    public I18NService(GeneratorWriter writer) {
        this.writer = writer;
    }

    public List<I18nModel> generateModel(String appName, Map<String, List<RawData>> rawData, List<JdlEntity> entities) {
        return entities.stream().map(entity -> getI18nModel(appName, rawData.get(entity.getEntityName()), entity)).collect(Collectors.toList());
    }

    public void saveModel(File resourceFolder, I18nModel i18nModel) {
        String fileName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, i18nModel.getEntityName()) + ".json";
        File file = Paths.get(resourceFolder.getPath(), fileName).toFile();
        try {
            writer.renderI18N(i18nModel, new FileOutputStream(file));
        } catch (Exception ex) {
            System.out.println("Failed to render i18n for entity:" + i18nModel.getEntityName() + "\n" + ex.toString());
        }

    }


    private I18nModel getI18nModel(String appName, List<RawData> rawData, JdlEntity entity) {
        RawData entityData = rawData.stream().filter(r -> r.getEntityName() != null).findFirst().get();
        I18nModel model = getI18nModel(appName, entityData);
        model.setHome(generateHome(entityData));
        model.setDetail(generateDetail(entityData));
        model.setDelete(generateDelete(entityData));
        model.setAppName(appName);
        rawData.forEach(field -> model.getFields().put(field.getFieldName(), CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, field.getFieldLabel())));
        return model;
    }

    private I18nModel getI18nModel(String appName, RawData entityData) {
        I18nModel model = new I18nModel();
        model.setAppName(appName);
        final String createLabel = String.format("Новая запись '%s' создана с идентификатором {{ param }}", entityData.getEntityLabel());
        final String updateLabel = String.format("Запись '%s' с идентификатором {{ param }} обновлена", entityData.getEntityLabel());
        final String deleteLabel = String.format("Запись '%s' с идентификатором {{ param }} удалена", entityData.getEntityLabel());
        model.setCreated(createLabel);
        model.setUpdated(updateLabel);
        model.setDeleted(deleteLabel);
        model.setEntityName(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, entityData.getEntityName()));
        return model;
    }

    private I18nModel.Delete generateDelete(RawData entityData) {
        I18nModel.Delete delete = new I18nModel.Delete();
        final String question = String.format("Вы уверены что хотите удалить '%s' {{ id }}?", entityData.getEntityLabel());
        delete.setQuestion(question);
        return delete;
    }

    private I18nModel.Detail generateDetail(RawData entityData) {
        I18nModel.Detail detail = new I18nModel.Detail();
        String title = entityData.getEntityLabel();
        detail.setTitle(title);
        return detail;
    }

    private I18nModel.Home generateHome(RawData entityData) {
        final String title = String.format("%s", entityData.getEntityLabel());
        final String createLabel = String.format("Создать новую запись '%s'", entityData.getEntityLabel());
        final String createOrEditLabel = String.format("Создать или отредактировать '%s'", entityData.getEntityLabel());
        I18nModel.Home home = new I18nModel.Home();
        home.setCreateLabel(createLabel);
        home.setTitle(title);
        home.setCreateOrEditLabel(createOrEditLabel);
        return home;
    }
}
