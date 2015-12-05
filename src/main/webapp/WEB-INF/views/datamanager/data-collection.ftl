<!-- <link rel="stylesheet" href="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"> -->
<!-- <script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.core-3.5.min.js"></script> -->
<!-- <script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.excheck-3.5.min.js"></script> -->
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
/* .col-md-8 .row div.col */
/* { */
/* 	padding-right:0px; */
/* } */
</style>
<div class="modal fade" id="dialogData">
    <div class="modal-dialog" style="width: 800px">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                <h4 class="modal-title">回收人员数据</h4>
            </div>
            <div class="modal-body">
                <form id="messageinfoa" action="${springMacroRequestContext.contextPath}/deptdata/dept/newCollectSave" class="smart-form" method="post">
	                <header>
	                	当前批次：${entry.batchname} — ${entry.deptname}
					</header>
	                <fieldset>
	                	<section>
			         	    <span><font color="red">注：</font>此操作将不会回收部门批次中的意向客户,成交客户,废号和黑名单号码量。</span>
						</section>
						<section>
							<input type="hidden" name="departmentUuid" value="${entry.departmentUuid}">
							<input type="hidden" name="dataBatchUuid" value="${entry.dataBatchUuid}">
							<input type="hidden" name="dpusers" id="dpusers"/>
							<label class="label">选择回收方案</label>
							<div class="row">
								<div class="col">
									<label class="checkbox">
										<input type="checkbox" class="checkbox style-0" name="collect" value="0" checked="checked">
										<span>未拨打</span> 
									</label>
								</div>
								<div class="col">
									<label class="checkbox">
										<input type="checkbox" class="checkbox style-0" name="collect" value="1">
										<span>已打未通</span> 
									</label>
								</div>
							</div>
						</section>
					</fieldset>
                </form>
                <div class="row" id="treeData">
             		<div class="col-md-4" >
		            	<strong>管辖部门</strong>
		            	<div class="btn btn-xs btn-primary" onclick="checkAll(true)"><span class="glyphicon glyphicon-ok"></span></div>
	               		<div class="btn btn-xs btn-primary" onclick="checkAll(false)"><span class="glyphicon glyphicon-remove"></span></div>
              			<div id="dtree" class="ztree" style="overflow-x: auto;overflow-y: auto;width: 100%;height: 100%;"></div>
             		</div>
	             	<div class="col-md-8">
						<select multiple="multiple" id="deptusers" name="deptusers">
							<#if users??>
								<#list users as u>
									<option class='${u.department}' value='${u.department}-${u.uid}' >[${u.departmentName}] ${u.loginName}</option>
								</#list>
							</#if>
						</select>
					</div>
	            </div>
            </div>
            <div class="modal-footer">
                <button id="msubmita" type="button" class="btn btn-primary">  回收    </button>
                <button type="button" class="btn btn-default" data-dismiss="modal">  关 闭     </button>
            </div>
     	</div>
     	
   	<script type="text/javascript">
    
   		
   		var optionMap = {};
   		$(document).ready(function(){
   			
   			$("#deptusers option").each(function(k,v){
   				optionMap[$(v).attr("class")] = $("#deptusers option." + $(v).attr("class"));
   			});
        	
        	var setting = {
                  check: {
                      enable: true,
                      chkboxType: {"Y" : "s", "N" : "s" }
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
       			var nodes = zTree.getNodesByParam("checked", true);
       			$("#deptusers option:not(:selected)").remove();
       			if(nodes.length == 0){
       				nodes = zTree.getNodesByParam("checked", false);
       			}
    	    	for (var i in nodes) {
    	    		$("#deptusers").append(optionMap[nodes[i].id]);
    	    	}
       			
    	    	$('#deptusers').bootstrapDualListbox('refresh', true);
       		};
    		
    		<#if datarangeTree??>
    	    	var zNodes = ${datarangeTree};
    	    	dtreeObj = $.fn.zTree.init($("#dtree"), setting, zNodes);
    		</#if>
    		
    		$('#deptusers').bootstrapDualListbox({
    			nonSelectedListLabel: '现有人员清单',
    			selectedListLabel: '待分配人员清单',
    			preserveSelectionOnMove: 'moved',
    			moveOnSelect: false,
    			selectorMinimalHeight: "150",
    			filterPlaceHolder: "筛选",
    			filterTextClear: "显示全部",
    			infoText: "列表包含 {1} 项",
    			infoTextFiltered: '<span class="label label-warning">筛选</span> {1} 项中的 {0} 项',
    			infoTextEmpty: "列表为空"
    		});

            /* 显示弹框  */
            $('#dialogData').modal({
            	show : true,
            	backdrop : "static"
            });

            /** 校验字段  **/
            var validator =$('#messageinfoa').validate({
            });

            /* 提交按钮单击事件  */
			$('#msubmita').click(function(){
                if($('#messageinfoa').valid()){
                	if($("[name='collect']:checked").length == 0){
                		alert("请至少选择一种回收方案！");
                		return false;
                	}else if($("#deptusers :selected").length == 0){
                		alert("请选择要回收数据的人员！");
                		return false;
                	}else{
                		var dpusers = "";
                		$("#deptusers option:selected").each(function(){
                			// 得到选中的值拼接成字符串
                			dpusers += $(this).val() + ",";
                        });
                		
                		$("#dpusers").val(dpusers);
                	}
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
                        $('#msubmita').attr("disabled",false);
                        $("table.dataTable").DataTable().ajax.reload(null,false);;
                    }
                    $('#dialogData').modal("hide");
                    validator.resetForm();
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

                        $("table.dataTable").DataTable().ajax.reload(null,false);;
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
