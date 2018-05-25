package com.sunrise.jdl.generator.entities;

/**
 * Класс создания связей.
 *
 * Types of supported in JDL relationships:
 * 1. A bidirectional one-to-many relationship
 * 2. A unidirectional many-to-one relationship
 * 3. A unidirectional one-to-many relationship
 * 4. Two one-to-many relationships on the same two entities
 * 5. A many-to-many relationship
 * 6. A one-to-one relationship
 * 7. A unidirectional one-to-one relationship
 *
 * Example for relationship OneToOne:
 * relationship OneToOne {
 * 	FirstEntity to SecondEntity
 * }
 *
 * Тип связи устанавливается с помощью внутреннего перечисления RelationType
 */

public class Relation {

    /**
     * Первая сущность
     */
    private String firstEntityName;

    /**
     * Вторая сущность
     */
    private String secondEntityName;

    /**
     * Тип связи
     */
    private RelationType relationType;

    public Relation(String firstEntity, String secondEntity, RelationType relationType) {
        this.firstEntityName = firstEntity;
        this.secondEntityName = secondEntity;
        this.relationType = relationType;
    }

    @Override
    public String toString() {
        return "relationship "  + this.relationType + " {\n" +
                firstEntityName + " to " + secondEntityName + "\n}";
    }

    /**
     * enum для типов связей (пока только один тип)
     */

   public enum RelationType {

        OneToMany("OneToMany");

        /**
         * поле хранит строковое представление перечисления
         */
        private String type;

        /**
         * Конструктор
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
