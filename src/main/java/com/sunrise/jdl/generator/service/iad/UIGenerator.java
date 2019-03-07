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
        for (UIEntity entity : entities) {
            RegistryItem registryItem = createPresentationFor(entity.getName(), generateParameters.getRegistryCode(), generateParameters);
            registryItems.add(registryItem);
            for (ProjectionParameter projectionParameter : generateParameters.getProjectionsInfoes()) {
                for (ProjectionType projectionType : ProjectionType.values()) {
                    projectionInfos.add(toProjectionInfo(entity.getName(), entity.getFields(), actions, registryItem.getCode(), projectionParameter, projectionType, generateParameters));
                }
            }
        }
        UIData uiData = new UIData();
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

        for (RegistryItem registryItem : uiData.getRegistryItems()) {
            Path baseDataPath = Files.createDirectories(Paths.get(destinationDir + "/" + registryItem.getCode()));
            mapper.writeValue(new File(baseDataPath + "/" + registryItem.getCode() + ".json"), registryItem);
        }
        for (ProjectionInfo projectionInfo : uiData.getProjectionInfos()) {
            String subdir = projectionInfo.getProjectionType().toString().startsWith("Lookup") ? "Lookup" : projectionInfo.getProjectionType().toString();
            Path baseDataPath = Files.createDirectories(Paths.get(destinationPath + "/" + projectionInfo.getParentCode() + "/" + subdir));
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
    public RegistryItem createPresentationFor(String entityName, String registryCode, UIGenerateParameters generateParameters) {
        RegistryItem item = new RegistryItem();
        item.setParentCode(registryCode);
        item.setCode(getPresentationName(generateParameters.isPluralPresentations() ? English.plural(entityName) : entityName));
        if (generateParameters.getTitlePath() != null) {
            item.setLabel(generateParameters.getTitlePath().replaceAll("<ENTITY_NAME>", generateParameters.isPluralTranslations() ? English.plural(entityName) : entityName));
        }
        return item;
    }
    /**
     * Преобразовать данные о сщуности в информацию о проекции
     *
     * @param entityName       Название сущности
     * @param fields           поля сущности
     * @param actions          доступные действия для проекции
     * @param presentationCode Код представления
     * @param projectionParameter
     * @return Информация о проекции
     */
    public ProjectionInfo toProjectionInfo(String entityName, List<UIField> fields, Collection<Action> actions, String presentationCode, ProjectionParameter projectionParameter, ProjectionType projectionType, UIGenerateParameters generateParameters) {
        ProjectionInfo projectionInfo = new ProjectionInfo();
        projectionInfo.setFilters(projectionParameter.getFilters());
        projectionInfo.setParentCode(presentationCode);

        // Добавляю все поля, кроме списков
        projectionInfo.setFields(new ArrayList<>());

        for (UIField f : fields) {
            if (isJdlType(f.getType())) {
                projectionInfo.getFields().add(new BaseField().code(f.getName()).name(f.getName()).displayFormat(f.getType()));
            } else {
                if (projectionType == ProjectionType.Form) {
                    String fieldType = f.getType().startsWith("List<") ? "List" : f.getType();
                    projectionInfo.getFields().add(new LookupField().lookup(extractEntityName(f.getType())).code(f.getName()).name(f.getName()).displayFormat(fieldType));
                }
            }
        }

        projectionInfo.setActions(new ArrayList<>(actions));
        projectionInfo.setOrder(projectionParameter.getOrder());
        projectionInfo.setProjectionType(projectionType);

        // Генерирую код перевода
        String translationEntityName = generateParameters.isPluralTranslations() ? English.plural(entityName) : entityName;
        List<String> names = new ArrayList<>();
        generateParameters.getAdditionalFields().forEach(x -> names.add(x.getFieldName()));
        for (BaseField f : projectionInfo.getFields()) {
            if (names.contains(f.getCode()))
                f.setTranslationCode(generateParameters.getAdditionalFieldsTranslationPath() + '.' + f.getCode());
            else
                f.setTranslationCode(generateParameters.getTranslationPath() + translationEntityName.substring(0, 1).toUpperCase() + translationEntityName.substring(1) + '.' + f.getCode());
        }

        String name = generateParameters.isUseEntityName() ? entityName : "";
        projectionInfo.setCode(getProjectionCode(name, projectionParameter.getName(), projectionType.toString()));
        projectionInfo.setLabel(generateParameters.getTranslationPath() + ".tabs." + projectionParameter.getName().toLowerCase());
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
