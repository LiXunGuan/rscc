
<div class="modal fade" id="dialog_owner">
	<div class="modal-dialog" style="width: 40%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">
					<strong>关闭</strong>
				</button>
				<h4 class="modal-title">选择拥有者</h4>
			</div>
			<div class="modal-body">
				<p class="row" align="left">
					<span style="color:red;font:initial;">注：</span>该处选择框中的数据格式为：用户名-姓名
				</p>
				<form id="ownerForm"
					action="${springMacroRequestContext.contextPath}/newuserdata/changeUser"
					class="smart-form" method="post">
					<fieldset>
						<section>
							<#if dataUuids??>
								<#list dataUuids as s >
									<input type="hidden" name='dataUuids' value="${s}">
								</#list>
							</#if>
							<input type="hidden" name='dataUuid' id='dataUuid' value="${dataUuid!''}">
							<input type="hidden" name='batchUuid' id='batchUuid' value="${batchUuid!''}">
							<input type="hidden" name='deptUuid' id='deptUuid' value="${deptUuid!''}">
							<input type="hidden" name='ownerUuid' id='ownerUuid' value="${ownerUuid!''}">
							
							<div class="row" align="center">
								<label class="label col col-2">拥有者</label>
								<label class="col col-8">
									<select class="select2" name="newOwnUser" id="owner_id" >
										<#if users??>
											<#list users as u>
											<#if u.uid==ownerUuid>
											<option value="${(u.uuid)!''}" selected="selected">${(u.loginName)!''}-${(u.userDescribe)!''}</option>
											<#else>
											<option value="${(u.uuid)!''}">${(u.loginName)!''}-${(u.userDescribe)!''}</option>
											</#if>
											</#list> 
										<#else>
											<option value="">暂无用户</option> 
										</#if>
									</select>
								</label>
							</div>
						</section>
					</fieldset>
				</form>

			</div>
			<div class="modal-footer">
				<button id="msubmit" type="button" class="btn btn-primary">
					保 存</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">
					关 闭</button>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">

	$(document).ready(function() {
		
		/* 显示弹框  */
		$('#dialog_owner').modal("show");

		/** 校验字段  **/
		var validator = $('#ownerForm').validate({
			rules : {
				owner_id : {
					required : true,
				}
			},
			messages : {
				owner_id : {
					required : "${c.validate_required}",
				}
			},
			errorPlacement : function(error, element) {
				error.insertAfter(element.parent());
			}
		});

	    $('#owner_id').select2({
	        allowClear : true,
	        width: '99%'
	     });
	    
	    
		$("#msubmit").click(function(){
			$("#ownerForm").submit();	
		});
	    
	    $('#ownerForm').ajaxForm({
			dataType : "json",
			success : function(data) {
					if (data.success) {
						$.smallBox({
							title : "操作成功",
							content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
							color : "#659265",
							iconSmall : "fa fa-check fa-2x fadeInRight animated",
							timeout : 2000
						});
					}
					$('#dialog_owner').modal("hide");
					$('table.dataTable').DataTable().ajax.reload(null,false);;
				},
				submitHandler : function(form) {
					$(form).ajaxSubmit({
						success : function() {
							$("#ownerForm").addClass('submited');
						}
					});
				},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				if (textStatus == 'error') {
					$('#dialog_owner').dialog('hide');
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
	



	$('#dialog_owner').on('hide.bs.modal', function(e) { 
		$("#dialog_owner").remove();
		/* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */});

	$('#dialog_owner').on('hidden.bs.modal', function(e) { 
		$("#dialog_owner").remove();
		/* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */});
	
</script>





