<div class="modal fade" id="sipprofileSaveModel" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width: 600px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true"><strong>关闭</strong></button>
				<h4 class="modal-title" id="myModalLabel">SipProfile</h4>
			</div>
			<div class="modal-body" style="height: 520px;">

				<form
					action="${springMacroRequestContext.contextPath}/config/sipprofile/save"
					class="smart-form" id="coreForm">

					<input type="hidden" id="id" name="id" value="${(item.id)!''}">
					
					<section>
						<div class="row">
							<label class="label col col-2"> 主机名 </label> <label
								class="select col col-8">
								<select id="fshostid" name="fshostid" >
									<option  value="">请选择</option> 
									<#list fshostls as s>
										<#if (item.fshostid)??>
											<#if (item.fshostid)?number==(s.id)?number>
												<option selected="selected"  value="${s.id}" >${s.name}</option> 
											<#else>
												<option  value="${s.id}">${s.name}</option> 
											</#if>
										<#else>
											<option  value="${s.id}">${s.name}</option> 
										</#if>
									</#list>
								
								</select>
								
							</label>
						</div>
					</section>

					<section>
						<div class="row">
							<label class="label col col-2"> 名称 </label> <label
								class="input col col-8"><input id="name" name="name"
								value="${(item.name)!''}" class="required" /></label>
						</div>
					</section>

					<section>
						<div class="row">
							<label class="col col-2"> SipIp </label> <label
								class="input col col-8"> <input id="sipIp" name="sipIp"
								value="${(item.sipIp)!''}" class="required" />
							</label>
						</div>
					</section>

					<section>
						<div class="row">
							<label class="label col col-2"> SipPort </label> <label
								class="input col col-8"><input id="sipPort"
								name="sipPort" value="${(item.sipPort)!''}" class="required" /></label>
						</div>
					</section>

					<section>
						<div class="row">
							<label class="label col col-2"> Type </label> <label
								class="select col col-8"> <select id="type" name="type">
									<#list intext?keys as key> <#if (item.type)??> <#if
									key==(item.type)>
									<option value="${key}" selected="selected">${intext[key]}</option>
									<#else>
									<option value="${key}">${intext[key]}</option> </#if> <#else>
									<option value="${key}">${intext[key]}</option> </#if> </#list>
							</select>
							</label>
						</div>
					</section>

					


					<#if codecls??> 
					<#list codecls as cs>
					<section>
						<div class="row">
							<label class="label col col-2">CodecString </label> <label
								class="select col col-8"> <select  name="codecString">
									<#list codec?keys as key> <#if key==cs>
									<option selected="selected" value="${key}">${codec[key]}</option>
									<#else>
									<option value="${key}">${codec[key]}</option> </#if> </#list>
							</select>
							</label>
						</div>
					</section>
					</#list> 
					<#else> <#list 0 .. 3 as i>
					<section>
						<div class="row">
							<label class="label col col-2">CodecString </label> <label
								class="select col col-8"> <select name="codecString">
									<#list codec?keys as key>
									<option value="${key}">${codec[key]}</option> </#list>
							</select>
							</label>
						</div>
					</section>
					</#list> </#if>


					<div class="modal-footer"
						style="margin-top: 15px; padding: 19px 0px 20px;">
						<button type="button" class="btn btn-default"
							style="padding: 6px 12px;" data-dismiss="modal">关 闭</button>
						<button id="saveButton" type="button" class="btn btn-primary"
							style="padding: 6px 12px;">保 存</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<script type="application/javascript">
		
    $(document).ready(function(){
    	jQuery.validator.addMethod(
    			"ipp",
    			function(value, element) {
    				var ip = /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
    				return this.optional(element)
    						|| (ip.test(value) && (RegExp.$1 < 256
    								&& RegExp.$2 < 256
    								&& RegExp.$3 < 256 && RegExp.$4 < 256));
    			}, "Ip地址格式错误");
    	/** 校验字段  **/
    	var validator = $('#coreForm').validate({
    	    rules: {
    	    	sipIp : {
    	    		ipp : true,
    				required : true
    			},
    			sipPort : {
    				digits : true,
    				maxlength : 5
    			},
    			serverId : {
    				required : true
    			},
    			name : {
    				required : true,
    				remote : {
    					type : 'post',
    					url : getContext()+ '/config/sipprofile/isRepeat',
    					data : {
    						id : function() {
    							return $("#coreForm #id").val(); /* 传递用户编号 */
    						}
    					}
    				}
    			}
    	    },messages : {
    			name : {
    				remote : $.format("该主机名已经存在！")
    			}
    		},errorPlacement: function (error, element) {
    	        error.insertAfter(element.parent());
    	    }
    	});

    	$('#sipprofileSaveModel').modal("show");
    	
    	$('#sipprofileSaveModel').on('hide.bs.modal', function(e){
			$("#sipprofileSaveModel").remove();
		});

    	$('#saveButton').click(function () {
    	    if ($('#coreForm').valid()) {
    	        $('#saveButton').attr("disabled", true);
    	        $('#coreForm').submit();
    	    }
    	});


    	$('#coreForm').ajaxForm({
    	    dataType: "json",
    	    type: "post",
    	    success: function (data) {
    	        $("#sipprofileSaveModel").modal("hide");
    	        if (data.success) {
    	            $.smallBox({
    	                title: "操作成功",
    	                content: "<i class='fa fa-clock-o'></i> <i>分机已成功保存...</i>",
    	                color: "#659265",
    	                iconSmall: "fa fa-check fa-2x fadeInRight animated",
    	                timeout: 2000
    	            });
    	            oTable.fnReloadAjax();
    	        }
    	        ;
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
    	            $("#sipprofileSaveModel").modal("hide");
    	            $.smallBox({
    	                title: "操作失败",
    	                content: "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作出现异常...</i>",
    	                color: "#C46A69",
    	                iconSmall: "fa fa-times fa-2x fadeInRight animated"
    	            });
    	        }
    	    }
    	});
    	
    })

	</script>