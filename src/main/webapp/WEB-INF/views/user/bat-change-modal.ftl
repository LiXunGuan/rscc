<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/css/metroStyle/metroStyle.css" type="text/css">
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.exhide-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/select2/select2.min.js"></script>
<style>
#dialogBatChange div.col label.input input
{
	height:25px;
}

#dialogBatChange #trees{
	padding-left: 8px;
}

#dialogBatChange .note.note-error
{
	display:none;
}
</style>
<div class="modal fade" id="dialogBatChange">
    <div class="modal-dialog" style="width: 600px">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                <h4 class="modal-title">批量修改</h4>
            </div>
            <div class="modal-body">
                <form id="messageinfoa" class="smart-form" method="post" action="${springMacroRequestContext.contextPath}/user/user/batAddSave">
                	<input type="hidden" id="permissionRoles" name="permissionRoles" />
                	<input type="hidden" id="roledataranges" name="roledataranges" />
                	<section>
						<div class="row">
							<label class="label col col-3 ">名称前缀</label>
							<div class="input col col-8">
							<label class="input">
								<input id="prefix" name="prefix" value="">
							</label>
							</div>
						</div>
					</section>
					<section>
						<div class="row">
							<label class="label col col-3 ">起始号码<sup>*</sup></label>
							<div class="input col col-8">
								<label class="input">
								<input id="start" name="start" value="">
								</label>
							</div>
						</div>
					</section>
					<section>
						<div class="row">
							<label class="label col col-3 ">结束号码<sup>*</sup></label>
							<div class="input col col-8">
								<label class="input">
								<input id="end" name="end" value="">
								</label>
							</div>
						</div>
					</section>
					<section>
						<div class="row">
						<label class="label col col-3">密码生成方式</label>
						<div class="col col-8">
							<div class="row">
								<div class="col">
									<label class="radio">
										<input type="radio" class="radiobox style-0" name="passwordType" value="0" checked="checked" >
										<span>与号码一致</span> 
									</label>
								</div>
							</div>
							<div class="row">
								<div class="col">
									<label class="radio">
										<input type="radio" class="radiobox style-0" name="passwordType" value="1">
										<span>随机生成六位数字</span> 
									</label>
								</div>
							</div>
							<div class="row">
								<div class="col">
									<label class="radio">
										<input type="radio" class="radiobox style-0" name="passwordType" value="2">
										<span>使用统一密码</span> 
									</label>
								</div>
								<div class="col col-4" id="passwordInput" style="padding-left:0px;display:none;">
									<label class="input">
										<input type="text" name="password" class="required">
									</label>
								</div>
							</div>
						</div>
						</div>
                        <div class="row">
                            <label class="label col col-3 ">归属部门<sup>*</sup></label>
                            <div class="col col-8">
                                <label class="select">
								<select name="department" id="department">
									 <#list departments as depart>
									 	<#if hasDepartments?seq_contains(depart.uid)>
									 		<option value="${depart.uid}">${depart.datarangeName}</option>
								 		</#if>
									 </#list>
								</select>
                                </label>
                            </div>
                        </div>
                    </section>
                </form>
                <div id="trees" class="row">
	                <div class="col-md-6" style="padding-left:5px">
		            	<label class="">所属角色</label>
		            	<div class="btn btn-xs btn-primary" onclick="checkAll(prtreeObj,true)"><span class="glyphicon glyphicon-ok"></span></div>
		            	<div class="btn btn-xs btn-primary" onclick="checkAll(prtreeObj,false)"><span class="glyphicon glyphicon-remove"></span></div>
		            	<div id="prtree" class="ztree" ></div>
		            </div>
	                
	                <div id="roledatarange" class="col-md-6">
	                	<label class="">管辖范围</label>
		               	<div class="btn btn-xs btn-primary" onclick="checkAll(rdtreeObj,true)"><span class="glyphicon glyphicon-ok"></span></div>
	                	<div class="btn btn-xs btn-primary" onclick="checkAll(rdtreeObj,false)"><span class="glyphicon glyphicon-remove"></span></div>
	               		<div id="rdtree" class="ztree" ></div>
            		</div>
            	</div>
            </div>
            <div class="modal-footer">
                <button id="msubmita" type="button" class="btn btn-primary">
                    保  存
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    关 闭
                </button>
            </div>
     	</div>
   	</div>
</div>
     	
	<script type="text/javascript">
        $(document).ready(function(){
        	 
	        /*密码生成方式选择第三个的话就显示出来输入框*/
	        $("[name='passwordType']").change(function() {
	        	if ($("[name='passwordType']:checked").val() == "2") {
	        		$("#passwordInput").show().find("input").focus();
	        	} else {
	        		$("#passwordInput").hide();
	        	}
	        });
	        
	        /*显示页面*/
        	 $('#dialogBatChange').modal("show");
        	 
            /* 禁用空白自动关闭*/
            $('#dialogBatChange').modal({backdrop: 'static'});
            
            /*归属部门的select2插件加载*/
            $("#department").select2({
        		width:"100%",
        		sortResults : function(results, container, query) {
	       			return results.sort(function(a,b) {
	       				if(a.text > b.text)
	       					return -1;
	       				return 1;
	       			});
        		}
        	});
            
            /*添加整数自定义jquery校验，用于校验起始和结束号码的类型*/
            jQuery.validator.addMethod("Positiveinteger", function(value, element) {   
                var tel = /^[0-9]\d*$/;
                return this.optional(element) || (tel.test(value));
            }, "请输入整数");
            
            jQuery.validator.addMethod("chinese", function(value) {
            	if(!value)
            		return true;
                var chinese =/[A-Za-z0-9_-]$/;
                return chinese.test(value);
            }, "只能输入字母、数字、下划线");
            
            /** 校验字段  **/
            var validator =$('#messageinfoa').validate({
                rules : {
                	prefix : {
                		chinese :true
                	},
                	start : {
                		required: true,
                		Positiveinteger: true
                	},
                	end : {
                		required: true,
                		Positiveinteger: true
                	},
                	password : {
                		pwd:1,
                		digits:true
                	}
                },
                message: {
                	start : {
                		required: "请填写开始号码"
                	},
                	end : {
                		required: "请填写结束号码"
                	},
                },
                errorPlacement : function(error, element) {
                    error.insertAfter(element.parent());
                }
            });
            
            jQuery.validator.addMethod("pwd",function(value, element) {
				return $("[name='passwordType']:checked").val() == "2" && $("[name='password']").val().length > 0;
			},"密码不能为空");

            /* 提交按钮单击事件  */
			$('#msubmita').click(function(){
                if($('#messageinfoa').valid()){
                    var nodes = prtreeObj.getCheckedNodes();
                   	var roles="";
                 	for(var n in nodes){
                    	if (nodes[n].id) {
                    		roles = roles + nodes[n].id + ',';
                    	}
                   	}
                   	$('#permissionRoles').val(roles.substr(0,roles.length-1));
	                
	                var parentNodes = rdtreeObj.getNodesByParam("level", "1", null);
	            	for(var i in parentNodes)
	            		rdtreeObj.checkNode(parentNodes[i],true,false);
	              	var nodes = rdtreeObj.getCheckedNodes();
	               	var roles="";
	             	for(var n in nodes){
	             	   if(nodes[n].level==0) {
	             	       continue;
	             	   }
	                   if(nodes[n].level==1) {
	                       roles = roles + ";" + nodes[n].id + ":";
	                   } else {
	                       if (nodes[n].id) {
	                      		roles = roles + nodes[n].id + ',';
	                       }
	                  	}
	               	}
	               	$('#roledataranges').val(roles.substr(0,roles.length-1));
	                
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
                            content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
                            color : "#659265",
                            iconSmall : "fa fa-check fa-2x fadeInRight animated",
                            timeout : 2000
                        });
                        $('#msubmita').attr("disabled",false);
                        $('#oTable').DataTable().ajax.reload(null,false);;
                    }
		    		else{
                    	$.smallBox({
                            title : "操作失败",
                            content : "<i class='fa fa-clock-o'></i> <i>操作失败，未做修改</i>",
                            color : "#C46A69",
                            iconSmall : "fa fa-times fa-2x fadeInRight animated",
                            timeout : 2000
                        });
                    }
                    $('#dialogBatChange').modal("hide");
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
                        $('#dialogBatChange').modal('hide');
                        $.smallBox({
                            title : "操作失败",
                            content : "<i class='fa fa-clock-o'></i> <i>操作失败</i>",
                            color : "#C46A69",
                            iconSmall : "fa fa-times fa-2x fadeInRight animated",
                            timeout : 2000
                        });

                        $('#oTable').DataTable().ajax.reload(null,false);;
                    }
                }
            });
            
            /* 关闭窗口的回调函数  */
            $('#dialogBatChange').on('hide.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
    			$("#dialogBatChange").remove();
    		});
            
            
        });

        var setting = {
            check: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
            	onClick: treeCheckNode
            }
        };
        
        var psetting = {
                check: {
                    enable: true,
                    chkboxType: { "Y" : "s", "N" : "s" }
                },
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                callback: {
                	onClick: treeCheckNode,
                	onCheck: zTreeOnCheck
                }
            };
        
        function treeCheckNode(e, t, n) {
       		$.fn.zTree.getZTreeObj(t).checkNode(n, !n.checked, true, true);
		}
        function zTreeOnCheck(event, treeId, treeNode) {
			if(treeNode.id!="ada" && treeNode.id!="aee")
    			return;
            var zTree = $.fn.zTree.getZTreeObj(treeId);
            if(treeNode.id=="ada"){
				node = zTree.getNodeByParam("id","01");
	            if(treeNode.checked)
					zTree.hideNode(node);
	            else
	            	zTree.showNode(node);
            } else if (treeNode.id=="aee") {
            	nodes = zTree.getNodesByFilter(function(node) {
            		if(node.pId=="queue" && node.id !="aee")
            			return true;
            	});
	            if(treeNode.checked)
					zTree.hideNodes(nodes);
	            else
	            	zTree.showNodes(nodes);
            }
        }
        
        var zNodes =${permissionRoleTree};
        prtreeObj = $.fn.zTree.init($("#prtree"), setting, zNodes);
        
        var zNodes =${roleDatarangeTree};
    	rdtreeObj = $.fn.zTree.init($("#rdtree"), psetting, zNodes);
    	var allNodes = rdtreeObj.getNodesByFilter(function(node){
    		if(Number(node.level) < 2 || node.pId == "queue" || 
    				(node.id == "ada" && $("#department option").size() == rdtreeObj.getNodesByFilter(function(node){
    			if(node.level>2) 
    				return true;
    			}).length + 1)) {
    			return false;
    		} else {
    			return true;
    		}
    	});
    	for(var k in allNodes) {
    		rdtreeObj.setChkDisabled(allNodes[k], true, false, true);
    	}
    	$("#department option").each(function(k,v){
    		rdtreeObj.setChkDisabled(rdtreeObj.getNodeByParam("id",$(v).val()), false, false, false);
    	});
    	
    	function nodesToOption(nodes,dom) {
        	for(var i in nodes) {
        		if(nodes[i].id==0)
        			continue;
	        	var option = $('<option></option>');
	        	option.val(nodes[i].id).text(new Array(nodes[i].level * 3).join("-") + nodes[i].name);
	        	dom.append(option);
	        	nodesToOption(nodes[i].children,dom);
        	}
        }
    	
    	function checkAll(o,t) {
       		o.checkAllNodes(t);
        }
        
    </script>
