package com.sunrise.jdl.generator.actions;

/**
 * Класс используется для сохранение возможных действий при парсинге csv-файла
 *
 */

public class Action {

    /**
     * Тип отображения - принимает значение tableMenu
     */
    private String displayType;

    /**
     * Поле определеяет группу, к которой принадлжеит действие
     * (варианты: edit, connect, others, info)
     */
    private String group;

    /**
     * Стиль - определяет вызуальное представление действия
     *
     */
    private String style;

    /**
     * Код соответствует стилю
     */
    private String code;

    /**
     * Пусто конструктор необходим для десериализации (используется в тесте)
     */
    public Action() {

    }
    /**
     * Конструктор
     * @param displayType
     * @param group
     * @param style
     * @param code
     */
    public Action(String displayType, String group, String style, String code) {
        this.displayType = displayType;
        this.group = group;
        this.style = style;
        this.code = code;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Action{" +
                "displayType='" + displayType + '\'' +
                ", group='" + group + '\'' +
                ", style='" + style + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
