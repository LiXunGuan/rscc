<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
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
.col-md-8 .row:nth-child(even) div.col
{
	padding-right:0px;
}
/* .col-md-8 .row div.col */
/* { */
/* 	padding-right:0px; */
/* } */
</style>
<div class="modal fade" id="dialogData">
    <div class="modal-dialog" style="width: 600px">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                <h4 class="modal-title">分配数据</h4>
            </div>
            <div class="modal-body">
                <form id="messageinfoa" action="${springMacroRequestContext.contextPath}/data/project/allocate" class="smart-form" method="post">
                <fieldset>
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 show-stats">
						<div class="col-xs-6 col-sm-6 col-md-12 col-lg-12"> <span class="text"> 数据状态（未分配数量/总量） <span id="taskStat" class="pull-right">${unallocateTask}/${totalTask}</span> </span>
							<div class="progress">
								<div class="progress-bar bg-color-blueDark" id="taskProgress" style="width: ${(unallocateTask/totalTask)?string.percent};"></div>
							</div> 
						</div>
						<div class="col-xs-6 col-sm-6 col-md-12 col-lg-12"> <span class="text"> 人员状态（项目人员数量/总量）<span id="userStat" class="pull-right">${projectUser}/${totalUser}</span> </span>
							<div class="progress">
								<div class="progress-bar bg-color-blue" id="userProgress" style="width: ${(projectUser/totalUser)?string.percent};"></div>
							</div> 
						</div>
						<div class="col-xs-6 col-sm-6 col-md-12 col-lg-12"> <span class="text"> 完成状态（已完成数据/总量） <span id="completeStat" class="pull-right">${completeTask}/${totalTask-unallocateTask}</span> </span>
							<div class="progress">
								<#if totalTask-unallocateTask==0>
								<div class="progress-bar bg-color-greenLight" id="taskProgress" style='width: 0%;'></div>
								<#else>
								<div class="progress-bar bg-color-greenLight" id="taskProgress" style='width: ((completeTask)/(totalTask-unallocateTask))?string.percent)};'></div>
								</#if>
							</div> 
						</div>
					</div>
					<div>
						<input type="hidden" name="uuid" value="${uuid}">
						<label class="col-md-2 control-label">选择分配方案</label>
						<label class="col-md-1"></label>
						<div class="col-md-8">
							<div class="row">
								<div class="col">
									<label class="radio">
										<input type="radio" class="radiobox style-0" name="allocate" value="0">
										<span>平均分配给每个人</span> 
									</label>
								</div>
							</div>
							<div class="row">
								<div class="col">
									<label class="radio">
										<input type="radio" class="radiobox style-0" name="allocate" value="1">
										<span class="hasInput">给每个人分配：</span> 
									</label>
								</div>
							</div>
							<div class="row">
								<div class="col">
									<label class="radio">
										<input type="radio" class="radiobox style-0" checked="checked" name="allocate" value="2">
										<span>平均每个人的现有数据</span> 
									</label>
								</div>
							</div>
							<div class="row">
								<div class="col">
									<label class="radio">
										<input type="radio" class="radiobox style-0" name="allocate" value="3">
										<span class="hasInput">每人分配至：</span> 
									</label>
								</div>
							</div>
						</div>
						<div class="col col-3" id="allocateMax" style="padding-left:0px">
							<label class="input">
								<span class="icon-append">条</span>
								<input type="text" name="allocateMax">
							</label>
						</div>
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
            var validator =$('#messageinfoa').validate({
            });

            /* 提交按钮单击事件  */
			$('#msubmita').click(function(){
                if($('#messageinfoa').valid()){
                    $('#msubmita').attr("disabled",true);
                    $('#messageinfoa').submit();
                }
            });
            
            var inputMax = $("#allocateMax").remove();
            $("input[type=radio]").change(function(){
            	if($(this).next().hasClass("hasInput")){
            		$(this).closest("div.row").append(inputMax);
            		inputMax.find("input").focus();
            	}
            	else
            		$("#allocateMax").remove();
            })
            
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

	
    

