<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/css/metroStyle/metroStyle.css" type="text/css">
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/select2/select2.min.js"></script>
<style>
#trees{
	padding-left: 20px;
}
#department{
	white-space: pre;
}
<#if model=="datarange">
#range
{
	display:none;
}
</#if>

.select2-hidden-accessible{
	display: none;
}
</style>
<div class="modal fade" id="dialog${model}Update">
    <div class="modal-dialog" style="width: 550px">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                <h4 class="modal-title">${title}</h4>
            </div>
            <div class="modal-body"  >
                <form id="messageinfo" action="${springMacroRequestContext.contextPath}/user/${model}/update" class="smart-form" method="post">
               		<fieldset>
					<input type="hidden" name="uid" value="${(entry.uid)!''}" />
					<input type="hidden" id="groups" name="groups" />
                   	<input type="hidden" id="permissionRoles" name="permissionRoles" />
                   	<input type="hidden" id="datarangeRoles" name="datarangeRoles" />
                   	<input type="hidden" id="permissions" name="permissions" />
                   	<input type="hidden" id="dataranges" name="dataranges" />
                    

						<@section titles names />
						<!-- 如果不存在，表名是添加  要选择上级部门 -->
						<#if model=="user">
							<section>
	                            <div class="row">
	                                <label class="label col col-3 control-label">归属部门<sup>*</sup></label>
	                                <div class="col col-8">
	                                    <label class="select">
											<select name="department" id="department">
												 <option value="" >---请选择---</option>
											</select>
	                                    </label>
	                                </div>
	                            </div>
	                        </section>
						</#if>
						<#if (actions)??>
							<section>
	                            <div class="row">
	                                <label class="label col col-3 control-label">${title[2..]}</label>
	                                <div class="col col-8">
	                                    <label class="select">
											<select name="permission" id="permission">
												 <option value="0"  selected="selected">---请选择---</option>
												 <#list actions as a>
													 <#if a.uid==entry.permission>
													 	<option value="${a.uid}" selected="selected">${a.actionName}</option>
													 <#else>
												 		<option value="${a.uid}">${a.actionName}</option>
											 		 </#if>	
												 </#list>
											</select>
	                                    </label>
	                                </div>
	                            </div>
	                        </section>
						</#if>
						<#if (types)??>
							<section>
	                            <div class="row">
	                                <label class="label col col-3 control-label">类型</label>
	                                <div class="col col-8">
	                                    <label class="select">
											<select name="typeUuid" id="typeUuid">
												 <option value="0"  selected="selected">---请选择---</option>
												 <#list types as a>
												 	<#if a.uid==entry.typeUuid>
													 	<option value="${a.uid}" selected="selected">${a.typeName}</option>
													 <#else>
												 		<option value="${a.uid}">${a.typeName}</option>
											 		 </#if>	
												 </#list>
											</select>
	                                    </label>
	                                </div>
	                            </div>
	                        </section>
						</#if>
						<#if model=="datarange" && entry.uid!="0">
							<section>
	                            <div class="row">
	                                <label class="label col col-3 control-label">上级${title[2..]}</label>
	                                <div class="col col-8">
	                                    <label class="select">
											<select name="parentUuid" id="parentUuid">
											</select>
	                                    </label>
	                                </div>
	                            </div>
	                        </section>
						</#if>
                    </fieldset>
                </form>
			<div id="trees" class="row">
                <#if groupTree??>
                <div class="col-md-6">
                	<label class="label label-info col col-3 control-label">所属分组</label>
                	<div class="btn btn-xs btn-primary" onclick="checkAll(gtreeObj,true)"><span class="glyphicon glyphicon-ok"></span></div>
                	<div class="btn btn-xs btn-primary" onclick="checkAll(gtreeObj,false)"><span class="glyphicon glyphicon-remove"></span></div>
               		<div id="gtree" class="ztree" ></div>
             	</div>
				</#if>
                <#if permissionRoleTree??>
                <div class="col-md-6">
                	<label class="label label-info col col-3 control-label">所属角色</label>
                	<div class="btn btn-xs btn-primary" onclick="checkAll(prtreeObj,true)"><span class="glyphicon glyphicon-ok"></span></div>
                	<div class="btn btn-xs btn-primary" onclick="checkAll(prtreeObj,false)"><span class="glyphicon glyphicon-remove"></span></div>
               		<div id="prtree" class="ztree" ></div>
               	</div>
				</#if>
                <#if datarangeRoleTree??>
                <div class="col-md-6">
                	<label class="label label-info col col-3 control-label">所属范围角色</label>
               		<div class="btn btn-xs btn-primary" onclick="checkAll(drtreeObj,true)"><span class="glyphicon glyphicon-ok"></span></div>
                	<div class="btn btn-xs btn-primary" onclick="checkAll(drtreeObj,false)"><span class="glyphicon glyphicon-remove"></span></div>
               		<div id="drtree" class="ztree" ></div>
               		</div>
				</#if>
				<#if permissionTree??>
                <div class="col-md-6">
                	<label class="label label-info label col col-3 control-label">包含权限</label>
	               	<div class="btn btn-xs btn-primary" onclick="checkAll(ptreeObj,true)"><span class="glyphicon glyphicon-ok"></span></div>
                	<div class="btn btn-xs btn-primary" onclick="checkAll(ptreeObj,false)"><span class="glyphicon glyphicon-remove"></span></div>
               		<div id="ptree" class="ztree" ></div>
               		</div>
				</#if>
				<#if datarangeTree??>
                <div id="range" class="col-md-6">
                	<label class="label label-info col col-3 control-label">管辖范围</label>
	               	<div class="btn btn-xs btn-primary" onclick="checkAll(dtreeObj,true)"><span class="glyphicon glyphicon-ok"></span></div>
                	<div class="btn btn-xs btn-primary" onclick="checkAll(dtreeObj,false)"><span class="glyphicon glyphicon-remove"></span></div>
               		<div id="dtree" class="ztree" ></div>
               		</div>
				</#if>
              	</div>

            </div>
            <div class="modal-footer">
                <button id="msubmit" type="button" class="btn btn-primary">
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
		$("#department").select2({
        		width:"100%",
        	});
        	$("#parentUuid").select2({
        		width:"100%",
        	});
        	
	        var setting = {
	            check: {
	                enable: true
	            },
	            data: {
	                simpleData: {
	                    enable: true
	                }
	            }
	        };
	        
	        <#if groupTree??>
		        var zNodes =${groupTree};
		        gtreeObj = $.fn.zTree.init($("#gtree"), setting, zNodes);
			</#if>
			<#if permissionRoleTree??>
		        var zNodes =${permissionRoleTree};
		        prtreeObj = $.fn.zTree.init($("#prtree"), setting, zNodes);
			</#if>
	        <#if datarangeRoleTree??>
	        	var zNodes =${datarangeRoleTree};
	        	drtreeObj = $.fn.zTree.init($("#drtree"), setting, zNodes);
			</#if>
			<#if permissionTree??>
	    		var zNodes =${permissionTree};
	    		ptreeObj = $.fn.zTree.init($("#ptree"), setting, zNodes);
			</#if>
			<#if datarangeTree??>
		    	var zNodes =${datarangeTree};
		    	dtreeObj = $.fn.zTree.init($("#dtree"), setting, zNodes);
				if($("#department").length>0) {
			    	$("#department").empty();
			    	nodesToOption(dtreeObj.getNodes(),$("#department"));
			    	$("#department").val("${(entry.department)!''}").trigger("change");
		    	}
		    	if($("#parentUuid").length>0) {
		    		$("#range").hide();
		    		$("#parentUuid").empty();
		    		nodesToOption(dtreeObj.getNodes(),$("#parentUuid"));
			    	$("#parentUuid").val("${(entry.parentUuid)!''}").trigger("change");
		    	}
			</#if>
			
			<#if group??>
				<@checkNode treeName=group treeObj="gtreeObj" />
			</#if>
			<#if permissionRole??>
				<@checkNode treeName=permissionRole treeObj="prtreeObj" />
			</#if>
			<#if datarangeRole??>
				<@checkNode treeName=datarangeRole treeObj="drtreeObj" />
			</#if>
			<#if permission??>
				<@checkNode treeName=permission treeObj="ptreeObj" />
			</#if>
			<#if datarange??>
				<@checkNode treeName=datarange treeObj="dtreeObj" />
			</#if>
    		
	/* 禁用空白自动关闭*/
            $('#dialog${model}Update').modal({backdrop: 'static'});	
            /* 显示弹框  */
            $('#dialog${model}Update').modal("show");

            /** 校验字段  **/
            var validator =$('#messageinfo').validate({
                <@validate titles names />
            });

            /* 提交按钮单击事件  */
            $('#msubmit').click(function(){
                if($('#messageinfo').valid()){
                    $('#msubmit').attr("disabled",true);
                    <#if groupTree??>
						<@treeString tree="groups" treeobj="gtreeObj" />
	                </#if>
	                <#if permissionRoleTree??>
	                	<@treeString tree="permissionRoles" treeobj="prtreeObj" />
	                </#if>
	                <#if datarangeRoleTree??>
	                	<@treeString tree="datarangeRoles" treeobj="drtreeObj" />
	                </#if>
	                <#if permissionTree??>
	                	<@treeString tree="permissions" treeobj="ptreeObj" />
	                </#if>
	                <#if datarangeTree??>
		            	<@treeString tree="dataranges" treeobj="dtreeObj" />
		            </#if>
                    $('#messageinfo').submit();
                }
            });

            $('#messageinfo').ajaxForm({
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
                        $('#msubmit').attr("disabled",false);
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
                    $('#dialog${model}Update').modal("hide");
                    validator.resetForm();
                },
                submitHandler : function(form) {
                    $(form).ajaxSubmit({
                        success : function() {
                            $("#messageinfo").addClass('submited');
                        }
                    });
                },
                error: function(XMLHttpRequest, textStatus, errorThrown){
                    if(textStatus=='error'){
                        $('#dialog${model}Update').modal('hide');
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
            $('#dialog${model}Update').on('hide.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
    			$("#dialog${model}Update").remove();
    		});
            
            //$(".ztree").hide();
            $(".tree").click(function(){
            	//$(".tree").removeClass("bg-color-red");
            	//$(this).addClass("bg-color-red");
            })
            
            <#if (types)??>
    		$("#typeUuid").change(function(){
    			$.post(getContext() + "user/${model}/type",{"uuid":$("#typeUuid").val()},function(data){
            		$("#parentUuid").empty();
    				$("#parentUuid").append("<option value='0'>---请选择---</option>");
    				for(var i in data){
    					$("#parentUuid").append("<option value='" + data[i].uid + "'>" + data[i].datarangeName + "</option>")
   					}
   				},"json")
   			});
			</#if>
        });
        
        function showTree(o,t) {
        	//$(".ztree").hide();
        	//$(o).show();
        }
        
        function checkAll(o,t) {
        	//$(".ztree").hide();
        	//所有的都选中时，取消所有选择
        		o.checkAllNodes(t);
        }

		function nodesToOption(nodes,dom) {
        	for(var i in nodes) {
        		if(nodes[i].id==$("[name=uid]").val())
        			continue;
	        	var option = $('<option></option>');
	        	option.val(nodes[i].id).text(new Array(nodes[i].level * 3).join("-") + nodes[i].name);
	        	dom.append(option);
	        	nodesToOption(nodes[i].children,dom);
        	}
        }
    </script>
<#macro section titles names>
	<#list titles as t>
      		<section>
     			<div class="row">
        		    <label class="label col col-3 control-label">${t}<sup>*</sup></label>
      			    <div class="col col-8">
					<label class="input">
						<input name="${names[t_index]}" value="${(entry[names[t_index]])!''}" />
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
		<#if model="user">
		department : {
			required : true
		}
		</#if>
       },
       messages: {
       <#list names as n>
       	${n}: {
       		required : "必须输入${titles[n_index]}"
       	},
     	</#list>
     	<#if model="user">
		department : {
			required : "必须选择归属部门"
		}
		</#if>
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
<#macro checkNode treeName treeObj>
	<#if entry??>
		<#list treeName as t>
	        if("${t}"){
	            var cn= ${treeObj}.getNodeByParam("id", "${t}", null);
	            if(!cn.isParent) {
	                ${treeObj}.checkNode(cn, true, true);
	            }
	        }
	    </#list>
	</#if>
</#macro>

