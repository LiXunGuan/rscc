<script src="${springMacroRequestContext.contextPath}/assets/js/jquery.min.js"></script>
<script src="${springMacroRequestContext.contextPath}/assets/js/libs/jquery-2.1.1.min.js"></script>
<!-- <script src="${springMacroRequestContext.contextPath}/assets/js/sockjs/sockjs.js"></script> -->
<style>
#activity.active b.bg-color-red#queueNumber
{
	background : #a90329!important;
}
</style>
<div id="infos"></div>
<div id="popInfo"></div>
<header id="header">
	<div id="logo-group">
		<span id="logo" style="margin-top: 5px"> 
			<img src="assets/img/rscclogo11.png" style="width: 150px;">
		</span>
		<span id="activity" class="activity-dropdown"> <i class="fa fa-bar-chart-o"><span id="peoples" style="position:absolute;left:30px;letter-spacing: -5px"></span></i> <b id="queueNumber" class="badge"> ${queueNumber!0} </b> </span>
		<div class="ajax-dropdown">
			<div class="btn-group btn-group-justified" data-toggle="buttons">
				<#if allQueue??>
					<#list allQueue as q>
					<label class="btn btn-default">
						<input type="radio" name="activity" id="queueState/${q}">
						队列${(queueNameMap[q])!''}
					</label>
					</#list>
				</#if>
				<label class="btn btn-default">
					<input type="radio" name="activity" id="dailySum">
					每日小结 
				</label>
			</div>
			<div class="ajax-notifications custom-scroll">
				<div class="alert alert-transparent">
					<h4>点击一个按钮，在这里显示消息</h4>
					这个空白页的信息有助于保护您的隐私.
				</div>
				<i class="fa fa-lock fa-4x fa-border"></i>
			</div>
			<span></span>
		</div>
	</div>
	
<!-- 	<div id="logo-group"> -->
<!-- 		<span id="logo" style="margin-top: 5px"> <img src="assets/img/ruisheng.png" style="height: 30">  </span> -->
<!-- 	</div> -->
	
<!-- 	<div></div> -->
	<div style="width: 126px;"></div>
	<div style="overflow: hidden;position: absolute;" id="noticewidth">
		<div class="notices" style="padding-top: 15px;white-space: nowrap;"></div>
	</div>
    <div class="pull-right">
    
<!--         <div id="logout" class="btn-header transparent pull-right"> -->
<!--             <span> <button class="btn btn-primary" onclick="tuichule()" style="margin-top: 8px;">退出系统</button> </span> -->
<!--         </div> -->

<!-- 		<div id="logout" class="btn-header transparent pull-right"> -->
<!-- 			<span> <a href="" title="退出"  data-action="userLogout" data-logout-msg="通过这里关闭打开的浏览器，您可以进一步提高您的安全性"><i class="fa fa-sign-out"></i></a> </span> -->
<!-- 		</div> -->
		
		<div id="logout" class="btn-header transparent pull-right">
			<span> <a href="" title="退出"><i class="fa fa-sign-out"></i></a> </span>
		</div>
		
		<div id="hide-menu" class="btn-header pull-right">
			<span> <a href="javascript:void(0);" data-action="toggleMenu" title="折叠菜单"><i class="fa fa-reorder"></i></a> </span>
		</div>
		
		<div id="fullscreen" class="btn-header transparent pull-right">
			<span> <a href="javascript:void(0);" data-action="launchFullscreen" title="全屏"><i class="fa fa-arrows-alt"></i></a> </span>
		</div>
		
		<div id="update-pwd" class="btn-header transparent pull-right">
			<span> <a href="javascript:void(0);" title="修改密码"><i class="fa fa-user"></i></a> </span>
		</div>
		
		<form action="" class="header-search pull-right">
			<input id="search-fld"  type="text" name="param" placeholder="编号/电话" data-autocomplete=''>
			<button type="button" id="ajaxSearch">
				<i class="fa fa-search"></i>
			</button>
			<a href="javascript:void(0);" id="cancel-search-js" title="Cancel Search"><i class="fa fa-times"></i></a>
		</form>
		
		
<!-- 		<div class="btn-header transparent pull-left"> -->
<!-- 	        <form action="${springMacroRequestContext.contextPath}/help/download" method="post" class="smart-form"> -->
<!-- 	        	<#if (Session["CURRENTUSER"].adminFlagSession)?? && Session["CURRENTUSER"].adminFlag == "0"> -->
<!-- 					<input type="button" class="btn btn-sm btn-primary" id="updaaa" onclick="adminWindow();" value="管理员模式" style="margin-top: 8px;"/> -->
<!-- 				<#else>	 -->
<!-- 					<input type="button" class="btn btn-sm btn-primary" id="updaaa" onclick="adminWindow();" value="坐席模式" style="margin-top: 8px;"/> -->
<!-- 	        	</#if> -->
<!-- 	        	<#if (Session["CURRENTUSER"].adminFlag)?? && Session["CURRENTUSER"].adminFlag == "0"> -->
<!-- 					<a class="btn btn-md btn-primary" href="${springMacroRequestContext.contextPath}/user/user/forwardToAdminPage" style="margin-top: 8px;">管理员模式</a> -->
<!-- 				</#if> -->
<!-- 				<input type="button" class="btn btn-sm btn-primary" id="updaaa" onclick="reload();" value="下载使用手册" style="margin-top: 8px;"/> -->
<!-- 			</form> -->
<!-- 		</div> -->
    </div>
</header>



<script src="${springMacroRequestContext.contextPath}/assets/js/jquery.pause.js" defer="defer"></script>
<script type="text/javascript">
	
	$("#update-pwd").click(function(){
		var urls = "${springMacroRequestContext.contextPath}/user/user/updatePwd";
		$.post(urls, function(data){
			$('#infos').empty();
			$('#infos').append(data);
		});
	})
	
	function getContext(){
		return "${springMacroRequestContext.contextPath}/";
	}
	
	function tuichule(){
		window.location.href="logout";
	}
	
	function smallBox(cont, type, uuid){
		$.smallBox({
			title : "日程提醒。。",
			content : cont,
			color : "#5384AF",
			icon : "fa fa-bell shake animated"
		});
		
		if(type == "schedule"){
			$("#divSmallBoxes").children().click(function(){
				var url = "${springMacroRequestContext.contextPath}/schedulereminder/get";
				var operation = "look";
				var temp = this;
				$.post(url, {operation:operation,uuid:uuid}, function(data){
					$('#infos').append(data);
					$('#quxiao').click(function(){
						var ull = "${springMacroRequestContext.contextPath}/schedulereminder/deleteScheduleUserByUUid";
						$.post(ull, {uuid:uuid});
					});
				});
			});
		}
	}
	
	function bigbox(cont, type, uuid){
		var colors;
		var titles;
		if(type == "schedule"){
			titles = "日程提醒。。";
			colors = "#3276B1"
		}else if(type == "missedcall"){
			titles = "漏话提醒。。";
			colors = "#C79121";
		}else if(type == "upload"){
			titles = "文件导入成功";
			colors = "#3276B1";
		}else if(type == "upload-error") {
			titles = "文件导入错误";
			colors = "#C79121";
		}
		$.bigBox({
			title : titles,
			content : cont,
			color : colors,
			icon : "fa fa-bell shake animated"
		});
		
		if(type == "schedule"){
			$("#divbigBoxes").children().click(function(){
				var url = "${springMacroRequestContext.contextPath}/schedulereminder/get";
				var operation = "look";
				var temp = this;
				$.post(url, {operation:operation,uuid:uuid}, function(data){
					$('#infos').append(data);
					$('#quxiao').click(function(){
						console.log($(temp));
						$(temp).find("[id*=Close]").click();
					});
				});
			});
			$("[id*=Close]").click(function(){
				var ull = "${springMacroRequestContext.contextPath}/schedulereminder/deleteScheduleUserByUUid";
				$.post(ull, {uuid:uuid});
				return false;
			});
		}
	}
	/* 
		header中弹屏转发规则:传递全部json值到后台，如果有特殊个别字段，添加到已存在的JSON中(js中添加和转发处添加都行)，如：pop_event转发；
	*/
	/*打开websocket*/	
	SockJS.prototype.actions = {};
	SockJS.prototype.bind = function(type, action){
	    this.actions[type] = action;
    };
	
	ws = new SockJS( getContext() + 'user', undefined, {});
	// new WebSocket(url);
	ws.onopen = function() {
		ws.onmessage = function(event) {
			var obj = JSON.parse(event.data);
			if(typeof this.actions[obj.type] == "function"){
				this.actions[obj.type](obj);
			}
			if(obj.type == "stat_change"){
				loadSty(obj);
			}else if(obj.type == "queue_status_event"){
// 				console.log(obj);
				queueCount[obj.queue_id] = obj.count;
				changeQueueNumer();
			}else if(obj.type == "confrence_stat"){
				if(obj.stat_type == "confrence_change"){
					if(typeof changeConfrenceStat == "function")
						changeConfrenceStat(1,"");
				}else{
					$('#confrenceTable').DataTable().ajax.reload(null,false);;
				}
			}else if(obj.type == 'data-upload'){
				if(!obj.error) {
					bigbox("共有数据" + obj.total + "条，导入数据" + obj.count + "条","upload");
				} else {
					bigbox(obj.error,"upload-error");
				}
				if($('#dataDataTable').length>0)
					$('#dataDataTable').DataTable().ajax.reload(null,false);;
			}else if(obj.type == "data-upload-progress"){
				if($("#progress" + obj.uuid).length > 0) {
					var dom = $("#progress" + obj.uuid).parent().prev();
					dom.find("div span").first().text("导入进度");
				    dom.find("div span").last().text("已完成" + obj.progress);
				    dom.find(".progress").addClass("active");
// 				    dom.find(".progress-bar").css("width", obj.progress);
					$("#progress" + obj.uuid).css("width",obj.progress);
				}
				if(obj.progress === "100%" && $('#dataDataTable').length>0)
					$('#dataDataTable').DataTable().ajax.reload(null,false);;
			}else if(obj.type == "schedule"){
				//日程提醒
// 				bigbox(obj.content, obj.type, obj.uuid);
				smallBox(obj.content, obj.type, obj.uuid);
			}else if(obj.type == "notice"){
				//坐席公告
				//刷新页面的公告信息
				getAgentNotices(notwidth);
			}else if(obj.type == "missedcall"){
				//漏话提醒
// 				bigbox(obj.content,obj.type);
				//刷新页面的漏话提醒
				$("#misscalls").show();
				$("#misscalls").addClass("bg-color-red");
				$("#misscalls").text(obj.content);
			}else if(obj.type == 'pop_event'){
				pop_event(obj);
			}else if(obj.type == 'extenubind'){
				alert("分机已被踢掉,请重新登陆!");
				tuichule();
			}else if(obj.type == 'kickout'){
				alert("您的账号在其他浏览器登录!");
				tuichule();
			}else if(obj.type == 'hidephone'){
				hiddenPhoneFlag.globalHide = obj.flag == "true";
			
			}
		};
	};
	
	ws.onclose = function(event) {
		ws = new SockJS( getContext() + 'user', undefined, {});
		//alert('Info: connection closed.');
	};
	
	/* 菜单鼠标事件 */
	function buttonSty1(obj) {
		var a = $(obj);
		a.css("color", "white");
	}
	
	function buttonSty2(obj) {
		var a = $(obj);
		a.css("color", "#AAAAAA");
	}
	
	var cycle = function(id,notwidth) {
		if ($("#notice" + id).size() == 0)
			id = 1;
		var nwidth = $("#notice" + id).width();
		$("#notice" + id).css("right", "-" + (nwidth/notwidth*100) + "%").show();
		$("#notice" + id).stop().animate({
			'right' : "100%"
		}, 14000, "linear", function() {
			$("#notice" + id).css("right", "-" + (nwidth/notwidth*100) + "%").hide();
			cycle(id + 1,notwidth);
		});
	}
	
	var notwidth = $(window).width() -  220 - 126 - 350 - 20;
	
	$(document).ready(function() {
		
		//浏览器当前窗口可视区域宽度 
		var winwidth = $(window).width();
		
		notwidth = winwidth - 220 - 126 - 350 - 20;
		
		$("#noticewidth").width(notwidth);
		
		//获取公告信息
		getAgentNotices(notwidth);
		
		//获取日程提醒信息
		getScheduleReminders();

		changeQueueNumer();
		
	});
	
	var queueCount = {<#list queueMap?keys as q>${q}:${queueMap[q]},</#list>};
	var myQueue = [<#if queues??><#list queues as q>"${q}",</#list></#if>];
	function changeQueueNumer() {
		var num = 0;
		for (var i in myQueue)
			num += parseInt(queueCount[myQueue[i]] || 0);
		if(num > 0){
// 			$("#queueNumber").prev().addClass("fa-list-ul").removeClass("fa-bar-chart-o");
			$("#queueNumber").addClass("bg-color-red");
		}
		else{
// 			$("#queueNumber").prev().addClass("fa-bar-chart-o").removeClass("fa-list-ul");
			$("#queueNumber").removeClass("bg-color-red");
		}
		var peoples = "";
		for(var i = 0 ; i < num && i < 10 ; i++)
			peoples += "\uf007";
		if(num > 10){
			peoples += "......";
			$("#queueNumber").css("right","-120px");
		}else{
			var right = +11*num + 15;
			$("#queueNumber").css("right","-" + right + "px");
		}
		$("#queueNumber").text(num);
		$("#activity i span").text(peoples);
	}
	$("#logo-group").on("click","#activity",function(event){
		changeQueueNumer();
		$("[name=activity]:first").click();
	})
	
	function getAgentNotices(notwidth){
		
		if($(".notice").size() != 0){
			$(".notice").stop();
		}
		
		var url = "${springMacroRequestContext.contextPath}/agentnotice/getUserAgentNotices";
		$.post(url, function(data){
			if(data.success){
				$(".notices").empty();
				if(data.agentNotices.length != 0){
					$.each(data.agentNotices,function(k, v){
						$(".notices").append("<span style='position: absolute;' id='notice" + (k+1) + "' class='notice'>来自[" + v.publishUserName + "]的公告"+ (k+1) +"：<a href='javascript:void(0)' style='text-decoration: none;'>" + v.noticeTitle + "</a></span>");
// 						$(".notices").append("<span style='position: absolute;' id='notice" + (k+1) + "' class='notice'>公告"+ (k+1) +"：<a href='javascript:(0)' style='text-decoration: none;'>" + v.noticeTitle + "</a></span>");
						$("#notice"+(k+1)).click(function(){
							$('#infos').empty();
							var urls = "${springMacroRequestContext.contextPath}/agentnotice/get";
							$.post(urls, {uuid:v.uid,operation:"look",number:(k+1)},function(data){
								$('#infos').append(data);
							});
						});
						//不论鼠标指针离开被选元素还是任何子元素，都会触发 mouseout事件;只有在鼠标指针离开被选元素时，才会触发 mouseleave 事件。
						$("#notice"+(k+1)).mouseenter(function() {
							$("#notice"+(k+1)).pause();
						})
						$("#notice"+(k+1)).mouseleave(function() {
							$("#notice"+(k+1)).resume();
						})
					});
					$(".notices").children().hide();
					console.log(notwidth);
					cycle(1,notwidth);
				}
			}
		},"json");
	}
	
	//日程提醒信息
	function getScheduleReminders(){
		var url = "${springMacroRequestContext.contextPath}/schedulereminder/getScheduleReminders";
		$.post(url, function(data){
			if(data.success){
				$("#divSmallBoxes").empty();
				$.each(data.srs, function(k,v){
					smallBox(v.schedulecontent, "schedule", v.scheduleUUID);
				});
			}
		},"json");
	}
	
	
	var last_call_session_uuid = [];
	
	/* pop_event弹屏 */
	function pop_event(num){
		
		if(last_call_session_uuid.indexOf(obj["call_session_uuid"]) >= 0)
			return;
		last_call_session_uuid.push(obj["call_session_uuid"]);
// 		addTab('添加信息','addInfo',num);
		window.open("${springMacroRequestContext.contextPath}/addWindow?detail="+JSON.stringify(num)+"");
// 		window.open("${springMacroRequestContext.contextPath}/addTab?detail="+JSON.stringify(num)+"");

	}

	/* 跳转到新窗口 */
	function adminWindow() {
		$.post(getContext() + 'user/user/forwardToAdminPage');
	}
	
	$("#logout").click(function(e) {
		console.log(e);
		$.SmartMessageBox({
			title : "<i class='fa fa-sign-out txt-color-orangeDark'></i>退出<span class='txt-color-orangeDark'><strong>${Session.CURRENTUSER.loginName}</strong></span>?",
			content : "通过这里关闭打开的浏览器,将安全解绑分机，进一步提高安全性!",
			buttons : '[No][Yes]'
		}, function(ButtonPressed) {
			if (ButtonPressed === "Yes") {
				tuichule();
			}
		});
		e.preventDefault();
	})
	
	/* 全局搜索和查找 */
	$("#ajaxSearch").click(function(){
		
		getCodeOrPhoneSearch($("#search-fld").val());
	});
	/* 传入电话号码或者是客户编号 */
	function getCodeOrPhoneSearch(params){
		
		$.post(getContext()+"cstm/checkExists",{params:params},function(data){
			
			if(data.success){

				/* 查询没有call_session_UUID */
				window.open("${springMacroRequestContext.contextPath}/addWindow?detailq="+params+"");
			}else{
				$.bigBox({
					title : "提示",
					content : data.error,
					color : "#C79121",
					icon : "fa fa-bell shake animated",
					timeout : 5000
				});
			}
		},"json");
		
	}
	
	$('#search-fld').bind('keypress',function(event){
        if(event.keyCode == "13"){
        	$('#ajaxSearch').click();
        	return false;
        }
    });
	
	var hiddenPhoneFlag = {globalHide : false, hasPermission : false};
	
	<#if hiddenPhone?? && "true" == hiddenPhone>
		hiddenPhoneFlag.globalHide = true;
	<#else>
		hiddenPhoneFlag.globalHide = false;
	</#if>
	
	<#if hasPhonePermission?? && true == hasPhonePermission>
		hiddenPhoneFlag.hasPermission = true;
	<#else>
		hiddenPhoneFlag.hasPermission = false;
	</#if>
	
	
	/*隐藏号码*/
	hiddenPhone = function hiddenPhone(phoneNumber){
		if(hiddenPhoneFlag.globalHide && !hiddenPhoneFlag.hasPermission){

			return phoneNumber.replace(/(\d{3})\d*/, '$1********');
		}else{
			
			return phoneNumber;
		}
	}
	
	
	
</script>


