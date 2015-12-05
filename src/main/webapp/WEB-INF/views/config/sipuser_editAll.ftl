<div class="modal fade" id="sipuserEditAllSaveModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    <strong>关闭</strong>
                </button>
                <h4 class="modal-title" id="myModalLabel">批量修改</h4>
            </div>
            <div class="modal-body">
                <form action="${springMacroRequestContext.contextPath}/config/sipuser/alledit" class="smart-form"
                      id="coreForm">
                   <input type="hidden" id="pwdType" name="pwdType" value=""/>
                    <section>
                        <div class="row">
                            <label class="label col col-2"> 起始分机号码： </label>
                            <label class="input col col-8"><input id="sipId" name="sipId" value=""
                                                                  class="required"/></label>
                        </div>
                    </section>
                    <section>
                        <div class="row">
                            <label class="label col col-2"> 结束分机号码： </label>
                            <label class="input col col-8">
                            		<input id="endsipId" name="endsipId" value="" class="required"/>
                            </label>
                            <input type="hidden" id="num" name="num" value="" class="required"/>
                        </div>
                    </section>
                    <section>
                    	<div class="row">

                            <label class="label col col-1">
                                <input type="checkbox" name="check" val="chkPwd"/>
                                <input type="hidden" name="check_chkPwd"/>

                            </label>

                            <div class="row">
                            <label class="label col col-2">密码生成方式： </label>
                    	        </div>
                    	<div class="row">
                    		<label class="label col col-2"></label>
                    		<input  disabled="disabled" type="radio" name="chkPwd" onclick="pwdSty();" value="1" checked="checked"/>与分机号码一致
                    	</div>
                    	<div class="row">
                    		<label class="label col col-2"></label>
                            <input disabled="disabled" type="radio" name="chkPwd" onclick="pwdSty();" value="2" />随机生成6位数字
                        </div>
                    	<div class="row">
                    		<label class="label col col-2"></label>
                    		 <input disabled="disabled" type="radio" name="chkPwd" onclick="pwdSty();" value="3" />使用统一密码
                    		 <input type="hidden" id="sipPassword" name="sipPassword" value="" />
                    	</div>
                    <!-- 
                        <div class="row">
                            <label class="label col col-2">密码生成方式： </label>&nbsp;&nbsp;
                            	<input type="radio" name="chkPwd" onclick="pwdType();" value="1" />与分机号码一致
                            	<input type="radio" name="chkPwd" onclick="pwdType();" value="2" checked="checked"/>随机生成6位数字
                            	<input type="radio" name="chkPwd" onclick="pwdType();" value="3"/>使用统一密码
                            <label class="input col col-8">
                            	<input type="hidden" id="sipPassword" name="sipPassword" class="required">
                            </label>
                        </div>
                         -->
                    </section>
                    <section>
                        <div class="row">

                            <label class="label col col-1">
                                <input type="checkbox" name="check" val="area_code"/>
                                <input type="hidden" name="check_area_code"/>

                            </label>
                            <label class="label col col-2">区号</label> <label
                                class="input col col-8">
                            <input disabled id="area_code" style="background-color: #DDDDDD" disabled="disabled" onfocus="onfocus" name="area_code" value="" />
                        </label>
                        </div>
                    </section>
                     <section>
						<div class="row">
							 <label class="label col col-1">
							 	<input type="checkbox" name="check" val="rate"/>
                                <input type="hidden" name="check_rate"/>
							 </label>
							 <label class="label col col-2">费率</label>
							 <label class="input col col-8">
							 	<select id="rate" name="rate" disabled="disabled">
							 		<option value="" >请选择</option>
							 		<#list allrate as rate>
							 			<#if rate.billRateType=="Sip">
							 			<option value="${rate.uuid}">${rate.billRateName}</option>
							 			</#if>
							 		</#list>
								</select>
							</label>
						</div>
					</section>
                    <section>
                        <div class="row">
                            <label class="label col col-1">
                                <input type="checkbox" name="check" val="caller_id_number"/>
                                <input type="hidden" name="check_caller_id_number"/>

                            </label>
                            <label class="label col col-2">显示号码</label> <label
                                class="input col col-8">
                            <input id="caller_id_number" style="background-color: #DDDDDD" disabled="disabled" onfocus="onfocus" name="caller_id_number" value="" />
                        </label>
                        </div>
                    </section>

                    <section>
                        <div class="row">

                            <label class="label col col-1">
                                <input type="checkbox" name="check" val="caller_id_name"/>
                                <input type="hidden" name="check_caller_id_name"/>
                            </label>
                            <label class="label col col-2">显示名称</label>
                            <label class="input col col-8"  >
                            <input id="caller_id_name" style="background-color: #DDDDDD" disabled="disabled" onfocus="onfocus" name="caller_id_name" value="" />
                        </label>
                        </div>
                    </section>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	                <button type="button" id="saveButton" class="btn btn-primary">保存 </button>
	            </div>
            </form>
        </div>
    </div>
</div>

<script type="application/javascript">
	$('#rate').select2({
	    allowClear : true,
	    width:'100%'
	});

    /** 校验字段  **/
    var validator = $('#coreForm').validate({
        rules: {
        	sipId : {
				required : true,
				minlength : 4,
				digits : true
			},
			endsipId : {
				required : true,
				minlength : 4,
				digits : true
			},
			num : {
				required : true,
				digits : true
			},
			area_code : {
                digits : true
            },
            caller_id_number : {
                digits : true
            }
        },
        messages : {
        	sipId : {
        		minlength : "最少4位！"
			},
			endsipId : {
        		minlength : "最少4位！"
			}
		},
        errorPlacement: function (error, element) {
            error.insertAfter(element.parent());
        }
    });


    $('[name="check"]').click(function(){

        if($(this).is(':checked')){
            var name =$(this).attr("val");
            $('[name="'+name+'"]').removeAttr("disabled");
            $('[name="'+name+'"]').css("background-color","white");
            $('[name="check_'+name+'"]').val("11111");

        }else{
            var name =$(this).attr("val");
            $('[name="'+name+'"]').attr("disabled",true);
            $('[name="'+name+'"]').css("background-color","#DDDDDD");
            $('[name="check_'+name+'"]').val("");
        }

    });


    $('#sipuserEditAllSaveModel').modal("show");
    
    $('#sipuserEditAllSaveModel').on('hide.bs.modal', function(e){
		$("#sipuserEditAllSaveModel").remove();
	});

    $('#saveButton').click(function () {
        if ($('#coreForm').valid()) {
            $('#saveButton').attr("disabled", true);
            var num=$('input[name="chkPwd"]:checked').val();
            if (num==1) {
            	$('#pwdType').attr("value", "pwdType1");
			}else if(num==2){
				$('#pwdType').attr("value", "pwdType2");
			}else{
				$('#pwdType').attr("value", "pwdType3");
			}
            var startsipid = $('input[name="sipId"]').val();
            var endsipid = $('input[name="endsipId"]').val();
            var number = endsipid - startsipid;
            if(number > 0){
            	$('#num').attr("value", number + 1);
            	$('#coreForm').submit();
            }else{
            	alert("起始分机号码必须小于结束分机号码!");
            	$('#saveButton').attr("disabled", false);
            }
        }
    });


    $('#coreForm').ajaxForm({
        dataType: "json",
        type: "post",
        success: function (data) {
            $("#sipuserEditAllSaveModel").modal("hide");
            if (data.success) {
                $.smallBox({
                    title: "操作成功",
                    content: "<i class='fa fa-clock-o'></i> <i>分机已成功保存...</i>",
                    color: "#659265",
                    iconSmall: "fa fa-check fa-2x fadeInRight animated",
                    timeout: 2000
                });
                $("#toDeployButton").removeAttr("disabled");
                $("#toDeployButton").removeAttr("disabled");
                $("#toDeployButton").removeClass("btn-default");
    			$("#toDeployButton").addClass("btn-sm");
    			$("#toDeployButton").addClass("btn-success");
                oTable.DataTable().ajax.reload(null,false);;
            }
            ;
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
                $("#sipuserEditAllSaveModel").modal("hide");
                $.smallBox({
                    title: "操作失败",
                    content: "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作出现异常...</i>",
                    color: "#C46A69",
                    iconSmall: "fa fa-times fa-2x fadeInRight animated"
                });
            }
        }
    });
    function pwdSty(){
    	var n=$("input[name='chkPwd']:checked").val();
    	if (n=="3") {
    		$("#sipPassword").attr("type","text");
		}else{
			$("#sipPassword").attr("type","hidden");
		}
    }
 
</script>
