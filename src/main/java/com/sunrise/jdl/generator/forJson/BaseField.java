package com.sunrise.jdl.generator.forJson;

import com.sunrise.jdl.generator.entities.Field;

/**
 * Класс используется для корректной записи полей в json-файл объектов BaseData
 */
public class BaseField {

    /**
     * Имя
     */
    private String name;

    /**
     * Код
     */
    private String code;

    /**
     * Возможность произвести сортировку по полю
     */
    private boolean sorting = true;

    /**
     * Возможность произвести поиск по полю
     */
    private boolean searching = true;

    /**
     * Тип отображения
     */
    private String displayFormat;

    /**
     * Пустой конструктор необходим для десериализации (используется в тесте)
     */
    public BaseField() {

    }

    public BaseField(Field field) {
        this.name = field.getFieldLabel();
        this.code = field.getFieldName();
        this.displayFormat = displayFormat;
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

    public boolean isSorting() {
        return sorting;
    }

    public void setSorting(boolean sorting) {
        this.sorting = sorting;
    }

    public boolean isSearching() {
        return searching;
    }

    public void setSearching(boolean searching) {
        this.searching = searching;
    }

    public String getDisplayFormat() {
        return displayFormat;
    }

    public void setDisplayFormat(String displayFormat) {
        this.displayFormat = displayFormat;
    }

    @Override
    public String toString() {
        return "BaseField{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", sorting=" + sorting +
                ", searching=" + searching +
                ", displayFormat='" + displayFormat + '\'' +
                '}';
    }
}
