<script src="${springMacroRequestContext.contextPath}/assets/js/adddate.js"></script>
<script src="${springMacroRequestContext.contextPath}/assets/js/calendar/WdatePicker.js"></script>
<div class="modal fade" id="agentnoticeSave" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 1200px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    <strong>关闭</strong>
                </button>
                <h4 class="modal-title" id="myModalLabel">坐席公告</h4>
            </div>
            <div class="modal-body">
                <form action="${springMacroRequestContext.contextPath}/agentnotice/save" class="smart-form" id="coreForm">
                	<fieldset style="padding-top: 2px;">
	                    <input type="hidden" id="uid" name="uid" value="${(item.uid)!''}">
	                    <input type="hidden" id="publishStatus" name="publishStatus" value="${(item.publishStatus)!''}">
						<section>
							<#if number??>
								<span style="font-size: large;">公告${number}</span>
							</#if>
						</section>
	                    <section>
	                        <div class="row">
	                            <label class="label col col-1"> 标题： </label>
	                            <label class="input col col-6">
	                            	<input id="noticeTitle" name="noticeTitle" value="${(item.noticeTitle)!''}" />
	                            </label>
	                        </div>
	                    </section>
	                     <section>
	                       <div class="row">
	                            <label class="label col col-1"> 内容： </label>
	                            <label class="textarea col col-8"> 
									<textarea class="custom-scroll" rows="20" id="noticeContent" name="noticeContent">${(item.noticeContent)!''}</textarea>
								</label>
	                        </div>
	                    </section>
                    </fieldset>
	            </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" id="agquxiao">取消</button>
                <button type="button" id="agentnoticeSaveButton" class="btn btn-primary">保存 </button>
            </div>
        </div>
    </div>
</div>

<script type="application/javascript">

$(document).ready(function() {
    
    $('#agentnoticeSave').modal("show");
    
	<#if operation??>
		$('#noticeTitle').attr("disabled", true);
		$('#noticeTitle').css("border", 0);
		$('#noticeContent').attr("disabled", true);
// 		$('#noticeContent').css("border", 0);
		$('#agentnoticeSaveButton').hide();
		$('#agquxiao').text("关闭");
	</#if>
	
	/* 关闭窗口的回调函数,hide是关闭调用后在关闭    */
    $('#agentnoticeSave').on('hide.bs.modal', function(e){
		$("#agentnoticeSave").remove();
	});
    
    $('#agentnoticeSaveButton').click(function () {
        if ($('#coreForm').valid()) {
            $('#agentnoticeSaveButton').attr("disabled", true);
            $('#coreForm').submit();
        }
    });
    
    /** 校验字段  **/
    var validator = $('#coreForm').validate({
        rules: {
			noticeTitle : {
				required : true,
				remote : {
					type : 'post',
					url : getContext()+ 'agentnotice/checkTitle',
					data : {
						uid : function() {
							return $("#uid").val();
						},
						title : function() {
							return $("#noticeTitle").val();
						}
					}
				},
				maxlength : 64
			}
        },messages : {
        	noticeTitle : {
        		required : "请输入标题！",
        		remote : "此公告标题已存在!"
			}
		},
        errorPlacement: function (error, element) {
            error.insertAfter(element.parent());
        }
    });

    $('#coreForm').ajaxForm({
        dataType: "json",
        type: "post",
        success: function (data) {
            $("#agentnoticeSave").modal("hide");
            if (data.success) {
                $.smallBox({
                    title: "操作成功",
                    content: "<i class='fa fa-clock-o'></i> <i>知识已成功保存...</i>",
                    color: "#659265",
                    iconSmall: "fa fa-check fa-2x fadeInRight animated",
                    timeout: 2000
                });
                $('#dt_basic_agentnotice').DataTable().ajax.reload(null,false);
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
                $("#agentnoticeSave").modal("hide");
                $.smallBox({
                    title: "操作失败",
                    content: "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作出现异常...</i>",
                    color: "#C46A69",
                    iconSmall: "fa fa-times fa-2x fadeInRight animated"
                });
                $('#dt_basic_agentnotice').DataTable().ajax.reload(null,false);
            }
        }
    });
    
});

</script>
