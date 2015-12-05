<div class="modal fade" id="accessnumberSaveModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    <strong>关闭</strong>
                </button>
                <h4 class="modal-title" id="myModalLabel">接入号管理</h4>
            </div>
            <div class="modal-body">

                <form action="${springMacroRequestContext.contextPath}/config/accessnumber/save" class="smart-form"
                      id="coreForm">

                   <input type="hidden" id="id" name="id" value="${(item.id)!''}">


                    <section>
                        <div class="row">
                            <label class="label col col-3">接入号</label>
                            <label class="input col col-6"><input id="accessnumber" name="accessnumber" onfocus="onfocus" value="${(item.accessnumber)!''}"
                                                                  class="required"/></label>
                        </div>
                    </section>
                    
						<section>
							<div class="row">
								<label class="label col col-3">并发</label> <label
									class="input col col-6"> <input id="concurrency"
									name="concurrency" value="${(item.concurrency)!''}">
								</label>
							</div>
						</section>
                    
                    
                    <section>
								<div class="row">
									<label class="label col col-3">可以呼入</label>
									<label class="checkbox col col-6">
												<input type="checkbox" name="canin" id="canin"  <#if (item.canin)??><#if (item.canin)?number==1>checked="checked" </#if><#else>checked="checked"</#if> /><i  style="left: 15px"></i>
									</label>
								</div>
					</section>
					
					<section>
								<div class="row">
									<label class="label col col-3">可以呼出</label>
									<label class="checkbox col col-6">
												<input type="checkbox" name="canout" id="canout"  <#if (item.canout)??><#if (item.canout)?number==1>checked="checked" </#if><#else>checked="checked"</#if> /><i  style="left: 15px"></i>
									</label>
								</div>
					</section>
					
					<section>
						<div class="row">
							<label class="label col col-3">设置默认</label>
							<label class="checkbox col col-6">
								<input type="checkbox" name="defaultaccessnumber" id="defaultaccessnumber"  <#if defaultaccessnumber??> checked="checked" </#if> /> <i  style="left: 15px"></i>
							</label>
						</div>
					</section>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    取消
                </button>
                <button type="button" id="saveButton" class="btn btn-primary">
                    保存
                </button>
            </div>
            </form>
        </div>
    </div>
</div>

<script type="application/javascript">

$(document).ready(function(){
	
	/** 校验字段  **/
	var validator = $('#coreForm').validate({
	    rules: {
	    	accessnumber : {
	    		digits : true,
				required : true,
				remote : {
					type : 'post',
					url : getContext()+ 'config/accessnumber/isRepeat',
					data : {
						id : function() {
							return $("#coreForm #id").val(); 
						},
						name : function() {
							return $("#accessnumber").val();
						}
					}
				}
			},
			concurrency:{
				required : true,
				digits : true
			}
			
	    },messages : {
	    	accessnumber : {
				remote : $.format("该接入号已经存在！")
			}
		},errorPlacement: function (error, element) {
	        error.insertAfter(element.parent());
	    }
	});

	$('#accessnumberSaveModel').modal("show");
	
	$('#accessnumberSaveModel').on('hide.bs.modal', function(e){
		$("#accessnumberSaveModel").remove();
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
	        $("#accessnumberSaveModel").modal("hide");
	        if (data.success) {
	        	$("#toDeployButton").removeAttr("disabled");
	        	$("#toDeployButton").removeClass("btn-default");
				$("#toDeployButton").addClass("btn-sm");
				$("#toDeployButton").addClass("btn-success");
	            $.smallBox({
	                title: "操作成功",
	                content: "<i class='fa fa-clock-o'></i> <i>接入号已成功保存...</i>",
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
	                $("#coreForm").addClass('submited');
	            }
	        });
	    },
	    error: function (XMLHttpRequest, textStatus, errorThrown) {
	        if (textStatus == 'error') {
	            $("#accessnumberSaveModel").modal("hide");
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
