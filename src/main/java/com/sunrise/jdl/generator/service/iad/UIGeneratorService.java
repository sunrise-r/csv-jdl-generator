package com.sunrise.jdl.generator.service.iad;

import com.sunrise.jdl.generator.actions.Action;
import com.sunrise.jdl.generator.entities.Field;
import com.sunrise.jdl.generator.forJson.BaseField;
import com.sunrise.jdl.generator.forJson.ProjectionInfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        ProjectionInfo projectionInfo = new ProjectionInfo();
        projectionInfo.setCode(entry.getKey());
        projectionInfo.setName(entry.getKey());
        projectionInfo.setListFields(entry.getValue().stream().map(f->new BaseField(f)).collect(Collectors.toList()));
        projectionInfo.setActions(new ArrayList<>(actions));
        return projectionInfo;
    }


    /**
     * Преобразует данные о сущностях в список их проекций
     *
     * @param entityInfoes Информация о сущностях
     * @param actions      Список доступных actions для сущностей
     * @return Список проекций созданный на сонове @entitiesInfoes
     */
    public List<ProjectionInfo> toProjectionInfo(Map<String, Set<Field>> entityInfoes, Collection<Action> actions) {
        return entityInfoes.entrySet().stream().map(x -> this.toProjectionInfo(x, actions)).collect(Collectors.toList());
    }

    /**
     * Очистить целевую диркторию  генерации файлов описания интерфейса
     *
     * @param targetDirectory Целевая директория
     * @throws IOException Возникает в случае если невозможно очистить директорию
     */
    public void cleanupTargetDirecotry(Path targetDirectory) throws IOException {
        if (Files.exists(targetDirectory)) {
            try {
                Files.walk(targetDirectory)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            } catch (IOException e) {
                throw new IOException("Ошибка при очистке целевой директории: " + Arrays.toString(e.getStackTrace()));
            }
        }
    }

}
