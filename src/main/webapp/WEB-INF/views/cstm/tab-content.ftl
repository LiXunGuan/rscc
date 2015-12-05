<div id="$("#hiddenPoolId").val()" name="tab-contents" class="widget-body" style="height: 750px ; padding-top: 20px;width: 100%; ">
	<div class="tabs-left" style="">
		<ul class="nav nav-tabs tabs-left" id="demo-pill-nav" style="width: 120px;">
			<li class="active"><a href="#${poolid}-r1" data-toggle="tab"><!-- <span class="badge bg-color-blue txt-color-white">12</span>  -->客户池统计 </a></li>
			<li><a href="#${poolid}-r2" data-toggle="tab" onclick="getCstmsByPoolId('${poolid}');"><!-- <span class="badge bg-color-blueDark txt-color-white">3</span> --> 客户详细信息 </a>
			</li>
			<li style="height: 648px;overflow:hidden;"></li>
		</ul>
		<div class="tab-content">
			<div class="tab-pane active" id="${poolid}-r1">
				<p style="text-indent: 2em;">${pool_des}</p>
			</div>
			<div class="tab-pane" id="${poolid}-r2"></div>
		</div>
	</div>
</div>