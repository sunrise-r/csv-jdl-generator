package com.sunrise.jdl.generator.service.jdl;

import com.google.common.base.Objects;

public class JdlRelation {

    private EntityRelationDescription source;
    private EntityRelationDescription target;
    private RelationType relationType;

    public EntityRelationDescription getSource() {
        return source;
    }

    public void setSource(EntityRelationDescription source) {
        this.source = source;
    }

    public EntityRelationDescription getTarget() {
        return target;
    }

    public void setTarget(EntityRelationDescription target) {
        this.target = target;
    }

    public RelationType getRelationType() {
        return relationType;
    }

    public void setRelationType(RelationType relationType) {
        this.relationType = relationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JdlRelation that = (JdlRelation) o;
        return Objects.equal(source, that.source) &&
                Objects.equal(target, that.target) &&
                relationType == that.relationType;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(source, target, relationType);
    }

    public enum RelationType {
        ONE_TO_ONE("OneToOne"),
        ONE_TO_MANY("OneToMany"),
        MANY_TO_ONE("ManyToOne"),
        MANY_TO_MANY("ManyToMany");

        private String typeName;

        RelationType(String typeName) {
            this.typeName = name();
        }

        @Override
        public String toString() {
            return this.typeName;
        }
    }

    public static class EntityRelationDescription {

        private String entity;

        private String field;

        public String getEntity() {
            return entity;
        }

        public void setEntity(String entity) {
            this.entity = entity;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }
    }


}

