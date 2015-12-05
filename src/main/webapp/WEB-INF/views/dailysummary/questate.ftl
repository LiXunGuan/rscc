
<style>

	
</style>

<ul class="notification-body">
	<#if allQueue??>
		<#list allQueue as q>
			<li style="padding-left: 20px;font-size: large;">
				<i class="fa fa-lg fa-caret-up"></i>&nbsp;&nbsp;<strong>${(queueNameMap[q])!''}</strong>(2/5)
			</li>
		</#list>
	</#if>
</ul>

<script type="text/javascript">


</script>

