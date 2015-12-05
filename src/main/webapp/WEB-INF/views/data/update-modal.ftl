<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<div class="modal fade" id="dialogProjectUpdate">
    <div class="modal-dialog" style="width: 600px">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                <h4 class="modal-title">${title}</h4>
            </div>
            <div class="modal-body"  >
                <form id="messageinfo" action="${springMacroRequestContext.contextPath}/data/${model}/update" class="smart-form" method="post">
               		<input type="hidden" name="uid" value="${(entry.uid)!''}" />
                    <fieldset>
						<@section titles names />
                    </fieldset>
                </form>
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
            $('#dialogProjectUpdate').modal("show");

            /** 校验字段  **/
            var validator =$('#messageinfo').validate({
                <@validate titles names />
            });

            /* 提交按钮单击事件  */
            $('#msubmit').click(function(){
                if($('#messageinfo').valid()){
                    $('#msubmit').attr("disabled",true);
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
                        $("table.dataTable").DataTable().ajax.reload(null,false);;
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
                    $('#dialogProjectUpdate').modal("hide");
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
                        $('#dialogProjectUpdate').modal('hide');
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
            $('#dialogProjectUpdate').on('hide.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
    			$("#dialogProjectUpdate").remove();
    		});
        });

    </script>
<#macro section titles names>
	<#list titles as t>
      		<section>
     			<div class="row">
        		    <label class="label col col-2">${t}</label>
      			    <div class="col col-6">
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

