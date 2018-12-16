package com.sunrise.jdl.generator.service.jdl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sunrise.jdl.generator.entities.RawData;

import java.util.List;
import java.util.Map;

public class JdlData {

    private List<JdlEntity> jdlEntities = Lists.newArrayList();

    private List<JdlRelation> jdlRelations = Lists.newArrayList();

    private Map<RelationType, List<JdlRelation>> groupedRelations = Maps.newHashMap();

    private List<RawData> rawDataList = Lists.newArrayList();

    private Map<String, List<RawData>> groupedByEntityName = Maps.newHashMap();

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

    public List<RawData> getRawDataList() {
        return rawDataList;
    }

    public void setRawDataList(List<RawData> rawDataList) {
        this.rawDataList = rawDataList;
    }

    public Map<String, List<RawData>> getGroupedByEntityName() {
        return groupedByEntityName;
    }

    public void setGroupedByEntityName(Map<String, List<RawData>> groupedByEntityName) {
        this.groupedByEntityName = groupedByEntityName;
    }
}
