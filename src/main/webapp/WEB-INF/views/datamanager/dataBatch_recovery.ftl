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
<div class="modal fade" id="dialogrecData">
    <div class="modal-dialog" style="width: 700px">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                <h4 class="modal-title">回收数据</h4>
            </div>
            <div class="modal-body">
                <form id="messageinfoa" action="${springMacroRequestContext.contextPath}/databatch/data/batRecoveryData" class="smart-form" method="post">
                <header>
                	当前数据源：${entry.batchName}，共有数据${entry.dataCount}条，已分配${entry.ownCount}条
				</header>
                <fieldset>
					<section>
						<input type="hidden" name="dataTable" value="${entry.dataTable}">
						<label class="label">选择回收方案</label>
<!-- 						<label class="col-md-1"></label> -->
						<div class="row">
									<div class="col">
										<label class="radio">
											<input type="radio" class="radiobox style-0" name="collection" value="0" checked="checked">
											<span class="hasInput">回收未完成数据</span> 
										</label>
									</div>
									<div class="col">
										<label class="radio">
											<input type="radio" class="radiobox style-0" name="collection" value="1">
											<span class="hasInput">回收已完成数据</span> 
										</label>
									</div>
									<div class="col">
										<label class="radio">
											<input type="radio" class="radiobox style-0" name="collection" value="2">
											<span class="hasInput">回收所有数据</span> 
										</label>
									</div>
								</div>
					</section>
                    <div>
						<select multiple="multiple" id="userDuallistbox" class="form-control" name="users">
						
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
				selectedListLabel: '待回收人员清单',
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
            $('#dialogrecData').modal("show");

            /* 校验字段  */
            var validator =$('#messageinfoa').validate({
            });

            /* 提交按钮单击事件  */
			$('#msubmita').click(function(){
                if($('#messageinfoa').valid()){
                    $('#msubmita').attr("disabled",true);
                    $('#messageinfoa').submit();
                }
            });
            
            var inputMax = $("#allocateMax");
            $("input[name=allocate]").change(function(){
            	if($(this).next().hasClass("hasInput")){
            		$(this).closest("div.row").append(inputMax);
            		inputMax.find("input").focus();
            	}
            	else
            		$("#allocateMax").remove();
            });
            
            var dataMax = $("#dataMax").remove();
            $("input[name=dataType]").change(function(){
            	if($(this).next().hasClass("hasInput")){
            		$(this).closest("div.row").append(dataMax);
            		dataMax.find("input").focus();
            	}
            	else
            		$("#dataMax").remove();
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

	
    

