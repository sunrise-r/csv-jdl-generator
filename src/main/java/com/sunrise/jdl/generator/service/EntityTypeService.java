package com.sunrise.jdl.generator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sunrise.jdl.generator.actions.Action;
import com.sunrise.jdl.generator.entities.Entity;
import com.sunrise.jdl.generator.entities.Field;
import com.sunrise.jdl.generator.entities.ResultWithWarnings;
import com.sunrise.jdl.generator.forJson.ProjectionInfo;
import com.sunrise.jdl.generator.forJson.BaseField;
import com.sunrise.jdl.generator.service.iad.UIGeneratorService;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class EntityTypeService {

    private CSVEntityTypeReader csvEntityTypeReader = new CSVEntityTypeReader();

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
     * @return Map<String                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               Set                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               <                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               Field>> result - имя родителя и список его полей
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
     * @param fileWithActions   - путь к csv-файлу c Action (объекты Action создаются при парсинге файла),
     *                          которые добавляются к каждому объекту BaseData
     * @param destinationFolder - директория, в которой создадутся директории с файлами для объектов BaseData.
     * @param parentWithFields  - исходные данные для конвертации в объекты BaseData
     * @throws FileNotFoundException
     */
    public void writeToJsonFile(String fileWithActions, String destinationFolder, Map<String, Set<Field>> parentWithFields) throws FileNotFoundException {
        ActionService actionService = new ActionService();
        InputStream actionsStream = null;

        File file = new File(fileWithActions);
        if (file.exists()) {
            actionsStream = new FileInputStream(file);
        } else {
            actionsStream = this.getClass().getResourceAsStream(fileWithActions); //why?
        }

        Collection<Action> actions = actionService.readDataFromCSV(actionsStream);

        List<ProjectionInfo> projectionInfoList = toProjectionInfo(parentWithFields, actions);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        Path targetFolder = Paths.get(destinationFolder);

        if (Files.exists(targetFolder)) {
            try {
                Files.walk(targetFolder)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            } catch (IOException e) {
                System.err.println("Ошибка при очистке целевой директории: " + Arrays.toString(e.getStackTrace()));
            }
        }

        for (ProjectionInfo projectionInfo : projectionInfoList) {
            Path baseDataPath = null;
            try {
                String correctPath = projectionInfo.getCode().substring(0, 1).toLowerCase() + projectionInfo.getCode().substring(1);
                baseDataPath = Files.createDirectories(Paths.get(destinationFolder + "/" + correctPath));
                mapper.writeValue(new File(baseDataPath + "/" + correctPath + ".json"), projectionInfo);
            } catch (IOException e) {
                System.err.println("Ошибка создания директории назначения: " + Arrays.toString(e.getStackTrace()));
            }
        }

    }


    /**
     * Конвертация входных данных в объекты BaseData (используется для корректной записи в json-формате в файл).
     * Объект BaseData создаяется для каждой пары ключ-значение.
     * Ключ карты используется как name для BaseData. Объекты Field в значении карты Set<Field> конвертируются в объекты
     * BaseField (также используются для корректной записи в json-формате в файл) и сохряняются в поле listFields
     * у соответствующего объекта BaseData.
     * У всех созданных объектов BaseData полю actions присваиваится значение List actions.
     *
     * @param entityInfoes - Инфрмация о сущностях и полях
     * @param actions    - список возможных действий. Получается при парсинге файла в формате csv.
     * @return список созданных BaseData
     */
    private List<ProjectionInfo> toProjectionInfo(Map<String, Set<Field>> entityInfoes, Collection<Action> actions) {
        return uiGeneratorService.toProjectionInfo(entityInfoes, actions);
    }
}
