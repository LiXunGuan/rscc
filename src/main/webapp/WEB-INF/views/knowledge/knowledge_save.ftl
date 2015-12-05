<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/css/metroStyle/metroStyle.css" type="text/css">
<!-- <link rel="stylesheet" href="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"> -->
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.exedit-3.5.js"></script>
<script src= "${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-tags/bootstrap-tagsinput.min.js"></script>

<style>
	.smart-form .label {display: inline-block;}
	.tag.label.label-info {color: white;}
</style>

<div class="modal fade" id="knowledgeSave" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 1200px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    <strong>关闭</strong>
                </button>
                <h4 class="modal-title" id="myModalLabel">知识管理</h4>
            </div>
            <div class="modal-body" >
            	<div class="row">
					<section class="col col-8" style="display: inline-block;width: 70%">
						<form action="${springMacroRequestContext.contextPath}/knowledge/knowledge/save" class="smart-form" id="knowledgeForm">
	                		<fieldset style="padding-top: 2px;">
			                     <input type="hidden" id="uid" name="uid" value="${(item.uid)!''}">
			                     <input type="hidden" id="directoryUUid" name="directoryUUid" />
			                     <section>
			                        <div class="row">
			                            <label class="label col col-2">标签：</label>
										<div class="form-group col col-lg-6">
											<#if tagsStr?? && (tagsStr?length) &gt; 0 >
												<input class="form-control tagsinput" style="color: white;" id="knowledgeTags" name="knowledgeTags"  value="${tagsStr?substring(1,tagsStr?length-1)}" data-role="tagsinput">
											<#else>
												<input class="form-control tagsinput" style="color: white;" id="knowledgeTags" name="knowledgeTags"  value="" data-role="tagsinput" >
											</#if>
										</div>
			                        </div>
			                    </section>
			                    <section>
			                        <div class="row">
			                            <label class="label col col-2"> 标题： </label>
			                            <label class="input col col-6">
			                            	<input id="knowledgeTitle" name="knowledgeTitle" value="${(item.knowledgeTitle)!''}"  class="required"/>
			                            </label>
			                        </div>
			                    </section>
			                    <section>
			                        <div class="row">
			                            <label class="label col col-2"> 内容： </label>
			                            <label class="textarea col col-9"> 
											<textarea class="custom-scroll required" rows="15" id="knowledgeContent" name="knowledgeContent">${(item.knowledgeContent)!''}</textarea>
										</label>
			                        </div>
			                        
			                    </section>
	                    	</fieldset>
	            		</form>
					</section>
					<section class="col col-2" style="display: inline-block;vertical-align: top;margin-left: 10px;">
			        	<div>
				            <div class="tree btn btn-info" onclick="showTree('#tree');">所属目录</div> 
			            	<div id="tree" class="ztree" style="text-align:left;margin-left: 20px;"></div> 
						</div>
					</section>
				</div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	                <button type="button" id="knowledgeSaveButton" class="btn btn-primary">保存 </button>
	            </div>
        	</div>
        </div>
   </div>
</div>

<script type="application/javascript">

$(document).ready(function() {
    
	$('#knowledgeSave').modal("show");
	
	var setting = {
		view:{
			 addHoverDom: addHoverDom,
             removeHoverDom: removeHoverDom
		},
        check: {
            enable: true,
            chkStyle:"radio",
            radioType:"all"
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback:{
        	onClick: onClick,
        	onCheck: onCheck
        },
        edit:{
        	 enable: true
        }
    };
	
	<#if directoryTree??>
		var zNodes =${directoryTree};
	    var treeObj = $.fn.zTree.init($("#tree"), setting, zNodes);
	</#if>

    /** 校验字段  **/
    var validator = $('#knowledgeForm').validate({
        rules: {
        },messages : {
        	knowledgeTitle : {
        		required : "请输入标题!"
			},
			knowledgeContent : {
				required : "请输入内容!"
			},
			knowledgeDirectory : {
				required : "请选择所属目录!"
			}
		},
        errorPlacement: function (error, element) {
            error.insertAfter(element.parent());
        }
    });

	<#if item??>
	    <#if directory??>
	    	$("#tree").show();
	    	var cn= treeObj.getNodeByParam("id", "${directory.uid}", null);
            treeObj.checkNode(cn, true, true);
	    </#if>
	<#else>
	    $("#tree").show();
	</#if>

    $('#knowledgeSaveButton').click(function () {
        if ($('#knowledgeForm').valid()) {
            
        	$('#knowledgeSaveButton').attr("disabled", true);
            
        	var nodes = treeObj.getCheckedNodes(true);
        	if(nodes.length < 1){
        		 $.smallBox({
                     title: "保存失败！！",
                     content: "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>请选择知识所属目录！！！</i>",
                     color: "#C46A69",
                     iconSmall: "fa fa-times fa-2x fadeInRight animated",
                     timeout: 2000
                 });
        		$('#knowledgeSaveButton').attr("disabled", false);
        		return false;
        	}
        	for(var i=0;i<nodes.length;i++){
//         		$('#directoryUUid').val(nodes[i].id); //获取选中节点的值
				$('#directoryUUid').val(getPathText(nodes[i]));
            }
            
           $('#knowledgeForm').submit();
        }
    });

    $('#knowledgeForm').ajaxForm({
        dataType: "json",
        type: "post",
        success: function (data) {
            $("#knowledgeSave").modal("hide");
            if (data.success) {
                $.smallBox({
                    title: "操作成功",
                    content: "<i class='fa fa-clock-o'></i> <i>知识已成功保存...</i>",
                    color: "#659265",
                    iconSmall: "fa fa-check fa-2x fadeInRight animated",
                    timeout: 2000
                });
//                 $('#dt_basic_knowledge').DataTable().ajax.reload(null,false);;
            };
        },
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function () {
                    $("#knowledgeForm").addClass('submited');
                }
            });
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            if (textStatus == 'error') {
                $("#knowledgeSave").modal("hide");
                $.smallBox({
                    title: "操作失败",
                    content: "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作出现异常...</i>",
                    color: "#C46A69",
                    iconSmall: "fa fa-times fa-2x fadeInRight animated"
                });
//                 $('#dt_basic_knowledge').DataTable().ajax.reload(null,false);;
            }
        }
    });
});
	
function showTree(o) {
	$(o).toggle();
}

function getPathText(node){
   var s = node.name;
   while(node=node.getParentNode())s=s+','+node.name;
   return s;
}

function onCheck(e, treeId, treeNode){
}

function onClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("tree");
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}

var newCount = 1;
function addHoverDom(treeId, treeNode) {
    var sObj = $("#" + treeNode.tId + "_span");
    if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
        + "' title='添加子节点' onfocus='this.blur();'></span>";
    sObj.after(addStr);
    var btn = $("#addBtn_"+treeNode.tId);
    if (btn) btn.bind("click", function(){
        var zTree = $.fn.zTree.getZTreeObj("tree");
        zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, name:"新节点" + (newCount++)});
        return false;
    });
};
function removeHoverDom(treeId, treeNode) {
    $("#addBtn_"+treeNode.tId).unbind().remove();
};

</script>
