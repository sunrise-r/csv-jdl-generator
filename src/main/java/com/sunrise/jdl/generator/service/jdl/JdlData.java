package com.sunrise.jdl.generator.service.jdl;

import com.google.common.collect.Lists;

import java.util.List;

public class JdlData {

    private List<JdlEntity> jdlEntities = Lists.newArrayList();

    private List<JdlRelation> jdlRelations = Lists.newArrayList();

    public List<JdlEntity> getJdlEntities() {
        return jdlEntities;
    }

    public void setJdlEntities(List<JdlEntity> jdlEntities) {
        this.jdlEntities = jdlEntities;
    }

    public List<JdlRelation> getJdlRelations() {
        return jdlRelations;
    }

    public void setJdlRelations(List<JdlRelation> jdlRelations) {
        this.jdlRelations = jdlRelations;
    }
}
