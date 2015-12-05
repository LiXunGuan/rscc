<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" 
	src="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-duallistbox/jquery.bootstrap-duallistbox.min.js"></script>
<style>
div.col label.input input
{
	height:25px;
}
div.col label.input span
{
	top: 1px;
	height:23px;
}
.smart-form .col-md-6
{
	width: 45%;
	padding-left: 13px;
 	padding-right: 13px;
}
.smart-form .col.col-7
{
	width: 58.3%;
}
/* .col-md-8 .row div.col */
/* { */
/* 	padding-right:0px; */
/* } */
</style>
<div class="modal fade" id="dialogData">
    <div class="modal-dialog" style="width: 700px">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                <h4 class="modal-title">分配数据</h4>
            </div>
            <div class="modal-body">
                <form id="messageinfoa" action="${springMacroRequestContext.contextPath}/data/data/allocateData" class="smart-form" method="post">
                <header>
                	当前数据源：${entry.containerName}，共有数据${entry.dataCount}条，已分配${entry.allocateCount}条
				</header>
                <fieldset>
					<section>
						<input type="hidden" name="dataTable" value="${entry.dataTable}">
						<label class="label">选择分配方案</label>
<!-- 						<label class="col-md-1"></label> -->
						<div class="row">
<!-- 							<div class="col col-5"> -->
<!-- 								<div class="row"> -->
<!-- 									<div class="col"> -->
<!-- 										<label class="radio"> -->
<!-- 											<input type="radio" class="radiobox style-0" name="dataType" value="0" checked="checked"> -->
<!-- 											<span>分配全部数据</span>  -->
<!-- 										</label> -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 								<div class="row"> -->
<!-- 									<div class="col"> -->
<!-- 										<label class="radio"> -->
<!-- 											<input type="radio" class="radiobox style-0" name="dataType" value="1"> -->
<!-- 											<span class="hasInput">最多分配：</span>  -->
<!-- 										</label> -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 							</div> -->
							<div class="col col-7">
<!-- 								<div class="row"> -->
<!-- 									<div class="col"> -->
<!-- 										<label class="radio"> -->
<!-- 											<input type="radio" class="radiobox style-0" name="allocate" value="0" checked="checked"> -->
<!-- 											<span>平均分配给每人</span>  -->
<!-- 										</label> -->
<!-- 									</div> -->
<!-- 								</div> -->
								<div class="row">
									<div class="col">
										<label class="radio">
											<input type="radio" class="radiobox style-0" name="allocate" value="1" checked="checked">
											<span class="hasInput">给每人分配：</span> 
										</label>
									</div>
									<div class="col col-4" id="allocateDataMax" style="padding-left:0px">
										<label class="input">
											<span class="icon-append">条</span>
											<input type="text" name="allocateMax" class="required">
										</label>
									</div>
								</div>
<!-- 								<div class="row"> -->
<!-- 									<div class="col"> -->
<!-- 										<label class="radio"> -->
<!-- 											<input type="radio" class="radiobox style-0" name="allocate" value="2"> -->
<!-- 											<span>平均每人的现有数据</span>  -->
<!-- 										</label> -->
<!-- 									</div> -->
<!-- 								</div> -->
								<div class="row">
									<div class="col">
										<label class="radio">
											<input type="radio" class="radiobox style-0" name="allocate" value="3">
											<span class="hasInput">给每人分配<span style="color:red">至</span>：</span> 
										</label>
									</div>
								</div>
<!-- 								<div class="row"> -->
<!-- 									<div class="col"> -->
<!-- 										<label class="checkbox"> -->
<!-- 											<input type="checkbox" name="containAll"> -->
<!-- 											<i></i>计算时包含完成数据 -->
<!-- 										</label> -->
<!-- 									</div> -->
<!-- 								</div> -->
							</div>
						</div>
						<div class="col col-5" id="dataGetMax" style="padding-left:0px">
							<label class="input">
								<span class="icon-append">条</span>
								<input type="text" name="dataMax" class="required">
							</label>
						</div>
					</section>
<!-- 				</fieldset> -->
<!-- 				<fieldset> -->
                    <div>
						<select multiple="multiple" id="userDuallistbox" class="form-control" name="users">
						<#list users as u>
							<#if addedUsers?keys?seq_contains(u.uid)>
								<option value=${u.uid} ><span style="color:red">[${u.departmentName}]</span> ${u.loginName} [未完成：${addedUsers[u.uid]}条]</option>
							<#else>
								<option value=${u.uid} ><span style="color:red">[${u.departmentName}]</span> ${u.loginName} [未完成：0条]</option>
							</#if>
						</#list>
						</select>
					</div>
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

	        userlist = $('#userDuallistbox').bootstrapDualListbox({
				nonSelectedListLabel: '现有人员清单',
				selectedListLabel: '待分配人员清单',
				preserveSelectionOnMove: 'moved',
				moveOnSelect: false,
				selectorMinimalHeight: "200",
				filterPlaceHolder: "筛选",
				filterTextClear: "显示全部",
				infoText: "列表包含 {1} 项",
				infoTextFiltered: '<span class="label label-warning">筛选</span> {1} 项中的 {0} 项',
				infoTextEmpty: "列表为空"
			});
	        
            /* 显示弹框  */
            $('#dialogData').modal("show");

            /** 校验字段  **/
            var validator =$('#messageinfoa').validate({
            });

            /* 提交按钮单击事件  */
			$('#msubmita').click(function(){
                if($('#messageinfoa').valid()){
                	if($("#userDuallistbox :selected").length == 0){
                		alert("请选择要分配的人员！");
                		return false;
                	}
                    $('#msubmita').attr("disabled",true);
                    $('#messageinfoa').submit();
                }
            });
            
            var inputMax = $("#allocateDataMax");
            $("input[name=allocate]").change(function(){
            	if($(this).next().hasClass("hasInput")){
            		$(this).closest("div.row").append(inputMax);
            		inputMax.find("input").focus();
            	}
            	else
            		$("#allocateDataMax").remove();
            });
            
            var dataMax = $("#dataGetMax").remove();
            $("input[name=dataType]").change(function(){
            	if($(this).next().hasClass("hasInput")){
            		$(this).closest("div.row").append(dataMax);
            		dataMax.find("input").focus();
            	}
            	else
            		$("#dataGetMax").remove();
            });
            
            inputMax.find("input").focus();
            
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

	
    

