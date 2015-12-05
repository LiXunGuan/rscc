
<style>
	
	#dialog_cstm_edit_column .smart-form .label {display: inline-block;line-height: inherit;}
	.tag.label.label-info {color: white;}
	
	/* 选择框样式 */
	.smart-form .toggle i{
		left:0px;
	}
	
</style>

<div class="modal fade" id="dialog_cstm_edit_column">
    <div class="modal-dialog" style="width: 600px">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                <h4 class="modal-title">编辑列</h4>
            </div>
            <div class="modal-body"  >

                <form id="tableInfos" action="${springMacroRequestContext.contextPath}/data/taskDesign/editSave" class="smart-form" method="post">
                    <fieldset>
                        <section>
                            <div class="row">
                                <label class="label col col-2">字段名称</label>
                                <div class="input col col-4">
                                    <label class="input">
                                        <input name="columnName" value="">
                                    </label>
                                </div>
                                <label class="label col col-2">字段类型</label>
                                <div class="col col-4">
                                     <select name="columnType" class="form-control">
                                       	<#list COLUMNTYPE?keys as key>
                                       		<option value="${key}">${COLUMNTYPE[key]}</option>
                                       	</#list>
                                     </select>
                                </div>
                            </div>
                        </section>
                        
                        <section>
                            <div class="row">
                                
                                <label class="label col col-2">字符属性</label>
                                <div class="input col col-4">
                                    <label class="input">
                                        <input name="characterProperty" value="">
                                    </label>
                                </div>
                               <label class="label col col-2">显示顺序</label>
                                <div class="input col col-4">
                                    <label class="input">
                                        <input name="orders" value="">
                                    </label>
                                </div>
                            </div>
                        </section>
                        
                        <section>
                            <div class="row">
                                
                                <label class="label col col-2">&nbsp;默&nbsp;认&nbsp;值&nbsp;</label>
                                <div class="input col col-4">
                                    <label class="input">
                                        <input name="columnValue" value="">
                                    </label>
                                </div>

                            </div>
                        </section>
                        
                        
                        <section>
                            <div class="row">
                                <label class="label col col-2">是否可查</label>
                                <div class="input col col-2">
	                                <label class="toggle">
										<input type="checkbox" name="allowSelect" checked="checked">
										<i data-swchon-text="开启" data-swchoff-text="关闭"></i>
									</label>
                                </div>
                                
                                <label class="label col col-2">是否索引</label>
                                <div class="input col col-2">
	                                <label class="toggle">
										<input type="checkbox" name="allowIndex" checked="checked">
										<i data-swchon-text="开启" data-swchoff-text="关闭"></i>
									</label>
                                </div>
                                
                                 <label class="label col col-2">是否显示</label>
                                <div class="input col col-2">
	                                <label class="toggle">
										<input type="checkbox" name="allowShow" checked="checked">
										<i data-swchon-text="开启" data-swchoff-text="关闭"></i>
									</label>
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
            $('#dialog_cstm_edit_column').modal("show");

            /** 校验字段  **/
            var validator =$('#tableInfos').validate({
                rules : {
                	
                },
                messages : {
                	parentId : '必须选择组织!'
                },
                errorPlacement : function(error, element) {
                    error.insertAfter(element.parent());
                }
            });

            /* 提交按钮单击事件  */
            $('#msubmit').click(function(){
                if($('#tableInfos').valid()){
//                     $('#msubmit').attr("disabled",true);

					console.log($("form").serialize());
                    $('#tableInfos').submit();
                }
            });

            $('#tableInfos').ajaxForm({
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
                    }
                    
                    $('#dialog_cstm_edit_column').modal("hide");
		            $('#dt_basic_cstm').DataTable().ajax.reload(null,false);;
                },
                submitHandler : function(form) {
                    $(form).ajaxSubmit({
                        success : function() {
                            $("#tableInfos").addClass('submited');
                        }
                    });
                },
                error: function(XMLHttpRequest,textStatus , errorThrown){
                    if(textStatus=='error'){
                        $('#dialog_cstm_edit_column').dialog('hide');
                        $.smallBox({
                            title : "操作失败",
                            content : "<i class='fa fa-clock-o'></i> <i>操作失败</i>",
                            color : "#C46A69",
                            iconSmall : "fa fa-times fa-2x fadeInRight animated",
                            timeout : 2000
                        });
                    }
                }
            },"json");
            
        });
        
        $('#dialog_cstm_edit_column').on('hidden.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
        	$("#dialog_cstm_edit_column").remove();
//         	history.go(0) ;
        	$('#dt_basic_cstm_des').DataTable().ajax.reload(null,false);;
		});
        
		$('#startDate').datepicker({
			dateFormat : 'yy-mm-dd'
		});

    </script>






