<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/css/metroStyle/metroStyle.css" type="text/css">
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.exedit-3.5.js"></script>
<script src= "${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-tags/bootstrap-tagsinput.min.js"></script>
<script src= "${springMacroRequestContext.contextPath}/assets/js/jqPaginator.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/select2/select2.min.js"></script>
<style>
	.dirs{
		width: 20%;
		padding-left: 3%;
		margin-bottom: 16%;
	}
	
	.contents{
		width: 79%;
		display: inline-block;
		vertical-align: top;
	}
	
	#search{
 		padding-top: 15px;
	}
	
	#knowInfo{
	}
	
	.search-results.clearfix:hover{
		background-color:#DDDDDD;
	}
	
	.smart-form .label {display: inline-block;}
	.tag.label.label-info {color: white;}
</style>

<div id="knowInfo">
	<div id="search">
		<div class="input-group" style="float: left;">
			<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" id="checkinfo" value="dirs">
				&nbsp;目&nbsp;&nbsp;录&nbsp;<span class="caret"></span>
			</button>
			<ul class="dropdown-menu">
				<li class="active">
					<a href="javascript:void(0)" id="dirs" onclick="getcheckinfo('&nbsp;目&nbsp;&nbsp;录&nbsp;','dirs')">&nbsp;目&nbsp;&nbsp;录&nbsp;<i class="fa fa-check"></i></a>
				</li>
				<li>
					<a href="javascript:void(0)" id="tags" onclick="getcheckinfo('&nbsp;标&nbsp;&nbsp;签&nbsp;','tags')">&nbsp;标&nbsp;&nbsp;签&nbsp;</a>
				</li>
			</ul>
		</div>
		<input type="text" name="ktitle" class="form-control" style="width: 20%;float: left;" id="ktitle" value="" placeholder="匹配知识、目录信息.."/>
		<button class="btn btn-default btn-primary" type="button" onclick="getResult();">
			<i class="fa fa-search"></i> 搜索
		</button>
	</div>
	<div style="margin-top: 7px;font-size: 10px;color: #999;margin-left: 3px;" id="result"></div>
	<hr>
	<div class="row">
		<div class="dirs col-md-4">
			<!--<div class="tree btn btn-info" onclick="showTree('#tree');" style="margin-left: 10px;">所属目录</div> -->
			<div id="ktree" class="ztree" style="text-align:left;overflow-x: auto;overflow-y: auto;"></div>
		</div>
		<div class="contents">
			<div id="kmsg">
				<div id="kt" style="font-size: x-large;"></div>
				<div id="kdir"></div>
				<div id="klabels"></div>
				<div id="kcontent" style="overflow-y: auto;white-space: pre-wrap;max-height: 500px;"></div>
			</div>
			<div id="info">
				 <div class="search-results clearfix" style="padding: 0px;">
					<h4><a href="javascript:void(0);"><span class="title" id="" onclick="tclick(this);"></span></a></h4>
					<div class="url text-success">
						<span class="dir" style="font-size: small;"></span>
					</div>
					<p class="note">
						<span class="labels" style="font-size: small;"></span>
					</p>
					<p class="description" style="height: 35px;text-overflow : ellipsis;overflow: hidden;">
						<span class="resultContent" style="white-space: pre-wrap;"></span>
					</p>
				</div>
			</div>
			<div id="page">
	    		<ul class="pagination" id="pagination1"></ul>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">

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
		
		
		$("#result").hide();
		$("#info").hide();
		$("#page").hide();
		var setting = {
	        data: {
	            simpleData: {
	                enable: true
	            }
	        },
	        callback:{
	        	onClick: onTreeClick,
	        	onCheck: onCheck
	        }
	    };
		//初始化树结构 
		var zNodes = ${directoryTree};
	    var treeObj = $.fn.zTree.init($("#ktree"), setting, zNodes);
	    //默认选中树第一条知识并触发它的点击事件 
	    var n = treeObj.getNodeByParam("isParent",false);
	    treeObj.selectNode(n,false);
	    onTreeClick(null,null,n);
	});
	
	//点击筛选类型
	function getcheckinfo(str,type){
		//更新查询类型的值
		$("#checkinfo").val(type); 
		$("input[id='ktitle']").removeAttr("placeholder");
		if(type == "tags"){
			$("#ktitle").attr("placeholder","匹配知识、标签信息..");
		}else if(type == "dirs"){
			$("#ktitle").attr("placeholder","匹配知识、目录信息..");
		}
		$("#checkinfo").text(str).append('&nbsp;&nbsp;<span class="caret"></span>');
		//删除之前选中的class属性
		$("li[class=active]").removeAttr("class");
		//为新选中的数据类型添加样式
		$("#"+type).parent().attr("class","active");
		//删除之前选中的样式
		$(".fa-check").remove();
		//为新选中的数据类型添加选中的样式
		$("#"+type).text(str).append('&nbsp;&nbsp;&nbsp;<i class="fa fa-check"></i>');
	}
	
	/* 点击知识标题时的事件   */
	function tclick(val){
		var zTree = $.fn.zTree.getZTreeObj("ktree");
		var node = zTree.getNodeByParam("id",val.id);
		$("#info").hide();
		onTreeClick(null,null,node);
	}
	
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
	
	/* 点击节点时获取所有的父目录  */
	function checkAllParents(treeNode){
		if(treeNode.pId == null) {
			return treeNode.name;
		}else{
			return checkAllParents(treeNode.getParentNode()) + " > " + treeNode.name;
		}
	}
	
	/* 点击节点时获取所有的子节点(使用过后要清除k和result) */
	var k = 0;
	var result = [];
	function checkAllChildrens(treeNode){
		if(treeNode.isParent){
			var childs = treeNode.children;
			if(childs){
				for(var i = 0; i < childs.length; i++){
					if(childs[i].isParent){
						result = checkAllChildrens(childs[i], result);
					}else{
						result[k] = childs[i];
						k++;
					}
				}
			}
		}
		return result;
	}
	
	/* 点击树上的目录和知识信息时    */
	var infonode = {};
	function onTreeClick(e, treeId, treeNode, operation) {
		if(treeNode) {
			/* 判断是知识还是目录  */
			if(!treeNode.isParent && operation != "dirSearch" && operation != "knoSearch"){
				$("#kmsg").show();/* 显示单条知识信息的div */
				$("#info").hide();/* 隐藏知识查询结果信息的总体的div */
				$("#page").hide();/* 隐藏分页信息   */
				$('#kt').text(treeNode.name);
				$('#kdir').text("目录：" + checkAllParents(treeNode));
				var kuid = treeNode.id;
				var kurl = getContext() + "knowledge/knowledge/getKnowledge";
				$.post(kurl, {uid:kuid}, function(data){
					if(data.success){
						var tags = data.tagsStr.substring(1,data.tagsStr.length-1);
						$('#klabels').text("标签：" + tags);
						$('#kcontent').empty();
						$('#kcontent').append($(data.cont));
						//$('#kcontent').text(data.cont);
					}
				},"json");
			}else{
				$("#kmsg").hide();/* 隐藏显示单条知识信息的div */
				$("#info").show();/* 显示知识查询结果信息的总体的div */
				$("#page").hide();/* 隐藏分页栏信息   */
				$(".search-results.clearfix").show();/* 显示查询后单条信息的div */
				if(operation == "dirSearch"){
					var nodes = [];//存放全部的子节点信息 
					nodes = checkAllChildrens(treeNode);
					if(nodes != ""){
						for(var j = 0;j < nodes.length; j++){
							onkclick(null,nodes[j].tId,nodes[j]);
							infonode[nodes[j].tId] = $(".search-results.clearfix").clone();
						}
						// 清空存放的数据 
						k = 0;
						result = [];
					}
				}else if(operation == "knoSearch"){
					onkclick(null, treeId, treeNode);
					infonode[treeId] = $(".search-results.clearfix").clone();
				}else{
					var cnodes = [];//存放全部的子节点信息 
					cnodes = checkAllChildrens(treeNode);
					var childsnodes = [];
					if(cnodes != ""){
						for(var j = 0;j < cnodes.length; j++){
							onkclick(null, cnodes[j].tId, cnodes[j]);
							childsnodes[cnodes[j].tId] = $(".search-results.clearfix").clone();
						}
						$("#info").empty();
						for(var i in childsnodes){
							$("#info").append(childsnodes[i]);
						}
						// 清空存放的数据 
						k = 0;
						result = [];
					}
				}
			}
			var zTree = $.fn.zTree.getZTreeObj("ktree");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		}
	}
	
	/* 构建查询后的单条知识信息   */
	function onkclick(e, treeId, treeNode){
		$(".search-results.clearfix").show();
		$(".search-results.clearfix").eq(0).nextAll().remove();
		if(!treeNode.isParent){
			$('.title').text(treeNode.name);
			$('.title').attr("id",treeNode.id);
			$('.note').show();
			$('.dir').text("目录：" + checkAllParents(treeNode));
			var tags = treeNode.tagsStr.substring(1,treeNode.tagsStr.length-1);
			$('.labels').text("标签：" + tags);
			$('.resultContent').empty();
			$('.resultContent').append($(treeNode.content).text());
		}
	}
	
	//jquery的扩展方法,获取一个对象的长度 
	$.extend({
		 // 获取对象的长度，需要指定上下文 this
		 Object: {
			 count: function( p ) {
			 	p = p || false;
				return $.map( this, function(o) {
				 	if( !p ) return o;
				 	return true;
				}).length;
			 }
		 }
	});
	
	var zTree = $.fn.zTree.getZTreeObj("ktree");
	var infonodes = {};
	/* 点击查询  */
	function getResult(){
		$("#kmsg").hide();/* 隐藏显示单条知识信息的div */
		$("#info").show();/* 显示知识查询结果信息的总体的div */
		$("#page").show();/* 显示分页信息   */
		$("#result").show();/* 显示搜索结果个数的信息   */
		$(".search-results.clearfix").show();/* 显示查询后单条信息的div */
		var title = $('#ktitle').val(); /* 获取筛选调价  */
		var checktype = $("#checkinfo").val(); /* 获取筛选类型的值   */
		var url = getContext() + "knowledge/knowledge/search";
		$.post(url,{title:title,checktype:checktype},function(data){
			if(data.success){
				if(data.type == "dir"){  /* 目录和知识查询   */
					infonode = {};       /* 清空之前查询的的存储数据   */
					infonodes = {};      /* 清空之前查询的的存储数据   */
					if(data.dirs.length != 0){
						/* 遍历目录信息 */
						$.each(data.dirs,function(k,v){
							//得到目录名对应的节点信息
							var dirnode = zTree.getNodeByParam("id",v.uid);
							onTreeClick(null, null, dirnode, "dirSearch");
						});
					};
					if(data.knos.length != 0){
						/* 遍历知识信息 */
						$.each(data.knos,function(m,n){
							/*  得到知识对应的节点信息 */
							var knonode = zTree.getNodeByParam("id",n.uid);
							onTreeClick(null, knonode.tId, knonode, "knoSearch");
						});
					};
					$("#info").empty();
					var counts = 0;
					for(var i in infonode){
						infonodes[counts] = infonode[i];
						counts++;
					}
					var count = 0;
					for(var i in infonode){
						$("#info").append(infonode[i]);
						count++;
						if(count >= 10 ){
							break;
						}
					}
					//分页
					$("#page").show();
					createPage($.Object.count.call(infonode), "dirsPage");
				}else{ //标签查询
// 					$("#result").text("为您找到相关结果" + data.countRecord + "个");
					var tagsnode = [];
					$.each(data.kns,function(k, v){
						var nodes = zTree.getNodesByParam("id",v.uid,null);
						onkclick(null,nodes[0].tId,nodes[0]);
						tagsnode[k] = $(".search-results.clearfix").clone();//克隆并追加一个元素
					});
	 				$("#info").empty();
					for(var i in tagsnode){
						$("#info").append(tagsnode[i]);
					}
					//分页
					createPage(data.countRecord, "tagsPage");
				}
			}else{
				$("#page").hide();
				$("#info .temp").remove();
				$("#info").append("<p><a class='temp' href='javascript:void(0);'>没有找到相关信息！</a></p>");
				$(".search-results.clearfix").hide();
// 				$("#result").text("为您找到相关结果0个");
			}
		},"json");
	}
	
	//查询数据的分页 
	function createPage(totalcounts, operation) {
		 $.jqPaginator('#pagination1', {
			totalCounts: totalcounts,//分页的总条目数
			pageSize: 10,//每一页的条目数
	        visiblePages: 10,//最多显示的页码数
	        currentPage: 1,//当前页
	        first: '<li class="first"><a href="javascript:;">首页</a></li>',
	        prev: '<li class="prev"><a href="javascript:;">上一页</a></li>',
	        next: '<li class="next"><a href="javascript:;">下一页</a></li>',
	        last: '<li class="last"><a href="javascript:;">末页</a></li>',
	        page: '<li class="page"><a href="javascript:;">{{page}}</a></li>',
	        onPageChange: function (num, type) {
	            if(operation == "tagsPage"){ //标签分页
		        	var title = $('#ktitle').val();
		            var checktype = $("#checkinfo").val();
		            var urll = getContext() + "knowledge/knowledge/search";
		            $.post(urll, {title:title,pageNum:num,checktype:checktype}, function(data){
						if(data.success){
							var node = [];
							var zTree = $.fn.zTree.getZTreeObj("ktree");
							$.each(data.kns,function(k, v){
								var nodes = zTree.getNodesByParam("id",v.uid,null);
								onkclick(null,nodes[0].tId,nodes[0]);
			 					node[k] = $(".search-results.clearfix").clone();//克隆并追加一个元素
							});
			 				$("#info").empty();
							for(var i in node){
								$("#info").append(node[i]);
							}
						}
			  		},"json");
	            }else{  //知识和目录分页
	            	var pageSize = 10;
	            	var pageNum = (num - 1)*pageSize;
	            	var endPageNum = pageNum + pageSize;
	            	$("#info").empty();
	            	for(pageNum; pageNum < totalcounts && pageNum < endPageNum; pageNum++) {
						$("#info").append(infonodes[pageNum]);
	            	}
	            }
	        }
		});
	}
	
</script>