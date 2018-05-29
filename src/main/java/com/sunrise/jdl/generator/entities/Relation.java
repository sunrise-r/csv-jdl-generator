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
    private Entity firstField;

    /**
     * Вторая сущность
     */
    private Field secondfield;

    /**
     * Тип связи
     */
    private RelationType relationType;

    /**
     *
     * @param firstField
     * @param secondfield
     * @param relationType
     */
    public Relation(Entity firstField, Field secondfield, RelationType relationType) {
        this.firstField = firstField;
        this.secondfield = secondfield;
        this.relationType = relationType;
    }





    /**
     * Возвращает строковое представление Relation
     * @return
     */
    @Override
    public String toString() {
//        String fieldType = secondfield.getFieldType();
//        if (secondfield.getFieldType().contains("Список")) {
//           fieldType = parseFieldType();
//        }
        return "relationship "  + this.relationType + " {\n" +
                firstField.getClassName() + "{" + secondfield.getFieldName() + "}"
                + " to " + parseFieldType() +
                "{" + firstField.getClassName().toLowerCase() + "}"
                + "\n}";
    }

    /**
     * Метод используется если поле secondfield является списком.
     * Метод парсит тип поля и возвращает тип, содержащийся между угловыми скобками <>.
     * @return fieldType
     */
    private String parseFieldType() {
        String fieldType = secondfield.getFieldType();
        if (secondfield.getFieldType().contains("Список")){
            int start = fieldType.indexOf("<");
            int finish = fieldType.indexOf(">");
            fieldType = fieldType.substring(start + 1, finish);
        }
        return fieldType;
    }


    /**
     * Enum для типов связей (пока только один тип)
     */

   public enum RelationType {

        OneToMany("OneToMany"), OneToOne("OneToOne");

        /**
         * Поле хранит строковое представление перечисления
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
