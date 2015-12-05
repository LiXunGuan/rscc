<script src="${springMacroRequestContext.contextPath}/assets/js/plugin/msie-fix/jquery.mb.browser.min.js"></script>
<div class="modal fade" id="voice_index_add">
	<div class="modal-dialog " >
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>


				<h4 class="modal-title">语音管理</h4>
			</div>
			<div class="modal-body">
				<form id="voiceForm" action="${springMacroRequestContext.contextPath}/system/uploadingvoice/save" class="smart-form" method="post">
					<fieldset style="padding-top: 2px;">
						<input id="id"  name="id" type="hidden" value="${(item.id)!''}" />
						<section>
							<div class="row">
								<label class="label col col-2">语音名称</label> <label
									class="input col col-8"> <input id="name" type="text"
									onfocus="onfocus" name="name" value="${(item.name)!''}" /> 
								</label>
							</div>
						</section>
						<section>
							<div class="row">
								<label class="label col col-2">上传文件</label> 
								<div class=" col col-8">
									<div id="fcolor" class="input input-file">
											<span class="button"><input type="file" id="file" name="file" onchange="this.parentNode.nextSibling.value = this.value">浏览...</span><input type="text"  readonly>
									</div>
									<label id="error_hide" class="hide" style="color: #CD5C5C"></label>
									<input type="hidden" id="type" name="type"/>
								</div>
								
							</div>
						</section>
						<section>
							<div class="row">
								<label class="label col col-2">语音描述</label> 
								<label class="textarea col col-8"> 
									<textarea id="info" name="info" class="custom-scroll" rows="3">${(item.info)!''}</textarea>
								</label>
							</div>
						</section>
						
					</fieldset>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					关 闭</button>
				<button id="addVoice" type="button" class="btn btn-primary">
					保 存</button>
			</div>
		</div>
	</div>
</div>


<script type="text/javascript">
	$(document).ready(function() {

		pageSetUp();

		/* 展示modal */
		$('#voice_index_add').modal("show");
		
		$('#voice_index_add').on('hide.bs.modal', function(e){
			$("#voice_index_add").remove();
		});

		/* 提交按钮单击事件  */
		$('#addVoice').click(function() {
			if ($('#voiceForm').valid() && ckFile()) {
				$("#addVoice").attr("disabled", "true");
				$('#voiceForm').submit();
			}

		});

		/* 校验数据 */
		var validator = $('#voiceForm').validate({
			rules : {
				name : {
					required : true,
					remote : {
						type : 'post',
						url : getContext()+ '/system/uploadingvoice/checkVoiceName',
						data : {
							id : function() {
								return $("#voiceForm #id").val(); /* 传递用户编号 */
							},
							voiceName : function() {
								return $("#name").val();
							}
						}
					}
				}
			},messages : {
				name : {
					remote : $.format("该语音名已经存在！")
				}
			},errorPlacement : function(error, element) {
				error.insertAfter(element.parent());
			}
		});

		$('#voiceForm').ajaxForm({
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

					oTable.DataTable().ajax.reload(null,false);;
				}
				$('#voice_index_add').modal("hide");
				validator.resetForm();
			},
			submitHandler : function(form) {
				$(form).ajaxSubmit({
					success : function() {

						$("#voiceForm").addClass('submited');
					}
				});
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				if (textStatus == 'error') {
					$('#voice_index_add').dialog('hide');
					$.smallBox({
						title : "操作失败",
						content : "<i class='fa fa-clock-o'></i> <i>操作失败</i>",
						color : "#C46A69",
						iconSmall : "fa fa-times fa-2x fadeInRight animated",
						timeout : 2000
					});

					oTable.DataTable().ajax.reload(null,false);;
				}
			}
		});
		   //检查上传的文件 
       	function ckFile() {
       		var file = $("#file").val();
       		var fstart = file.lastIndexOf(".");
       		var suffix = file.substring(fstart, file.length).toLowerCase();
       		if (file == "" || file == null) {
       			$("#fcolor").attr("class","input input-file state-error");
       			$("#error_hide").text("请选择上传的文件");
       			$("#error_hide").attr("class","show note");
       			return false;
       		}
       		if (suffix != ".mp3" && suffix != ".MP3" && suffix != ".wav" && suffix != ".WAV") {
       			$("#fcolor").attr("class","input input-file state-error");
       			$("#error_hide").text("类型错误");
       			$("#error_hide").attr("class","show note");
       			return false;
       		}
       		var filesize = 0;
       		var maxsize = 5*1024*1024;//最大文件大小为5M
       		filesize = $("#file").get(0).files[0].size;
       		if(filesize > maxsize){
       			$("#fcolor").attr("class","input input-file state-error");
       			$("#error_hide").text("请上传小于5M的文件！");
       			$("#error_hide").attr("class","show note");
       			return false;
       		};
       		$("#fcolor").attr("class","input input-file state-success");
       		return true;
       	};

	});

</script>
