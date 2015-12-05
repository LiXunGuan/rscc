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

</style>
<div class="modal fade" id="allotSkillData">
    <div class="modal-dialog" style="width: 700px">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                <h4 class="modal-title">分配技能组</h4>
            </div>
            <div class="modal-body">
                <form id="rateAllotSkillForm" action="${springMacroRequestContext.contextPath}/billing/rate/allotSkillGroupData" class="smart-form" method="post">
                <fieldset>
					<section>
						<input type="hidden" name="uuid" id="uuid" value="${uuid!''}">

						<div class="col col-5" id="dataGetMax" style="padding-left:0px">
							<label class="input">
								<span class="icon-append">条</span>
								<input type="text" name="dataMax" class="required">
							</label>
						</div>
					</section>

                    <div>
						<select multiple="multiple" id="userDuallistbox" class="form-control" name="skills">
							<#list skills as s>
								<#if selskid?seq_contains(s.id)>
								<option value=${s.id}#${s.name} selected='selected'>${s.name}</option>
								<#elseif !(bslinkskillid?seq_contains(s.id))>
								<option value=${s.id}#${s.name} >${s.name}</option>
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

	 allotskill = $('#userDuallistbox').bootstrapDualListbox({
				nonSelectedListLabel: '现有技能组清单',
				selectedListLabel: '已分配技能组清单',
				preserveSelectionOnMove: 'moved',
				moveOnSelect: false,
				selectorMinimalHeight: "500",
				filterPlaceHolder: "筛选",
				filterTextClear: "显示全部",
				infoText: "列表包含 {1} 项",
				infoTextFiltered: '<span class="label label-warning">筛选</span> {1} 项中的 {0} 项',
				infoTextEmpty: "列表为空"
			});
	        
            /* 显示弹框  */
            $('#allotSkillData').modal("show");

            /* 提交按钮单击事件  */
			$('#msubmita').click(function(){
                if($('#rateAllotSkillForm').valid()){
                	/*if($("#userDuallistbox :selected").length == 0){
                		alert("请选择要分配的技能组！");
                		return false;
                	}*/
                    $('#msubmita').attr("disabled",true);
                    $('#rateAllotSkillForm').submit();
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
            
            $('#rateAllotSkillForm').ajaxForm({
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
                       $('#allotSkillData').modal('hide');
                    }
                    $('#allotSkillData').modal("hide");
                    validator.resetForm();
                },
                submitHandler : function(form) {
                    $(form).ajaxSubmit({
                        success : function() {
                            $("#rateAllotSkillForm").addClass('submited');
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
                    }
                }
            });
            
            /* 关闭窗口的回调函数  */
            $('#allotSkillData').on('hide.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
    			$("#allotSkillData").remove();
    		});
        });
        
    </script>
</div>


	
    

