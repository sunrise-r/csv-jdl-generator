package com.sunrise.jdl.generator.service.iad;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunrise.jdl.generator.actions.Action;
import com.sunrise.jdl.generator.entities.Field;
import com.sunrise.jdl.generator.service.JDLFieldsType;
import com.sunrise.jdl.generator.ui.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Сервис генерации данных визуального интерфейса
 */
public class UIGeneratorService {

    private static final String PRESENTATION_CODE_TEMPLATE = "%sPresentation";

    private static final String PROJECTION_CODE_TEMPLATE = "%s%sListProjection";


    /**
     * Преобразовать данные о сщуности в информацию о проекции
     *
     * @param entityName       Название сущности
     * @param fields           поля сущности
     * @param actions          доступные действия для проекции
     * @param presentationCode Код представления
     * @param projectionType
     * @return Информация о проекции
     */
    public ProjectionInfo toProjectionInfo(String entityName, Set<Field> fields, Collection<Action> actions, String presentationCode, ProjectionParameter projectionType, String translationPath) {
        ProjectionInfo projectionInfo = new ProjectionInfo();
        projectionInfo.setCode(getProjectionCode(entityName, projectionType.getName()));
        projectionInfo.setName(getProjectionCode(entityName, projectionType.getName()));
        projectionInfo.setFilters(projectionType.getFilters());
        projectionInfo.setParentCode(presentationCode);
        projectionInfo.setFields(fields.stream().map(f -> new BaseField().code(f.getFieldName()).name(f.getFieldLabel()).displayFormat(parse(f.getFieldType()))).collect(Collectors.toList()));
        projectionInfo.getFields().forEach(f -> f.setTranslationCode(translationPath + presentationCode.substring(0,1).toUpperCase() + presentationCode.substring(1) + '.' + f.getCode()));
        projectionInfo.setActions(new ArrayList<>(actions));
        projectionInfo.setOrder(projectionType.getOrder());



        return projectionInfo;
    }

    private String parse(String fieldType) {
        try {
            JDLFieldsType.valueOf(fieldType);
            return fieldType;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Создать представление на основе информации о названии сущности и коде реестра
     *
     * @param entityName   Название сущности
     * @param registryCode код  реестра интерфейсов
     * @return Базовая инфомрация о представлении созданная на основе @entityName and @registryCode
     */
    public RegistryItem createPresenationFor(String entityName, String registryCode) {
        RegistryItem item = new RegistryItem();
        item.setParentCode(registryCode);
        item.setCode(getPresentationName(entityName));
        item.setName(item.getCode());
        return item;
    }

    /**
     * Очистить целевую диркторию  генерации файлов описания интерфейса
     *
     * @param directory Целевая директория
     * @throws IOException Возникает в случае если невозможно очистить директорию
     */
    public void removeDirectory(Path directory) throws IOException {
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


    private String getPresentationName(String entityName) {
        return toLowcase(String.format(PRESENTATION_CODE_TEMPLATE, entityName));
    }

    private String getProjectionCode(String entityName, String type) {
        return toLowcase(String.format(PROJECTION_CODE_TEMPLATE, entityName, type));
    }

    private String toLowcase(String toLower) {
        return Character.toLowerCase(toLower.charAt(0)) + toLower.substring(1);
    }


    /**
     * Load gid generation config file
     *
     * @param configPath path to config file
     * @return UIGenerationParameters
     */
    public UIGenerateParameters loadConfig(String configPath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(configPath), UIGenerateParameters.class);
    }
}
