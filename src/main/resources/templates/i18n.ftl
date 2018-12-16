{
    "${model.appName}App": {
        "${model.entityName}" : {
            "home": {
                "title": "${model.home.title}",
                "createLabel": "${model.home.createLabel}",
                "createOrEditLabel": "${model.home.createOrEditLabel}"
            },
            "created": "${model.created}",
            "updated": "${model.updated}",
            "deleted": "${model.deleted}",
            "delete": {
                "question": "${model.delete.question}?"
            },
            "detail": {
                "title": "${model.detail.title}"
            },
            <#list model.fields as name, value>
            "${name}": "${value}"<#if name?has_next>,</#if>
            </#list>
        }
    }
}
