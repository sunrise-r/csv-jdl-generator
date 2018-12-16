package com.sunrise.jdl.generator.service.i18n;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * "home": {
 * "title": "Employees",
 * "createLabel": "Создать новый Employee",
 * "createOrEditLabel": "Создать или отредактировать Employee"
 * },
 * "created": "Новый Employee создан с идентификатором {{ param }}",
 * "updated": "Employee обновлен с идентификатором {{ param }}",
 * "deleted": "Employee удален с идентификатором {{ param }}",
 * "delete": {
 * "question": "Вы уверены что хотите удалить Employee {{ id }}?"
 * },
 * "detail": {
 * "title": "Employee"
 * },
 */
public class I18nModel {

    private String entityName;

    private String appName;

    private Home home;

    private String created;

    private String updated;

    private String deleted;

    private Delete delete;

    private Detail detail;

    private Map<String, String> fields = Maps.newHashMap();

    public static class Home {
        private String title;
        private String createLabel;
        private String createOrEditLabel;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCreateLabel() {
            return createLabel;
        }

        public void setCreateLabel(String createLabel) {
            this.createLabel = createLabel;
        }

        public String getCreateOrEditLabel() {
            return createOrEditLabel;
        }

        public void setCreateOrEditLabel(String createOrEditLabel) {
            this.createOrEditLabel = createOrEditLabel;
        }
    }

    public static class Delete {
        private String question;

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }
    }

    public static class Detail {
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public Map<String, String> getFields() {
        return fields;
    }

    public void setFields(Map<String, String> fields) {
        this.fields = fields;
    }

    public Delete getDelete() {
        return delete;
    }

    public void setDelete(Delete delete) {
        this.delete = delete;
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
}
