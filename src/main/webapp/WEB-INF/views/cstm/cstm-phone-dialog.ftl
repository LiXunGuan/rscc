
<style>
	.smart-form .label {display: inline-block;}
	.tag.label.label-info {color: white;}
</style>

<div class="modal fade" id="dialog_cstm_phone">
    <div class="modal-dialog" style="width: 40%;">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                <h4 class="modal-title">主电话号码设置</h4>
            </div>
            <div class="modal-body"  >

                <form id="phoneInfos" action="${springMacroRequestContext.contextPath}/cstm/changePhone" class="smart-form" method="post">
                   	<input type="hidden" name="pId" value="${pId!''}">
                   	<input type="hidden" name="uid" value="${uuid!''}">
                   	<input type="hidden" name="phone" id="phone" value="">
                    <fieldset>
                        <section>
                            <div class="row">
                                <label class="label col col-2">电话号码</label>
                                <div class="input col col-6">
                                    <label class="input">
                                        <input name="number" value="${phone}">
                                    </label>
                                </div>
       							<label class="radio" style="float: left;">
								<input type="radio" name="radio" checked="checked" onclick="setDefault(this);">
								<i></i>设为主号码</label>
                                <label class="btn btn-sm btn-primary" style="margin-left: 10px;" id="addNumber">添加</label>
                            </div>
                        </section>
                        
                        <#if phoneList??>
                        	<#list phoneList?trim?split(",") as l>
		                    	<#if l?trim!=phone>
		                    		<section>
			                            <div class="row">
			                                <label class="label col col-2">电话号码</label>
			                                <div class="input col col-6">
			                                    <label class="input">
			                                        <input name="number" value="${l?trim!''}">
			                                    </label>
			                                </div>
			                                <label class="radio" style="float: left;">
											<input type="radio" name="radio" onclick="setDefault(this)">
											<i></i>设为主号码</label>
											<label class="btn btn-sm btn-danger" style="margin-left: 10px;" onclick="$(this).closest('section').remove()">删除</label>
			                            </div>
			                        </section>
		                    	</#if>
                        	</#list>
                        </#if>
                    </fieldset>
                </form>
                    <section class="addable hidden">
                        <div class="row">
                            <label class="label col col-2">电话号码</label>
                            <div class="input col col-6">
                                <label class="input">
                                    <input name="number" value="">
                                </label>
                            </div>
   							<label class="radio" style="float: left;">
				<input type="radio" name="radio">
				<i></i>设为主号码</label>
                            <label class="btn btn-sm btn-danger" style="margin-left: 10px;" onclick="$(this).closest('section').remove()">删除</label>
                        </div>
                    </section>

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
    
    <script type="text/javascript">
    
        $(document).ready(function(){
			
            /* 显示弹框  */
            $('#dialog_cstm_phone').modal("show");

            /** 校验字段  **/
            var validator =$('#phoneInfos').validate({
                rules : {
                	poolName  :  {
                		required : true,
                		maxlength : 8
                	}
                },
                messages : {
                	poolName :{
                		required : "${c.validate_required}",
                		maxlength :"${c.validate_maxlength}",
                	}
                },
                errorPlacement : function(error, element) {
                    error.insertAfter(element.parent());
                }
            });

        });
        
        $("#msubmit").click(function(){
        	
			if($("input:radio:checked").val()=="on"){
				
				$("#phone").val($("input:radio:checked").parent().prev().find("input").val());
				$("#phoneInfos").submit();
			}else{
				alert("请选择一个默认主号码！");
				return;
			}
        	
        	
        });

        $('#phoneInfos').ajaxForm({
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
					
                }
                $('#dialog_cstm_phone').modal("hide");
                
                cstmTabel.ajax.reload(null,false);;
                
            },
            submitHandler : function(form) {
                $(form).ajaxSubmit({
                    success : function() {
                        $("#phoneInfos").addClass('submited');
                    }
                });
            },
            error: function(XMLHttpRequest,textStatus , errorThrown){
                if(textStatus=='error'){
                    $('#dialog_cstm_phone').dialog('hide');
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
        
        
        
        
        
        /* 设置显示号码 */
        function setDefault(obj){
        	var val = $(obj).parent().prev().find("input").val();
        	if(""!=val && val!="undifined"){
        		$("#phone").val(val);
        	}
        	
//         	alert($(obj).parent().prev().find("input").val());
        	
        }
        
        $('#dialog_cstm_phone').on('hide.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
        
		});
        
        
        $('#dialog_cstm_phone').on('hidden.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
		});
        
        $("#addNumber").click(function(){
        	
        	$("#phoneInfos fieldset").append($(".addable").clone().removeClass("addable").removeClass("hidden"));
        	
        });
        
        
        
        
       
    </script>






