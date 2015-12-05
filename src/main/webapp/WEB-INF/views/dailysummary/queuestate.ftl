
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
		font-size: 25px;
	}
	
</style>

<ul class="notification-body">
	<li>
		<div class="panel panel-primary pricing-big">
			<div class="panel-heading">
				<label>接起率:</label>
				<span id="pick_up_rate"></span>
			</div>
		</div>
	</li>
	<li>
		<div class="panel panel-orange pricing-big">
			<div class="panel-heading">
				<label>未通话（闲）:</label>
				<span id="idle_ready_count"></span>
			</div>
		</div>
	</li>
	<li>
		<div class="panel panel-magenta pricing-big">
			<div class="panel-heading">
				<label>未通话（忙）:</label>
				<span id="busy_ready_count"></span>
			</div>
		</div>
	</li>
	<li>
		<div class="panel panel-greenLight pricing-big">
			<div class="panel-heading">
				<label>排队人数:</label>
				<span id="member_count"></span>
			</div>
		</div>
	</li>
	<li>
		<div class="panel panel-greenDark pricing-big">
			<div class="panel-heading">
				<label>总次数:</label>
				<span id="in_count"></span>
			</div>
		</div>
	</li>
	<li>
		<div class="panel panel-red pricing-big">
			<div class="panel-heading">
				<label>不在线:</label>
				<span id="offline_count"></span>
			</div>
		</div>
	</li>
	<li>
		<div class="panel panel-greenLight pricing-big">
			<div class="panel-heading">
				<label>通话中:</label>
				<span id="not_ready_count"></span>
			</div>
		</div>
	</li>
	<li>
		<div class="panel panel-greenDark pricing-big">
			<div class="panel-heading">
				<label>接通次数:</label>
				<span id="in_answer_count"></span>
			</div>
		</div>
	</li>
	<li>
		<div class="panel panel-magenta pricing-big">
			<div class="panel-heading">
				<label>响铃时间:</label>
				<span id="ringring_time"></span>
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
		var url = getContext() + "queue";
		$.post(url, {queueId:${queueId}}, function(data) {
			if (data.ret == "false") {
				$("#queue_name").text("无数据！");
				$("#idle_ready_count").text("无数据！");
				$("#member_count").text("无数据！");
				$("#in_count").text("无数据！");
				$("#offline_count").text("无数据！");
				$("#not_ready_count").text("无数据！");
				$("#in_answer_count").text("无数据！");
				$("#ringring_time").text("无数据！");
				$("#busy_ready_count").text("无数据！");
			} else {
// 				$("#queue_name").text(data.queue_name);
 				$("#pick_up_rate").text((data.in_count==0?0:Math.round(data.in_answer_count/data.in_count*100))+"%");
				$("#idle_ready_count").text(data.idle_ready_count);
				$("#member_count").text(data.member_count);
				$("#in_count").text(data.in_count);
				$("#offline_count").text(data.offline_count);
				$("#not_ready_count").text(data.not_ready_count);
				$("#in_answer_count").text(data.in_answer_count);
				$("#ringring_time").text(Math.round(data.ringring_time / 1000));
				$("#busy_ready_count").text(data.busy_ready_count);
// 				$("#miss_count").text(data.miss_count);
// 				$("#incount").text(data.in_count);
// 				$("#intime").text(
// 						timeFormatConversion(Math.round(data.in_time / 1000
// 								+ (data.in_time % 1000 != 0 ? 1 : 0))));
// 				$("#outcount").text(data.out_count);
// 				$("#outtime").text(
// 						timeFormatConversion(Math.round(data.out_time / 1000
// 								+ (data.out_time % 1000 != 0 ? 1 : 0))));
// 				var now = new Date();	
// 				$("#bindertime").text(
// 						timeFormatConversion(Math.round(data.bindertime)));
// 				$("#busytime").text(
// 						timeFormatConversion(Math.round(data.busytime)));
// 				$("#ringingtime").text(
// 						data.in_count != 0 ? (Math.round(data.ringing_time
// 								/ data.in_count / 1000)) : 0);
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

