
<div class="modal fade" id="dialog_content">
    <div class="modal-dialog" style="width: 600px">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                <h4 class="modal-title">部门信息</h4>
            </div>
            <div class="modal-body"  >

                <form id="messageinfo" action="${springMacroRequestContext.contextPath}/sys/dept/save" class="smart-form" method="post">
                    <fieldset>
                        <section>
                            <div class="row">
                                <label class="label col col-2">部门名称</label>
                                <div class="col col-6">
                                    <label class="input">
                                        <input name="name" value="${(entry.name)!''}">
                                    </label>
                                </div>
                            </div>
                        </section>
                        <section>
                            <div class="row">
                                <label class="label col col-2 ">电话号码</label>
                                <div class="col col-6">
                                    <label class="input">
                                        <input name="phone" value="${(entry.phone)!''}">
                                    </label>
                                </div>
                            </div>
                        </section>
						<!-- 如果不存在，表名是添加  要选择上级部门 -->
						<#if !(entry.parentId)??>
						<section>
                            <div class="row">
                                <label class="label col col-2 ">上级部门</label>
                                <div class="col col-6">
                                    <label class="select">
										<select name="parentId" id="parentId">
											<option value="">---请选择---</option>
											<#if depts??>
												 <#list depts?keys as key>
												 	<option value="${key}">${depts[key]}</option>
												 </#list>
											</#if>
										</select>
                                    </label>
                                </div>
                            </div>
                        </section>
						</#if>

                    </fieldset>
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

    <script type="text/javascript">

        $(document).ready(function(){

            /* 显示弹框  */
            $('#dialog_content').modal("show");

            /** 校验字段  **/
            var validator =$('#messageinfo').validate({
                rules : {
                	name : {
                		required : true
                	},
                	phone : {
                		required : true,
//                 		digits : true
                	},
                	parentId : {
                		required : true
                	}
                	
                },
                messages : {
                	parentId : '必须选择组织!'
                },
                errorPlacement : function(error, element) {
                    error.insertAfter(element.parent());
                }
            });

            /* 提交按钮单击事件  */
            $('#msubmit').click(function(){
                if($('#messageinfo').valid()){
                    $('#msubmit').attr("disabled",true);
                    $('#messageinfo').submit();
                }
            });

            $('#messageinfo').ajaxForm({
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
                        oTable.fnReloadAjax();
                    }
                    $('#dialog_content').modal("hide");
                    validator.resetForm();
                },
                submitHandler : function(form) {
                    $(form).ajaxSubmit({
                        success : function() {
                            $("#messageinfo").addClass('submited');
                        }
                    });
                },
                error: function(XMLHttpRequest,textStatus , errorThrown){
                    if(textStatus=='error'){
                        $('#dialog_content').dialog('hide');
                        $.smallBox({
                            title : "操作失败",
                            content : "<i class='fa fa-clock-o'></i> <i>操作失败</i>",
                            color : "#C46A69",
                            iconSmall : "fa fa-times fa-2x fadeInRight animated",
                            timeout : 2000
                        });

                        oTable.fnReloadAjax();
                    }
                }
            });
        });

    </script>


