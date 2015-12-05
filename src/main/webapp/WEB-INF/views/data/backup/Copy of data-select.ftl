<div class="modal fade" id="dialog_data">
	<div class="modal-dialog" style="width: 75%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
				<h4 class="modal-title">数据预览</h4>
			</div>
			
			<div class="modal-body">
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
													<input type="hidden" name="currentDataTable" id="currentDataTable" value="${currentDataTable!''}" />
													<label class="label col">名称</label>
													<label class="input col col-2">
														<input type="text" name="itemName" id="itemName" value="" />
													</label>
													<label class="label col">号码</label>
													<label class="input col col-2">
														<input type="text" name="itemPhone" id="itemPhone" value="" />
													</label>
													<label class="label col">状态</label>
													<label class="input col col-1">
														<input type="text" name="itemStat" id="itemStat" value="" />
													</label>
													<label class="label col">描述</label>
													<label class="input col col-2">
														<input type="text" name="itemDescribe" id="itemDescribe" value="" />
													</label>
													<label class="label col">地址</label>
													<label class="input col col-2">
														<input type="text" name="itemAddress" id="itemAddress" value="" />
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
								<div class="smart-form">
									<label class="label col">导入项目</label>
									<label class="select col col-2">
									<select name="currentProject" id="currentProject">
									<option value="">---请选择导入项目---</option>
									<#if projects??>
										 <#list projects as key>
										 	<option value="${key['uid']}">${key['projectName']}</option>
										 </#list>
									</#if>
									</select>
                              		</label>
                              		<label class="label col">导入任务</label>
									<label class="select col col-2">
									<select name="currentTask" id="currentTask">
									<option value="">---请选择导入任务---</option>
									</select>
                              		</label>
                           		</div>
								<label data-dismiss="modal" class="btn btn-primary" id="doImport" onclick="doImport()">导入</label>
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

		var checklist={};

		$('#dialog_data').modal("show");
	
		$("#doFilter").click(function(){
			$('#dt_basic_select').DataTable().ajax.reload(null,false);;
		});

		function doFilter(){
			$('#dt_basic_select').DataTable().ajax.reload(null,false);;
		}
		
		function doImport(){
			var list = [];
			for(var i in checklist) {
				if(checklist[i]==true)
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
					$('#oTable').DataTable().ajax.reload(null,false);;
				}else{
					$.smallBox({
						title : "操作失败",
						content : "<i class='fa fa-clock-o'></i> <i>操作失败...</i>",
						color : "#C46A69",
						iconSmall : "fa fa-times fa-2x fadeInRight animated",
						timeout : 2000
					});
				}
				$('#dt_basic_select').DataTable().ajax.reload(null,false);;
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

		    checklist[i]=false;
		    $('#ck'+i).click();
		}

		function docheckall(){

		    var url = getContext()+'data/data/importAll';
		    $.post(url,{currentDataTable:$("#currentDataTable").val()},function(data){
		        for(var i in data.items){
	                checklist[data.items[i]]=true;
		        }
		        docheckallpage();
		    });
		}

		function docheckallpage(){

		    $("[id^='ck']").each(function(){
		        if(!$(this).is(':checked')){
		            $(this).click();
		        }
		    });
		}

		function docancelAll(){

		    $("[id^='ck']").each(function(){
		        if($(this).is(':checked')){
		            $(this).click();
		        }
		    });

		    checklist={};
		}

		function docheck(uid){

		    if($("#ck"+uid).is(':checked')){
// 		        checklist[uid]=$("#ck"+uid).parent().next().text()
		        checklist[uid]=true;
		    }else{
		        checklist[uid]=false;
		    }
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
    					checklist[data.items[i]]=true;
    				}
    				$('#dt_basic_select').DataTable().ajax.reload(null,false);;
   				},"json")
	    	}
	    	else {
	    	}
		});
		
		function deleteOne(uuid) {
			$.post(getContext()+'data/data/deleteItem',{'uuid':uuid,'tableName':$("#currentDataTable").val()},function(data){
				if(data.success){
					$.smallBox({
						title : "操作成功",
						content : "<i class='fa fa-clock-o'></i> <i>操作成功...</i>",
						color : "#659265",
						iconSmall : "fa fa-check fa-2x fadeInRight animated",
						timeout : 2000
					});
					checklist[uuid]=false;
					$('#oTable').DataTable().ajax.reload(null,false);;
				}else{
					$.smallBox({
						title : "操作失败",
						content : "<i class='fa fa-clock-o'></i> <i>操作失败...</i>",
						color : "#C46A69",
						iconSmall : "fa fa-times fa-2x fadeInRight animated",
						timeout : 2000
					});
				}
				$('#dt_basic_select').DataTable().ajax.reload(null,false);;
			},"json").error(function(){
				$.smallBox({
					title : "操作失败",
					content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>添加时出现异常...</i>",
					color : "#C46A69",
					iconSmall : "fa fa-times fa-2x fadeInRight animated"
				});
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
						checklist[uuid]=true;
						$('#oTable').DataTable().ajax.reload(null,false);;
					}else{
						$.smallBox({
							title : "操作失败",
							content : "<i class='fa fa-clock-o'></i> <i>操作失败...</i>",
							color : "#C46A69",
							iconSmall : "fa fa-times fa-2x fadeInRight animated",
							timeout : 2000
						});
					}
					$('#dt_basic_select').DataTable().ajax.reload(null,false);;
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

<!-- <script src="${springMacroRequestContext.contextPath}/public/task/criminal-select.js"></script> -->

<script src="/rs/public/js/data/data-select.js"></script>


