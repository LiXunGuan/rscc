<div class="modal fade" id="gateway_index_add">
	<div class="modal-dialog " style="">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true"><strong>关闭</strong></button>


				<h4 class="modal-title">添加网关</h4>
			</div>
			<div class="modal-body">
				<form id="sysexternalForm"
					action="${springMacroRequestContext.contextPath}/config/gateway/save"
					class="smart-form" method="post">
					<fieldset style="padding-top: 2px;">


						
						<section>
						<div class="row hidden">
							<label class="label col col-3"> 主机 </label> <label
								class="select col col-6">
								<select id="fshost_id" name="fshost_id" class="select2"<#if (item.id)??>disabled="disabled"</#if> >
								<option  value="1" selected="selected">local</option>
<!--								<#list serverls as s>
										<#if (item.fshost_id)??>
											<#if (item.fshost_id)?number==(s.id)?number>
												<option selected="selected"  value="${s.id}" >${s.name}</option> 
											<#else>
												<option  value="${s.id}">${s.name}</option> 
											</#if>
										<#else>
											<option  value="${s.id}">${s.name}</option> 
										</#if>
									</#list>    -->               
								</select>
							</label>
						</div>
						</section>
						
						<section>
						<div class="row hidden">
							<label class="label col col-3"> SipProfile </label> <label
								class="select col col-6">
								<select id="sipProfileId" name="sipProfileId" class="select2"<#if (item.id)??>disabled="disabled"</#if>>
									<option  value="2" selected="selected">external</option>
<!--									<#if sipls??>
										<#list sipls as s>
											<#if (item.sipProfileId)??>
												<#if (item.sipProfileId)?number==(s.id)?number>
													<option selected="selected"  value="${s.id}" >${s.name}</option> 
												<#else>
													<option  value="${s.id}" <#if s.name=="external">selected="selected"</#if>>${s.name}</option> 
												</#if>
											<#else>
												<option  value="${s.id}" <#if s.name=="external">selected="selected"</#if>>${s.name}</option> 
											</#if>
										</#list>
									</#if>             -->
								</select>
							</label>
						</div>
						</section>
						
						
						
						<section>
							<div class="row">
								<label class="label col col-3">网关名称</label> <label
									class="input col col-6"> 
									<input id="id" type="hidden" onfocus="onfocus" name="id"
									value="${(item.id)!''}" /> <input id="name"
									onfocus="onfocus" name="name" value="${(item.name)!''}"   />
								</label>
							</div>
						</section>

						<section>
							<div class="row">
								<label class="label col col-3">网关IP</label> <label
									class="input col col-6"> <input id="ip" name="ip"
									value="${(item.ip)!''}">
								</label>

							</div>
						</section>
						
						<section>
							<div class="row">
								<label class="label col col-3">网关端口</label> <label
									class="input col col-6"> <input id="port" name="port"
									value="${(item.port)!'5060'}">
								</label>

							</div>
						</section>
						

						
							<section>
								<div class="row">
									<label class="label col col-3">是否注册</label>
									<label class="checkbox col col-6">
												<input type="checkbox" name="registrations" id="registrations"  <#if item??><#if (item.registration)?number==1>checked="checked" value="1"</#if></#if>  onclick="typeSty()" /><i  style="left: 15px"></i>
									</label>
								</div>
						</section>
						
						
								<section>
								<div class="row">
									<label class="label col col-3 ">注册用户名</label>
									<label <#if item??><#if (item.registration)?number!=1>class="input col col-6 state-disabled"<#else>class="input col col-6"</#if><#else>class="input col col-6 state-disabled"</#if> >
										<input type="text" id="username" name="username"  value="${(item.username)!''}"<#if item??><#if (item.registration)?number!=1>disabled="disabled"<#else>class="required"</#if><#else>disabled="disabled"</#if>  />
									</label>
								</div>
							</section>
	
							<section>
								<div class="row">
									<label class="label col col-3">注册密码</label>
									<label <#if item??><#if (item.registration)?number!=1>class="input col col-6 state-disabled"<#else>class="input col col-6"</#if><#else>class="input col col-6 state-disabled"</#if> >
										 <input type="password" id="pwd" name="pwd" value="${(item.password)!''}"<#if item??> <#if (item.registration)?number!=1>disabled="disabled"<#else>class="required"</#if><#else>disabled="disabled"</#if>  />
									</label>
								</div>
							</section>
							


					</fieldset>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					关 闭</button>
				<button id="addExternal" type="button" class="btn btn-primary">
					保 存</button>
			</div>
		</div>
	</div>
</div>


<script type="text/javascript">
	$(document).ready(function() {

		$("#fshost_id").select2({
			width:"100%"
		});
		$("#sipProfileId").select2({
			width:"100%"
		});
						pageSetUp();

						/* jQuery IP 验证   */
						jQuery.validator
								.addMethod(
										"ipp",
										function(value, element) {
											var ip = /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
											return this.optional(element)
													|| (ip.test(value) && (RegExp.$1 < 256
															&& RegExp.$2 < 256
															&& RegExp.$3 < 256 && RegExp.$4 < 256));
										}, "Ip地址格式错误");
						/* jquery 汉字判断  */
						jQuery.validator
								.addMethod(
										"chinese",
										function(value, element) {
											
											// 不能判断到汉字和其他数字字母组合情况
											// var myReg = /^[\u4e00-\u9fa5]+$/;
											
											// onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" 放在input中使用
											var myReg = /[^\w\.\/]/ig;
											return !myReg.test(value);
										}, "名称不能包含汉字！");

						/* 展示modal */
						$('#gateway_index_add').modal("show");
						
						$('#gateway_index_add').on('hide.bs.modal', function(e){
							$("#gateway_index_add").remove();
						});

						/* 提交按钮单击事件  */
						$('#addExternal').click(function() {
							if ($('#sysexternalForm').valid()) {
								$("#addExternal").attr("disabled", "true");
								$('#sysexternalForm').submit();
							}

						});

						/* 校验数据 */
						var validator = $('#sysexternalForm')
								.validate(
										{
											rules : {
												ip : {
													ipp : true,
													required : true
												},
												pwd : {
													required : true,
													maxlength : 32
												},
												name : {
													chinese : true,
													required : true,
													maxlength : 32,
													remote : {
														type : 'post',
														url : getContext()+ 'config/gateway/isRepeat',
														data : {
															id : function() {
																return $("#gateway_index_add #id").val(); /* 传递用户编号 */
															},
															name : function(){
																return $("#name").val();
															}
														}
													}
												},
												username : {
													required : true,
													maxlength : 16
												},
												prefixString : {
													digits : true,
													maxlength : 16

												},
												concurrency : {
													required : true,
													digits : true,
													maxlength : 16

												},
												fshost_id : {
													required : true,

												},
												sipProfileId : {
													required : true,

												}
											},
											messages : {
												name : {
													remote : $
															.format("该SipTrunk名已经存在！")
												}
											},
											errorPlacement : function(error,
													element) {
												error.insertAfter(element
														.parent());
											}
										});

						$('#sysexternalForm')
								.ajaxForm(
										{
											dataType : "json",
											success : function(data) {
												if (data.success) {
													 $("#toDeployButton").removeAttr("disabled");
													 $("#toDeployButton").removeClass("btn-default");
														$("#toDeployButton").addClass("btn-sm");
														$("#toDeployButton").addClass("btn-success");
													$
															.smallBox({
																title : "操作成功",
																content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
																color : "#659265",
																iconSmall : "fa fa-check fa-2x fadeInRight animated",
																timeout : 2000
															});
													oTable.DataTable().ajax.reload(null,false);;
												}
												$('#gateway_index_add').modal(
														"hide");
												validator.resetForm();
											},
											submitHandler : function(form) {
												$(form)
														.ajaxSubmit(
																{
																	success : function() {

																		$(
																				"#sysexternalForm")
																				.addClass(
																						'submited');
																	}
																});
											},
											error : function(XMLHttpRequest,
													textStatus, errorThrown) {
												if (textStatus == 'error') {
													$('#gateway_index_add')
															.dialog('hide');
													$
															.smallBox({
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

					});

	$('#ownagent').select2({
		allowClear : true,
		width : '99%'
	});
	$('#userId').select2({
		allowClear : true,
		width : '99%'
	});
	$('#useravilable').select2({
		allowClear : true,
		width : '99%'
	});
	
	$("#fshost_id").change(function(){
		var fshost_id=$("#fshost_id").val();
		var url =getContext()+"config/gateway/getSipProfile";
		$.post(url,{fshost_id:fshost_id},function(data){
			$("#sipProfileId option").remove();
			if (data.result=="false") {
				$("#sipProfileId").append("<option value=''>请选择</option>");
			}else{
				for(var i in data){
					  if(typeof data[i] !="function"){
						  $("#sipProfileId").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
					  }
				  }	
			}
		  
		},'json');
		
	});
	
	function typeSty(){
    	if ($("#registrations").is(':checked')) {
    		$("#username").parent().removeClass("state-disabled"); 
			$("#pwd").parent().removeClass("state-disabled"); 
			$("#username").removeAttr("disabled"); 
			$("#pwd").removeAttr("disabled"); 
			$("#username").addClass("required"); 
			$("#pwd").addClass("required"); 
			$("#registrations").attr("value","1"); 
		}else{
			$("#username").parent().addClass("state-disabled");
    		$("#pwd").parent().addClass("state-disabled");
    		$("#username").attr("disabled","disabled");
    		$("#pwd").attr("disabled","disabled");
    		$("#username").removeAttr("required"); 
			$("#pwd").removeAttr("required"); 
			
		}
    }
</script>
