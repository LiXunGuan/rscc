
<style>
	
	.panel{
		margin-bottom: 0px;
	}

	.panel-footer.text-align-center{
		padding: 0px;
	}
	
	.panel-footer.text-align-center label{
		font-size: 20px;
		font-weight: 700;
		margin-right: 10px;
	}
	
	.panel-footer.text-align-center span{
		font-size: 30px;
	}
	
	.panel-heading{
		padding: 0px;
	}
	
	.panel-heading label{
		font-size: 20px;
		font-weight: 700;
		margin-right: 10px;
	}
	
	.panel-heading span{
		font-size: 30px;
	}
	
</style>

<ul class="notification-body">
	<li>
		<div class="panel panel-primary pricing-big">
			<div class="panel-heading">
				<label>在线时长:</label>
				<span id="bindertime"></span>
			</div>
		</div>
	</li>
	<li>
		<div class="panel panel-orange pricing-big">
			<div class="panel-heading">
				<label>置忙时长:</label>
				<span id="busytime"></span>
			</div>
		</div>
	</li>
	<li>
		<div class="panel panel-red pricing-big">
			<div class="panel-heading">
				<label>漏接次数:</label>
				<span id="miss_count"></span>
			</div>
		</div>
	</li>
	
	<li>
		<div class="panel panel-magenta pricing-big">
			<div class="panel-heading">
				<label>接起速率:</label>
				<span id="ringingtime"></span>
			</div>
		</div>
	</li>
	<li>
		<div class="panel panel-greenLight pricing-big">
			<div class="panel-heading">
				<label>呼入接通次数:</label>
				<span id="incount"></span>
			</div>
		</div>
	</li>
	<li>
		<div class="panel panel-greenLight pricing-big">
			<div class="panel-heading">
				<label>呼入时长:</label>
				<span id="intime"></span>
			</div>
		</div>
	</li>
	
	<li>
		<div class="panel panel-greenDark pricing-big">
			<div class="panel-heading">
				<label>呼出次数:</label>
				<span id="outcount"></span>
			</div>
		</div>
	</li>
	
	<li>
		<div class="panel panel-greenDark pricing-big">
			<div class="panel-heading">
				<label>呼出时长:</label>
				<span id="outtime"></span>
			</div>
		</div>
	</li>
	
	
</ul>

<script type="text/javascript">

	$(document).ready(function() {
		timeFormatConversion();
		getAgentStatus();
		clearInterval(curInterval);
		
		$("ul li div div.panel-heading label").css("width","50%").css("text-align","right");
		$("ul li div div.panel-heading span").css("width","50%").css("text-align","left");
		
		
	});
	
	function getAgentStatus() {
		if($(".ajax-dropdown").is(':visible') == false){
			clearInterval();
			return false;
		}
		var url = getContext() + "daily";
		$.post(url,function(data) {
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
// 				$("#miss_count").text("无数据！");
// 				$("#incount").text("无数据！");
// 				$("#intime").text("无数据！");
// 				$("#outcount").text("无数据！");
// 				$("#outtime").text("无数据！");
// 				$("#bindertime").text("无数据！");
// 				$("#busytime").text("无数据！");
// 				$("#ringingtime").text("无数据！");
// 			}
// 			else if(data.ret == undefined){
// 				$("#miss_count").text("0");
// 				$("#incount").text("0");
// 				$("#intime").text("0");
// 				$("#outcount").text("0");
// 				$("#outtime").text("0");
// 				$("#bindertime").text("0");
// 				$("#busytime").text("0");
// 				$("#ringingtime").text("0");
			}else {
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
	
	var curInterval = setInterval("getAgentStatus();", 5000);;
	
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

