package com.sunrise.jdl.generator.entities;

public class ModuleInfo extends BasicEntity {

    private String moduleName;

    public ModuleInfo(String className, String moduleName) {
        this.className = className;
        this.moduleName = moduleName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getClassName() {
        return className;
    }
}
