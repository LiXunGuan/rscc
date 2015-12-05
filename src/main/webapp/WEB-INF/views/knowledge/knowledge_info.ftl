<script src= "${springMacroRequestContext.contextPath}/assets/js/plugin/ckeditor/ckeditor.js"></script>
<style>
	.diys{
		width: 23%;
		height: 100%;
		float: left;
		padding: 20px 5px;
		z-index: 1;
		margin-bottom: 14%;
	}
	
	.contents{
		width: 76%;
		height: 100%;
		display: inline-block;
		vertical-align: top;
		margin-left: 10px;
	}
	
	.smart-form .label {display: inline-block;}
	.tag.label.label-info {color: white;}
	
</style>
<div id="knowledgeDiv"></div>
<div style="">
	<section id="widget-grid-kinfo" class="">
		<div class="row">
			<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<div class="jarviswidget jarviswidget-color-darken" data-widget-editbutton="false">
					<header>
						<span class="widget-icon"><i class="fa fa-table"></i></span>
						<h2>知识库</h2>
					</header>
					<div>
						<div>
							<div class="diys" id="diys">
<!-- 								<div class="tree btn btn-info" onclick="showTree('#tree');" style="margin-left: 10px;margin-top: 8px;">所属目录</div> -->
								<div id="tree" class="ztree" style="text-align:left;margin-left: 20px;"></div>
							</div>
							<div class="contents">
								<form action="${springMacroRequestContext.contextPath}/knowledge/knowledge/save"  method="post" class="smart-form"  id="knowledgeForm">
			                		<fieldset style="padding-top: 20px;">
					                     <input type="hidden" id="uid" name="uid" />
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
														<input class="form-control tagsinput" style="color: white;" id="knowledgeTags" name="knowledgeTags"  data-role="tagsinput" >
													</#if>
												</div>
					                        </div>
					                    </section>
					                    <section>
					                        <div class="row">
					                            <label class="label col col-1"> 标题： </label>
					                            <label class="input col col-6">
					                            	<input id="knowledgeTitle" name="knowledgeTitle"  class="required" />
					                            </label>
					                        </div>
					                    </section>
					                    <section>
					                        <div class="row">
					                            <label class="label col col-1"> 内容： </label>
					                            <label class="textarea col col-9"> 
													<textarea id="knowledgeContent" name="knowledgeContent" ></textarea>
													<script>
														CKEDITOR.replace('knowledgeContent');
													</script>
												</label>
					                        </div>
					                    </section>
			                    	</fieldset>
			            		</form>
			            		<div style="margin: 15px;">
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

<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/css/metroStyle/metroStyle.css" type="text/css">
<!-- <link rel="stylesheet" href="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"> -->
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.exedit-3.5.js"></script>
<!-- <script src= "${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-tags/bootstrap-tagsinput.min.js"></script> -->

<script type="text/javascript">
	
	var setting = {
		view:{
			selectedMulti: false,//是否允许同时选中多个节点
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
        		parent:true,
        		leaf: true //子节点始终保持isParent = false
        	},
            simpleData: {
                enable: true,//使用简单数据模式
                idKey: "id",
    			pIdKey: "pId",
    			rootPId: 0
            }
        },
        callback:{
        	onClick: onClick,
        	onCheck: onCheck,
        	beforeRemove: beforeRemove,
			beforeRename: beforeRename,
			onRemove: onRemove,
			beforeCheck: beforeCheck
        },
        edit:{
        	enable: true,
        	showRemoveBtn: showRemoveBtn,
            showRenameBtn: showRenameBtn,
            removeTitle: "删除",
            renameTitle: "编辑"
        }
    };
	
	/* 标签内容失去焦点时    */
// 	var knowTags = $('#knowledgeTags').tagsinput('input');
// 	knowTags.bind("blur",function(){
// 	      $('#knowledgeTags').tagsinput('add', knowTags.val());
// 	      $(this).val("");
// 	      $(this).css("background","white");
// 	});
	
	function showTree(o) {
		$(o).toggle();
	}
	
	function getPathText(node){
	   var s = node.name;
	   while(node=node.getParentNode())s=s+','+node.name;
	   return s;
	}
	
	//声明变量存储标签和标题 
	var oldftags="",oldtitles="";
	function onCheck(e, treeId, treeNode){
		$("#knowledgeTitle").focus();
		var editor = CKEDITOR.instances.knowledgeContent;
		if(!treeNode.isParent){
			var kuid = treeNode.id;
			var kurl = getContext() + "knowledge/knowledge/getKnowledge";
			$.post(kurl, {uid:kuid}, function(data){
				if(data.success){
					if(data.tagsStr != ""){
						var tags = data.tagsStr.substring(1,data.tagsStr.length-1);
						$('#knowledgeTags').tagsinput('add', tags);
						oldftags = $('#knowledgeTags').val();
					};
					editor.setData(data.cont,function(){
						editor.resetDirty();//编辑器的回调函数
					});
				}
			},"json");
			
			$('#directory').val(treeNode.getParentNode().name);
			$('#directoryUUid').val(treeNode.getParentNode().id);
			$("#knowledgeTitle").val(treeNode.name);
			$('#uid').val(treeNode.id);
		}else{
			editor.setData("",function(){
				editor.resetDirty();//编辑器的回调函数
			});
			$('#directory').val(treeNode.name);
			$('#directoryUUid').val(treeNode.id);
			$("#knowledgeTitle").val("");
			$('#knowledgeContent').val("");
			$('#uid').val("");
			oldftags = "";
		}
		
		oldtitles = $("#knowledgeTitle").val();
	}
	
	function onClick(e, treeId, treeNode) {
		var editor = CKEDITOR.instances.knowledgeContent;
		if(oldftags != undefined && ($('#knowledgeTags').val() != oldftags || $("#knowledgeTitle").val() != oldtitles || editor.checkDirty())){
			if(confirm("要保存当前正在编辑的内容？")){
				return false;
			}
		}
		
		var zTree = $.fn.zTree.getZTreeObj("tree");
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		return false;
	}
	
	function beforeCheck(treeId, treeNode){
		//清空标签信息
		$('#knowledgeTags').tagsinput('removeAll');
		return true;
	}
	
	function beforeRemove(treeId, treeNode){
		if(treeNode.id == 'ede001'){
			alert("默认目录暂不能删除!");
	    	return false;
		}
		if(treeNode.isParent){//删除目录信息
			if(confirm("目录[" + treeNode.name + "]包含的信息将全部被删除,确认删除吗？")){
				$('#directory').val("");
				$('#knowledgeTags').tagsinput('removeAll');
				$("#knowledgeTitle").val("");
				$('#knowledgeContent').val("");
				return true;
			};
			return false;
		}else{
			if(confirm("确认删除这条知识吗？")){
				$('#directory').val("");
				$('#knowledgeTags').tagsinput('removeAll');
				$("#knowledgeTitle").val("");
				$('#knowledgeContent').val("");
				return true;
			};
			return false;
		}
	}
	
	function onRemove(e, treeId, treeNode) {
		var type;
		var uid;
		var zTree = $.fn.zTree.getZTreeObj("tree");
		var editor = CKEDITOR.instances.knowledgeContent;
		if(treeNode.isParent){
			type = "directory";
		}else{
			type = "knowledge";
			$('#directory').val("");
			$("#knowledgeTitle").val("");
			editor.setData("");//清空内容信息
		};
		uid = treeNode.id;
		var url = getContext() + "knowledge/knowledge/deleteTree";
		$.post(url, {type:type,uid:uid}, function(data){
			if(data.success){
				zTree.reAsyncChildNodes(null,"refresh");
				oldftags = undefined;
			}
		},"json");
	}
	
	function beforeRename(treeId, treeNode, newName) {
		var zTree = $.fn.zTree.getZTreeObj("tree");
		//判断节点名称
		if (newName.length == 0) {
			alert("节点名称不能为空.");
			setTimeout(function(){zTree.editName(treeNode)}, 10);
			return false;
		}else{
			if(treeNode.name != newName){
				var childNodes = treeNode.getParentNode().children;
				for(var o in childNodes){
					if(childNodes[o].name == newName){
						alert("该目录下已存在此节点名!");
						setTimeout(function(){zTree.editName(treeNode)}, 10);
						return false;
					}
				}
			};
			var type;//操作类型
			var pid;//父节点
			var uid;//当前节点的uid 
			if(treeNode.isParent){
				type = "directory";
				//更新目录框中的值
				$('#directory').val(newName);
				//更新目录的uid
				$('#directoryUUid').val(treeNode.id);
			}else{
				type = "knowledge";
				//更新目录框中的值
				$('#directory').val(treeNode.getParentNode().name);
				//更新目录的uid
				$('#directoryUUid').val(treeNode.getParentNode().id);
				//更新标题信息
				$("#knowledgeTitle").val(newName);
			};
			if(treeNode.getParentNode() != null){//判断父节点是否为空 
				pid = treeNode.getParentNode().id;
			}else{
				pid = "";
			}
			uid = treeNode.id;
			var url = getContext() + "knowledge/knowledge/saveTree";
			$.post(url, {name:newName,type:type,pid:pid,uid:uid}, function(data){
				if(data.success){
					//跟新节点的id
					treeNode.id = data.uid;
					//清空标签信息
					$('#knowledgeTags').tagsinput('removeAll');
					if(data.type == "knowledge"){
						//保存知识的uid以便修改时用
						$('#uid').val(data.uid);
						//添加标签信息和内容信息
						if(data.tagsStr != ""){
							$('#knowledgeTags').tagsinput('add',data.tagsStr.substring(1,data.tagsStr.length-1));
						}
						//更新内容的信息
						$('#knowledgeContent').val(data.content);
					}else if(data.type == "directory"){
						//清空标题
						$("#knowledgeTitle").val("");
						//清空内容
						$('#knowledgeContent').val("");
						//更新目录的uuid
						$('#directoryUUid').val(data.uid);
					}
					
					oldftags = undefined;
				}
			},"json");
			return true;
		}
	}
	
	var newCount = 1;
	function add(e) {
		var zTree = $.fn.zTree.getZTreeObj("tree"),
		isParent = e.data.isParent,
		nodes = zTree.getSelectedNodes(),
		treeNode = nodes[0];
		if(treeNode) {
			treeNode = zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, isParent:isParent, name:"新知识" + (newCount++)});
		}else {
			treeNode = zTree.addNodes(null, {id:(100 + newCount), pId:0, isParent:isParent, name:"新目录" + (newCount++)});
		}
		if(treeNode) {
			zTree.editName(treeNode[0]);
		}else{
			alert("请选择目录进行添加知识！");
		}
	};
	
	function edit() {
		var zTree = $.fn.zTree.getZTreeObj("tree"),
		nodes = zTree.getSelectedNodes(),
		treeNode = nodes[0];
		if (nodes.length == 0) {
			alert("请先选择一个节点");
			return;
		}
		zTree.editName(treeNode);
	};
	
	function remove(e) {
		var zTree = $.fn.zTree.getZTreeObj("tree"),
		nodes = zTree.getSelectedNodes(),
		treeNode = nodes[0];
		if (nodes.length == 0) {
			alert("请先选择一个节点");
			return;
		}
		var callbackFlag = $("#callbackTrigger").attr("checked");
		zTree.removeNode(treeNode, callbackFlag);
	};
	
	function clearChildren(e) {
		var zTree = $.fn.zTree.getZTreeObj("tree"),
		nodes = zTree.getSelectedNodes(),
		treeNode = nodes[0];
		if (nodes.length == 0 || !nodes[0].isParent) {
			alert("请先选择一个父节点");
			return;
		}
		zTree.removeChildNodes(treeNode);
	};
	
	var count = 1;
	function addHoverDom(treeId, treeNode) {
// 	    if(treeNode.id == 'ede001')
// 	    	return;
	    
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
	        zTree.addNodes(treeNode, {id:(100 + count), pId:treeNode.id, name:"子目录" + (count++), isParent:true});
	        zTree.editName(zTree.getNodeByParam("id",100+count-1,null));
	        return false;
	    });
	    
	    var kno = $("#addKno_"+treeNode.tId);
	    if (kno) kno.bind("click", function(){
	        var zTree = $.fn.zTree.getZTreeObj("tree");
	        zTree.addNodes(treeNode, {id:(100 + count), pId:treeNode.id, name:"新知识" + (count++)});
	        zTree.editName(zTree.getNodeByParam("id",100+count-1,null));
	        return false;
	    });
	};
	
	function removeHoverDom(treeId, treeNode) {
	    $("#addBtn_"+treeNode.tId).unbind().remove();
	    $("#addKno_"+treeNode.tId).unbind().remove();
	};
	
	function showRemoveBtn(treeId, treeNode){
		if(treeNode.id == 'ede001'){
	    	return false;
		}
		return true;
	}
	
	function showRenameBtn(treeId, treeNode){
		if(treeNode.id == 'ede001'){
	    	return false;
		}
		return true;
	}
	    
	window.onbeforeunload = function(){
		return "是否放弃保存当前正在编辑的内容？";
	}
	
	$(document).ready(function() {
		
// 		var tags = document.getElementsByTagName("*"),
// 	    count = tags.length, time, ret = {}, id;
// 	    time = new Date();
// 	    for(var i = 0; i < count; i++){
// 	        id = tags[i].id;
// 	        if(id){
// 	            if(ret[id]){
// 	                alert(id + "/n用时：" + (new Date() - time));
// 	            }else{
// 	                ret[id] = true;
// 	            }
// 	        }
// 	    }
// 	    alert("未找到相同ID");

		
		//初始化树
		var zNodes = ${directoryTree};
	    var treeObj = $.fn.zTree.init($("#tree"), setting, zNodes);
	    
	    //提交表单
	    $('#knowledgeSaveButton').click(function () {
	        if ($('#knowledgeForm').valid()) {
	        	//判断目录是否有值
	        	var dir = $('#directory').val();
	        	if(dir == ""){
	        		alert("请选择目录！");
	        		return false;
	        	};
	        	//获取知识n内容 
	        	var editor = CKEDITOR.instances.knowledgeContent;
				if(editor.getData() == ""){
					alert("请填写知识内容！");
					return false;
				};
// 				var result = knows.replace(/</p>/g,"");
				//给知识内容赋值
				$('#knowledgeContent').val(editor.getData());
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
	    
	    $('#knowledgeForm').ajaxForm({
	        dataType: "json",
	        type: "post",
	        success: function (data) {
	            if (data.success) {
	                $.smallBox({
	                    title: "操作成功",
	                    content: "<i class='fa fa-clock-o'></i> <i>知识已成功保存...</i>",
	                    color: "#659265",
	                    iconSmall: "fa fa-check fa-2x fadeInRight animated",
	                    timeout: 2000
	                });
// 	                $('#directory').val("");
// 	    			$('#directoryUUid').val("");
// 	    			$("#knowledgeTitle").val("");
// 	    			$('#uid').val("");
// 	    			$('#knowledgeContent').val("");
// 	    			//清空标签信息
// 	    			$('#knowledgeTags').tagsinput('removeAll');
	    			
	    			//刷新(初始化)zTree
	    			var dts = data.dts;
	    			$.fn.zTree.init($("#tree"), setting, dts);
	    			
	    			oldftags = undefined;
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