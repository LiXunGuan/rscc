<div class="modal fade" id="sipuserSaveModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    <strong>关闭</strong>
                </button>
                <h4 class="modal-title" id="myModalLabel">分机管理</h4>
            </div>
            <div class="modal-body">

                <form action="${springMacroRequestContext.contextPath}/config/sipuser/save" class="smart-form"
                      id="coreForm">

                   <input type="hidden" id="id" name="id" value="${(item.id)!''}">

                    <section>
                        <div class="row">
                            <label class="col col-2"> 账号 </label>
                            <label class="input col col-8">
                            <#if (item.id)??>
                            	<input type="hidden" id="sipId" name="sipId" value="${(item.sipId)!''}"  />
                            	${(item.sipId)!''}
                            <#else>
                            	<input id="sipId" name="sipId" value="${(item.sipId)!''}"  class="required"/>
                            </#if>
                            </label>
                        </div>
                    </section>
                    <section>
                        <div class="row">
                            <label class="label col col-2"> 密码   </label>
                            <label class="input col col-8">
                            	<input id="sipPassword" name="sipPassword" value="${(item.sipPassword)!''}" class="required"/>
                            </label>
                        </div>
                    </section>
                    <section>
                        <div class="row">
                            <label class="label col col-2">区号</label> <label
                                class="input col col-8">
                            <input id="area_code" onfocus="onfocus" name="area_code" value="${(item.area_code)!''}" />
                        </label>
                        </div>
                    </section>
                    <section>
						<div class="row">
							 <label class="label col col-2">费率设置 </label>
							 <label class="select col col-8"> 
							 	<select id="rate" name="rate" > 
							 		<option value="" >请选择</option>
							 		<#list allrate as rate>
							 			<#if (thisrate.uid)?exists>
							 				<#if thisrate.uid==rate.uid>
							 					<option value="${thisrate.uuid}" selected="selected">${thisrate.billRateName}---${thisrate.rateSdfMoney}元/${thisrate.rateSdfTime}秒</option>
							 				<#else>
							 					<#if rate.billRateType == "Sip">
							 						<option value="${rate.uuid}">${rate.billRateName}---${rate.rateSdfMoney}元/${rate.rateSdfTime}秒</option>
							 					</#if>
							 				</#if>
							 			<#elseif rate.billRateType=="Sip">
							 				<option value="${rate.uuid}">${rate.billRateName}---${rate.rateSdfMoney}元/${rate.rateSdfTime}秒</option>
							 			</#if>
							 		</#list>
							</select>
							</label>
						</div>
					</section>
                    <section>
                        <div class="row">
                            <label class="label col col-2">显示号码</label> <label
                                class="input col col-8">
                            <input id="caller_id_number" onfocus="onfocus" name="caller_id_number" value="${(item.caller_id_number)!''}" />
                        </label>
                        </div>
                    </section>
                    <section>
                        <div class="row">
                            <label class="label col col-2">显示名称</label> <label
                                class="input col col-8">
                            <input id="caller_id_name" onfocus="onfocus" name="caller_id_name" value="${(item.caller_id_name)!''}" />
                        </label>
                        </div>
                    </section>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    取消
                </button>
                <button type="button" id="saveButton" class="btn btn-primary">
                    保存
                </button>
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
            area_code : {
                digits : true
            },
            caller_id_number : {
                digits : true
            },
        	sipId : {
        		digits : true,
				required : true,
				minlength : 3,
				remote : {
					type : 'post',
					url : getContext()
							+ 'config/sipuser/isRepeat',
					data : {
						id : function() {
							return $("#coreForm #id").val();
						}
					}
				}
			},
			sipPassword : {
				required : true
// 				remote : {
// 					type : 'post',
// 					url : getContext() + '/config/sipuser/checkIdPwd',
// 					data : {
// 						sipid : function(){
// 							return $("#sipId").val();
// 						},
// 						sipwd : function(){
// 							return $("#sipPassword").val();
// 						}
// 					}
// 				}
			}
        },
        messages : {
        	sipId : {
        		minlength : "至少3位！",
				remote : $.format("该分机名已经存在！")
			},
			sipPassword : {
				minlength : "至少6位"
// 				remote : $.format("密码不能与账号重复！")
			}
		},
        errorPlacement: function (error, element) {
            error.insertAfter(element.parent());
        }
    });

    $('#sipuserSaveModel').modal("show");
    
    $('#sipuserSaveModel').on('hide.bs.modal', function(e){
		$("#sipuserSaveModel").remove();
	});

    $('#saveButton').click(function () {
        if ($('#coreForm').valid()) {
        	var sipid = $("#sipId").val();
        	var pwd = $("#sipPassword").val();
        	if(pwd.length < 6){
        		if(confirm("密码长度确定要小于6位吗?")){
        			if(sipid == pwd){
                		if(confirm("密码与账号一样,确定吗?")){
        		            $('#saveButton').attr("disabled", true);
        		            $('#coreForm').submit();
                		};
                	}else{
        	        	$('#saveButton').attr("disabled", true);
        	            $('#coreForm').submit();
                	}
        		}
        	}else{
	        	if(sipid == pwd){
	        		if(confirm("密码与账号一样,确定吗?")){
			            $('#saveButton').attr("disabled", true);
			            $('#coreForm').submit();
	        		};
	        	}else{
		        	$('#saveButton').attr("disabled", true);
		            $('#coreForm').submit();
	        	}
        	}
        }
    });


    $('#coreForm').ajaxForm({
        dataType: "json",
        type: "post",
        success: function (data) {
            $("#sipuserSaveModel").modal("hide");
            if (data.success) {
                $.smallBox({
                    title: "操作成功",
                    content: "<i class='fa fa-clock-o'></i> <i>分机已成功保存...</i>",
                    color: "#659265",
                    iconSmall: "fa fa-check fa-2x fadeInRight animated",
                    timeout: 2000
                });
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
                $("#sipuserSaveModel").modal("hide");
                $.smallBox({
                    title: "操作失败",
                    content: "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作出现异常...</i>",
                    color: "#C46A69",
                    iconSmall: "fa fa-times fa-2x fadeInRight animated"
                });
            }
        }
    });

</script>
