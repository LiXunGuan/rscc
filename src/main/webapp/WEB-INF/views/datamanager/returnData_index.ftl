<div class="modal fade" id="returnDataSave" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 600px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    <strong>关闭</strong>
                </button>
                <h4 class="modal-title">归还数据</h4>
            </div>
            <div class="modal-body">
                <form action="${springMacroRequestContext.contextPath}/deptdata/dept/returnDataSave" class="smart-form" id="returnDataForm">
                	<fieldset style="padding-top: 2px;">
						<input type="hidden" name="dataBatchUuid" id="dataBatchUuid" value="${(dataBatchUuid)!''}" />
						<input type="hidden" name="deptUuid" id="deptUuid" value="${(deptUuid)!''}" />
						<input type="hidden" name="totalLimit" id="totalLimit" value="${(totalLimit)!''}" />
	                    <i class="fa fa-lg fa-volume-up" style="margin-right: 5px;"></i>
						<span>最多归还数据<font color="red">${(totalLimit)!''}</font>条</span><br><br>
	                    <section>
	                        <div class="row">
	                            <label class="label col col-3"> 归还数量： </label>
	                            <label class="input col col-4">
	                            	<input id="returnDataNumber" name="returnDataNumber" value="${(totalLimit)!''}" />
	                            </label>
	                        </div>
	                    </section>
                    </fieldset>
	            </form>
                <div id="msgs"><span>暂无数据归还！</span></div>
            </div>
            <div class="modal-footer">
                <button type="button" id="quxiao" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" id="returnDataSaveButton" class="btn btn-primary">归还</button>
            </div>
        </div>
    </div>
</div>

<script type="application/javascript">

	$(document).ready(function() {
	    
		$("#msgs").hide();
		
	    $('#returnDataSave').modal("show");
		
		/* 关闭窗口的回调函数,hide是关闭调用后在关闭    */
	    $('#returnDataSave').on('hide.bs.modal', function(e){
			$("#returnDataSave").remove();
		});
		
	    var numlimit = $('#totalLimit').val();
	    if(numlimit <= 0){
	    	$("#returnDataForm").hide();
	    	$("#msgs").show();
	    	$("#quxiao").text("关闭");
	    	$("#returnDataSaveButton").hide();
	    }
	    
// 	    $("#returnDataNumber").attr("max", numlimit);
// 	    $("#returnDataNumber").attr("min", 1);
	    
	    $('#returnDataSaveButton').click(function () {
	        if($('#returnDataForm').valid()){
	        	$("#returnDataSaveButton").attr("disabled","disabled");
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
			},
	        errorPlacement: function (error, element) {
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
	                    content: "<i class='fa fa-clock-o'></i> <i>数据成功归还...</i>",
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
