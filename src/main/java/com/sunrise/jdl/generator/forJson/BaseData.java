package com.sunrise.jdl.generator.forJson;

import com.sunrise.jdl.generator.actions.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс используется для записи данных в json-фррмате в файл
 */
public class BaseData {

    /**
     * Имя
     */
    private String name;

    /**
     * Код
     */
    private String code;

    /**
     * Код родителя
     */
    private String parentCode;

    /**
     * Список полей
     */
    private List<BaseField> listFields;

    /**
     * Список действий которые можно производить с объектом
     */
    private List<Action> actions;

    /**
     * Пустой конструкто используется для десериализации (используется в тесте)
     */
    public BaseData() {
    }

    /**
     * @param code
     */
    public BaseData(String code) {
        this.code = code;
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

    @Override
    public String toString() {
        return "BaseData{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", parentCode='" + parentCode + '\'' +
                ", listFields=" + listFields +
                ", actions=" + actions +
                '}';
    }
}
