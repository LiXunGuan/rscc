<style>
.invalid{
	border-width:thin;
}
.valid{
	border-width:thin;
}
</style>
<div class="modal fade" id="dialog_addrate">
    <div class="modal-dialog" style="width: 32%">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                <h4 class="modal-title">添加费率</h4>
            </div>
            <div class="modal-body"  >

                <form id="rateinfo" action="${springMacroRequestContext.contextPath}/billing/rate/save" class="smart-form" method="post">
                    <input name="uid" id="uid" type="hidden" value="">
                	<section>
						<label class="label col">计费类型</label> 
						<label class="col-5" style="width: 202px"> 
							<select class="select2 col-3" name="billRateType" id="billRateType">
									<option value="Sip" >按分机计费</option>
									<option value="SkillGroup">按技能组计费</option>
							</select>
						</label>
					</section>
					
	                <section>
	                    	<label class="label col">费率名称</label>
							<label class="col-11"> 
								<input id="billRateName" name="billRateName" class="col col-11" style="height:31px;"/>
							</label>
					</section> 
					
					<section>
	                    <label class="label col col-2" style="width:82px">费率明细</label>
							<input id="rateSdfMoney" name="rateSdfMoney" class="col col-2" value="" style="height:31px;"/>
						<label class="label col">元/</label>
							<input id="rateSdfTime" name="rateSdfTime" class="col col-2" value="" style="height:31px"/>
						<label class="label col">秒</label>
					</section>
					<br>
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
            /* 显示弹框  */
            $('#dialog_addrate').modal({
            	show:true,
            	backdrop : "static"        //点击空白区域弹窗不会消失
            });
            
            $('#billRateType').select2({
			    width:"100%"
			});
            
            jQuery.validator.addMethod("decimal", function(value, element) {
            	var decimal = /^-?\d+(\.\d{1,3})?$/;
            	return this.optional(element) || (decimal.test(value));
            });
            /** 校验字段  **/
            var validator = $('#rateinfo').validate({
                rules : {
                	billRateName : {
                		required : true,
                		remote : {
            				url:"${springMacroRequestContext.contextPath}/billing/rate/checkname",
            				type:"post",
            				data : {
                				uuid : function(){
                					return "";
                				}
                			}
            			}
                	},
                	rateSdfMoney : {
                		number:true,
                		decimal:true
                	},
                	rateSdfTime : {
                		digits:true
                	}
                },
                messages : {
                	billRateName:{
                		required : "必须输入名称！",
                    	remote : "名称已经存在！"
                	},
                	rateSdfMoney:{
                		decimal : "小数点后不能大于3位！"
                	},
                	rateSdfTime:{
                		digits : "秒数只能为整数！"
                	}
                },
                errorPlacement : function(error, element) {
                    error.insertAfter(element.parent());
                }
            });
            
            /* 提交按钮单击事件  */
			$('#msubmita').click(function(){
                if($('#rateinfo').valid()){
                    $('#msubmita').attr("disabled",true);
                    $('#rateinfo').submit();
                }
            });
            
            $('#rateinfo').ajaxForm({
                dataType:"json",
                success: function(data) {
                 	$('#dialog_addrate').modal('hide');
                     if(data.success){
                         $.smallBox({
                             title : "操作成功",
                             content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
                             color : "#659265",
                             iconSmall : "fa fa-check fa-2x fadeInRight animated",
                             timeout : 2000
                         });
                         $('#msubmita').attr("disabled",false);
                     }
                     $('#dialog_addrate').modal("hide"); 
                     $('#rateform').DataTable().ajax.reload(null,false);
                },
                submitHandler : function(form) {
                    $(form).ajaxSubmit({
                        success : function() {
                            $("#rateinfo").addClass('submited');
                        }
                    });
                },
                error: function(XMLHttpRequest,textStatus , errorThrown){
                    if(textStatus=='error'){
                        $('#dialog_addrate').modal('hide');
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
            
            /* 关闭窗口的回调函数  */
            $('#dialog_addrate').on('hide.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
    			$("#dialog_addrate").remove();
    		});
        });

    </script>





