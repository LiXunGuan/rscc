<link rel="stylesheet" type="text/css" href="${springMacroRequestContext.contextPath}/assets/js/plugin/duallistbox/bootstrap-duallistbox.css">
<script src="${springMacroRequestContext.contextPath}/assets/js/plugin/duallistbox/jquery.bootstrap-duallistbox.min.js"></script>

<div class="modal fade" id="accessnumberGatewaySaveModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    <strong>关闭</strong>
                </button>
                <h4 class="modal-title" id="myModalLabel">Gateway 配置</h4>
            </div>
            <div class="modal-body col-md-12" >
            	<form id="agentQueueForm" action="${springMacroRequestContext.contextPath}/config/accessnumber/saveag"  method="post">
            		<input type="hidden" id="id" name="id" value="${(id)!''}"/>
            	
						<select id="gatewayId" name="gatewayId" multiple="multiple" size="10" name="duallistbox_demo2" class="demo2" style="height: 400px;">
							<#if gatewayls??>
								<#list gatewayls as g>
									<option value="${(g.id)!''}">${(g.name)!''}</option>
								</#list>
							</#if>
							<#if gatewayCheckls??>
								<#list gatewayCheckls as gc>
									<option value="${(gc.id)!''}" selected="selected">${(gc.name)!''}</option>
								</#list>
							</#if>
						</select>
						</form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    取消
                </button>
                <button type="button" id="saveButton" class="btn btn-primary">
                    保存
                </button>
            </div>
        </div>
    </div>
</div>

<script type="application/javascript">

	var demo2 = $('.demo2').bootstrapDualListbox({
		nonSelectedListLabel: '可选 Gateway',
		selectedListLabel: '已选  Gateway',
		preserveSelectionOnMove: 'moved',
		moveOnSelect: false,
		nonSelectedFilter: '',
		infoText:'<span style="text-align: center">总计:{0}</span>',
	    infoTextEmpty:'无'
	
	});
  
    $('#accessnumberGatewaySaveModel').modal("show");
    
    $('#accessnumberGatewaySaveModel').on('hide.bs.modal', function(e){
		$("#accessnumberGatewaySaveModel").remove();
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
            $("#accessnumberGatewaySaveModel").modal("hide");
            if (data.success) {
            	$("#toDeployButton").removeAttr("disabled");
                $.smallBox({
                    title: "操作成功",
                    content: "<i class='fa fa-clock-o'></i> <i>GateWay 配置已成功保存...</i>",
                    color: "#659265",
                    iconSmall: "fa fa-check fa-2x fadeInRight animated",
                    timeout: 2000
                });
                oTable.DataTable().ajax.reload(null,false);;
            };
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
                $("#accessnumberGatewaySaveModel").modal("hide");
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
