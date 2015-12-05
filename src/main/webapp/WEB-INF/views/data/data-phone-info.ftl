
<table border="1px solid gray" style="width: 100%; text-align: cetnter;margin-top: 5px;" class="table table-striped table-bordered table-hover">
	<#if mapPhone?exists>
		<tr>
			<td style="font-weight: bold;">参数名</td>
	   		<td style="font-weight: bold;">参数值</td>
		</tr>
	    <#list mapPhone?keys as key> 
	    	<tr>
	    		<td>${key}</td>
	    		<td>${mapPhone[key]}</td>
	    	</tr>
	    </#list>
	<#else>
		<tr>
			<td>参数名</td>
	   		<td>参数值</td>
		</tr>
		<tr>
			<td colspan="2">没有任何数据信息！</td>
		</tr>
	</#if>
</table>