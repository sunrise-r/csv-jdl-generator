package com.sunrise.jdl.generator.service.jdl;

public enum RelationType {
    ONE_TO_ONE("OneToOne"),
    ONE_TO_MANY("OneToMany"),
    MANY_TO_ONE("ManyToOne"),
    MANY_TO_MANY("ManyToMany");

    private String typeName;

    RelationType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return this.typeName;
    }
}
