package com.sunrise.jdl.generator.service.jdl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class JdlData {

    private List<JdlEntity> jdlEntities = Lists.newArrayList();

    private List<JdlRelation> jdlRelations = Lists.newArrayList();

    private Map<RelationType, List<JdlRelation>> groupedRelations = Maps.newHashMap();

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

    public Map<RelationType, List<JdlRelation>> getGroupedRelations() {
        return groupedRelations;
    }

    public void setGroupedRelations(Map<RelationType, List<JdlRelation>> groupedRelations) {
        this.groupedRelations = groupedRelations;
    }
}
