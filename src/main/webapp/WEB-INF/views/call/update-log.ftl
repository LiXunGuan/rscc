
<div class="modal fade" id="dialog_call_log">
    <div class="modal-dialog" style="width: 800px;">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                <h4 class="modal-title">呼叫记录信息</h4>
            </div>
            <div class="modal-body"  >

                <form id="callLogInfo" action="${springMacroRequestContext.contextPath}/calllog/update" class="smart-form" method="post">
                    <fieldset>
                   		<section>
							<div class="row">
								<@showLabel maps 2 flag></@showLabel>
							</div>
							<section>
								<label class="label col col-2">内容小计</label>
								<label class="textarea col col-10" style="padding-left:10px;"> 										
									<textarea rows="3" class="custom-scroll" name="text_log" id="text_log_a"></textarea>
								</label>
							</section>
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
        	
			
            /* 显示弹框  */
            $('#dialog_call_log').modal({
            	show : true,
            	backdrop : "static"
            });

           
            <#if map??>
				<#list map?keys as k>
					$("#${(k)!''}_a").val('${(map[k])!''}');
				</#list>
			</#if>
			
// 			$(".date").datepicker({
// 	   			dateFormat : 'yy-mm-dd'
// 	    	});
			
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
			
            /** 校验字段  **/
            var validator =$('#callLogInfo').validate({
                rules : {
                	customer_id : { required : true, maxlength : 7, remote : {
                			type : "post",
                			url : getContext() + 'cstm/cstmIdRepeat',
                			data : { uid : function(){ return $('#uid').val(); }}
                		}
                	},
                	cstm_name :{ required : true, maxlength : 7, },
                	pool_id : { required : true },
                	start_date : { required : true, dateISO : true}
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
                		dateISO : "${c.validate_dateISO}",
                		required :" ${c.validate_required}",
                	},
                	pool_id : {
                		required :" ${c.validate_required}",
               		},
                },
                errorPlacement : function(error, element) {
                    error.insertAfter(element.parent());
                }
            });

            /* 提交按钮单击事件  */
            $('#msubmit').click(function(){
                if($('#callLogInfo').valid()){
//                     $('#msubmit').attr("disabled",true);
                    $('#callLogInfo').submit();
                }
            });

            $('#callLogInfo').ajaxForm({
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
                        $('#callLogTable').DataTable().ajax.reload(null,false);;
                    }
                    $('#dialog_call_log').modal("hide");
                },
                submitHandler : function(form) {
                    $(form).ajaxSubmit({
                        success : function() {
                            $("#callLogInfo").addClass('submited');
                        }
                    });
                },
                error: function(XMLHttpRequest,textStatus , errorThrown){
                    if(textStatus=='error'){
                        $('#dialog_call_log').dialog('hide');
                        $.smallBox({
                            title : "操作失败",
                            content : "<i class='fa fa-clock-o'></i> <i>操作失败</i>",
                            color : "#C46A69",
                            iconSmall : "fa fa-times fa-2x fadeInRight animated",
                            timeout : 2000
                        });

                        cstmTabel.ajax.reload(null,false);;
                    }
                }
            });
            
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
            
//             $('#start_date_a').datepicker({
// //         		monthNames: ['一月','二月','三月','四月','五月','六月', '七月','八月','九月','十月','十一月','十二月'],
// //         		nextText: '>', 
// //         		prevText: '<',
// //        			firstDay: 1, 
//        			dateFormat : 'yy-mm-dd'
//         	});
        });
        
        
        $('#dialog_call_log').on('hidden.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
        	$("#dialog_call_log").remove();
        	cstmTabel.ajax.reload(null,false);;
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
// 		$(".date").datepicker({
//     		monthNames: ['一月','二月','三月','四月','五月','六月', '七月','八月','九月','十月','十一月','十二月'],
//     		nextText: '>', 
//     		prevText: '<',
//    			firstDay: 1, 
//    			dateFormat : 'yy-mm-dd'
//     	});
    	
    	
    </script>

    
    <#macro showLabel maps count inoutflag>
	<#assign index = 0>
	<#list maps?keys as key>
	
		<#if (maps[key].allowEmpty) == "0">
			<#assign requireds = "required">
		<#else>
			<#assign requireds = "">
		</#if>
		
		<#if (maps[key].isHidden)?? && (maps[key].isHidden) == "1" && (maps[key].isReadonly)?? && (maps[key].isReadonly) == "1">
			<#assign disabled = "disabled='disabled'">
		<#else>
			<#assign disabled = "">
		</#if>

		<#if (maps[key].isReadonly)?? && (maps[key].isReadonly) == "1">
			<#assign readonly = "readonly='readonly'">
		<#else>
			<#assign readonly = "">
		</#if>
		
		<#if (maps[key].isHidden)?? && (maps[key].isHidden) == "1">
			<input ${disabled} type="hidden" name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" value="" ${readonly}/>
		<#elseif (maps[key].columnNameDB) == "text_log" || (maps[key].columnNameDB)?starts_with(inoutflag)>
		<#else>
			<#assign index = index + 1 />
			<section class="col col-6">
			<#if (maps[key].columnType) == "DATE" >
				<label class="label col col-4">${maps[key].columnName}</label>
				<div class="input col col-8">
					<label class="input">
						<input ${readonly} name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" value="" class="datetime"/>
					</label>
				</div>
			<#elseif (maps[key].columnType) == "ENUM" >
				<#assign enumStr = (maps[key].characterProperty)?string>
				<label class="label col col-4">${maps[key].columnName}</label>
				<div class="col col-lg-8">
					<select name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" class="form-control">
						<#list enumStr?replace("，",",")?split(",") as a>
							<option value="${a!''}">${a!''}</option>
						</#list>
					</select>
				</div>
			<#else>
				<label class="label col col-4">${maps[key].columnName}</label>
				<div class="col col-lg-8">
					<label class="input">
						<input ${readonly} name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a"  value="${serialized!''}" class="${requireds}">
					</label>
				</div>
			</#if>
			</section>
		</#if>
		
		<#if !((maps[key].columnNameDB) == "text_log"  || (maps[key].columnNameDB)?starts_with(inoutflag)) && !((maps[key].isHidden)?? && (maps[key].isHidden) == "1") && index%count == 0>
			</div>
			<div class="row">
		</#if>
	</#list>
</#macro>