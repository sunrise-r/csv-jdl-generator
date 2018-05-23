package com.sunrise.jdl.generator.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
     * Конструктор
     * @param className Название класса
     * @param fields Поля класса
     */
    public Entity(String className, ArrayList<Field> fields) {
        this.className = className;
        this.fields = fields;
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

    /**
     * TODO реализовать метод.
     * Возвращает строку описывающею entity в формате jdl
     * @return
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
