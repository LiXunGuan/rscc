<div class="modal fade" id="dialog_getdatacount">
	<div class="modal-dialog" style="width: 90%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
				<h4 class="modal-title">部门内数据量数据查看</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<article class="col-lg-12 col-lg-12 col-lg-12 col-lg-12">
						<input type="hidden" name="islock" id="islock" value="${(islock)!''}" />
						<div style="margin-bottom: 8px;">
<!-- 							<label class="btn btn-sm btn-primary" onclick="batchReturnItem()">批量回收</label> -->
							<a href="javascript:void(0);" id="fenpei" class="btn btn-sm btn-primary" rel="popover" data-placement="right"  data-original-title="分配人员" data-content="<div class='modal-body' style='padding: 0px;width:200px;'> <form action='' class='smart-form' id='allotUserForm'> <fieldset style='padding-top: 2px;'> <section> <div class='row'> <select id='users' name='users'> <#list users as u> <option value='${u.uid}'>[${u.departmentName}] ${u.loginName} </option> </#list> </select> </div></section> </fieldset> </form> </div> <div class='modal-footer' style='padding: 5px;height:20px;'> <button type='button' id='saveUserButton' class='btn btn-primary btn-xs' onclick='saveUser();'>保存 </button> </div>" data-html="true">批量分配</a>
							<!--<label class="btn btn-sm btn-primary" id="batchAllot" onclick="batchAllotItem()">批量分配</label> -->

<!-- 								<div class='modal-body'> <form action='${springMacroRequestContext.contextPath}/deptdata/dept/achieveDataSave' class='smart-form' id='achieveDataForm'> <fieldset style='padding-top: 2px;'> <section> <div class='row'> <select id='users' name='users'> <option value=''>请选择</option> <#list users as u> <option value='${u.uid}'>[${u.departmentName}] ${u.loginName} </option> </#list> </select> </div></section> </fieldset> </form> </div> <div class='modal-footer' style='padding: 8px;'> <button type='button' id='achieveDataSaveButton' class='btn btn-primary'>保存 </button> </div> -->

<!-- 					            <div class='modal-body' style='padding: 0px;'> -->
<!-- 					                <form action='${springMacroRequestContext.contextPath}/deptdata/dept/achieveDataSave' class='smart-form' id='achieveDataForm'> -->
<!-- 					                	<fieldset style='padding-top: 2px;'> -->
<!-- 						                    <section> -->
<!-- 						                        <div class='row'> -->
<!-- 						                            <select id='users' name='users'> -->
<!-- 														<option value=''>请选择</option> -->
<!-- 														<#list users as u> -->
<!-- 															<option value='${u.uid}'>[${u.departmentName}] ${u.loginName} -->
<!-- 															</option> -->
<!-- 														</#list>  -->
<!-- 													</select>  -->
<!-- 						                        </div> -->
<!-- 						                    </section> -->
<!-- 					                    </fieldset> -->
<!-- 						            </form> -->
<!-- 					            </div> -->
<!-- 					            <div class='modal-footer' style='padding: 8px;'> -->
<!-- 					                <button type='button' id='achieveDataSaveButton' class='btn btn-primary'>保存 </button> -->
<!-- 					            </div> -->
						</div>
						<div class="jarviswidget jarviswidget-color-darken" data-widget-editbutton="false">
							<header>
								<span class="widget-icon"> <i class="fa fa-table"></i>
								</span>
								<h2>数据信息</h2>
							</header>
							<div>
								<div class="widget-body no-padding">
<!-- 									<div class="widget-body-toolbar"> -->
<!-- 									</div> -->
									<input type="hidden" name="batchUuid" id="batchUuid" value="${(batchUuid)!''}" />
									<input type="hidden" name="deptUuid" id="deptUuid" value="${(deptUuid)!''}" />
									<table id="dt_basic_getdatacount" class="table table-bordered table-hover"></table>
								</div>
							</div>
						</div>
					</article>
				</div>
			</div>
			<div class="modal-footer">
				<label data-dismiss="modal" class="btn btn-primary">关闭</label>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
		
		$(document).ready(function() {
			var islock = $("#islock").val();
			if(islock == "1"){
				$("#fenpei").hide();
			}
		});

		var checklist = {};

		$('#dialog_getdatacount').modal("show");
		
// 		$("#users option:first").attr("selected","selected");
		
		$('#dialog_getdatacount').on('hide.bs.modal', function (e) { 
			$("#dialog_getdatacount").remove();
			
// 			$('#dt_basic_deptdata').DataTable().ajax.reload(null,false);
			
		});
		
		// 全选所有
	    function docheckall(){
		    var url = getContext() + 'deptdata/dept/dataCountAll';
		    var batchUuid = $("#batchUuid").val(); 
		    var deptUuid = $("#deptUuid").val();
		    $.post(url,{batchUuid:batchUuid,deptUuid:deptUuid},function(data){
		    	checklist = {};
		    	$("#datacheckInfo").text(data.length);
		    	if(data != ""){
			    	var chs = data;
			        for(var i in chs){
		            	checklist[chs[i]] = chs[i];
			        }
		    	}
		        docheckallpage();
		    },"json");
		}

		// 全选本页
		function docheckallpage(){
			
		    $("[id^='ck']").each(function(k,v){
		    	 if(!$(v).is(':checked')){
		            $(v).trigger("click");
		         }
		    });
		};
		
		// 取消选择
		function docancelAll(){
		    $("[id^='ck']").each(function(){
		        if($(this).is(':checked')){
		            $(this).click();
		        }
		    });
		    checklist={};
		    $("#datacheckInfo").text(0);
		}
		
		// 选中某个
		function docheck(id){
		    if($("#ck"+id).is(':checked')){
		        checklist[id] = id;
		    }else{
		        checklist[id] = false;
		    }
		}
		
		function getCheckNum(){
			var num = 0;
			for(var i in checklist) {
				if(checklist[i]){
					num++;
				}
			}
			return "<span id='datacheckInfo'>" + num + "</span>";
		}

		// 批量归还
		function batchReturnItem(){
			var dataUuid='';
	    	for (var i in checklist) {
	    		dataUuid += checklist[i]+",";
			}
	    	if (!dataUuid.length>0) {
	    		alert("请先选择数据！");
				return;
			}
	    	$.SmartMessageBox({
	            title : "批量归还",
	            content : "确定批量归还选择的数据吗？",
	            buttons : '[No][Yes]'
	        }, function(ButtonPressed) {
	            if (ButtonPressed === "Yes") { 
	                var url = getContext() + "deptdata/dept/batchReturnData";
	                var batchUuid = $("#batchUuid").val();
	                var deptUuid = $("#deptUuid").val();
	                $.post(url,{dataUuid:dataUuid,batchUuid:batchUuid,deptUuid:deptUuid},function(data){
	                    if(data.success){
	                        $.smallBox({
	                            title : "操作成功",
	                            content : "<i class='fa fa-clock-o'></i> <i>批量归还成功...</i>",
	                            color : "#659265",
	                            iconSmall : "fa fa-check fa-2x fadeInRight animated",
	                            timeout : 2000
	                        });
	                        checklist={};
	                        $('#dt_basic_getdatacount').DataTable().ajax.reload(null,false);
	                    }else{
	                        $.smallBox({
	                            title : "操作失败",
	                            content : "<i class='fa fa-clock-o'></i> <i>批量归还失败...</i>",
	                            color : "#C46A69",
	                            iconSmall : "fa fa-times fa-2x fadeInRight animated",
	                            timeout : 2000
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
		
		$('#users').select2({
           allowClear : true,
           width: '99%'
        });
		
		function saveUser(){
			var dataUuid='';
	    	for (var i in checklist) {
	    		dataUuid += checklist[i]+",";
			}
	    	if (!dataUuid.length>0) {
	    		alert("请先选择数据！");
				return;
			}
	    	$.SmartMessageBox({
	            title : "批量分配",
	            content : "确定批量分配选择的数据吗？",
	            buttons : '[No][Yes]'
	        }, function(ButtonPressed) {
	            if (ButtonPressed === "Yes") { 
	                var url = getContext() + "deptdata/dept/batchAllotData";
	                var user = $("#users").val();
	                var batchUuid = $("#batchUuid").val();
	                var deptUuid = $("#deptUuid").val();
	                $.post(url,{dataUuid:dataUuid,user:user,batchUuid:batchUuid,deptUuid:deptUuid},function(data){
	                    if(data.success){
	                        $.smallBox({
	                            title : "操作成功",
	                            content : "<i class='fa fa-clock-o'></i> <i>批量分配成功...</i>",
	                            color : "#659265",
	                            iconSmall : "fa fa-check fa-2x fadeInRight animated",
	                            timeout : 2000
	                        });
	                        checklist={};
	                        $('#dt_basic_getdatacount').DataTable().ajax.reload(null,false);
	                    }else{
	                        $.smallBox({
	                            title : "操作失败",
	                            content : "<i class='fa fa-clock-o'></i> <i>批量分配失败...</i>",
	                            color : "#C46A69",
	                            iconSmall : "fa fa-times fa-2x fadeInRight animated",
	                            timeout : 2000
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
		};
		
</script>

<script src="${springMacroRequestContext.contextPath}/public/js/databatch/getDataCount.js"></script>


