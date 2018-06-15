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

    public DescriptionService(DescriptionServiceSettings settings){
        this.settings = settings;
    }

    /**
     * Для каждой переданной сущности возвращает ее описание как значение и ключь как название файла в котором должно храниться описание
     * @param enitites Список сущностей для которых нужно сгенерировать описание
     * @return Ключ - название файла. Значение - описание сущности
     */
    public Map<String,String> getEntitiesDescription(List<Entity> enitites){
        return enitites.stream().collect(Collectors.toMap(Entity::getClassName, en->parseEn(en)));
    }

    private String parseEn(Entity en){
        Map<String,String> result = new LinkedHashMap<>();
        result.put(settings.getGatewayAppName(), generateGatewayPart(en));
        result.put(settings.getGatewayAppName(), generateMSPart(en));
        try {
            return objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateMSPart(Entity en) {
        final String fullEntityName = getFullEntityName(en.getClassName());
        final String title = String.format("%s", en.getLabel());
        final String createLabel = String.format("Создать новый '%s'",en.getLabel());
        final String createOrEditLabel = String.format("Создать или отредактировать Accaunt Cloud Certificate",en.getLabel());
        final String question = String.format("Вы уверены что хотите удалить '%s' {{ id }}",en.getLabel());
        Map<String,Object> result = new LinkedHashMap<>();
        Map<String,Object> enProps = new LinkedHashMap<>();
        Map<String,String> homeProps = new LinkedHashMap<>();
        homeProps.put("title",title);
        homeProps.put("createLabel",createLabel);
        homeProps.put("createOrEditLabel", createOrEditLabel);
        enProps.put("home",homeProps);
        Map<String,String> deleteProps = new LinkedHashMap<>();
        deleteProps.put("question",question);
        enProps.put("delete",deleteProps);
        Map<String,String> detailsProps = new LinkedHashMap<>();
        detailsProps.put("title",title);
        enProps.put("detail",detailsProps);
        en.getFields().stream().forEach(f->enProps.put(f.getFieldName(),f.getFieldLable()));

        result.put(settings.getGatewayAppName(),enProps);


    }

    private String generateGatewayPart(Entity en) {
        return null;
    }

}
