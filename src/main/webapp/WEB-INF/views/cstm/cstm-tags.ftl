
<style>
.smart-form .label {
	display: inline-block;
}

.tag.label.label-info {
	color: white;
}

.row label.col.col-2 {
	text-align: right;
}

.select2-hidden-accessible {
	display: none;
}

</style>

<div class="modal fade" id="dialog_cstm_tags">
    <div class="modal-dialog" style="width: 50%;">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                <h4 class="modal-title">客户信息管理</h4>
            </div>
            <div class="modal-body"  >

                <form id="tagsInfos" action="${springMacroRequestContext.contextPath}/cstm/save" class="smart-form" method="post">
                
                	<!-- 数据来源 -->
                	<input type="hidden" value="${source!''}" name="source" id="source">
                    <fieldset>
                   		<section>
							<div class="row">
								<@showLable maps></@showLable>
							</div>
						</section>
						
						<section>
							<div class="row">
								<label class="label col col-2">号码一</label>
								<div class="form-group col col-3">
									<label class="input">
										<input type="hidden" name="phone_number" id="phone_number_a"  value="" class="required">
										<input name="number1" id="number1" value="${phone_number!''}">
									</label>
								</div>							
								
								<label class="label col col-2">号码二</label>
								<div class="form-group col col-3">
									<label class="input">
										<input type="hidden" name="phone_number_2" id="phone_number_a_2"  value="${secPhone!''}" >
										<input name="number2" id="number2">
									</label>
								</div>
							</div>
						</section>
						<!---------------------------------------------------- 客户标签选项 ---------------------------------------------------->
						<section>
							<div class="row">
								<label class="label col col-2">客户标签</label>
								<div class="form-group col col-8">
									<#if tagsStr?? && (tagsStr?length) &gt; 0 >
										<input class="form-control tagsinput" style="color: white;" id="customerTagss" name="customerTags" value="${tagsStr?substring(1,tagsStr?length-1)}" data-role="tagsinput">
									<#else>
										<input class="form-control tagsinput" style="color: white;"  id="customerTagss" name="customerTags" value="" placeholder="按回车确定" data-role="tagsinput">
									</#if>
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

    <script src="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-tags/bootstrap-tagsinput.js"></script>
    <script src="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-timepicker/bootstrap-timepicker.min.js"></script>
    
    <script type="text/javascript">
    
        $(document).ready(function(){
        	
        	jQuery.validator.addMethod("notEqualTo", function(value, element, param) {
        		
        		return this.optional(element) ||value != $(param).val();
       			}, $.validator.format("两个电话号码不能相同"));
        	
        	
			/* 默认填写时间 */
        	$("#start_date_a").val(('${.now?datetime?string("yyyy-MM-dd HH:mm:ss")}'));
        	
            /* 显示弹框  */
            $('#dialog_cstm_tags').modal({
            	show : true,
            	backdrop : "static"
            });
            
            /* 如果存在客户池，选中默认客户池 */
            if($("#tabs ul li.ui-state-active").attr("aria-controls")){
            	
            	var vals = $("#tabs ul li.ui-state-active").attr("aria-controls");
            	$("#pool_ids").val(vals).trigger("change");
            }
            
            <#if Acstm??>
				<#list Acstm?keys as k>
					$("#${(k)!''}_a").val('${(Acstm[k])!''}');
					$("#${(k)!''}_a_1").val('${(Acstm[k])!''}');
				</#list>
			</#if>
			
			$("#number1").val(window.parent.hiddenPhone($("#phone_number_a").val()));
			$("#number2").val(window.parent.hiddenPhone($("#phone_number_a_2").val()));
			
			//如果不为空 不允许修改
			if($("#number1").val()!=""){
				$("#number1").attr("disabled","disabled");
			}
			
			if($("#number2").val()!=""){
				$("#number2").attr("disabled","disabled");
			}
			
            /** 校验字段  **/
            var validator =$('#tagsInfos').validate({
                rules : {
                	customer_id : { required : true, maxlength : 12, remote : {
                			type : "post",
                			url : window.parent.getContext() + 'cstm/cstmIdRepeat',
                			data : { uid : function(){ return $('#uid').val(); }}
                		}
                	},
                	cstm_name :{ required : true, maxlength : 15 },
                	pool_id : { required : true },
                	start_date : { required : true},
                	number1 : {
                		required :true,
                		digits : true,
                		remote : {
                			type : "post",
                			url : window.parent.getContext() + 'cstm/checkPhoneRepeat',
                			data : { uid : function(){ return $('#uid').val(); }},
                			complete:function(d){
                				if(d.responseText != 'true'){}
                			}
                		}
                	},
                	number2 : {
                		digits : true,
                		notEqualTo : "#number1",
						remote : {
							type : "post",
                			url : window.parent.getContext() + 'cstm/checkPhoneRepeat',
                			data : { uid : function(){ return $('#uid').val(); }}
                		}
                	}
                },
                messages : {
                	customer_id : {
                		required :" ${c.validate_required}",
                		maxlength : "${c.validate_maxlength}",
               			remote : "${c.validate_remote}"
                	},
                	cstm_name : {
                		required :" ${c.validate_required}",
                		maxlength : "${c.validate_maxlength}"
                	},
                	start_date :{
                		required :" ${c.validate_required}",
                	},
                	pool_id : {
                		required :" ${c.validate_required}",
               		},
               		number1 :{
               			remote :"已经存在该号码",
               		},
               		number2 :{
               			remote :"已经存在该号码",
               		},
                },
                errorPlacement : function(error, element) {
                    error.insertAfter(element.parent());
                }
            });

            /* 提交按钮单击事件  */
            $('#msubmit').click(function(){
                if($('#tagsInfos').valid()){
                	$('#msubmit').attr("disabled",true);
                    if($("#dialog_cstm_tags #phone_number_a").val()==""){
                    	$("#dialog_cstm_tags #phone_number_a").val(window.parent.deleteZero($("#number1").val()));
                    }
                    if($("#dialog_cstm_tags #phone_number_a_2").val()==""){
                    	$("#dialog_cstm_tags #phone_number_a_2").val(window.parent.deleteZero($("#number2").val()));
                    }
                    $('#tagsInfos').submit();
                }
            });
            
            $('#tagsInfos').ajaxForm({
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
                        saveResult = true;
                    }
                    $('#dialog_cstm_tags').modal("hide");
                },
                submitHandler : function(form) {
                    $(form).ajaxSubmit({
                        success : function() {
                            $("#tagsInfos").addClass('submited');
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

                        cstmTabel.ajax.reload(null,false);
                    }
                }
            });
            
            
            $("#customerTagss").tagsinput("build",{confirmKeys:[13,32]});
            
            $('#start_date_a').datetimepicker({
                format: "yyyy-mm-dd hh:ii",
                weekStart: 1,
                todayBtn:  1,
        		autoclose: 1,
        		todayHighlight: 1,
        		startView: 2,
        		forceParse: 0,
                showMeridian: 1
        	});
        });
        
    	/* 标签内容失去焦点时    */
//     	var cstmTags = $('#customerTagss').tagsinput('input');
//     	cstmTags.bind("blur",function(){
//     	      $('#customerTagss').tagsinput('add', cstmTags.val());
//     	      $(this).val("");
//     	      $(this).css("background","white");
//     	});
        
        
        //保存结果状态
        var saveResult;
        
        $('#dialog_cstm_tags').on('hidden.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
        	$("#dialog_cstm_tags").remove();
        	cstmTabel.ajax.reload(null,false);
        });
        
    	$('#pool_ids').select2({
    	    allowClear : true,
    	    width:'99%',
    	});
    	
    	$('#own_ids').select2({
    	    allowClear : true,
    	    width:'99%',
    	});
    	
		$(".datetime").datetimepicker({
            format: "yyyy-mm-dd hh:ii",
            weekStart: 1,
            todayBtn:  1,
    		autoclose: 1,
    		todayHighlight: 1,
    		startView: 2,
    		forceParse: 0,
            showMeridian: 1
    	});
		
		/* 如果有值。去除必填提示 */
		function addClass(obj){
			if($(obj).val()){
				$(obj).closest("label").next("em").remove();
			}
		}
    </script>


<!-- 生成表单 -->
<#macro showLable maps>
	<#if maps??>
		<#assign lineNumber = 0>
		<#list maps?keys as key>
			
			<!-- 生成必填校验 必填 -->
			<#if (maps[key].allowEmpty) == "0">
				<#assign requireds = "required">
			<#else>
				<#assign requireds = "">
			</#if>
			
			<#if (maps[key].isHidden)?? && (maps[key].isHidden) == "1">
				<#assign bedisabled = "type='hidden'">
			<#else>
				<#assign bedisabled = "">
			</#if>
			
			<#if (maps[key].columnType) == "DATE" >
				<label class="label col col-2">${maps[key].columnName}</label>
				<div class="input col col-3">
					<label class="input">
						<input name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" value="" class="${requireds} datetime"/>
					</label>
				</div>
			<#elseif (maps[key].columnType) == "ENUM" >
				<#assign enumStr = (maps[key].characterProperty)?string>
					<label class="label col col-2">${maps[key].columnName}</label>
					<div class="form-group col col-lg-3">
						<select name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" class="form-control" required="${requireds}">
							<#if requireds =="">
								<option value="无">----请选择----</option>
							</#if>
							
							<#list enumStr?replace("，",",")?split(",") as a>
								<option value="${a!''}">${a!''}</option>
							</#list>
						</select>
					</div>
			<#else>
				<#if (maps[key].columnNameDB) == "pool_id">
					<label class="label col col-2">${maps[key].columnName}</label>
					<div class="form-group col col-lg-3">
						<label class="select">
							<select name="pool_id" id="pool_ids" onchange="addClass(this)">
<!-- 								<optgroup label="成交客户类型"> -->
								<#if pools??>
									<#list pools as li>
									
										<#if pool_id?? && pool_id == (li.uid)>
											<option value="${li.uid!''}" selected="selected">${li.poolName!''}</option>
										<#else>
											<#if !pool_id?? && (li.beDefault)?? && li.beDefault == "1">
												<option value="${li.uid!''}" selected="selected">${li.poolName!''}</option>
											<#else>
												<option value="${li.uid!''}">${li.poolName!''}</option>
											</#if>
										</#if>
										
									</#list>
								<#else>
									<option value="" onclick="addPools();">添加客户池</option>
								</#if>
<!-- 								</optgroup> -->
								
<!-- 									<optgroup label="意向客户类型"> -->
<!-- 									<#if allIntent??> -->
<!-- 										<#list allIntent as li> -->
<!-- 											<#if pool_id?? && pool_id == (li.uid)> -->
<!-- 												<option value="${li.uid!''}" selected="selected">${li.intentName!''}</option> -->
<!-- 											<#else> -->
<!-- 												<option value="${li.uid!''}">${li.intentName!''}</option> -->
<!-- 											</#if> -->
<!-- 										</#list> -->
<!-- 									</#if> -->
<!-- 								</optgroup> -->
							</select>
						</label>
					</div>
				<#elseif (maps[key].columnNameDB) == "own_id">
						<label class="label col col-2">${maps[key].columnName}</label>
						<div class="form-group col col-3">
							<label class="select">
								<select name="own_id" id="own_ids">
									<option value="-1">----请选择----</option>
									<#if user??>
										<#list user as li>
											<#if own_id?? && own_id == (li.uid)>
					  							<option value="${li.uid!''}" selected="selected">${li.loginName!''}</option>
											<#else>
												<option value="${li.uid!''}">${li.loginName!''}</option>
											</#if>
										</#list>
									</#if>
								</select>
							</label>
						</div>
				<#elseif (maps[key].columnNameDB) == "phone_number">
					<#assign lineNumber = lineNumber - 1 >
				<#else>
					
					<#if (maps[key].columnNameDB) != "customer_id">
						<#if (maps[key].columnType) == "INT">
							<label class="label col col-2">${maps[key].columnName}</label>
							<div class="form-group col col-3">
								<label class="input">
									<input name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a"  value="" class="digits ${requireds}">
								</label>
							</div>
						<#elseif (maps[key].columnType) == "FLOAT">
							<label class="label col col-2">${maps[key].columnName}</label>
							<div class="form-group col col-3">
								<label class="input">
									<input name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a"  value="" class="number ${requireds}">
								</label>
							</div>
						<#else>
						
							<#if "" != bedisabled>
								<#assign lineNumber = lineNumber - 1 >
								<input ${bedisabled} name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" value="" class="${requireds}">
							<#else>
								<label class="label col col-2">${maps[key].columnName}</label>
								<div class="form-group col col-3">
									<label class="input">
										<input name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" value="" class="${requireds}">
									</label>
								</div>
							</#if>
						
<!-- 							<label class="label col col-2">${maps[key].columnName}</label> -->
<!-- 							<div class="form-group col col-lg-3"> -->
<!-- 								<label class="input"> -->
<!-- 									<input name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a"  value="" class="${requireds}"> -->
<!-- 								</label> -->
<!-- 							</div> -->
						</#if>
					<#else>
						<label class="label col col-2">${maps[key].columnName}</label>
						<div class="form-group col col-3">
							<label class="input">
								<#if uid??>
									<input type="hidden" name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a_1" value="${serialized!''}"/>
									<input id="${(maps[key].columnNameDB)!''}_a" value="${serialized!''}" class="${requireds}" disabled="disabled"/>
								<#else>
									<input type="hidden" name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" value="${serialized!''}"/> 
									<input value="${serialized!''}" class="${requireds}" disabled="disabled"/>
								</#if>
								<input type="hidden" name="uid" id="uid" value="${uid!''}">
							</label>
						</div>
					</#if>
				</#if>
			</#if>
			<#assign lineNumber = lineNumber+1 >
			<!-- 判断是否换行 -->
			<#if lineNumber%2 == 0>
					</div>
				</section>
				<section>
					<div class="row">
			</#if>
			
		</#list>
	</#if>
</#macro>



<!--
	list集合遍历
	集合 
	单个对象名 
	值的valueID 
	值得显示名字
	要选中的valueID
-->
<#macro select lists>
	<option>----请选择----</option>
	<#if lists??>
		<#list lists as li>
				<option value="${li.uid!''}">${li.poolName!''}</option>
		</#list>
	</#if>
</#macro>