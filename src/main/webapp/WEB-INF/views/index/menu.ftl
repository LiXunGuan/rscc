<!-- Left panel : Navigation area -->
<!-- Note: This width of the aside area can be adjusted through LESS variables -->

<script src="${springMacroRequestContext.contextPath}/assets/js/libs/jquery-2.1.1.min.js"></script>
<!-- <script src="${springMacroRequestContext.contextPath}/assets/js/sockjs/sockjs.js"></script> -->

<style>

#main
{
	padding-bottom:0px;
}
	/*定义滚动条高宽及背景 高宽分别对应横竖滚动条的尺寸*/
	
#myMenu::-webkit-scrollbar
{
	width: 10px;
	height: 16px;
	background-color: #3A3633;
}

/*定义滚动条轨道 内阴影+圆角*/
#myMenu::-webkit-scrollbar-track
{
	-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
	border-radius: 10px;
	background-color: #3A3633;
}

/*定义滑块 内阴影+圆角*/
#myMenu::-webkit-scrollbar-thumb
{
	border-radius: 10px;
	-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
	background-color: #FFF;
}

#tabs2>.ui-tabs-panel
{
	overflow : auto;
}

</style>

<#macro sub childs>
	<#list childs as s>
		<li>
			<#if s.actionType == 0>
				<#if (s.subMenuList)?size!=0>
					<#--
						<#if (s.subMenuList)?size==1>
							<#list (s.subMenuList) as sub>
								<a href="" onclick="addTab('${sub.actionName}','${sub.actionDescribe}')"><i class="${sub.actionCSS}"></i>
									<#if (sub.actionJSON)?? && sub.actionJSON == "1">
										<span id="activityMissCall" class="activity-dropdown"><b class="badge" id="misscalls">${(missedcalls)!''}</b></span>
									</#if>
									${sub.actionName}
								</a>
							</#list>
						<#else>
					-->
						<#if (s.actionDescribe)??>
							<a href="" onclick="addTab('${s.actionName}','${(s.actionDescribe)!''}')" ><i class="${(s.actionCSS)!''}"></i>
								<#if (s.actionJSON)?? && s.actionJSON == "1">
									<span id="activityMissCall" class="activity-dropdown"><b class="badge" id="misscalls">${(missedcalls)!''}</b></span>
								</#if>
								${s.actionName}
							</a>
						<#else>
							<a href="" onclick=""><i class="${(s.actionCSS)!''}"></i>
								<#if (s.actionJSON)?? && s.actionJSON == "1">
									<span id="activityMissCall" class="activity-dropdown"><b class="badge" id="misscalls">${(missedcalls)!''}</b></span>
								</#if>
								${s.actionName}
							</a>
						</#if>
						<ul>        
							<#list (s.subMenuList) as ss>
								<li><a href="" onclick="addTab('${ss.actionName}','${(ss.actionDescribe)!''}')"><i class="${(ss.actionCSS)!''}"></i>${ss.actionName}</a></li>
							</#list>
						</ul>
					<#--
						</#if>
					-->
				<#else>
					<#if (s.actionDescribe)??>
						<a href="" onclick="addTab('${s.actionName}','${(s.actionDescribe)!''}')"><i class="${s.actionCSS}"></i>
							<#if (s.actionJSON)?? && s.actionJSON == "1">
								<span id="activityMissCall" class="activity-dropdown"><b class="badge" id="misscalls">${(missedcalls)!''}</b></span>
							</#if>
							${s.actionName}
						</a>
					<#else>
						<a href="" onclick=""><i class="${s.actionCSS}"></i>
							<#if (s.actionJSON)?? && s.actionJSON == "1">
								<span id="activityMissCall" class="activity-dropdown"><b class="badge" id="misscalls">${(missedcalls)!''}</b></span>
							</#if>
							${s.actionName}
						</a>
					</#if>
				</#if>
			</#if>
		</li>
	</#list>
</#macro>

<aside id="left-panel">
	<div>
		<#if (Session.unexten)?exists>
			<div style="color: #AAAAAA; margin: 20px; font-size: 14px;">
				<input type="hidden" name="loginName" id="uName" value="${(loginUser.loginName)!''}" />
				<input type="hidden" name="password" id="password" value="${(loginUser.password)!''}" />
				<div>
					<table>
						<tr>
							<td>坐席名称：</td>
							<td style="padding-left: 10px;">${(loginUser.loginName)!''}</td>
						</tr>
					</table>
				</div>
			</div>
			<div style="margin: 10px;">
				<a class="btn btn-success" style="width: 96%;"  href="javascript:void(0);" onclick="bindexten('');">绑定分机</a>
			</div>
		<#else>
			<div style="width: 200px; height: 110px; color: #AAAAAA; margin: 20px; font-size: 14px;">
				<input type="hidden" id="id" name="id" value="${(loginUser.loginName)!''}" />
				<div id="loadStatus">
					<table>
						<tr>
							<td>坐席名称：</td>
							<td style="padding-left: 10px;">${(loginUser.loginName)!''}</td>
						</tr>
						<tr>
							<td>坐席分机：</td>
							<td style="padding-left: 10px;">${(LOGINEXTEN)!''}&nbsp;&nbsp;&nbsp;&nbsp;<a style="color: currentColor;"><i class="fa fa-pencil" id="bindexten" title="修改分机"></i></a>&nbsp;&nbsp;<a style="color: currentColor;"><i class="fa fa-times-circle" id="unbindexten" title="解绑分机"></i></a></td>
						</tr>
						<tr>
							<td>通话状态：</td>
							<td style="padding-left: 10px;">未通话</td>
						</tr>
						<tr>
							<td>电话号码：</td>
							<td style="padding-left: 10px;"></td>
						</tr>
						<tr>
							<td>通话时长：</td>
							<td style="padding-left: 10px;">0</td>
						</tr>
						<tr>
							<td>等待时长：</td>
							<td style="padding-left: 10px;">0</td>
						</tr>
					</table>
				</div>
			</div>
			
			<div>
				<table id="callStat" style="margin-left: 13px;">
					<tr>
						<td id="musicandsty" style="width: 70px;">
							<a id="musicsty" class="btn btn-default btn-lg disabled" href="javascript:void(0);" title="呼叫保持" id="tomusic" onclick="tomusic(this);">
								<i class="glyphicon glyphicon-music"></i>
							</a>
						</td>
						<td style="width: 70px;">
							<a id="callsty" class="btn btn-default  btn-lg disabled" href="javascript:void(0);" title="呼叫" id="tocall" onclick="tocall(this);"> 
								<i class="glyphicon glyphicon-earphone"></i>
							</a>
						</td>
						<td style="width: 70px;">
							<a id="busysty" class="btn btn-default  btn-lg disabled" href="javascript:void(0);" title="转接" id="transfer" onclick="totransfer(this);">
								<i class="glyphicon glyphicon-share-alt"></i>
							</a>
						</td>
					</tr>
				</table>
			</div>
			
			<div class="dropdown" style="margin: 13px;">
				<#if agentStat?? && !((agentStat.status)=='Available') && agentpause[agentStat.busy_reason]??>
					<a id="dLabel" role="button" data-toggle="dropdown" class="btn btn-primary bg-color-orange" style="width: 96%;" data-target="#" href="javascript:void(0);" aria-expanded="false">
						忙(${agentpause[agentStat.busy_reason]})
						<span class="caret"></span>
					</a>
				<#else>
					<a id="dLabel" role="button" data-toggle="dropdown" class="btn btn-primary" style="width: 96%;" data-target="#" href="javascript:void(0);">
						闲 
						<span class="caret"></span>
					</a>
				</#if>
				<!--<a id="dLabel" role="button" data-toggle="dropdown" class="btn btn-primary" style="width: 96%;" data-target="#" href="javascript:void(0);">${(agentpause[agentStat.busy_reason])!"闲"} <span class="caret"></span></a>  -->
				<ul class="dropdown-menu multi-level" role="menu" id="dLabelUl">
					<#if agentpause??>
						<#list agentpause?keys as key>
							<li style="width: 190px;" busy='${key}'>
								<a href="javascript:void(0);" onclick="topause('${key}');"><span busy="pause" style="color: black;"><i class="fa fa-circle txt-color-orange"></i>${agentpause[key]}</span></a>
							</li>
						</#list>
						<li class="divider"></li>
						<li style="width: 190px;" >
							<a href="javascript:void(0);" onclick="nopause()"><span busy="nopause" style="color: #AAAAAA;"><i class="fa fa-circle txt-color-green"></i>闲</span></a>
						</li>
					</#if>
				</ul>
			</div>
		</#if>
		
		<div class="btn-group" id="myMenu" style="width: 100%; border: 0px;overflow:auto;">
			<#if (menus.subMenuList)?size gt 0>
			<nav> 
				<ul >
					<@sub menus.subMenuList></@sub>
				</ul>
			</nav>
			</#if>
			<!-- <@sub menus.subMenuList></@sub> -->
		</div>
		<br />
		<div style="margin-left: 20px; ">
			<table id="queueStatus">

			</table>
		</div>
	</div>
</aside>

<script type="text/javascript">

	$(function(){
		
		$("#myMenu").height($(window).height()-$("#myMenu").position().top - 25);
// 		addCssByStyle("#tabs2>.ui-tabs-panel{height:" + ($(window).height()-$("#tabs2>div[aria-hidden='false']").position().top - 75) + "px;}")
		
		$(window).resize(function(){
			$("#myMenu").height($(window).height()-$("#myMenu").position().top - 25);
// 			addCssByStyle("#tabs2>.ui-tabs-panel{height:" + ($(window).height()-$("#tabs2>div[aria-hidden='false']").position().top - 75) + "px;}")
		});
		
		$('#main').on( 'length.dt', 'div:eq(1)>div>div>section table.dataTable', function ( e, settings, len ) {
			var t = $("#tabs2 ul li.ui-state-active span").attr("m");
			window.localStorage.setItem(t,len);
		} );
		
		$('#main').on( 'init.dt', 'div:eq(1)>div>div>section table.dataTable', function ( e, settings, json ) {
			
			var ts = $("#tabs2 ul li.ui-state-active span").attr("m");
			var pageLength = window.localStorage.getItem(ts)?window.localStorage.getItem(ts):10;
			$("#main div:eq(1)>div>div>section table.dataTable").DataTable().page.len(pageLength);
			window.setTimeout('$("#main div:eq(1)>div>div>section table.dataTable").DataTable().draw()',1);
			
		} );
		 
		$('#div').on( 'length.dt', "div#newpop table.dataTable", function ( e, settings, len ) {
			
			window.localStorage.datatable = 5;
		} );
		
		
		$('#div').on( 'init.dt', "div#newpop table.dataTable", function ( e, settings, json ) {
			
			$("div#newpop table.dataTable").DataTable().page.len(5);
			window.setTimeout('$("div#newpop table.dataTable").DataTable().draw()',1);
		} );

		$('#main').on( 'init.dt', ".modal-dialog table.dataTable", function ( e, settings, len ) {
			var t = $("#main .modal-dialog table.dataTable").attr("id");
			var pageLength = window.localStorage.getItem(t)?window.localStorage.getItem(t):10;
			$("#main .modal-dialog table.dataTable").DataTable().page.len(pageLength);
		} );

		/* 弹出框的默认勾选 */
		$('#main').on( 'length.dt', ".modal-dialog table.dataTable", function ( e, settings, len ) {
			var t = $("#main .modal-dialog table.dataTable").attr("id");
			window.localStorage.setItem(t,len);
		} );

		<#if (Session.unexten)?exists || (!agentStat??)>
		<#else>
			//通话状态加载
			var totalTime = Date();
			<#if agentStat?? && (agentStat.state)?? && agentStat.state=='up'>
				var sTime = new Date("${agentStat.up_time}"); //开始时间
				var eTime = new Date();
				totalTime = (eTime.getTime() + 14 * 60 * 60 * 1000 - sTime.getTime())/1000;
			</#if>
			
			var obj = {exten_status:"${agentStat.state}",number:"${(agentStat.number)!''}",time:totalTime};
			loadSty(obj);
		</#if>
		
		$("#bindexten").click(function(){
			bindexten(${(LOGINEXTEN)!''});
		})
		
		$("#unbindexten").click(function(){
			unbindexten(${(LOGINEXTEN)!''});
		})
	});

	var callNum;
	
// 	var currentCallStat = 0;
	
	$("#activityMissCall").parent().click(function(){
		//后台清除漏话信息
		$.post(getContext() + 'record/clearMissCalls');
		$("#activityMissCall").click();
		$("#activityMissCall").removeClass("active");
		$("#misscalls").hide();
	});
	
	function getContext(){
		return "${springMacroRequestContext.contextPath}/";
	}
	
	
	/* 忙/闲的加载 */
    function loadBusyor(state,busyname){
    	var busy='';
    	if (busyname=='system') {
    		busy='系统';
    		$("#dLabel").removeClass("bg-color-green");
            $("#dLabel").addClass("bg-color-orange");
            $("#dLabel").html("忙("+busy+")<span class='caret'></span>");
            $("[busy='pause']").css("color","#AAAAAA");
            $("[busy='nopause']").css("color","black");
		}else{
			$("#dLabelUl li").each(function(index){
	    		if (busyname==$("#dLabelUl").find('li').eq(index).attr('busy')) {
	    			busy=$("#dLabelUl").find('li').eq(index).find('a').eq(0).find('span').eq(0).text();
				}
	    	});
			
			if (state=='Logged Out') {
	        	$("#dLabel").removeClass("bg-color-green");
	            $("#dLabel").addClass("bg-color-orange");
	            $("#dLabel").html("忙("+busy+")<span class='caret'></span>");
	            $("[busy='pause']").css("color","#AAAAAA");
	            $("[busy='nopause']").css("color","black");

	        }else if(state=='Available'){
	            $("#dLabel").removeClass("bg-color-orange");
	        	$("#dLabel").addClass("btn-primary");
	            $("#dLabel").html("闲<span class='caret'></span>");
	            $("[busy='pause']").css("color","black");
	            $("[busy='nopause']").css("color","#AAAAAA");
	        }
		}
    	
    }
	
	/* 播放音乐 ,保持呼叫 */
	function tomusic(obj){
		if($(obj).attr("title")=="呼叫保持"){
			var url=getContext() + "callcenter/tomusic";
			$.post(url,{id:$("#id").val()},function(data){
				 if(data.success){
					 $("#musicsty").removeClass("disabled").addClass("bg-color-blue").attr("title","返回通话").children().removeClass("glyphicon-music").addClass("glyphicon-ban-circle");
		         }else{
		             $.smallBox({
		                 title : "操作失败",
		                 content : "<i class='fa fa-clock-o'></i> <i>呼叫保持失败...</i>",
		                 color : "#C46A69",
		                 iconSmall : "fa fa-times fa-2x fadeInRight animated",
		                 timeout : 2000
		             });
		         }
			},"json").error(function(){
		        $.smallBox({
		            title : "操作失败",
		            content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作失败...</i>",
		            color : "#C46A69",
		            iconSmall : "fa fa-times fa-2x fadeInRight animated"
		        });
		    });
		}else if($(obj).attr("title")=="返回通话"){
			backcall();
		}
	}
	
	/* 返回通话   */
	function backcall(){
		var url=getContext() + "callcenter/backcall"
		$.post(url,{id:$("#id").val()},function(data){
			 if(data.success){
				 $("#musicsty").removeClass("disabled").addClass("bg-color-blue").attr("title","呼叫保持").children().removeClass("glyphicon-ban-circle").addClass("glyphicon-music");
	         }else{
	             $.smallBox({
	                 title : "操作失败",
	                 content : "<i class='fa fa-clock-o'></i> <i>返回通话失败...</i>",
	                 color : "#C46A69",
	                 iconSmall : "fa fa-times fa-2x fadeInRight animated",
	                 timeout : 2000
	             });
	         }
		},"json").error(function(){
	        $.smallBox({
	            title : "操作失败",
	            content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作失败...</i>",
	            color : "#C46A69",
	            iconSmall : "fa fa-times fa-2x fadeInRight animated"
	        });
	    });
	};
	
	/* 呼叫 */
	function tocall(obj){
// 		 $('#forModel').empty();
		if($(obj).attr("title")=="呼叫"){
// 			if(currentCallStat == 1)
// 				return;
		    var url =getContext() + "callcenter/call";
		    $.post(url,{id:$("#id").val()},function(data) {
				$("#agent_index_call").remove();
		    	$("#forModel").append(data);
		    });
		}else if($(obj).attr("title")=="挂断"){
			callclose();
		}
	};
	
	/* 挂断  */
	function callclose(){
		var url=getContext() + "callcenter/callclose"
		$.post(url,{id:$("#id").val()},function(data){
			 if(data.success){
// 				 currentCallStat = 0;
			 }else{
	             $.smallBox({
	                 title : "操作失败",
	                 content : "<i class='fa fa-clock-o'></i> <i>挂断失败...</i>",
	                 color : "#C46A69",
	                 iconSmall : "fa fa-times fa-2x fadeInRight animated",
	                 timeout : 2000
	             });
	         }
		},"json").error(function(){
	        $.smallBox({
	            title : "操作失败",
	            content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作失败...</i>",
	            color : "#C46A69",
	            iconSmall : "fa fa-times fa-2x fadeInRight animated"
	        });
	    });
	};
	
	/* 转接  */
	function totransfer(){
	    var url =getContext() + "callcenter/transfer";
	    $.post(url,{id:$("#id").val()},function(data) {
	        $("#forModel").append(data);
	    });
	};
	
	/* 通话状态样式  */
	
	window.nowCallStat = '${(agentStat.state)!"down"}';
	
	var waittt;
	
    function loadSty(obj){
		var status = obj.exten_status;
		window.nowCallStat = status;
		//未通话
		if (status=="down") {
			
	        clearCalling(obj);
			$("#loadStatus tr:eq(2) td:last").html("未通话");
			$("#loadStatus tr:eq(3) td:last").html(hiddenPhone(obj.number));
			$("#musicsty").removeClass("bg-color-blue").addClass("disabled").attr("title","不可用").children().removeClass("glyphicon-ban-circle").addClass("glyphicon-music");
			$("#callsty").removeClass("disabled bg-color-red").addClass("bg-color-green").attr("title","呼叫");
			$("#busysty").removeClass("bg-color-green").addClass("disabled").attr("title","不可用");
			
			/* 呼叫异常 也要移除已经选择的号码。呼叫下一个 */
// 			if(window.frames["iframenewuserdata"] && typeof window.frames["iframenewuserdata"].removePhoneByAutoCall == 'function') {
// 				window.frames["iframenewuserdata"].removePhoneByAutoCall(obj.number?obj.number:obj.caller_id);
// 			}
			
			// 停止等待时长
			clearInterval(waittt);
			$("#loadStatus tr:eq(5) td:last").html("0 s");
			
		//振铃
		}else if(status=="ringing"){
			startCalling(obj.number,true);
			$("#loadStatus tr:eq(2) td:last").html("振铃");
// 			$("#loadStatus tr:eq(3) td:last").html(obj.number);
			$("#loadStatus tr:eq(3) td:last").html(hiddenPhone(obj.number));
			$("#musicsty").removeClass("bg-color-blue").addClass("disabled").attr("title","不可用").children().removeClass("glyphicon-ban-circle").addClass("glyphicon-music");
			$("#callsty").removeClass("disabled bg-color-green").addClass("bg-color-red").attr("title","挂断");
			$("#busysty").removeClass("bg-color-green").addClass("disabled").attr("title","不可用");
		//通话中	
		}else if(status=="up"){
			
			// 停止等待时长
			clearInterval(waittt);
			$("#loadStatus tr:eq(5) td:last").html("0 s");
			
	        calling(obj.number,true,obj.time);
			$("#loadStatus tr:eq(2) td:last").html("通话中");
// 			$("#loadStatus tr:eq(3) td:last").html(obj.number);
			$("#loadStatus tr:eq(3) td:last").html(hiddenPhone(obj.number));
			$("#musicsty").removeClass("disabled").addClass("bg-color-blue").attr("title","呼叫保持").children().removeClass("glyphicon-ban-circle").addClass("glyphicon-music");
			$("#callsty").removeClass("disabled bg-color-green").addClass("bg-color-red").attr("title","挂断");
			$("#busysty").removeClass("disabled").addClass("bg-color-green").attr("title","不可用");
			
		//呼叫中	
		}else if(status=="dailing"){
			
			// 添加等待时长
			waittt = setInterval(waitTime, 1000);
			
			if(obj.openwindow){
// 				if(last_call_session_uuid.indexOf(obj["call_session_uuid"]) >= 0) {
// 					return;
// 				}
				//在弹屏页面呼叫同一个号码，要把UUID等信息更新掉
				//不存在caller_id时，用number填充
				if(!obj.caller_id) {
					obj.caller_id = obj.number;
				}
				/* 正常呼出有dialing 移除已经选择的号码。呼叫下一个 */
				if(window.frames["iframenewuserdata"] && typeof window.frames["iframenewuserdata"].removePhoneByAutoCall == 'function') {
					window.frames["iframenewuserdata"].removePhoneByAutoCall(obj.number?obj.number:obj.caller_id);
				}
// 				$.post(getContext() + 'newuserdata/updateAnswerTime', {phone:obj.number,date:obj.timestamp});
				
				pop_event(obj, "newdatacall");
// 				if($("#newpop #phone_number_a").val() == obj.number) {
					
// 				} else {
// 					addTab("弹屏|" + obj.number,"newdatacall",obj,obj.number);
// 				}
// 				last_call_session_uuid.push(obj["call_session_uuid"]);
// 				var num = { "caller_id":obj.number,"call_session_uuid":obj.call_session_uuid}
// 				pop_event(num);
			}
				
			$("#loadStatus tr:eq(2) td:last").html("呼叫中");
// 			$("#loadStatus tr:eq(3) td:last").html(obj.number);
			$("#loadStatus tr:eq(3) td:last").html(hiddenPhone(obj.number));
			$("#musicsty").removeClass("bg-color-blue").addClass("disabled").attr("title","不可用").children().removeClass("glyphicon-ban-circle").addClass("glyphicon-music");
			$("#callsty").removeClass("disabled bg-color-green").addClass("bg-color-red").attr("title","挂断");
			$("#busysty").removeClass("bg-color-green").addClass("disabled").attr("title","不可用");
			
		//未注册
		}else if(status=="notregistered"){
			$("#loadStatus tr:eq(2) td:last").html("未注册");
			$("#musicsty").removeClass("bg-color-blue").addClass("disabled").attr("title","不可用").children().removeClass("glyphicon-ban-circle").addClass("glyphicon-music");
			$("#callsty").removeClass("bg-color-red bg-color-green").addClass("disabled").attr("title","不可用");
			$("#busysty").removeClass("bg-color-green").addClass("disabled").attr("title","不可用");
		}
	}
    
    /* 数字转时间 */
	function c(arg) {
		return arg >= 10 ? arg : "0" + arg;
	}
    
    function waitTime(){
		var bt =  $("#loadStatus tr:eq(5) td:last").text();
		if(bt == 0 || bt == "0" || bt == "0 s"){
			bt = "00:00:00";
		}
		var time = bt.split(":");
		var str = new Date();
		str.setHours(new Number(time[0]));
		str.setMinutes(new Number(time[1]));
		str.setSeconds(new Number(time[2]) + 1);
		$("#loadStatus tr:eq(5) td:last").text(c(str.getHours()) + ":" + c(str.getMinutes()) + ":" + c(str.getSeconds()));
	}
    

	/* 清除状态显示 */
    function clearCalling(obj){
//           currentCallStat = 0;
//           setTimeout("$('#dataTable').DataTable().ajax.reload(null,false);", 200);
	      $("#loadStatus tr:eq(3) td:last").html("");
	      $("#loadStatus tr:eq(4) td:last").html("0 s");
	      $("#loadStatus tr:eq(5) td:last").html("0 s");
	      stop(obj);
  	}
	
	/* 置忙  */
	function topause(pause){ 
		if (!$("#dLabel").hasClass('bg-color-orange')) {
			var url = getContext() + "agentpause/pause";
			var uid = $('#id').val();
			var busy = '';
		    $.post(url,{id:uid, pause:pause},function(data){
				 if(data.success){
					if("-1" == pause){
						busy = "自动置忙";
					}else{
						$("#dLabelUl li").each(function(index){
				    		if(data.pause == $("#dLabelUl").find('li').eq(index).attr('busy')) {
				    			busy = $("#dLabelUl").find('li').eq(index).find('a').eq(0).find('span').eq(0).text();
							}
				    	});	
					} 
					
					$("#dLabel").removeClass("bg-color-green");
			        $("#dLabel").addClass("bg-color-orange");
		            $("#dLabel").html("忙("+busy+")<span class='caret'></span>");
		            $("[busy='pause']").css("color","#AAAAAA");
		            $("[busy='nopause']").css("color","black");
// 		             $.smallBox({
// 		                 title : "操作成功",
// 		                 content : "<i class='fa fa-clock-o'></i> <i>置忙成功...</i>",
// 		                 color : "#659265",
// 		                 iconSmall : "fa fa-check fa-2x fadeInRight animated",
// 		                 timeout : 2000
// 		             });
		         }else{
		             $.smallBox({
		                 title : "操作失败",
		                 content : "<i class='fa fa-clock-o'></i> <i>置忙失败...</i>",
		                 color : "#C46A69",
		                 iconSmall : "fa fa-times fa-2x fadeInRight animated",
		                 timeout : 2000
		             });
		         }
			},"json").error(function(){
		        $.smallBox({
		            title : "操作失败",
		            content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作失败...</i>",
		            color : "#C46A69",
		            iconSmall : "fa fa-times fa-2x fadeInRight animated"
		        });
		    });
		}
	}
	
	/* 置闲  */
	function nopause(){
		if ($("#dLabel").hasClass('bg-color-orange')) {
			var url=getContext() + "/agentpause/nopause";
			$.post(url,{id:$("#id").val()},function(data){
				 if(data.success){
					$("#dLabel").removeClass("bg-color-orange");
		        	$("#dLabel").addClass("btn-primary");
		            $("#dLabel").html("闲<span class='caret'></span>");
		            $("[busy='pause']").css("color","black");
		            $("[busy='nopause']").css("color","#AAAAAA");
// 		            $.smallBox({
// 		                 title : "操作成功",
// 		                 content : "<i class='fa fa-clock-o'></i> <i>置闲成功...</i>",
// 		                 color : "#659265",
// 		                 iconSmall : "fa fa-check fa-2x fadeInRight animated",
// 		                 timeout : 2000
// 		             });
		         }else{
		             $.smallBox({
		                 title : "操作失败",
		                 content : "<i class='fa fa-clock-o'></i> <i>置闲失败...</i>",
		                 color : "#C46A69",
		                 iconSmall : "fa fa-times fa-2x fadeInRight animated",
		                 timeout : 2000
		             });
		         }
			},"json").error(function(){
		        $.smallBox({
		            title : "操作失败",
		            content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作失败...</i>",
		            color : "#C46A69",
		            iconSmall : "fa fa-times fa-2x fadeInRight animated"
		        });
		    });
		}
	}
	
	var popOptionStat;
	var currentCallStat = {};
    /* 状态的一些显示 */
    function startCalling(callNum, bool) {
    	
    }
    
    
	function calling(callNum, timer, begin) {
    	
    	//修改弹出框的状态
    	console.log(callNum);
    	//如果与当前呼叫相同再更新
    	if(window.frames["iframe" + callNum]) {
    		window.frames["iframe" + callNum].updateCallStatus(callNum);
    	}
    	
		if (callNum) {
			$("#loadStatus tr:eq(3) td:last").html(callNum);
		}
		if (timer) {
			getStart(begin);
		}
	}

	/* 时间开始 */
	var t ;
	var now;
	function getStart(num) {
		now = new Date();
		now.setHours(0);
		now.setMinutes(0);
		now.setSeconds(num != null ? num : 0);
		startit();
	}
	
	var toTen = function(arg){
		return arg>=10 ? arg : "0" + arg;
	}

	function startit(){
		t = setTimeout("startit()", 1000);
		now.setSeconds(now.getSeconds() + 1);
		$("#loadStatus tr:eq(4) td:last").html(toTen(now.getHours()) + ":" + toTen(now.getMinutes()) + ":" + toTen(now.getSeconds()));
	}
	
	/* 通话停止 */
	function stop(obj){
		
		clearTimeout(t);
		
		//在收到down的事件后判断是否已经dialing过，如果没有，直接停掉任务 //第一个条件是因为有两个down事件 第二个事件中没有callSessionUuid
		if (obj["call_session_uuid"] && last_call_session_uuid.indexOf(obj["call_session_uuid"]) < 0 ) {
			if(window.frames["iframenewuserdata"] && typeof window.frames["iframenewuserdata"].changeAutoCallStat == 'function'){
				window.frames["iframenewuserdata"].changeAutoCallStat(1);
				last_call_session_uuid = [];
// 				alert("对不起,检测到你通话后长时间未挂机或者长时间未摘机或者主动挂机,已停止呼叫！");
// 				$.SmartMessageBox({
// 		        	title : "停止呼叫",
// // 			        content : "系统检测到你的非法操作，已经停止呼叫，可能的原因有:<br/><br/><ul><li>客户挂断后长时间未挂机</li><li>系统呼叫长时间未摘机</li></ul>",
// 			        content : "系统检测到你的非法操作，已经停止呼叫，可能的原因有:<br/><br/><ul><li>长时间未挂机</li><li>长时间未摘机</li></ul>",
// 			        buttons : '[确定]'
// 			    });
				 
				$.SmartMessageBox({
		        	title : "停止呼叫",
			        content : "系统检测到你的非法操作，已经停止呼叫，可能的原因有:<br/><ul><li>通话结束后长时间未挂机</li><li>电话拨通后长时间未摘机</li></ul><font style='font-size: large;'>提示：您可以确定挂掉电话后,重新开启呼叫！</font>",
			        buttons : '[Yes]'
				});
			}
			return;
		}
		
		//停止时开始下一次计数
		if(window.nowCallStat=="down" && window.startAutoCall && window.autocallConfig && window.autocallConfig.timing == 0) {
   			var startNextByStop = function(){
   				if (window.nowCallStat=="down" && window.startAutoCall && window.autocallConfig && window.autocallConfig.timing == 0) {
   					
   					//取下一个号码 三种可能
   					var nextPhone = window.frames["iframenewuserdata"].getNextPhoneByAutoCall();
					var url;
					if(nextPhone != undefined) {
						if(nextPhone == -1) {
							window.frames["iframenewuserdata"].changeAutoCallStat();
							return;
						}
						url = getContext() + "newuserdata/makecall"
					} else {
						//closetag置为0,弹屏的时候不会有关闭按钮
						closetag = "1";
						url = getContext()+"newuserdata/callNextPhone";
					}
    				$.post(url,{phone:nextPhone},function(data){
						if(data.success) {
							
						} else {
							 $.smallBox({
			                    title : "失败",
			                    content : "<i class='fa fa-clock-o'></i> <i>呼叫失败，"+data.message+"</i>",
			                    color : "#C46A69",
			                    iconSmall : "fa fa-times fa-2x fadeInRight animated",
			                    timeout : 3000
			                });
							 window.frames["iframenewuserdata"].changeAutoCallStat();
						}
    				},"json").error(function(){
    					 window.frames["iframenewuserdata"].changeAutoCallStat();
    					 $.smallBox({
    	                   title : "自动呼叫失败",
    	                   content : "<i class='fa fa-clock-o'></i> <i>呼叫失败</i>",
    	                   color : "#C46A69",
    	                   iconSmall : "fa fa-times fa-2x fadeInRight animated",
    	                   timeout : 3000
    	               });
   					});
   				}
   			};
   			var nextCallTime = (window.autocallConfig && window.autocallConfig.time?window.autocallConfig.time:5) * 1000;
			setTimeout(startNextByStop, nextCallTime);
   		}
		
	}

	var current_pool_id = "";
	
	function bindexten(ext){
		$('#bindExtenDiv').empty();
        var url = getContext() + "getBindExten";
//         var uname = $("#uName").val();
//         var upwd = $("#password").val();
        var uname = '${(loginUser.loginName)!''}';
        var upwd = '${(loginUser.password)!''}';
		$.post(url, {uname:uname, upwd:upwd, ext:ext}, function(data){
			$("#bindExtenDiv").append(data);
		});
	}
	function unbindexten(ext){
        
        var uname = '${(loginUser.loginName)!''}';
        var upwd = '${(loginUser.password)!''}';
		
		$.SmartMessageBox({
	        title : "解绑",
	        content : "确定解绑分机信息吗？",
	        buttons : '[No][Yes]'
	    }, function(ButtonPressed) {
	        if (ButtonPressed === "Yes") {
	            var url = getContext() + "unbind";
	            $.post(url, {uname:uname, upwd:upwd, ext:ext}, function(data){
	                if(data.success){
	                    $.smallBox({
	                        title : "操作成功",
	                        content : "<i class='fa fa-clock-o'></i> <i>解绑成功...</i>",
	                        color : "#659265",
	                        iconSmall : "fa fa-check fa-2x fadeInRight animated",
	                        timeout : 2000
	                    });
		    			window.location.reload(true);
	                }else{
	                    $.smallBox({
	                        title : "操作失败",
	                        content : "<i class='fa fa-clock-o'></i> <i>解绑失败...</i>",
	                        color : "#C46A69",
	                        iconSmall : "fa fa-times fa-2x fadeInRight animated",
	                        timeout : 2000
	                    });
	                }
	            },"json").error(function(){
	                $.smallBox({
	                    title : "操作失败",
	                    content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作失败...</i>",
	                    color : "#C46A69",
	                    iconSmall : "fa fa-times fa-2x fadeInRight animated"
	                });
	            });
	        }
	    });
	}
	
	function addCssByStyle(cssString){
		var doc=document;
		var style=doc.createElement("style");
		style.setAttribute("type", "text/css");

		if(style.styleSheet){// IE
			style.styleSheet.cssText = cssString;
		} else {// w3c
			var cssText = doc.createTextNode(cssString);
			style.appendChild(cssText);
		}

		var heads = doc.getElementsByTagName("head");
		if(heads.length) {
			heads[0].appendChild(style);
		}
		else {
			doc.documentElement.appendChild(style);
		}	
	}


	
</script>


