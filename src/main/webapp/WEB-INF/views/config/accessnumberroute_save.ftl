<script src="${springMacroRequestContext.contextPath}/assets/js/adddate.js" xmlns="http://www.w3.org/1999/html"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/calendar/WdatePicker.js"></script>
<script src="${springMacroRequestContext.contextPath}/assets/js/plugin/select2/select2.min.js"></script>
<!-- <div class="modal fade" id="accessnumberRoute_index_add"> -->
<div class="modal fade" id="accessnumberRoute_index_add" >
	<div class="modal-dialog" style="">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>


				<h4 class="modal-title">接入号</h4>
			</div>
			<div class="modal-body">
				<form id="accessnumberRouteForm"
					action="${springMacroRequestContext.contextPath}/config/accessnumberroute/save"
					class="smart-form" method="post">
					<fieldset style="padding-top: 2px;">

						<input id="id" type="hidden" onfocus="onfocus" name="id"
							value="${(item.id)!''}" />
							
						<section>
							<div class="row">
								<label class="label col col-3">名称</label> <label
									class="input col col-6"> <input
									class="form-control spinner-right" id="name" name="name" 
									value="${(item.name)!''}">
								</label>
							</div>
						</section>
							
						<section>
							<div class="row">
								<label class="label col col-3">号码</label> <label
									class="select col col-6"> <select id="number"
									name="number">
										<option value="">请选择</option> 
										
										
										
										<#if fsMap??> 
											<#list fsMap?keys as key> 
												<#if (item.callerIdNumber)??>
													<#if (item.callerIdNumber)==(fsMap[key])>
														<option selected="selected" value="${fsMap[key]}">${fsMap[key]}</option>
													<#else>
														<option value="${fsMap[key]}">${fsMap[key]}</option> 
													</#if>
												<#else>
													<#if num??>
														<#if num==(fsMap[key])>
															<option selected="selected" value="${fsMap[key]}">${fsMap[key]}</option>
														<#else>
															<option value="${fsMap[key]}">${fsMap[key]}</option> 
														</#if>
													<#else>
														<option value="${fsMap[key]}">${fsMap[key]}</option> 
													</#if>
												</#if>
											</#list> 
										</#if>
										
								</select>
								</label>
							</div>
						</section>




						<section>
							<div class="row">
								<label class="label col col-3">开始时间</label> <label
									class="input col col-6"> 
									<input id="startDates" name="startDates" value="${((item.startDate)?string('yyyy-MM-dd HH:mm'))!''}" type="text" class="ui_input_text 400w"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endDates\')}'});" placeholder="开始时间"/>
								</label>
							</div>
						</section>


						<section>
							<div class="row">
								<label class="label col col-3">结束时间</label> <label
									class="input col col-6"> 
									<input id="endDates" name="endDates" value="${((item.endDate)?string('yyyy-MM-dd HH:mm'))!''}" type="text" class="ui_input_text 400w"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'startDates\')}'});" placeholder="结束时间"/>
								</label>
							</div>
						</section>


                        <section >
                            <div class="row">
                                <label class="label col col-3">时段</label>
                                <label class="label col">
                                    <input type="radio" name="timecheck" id="normalradio"  value="n" checked onclick="normalcheck()"> 全时段</input>
                                    <br/>
                                    <input type="radio" name="timecheck" id="normalradio"  value="d" onclick="daycheck()"> 每日</input>
                                    <br/>
                                    <input type="radio" name="timecheck" id="weekradio" value="w" onclick="weekcheck()"> 每周</input>
                                    <br/>
                                    <div id="weeksection" style="display: none">
                                    <input type="checkbox" name="weekcheckbox" value="2">星期一</input>
                                    <input type="checkbox" name="weekcheckbox" value="3">星期二</input>
                                    <input type="checkbox" name="weekcheckbox" value="4">星期三</input>
                                    <input type="checkbox" name="weekcheckbox" value="5">星期四</input>

                                    <input type="checkbox" name="weekcheckbox" value="6">星期五</input>
                                    <input type="checkbox" name="weekcheckbox" value="7">星期六</input>
                                    <input type="checkbox" name="weekcheckbox" value="1">星期日</input>
                                    </div>
                                </label>
                            </div>
                        </section>



                        <section  id="timedetail" style="display: none">
                            <div class="row">
                                <label class="label col col-3">时段设置</label>


                                <label class="label col">
                                    <label>开始时间&nbsp;&nbsp;</label>
                                    <select id="start_1_h" name="start_1_h" style="height: 28px;">


                                    </select>&nbsp;&nbsp;时&nbsp;&nbsp;
                                    <select id="start_1_m" name="start_1_m" style="height: 28px;">


                                    </select>&nbsp;&nbsp;分&nbsp;&nbsp;
                                    <br/>
                                    <label>结束时间&nbsp;&nbsp;</label>
                                    <select id="stop_1_h" name="stop_1_h" style="height: 28px;">

                                    </select>&nbsp;&nbsp;时&nbsp;&nbsp;
                                    <select id="stop_1_m"  name="stop_1_m"  style="height: 28px;">

                                    </select>&nbsp;&nbsp;分&nbsp;&nbsp;
                                </label>

                            </div>
                        </section>


						<section>
						<div class="row">
							<label class="label col col-3">类型 </label> <label
								class="select col col-6">
								<select id="types" name="types">
									<option  value="">请选择</option>
									<#list extRou ?keys as key>
										<#if (type)??>
											<#if type==key>
													<option selected="selected"  value="${key}" >${extRou[key]}</option> 
											<#else>
												<option  value="${key}">${extRou[key]}</option> 
											</#if>
										<#else>
											<option  value="${key}">${extRou[key]}</option> 
										</#if>
									</#list>
								</select>
							</label>
						</div>
						</section>
						
						<section>
							<div class="row"  >
								<label class='label col col-3'>分机号</label> 
								<label class='select col col-6'>
									<select id="extension" name="extension" >
										<option value=''>请选择</option>
									</select>
								</label>
							</div>
						</section>

					

						<section>
							<div class="row">
								<label class="label col col-3">顺序</label> <label
									class="input col col-6"> <input
									class="form-control spinner-right" id="rank" name="rank" 
									value="${(item.rank)!'1'}">
								</label>
							</div>
						</section>



					</fieldset>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					关 闭</button>
				<button id="addAccessnumber" type="button" class="btn btn-primary">
					保 存</button>
			</div>
		</div>
	</div>
</div>


<script type="text/javascript">

    var start_1_h = $('#start_1_h');
    var start_1_m = $('#start_1_m');
    var stop_1_h = $('#stop_1_h');
    var stop_1_m = $('#stop_1_m');
    var hours = ['0','1','2','3','4','5','6','7','8','9','10','11',
        '12','13','14','15','16','17','18','19','20','21','22','23'];
    var mins = ['0','10','20','30','40','50'];
    for (var inde = 0; inde < hours.length; inde++) {
        start_1_h.append("<option value='"+hours[inde]+"'>"+hours[inde]+"</option>");
        stop_1_h.append("<option value='"+hours[inde]+"'>"+hours[inde]+"</option>");
    }
    for (var index = 0; index < mins.length; index++) {
        start_1_m.append("<option value='"+mins[index]+"'>"+mins[index]+"</option>");
        stop_1_m.append("<option value='"+mins[index]+"'>"+mins[index]+"</option>");
    }


	<#if item??>
	
	
	    $("#start_1_h").val('${start_1_h!''}');
	    $("#start_1_m").val('${start_1_m!''}');
	    $("#stop_1_h").val('${stop_1_h!''}');
	    $("#stop_1_m").val('${stop_1_m!''}');
	
	   <#if weeks??>
	        <#list weeks?split(',') as w>
	            $("[name='weekcheckbox'][value=${w}]").attr('checked',true);
	        </#list>
	   </#if>
	
	    $("[value='${item.type}']").click();
	<#else>
	    $('#normalradio').click();
	</#if>
	
	
	function normalcheck(){
	    $("[name='weekcheckbox']").removeAttr('checked');
	    $("[name='weekcheckbox']").attr("disabled",true);
	
	    $("#timedetail").hide();
	    $("#weeksection").hide()
	}
	
	function weekcheck(){
	    $("[name='weekcheckbox']").attr("disabled",false);
	    $("#timedetail").show();
	    $("#weeksection").show();
	
	}
	
	function daycheck(){
	
	    $("[name='weekcheckbox']").removeAttr('checked');
	    $("[name='weekcheckbox']").attr("disabled",true);
	    $("#timedetail").show();
	    $("#weeksection").hide()
	}
	
	
	
	$('#extension').select2({
	    allowClear : true,
	    width:'99%'
	});
	$('#types').select2({
	    allowClear : true,
	    width:'99%'
	});
	
	$('#number').select2({
		allowClear : true,
		width:'99%'
	});
	
	
	$("#types").change(function(){
		var types=$("#types").val();
			var url=getContext()+"config/accessnumberroute/getFSXmlType";
			$.post(url,{types:types},function(data){
				$("#extension").empty();
				if (data.result=="false") {
					$("#extension").append("没有对应的分机号！");
				}else{
					var sb="<option value=''>请选择</option>"
						for(var i in data){
							  if(typeof data[i] !="function"){
								  sb+="<option value='"+data[i].extension+"'>"+data[i].extension+"</option>";
							  }
						  }	
						 $("#extension").append(sb);
						 <#if item??>
					        var selected ='${item.extension}';
					        $("#extension").find("option[value='"+selected+"']").attr("selected",true);
					    </#if>
	            
				}
				$('#extension').select2({
					allowClear : true,
					width:'99%'
				});
				
				
			},'json');
		
	});
	<#if item??>
		$("#types").change();
	</#if>
	
	
		$(document).ready(function() {
			pageSetUp();
			
			$("#rank").spinner({
				min : 1,
				max : 99999,
				step : 1,
				start : 1,
				numberFormat : "C"
			});
			 
	
			/* 校验数据 */
			var validator = $('#accessnumberRouteForm').validate({
				rules : {
					callerIdNumber : {
						required : true
					},
					name : {
						required : true,
						
					},
					startDates : {
						required : true
					},
					endDates : {
						required : true
					},
					rank : {
						required : true,
						digits : true
					},
					types : {
						required : true
					},
					extension : {
						required : true,
					},
	
				},
				messages : {
					rank : {
						digits : $.format("只可输入整数！")
	    			}
				},
					errorPlacement : function(error, element) {
						error.insertAfter(element.parent());
					}
			});
	
			/* 展示modal */
			$('#accessnumberRoute_index_add').modal("show");
			
			$('#accessnumberRoute_index_add').on('hide.bs.modal', function(e){
				$("#accessnumberRoute_index_add").remove();
			});
	
			/* 提交按钮单击事件  */
			$('#addAccessnumber').click(function() {
				if ($('#accessnumberRouteForm').valid()) {
					$("#addAccessnumber").attr("disabled", "true");
					$('#accessnumberRouteForm').submit();
				}
	
			});
	
			$('#accessnumberRouteForm').ajaxForm({
				dataType : "json",
				success : function(data) {
					if (data.success) {
						$("#toDeployButton").removeAttr("disabled");
						$("#toDeployButton").removeClass("btn-default");
						$("#toDeployButton").addClass("btn-sm");
						$("#toDeployButton").addClass("btn-success");
						$.smallBox({
							title : "操作成功",
							content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
							color : "#659265",
							iconSmall : "fa fa-check fa-2x fadeInRight animated",
							timeout : 2000
						});
	
						oTable.DataTable().ajax.reload(null,false);;
					}
					$('#accessnumberRoute_index_add').modal("hide");
					validator.resetForm();
				},
				submitHandler : function(form) {
					$(form).ajaxSubmit({
						success : function() {
	
							$("#accessnumberRouteForm").addClass('submited');
						}
					});
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					if (textStatus == 'error') {
						$('#accessnumberRoute_index_add').dialog('hide');
						$.smallBox({
							title : "操作失败",
							content : "<i class='fa fa-clock-o'></i> <i>操作失败</i>",
							color : "#C46A69",
							iconSmall : "fa fa-times fa-2x fadeInRight animated",
							timeout : 2000
						});
	
						oTable.DataTable().ajax.reload(null,false);;
					}
				}
			});
	
		});
</script>
