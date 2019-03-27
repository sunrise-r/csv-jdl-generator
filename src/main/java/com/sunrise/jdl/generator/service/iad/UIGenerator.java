package com.sunrise.jdl.generator.service.iad;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sunrise.jdl.generator.actions.Action;
import com.sunrise.jdl.generator.service.ActionService;
import com.sunrise.jdl.generator.service.JDLFieldsType;
import com.sunrise.jdl.generator.ui.*;
import org.atteo.evo.inflector.English;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class UIGenerator {

    private static final String PRESENTATION_CODE_TEMPLATE = "%sPresentation";

    private static final String PROJECTION_CODE_TEMPLATE = "%s%s%sProjection";

    public UIData generateEntitiesPresentations(UIGenerateParameters generateParameters) throws IOException {
        File actionsFile = new File(generateParameters.getActionsPath());
        InputStream actionsStream = new FileInputStream(actionsFile);

        List<UIEntity> entities = new UIEntityBuilder().createEntities(generateParameters.getEntitiesPath());

        ActionService actionService = new ActionService();
        Collection<Action> actions = actionService.readDataFromCSV(actionsStream);

        List<RegistryItem> registryItems = new ArrayList<>();
        List<ProjectionInfo> projectionInfos = new ArrayList<>();
        RegistryItem rootRegistryItem = new RegistryItem();
        rootRegistryItem.setCode(generateParameters.getRegistryCode());
        generatePresentationTranslation(rootRegistryItem, generateParameters);
        for (UIEntity entity : entities) {
            RegistryItem registryItem = createPresentationFor(entity.getName(), generateParameters.getRegistryCode(), generateParameters.isPluralPresentations());
            registryItems.add(generatePresentationTranslation(registryItem, generateParameters));
            for (ProjectionParameter projectionParameter : generateParameters.getProjectionsInfoes()) {
                for (ProjectionType projectionType : ProjectionType.values()) {
                    ProjectionInfo projectionInfo = toProjectionInfo(entity, actions, registryItem.getCode(), projectionParameter, projectionType, generateParameters.getSearchUrlPrefix());
                    projectionInfos.add(generateProjectionTranslation(projectionInfo, entity.getName(), generateParameters, projectionParameter.getName()));
                }
            }
        }

        UIData uiData = new UIData();
        uiData.setRootRegistryItem(rootRegistryItem);
        uiData.setRegistryItems(registryItems);
        uiData.setProjectionInfos(projectionInfos);
        return uiData;
    }

    public void writeUIData(UIData uiData, String destinationDir) throws IOException {
        Path destinationPath = Paths.get(destinationDir);
        if (Files.isDirectory(destinationPath))
            for (File f : destinationPath.toFile().listFiles())
                if (f.isDirectory())
                    removeDirectory(f.toPath());

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        Path baseDataPath = Files.createDirectories(Paths.get(destinationDir));
        mapper.writeValue(new File(baseDataPath + "/" + uiData.getRootRegistryItem().getCode() + ".json"), uiData.getRootRegistryItem());
        for (RegistryItem registryItem : uiData.getRegistryItems()) {
            baseDataPath = Files.createDirectories(Paths.get(destinationDir + "/" + registryItem.getCode()));
            mapper.writeValue(new File(baseDataPath + "/" + registryItem.getCode() + ".json"), registryItem);
        }
        for (ProjectionInfo projectionInfo : uiData.getProjectionInfos()) {
            String subdir = projectionInfo.getProjectionType().toString().startsWith("Lookup") ? "Lookup" : projectionInfo.getProjectionType().toString();
            baseDataPath = Files.createDirectories(Paths.get(destinationPath + "/" + projectionInfo.getParentCode() + "/" + subdir));
            mapper.writeValue(new File(baseDataPath + "/" + projectionInfo.getCode() + ".json"), projectionInfo);
        }
    }



    /**
     * Создать представление на основе информации о названии сущности и коде реестра
     *
     * @param entityName   Название сущности
     * @param registryCode код  реестра интерфейсов
     * @return Базовая инфомрация о представлении созданная на основе @entityName and @registryCode
     */
    public RegistryItem createPresentationFor(String entityName, String registryCode, boolean pluralizeEntityName) {
        RegistryItem item = new RegistryItem();
        item.setParentCode(registryCode);
        item.setCode(getPresentationName(pluralizeEntityName ? English.plural(entityName) : entityName));
        return item;
    }
    /**
     * Преобразовать данные о сщуности в информацию о проекции
     *
     * @param entity Сущность
     * @param actions доступные действия для проекции
     * @param presentationCode код представления
     * @param projectionParameter параметр представления
     * @param projectionType тип представления
     * @return Информация о проекции
     */
    public ProjectionInfo toProjectionInfo(UIEntity entity, Collection<Action> actions, String presentationCode, ProjectionParameter projectionParameter, ProjectionType projectionType, String searchUrlPrefix) {
        ProjectionInfo projectionInfo = new ProjectionInfo();
        projectionInfo.setFilters(projectionParameter.getFilters());
        projectionInfo.setParentCode(presentationCode);

        // Добавляю все поля, кроме списков
        projectionInfo.setFields(new ArrayList<>());

        for (UIField f : entity.getFields()) {
            BaseField projectionInfoField = null;
            if (projectionType == ProjectionType.Form) {
                FormField formField = new FormField().jdlType(isJdlType(f.getType())).required(f.isRequired()).length(f.getLength());
                if (isJdlType(f.getType())) {
                    formField.fieldType(f.getType());
                } else {
                    formField.presentationCode(extractEntityName(f.getType()));
                    if (f.getType().startsWith("List<")) {
                        formField.fieldType("List");
                        formField.lookup(extractEntityName(f.getType())).code(f.getName()).name(f.getName());
                    } else {
                        formField.fieldType("ProjectionReference");
                        formField.referenceProjectionCode(getProjectionCode(f.getType(), "", "Form"));
                    }
                }
                projectionInfoField = formField;
            } else {
                if (isJdlType(f.getType())) projectionInfoField = new BaseField().displayFormat(f.getType());
            }
            if (projectionInfoField != null) {
                projectionInfo.getFields().add(projectionInfoField.code(f.getName()).name(f.getLabel()));
            }
        }

        if (projectionType == ProjectionType.List) {
            projectionInfo.setSearchUrl(searchUrlPrefix + lowerFirstChar(entity.getName()));
        }
        projectionInfo.setActions(new ArrayList<>(actions));
        projectionInfo.setOrder(projectionParameter.getOrder());
        projectionInfo.setProjectionType(projectionType);
        projectionInfo.setCode(getProjectionCode(entity.getName(), projectionParameter.getName(), projectionType.toString()));
        return projectionInfo;
    }

    private RegistryItem generatePresentationTranslation(RegistryItem registryItem, UIGenerateParameters uiGenerateParameters) {
        if (uiGenerateParameters.getTitlePath() != null) {
            registryItem.setLabel(uiGenerateParameters.getTitlePath().replaceAll("<ENTITY_NAME>", registryItem.getCode()));
        }
        return registryItem;
    }

    /**
     * Генерирует коды переводов для полей проекции.
     * @param projectionInfo информация о проекции для обновления
     * @param entityName имя сущности, для которой сгенерирована проекция
     * @param uiGenerateParameters объект, содержащий информацию для генерации переводов
     * @param projectionParameterName имя генерируемой проекции
     * @return информацию о проекции с установленными путями переводов
     */
    private ProjectionInfo generateProjectionTranslation(ProjectionInfo projectionInfo, String entityName, UIGenerateParameters uiGenerateParameters,
                                                         String projectionParameterName) {
        String translationEntityName = uiGenerateParameters.isPluralTranslations() ? English.plural(entityName) : entityName;
        List<String> names = new ArrayList<>();
        uiGenerateParameters.getAdditionalFields().forEach(x -> names.add(x.getFieldName()));
        for (BaseField f : projectionInfo.getFields()) {
            if (names.contains(f.getCode()))
                f.setTranslationCode(uiGenerateParameters.getAdditionalFieldsTranslationPath() + '.' + f.getCode());
            else
                f.setTranslationCode(uiGenerateParameters.getTranslationPath() + "." + lowerFirstChar(translationEntityName) + '.' + f.getCode());
        }
        projectionInfo.setLabel(uiGenerateParameters.getTranslationPath() + ".projection" +
                (projectionParameterName.isEmpty() ? "" : "." + lowerFirstChar(projectionParameterName)));
        return projectionInfo;

    }

    /**
     * Очистить целевую диркторию  генерации файлов описания интерфейса
     *
     * @param directory Целевая директория
     * @throws IOException Возникает в случае если невозможно очистить директорию
     */
    private void removeDirectory(Path directory) throws IOException {
        if (Files.exists(directory)) {
            try {
                Files.walk(directory)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
                directory.toFile().delete();
            } catch (IOException e) {
                throw new IOException("Ошибка при очистке целевой директории: " + Arrays.toString(e.getStackTrace()));
            }
        }
    }

    private String extractEntityName(String fieldType) {
        if (fieldType.startsWith("List<")) {
            return fieldType.replace("List<", "").replace(">", "");
        } else {
            return fieldType;
        }
    }

    private boolean isJdlType(String fieldType) {
        try {
            JDLFieldsType.valueOf(fieldType);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private String getPresentationName(String entityName) {
        return lowerFirstChar(String.format(PRESENTATION_CODE_TEMPLATE, entityName));
    }

    private String getProjectionCode(String entityName, String projectionParameterName, String projectionType) {
        return lowerFirstChar(String.format(PROJECTION_CODE_TEMPLATE, entityName, projectionParameterName, projectionType));
    }

    private String lowerFirstChar(String string) {
        return string.substring(0, 1).toLowerCase() + string.substring(1);
    }
 }
