<div class="modal fade" id="bindExtenSave" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 30%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    <strong style="font-size: small;">关闭</strong>
                </button>
                <h4 class="modal-title">绑定分机</h4>
            </div>
            <div class="modal-body">
                <form action="${springMacroRequestContext.contextPath}/bindExtenSave" class="smart-form" id="bindExtenForm">
                	<fieldset style="padding-top: 2px;">
                		<input type="hidden" name="loginName" id="loginName" value="${(uname)!''}" />
						<input type="hidden" name="password" id="password" value="${(upwd)!''}" />
	                    <section>
	                        <div class="row">
	                            <label class="label col col-3" style="width: inherit;"> 分机号： </label>
	                            <label class="input col col-7">
	                            	<input id="extension" name="extension"  value="${(ext)!''}"/>
	                            </label>
	                            <div class="note">
									<font color="red" size="2" id="checkresult"></font>
								</div>
	                        </div>
	                    </section>
                    </fieldset>
	            </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" id="bindExtenSaveButton" class="btn btn-primary">保存 </button>
            </div>
        </div>
    </div>
</div>

<script type="application/javascript">

	$(document).ready(function() {
	    
	    $('#bindExtenSave').modal("show");
		
		/* 关闭窗口的回调函数,hide是关闭调用后在关闭    */
	    $('#bindExtenSave').on('hide.bs.modal', function(e){
			$("#bindExtenSave").remove();
		});
	    
	    $('#bindExtenSaveButton').click(function () {
	        if ($('#bindExtenForm').valid()) {
	        	var url = getContext() + "checkExten";
	            var uname = $("#loginName").val();
	            var upwd = $("#password").val();
	            var exten = $("#extension").val();
	     		$.post(url, {uname:uname, upwd:upwd, exten:exten}, function(data){
	     			if(data.success){
	     				console.log(data.registered);
	     				if(data.registered != undefined){
	     					if(confirm(data.registered)){
	     						//强制解绑
	     						var url = "${springMacroRequestContext.contextPath}/ubind";
	     				    	$.post(url, {exten:exten, uname:uname, upwd:upwd, operation:"bindexten"}, function(data){
	     				    		 if(data.success){
	     				    			window.location.reload(true);
	     				    		 }
	     				    	},"json");
	     					}else{
	     						return false;
	     					};
	     				}else{
		     				$("#checkresult").text(data.checkresult);
		     				return false;
	     				}
	     			}else{
		            	window.location.reload(true);
	     				//不需要提交表单
// 						$('#bindExtenSaveButton').attr("disabled", true);
// 			            $('#bindExtenForm').submit();
	     			}
	     		},"json");
	        }
	    });
	    
	    /** 校验字段  **/
	    var validator = $('#bindExtenForm').validate({
	        rules: {
	        	extension : {
	        		required : true
	        	}
	        },messages : {
	        	extension : {
	        		required : "请输入分机号！"
	        	}
			},
	        errorPlacement: function (error, element) {
	            error.insertAfter(element.parent());
	        }
	    });
	
	    $('#bindExtenForm').ajaxForm({
	        dataType: "json",
	        type: "post",
	        success: function (data) {
	            $("#bindExtenSave").modal("hide");
	            if (data.success) {
	                $.smallBox({
	                    title: "操作成功",
	                    content: "<i class='fa fa-clock-o'></i> <i>分机绑定成功...</i>",
	                    color: "#659265",
	                    iconSmall: "fa fa-check fa-2x fadeInRight animated",
	                    timeout: 2000
	                });
	            };
	        },
	        submitHandler: function (form) {
	            $(form).ajaxSubmit({
	                success: function () {
	                    $("#bindExtenForm").addClass('submited');
	                }
	            });
	        },
	        error: function (XMLHttpRequest, textStatus, errorThrown) {
	            if (textStatus == 'error') {
	                $("#bindExtenSave").modal("hide");
	                $.smallBox({
	                    title: "操作失败",
	                    content: "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作出现异常...</i>",
	                    color: "#C46A69",
	                    iconSmall: "fa fa-times fa-2x fadeInRight animated"
	                });
	            }
	        }
	    });
	    
	    $('#extension').bind('keypress',function(event){
            if(event.keyCode == "13"){
            	$('#bindExtenSaveButton').click();
            	return false;
            }
        });
	    
	});

</script>
