package com.sunrise.jdl.generator.entities;

/**
 * Класс создания связей.
 * <p>
 * Types of supported in JDL relationships:
 * 1. A bidirectional one-to-many relationship
 * 2. A unidirectional many-to-one relationship
 * 3. A unidirectional one-to-many relationship
 * 4. Two one-to-many relationships on the same two entities
 * 5. A many-to-many relationship
 * 6. A one-to-one relationship
 * 7. A unidirectional one-to-one relationship
 * <p>
 * Example for relationship OneToOne:
 * relationship OneToOne {
 * FirstEntity to SecondEntity
 * }
 * <p>
 * Тип связи устанавливается с помощью внутреннего перечисления RelationType
 */

public class Relation {

    /**
     * Сущность в которой объявлена связь
     */
    private Entity entity;

    /**
     *  Поле для которого создается отношение
     */
    private Field field;

    /**
     * Тип связи
     */
    private RelationType relationType;


    private String entityTo;

    /**
     * @param entity Сущность владелец отношения
     * @param field Поле для которого создается отношение
     * @param relationType Тип создоваемого отношения.
     */
    public Relation(Entity entity, Field field, RelationType relationType) {
        this.entity = entity;
        this.field = field;
        this.relationType = relationType;
        entityTo = getEntityType(field.getFieldType());
    }


    /**
     * Возвращает строковое представление Relation
     *
     * @return
     */
    @Override
    public String toString() {
        return "relationship " + this.relationType + " {\n" +
                entity.getClassName() + "{" + field.getFieldName() + "}"
                + " to " + entityTo
                + "\n}";
    }

    /**
     * Метод используется если поле field является списком.
     * Метод парсит тип поля и возвращает тип, содержащийся между угловыми скобками <>.
     *
     * @return fieldType
     */
    private String getEntityType(String fieldType) {
        if (fieldType!=null && fieldType.matches("[сС]писок.*")) {
            int start = fieldType.indexOf("<");
            int finish = fieldType.indexOf(">");
            fieldType = fieldType.substring(start + 1, finish);
        }
        return fieldType;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public RelationType getRelationType() {
        return relationType;
    }

    public void setRelationType(RelationType relationType) {
        this.relationType = relationType;
    }

    public String getEntityTo() {
        return entityTo;
    }

    public void setEntityTo(String entityTo) {
        this.entityTo = entityTo;
    }

    /**
     * Enum для типов связей (пока только один тип)
     */

    public enum RelationType {

        OneToMany("OneToMany"),
        OneToOne("OneToOne");

        /**
         * Поле хранит строковое представление перечисления
         */
        private String type;

        /**
         * Конструктор
         *
         * @param type of relation
         */
        RelationType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }
    }

}
