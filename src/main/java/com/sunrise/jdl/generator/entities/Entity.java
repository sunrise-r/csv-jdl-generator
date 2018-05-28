package com.sunrise.jdl.generator.entities;

import java.util.ArrayList;

/**
 * Класс для хранения сущностей
 * TODO: не плохо бы добавить описания из документации, пускай и на английском
 */
public class Entity {


    /**
     * Название класса
     */
    private String className;

    /**
     * Список полей класса
     */
    private ArrayList<Field> fields;

    /**
     * Список структур
     */
    private ArrayList<Relation> relations;


    /**
     * Конструктор
     *
     * @param className Название класса
     * @param fields    Поля класса
     */
    public Entity(String className, ArrayList<Field> fields) {
        this.className = className;
        this.fields = fields;
        this.relations = new ArrayList<Relation>();
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public ArrayList<Field> getFields() {
        return fields;
    }

    public void setFields(ArrayList<Field> fields) {
        this.fields = fields;
    }

    public ArrayList<Relation> getRelations() {
        return relations;
    }

    public void setRelations(ArrayList<Relation> relations) {
        this.relations = relations;
    }


    /**
     * @return Возвращает строку описывающюю entity в формате jdl
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("entity " + this.className + " {\n");
        for (int i = 0; i < fields.size(); i++) {
            if (fields.get(i).isNotEntity()) {
                s.append(fields.get(i).toString()).append(",\n");
            }
        }
        String s1 = s.toString();
        if (s1.endsWith(",\n")) {
            s1 = s1.substring(0, s1.length()-2);
        }
        return s1 + "\n}";
    }

}
