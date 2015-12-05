<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/select2/select2.min.js"></script>
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
                <h4 class="modal-title">修改${title}</h4>
            </div>
            <div class="modal-body">
                <form id="messageinfoa" class="form-horizontal" action="${springMacroRequestContext.contextPath}/data/groupCall/update" method="post">
                	<input type="hidden" name="uid" id="uid" value="${entry.uid}">
                	<input type="hidden" name="groupcall_id" value="${entry.groupcall_id}">
                	<div class="form-group">
						<label class="col-md-2 control-label input-sm">任务描述<sup>*</sup></label>
						<div class="col-md-9">
							<input class="form-control input-sm" required placeholder="输入任务描述" data-bv-notempty-message="必须输入描述" data-bv-field="description" id="wzdescription" name="description" type="text" value="${(entry.description)!''}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 control-label input-sm">外呼号码</label>
						<div class="col-md-9">
							<select id="wzcaller_id_num" name="caller_id_num">
								 <option value="" selected="selected">---请选择---</option>
								 <#list accessNumbers?keys as k>
								 <option value="${k}" data-max="${accessNumbers[k]}"<#if entry.caller_id_num==k> selected="selected"</#if>>${k} - 并发上限：${accessNumbers[k]}</option>
								 </#list>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 control-label input-sm">转接队列<sup>*</sup></label>
						<div class="col-md-9">
							<select name="dst_exten" id="wzdst_exten">
								 <option value="" selected="selected">---请选择---</option>
								 <#list queues?keys as k>
								 <#if k==entry.dst_exten>
								 <option selected="selected" value="${k}">${queues[k]}</option>
								 <#else>
								 <option value="${k}">${queues[k]}</option>
								 </#if>
								 </#list>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 control-label input-sm">群呼策略<sup>*</sup></label>
						<div class="col-md-10">
							<div class="form-group">
								<div class="col-md-3">
									<div class="radio">
										<label>
											<input type="radio" class="radiobox style-0" <#if entry.strategy=="0">checked="checked"</#if> name="strategy" value="0" onclick="$(this).closest('.row').find('input:last').focus();">
											<span>静态群呼</span> 
										</label>
									</div>
								</div>
								<div class="col-md-3" style="padding-left:0px">
									<input type="text" class="form-control input-md" name="concurrency" placeholder="默认并发数:10" value=${(entry.concurrency)!""} >
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-3">
									<div class="radio">
										<label>
											<input type="radio" class="radiobox style-0" <#if entry.strategy=="1">checked="checked"</#if> name="strategy" value="1" onclick="$(this).closest('.row').find('input:last').focus();" onblur="alert(1)">
											<span>动态群呼</span> 
										</label>
									</div>
								</div>
								<div class="col-md-3" style="padding-left:0px">
<!-- 									<input class="form-control ui-spinner-input" id="ratio" name="ratio" autocomplete="off" role="spinbutton"> -->
									<select id="ratio" name="ratio">
									<option value=""></option>
									<#list 10..100 as ra>
										<#if entry.ratio==(ra/10)?string>
										<option value="${ra/10}" selected='selected'>${ra/10}</option>
										<#else>
										<option value="${ra/10}">${ra/10}</option>
										</#if>
									</#list>
									</select>
								</div>
							</div>
						</div>
					</form>
                </div>
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

            jQuery.validator.addMethod("con",function(value, element) {
				return $("[name='strategy']:checked").val()!=0 || $("#wzcaller_id_num :selected").val() == "" || +$("[name='concurrency']").val() <= +$("#wzcaller_id_num :selected").attr("data-max");
			},"不能超过并发上限");
			
			var validator = $("#messageinfoa").validate({
				rules: {
					description:{
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
					},
					dst_exten:{
						required : true
					},
					concurrency:{
						con : 10
					}
				},
				messages: {
					description:{
						remote:"群呼任务描述已存在,请更换"
					},
					concurrency:{
						con: "不能超过并发上限"
					}
				},
				errorPlacement : function(error, element) {
            		error.css({"font-style":"normal","color":"#D56161"}).insertAfter(element);
                }
			});
            
//             var validator =$('#messageinfoa').validate({
//             	rules:{
//             		description : {
//             			required:true
//             		}, 	
//             		dst_exten : {
//             			required:true
//             		}
//             	},
//             	messages:{
//             		description : {
//             			required:"必须输入描述"
//             		},
//             		dst_exten : {
//             			required:"必须选择一个分机号"
//             		}
//             	},
//             	errorPlacement : function(error, element) {
//             		error.css("color","#D56161").insertAfter(element);
//                 }
//             });
            
            /* 提交按钮单击事件  */
			$('#msubmita').click(function(){
                if($('#messageinfoa').valid()){
                    $('#msubmita').attr("disabled",true);
                    $('#messageinfoa').submit();
                }
            });
            
            $("#wzdst_exten").select2({
				width:"50%"
			});
            
			$("#wzcaller_id_num").select2({
				width:"50%"
			});
            
// 			$("#ratio").spinner({
// 				step: 0.1,
// 				min: 0.1,
// 				max: 10
// 			});
			$("#ratio").select2({
				placeholder: "默认比率:1.5",
				width:"100%"
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

	
    

