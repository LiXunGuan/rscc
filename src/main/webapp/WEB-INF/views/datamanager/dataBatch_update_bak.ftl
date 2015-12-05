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
	                <section>
		                <div class="row" >
	              			<div class="col col-2">
			        		    <label class="label">部门</label>
		              		</div>
		              		<div class="col col-10">
		     				<#list departments as d>
		              		<div class="row">
			     				<div class="col col-4">
										<label class="checkbox">
										
										<input type="checkbox" name="departments" 
											<#if dataBatchDepartmentLink??>
												<#list dataBatchDepartmentLink as dataDept>
													<#if dataDept.departmentUuid == d.uuid>
														checked="checked"
													</#if>
												</#list>
											</#if> 
											id='${d.uuid}' value=${d.uuid} onclick="addlimit(this)">
											
										<i></i>${d.departmentName}
									</label>
								</div>
							</div> 
							</#list>
							<div class="col col-8 added" style="display:none;" id="adddept">
								 单次上限
								&nbsp;&nbsp;
								<input class= "col-2" id="singlelimit" name="singlelimit" value="${singlelimit}" onblur="valising(this);" onfocus="if(value!=''){value=''}">
								&nbsp;
								 单日上限&nbsp;&nbsp;<input class= "col-2" id="daylimit" name="daylimit" value="${daylimit}" onblur="validay(this);" onfocus="if(value!=''){value=''}">
							</div>
						</div>
						</div>
					</section>
				</form>
	            <div class="modal-footer">
	            	<label id="validatelimit" class="hidden" style="color:red;margin-right: 20%;">单次上限必须小于单日上限</label>
			         <button id="msubmita" type="button" class="btn btn-primary"> 保  存  </button>
	                <button type="button" class="btn btn-default" data-dismiss="modal">  关 闭  </button>
	            </div>
            </div>
            </div>
     	</div>
     	
   	<script type="text/javascript">
   		
   		function addlimit(obj){
			var box= document.getElementsByName("departments");
			if (obj.checked) {
				$(obj).closest('div').after($('#adddept').clone().css('display','block'));
				$(obj).closest('div').next().children('input').val('1000');
			}else{
				$(obj).closest('div').next().remove();
			}
		}
   		
        $(document).ready(function(){
        	
        	/*  如果checkbox被选中那么后面显示上限输入框，框中显示该部门的上限 */
        	var dept = $("input[checked='checked']");
        	dept.closest('div').after($('#adddept').clone().css('display','block'));
        	
        	for(var i = 0;i<dept.size();i++){
        		<#list deptAndLimit as deptlimit>
        			var deptid = "${deptlimit}".split('-')[0];
        			if($(dept[i]).val()==deptid){
        				var a = $("#"+deptid).parent().parent().next().children()[0];
        				$(a).val('${deptlimit}'.split('-')[1]);
        				var b = $("#"+deptid).parent().parent().next().children()[1];
        				$(b).val('${deptlimit}'.split('-')[2]);
        			}
        		</#list>
        	}

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
		 				
		 				var singledata = $(this).parent().prev().children('section').eq(1).find("input[name='singlelimit']");
		 				var daydata = $(singledata).next('input');
		 				if(+$(singledata).val() > +$(daydata).val()){
		 					$("#msubmita").attr("disabled",true);
		 					$("#validatelimit").attr("class","");
		 					return false;
		 				}
		 				
		 				$.post(getContext() + 'databatch/data/update',$.param({uid:$("#uid").val(),batchName:$("#newDataName").val(),
		 					undepartments:function(){
		 						var undepts = [];
		 					$("#dialogData [name='departments']:unchecked").each(function(k,v){
		 						undepts.push(v.value);
		 					})
		 					return undepts;
		 				},departments:(function(){
		 					var d = $("#dialogData [name='departments']:checked");
		 					var l = [];
		 					d.each(function(k,v){
		 						var singval = $(v).closest("div").next("div").children("[id='singlelimit']").val();
		 						var dayval = $(v).closest("div").next("div").children("[id='daylimit']").val();
		 						
		 						l.push(v.value+"-"+singval+"-"+dayval);
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
        function valising(data){
        	
			  var singlim = $(data).val();
		      var daylim = $(data).next('input').val();

		      if(singlim > daylim){
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
			  
			  if(singlim > daylim){
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

