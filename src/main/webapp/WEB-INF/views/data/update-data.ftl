<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
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
    <div class="modal-dialog" style="width: 500px">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                <h4 class="modal-title">修改${title}</h4>
            </div>
            <div class="modal-body">
                <form id="messageinfoa" class="smart-form" method="post" action="${springMacroRequestContext.contextPath}/data/data/update">
                	<input name="uuid" id="uid" type="hidden" value="${entry.uid}">
                	<section>
						<div class="row">
							<label class="label col col-3 ">${name}<sup>*</sup></label>
							<div class="input col col-8">
								<label class="input"> 
									<input id="containerName" name="containerName" value="${entry.containerName}">
								</label>
							</div>
						</div>
					</section>
	                <section>
	                <div class="row${(title=='批次')?string(' hidden','')}">
	                    <label class="label col col-3 ">描述</label>
	                    <div class="input col col-8">
							<label class="input"> 
								<input id="dataInfo" name="dataInfo" value="${entry.dataInfo}">
							</label>
						</div>
	                </div>
					</section>
					<div class="row">
              			<div class="col">
		        		    <label class="label">${title}归属部门</label>
	              		</div>
	     				<div class="col col-4">
	     				<#list departments as d>
							<label class="checkbox">
								<input type="checkbox" name="departments" <#if hasDepartments?seq_contains(d.uuid)> checked="checked"</#if>value=${d.uuid}>
								<i></i>${d.datarangeName}</label>
						</#list>
						</div>
              		</div>
                </form>
            </div>
            <div class="modal-footer">
                <button id="msubmita" type="button" class="btn btn-primary" >
                    保 存
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    关 闭
                </button>
            </div>
     	</div>
   	</div>
     	
   	<script type="text/javascript">
        $(document).ready(function(){
        	
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

            /* 显示弹框  */
            $('#dialogData').modal("show");
            /** 校验字段  **/
            var validator = $('#messageinfoa').validate({
                rules : {
                	containerName : {
                		required : true,
                		remote : {
            				url:"${springMacroRequestContext.contextPath}/data/${model}/checkname/1",
            				type:"post",
            				data : {
                				uid : function(){
                					return $('#uid').val();
                				}
                			}
            			}
                	}
                },
                messages : {
                	containerName:{
                		required : "必须输入名称!",
                    	remote : "名称已经存在!"
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
                beforeSubmit:function(formData, jqForm, options){
                	$("#dialogData [name='departments']:unchecked").each(function(k,v){
                		formData.push({name:"undepartments",value:v.value});
                	})
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
                        $('#dataDataTable').DataTable().ajax.reload(null,false);;
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
        	if($("#newDataName").val()!="" && $("#filetext").val()!="")
        		return true;
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
<#macro nosection notitles nonames>
	<#list notitles as t>
      		<section>
     			<div class="row">
        		    <label class="label col col-3">${t}</label>
      			    <div class="col col-8">
					<label class="input">
						<input name="${nonames[t_index]}" />
                		</label>
              		</div>
            	</div>
        	</section>
	</#list>
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

	
    

