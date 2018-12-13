<#list model.jdlEntities as entity>
  entity ${entity.name} {
  <#list entity.fields as field>
    ${field.name} ${field.type} ${field.validation}
    <field name> <type> [<validation>*]
  </#list>
  }
</#list>
