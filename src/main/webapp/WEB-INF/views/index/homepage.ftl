<div id="homepageinfos"></div>
<section>
	<div class="row">
		<article class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
			<div class="jarviswidget jarviswidget-color-blue" id="wid-id-0">
				<header>
					<span class="widget-icon"> <i class="fa fa-user"></i> </span>
					<h2><strong>坐席信息</strong></h2>				
				</header>
				<div>
					<div class="jarviswidget-editbox"></div>
					<div class="widget-body" style="padding-left: 20px;">
						<h2><strong>坐席&nbsp;&nbsp;${(Session.CURRENTUSER.loginName)!''}</strong></h2>
						<span id="date"></span><br>
						<span>企业名称：${(companyname)!''}</span><br>
						<span>热线号码：${(companyphone)!''}</span><br>
						<span>所属网址：${(companyadd)!''}</span>
					</div>
				</div>
			</div>
		</article>
		<article class="col-xs-12 col-sm-8 col-md-8 col-lg-8">
			<div class="jarviswidget jarviswidget-color-greenLight" id="wid-id-2">
				<header>
					<span class="widget-icon"> <i class="fa fa-comments"></i> </span>
					<h2><strong>公告</strong></h2>				
				</header>
				<div>
					<div class="jarviswidget-editbox"></div>
					<div class="widget-body" id="agentnotices" style="height: 150px;font-size: large;">
						
					</div>
				</div>
			</div>
		</article>
	</div>
	<div class="row">
		<article class="col-xs-12">
			<div class="jarviswidget jarviswidget-color-blueLight" id="wid-id-2">
				<header>
					<span class="widget-icon"> <i class="fa fa-asterisk"></i> </span>
					<h2 class="font-md"><strong>每日小结</strong></h2>				
				</header>
				<div>
					<div class="jarviswidget-editbox"></div>
					<div class="widget-body">
						<div class="col-sm-12">
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
											<h3 class="panel-title">呼入接通次数</h3>
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
			</div>
		</article>
	</div>
</section>

<script src="${springMacroRequestContext.contextPath}/assets/js/adddate.js"></script>

<script type="text/javascript">

	$(document).ready(function() {
		timeFormatConversion();
		getAgentStatus();
		getAgentNotices();
		$("#date").text(new Date().format("yyyy年MM月dd日  星期w"));
	});
	
	function getAgentNotices(){
		var url = getContext() + "agentnotices";
		$.post(url, function(data) {
			if(data.ans.length > 0){
				$.each(data.ans,function(k, v){
					$("#agentnotices").append("<div><small class='text-muted'>"+ v.pubTime +"</small><span class='text-success' id='homepagenotice" + (k+1) + "' style='margin-left:10px;'>" + v.noticeTitle + "</span></div>");
					$("#homepagenotice"+(k+1)).click(function(){
						$('#homepageinfos').empty();
						var urls = "${springMacroRequestContext.contextPath}/agentnotice/get";
						$.post(urls, {uuid:v.uid,operation:"look",number:(k+1)},function(data){
							$('#homepageinfos').append(data);
						});
					});
				});
			}else{
				$("#agentnotices").append("<small class='text-danger'><strong>暂无公告！！</strong></small>");
			};
		}, 'json');
	}
	
	var needRefresh = true;
	function getAgentStatus() {
		var homepage = $(window.parent.document).find(".ui-tabs-nav").children("[aria-controls='tabs-homepage']");
		if(homepage.is(':visible')){
			if(homepage.attr("aria-selected") == "false"){
				needRefresh = false;
				return false;
			} else {
				needRefresh = true;
			}
		}else{
			needRefresh = false;
			return false;
		}
		
		var url = window.parent.getContext() + "daily";
		if(needRefresh) {
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
				} else {
					$("#miss_count").text(data.miss_count);
					$("#incount").text(data.in_count);
					$("#intime").text(timeFormatConversion(Math.round(data.in_time / 1000 + (data.in_time % 1000 != 0 ? 1 : 0))));
					$("#outcount").text(data.out_count);
					$("#outtime").text(timeFormatConversion(Math.round(data.out_time / 1000 + (data.out_time % 1000 != 0 ? 1 : 0))));
					var now = new Date();
					$("#bindertime").text(timeFormatConversion(Math.round(data.bindertime)));
					$("#busytime").text(timeFormatConversion(Math.round(data.busytime)));
					$("#ringingtime").text(data.in_count != 0 ? (Math.round(data.ringing_time / data.in_count / 1000)) : 0);
				}
			}, 'json');
		}
		
// 		var curInterval = setInterval(refreshTime, 1000);

		setInterval(refreshTime, 1000);
		
	}
	
	function refreshTime(){
		var bt = $("#bindertime").text();
		if(bt == 0){
			bt = "00:00:00";
		}
		var time = bt.split(":");
		var str = new Date();
		str.setHours(new Number(time[0]));
		str.setMinutes(new Number(time[1]));
		str.setSeconds(new Number(time[2]) + 1);
		$("#bindertime").text(c(str.getHours()) + ":" + c(str.getMinutes()) + ":" + c(str.getSeconds()));
	}
	
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
		str = c(now.getHours()) + ":" + c(now.getMinutes()) + ":" + c(now.getSeconds());
		return str;
	}
	
	function addTime(now) {
		var str = c(now.getHours()) + ":" + c(now.getMinutes()) + ":" + c(now.getSeconds()+1);
		return str;
	}
	
</script>

