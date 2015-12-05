<div class="modal fade" id="forgetPwd" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	 <div class="modal-dialog" style="width: 500px">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">找回密码</h4>
            </div>
            <div class="modal-body">
                <form action="${springMacroRequestContext.contextPath}/forgetpwd/sendMail" class="smart-form" id="forgetPwdForm" method = "post">
                	<fieldset style="padding-top: 2px;">
                		<input type="hidden" id="uid" name="uid" value="${(user.uid)!''}">
	                    <section>
	                        <div class="row">
	                            <label class="label col col-3"> 用户名： </label>
	                            <label class="input col col-7">
	                            	<input id="loginname" name="loginname" type="text" ;"/>
	                            </label>
	                        </div>
	                    </section>
	                    <section>
	                        <div class="row">
	                            <label class="label col col-3"> 绑定邮箱： </label>
	                            <label class="input col col-7">
	                            	<input id="mail" name="mail" type="text" ;"/>
	                            </label>
	                        </div>
	                    </section>
                    </fieldset>
	            </form>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" id="forgetPwdButton" class="btn btn-primary">提交 </button>
            </div>
        </div>
    </div>
    </div>
</div>

<script type="application/javascript">

	$(document).ready(function() {
	    
	    $('#forgetPwd').modal("show");
		
		/* 关闭窗口的回调函数,hide是关闭调用后在关闭    */
	    $('#forgetPwd').on('hide.bs.modal', function(e){
			$("#forgetPwd").remove();
		});
	    
	    $('#forgetPwdButton').click(function (loginname,mail) {
	        if ($('#forgetPwdForm').valid()) {
	        	$('#forgetPwdForm').submit();
	        }
	    });
	    
	   $('#forgetPwdForm').ajaxForm({
             dataType:"json",
             success: function(data) {
                 if(data.success){
                      $.smallBox({
                            title : "操作成功",
                            content : "<i class='fa fa-clock-o'></i> <i>操作成功，已发送邮件</i>",
                            color : "#659265",
                            iconSmall : "fa fa-check fa-2x fadeInRight animated",
                            timeout : 2000
                        });
                        $('#forgetPwdButton').attr("disabled",false);
                       
                    }
		    		else{
                    	$.smallBox({
                            title : "操作失败",
                            content : "<i class='fa fa-clock-o'></i> <i>操作失败，请正确填写用户名和邮箱</i>",
                            color : "#C46A69",
                            iconSmall : "fa fa-times fa-2x fadeInRight animated",
                            timeout : 2000
                        });
                    }
                    $('#forgetPwd').modal("hide");
                },
                submitHandler : function(form) {
                    $(form).ajaxSubmit({
                        success : function() {
                            $("#forgetPwdForm").addClass('submited');
                        }
                    });
                },
                error: function(XMLHttpRequest,textStatus , errorThrown){
                    if(textStatus=='error'){
                        $('#forgetPwd').modal('hide');
                        $.smallBox({
                            title : "操作失败",
                            content : "<i class='fa fa-clock-o'></i> <i>操作失败</i>",
                            color : "#C46A69",
                            iconSmall : "fa fa-times fa-2x fadeInRight animated",
                            timeout : 2000
                        });
                    }
                }
            });
            
	    /** 校验字段  **/
	   $("#forgetPwdForm").validate({
	        rules: {
	        	loginname : {
	        		required : true,
	        	},
	        	mail : {
	        		required : true,
	        		email : true,
	        		remote : {
						url:"${springMacroRequestContext.contextPath}/${model}/checkMail",
						type:"post",
						data : {
							loginname :　function(){
								return $('#loginname').val();
							},
						mail : function(){
							return $('#mail').val();
							}
						}
					} 
	        	}
	        },
	        messages : {
	        	loginname : {
	        		required : "请输入用户名",
	        	},
	        	mail : {
	        		required : "请输入邮箱,用于接收重置密码邮件",
	        		remote : "请确认邮箱是否正确"
	        	}
			},
		
	        errorPlacement: function (error, element) {
	            error.css({"font-style":"normal","color":"#D56161"}).appendTo(element.parent());
	        }
	    });      
	});

</script>
