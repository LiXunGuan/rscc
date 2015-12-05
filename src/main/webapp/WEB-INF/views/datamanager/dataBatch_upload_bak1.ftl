<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/css/metroStyle/metroStyle.css" type="text/css">
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.exhide-3.5.min.js"></script>
<style>
	div.col label.input input
	{
		height:25px;
	}
	.note.note-error
	{
		display:none;
	}
</style>
<div class="modal fade" id="dialogData">
    <div class="modal-dialog" style="width: 600px">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                <h4 class="modal-title">${title}</h4>
            </div>
            <div class="modal-body">
				<a class="col-3" onclick="window.open('public/docs/example.xlsx')">点击下载模板文件</a>
				<input type="hidden" id="slimit" value="${singlelimit}">
				<input type="hidden" id="dlimit" value="${daylimit}">
                <div id="messageinfoa" class="smart-form" style="margin-top: 10px" >
                	<section>
						<div class="row">
							<label class="label col col-2 ">批次名<sup>*</sup></label>
							<div class="input col col-10">
								<form id="temp">
								<#if entry??>
									<input name="uuid" id="uid" type="hidden" value="${entry.uid}">
									</#if>
									<label class="input "> 
										<input id="newDataName" name="newDataName" style="height:32px" value="${(entry.batchName)!''}" onchange="clearnValid()">
									</label>
									<div id="name-error-message" class="note note-error">必须输入批次名</div>
									<div id="name-error-message1" class="note note-error">批次名已存在</div>
								</form>
							</div>
						</div>
					</section>
					<#if title == "上传数据">
		                <section>
			                <div class="row">
			                    <label class="label col col-2 ">选择文件<sup>*</sup></label>
			                    <div class="col col-10">
			                        <div class="input input-file">
										<span class="button"><input id="fileupload" type="file" name="multipartFile" onchange="this.parentNode.nextSibling.value = this.value;clearfValid();" accept=".xlsx">浏览</span><input id="filetext" type="text" placeholder="" readonly="">
									</div>
									<div id="file-error-message" class="note note-error">必须选择一个文件</div>
			                    </div>
			                </div>
		                </section>
	                </#if>
					<div style="display:none;" id="adddept">
						<div class="adddept" style="margin-top: 7px;text-align: right;">
							<strong style="padding-right: 50px;"></strong>
							单次上限
							<input style="width: 100px;margin-right: 50px;" id="singlelimit" name="singlelimit" value="${singlelimit}" onblur="valising(this);" onfocus="if(value!=''){value=''}">
							单日上限
							<input style="width: 100px;margin-right: 40px;" id="daylimit" name="daylimit" value="${daylimit}" onblur="validay(this);" onfocus="if(value!=''){value=''}">
						</div>
					</div>
                </div>
				<!-- 新加ztree显示部门  -->
                <#if departments??>
	                <div id="trees" class="row" style="padding-left: 15px;">
		            	<label id="guishu">归属部门</label>
	               		<div id="dtree" class="ztree"></div>
		            </div>
	            </#if>
            </div>
            <div class="modal-footer">
            	<label id="validatelimit" class="hidden" style="color:red;margin-right: 49%;">单次上限必须小于单日上限</label>
                <button id="msubmita" type="button" class="btn btn-primary" onclick="returnFalse(event)">上  传 </button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关 闭 </button>
            </div>
     	</div>
    </div>	
   	<script type="text/javascript">
   		
   		function returnFalse(event){
   			if(!valid()) {
   				event.stopImmediatePropagation();
   				return false;
   			}
   		}
   	
        $(document).ready(function(){
        	
	        var setting = {
                check: {
                    enable: true,
                    chkboxType: { "Y" : "", "N" : "s" }
                },
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                callback :{
                	onClick: treeCheckNode,
                	onCheck: treeCheckNodes
                }
    	    };
	    	    
    	    function treeCheckNode(e, t, n) {
           		$.fn.zTree.getZTreeObj(t).checkNode(n, !n.checked, true, true);
    		};
    		
    		var addedId = [];
    		
    		function treeCheckNodes(e, t, n){
    			if(!n.chkDisabled){ // 判断节点是否禁用
	    	    	if(n.checked){ // 判断节点是否选中
	    	    		if (addedId.indexOf(n.id) < 0) { // 判断数组中是否存在
		   	    			$("#dtree").after($('#adddept').children().clone().css('display','block'));
		   	    			$("#dtree").next("div").attr("id", n.id);
// 		   	    			$("#dtree").next().children('input').val(5000);	
		   	    			$("#dtree").next().children('strong').text(n.name);
		   	    			addedId.push(n.id);
	    	    		}
						for(var nn in n.children){
							$.fn.zTree.getZTreeObj(t).checkNode(n.children[nn], true, true, true);
							n.children[nn].chkDisabled = true;
						}
	    	    	}else{
	    	    		for(var nn in n.children){
    	    				n.children[nn].chkDisabled = false;
    	    				$.fn.zTree.getZTreeObj(t).checkNode(n.children[nn], false, true, true);
						}
	    	    		$("#" + n.id).remove();
	    	    		addedId[addedId.indexOf(n.id)] = undefined;
	    	    	}
    	    	};
    		};
    		
    		<#if departments??>
    	    	var zNodes =${departments};
    	    	dtreeObj = $.fn.zTree.init($("#dtree"), setting, zNodes);
    	    	
    	    	// 禁用节点点击
//     	    	var allNodes = dtreeObj.getNodesByFilter(function(node){
// 		    		if(node.hasPermission){
// 		    			return false;
// 		    		}else{
// 		    			return true;
// 		    		}
// 		    	});
// 		    	for(var k in allNodes) {
// 		    		dtreeObj.setChkDisabled(allNodes[k], true, true, false);
// 		    	}
    		</#if>

            /* 显示弹框  */
            $('#dialogData').modal("show");
            
            $("#temp").validate({
            	rules: {
            		newDataName:{
						required : true,
						remote : {
            				url:"${springMacroRequestContext.contextPath}/databatch/data/checkname/1",
            				type:"post",
            				data : {
                				uid : function(){
                					return $('#uid').val();
                				}
                			}
            			}
					}
				},
				messages: {
					newDataName:{
						remote:"批次名已存在,请更换"
					}
				},
		        errorPlacement : function(error, element) {
		            error.insertAfter(element.parent());
		        }
            });
            
            $('#messageinfoa').ajaxForm({
                dataType:"json",
                 beforeSubmit:function(){
                	var singledata = $("input[name='singlelimit']").val();
	 				var daydata = $(singledata).next('input').val();
	 				
	 				if(Number(singledata) > Number(daydata)){
	 					$("#msubmita").attr("disabled",true);
	 					$("#validatelimit").attr("class","");
	 					return false;
	 				}
                },
                success: function(data) {
                    if(data.success){
                        $.smallBox({
                            title : "操作成功",
                            content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
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

                        $('#dataDataTable').DataTable().ajax.reload(null,false);;
                    }
                }
            });
            /* 关闭窗口的回调函数  */
            $('#dialogData').on('hide.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
    			$("#dialogData").remove();
    		});
            
        });
        
        function valid(){
 			if(!$("#temp").valid())
 				return false;
        	if($("#newDataName").val()!="" && $("#filetext").val()!=""){
        		return true;
        	}
        	if($("#newDataName").val()==""){
        		$("#name-error-message").show();
        		$("#name-error-message").prev().addClass("state-error");
        	}
        	 
        	if($("#filetext").val()==""){
        		$("#file-error-message").show();
        		$("#file-error-message").prev().addClass("state-error");
        	}
        	return false;
        }
        
        function clearnValid(){
        	$("#name-error-message").hide();
    		$("#name-error-message").prev().removeClass("state-error");
        }
        
        function clearfValid(){
			$("#file-error-message").hide();
			$("#file-error-message").prev().removeClass("state-error");
        }
        
        function valising(data){
			  var singlim = $(data).val();
		      var daylim = $(data).next('input').val();
			  if($.trim(singlim) == ''){
				  $(data).val($("#slimit").val()); 
			  };
			  if($.trim(daylim) == ''){
				  $(data).next('input').val($("#dlimit").val()); 
			  };
			  singlim = $(data).val();
		      daylim = $(data).next('input').val();
		      if(Number(singlim) > Number(daylim)){
		      	$("#validatelimit").attr("class","");
		      	$("#msubmita").attr("disabled",true);
		      }else{
		      	$("#validatelimit").attr("class","hidden");
		      	$("#msubmita").attr("disabled",false);
		      }
        }
        
        function validay(data){
        	  var singlim = $(data).prev('input').val();
			  var daylim = $(data).val();
			  if($.trim(singlim) == ''){
				  $(data).prev('input').val($("#slimit").val()); 
			  };
			  if($.trim(daylim) == ''){
				  $(data).val($("#dlimit").val()); 
			  };
			  singlim = $(data).prev('input').val();
			  daylim = $(data).val();
			  if(Number(singlim) > Number(daylim)){
			  	$("#validatelimit").attr("class","");
			  	$("#msubmita").attr("disabled",true);
			  }else{
			  	$("#validatelimit").attr("class","hidden");
			  	$("#msubmita").attr("disabled",false);
			  }
      	}
        
    </script>
</div>
<#macro section titles names>
	<#list titles as t>
      		<section>
     			<div class="row">
        		    <label class="label col col-2">${t}</label>
      			    <div class="col col-6">
					<label class="input">
						<input name="${names[t_index]}" />
                		</label>
              		</div>
            	</div>
        	</section>
	</#list>
</#macro>
<#macro validate titles names>
	rules : {
	<#list names as n>
       	${n} : {
       		required : true
       	},
	</#list>
	
      },
       messages: {
       <#list names as n>
       	${n}: {
       		required : "必须输入${titles[n_index]}"
       	},
     	</#list>
       },
       errorPlacement : function(error, element) {
           error.insertAfter(element.parent());
       }
</#macro>
<#macro treeString tree treeobj>
  	var nodes = ${treeobj}.getCheckedNodes();
   	var roles="";
 	for(var n in nodes){
       if(!nodes[n].isParent) {
           if (nodes[n].id) {
          		roles = roles + nodes[n].id + ',';
           }
      	}
   	}
   	$('#${tree}').val(roles.substr(0,roles.length-1));
</#macro>

	
    

