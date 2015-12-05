<!-- 从部门分配到人 -->
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
	
	.bootstrap-duallistbox-container label{
	    padding-top: 1px;
	    margin-bottom: 5px;
	}
	
</style>
<div class="modal fade" id="allotDataSave" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 800px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                <h4 class="modal-title">分配数据</h4>
            </div>
            <div class="modal-body">
                <form action="${springMacroRequestContext.contextPath}/deptdata/dept/allotDataSave" class="smart-form" id="allotDataForm" method="post">
					<input type="hidden" name="dataBatchUuid" id="dataBatchUuid" value="${(dataBatchUuid)!''}" />
					<input type="hidden" name=deptUuid id="deptUuid" value="${(deptUuid)!''}" />
					<input type="hidden" name="numLimit" id="numLimit" value="${(numLimit)!''}" />
					<input type="hidden" name="dpusers" id="dpusers"/>
                	<header style="padding: 0px;">
                		<i class="fa fa-lg fa-volume-up" style="margin-right: 5px;"></i>
						<span>当前批次：${entry.batchName}，共有数据<font color="red"> ${entry.dataCount} </font>条，剩余数据<font color="red"> ${(numLimit)!''} </font>条</span><br /><br />
                	</header>
                	<fieldset>
<!-- 	                    <section> -->
<!-- 	                        <div class="row"> -->
<!-- 	                            <label class="label col col-3"> 分配数量： </label> -->
<!-- 	                            <label class="input col col-4"> -->
<!-- 	                            	<input id="allotDataNumber" name="allotDataNumber" value="${(numLimit)!''}" /> -->
<!-- 	                            </label> -->
<!-- 	                        </div> -->
<!-- 	                    </section> -->
						<section>
							<label class="label">选择分配方案</label>
							<div class="row">
								<div class="col col-7">
									<div class="row">
										<div class="col">
											<label class="radio">
												<input type="radio" class="radiobox style-0" name="allocate" value="1" checked="checked">
												<span class="hasInput">给每人分配：</span> 
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
												<span class="hasInput">给每人分配<span style="color:red">至</span>：</span> 
											</label>
										</div>
									</div>
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
	            <div id="msg"><span style="color: red">部门内没有数据,暂不能分配！</span></div>
            </div>
            <div class="modal-footer">
                <button type="button" id="quxiao" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" id="allotDataSaveButton" class="btn btn-primary">分配 </button>
            </div>
        </div>
    </div>
</div>

<script type="application/javascript">

	var optionMap = {};
	$(document).ready(function() {
		
		$("#deptusers option").each(function(k,v){
			optionMap[$(v).attr("class")] = $("#deptusers option." + $(v).attr("class"));
		});
		
		
		$("#msg").hide();
	    
	    $('#allotDataSave').modal("show");
		
		/* 关闭窗口的回调函数,hide是关闭调用后在关闭    */
	    $('#allotDataSave').on('hide.bs.modal', function(e){
			$("#allotDataSave").remove();
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
		
		
	    var numlimit = Number($('#numLimit').val());
	    if(numlimit <= 0){
	    	
	    	$("#treeData").hide(); // 部门内没有数据,不能分配,隐藏子部门树结构 
	    	
	    	$("#allotDataForm").hide();
	    	$("#msg").show();
	    	$("#quxiao").text("关闭");
	    	$("#allotDataSaveButton").hide();
	    }
		
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
	    
	    $('#allotDataSaveButton').click(function () {
	        if($('#allotDataForm').valid()){
	        	if($("#deptusers :selected").length == 0){
            		alert("请选择要分配的人员！");
            		return false;
            	}else{
            		var dpusers = "";
            		$("#deptusers option:selected").each(function(){
            			// 得到选中的值拼接成字符串
            			dpusers += $(this).val() + ",";
                    });
            		
            		$("#dpusers").val(dpusers);
            	}
	        	$('#allotDataSaveButton').attr("disabled",true);
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
	            $("#allotDataSave").modal("hide");
	            if (data.success) {
	                $.smallBox({
	                    title: "操作成功",
	                    content: "<i class='fa fa-clock-o'></i> <i>成功分配数据    <font style='font-size: large;'>" + data.num + "</font>  条</i>",
	                    color: "#659265",
	                    iconSmall: "fa fa-check fa-2x fadeInRight animated",
	                    timeout: 4000
	                });
	                $("#dt_basic_deptdata").DataTable().ajax.reload(null,false);
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
	                $("#allotDataSave").modal("hide");
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
            	$('#allotDataSaveButton').click();
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
    };

</script>
