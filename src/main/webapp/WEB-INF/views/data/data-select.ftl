<div class="modal fade" id="dialog_data">
	<div class="modal-dialog" style="width: 75%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
				<h4 class="modal-title">数据预览——当前数据源：${entry.containerName}，共有数据${entry.dataCount}条，已分配${entry.allocateCount}条</h4>
			</div>
			<div class="modal-body">
				<div class="row" style="margin:0;position:relative;">
				<table cellspacing="0" cellpadding="0" border="0" class="ui-pg-table navtable" id="toolBox">
				<tbody>
				<tr>
				<td class="ui-pg-button ui-corner-all" title="" id="batDel" data-original-title="Delete selected row" onclick="batDataDel();">
					<div class="btn btn-sm btn-danger">
					<span class="">删除选中项</span>
					</div>
				</td>
				<td class="ui-pg-button ui-state-disabled" style="width:6px;" data-original-title="" title="">
					<span class="ui-separator"></span>
				</td>
<!-- 				<td class="ui-pg-button ui-corner-all" title="" id="refresh" data-original-title="Reload Grid" onclick="getResult();"> -->
<!-- 					<div class="btn btn-sm btn-primary"> -->
<!-- 					<span class="">刷新内容</span> -->
<!-- 					</div> -->
<!-- 				</td> -->
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
											<div class="row">
												<section>
													<input type="hidden" name="currentDataTable" id="currentDataTable" value="${(entry.dataTable)!''}" />
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
													<label class="label col">归属人</label>
													<label class="input col col-1">
														<input type="text" name="itemOwner" id="itemOwner" value="" />
													</label>
													<label class="label col">呼叫次数</label>
													<label class="input col col-1">
														<input type="text" name="callTimes" id="callTimes" value="" />
													</label>
													<label class="btn btn-sm btn-primary" id="doFilter">筛选</label>
												</section>
											</div>
										</div>
									</div>
									<table id="dt_basic_select" class="table table-bordered table-hover"></table>
								</div>
							</div>
							
							<div class="modal-footer">
<!-- 								<label data-dismiss="modal" class="btn btn-primary" id="doImport" onclick="doImport()">导入</label> -->
								<label data-dismiss="modal" class="btn btn-primary" id="doClose">关闭</label>
							</div>
						</div>
					</article>
				</div>
			</div>
			
		</div>

	</div>
</div>


<script type="text/javascript">

		var itemCheckList={};

		var itemCondition = {
				itemTable:"${(entry.dataTable)!''}"
		};
		
		$('#dialog_data').modal("show");
	
		$("#doFilter").click(function(){
			itemCondition = {
					itemTable:$("#currentDataTable").val(),
					itemName:$("#itemName").val(),
					itemPhone:$("#itemPhone").val(),
					itemJson:$("#itemJson").val(),
					itemOwner:$("#itemOwner").val(),
					callTimes:$("#callTimes").val()
		    	};
			$("table.dataTable").DataTable().ajax.reload(null,false);;
		});

// 		function doFilter(){
// 			$("table.dataTable").DataTable().ajax.reload(null,false);;
// 		}
		
		function doImport(){
			var list = [];
			for(var i in itemCheckList) {
				if(itemCheckList[i]==true)
					list.push(i);
			}
			var data = {
				dataTable : $("#currentDataTable").val(),
				projectName: $("#currentProject").val(),
				taskName : $("#currentTask").val(),
				items : list
			}
		    $.post(getContext()+'data/data/importToProject',data,function(data){
				if(data.success){
					$.smallBox({
						title : "操作成功",
						content : "<i class='fa fa-clock-o'></i> <i>操作成功...</i>",
						color : "#659265",
						iconSmall : "fa fa-check fa-2x fadeInRight animated",
						timeout : 2000
					});
					$("table.dataTable").DataTable().ajax.reload(null,false);;
				}else{
					$.smallBox({
						title : "操作失败",
						content : "<i class='fa fa-clock-o'></i> <i>操作失败...</i>",
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
		
		/* 关闭窗口的回调函数  */
		$('#dialog_data').on('hide.bs.modal', function (e) {   /* hide 是关闭   调用后在关闭;hidden 是关闭后在调用； */
			$("#dialog_data").remove();
		});

		function removeThis(t,i){
		     $(t).parent().remove();

		    itemCheckList[i]=false;
		    $('#dck'+i).click();
		}

		function docheckall(){

		    var url = getContext()+'data/data/importAll';
		    $.post(url, itemCondition, function(data){
		    	itemCheckList = {};
				$("#dataCheckInfo").text(data.items.length);
		        for(var i in data.items){
	                itemCheckList[data.items[i]]=true;
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

		    itemCheckList={};
		    $("#dataCheckInfo").text(0);
		}

		function docheck(uid){

		    if($("#dck"+uid).is(':checked')){
// 		        itemCheckList[uid]=$("#ck"+uid).parent().next().text()
		        itemCheckList[uid]=true;
		    }else{
		        itemCheckList[uid]=false;
		    }
		    addCheckInfo();
		}
		
		function addCheckInfo(){
			var num=0;
			for(var i in itemCheckList) {
				if(itemCheckList[i]==true)
					num++;
			}
			$("#dataCheckInfo").text(num);
		}
		
		function getCheckNum(){
			var num=0;
			for(var i in itemCheckList) {
				if(itemCheckList[i]==true)
					num++;
			}
			return "<span id='dataCheckInfo'>" + num + "</span>";
		}
		
		$("#currentProject").change(function(){
	    	if($("#currentProject").val()!=""){
    			$.post(getContext() + "data/data/tasks",{"projectUuid":$("#currentProject").val()},function(data){
            		$("#currentTask").empty();
    				$("#currentTask").append("<option value=''>---请选择导入任务---</option>");
    				for(var i in data){
    					$("#currentTask").append("<option value='" + data[i].taskTable + "'>" + data[i].taskTable + "</option>")
   					}
   				},"json")
	    	}
	    	else {
	    		$("#currentTask").empty();
				$("#currentTask").append("<option value=''>---请选择导入任务---</option>");
	    	}
		});
		
		$("#currentTask").change(function(){
	    	if($("#currentTask").val()!=""){
    			$.post(getContext() + "data/data/items",{"projectUuid":$("#currentProject").val(),"taskName":$("#currentTask").val()},function(data){
    				for(var i in data.items){
    					itemCheckList[data.items[i]]=true;
    				}
    				$("table.dataTable").DataTable().ajax.reload(null,false);;
   				},"json")
	    	}
	    	else {
	    	}
		});
		
		function deleteOne(uuid) {
			$.SmartMessageBox({
				title : "删除",
				content : "该操作将执行删除操作，确定执行？",
				buttons : "[No][Yes]",
				//input : "text",
				//placeholder : "请填写备注:"
			}, function(ButtonPressed, Value) {
				if (ButtonPressed === "Yes") {
					$.post(getContext()+'data/data/deleteItem',{'uuid':uuid,'tableName':$("#currentDataTable").val()},function(data){
						if(data.success){
							$.smallBox({
								title : "操作成功",
								content : "<i class='fa fa-clock-o'></i> <i>操作成功...</i>",
								color : "#659265",
								iconSmall : "fa fa-check fa-2x fadeInRight animated",
								timeout : 2000
							});
							itemCheckList[uuid]=false;
							$("table.dataTable").DataTable().ajax.reload(null,false);;
						}else{
							$.smallBox({
								title : "操作失败",
								content : "<i class='fa fa-clock-o'></i> <i>操作失败...</i>",
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
			});
		}
		
		function batDataDel(){
			$.SmartMessageBox({
				title : "删除",
				content : "该操作将执行删除操作，确定执行？",
				buttons : "[No][Yes]",
				//input : "text",
				//placeholder : "请填写备注:"
			}, function(ButtonPressed, Value) {
				if (ButtonPressed === "Yes") {
					var list = [];
					for(var i in itemCheckList) {
						if(itemCheckList[i]==true)
							list.push(i);
					}
					if(list.length>0){
					    $.post(getContext()+'data/data/batDataDelete',{uuids:list,dataTable:$('#currentDataTable').val()},function(data){
							if(data.success){
								$.smallBox({
									title : "操作成功",
									content : "<i class='fa fa-clock-o'></i> <i>操作成功...</i>",
									color : "#659265",
									iconSmall : "fa fa-check fa-2x fadeInRight animated",
									timeout : 2000
								});
								for(var i in list)
									itemCheckList[list[i]]=false;
								$("table.dataTable").DataTable().ajax.reload(null,false);;
							}else{
								$.smallBox({
									title : "操作失败",
									content : "<i class='fa fa-clock-o'></i> <i>操作失败...</i>",
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
				}
			});
		}
		
		function importOne(uuid) {
			if($("#currentTask").val()!=""){
				var data = {
						"items[]":uuid,
						"dataTable" : $("#currentDataTable").val(), 
						"taskName" : $("#currentTask").val()
						};
				$.post(getContext()+'data/data/importToProject',data,function(data){
					if(data.success){
						$.smallBox({
							title : "操作成功",
							content : "<i class='fa fa-clock-o'></i> <i>操作成功...</i>",
							color : "#659265",
							iconSmall : "fa fa-check fa-2x fadeInRight animated",
							timeout : 2000
						});
						itemCheckList[uuid]=true;
						$("table.dataTable").DataTable().ajax.reload(null,false);;
					}else{
						$.smallBox({
							title : "操作失败",
							content : "<i class='fa fa-clock-o'></i> <i>操作失败...</i>",
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
			else{
				$.smallBox({
					title : "操作失败",
					content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>请选择任务</i>",
					color : "#C46A69",
					iconSmall : "fa fa-times fa-2x fadeInRight animated"
				});
			}
		}
</script>

<script src="${springMacroRequestContext.contextPath}/public/js/data/data-select.js"></script>


