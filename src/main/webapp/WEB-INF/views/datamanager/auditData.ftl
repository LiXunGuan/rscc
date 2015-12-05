	<div id="intentContent" style="">
		<section id="widget-grid-intentData" class="">
			<div class="row">
				<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="jarviswidget jarviswidget-color-darken" data-widget-editbutton="false">
						<header>
							<span class="widget-icon"><i class="fa fa-table"></i></span>
							<h2>待审批客户</h2>
						</header>
						<div>
							<div class="widget-body no-padding">
								<div class="widget-body-toolbar">
									<div class="smart-form">
											<div class="row" style="margin:0;">
												
												<label class="label col">选择部门</label> 
												<label class="input col col-2"> 
													<select id="department" class="select2" onchange="getDepartmentResult();">
														<#list alldept as d>
															<#list depts as s>
																<#if s==d.uid>
																	<option value="${d.uid}">${d.departmentName}</option>
																</#if>	
															</#list>
														</#list>
													</select>
												</label>
												
												<label class="label col">选择批次</label> 
												<label class="input col col-2"> 
													<select id="databatch" class="select2" onchange="getResult();">
														<option value='' selected='selected'>全部批次</option>
													</select> 
												</label>
											
												<label class="btn btn-sm btn-primary" onclick="getResult();">查询</label>
											</div>
											<table id="dt_basic_intentData" class="table table-striped table-bordered table-hover"  data-order='[[ 0, "asc" ]]' width="100%"></table>
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
	
		$(document).ready(function() {
			$("#department").select2({
				width:"100%"
			});
			$("#databatch").select2({
				width:"100%"
			});
			initTable();
			
		});
		
		function initTable(){
			oTable = $('#dt_basic_intentData').dataTable({
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
			    	"url" : getContext() + "audit/data",
			    	"type":"POST",
			    	"data" :function(param){
			    		param.deptUuid = $("#department").val();
			    		param.batchUuid = $("#databatch").val();
			    	}
			    },
			    "drawCallback":function(){
			    	if(flag){
			    		flag = false;
				    	$("#databatch").children("option").remove();
				    	var data = $('#dt_basic_intentData').DataTable().ajax.json();
				    	var batch = data==undefined?0:data.batch;
				    	$("#databatch").append("<option value='' selected='selected'>全部批次</option>");
						for(var b in batch){
							$("#databatch").append("<option value='"+batch[b].uid+"'>"+batch[b].batchName+"</option>");
						}
						$("#databatch").change();
			    	}
			    },
			 	"paging" :true,
				"pagingType" :"bootstrap",
				"lengthChange" : true,
				"order" : [[ "2", "desc"]],
				"columns" : [
					   { "title" : "批次id", "data" : "batchUuid","visible":false},
					   { "title" : "批次名", "data" : "batchName"},
					   { "title" : "电话号码", "data" : "phoneNumber"},
					   { "title" : "领用人id", "data" : "ownUser","visible":false},
					   { "title" : "领用人", "data" : "NownUser"},
					   { "title" : "领用时间", "data" : "ownUserTimestamp"},
					   { "title" : "呼叫次数", "data" : "callCount"},
// 					   { "title" : "最后一次通话结果", "data" : ""},
					   {
							"title" : "最后呼叫结果",
							"data" : "lastCallResult",
							"render" : function(data, type, full) {
								if (full.callCount == 0) {
									return "无";
								}
								if (full.lastCallResult) {
									return "接通";
								} else {
									return "未接通";
								}
							}
						},
					   { "title" : "最后一次通话时间", "data" : "lastCallTime"},
					   { "title" : "意向类型id", "data" : "intentType","visible":false},
					   { "title" : "意向类型", "data" : "intentTypeName"},
					   { "title" : "意向时间", "data" : "intentTimestamp"},
					   { "title" : "操作", "data" : null,"visible":true,
						 "render":function(data,type,full){
							 return "<a onclick='rejectAudit(\""+full.batchUuid+"\",\""+full.phoneNumber+"\")'>驳回</a>&nbsp;&nbsp;" + 
							 	"<a onclick='acceptAudit(\""+full.batchUuid+"\",\""+full.phoneNumber+"\")'>通过</a>";
					   }}
					],
			});
		}
// 				content : "<h2>该操作会提交一条意向客户转为成交客户的申请，需要有审核权限的用户进行审核</h2>",
		function rejectAudit(batchUuid, phonenumber) {
			$.SmartMessageBox({
				title : "驳回审批",
				content : "<h2>该操作会驳回审批</h2>",
				buttons : "[No][Yes]",
			}, function(ButtonPressed) {
				if (ButtonPressed === "Yes") {
					$.post(getContext()+"audit/reject",{department:$("#department").val(),phoneNumber:phonenumber,batchUuid:batchUuid},function(data){
						 if(data.success){
			                    $.smallBox({
			                        title : "操作成功",
			                        content : "<i class='fa fa-clock-o'></i> <i>删除成功...</i>",
			                        color : "#659265",
			                        iconSmall : "fa fa-check fa-2x fadeInRight animated",
			                        timeout : 2000
			                    });
			                    getResult();
			                }else{
			                    $.smallBox({
			                        title : "操作失败",
			                        content : "<i class='fa fa-clock-o'></i> <i>删除失败...</i>",
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
			});
		}
			
		function acceptAudit(batchUuid, phonenumber){
			$.SmartMessageBox({
				title : "通过审批",
				content : "该操作会把该条意向客户转为成交客户",
				css : "bg-color-green",
				buttons : "[No][Yes]",
			}, function(ButtonPressed) {
				if (ButtonPressed === "Yes") {
					$.post(getContext()+"audit/accept",{department:$("#department").val(),phoneNumber:phonenumber,batchUuid:batchUuid},function(data){
						 if(data.success){
			                    $.smallBox({
			                        title : "操作成功",
			                        content : "<i class='fa fa-clock-o'></i> <i>操作成功...</i>",
			                        color : "#659265",
			                        iconSmall : "fa fa-check fa-2x fadeInRight animated",
			                        timeout : 2000
			                    });
			                    getResult();
			                }else{
			                    $.smallBox({
			                        title : "操作失败",
			                        content : "<i class='fa fa-clock-o'></i> <i>操作失败...</i>",
			                        color : "#C46A69",
			                        iconSmall : "fa fa-times fa-2x fadeInRight animated",
			                        timeout : 2000
			                    });
			                }
					},"json").error(function(){
						$.smallBox({
							title : "操作失败",
							content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作时出现异常...</i>",
							color : "#C46A69",
							iconSmall : "fa fa-times fa-2x fadeInRight animated"
						});
					});
				}
			});
		}
		
		var flag = true;
		// 刷新
		function getResult(){
			$('#dt_basic_intentData').DataTable().ajax.reload(null,false);
		}
		function getDepartmentResult(){
			flag = true;
			$("#databatch").val("");
			$('#dt_basic_intentData').DataTable().ajax.reload(null,false);
		}

		
</script>
	
