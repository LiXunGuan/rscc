<div class="modal fade" id="saveModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">创建会议室</h4>
            </div>
            <div class="modal-body">
                <form action="${springMacroRequestContext.contextPath}/confrence/add" class="smart-form" id="coreForm">
                    <section>
                        <div class="row">
                            <label class="label col col-3">本次会议时间</label>
                            <label class="input col col-3">
                            	<input id="timeout" name="timeout" onfocus="onfocus" value="120" class="required digits"/>
                            </label>
                            <label class="label col col-2">
                                分钟
                            </label>
                        </div>
                    </section>
	            </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" id="saveButton" class="btn btn-primary">保存</button>
            </div>
        </div>
    </div>
</div>

<script type="application/javascript">

    $('#saveModel').modal("show");

    $('#saveButton').click(function () {
        if($('#coreForm').valid()) {

            var url = getContext() + "/confrence/add";
            var timeout = $('#timeout').val();
            $.post(url, {timeout: timeout}, function (data) {

                if (data.success) {
					if(data.confrenceId != "0"){
						changeConfrenceStat(0, data.confrenceId);
					}
				}
                $("#saveModel").modal("hide");

            }, "json");
        }

    });

    $('#saveModel').on('hide.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
		$("#saveModel").remove();
	});
    
//    $('#coreForm').ajaxForm({
//        dataType: "json",
//        type: "post",
//        success: function (data) {
//            $("#saveModel").modal("hide");
//            if (data.success) {
//
//                alert('');
//                $.smallBox({
//                    title: "操作成功",
//                    content: "<i class='fa fa-clock-o'></i> <i>分机已成功保存...</i>",
//                    color: "#659265",
//                    iconSmall: "fa fa-check fa-2x fadeInRight animated",
//                    timeout: 2000
//                });
//                  conferenceLoad('1');
//
//                 $('#conference_id').val(data.id);
//                 $('#conference_name').val(data.name);
//                 $('#conference_uid').text(data.conference_uid);
//                 $('#conference_extension').text(data.conference_extension);
//                 $('#establish_time').text(data.establish_time);
//                 oTable.fnReloadAjax();
//            }
//        },
//        submitHandler: function (form) {
//            $(form).ajaxSubmit({
//                success: function () {
//                    $("#coreForm").addClass('submited');
//                }
//            });
//        },
//        error: function (XMLHttpRequest, textStatus, errorThrown) {
//            if (textStatus == 'error') {
//                $("#saveModel").modal("hide");
//                $.smallBox({
//                    title: "操作失败",
//                    content: "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作出现异常...</i>",
//                    color: "#C46A69",
//                    iconSmall: "fa fa-times fa-2x fadeInRight animated"
//                });
//            }
//        }
//    });
</script>
