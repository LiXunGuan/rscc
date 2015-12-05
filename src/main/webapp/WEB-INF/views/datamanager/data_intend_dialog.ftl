<div class="modal fade" id="dialog-data">
    <div class="modal-dialog" style="width: 400px">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                
                <#if target?? && target == "1">
	                <h4 class="modal-title">标记为意向客户</h4>
                <#else>
    	            <h4 class="modal-title">归还数据</h4>
                </#if>
            </div>
            <div class="modal-body">
                <form id="dialog-data-form" class="smart-form" method="post" action="${springMacroRequestContext.contextPath}/newuserdata/changeIntend">
                	<section>
						<div class="row">
							<label class="label col col-3 ">意向类型</label>
							<input type="hidden" value="${phoneNumber!''}" name="phoneNumber" id="phoneNumber">
							<div class="input col col-8">
								<label class="select"> 
									<select class="select" id="intend" name="intend">
										<#if datas??>
											<#list datas as d>
												<#if d.intentName == intentType >
												<option selected="selected" value="${(d.uuid)!''}">${(d.intentName)!''}</option>
												<#else>
												<option value="${(d.uuid)!''}">${(d.intentName)!''}</option>
												</#if>
											</#list>
<!-- 												<option value="nointent">没有意向</option> -->
										<#else>
											<option value="">暂无批次数据可以获取</option>
										</#if>
									</select>
								</label>
							</div>	
						</div>
					</section>
                </form>
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

            /* 显示弹框  */
            $('#dialog-data').modal("show");
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
                },
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
                        $('table.dataTable').DataTable().ajax.reload(null,false);;
                    }
                    $('#dialog-data').modal("hide");
//                     validator.resetForm();
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

                        $('table.dataTable').DataTable().ajax.reload(null,false);;
                    }
                }
            });
            
            /* 关闭窗口的回调函数  */
            $('#dialog-data').on('hide.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
    			$("#dialog-data").remove();
    		});
        });
        
    </script>
</div>