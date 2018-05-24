package com.sunrise.jdl.generator.entities;

import java.io.OutputStreamWriter;
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
    private ArrayList<String> structure;


    /**
     * Конструктор
     *
     * @param className Название класса
     * @param fields    Поля класса
     */
    public Entity(String className, ArrayList<Field> fields) {
        this.className = className;
        this.fields = fields;
        this.structure = new ArrayList<>();
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

    public ArrayList<String> getStructure() {
        return structure;
    }

    /**
     * Возвращает строку описывающею entity в формате jdl
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("entity " + this.className + " {\n");
        for (int i = 0; i < fields.size(); i++) {
            s.append("%s");
        }
        s.append("}");
        return String.format(s.toString(), fields.toArray());
    }

    /**
     * Метод корректирует тип полей в соотвествии с требованиями jdl.
     *
     * @return number of corrections
     */
    public int correctsFieldsType() {
        int numberOfCorrection = 0;
        for (Field field : fields) {
            boolean wasCorrection = field.correctFieldsType();
            if (wasCorrection) numberOfCorrection++;
        }
        return numberOfCorrection;
    }

    /**
     * Если сущность содержит в полях Список, метод записывает структуру в поле structure.
     * Метод возращает количество считанных структур.
     * @return
     */
    public int createStructure() {
        int count = 0;
        StringBuilder builder = new StringBuilder();
        for (Field field : fields) {
            if (field.isEntity()) {
                count++;
                builder.append("relationship OneToMany {\n");
                String fieldType = field.getFieldType();
                int start = fieldType.indexOf("<");
                int finish = fieldType.indexOf(">");
                String type = fieldType.substring(start + 1, finish);
                builder.append(this.getClassName()).append(" to ").append(type).append("\n}");
                structure.add(builder.toString());
            }
        }
        return count;
    }


}
