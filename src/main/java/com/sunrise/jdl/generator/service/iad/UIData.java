package com.sunrise.jdl.generator.service.iad;

import com.sunrise.jdl.generator.ui.ProjectionInfo;
import com.sunrise.jdl.generator.ui.RegistryItem;

import java.util.List;

class UIData {
    private RegistryItem rootRegistryItem;
    private List<RegistryItem> registryItems;
    private List<ProjectionInfo> projectionInfos;

    public RegistryItem getRootRegistryItem() {
        return rootRegistryItem;
    }

    public void setRootRegistryItem(RegistryItem rootRegistryItem) {
        this.rootRegistryItem = rootRegistryItem;
    }

    public List<RegistryItem> getRegistryItems() {
        return registryItems;
    }

    public void setRegistryItems(List<RegistryItem> registryItems) {
        this.registryItems = registryItems;
    }

    public List<ProjectionInfo> getProjectionInfos() {
        return projectionInfos;
    }

    public void setProjectionInfos(List<ProjectionInfo> projectionInfos) {
        this.projectionInfos = projectionInfos;
    }
}
