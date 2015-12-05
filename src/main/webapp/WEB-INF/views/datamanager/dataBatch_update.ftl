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
            	<input type="hidden" id="slimit" value="${singlelimit}">
				<input type="hidden" id="dlimit" value="${daylimit}">
                <form id="messageinfoa" class="smart-form" method="post" action="${springMacroRequestContext.contextPath}/databatch/data/save">
                	<section>
						<div class="row">
							<label class="label col col-2 ">批次名<sup>*</sup></label>
							<div class="input col col-10">
								<#if entry??>
									<input name="uuid" id="uid" type="hidden" value="${entry.uid}">
								</#if>
								<label class="input "> 
									<input id="newDataName" name="newDataName" style="height:32px" value="${(entry.batchName)!''}" onchange="clearnValid()">
								</label>
							</div>
						</div>
					</section>
				</form>
				<div style="display:none;" id="updateadddept">
					<div class="adddept" style="margin-top: 7px;text-align: right;">
						<strong style="padding-right: 50px;"></strong>
						单次上限
						<input style="width: 100px;margin-right: 50px;" id="singlelimit" name="singlelimit" value="${singlelimit}" onblur="valising(this);" onfocus="if(value!=''){value=''}">
						单日上限
						<input style="width: 100px;margin-right: 40px;" id="daylimit" name="daylimit" value="${daylimit}" onblur="validay(this);" onfocus="if(value!=''){value=''}">
					</div>
				</div>
				<div id="trees" class="row" style="padding-left: 15px;margin-bottom: 10px;">
	            	<label id="guishu">归属部门</label>
	            	<div class="btn btn-xs btn-primary" onclick="checkAll(true)"><span class="glyphicon glyphicon-ok"></span></div>
                	<div class="btn btn-xs btn-primary" onclick="checkAll(false)"><span class="glyphicon glyphicon-remove"></span></div>
               		<div id="dtree" class="ztree"></div>
	            </div>
	            <div class="modal-footer">
	            	<label id="validatelimit" class="hidden" style="color:red;margin-right: 20%;">单次上限必须小于单日上限</label>
			         <button id="msubmita" type="button" class="btn btn-primary"> 保  存  </button>
	                <button type="button" class="btn btn-default" data-dismiss="modal">  关 闭  </button>
	            </div>
            </div>
            </div>
     	</div>
     	
   	<script type="text/javascript">
   		
        $(document).ready(function(){
	        
	        var setting = {
                check: {
                    enable: true,
//                     chkboxType: { "Y" : "s", "N" : "p" }
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
		   	    			$("#dtree").after($('#updateadddept').children().clone().css('display','block'));
		   	    			$("#dtree").next("div").attr("id", n.id);
		   	    			$("#dtree").next().children('strong').text(n.name);
		   	    			
		   	    			<#if deptAndLimit??>
		   	    			<#list deptAndLimit as deptlimit>
			        			var deptid = "${deptlimit}".split('-')[0];
			        			if(n.id == deptid){
			        				$("#"+n.id).children("[id='singlelimit']").val('${deptlimit}'.split('-')[1]);
			        				$("#"+n.id).children("[id='daylimit']").val('${deptlimit}'.split('-')[2]);
			        			}
			        		</#list>
			        		</#if>
		   	    			
		   	    			addedId.push(n.id);
	    	    		}
						for(var nn in n.children){
							$.fn.zTree.getZTreeObj(t).checkNode(n.children[nn], true, true, true);
						}
	    	    	}else{
	    	    		$("#" + n.id).remove();
	    	    		addedId[addedId.indexOf(n.id)] = undefined;
	    	    		for(var nn in n.children){
    	    				$.fn.zTree.getZTreeObj(t).checkNode(n.children[nn], false, true, true);
						}
//     	    			$.fn.zTree.getZTreeObj(t).checkNode(n.getParentNode(), false, false, true);
	    	    	}
    	    	};
    		};
	    		
    		<#if departments??>
    	    	var zNodes = ${departments};
    	    	dtreeObj = $.fn.zTree.init($("#dtree"), setting, zNodes);
    	    	
    	    	for(var nn in zNodes){// 判断所有节点,如果选中则触发点击事件
    	    		if(zNodes[nn].checked){
    	    			dtreeObj.checkNode(zNodes[nn], true, true, true);
    	    			// 注意zNodes[nn]此时并不是原始node,必须通过api重新获得
    	    			var node = dtreeObj.getNodeByParam("id", zNodes[nn].id);
//     	    			for(var k in node.children){
//     	    				node.children[k].chkDisabled = true;
// 						}
    	    		}
				}
    	    	
//     	    	var d = $("#dialogData .adddept");
// 				d.each(function(k,v){
// 					if(k > 0){
// 						<#list deptAndLimit as deptlimit>
// 		        			var deptid = "${deptlimit}".split('-')[0];
// 		        			if(v.id == deptid){
// 		        				$("#"+v.id).children("[id='singlelimit']").val('${deptlimit}'.split('-')[1]);
// 		        				$("#"+v.id).children("[id='daylimit']").val('${deptlimit}'.split('-')[2]);
// 		        			}
// 		        		</#list>
// 					};
// 				});
    	    	
    		</#if>

            /* 显示弹框  */
            $('#dialogData').modal("show");
                    
            $("#messageinfoa").validate({
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

    		/*fadein out或者slideup down*/
    		//移除所有msubmita上面的所有click事件
    		$("#msubmita").off("click");
 			$("#msubmita").on("click",function(){
 				
 				// 获取所有的单次上限和单日上限
 				var d = $("#dialogData .adddept");
				d.each(function(k,v){
					if(k > 0){
						var slimit = $("#"+v.id).children("[id='singlelimit']").val();
						var dlimit = $("#"+v.id).children("[id='daylimit']").val();
						if(Number(slimit) > Number(dlimit)){
							$("#msubmita").attr("disabled",true);
		 					$("#validatelimit").attr("class","");
		 					return false;
						}
					};
				});
 				
 				$.post(getContext() + 'databatch/data/update',$.param({uid:$("#uid").val(),batchName:$("#newDataName").val(),
 					undepartments:function(){
 						var undepts = [];
	 					var allNodes = dtreeObj.getNodesByFilter(function(n){
				    		if(!n.chkDisabled && !n.checked){
				    			return true;
				    		}else{
				    			return false;
				    		}
				    	});
				    	for(var k in allNodes) {
				    		undepts.push(allNodes[k].id);
				    	}
 						return undepts;
 				},departments:(function(){
 					var l = [];
 					var d = $("#dialogData .adddept");
 					d.each(function(k,v){
 						if(k > 0){
	 						l.push(v.id + "-" + $("#"+v.id).children("[id='singlelimit']").val() + "-" + $("#"+v.id).children("[id='daylimit']").val());
 						};
 					});
 					return l;
 				})()}, true),function(newdata){
 					if(newdata.success){
                           $.smallBox({
                               title : "操作成功",
                               content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
                               color : "#659265",
                               iconSmall : "fa fa-check fa-2x fadeInRight animated",
                               timeout : 2000
                           });
   	 					$("#dataUuid").val(newdata.uuid);
                           $('#msubmita').attr("disabled",true);
                           $('#dialogData').modal("hide");
                           $('#dataDataTable').DataTable().ajax.reload(null,false);;
                           		 					
                       }
 					else{
 						$.smallBox({
                            title : "操作失败",
                            content : "<i class='fa fa-clock-o'></i> <i>操作失败，请重试</i>",
                            color : "#C46A69",
                            iconSmall : "fa fa-times fa-2x fadeInRight animated",
                            timeout : 2000
                        });
 					}
 					$('#dataDataTable').DataTable().ajax.reload(null,false);;
 				},"json");
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

