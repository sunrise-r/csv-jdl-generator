package com.sunrise.jdl.generator.entities;

public class Relation {

    /**
     * Константное поле добавляется в строковое представление Relation
     * для отображения Relation в формате jdl
     */
    private String RELATIONSHIP = "relationship";
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
        return RELATIONSHIP + " " + this.relationType + " {" +
                firstEntityName + " to " + secondEntityName + "\n}";
    }

    /**
     * Перечисление для типов связей
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
