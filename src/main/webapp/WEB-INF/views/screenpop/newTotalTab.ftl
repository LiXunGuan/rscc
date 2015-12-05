
<style type="text/css">
	
	.smart-form legend {
    	padding-top: 0px;
	}
	
	/* 设置电话详细信息下的所有显示信息为不可编辑 背景透明 */
	#newphoneInfo input{
		border: none;
		background-color: transparent;
	}
	
	#newdataInfo input{
		border: none;
		background-color: transparent;
	}
	
	/* 字体间距 */
	#first .label.col {
		width: 90px;
		text-align: right;
		padding-left: 12px;
		padding-right: 12px;
	}
	
	.input input.readOnly {
		background-color: transparent;
		border: none;
	}
	
	div .select select.readOnly {
		background-color: transparent;
		border: none;
	}
	
 	.row .label.col.col-1 { 
 		width: 100px;
 		text-align: right; 
 		text-justify: inter-ideograph; 
 	}
 	
 	.smart-form section{
 		margin-bottom: 5px;
 	}
 	.smart-form fieldset{
 		padding-top: 5px;
 	}
 	
 	.jarviswidget{
  		margin-bottom: 20px; 
 	}
 	
 	.smart-form.widget-body-toolbar, .widget-body-toolbar {
 		border-bottom: none;
 	}
	
	#newcstmInfo div section input[disabled] {
    	cursor: not-allowed;
    	background-color: #eee;
    	opacity: 1;
	}
	#newcstmInfo div section select[disabled] {
    	cursor: not-allowed;
    	background-color: #eee;
    	opacity: 1;
	}
</style>

<div class="row">
	<div id="newpop">
		<article class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
			<div class="jarviswidget" id="wid-id-1-tabs-new" data-widget-editbutton="false" data-widget-custombutton="false">
				<header>
					<span class="widget-icon" style=""> <i class="fa fa-edit"></i>
					</span>
					<h2>电话详细信息</h2>
				</header>
				<div>
					<input type="hidden" value="${callsessionuuid!''}" id="call_session_uuid" name="call_session_uuid">
					<div class="jarviswidget-editbox"></div>
					<div class="widget-body no-padding">
						<div class="widget-body-toolbar" style="border-bottom: none; background-color: transparent;">
						<div class="row">
						<div class="col col-6">
							<div class="smart-form" id="newphoneInfo">
								<section>
									<div class="row" style="">
										<label class="label col col-1">客户号码</label>
										<label class="input col col-3">
											<input type="hidden" value="${titleContent!''}" id="hidPhone">
                               				<input name="special" id="special" value="pop" type="hidden">
                               				<input name="cstmserviceId" id="cstmserviceId"  value="${titleContent!''}">
                           				</label>
                           				
										<label class="label col col-1">来/去电时间</label>
										<label class="input col col-4">
                               				<input name="timestamp" id="timestamp"  value="${timestamp!'${.now?datetime}'}">
                           				</label>
									</div>
								</section>

								<section>
									<div class="row" style="">
                           				
										<label class="label col col-1">号码归属</label>
										<label class="input col col-3">
                               				<input name="location" id="location"  value="${location!'未知'}">
                           				</label>
                           				
										<label class="label col col-1">热线号码</label>
										<label class="input col col-4">
                               				<input name="access_number" id="access_number"  value="${access_number!'未知'}">
                           				</label>
<!-- 										<label class="label col col-1">通话类型</label> -->
<!-- 										<label class="input col col-2"> -->
<!--                                				<input name="call_direction" id="call_direction"  value="${call_direction!'未知'}"> -->
<!--                            				</label> -->
										
									</div>
								</section>
								
								<section>
									<div class="row" style="">
									<label class="label col col-1">来电描述</label>
										<label class="input col col-3">
                               				<input name="ivr_desc" id="ivr_desc"  value="${ivr_desc!'未知'}">
                           				</label>
									</div>
								</section>
								
								
							</div>
						</div>
						</div>
						</div>
					</div>
				</div>
			</div>
		</article>
		<article class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
			<div class="jarviswidget" id="wid-id-1-tabs-new" data-widget-editbutton="false" data-widget-custombutton="false">
				<header>
					<span class="widget-icon" style=""> <i
						class="fa fa-edit"></i>
					</span>
					<h2>数据详细信息</h2>
				</header>
				<div>
					<input type="hidden" value="${callsessionuuid!''}" id="call_session_uuid" name="call_session_uuid">
					<div class="jarviswidget-editbox"></div>
					<div class="widget-body no-padding" style="min-height:127px">
						<div class="widget-body-toolbar" style="border-bottom: none; background-color: transparent;">
						<div class="row">
						<div class="col col-6">
							<div class="smart-form" id="newdataInfo">
								<div class="row last">
								</div>
							</div>
						</div>
						</div>
						</div>
					</div>
				</div>
			</div>
		</article>
		

		<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">

			<div class="jarviswidget" id="wid-id-1-tabs-customerinfo"
				data-widget-editbutton="false" data-widget-custombutton="false">
				<header>
					<span class="widget-icon" style="padding-top: 10;"> <i class="fa fa-edit"></i>
					</span>
					<h2>客户详细信息</h2>
				</header>
				<div>
					<div class="widget-body no-padding">

						<!-- 客户信息展示 -->
						<div id="checkout-form" class="smart-form" novalidate="novalidate">
							<fieldset style="background: rgba(250, 250, 250, 1);">
								<section class="col col-lg-12">

									<!-- 客户信息展示 -->
									<div id="div1">
										<div id="div-1">
											<div id="first" class="col-xs-12 col-sm-12 col-md-12 col-lg-12" >
												<div class="jarviswidget-editbox"></div>
												<div class="widget-body">
													<div class="widget-body-toolbar" style="border-bottom: none;">
														<div class="smart-form" style=" overflow-y: auto;overflow-x: hidden; height:200px;">
											           		<form id="newcstmInfo" action="${springMacroRequestContext.contextPath}/cstm/save" class="smart-form" method="post">
											               		<legend style="text-align: right;" id="legend-id">客户匹配数：${sameCount!'0'}&nbsp;&nbsp;
											               			<#if sameCount &gt; 1 >
											               				<div class="btn-group" style="margin-bottom: 8px;">
																			<button class="btn btn-xs btn-default dropdown-toggle" data-toggle="dropdown" id="btnText"> ${(sameList[0].customerName)!''} <span class="caret"></span></button>
																			<ul class="dropdown-menu pull-right">
																				<#list sameList as l>
																					<li><a href="javascript:changeCustomerInfo('${(l.customerId)!''}','${(l.customerName)!''}');">${(l.customerName)!''}</a></li>
																				</#list>
																			</ul>
																		</div>
											               			</#if>
											               			<input type="hidden" name="poptype" value="task" />
											               			<input type="hidden" name="status" id="status" value="" />
											               		</legend>
											               		<div class="row">
												               		<div id="editedForm" class="col col-9">
													               		<section>
																			<div class="row">
																				<@showLable maps></@showLable>
																			</div>
																		</section>
																	</div>
																	<div class="col col-3" id="calllogsave"> 
																		<section>
																			<div class="row">
																				<fieldset style="background: rgba(250, 250, 250, 1);">
																					<section style="height:10px;">
																					</section>
																           			<section>
																						<div class="row">
<!-- 																						<div class="col col-3"> -->
<!-- 																							<section> -->
<!-- 																							<section style="height:10px;"> -->
<!-- 																							</section> -->
<!-- 																		           			<div class="row" id="operate"> -->
<!-- 																		           				<div class="btn btn-success btn-lg " id="startCall"> -->
<!-- 																									呼叫 -->
<!-- 																								</div> -->
<!-- 																							</div> -->
<!-- 																							</section> -->
<!-- 																						</div> -->
																						<div class="col col-6" id="chooseType">
																           					<span>选择分类</span>
																							<select name="result" id="result">
																								<optgroup label="未接通时" class='noanswer'>
																								<option value="noanswer">无人接听</option>
																								<option value="abandon">号码无效</option>
																								</optgroup>
																								<optgroup label="接通时" class='answered'>
																								<option value="intent">意向客户</option>
																								<option value="customer">成交客户</option>
																								<option value="global_share">共享池</option>
																								<option value="blacklist">黑名单</option>
																								</optgroup>
																							</select>
																							<optgroup id="allIntent" style="display:none">
																							<#if intents??>
																								<#list intents as intent>
																									<#if (entry.intentType)?? && intent.uid=entry.intentType>
																										<option selected='selected' value="${intent.uid}">${intent.intentName}</option>
																									<#else>												
																										<option value="${intent.uid}">${intent.intentName}</option>
																									</#if>
																								</#list>
																							</#if>
																							</optgroup>
																						</div>
																						<div class="col col-3">
																							<section>
																							<section style="height:10px;">
																							</section>
																		           			<div class="row" id="operate">
																								<div class="btn btn-primary btn-lg " id="saveCall">
																									保存
																								</div>
																							</div>
																							</section>
																						</div>
																						</div>
																					</section>
																					<section style="height:30px;">
																					</section>
<!-- 																					<section> -->
<!-- 																	           			<div class="row" id="operate"> -->
<!-- 																	           				<div class="btn btn-success btn-lg " id="startCall"> -->
<!-- 																								呼叫 -->
<!-- 																							</div> -->
<!-- 																							<div class="btn btn-primary btn-lg " id="saveCall"> -->
<!-- 																								保存 -->
<!-- 																							</div> -->
<!-- 																						</div> -->
<!-- 																					</section> -->
															              		</fieldset>
																			</div>
																		</section>
																	</div>
																</div>
											 			<!-- 		<section> 
																	<div class="row">
																		<label class="label col">客户标签</label>
																		<div class="form-group col col-lg-8"> -->
																			<#if tagsStr?? && (tagsStr?length) &gt; 0 >
																				<input class="" type="hidden" style="color: white;" id="customerTagss" name="customerTags" value="${tagsStr?substring(1,tagsStr?length-1)}" >
											<!-- 									<input class="form-control tagsinput" type="hidden" style="color: white;" id="customerTagss" name="customerTags" value="${tagsStr?substring(1,tagsStr?length-1)}" data-role="tagsinput"> -->
																			<#else>
																			<input class="" type="hidden" style="color: white;" id="customerTagss" name="customerTags" value="" placeholder="按回车确定" >
											<!-- 									<input class="form-control tagsinput" type="hidden" style="color: white;" id="customerTagss" name="customerTags" value="" placeholder="按回车确定" data-role="tagsinput"> -->
																			</#if>
																		  <!-- </div>
																	</div>
																</section>  -->
																
																
																
<!-- 									<div class="row"> -->
<!-- 										<div class="col col-3"> -->
<!-- 											<section> -->
<!-- 						           				<div class="btn btn-success btn-sm" id="startCall"> -->
<!-- 													呼叫 -->
<!-- 												</div> -->
<!-- 											</section> -->
<!-- 										</div> -->
<!-- 									</div> -->
															</form>
														</div>
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
		
		<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<div class="jarviswidget" id="wid-id-1-tabs-customerinfo"
				data-widget-editbutton="false" data-widget-custombutton="false">
				<header>
					<span class="widget-icon" style="padding-top: 10;"> <i class="fa fa-edit"></i>
					</span>
					<h2>客户业务信息</h2>
				</header>
				<div>
					<div class="widget-body no-padding">
					
						<!--  显示客户业务tab展示  标签页  -->
						<div id="div2" style="padding: 0px; border: none;">
							<!-- 标签页 -->
							<ul id="myTab1" class="nav nav-tabs bordered">
								<#if showTabs??> 
									<#list showTabs?keys as key> 
										<#if key_index == 0>
												<li class="active"><a href="#${key}" data-toggle="tab" onclick="getActive('${key}')"> ${showTabs[key]}</a></li> 
											<#else>
												<li><a href="#${key}" data-toggle="tab" onclick="getActive('${key}')"> ${showTabs[key]}</a></li> 
											</#if>
									</#list> 
								</#if>
							</ul>
							<!-- 标签页内容 -->
							<div id="myTabContentss" class="tab-content" style="padding-left: 8px;padding-top: 8px;padding-right: 8px;">
								<#if showTabs??> 
									<#list showTabs?keys as key>
										<#if key_index == 0>
											<div class="tab-pane active" id="${key}"></div>
										<#else>
											<div class="tab-pane fade" id="${key}"></div>
										</#if> 
									</#list> 
								</#if>
							</div>
						</div>
					</div>
				</div>
			</div>
		</article>
	</div>
</div>

<script type="text/javascript">
	
	var popLog = "";
	$(function(){
		
// 		$("#newpop").on("click","#msSave",function(){
// 			$("#saveCall").click();
// 		});
		
		$("#pool_ids").parent().parent().prev("label").text("类型");
		
    	jQuery.validator.addMethod("notEqualTo", function(value, element, param) {
    		
    		return this.optional(element) ||value != $(param).val();
		}, $.validator.format("两个电话号码不能相同"));
		
		popOptionStat = $("#result .answered");
		<#if !(entry.intentType)?? && !type??>
		if(window.parent.currentCallStat.callNum === $("#newpop #phone_number_a").val()) {
			$("#result .noanswer").remove();
		} else {
			$("#result .answered").remove();
		}
		<#elseif type?? && type='detail'>
		</#if>
		
		$("#result").select2({
    		width:"100%",
    	});
		
        if ($("#result option:selected" ).val()==="customer" ) {
        	
            $( "#newcstmInfo #editedForm input").removeAttr( "disabled");
            $( "#newcstmInfo #editedForm select").removeAttr( "disabled");
            $( "#customer_id_a_1" ).attr("disabled" ,"" );
            $( "#number1").attr( "disabled", "");
      	} else {
            $( "#newcstmInfo #editedForm input").attr( "disabled", "");
            $( "#newcstmInfo #editedForm select").attr( "disabled", "");
      	}
        
        var allCustomerType = $("#pool_ids option").remove();
        var allIntendType = $("#allIntent").children();
        $("#result").change( function(){
           	$("#pool_ids").parent().parent().prev("label").text("类型");

           	if($("#result option:selected" ).val()==="customer" || $("#result option:selected" ).val()==="intent" ) {
            	
                 $( "#newcstmInfo #editedForm input").removeAttr( "disabled");
                 $( "#newcstmInfo #editedForm select").removeAttr( "disabled");
                 $( "#customer_id_a_1" ).attr("disabled" ,"" );
                 $( "#number1").attr( "disabled", "");
               	 $("#own_ids").addClass("readOnly").attr("disabled","disabled");
                 if($("#result option:selected" ).val()==="intent"){
                	 $("#pool_ids option").remove();
                	 $("#status").val("0");
                	 $("#pool_ids").append(allIntendType);
                	 $("#pool_ids").parent().parent().prev("label").text("意向类型");
                 }
               
                 if($("#result option:selected" ).val()==="customer"){
                	 $("#pool_ids").parent().parent().prev("label").text("成交类型");
                	 $("#status").val("1");
                	 $("#pool_ids option").remove();
                	 $("#pool_ids").append(allCustomerType);
                 }
           } else {
                 $( "#newcstmInfo #editedForm input").attr( "disabled", "");
                 $( "#newcstmInfo #editedForm select").attr( "disabled", "");
           }
   	  })
		
		getDataInfo();
		
		getActive("poplog");
		
		/* 设置电话信息仅显示 */
		$("#newphoneInfo input").attr("disabled","disabled");
		
		/* 表单赋值 */
		<#if Acstm?? >
			<#list Acstm?keys as k>
				$("#${(k)!''}_a").val('${(Acstm[k])!''}');
				$("#${(k)!''}_a_1").val('${(Acstm[k])!''}');
			</#list>
			
			//归属人不是我，禁用所有操作
			if(!$("#own_idss").val() == '${currentUser.uid}') {
				
			
				$("#first input").addClass("readOnly").attr("readonly","readonly");	
				$("#first select").addClass("readOnly").attr("disabled","disabled");
				$("#sameselect").removeAttr("disabled").removeClass("readOnly");
				
				/* tab 头信息赋值 */
				$("div#tabs2 ul li[aria-selected='true'] a").html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +"弹屏|"+$("#cstmserviceId").val());
				$("#startCall").attr("disabled","disabled");
				$("#saveCall").attr("disabled","disabled");
				$("#formsubmit").css("display","none");
				
				
			} else if ("${entry!''}"){	//entry不存在，则不进行下面的判断，直接交给下面的没有entry判断里面做操作
				if ($("#status_a").val() == 1) {
				//已经是我的客户，隐藏这个操作
					$("#calllogsave #result optgroup").remove();
					$("#calllogsave #result").val("").trigger("change");
					$("#calllogsave #chooseType").hide();
					$("#editedForm input").removeAttr("disabled");
					$("#editedForm select").removeAttr("disabled");
					$("#editedForm #pool_ids").attr("readonly", "readonly");
				} else {	//不是我的客户，可能是我的意向
					
				}
				
			}
			
		<#else>
			/* 如果不是客户，第一次打进系统 绑定电话号码 编号 日期 */
			$("#phone_number_a").val('${newPhoneNumber!''}');
			$("#customer_id_a").val('${customer_id!''}');
			$("#customer_id_a_1").val('${customer_id!''}');
			$("#start_date_a").val('${cDate?datetime!''}');
			
		</#if>
		//不是他的数据，要禁用一部分内容
		<#if (!entry?? || entry.ownUser!=currentUser.uid) >
			$("#first input").addClass("readOnly").attr("readonly","readonly");	
			$("#first select").addClass("readOnly").attr("disabled","disabled");
			$("#sameselect").removeAttr("disabled").removeClass("readOnly");
			$("#own_ids").val(${own_ids!''});
			
			<#if customerType?? && customerType=='1'>
				var optionDom = $("<option value='1' selected='selected'>成交客户</option>")
			<#else>
				var optionDom = $("<option value='0' selected='selected'>意向客户</option>");
			</#if>
			$("#pool_ids").append(optionDom);
			
			/* tab 头信息赋值 */
			$("div#tabs2 ul li[aria-selected='true'] a").html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +"弹屏|"+$("#hidPhone").val());
			$("#startCall").attr("disabled","disabled");
			$("#saveCall").attr("disabled","disabled");
			$("#formsubmit").css("display","none");
		<#elseif entry??>
			<#if (entry.intentType)??>
				<#if type?? && type='detail'>
					$("#chooseType #result [value='abandon']").remove();
					$("#chooseType #result [value='blacklist']").remove();
					$("#chooseType #result").val("intent").trigger("change");
				<#else>
				allCustomerType = $("#pool_ids option").remove();
       	 		$("#status").val("0");
       	 		$("#pool_ids").append(allIntendType);
				</#if>
				$("#pool_ids").val("${entry.intentType}").trigger("change");
			</#if>
		</#if>
		
		$("div#tabs2 ul li[aria-selected='true'] a").html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +"弹屏|"+$("#hidPhone").val());
		
		//号码隐藏
		$("#number1").val(window.parent.hiddenPhone($("#phone_number_a").val()));
		$("#number2").val(window.parent.hiddenPhone($("#phone_number_a_2").val()));

		$("#csss").text(window.parent.hiddenPhone($("#phone_number_a").val()));
		$("#phonenumber_button ul li a").text("0"+window.parent.hiddenPhone($("#phone_number_a").val()));
		
		if($("#phone_number_a_2").val() != ''){
			$("#csss1").text(window.parent.hiddenPhone($("#phone_number_a_2").val()));
			$("#phonenumber_button_1 ul li a").text("0"+window.parent.hiddenPhone($("#phone_number_a_2").val()));
		}
		
		if($("#phone_number_a").val()!=''){
			$("#number1").attr("disabled","disabled");	
		}
		
		if($("#phone_number_a_2").val()!=''){
			$("#number2").attr("disabled","disabled");
		}
		
		$("#own_ids").addClass("readOnly").attr("disabled","disabled").css("background","rgb(250, 250, 250)");
		$("#own_idss").val($("#own_ids").val());
// 		$("#own_ids").attr("disabled","disabled").css("background","rgb(250, 250, 250)");
		
		
		$("#startCall").click(function(){
			makeCallAgain();
		})
		
		/* 添加呼叫按钮 */
		function addCallInfo(){
			
			$("#formsubmit").before(''+
				'<div class="btn-group" style="margin-right:10px;" id="allcalls">' +
				'<a class="btn btn-success btn-sm" href="javascript:callByNow('+$("#phone_number_a").val() +');">呼叫'+window.parent.hiddenPhone($("#phone_number_a").val()) +'</a>' +
				'<a class="btn btn-success btn-sm dropdown-toggle" data-toggle="dropdown" href="javascript:void(0);">' +
					'<span class="caret"></span></a>' +
					'<ul class="dropdown-menu">' +
						'<li><a href="javascript:callByNow(0'+$("#phone_number_a").val() +');">0'+ window.parent.hiddenPhone($("#phone_number_a").val()) +'</a></li>' +
					 '</ul> '   
				+ '</div>');
		}
		
		/* 添加第二个号码 */
		function addSecondCall(num){
			
			if(num != ''){
				$("#newcstmInfo #allcalls div.btn-group ul").append(''+'<li><a href="javascript:callByNow('+num +');">'+ window.parent.hiddenPhone(num) +'</a></li>' +
					'<li><a href="javascript:callByNow(0'+num +');">0'+ window.parent.hiddenPhone(num) +'</a></li>' 
				);
			}
		}
		
		addCallInfo();
		addSecondCall($("#phone_number_a_2").val());
		
		//点击弹屏页面的保存按钮
		$('#saveCall').click(function(){
			
			if($('#saveCall').attr("disabled")) {	//只允许保存一次
				return;
			}
			
			if($("#result option:selected").val() === "customer" || $("#result option:selected").val() === "intent" || $("#result").val()=="" || $("#result").val()==null){
				if($('#newcstmInfo').valid()){
					$('#saveCall').attr("disabled", "disabled");
	                $('#msubmit').attr("disabled",true);
	                
	                if($("#phone_number_a").val()==""){
	                	$("#phone_number_a").val($("#number1").val());
	                }
	                if($("#phone_number_a_2").val()==""){
	                	$("#phone_number_a_2").val($("#number2").val());
	                }

	                $('#newcstmInfo').submit();
	            }
			} else { //不保存客户，保存为人
				$('#saveCall').attr("disabled", "disabled");
				var callresult = {};
				if($("#call_time_a").val()) {
               		callresult = {
          				action:$("#result").val(),
               			phoneNumber:$("#phone_number_a").val(),
               			callSessionUuid:$("#call_session_uuid").val(),
               			callTime:$("#call_time_a").val(),
               			callStat:$("#result optgroup").attr("label").indexOf("未接通")<0?1:0
   					}
               	} else {
               		callresult = {
           				action:$("#result").val(),
               			phoneNumber:$("#phone_number_a").val(),
               			callSessionUuid:$("#call_session_uuid").val(),
   					}
               	}
               			
           		$.post(getContext() + "newuserdata/saveData",
           				callresult,
         				function(data){
        			if(data.success){
                       	$.smallBox({
                           title : "保存记录成功",
                           content : "<i class='fa fa-clock-o'></i> <i>保存记录成功</i>",
                           color : "#659265",
                           iconSmall : "fa fa-check fa-2x fadeInRight animated",
                           timeout : 2000
                       	});
        			}else{
        				$.smallBox({
        					title : "保存失败",
        					content : "<i class='fa fa-clock-o'></i> <i>操作失败，请稍后重试...</i>",
        					color : "#C46A69",
        					iconSmall : "fa fa-times fa-2x fadeInRight animated",
        					timeout : 2000
        				});
        			}
        		},"json");
			}
		})
		
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
                	if($("#result").val()) {
	                	$("#formsubmit").css("display","none");
	                	$("#forEdit").attr("display","show");
	                	/* 返回客户的UID */
	                	$("#uid").val(data.cstmUuid);
							
		       			var callresult = {};
		               	if($("#call_time_a").val()) {
		               		callresult = {
		          				action:$("#pool_ids").val(),
		          				cstmUuid:data.cstmUuid,
		          				phoneNumber:$("#phone_number_a").val(),
		               			callSessionUuid:$("#call_session_uuid").val(),
		               			callTime:$("#call_time_a").val(),
		               			callStat:$("#result optgroup").attr("label").indexOf("未接通")<0?1:0
		   					};
		               		
		               	} else {
		               		callresult = {
		           				action:$("#pool_ids").val(),
		           				cstmUuid:data.cstmUuid,
		               			phoneNumber:$("#phone_number_a").val(),
		               			callSessionUuid:$("#call_session_uuid").val(),
		   					}
		               	}
		               	
		               	if($("#result option:selected").val() === "intent"){
	               			delete callresult.cstmUuid;
	               		} else if ($("#result option:selected").val() === "customer") {
	               			callresult.action = "customer";
	               		}
		               			
		           		$.post(getContext() + "newuserdata/saveData",
		           				callresult,
		         				function(data){
		        			if(data.success){
		                       	$.smallBox({
		                           title : "保存记录成功",
		                           content : "<i class='fa fa-clock-o'></i> <i>保存记录成功</i>",
		                           color : "#659265",
		                           iconSmall : "fa fa-check fa-2x fadeInRight animated",
		                           timeout : 2000
		                       	});
		        			}else{
		        				$.smallBox({
		        					title : "保存失败",
		        					content : "<i class='fa fa-clock-o'></i> <i>操作失败，请稍后重试...</i>",
		        					color : "#C46A69",
		        					iconSmall : "fa fa-times fa-2x fadeInRight animated",
		        					timeout : 2000
		        				});
		        			}
		        		},"json");
		       			
	                    $.smallBox({
	                        title : "操作成功",
	                        content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
	                        color : "#659265",
	                        iconSmall : "fa fa-check fa-2x fadeInRight animated",
	                        timeout : 2000
	                    });
	                    
	                	$("#first input").attr("readonly","readonly").addClass("readOnly");	
	        			$("#first select").attr("disabled","disabled").addClass("readOnly");
	                    
	                    $('#dt_basic_cstm').DataTable().ajax.reload(null,false);
                	} else {
                		$.smallBox({
	                        title : "操作成功",
	                        content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
	                        color : "#659265",
	                        iconSmall : "fa fa-check fa-2x fadeInRight animated",
	                        timeout : 2000
	                    });
                	}
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
		
   });
    
	
	function makeButtonCall(thiz){
		
// 		alert($(thiz).text());
		callByNow($(thiz).text());
	}
    
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
                    content : "<i class='fa fa-clock-o'></i> <i>呼叫失败，"+d.msg+"</i>",
                    color : "#C46A69",
                    iconSmall : "fa fa-times fa-2x fadeInRight animated",
                    timeout : 3000
                });
			}
		},"json");
	}

	
	
	
	function makeCallAgain() {
		$.post(getContext() + "newuserdata/makecall", {phone:$("#newpop #phone_number_a").val()},function(data) {
			var dataLog = data.dataLog;
	      	if(dataLog != ''){
	      		$("#call_session_uuid").val(dataLog["call_session_uuid"]);
				for(var k in dataLog){
					$("#" + k + "_a").val(dataLog[k]);
				}
				$("#uid").val(dataLog.uid);
				$("#uuid_a").val(dataLog.uid);
	      	}
		},"json");
	}
	
	
	function getDataInfo(){
		<#if entry??>
			var itemJson = ${entry.json};
			var dom = $("<div class='row'></div>");
			var domStr = "<div class='row'>";
			var j = 0;
			for(var i in itemJson) {
				domStr = domStr + '<section class="col col-6"><label class="label col col-3">' + i + '</label><label class="input col col-9"><input readonly="readonly" value="' + itemJson[i] +'" /></label></section>'
	       		if ((++j) % 2 == 0)
	       			domStr = domStr + '</div><div class="row">'
			}
			domStr = domStr + "</div>";
// 			dom.append($(domStr));
			$("#newdataInfo .last").nextAll().remove()
			$("#newdataInfo").append($(domStr));
		</#if>
	}
        
        
	/* 编辑 */
	$("#forEdit").click(function(){
		
		$("#first input").removeAttr("readonly").removeClass("readOnly");
		$("#first select").removeAttr("disabled").removeClass("readOnly");
		$("#own_ids").attr("disabled","disabled");
		
		$("#formsubmit").css("display","block");
		
		$('#own_idss').select2({
	        allowClear : true,
	   	    width: '99%'
	    });
		
		$('#pool_id').select2({
		     allowClear : true,
		     width: '99%'
		});
		
	});
	
	/* 变换客户信息 */
	function changeCustomerInfo(customerId ,customerName){

		$("#btnText").text(customerName);
		$("#newcstmInfo section[id !='cstm-operate']").remove();
		/* 根据查询条件发送请求 */		
		$.post(getContext() + "cstm/getCustomerInfoByCustomerId", { customerId : customerId },function(d){
			
			$("#cstm-operate").before(d);
		});
	}
	

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
		}
	}
	
	function initPopTable(){
		
		return $('#dt_pop_log').DataTable({
			"dom" : "t" + "<'dt-toolbar-footer'<'col-sm-6 col-xs-12'i><'col-xs-12 col-sm-6 text-right'lp>>",
			"autoWidth" : true,
			"ordering" : true,
			"serverSide" : true,
			"processing" : true,
			"searching" : false,
			"pageLength" : 5,
				"lengthMenu" : [ 5, 10, 15, 20],
				"language" : {
					"url" : getContext()+ "public/dataTables.cn.txt"
				},
				"ajax" : {
					"url" : getContext() + "calllog/getCallLog",
					"type" : "POST",	
					"data" : function(param) {
						param.phone=$("#phone_number_a").val();
					}
			},
			"paging" : true,
			"pagingType" : "bootstrap",
			"lengthChange" : true,
			"order" : [ [ "1", "desc" ] ],
			"columns" : [
				<#if title??>
					<#list dataRows?keys as key>
						<#if key == "call_time" || key == "agent_name" || key == "text_log" > 
							{"title" : "${dataRows[key]}",  "data" : "${key}","defaultContent":""},
						</#if>
					</#list>
				</#if>
				{ 
				   "title" : "操作",
				   "data" : "null",
				   "render": function(data,type,full){
					   if(full.talk_time > 0){
						   return "<a onclick='playCallLog(\""+full.uid+ "\");'>播放录音</a>";
					   }else{
						   return "";
					   }
				   }
			   }
			],
		});
	}
	
	
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
                    content : "<i class='fa fa-clock-o'></i> <i>呼叫失败，"+d.msg+"</i>",
                    color : "#C46A69",
                    iconSmall : "fa fa-times fa-2x fadeInRight animated",
                    timeout : 3000
                });
			}
		},"json");
	}
	
	function updateCallStatus(callNum) {
		if($("#newpop #phone_number_a").val() === callNum) {
			$("#calllogsave #result").append(popOptionStat);
			$("#calllogsave #result .noanswer").remove();
			$("#calllogsave #result").trigger("change");
		}	
	}
	
	
</script>

<!-- 宏定义 -->
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
			
			<#if (maps[key].isHidden)?? && (maps[key].isHidden) == "1">
				<#assign bedisabled = "type='hidden'">
			<#else>
				<#assign bedisabled = "">
			</#if>
			
			<#if keyIndex%3 == 0>
			
				<!-- 判断是否换行 -->
				<#if keyIndex==3>
					<!-- 换行的时候添加操作按钮 添加编辑操作 -->
<!--                		 <button id="forEdit" type="button" class="btn btn-sm btn-primary"> -->
<!-- 	                   	${'编  辑'} -->
<!-- 	                </button> -->
				<#elseif keyIndex==6>
					<!-- 换行的时候添加操作按钮 添加保存操作 -->
<!--                		 <div style="display: inline-block;"> -->
<!--                		 	<button id="formsubmit" type="button" class="btn btn-sm btn-primary" style="float: right;"> -->
<!-- 	                   	${saveInfo!'保  存'} -->
<!-- 	                </button> -->
<!--                		 </div> -->
				</#if>
					</div>
				</section>
				<section>
					<div class="row">
			<#else>
			</#if>
			
			<#if (maps[key].columnType) == "DATE" >
				<label class="label col col-2">${maps[key].columnName}</label>
				<div class="input col col-2">
					<label class="input">
						<input name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" value="" class="datetime" />
					</label>
				</div>
				
			<#elseif (maps[key].columnType) == "ENUM" >
				<#assign enumStr = (maps[key].characterProperty)?string>
					<label class="label col col-2">${maps[key].columnName}</label>
					<div class="form-group col col-2">
						<select name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" class="form-control" >
							<#if requireds =="">
							</#if>
							<#list enumStr?replace("，",",")?split(",") as a>
								<option value="${a!''}">${a!''}</option>
							</#list>
						</select>
					</div>
			<#else>
				<#if (maps[key].columnNameDB) == "pool_id">
					<label class="label col col-2">${maps[key].columnName}</label>
					<div class="form-group col col-2">
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
						<label class="label col col-2">${maps[key].columnName}</label>
						<div class="form-group col col-2">
							<label class="select">
								<select name="own_ids" id="own_ids" >
								<option value="${own_id!''}" selected="selected">${own_name!''}</option>
								</select>
								<#--<select name="own_ids" id="own_ids">
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
								-->
							</label>
						</div>
						
				<#elseif (maps[key].columnNameDB) == "phone_number">
					
					<!-- 电话号码1 -->
					<input type="hidden" name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a"  value="" class="${requireds}">
					<label class="col col-3">
						<div class="btn-group" id="phonenumber_button">
							<label class="label col col-2">${maps[key].columnName}一</label>
							<label id="csss" class="btn btn-xs btn-default" onclick="makeButtonCall(this);"></label>
							<button class="btn btn-xs btn-default dropdown-toggle" data-toggle="dropdown"><span class="caret"></span>
							</button>
							<ul class="dropdown-menu pull-right">
								<li>
									<a href="javascript:void(0);" onclick="makeButtonCall(this);"></a>
								</li>
							</ul>
						</div>
					</label>
					
					
					<#if secPhone??>
						<!-- 电话号码2 -->
						<label class="col col-3">
							<input type="hidden" name="${(maps[key].columnNameDB)!''}_2" id="${(maps[key].columnNameDB)!''}_a_2"  value="${secPhone!''}" >
							<div class="btn-group" id="phonenumber_button_1">
								<label class="label col col-2">${maps[key].columnName}二</label>
								<label id="csss1" class="btn btn-xs btn-default" onclick="makeButtonCall(this);"></label>
								<button class="btn btn-xs btn-default dropdown-toggle" data-toggle="dropdown"><span class="caret"></span>
								</button>
								<ul class="dropdown-menu pull-right">
									<li>
										<a href="javascript:void(0);" onclick="makeButtonCall(this);"></a>
									</li>
								</ul>
							</div>
						</label>
					
					<#else>
						<label class="label col col-2">${maps[key].columnName}二</label>
						<div class="form-group col col-2">
							<label class="input">
								<input type="hidden" name="${(maps[key].columnNameDB)!''}_2" id="${(maps[key].columnNameDB)!''}_a_2"  value="${secPhone!''}" >
								<input name="number2" id="number2" class="digits">
							</label>
						</div>
					</#if>
					
					
					
					
					<#assign keyIndex = keyIndex+1>
				<#else>

					<#if (maps[key].columnNameDB) != "customer_id">
						<#if (maps[key].columnType) == "INT">
							<label class="label col col-2">${maps[key].columnName}</label>
							<div class="form-group col col-2">
								<label class="input">
									<input name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" value="" class="digits">
								</label>
							</div>
						<#else>
						
							<#if "" != bedisabled>
								<#assign keyIndex = keyIndex - 1 >
								<input ${bedisabled} name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" value="" class="${requireds}">
							<#else>
								<label class="label col col-2">${maps[key].columnName}</label>
								<div class="form-group col col-2">
									<label class="input">
										<input name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" value="" class="${requireds}">
									</label>
								</div>
							</#if>
						
						</#if>
					<!-- 如果是cstmservice_id -->
					<#else>
						<label class="label col col-2">${maps[key].columnName}</label>
						<div class="form-group col col-2">
							<label class="input">
								<#if uid??>
									<input type="hidden" name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a_1" value="${serialized!''}" />
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

