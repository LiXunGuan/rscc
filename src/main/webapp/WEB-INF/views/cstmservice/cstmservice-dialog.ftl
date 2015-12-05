
<style>
	.smart-form .label {display: inline-block;}
	.tag.label.label-info {color: white;}
	.row label.col.col-3{text-align: right;}
</style>

<div class="modal fade" id="dialog_cstmservice">
    <div class="modal-dialog" style="width: 600px">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                <h4 class="modal-title">工单信息</h4>
            </div>
            <div class="modal-body"  >

                <form id="cstmserviceInfo" action="${springMacroRequestContext.contextPath}/cstmservice/save" class="smart-form" method="post">
                    <fieldset>
                        <section>
                            <div class="row">
                                <label class="label col col-3">工单名称</label>
                                <div class="input col col-8">
                                    <label class="input">
                                        <input type="hidden" name="uid" value="${(cs.uid)!''}">
                                        <input name="cstmserviceName" value="${(cs.cstmserviceName)!''}">
                                    </label>
                                </div>
                            </div>
                        </section>
                        <section>
                            <div class="row">
                                <label class="label col col-3">工单负责人</label>
                                <div class="select col col-8">
									<select name="cstmserviceAssignee" id="cstmserviceAssignees" class="from-control">
										<#if userList??>
<!-- 											<option value="">----请选择----</option> -->
											<#list userList as a>
												<option value="${a.uid}">${a.loginName}</option>
											</#list>
										<#else>
											<option>----暂无----</option>
										</#if>
									</select>
                                </div>
                            </div>
                        </section>

                        <section>
                            <div class="row">
                                <label class="label col col-3 " >计划结束时间</label>
                                <div class="input col col-8">
                                    <label class="input">
                                        <#if (cs.cstmservicePlanEndTime)??>
	                                        <input name="cstmservicePlanEndTimes" id="cstmservicePlanEndTimes" value="${(cs.cstmservicePlanEndTime)?datetime!''}">
                                        <#else>
	                                        <input name="cstmservicePlanEndTimes" id="cstmservicePlanEndTimes" value="${(cs.cstmservicePlanEndTime)!''}">
                                        </#if>
                                    </label>
                                </div>
                            </div>
                        </section>
                        <section>
                            <div class="row">
                                <label class="label col col-3">工单描述</label>
                                <div class="input col col-8">
                                    <label class="input">
										<textarea name="cstmserviceDescription" id="cstmserviceDescription" rows="" cols="" style="width: 100%;resize:none;height: 120px;"></textarea>

                                    </label>
                                </div>
                            </div>
                        </section>
                </form>

            </div>
            <div class="modal-footer">
                <button id="msubmit" type="button" class="btn btn-primary">
                    保  存
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    关 闭
                </button>
            </div> 
        </div>
    </div>
    
    
    <script src= "${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-timepicker/bootstrap-timepicker.min.js"></script>
    <script type="text/javascript" src="/Servers/resource/DEVELOPER/AngularJS_version/public/plugin/angular/angular.min.js"></script>
    <script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-daterangepicker-master/moment.js"></script>
    <script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/calendar/lang/zh-cn.js"></script>
    <script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/demo-controller.js"></script>
    <script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/datetimepicker.js"></script>
    
    
    <script type="text/javascript">
    
        $(document).ready(function(){
			
            /* 显示弹框  */
            $('#dialog_cstmservice').modal({
            	show :true,
            	backdrop:"static",
            	
            });
            
            /** 校验字段  **/
            var validator =$('#cstmserviceInfo').validate({
                rules : {
                	cstmserviceName  :  {
                		required : true,
                		maxlength : 100
                	},
                	cstmservicePlanEndTimes : {
                		required : true,
                	},
                	cstmserviceDescription : {
                		required : true
                	}
                },
                
                messages : {
                	cstmserviceName :{
                		required : "${c.validate_required}",
                		maxlength :"${c.validate_maxlength}"
                	},
                	cstmservicePlanEndTimes :{
                		required : "${c.validate_required}",
               	    },
               	    cstmserviceDescription : {
               	    	required : "${c.validate_required}"
               	    }
                },
                errorPlacement : function(error, element) {
                    error.insertAfter(element.parent());
                }
            });

            /* 提交按钮单击事件  */
            $('#msubmit').click(function(){
                if($('#cstmserviceInfo').valid()){
                	$('#msubmit').attr("disabled",true);
                    $('#cstmserviceInfo').submit();
                }
            });

            $('#cstmserviceInfo').ajaxForm({
                dataType:"json",
                data : { 
                	'ssName': function(){
                		return $("#cstmserviceAssignees :selected").text();
                	}
                },
                success: function(data) {
                    if(data.success){
                        $.smallBox({
                            title : "操作成功",
                            content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
                            color : "#659265",
                            iconSmall : "fa fa-check fa-2x fadeInRight animated",
                            timeout : 2000
                        });
                        dt_basic_cstmservice.ajax.reload(null,false);;
                    }
                    $('#dialog_cstmservice').modal("hide");
                },
                submitHandler : function(form) {
                    $(form).ajaxSubmit({
                        success : function() {
                            $("#cstmserviceInfo").addClass('submited');
                        }
                    });
                },
                error: function(XMLHttpRequest,textStatus , errorThrown){
                	
                    if(textStatus=='error'){
                        $('#dialog_cstmservice').dialog('hide');
                        $.smallBox({
                            title : "操作失败",
                            content : "<i class='fa fa-clock-o'></i> <i>操作失败</i>",
                            color : "#C46A69",
                            iconSmall : "fa fa-times fa-2x fadeInRight animated",
                            timeout : 2000
                        });

                        dt_basic_cstmservice.ajax.reload(null,false);;
                    }
                }
            });
        });
        
        $('#dialog_cstmservice').on('hidden.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
        	$("#dialog_cstmservice").remove();
        	$('#dt_basic_cstmservice').DataTable().ajax.reload(null,false);;
		});
        
        $('#cstmservicePlanEndTimes').datetimepicker({
            format: "yyyy-mm-dd hh:ii",
            weekStart: 1,
            todayBtn:  1,
    		autoclose: 1,
    		todayHighlight: 1,
    		startView: 2,
    		forceParse: 0,
            showMeridian: 1
    	});
       
        $('#cstmserviceRealEndTimes').datetimepicker({
            format: "yyyy-mm-dd hh:ii",
            weekStart: 1,
            todayBtn:  1,
    		autoclose: 1,
    		todayHighlight: 1,
    		startView: 2,
    		forceParse: 0,
            showMeridian: 1
    	});
//         $('#cstmservicePlanEndTimes').datepicker({
//         	dateFormat: 'yy-mm-dd',
//         });
       
//         $('#cstmserviceRealEndTimes').datepicker({
        	
//         	dateFormat: "yy-mm-dd",
//         });
        
        $('#cstmserviceAssignees').select2({
            allowClear : true,
            width: '99%'
         });
        
    </script>






