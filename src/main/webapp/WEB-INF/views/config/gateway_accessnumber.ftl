<link rel="stylesheet" type="text/css" href="${springMacroRequestContext.contextPath}/assets/js/plugin/duallistbox/bootstrap-duallistbox.css">
<script src="${springMacroRequestContext.contextPath}/assets/js/plugin/duallistbox/jquery.bootstrap-duallistbox.min.js"></script>

<div class="modal fade" id="gatewayAccessnumberSaveModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    <strong>关闭</strong>
                </button>
                <h4 class="modal-title" id="myModalLabel">接入号 配置</h4>
            </div>
            <div class="modal-body col-md-12" >
            	<form id="agentQueueForm" action="${springMacroRequestContext.contextPath}/config/gateway/savega"  method="post">
            		<input type="hidden" id="id" name="id" value="${(id)!''}"/>
					<select id="numberId" name="numberId" multiple="multiple" size="10" name="duallistbox_demo2" class="demo2" style="height: 400px;">
						<#if numbers??>
							<#list numbers as n>
								<option value="${(n.id)!''}">${(n.accessnumber)!''}</option>
							</#list>
						</#if>
						<#if numbersCheckls??>
							<#list numbersCheckls as nc>
								<option value="${(nc.id)!''}" selected="selected">${(nc.accessnumber)!''}</option>
							</#list>
						</#if>
					</select>
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

	var demo2 = $('.demo2').bootstrapDualListbox({
		nonSelectedListLabel: '可选 接入号',
		selectedListLabel: '已选  接入号',
		preserveSelectionOnMove: 'moved',
		moveOnSelect: false,
		nonSelectedFilter: '',
		infoText:'<span style="text-align: center">总计:{0}</span>',
	    infoTextEmpty:'无'
	
	});
  
    $('#gatewayAccessnumberSaveModel').modal("show");
    
    $('#gatewayAccessnumberSaveModel').on('hide.bs.modal', function(e){
		$("#gatewayAccessnumberSaveModel").remove();
	});

    $('#saveButton').click(function () {
        if ($('#agentQueueForm').valid()) {
            $('#saveButton').attr("disabled", true);
            $('#agentQueueForm').submit();
        }
    });

    $('#agentQueueForm').ajaxForm({
        dataType: "json",
        type: "post",
        success: function (data) {
            $("#gatewayAccessnumberSaveModel").modal("hide");
            if (data.success) {
//             	$("#toDeployButton").removeAttr("disabled");
                $.smallBox({
                    title: "操作成功",
                    content: "<i class='fa fa-clock-o'></i> <i>GateWay 配置已成功保存...</i>",
                    color: "#659265",
                    iconSmall: "fa fa-check fa-2x fadeInRight animated",
                    timeout: 2000
                });
                oTable.DataTable().ajax.reload(null,false);;
            }
            ;
        },
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function () {
                    $("#agentQueueForm").addClass('submited');
                }
            });
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            if (textStatus == 'error') {
                $("#gatewayAccessnumberSaveModel").modal("hide");
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
