<#if (c.usering_iframe==true)>

<!DOCTYPE html>
<html lang="en-us">
<head>
<#if beExists??>
<#else>
	<#include "index/importcss.ftl">
</#if>

<style>
.modal-backdrop {
	background-color:rgba(0, 0, 0, 0);
}
</style>
</head>
<body class="">

<script type="application/javascript">
    function getContext() {
        return "${springMacroRequestContext.contextPath}/";
    }
</script>
<div id="forModel"></div>

	<#if beExists??>
    <#else>
    	<#include "index/importsjs.ftl">
    </#if>
   	
   	<#include iframecontent+'.ftl'>

</body>
</html>

<#else >
    <#include iframecontent+'.ftl'>
</#if>


