	<div id="intentContent" style="">
		<section id="widget-grid-intentData" class="">
			<div class="row">
				<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="jarviswidget jarviswidget-color-darken" data-widget-editbutton="false">
						<header>
							<span class="widget-icon"><i class="fa fa-table"></i></span>
							<h2>意向客户管理</h2>
						</header>
						<div>
							<div class="widget-body no-padding">
								<div class="widget-body-toolbar">
									<div class="smart-form">
											<div class="row" style="margin:0;">
												
												<label class="label col">选择部门</label> 
												<label class="input col col-2"> 
													<select id="department" class="select2" onchange="getDepartmentResult();">
														<option value="" selected='selected'>全部部门</option>
														<#if alldept??>
														<#list alldept as d>
															<#if depts??>
															<#list depts as s>
																<#if s==d.uid>
																	<option value="${d.uid}">${d.departmentName}</option>
																</#if>	
															</#list>
															</#if>	
														</#list>
														</#if>
													</select>
												</label>
												
												<label class="label col">选择批次</label> 
												<label class="input col col-2"> 
													<select id="databatch" class="select2" onchange="getResult();">
														<option value='' selected='selected'>全部批次</option>
													</select> 
												</label>
												
												<label class="label col">选择领用人</label> 
												<label class="input col col-2"> 
													<select id="userdata" class="select2" onchange="getResult();">
														<option value='' selected='selected'>全部用户</option>
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
			$("#userdata").select2({
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
			    	"url" : getContext() + "indent/data/data",
			    	"type":"POST",
			    	"data" :function(param){
			    		param.deptUuid = $("#department").val();
			    		param.batchUuid = $("#databatch").val();
			    		param.userID = $("#userdata").val();
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
							if(batch[b].uid == ""){
								batch[b].uid = "default";
							}
							$("#databatch").append("<option value='"+batch[b].uid+"'>"+batch[b].batchName+"</option>");
						}
						$("#databatch").change();
						
						//选择领用人
				    	$("#userdata").children("option").remove();
				    	var user = $('#dt_basic_intentData').DataTable().ajax.json();
				    	var users = user==undefined?0:user.users;
				    	$("#userdata").append("<option value='' selected='selected'>全部用户</option>");
						for(var u in users){
							$("#userdata").append("<option value='"+users[u].uid+"'>"+users[u].loginName+"--"+users[u].userDescribe+"</option>");
						}
						$("#userdata").change();
			    	}
			    },
			 	"paging" :true,
				"pagingType" :"bootstrap",
				"lengthChange" : true,
				"order" : [[ "1", "desc"]],
				"columns" : [
					   { "title" : "批次id", "data" : "batchUuid","visible":false},
					   { "title" : "批次名", "data" : "batchName"},
					   { "title" : "电话号码", "data" : "phoneNumber"},
// 					   { "title" : "领用人id", "data" : "ownUser","visible":false},
					   { "title" : "领用人", "data" : "ownUser"},
					   { "title" : "领用时间", "data" : "ownUserTimestamp"},
//  				   { "title" : "归属部门", "data" : "ownDept"},
					   { "title" : "呼叫次数", "data" : "callCount"},
// 					   { "title" : "最后一次通话结果", "data" : "lastCallResult"},
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
					   { "title" : "转意向时间", "data" : "intentTimestamp"},
					   { "title" : "操作", "data" : null,"visible":true,
						 "render":function(data,type,full){
							 var stat;
							if(full.beAudit=='1') {
								stat = "撤销审批";
							} else {
								stat = "提交审批";
							}
							 var detail = "<a onclick='getDetails(\""+full.ownUseruuid+"\",\""+full.phoneNumber+"\")'>修改</a>&nbsp;&nbsp;";
							 return detail
// 							 +"<a onclick='changeStatu(\""+full.batchUuid+"\",\""+full.phoneNumber+"\",\""+full.intentTypeName+"\")'>修改意向类型</a>&nbsp;&nbsp;"
// 							 +"<a onclick=putToSharePool('" + full.batchUuid + "','" + full.phoneNumber + "','" +"global_share" + "');>放入共享池</a>&nbsp;&nbsp;"
// 							 +"<a onclick=acceptAudit('" + full.batchUuid + "','" + full.phoneNumber + "');>提交审批</a>&nbsp;&nbsp;"
							 +"<a onclick=commitAudit('" + full.ownUseruuid + "','" + full.phoneNumber + "','" + (full.beAudit=='1'?0:1) + "');>" + stat + "</a>&nbsp;&nbsp;"
							 +"<a onclick=changeOwner('" + full.batchUuid + "','" + full.uid + "','" + full.ownUseruuid + "');>转移客户</a>";
					   }}
					],
			});
		}
		
		function getDetails(userUuid, phoneNumber){
			console.log(userUuid+"-=-----------------");
			window.parent.addTab("详情-" + phoneNumber,phoneNumber,JSON.stringify({number:phoneNumber,popType:'detail',controller:"phoneInfoDetail",userUuid:userUuid},"detail"));
		}
		
		function putToSharePool(batchuuid, phoneNumber,tag) {
			
			$.SmartMessageBox({
				title : "删除",
				content : "该操作会将该客户放入共享池，确定执行？",
				buttons : "[No][Yes]",
			}, function(ButtonPressed) {
				if (ButtonPressed === "Yes") {
					
					$.post(getContext() + "newuserdata/changeStatus", {
						"batchuuid" : batchuuid,
						"department" : $("#department").val(),
						"phoneNumber" : phoneNumber,
						"tag" : tag
					}, function(d) {
						if (d.success) {
							$.smallBox({
								title : "操作成功",
								content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
								color : "#659265",
								iconSmall : "fa fa-check fa-2x fadeInRight animated",
								timeout : 2000
							});
							
							$('#dt_basic_intentData').DataTable().ajax.reload(null,false);
						}
					}, "json");
				}
				
			});
		}
		
		function changeStatu(batchuuid, phoneNumber, intentType) {
			
			$.post(getContext() + "newuserdata/toChangeStatuPage", {
				"batchuuid" : batchuuid,
				"phoneNumber" : phoneNumber,
				"intentType" : intentType,
				"tag" : "intend"},function(d){
				$("#forModel").append(d);
			}); 
		}
		
		function changeOwner(batchUuid, dataUuid, ownerUuid) {
			
			$.post(getContext() + "newuserdata/changeOwner", {
				"batchUuid" : batchUuid,
				"dataUuid" : dataUuid,
				"ownerUuid" : ownerUuid,
				"department":$("#department").val()
				},function(d){
				$("#forModel").append(d);
			}); 
		}
		
		function toCstm(phonenumber,batchUuid){
			$.SmartMessageBox({
				title : "转为成交客户申请",
				content : "<h2>该操作会提交一条意向客户转为成交客户的申请，需要有审核权限的用户进行审核</h2>",
				buttons : "[No][Yes]",
			}, function(ButtonPressed) {
				if (ButtonPressed === "Yes") {
				$.post(getContext()+"indent/data/audit",{phonenumber:phonenumber,batchUuid:batchUuid},function(data){
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
		
		function commitAudit(userUuid, phoneNumber, stat) {
			$.post(getContext() + "indent/data/commitAudit", {
				"userUuid" : userUuid,
				"deptUuid" : $("#department").val(),
				"phoneNumber" : phoneNumber,
				"stat" : stat
			}, function(d) {
				if (d.success) {
					$.smallBox({
						title : "操作成功",
						content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
						color : "#659265",
						iconSmall : "fa fa-check fa-2x fadeInRight animated",
						timeout : 2000
					});
					
					$('#dt_basic_intentData').DataTable().ajax.reload(null,false);
				}
			}, "json");
		}
		
		function acceptAudit(batchUuid, phonenumber){
			$.SmartMessageBox({
				title : "转为成交客户",
				content : "该操作会把该条意向客户转为成交客户",
				buttons : "[No][Yes]",
			}, function(ButtonPressed) {
				if (ButtonPressed === "Yes") {
					$.post(getContext()+"audit/accept",{department:$("#department").val(),phoneNumber:phonenumber,batchUuid:batchUuid},function(data){
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
		
		
		var flag = true;
		// 刷新
		function getResult(){
			
			$('#dt_basic_intentData').DataTable().ajax.reload(null,false);
		}
		function getDepartmentResult(){
			flag = true;
			$("#databatch").val("");
			$("#userdata").val("");
			$('#dt_basic_intentData').DataTable().ajax.reload(null,false);
		}

		
</script>
	
