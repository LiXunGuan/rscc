
<div id="tmpDiv"></div>
<div id="userContent" style="">
	<!-- HEADER -->
	<!-- END RIBBON -->
	<!-- widget grid -->
	<section id="widget-grid-task" class="">

		<div class="row">
			<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">

				<div class="jarviswidget jarviswidget-color-darken" id="wid-id-0"
					data-widget-editbutton="false">

					<header>
						<span class="widget-icon"><i class="fa fa-table"></i></span>
						<h2>Standard Data Tables</h2>
					</header>
					<div>
						<div class="widget-body no-padding">
							<div class="widget-body-toolbar">
								<div class="smart-form">
									<section>
										<div class="row" style="margin: 0">
											<label
												class="select col col-2"> <select name="projectName"
												id="projectName">
													<option value="">---请选择项目---</option> <#if projects??>
													<#list projects as key>
													<option value="${key['uid']}">${key['projectName']}</option>
													</#list> </#if>
											</select>
											</label>
										<div class="row" style="margin: 0" style="padding:10px 0px">
											<#if selects??>
												<#list selects?keys as s>
													<label class="label col">${selects[s]}</label>
													<label class="input col col-1">
														<input type="text" name="${s}" class="form-control" id="${s}" value="" />
													</label>
												</#list>
											</#if>
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
			       		param.projectUuid = $("#projectName").val();
			       		<@params selects></@params>
			       	}
			    },
			 	"paging" :true,
				"pagingType" :"bootstrap",
				"lengthChange" : true,
				"order" : [[ "1", "desc"]],
				"columns" : [
					   <#list columns?keys as k>
					   	{ "title" : "${columns[k]}","data" : "${k}","defaultContent":""}, 
					   </#list>
					   { 
						   "title" : "操作",
						   "data" : "null",
						   "render": function(data,type,full){
							   return "<a onclick='update${model?cap_first}(\""+full.uid+
									   "\");'>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='delete${model?cap_first}(\""+full.uid+
											   "\");'>删除</a>";
						   }
					   }
					],
			});
			    
		    $("#projectName").change(function(){
		    	$("table.dataTable").DataTable().ajax.reload(null,false);;
   			});
		    
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
