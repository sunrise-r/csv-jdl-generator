package com.sunrise.jdl.generator.entities;

public class ModuleInfo extends BasicEntity {

    private String moduleName;

    public ModuleInfo(String className, String entityLabel, String title, String moduleName) {
        this.className = className;
        this.title = title;
        this.moduleName = moduleName;
        this.label = entityLabel;
    }

    public ModuleInfo(Entity e, String moduleName) {
        className = e.className;
        title = e.title;
        label = e.label;
        this.moduleName = moduleName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getClassName() {
        return className;
    }

    public String getLabel() {
        return label;
    }

    public String getTitle() {
        return title;
    }
}
