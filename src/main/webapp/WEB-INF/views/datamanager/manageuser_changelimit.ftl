<div class="modal fade" id="changeLimitSave" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 600px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    <strong>关闭</strong>
                </button>
                <h4 class="modal-title">配额设置</h4>
            </div>
            <div class="modal-body">
                <form action="${springMacroRequestContext.contextPath}/userManage/changeLimitSave" class="smart-form" id="changeLimitForm" method="post">
                	<fieldset style="padding-top: 2px;">
						<input type="hidden" name="userUuid" id="userUuid" value="${(userUuid)!''}" />
	                    <section>
	                        <div class="row">
	                            <label class="label col col-3"> 总任务上限： </label>
	                            <label class="input col col-6">
	                            	<input id="totalLimit" name="totalLimit" value="${totalLimit!''}" class="digits"/>
	                            </label>
	                        </div>
	                    </section>
	                    <section>
	                        <div class="row">
	                            <label class="label col col-3"> 单日任务上限： </label>
	                            <label class="input col col-6">
	                            	<input id="dayLimit" name="dayLimit" value="${dayLimit!''}" />
	                            </label>
	                        </div>
	                    </section>
	                    <section>
	                        <div class="row">
	                            <label class="label col col-3"> 单次任务上限： </label>
	                            <label class="input col col-6">
	                            	<input id="singleLimit" name="singleLimit" value="${singleLimit!''}" />
	                            </label>
	                        </div>
	                    </section>
	                    <section>
	                        <div class="row">
	                            <label class="label col col-3"> 意向客户上限： </label>
	                            <label class="input col col-6">
	                            	<input id="intentLimit" name="intentLimit" value="${intentLimit!''}" />
	                            </label>
	                        </div>
	                    </section>
                    </fieldset>
	            </form>
            </div>
            <div class="modal-footer">
            	<label id="validatelimit" class="hidden" style="color:red;margin-right: 30%;"></label>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" id="changeLimitSaveButton" class="btn btn-primary">保存 </button>
            </div>
        </div>
    </div>
</div>

<script type="application/javascript">

	$(document).ready(function() {
	    
	    $('#changeLimitSave').modal("show");
		
		/* 关闭窗口的回调函数,hide是关闭调用后在关闭    */
	    $('#changeLimitSave').on('hide.bs.modal', function(e){
			$("#changeLimitSave").remove();
		});
		
		var numlimit = Number($('#totalLimit').val());
	    
	    $('#changeLimitSaveButton').click(function () {
	        if($('#changeLimitForm').valid()){
	        	var sumLimit = $("#totalLimit").val();
	        	var dayLimit = $("#dayLimit").val();
	        	var singleLimit = $("#singleLimit").val();
	        	if(Number(sumLimit) < Number(dayLimit)){
	        		$("#validatelimit").attr("class","");
	        		$("#validatelimit").text("总任务上限不能小于单日任务上限！");
	        		return false;
	        	}
	        	if(Number(dayLimit) < Number(singleLimit)){
	        		$("#validatelimit").attr("class","");
	        		$("#validatelimit").text("单日任务上限不能小于单次任务上限！");
	        		return false;
	        	}
	        	$('#changeLimitForm').submit();
	        }
	    });
	    
	    /** 校验字段  **/
	    var validator = $('#changeLimitForm').validate({
	        rules: {
	        	totalLimit : {
	        		required : true,
	        		digits:true,
	        		number:true,
	        		min:1,
	        		remote : {
            			type : "post",
            			url : window.parent.getContext() + 'userManage/limitCompare?dueTime=sum',
            			data : { uid : function(){ return $('#uid').val(); }},
            			complete:function(d){
            				if(d.responseText != 'true'){}
            			}
            		}
	        	},
	        	dayLimit : {
	        		required : true,
	        		digits:true,
	        		number:true,
	        		min:1,
	        		remote : {
            			type : "post",
            			url : window.parent.getContext() + 'userManage/limitCompare?dueTime=day',
            			data : { uid : function(){ return $('#uid').val(); }},
            			complete:function(d){
            				if(d.responseText != 'true'){}
            			}
            		}
	        	},
	        	singleLimit : {
	        		required : true,
	        		digits:true,
	        		number:true,
	        		min:1,
	        		remote : {
            			type : "post",
            			url : window.parent.getContext() + 'userManage/limitCompare?dueTime=single',
            			data : { uid : function(){ return $('#uid').val(); }},
            			complete:function(d){
            				if(d.responseText != 'true'){}
            			}
            		} 
	        	},
	        	intentLimit : {
	        		required : false,
	        		digits:true,
	        		number:true,
	        		min:0
	        	}
	        },messages : {
	        	totalLimit : {
	        		required : "请输入数量",
	        		remote : "不能超过全局设置的数值!"
	        	},
	        	dayLimit : {
	        		required : "请输入数量",
	        		remote : "不能超过全局设置的数值!"
	        	},
	        	singleLimit : {
	        		required : "请输入数量",
	        		remote : "不能超过全局设置的数值!"
	        	}
			},
	        errorPlacement: function (error, element) {
	            error.insertAfter(element.parent());
	        }
	    });
	
	    $('#changeLimitForm').ajaxForm({
	        dataType: "json",
	        type: "post",
	        success: function (data) {
	            $("#changeLimitSave").modal("hide");
	            if (data.success) {
	                $.smallBox({
	                    title: "操作成功",
	                    content: "<i class='fa fa-clock-o'></i> <i>获取数据成功...</i>",
	                    color: "#659265",
	                    iconSmall: "fa fa-check fa-2x fadeInRight animated",
	                    timeout: 2000
	                });
	            	$("table.dataTable").DataTable().ajax.reload(null,false);
	            };
	        },
	        submitHandler: function (form) {
	            $(form).ajaxSubmit({
	                success: function () {
	                    $("#changeLimitForm").addClass('submited');
	                }
	            });
	        },
	        error: function (XMLHttpRequest, textStatus, errorThrown) {
	            if (textStatus == 'error') {
	                $("#changeLimitSave").modal("hide");
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
            	$('#changeLimitSaveButton').click();
            	return false;
            }
        });
	});

</script>
