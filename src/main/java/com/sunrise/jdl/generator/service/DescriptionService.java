package com.sunrise.jdl.generator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunrise.jdl.generator.entities.Entity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Сервис формирвоания информации названии сущности.
 * Генерирует на основании описания сущности json файлы с описанием основных текстов для jhipster
 * Нужен так как, jdl нет опции которая бы позволяла задать метку сущности. Поэтому добавлять перевод придется руками, предварительно его сгенерировав.
 * Created by igorch on 15.06.18.
 */
public class DescriptionService {

    private final DescriptionServiceSettings settings;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DescriptionService(DescriptionServiceSettings settings) {
        this.settings = settings;
    }

    /**
     * Для каждой переданной сущности возвращает ее описание как значение и ключь как название файла в котором должно храниться описание
     *
     * @param enitites Список сущностей для которых нужно сгенерировать описание
     * @return Ключ - название файла. Значение - описание сущности
     */
    public Map<String, String> getEntitiesDescription(List<Entity> enitites) {
        return enitites.stream().collect(Collectors.toMap(en->settings.getMicroserviceAppName() + en.getClassName(), en -> parseEn(en)));
    }

    private String parseEn(Entity en) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put(getAppName(settings.getGatewayAppName()), generateGatewayPart(en));
        result.put(getAppName(settings.getMicroserviceAppName()), generateMSPart(en));
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, Object> generateGatewayPart(Entity en) {
        final String title = String.format("%s", en.getTitle());
        final String label = String.format("%s", en.getLabel());
        final String createLabel = String.format("Создать новый '%s'", en.getLabel());
        final String createOrEditLabel = String.format("Создать или отредактировать '%s'", en.getLabel());
        final String question = String.format("Вы уверены что хотите удалить '%s' {{ id }}?", en.getLabel());
        final String fullName = getEntityFullPath(en);
        Map<String, Object> enProps = new LinkedHashMap<>();
        Map<String, String> homeProps = new LinkedHashMap<>();
        Map<String, Object> gatewayProps = new LinkedHashMap<>();
        homeProps.put("title", title);
        homeProps.put("createLabel", createLabel);
        homeProps.put("createOrEditLabel", createOrEditLabel);
        enProps.put("home", homeProps);
        Map<String, String> deleteProps = new LinkedHashMap<>();
        deleteProps.put("question", question);
        enProps.put("delete", deleteProps);
        Map<String, String> detailsProps = new LinkedHashMap<>();
        detailsProps.put("title", label);
        enProps.put("detail", detailsProps);
        en.getFields().stream().forEach(f -> enProps.put(f.getFieldName(), f.getFieldLabel()));
        gatewayProps.put(fullName, enProps);
        return gatewayProps;
    }

    private Map<String, Object> generateMSPart(Entity en) {

        Map<String, Object> msProps = new LinkedHashMap<>();
        Map<String, Object> labels = new LinkedHashMap<>();
        final String createLabel = String.format("Новая запись '%s' создана с идентификатором {{ param }}", en.getLabel());
        final String updateLabel = String.format("Запись '%s' с идентификатором {{ param }} обновлена", en.getLabel());
        final String deleteLabel = String.format("Запись '%s' с идентификатором {{ param }} удалена", en.getLabel());
        labels.put("created", createLabel);
        labels.put("updated", updateLabel);
        labels.put("deleted", deleteLabel);
        msProps.put(getEntityFullPath(en), labels);
        return msProps;

    }

    private String getEntityFullPath(Entity en) {
        return String.format("%s%s", settings.getMicroserviceAppName(), en.getClassName());
    }

    private String getAppName(String app) {
        return String.format("%sApp", app);
    }

}
