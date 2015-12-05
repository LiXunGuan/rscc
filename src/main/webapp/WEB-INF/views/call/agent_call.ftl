<div class="modal fade" id="agent_index_call" >
	<div class="modal-dialog" >
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
				<h4 class="modal-title">呼叫</h4>
			</div>
			<div class="modal-body">
				<form id="agentCallForm" action="${springMacroRequestContext.contextPath}/callcenter/agentcall" class="smart-form" method="post">
					<fieldset style="padding-top: 2px;">

						<section>
							<div class="row">
								<label class="label col col-2">呼叫号</label> <label
									class="input col col-8"> 
									<input id="callid" type="hidden" onfocus="onfocus" name="callid" value="${callid!''}" /> 
									<input id="callNum" onfocus="onfocus" name="callNum" value="" />
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
					呼 叫</button>
			</div>
		</div>
	</div>
</div>


<script type="text/javascript">
	$(document).ready(function() {
		pageSetUp();

		/* 展示modal */
		$('#agent_index_call').modal({
        	show : true,
        	backdrop : "static"
        });

		/* 提交按钮单击事件  */
		$('#agentCall').click(function() {
			//将该变量设置为0，该函数执行后的弹屏的tab页将有关闭按钮
			window.parent.closetag = "1";
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
// 					currentCallStat = 1;
					$("#loadStatus tr:eq(3) td:last").html($("#callNum").val());
					$('#agent_index_call').modal("hide");
					
					//根据时间弹窗
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
					$('#agent_index_call').modal("hide");
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
</script>
