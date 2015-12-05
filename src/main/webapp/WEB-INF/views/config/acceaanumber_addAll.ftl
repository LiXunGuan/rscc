<div class="modal fade" id="accessnumberAddAllSaveModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    <strong>关闭</strong>
                </button>
                <h4 class="modal-title" id="myModalLabel">批量添加</h4>
            </div>
            <div class="modal-body">
                <form action="${springMacroRequestContext.contextPath}/config/accessnumber/saveAll" class="smart-form" id="coreForm">
                    <section>
                        <div class="row">
                            <label class="label col col-3">起始接入号</label>
                            <label class="input col col-6">
                            	<input id="accessnumber" name="accessnumber" onfocus="onfocus" value="" class="required"/>
                            </label>
                        </div>
                    </section>
                    <section>
                        <div class="row">
                            <label class="label col col-3">结束接入号</label>
                            <label class="input col col-6">
                            	<input id="endaccessnumber" name="endaccessnumber" value="" class="required"/>
                            </label>
                            <input type="hidden" id="num" name="num" value="" class="required"/>
                        </div>
                    </section>
                    <section>
						<div class="row">
							<label class="label col col-3">并发</label>
							<label class="input col col-6"> 
								<input id="concurrency" name="concurrency" value="">
							</label>
						</div>
					</section>
                    <section>
						<div class="row">
							<label class="label col col-3">可以呼入</label>
							<label class="checkbox col col-6">
								<input type="checkbox" name="canin" id="canin" <#if (item.canin)??><#if (item.canin)?number==1>checked="checked" </#if><#else>checked="checked"</#if> /><i  style="left: 15px"></i>
							</label>
						</div>
					</section>
					<section>
						<div class="row">
							<label class="label col col-3">可以呼出</label>
							<label class="checkbox col col-6">
								<input type="checkbox" name="canout" id="canout" <#if (item.canout)??><#if (item.canout)?number==1>checked="checked" </#if><#else>checked="checked"</#if> /><i  style="left: 15px"></i>
							</label>
						</div>
					</section>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	                <button type="button" id="saveButton" class="btn btn-primary">保存</button>
	            </div>
            </form>
        </div>
    </div>
</div>

<script type="application/javascript">

    /** 校验字段  **/
    var validator = $('#coreForm').validate({
        rules: {
        	accessnumber : {
        		required : true,
        		digits : true
        	},
        	endaccessnumber : {
        		required : true,
        		digits : true
        	},
        	concurrency : {
        		required : true,
        		digits : true
        	}
        },
        errorPlacement: function (error, element) {
            error.insertAfter(element.parent());
        }
    });

    $('#accessnumberAddAllSaveModel').modal("show");
    
    $('#accessnumberAddAllSaveModel').on('hide.bs.modal', function(e){
		$("#accessnumberAddAllSaveModel").remove();
	});

    $('#saveButton').click(function () {
        if($('#coreForm').valid()) {
            $('#saveButton').attr("disabled", true);
            var startnumber = $('input[name="accessnumber"]').val();
            var endnumber = $('input[name="endaccessnumber"]').val();
            var number = endnumber - startnumber;
            if(number > 0){
            	$('#num').attr("value", number + 1);
            	$('#coreForm').submit();
            }else{
            	alert("起始接入号必须小于结束接入号!");
            	$('#saveButton').attr("disabled", false);
            }
        }
    });

    $('#coreForm').ajaxForm({
        dataType: "json",
        type: "post",
        success: function (data) {
            $("#accessnumberAddAllSaveModel").modal("hide");
            if (data.success) {
                $.smallBox({
                    title: "操作成功",
                    content: "<i class='fa fa-clock-o'></i> <i>分机已成功保存...</i>",
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
                $("#accessnumberAddAllSaveModel").modal("hide");
                $.smallBox({
                    title: "操作失败",
                    content: "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作出现异常...</i>",
                    color: "#C46A69",
                    iconSmall: "fa fa-times fa-2x fadeInRight animated"
                });
            }
        }
    });
</script>
