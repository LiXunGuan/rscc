<head>
<#include "/index/importcss.ftl">
</head>
<#include "/index/importsjs.ftl">
<div class="modal fade" id="pwdReset" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">重置密码</h4>
            </div>
            <div class="modal-body">
                <form action="${springMacroRequestContext.contextPath}/forgetpwd/savePwd" class="smart-form" id="resetPwd">
                	<fieldset style="padding-top: 2px;">
                		<input type="hidden" id="sid" name="sid" value="${(checkinfo)!''}">
	                    <section>
	                        <div class="row">
	                            <label class="label col col-3"> 新密码： </label>
	                            <label class="input col col-5">
	                            	<input id="newpwd" name="newpwd" type="password" class="required" onkeydown="onkey();"/>
	                            </label>
	                        </div>
	                    </section>
	                    <section>
	                        <div class="row">
	                            <label class="label col col-3"> 确认密码： </label>
	                            <label class="input col col-5">
	                            	<input id="password" name="password" type="password" onkeydown="onkey();" class="required"/>
	                            </label>
	                        </div>
	                    </section>
                    </fieldset>
	            </form>
            </div>
            <div class="modal-footer">
            	<p style="text-align:left;"><b>tip:&nbsp;</b>本页面将在您密码重置成功后自动清除!</p>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" id="resetPwdSaveButton" class="btn btn-primary">保存 </button>
            </div>
        </div>
    </div>
</div>

<script type="application/javascript">

	/* 禁用空白自动关闭*/
    $('#pwdReset').modal({backdrop: 'static'});

	$(document).ready(function(){
	   
	    $('#pwdReset').modal("show");
	    
		/* 关闭窗口的回调函数,hide是关闭调用后在关闭    */
		$('#resetPwd').on('hide.bs.modal', function(e){
			$("#userPwdSave").remove();
		});
	    
	    $('#resetPwdSaveButton').click(function () {
	    	
	        if ($('#resetPwd').valid()) {
	        	$('#resetPwd').submit();
	        }
	    });
	    
	    /** 校验字段  **/
	    var validator = $('#resetPwd').validate({
	        rules: {
	        	
	        	password : {
	        		equalTo:'#newpwd'
	        	}
	        },messages : {
	        	newpwd : {
	        		required : "请输入新密码！"
	        	},
	        	password : {
	        		required : "请输入确认密码！",
	        		equalTo : "两次密码不一致！"
	        	}
			},
	        errorPlacement: function (error, element) {
	            error.insertAfter(element.parent());
	        }
	    });
	
	    $('#resetPwd').ajaxForm({
	        dataType: "json",
	        type: "post",
	        success: function (data) {
	            $("#userPwdSave").modal("hide");
	            if (data.success) {
	                $.smallBox({
	                    title: "操作成功",
	                    content: "<i class='fa fa-clock-o'></i> <i>密码修改成功...</i>",
	                    color: "#659265",
	                    iconSmall: "fa fa-check fa-2x fadeInRight animated",
	                    timeout: 2000,
	                });
	                setTimeout(function(){
		                if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){ 
						     window.location.href = '${springMacroRequestContext.contextPath}';
						}else{
	                		window.close();
	                	}
                	},3000
               		)
	                
	            };
	        },
	        
	        submitHandler: function (form) {
	            $(form).ajaxSubmit({
	                success: function () {
	                    $("#resetPwd").addClass('submited');
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
	    
	    $('#newpwd').bind('keypress',function(event){
            if(event.keyCode == "13"){
            	$('#resetPwdSaveButton').click();
            }
        });
	    
	    $('#password').bind('keypress',function(event){
            if(event.keyCode == "13"){
            	$('#resetPwdSaveButton').click();
            }
        });
	    
	});

</script>
