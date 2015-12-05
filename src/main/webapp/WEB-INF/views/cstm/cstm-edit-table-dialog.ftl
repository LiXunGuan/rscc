
<style>
#dialog_cstm_edit_column .smart-form .label {
	display: inline-block;
	line-height: inherit;
}

.tag.label.label-info {
	color: white;
}

/* 选择框样式 */
.smart-form .toggle i {
	left: 0px;
}

/* 	#characterProperty{width: 50%;} */
</style>

<div class="modal fade" id="dialog_cstm_edit_column">
	<div class="modal-dialog" style="">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">
					<strong>关闭</strong>
				</button>
				<h4 class="modal-title">${tablenamestr!''}的字段信息</h4>
			</div>
			<div class="modal-body">

				<form id="tableInfos"
					action="${springMacroRequestContext.contextPath}/design/editSave"
					class="smart-form" method="post">
					<fieldset>

						<input type="hidden" name="tablename" value="${tablename!''}">

						<input type="hidden" name="uid" id="uid"
							value="${(default.uid)!''}"> <#if default?? &&
						<!-- -------------------------------------------------默认字段只允许需改名字------------------------------------------------- -->
						(default.isDefault) == "1">
						<section>
							<div class="row">
								<label class="label col col-3" style="text-align: right;">字段顺序<sup>*</sup></label>
								<div class="input col col-6" style="padding-left: 0px;">
									<label class="input"> <input name="orders"
										value="${(default.orders)!''}">
									</label>
								</div>
							</div>
						</section>
						<#else>
						<!-- -------------------------------------------------非默认字段------------------------------------------------- -->

						
						<#if tablename?? && tablename == 'sys_call_log'>
							<section>
								<div class="row">
									<label class="label col col-3" style="text-align: right;">呼入/呼出<sup>*</sup></label>
									<div class="col col-6">
									<select name="callType" id="callType" class="form-control">
										<option value="">公有</option>
										<option value="callOut">呼出</option>
										<option value="callIn">呼入</option>
									</select>
								</div>
							</div>
							</section>
						</#if>


						<section>
							<div class="row">
								<label class="label col col-3" style="text-align: right;">字段名称<sup>*</sup></label>
								<div class="input col col-6">
									<label class="input"> <input name="columnName"
										value="${(default.columnName)!''}" id="columnName"
										placeholder="字段的显示名称">
									</label>
								</div>
							</div>
						</section>

						<section>
							<div class="row">
								<label class="label col col-3" style="text-align: right;">字段类型<sup>*</sup></label>
								<#if default??>
								<div class="label col col-6" id="colType" >
									${COLUMNTYPE[default.columnType]} 
								<#else>
									<div class="col col-6" >
										<select name="columnType" id="columnType" class="form-control"
											onchange="getSelectedInfo();"> 
											<#list COLUMNTYPE?keys as key>
												<option value="${key}">${COLUMNTYPE[key]}</option> 
											</#list>
										</select> 
								</#if>
									</div>

									<label class="btn-sm btn-info col col" id="characterProper0ty1"
										onclick="getBuild()"
										style="text-align: center; width: 12%; padding-left: 2px; padding-right: 2px; vertical-align: bottom;">添加栏位</label>
							</div>
						</section>

						<section>
							<div class="row">
								<label class="label col col-3" id="characterProperty1"
									style="text-align: right;">字符串长度<sup>*</sup></label> 
								<#if default??>
									<div class="input col col-6">
										<label class="input">
											<input name="characterProperty" id="characterProperty" placeholder="字段在数据库中的长度" value="${(default.characterProperty)!''}">
								<#else>
									<div class="input col col-6">
										<label class="input"> 
											<input name="characterProperty" id="characterProperty" placeholder="字段在数据库中的长度" value=""> 
								</#if>
										</label>
									</div>
							</div>
						</section>


						<section>
							<div class="row" id="appendContent"></div>
						</section>

						<section>
							<div class="row">
								<label class="label col col-3" style="text-align: right;">是否可查</label> 
								<div class="onoffswitch col col-2" style="padding-left: 0px;">
									<span class="onoffswitch">
										<#if default??>
											<#if default.allowSelect == "1">
												<input type="checkbox" name="allowSelect" id="allowSelect" class="onoffswitch-checkbox" checked="checked">
											<#else>
												<input type="checkbox" name="allowSelect" id="allowSelect" class="onoffswitch-checkbox">
											</#if>
										<#else>
											<input type="checkbox" name="allowSelect" id="allowSelect" class="onoffswitch-checkbox" checked="checked">
										</#if>
										<label class="onoffswitch-label" for="allowSelect"> 
											<span class="onoffswitch-inner" data-swchon-text="开启" data-swchoff-text="关闭"></span> 
											<span class="onoffswitch-switch"></span>
										</label> 
									</span>
								</div>

								<label class="label col col-3" style="text-align: right;">是否索引</label>

								<div class="onoffswitch col col-2" style="padding-left: 0px;">
									<span class="onoffswitch" >
										<#if default??>
											<#if default.allowIndex == "1">
												<input type="checkbox" name="allowIndex" id="allowIndex" class="onoffswitch-checkbox" checked="checked">
											<#else>
												<input type="checkbox" name="allowIndex" id="allowIndex" class="onoffswitch-checkbox">
											</#if>
										<#else>
											<input type="checkbox" name="allowIndex" id="allowIndex" class="onoffswitch-checkbox" checked="checked">
										</#if>
										<label class="onoffswitch-label" for="allowIndex"> 
											<span class="onoffswitch-inner" data-swchon-text="开启" data-swchoff-text="关闭"></span> 
											<span class="onoffswitch-switch"></span>
										</label> 
									</span>
								</div>	
							</div>
							</section>
							
							<section>
								<div class="row">
								<label class="label col col-3" style="text-align: right;">是否显示</label>
								<div class="onoffswitch col col-2" style="padding-left: 0px;">
									<span class="onoffswitch">
										<#if default?? >
											<#if default.allowShow == "1">
												<input type="checkbox" name="allowShow" id="allowShow" class="onoffswitch-checkbox" checked="checked">
											<#else>
												<input type="checkbox" name="allowShow" id="allowShow" class="onoffswitch-checkbox">
											</#if>
										<#else>
												<input type="checkbox" name="allowShow" id="allowShow" class="onoffswitch-checkbox" checked="checked">
										</#if>
										<label class="onoffswitch-label" for="allowShow"> 
											<span class="onoffswitch-inner" data-swchon-text="开启" data-swchoff-text="关闭"></span> 
											<span class="onoffswitch-switch"></span>
										</label> 
									</span>
								</div>	
								<label class="label col col-3" style="text-align: right;">是否必填</label>
								<div class="onoffswitch col col-2" style="padding-left: 0px;">
									<span class="onoffswitch">
										<#if default??>
											<#if default.allowEmpty == "0">
												<input type="checkbox" name="allowEmpty" id="allowEmpty" class="onoffswitch-checkbox" checked="checked">
											<#else>
												<input type="checkbox" name="allowEmpty" id="allowEmpty" class="onoffswitch-checkbox">
											</#if>
										<#else>
											<input type="checkbox" name="allowEmpty" id="allowEmpty" class="onoffswitch-checkbox" checked="checked">
										</#if>
										<label class="onoffswitch-label" for="allowEmpty"> 
											<span class="onoffswitch-inner" data-swchon-text="开启" data-swchoff-text="关闭"></span> 
											<span class="onoffswitch-switch"></span>
										</label> 
									</span>
								</div>	
							</div>
						</section>
						</#if>
					</fieldset>
				</form>
			</div>
			<div class="modal-footer">
				<button id="formSubmit" type="button" class="btn btn-primary">
					保 存</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">
					关 闭</button>
			</div>
		</div>
	</div>
	<script
		src="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-timepicker/bootstrap-timepicker.min.js"></script>

	<script type="text/javascript">
    
        $(document).ready(function(){
			$("#characterProper0ty1").css("display", "none");
			$("#validate").css("display", "none");
			$("#appendContent").css("display", "none");
			
			if($('#uid').val()!=''){
				//字体和值都不显示
				$("#characterProperty1").css("display", "none");
				$("#characterProperty").css("display", "none");
				
				//如果是修改  仅字符串有长度展示
				if($("#colType").text().trim() === "字符串"){
					
					$("#characterProperty1").css("display", "block");
					$("#characterProperty").css("display", "block");
				}
			}
			
			if($("#characterProperty").val()==""){
				$("#characterProperty").val(64);
			}
			
			
            /* 显示弹框  */
            $('#dialog_cstm_edit_column').modal({
            	show :true,
            	backdrop:"static",
            });

			/** 校验字段  **/
			var validator =$('#tableInfos').validate({
				rules : {
					columnName : {
					required : true,
					maxlength :9, 
						remote : {
							type : "post",
							url : getContext() + "design/columnRepeat",
							data : { uid : function(){ return $('#uid').val(); }}
						}
						},
						orders : {
							required : true,
							maxlength :3, 
							digits : true
						},
						characterProperty :{
							required : true,
							digits :true,
							remote : {
							type : "post",
							url : getContext() + "design/lengthCompare",
							data : { uid : function(){ return $('#uid').val(); }},
							complete : function(data){
								console.log(data);
							}
						}
                	}
               	 },
                messages : {
                	columnName : {
                		required :" ${c.validate_required}",
                	},
                	characterProperty : {
                		required : "${c.validate_required}",
                		digits : "${c.validate_digits}",
                	}
                },
                errorPlacement : function(error, element) {
                    error.insertAfter(element.parent());
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
                        
                        cstmDesignTable.ajax.reload( null, false );
                        
                    }
                    
                    $("#tableName").val(data.tablename);
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
                error: function(XMLHttpRequest, textStatus, errorThrown){
                    if(textStatus == 'error'){
                        $('#dialog_cstm_edit_column').modal('hide');
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

            
            /* 提交按钮单击事件  */
    		$('#formSubmit').click(function(){
    			
            	var value = $("#columnType").val();
            	if(value == "ENUM"){
            		
            		if(num-1<=1){
            			
                        $.smallBox({
                            title : "操作失败",
                            content : "<i class='fa fa-clock-o'></i> <i>请至少添加两个枚举类型数据</i>",
                            color : "#C46A69",
                            iconSmall : "fa fa-times fa-2x fadeInRight animated",
                            timeout : 2000
                        });
                        
                        return false;
            		}
            		
    	        	var enumVal = "";
    	        	//取到所有枚举值 赋值
    	        	for(var i=1 ;i<=num-1;i++){
    	        		enumVal += $("#add"+i+"").val()+",";
    	        		
    	        		if($("#add"+i+"").val() == ""){
                            $.smallBox({
                                title : "操作失败",
                                content : "<i class='fa fa-clock-o'></i> <i>添加的枚举数据不能为空</i>",
                                color : "#C46A69",
                                iconSmall : "fa fa-times fa-2x fadeInRight animated",
                                timeout : 2000
                            });
                            
                            return false;
    	        		}
    	        		
    	        		if(i >=2){
	    	        		if(enumVal.indexOf($("#add"+i+"").val())<= 0){
	    	        			break ;
	    	        		}
    	        		}
    	        	}
    				$("#characterProperty").val(enumVal);
            	}
    			
            	if($('#tableInfos').valid()){
            		
	                /* 提交按钮单击事件  */
					$('#tableInfos').submit();
            	}
            	
            });
            
        });
        
       /* 栏位个数从1开始 */
       var num = 1;
       
       /* 移除栏位 */
       function removeAdded(removeid){
       	
			$("#lan"+removeid+"").remove();
			$("#add"+removeid+"").remove();
			$("#rem"+removeid+"").remove();
			$("#div"+removeid+"").remove();
			num--;
       }
       
       //检查并创建下拉列表框
       function getBuild(){
	       	$("#appendContent").css("display", "block");
			$("#appendContent").append('<label class="label col col-3" style="text-align: center;" id="lan'+num+'" style="">栏位</label>'+
							       		'<div id="div'+num+'" class="input col col-6" style="text-align: left;">'+
							       			'<label class="input"><input id="add'+num+'" name="add'+num+'" value="" placeholder="添加的栏位"></label>'+
					                   '</div>');
			$("#appendContent").append('<label class="label btn-sm btn-danger" style="float: left; color:white" id="rem'+num+'" onclick="removeAdded('+num+');">移除</label>');
			
			num ++;
		}

	/* 下拉框键 和 值 */
	function getSelectedInfo() {
		var value = $("#columnType").val();
		$("#characterProperty1").css("display", "block");
		$("#characterProperty").css("display", "block");
		$("#characterProper0ty1").css("display", "none");

		switch (value) {
			case 'DATE':
				$("#characterProperty").attr("placeholder","已切换日期类型！");
				$("#characterProperty").css("display", "none");
				$("#characterProperty1").css("display", "none");
				$("#appendContent").css("display", "none");
				$("#characterProperty").val(64);
				break;
			case 'VARCHAR':
				$("#characterProperty").attr("placeholder","字符类型，请输入长度！");
				$("#appendContent").css("display", "none");
				break;
			case 'INT':
				$("#appendContent").css("display", "none");
				$("#characterProperty1").css("display", "none");
				$("#characterProperty").css("display", "none");
				break;
			case 'FLOAT':
				$("#characterProperty").attr("placeholder","已经切换浮点类型！");
				$("#characterProperty").css("display", "none");
				$("#characterProperty1").css("display", "none");
				$("#appendContent").css("display", "none");
				break;
			case 'ENUM':
				$("#characterProperty").attr("placeholder","枚举类型，输入请用逗号隔开，示例：完成，未完成");
				$("#characterProper0ty1").css("display", "block");
				$("#characterProperty").css("display", "none");
				$("#characterProperty1").css("display", "none");
				$("#appendContent").css("display", "block");
				break;
			default:
		}
	}


	$('#dialog_cstm_edit_column').on('hidden.bs.modal',function(e) { /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
		$("#dialog_cstm_edit_column").remove();
		$('#dt_basic_cstm_des').DataTable().ajax.reload(null,false);
	});

	function contains(string, substr, isIgnoreCase) {
		if (isIgnoreCase) {
			string = string.toLowerCase();
			substr = substr.toLowerCase();
		}
		var startChar = substr.substring(0, 1);
		var strLen = substr.length;
		for (var j = 0; j < string.length - strLen + 1; j++) {
			if (string.charAt(j) == startChar) {//如果匹配起始字符,开始查找

				if (string.substring(j, j + strLen) == substr) {//如果从j开始的字符与str匹配，那ok
					return true;
				}
			}
		}
		return false;
	}
</script>