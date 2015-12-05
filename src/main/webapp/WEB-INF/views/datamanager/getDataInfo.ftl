<div class="modal fade" id="dialog-data">
    <div class="modal-dialog" style="width: 400px">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                
                <#if target?? && target == "get">
	                <h4 class="modal-title">获取数据</h4>
                <#else>
    	            <h4 class="modal-title">归还数据</h4>
                </#if>
            </div>
            <div class="modal-body">
                <form id="dialog-data-form" class="smart-form" method="post" action="${springMacroRequestContext.contextPath}/newuserdata/getDataByBatchId">
                	<section>
						<div class="row" style="display: inline;">
		                	<header style="padding: 0px;">
		                		<i class="fa fa-lg fa-volume-up" style="margin-right: 5px;"></i>
								<span id="usercount">最多可获取数据：${usercount!0}条</span>
		                	</header>
						</div>
					</section>
                	<section>
						<div class="row">
							<#if target?? && target == "get">
								<label class="label col col-3 ">批次名</label>
			                <#else>
								<label class="label col col-3 ">批次名</label>
			                </#if>
							<div class="input col col-8">
								<label class="select"> 
									<select class="select" id="batchId" name="batchId" onchange="updateCount(this);">
										<#if datas??>
											<#list datas as d>
												<option value="${(d.dataBatchUuid)!''}">${(d.batchname)!''}</option>
											</#list>
										<#else>
											<option value="">暂无批次数据可以获取</option>
										</#if>
									</select>
								</label>
							</div>	
						</div>
					</section>
					<section>
						<div class="row">
							<label class="label col col-3 ">获取条数</label>
							<div class="input col col-8">
								<label class="input"> 
									<input id="countNumber" name="countNumber" value="" >
<!-- 									<input id="batchId" type="hidden" name="batchId" value="${(batchId)!''}"> -->
									<input id="ownId" type="hidden"  name="ownId" value="${(cUser.uid)!''}">
									<input id="deptId" type="hidden" name="deptId" value="${(cUser.department)!''}">
									<input id="target" type="hidden" name="target" value="${(target)!''}">
								</label>
							</div>
						</div>
					</section>
                </form>
            </div>
            <div class="modal-footer">
                <button id="msubmita" type="button" class="btn btn-primary" >
                    获 取
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    关 闭
                </button>
            </div>
     	</div>
   	</div>
     	
   	<script type="text/javascript">
        $(document).ready(function(){

            /* 显示弹框  */
            $('#dialog-data').modal({
            	show : true,
            	backdrop : "static"
            });
            /** 校验字段  **/
            var validator = $('#dialog-data-form').validate({
                rules : {
                	batchId :{
                		required:true
                	},
                	countNumber :{
                		digits : true,
                		remote : {
            				url:"${springMacroRequestContext.contextPath}/newuserdata/getValidate",
            				type:"post",
            				data : {
            					batchuuid : function(){
                					return $('#batchId').val();
                				}
                			}
            			}
                	}
                },
                messages : {
                	batchId:{
                	},
                	countNumber :{
               			remote : $.format("请修正该字段！")
                	}
                },
                errorPlacement : function(error, element) {
                    error.insertAfter(element.parent());
                }
            });
            
            <#if datas??><#else>
            	$("#msubmita").attr("disabled","disabled");
            </#if>
            
            /* 提交按钮单击事件  */
			$('#msubmita').click(function(){
                if($('#dialog-data-form').valid()){
                    $('#msubmita').attr("disabled",true);
                    $('#dialog-data-form').submit();
                }
            });
            
            $('#dialog-data-form').ajaxForm({
                dataType:"json",
                beforeSubmit:function(formData, jqForm, options){
                	$("#dialog-data [name='departments']:unchecked").each(function(k,v){
                		formData.push({name:"undepartments",value:v.value});
                	})
                },
                success: function(data) {
                    if(data.success){
                        $.smallBox({
                            title : "操作成功",
                            content : "<i class='fa fa-clock-o'></i> <i>操作成功!</i>",
                            color : "#659265",
                            iconSmall : "fa fa-check fa-2x fadeInRight animated",
                            timeout : 2000
                        });
                        $('#msubmita').attr("disabled",false);
	                       getResult();
//                         $('table.dataTable').DataTable().ajax.reload(null,false);
                    }else{
		                $.smallBox({
		                    title : "操作失败",
		                    content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>"+ data.msg +"</i>",
		                    color : "#C79121",
		                    iconSmall : "fa fa-times fa-2x fadeInRight animated",
		                    timeout : 2000
		                });
                    }
                    $('#dialog-data').modal("hide");
                    validator.resetForm();
                },
                submitHandler : function(form) {
                    $(form).ajaxSubmit({
                        success : function() {
                            $("#dialog-data-form").addClass('submited');
                        }
                    });
                },
                error: function(XMLHttpRequest,textStatus , errorThrown){
                    if(textStatus=='error'){
                        $('#dialog-data').modal('hide');
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
            $('#dialog-data').on('hide.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
    			$("#dialog-data").remove();
    		});
            
            /* 默认选中 */
            $("#batchId").trigger("change");
        });
        
        //更新
        function updateCount(thiz){
        	
        	var batchuuid = $(thiz).val();
	        $.post(getContext()+"newuserdata/updateCount",{batchUUid:batchuuid},function(d){
	        	/* 最多可获取数据：${usercount!0}条 */
	        	if(d.data < 0){
	        		$("#usercount").text("最多可获取数据："+0+"条");	        		
		        	$("#countNumber").val(0);
	        	}else{
					$("#usercount").text("最多可获取数据："+d.data+"条");
		        	$("#countNumber").val(d.data);
	        	}
	        	
	        	
	        },"json");
        	
        }
        
        
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