<style>
</style>
<div class="modal fade" id="dialog_data">
	<div class="modal-dialog" style="width: 60%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">关闭</button>
				<h4 class="modal-title">数据预览——当前任务ID:${groupCallId!""}，共有数据${(dataCount)!""}条，待呼叫${(ready)!""}条，已呼叫${(called)!""}条</h4>
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
					<td class="ui-pg-button ui-corner-all" title="" id="batDel" data-original-title="Delete selected row" onclick="collectionPhone();">
						<div class="btn btn-sm btn-primary">
						<span class="">回收未接通号码</span>
						</div>
					</td>
<!-- 					<td class="ui-pg-button ui-corner-all" title="" id="refresh" data-original-title="Reload Grid" onclick="doFilter();"> -->
<!-- 						<div class="btn btn-sm btn-primary"> -->
<!-- 						<span class="">刷新内容</span> -->
<!-- 						</div> -->
<!-- 					</td> -->
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
													<input type="hidden" name="groupcall_id" id="wzgroupcall_id" value="${groupCallId!''}" />
													<label class="label col">号码</label>
													<label class="input col col-2">
														<input type="text" name="data_phone" id="wzdata_phone" value="" />
													</label>
													<label class="label col">数据来源</label>
													<label class="input col col-2">
														<input type="text" name="container_name" id="wzcontainer_name" value="" />
													</label>
													<label class="label col">呼叫状态</label>
													<label class="select col col-1"> 
														<select class="input-sm" name="call_flag" id="wzcall_flag">
															<option value="">全部</option>
															<option value="0">未开始</option>
															<option value="1">待呼叫</option>
															<option value="2">未接通</option>
															<option value="4">单方接通</option>
															<option value="3">双方接通</option>
															<option value="5">呼叫异常</option>
														</select>
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

		var itemCondition = {
			groupcall_id:"${groupCallId}"
		};
		
		var itemCheckList = {};

		$('#dialog_data').modal("show");
	
		$("#doFilter").click(function(){
			itemCondition = {
					groupcall_id:$("#wzgroupcall_id").val(),
					data_phone:$("#wzdata_phone").val(),
					container_name:$("#wzcontainer_name").val(),
					call_flag:$("#wzcall_flag").val()
			}
			$("table.dataTable").DataTable().ajax.reload(null,false);;
		});

		function doFilter(){
			$("table.dataTable").DataTable().ajax.reload(null,false);;
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

		    var url = getContext()+'data/groupCall/checkDataAll';
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
		    addCheckInfoData();
		}
		
		function addCheckInfoData(){
			var num=0;
			for(var i in itemCheckList) {
				if(itemCheckList[i]==true)
					num++;
			}
			$("#dataCheckInfo").text(num);
		}
		
		function getCheckNumData(){
			var num=0;
			for(var i in itemCheckList) {
				if(itemCheckList[i]==true)
					num++;
			}
			return "<span id='dataCheckInfo'>" + num + "</span>";
		}
		
		function collectionPhone() {
			$.post(getContext()+'data/groupCall/collectionPhone',{groupCallId:$('#wzgroupcall_id').val()},function(data){
				if(data.success){
					$.smallBox({
						title : "操作成功",
						content : "<i class='fa fa-clock-o'></i> <i>操作成功...</i>",
						color : "#659265",
						iconSmall : "fa fa-check fa-2x fadeInRight animated",
						timeout : 2000
					});
					$("table.dataTable").DataTable().ajax.reload(null,false);
				}
			},"json");
		}
		
		function deleteOne(uuid) {
			$.SmartMessageBox({
				title : "删除",
				content : "该操作将执行删除操作，确定执行？",
				buttons : "[No][Yes]",
				//input : "text",
				//placeholder : "请填写备注:"
			}, function(ButtonPressed, Value) {
				if (ButtonPressed === "Yes") {
					$.post(getContext()+'data/groupCall/deleteOneData',{'dataPhone':uuid,'groupCallId':$("#wzgroupcall_id").val()},function(data){
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
			})
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
					    $.post(getContext()+'data/groupCall/batchDeleteData',{phones:list,groupCallId:$('#wzgroupcall_id').val()},function(data){
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
		
</script>

<!-- <script src="${springMacroRequestContext.contextPath}/public/task/criminal-select.js"></script> -->

<script src="${springMacroRequestContext.contextPath}/public/js/data/call-data-review.js"></script>


