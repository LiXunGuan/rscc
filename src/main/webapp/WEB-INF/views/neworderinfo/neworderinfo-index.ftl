
	<link rel="stylesheet" type="text/css" media="all" href="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-daterangepicker-master/font-awesome.min.css"/>
	<link rel="stylesheet" type="text/css" media="all" href="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-daterangepicker-master/daterangepicker-bs3.css"/>
	
	
	<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-daterangepicker-master/moment.js"></script>
	<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-daterangepicker-master/daterangepicker.js"></script>
	<script src="${springMacroRequestContext.contextPath}/assets/js/smartwidgets/jarvis.widget.js"></script>
	
	<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/calendar/WdatePicker.js"></script>

	<style>
	
		#addNewOrderInfo {
			float: left;
			margin-bottom: 5px;
		}
		
		#alterTable {
			float: right;
			margin-right: 1%;
		}
		
		.row .label.col.col-1 {
			text-align: right;
			text-justify: inter-ideograph;
		}
		
		.select2-hidden-accessible {
			display: none;
		}
		
	</style>

	<div id="tmpNewOrderInfo"></div>
	<div style="">
	<!-- HEADER -->
		<!-- END RIBBON -->
		<!-- widget grid -->
		<section>
			<label class="btn btn-sm btn-primary" id="addNewOrderInfo" onclick="editModel(null)">添加订单</label>
			<div class="row">
				<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<div class="jarviswidget ${c.global_color}" data-widget-colorbutton="false" data-widget-editbutton="false"
					 data-widget-togglebutton="true" data-widget-deletebutton="true">
						<header>
							<span class="widget-icon"><i class="fa fa-table"></i></span>
							<h2>${agent!''}</h2>
						</header>
						<div>
							<div class="jarviswidget-editbox"></div>
							<div class="widget-body">
								<div class="widget-body-toolbar">
									<div class="smart-form">
										<section>
											<div class="row" style="margin-top: 5px;">
												<label class="label col col-1">订单编号</label>
												<label class="input col col-2">
													<input type="text" name="neworderid" class="form-control" id="neworderid" placeholder="订单编号"/>
												</label>
												<label class="label col col-1">订单状态</label>
												<label class="input col col-2">
						    						<select id="neworderStatus" name="neworderStatus"  onchange="getResult();">
						    							<option value="">请选择</option>
						    							<#list orderstatus?keys as key>
															<option value="${key}">${orderstatus[key]}</option>
														</#list>
						    						</select>
												</label>
												<label class="btn btn-sm btn-primary" onclick="getResult();">&nbsp;查&nbsp;&nbsp;&nbsp;询&nbsp;</label>
												<label class="btn btn-sm btn-success" onclick="exportdata();">&nbsp;导&nbsp;&nbsp;&nbsp;出&nbsp;</label>
											</div>
										</section>
										<section>
											<div class="row" style="margin-top: 5px;">
												<label class="label col col-1" >开始时间 </label >
												<label class= "input col col-2">
													<input id="startTime" name="startTime" value="${((startTime)?string('yyyy-MM-dd 00:00:00'))!''}" type="text" class="ui_input_text 400w" onclick= "WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endTime\')}'});" placeholder="开始时间" />
												</label>
												<label class="label col col-1">结束时间 </label >
												<label class="input col col-2">
													<input id="endTime" name="endTime" value="${((endTime)?string('yyyy-MM-dd 23:59:59'))!''}" type="text" class="ui_input_text 400w"  onclick= "WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\')}'});" placeholder="结束时间" />
												</label>
											</div>
										</section>
										<section>
											<div class="row">

										<!-- 遍历所有可查询的自定义字段 非必须字段 -->
										<#if allMaps??>
											<#assign num = 1>
												<#list allMaps?keys as columndesign>
													<#if num = 0 || num%3 == 0>
															</div>
														</section>
														<section>
															<div class="row">
													</#if>
													<label class="label col col-1" >${allMaps[columndesign].columnName}</label>
													<#if allMaps[columndesign].columnType == "ENUM">
														<#assign enumStr = allMaps[columndesign].characterProperty>
														<div class="form-group col col-lg-2">
															<select name="${columndesign!''}" id="${columndesign!''}" class="form-control" onchange="getResult();">
																<option value="">----请选择----</option>
																<#list enumStr?replace("，",",")?split(",") as a>
																	<option value="${a!''}">${a!''}</option>
																</#list>
															</select>
														</div>
													<#elseif allMaps[columndesign].columnType == "DATE">
														<label class="input col col-2"> 
																<input type="text" name="${columndesign}" id="${columndesign}" onchange="setDefault()" class="form-control" value="" />
																<script type="text/javascript">
																	$(document).ready(function() {
																		$('#${columndesign}').daterangepicker({
																			format: 'YYYY-MM-DD HH:mm:ss',
																			timePicker: true,
																			timePicker12Hour: false,
																			showDropdowns: true,
														                    locale: {
														                        applyLabel: '确定',
														                        cancelLabel: '清空',
														                        fromLabel: '从',
														                        toLabel: '到',
														                        daysOfWeek: ['日', '一', '二', '三', '四', '五','六'],
														                        monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
														                        firstDay: 1
														                    }
																		},function(start, end, label) {
																			console.log($("#${columndesign}").val());
																		}).on('cancel.daterangepicker',function(ev, picker){
																			$('#${columndesign}').val('');
																			getResult();
																		}).on('apply.daterangepicker',function(ev, picker){
																			getResult();
																		});
																	});
																</script>
														</label>
													<#elseif allMaps[columndesign].columnType == "INT" || allMaps[columndesign].columnType == "FLOAT">
														<label class="input col col-2">
															<input type="hidden" id="${columndesign}" name="${columndesign}">
															<input class="form-control col" style="width: 34%;" id="${columndesign}_1" onchange="changeVal_${columndesign}();" name="${columndesign}_1" value="">
															<lable class="label col">到</lable>
															<input class="form-control col" style="width: 34%;" id="${columndesign}_2" onchange="changeVal_${columndesign}();"  name="${columndesign}_2" value="">
														</label>
														<script type="text/javascript">
															function changeVal_${columndesign}(){
																$("#${columndesign}").val($("#${columndesign}_1").val()+","+$("#${columndesign}_2").val());
															}
														</script>

													<#else>	 
														<label class="input col col-2"> 
															<input type="text" id="${columndesign}"  name="${columndesign}" class="form-control" value=""/>
														</label>
													</#if>
													<#assign num=num+1 />
											</#list>
											</#if>
											</div>
										</section>
										<input type="hidden" name="newadminflag" id="newadminflag" value="${adminflag!''}">
										<input type="hidden" name="newuseruuid" id="newuseruuid" value="${useruuid!''}">
<!-- 										<section> -->
<!-- 											<div class="row" style="margin-top: 5px;"> -->
<!-- 												<label class="label col">质检分数</label> -->
<!-- 												<label class="input col col-1"> -->
<!-- 													<input name="score1" id="score1" > -->
<!-- 												</label> -->
<!-- 												<label class="label col">~</label> -->
<!-- 												<label class="input col col-1"> -->
<!-- 													<input name="score2" id="score2" > -->
<!-- 												</label> -->
<!-- 											</div> -->
<!-- 										</section> -->
										<table id="dt_basic_neworderinfo" class="table table-striped table-bordered table-hover"  data-order='[[ 0, "asc" ]]' width="100%"></table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</article>
			</div>
		</section>
	</div>

	<!-- 提交参数 -->
	<#macro params listParam> 
		<#if listParam??> 
			<#list listParam as p>
				param.${p} = $("#${p}").val(); 
			</#list> 
		</#if> 
	</#macro>
	
	<#macro listParameter allParameters> 
    	<#if allParameters??> 
    		<#list allParameters as p>
    			url += "&${p}="+$("#${p}").val();
    		</#list> 
    	</#if> 
    </#macro>
	
	<style type="text/css">
		.popover .popover-title{
			padding: 8px 14px;
		}
		
		.popover .popover-content{
			padding: 9px 14px;
		}
	</style>

	<script type="text/javascript">
	
			var i = 0;
			
			<#if manage??>
				i = 1;
			</#if>
	
			var orderinfoTable = "";
			
			$(document).ready(function() {
				pageSetUp();
				orderinfoTable = $('#dt_basic_neworderinfo').DataTable({
				"dom" :"t" + "<'dt-toolbar-footer'<'col-sm-6 col-xs-12'i><'col-xs-12 col-sm-6 text-right'lp>>",
				"autoWidth" : true,
				"ordering" : true,
				"serverSide" : true,
				"processing" : true,
				"searching" : false,
				"retrieve":true,
				"pageLength" : '${pageLength!"5"}',
				"lengthMenu" : [5, 10, 15, 20, 25, 30 ],
			 	"language": {
					"url" : getContext() + "public/dataTables.cn.txt"
				},
				"ajax":{
					"url" : getContext() + "neworderinfo/data",
					"type":"POST",
					"data" :function(param){
						<@params tit></@params>
						param.order_id = $("#neworderid").val();
						param.order_status = $("#neworderStatus").val();
						param.adminflag = $("#newadminflag").val();
						param.useruuid = $("#newuseruuid").val();
						param.starttime = $("#startTime").val();
						param.endtime = $("#endTime").val();
// 						param.score1 = $("#score1").val();
// 						param.score2 = $("#score2").val();
					}
				},
				"paging" :true,
				"pagingType" :"bootstrap",
				"lengthChange" : true,
				"order" : [[ "3", "desc"]],
				"columns" : [
						<#if title??>
							<#list dataRows?keys as key>
								<#if key == "receive_user_mobile">
									{"title" : "${dataRows[key]}",  "data" : "null","defaultContent":"","render":function(a,s,d){
										return window.parent.hiddenPhone(d.receive_user_mobile);
									}},
								<#elseif key == "order_id">
									{"title" : "${dataRows[key]}",  "data" : "null","defaultContent":"","render":function(a,s,d){
// 										return '<a onclick=getOrderInfo(\''+d.orderid+'\',this);>' + d.orderid + '</a>';
										return '' + d.orderid + '';
									}},
								<#elseif key != "call_session_uuid">
									{"title" : "${dataRows[key]}",  "data" : "${key}","defaultContent":""},
								</#if>
							</#list>
							{
							    "title": "是否接通",
							    "data": "put_through"
							},
							{
							    "title": "接通时长(秒)",
							    "data": "bridgesec"
							},
							{ "title" : "操作",  "data" : "null",  "orderable":false,
							  "render": function(data,type,full){
// 								  <a onclick=recordDestNumber(\''+full.call_session_uuid+'\',this);>详情</a>
								  // 判断是否是管理员(不是管理员不能质检)
								  if(i == 1){
									  if(full.bridgesec > 0 && full.put_through == '接通'){
										  return '&nbsp;&nbsp;<a onclick=recordPlayItem(\''+full.call_session_uuid+'\');>播放</a>'
				    						 +'&nbsp;&nbsp;<a onclick=qualityCheck(\''+full.call_session_uuid+'\',\''+full.uuid+'\',\''+full.orderid+'\');>订单质检</a>'
				    						 +'&nbsp;&nbsp;<a onclick=editModel(\''+full.uuid+'\',\''+full.orderid+'\');>修改</a>'
				    						 +'&nbsp;&nbsp;<a onclick=deleteInfo(\''+full.uuid+'\',\''+full.orderid+'\');>删除</a>'; 
									  }
									  return '&nbsp;&nbsp;<a onclick=qualityCheck(\''+full.call_session_uuid+'\',\''+full.uuid+'\',\''+full.orderid+'\');>订单质检</a>' 
									  +'&nbsp;&nbsp;<a onclick=editModel(\''+full.uuid+'\',\''+full.orderid+'\');>修改</a>'
									  +'&nbsp;&nbsp;<a onclick=deleteInfo(\''+full.uuid+'\',\''+full.orderid+'\');>删除</a>';
								  }else{
									  if(full.bridgesec > 0 && full.put_through == '接通'){
										  return '&nbsp;&nbsp;<a onclick=recordPlayItem(\''+full.call_session_uuid+'\');>播放</a>'
				    						 +'&nbsp;&nbsp;<a onclick=editModel(\''+full.uuid+'\',\''+full.orderid+'\');>修改</a>'
				    						 +'&nbsp;&nbsp;<a onclick=deleteInfo(\''+full.uuid+'\',\''+full.orderid+'\');>删除</a>'; 
									  }
									  return '&nbsp;&nbsp;<a onclick=editModel(\''+full.uuid+'\',\''+full.orderid+'\');>修改</a>'
									  +'&nbsp;&nbsp;<a onclick=deleteInfo(\''+full.uuid+'\',\''+full.orderid+'\');>删除</a>';
								  }
							   },
							   "width" : "15%"
							}
							<#else>
							{}
						</#if>
					]
				});
			});
			
			
			/* 详情  */
			function recordDestNumber(uuid, th){
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
		    }
			
			/* 点击订单编号  */
			function getOrderInfo(orderid, th){
				var target = $(th);
		   	    var url = getContext() + "neworderinfo/getOrderInfo";
		   	    $.post(url, {orderid : orderid}, function(data) {
	               if(data.ext){
		               target.attr("rel", "popover");
		               target.attr("data-placement", "right");
		               target.attr("data-original-title", "<h6>订单信息</h6>");
		               target.attr("data-html", true);
		               target.attr("data-content", data.ext);
		               $("[rel='popover']").popover();
	                }
		   	    },'json');
			}
			
			/* 播放  */
		    function recordPlayItem(uuid) {
		        var url = getContext() + "record/player";
		        $.post(url, {callsessionuuid : uuid},function(data) {
		            $("#tmpNewOrderInfo").append(data);
		        });
		    }
			
			/* 质检 */
			function qualityCheck(callSessionUUid, uuid, orderid){
				
// 				var requestUrl = getContext() + "record/get"; 
// 				$.post(requestUrl,{uid : uuid},function(data){
// 					$("#tmpNewOrderInfo").append(data);
// 				});
				
				var requestUrl = getContext() + "neworderinfo/get"; 
				$.post(requestUrl,{callSessionUUid : callSessionUUid, uuid : uuid, orderid : orderid, operation : "quality"},function(data){
					$("#tmpNewOrderInfo").append(data);
				});
				
// 				if(uuid != ''){
// 					var requestUrl = getContext() + "record/get"; 
// 					$.post(requestUrl,{uid : uuid},function(data){
// 						$("#tmpNewOrderInfo").append(data);
// 					});
// 				}else{
// 					alert("抱歉,此数据暂不能质检！！");
// 				}
			}
	
			/* 刷新  */
			function getResult(){
				$('#dt_basic_neworderinfo').DataTable().ajax.reload(null,false);;
			}
			
			function editModel(uuid,orderid){
				
// 				/* 检查是否已有产品   */
// 				$.post(getContext() + "neworderinfo/checkPros", function(data){
// 					if(data.success){
						$("#tmpNewOrderInfo").empty();
						$.post(getContext() + "neworderinfo/get", {uuid : uuid,orderid : orderid}, function(data){
							$("#tmpNewOrderInfo").append(data);
						});
// 					}else{
// 						alert("抱歉,暂没有产品,无法添加订单！");
// 					}
// 				},"json");
			}
			
			/* 添加订单字段    */
			$("#addOrderInfoField").click(function(){
				$("#tmpNewOrderInfo").empty();
				$.post(getContext() + "design/edit", {tablename:"order_info"}, function(data){
					$("#tmpNewOrderInfo").append(data);
				});
			});
	
			/* 删除  */
			function deleteInfo(uuid,orderid){
				$.SmartMessageBox({
					title : "删除",
					content : "该操作将执行删除操作，确定执行？",
					buttons : "[No][Yes]",
				}, function(ButtonPressed) {
					if (ButtonPressed === "Yes") {
						var url = getContext() + "neworderinfo/delete";
						$.post(url,{uuid:uuid,orderid:orderid},function(data){
							if(data.success){
								$.smallBox({
									title : "操作成功",
									content : "<i class='fa fa-clock-o'></i> <i>删除成功...</i>",
									color : "#659265",
									iconSmall : "fa fa-check fa-2x fadeInRight animated",
									timeout : 2000
								});
								
								orderinfoTable.ajax.reload(null,false);;
								
							}else{
								$.smallBox({
									title : "Callback function",
									content : "<i class='fa fa-clock-o'></i> <i>You pressed No...</i>",
									color : "#C46A69",
									iconSmall : "fa fa-times fa-2x fadeInRight animated",
									timeout : 2000
								});
							}
						},"json").error(function(){
								$.smallBox({
									title : "操作失败",
									content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>删除时出现异常...</i>",
									color : "#C46A69",
									iconSmall : "fa fa-times fa-2x fadeInRight animated"
								});
							});
					}
					if (ButtonPressed === "No") {
						$.smallBox({
							title : "取消",
							content : "<i class='fa fa-clock-o'></i> <i>操作已经被取消...</i>",
							color : "#659265",
							iconSmall : "fa fa-times fa-2x fadeInRight animated"
						});
					}
				});
	
				orderinfoTable.ajax.reload(null,false);;
			}
			
			function exportdata(){
				
				var url = getContext() + "neworderinfo/exportdata?";
				
				var order_id = $("#neworderid").val();
				var order_status = $("#neworderStatus").val();
				var adminflag = $("#newadminflag").val();
				var useruuid = $("#newuseruuid").val();
				var starttime = $("#startTime").val();
				var endtime = $("#endTime").val();
				
				if(starttime!= null && starttime!=''){url += "&starttime=" + starttime ;}
				if(endtime!= null && endtime!=''){url += "&endtime=" + endtime ;}
				url += "&order_status=" + order_status;
				url += "&order_id=" + order_id;
				url += "&adminflag=" + adminflag;
				url += "&useruuid=" + useruuid;
				
				<#if tit??>
					<@listParameter tit></@listParameter>
				</#if>
				
				window.location.href = url;
			}
			$("#neworderStatus").select2({
	           width: '99%'
	        });
		</script>
