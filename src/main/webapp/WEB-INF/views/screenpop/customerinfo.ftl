
<!-- 
	1、修改title；
	2、两个宏 生成客户表单和沟通记录表单
	3、绑定客户的信息；
 -->

<style>
.widget-body-toolbar {
  display: block;
  padding: 8px 10px;
  margin: -13px -13px 13px;
  min-height: 42px;
  background: #fafafa;
  border-bottom:none;
}

.bootstrap-tagsinput .tag{
	display: inline-block;
}

.tag.label.label-info {
	color: white;
}

.smart-form fieldset {
	padding: 0px 0px 0px
}

#poploginfo .col{
	padding-right: 5px;
}

/* 字体间距 */
#first .label.col {
	width: 85px;
	text-align: right;
	padding-left: 12px;
	padding-right: 12px;
}


/* 
	控制按钮的样式
 */

#allcalls .btn-sm{
	line-height: 1.2
}

#formsubmit{
	line-height: 1.2
}

</style>
<div id="first" class="col-xs-9 col-sm-9 col-md-9 col-lg-9" style=" background: rgba(250, 250, 250, 1); ">
	<div class="jarviswidget-editbox"></div>
	<div class="widget-body">
		<div class="widget-body-toolbar">
			<div class="smart-form" style="height: 300px; overflow-y: auto;overflow-x: hidden;">
           		<form id="cstmInfo" action="${springMacroRequestContext.contextPath}/cstm/save" class="smart-form" method="post">
          			<legend>客户详情</legend>
               		<section>
						<div class="row">
							<@showLable maps></@showLable>
						</div>
					</section>
 			<!-- 		<section> 
						<div class="row">
							<label class="label col">客户标签</label>
							<div class="form-group col col-lg-8"> -->
								<#if tagsStr?? && (tagsStr?length) &gt; 0 >
									<input class="" type="hidden" style="color: white;" id="customerTagss" name="customerTags" value="${tagsStr?substring(1,tagsStr?length-1)}" data-role="tagsinput">
<!-- 									<input class="form-control tagsinput" type="hidden" style="color: white;" id="customerTagss" name="customerTags" value="${tagsStr?substring(1,tagsStr?length-1)}" data-role="tagsinput"> -->
								<#else>
									<input class="" type="hidden" style="color: white;" id="customerTagss" name="customerTags" value="" placeholder="按回车确定" data-role="tagsinput">
<!-- 									<input class="form-control tagsinput" type="hidden" style="color: white;" id="customerTagss" name="customerTags" value="" placeholder="按回车确定" data-role="tagsinput"> -->
								</#if>
							  <!-- </div>
						</div>
					</section>  -->
					
					<section>
			            <div class="form-group col col-lg-8" style="text-align: right;">
		               		 <button id="formsubmit" type="button" class="btn btn-sm btn-primary">
			                   	${saveInfo!'保  存'}
			                </button>
			            </div> 
					</section>
				</form>
			</div>
		</div>
	</div>
</div>
<div id="second" class="col-xs-3 col-sm-3 col-md-3 col-lg-3" style="background:rgba(250, 250, 250, 1); display: block;">
	<div class="jarviswidget-editbox"></div>
		<div class="widget-body">
			<div class="widget-body-toolbar">
				<div class="smart-form" style="height: 300px;">
					<div id="popDiv" class="smart-form">
	           			<legend>电话信息</legend>
	           			<fieldset style=" background: rgba(250, 250, 250, 1); ">
							<section>
      							<div class="row">
									<h5 class="todo-group-title" style="padding-top: 10px;">呼叫时间 (<small class="num-of-tasks" id="timestamp_1">1</small>)</h5>
									<h5 class="todo-group-title" style="padding-top: 10px;">来电号码 (<small class="num-of-tasks" id="caller_id_1">1</small>)</h5>
									<h5 class="todo-group-title" style="padding-top: 10px;">接&nbsp;&nbsp;入&nbsp;&nbsp;号 (<small class="num-of-tasks" id="access_number_1">1</small>)</h5>
									<h5 class="todo-group-title" style="padding-top: 10px;">来电描述 (<small class="num-of-tasks" id="ivr_desc_1">1</small>)</h5>
									<h5 class="todo-group-title" style="padding-top: 10px;">归属地 (<small class="num-of-tasks" id="location">1</small>)</h5>
								</div>
							</section>
	              		</fieldset>
		         	</div>
	        	 </div>
         	</div>
		</div>
</div>

<script src="${springMacroRequestContext.contextPath}/assets/js/plugin/select2/select2.min.js"></script>

<script type="text/javascript">

	/*隐藏号码*/
	var hiddenPhoneFlag = {globalHide : false, hasPermission : false};

	<#if hiddenPhone?? && "true" == hiddenPhone>
		hiddenPhoneFlag.globalHide = true;
	<#else>
		hiddenPhoneFlag.globalHide = false;
	</#if>
	
	<#if hasPhonePermission?? && true == hasPhonePermission>
		hiddenPhoneFlag.hasPermission = true;
	<#else>
		hiddenPhoneFlag.hasPermission = false;
	</#if>
	
	
	
	/*隐藏号码*/
	hiddenPhone = function hiddenPhone(phoneNumber){
		if(hiddenPhoneFlag.globalHide && !hiddenPhoneFlag.hasPermission)
			return phoneNumber.replace(/(\d{3})\d*/, '$1********');
		else
			return phoneNumber;
	}

	/* ----------------------------------title信息展示---------------------------------- */
	document.title='【电话】${titleContent!''}';

	/* 滚动title */
	function scroll() { 
		//获取title信息。 
		var titleInfo = document.title; 
		//获取title第一个汉字（数字、字母）。 
		//注释：字符串中第一个字符的下标是 0。如果参数 index 不在 0 与 string.length 之间，该方法将返回一个空字符串。 
		var firstInfo = titleInfo.charAt(0); 
		//获取第二位到最后的信息。 
		var lastInfo = titleInfo.substring(1, titleInfo.length); 
		//拼接输出信息 
		document.title = lastInfo + firstInfo; 
	} 
	setInterval("scroll()", 900); 
	/* ----------------------------------title信息展示---------------------------------- */
	
		/* 表单赋值 */
        <#if Acstm??>
			<#list Acstm?keys as k>
				$("#${(k)!''}_a").val('${(Acstm[k])!''}');
				$("#${(k)!''}_a_1").val('${(Acstm[k])!''}');
			</#list>
		<#else>
        /* 如果不是客户，第一次打进系统 绑定电话号码 编号 日期 */
        	$("#phone_number_a").val('${newPhoneNumber!''}');
        	$("#customer_id_a").val('${customer_id!''}');
        	$("#customer_id_a_1").val('${customer_id!''}');
        	$("#start_date_a").val('${cDate?datetime!''}');
		</#if>
	
		//号码隐藏
		$("#number1").val(window.parent.hiddenPhone($("#phone_number_a").val()));
		$("#number2").val(window.parent.hiddenPhone($("#phone_number_a_2").val()));
		
		
	(function(){
		
		if($("#phone_number_a").val()!=''){
			
			$("#number1").attr("disabled","disabled");	
		}
		
		if($("#phone_number_a_2").val()!=''){
			
			$("#number2").attr("disabled","disabled");	
		}
		
		$("#own_ids").attr("disabled","disabled").css("background","rgb(250, 250, 250)");
		$("#own_idss").val($("#own_ids").val())
		
		if($("#timestamp").val()){
			/* 来电信息赋值 */
			$("#access_number_1").text($("#access_number").val());
			$("#caller_id_1").text($("#caller_id").val());
			$("#timestamp_1").text($("#timestamp").val());
			$("#ivr_desc_1").text($("#ivr_desc").val());
		}else{
			
			$("#popDiv").empty();
			$("#popDiv").append("<legend>操作</legend>");
			$("#popDiv").append('<fieldset style=" background: rgba(250, 250, 250, 1); "><div class="row"></div></fieldset>');
			$("#popDiv").append('<fieldset style=" background: rgba(250, 250, 250, 1); "><div class="row"><label class="btn-lg btn bg-color-green txt-color-white" style="padding-left:50px;padding-right:50px;margin:50px;">呼叫</label>');
			$("#popDiv").append('</div></fieldset>');
		}

		
		/* 如果没有值 表示是查看 不显示添加客服小计的框 否则为来电弹屏 */
		if($("#timestamp").val()==''){
			
			$("#first").removeClass("col-xs-9 col-sm-9 col-md-9 col-lg-9");
			$("#first").addClass("col-xs-12 col-sm-12 col-md-12 col-lg-12");
			$("#second").hide();
			
			if($("#phone_number_a_2").val()){
				$("#formsubmit").before(''+
						'<div class="btn-group" style="margin-right:10px;" id="allcalls">' +
						'<a class="btn btn-success btn-sm" href="javascript:callByNow('+$("#phone_number_a").val() +');">呼叫'+window.parent.hiddenPhone($("#phone_number_a").val()) +'</a>' +
						'<a class="btn btn-success btn-lg dropdown-toggle" data-toggle="dropdown" href="javascript:void(0);">' +
							'<span class="caret"></span></a>' +
							'<ul class="dropdown-menu">' +
								'<li><a href="javascript:callByNow(0'+$("#phone_number_a").val() +');">0'+ window.parent.hiddenPhone($("#phone_number_a").val()) +'</a></li>' +
								'<li><a href="javascript:callByNow('+$("#phone_number_a_2").val() +');">'+ window.parent.hiddenPhone($("#phone_number_a_2").val()) +'</a></li>' +
								'<li><a href="javascript:callByNow(0'+$("#phone_number_a_2").val() +');">0'+ window.parent.hiddenPhone($("#phone_number_a_2").val()) +'</a></li>' +
							'</ul> ' +
						'</div>');
			}else{
				$("#formsubmit").before(''+
						'<div class="btn-group" style="margin-right:10px;" id="allcalls">' +
						'<a class="btn btn-success btn-sm" href="javascript:callByNow('+$("#phone_number_a").val() +');">呼叫'+window.parent.hiddenPhone($("#phone_number_a").val()) +'</a>' +
						'<a class="btn btn-success btn-lg dropdown-toggle" data-toggle="dropdown" href="javascript:void(0);">' +
							'<span class="caret"></span></a>' +
							'<ul class="dropdown-menu">' +
								'<li><a href="javascript:callByNow(0'+$("#phone_number_a").val() +');">呼叫0'+ window.parent.hiddenPhone($("#phone_number_a").val()) +'</a></li>' +
							'</ul> ' +
						'</div>');
			}
		}
		

		
		
        /** 校验字段  **/
        var validator =$('#cstmInfo').validate({
            rules : {
            	customer_id : { required : true, maxlength : 7, remote : {
            			type : "post",
            			url : getContext() + 'cstm/cstmIdRepeat',
            			data : { uid : function(){ return $('#uid').val(); }}
            		}
            	},
            	cstm_name :{ required : true, maxlength : 20, },
            	pool_id : { required : true },
            	start_date : { required : true}
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
            },
            errorPlacement : function(error, element) {
                error.insertAfter(element.parent());
            }
        });

        /* 提交按钮单击事件  */
        $('#formsubmit').click(function(){
            if($('#cstmInfo').valid()){
            	
                $('#msubmit').attr("disabled",true);
                if($("#phone_number_a").val()==""){
                	$("#phone_number_a").val($("#number1").val());
                }
                if($("#phone_number_a_2").val()==""){
                	$("#phone_number_a_2").val($("#number2").val());
                }

                $('#cstmInfo').submit();
            }
        });

        $('#cstmInfo').ajaxForm({
            dataType:"json",
            success: function(data) {
                if(data.success){
                	
                	/* 返回客户的UID */
                	$("#uid").val(data.cstmUuid);
                	
                    $.smallBox({
                        title : "操作成功",
                        content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
                        color : "#659265",
                        iconSmall : "fa fa-check fa-2x fadeInRight animated",
                        timeout : 2000
                    });
                    
                    //删除生数据
                    $.post(getContext() + 'userdata/deleteByPhone',{customerPhone:window.location.search.split(/[?&=]/)[2]},function(data){
                    	if(data.success === undefined) {
                    		
                    	} else if(!data.success){
                    		$.smallBox({
                                title : "操作失败",
                                content : "<i class='fa fa-clock-o'></i> <i>操作失败</i>",
                                color : "#C46A69",
                                iconSmall : "fa fa-times fa-2x fadeInRight animated",
                                timeout : 2000
                            });
                    	}
                    },"json");
                    
                    $('#dt_basic_cstm').DataTable().ajax.reload(null,false);;
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
                    cstmTabel.ajax.reload(null,false);;
                }
            }
        });
        
//         $("#customerTagss").tagsinput("build",{confirmKeys:[13,32]});

	})();

	
	//呼叫
	function callByNow(phoneNumber){
		
		$.post(getContext()+'cstm/callByNow',{phone:phoneNumber},function(d){
			
			if(d.success){
                $.smallBox({
                    title : "成功",
                    content : "<i class='fa fa-clock-o'></i> <i>正在呼叫"+window.parent.hiddenPhone(phoneNumber+"")+"请稍后...</i>",
                    color : "#659265",
                    iconSmall : "fa fa-check fa-2x fadeInRight animated",
                    timeout : 2000
                });
				$("#uid_b").val(d.call_session_uuid);
			}else{

                $.smallBox({
                    title : "失败",
                    content : "<i class='fa fa-clock-o'></i> <i>呼叫失败！</i>",
                    color : "#C46A69",
                    iconSmall : "fa fa-times fa-2x fadeInRight animated",
                    timeout : 3000
                });
			}
		},"json");
	}
	
	 $('#own_idss').select2({
        allowClear : true,
        width: '99%'
     });

     $('#pool_id').select2({
        allowClear : true,
        width: '99%'
     });
     
     $('#own_ids').select2({
    	 allowClear : true,
    	 width: '99%'
     });
     
     
 	$(".datetime:not([readonly=readonly])").datetimepicker({
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


<#macro showLabesl maps count inoutflag>
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
						<#list enumStr?replace("£¬",",")?split(",") as a>
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


<!-- 展示客户详细信息表单 -->
<#macro showLable maps>
	<#if maps??>
		<#assign keyIndex = 0>
		<#list maps?keys as key>
		
			<!-- 生成必填校验 0为必填 -->
			<#if (maps[key].allowEmpty) == "0">
				<#assign requireds = "required">
			<#else>
				<#assign requireds = "">
			</#if>
			
			<#if keyIndex%3 == 0>
			
				<!-- 判断是否换行 -->
				<#if keyIndex==3>
				</#if>
			
					</div>
				</section>
				<section>
					<div class="row">
			</#if>
			
			<#if (maps[key].columnType) == "DATE" >
				<label class="label col">${maps[key].columnName}</label>
				<div class="input col col-lg-2">
					<label class="input">
						<input name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" value="" class="datetime" />
					</label>
				</div>
				
			<#elseif (maps[key].columnType) == "ENUM" >
				<#assign enumStr = (maps[key].characterProperty)?string>
					<label class="label col">${maps[key].columnName}</label>
					<div class="form-group col col-lg-2">
						<select name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" class="form-control" >
							<#if requireds =="">
								<option value="">----请选择----</option>
							</#if>
							<#list enumStr?replace("，",",")?split(",") as a>
								<option value="${a!''}">${a!''}</option>
							</#list>
						</select>
					</div>
			<#else>
				<#if (maps[key].columnNameDB) == "pool_id">
					<label class="label col">${maps[key].columnName}</label>
					<div class="form-group col col-lg-2">
						<label class="select">
							<select name="pool_id" id="pool_ids" required="${requireds}">
								<option value="">----请选择----</option>
								<#if pools??>
									<#list pools as li>
										<#if pool_id?? && pool_id == (li.uid)>
											<option value="${li.uid!''}" selected="selected">${li.poolName!''}</option>
										<#else>
											<#if (li.beDefault)?? && li.beDefault == "1">
												<option value="${li.uid!''}" selected="selected">${li.poolName!''}</option>
											<#else>
												<option value="${li.uid!''}">${li.poolName!''}</option>
											</#if>
										</#if>
									</#list>
								<#else>
									<option value="" onclick="addPools();">添加客户池</option>
								</#if>
							</select>
						</label>
					</div>
				<#elseif (maps[key].columnNameDB) == "own_id">
				
						<input type="hidden" name="own_id" id="own_idss"> 
						<label class="label col">${maps[key].columnName}</label>
						<div class="form-group col col-lg-2">
							<label class="select">
								<select name="own_ids" id="own_ids" class="select2">
								<option value="${own_id!''}">${own_name!''}</option>
								</select>
								<select name="own_ids" id="own_ids" class="select2">
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
					
					<label class="label col">${maps[key].columnName}1</label>
					<div class="form-group col col-lg-2">
						<label class="input">
							<input type="hidden" name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a"  value="" class="${requireds}">
							<input name="number1" id="number1">
						</label>
					</div>							
					
					<label class="label col">${maps[key].columnName}2</label>
					<div class="form-group col col-lg-2">
						<label class="input">
							<input type="hidden" name="${(maps[key].columnNameDB)!''}_2" id="${(maps[key].columnNameDB)!''}_a_2"  value="${secPhone!''}" >
							<input name="number2" id="number2">
						</label>
					</div>
					<#assign keyIndex = keyIndex+1>
				<#else>
					
					<#if (maps[key].columnNameDB) != "customer_id">
						<#if (maps[key].columnType) == "INT">
							<label class="label col">${maps[key].columnName}</label>
							<div class="form-group col col-lg-2">
								<label class="input">
									<input name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" value="" class="digits">
								</label>
							</div>
						<#else>
							<label class="label col">${maps[key].columnName}</label>
							<div class="form-group col col-lg-2">
								<label class="input">
									<input name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" value="" class="${requireds}">
								</label>
							</div>
						</#if>
					<#else>
						<label class="label col">${maps[key].columnName}</label>
						<div class="form-group col col-lg-2">
							<label class="input">
								<#if uid??>
									<input type="hidden" name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a_1" value="${serialized!''}"/>
									<input id="${(maps[key].columnNameDB)!''}_a" value="${serialized!''}" class="${requireds}" disabled="disabled"/>
								<#else>
									<input type="hidden" name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" value="${serialized!''}"/> 
									<input id="${(maps[key].columnNameDB)!''}_a_1" class="${requireds}" value="${serialized!''}" disabled="disabled"/>
								</#if>
								<input type="hidden" name="uid" id="uid" value="${uid!''}">
							</label>
						</div>
					</#if>
				</#if>
			</#if>
			
			<#assign keyIndex = keyIndex + 1 >
		</#list>
	</#if>
</#macro>
