
<section id="widget-grid-time">
	<div class="row">
		<div class="col-sm-12">
			<div class="well well-light">
				<div class="row">
					<div class="col-xs-12 col-sm-6 col-md-3">
						<div class="panel panel-primary pricing-big ">
							<div class="panel-heading ">
								<h3 class="panel-title" style="color: white;">在线时长</h3>
							</div>
							<div class="panel-footer text-align-center">
								<div>
									<span id="bindertime" style="font-size: 34px;"></span>
								</div>
							</div>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-md-3">
						<div class="panel panel-red pricing-big">
							<div class="panel-heading ">
								<h1 class="panel-title">漏接次数</h1>
							</div>
							<div class="panel-footer text-align-center">
								<div>
									<span id="miss_count" style="font-size: 34px;"></span>
								</div>
							</div>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-md-3">
						<div class="panel panel-greenLight pricing-big">
							<div class="panel-heading">
								<h3 class="panel-title">接通次数</h3>
							</div>
							<div class="panel-footer text-align-center">
								<div>
									<span id="incount" style="font-size: 34px;"></span>
								</div>
							</div>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-md-3">
						<div class="panel panel-greenDark pricing-big">
							<div class="panel-heading">
								<h3 class="panel-title">呼出次数</h3>
							</div>
							<div class="panel-footer text-align-center">
								<div>
									<span id="outcount" style="font-size: 34px;"></span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12 col-sm-6 col-md-3">
						<div class="panel panel-orange pricing-big">
							<div class="panel-heading bg-color-orange">
								<h3 class="panel-title">置忙时长</h3>
							</div>
							<div class="panel-footer text-align-center">
								<div>
									<span id="busytime" style="font-size: 34px;"></span>
								</div>
							</div>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-md-3 ">
						<div class="panel panel-magenta pricing-big">
							<div class="panel-heading ">
								<h3 class="panel-title">接起速率</h3>
							</div>
							<div class="panel-footer text-align-center">
								<div>
									<span id="ringingtime" style="font-size: 34px;"></span>
								</div>
							</div>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-md-3">
						<div class="panel panel-greenLight pricing-big">
							<div class="panel-heading">
								<h3 class="panel-title">呼入时长</h3>
							</div>
							<div class="panel-footer text-align-center">
								<div>
									<span id="intime" style="font-size: 34px;"></span>
								</div>
							</div>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-md-3">
						<div class="panel panel-greenDark pricing-big">
							<div class="panel-heading">
								<h3 class="panel-title">呼出时长</h3>
							</div>
							<div class="panel-footer text-align-center">
								<div>
									<span id="outtime" style="font-size: 34px;"></span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>


<script type="text/javascript">

	$(document).ready(function() {
		timeFormatConversion();
		getAgentStatus();
		clearInterval(curInterval);
	});
	
	function getAgentStatus() {
		var url = getContext() + "daily";
		$.post(url, function(data) {
			if (data.in_time === undefined || !data.bindertime > 0) {
				$("#miss_count").text("0");
				$("#incount").text("0");
				$("#intime").text("0");
				$("#outcount").text("0");
				$("#outtime").text("0");
				$("#bindertime").text("0");
				$("#busytime").text("0");
				$("#ringingtime").text("0");
// 			if (data.ret == "false") {
// 				$("#misscount").text("无数据！");
// 				$("#incount").text("无数据！");
// 				$("#intime").text("无数据！");
// 				$("#outcount").text("无数据！");
// 				$("#outtime").text("无数据！");
// 				$("#bindertime").text("无数据！");
// 				$("#busytime").text("无数据！");
// 				$("#ringingtime").text("无数据！");
			} else {
				$("#miss_count").text(data.miss_count);
				$("#incount").text(data.in_count);
				$("#intime").text(
						timeFormatConversion(Math.round(data.in_time / 1000
								+ (data.in_time % 1000 != 0 ? 1 : 0))));
				$("#outcount").text(data.out_count);
				$("#outtime").text(
						timeFormatConversion(Math.round(data.out_time / 1000
								+ (data.out_time % 1000 != 0 ? 1 : 0))));
				var now = new Date();
				// 			var lb=0;
				// 			if(data.last_binder_date) {
				//                lb  = now.getTime() - new Date(data.last_binder_date).getTime();
				//             }
				// 			var lbd=0;
				// 			if(data.last_busy_date){
				//                 lbd = now.getTime() -new Date(data.last_busy_date).getTime();
				//             }
				// 			$("#bindertime").text(timeFormatConversion(Math.round((data.binder_time+lb)/1000)));
				// 	    	$("#busytime").text(timeFormatConversion(Math.round((data.busy_time+lbd)/1000)));			
				$("#bindertime").text(
						timeFormatConversion(Math.round(data.bindertime)));
				$("#busytime").text(
						timeFormatConversion(Math.round(data.busytime)));
				$("#ringingtime").text(
						data.in_count != 0 ? (Math.round(data.ringing_time
								/ data.in_count / 1000)) : 0);
			}
		}, 'json');
	}
	
	var curInterval = setInterval("getAgentStatus();", 5000);
	/* 数字转时间 */
	function c(arg) {
		return arg >= 10 ? arg : "0" + arg;
	}
	function timeFormatConversion(num) {
		var str;
		var now = new Date();
		now.setHours(0);
		now.setMinutes(0);
		now.setSeconds(now.getSeconds(now.setSeconds(num)));
		str = c(now.getHours()) + ":" + c(now.getMinutes()) + ":"
				+ c(now.getSeconds());
		return str;
	}
</script>

