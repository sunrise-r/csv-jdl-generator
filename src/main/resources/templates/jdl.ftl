<#list model.jdlEntities as entity>
entity ${entity.entityName} {
<#list entity.fields as field>
  ${field.fieldName} ${field.fieldType} ${field.validation!''}
</#list>
}
</#list>

<#list model.groupedRelations as relationType, relations>
relationship ${relationType.toString()} {
    <#list relations as relation>
    ${relation.source.entity}<#if relation.source.field??>{${relation.source.field!''}}</#if> to ${relation.target.entity}<#if relation.target.field??>{${relation.target.field!''}}</#if>
    </#list>
}
</#list>