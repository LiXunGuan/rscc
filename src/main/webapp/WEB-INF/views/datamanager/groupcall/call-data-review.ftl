<style>
</style>
<div class="modal fade" id="dialog_data">
	<div class="modal-dialog" style="width: 60%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">关闭</button>
				<h4 class="modal-title">任务详情——当前任务:${(entry.description)!""}，共有数据${(entry.dataCount)!""}条，已呼叫${(dataCount)!""}条</h4>
				<input type="hidden" name="uuid" id="groupcallUuid" value=${entry.uid}>
			</div>
			
			<div class="modal-body">
	<div class="tabs-top">
		<ul class="nav nav-tabs tabs-top" id="demo-pill-nav">
			<li class="active">
				<a href="#tab-r1" data-toggle="tab"> 统计信息 </a>
			</li>
			<li>
				<a href="#tab-r2" data-toggle="tab"> 详细信息 </a>
			</li>
		</ul>
		<div class="tab-content">
		
			<div class="tab-pane active" id="tab-r1">
				<div class="row">
					<article class="col-lg-12 col-lg-12 col-lg-12 col-lg-12">
						<div class="jarviswidget jarviswidget-color-darken" id="wid-id-0" data-widget-editbutton="false" style="margin-bottom:0px">
							
							<div>
								<div class="widget-body no-padding">
									<div class="widget-body-toolbar">
										<div class="smart-form">
											<div class="row">
												<section>
													<label class="label col">数据批次</label>
													<label class="input col col-2">
														<input type="text" name="batch_name" id="zzbatch_name" value="" />
													</label>
													<label class="label col">数据部门</label>
													<label class="input col col-2">
														<input type="text" name="dept_name" id="zzdept_name" value="" />
													</label>
													<label class="btn btn-sm btn-primary" onclick="doFilter()">查找</label>
												</section>
											</div>
										</div>
									</div>
							<table id="dt_statistic" class="table table-bordered table-hover"></table>
								</div>
							</div>
						</div>
					</article>
				</div>
			</div>
		
		
		
		
		
		
		
			<div class="tab-pane " id="tab-r2">
				<div class="row">
					<article class="col-lg-12 col-lg-12 col-lg-12 col-lg-12">
						<div class="jarviswidget jarviswidget-color-darken" id="wid-id-1" data-widget-editbutton="false" style="margin-bottom:0px">
							
							<div>
								<div class="widget-body no-padding">
									<div class="widget-body-toolbar">
										<div class="smart-form">
											<div class="row">
												<section>
													<input type="hidden" name="groupcall_id" id="wzgroupcall_id" value="${groupCallId!''}" />
													<label class="label col">电话号码</label>
													<label class="input col col-2">
														<input type="text" name="data_phone" id="wzdata_phone" value="" />
													</label>
													<label class="label col">数据批次</label>
													<label class="input col col-2">
														<input type="text" name="batch_name" id="wzbatch_name" value="" />
													</label>
													<label class="label col">数据部门</label>
													<label class="input col col-2">
														<input type="text" name="dept_name" id="wzdept_name" value="" />
													</label>
													<label class="btn btn-sm btn-primary" id="doFilter">查找</label>
												</section>
											</div>
											<div class="row">
												<section>
													<label class="label col">呼叫状态</label>
													<label class="form-group col col-2"> 
														<select class="form-control" name="call_result" id="wzcall_result" onchange="doFilter()">
															<option value="">全部</option>
<!-- 															<option value="0">未开始</option> -->
<!-- 															<option value="1">待呼叫</option> -->
															<option value="2">未接通</option>
															<option value="3">单方接通</option>
															<option value="4">双方接通</option>
															<option value="5">呼叫异常</option>
														</select>
													</label>
												</section>
											</div>
										</div>
									</div>
									<table id="dt_basic_select" class="table table-bordered table-hover"></table>
								</div>
							</div>
						</div>
					</article>
				</div>
			</div>
			<div class="modal-footer">
<!-- 								<label data-dismiss="modal" class="btn btn-primary" id="doImport" onclick="doImport()">导入</label> -->
				<div class="row">
<!-- 					<div class="col col-md-3 col-md-offset-3"></div><div class="col col-md-3"></div> -->
					<label data-dismiss="modal" class="btn btn-success" onclick="recall(2)">重新呼叫未接通号码</label>
					<label data-dismiss="modal" class="btn btn-success" onclick="recall(3)">重新呼叫单方接通号码</label>
					<label data-dismiss="modal" class="btn btn-primary" id="doClose">关闭</label>
				</div>
			</div>
			</div>
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
					batch_name:$("#wzbatch_name").val(),
					dep_name:$("#wzbatch_name").val(),
					call_result:$("#wzcall_result").val()
			}
			$("table.dataTable").DataTable().ajax.reload(null,false);
		});

		function doFilter(){
			itemCondition = {
					groupcall_id:$("#wzgroupcall_id").val(),
					data_phone:$("#wzdata_phone").val(),
					batch_name:$("#wzbatch_name").val(),
					dep_name:$("#wzbatch_name").val(),
					call_result:$("#wzcall_result").val()
			}
			$("table.dataTable").DataTable().ajax.reload(null,false);
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

		    var url = getContext()+'newdata/groupCall/checkDataAll';
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
			$.post(getContext()+'newdata/groupCall/collectionPhone',{groupCallId:$('#wzgroupcall_id').val()},function(data){
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
					$.post(getContext()+'newdata/groupCall/deleteOneData',{'dataPhone':uuid,'groupCallId':$("#wzgroupcall_id").val()},function(data){
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
					    $.post(getContext()+'newdata/groupCall/batchDeleteData',{phones:list,groupCallId:$('#wzgroupcall_id').val()},function(data){
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
								$("table.dataTable").DataTable().ajax.reload(null,false);
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
		
		function recall(type) {
			//将该变量设置为0，该函数执行后的弹屏的tab页将有关闭按钮
			window.parent.closetag = "0";
			var groupcalluuid = $("#groupcallUuid").val()
			 $.post(getContext()+'newdata/groupCall/recall',{type:type,groupCallId:$('#wzgroupcall_id').val()},function(data){
				 if(data.success) {
					 $.post(getContext() + "newdata/groupCall/changeStat",{uuid:groupcalluuid,stat:1},function(data){
							if(data.success){
								if(data.stat){
									$("#" + $("#groupcallUuid").val()).removeAttr("disabled");
								}
								$.smallBox({
									title : "操作成功",
									content : "<i class='fa fa-clock-o'></i> <i>操作成功...</i>",
									color : "#659265",
									iconSmall : "fa fa-check fa-2x fadeInRight animated",
									timeout : 2000
								});
							}
						},"json");
				 } else {
					 $.smallBox({
						title : "操作失败",
						content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>请先停止群呼任务</i>",
						color : "#C46A69",
						iconSmall : "fa fa-times fa-2x fadeInRight animated"
					});
				 }
			 },"json");
			 $("table.dataTable").DataTable().ajax.reload(null,false);
		}
		
		$(document).ready(function(){
			var oTable = $('#dt_statistic')
			.DataTable(
					{
						"dom" : "t"
								+ "",
						"autoWidth" : false,
						"ordering" : true,
						"serverSide" : true,
						"processing" : true,
						"searching" : false,
						"pageLength" : 20,
						"lengthMenu" : [ 10, 15, 20, 25, 30 ],
						"infoCallback":function(settings, start, end, max, total, pre){
					    	return "";
					    },
						"language" : {
							"url" : getContext() + "public/dataTables.cn.txt"
						},
						"ajax" : {
							"url" : getContext() + "newdata/groupCall/statistic",
							"type" : "POST",
							"data" : function(param) {
								param.groupcall_id = $("#wzgroupcall_id").val();
								param.batch_name = $("#zzbatch_name").val();
								param.dept_name = $("#zzdept_name").val();
							}
						},
						"paging" : true,
						"pagingType" : "bootstrap",
						"lengthChange" : true,
						"order" : [ [ "1", "desc" ] ],
						"columns" : [
								{ "title" : "批次","data" : "batchName","defaultContent":"无"},
								{ "title" : "部门","data" : "deptName","defaultContent":"无"},
								{ "title" : "总量","data" : "dataCount","defaultContent":"无"},
								{ "title" : "未接通","data" : "noAnswer","defaultContent":"无"},
								{ "title" : "单方接通","data" : "singleAnswer","defaultContent":"无"},
								{ "title" : "已接通","data" : "answered","defaultContent":"无"},
// 								{
// 									"title" : "操作",
// 									"data" : "null",
// 									"render" : function(data, type, full) {
// 										return  "<a onclick='deleteOne(\""
// 												+ full.data_phone + "\");'>重新呼叫该数据</a>";
// 									}
// 								}
								],
					});

		})
		
</script>

<!-- <script src="${springMacroRequestContext.contextPath}/public/task/criminal-select.js"></script> -->

<script src="${springMacroRequestContext.contextPath}/public/js/data/groupcall/call-data-review.js"></script>


