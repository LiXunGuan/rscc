<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/select2/select2.min.js"></script>
<div id="tmpDiv"></div>
<div id="userContent" style="">
	<!-- HEADER -->
	<!-- END RIBBON -->
	<!-- widget grid -->
	<section id="widget-grid-result" class="">

		<div class="row">
			<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">

				<div class="jarviswidget jarviswidget-color-darken" id="wid-id-0"
					data-widget-editbutton="false">

					<header>
						<span class="widget-icon"><i class="fa fa-table"></i></span>
						<h2>结果统计</h2>
					</header>
					<div>
						<div class="widget-body no-padding">
							<div class="widget-body-toolbar">
								<div class="smart-form">
									<section>
										<div class="row" style="margin: 0">
											<label class="label col">人员</label>
											<label class="select col col-1"> 
											<select name="taskTable" id="taskTable">
												<option value="">--请选择--</option> 
												<#if projects??>
												<#list projects as key>
												<option value="${key['uid']}">${key['projectName']}</option>
												</#list> 
												</#if>
											</select>
											</label> 
											<label class="label col">名称</label>
											<label class="input col col-1">
												<input type="text" name="itemName" id="itemName" value="" />
											</label>
											<label class="label col">号码</label>
											<label class="input col col-1">
												<input type="text" name="itemPhone" id="itemPhone" value="" />
											</label>
											<label class="label col">地址</label>
											<label class="input col col-2">
												<input type="text" name="itemAddress" id="itemAddress" value="" />
											</label>
											<label class="label col">其它信息</label>
											<label class="input col col-2">
												<input type="text" name="itemJson" id="itemJson" value="" />
											</label>
											<label class="label col">呼叫次数</label>
											<label class="input col col-1">
												<input type="text" name="callTimes" id="callTimes" value="" />
											</label>
											<label class="btn btn-sm btn-primary" onclick="getResult();">查询</label>
										</div>
									</section>
									<table id="oTable"
										class="table table-striped table-bordered table-hover"
										data-order='[[ 0, "asc" ]]' width="100%"></table>
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
			
			$("#taskTable").select2({
	    		width:"100%",
	    	});
			
			    $('#oTable').DataTable({
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
			    	"url" : getContext() + "data/${model}/data",
			    	"type":"POST",
			    	"data" :function(param){
			       		param.taskTable = $("#taskTable").val();
			       		/* param.taskTable = $("#taskName").val(); */
			       		param.itemName = $("#itemName").val();
						param.itemPhone = $("#itemPhone").val();
						param.itemAddress = $("#itemAddress").val();
						param.itemJson = $("#itemJson").val();
						param.dataSource = $("#dataSource").val();
						param.callTimes = $("#callTimes").val();
			       	}
			    },
			 	"paging" :true,
				"pagingType" :"bootstrap",
				"lengthChange" : true,
				"order" : [[ "1", "desc"]],
				"columns" : [
					    { "title" : "条目号码","data" : "itemPhone","defaultContent":"无号码"},
						{ "title" : "条目地址","data" : "itemAddress","defaultContent":"无地址"},
						{ "title" : "条目其它信息","data" : "itemJson","defaultContent":"无其它信息"},
						{ "title" : "数据来源","data" : "dataSource","defaultContent":"无"},
						{ "title" : "呼叫次数","data" : "callTimes","defaultContent":"无"},
						{ "title" : "分配时间","data" : "allocateTime","defaultContent":"未分配"},
					    { 
						   "title" : "操作",
						   "data" : "null",
						   "render": function(data,type,full){
							   return "<a onclick='update${model?cap_first}(\""+full.uid+
									   "\");'>添加</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='delete${model?cap_first}(\""+full.uid+
											   "\");'>删除</a>";
						   }
					   }
					],
			});
			    
		    $("#taskTable").change(function(){
		    	/* if($("#projectName").val()!=""){
	    			$.post(getContext() + "data/${model}/tasks",{"projectUuid":$("#projectName").val()},function(data){
	            		$("#taskName").empty();
	    				$("#taskName").append("<option value=''>---请选择任务---</option>");
	    				for(var i in data){
	    					$("#taskName").append("<option value='" + data[i].taskTable + "'>" + data[i].taskTable + "</option>")
	   					}
	   				},"json")
		    	}
		    	else {
		    		$("#taskName").empty();
    				$("#taskName").append("<option value=''>---请选择任务---</option>");
		    	} */
		    	$("table.dataTable").DataTable().ajax.reload(null,false);;
   			});
		    
		    /* $("#taskName").change(function(){
		    	$("table.dataTable").DataTable().ajax.reload(null,false);;
   			}); */
		    
		});
		
		/* 查询结果 */
		function getResult(){
			$("table.dataTable").DataTable().ajax.reload(null,false);;
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
					projectUuid: $("#taskTable").val(),
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
						$("#taskTable").change();
						$("table.dataTable").DataTable().ajax.reload(null,false);;
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
		
		function update${model?cap_first}(uuid){
			$.post(getContext() + "data/${model}/change",{"uuid":uuid,"tableName":$("#taskName").val()},function(data){
				$("#userContent").append(data);
			});
		}
		
		function delete${model?cap_first}(uuid){
			$.SmartMessageBox({
				title : "删除",
				content : "该操作将执行删除操作，确定执行？",
				buttons : "[No][Yes]",
				//input : "text",
				//placeholder : "请填写备注:"
			}, function(ButtonPressed, Value) {
				if (ButtonPressed === "Yes") {
					$.post(getContext() + "data/${model}/delete",{"uuid":uuid,"tableName":$("#taskName").val()},function(data){
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
						$("table.dataTable").DataTable().ajax.reload(null,false);;
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
		
		function add${model?cap_first}(){
			$.post(getContext() + "data/${model}/add",function(data){
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
