<div id="content" style="opacity: 1;">
<div class="row">
	<div class="col-md-5">
		<div class="well no-padding">
			<form id="netForm"
				action="${springMacroRequestContext.contextPath}/netconfig/upNetConfig" class="smart-form" method="post">
				<header>
					<label style="font-size: 20px;font-weight: bold;">网络配置</label>
				</header>
				<fieldset>
					<section>
						<div class="row">
							<label class="label col col-3">MAC地址</label>
							<label class="label col col-9">${macAddr}</label>
						</div>
					</section>
					<section>
						<div class="row">
							<label class="label col col-3">IP 地址</label>
							<div class="col col-9">
								<label class="input"> 
									<input name="ipAddr" value="${ipAddr}"/>
								</label>
							</div>
						</div>
					</section>
					<section>
						<div class="row">
							<label class="label col col-3">网关地址</label>
							<div class="col col-9">
								<label class="input"> 
									<input name="gateWay" value="${gateWay}"/>
								</label>
							</div>
						</div>
					</section>
					<section>
						<div class="row">
							<label class="label col col-3">子网掩码</label>
							<div class="col col-9">
								<label class="input"> 
									<input name="netMask" value="${netMask}"/>
								</label>
							</div>
						</div>
					</section>
					<section>
						<div class="row">
							<label class="label col col-3">nginx地址</label>
							<div class="col col-9">
								<label class="input"> 
									<input name="nginx" value="${nginx}"/>
								</label>
							</div>
						</div>
					</section>
					<section>
						<div class="row">
							<label class="col col-3"></label>
							<div class="col col-9" align="right">
								<label id="updNet" class="btn btn-sm btn-primary disabled">修改</label>
							</div>
						</div>
					</section>
				</fieldset>
			</form>
		</div>
	</div>
</div>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/adddate.js"></script>
<script type="text/javascript">
	// jQuery IP 验证   
	jQuery.validator.addMethod("ipp",function(value, element) {
		var ip = /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
		return this.optional(element)|| (ip.test(value) && (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256 && RegExp.$4 < 256));
	}, "Ip地址格式错误");
	$(document).ready(function() {
		var validator = $('#netForm').validate({
			rules : {
				ipAddr : {
					 required : true,
					 ipp : true
				},
				gateWay : {
					required : true,
					ipp : true
				},
				netMask : {
					required : true,
					ipp : true
				}
			},
			errorPlacement : function(error, element) {
				error.insertAfter(element.parent());
			}
		});

		$('#updNet').click(function() {
			if($('#netForm').valid()){
				$('#netForm').submit();
			}
		});

		$('#netForm').ajaxForm({
			dataType : "json",
			success : function(data) {
				if (data.success) {
					$.smallBox({
						title : "操作成功",
						content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px'>配置已成功修改...</i>",
						color : "#659265",
						iconSmall : "fa fa-check fa-2x fadeInRight animated",
						timeout : 2000
					});
				}
			},
			submitHandler : function(form) {
				$(form).ajaxSubmit({
					success : function() {
						$("#netForm").addClass('submited');
					}
				});
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				if (textStatus == 'error') {
					$.smallBox({
						title : "操作失败",
						content : "<i class='fa fa-clock-o'></i> <i>修改出现异常,请重试或与服务商联系...</i>",
						color : "#C46A69",
						iconSmall : "fa fa-times fa-2x fadeInRight animated",
					});
				}
			}
		});
	});
</script>
</div>