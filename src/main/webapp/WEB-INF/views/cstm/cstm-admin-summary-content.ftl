<div class="col col-md-8" style="border-right:1px #E6E6E6 solid ;border-top:1px #E6E6E6 solid ;height: 645px; text-indent: 2em;">
<div class="widget-body no-padding">
	<div id="non-continu-graph${(poolUUID)!''}" class="chart no-padding"></div>
</div>
	<script type="text/javascript">
		$(document).ready(function() {
			var day_data = [{
				"period" : "2012-10-01",
				"licensed" : 3407
			}, {
				"period" : "2012-09-18",
				"licensed" : 3248,
				"other" : 1000
			}, {
				"period" : "2012-09-17",
				"sorned" : 0
			}, {
				"period" : "2012-09-16",
				"sorned" : 0
			}, {
				"period" : "2012-09-15",
				"licensed" : 3201,
				"sorned" : 656
			}, {
				"period" : "2012-09-10",
				"licensed" : 3215
			}];
			Morris.Line({
				element : 'non-continu-graph${(poolUUID)!""}',
				data : day_data,
				xkey : 'period',
				ykeys : ['licensed', 'sorned', 'other'],
				labels : ['Licensed', 'SORN', 'Other'],
				/* custom label formatting with `xLabelFormat` */
				xLabelFormat : function(d) {
					return (d.getMonth() + 1) + '/' + d.getDate() + '/' + d.getFullYear();
				},
				/* setting `xLabels` is recommended when using xLabelFormat */
				xLabels : 'day'
			});
			
		});
	</script>

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


<script type="text/javascript">

</script>