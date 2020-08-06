<#-- assign指令 : 再ftl模板中定义数据存入到root节点下
<#assign name="zhangsan">
-->

<#--
${name}

欢迎您：${username}
-->
<#-- 获取数据  ${根节点下的数据....属性} -->

<#-- if指令
<#if flag=1>
    传入数据=1
    <#elseif flag=2>
    传入数据=2
    <#else>
    传入数据=其他
</#if>
 -->

<#-- list指令 : 循环迭代
    数据名称 as 别名

<#list weeks as abc>
    ${abc_index} =  ${abc}
</#list>
-->

<#--模板包含 include
<#include "template02.ftl" >
-->

${username?lower_case}