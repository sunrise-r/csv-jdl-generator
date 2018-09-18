package com.sunrise.jdl.generator.service;


/**
 * Сервис генерации SQL запроса для заполнения данных для двухуровнего меню.
 */
public class MenuEntryGenerationService {

    private static final String INSERT_TEMPLATE = "(`%s`,`%s`,`%s`,`%s`,`%s`) VALUES (%s, %s, %s, %s, %s);";

    private final TableData data;
    private final String currentTemplate;

    public MenuEntryGenerationService() {
        this.data = new TableData("menu_item", "code", "url", "parent", "label", "role");
        currentTemplate = generateTemplate(INSERT_TEMPLATE, data);
    }

    private String generateTemplate(String insertTemplate, TableData data) {
        return String.format(insertTemplate, data.parentColumnName, data.urlColumnName, data.codeColumnName, data.labelColumnName, data.roleColumnName, "%s", "%s", "%s", "%s", "%s");
    }

    public String getCurrentTemplate() {
        return currentTemplate;
    }

    public class TableData {

        /**
         * Название таблицы с меню
         */
        private final String tableName;

        /**
         * Название колонки с кодом меню
         */
        private final String codeColumnName;

        /**
         * Название колонки в которой храниться ссылка на страницу на которую происходить переход но клику на меню
         */
        private final String urlColumnName;

        /**
         * Название колонки в котрой храниться ссылка на родительское меню
         */
        private final String parentColumnName;

        /**
         * Название колонки в которой храниться название меню
         */
        private final String labelColumnName;

        /**
         * Название колонки в которой храниться роль пользователя, которому доступно данное меню.
         */
        private final String roleColumnName;

        public TableData(String tableName, String codeColumnName, String urlColumnName, String parentColumnName, String labelColumnName, String roleColumnName) {
            this.tableName = tableName;
            this.codeColumnName = codeColumnName;
            this.urlColumnName = urlColumnName;
            this.parentColumnName = parentColumnName;
            this.labelColumnName = labelColumnName;
            this.roleColumnName = roleColumnName;
        }

        public String getTableName() {
            return tableName;
        }

        public String getCodeColumnName() {
            return codeColumnName;
        }

        public String getUrlColumnName() {
            return urlColumnName;
        }

        public String getParentColumnName() {
            return parentColumnName;
        }

        public String getLabelColumnName() {
            return labelColumnName;
        }

        public String getRoleColumnName() {
            return roleColumnName;
        }
    }
}
