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
<div class="modal fade" id="dialogData">
    <div class="modal-dialog" style="width: 700px">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                <h4 class="modal-title">回收部门数据</h4>
            </div>
            <div class="modal-body">
                <form id="messageinfoa" action="${springMacroRequestContext.contextPath}/deptdata/dept/collectDeptDataSave" class="smart-form" method="post">
	                <header>
	                	当前批次：${entry.batchname} —  ${entry.deptname}
					</header>
					<fieldset>
	                	<section>
			         	    <span><font color="red">注：</font>此部门总数据上限为<font color="red">${totallimit}</font>条,现有数据<font color="red">${entry.dataCount}</font>条,最多回收数据<font color="red">${totallimit - entry.dataCount}</font>条.</span>
						</section>
						<section>
							<input type="hidden" name="deptUuid" value="${entry.departmentUuid}">
							<input type="hidden" name="batchUuid" value="${entry.dataBatchUuid}">
							<input type="hidden" name="depts" id="depts" />
						</section>
					</fieldset>
                </form>
	            <div id="trees" class="row">
	            	<div class="col-md-12">
		            	<strong>当前子部门</strong>
		            	<div class="btn btn-xs btn-primary" onclick="checkAll(true)"><span class="glyphicon glyphicon-ok"></span></div>
	               		<div class="btn btn-xs btn-primary" onclick="checkAll(false)"><span class="glyphicon glyphicon-remove"></span></div>
	               		<div id="dtree" class="ztree" style="overflow-x: auto;overflow-y: auto;width: 100%;height: 100%;"></div>
	            	</div>
	            </div>
	            <div id="msg"><span style="color: red">该部门内没有没有子部门,暂不能回收！</span></div>
            </div>
            <div class="modal-footer">
                <button id="msubmita" type="button" class="btn btn-primary"> 回收  </button>
                <button type="button" class="btn btn-default" data-dismiss="modal"> 关 闭   </button>
            </div>
     	</div>
   		
   		<script type="text/javascript">
   		
	   		$(document).ready(function(){
	   			
	   			$("#msg").hide();
	   			
	   			var setting = {
	   		            check: {
	   		                enable: true,
	   		                chkboxType : { "Y" : "", "N" : "" }
	   		            },
	   		            data: {
	   		                simpleData: {
	   		                    enable: true
	   		                }
	   		            },
	   		            callback :{
	   		            	onClick: onClick,
	   		            	onCheck: onCheck
	   		            }
	   			    };
	        	
	   			function onClick(e, t, n) {
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
	    	    	var zNodes = ${datarangeTree};
	    	    	dtreeObj = $.fn.zTree.init($("#dtree"), setting, zNodes);
	    	    <#else>
	   		    	$("#msg").show();
	   		    	$("#messageinfoa").hide();
	   		    	$("#trees").hide();
	   		    	$("#msubmita").hide();	
	    		</#if>
	    		
	            /* 显示弹框  */
	            $('#dialogData').modal({
	            	show : true,
	            	backdrop : "static"
	            });
	
	            /** 校验字段  **/
	            var validator =$('#messageinfoa').validate({});
	
	            /* 提交按钮单击事件  */
				$('#msubmita').click(function(){
	                if($('#messageinfoa').valid()){
	                	var nodes = $.fn.zTree.getZTreeObj("dtree").getCheckedNodes(true);
	    	        	if(nodes.length <= 0){
	    	        		alert("请选择要回收的子部门！");
	                		return false;
	    	        	}else{
	    	        		var depts = '';
	    	        		for(var n in nodes){
	    	        			depts += nodes[n].id+",";
	    	        		}
	    	        		$("#depts").val(depts);
	    	        	};
	                    $('#msubmita').attr("disabled",true);
	                    $('#messageinfoa').submit();
	                }
	            });
	            
	            $('#messageinfoa').ajaxForm({
	                dataType:"json",
	                success: function(data) {
	                    if(data.success){
	                        $.smallBox({
	                            title : "操作成功",
	                            content : "<i class='fa fa-clock-o'></i> <i>成功回收数据" + data.num + "条</i>",
	                            color : "#659265",
	                            iconSmall : "fa fa-check fa-2x fadeInRight animated",
	                            timeout : 2000
	                        });
	                    }
	                    $('#dialogData').modal("hide");
	                    validator.resetForm();
                        getResult();
	                },
	                submitHandler : function(form) {
	                    $(form).ajaxSubmit({
	                        success : function() {
	                            $("#messageinfoa").addClass('submited');
	                        }
	                    });
	                },
	                error: function(XMLHttpRequest,textStatus , errorThrown){
	                    if(textStatus=='error'){
	                        $('#dialogData').modal('hide');
	                        $.smallBox({
	                            title : "操作失败",
	                            content : "<i class='fa fa-clock-o'></i> <i>操作失败</i>",
	                            color : "#C46A69",
	                            iconSmall : "fa fa-times fa-2x fadeInRight animated",
	                            timeout : 2000
	                        });
// 	                        $("table.dataTable").DataTable().ajax.reload(null,false);
	                        getResult();
	                    }
	                }
	                
	            });
	            
	            /* 关闭窗口的回调函数  */
	            $('#dialogData').on('hide.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
	    			$("#dialogData").remove();
	    		});
	        });
	        
	        function checkAll(t) {
	       		var dTree = $.fn.zTree.getZTreeObj("dtree");
	       		var allnodes = dTree.getNodesByParam("chkDisabled", false);
	       		for(var k in allnodes) {
	       			dTree.checkNode(allnodes[k],t,false,true);
	        	}
	        };
	        
	    </script>
	</div>
</div>
