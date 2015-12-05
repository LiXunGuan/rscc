<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<script src= "${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-tags/bootstrap-tagsinput.min.js"></script>

<style>
	.smart-form .label {display: inline-block;}
	.tag.label.label-info {color: white;}
</style>

<div class="modal fade" id="getKnowledge" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 1200px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    <strong>关闭</strong>
                </button>
                <h4 class="modal-title" id="myModalLabel">知识信息</h4>
            </div>
            <div class="modal-body">
                <form action="${springMacroRequestContext.contextPath}/knowledge/knowledge/save" class="smart-form" id="coreForm">
                	<fieldset style="padding-top: 2px;">
                    <input type="hidden" id="uid" name="uid" value="${(item.uid)!''}">
                    <input type="hidden" id="directoryUUid" name="directoryUUid" />
                     <section>
                        <div class="row">
                            <label class="label col col-1">标签：</label>
							<div class="form-group col col-lg-6">
								<#if tagsStr?? && (tagsStr?length) &gt; 0 >
									<input class="form-control tagsinput"  id="knowledgeTags" name="knowledgeTags" disabled="disabled"  value="${tagsStr?substring(1,tagsStr?length-1)}" >
								<#else>
									<input class="form-control tagsinput"  id="knowledgeTags" name="knowledgeTags" disabled="disabled" value="" >
								</#if>
							</div>
                        </div>
                    </section>
                    <section>
                        <div class="row">
                            <label class="label col col-1"> 所属目录： </label>
                            <label class="input col col-6">
                            	<input id="knowledgeTitle" name="knowledgeTitle" value="${(directory.directoryName)!''}"  class="required" disabled="disabled" />
                            </label>
                        </div>
                    </section>
                    <section>
                        <div class="row">
                            <label class="label col col-1"> 标题： </label>
                            <label class="input col col-6">
                            	<input id="knowledgeTitle" name="knowledgeTitle" value="${(item.knowledgeTitle)!''}"  class="required" disabled="disabled" />
                            </label>
                        </div>
                    </section>
                    <section>
                        <div class="row">
                            <label class="label col col-1"> 内容： </label>
                            <label class="textarea col col-8"> 
									<textarea class="custom-scroll required" rows="20" id="knowledgeContent" name="knowledgeContent" disabled="disabled">${(item.knowledgeContent)!''}</textarea>
							</label>
                        </div>
                    </section>
                    </fieldset>
                                                                     此条知识共被查看<label style="font-size: large;">${clickNumbers!''}</label>次！
	            </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
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
        	knowledgeTitle : {
        		required : "请输入标题!"
			},
			knowledgeContent : {
				required : "请输入内容!"
			},
			knowledgeDirectory : {
				required : "请选择所属目录!"
			}
		},
        errorPlacement: function (error, element) {
            error.insertAfter(element.parent());
        }
    });

    $('#getKnowledge').modal("show");

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
            $("#getKnowledge").modal("hide");
            if (data.success) {
                $.smallBox({
                    title: "操作成功",
                    content: "<i class='fa fa-clock-o'></i> <i>知识已成功保存...</i>",
                    color: "#659265",
                    iconSmall: "fa fa-check fa-2x fadeInRight animated",
                    timeout: 2000
                });
                $('#dt_basic_knowledge').DataTable().ajax.reload(null,false);;
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
                $("#getKnowledge").modal("hide");
                $.smallBox({
                    title: "操作失败",
                    content: "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作出现异常...</i>",
                    color: "#C46A69",
                    iconSmall: "fa fa-times fa-2x fadeInRight animated"
                });
                $('#dt_basic_knowledge').DataTable().ajax.reload(null,false);;
            }
        }
    });
});
	
</script>
