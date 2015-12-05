<div class="modal fade" id="agent_index_call">
	<div class="modal-dialog " >
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true"><strong>关闭</strong></button>


				<h4 class="modal-title">转接选择</h4>
			</div>
			<div class="modal-body">
				<form id="agentCallForm"
					action="${springMacroRequestContext.contextPath}/callcenter/transferto"
					class="smart-form" method="post">
					<fieldset style="padding-top: 2px;">

						<section>
							<div class="row">
								<label class="label col col-2">转接到</label> <label
									class="input col col-8"> 
									<input id="id" type="hidden" onfocus="onfocus" name="id" value="${id!''}" /> 
									 <select id="extensions" name="extensions" style="height: 35px;">
										<#if extenRoute??> 
											<#assign nameKey = {"SIPUSER":"分机","CALLCENTER":"技能组","IVR":"IVR","DIALPLAN":"拨号计划","CUSTOMER":"自定义","CONFERENCE":"会议室","AGENT":"坐席人员"} />
											<#assign json=extenRoute />
											<#list extenRoute?keys as ext> 
												<option selected="selected" value="ext">${extenRoute[ext]}</option>
											</#list>
										<#else>
											<option value="">没有可用</option>
										</#if>
									</select>
								</label>
							</div>
						</section>
						
					</fieldset>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					关 闭</button>
				<button id="agentCall" type="button" class="btn btn-primary">
					转 接</button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		pageSetUp();

		/* 展示modal */
		$('#agent_index_call').modal("show");

		/* 提交按钮单击事件  */
		$('#agentCall').click(function() {
			if ($('#agentCallForm').valid()) {
				$("#agentCall").attr("disabled", "true");
				$('#agentCallForm').submit();
			}
		});
		
		/* 校验数据 */
		var validator = $('#agentCallForm').validate({
			rules : {
				callNum : {
					required : true,
					digits : true
				}
			},
			errorPlacement : function(error, element) {
				error.insertAfter(element.parent());
			}
		});

		/* 关闭窗口的回调函数  */
        $('#agent_index_call').on('hide.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
			$("#agent_index_call").remove();
		});
		
		$('#agentCallForm').ajaxForm({
			dataType : "json",
			success : function(data) {
				if (data.success) {
					$('#agent_index_call').modal("hide");
					$.smallBox({
						title : "操作成功",
						content : "<i class='fa fa-clock-o'></i> <i>转接成功</i>",
						color : "#659265",
						iconSmall : "fa fa-check fa-2x fadeInRight animated",
						timeout : 2000
					});
				}
			},
			submitHandler : function(form) {
				$(form).ajaxSubmit({
					success : function() {

						$("#agentCallForm").addClass('submited');
					}
				});
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				if (textStatus == 'error') {
					$('#agent_index_call').dialog('hide');
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

	});
	$('#extensions').select2({
	    allowClear : true,
	    width:'99%'
	});

</script>
