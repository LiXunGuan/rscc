	<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/calendar/WdatePicker.js"></script>
	<style>
		.demo-red {
		    color: #DF413E;
		}
	</style>
	<div id="main_record" style="">
	<!-- HEADER -->
		<!-- END RIBBON -->
		<!-- widget grid -->
		<section id="widget-grid-record" class="">
		
			<div class="row">
				<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="jarviswidget jarviswidget-color-darken" data-widget-editbutton="false">
						<header>
							<span class="widget-icon"><i class="fa fa-table"></i></span>
							<h2>${agent!''}</h2>
						</header>
						<div>
							<div class="jarviswidget-editbox"></div>
							<div class="widget-body">
								<div class="widget-body-toolbar">
									<div class="smart-form">
										<section></section>
										<section>
											<div class="row">
												<label class="label col" >开始时间 </label >
												<label class= "input col col-2">
													<input id = "startTime" name = "startTime"  value="${((startTime)?string('yyyy-MM-dd 00:00:00'))!''}" type="text" class="ui_input_text 400w" onclick= "WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endTime\')}'});" placeholder="开始时间" />
												</label>
												<label class="label col">结束时间 </label >
												<label class="input col col-2">
<!-- 													<input id="endTime" name="endTime"  value="${((endTime)?string('yyyy-MM-dd HH:mm:ss'))!''}" type="text" class="ui_input_text 400w"  onclick= "WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\')}'});" placeholder="结束时间" /> -->
													<input id="endTime" name="endTime"  value="${((endTime)?string('yyyy-MM-dd 23:59:59'))!''}" type="text" class="ui_input_text 400w"  onclick= "WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\')}'});" placeholder="结束时间" />
												</label>	
												
												<label class="label col">质检分数/分</label>
												<label class="input col col-1">
													<input name="score1" id="score1"  value="${(score1)!''}">
												</label>
												<label class="label col">~</label>
												<label class="input col col-1">
													<input name="score2" id="score2"  value="${(score2)!''}">
												</label>
												<button class="btn btn-sm btn-primary" id="exportSearch"> 导出搜索 </button>
<!-- 													<button class="btn btn-sm btn-primary" onclick="getMissedCall();">漏话信息</button> -->
												<button class="btn btn-sm btn-primary" onclick="searchData();">查询</button>
											</div>
										</section>
										<section>
											<div class="row">
												
												<label class="label col">主叫号码 </label>
												<label class="input col col-2">
													<input type="text" id="caller_id_number" name="caller_id_number"  value="${(caller_id_number)!''}"/>
    											</label>
    											<label class="label col">被叫号码 </label>
    											<label class="input col col-2">
    												<input type="text" id="dest_agent_interface_exten" name="dest_agent_interface_exten"  value= "${(dest_agent_interface_exten)!''}"/>
												</label>
												
<!-- 											</div> -->
<!-- 										</section> -->
<!-- 										<section> -->
<!-- 											<div class="row"> -->
												<label class="label col">接入号&nbsp;&nbsp;&nbsp; </label>
												<label class="select col col-2"> 
						    						<select id="access_number" name="access_number" onchange="getResult();">
						    							<option value="">----请选择----</option>
						    							<#if allAccessNumbers??>
							    							<#list allAccessNumbers as number>
																<#if access_number??>
																	<#if access_number == number.accessnumber>
																		<option selected="selected"  value="${number.accessnumber}">${number.accessnumber}</option>
																	<#else>
																		<option  value="${number.accessnumber}">${number.accessnumber}</option>
																	</#if>
																<#else>
																	<option  value="${number.accessnumber}">${number.accessnumber}</option>
																</#if>
															</#list>
														</#if>
						    						</select>
						    					</label>
						    					<label class="label col">呼叫方向</label>
						    					<label class="select col col-2"> 
						    						<select id="calldirection" name="calldirection"  onchange="getResult();">
						    							<option value="">----请选择----</option>
						    							<#list callDirections?keys as key>
															<#if calldirection??> 
																<#if key == calldirection>
																	<option value="${key}" selected="selected">${callDirections[key]}</option>
																<#else>
																	<option value="${key}">${callDirections[key]}</option> </#if>
															<#else>
																<option value="${key}">${callDirections[key]}</option> </#if>
														</#list>
						    						</select>
						    					</label>
						    					
						    							
												</div>
										</section>
												<section>
											<div class="row">
						    					
						    					<label class="label col">通话时长/秒</label>
						    					<label class="input col col-1">
					    							<input type="text" id="sduration" name="sduration"  value="${(sduration)!''}"/>
					    						</label>
					    						<label class="label col">~</label>
					    						<label class="input col col-1">
					    							<input type="text" id="eduration" name="eduration"  value="${(eduration)!''}"/>
					    						</label>
					    						<label class="label col">接通时长/秒</label>
					    						<label class="input col col-1">
						    						<input type="text" id="sbillsec" name="sbillsec"  value="${(sbillsec)!''}"/>
						    					</label>
						    					<label class="label col">~</label>
						    					<label class="input col col-1">
						    						<input type="text" id="ebillsec" name="ebillsec"  value="${(ebillsec)!''}"/>
					    						</label>
<!-- 											</div> -->
<!-- 										</section> -->
<!-- 										<section> -->
<!-- 											<div class="row"> -->
												<label class="label col">是否接通</label >
												<label class="select col col-2">  
						    						<select id="bridgesec" name="bridgesec" onchange="getResult();">
						    							<option value="">----请选择----</option>
						    							<#list bridgesecs?keys as key>
															<#if bridgesec??> 
																<#if key == bridgesec>
																	<option value="${key}" selected="selected">${bridgesecs[key]}</option>
																<#else>
																	<option value="${key}">${bridgesecs[key]}</option> </#if>
															<#else>
																<option value="${key}">${bridgesecs[key]}</option> </#if>
														</#list>
						    						</select>
						    					</label>
												
												<label class="label col">坐席</label>
												<label class="input col col-1">
													<input type="text" id="agentinfo" name="agentinfo" />
    											</label>
											</div>
										</section>
<!-- 										<section> -->
<!-- 											<div class="row"> -->
<!-- 											</div> -->
<!-- 										</section> -->
										<table id="dt_basic_record" class="display projects-table table table-striped table-bordered table-hover"  data-order='[[ 0, "asc" ]]' width="100%"></table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</article>
			</div>
			<div id="forModel"></div>
		</section>
	</div>
	
	<script src="${springMacroRequestContext.contextPath}/assets/js/adddate.js"></script>
	
	<style type="text/css">
		.popover .popover-title{
			padding: 8px 14px;
		}
		
		.popover .popover-content{
			padding: 9px 14px;
		}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			
			var i = 0;
			
			// 判断是否存在录音质检按钮权限
			<#if Session.actionMaps?exists>
		   		<#list actionMaps?keys as key>
		   			<#if key == "录音质检">
		   				i = 1;
		   			</#if>		
		   		</#list>
		   </#if> 
		   
		   var j = 0;
			
		   <#if manage??>
				j = 1;
		   </#if>
			
		    $('#dt_basic_record').DataTable({
		    	"dom" :"t" + "<'dt-toolbar-footer'<'col-sm-6 col-xs-12'i><'col-xs-12 col-sm-6 text-right'lp>>",
				"autoWidth" : true,
				"ordering" : true,
				"serverSide" : true,
				"processing" : true,
				"searching" : false,
				"pageLength" : 10,
				"lengthMenu" : [ 10, 15, 20, 25, 30 ],
			 	"language": {
			         "url" : getContext() + "public/dataTables.cn.txt"
			     },
			    "ajax":{
			    	"url" : getContext() + "record/data",
			    	"type":"POST",
			    	"data" :function(param){
			    		param.score1 = $("#score1").val();
			    		param.score2 = $("#score2").val();
			    		param.startTime = $("#startTime").val();
			    		param.endTime = $("#endTime").val();
			    		param.caller_id_number = $("#caller_id_number").val();
			    		param.dest_agent_interface_exten = $("#dest_agent_interface_exten").val();
			    		param.access_number = $("#access_number").val();
			    		param.calldirection = $("#calldirection").val();
			    		param.sduration = $("#sduration").val();
			    		param.eduration = $("#eduration").val();
			    		param.sbillsec = $("#sbillsec").val();
			    		param.ebillsec = $("#ebillsec").val();
			    		param.bridgesec = $("#bridgesec").val();
			    		param.startTime = $("#startTime").val();
			    		param.endTime = $("#endTime").val();
			    		param.agentinfo = $("#agentinfo").val();
			    	}
			    },
			    "rowCallback":function(nRow,aData,iDisplayIndex){
	            	if (aData.put_through=='未接通') {
						nRow.className='demo-red';
					}
	            },
			 	"paging" :true,
				"pagingType" :"bootstrap",
				"lengthChange" : true,
				"order" : [[ "0", "desc"]],
				"columns" : [
					{
					    "title": "时间",
					    "data": "start_stamp",
					    "width" : "10%",
					},
					{
					    "title": "主叫号码",
					    "data": "caller_id_number"
					},
					{
					    "title": "主叫坐席 ID",
					    "data": "caller_agent_id"
					},
					{
					    "title": "主叫坐席 信息",
					    "data": "caller_agent_info"
					},
					{
					    "title": "被叫号码", "data": "dest_agent_interface_exten",
					    "render": function(data,type,full){
						   if(!isNaN(full.dest_agent_interface_exten) && full.put_through == '未接通'){
							   return '<label id='+full.call_session_uuid+' onclick=callDestAgentExten(\''+full.dest_agent_interface_exten+'\',\''+full.call_session_uuid+'\')>'+ full.dest_agent_interface_exten +'</label>';
						   }
						   return full.dest_agent_interface_exten;
						}
					},
// 					{
// 					    "title": "被叫号码", 
// 					    "data": "dest_agent_interface_exten"
// 					},
					{
					    "title": "被叫坐席 ID",
					    "data": "dest_agent_id"
					},
					{
					    "title": "被叫坐席 信息",
					    "data": "dest_agent_info"
					},
					{
					    "title": "呼叫方向",
					    "data": "call_direction"
					},
					{
					    "title": "通话时长（秒）",
					    "data": "duration"
					},
					{
					    "title": "接通时长（秒）",
					    "data": "billsec"
					},
					{
					    "title": "是否接通",
					    "data": "put_through"
					},
					{
					    "title": "接入号",
					    "data": "access_number"
					},
// 				    { "title" : "主机名字","data" : "fshost_name"}, 
// 				    { "title" : "开始时间","data" : "start_stamp"},
// 				    { "title" : "结束时间","data" : "end_stamp"},
				    { "title" : "质检评分","data" : "null",
						   "render": function(data,type,full){
							   /* 显示的个数 */
							   if(full.score == '0'){
								   return "暂无评分";
							   }
							   /* 显示的个数 */
							   var count = '${((c.global_QC_count)!'0')?int}';
// 							   console.log(new Number(count)+"----------"+new Number((full.score)));
// 							   console.log(new Number(count) <= new Number((full.score)));
							   if(new Number(count) <= new Number((full.score))){ //如果显示大于分数
									return 	count+"分";
							   }else{
							   		return (full.score)+"分";
							   }
						}
				   },
				   { 
					   "title" : "操作",
					   "data" : "null",
					   "width" : "12%",
					   "orderable":false,
					   "render": function(data,type,full){
						   if(j == 1){ // 判断当前登陆用户是否是管理员(有删除录音权限)
							   if(full.billsec > 0) { // 接通(有播放权限)       		 
									if(i == 1){ // 是否有录音质检权限
										 if(full.level != 'agent'){ // 是否是录音质检管理页面而不是我的录音
				    						 return '&nbsp;&nbsp;<a  href="javascript:void(0);" onclick=getDestNumber(\''+full.call_session_uuid+'\',this);>详情</a>'
				    						 +'&nbsp;&nbsp;<a onclick=playItem("'+full.call_session_uuid+'");>播放</a>'
				    						 +'&nbsp;&nbsp;<a onclick=qualityCheck("'+full.call_session_uuid+'");>录音质检</a>'
				    						 +'&nbsp;&nbsp;<a href="javascript:void(0);" onclick=deleteRecord(\''+full.call_session_uuid+'\');>删除</a>';
										 }
									}
									return '&nbsp;&nbsp;<a  href="javascript:void(0);" onclick=getDestNumber(\''+full.call_session_uuid+'\',this);>详情</a>'
		    						 	  +'&nbsp;&nbsp;<a onclick=playItem("'+full.call_session_uuid+'");>播放</a>'
		    						 	  +'&nbsp;&nbsp;<a href="javascript:void(0);" onclick=deleteRecord(\''+full.call_session_uuid+'\');>删除</a>';
		    					}else{ // 未接通 
		    						return '&nbsp;&nbsp;<a href="javascript:void(0);" onclick=getDestNumber(\''+full.call_session_uuid+'\',this);>详情</a>'
		                             	   +'&nbsp;&nbsp;<a href="javascript:void(0);" onclick=deleteRecord(\''+full.call_session_uuid+'\');>删除</a>';
		    					}
						   }else{
							   if(full.billsec > 0) {  // 接通      		 
									if(i == 1){ // 是否有录音质检权限
										 if(full.level != 'agent'){ // 是否是录音质检管理页面而不是我的录音
				    						 return '&nbsp;&nbsp;<a  href="javascript:void(0);" onclick=getDestNumber(\''+full.call_session_uuid+'\',this);>详情</a>'
				    						 +'&nbsp;&nbsp;<a onclick=playItem("'+full.call_session_uuid+'");>播放</a>'
				    						 +'&nbsp;&nbsp;<a onclick=qualityCheck("'+full.call_session_uuid+'");>录音质检</a>';
										 }
									}
									return '&nbsp;&nbsp;<a  href="javascript:void(0);" onclick=getDestNumber(\''+full.call_session_uuid+'\',this);>详情</a>'
		    						 	  +'&nbsp;&nbsp;<a onclick=playItem("'+full.call_session_uuid+'");>播放</a>&nbsp;&nbsp;';
		    					}else{  // 未接通 
		    						return '&nbsp;&nbsp;<a href="javascript:void(0);" onclick=getDestNumber(\''+full.call_session_uuid+'\',this);>详情</a>';
		    					}
						   }
					   }
				   }
				],
			});
			
		});
		
		function callDestAgentExten(phoneNumber, callsessionuuid){
			<#if Session.LOGINEXTEN?exists> 
				$.post(getContext() + "callcenter/agentcall", {callNum:phoneNumber},function(data) {
					if(!data.success){
						$.smallBox({
							title : "操作失败",
							content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>"+data.message+"</i>",
							color : "#C46A69",
							iconSmall : "fa fa-times fa-2x fadeInRight animated",
							timeout : 2000
						});
					}else{
						$("#"+callsessionuuid).removeAttr("style");
					}
				},"json");
			<#else>
				$.smallBox({
	                title : "不能呼叫",
	                content : "<i class='fa fa-clock-o'></i> <i>请先绑定分机...</i>",
	                color : "#C79121",
	                iconSmall : "fa fa-times fa-2x fadeInRight animated",
	                timeout : 3000
	            });
			</#if>
		}
		
		//被叫明细
	    function getDestNumber(uuid, th){
	        var target = $(th);
	   	    var url = getContext() + "record/getdestnumber";
	   	    $.post(url, {callsessionuuid : uuid}, function(data) {
               if(data.ext){
	               target.attr("rel", "popover");
	               target.attr("data-placement", "left");
	               target.attr("data-original-title", "被叫详细");
	               target.attr("data-content", data.ext);
	               $("[rel='popover']").popover();
                }
	   	    },'json');
	    };
		
		
		function deleteRecord(uuid){
			$.SmartMessageBox({
				title : "删除",
				content : "该操作将执行删除操作，确定执行？",
				buttons : "[No][Yes]",
			}, function(ButtonPressed) {
				if (ButtonPressed === "Yes") {
					var url = getContext() + "record/delete";
					$.post(url,{uuid:uuid},function(data){
						if(data.success){
							$.smallBox({
								title : "操作成功",
								content : "<i class='fa fa-clock-o'></i> <i>删除成功...</i>",
								color : "#659265",
								iconSmall : "fa fa-check fa-2x fadeInRight animated",
								timeout : 2000
							});
							getResult();
						}
					},"json").error(function(){
						$.smallBox({
							title : "操作失败",
							content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>删除时出现异常...</i>",
							color : "#C46A69",
							iconSmall : "fa fa-times fa-2x fadeInRight animated",
							timeout : 2000
						});
					});
				}
				if (ButtonPressed === "No") {
					$.smallBox({
						title : "取消",
						content : "<i class='fa fa-clock-o'></i> <i>操作已经被取消...</i>",
						color : "#659265",
						iconSmall : "fa fa-times fa-2x fadeInRight animated",
						timeout : 2000
					});
				}
			});
		};
		
	    /* 播放  */
	    function playItem(uuid) {
	        var url = getContext() + "record/player";
	        $.post(url, {callsessionuuid : uuid},function(data) {
	            $("#main_record").append(data);
	        });
	    };
		
		/* 查询结果 */
		function getResult(){
			$('#dt_basic_record').DataTable().ajax.reload();
		}
		
		function searchData(){
			var stime = $("#startTime").val();
			var etime = $("#endTime").val();
			if(stime == ''){
				$("#startTime").val(new Date().format("yyyy-MM-dd 00:00:00"));
			};
			if(etime == ''){
				$("#endTime").val(new Date().format("yyyy-MM-dd 23:59:59"));
			}
			if(stime != '' && etime != ''){
				if(GetDateDiff(stime, etime) > 30){
					alert("请选择一个月以内的时间进行查询！！");
					return false;
				}
			}
			getResult();
		}
		
		function GetDateDiff(sDate1, sDate2){ 
			var aDate, oDate1, oDate2, iDays;
		    aDate = sDate1.split("-");
		    oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);  //转换为yyyy-MM-dd格式
		    aDate = sDate2.split("-");
		    oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);
		    iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24); //把相差的毫秒数转换为天数
		    return iDays;  // 返回相差天数
		}
		
		//点击白表格变色
// 		$('#dt_basic_record').on("click"," tbody td ",function(data){
// 			data.currentTarget.bgColor = "red";
// 		});
		
		/* 质检 */
		function qualityCheck(uuid){
			var requestUrl = getContext() + "record/get"; 
			$.post(requestUrl,{uid : uuid},function(data){
				$("#main_record").append(data);
			});
		}
		
		function getMissedCall(){
			var url = getContext() + "missedcall/miss"; 
			$.post(url,function(data){
				
			});
		}
		
		$('#access_number').select2({
		    allowClear : true,
		    width:'99%'
		});
		
		$('#calldirection').select2({
		    allowClear : true,
		    width:'99%'
		});
		
		$('#bridgesec').select2({
		    allowClear : true,
		    width:'99%'
		});
		
		/*导出搜索 */
	    $("#exportSearch").click(function (){
	    	var url = getContext()+"/record/exportSearch?";
	    	
	    	var caller_id_number = $("#caller_id_number").val();
	    	var dest_agent_interface_exten = $("#dest_agent_interface_exten").val();
	    	var access_number = $("#access_number").val();
	    	var startTime = $("#startTime").val();
	    	var endTime = $("#endTime").val();
	    	var score1 = $("#score1").val();
	    	var score2 = $("#score2").val();
	    	var calldirection = $("#calldirection").val();
	    	var sduration = $("#sduration").val();
	    	var eduration = $("#eduration").val();
	    	var sbillsec = $("#sbillsec").val();
	    	var ebillsec = $("#ebillsec").val();
	    	var bridgesec = $("#bridgesec").val();
	    	var agentinfo = $("#agentinfo").val();
	    	
	    	if(caller_id_number!= null && caller_id_number!=''){url += "&caller_id_number=" + caller_id_number ;}
	    	if(dest_agent_interface_exten!= null && dest_agent_interface_exten!=''){url += "&dest_agent_interface_exten=" + dest_agent_interface_exten ;}
	    	if(access_number!= null && access_number!=''){url += "&access_number=" + access_number ;}
	    	if(startTime!= null && startTime!=''){url += "&stime=" + startTime ;}
	    	if(endTime!= null && endTime!=''){url += "&etime=" + endTime ;}
	    	if(score1!= null && score1!=''){url += "&score1=" + score1 ;}
	    	if(score2!= null && score2!=''){url += "&score2=" + score2 ;}
	    	if(calldirection!= null && calldirection!=''){url += "&calldirection=" + calldirection ;}
	    	if(sduration!= null && sduration!=''){url += "&sduration=" + sduration ;}
	    	if(eduration!= null && eduration!=''){url += "&eduration=" + eduration ;}
	    	if(sbillsec!= null && sbillsec!=''){url += "&sbillsec=" + sbillsec ;}
	    	if(ebillsec!= null && ebillsec!=''){url += "&ebillsec=" + ebillsec ;}
	    	if(bridgesec!= null && bridgesec!=''){url += "&bridgesec=" + bridgesec ;}
	    	if(agentinfo!= null && agentinfo!=''){url += "&agentinfo=" + agentinfo ;}
	    	
	    	window.location.href=url;
	    });

	</script>

	<!-- 显示等级 类型 个数 -->
	<#macro showRate type type_show count>
		<#list count..1 as c>
			<input type="radio" name="${type}-rating" id="${type}-rating-${c}" onclick="getId(${c});">
			<label for="${type}-rating-${c}"><i class="fa fa-${type_show}"></i></label>
		</#list>
	</#macro>
	