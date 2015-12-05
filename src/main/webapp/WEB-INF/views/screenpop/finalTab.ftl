<style>
	@IMPORT url("${s}/assets/css/popCssFile.css");
</style>
<div class="row" style="background-color: white;">
	<div id="newpop" align="center" class="row">
		<!-- 客户详细信息 -->
		<article class="col-xs-2 col-sm-2 col-md-2 col-lg-2"></article>
		<article class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
			<div class="jarviswidget" id="wid-id-1-tabs-customerinfo"
				data-widget-editbutton="false" data-widget-custombutton="false">
				<header>
					<span class="widget-icon"><i class="fa fa-edit"></i></span>
					<h2>客户详细信息</h2>
				</header>
				<div>
					<div class="widget-body no-padding" style="margin-bottom: -30px;">
						<!-- 客户信息展示 -->
						<div id="checkout-form" class="smart-form">
							<fieldset style="background: rgba(250, 250, 250, 1);">
								<section class="" style="width: 100%;">
									<!-- 客户信息展示 -->
									<div id="div1">
										<div id="first" class="" style="width: 100%;">
											<div class="jarviswidget-editbox"></div>
											<div class="widget-body">
												<div class="widget-body-toolbar"
													style="border-bottom: none;">
													<div class="smart-form"
														style="overflow-y: auto; overflow-x: hidden; height: auto; text-align: center;">
														<form id="newcstmInfo"
															action="${springMacroRequestContext.contextPath}/newuserdata/saveAll"
															class="smart-form" method="post">
															<!-- hidden area -->
															<input type="hidden" name="phoneStat" id="phoneStat" />
															<input type="hidden" value="${callsessionuuid!''}" id="call_session_uuid" name="call_session_uuid">
															<input type="hidden" name="phone_number" /> 
															<input type="hidden" name="phone_number_a" /> 
															<input type="hidden" name="log_uid" id="log_uid" /> 
															<input type="hidden" name="data_id" id="data_id" /> 
															<input type="hidden" name="call_time" id="call_time" /> 
															<input type="hidden" name="in_out_flag" id="in_out_flag" /> 
															<input type="hidden" name="data_source" id="data_source" /> 
															<input type="hidden" name="call_phone" id="call_phone" /> 
															<input type="hidden" name="agent_id" id="agent_id" /> 
															<input type="hidden" name="talk_time" id="talk_time" /> 
															<input type="hidden" name="agent_name" id="agent_name" /> 
															<input type="hidden" name="callStat" id="callStat" /> 
															
															<input type="hidden" name="scheduleId" id="scheduleId" value="${scheduleId!''}" /> 
															<input type="hidden" name="oldstime" id="oldstime" value="${time!''}" />

															<fieldset style="background-color: #FAFAFA">
																<legend style="text-align: right;" id="legend-id">
																	客户匹配数：${sameCount!'0'}&nbsp;&nbsp; <#if sameCount?? &&
																	sameCount &gt; 1>
																	<div class="btn-group" style="margin-bottom: 8px;">
																		<button class="btn btn-xs btn-default dropdown-toggle"
																			data-toggle="dropdown" id="btnText">
																			${(sameList[0].customerName)!''} <span class="caret"></span>
																		</button>
																		<ul class="dropdown-menu pull-right">
																			<#if samelist??> <#list samelist as l>
																			<li><a
																				href="javascript:changeCustomerInfo('${(l.customerId)!''}','${(l.customerName)!''}');">${(l.customerName)!''}</a></li>
																			</#list> </#if>
																		</ul>
																	</div>
																	</#if> <input type="hidden" name="poptype" value="" />
																	<input type="hidden" name="status" id="status" value="" />
																</legend>
															</fieldset>
															<div class="row" style="padding-top: 10px;">
																<div id="editedForm">
																	<section>
																		<div class="row">
																			<label class="label col col-2">号码归属地</label>
																			<div class="form-group col col-3">
																				<label class="label col">${location!'未知地区'}</label>
																			</div>
																			
																			<!-- 如果是意向客户 不显示编号  成交客户 显示编号 -->
																			<#if Acstm?? && Acstm["status"]=="1">																	<label class="label col col-2">客户编号</label>
																			<div class="form-group col col-3">
																				<label class="input"> <input
																					id="customer_id" name="customer_id" value=""
																					class="required" disabled="disabled"><input
																					type="hidden" name="uid" id="uid" value="">
																				</label>
																			</div>
																			</#if>
																			
																		</div>
																	</section>
																	<section>
																		<div class="row">
																			<label class="label col col-2">客户姓名</label>
																			<div class="form-group col col-3">
																				<label class="input"> <input
																					name="cstm_name" id="cstm_name" value="未知"
																					class="required">
																				</label>
																			</div>
																			<label class="label col col-2">拥有者</label>
																			<div class="form-group col col-3">
																				<label class="select"> <select name="own_id"
																					id="own_id" style="text-align: left;">
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
																		</div>
																	</section>
																	<section>
																		<div class="row">
																			<label class="label col col-2">号码一</label>
																			<div class="form-group col col-3">
																				<div class="input-group">
																					<label class="input"><input type="text" class="form-control" id="phone_number" value="" class="required"></label>
																					<span class="input-group-addon" title="直接呼叫" onclick="callThisSpan(this,1)"><i class="fa fa-phone"></i></span><span class="input-group-addon" title="加0呼叫" onclick="callThisSpan(this,0)"><i class="fa fa-phone-square"></i></span>
<!-- 																					<div class="input-group-btn btn btn-xs btn-default"> -->
<!-- 																						<button type="button" -->
<!-- 																							class="btn btn-xs btn-default dropdown-toggle" -->
<!-- 																							data-toggle="dropdown" tabindex="-1"> -->
<!-- 																							<span class="caret"></span> -->
<!-- 																						</button> -->
<!-- 																						<ul class="dropdown-menu pull-right" role="menu"> -->
<!-- 																							<li><a href="javascript:void(0);" onclick="callThisNumber(this);">呼叫</a></li> -->
<!-- 																							<li><a href="javascript:void(0);" onclick="callThisNumber(this);">加0呼叫</a></li> -->
<!-- 																						</ul> -->
<!-- 																					</div> -->
																				</div>
																			</div>
																			<label class="label col col-2">号码二</label>
																			<div class="form-group col col-3">
																				<div class="input-group">
																					<label class="input"><input type="text" class="form-control" id="phone_number_a" value="${secPhone!''}" class="required"></label>
																					<span class="input-group-addon" title="直接呼叫" onclick="callThisSpan(this,1)"><i class="fa fa-phone"></i></span><span class="input-group-addon" title="加0呼叫" onclick="callThisSpan(this,0)"><i class="fa fa-phone-square"></i></span>
<!-- 																					<div class="input-group-btn btn btn-xs btn-default"> -->
<!-- 																						<button type="button" class="btn btn-xs btn-default dropdown-toggle" data-toggle="dropdown" tabindex="-1"> -->
<!-- 																							<span class="caret"></span> -->
<!-- 																						</button> -->
<!-- 																						<ul class="dropdown-menu pull-right" role="menu"> -->
<!-- 																							<li><a href="javascript:void(0);" onclick="callThisNumber(this);">呼叫</a></li> -->
<!-- 																							<li><a href="javascript:void(0);" onclick="callThisNumber(this);">加0呼叫</a></li> -->
<!-- 																						</ul> -->
<!-- 																					</div> -->
																				</div>
																			</div>
																		</div>
																	</section>
																	<section>
																		<div class="row">
																			<label class="label col col-2">预约时间</label>
																			<div class="form-group col col-3">
																				<label class="input"><input type="text"
																					name="reminderTime" id="reminderTime"
																					value="${time!''}"> </label>
																			</div>
																			<label class="label col col-2">预约内容</label>
																			<div class="input col col-3">
																				<label class="input"><textarea
																						name="reminderTimeContent"
																						id="reminderTimeContent" rows="" cols=""
																						style="width: 100%; resize: none; height: 50px;">${content!''}</textarea>
																				</label>
																			</div>
																		</div>
																	</section>
																	<section>
																		<div class="row">
																			<label class="label col col-2">内容小记</label>
																			<div class="input col col-8" align="center;">
																				<label class="input"><textarea
																						name="text_log" id="text_log" rows="" cols=""
																						style="width: 100%; resize: none; height: 50px;"></textarea>
																				</label>
																			</div>
																		</div>
																	</section>
																	<section>
																		<div class="row">
																			<fieldset style="background: rgba(250, 250, 250, 1);">
																				<section>
																					<div class="row">
																						<label class="label col col-2">客户分类</label>
																						<div class="col col-3" id="chooseType">
																							<select name="result" id="result">
																								<optgroup label="未接通时" class='noanswer' id="noanswer">
																									<#if defaultUserNocall?? && defaultUserNocall=="noanswer">
																										<option value="noanswer"  selected='selected' >无人接听</option>
																									<#else>
																										<option value="noanswer"  >无人接听</option>
																									</#if>
																										<#if defaultUserNocall?? && defaultUserNocall=="global_share">
																										<option value="global_share"  selected='selected' >共享池</option>
																									<#else>
																										<option value="global_share"  >共享池 </option>
																									</#if>
																									<#if defaultUserNocall?? && defaultUserNocall=="abandon">
																										<option value="abandon"  selected='selected'>号码无效</option>
																									<#else>
																										<option value="abandon" >号码无效</option>
																									</#if>
																								</optgroup>
																								<optgroup id="intent" label="意向客户">
																									<!-- 如果原来有意向 默认选中该意向  -->
																									<#if intents??> 
																										<#list intents as intent> 
																											<#if (entry.intentType)?? && intent.uid="entry.intentType">
																												<option class="intent" selected='selected' value="${intent.uid}">${intent.intentName}</option>
																											<#elseif pool_id?? && pool_id == intent.uid>
																												<option class="intent" selected='selected' value="${intent.uid}">${intent.intentName}</option>
																											<#else>
																												<option class="intent" value="${intent.uid}">${intent.intentName}</option>
																											</#if> 
																										</#list> 
																									</#if>
																								</optgroup>
																								<optgroup label="接通时" class='answered'>
																									<!-- 																					<option value="customer">成交客户</option> -->
																									<option value="global_share">共享池</option>
																									<option value="blacklist">黑名单</option>
																								</optgroup>
																							</select>
																						</div>
																						<div class="col col-2"></div>
																						<div class="col col-3">
																							<section>
																								<div class="row" id="operate">
																									<div class="btn btn-primary btn-lg" id="saveCall">保存并关闭</div>
																								</div>
																							</section>
																						</div>
																					</div>
																				</section>
																			</fieldset>
																		</div>
																	</section>
																</div>
															</div>
														</form>
													</div>
												</div>
											</div>
										</div>
									</div>
								</section>
							</fieldset>
						</div>
					</div>
				</div>
			</div>
		</article>
		<article class="col-xs-2 col-sm-2 col-md-2 col-lg-2"></article>
	</div>
	<!-- 客户业务信息 -->
	<div class="row">
		<article class="col-xs-2 col-sm-2 col-md-2 col-lg-2"></article>
		<article class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
			<div class="jarviswidget" id="wid-id-1-tabs-customerinfo"
				data-widget-editbutton="false" data-widget-custombutton="false">
				<header>
					<span class="widget-icon"><i class="fa fa-edit"></i> </span>
					<h2>客户沟通记录</h2>
				</header>
				<div>
					<div class="widget-body no-padding">
						<!--  显示客户业务tab展示  标签页  -->
						<div id="div2" style="padding: 0px; border: none;">
							<!-- 标签页 -->
							<ul id="myTab1" class="nav nav-tabs bordered">
								<li class="active"><a href="#poplog" data-toggle="tab" onclick="getActive('poplog');">沟通记录</a></li>
					            <li><a href="#phoneDetail" data-toggle="tab" id="detailfPhone">电话详情</a></li>
<!-- 								<li><a href="#poplog" data-toggle="tab" onclick="getActive('poplog')">沟通记录</a></li> -->
							</ul>
							<!-- 标签页内容 -->
							<div id="myTabContentss" class="tab-content"style="padding-left: 8px; padding-right: 8px;">
								<div class="tab-pane active" id="poplog"></div>
								<div class="tab-pane fade" id="phoneDetail"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</article>
	</div>
</div>
<script type="text/javascript"
	src="${springMacroRequestContext.contextPath}/assets/datetimepicker.js"></script>
<script type="text/javascript">

	var phoneStat = {
		currentStat : 0,
		EntryNotIntent : 0,		//在自己的数据表中，不是意向客户 不能转换为黑名单 黑名单选项移除掉
		EntryIntent : 1,		//在自己的数据表中，是自己的意向客户
		NotEntryNotAcstm : 2,	//不在自己的数据表中，且没有数据详细信息(可能在某人的任务中，也可能是某人的意向)
		NotEntryAcstmNotMe : 3,	//不在自己的任务表中，但是有数据详细信息，但是数据详细信息中的拥有者不是自己(可能是某人的意向，或者某人的成交)
		NotEntryAcstmMe : 4		//是自己的成交客户
	};
	
	var noAnswer = $("#result .noanswer");
	var answered = $("#result .answered");
	var intentTypes = $("#result #intent");
	
	// 0 不禁用客户姓名 客户分类 归属人
	// 1 禁用客户姓名 客户分类 归属人
	function changeAcstmInputStat(stat){
		var func = stat?"attr":"removeAttr";
		$("#cstm_name")[func]("disabled","disabled");
		$("#pool_id")[func]("disabled","disabled");
		$("#own_id")[func]("disabled","disabled");
	}
	
	/* 呼叫 */
	function callThisSpan(obj,str){

		var phoneNUmber = $(obj).prevUntil("div.input-group").children('input').val();
		
		if(phoneNUmber.length < 0){
			$.smallBox({
                title : "不能呼叫",
                content : "<i class='fa fa-clock-o'></i> <i>没有电话号码不能呼叫</i>",
                color : "#C79121",
                iconSmall : "fa fa-times fa-2x fadeInRight animated",
                timeout : 5000
            });
		}
		
		/* 加0呼叫  */
		if(str == 0){
			phoneNUmber = "0"+phoneNUmber;
		}
		
		$.post(getContext() + "newuserdata/makecall", {phone:phoneNUmber},function(data) {
			//更新callSessionUUid
			if(data.success){
				window.parent.changeTitleMethod(phoneNUmber);
			}else{
				$.smallBox({
                    title : "不能呼叫",
                    content : "<i class='fa fa-clock-o'></i> <i>"+ data.message +"</i>",
                    color : "#C79121",
                    iconSmall : "fa fa-times fa-2x fadeInRight animated",
                    timeout : 5000
                });
			}
		},"json");
	}
	
	
	//直接拨打号码
	function callThisNumber(thiz,obj){
		
		var phoneNUmber = $(thiz).parents("div.input-group").children('input').val();
		
		if(phoneNUmber.length > 0){
			//加0呼叫
			if(obj == 0){
				phoneNUmber = "0"+phoneNUmber;
			}
			
			$.post(getContext() + "newuserdata/makecall", {phone:phoneNUmber},function(data) {
				//更新callSessionUUid
				if(data.success){
					window.parent.changeTitleMethod(phoneNUmber);
				}else{
					$.smallBox({
	                    title : "不能呼叫",
	                    content : "<i class='fa fa-clock-o'></i> <i>"+ data.message +"</i>",
	                    color : "#C79121",
	                    iconSmall : "fa fa-times fa-2x fadeInRight animated",
	                    timeout : 5000
	                });
				}
			},"json");
		}
	}
	
	// 1 添加属性 禁用电话号码
	// 0 移除属性 不禁用电话号码
	function changePhoneinputStat(stat){
		var func = stat?"attr":"removeAttr";
		$("#phone_number")[func]("disabled","disabled");
		
		if($("#phone_number_a").val().length >= 1){
			$("#phone_number_a")[func]("disabled","disabled");
		}
	}
	
	function changeCallSessionUuid(callSessionUuid){
		
		$("#call_session_uuid").val(callSessionUuid);
	}
	
	function changeResultStat(stat){
		var func = stat?"attr":"removeAttr";
		$("#result")[func]("disabled","disabled");
	}
 	
	$(function(){
		if($("#call_session_uuid").val()!="" && $("#call_session_uuid").val() != "undefined" ){
			$.post(getContext()+'calllog/get',{uuid:$("#call_session_uuid").val()},function(data){
				for(var k in data.entry){
					$("#" + k).val(data.entry[k]);
				}
			},"json");
		}
		/* 校验号码不能相同 */
    	jQuery.validator.addMethod("notEqualTo", function(value, element, param) {
    		return this.optional(element) ||value != $(param).val();
		}, $.validator.format("两个电话号码不能相同"));
		popOptionStat = $("#result .answered");
		$("#result").select2({
    		width:"100%",
    	});
		$("#own_id").select2({
    		width:"100%",
    	});
        /* 添加日期时间控件 */
        $('#reminderTime').datetimepicker({
            format: "yyyy-mm-dd hh:ii:ss",
            weekStart: 1,
            todayBtn:  1,
    		autoclose: 1,
    		todayHighlight: 1,
    		startView: 2,
    		forceParse: 0,
            showMeridian: 1,
            language: "zh-CN"
    	});
      $("div#tabs2 ul li[aria-selected='true'] a").html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +"弹屏|"+$("#hidPhone").val());
		/* 表单赋值 */
		<#if Acstm?? >
			<#list Acstm?keys as k>
				$("#${(k)!''}").val('${(Acstm[k])!''}');
// 				$("#${(k)!''}_a").val('${(Acstm[k])!''}');
			</#list>
		<#else>
			/* 如果不是客户，第一次打进系统 绑定电话号码 编号 日期 */
			$("#phone_number").val('${newPhoneNumber!''}');
			$("#customer_id").val('${customer_id!''}');
		</#if>
		
		//隐藏表单值赋值
		$("#newcstmInfo input[name='phone_number']").val($("#phone_number").val());
		$("#newcstmInfo input[name='phone_number_a']").val($("#phone_number_a").val());
		//添加隐藏号码
		$("#phone_number").val(window.parent.hiddenPhone($("#phone_number").val()));
		$("#phone_number_a").val(window.parent.hiddenPhone($("#phone_number_a").val()));
		
		//赋值当前号码的状态
		<#if entry??>
			<#if (entry.intentType)??>
				//在自己的任务中 是意向客户
				phoneStat.currentStat = phoneStat.EntryIntent;
				phoneStat.intentType = '${entry.intentType}';
			<#else>
				//在自己的数据表中，不是意向客户
				phoneStat.currentStat = phoneStat.EntryNotIntent;
			</#if>
		<#else>
			<#if Acstm??>
				/* 
					第一个和第二个条件其实是一致的  理由是写一起太长，所以换行写了  
					区别是：是我的成交客户 
				*/
				//不是自己的意向客户
				<#if own_id?? && currentUser??  && own_id!=currentUser.uid && Acstm["status"] == "0"> 
					phoneStat.currentStat = phoneStat.NotEntryAcstmNotMe;
				//隐含条件还是不是我的意向客户  status=0  =="" -->意向 status = 1 
				<#elseif Acstm["status"]?? && (Acstm["status"]=="0" || Acstm["status"]=="")>
					phoneStat.currentStat = phoneStat.NotEntryAcstmNotMe;
				<#else>
					// 是自己的成交客户
// 					phoneStat.currentStat = phoneStat.NotEntryAcstmNotMe;
					phoneStat.currentStat = phoneStat.NotEntryAcstmMe;
				</#if>
				
			<#else>
				//数据详细信息中不存在 也不是自己的客户 可能是别人的任务，在别人到意向
				phoneStat.currentStat = phoneStat.NotEntryNotAcstm;
			</#if>
		</#if>
		
		/** 校验字段  **/
        var validator =$('#newcstmInfo').validate({
            rules : {
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
            		notEqualTo : "#phone_number_a",
					remote : {
						type : "post",
            			url : window.parent.getContext() + 'cstm/checkPhoneRepeat',
            			data : { uid : function(){ return $('#uid').val(); }}
            		}
            	}
            },
            messages : {
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
		
		
        $('#newcstmInfo').ajaxForm({
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
                    
                    window.parent.$("#forModel div#tabs2 ul li.ui-state-default.ui-corner-top.ui-tabs-active.ui-state-active span.air.air-top-left.delete-tab button").click();
                } else {
                	$.smallBox({
                        title : "操作失败",
                        content : "<i class='fa fa-clock-o'></i> <i>操作失败" + data.message + "</i>",
                        color : "#C46A69",
                        iconSmall : "fa fa-check fa-2x fadeInRight animated",
                        timeout : 2000
                    });
                }
            },
            submitHandler : function(form) {
                $(form).ajaxSubmit({
                    success : function() {
                        $("#newcstmInfo").addClass('submited');
                    }
                });
            },
            error: function(XMLHttpRequest,textStatus , errorThrown){
                if(textStatus=='error'){
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
		
		$("#result").change(function(){
			if($("#result option:selected" ).val()==="customer" || $("#result option:selected" ).hasClass("intent")) {
				changeAcstmInputStat(0);
	        } else {
				changeAcstmInputStat(1);
    	    }
		});

	$("#phoneStat").val(phoneStat.currentStat);
	
	//如果号码状态是   在自己的数据表中，不是意向客户  禁用客户姓名、客户分类、归属人
	if (phoneStat.currentStat === phoneStat.EntryNotIntent) {
		changeAcstmInputStat(1);
		changePhoneinputStat(1);
	}
	
	//如果号码状态是   在自己的数据表中，不是意向客户
	if (phoneStat.currentStat === phoneStat.EntryIntent) {
		$("#chooseType #result [value='abandon']").remove();
		$("#chooseType #result [value='blacklist']").remove();
		$("#chooseType #result").val(phoneStat.intentType).change();
		changePhoneinputStat(1);
	}

	//如果号码状态是 不在自己的数据表中，且没有数据详细信息(可能在某人的任务中，也可能是某人的意向)
	if (phoneStat.currentStat === phoneStat.NotEntryNotAcstm) {
		changeAcstmInputStat(1);
		changePhoneinputStat(1);
// 		changeResultStat(1);
	}

	//如果号码状态是 不在自己的任务表中，但是有数据详细信息，但是数据详细信息中的拥有者不是自己(可能是某人的意向，或者某人的成交)
	// 不是自己的意向客户
	if (phoneStat.currentStat === phoneStat.NotEntryAcstmNotMe) {
// 		$("#chooseType #result").append("<option value='customer' selected='selected'>成交客户</option>").change();
		changePhoneinputStat(1);//禁用电话号码
		changeResultStat(1);//禁用客户分类
		changeAcstmInputStat(1);//禁用输入框
	}

	//如果号码状态是  是自己的成交客户 禁用
	if (phoneStat.currentStat === phoneStat.NotEntryAcstmMe) {
		$("#chooseType #result").append("<option value='customer' selected='selected'>成交客户</option>").change();
		$("#chooseType #result").val("customer").change();
		changeAcstmInputStat(1);
		changePhoneinputStat(1);
		changeResultStat(1);
	}
	
	noAnswer = $("#result .noanswer");
	<#if !type?? || type!="detail">
		answered = $("#result .answered").remove();
		intentTypes = $("#result #intent").remove();
	<#else>
		answered = $("#result .answered");
		intentTypes = $("#result #intent");
	</#if>
	
	$('#saveCall').click(function(){
		//关闭tab后需要重置掉该变量，全局变量存在于newindex.ftl
		window.parent.closetag = "0";
		if($('#saveCall').attr("disabled")) {	//只允许保存一次
			return;
		}
		if($('#newcstmInfo').valid()){
			$('#saveCall').attr("disabled", "disabled");
            $('#msubmit').attr("disabled",true);
            $("input[name='phone_number_a']").val($("#phone_number_a").val())
            $('#newcstmInfo').submit();
        }
		
	});
	
	/* 请求第一个tab的数据 issue */
	function getActive(str){
		/* popEventHandler中查看具体逻辑 */
		/* 沟通小计  */
		if(str == "poplog"){
			/* 返回页面 mod-cstm-pop-log  */
			$.post(getContext()+"cstm/poplog",function(data){
				$("#poplog").empty();
				$("#poplog").append(data);
			});
		/* 订单信息 */
		}else if(str == 'order'){
			$.post(getContext()+"neworderinfo",{level:"pop",cstmId:$("#phone_number_a").val()+","+$("#phone_number_a_2").val()},function(data){
				$("#order").empty();
				$("#order").append(data);
			});
		}else if(str == 'appointment'){
			$.post(getContext()+"schedulereminder",{level:"pop",cstmId:$("#uid").val(),phone1:$("#phone_number_a").val(),phone2:$("#phone_number_a_2").val()},function(data){
				$("#appointment").empty();
				$("#appointment").append(data);
			});
		}else if(str == 'phoneDetail'){
			
			
			

		}
	}
	
	getActive("poplog");
		
	});
	
	$("#detailfPhone").click(function(){
		
		$.post(getContext()+"newuserdata/getPhoneJson",{phone:$("#phone_number").val()},function(data){
			$("#phoneDetail").empty();
			$("#phoneDetail").append(data);
		});
	});
	

	//这里没有考虑进入弹屏时就已经是通话状态的情况，改成iframe之后应该没有这种情况，所以暂时排除
	function updateCallStatus(callNum) {
		if($("#newcstmInfo [name='phone_number']").val() === callNum) {
			if (!$("#result").attr("disabled")) {
				$("#newcstmInfo #result .noanswer").remove();
				if($("#newcstmInfo #result #intent").length == 0) {
					$("#newcstmInfo #result").append(intentTypes).append(answered);
					//如果接通后根据个人配置中配置的默认选项，更改选中值
					<#if intents??> 
						<#list intents as intent> 
							<#if !(entry.intentType)?? && userconfig?? && userconfig == intent.uid>
								$(intentTypes).find("option[value='${userconfig}']").attr("selected",true);
							</#if>
						</#list> 
					</#if>
					<#if !(entry.intentType)?? && userconfig?? && userconfig=="global_share">
						$(answered).children("option").eq(0).attr("selected",true);
					</#if>
				}
				$("#newcstmInfo #result").trigger("change");
				//0未通，1接通
				$("#callStat").val(1);
			}
		}	
	}
	
	//关闭弹屏时默认保存到共享池
	function closeSave(){
		if($('#saveCall').attr("disabled") || $("#result").attr("disabled")) {	//只允许保存一次
			return;
		}
		if($("#newcstmInfo input[name='phone_number']").val()) {
			//接通时再保存
			if($("#result .answered").length > 0) {
				//不是点详情打开的
				if($("#result .noanswer").length == 0) {
					console.log("打过电话后直接关闭弹屏，再做的保存！");
					$("#result").val("global_share").change();
					$("#saveCall").click();
				}else{
					console.log("点击详情后打开的Tab，直接关闭，不做处理！");
				}
				
			}
		} 
	}

</script>