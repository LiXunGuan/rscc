<div class="modal fade" id="achieveDataSave" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 600px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    <strong>关闭</strong>
                </button>
                <h4 class="modal-title">获取数据</h4>
            </div>
            <div class="modal-body">
                <form action="${springMacroRequestContext.contextPath}/deptdata/dept/achieveDataSave" class="smart-form" id="achieveDataForm" method="post">
                	<fieldset style="padding-top: 2px;">
						<input type="hidden" name="dataBatchUuid" id="dataBatchUuid" value="${(dataBatchUuid)!''}" />
						<input type="hidden" name="deptUuid" id="deptUuid" value="${(deptUuid)!''}" />
						<input type="hidden" name="totalLimit" id="totalLimit" value="${(totalLimit)!''}" />
						<i class="fa fa-lg fa-volume-up" style="margin-right: 5px;"></i>
						<span>最多获取数据<font color="red">${(totalLimit)!''}</font>条</span><br><br>
	                    <section>
	                        <div class="row">
	                            <label class="label col col-3"> 获取数量： </label>
	                            <label class="input col col-4">
	                            	<input id="achieveDataNumber" name="achieveDataNumber" value="${(totalLimit)!''}"/>
	                            </label>
	                        </div>
	                    </section>
                    </fieldset>
	            </form>
				<div id="msg"><span>暂不能获取！<br><br>请确认该部门内数据是否已达上限，或者关联批次中是否还有可获取数据。</span></div>
            </div>
            <div class="modal-footer">
                <button type="button" id="quxiao" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" id="achieveDataSaveButton" class="btn btn-primary">获取</button>
            </div>
        </div>
    </div>
</div>

<script type="application/javascript">

	$("#msg").hide();

	$(document).ready(function() {
	    
	    $('#achieveDataSave').modal("show");
		
		/* 关闭窗口的回调函数,hide是关闭调用后在关闭    */
	    $('#achieveDataSave').on('hide.bs.modal', function(e){
			$("#achieveDataSave").remove();
		});
		
		var numlimit = Number($('#totalLimit').val());
		
		if(numlimit <= 0){
			$("#achieveDataForm").hide();
			$("#msg").show();
			$("#quxiao").text("关闭");
			$("#achieveDataSaveButton").hide();
		}
// 	    $("#achieveDataNumber").attr("max", numlimit);
// 	    $("#achieveDataNumber").attr("min", 1);
	    
	    $('#achieveDataSaveButton').click(function () {
	        if($('#achieveDataForm').valid()){
	        	$('#achieveDataSaveButton').attr("disabled","disabled");
	        	$('#achieveDataForm').submit();
	        }
	    });
	    
	    /** 校验字段  **/
	    var validator = $('#achieveDataForm').validate({
	        rules: {
	        	achieveDataNumber : {
	        		required : true,
	        		digits:true,
	        		number:true,
	        		max:numlimit,
	        		min:1
	        	}
	        },messages : {
	        	achieveDataNumber : {
	        		required : "请输入获取的数量"
	        	},
			},
	        errorPlacement: function (error, element) {
	            error.insertAfter(element.parent());
	        }
	    });
	
	    $('#achieveDataForm').ajaxForm({
	        dataType: "json",
	        type: "post",
	        success: function (data) {
	            $("#achieveDataSave").modal("hide");
	            if (data.success) {
	                $.smallBox({
	                    title: "操作成功",
	                    content: "<i class='fa fa-clock-o'></i> <i>获取数据成功...</i>",
	                    color: "#659265",
	                    iconSmall: "fa fa-check fa-2x fadeInRight animated",
	                    timeout: 2000
	                });
	            	$("table.dataTable").DataTable().ajax.reload(null,false);
	            	$("#dt_basic_deptdata").DataTable().ajax.reload(null,false);
	            };
	        },
	        submitHandler: function (form) {
	            $(form).ajaxSubmit({
	                success: function () {
	                    $("#achieveDataForm").addClass('submited');
	                }
	            });
	        },
	        error: function (XMLHttpRequest, textStatus, errorThrown) {
	            if (textStatus == 'error') {
	                $("#achieveDataSave").modal("hide");
	                $.smallBox({
	                    title: "操作失败",
	                    content: "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作出现异常...</i>",
	                    color: "#C46A69",
	                    iconSmall: "fa fa-times fa-2x fadeInRight animated"
	                });
	            }
	        }
	    });
	    
	    $('#achieveDataNumber').bind('keypress',function(event){
            if(event.keyCode == "13"){
            	$('#achieveDataSaveButton').click();
            	return false;
            }
        });
	});

</script>
