<script src="${springMacroRequestContext.contextPath}/assets/js/adddate.js"></script>
<script src="${springMacroRequestContext.contextPath}/assets/js/calendar/WdatePicker.js"></script>
<div class="modal fade" id="reminderSave" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 800px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    <strong>关闭</strong>
                </button>
                <h4 class="modal-title" id="myModalLabel">日程提醒</h4>
            </div>
            <div class="modal-body">
                <form action="${springMacroRequestContext.contextPath}/schedulereminder/save" class="smart-form" id="coreForm">
                	<fieldset style="padding-top: 2px;">
                    <input type="hidden" id="uid" name="uid" value="${(item.uid)!''}">
                    <input type="hidden" id="oldstime" name="oldstime" value="${((item.schStartTime)?string('yyyy-MM-dd HH:mm:ss'))!''}">
                    <section>
                        <div class="row">
							<label class="label col col-2">开始时间：</label>
							<label class="input col col-3"> 
								<input id="schStartTime" name="schStartTime" value="${((item.schStartTime)?string('yyyy-MM-dd HH:mm:ss'))!''}" type="text" class="ui_input_text 400w required"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" placeholder="开始时间"/>
							</label>
						</div>
                    </section>
<!--                     <section> -->
<!--                         <div class="row"> -->
<!-- 							<label class="label col col-2">开始时间：</label> -->
<!-- 							<label class="input col col-3">  -->
<!-- 								<input id="schStartTime" name="schStartTime" value="${((item.schStartTime)?string('yyyy-MM-dd HH:mm:ss'))!''}" type="text" class="ui_input_text 400w required"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'schEndTime\')}'})" placeholder="开始时间"/> -->
<!-- 							</label> -->
<!-- 						</div> -->
<!--                     </section> -->
<!--                     <section> -->
<!-- 						<div class="row"> -->
<!-- 							<label class="label col col-2">结束时间：</label> -->
<!-- 							<label class="input col col-3">  -->
<!-- 								<input id="schEndTime" name="schEndTime" value="${((item.schEndTime)?string('yyyy-MM-dd HH:mm:ss'))!''}" type="text" class="ui_input_text 400w required"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'schStartTime\')}'})" placeholder="结束时间"/> -->
<!-- 							</label> -->
<!-- 						</div> -->
<!-- 					</section> -->
<!--                 	<button type="button" id="moreButton" class="btn  bg-color-blueDark txt-color-white">更多选项</button> -->
                    <div id="moreInfo">
	                    <section>
	                        <div class="row">
	                            <label class="label col col-2"> 提醒： </label>
	                            <label class="select col col-4">
	                            	<input type="hidden" id="phone1" name="phone1" value="${phone1!''}">
	                            	<select id="scheduleRemind" name="scheduleRemind">
										<#list reminds ?keys as key>
											<#if (item.scheduleRemind)??>
												<#if (item.scheduleRemind) == key>
													<option selected="selected"  value="${key}">${reminds[key]}</option> 
												<#else>
													<option value="${key}">${reminds[key]}</option> 
												</#if>
											<#else>
												<option value="${key}">${reminds[key]}</option> 
											</#if>
										</#list>
									</select>
	                            </label>
	                        </div>
	                    </section>
	                    <section>
	                        <div class="row">
	                            <label class="label col col-2"> 重复： </label>
	                            <label class="select col col-4">
	                            	<select id="scheduleRepeat" name="scheduleRepeat">
										<#list repeats?keys as key>
											<#if (item.scheduleRepeat)??>
												<#if (item.scheduleRepeat) == key>
													<option selected="selected"  value="${key}">${repeats[key]}</option> 
												<#else>
													<option value="${key}">${repeats[key]}</option> 
												</#if>
											<#else>
												<option value="${key}">${repeats[key]}</option> 
											</#if>
										</#list>
									</select>
	                            </label>
	                        </div>
	                    </section>
	                    <section>
	                        <div class="row" style="display: none;" id="overrepeat">
	                            <label class="label col col-2"> 结束重复： </label>
	                            <label class="select col col-6">
	                            	<select id="endRepeat" name="endRepeat">
										<#list endrepeats?keys as key>
											<#if (item.endRepeat)??>
												<#if (item.endRepeat) == key>
													<option selected="selected"  value="${key}">${endrepeats[key]}</option> 
												<#else>
													<option value="${key}">${endrepeats[key]}</option> 
												</#if>
											<#else>
												<option value="${key}">${endrepeats[key]}</option> 
											</#if>
										</#list>
									</select>
	                            </label>
	                        </div>
	                    </section>
	                    <section>
							<div class="row" style="display: none;" id="etime">
	                            <label class="label col col-2"> 时间： </label>
	                            <label class="input col col-8">
	                            	<input id="endRepeatTime" name="endRepeatTime" value="${((item.endRepeatTime)?string('yyyy-MM-dd HH:mm:ss'))!''}" type="text" class="ui_input_text 400w"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'schEndTime\')}'})" placeholder="结束重复时间"/>
	                            </label>
	                        </div>
						</section>
						 <section>
							<div class="row" style="display: none;" id="enumber">
	                            <label class="label col col-2"> 次数： </label>
	                            <label class="input col col-8">
	                            	<input id="endRepeatNumber" name="endRepeatNumber" value="${(item.endRepeatNumber)!''}"/>
	                            </label>
	                        </div>
						</section>
	                    <section>
	                        <div class="row">
	                            <label class="label col col-2"> 内容： </label>
	                            <label class="textarea col col-9">
	                            	<textarea class="custom-scroll required" rows="15" id="scheduleContent" name="scheduleContent" >${(item.scheduleContent)!''}</textarea>
	                            </label>
	                        </div>
	                    </section>
                    </div>
                    </fieldset>
	            </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" id="quxiao">取消</button>
                <button type="button" id="remindSaveButton" class="btn btn-primary">保存 </button>
            </div>
        </div>
    </div>
</div>

<script src="${springMacroRequestContext.contextPath}/assets/js/plugin/select2/select2.min.js"></script>

<script type="application/javascript">

/* 更多选项的点击事件  */
// $('#moreButton').click(function(){
// 	$("#moreInfo").show();
// 	$("#moreButton").hide();
// });

/* 判断重复是否为一次性活动,否则显示结束重复    */
// $("#scheduleRepeat").change(function(){
// 	var re = $("#scheduleRepeat").val();
// 	if(re != 'disposable'){
// 		/* 显示结束重复  */
// 		$("#overrepeat").show();
// 	}else{
// 		/* 还原默认数据,并隐藏结束重复   */
// 		$("#endRepeat").val("never")
// 		$("#overrepeat").hide();
		
// 		//如果此时次数是显示的,清空数据并隐藏 
// 		if($("#enumber").is(':visible')){
// 			$("#endRepeatNumber").val("");
// 			$("#enumber").hide(); 
// 		}
		
// 		//如果此时时间是显示的,清空数据并隐藏
// 		if($("#etime").is(':visible')){ 
// 			$("#endRepeatTime").val("");
// 			$("#etime").hide();
// 		}
// 	}
// });

/* 结束重复的值改变事件  */
$("#endRepeat").change(function(){
	var er = $("#endRepeat").val();
	if(er == 'time'){
		if($("#enumber").is(':visible')){//如果此时次数是显示的,清空数据并隐藏 
			$("#endRepeatNumber").val("");
			$("#enumber").hide(); 
		}
		$("#etime").show();  /* 显示时间  */
		$("#endRepeatTime").focus();
	    $("#endRepeatTime").blur(function(){
	    	var time = $("#endRepeatTime").val();
	    	if(time == ""){
	    		$("#endRepeat").val("never");
	    		$("#etime").hide();
	    	};
	    });
	}else if(er == 'number'){
		if($("#etime").is(':visible')){//如果此时时间是显示的,清空数据并隐藏 
			$("#endRepeatTime").val("");
			$("#etime").hide();
		}
		$("#enumber").show();/* 显示次数  */
		$("#endRepeatNumber").focus();
		$("#endRepeatNumber").blur(function(){
	    	var time = $("#endRepeatNumber").val();
	    	if(time == ""){
	    		$("#endRepeat").val("never");
	    		$("#enumber").hide();
	    	};
	    });
	}
});

$(document).ready(function() {
    
	/* 点击弹出框空白部分不关闭窗口     */
    $('#reminderSave').modal({backdrop: 'static'});
    
    $('#reminderSave').modal("show");
	
    /* 关闭窗口的回调函数,hide是关闭调用后在关闭    */
    $('#reminderSave').on('hide.bs.modal', function(e){
		$("#reminderSave").remove();
	});
    
    
	/* 打开窗口的回调函数  */
	$('#reminderSave').on('shown.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
	});
    

    /* 如果是修改则显示更多选项  */
//     <#if item??>
// 	    $("#moreInfo").show();
// 		$("#moreButton").hide();
		
// 		/* 判断重复的值 ,如果不是一次性活动 ,则显示结束重复    */
// 	    var re = $("#scheduleRepeat").val();
// 		if(re != 'disposable'){
// 			/* 显示结束重复  */
// 			$("#overrepeat").show();
// 			/* 显示结束重复对应的数据  */
// 			var enr = $("#endRepeat").val();
// 			if(enr == 'time'){
// 				$("#etime").show();
// 			}else if(enr == 'number'){
// 				$("#enumber").show();
// 			}
			
// 		}else{
// 			$("#overrepeat").hide();
// 		}
// 	<#else>
// 	    $("#moreInfo").hide();
// 		$("#moreButton").show();
//     </#if>

	<#if operation??>
		$('#schStartTime').attr("disabled", true);
		$('#scheduleRemind').attr("disabled", true);
		$('#scheduleRepeat').attr("disabled", true);
		$('#scheduleContent').attr("disabled", true);
		$('#remindSaveButton').hide();
		$('.close').hide();
		$('#quxiao').text("关闭");
	</#if>
	
    
    /* 点击保存  */
    $('#remindSaveButton').click(function () {
        if ($('#coreForm').valid()) {
            $('#remindSaveButton').attr("disabled", true);
            $('#coreForm').submit();
        }
    });
    
    /** 校验字段  **/
    var validator = $('#coreForm').validate({
        rules: {
        },messages : {
        	scheduleContent : {
        		required : "请输入提醒内容！"
			},
			schStartTime : {
				required : "请输入开始时间！"
			},
			schEndTime : {
				required : "请输入结束时间！"
			}
		},
        errorPlacement: function (error, element) {
            error.insertAfter(element.parent());
        }
    });

    $('#coreForm').ajaxForm({
        dataType: "json",
        type: "post",
        beforeSubmit:function(formData, jqForm, options){
        	$("#phone1").val($("#phone_number_a").val());
        },
        success: function (data) {
            $("#reminderSave").modal("hide");
            if (data.success) {
                $.smallBox({
                    title: "操作成功",
                    content: "<i class='fa fa-clock-o'></i> <i>日程提醒已成功保存...</i>",
                    color: "#659265",
                    iconSmall: "fa fa-check fa-2x fadeInRight animated",
                    timeout: 20
                });
                $('#dt_basic_schedule').DataTable().ajax.reload(null,false);
            };
        },
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function () {
                    $("#coreForm").addClass('submited');
                }
            });
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            if (textStatus == 'error') {
                $("#reminderSave").modal("hide");
                $.smallBox({
                    title: "操作失败",
                    content: "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作出现异常...</i>",
                    color: "#C46A69",
                    iconSmall: "fa fa-times fa-2x fadeInRight animated"
                });
                $('#dt_basic_schedule').DataTable().ajax.reload(null,false);
            }
        }
    });
    
});

$('#scheduleRemind').select2({
	allowClear : true,
	width : '99%'
});

$('#scheduleRepeat').select2({
	allowClear : true,
	width : '99%'
});

// function getopt(){
// 	var ss = directoryParentUUid.options[directoryParentUUid.selectedIndex].parentNode.attributes;
// 	alert(ss["label"].value);
// }

</script>
