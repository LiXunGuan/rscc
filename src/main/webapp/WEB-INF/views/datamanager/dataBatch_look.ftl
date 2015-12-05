<div class="modal fade" id="dialog_dataImport">
	<div class="modal-dialog" style="width: 75%;">
		<div class="modal-content">
			<div class="modal-header">
<!--				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>   -->
				<h4 class="modal-title">数据预览——当前数据源：${entry.batchName}，共有数据<font color="red" id="dataCount">${entry.dataCount}</font>条，已领用<font color="red" id="ownCount">${entry.ownCount}</font>条</h4>
			</div>
			<div class="modal-body">
				<div class="row" style="margin:0;position:relative;">
				<table cellspacing="0" cellpadding="0" border="0" class="ui-pg-table navtable" id="toolBox">
					<tbody>
						<tr>
<!--							<td class="ui-pg-button ui-corner-all" title="" id="allot" data-original-title="分配数据到个人">  
								<a class="btn btn-sm btn-primary" id="allot" data-toggle="popover">
									分配数据
								</a>
							</td>     
							<td class="ui-pg-button ui-state-disabled" style="width:6px;" data-original-title="" title="">
								<span class="ui-separator"></span>
							</td>
							<td class="ui-pg-button ui-corner-all" title="" id="recover" data-original-title="Delete selected row" onclick="batRecoveryData();">
								<div class="btn btn-sm btn-success">
								<span class="">回收数据</span>
								</div>
							</td>   -->
<!-- 							<td class="ui-pg-button ui-state-disabled" style="width:6px;" data-original-title="" title=""> -->
<!-- 								<span class="ui-separator"></span> -->
<!-- 							</td> -->
							<td>
								<label class="btn btn-sm btn-danger" onclick="batchDeleteItem()">批量删除</label>
							</td>
						</tr>
					</tbody>
				</table>
				</div>
				<div class="row">
					<article class="col-lg-12 col-lg-12 col-lg-12 col-lg-12">
						<div class="jarviswidget jarviswidget-color-darken" id="wid-id-0" data-widget-editbutton="false">
							<header>
								<span class="widget-icon"> <i class="fa fa-table"></i>
								</span>
								<h2>条目信息</h2>
							</header>
							<div>
								<div class="widget-body no-padding">
									<div class="widget-body-toolbar">
										<div class="smart-form">
											<section></section>
												<section>
													<div class="row">
														<input type="hidden" name="operation" id="operation" value="${(operation)!''}" />
														
														<input type="hidden" name="batchUuid" id="batchUuid" value="${(entry.dataTable)!''}" />
														
														<input type="hidden" name="customerUuid" id="customerUuid" value="${customerUuid!''}" />
														
														<label class="label col">电话号码</label>
														<label class="input col col-2">
															<input type="text" name="phoneNumber" id="phoneNumber" value="" />
														</label>
														<label class="label col">归属部门</label>
															<label class="input col col-2">
																<select style="width:100%" class="select2" name="ownDepartment" id="ownDepartment" onchange="getResultLook();">
																	<option value="" >请选择归属部门</option>
																	<#if  deptbeans??>
																		<#list deptbeans as d>
																			<option value="${d.uid}">${d.departmentName}</option>
																		</#list>
																	</#if>
																	<option value="global_share" <#if ownDepartment?? && ownDepartment=='global_share'>selected="selected"</#if>>共享池数据</option>
																	<option value="notnull" <#if ownDepartment?? && ownDepartment=='notnull'>selected="selected"</#if>>已分配部门数据</option>
																	<option value="nodept">未分配部门数据</option>
																</select>
															</label>
														<label class="label col">归属人</label>
														<label class="input col col-2">
															<select style="width:100%" class="select2" name="ownUser" id="ownUser" onchange="getResultLook();">
																<option value="" selected="selected">请选择归属人</option>
																<#if  users??>
																	<#list users as u>
																		<option value="${u.uid}">${u.userDescribe}</option>
																	</#list>
																</#if>
																<option value="noagent">未分配坐席数据</option>
															</select>
														</label>
														<label class="btn btn-sm btn-primary" id="doFilter">查询</label>
													</div>
												</section>
												
												<section>
													<div class="row">
														<label class="label col">意向类型</label>
														<label class="input col col-2">
															<select class="select2" name="intentType" id="intentType" onchange="getResultLook();">
																<option value="${intentType!''}" selected="selected">请选择意向类型</option>
																<#if intents??>
																	<#list intents as i>
																		<option value="${i.uid}">${i.intentName}</option>
																	</#list>
																</#if>
															</select>
														</label>
														
<!-- 													</div> -->
<!-- 												</section> -->
												
<!-- 												<section> -->
<!-- 													<div class="row"> -->
														<label class="label col">无效类型</label>
														<label class="input col col-2">
															<!-- onchange="getType();" -->
															<select class="select2" id="invalid" style="height: 32px;" onchange="getType();">
																<option value="">请选择无效类型</option>
																<option id="isFrozen" name="isFrozen" value="isFrozen" >已冻结</option>
																<option id="isAbandon" name="isAbandon" value="isAbandon">已废弃</option>
																<option id="isBlacklist" name="isBlacklist" value="isBlacklist">黑名单</option>
															</select>
														</label>
													</div>
												</section>
												<table id="dt_basic_look" class="table table-bordered table-hover"></table>
										</div>
									</div>
								</div>
							</div>
							
							<div class="modal-footer">
								<label data-dismiss="modal" class="btn btn-primary" id="doClose">关闭</label>
							</div>
						</div>
					</article>
				</div>
			</div>
			
		</div>

	</div>
</div>
<script src="${springMacroRequestContext.contextPath}/public/js/databatch/data-select.js"></script>

<script type="text/javascript">
	
	var dataCheckList={};
	 $(document).ready(function(){
			
			$('#ownUser').select2({
				width:"100%"
			});
						
			<#if ownDepartment??>
			$('#ownDepartment').select2({
				width:"100%"
			});
			<#else>
			$('#ownDepartment').select2({
				width:"100%"
			});
			</#if>
			
			$('#intentType').select2({
				width:"100%"
			});
			
			$('#invalid').select2({
				width:"100%"
			});
			
			
//			$('#allot').popover({
//				html:true,
//					content :'<form id="importAllotForm" action="${springMacroRequestContext.contextPath}/databatch/data/importAllot" method="post" class="smart-form" ><fieldset class="row" style="padding-top:10px;padding-bottom:10px;"><div class="form-group"><select name="dept" id="toid" style="height:30px" class="select2 alodept">' + <#if deptbeans??><#list deptbeans as dept> 
//				<#if usermapKey?seq_contains(dept.uuid)><#list usermap[dept.uuid] as u> 
//				'<option value="${u.uid}">[${u.departmentName}]${u.userDescribe}[${u.loginName}]</option>' +
//				</#list></#if></#list></#if>
//				'</select></div></fieldset>' + 
//				'<div class="form-actions"><div class="row"><div class="col col-6"><label class="btn btn-default btn-sm" onclick="$(\'#allot\').popover(\'hide\')">取消</button></div><div class="col col-6"><label class="btn btn-primary btn-sm" onclick="importAllot()">分配</label></div></div></div></form>'
//			});
//			
//			$("#toid").select2({
//	    		width:"120%",
//	    	});
			$("#invalid").val("${isType!''}").change();
			
	//$(document).ready---end
		});
		
	 	/* 查询结果 */
		function getResultLook(){
			$('#dt_basic_look').DataTable().ajax.reload();
		}		
		
		function getType(){
			getResultLook();
		}
		$("#invalid").change(getType);
		
		function importAllot(){
			var list = [];
			for(var i in dataCheckList) {
				if(dataCheckList[i]==true)
					list.push(i);
			}
			$.post("${springMacroRequestContext.contextPath}/databatch/data/importAllot",{batchid:$("#batchUuid").val(),dataid:list,toid:$("#toid").val()},function(data){
				
                    if(data.success){
                        $.smallBox({
                            title : "操作成功",
                            content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
                            color : "#659265",
                            iconSmall : "fa fa-check fa-2x fadeInRight animated",
                            timeout : 2000
                        });
                        // 更新页面的数据显示
	                    $.post(getContext() + "databatch/data/updateBatchDataDisplay", {batchUuid:$("#batchUuid").val()}, function(data){
	                    	$("#dataCount").text(data.dataCount);
	                    	$("#ownCount").text(data.ownCount);
	                    },"json");
                        $("#allot").popover("hide");
                        dataCheckList = {};
                        getResultLook();
                    }else{
                    	$("#allot").popover("hide");
                        $.smallBox({
                            title : "操作失败",
                            content : "<i class='fa fa-clock-o'></i> <i>操作失败</i>",
                            color : "#C46A69",
                            iconSmall : "fa fa-times fa-2x fadeInRight animated",
                            timeout : 2000
                        });
                    }
                    getResultLook();
			},"json");
		}
			

		
		$('#dialog_dataImport').modal("show");
		
		$("#doFilter").click(function(){
			dataBatchDataCondition = {
					phoneNumber:$("#phoneNumber").val(),
					ownDepartment:$("#ownDepartment").val(),
					ownUser:$("#ownUser").val(),
		    	};
			$("#dt_basic_look").DataTable().ajax.reload(null,false);
		});

		/* 关闭窗口的回调函数  */
		$('#dialog_dataImport').on('hide.bs.modal', function (e) {   /* hide 是关闭   调用后在关闭;hidden 是关闭后在调用； */
			$("#dialog_dataImport").remove();
		});
		
		function batchDeleteItem(){
			
	    	var list = [];
			for(var i in dataCheckList) {
				if(dataCheckList[i]==true){
					list.push(i);
				}
			}
			
			if(!list.length>0){
				alert("请先选择数据！");
				return;
			};
			
			$.SmartMessageBox({
		        title : "删除",
		        content : "确定批量删除该些数据信息吗？",
		        buttons : '[No][Yes]'
		    }, function(ButtonPressed) {
		        if (ButtonPressed === "Yes") {
		            var url = getContext() + "databatch/data/batchDelete";
		            $.post(url,{uuids:list, batchUuid:$("#batchUuid").val()},function(data){
		                if(data.success){
		                    $.smallBox({
		                        title : "操作成功",
		                        content : "<i class='fa fa-clock-o'></i> <i>删除成功...</i>",
		                        color : "#659265",
		                        iconSmall : "fa fa-check fa-2x fadeInRight animated",
		                        timeout : 2000
		                    });
		                    // 更新页面的数据显示
		                    $.post(getContext() + "databatch/data/updateBatchDataDisplay", {batchUuid:$("#batchUuid").val()}, function(data){
		                    	$("#dataCount").text(data.dataCount);
		                    	$("#ownCount").text(data.ownCount);
		                    },"json");
		                    $('#dt_basic_look').DataTable().ajax.reload(null,false);
		                    $('#dataDataTable').DataTable().ajax.reload(null,false);
		                }else{
		                    $.smallBox({
		                        title : "操作失败",
		                        content : "<i class='fa fa-clock-o'></i>" + data.msg,
		                        color : "#C46A69",
		                        iconSmall : "fa fa-times fa-2x fadeInRight animated",
		                        timeout : 5000
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

		function docheckall(){
			var dataBatchCondition = {
					batchUuid:"${(entry.uid)!''}",
					phoneNumber:$("#phoneNumber").val(),
					ownDepartment:$("#ownDepartment").val(),
					ownUser:$("#ownUser").val(),
					intentType:$("#intentType").val(),
					isFrozen : $("#invalid").val()=="isFrozen"?1:0,
					isAbandon : $("#invalid").val()=="isAbandon"?1:0,
					isBlacklist : $("#invalid").val()=="isBlacklist"?1:0
			};
			
		    var url = getContext()+'databatch/data/importAll';
		    $.post(url, dataBatchCondition, function(data){
		    	dataCheckList = {};
				$("#dataCheckInfo").text(data.items.length);
		        for(var i in data.items){
	                dataCheckList[data.items[i]]=true;
		        }
		        docheckallpage();
		    },"json");
		}

		function docheckallpage(){

		    $("[id^='dck']").each(function(){
		        if(!$(this).is(':checked')){
		            $(this).click();
		        }
		    });
		}

		function docancelAll(){

		    $("[id^='dck']").each(function(){
		        if($(this).is(':checked')){
		            $(this).click();
		        }
		    });

		    dataCheckList={};
		    $("#dataCheckInfo").text(0);
		}

		function docheck(uid){

		    if($("#dck"+uid).is(':checked')){
		        dataCheckList[uid]=true;
		    }else{
		        dataCheckList[uid]=false;
		    }
		    addCheckInfo();
		}
		
		function addCheckInfo(){
			var num=0;
			for(var i in dataCheckList) {
				if(dataCheckList[i]==true)
					num++;
			}
			$("#dataCheckInfo").text(num);
		}
		
		function getCheckNum(){
			var num=0;
			for(var i in dataCheckList) {
				if(dataCheckList[i]==true)
					num++;
			}
			return "<span id='dataCheckInfo'>" + num + "</span>";
		}
		
		function batRecoveryData() {
			
			var list = [];
			for(var i in dataCheckList) {
				if(dataCheckList[i]==true){
					list.push(i);
				}
			}
			
			if(!list.length>0){
				alert("请先选择数据！");
				return;
			};
			
			$.SmartMessageBox({
				title : "回收",
				content : "<h1>该操作将回收所选数据中已分配的数据，且无法找回，确定执行？</h1>",
				buttons : "[No][Yes]",
			}, function(ButtonPressed, Value) {
				if (ButtonPressed === "Yes") {
					var list = [];
					for(var i in dataCheckList) {
						if(dataCheckList[i]==true)
							list.push(i);
					}
					if(list.length>0){
					    $.post('${springMacroRequestContext.contextPath}/databatch/data/batRecoveryData',{uuids:list,batchUuid:$("#batchUuid").val()},function(data){
							if(data.success){
								$.smallBox({
									title : "操作成功",
									content : "<i class='fa fa-clock-o'></i> <i>操作成功...</i>",
									color : "#659265",
									iconSmall : "fa fa-check fa-2x fadeInRight animated",
									timeout : 2000
								});
								for(var i in list){
									checklist[list[i]]=false;
								}
								 // 更新页面的数据显示
			                    $.post(getContext() + "databatch/data/updateBatchDataDisplay", {batchUuid:$("#batchUuid").val()}, function(data){
			                    	$("#dataCount").text(data.dataCount);
			                    	$("#ownCount").text(data.ownCount);
			                    },"json");
								$("#dt_basic_look").DataTable().ajax.reload(null,false);
								$('#dataDataTable').DataTable().ajax.reload(null,false);
							}else{
								$.smallBox({
									title : "操作失败",
									content : "<i class='fa fa-clock-o'></i> <i>操作失败...</i>",
									color : "#C46A69",
									iconSmall : "fa fa-times fa-2x fadeInRight animated",
									timeout : 2000
								});
							}
						},"json");
					    $("#dt_basic_look").DataTable().ajax.reload(null,false);
					}
				}
			});
		}
		

</script>




