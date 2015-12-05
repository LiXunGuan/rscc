<div class="modal fade" id="returnDataSave" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 500px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    <strong>关闭</strong>
                </button>
                <h4 class="modal-title">修改部门数据上限</h4>
            </div>
            <div class="modal-body">
                <form action="${springMacroRequestContext.contextPath}/deptdata/dept/updateLimit" class="smart-form" id="returnDataForm">
                	<fieldset style="padding-top: 2px;">
						<#if allDept??>
							<#list allDept as dept>
								<div class="row">
			              			<input type="hidden" name="departmentUuid" value="${dept.uid}">
				     				<section class="col col-5">
										<label class="input">
											${dept.departmentName}
				                		</label>
				              		</section>
				            		<section class="col col-6">
										<label class="input">
						              		<#if Session["CURRENTUSER"]["uid"] == '0'>
												<input name="totalLimit" value="${dept.totalLimit}">
											<#else>
												<input name="totalLimit" value="${dept.totalLimit}" disabled="disabled">
											</#if>
				                		</label>
				              		</section>
				           		</div>
			           		</#list>
		           		</#if>
                    </fieldset>
	            </form>
	            <div id="msg"><span>暂无所管辖的部门！</span></div>
            </div>
            <div class="modal-footer">
                <button type="button" id="quxiao" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" id="returnDataSaveButton" class="btn btn-primary">确定</button>
            </div>
        </div>
    </div>
</div>

<script type="application/javascript">

	$(document).ready(function() {
	    
	    $('#returnDataSave').modal("show");
		
		/* 关闭窗口的回调函数,hide是关闭调用后在关闭    */
	    $('#returnDataSave').on('hide.bs.modal', function(e){
			$("#returnDataSave").remove();
		});
		
	    $("#msg").hide();
		
	    <#if !allDept??>
		    $("#returnDataForm").hide();
			$("#msg").show();
			$("#quxiao").text("关闭");
			$("#returnDataSaveButton").hide();
	    </#if>
	    
	    <#if Session["CURRENTUSER"]["uid"] != '0'>
		    $("#quxiao").text("关闭");
			$("#returnDataSaveButton").hide();
	    </#if>
	    
	    $('#returnDataSaveButton').click(function () {
	        if($('#returnDataForm').valid()){
	        	$('#returnDataForm').submit();
	        }
	    });
	    
	    /** 校验字段  **/
	    var validator = $('#returnDataForm').validate({
	        rules: {
	        	returnDataNumber : {
	        		required : true,
	        		digits:true,
	        		number:true,
	        		max:$('#totalLimit').val(),
	        		min:1
	        	}
	        },messages : {
	        	returnDataNumber : {
	        		required : "请输入分配的数量"
	        	}
			},errorPlacement: function (error, element) {
	            error.insertAfter(element.parent());
	        }
	    });
	
	    $('#returnDataForm').ajaxForm({
	        dataType: "json",
	        type: "post",
	        success: function (data) {
	            $("#returnDataSave").modal("hide");
	            if (data.success) {
	                $.smallBox({
	                    title: "操作成功",
	                    content: "<i class='fa fa-clock-o'></i> <i>修改...</i>",
	                    color: "#659265",
	                    iconSmall: "fa fa-check fa-2x fadeInRight animated",
	                    timeout: 2000
	                });
	                $("#dt_basic_deptdata").DataTable().ajax.reload(null,false);
	            };
	        },
	        submitHandler: function (form) {
	            $(form).ajaxSubmit({
	                success: function () {
	                    $("#returnDataForm").addClass('submited');
	                }
	            });
	        },
	        error: function (XMLHttpRequest, textStatus, errorThrown) {
	            if (textStatus == 'error') {
	                $("#returnDataSave").modal("hide");
	                $.smallBox({
	                    title: "操作失败",
	                    content: "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作出现异常...</i>",
	                    color: "#C46A69",
	                    iconSmall: "fa fa-times fa-2x fadeInRight animated"
	                });
	            }
	        }
	    });
	    
	    $('#returnDataNumber').bind('keypress',function(event){
            if(event.keyCode == "13"){
            	$('#returnDataSaveButton').click();
            	return false;
            }
        });
	});

</script>
