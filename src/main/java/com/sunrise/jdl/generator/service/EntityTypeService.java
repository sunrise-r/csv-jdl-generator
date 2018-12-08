package com.sunrise.jdl.generator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sunrise.jdl.generator.actions.Action;
import com.sunrise.jdl.generator.entities.Entity;
import com.sunrise.jdl.generator.entities.Field;
import com.sunrise.jdl.generator.entities.ResultWithWarnings;
import com.sunrise.jdl.generator.service.iad.TemplateService;
import com.sunrise.jdl.generator.service.iad.UIGeneratorService;
import com.sunrise.jdl.generator.ui.*;
import org.atteo.evo.inflector.English;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class EntityTypeService {

    private static final String RESOURCE_URL_TEMPLATE = "%s/api/_search/%s";

    private CSVEntityTypeReader csvEntityTypeReader = new CSVEntityTypeReader();

    private TemplateService templateService = TemplateService.getInstance();

    UIGeneratorService uiGeneratorService = new UIGeneratorService();


    /**
     * Гененирует карту со связями НазваниеРодителя - Список<Названия потомков> из CSV
     *
     * @param resource CSV-файл, в котором описаны отношения между сущностями
     * @return Карта с описанием связей, но самих сущностей нет
     */
    public Map<String, List<String>> readCsv(InputStream resource) {
        return csvEntityTypeReader.readDataFromCSV(resource);
    }

    /**
     * Перегруженный вариант метода prepareDataForParentEntity(String parentName, List<Entity> childrenEntities).
     * В цикле проходится по Map<String, List<Entity>> parentNameAndChildrenEntities и для каждого Map.Entry<String, List<Entity>>
     * вызывается метод prepareDataForParentEntity.
     *
     * @param parentNameAndChildrenEntities - Map<String, List<Entity>> карта в качестве ключа содержит имя родителя, в качестве значений -
     *                                      список дочерних полей
     * @return
     */
    public Map<String, Set<Field>> prepareDataForParentEntity(Map<String, List<Entity>> parentNameAndChildrenEntities) {
        Map<String, Set<Field>> allParents = new HashMap<>();
        for (Map.Entry<String, List<Entity>> relation : parentNameAndChildrenEntities.entrySet()) {
            Map<String, Set<Field>> singleParent = prepareDataForParentEntity(relation.getKey(), relation.getValue());
            allParents.putAll(singleParent);
        }
        return allParents;
    }

    /**
     * Метод подсчитывает частоту полей у дочерних сущностей List<Entity> childrenEntities и оставляет поля, которые
     * есть у всех сущностей. Возвращает имя родителя и список общих полей
     *
     * @param parentName       - имя родительской сущности
     * @param childrenEntities - список дочерних сущностей
     * @return Map<String                                                                                                                                                                                                                                                               Set                                                                                                                                                                                                                                                               <                                                                                                                               Field>> result - имя родителя и список его полей
     */
    public Map<String, Set<Field>> prepareDataForParentEntity(String parentName, List<Entity> childrenEntities) {
        Map<Field, Byte> fieldsWithFrequency = new HashMap<>();
        for (Entity entity : childrenEntities) {
            for (Field field : entity.getFields()) {
                if (fieldsWithFrequency.containsKey(field)) {
                    fieldsWithFrequency.put(field, (byte) (fieldsWithFrequency.get(field) + 1));
                } else {
                    fieldsWithFrequency.put(field, (byte) 1);
                }
            }
        }
        fieldsWithFrequency.entrySet().removeIf(pair -> pair.getValue() < childrenEntities.size());
        Map<String, Set<Field>> result = new HashMap<>();
        result.put(parentName, fieldsWithFrequency.keySet());
        return result;
    }

    /**
     * Сгруппировать сущности относительно родителькой группы.
     *
     * @param entities Список доступных сущностей
     * @param typesMap Словарь где ключ - название группы сущности, а значение название сущностей которые входят в эту группу
     * @return Список сущностей сгруппированный по группам из @typesMap, а так же список возниших при группировке предупреждений
     */
    public ResultWithWarnings<Map<String, List<Entity>>> mergeTypesWithThemSubtypes(Collection<Entity> entities, Map<String, List<String>> typesMap) {
        List<String> warnings = new ArrayList<>();
        Map<String, List<Entity>> result = typesMap.keySet().stream().collect(Collectors.toMap(x -> x, x -> new LinkedList<>()));
        Map<String, Entity> entityMap = entities.stream().collect(Collectors.toMap(x -> x.getClassName(), x -> x));
        for (String group : typesMap.keySet()) {
            for (String entityName : typesMap.get(group)) {
                Entity entity = entityMap.get(entityName);
                if (entity == null) {
                    warnings.add(String.format("В меню объявлена сущность под названем [%s], однако в списке загруженных сущностей она отсуствует", entityName));
                } else {
                    result.get(group).add(entity);
                }
            }
        }
        return new ResultWithWarnings<>(warnings, result);
    }

    /**
     * Метод конвертирует данные из параметра parentWithFields в объекты BaseData и записывает их
     * в json-файл в директории destinationFolder. Внутри директории destinationFolder для каждого объекта BaseData
     * создается собственная директория, в которую записывается json-файл. Если директория destinationFolder уже существует -
     * она будет пересоздана.
     *
     * @param actionsStream      - поток с данным описвающие доступные actions
     * @param destinationFolder  - директория, в которой создадутся директории с файлами для объектов BaseData.
     * @param entitiesInfo       - исходные данные для генерации описания UI
     * @param generateParameters Параметры генерации
     * @param entities
     * @throws FileNotFoundException
     */
    public boolean generateEntitiesPresentations(InputStream actionsStream, String destinationFolder, Map<String, Set<Field>> entitiesInfo, Map<String, List<Entity>> entitiesHierarchy, UIGenerateParameters generateParameters, List<TemplateProjection> templateProjections, Collection<Entity> entities) throws IOException {
        ActionService actionService = new ActionService();
        Map<String, Object> data = new HashMap<>();
        Collection<Action> actions = actionService.readDataFromCSV(actionsStream);

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        Path path = Paths.get(destinationFolder);

        if (Files.isDirectory(path))
            for (File f : path.toFile().listFiles())
                if (f.isDirectory())
                    uiGeneratorService.removeDirectory(f.toPath());

        for (String entityName : entitiesInfo.keySet()) {
            RegistryItem registryItem = uiGeneratorService.createPresentationFor(entityName, generateParameters.getRegistryCode(), generateParameters);
            Path baseDataPath = Files.createDirectories(Paths.get(destinationFolder + "/" + registryItem.getCode()));
            mapper.writeValue(new File(baseDataPath + "/" + registryItem.getCode() + ".json"), registryItem);
            for (ProjectionParameter projectionType : generateParameters.getProjectionsInfoes()) {
                ProjectionInfo projectionInfo;
                projectionInfo = uiGeneratorService.toProjectionInfo(entityName, entitiesInfo.get(entityName), actions, registryItem.getCode(), projectionType, generateParameters);
                projectionInfo.setSearchUrl(generateSearchUrl(entityName, generateParameters.getMicroservice(), generateParameters.isPluralSearchURL()));
                mapper.writeValue(new File(baseDataPath + "/" + projectionInfo.getCode() + ".json"), projectionInfo);
                if (projectionInfo.getCode().equals("inner"))
                    data.put("innerListProjectionFields", projectionInfo.getFields());
            }

            if (entities != null) {
                for (Entity entity : entitiesHierarchy.get(entityName)) {
                    data.put("ENTITY", entity.getClassName());
                    data.put("entityFields", entity.getFields());
                    data.put("PARENT_CODE", registryItem.getCode());
                    for (TemplateProjection tp : templateProjections) {
                        mapper.writeValue(new File(baseDataPath + "/" + templateService.fillTemplateWithData(tp.getCode(), data) + ".json"), templateService.toProjections(tp, data));
                    }
                }
            }

        }
        return true;
    }

    private String generateSearchUrl(String code, String microservice, boolean plural) {
        String urlCode = Arrays.stream(code.split("(?=\\p{Upper})")).collect(Collectors.joining("-"));
        return String.format(RESOURCE_URL_TEMPLATE, microservice, plural ? English.plural(urlCode) : urlCode).toLowerCase();
    }

}
