package com.sunrise.jdl.generator.service.iad;

import com.sunrise.jdl.generator.actions.Action;
import com.sunrise.jdl.generator.entities.Field;
import com.sunrise.jdl.generator.forJson.BaseField;
import com.sunrise.jdl.generator.forJson.ProjectionInfo;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Сервис генерации данных визуального интерфейса
 */
public class UIGeneratorService {

    /**
     * Преобразовать данные о сщуности в информацию о проекции
     *
     * @param entry   Информация о сущности. Название и поле.
     * @param actions Список доступных действий
     * @return Информация о проекции
     */
    public ProjectionInfo toProjectionInfo(Map.Entry<String, Set<Field>> entry, Collection<Action> actions) {
        ProjectionInfo projectionInfo = new ProjectionInfo(entry.getKey());
        for (Field field : entry.getValue()) {
            projectionInfo.getListFields().add(new BaseField(field));
        }
        projectionInfo.setActions(new ArrayList<>(actions));
        return projectionInfo;
    }


    public List<ProjectionInfo> toProjectionInfo(Map<String, Set<Field>> entityInfoes, Collection<Action> actions){
        return entityInfoes.entrySet().stream().map(x->this.toProjectionInfo(x,actions)).collect(Collectors.toList());
    }

}
