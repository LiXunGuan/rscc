<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-duallistbox/jquery.bootstrap-duallistbox.min.js"></script>
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/css/metroStyle/metroStyle.css" type="text/css">
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.exhide-3.5.min.js"></script>
<style>

	div.col label.input input
	{
		height:25px;
	}
	
	div.col label.input span
	{
		top: 1px;
		height:23px;
	}
	
	.smart-form .col-md-6
	{
		width: 45%;
		padding-left: 13px;
	 	padding-right: 13px;
	}
	
	.smart-form .col.col-7
	{
		width: 58.3%;
	}
	
	#trees{
		padding-left: 30px;
	}
	
</style>
<div class="modal fade" id="allotDeptDataSave" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 700px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                <h4 class="modal-title">分配数据</h4>
            </div>
            <div class="modal-body">
                <form action="${springMacroRequestContext.contextPath}/deptdata/dept/allotDeptDataSave" class="smart-form" id="allotDataForm" method="post">
					<input type="hidden" name="dataBatchUuid" id="dataBatchUuid" value="${(dataBatchUuid)!''}" />
					<input type="hidden" name=deptUuid id="deptUuid" value="${(deptUuid)!''}" />
					<input type="hidden" name="numLimit" id="numLimit" value="${(numLimit)!''}" />
					<input type="hidden" name="depts" id="depts" />
                	<header style="padding: 0px;">
                		<i class="fa fa-lg fa-volume-up" style="margin-right: 5px;"></i>
						<span>当前批次：${entry.batchName}，共有数据<font color="red"> ${entry.dataCount} </font>条，部门内现有数据<font color="red"> ${(numLimit)!''} </font>条</span><br /><br />
                	</header>
                	<fieldset>
						<section>
							<strong>选择分配方案</strong>
							<div class="row">
								<div class="col col-7">
									<div class="row">
										<div class="col">
											<label class="radio">
												<input type="radio" class="radiobox style-0" name="allocate" value="1" checked="checked">
												<span class="hasInput">给每个部门分配：</span> 
											</label>
										</div>
										<div class="col col-4" id="allocateDataMax" style="padding-left:0px">
											<label class="input">
												<span class="icon-append">条</span>
												<input type="text" name="allocateMax" class="required digits number" min="1">
											</label>
										</div>
									</div>
									<div class="row">
										<div class="col">
											<label class="radio">
												<input type="radio" class="radiobox style-0" name="allocate" value="2">
												<span class="hasInput">给每个部门分配<span style="color:red">至</span>：</span> 
											</label>
										</div>
									</div>
								</div>
							</div>
						</section>
                    </fieldset>
	            </form>
	            <div id="trees" class="row">
	            	<div class="col-md-12">
		            	<strong>管辖部门</strong>
		            	<div class="btn btn-xs btn-primary" onclick="checkAll(true)"><span class="glyphicon glyphicon-ok"></span></div>
	               		<div class="btn btn-xs btn-primary" onclick="checkAll(false)"><span class="glyphicon glyphicon-remove"></span></div>
	               		<div id="dtree" class="ztree" style="overflow-x: auto;overflow-y: auto;width: 100%;height: 100%;"></div>
	               	</div>
	            </div>
	            <div id="msg"><span style="color: red">部门内没有数据,暂不能分配！</span></div>
            </div>
            <div class="modal-footer">
                <button type="button" id="quxiao" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" id="allotDeptDataSaveButton" class="btn btn-primary">分配 </button>
            </div>
        </div>
    </div>
</div>

<script type="application/javascript">

	$(document).ready(function() {
		
		$("#msg").hide();
	    
	    $('#allotDeptDataSave').modal("show");
	    
	    var setting = {
            check: {
                enable: true,
                chkboxType : { "Y" : "", "N" : "" }
//                 chkboxType : { "Y" : "s", "N" : "s" }
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback :{
            	onClick: treeCheckNode,
            	onCheck: onCheck
            }
	    };
	    
	    function treeCheckNode(e, t, n) {
       		$.fn.zTree.getZTreeObj(t).checkNode(n, !n.checked, true, true);
		};
		
	    function onCheck(event, treeId, treeNode) {
	    	var zTree = $.fn.zTree.getZTreeObj(treeId);
	    	var nodes;
	    	if (zTree.getCheckedNodes().length == 0) {
	    		nodes = zTree.getNodesByParam("chkDisabled", true);
		    	for (var i in nodes) {
			    	zTree.setChkDisabled(nodes[i], false);
		    	}
	    	} else {
		    	nodes = zTree.getNodesByFilter(function(node) {
	        		if(node.level != treeNode.level)
	        			return true;
	        	});
		    	for (var i in nodes) {
			    	zTree.setChkDisabled(nodes[i], true);
		    	}
	    	}
		};
		
		
		<#if datarangeTree??>
	    	var zNodes =${datarangeTree};
	    	dtreeObj = $.fn.zTree.init($("#dtree"), setting, zNodes);
		</#if>
	    
		
		/* 关闭窗口的回调函数,hide是关闭调用后在关闭    */
	    $('#allotDeptDataSave').on('hide.bs.modal', function(e){
			$("#allotDeptDataSave").remove();
		});
		
	    var numlimit = Number($('#numLimit').val());
	    if(numlimit <= 0 || Number(${childdeptnum}) <= 1){
	    	if(numlimit > 0){
		    	$("#msg span").text("此部门没有子部门,暂不能分配！");
	    	}
	    	$("#allotDataForm").hide();
	    	$("#trees").hide();
	    	$("#msg").show();
	    	$("#quxiao").text("关闭");
	    	$("#allotDeptDataSaveButton").hide();
	    }
		
	    $('#allotDeptDataSaveButton').click(function () {
	        if($('#allotDataForm').valid()){
	        	var nodes = $.fn.zTree.getZTreeObj("dtree").getCheckedNodes(true);
	        	if(nodes.length <= 0){
	        		alert("请选择要分配的部门！");
            		return false;
	        	}else{
	        		var depts = '';
	        		for(var n in nodes){
	        			depts += nodes[n].id+",";
	        		}
	        		$("#depts").val(depts);
	        	};
	        	$('#allotDeptDataSaveButton').attr("disabled",true);
                $('#allotDataForm').submit();
	        }
	    });
	    
	    /** 校验字段  **/
	    var validator = $('#allotDataForm').validate({
	        rules: {
	        	allotDataNumber : {
	        		required : true,
	        		digits:true,
	        		number:true,
	        		max:numlimit,
	        		min:1
	        	}
	        },messages : {
	        	allotDataNumber : {
	        		required : "请输入分配的数量"
	        	}
			},
	        errorPlacement: function (error, element) {
	            error.insertAfter(element.parent());
	        }
	    });
	
	    $('#allotDataForm').ajaxForm({
	        dataType: "json",
	        type: "post",
	        success: function (data) {
	            $("#allotDeptDataSave").modal("hide");
	            if (data.success) {
	                $.smallBox({
	                    title: "操作成功",
	                    content: "<i class='fa fa-clock-o'></i> <i>成功分配数据    <font style='font-size: large;'>" + data.num + "</font>  条</i>",
	                    color: "#659265",
	                    iconSmall: "fa fa-check fa-2x fadeInRight animated",
	                    timeout: 4000
	                });
	                getResult();
	            };
	        },
	        submitHandler: function (form) {
	            $(form).ajaxSubmit({
	                success: function () {
	                    $("#allotDataForm").addClass('submited');
	                }
	            });
	        },
	        error: function (XMLHttpRequest, textStatus, errorThrown) {
	            if (textStatus == 'error') {
	                $("#allotDeptDataSave").modal("hide");
	                $.smallBox({
	                    title: "操作失败",
	                    content: "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作出现异常...</i>",
	                    color: "#C46A69",
	                    iconSmall: "fa fa-times fa-2x fadeInRight animated"
	                });
	            }
	        }
	    });
	    
	    $('#allotDataNumber').bind('keypress',function(event){
            if(event.keyCode == "13"){
            	$('#allotDeptDataSaveButton').click();
            	return false;
            }
        });
	    
	    var inputMax = $("#allocateDataMax");
        $("input[name=allocate]").change(function(){
        	if($(this).next().hasClass("hasInput")){
        		$(this).closest("div.row").append(inputMax);
        		inputMax.find("input").focus();
        	}else{
        		$("#allocateDataMax").remove();
        	}
        });
        
	});
	
	
	function checkAll(t) {
   		var dTree = $.fn.zTree.getZTreeObj("dtree");
   		var allnodes = dTree.getNodesByParam("chkDisabled", false);
   		for(var k in allnodes) {
   			dTree.checkNode(allnodes[k],t,false,true);
    	}
    }

</script>
