package com.sunrise.jdl.generator.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для хранения сущностей
 */
public class Entity extends BasicEntity{

    /**
     * Отображаемое имя сущности.
     */
    private String label;

    private String title;

    /**
     * Список полей класса
     */
    private final List<Field> fields;

    /**
     * Список структур
     */
    private final List<Relation> relations;

    /**
     * Конструктор
     *  @param className   Название класса
     * @param fields      Поля класса
     * @param entityLabel Метка(Отображаемое название) сущности
     * @param title Заголовк сущности.
     */
    public Entity(String className, List<Field> fields, String entityLabel, String title) {
        this.className = className;
        this.fields = fields;
        this.title = title;
        this.relations = new ArrayList<>();
        this.label = entityLabel;
    }

    public String getClassName() {
        return className;
    }


    public List<Field> getFields() {
        return fields;
    }


    public List<Relation> getRelations() {
        return relations;
    }

    public String getLabel() {
        return label;
    }

    public String getTitle() {
        return title;
    }


    /**
     * @return Возвращает строковое представление entity в формате jdl
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("entity " + this.className + " {\n");
        for (int i = 0; i < fields.size(); i++) {
            if (fields.get(i).isJdlType()) {
                s.append(fields.get(i).toString()).append(",\n");
            }
        }
        String s1 = s.toString();
        if (s1.endsWith(",\n")) {
            s1 = s1.substring(0, s1.length() - 2);
        }
        return s1 + "\n}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        return className.equals(entity.className);

    }

    @Override
    public int hashCode() {
        return className.hashCode();
    }
}
