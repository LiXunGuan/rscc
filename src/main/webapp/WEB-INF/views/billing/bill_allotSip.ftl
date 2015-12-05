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
<div class="modal fade" id="allotData">
    <div class="modal-dialog" style="width: 700px">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                <h4 class="modal-title">分配分机</h4>
            </div>
            <div class="modal-body">
                <form id="rateAllotForm" action="${springMacroRequestContext.contextPath}/billing/rate/allotSipuserData" class="smart-form" method="post">
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
						<select multiple="multiple" id="userDuallistbox" class="form-control" name="sipusers">
							<#list siplist as u>
								<#if selfsid?seq_contains(u.sipId)>
									<option value=${u.sipId} selected='selected'>${u.sipId}</option>
								<#elseif !(unselsipnum?seq_contains(u.sipId))>
									<option value=${u.sipId}>${u.sipId}</option>
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

	        userlist = $('#userDuallistbox').bootstrapDualListbox({
				nonSelectedListLabel: '现有分机清单',
				selectedListLabel: '已分配分机清单',
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
            $('#allotData').modal("show");

            /* 提交按钮单击事件  */
			$('#msubmita').click(function(){
                if($('#rateAllotForm').valid()){
                	/*if($("#userDuallistbox :selected").length == 0){
                		alert("请选择要分配的分机！");
                		return false;
                	}*/
                    $('#msubmita').attr("disabled",true);
                    $('#rateAllotForm').submit();
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
            
            $('#rateAllotForm').ajaxForm({
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
                    }
                    $('#allotData').modal("hide");
                    localtion.reload();
                },
                submitHandler : function(form) {
                    $(form).ajaxSubmit({
                        success : function() {
                            $("#rateAllotForm").addClass('submited');
                        }
                    });
                },
                error: function(XMLHttpRequest,textStatus , errorThrown){
                    if(textStatus=='error'){
                        $('#allotData').modal('hide');
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
            $('#allotData').on('hide.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
    			$("#allotData").remove();
    		});
        });
        
    </script>
</div>
