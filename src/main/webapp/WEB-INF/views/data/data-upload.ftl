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
span.checkbox_false_disable+a>span{
	color:red;
}
</style>
<div class="modal fade" id="dialogData">
    <div class="modal-dialog" style="width: 500px">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                <h4 class="modal-title">上传数据</h4>
            </div>
            <div class="modal-body">
                <div id="messageinfoa" class="smart-form">
                	<section>
						<div class="row">
							<label class="label col col-3 ">批次名<sup>*</sup></label>
							<div class="input col col-8">
							<form id="temp">
								<label class="input"> 
									<input id="newDataName" name="containerName" value="" onchange="clearnValid()">
								</label>
								<div id="name-error-message" class="note note-error">必须输入批次名</div>
								<div id="name-error-message1" class="note note-error">批次名已存在</div>
							</form>
							</div>
						</div>
					</section>
	                <section>
		                <div class="row">
		                    <label class="label col col-3 ">选择文件<sup>*</sup></label>
		                    <div class="col col-8">
		                        <div class="input input-file">
									<span class="button"><input id="fileupload" type="file" name="multipartFile" onchange="this.parentNode.nextSibling.value = this.value;clearfValid();" accept=".xlsx">浏览</span><input id="filetext" type="text" placeholder="" readonly="">
								</div>
								<div id="file-error-message" class="note note-error">必须选择一个文件</div>
		                    </div>
		                </div>
	                </section>
	                <section>
		                <div class="row">
	              			<div class="col col-3">
			        		    <label class="label">数据归属部门</label>
		              		</div>
		     				<div class="col col-4">
		     				<#list departments as d>
								<label class="checkbox">
									<input type="checkbox" name="departments" value=${d.uuid}>
									<i></i>${d.datarangeName}</label>
							</#list>
							</div>
	              		</div>
					</section>
					<a class="col-3" onclick="window.open('public/docs/example.xlsx')">点击下载模板文件</a>
                </div>
            </div>
            <div class="modal-footer">
            	
            	
                <button id="msubmita" type="button" class="btn btn-primary" onclick="if(!valid()) return false;">
                    上  传
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    关 闭
                </button>
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

            $("#temp").validate({
            	rules: {
            		containerName:{
						required : true,
						remote : {
            				url:"${springMacroRequestContext.contextPath}/data/data/checkname/1",
            				type:"post",
            				dataType:"json"
            			}
					}
				},
				messages: {
					containerName:{
						remote:"批次名已存在,请更换"
					}
				},
		        errorPlacement : function(error, element) {
		            error.insertAfter(element.parent());
		        }
            });
            
            /* 提交按钮单击事件  */
// 			$('#msubmita').click(function(){
//                 if(valid()){
//                     $('#msubmita').attr("disabled",true);
//                     $.post(getContext() + '/data/data/save',{dataName:$("#newDataName").val()},function(newdata){
//                     	if(newdata.success){
//                             $.smallBox({
//                                 title : "操作成功",
//                                 content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
//                                 color : "#659265",
//                                 iconSmall : "fa fa-check fa-2x fadeInRight animated",
//                                 timeout : 2000
//                             });
//     	 					$("#uuid").val(newdata.uuid);
//                             $('#msubmita').attr("disabled",false);
//                             $("table.dataTable").DataTable().ajax.reload(null,false);;
//                             $('#messageinfoa').submit();
//                         }
// //                         $('#dialogData').modal("hide");
// 	 				},"json");
//                 }
//             });
            
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
 //       	$.post(getContext() + '/data/${model}/checkname/1',{containerName:$('#newDataName').val()},function(data){
  //      		if(data=='false'){
 //       		$("#name-error-message1").show();
  //      		$("#name-error-message1").prev().addClass("state-error");
 //       		}
  //      	});
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

	
    

