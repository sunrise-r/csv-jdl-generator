package com.sunrise.jdl.generator.forJson;

import com.sunrise.jdl.generator.actions.Action;

import java.util.ArrayList;
import java.util.List;

public class BaseData {

    private String name;
    private String code;
    private String parentCode;
    private List<BaseField> listFields;
    private List<Action> actions;

    public BaseData(String name) {
        this.name = name;
        this.listFields = new ArrayList<>();
        this.actions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public List<BaseField> getListFields() {
        return listFields;
    }

    public void setListFields(List<BaseField> listFields) {
        this.listFields = listFields;
    }
}
