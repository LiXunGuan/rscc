
<style>
	.smart-form .label {display: inline-block;}
	.tag.label.label-info {color: white;}
</style>

<div class="modal fade" id="dialog_cstm_pool_tags">
    <div class="modal-dialog" style="width: 600px">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                <h4 class="modal-title">客户池管理</h4>
            </div>
            <div class="modal-body"  >

                <form id="poolInfos" action="${springMacroRequestContext.contextPath}/cstmpool/save" class="smart-form" method="post">
                    <fieldset>
                        <section>
                            <div class="row">
                                <label class="label col col-2">池名称</label>
                                <div class="input col col-8">
                                    <label class="input">
                                        <input type="hidden" name="uid" id="uid" value="${(customerpool.uid)!''}">
                                        <input name="poolName" value="${(customerpool.poolName)!''}">
                                    </label>
                                </div>
                            </div>
                        </section>
                        <section>
                            <div class="row">
                                <label class="label col col-2">池描述</label>
                                <div class="input col col-8">
                                    <label class="input">
<!--                                         <input name="poolDes" value="${(customerpool.poolDes)!''}"> -->
                                        <textarea rows="" cols="" style="height: 150px;width: 100%;" name="poolDes" id="poolDes">${(customerpool.poolDes)!''}</textarea>
                                    </label>
                                </div>
                            </div>
                        </section>
                        <section>
                            <div class="row">
                                <label class="label col col-2">归属部门</label>
                                <div class="col col-8">
<!--                                 	<label class="checkbox"> -->
<!-- 										<input type="checkbox" name="departments" onclick="checkall(this)"> -->
<!-- 										<i></i><b>全部部门</b></label> -->
	                                <#list departments as d>
		                                <#if selectedList??>
		                                	<#if selectedList?seq_contains(d.uuid)>
												<label class="checkbox">
													<input type="checkbox" name="departments" checked="checked" value=${d.uuid}>
													<i></i>${d.datarangeName}</label>
											<#else>
												<label class="checkbox">
													<input type="checkbox" name="departments" value=${d.uuid}>
													<i></i>${d.datarangeName}</label>
											</#if>
										<#else>
											<label class="checkbox">
												<input type="checkbox" name="departments" value=${d.uuid}>
												<i></i>${d.datarangeName}</label>
										</#if>
									</#list>
                                </div>
                            </div>
                        </section>
                         
                    </fieldset>
                </form>

            </div>
            <div class="modal-footer">
                <button id="msubmit" type="button" class="btn btn-primary">
                    保  存
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    关 闭
                </button>
            </div> 
        </div>
    </div>
    
    <script src= "${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-timepicker/bootstrap-timepicker.min.js"></script>
    
    <script type="text/javascript">
    
        $(document).ready(function(){
			
            /* 显示弹框  */
            $('#dialog_cstm_pool_tags').modal({
            	show : true,
            	backdrop : "static"
            });

            /** 校验字段  **/
            var validator =$('#poolInfos').validate({
                rules : {
                	poolName  :  {
                		required : true,
                		maxlength : 16,
						remote : {
							type : "post",
                			url : getContext() + 'cstmpool/checkPoolNameRepeat',
                			data : { uid : function(){ return $('#uid').val(); }}
                		}
                	}
                },
                messages : {
                	poolName :{
                		required : "${c.validate_required}",
                		maxlength :"${c.validate_maxlength}",
                	}
                },
                errorPlacement : function(error, element) {
                    error.insertAfter(element.parent());
                }
            });

            /* 提交按钮单击事件  */
            $('#msubmit').click(function(){
                if($('#poolInfos').valid()){
//                     $('#msubmit').attr("disabled",true);
                    $('#poolInfos').submit();
                }
            });

            $('#poolInfos').ajaxForm({
                dataType:"json",
                beforeSubmit:function(formData, jqForm, options){
                	$("#dialog_cstm_pool_tags [name='departments']:unchecked").each(function(k,v){
                		formData.push({name:"undepartments",value:v.value});
                	})
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
						
                        $("#hiddenPoolId").val(data.pool_uuid);
                        $("#successTag").val(data.success);
                    }
                    $('#dialog_cstm_pool_tags').modal("hide");
                },
                submitHandler : function(form) {
                    $(form).ajaxSubmit({
                        success : function() {
                            $("#poolInfos").addClass('submited');
                        }
                    });
                },
                error: function(XMLHttpRequest,textStatus , errorThrown){
                    if(textStatus=='error'){
                        $('#dialog_cstm_pool_tags').dialog('hide');
                        $.smallBox({
                            title : "操作失败",
                            content : "<i class='fa fa-clock-o'></i> <i>操作失败</i>",
                            color : "#C46A69",
                            iconSmall : "fa fa-times fa-2x fadeInRight animated",
                            timeout : 2000
                        });

                        
                        $('#dt_basic_pool').DataTable().ajax.reload(null,false);;
                    }
                }
            });
        });
        
        $('#dialog_cstm_pool_tags').on('hide.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
        
        	addNewTabs();
		});
        
        
        function checkall(thiz){
        	
        	if($("#dialog_cstm_pool_tags [name='departments']:checked").first().val()=="on"){
        		
        		$("#dialog_cstm_pool_tags [name='departments']:gt(0)").each(function(k,v){
        			
        			$(v).parent('label').css("display","none");
        		});
        		
        	}else{

        		$("#dialog_cstm_pool_tags [name='departments']:gt(0)").each(function(k,v){
        			
        			$(v).parent('label').css("display","block");
       			});
        		
        	}
        	
        }
        
        
        $('#dialog_cstm_pool_tags').on('hidden.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
        	$("#dialog_cstm_pool_tags").remove();
        	$('#dt_basic_pool').DataTable().ajax.reload(null,false);;
        	 
		});
        /* 添加Tab 标签 */
        function addNewTabs(){
        	
        	if($("#forModal")!="undifined"){
        		if($("#successTag").val()=="true"){
	        		$("#liID").before('<li><a href="#'+ $("#hiddenPoolId").val() +'" >'+ $("input[name='poolName']").val() +'</a></li>');
// 	        		<#assign pool_id>$("#hiddenPoolId").val()</#assign>
// 	        		<#assign pool_des>$('input[name='poolDes']').val()</#assign>
	        		$("div[name='tab-contents']").after();
					
					window.location.reload(true);
        		}
        	}
        }
        
// 		$('#startDate').datepicker({
// 			dateFormat : 'yy-mm-dd'
// 		});
		$('#startDate').datetimepicker({
            format: "yyyy-mm-dd hh:ii",
            weekStart: 1,
            todayBtn:  1,
    		autoclose: 1,
    		todayHighlight: 1,
    		startView: 2,
    		forceParse: 0,
            showMeridian: 1
    	});

    </script>






