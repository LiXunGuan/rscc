<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/css/metroStyle/metroStyle.css" type="text/css">
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/select2/select2.min.js"></script>
<div class="modal fade" id="directorySave" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    <strong>关闭</strong>
                </button>
                <h4 class="modal-title" id="myModalLabel">知识目录管理</h4>
            </div>
            <div class="modal-body">
                <form action="${springMacroRequestContext.contextPath}/knowledge/directory/save" class="smart-form" id="directoryForm">
                	<fieldset style="padding-top: 2px;">
                    <input type="hidden" id="uid" name="uid" value="${(item.uid)!''}">
                    <section>
                        <div class="row">
                            <label class="label col col-2"> 目录名： </label>
                            <label class="input col col-8">
                            	<input id="directoryName" name="directoryName" value="${(item.directoryName)!''}"  class="required"/>
                            </label>
                        </div>
                    </section>
                     <section>
                        <div class="row">
                            <label class="label col col-2"> 父目录： </label>
                            <label class="select col col-8">
                            	<select id="directoryParentUUid" name="directoryParentUUid">
									<option value="" selected="selected">------请选择-----</option>
								</select>
                            </label>
                        </div>
                    </section>
                    <section>
                        <div class="row">
                            <label class="label col col-2"> 备注： </label>
                            <label class="input col col-8">
                            	<input id="directoryRemark" name="directoryRemark" value="${(item.directoryRemark)!''}"/>
                            </label>
                        </div>
                    </section>
                    </fieldset>
	            </form>
            </div>
            <div id="dtree" class="ztree"></div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" id="directorySaveButton" class="btn btn-primary">保存 </button>
            </div>
        </div>
    </div>
</div>

<script src="${springMacroRequestContext.contextPath}/assets/js/plugin/select2/select2.min.js"></script>
<script type="application/javascript">

$(document).ready(function() {
    
    $('#directorySave').modal("show");
    
    /* 关闭窗口的回调函数,hide是关闭调用后在关闭    */
    $('#directorySave').on('hide.bs.modal', function(e){
		$("#directorySave").remove();
	});
    
    $('#dtree').hide();
    
    var setting = {
       check: {
           enable: true
       },
       data: {
           simpleData: {
               enable: true
           }
       }
    };
    
    <#if parentDirectorys??>
		var zNodes =${parentDirectorys};
		dtreeObj = $.fn.zTree.init($("#dtree"), setting, zNodes);
		if($("#directoryParentUUid").length>0) {
			$("#directoryParentUUid").empty();
			$("#directoryParentUUid").append('<option value="">请选择</option>')
			nodesToOption(dtreeObj.getNodes(),$("#directoryParentUUid"));
			$("#directoryParentUUid").val("${(item.directoryParentUUid)!''}").trigger("change");
		}
	</#if>
	
	function nodesToOption(nodes,dom) {
    	for(var i in nodes) {
    		if($("[name=uid]").val()==nodes[i].id)
    			continue;
        	var option = $('<option></option>');
        	option.val(nodes[i].id).text(new Array(nodes[i].level * 4).join("-") + nodes[i].name);
        	dom.append(option);
        	nodesToOption(nodes[i].children,dom);
    	}
    }
    
    $('#directorySaveButton').click(function () {
        if ($('#directoryForm').valid()) {
            $('#directorySaveButton').attr("disabled", true);
            $('#directoryForm').submit();
        }
    });
    
    /** 校验字段  **/
    var validator = $('#directoryForm').validate({
        rules: {
        	directoryName : {
        		remote : {
					type : 'post',
					url : getContext()+ 'knowledge/directory/checkDirectory',
					data : {
						uid : function() {
							return $("#uid").val();
						},
						directoryName : function() {
							return $("#directoryName").val();
						}
					}
				}
        	}
        },messages : {
        	directoryName : {
        		required : "请输入目录名!",
        		remote : "此目录名已经存在！"
        	}
		},
        errorPlacement: function (error, element) {
            error.insertAfter(element.parent());
        }
    });

    $('#directoryForm').ajaxForm({
        dataType: "json",
        type: "post",
        success: function (data) {
            $("#directorySave").modal("hide");
            if (data.success) {
                $.smallBox({
                    title: "操作成功",
                    content: "<i class='fa fa-clock-o'></i> <i>知识已成功保存...</i>",
                    color: "#659265",
                    iconSmall: "fa fa-check fa-2x fadeInRight animated",
                    timeout: 2000
                });
                $('#dt_basic_knowledgeDirectory').DataTable().ajax.reload(null,false);
            };
        },
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function () {
                    $("#directoryForm").addClass('submited');
                }
            });
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            if (textStatus == 'error') {
                $("#directorySave").modal("hide");
                $.smallBox({
                    title: "操作失败",
                    content: "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作出现异常...</i>",
                    color: "#C46A69",
                    iconSmall: "fa fa-times fa-2x fadeInRight animated"
                });
                $('#dt_basic_knowledgeDirectory').DataTable().ajax.reload(null,false);
            }
        }
    });
    
});

$('#directoryParentUUid').select2({
	allowClear : true,
	width : '99%'
});

function getopt(){
	var ss = directoryParentUUid.options[directoryParentUUid.selectedIndex].parentNode.attributes;
	alert(ss["label"].value);
}

</script>
