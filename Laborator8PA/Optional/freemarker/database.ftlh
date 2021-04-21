<#list Data as data>
    <br>
    <#list data?values as items>
        <#list items as item>
            <p>${item}</p>
        </#list>
    </#list>
</#list>