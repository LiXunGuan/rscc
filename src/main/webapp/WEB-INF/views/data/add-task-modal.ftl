<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<div class="modal fade" id="dialogTask">
    <div class="modal-dialog" style="width: 600px">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                <h4 class="modal-title">${title}</h4>
            </div>
            <div class="modal-body"  >
                <form id="messageinfoa" action="${springMacroRequestContext.contextPath}/data/${model}/save" class="smart-form" method="post">
                    <fieldset>
						<section>
                            <div class="row">
                                <label class="label col col-2 ">任务名</label>
                                <div class="col col-6">
                                    <label class="input">
                                        <input name="taskTable" value="" onkeyup="$('#resultTable').val($(this).val());">
                                    </label>
                                </div>
                            </div>
                        </section>
                        <section>
                            <div class="row">
                                <label class="label col col-2 ">结果名</label>
                                <div class="col col-6">
                                    <label class="input">
                                        <input id="resultTable" name="resultTable" value="" disabled="disabled">
                                    </label>
                                </div>
                            </div>
                        </section>
                        <section>
                            <div class="row">
                                <label class="label col col-2 ">归属项目</label>
                                <div class="col col-6">
                                    <label class="select">
										<select name="projectUuid" id="projectUuid">
											<option value="">---请选择---</option>
											<#if projects??>
												 <#list projects as key>
												 	<option value="${key['uid']}">${key['projectName']}</option>
												 </#list>
											</#if>
										</select>
                                    </label>
                                </div>
                            </div>
                        </section>
                    </fieldset>
                </form>
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
     	
   	<script type="text/javascript">
        $(document).ready(function(){
        	
            /* 显示弹框  */
            $('#dialogTask').modal("show");

            /** 校验字段  **/
            var validator =$('#messageinfoa').validate({
                rules : {
                	taskTable : {
                		required : true
                	},
                	resultTable : {
                		required : true
                	},
                	projectUuid : {
                		required : true
                	}
                },
                messages : {
                	taskTable : {
                		required : '必须输入任务名'
                	},
                	resultTable : {
                		required : '必须输入结果名'
                	},
                	projectUuid : {
                		required : '必须选择项目!'
                	}
                },
                errorPlacement : function(error, element) {
                    error.insertAfter(element.parent());
                }
            });

            /* 提交按钮单击事件  */
			$('#msubmita').click(function(){
                if($('#messageinfoa').valid()){
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
                        $("#projectName").change();
                        $('#msubmita').attr("disabled",false);
                        $("table.dataTable").DataTable().ajax.reload(null,false);;
                    }
                    $('#dialogTask').modal("hide");
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
                        $('#dialogTask').modal('hide');
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
            $('#dialogTask').on('hide.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
    			$("#dialogTask").remove();
    		});
        });
        
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

	
    

