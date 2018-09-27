package com.sunrise.jdl.generator.ui;

import com.sunrise.jdl.generator.actions.Action;

import java.util.List;

/**
 * Класс используется для записи данных в json-фррмате в файл
 */
public class ProjectionInfo extends RegistryItem{


    /**
     * Список полей
     */
    private List<BaseField> listFields;

    /**
     * Список действий которые можно производить с объектом
     */
    private List<Action> actions;


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
