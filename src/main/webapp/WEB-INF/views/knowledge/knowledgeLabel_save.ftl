<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<div class="modal fade" id="saveModel" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    <strong>关闭</strong>
                </button>
                <h4 class="modal-title" id="myModalLabel">知识管理</h4>
            </div>
            <div class="modal-body">
                <form action="${springMacroRequestContext.contextPath}/knowledge/label/save" class="smart-form" id="coreForm">
                	<fieldset style="padding-top: 2px;">
                    <input type="hidden" id="uid" name="uid" value="${(item.uid)!''}">
                    <input type="hidden" id="directoryUUid" name="directoryUUid" />
                    <section>
                        <div class="row">
                            <label class="label col col-2"> 名称： </label>
                            <label class="input col col-5">
                            	<input id="labelName" name="labelName" value="${(item.labelName)!''}"  class="required"/>
                            </label>
                        </div>
                    </section>
                    <section>
                        <div class="row">
                            <label class="label col col-2"> 描述： </label>
                            <label class="textarea col col-8"> 
									<textarea class="custom-scroll required" rows="8" id="labelRemark" name="labelRemark">${(item.labelRemark)!''}</textarea>
							</label>
                        </div>
                    </section>
                    </fieldset>
	            </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" id="saveButton" class="btn btn-primary">保存 </button>
            </div>
        </div>
    </div>
</div>

<script type="application/javascript">

$(document).ready(function() {

    /** 校验字段  **/
    var validator = $('#coreForm').validate({
        rules: {
        },messages : {
        	labelName : {
        		required : "请输入标签名!"
			}
		},
        errorPlacement: function (error, element) {
            error.insertAfter(element.parent());
        }
    });

    $('#saveModel').modal("show");
    
    $('#saveButton').click(function () {
        if ($('#coreForm').valid()) {
            
        	$('#saveButton').attr("disabled", true);
            
            $('#coreForm').submit();
        }
    });

    $('#coreForm').ajaxForm({
        dataType: "json",
        type: "post",
        success: function (data) {
            $("#saveModel").modal("hide");
            if (data.success) {
                $.smallBox({
                    title: "操作成功",
                    content: "<i class='fa fa-clock-o'></i> <i>知识已成功保存...</i>",
                    color: "#659265",
                    iconSmall: "fa fa-check fa-2x fadeInRight animated",
                    timeout: 2000
                });
                $('#dt_basic_knowledgeLabel').DataTable().ajax.reload(null,false);;
            };
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
                $("#saveModel").modal("hide");
                $.smallBox({
                    title: "操作失败",
                    content: "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作出现异常...</i>",
                    color: "#C46A69",
                    iconSmall: "fa fa-times fa-2x fadeInRight animated"
                });
                $('#dt_basic_knowledgeLabel').DataTable().ajax.reload(null,false);;
            }
        }
    });
});
	
</script>
