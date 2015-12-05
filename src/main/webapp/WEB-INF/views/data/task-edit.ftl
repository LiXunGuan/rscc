
<style>
	.smart-form .label {display: inline-block;}
	.tag.label.label-info {color: white;}
</style>


<!--
	list集合遍历
	集合 
	单个对象名 
	值的valueID 
	值得显示名字
	要选中的valueID
-->

<#macro showLable map value>
	<#if map??>
		<#list map?keys as key>
			<#if (map[key].columnType) == "DATE" >
				<section>
					<div class="row">
						<label class="label col col-2">${map[key].columnName}</label>
						<div class="input col col-8">
							<label class="input">
								<input name="${(map[key].columnNameDB)!''}" id="${(map[key].columnNameDB)!''}_b" value="${(entry[key])!''}" />
							</label>
						</div>
					</div>
				</section>
			<#else>
				<section>
					<div class="row">
						<label class="label col col-2">${map[key].columnName}</label>
						<div class="input col col-8">
							<label class="input">
								<input name="${(map[key].columnNameDB)!''}" id="${(map[key].columnNameDB)!''}_b"  value="${(entry[key])!''}">
							</label>
						</div>
					</div>
				</section>
			</#if>
		</#list>
		
		<!---------------------------------------------------- 客户标签选项 ---------------------------------------------------->
<!-- 			<section> -->
<!-- 				<div class="row"> -->
<!-- 					<label class="label col col-2">客户标签</label> -->
<!-- 					<div class="form-group col col-lg-8"> -->
<!-- 						<#if tagsStr?? && (tagsStr?length) &gt; 0 > -->
<!-- 								<input class="form-control tagsinput" style="color: white;" id="customerTags" name="customerTags" value="${tagsStr?substring(1,tagsStr?length-1)}" data-role="tagsinput"> -->
<!-- 						<#else> -->
<!-- 								<input class="form-control tagsinput" style="color: white;" id="customerTags" name="customerTags" value="" data-role="tagsinput"> -->
<!-- 						</#if> -->
<!-- 					  </div> -->
<!-- 				</div> -->
<!-- 			</section> -->
		
	</#if>
</#macro>


<div class="modal fade" id="dialog_cstm_tags">
    <div class="modal-dialog" style="width: 40%;">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                <h4 class="modal-title">任务结果</h4>
            </div>
            <div class="modal-body"  >

                <form id="taskInfo" action="${springMacroRequestContext.contextPath}/data/task/update" class="smart-form" method="post">
                    <fieldset>
                    	<input type="hidden" name="uuid" id="uuid" value="${entry.uuid}" >
                        <@showLable map entry></@showLable>
                        <input type="hidden" name="tableName" id="tableName_b" value="${table}" >
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
    <script src="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-tags/bootstrap-tagsinput.min.js"></script>
    <script src="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-timepicker/bootstrap-timepicker.min.js"></script>
    

    <script type="text/javascript">
    
        $(document).ready(function(){
			
            /* 显示弹框  */
            $('#dialog_cstm_tags').modal("show");
			
			
            /** 校验字段  **/
            var validator =$('#taskInfo').validate({
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
                if($('#taskInfo').valid()){
//                     $('#msubmit').attr("disabled",true);
					 console.log($("#taskInfo").serialize());
                    $('#taskInfo').submit();
                }
            });

            $('#taskInfo').ajaxForm({
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
                        $('#dt_basic_cstm').DataTable().ajax.reload(null,false);;
                    }
                    $('#dialog_cstm_tags').modal("hide");
                },
                submitHandler : function(form) {
                    $(form).ajaxSubmit({
                        success : function() {
                            $("#taskInfo").addClass('submited');
                        }
                    });
                },
                error: function(XMLHttpRequest,textStatus , errorThrown){
                    if(textStatus=='error'){
                        $('#dialog_cstm_tags').dialog('hide');
                        $.smallBox({
                            title : "操作失败",
                            content : "<i class='fa fa-clock-o'></i> <i>操作失败</i>",
                            color : "#C46A69",
                            iconSmall : "fa fa-times fa-2x fadeInRight animated",
                            timeout : 2000
                        });

                        $('#oTable').DataTable().ajax.reload(null,false);;
                    }
                }
            });
            
        });
        
        
        $('#dialog_cstm_tags').on('hidden.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
        	$("#dialog_cstm_tags").remove();
        	$('#oTable').DataTable().ajax.reload(null,false);;
        });

		$('#start_date').datepicker({
			dateFormat : 'yy-mm-dd'
		});

		$("#customerTags").tagsinput({
			confirmKeys: [13]
		});
		
    </script>






