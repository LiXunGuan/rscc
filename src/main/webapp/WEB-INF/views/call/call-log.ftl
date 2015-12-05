<style>
.row .label.col.col-1 {
	text-align: right;
	text-justify: inter-ideograph;
}
</style>
<div id="tmpDiv"></div>
<div id="userContent" style="">
	<!-- HEADER -->
	<!-- END RIBBON -->
	<!-- widget grid -->
	<section id="widget-grid-call-out" class="">

		<div class="row">
			<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">

				<div class="jarviswidget jarviswidget-color-darken" id="wid-id-0"
					data-widget-editbutton="false">

					<header>
						<span class="widget-icon"><i class="fa fa-table"></i></span>
						<#if level?? && level=="agent">
							<h2>我的客服记录</h2>
						<#else>
							<h2>部门客服记录</h2>
						</#if>
					</header>
					<div>
						<div class="widget-body no-padding">
								<div class="widget-body-toolbar">
									<div class="smart-form">
										<section>
											<div class="row">
												<@showLabel maps 3></@showLabel>
											</div>
										</section>
									<table id="callLogTable" class="table table-striped table-bordered table-hover" data-order='[[ 0, "asc" ]]' width="100%"></table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</article>
		</div>
	</section>
</div>



<script type="text/javascript">
	
	var taskTable = "";
		$(document).ready(function() {
			$("#in_out_flag").select2({
				width:"100%"
			});
			    $('#callLogTable').DataTable({
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
			    	"url" : getContext() + "calllog/data",
			    	"type":"POST",
			    	"data" :function(param){
			       		param.projectUuid = $("#projectName").val();
			       		<#if level??>
			       			param.menutype = "${level}";
			       		</#if>
			       		/* param.taskTable = $("#taskName").val(); */
			       		<@params selects></@params>
			       	}
			    },
			 	"paging" :true,
				"pagingType" :"bootstrap",
				"lengthChange" : true,
				"order" : [[ "0", "desc"]],
				"columns" : [
					   <#list columns?keys as k>
					   		
						   <#if hiddenPhone??>
							   <#if k == "call_phone">
							   		{ "title" : "${columns[k]}","data" : "null","defaultContent":"","render":function(a,s,d){
							   			return window.parent.hiddenPhone(d.call_phone);
							   		}},
							   	<#elseif k == "in_out_flag">
							   		{ "title" : "${columns[k]}","data" : "${k}","defaultContent":"","render":function(a,s,d){
							   			if("" == d.in_out_flag){
							   				return "未呼通";
							   			}else{
							   				return d.in_out_flag;
							   			}
							   		}},
							   	<#else>
							   		{ "title" : "${columns[k]}","data" : "${k}","defaultContent":""},
							   	</#if>
						   	<#else>
							   	{ "title" : "${columns[k]}","data" : "${k}","defaultContent":""}, 
						   	</#if>
						   
					   	</#list>
					   { 
						   "title" : "操作",
						   "data" : "null",
						   "render": function(data,type,full){
							   if(full.talk_time != undefined && full.talk_time == 0 && "${level}" == "agent"){
								   return "<a onclick='updateCalllog(\""+full.uid+
								   "\");'>修改</a>";
							   }else if(full.talk_time != undefined && full.talk_time == 0 && "${level}" != "agent"){
								   return "<a onclick='updateCalllog(\""+full.uid+
								   "\");'>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='deleteCalllog(\""+full.uid+
										   "\");'>删除</a>&nbsp;&nbsp;&nbsp;&nbsp";
							   }else if(full.talk_time != undefined && full.talk_time > 0 && "${level}" != "agent"){
								   return "<a onclick='updateCalllog(\""+full.uid+
								   "\");'>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='deleteCalllog(\""+full.uid+
										   "\");'>删除</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='playCallLog(\""+full.uid+
										   "\");'>播放</a>";
							   }else if(full.talk_time != undefined && full.talk_time > 0 && "${level}" == "agent"){
								   return "<a onclick='updateCalllog(\""+full.uid+
								   "\");'>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='playCallLog(\""+full.uid+
										   "\");'>播放</a>";
							   }else if(full.talk_time == undefined && "${level}" == "agent"){
								   return "<a onclick='updateCalllog(\""+full.uid+
								   "\");'>修改</a>";
							   }else if(full.talk_time == undefined && "${level}" != "agent"){
								   return "<a onclick='updateCalllog(\""+full.uid+
								   "\");'>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='deleteCalllog(\""+full.uid+
										   "\");'>删除</a>&nbsp;&nbsp;&nbsp;&nbsp";
							   }
						   }
					   }
					],
			});
		    
		});
		
		/* 查询结果 */
		function getResult(){
			$('#callLogTable').DataTable().ajax.reload(null,false);;
		}
		
		/* 播放 */
		function playCallLog(uid){
			var url = getContext() + "calllog/player";
	        $.post(url, {uid : uid},function(data) {
	            $("#userContent").append(data);
	        });
		}
		
		function deleteTaskResult(){
			$.SmartMessageBox({
				title : "删除",
				content : "该操作将执行删除操作，确定执行？",
				buttons : "[No][Yes]",
				//input : "text",
				//placeholder : "请填写备注:"
			}, function(ButtonPressed, Value) {
				if (ButtonPressed === "Yes") {
				var data = {
					projectUuid: $("#projectName").val(),
					tableName : $("#taskName").val()
				}
			    $.post(getContext()+'data/task/deleteTaskResult',data,function(data){
					if(data.success){
						$.smallBox({
							title : "操作成功",
							content : "<i class='fa fa-clock-o'></i> <i>删除成功...</i>",
							color : "#659265",
							iconSmall : "fa fa-check fa-2x fadeInRight animated",
							timeout : 2000
						});
						$("#taskName").val("");
						$("#projectName").change();
						$('#callLogTable').DataTable().ajax.reload(null,false);;
// 						$("#taskName[value=" + $("#taskName").val() + "]").remove();
					}else{
						$.smallBox({
							title : "操作失败",
							content : "<i class='fa fa-clock-o'></i> <i>删除失败...</i>",
							color : "#C46A69",
							iconSmall : "fa fa-times fa-2x fadeInRight animated",
							timeout : 2000
						});
					}
					$("table.dataTable").DataTable().ajax.reload(null,false);;
				},"json").error(function(){
					$.smallBox({
						title : "操作失败",
						content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>添加时出现异常...</i>",
						color : "#C46A69",
						iconSmall : "fa fa-times fa-2x fadeInRight animated"
						});
					});
				}
				if (ButtonPressed === "No") {
					$.smallBox({
						title : "取消操作",
						content : "<i class='fa fa-clock-o'></i> <i>您取消了该操作</i>",
						color : "#C46A69",
						iconSmall : "fa fa-times fa-2x fadeInRight animated",
						timeout : 2000
					});
				}
			});
		}
		
		function updateCalllog(uuid){
			
// 			$.post(getContext() + "calllog/change",{uuid:uuid,tableName:$("#taskName").val()},function(data){
			$.post(getContext() + "calllog/change",{uuid:uuid,tableName:"sys_call_log"},function(data){
// 			$("#userContent").empty();
				$("#tmpDiv").append(data);
			});
		}
		
		function deleteCalllog(uuid){
			$.SmartMessageBox({
				title : "删除",
				content : "该操作将执行删除操作，确定执行？",
				buttons : "[No][Yes]",
				//input : "text",
				//placeholder : "请填写备注:"
			}, function(ButtonPressed, Value) {
				if (ButtonPressed === "Yes") {
					$.post(getContext() + "calllog/delete",{"uuid":uuid,"tableName":$("#taskName").val()},function(data){
						if(data.success){
							$.smallBox({
								title : "操作成功",
								content : "<i class='fa fa-clock-o'></i> <i>删除成功...</i>",
								color : "#659265",
								iconSmall : "fa fa-check fa-2x fadeInRight animated",
								timeout : 2000
							});
						}else{
							$.smallBox({
								title : "操作失败",
								content : "<i class='fa fa-clock-o'></i> <i>删除失败...</i>",
								color : "#C46A69",
								iconSmall : "fa fa-times fa-2x fadeInRight animated",
								timeout : 2000
							});
						}
						$('#callLogTable').DataTable().ajax.reload(null,false);;
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
						title : "取消操作",
						content : "<i class='fa fa-clock-o'></i> <i>您取消了该操作</i>",
						color : "#C46A69",
						iconSmall : "fa fa-times fa-2x fadeInRight animated",
						timeout : 2000
					});
				}
			});
		}
		
		function addCalllog(){
			$.post(getContext() + "calllog/add",function(data){
				$("#userContent").append(data);
			});
		}

	</script>
	
	<!-- 提交参数 -->
<#macro params listParam>
	<#if listParam??>
		<#list listParam?keys as p>
			param.${p} = $("#${p}").val();
		</#list>
	</#if>
</#macro>


<#macro showLabel maps count>
	<#list maps?keys as key>
		<#if (maps[key].columnType) == "DATE" >
			<label class="label col col-1">${maps[key].columnName}</label>
			<div class="input col col-2">
				<label class="input">
					<input name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}" value="" class="date"/>
				</label>
				<script type="text/javascript">
					$(document).ready(function() {
						$("#${(maps[key].columnNameDB)!''}").daterangepicker({
							format: 'YYYY-MM-DD HH:mm',
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
// 									console.log($("#start_date").val());
// 									$("#start_date").val(start.toJSON()+","+end.toJSON());
							}).on('cancel.daterangepicker',function(ev, picker){
								$("#${(maps[key].columnNameDB)!''}").val('');
									getResult();
							}).on('apply.daterangepicker',function(ev, picker){
									getResult();
							});
						});
				</script>
			</div>
		<#elseif (maps[key].columnType) == "ENUM" >
			<#assign enumStr = (maps[key].characterProperty)?string>
				<label class="label col col-1">${maps[key].columnName}</label>
				<div class="form-group col col-lg-2">
					<select name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}" class="select2" onchange="getResult();">
					 	<option value="">----请选择----</option>
						<#list enumStr?replace("，",",")?split(",") as a>
						 	<option value="${a!''}">${a!''}</option>
						</#list>
					</select>
				</div>
		<#else>
			<label class="label col col-1">${maps[key].columnName}</label>
			<div class="form-group col col-lg-2">
				<label class="input">
					<input name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}"  value="${serialized!''}" >
					<input type="hidden" name="uid" id="uid" value="${uid!''}">
				</label>
			</div>
		</#if>
		<!-- 判断是否换行 -->
		<#if (key_index+1)%count == 0>
			<#if key_index+1 == 3>
				<label class="btn btn-sm btn-primary" onclick="getResult();">查询</label>
			</#if>
			
			</div>
			</section>
			<section>
			<div class="row">
		</#if>
	</#list>
</#macro>