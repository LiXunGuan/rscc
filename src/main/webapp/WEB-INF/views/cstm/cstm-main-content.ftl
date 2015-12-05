
<style>
	
	div.widget-body p{
		
		text-indent: 0em;
	}
	

</style>
<div class="tabs-left" style="">
	<ul class="nav nav-tabs tabs-left" id="demo-pill-nav" style="width: 120px;">
		<li class="active"><a href="#${poolUUID}-r1" data-toggle="tab"><!-- <span
				class="badge bg-color-blue txt-color-white">12</span>  -->客户池统计 </a></li>
		<li><a href="#${poolUUID}-r2" data-toggle="tab" onclick="getCstmsByPoolId('${poolUUID}');"><!-- <span
				class="badge bg-color-blueDark txt-color-white">3</span> --> 客户详细信息 </a>
		</li>
		<li><a href="#${poolUUID}-r3" data-toggle="tab" onclick="getEditCstmPool('${poolUUID}');"><!-- <span
				class="badge bg-color-blueDark txt-color-white">3</span> --> 编辑客户池 </a>
		</li>
		<!-- 调整竖型菜单的高度 -->
		<li style="height: 600px;overflow:hidden;"></li>
	</ul>
	<div class="tab-content" id="tab-content">
		<div class="tab-pane active in" id="${poolUUID}-r1">
			<p style="text-indent: 2em;">${(pool.poolDes)!'没有关于这个客户池的任何描述！'}</p>
			<fieldset>
				<div class="row" id="${poolUUID}_appendContents">
				
					<div class="col col-md-8" style="border-right:1px #E6E6E6 solid ;border-top:1px #E6E6E6 solid ;height: auto; text-indent: 2em;">
						<p>
							<!-- 人数统计: -->
						<span class="txt-color-green pull-right"></span>
						</p>
						<!-- 统计客户池信息 -->
						<div class="widget-body" style="width: 95%;">
							<#if reportDate??>
								<div id="non-continu-graph${poolUUID}" class="chart" style="margin-right: 8px;padding-right: 30px;"></div>
								<script type="text/javascript">
									$(document).ready(function() {
										var day_data = ${reportDate!'[{}]'};
										Morris.Line({
											element : 'non-continu-graph${poolUUID}',
											grid : true,
											data : day_data,
											xkey : 'date',
											ykeys : ['allCount'],
											labels : ["总人数"],
											units : "人",
// 											lineWidth: 3,
											hideHover: 'auto',
											resize: true,
											ymin : 1
										});
									});
								</script>
							<#else>
								<br/>
								<br/>
								暂时没有客户数据信息！
							</#if>
						</div>
						<!-- 统计客户池信息 结束 -->	
						
						<!-- 标签统计块 -->
						<div class="widget-body" style="width: 90%; padding-right: 8px;margin-left: 30px;" >
								<#if tagList??>
									<#list tagList as t>
										<p>标签${t_index+1}:<span class="txt-color-blue" style="font-size: 18px;"><b>${t.name}</b></span>
											<span class="txt-color-green pull-right">标记 <i><b>${t.count}</b></i> 次</span>
										</p>
										<div class="progress progress-sm">
											<div class="progress-bar bg-color-greenLight" role="progressbar" style="width: ${t.count?number*100/counts?number}%"></div>
										</div>
									</#list>
								</#if>
						</div>
						<!-- 标签统计块 结束 -->	
					</div>
					
					<div class="col col-md-4" style="border-top:1px #E6E6E6 solid ;">
						<#if logs??>
							<div class="smart-timeline" style="height: 648px; overflow: scroll;overflow-x:hidden">
								<ul class="smart-timeline-list">
									<#list logs as log>
										<li>
											<div class="smart-timeline-icon"><i class="fa fa-comments"></i></div>
											<div class="smart-timeline-time">
												<small><strong>${(log.optDate?datetime)!''}</strong></small>
											</div>
											<div class="smart-timeline-content" ><p><a href="javascript:void(0);">${(log.optUser)!''}：</a></p>
												<span style="word-wrap:break-word;"><label style="text-indent: 2em;">${(log.optAction)!''}</label></span>
											</div>
										</li>
									</#list>
								</ul>
							</div>
						<#else>
								<li>没有任何日志更新！</li>
						</#if>
					</div>
				</div>
			</fieldset>
		</div>
		
		<div class="tab-pane fade" id="${poolUUID}-r2"></div>
		<div class="tab-pane fade" id="${poolUUID}-r3">
		</div>
	</div>
</div>

<script src="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-progressbar/bootstrap-progressbar.min.js"></script>