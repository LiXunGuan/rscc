<div class="modal fade" id="saveModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 600px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">邀请会议人员</h4>
            </div>
            <div class="modal-body">
                <form action="${springMacroRequestContext.contextPath}/config/accessnumber/saveAll" class="smart-form" id="coreForm">


                    <section>
                        <div class="row">
                            <label class="label col col-3" style="margin-left:20px;">接入号</label>
                            <label class="select col col-6">
                                <select id="accessnumber" name="accessnumber">
                                <#if accessNumberls??>
                                    <#list accessNumberls as a>
                                        <#if accessnumber??>
                                            <#if a==accessnumber>
                                                <option value="${a}" selected="selected">${a}</option>
                                            <#else>
                                                <option value="${a}">${a}</option>
                                            </#if>
                                        <#else>
                                            <option value="${a}">${a}</option>
                                        </#if>
                                    </#list>
                                </#if>
                                </select>
                            </label>
                        </div>
                    </section>




                    <section>
                        <div class="row">
                            <label class="label col col-3" style="margin-left:20px;">分机</label>
                            <label class="input col col-8">
                            	<input id="numbers" name="numbers" value="" class="required"/>
                            </label>
                        </div>
                        <div class="row">
                        	<label class="label col col-8" style="margin-left:20px;">使用,分割可以邀请多个人进入</label>
                        </div>
                    </section>
	            </form>

	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	                <button type="button" id="saveButton" class="btn btn-primary">保存</button>
	            </div>
        </div>
    </div>
</div>

<script type="application/javascript">


    $('#accessnumber').select2({
        allowClear : true,
        width:'99%'
    });

    $('#saveModel').modal("show");

    $('#saveButton').click(function () {
        if($('#coreForm').valid()) {


            var numbers=$("#numbers").val();
            var accessnumber=$("#accessnumber").val();
            var conference_id=$("#confrenceID").val()
            $.post(getContext() + "confrence/invitation",{confrenceId:conference_id,num:numbers,accessnumber:accessnumber},function(data){

                $("#saveModel").modal("hide");

                if(data.success){
                    $.smallBox({
                        title : "操作成功",
                        content : "<i class='fa fa-clock-o'></i> <i>邀请成功...</i>",
                        color : "#659265",
                        iconSmall : "fa fa-check fa-2x fadeInRight animated",
                        timeout : 2000
                    });
                    $('#confrenceTable').DataTable().ajax.reload(null,false);
                }else{
                    $.smallBox({
                        title : "操作失败",
                        content : "<i class='fa fa-clock-o'></i> <i>邀请失败...</i>",
                        color : "#C46A69",
                        iconSmall : "fa fa-times fa-2x fadeInRight animated",
                        timeout : 2000
                    });
                }
            },"json").error(function(){
                $.smallBox({
                    title : "操作失败",
                    content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作失败...</i>",
                    color : "#C46A69",
                    iconSmall : "fa fa-times fa-2x fadeInRight animated"
                });
            });
        }
        
       
    });
    
    $('#saveModel').on('hide.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
		$("#saveModel").remove();
	});
    
    $('#coreForm').ajaxForm({
        dataType: "json",
        type: "post",
        success: function (data) {
            $("#saveModel").modal("hide");
            if (data.success) {
                $.smallBox({
                    title: "操作成功",
                    content: "<i class='fa fa-clock-o'></i> <i>分机已成功保存...</i>",
                    color: "#659265",
                    iconSmall: "fa fa-check fa-2x fadeInRight animated",
                    timeout: 2000
                });
                $('#confrenceTable').DataTable().ajax.reload(null,false);
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
                $("#saveModel").modal("hide");
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
