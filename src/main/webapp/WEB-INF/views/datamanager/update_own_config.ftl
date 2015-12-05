<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<div class="modal fade" id="dialogOwnConfig">
    <div class="modal-dialog" style="width: 600px">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                <h4 class="modal-title">修改配置</h4>
            </div>
            <div class="modal-body"  >
                <form id="messageinfoa" action="${springMacroRequestContext.contextPath}/newuserdata/userconfigSave" class="smart-form" method="post">
                    <fieldset>
	     				<div class="row">
	     					<div class="col col-3">
							<label class="label">自动呼叫时机</label>
							</div>
							<div class="col col-8">
							
								<input type="hidden" name="uid" id="userUuid" value="${user.uid}">
								<div class="row">
									<div class="col">
										<label class="radio input-sm">
											<input type="radio" class="radiobox style-0" name="timing" value="0" checked="checked">
											<span class="hasInput">上一通挂断后</span> 
										</label>
									</div>
									<div class="col col-4" id="afterTime" style="padding-left:0px">
										<label class="input input-sm">
											<span class="icon-append">秒</span>
											<input type="text" name="time" class="required digits number" min="1" max="60">
										</label>
									</div>
								</div>
								<div class="row">
									<div class="col">
										<label class="radio input-sm">
											<input type="radio" class="radiobox style-0" name="timing" value="1">
											<span>关闭所有弹屏窗口时</span> 
										</label>
										<span class='alert'><span style='color:red;'>注:</span>如果先关闭弹屏，再挂电话，系统将会终止自动呼叫</span>
									</div>
								</div>
							</div>
							<div class="col col-8" >
								<label class="label">呼叫未接通默认保存项</label>
							</div>
							<div class="row">
								<div class="col col-4" style="margin-left: 28%;">
									<select name="nocall" id="nocall">
											<option value="noanswer" <#if defaultUserNocall?? && defaultUserNocall=="noanswer">selected="selected"</#if> >无人接听</option>
											<option value="abandon" <#if defaultUserNocall?? && defaultUserNocall=="abandon">selected="selected"</#if>>号码无效</option>
											<option value="global_share" <#if defaultUserNocall?? && defaultUserNocall=="global_share">selected="selected"</#if>>共享池</option>
									</select>
								</div>
							</div>
							<div class="col col-8" >
								<label class="label">呼叫接通后默认保存项</label>
							</div>
							<div class="row">
								<div class="col col-4" style="margin-left: 28%;">
									<select name="intent" id="intent">
											<#if intents??> 
											<#list intents as intent>
											<option class="intent" value="${intent.uid}" <#if defaultUserCfg?? && defaultUserCfg == "${intent.uid}">selected="selected"</#if>>${intent.intentName}</option>
											</#list> </#if>
											<option value="global_share" <#if defaultUserCfg?? && defaultUserCfg == "global_share">selected="selected"</#if>>共享池</option>
											<!-- <option value="blacklist">黑名单</option> -->
									</select>
								</div>
							</div>
						</div>
                    </fieldset>
                </form>
            </div>
            <div class="modal-footer">
                <button id="msubmita" type="button" class="btn btn-primary">
                    保  存
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    关 闭
                </button>
            </div>
     	</div>
   	</div>
     	
   	<script type="text/javascript">
        $(document).ready(function(){
        	
        	var oldConfig = ${(config)!'{"timing":"0","time":"5"}'};
        	
        	$("#intent").select2({
        		width:"100%"
        	});
        	$("#nocall").select2({
        		width:"100%"
        	});
        	
        	if(oldConfig.timing == 0) {
           		$("#dialogOwnConfig [name='time']").parent().show();
        		$("#dialogOwnConfig [name='timing']:first").click();
        		$("#dialogOwnConfig .alert").hide();
        	} else {
           		$("#dialogOwnConfig [name='time']").parent().hide();
        		$("#dialogOwnConfig [name='timing']:last").click();
        		$("#dialogOwnConfig .alert").show();
        	}
       		$("#dialogOwnConfig [name='time']").val(oldConfig.time);
        	
       		$("input[name=timing]").change(function(){
            	if ($(this).val() == "0"){
            		$("#dialogOwnConfig [name='time']").parent().show();
            		$("#dialogOwnConfig .alert").hide();
            	} else {
            		$("#dialogOwnConfig [name='time']").parent().hide();
            		$("#dialogOwnConfig .alert").show();
            	}
            });
       		
       		
	        var setting = {
	            check: {
	                enable: true
	            },
	            data: {
	                simpleData: {
	                    enable: true
	                }
	            }
	        };

            /* 显示弹框  */
            $('#dialogOwnConfig').modal("show");
            
            /** 校验字段  **/
            var validator =$('#messageinfoa').validate({
            	rules:{
            		time:{
            			
            		}
            	},
            	messages:{
            		time:{
            			required:"请输入一个数字",
            		}
            	},
            	 errorPlacement : function(error, element) {
                     error.insertAfter(element.parent());
                 }
            });
            /* 提交按钮单击事件  */
			$('#msubmita').click(function(){
                if($('#messageinfoa').valid()){
                    $('#msubmita').attr("disabled",true);
                    
                    var temp = $("#messageinfoa").serializeArray();
                    var result = {};
                    for(var obj in temp) {
                    	result[temp[obj].name] = temp[obj].value;
                    }
                    delete result.uid;
                    $.post(getContext() + "newuserdata/userconfigSave", {uid:$("#userUuid").val(),config:JSON.stringify(result)}, function(data) {
            	
            			if(data.success){
            				$.smallBox({
                                title : "操作成功",
                                content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
                                color : "#659265",
                                iconSmall : "fa fa-check fa-2x fadeInRight animated",
                                timeout : 2000
                            });
            				window.parent.autocallConfig = result;
            			}else{
            				$.smallBox({
                                title : "操作失败",
                                content : "<i class='fa fa-clock-o'></i> <i>操作失败</i>",
                                color : "#C46A69",
                                iconSmall : "fa fa-times fa-2x fadeInRight animated",
                                timeout : 2000
                            });
            			}
            			$('#dialogOwnConfig').modal('hide');
            		},"json");
//                     $('#messageinfoa').submit();
                }
            });
            
            $('#messageinfoa').ajaxForm({
                dataType:"json",
                success: function(data) {
                    if(data.success){
                        $.smallBox({
                            title : "操作成功",
                            content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
                            color : "#659265",
                            iconSmall : "fa fa-check fa-2x fadeInRight animated",
                            timeout : 2000
                        });
                        $('#msubmita').attr("disabled",false);
                        $("table.dataTable").DataTable().ajax.reload(null,false);;
                    }
                    $('#dialogOwnConfig').modal("hide");
                    validator.resetForm();
                },
                submitHandler : function(form) {
                    $(form).ajaxSubmit({
                        success : function() {
                            $("#messageinfoa").addClass('submited');
                        }
                    });
                },
                error: function(XMLHttpRequest,textStatus , errorThrown){
                    if(textStatus=='error'){
                        $('#dialogOwnConfig').modal('hide');
                        $.smallBox({
                            title : "操作失败",
                            content : "<i class='fa fa-clock-o'></i> <i>操作失败</i>",
                            color : "#C46A69",
                            iconSmall : "fa fa-times fa-2x fadeInRight animated",
                            timeout : 2000
                        });

                        $("table.dataTable").DataTable().ajax.reload(null,false);;
                    }
                }
            });
            
            /* 关闭窗口的回调函数  */
            $('#dialogOwnConfig').on('hide.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
    			$("#dialogOwnConfig").remove();
    		});
        });

    </script>
</div>

	
    

