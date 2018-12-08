package com.sunrise.jdl.generator.service;

import com.sunrise.jdl.generator.entities.Entity;
import com.sunrise.jdl.generator.entities.Field;
import com.sunrise.jdl.generator.entities.Relation;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Считывает Entity, корректириреут их поля, создает их структуру,
 * записывает Entity и их структуру в файл
 */
public class EntitiesService {


    /**
     * Шаблон вывода информации о пейджинации сущностей.
     */
    private static final String PAGINATE_TEMPLATE = "paginate %s with %s\n";

    /**
     * Шаблон настройки генерации ДТО
     */
    private static final String MAPSTRUCT_TEMPLATE = "dto * with mapstruct\n";


    /**
     * Шаблон настройки генерации сервисов и исключений генерации
     */
    private static final String GENERATE_SERVICIES_WITH_EXCEPT_TEMPLATE = "service %s with serviceClass except %s\n";

    /**
     * Шаблон настройки генерации серисов
     */
    private static final String GENERATE_SERVICIES_TEMPLATE = "service %s with serviceClass\n";

    /**
     * Шаблон настройки генерации микросервисов
     */
    public static final String MICROSERVICE_TEMPLATE = "microservice * with %s\n";

    private final Set<String> convertableToJdlTypes = new HashSet<>();
    private final Set<String> entitiesToIngore = new HashSet<>();
    private final Set<String> fieldsToIngore = new HashSet<>();
    private final Settings settings;
    private final CSVEntityReader entityReader;

    /**
     * Конструктор
     *
     * @param settings Настройки генерации файла описания данных.
     */
    public EntitiesService(Settings settings) {
        this.settings = settings;
        convertableToJdlTypes.add("строка");
        convertableToJdlTypes.add("число");
        convertableToJdlTypes.add("дата/время");
        convertableToJdlTypes.add("дробное");
        convertableToJdlTypes.add("булев");
        convertableToJdlTypes.add("список");
        if (entitiesToIngore != null) {
            this.entitiesToIngore.addAll(settings.getEntitiesToIngore());
        }
        if (fieldsToIngore != null) {
            this.fieldsToIngore.addAll(settings.getFieldsToIngore());
        }
        entityReader = new CSVEntityReader(fieldsToIngore, entitiesToIngore, convertableToJdlTypes);
    }

    public Set<Entity> readAll(List<InputStream> resources) {
        Set<Entity> entities = new LinkedHashSet<>();
        for (InputStream st : resources) {
            for (Entity en : readDataFromCSV(st)) {
                if (entities.contains(en)) {
                    System.out.println("Duplicated entity, exist=" + en.toString());
                    continue;
                }
                entities.add(en);
            }
            entities.addAll(readDataFromCSV(st));
        }
        return entities;
    }

    /**
     * Метод читает сущности из передаваемого .csv файла
     *
     * @param stream Поток с данными о сущностях
     * @return entitiesToIngore Список сущностей сформированных на основе потока данных
     */
    public java.util.Collection<Entity> readDataFromCSV(InputStream stream) {
        return entityReader.readDataFromCSV(stream);
    }

    /**
     * Для каждой entity из entitiesToIngore вызывается метод createStructures(Entity entity)
     *
     * @param entities
     * @return total number of created structure
     */
    public int createStructures(Collection<Entity> entities) {
        int totalCreatedStructure = 0;
        for (Entity entity : entities) {
            totalCreatedStructure += this.createStructure(entity);
        }
        return totalCreatedStructure;
    }

    /**
     * Если сущность содержит в fieldsToIngore Список, метод создает объект Relation и
     * добавляет его в поле relations у Entity.
     * Метод возращает количество считанных структур.
     *
     * @param entity
     * @return number of created structure
     */
    public int createStructure(Entity entity) {
        int count = 0;
        List<Field> fields = entity.getFields();
        for (Field field : fields) {
            if (!field.isJdlType() && (field.getFieldType().matches(".*[Сс]писок.*") || field.getFieldType().matches(".*[Ll]ist.*"))) {
                Relation relation = new Relation(entity, field, Relation.RelationType.OneToMany);
                entity.getRelations().add(relation);
            } else if (!field.isJdlType()) {
                Relation relation = new Relation(entity, field, Relation.RelationType.OneToOne);
                entity.getRelations().add(relation);
            }
            count++;
        }
        return count;
    }

    /**
     * Анализирует список отношений в сущностях и находит те отношения для которых несуществует сущностей.
     *
     * @param entities Список дотупных сущностей
     * @return
     */
    public Map<Entity, List<Relation>> checkRelations(Collection<Entity> entities) {
        Map<Entity, List<Relation>> relationMap = new HashMap<>();
        Set<String> availableEntities = entities.stream().map(x -> x.getClassName()).collect(Collectors.toSet());
        entities.stream().filter(entity -> entity.getRelations() != null).forEach(entity -> {
            for (Relation relation : entity.getRelations()) {
                if (availableEntities.contains(relation.getEntityTo())) return;
                if (!relationMap.containsKey(entity)) {
                    relationMap.put(entity, new ArrayList<>());
                }
                relationMap.get(entity).add(relation);
            }
        });
        return relationMap;
    }

    /**
     * Перегруженный вариант writeEntities(Entity entity, BufferedWriter writer).
     * Для каждой entity из enities вызывается метод writeEntities(Entity entity, BufferedWriter writer)
     *
     * @param entities
     * @param writer
     * @return
     */
    public void writeEntities(Collection<Entity> entities, Writer writer) throws IOException {
        for (Entity entity : entities) {
            writeEntity(entity, writer);
        }
        if (settings.getPaginationType() != null) {
            writer.write(String.format(PAGINATE_TEMPLATE, "*", settings.getPaginationType()));
        }
        if (settings.isUseMapStruct()) {
            writer.write(String.format(MAPSTRUCT_TEMPLATE, "*"));
        }
        if (settings.getGenerateServiciesFor() != null) {
            if (settings.getExceptServiceGenerationFor() != null) {
                writer.write(String.format(GENERATE_SERVICIES_WITH_EXCEPT_TEMPLATE, settings.getGenerateServiciesFor(), settings.getExceptServiceGenerationFor()));
            } else {
                writer.write(String.format(GENERATE_SERVICIES_TEMPLATE, settings.getGenerateServiciesFor()));
            }
        }
        if (settings.getMicroserviceName() != null) {
            writer.write(String.format(MICROSERVICE_TEMPLATE, settings.getMicroserviceName()));
        }
    }

    /**
     * Метод пишет сущности с полями и структурами в файл в формате JDL
     *
     * @param entity
     * @param writer
     * @throws IOException
     */
    private void writeEntity(Entity entity, Writer writer) throws IOException {
        writer.write(entity.toString() + "\n");
        List<Relation> relations = entity.getRelations();
        if (relations.size() > 0) {
            for (Relation relation : relations) {
                writer.write(relation.toString() + "\n");
            }
        }
    }
}
