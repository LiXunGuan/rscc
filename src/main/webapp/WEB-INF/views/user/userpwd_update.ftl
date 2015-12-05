<div class="modal fade" id="userPwdSave" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 40%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                <h4 class="modal-title">个人配置</h4>
            </div>
            <div class="modal-body">
	            <div class="tabs-left">
					<ul class="nav nav-tabs tabs-left" id="demo-pill-nav">
						<li class="active">
							<a href="#tab-userConfig" data-toggle="tab" id="userConfig"> 个人信息 </a>
						</li>
						<li>
							<a href="#tab-safeConfig" data-toggle="tab" id="safeConfig"> 安全设置 </a>
						</li>
						<li>
							<a href="#tab-callConfig" data-toggle="tab" id="callConfig"> 呼叫配置 </a>
						</li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane active" id="tab-userConfig">
			                <form action="${springMacroRequestContext.contextPath}/user/user/changename" class="smart-form" id="userConfigForm">
								<header>修改个人信息</header>
			                	<fieldset>
				                    <section>
				                        <div class="row">
				                            <label class="label col col-3"> 姓名： </label>
				                            <label class="input col col-5">
				                            	<input id="userName" name="userName" type="text" value="${user.userDescribe!''}"/>
				                            </label>
				                        </div>
				                    </section>
			                    </fieldset>
				            </form>
			            </div>
						<div class="tab-pane" id="tab-safeConfig">
			                <form action="${springMacroRequestContext.contextPath}/user/user/savePwd" class="smart-form" id="userPwdForm">
								<header>修改密码及邮箱</header>
			                	<fieldset>
			                		<input type="hidden" id="uid" name="uid" value="${(user.uid)!''}">
			                		<input type="hidden" id="loginName" name="loginName" value="${(user.loginName)!''}">
				                    <section>
				                        <div class="row">
				                            <label class="label col col-3"> 旧密码： </label>
				                            <label class="input col col-5">
				                            	<input id="oldPwd" name="oldPwd" type="password" />
				                            </label>
				                        </div>
				                    </section>
				                    <section>
				                        <div class="row">
				                            <label class="label col col-3"> 新密码： </label>
				                            <label class="input col col-5">
				                            	<input id="newpwd" name="newpwd" type="password" />
				                            </label>
				                        </div>
				                    </section>
				                    <section>
				                        <div class="row">
				                            <label class="label col col-3"> 确认密码： </label>
				                            <label class="input col col-5">
				                            	<input id="password" name="password" type="password"  />
				                            </label>
				                        </div>
				                    </section>
				                    <section>
				                        <div class="row">
				                            <label class="label col col-3"> 邮箱： </label>
				                            <label class="input col col-5">
				                            	<input id="email" name="email" type="email"  class="email" value="${user.mail!''}"/>
				                            </label>
				                        </div>
				                    </section>
			                    </fieldset>
				            </form>
			            </div>
						<div class="tab-pane" id="tab-callConfig">
							<form id="userAutoCallForm" action="${springMacroRequestContext.contextPath}/newuserdata/userconfigSave" class="smart-form" method="post">
			                    <header>自动呼叫配置</header>
			                    <fieldset>
				     				<div class="row">
				     					<div class="col col-6">
										<label class="label">自动呼叫时机</label>
										</div>
										<div class="col col-8" style="margin-left: 28%;">
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
														<input type="text" name="time" class="required digits number" min="1">
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
										<div class="col col-4" >
											<label class="label">呼叫未接通默认保存项</label>
										</div>
										<div class="col col-5" style="margin-left: 28%;">
											<select name="nocall" id="nocall">
													<option value="noanswer" <#if defaultUserNocall?? && defaultUserNocall=="noanswer">selected="selected"</#if> >无人接听</option>
													<option value="abandon" <#if defaultUserNocall?? && defaultUserNocall=="abandon">selected="selected"</#if>>号码无效</option>
													<option value="global_share" <#if defaultUserNocall?? && defaultUserNocall=="global_share">selected="selected"</#if>>共享池</option>
											</select>
										</div>
										<div class="col col-4" >
											<label class="label">呼叫接通后默认保存项</label>
										</div>
										<div class="col col-5" style="margin-left: 28%;">
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
			                    </fieldset>
			                </form>
						</div>
		            </div>
	            </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" id="userPwdSaveButton" class="btn btn-primary">保存 </button>
            </div>
        </div>
    </div>
</div>

<script type="application/javascript">

	$(document).ready(function() {
	    
	    $('#userPwdSave').modal("show");
	    
	    $("#intent").select2({
	    	width:"100%"
	    });
	    $("#nocall").select2({
	    	width:"100%"
	    });
		
		/* 关闭窗口的回调函数,hide是关闭调用后在关闭    */
	    $('#userPwdSave').on('hide.bs.modal', function(e){
			$("#userPwdSave").remove();
		});
	    
	    $('#userPwdSaveButton').click(function () {
	    	
	    	if($("#email").val()!='' || $("#newpwd").val()!=''){
		        if ($('#userPwdForm').valid()) {
		        	$('#userPwdForm').submit();
		        }
	    	}else{
	    		
//     		 	$("#userPwdSave").modal("hide");
// 	    		$("#userPwdSave").remove();
	    	}
	    	
	    });
	    
		$('#userPwdSaveButton').click(function () {
	    	
        	$('#userConfigForm').submit();
	    	
	    });
	    
	    /** 校验字段  **/
	    var validator = $('#userPwdForm').validate({
	        rules: {
	        	oldPwd : {
	        		remote : {
						type : 'post',
						url : getContext()+ 'user/user/checkoldPwd',
						data : {
							uid : function() {
								return $("#uid").val();
							},
							oldPwd : function() {
								return $("#oldPwd").val();
							},
							loginName : function(){
								return $("#loginName").val();
							}
						}
					}
	        	},
	        	password : {
	        		equalTo:'#newpwd'
	        	}
	        },messages : {
	        	oldPwd : {
	        		remote : "旧密码错误！"
	        	},
	        	newpwd : {
	        	},
	        	password : {
	        		equalTo : "两次密码不一致！"
	        	}
			},
	        errorPlacement: function (error, element) {
	            error.insertAfter(element.parent());
	        }
	    });
	
	    $('#userConfigForm').ajaxForm({
	        dataType: "json",
	        type: "post",
	        success: function (data) {
	        	
	        }
	    });
	    
	    $('#userPwdForm').ajaxForm({
	        dataType: "json",
	        type: "post",
	        success: function (data) {
	            $("#userPwdSave").modal("hide");
	            if (data.success) {
// 	                $.smallBox({
// 	                    title: "操作成功",
// 	                    content: "<i class='fa fa-clock-o'></i> <i>密码修改成功...</i>",
// 	                    color: "#659265",
// 	                    iconSmall: "fa fa-check fa-2x fadeInRight animated",
// 	                    timeout: 2000
// 	                });
	                window.location.href="logout";
	            };
	        },
	        submitHandler: function (form) {
	            $(form).ajaxSubmit({
	                success: function () {
	                    $("#userPwdForm").addClass('submited');
	                }
	            });
	        },
	        error: function (XMLHttpRequest, textStatus, errorThrown) {
	            if (textStatus == 'error') {
	                $("#userPwdSave").modal("hide");
	                $.smallBox({
	                    title: "操作失败",
	                    content: "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作出现异常...</i>",
	                    color: "#C46A69",
	                    iconSmall: "fa fa-times fa-2x fadeInRight animated"
	                });
	            }
	        }
	    });
	    
	    $('#oldPwd').bind('keypress',function(event){
            if(event.keyCode == "13"){
            	$('#userPwdSaveButton').click();
            }
        });
	    
	    $('#newpwd').bind('keypress',function(event){
            if(event.keyCode == "13"){
            	$('#userPwdSaveButton').click();
            }
        });
	    
	    $('#password').bind('keypress',function(event){
            if(event.keyCode == "13"){
            	$('#userPwdSaveButton').click();
            }
        });
	    
	});

	
	
	//保存呼叫信息
	$(document).ready(function(){
    	
    	var oldConfig = ${(config)!'{"timing":"0","time":"5"}'};
    	
    	if(oldConfig.timing == 0) {
       		$("#userAutoCallForm [name='time']").parent().show();
    		$("#userAutoCallForm [name='timing']:first").click();
    		$("#userAutoCallForm .alert").hide();
    	} else {
       		$("#userAutoCallForm [name='time']").parent().hide();
    		$("#userAutoCallForm [name='timing']:last").click();
    		$("#userAutoCallForm .alert").show();
    	}
   		$("#userAutoCallForm [name='time']").val(oldConfig.time);
    	
   		$("input[name=timing]").change(function(){
        	if ($(this).val() == "0"){
        		$("#userAutoCallForm [name='time']").parent().show();
        		$("#userAutoCallForm .alert").hide();
        	} else {
        		$("#userAutoCallForm [name='time']").parent().hide();
        		$("#userAutoCallForm .alert").show();
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

        /** 校验字段  **/
        var validator =$('#userAutoCallForm').validate({
        	rules:{
        		time:{
        			max:60
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
		$('#userPwdSaveButton').click(function(){
//             if($('#userAutoCallForm').valid()){
            if(1){
                $('#userPwdSaveButton').attr("disabled",true);
                
                var temp = $("#userAutoCallForm").serializeArray();
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
        				window.autocallConfig = result;
        			}else{
        				$.smallBox({
                            title : "操作失败",
                            content : "<i class='fa fa-clock-o'></i> <i>操作失败</i>",
                            color : "#C46A69",
                            iconSmall : "fa fa-times fa-2x fadeInRight animated",
                            timeout : 2000
                        });
        			}
        			$("#userPwdSave").modal("hide");
        		},"json");
            }
        });
        
	})
	
	
	
</script>
