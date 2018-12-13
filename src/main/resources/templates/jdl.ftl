<#list model.jdlEntities as entity>
  entity ${entity.entityName} {
  <#list entity.fields as field>
    ${field.fieldName} ${field.fieldType} ${field.validation!''}
  </#list>
  }
</#list>
