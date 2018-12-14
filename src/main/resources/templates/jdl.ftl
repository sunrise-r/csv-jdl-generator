<#list model.jdlEntities as entity>
entity ${entity.entityName} {
<#list entity.fields as field>
  ${field.fieldName} ${field.fieldType} ${field.validation!''}
</#list>
}
</#list>

<#list model.jdlRelations as relation>
relationship ${relation.relationType.toString()} {
  ${relation.source.entity}<#if relation.source.field??>{${relation.source.field!''}}</#if> to ${relation.target.entity}<#if relation.target.field??>{${relation.target.field!''}}</#if>
}
</#list>