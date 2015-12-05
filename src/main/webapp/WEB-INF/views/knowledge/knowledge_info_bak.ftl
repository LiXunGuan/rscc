<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/css/metroStyle/metroStyle.css" type="text/css">
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.exedit-3.5.js"></script>
<script src= "${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-tags/bootstrap-tagsinput.min.js"></script>
<style>
	.dirs{
		background-color:#DDDDDD;
		width: 20%;
		height: 600px;
		float: left;
		padding: 20px 5px;
		z-index: 1;
		margin-bottom: 10px;
	}
	
	.contents{
		background-color: #DDDDDD;
		width: 79%;
		height: 600px;
		display: inline-block;
		vertical-align: top;
		margin-left: 10px;
	}
	
	.smart-form .label {display: inline-block;}
	.tag.label.label-info {color: white;}
	
</style>
<div id="knowledgeDiv"></div>
<div id="mainContent" style="">
	<section id="widget-grid" class="">
		<div class="row">
			<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<div class="jarviswidget jarviswidget-color-darken" id="wid-id-0" data-widget-editbutton="false">
					<header>
<!-- 						<span class="widget-icon"><i class="fa fa-table"></i></span> -->
<!-- 						<h2></h2> -->
					</header>
					<div>
						<div>
							<div class="dirs" id="dirs">
								 <div class="tree btn btn-info" onclick="showTree('#tree');" style="margin-left: 10px;">所属目录</div>
								 <div id="tree" class="ztree" style="text-align:left;margin-left: 20px;"></div>
							</div>
							<div class="contents">
								<form action="${springMacroRequestContext.contextPath}/knowledge/knowledge/save"  method="post" class="smart-form"  id="knowledgeForm">
			                		<fieldset style="padding-top: 20px;background-color: #DDDDDD;">
					                     <input type="hidden" id="uid" name="uid" value="${(item.uid)!''}">
					                     <input type="hidden" id="directoryUUid" name="directoryUUid" />
					                     <section>
					                        <div class="row">
					                            <label class="label col col-1"> 目录： </label>
					                            <label class="input col col-2">
					                            	<input id=directory name="directory" disabled="disabled" style="background-color: #DDDDDD"/>
					                            </label>
					                        </div>
					                    </section>
					                     <section>
					                        <div class="row">
					                            <label class="label col col-1">标签：</label>
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
					                            <label class="label col col-1"> 标题： </label>
					                            <label class="input col col-6">
					                            	<input id="knowledgeTitle" name="knowledgeTitle" value="${(item.knowledgeTitle)!''}"  class="required"/>
					                            </label>
					                        </div>
					                    </section>
					                    <section>
					                        <div class="row">
					                            <label class="label col col-1"> 内容： </label>
					                            <label class="textarea col col-9"> 
													<textarea class="custom-scroll required" rows="20" id="knowledgeContent" name="knowledgeContent">${(item.knowledgeContent)!''}</textarea>
												</label>
					                        </div>
					                    </section>
			                    	</fieldset>
			            		</form>
			            		<div style="margin-left: 20px;">
							    	<button type="button" id="knowledgeSaveButton" class="btn btn-primary">保存 </button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</article>
		</div>
	</section>
</div>
<script type="text/javascript">
	$(document).ready(function() {
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
	        	keep:{
	        		parent:true
	        	},
	            simpleData: {
	                enable: true
	            }
	        },
	        callback:{
	        	onClick: onClick,
	        	onCheck: onCheck,
	        	beforeRemove: beforeRemove
	        },
	        edit:{
	        	 enable: true
	        }
	    };
		
		var zNodes = ${directoryTree};
	    var treeObj = $.fn.zTree.init($("#tree"), setting, zNodes);
		
	    $('#knowledgeSaveButton').click(function () {
	        if ($('#knowledgeForm').valid()) {
	        	var nodes = treeObj.getCheckedNodes(true);
	        	if(nodes.length < 1){
	        		 $.smallBox({
	                     title: "保存失败！！",
	                     content: "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>请选择知识所属目录！！！</i>",
	                     color: "#C46A69",
	                     iconSmall: "fa fa-times fa-2x fadeInRight animated",
	                     timeout: 2000
	                 });
	        		return false;
	        	}
	        	for(var i=0;i<nodes.length;i++){
					$('#directoryUUid').val(getPathText(nodes[i]));
					alert($('#directoryUUid').val());
	            }
	        	$('#knowledgeForm').submit();
	         }
	    });
	    
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
	})
	
	
	function showTree(o) {
		$(o).toggle();
	}
	
	function getPathText(node){
	   var s = node.name;
	   while(node=node.getParentNode())s=s+','+node.name;
	   return s;
	}
	
	function onCheck(e, treeId, treeNode){
		$('#directory').val(treeNode.name);
		if($('#knowledgeTitle').val() == ""){
			$("#knowledgeTitle").val(treeNode.name);
		}
	}
	
	function onClick(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("tree");
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		return false;
	}
	
	var newCount = 1;
	function addHoverDom(treeId, treeNode) {
	    var sObj = $("#" + treeNode.tId + "_span");
	    
	    if(treeNode.isParent == false)return;
	    if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length > 0) return;
	    if (treeNode.editNameFlag || $("#addKno_"+treeNode.tId).length > 0) return;
	    
	    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='添加子目录' onfocus='this.blur();'></span>";
	    var addKno = "<span class='button add' id='addKno_" + treeNode.tId + "' title='添加新知识' onfocus='this.blur();'></span>";
	    
	    sObj.after(addKno);
	    sObj.after(addStr);
	    
	    var btn = $("#addBtn_"+treeNode.tId);
	    if (btn) btn.bind("click", function(){
	        var zTree = $.fn.zTree.getZTreeObj("tree");
	        zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, name:"子目录" + (newCount++), isParent:true});
	        alert(zTree.getNodesByParam("name","子目录"+newCount-1,treeNode.id));
	        return false;
	    });
	    
	    var kno = $("#addKno_"+treeNode.tId);
	    if (kno) kno.bind("click", function(){
	        var zTree = $.fn.zTree.getZTreeObj("tree");
	        zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, name:"新知识" + (newCount++)});
	        return false;
	    });
	};
	
	function beforeRemove(treeId, treeNode){
		return confirm("确认删除 节点 -- " + treeNode.name + " 吗？");
	}
	
	function removeHoverDom(treeId, treeNode) {
	    $("#addBtn_"+treeNode.tId).unbind().remove();
	    $("#addKno_"+treeNode.tId).unbind().remove();
	};
	
</script>