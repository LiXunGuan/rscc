
<!DOCTYPE html>
<html lang="en-us" id="lock-page">
<head>
<meta charset="utf-8">
<title>配置向导</title>
<meta name="description" content="">
<meta name="author" content="">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

<link rel="stylesheet" type="text/css" media="screen"
	href="${springMacroRequestContext.contextPath}/assets/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" media="screen"
	href="${springMacroRequestContext.contextPath}/assets/css/font-awesome.min.css">

<link rel="stylesheet" type="text/css" media="screen"
	href="${springMacroRequestContext.contextPath}/assets/css/smartadmin-production-plugins.min.css">
<link rel="stylesheet" type="text/css" media="screen"
	href="${springMacroRequestContext.contextPath}/assets/css/smartadmin-production.min.css">
<link rel="stylesheet" type="text/css" media="screen"
	href="${springMacroRequestContext.contextPath}/assets/css/smartadmin-skins.min.css">

<link rel="stylesheet" type="text/css" media="screen"
	href="${springMacroRequestContext.contextPath}/assets/css/smartadmin-rtl.min.css">

<link rel="stylesheet" type="text/css" media="screen"
	href="${springMacroRequestContext.contextPath}/assets/css/demo.min.css">

<!-- page related CSS -->
<link rel="stylesheet" type="text/css" media="screen"
	href="${springMacroRequestContext.contextPath}/assets/css/lockscreen.min.css">

<!-- #FAVICONS -->
<link rel="apple-touch-icon"
	href="${springMacroRequestContext.contextPath}/assets/img/splash/sptouch-icon-iphone.png">
<link rel="apple-touch-icon" sizes="76x76"
	href="${springMacroRequestContext.contextPath}/assets/img/splash/touch-icon-ipad.png">
<link rel="apple-touch-icon" sizes="120x120"
	href="${springMacroRequestContext.contextPath}/assets/img/splash/touch-icon-iphone-retina.png">
<link rel="apple-touch-icon" sizes="152x152"
	href="${springMacroRequestContext.contextPath}/assets/img/splash/touch-icon-ipad-retina.png">

<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<link rel="apple-touch-startup-image"
	href="${springMacroRequestContext.contextPath}/assets/img/splash/ipad-landscape.png"
	media="screen and (min-device-width: 481px) and (max-device-width: 1024px) and (orientation:landscape)">
<link rel="apple-touch-startup-image"
	href="${springMacroRequestContext.contextPath}/assets/img/splash/ipad-portrait.png"
	media="screen and (min-device-width: 481px) and (max-device-width: 1024px) and (orientation:portrait)">
<link rel="apple-touch-startup-image"
	href="${springMacroRequestContext.contextPath}/assets/img/splash/iphone.png"
	media="screen and (max-device-width: 320px)">


<style type="text/css">
em.invalid {
	color: #b94a48;
}

.form-group label.radio {
	margin-left: 40px;
}
#guideFrom .form-control{
	font-size: 18px;
}
#guideFrom label{
	font-size: 15px;
}

.bootstrapWizard li{
	width: 20%;
}


.bootstrap-duallistbox-container label, .form header, legend{
	padding-top: 0px;
}

.checkbox, .radio{
	margin-top: 0px;
	margin-bottom: 0px;
	margin-left: 25px;
}

.tab-pane{
	min-height: 400px;
}

.btn.btn-lg.bg-color-blue.txt-color-white{

	background-color: #3276B1!important;
}


</style>


<#include "index/importsjs.ftl">

<script type="text/javascript"
	src="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-wizard/jquery.bootstrap.wizard.js"></script>

<script type="text/javascript"
	src="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-duallistbox/jquery.bootstrap-duallistbox.min.js"></script>

<script type="text/javascript"
	src="${springMacroRequestContext.contextPath}/assets/js/plugin/select2/select2.min.js"></script>

<script type="text/javascript" 
	src="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-duallistbox/jquery.bootstrap-duallistbox.min.js"></script>

</head>

<body>
	<div id="main" role="main" style="" class="pull-center;">

		<section id="widget-grid" class="pull-center" style="margin-top: 5%;">
			<div class="row">

				<article class="" style="width: 80%;">

					<div class="jarviswidget jarviswidget-color-darken pull-center"
						id="wid-id-0" data-widget-editbutton="false"
						data-widget-deletebutton="false">
						<header>
							<span class="widget-icon"> <i class="fa fa-cog"></i>
							</span>
							<h2>配置向导</h2>
						</header>

						<div>
							<div class="jarviswidget-editbox"></div>
							<div class="widget-body pull-center">

								<div class="row pull-center">
									<form id="guideFrom" novalidate="novalidate" method="post" action="${springMacroRequestContext.contextPath}/config">

										<div id="bootstrap-wizard-guide" class="col-sm-12 pull-center">
											<div class="alert alert-info fade in">
												<button class="close" data-dismiss="alert">×</button>
												<i class="fa-fw fa fa-info"></i> <strong>信息</strong>
												欢迎使用睿声RSCC呼叫中心系统，请完成以下向导来初始化您的系统，您也可以一直点击下一步使用默认的设置初始化系统。
											</div>
											<div class="form-bootstrapWizard">
												<ul class="bootstrapWizard form-wizard">
													<li data-target="#step1"><a href="#tab1"
														data-toggle="tab"> <span class="step">1</span>
														 <span class="title">网络信息配置</span>
													</a></li>
													<li data-target="#step2"><a href="#tab2"
														data-toggle="tab"> <span class="step">2</span> <span
															class="title">管理员信息设置</span>
													</a></li>
													<li data-target="#step3"><a href="#tab3"
														data-toggle="tab"> <span class="step">3</span> <span
															class="title">分机设置</span>
													</a></li>
													<li data-target="#step4"><a href="#tab4"
														data-toggle="tab"> <span class="step">4</span> <span
															class="title">网关设置</span>
													</a></li>
													<li data-target="#step5"><a href="#tab5"
														data-toggle="tab"> <span class="step">5</span> <span
															class="title">接入号设置</span>
													</a></li>
												</ul>
												<div class="clearfix"></div>
											</div>
											
											<div class="tab-content">
												<div class="tab-pane" id="tab1">
													<br>
													<h3>
														<strong>步骤 1</strong> - 网络信息配置
													</h3>
													<div class="row">
														<div class="input col-md-3"></div>
														<label class="col-md-2" style="margin-top: 7px;text-align: right;">IP地址</label>
														<div class="input col-md-3">
															<input class="form-control" placeholder="IP地址" name="ipAddress" id="ipAddress" type="text" value="192.168.1.100">
														</div>
													</div>													
													<br/>
													<div class="row">
														<div class="input col-md-3"></div>
														<label class="col-md-2" style="margin-top: 7px;text-align: right;">子网掩码</label>
														<div class="input col-md-3">
															<input class="form-control" placeholder="子网掩码" name="netMask" id="netMask" type="text" value="255.255.255.0">
														</div>	
													</div>													
													<br/>
													<div class="row">
														<div class="input col-md-3"></div>
														<label class="col-md-2" style="margin-top: 7px;text-align: right;">默认网关</label>
														<div class="input col-md-3">
															<input class="form-control" placeholder="默认网关" name="gateway" id="gateway" type="text" value="192.168.1.1">
														</div>
													</div>									
													
												</div>
												<div class="tab-pane" id="tab2">
													<br>
													<h3>
														<strong>步骤 2</strong> - 管理员信息配置
													</h3>
														<div class="row">
															<div class="input col-md-3"></div>
															<label class="col-md-2" style="margin-top: 7px;text-align: right;">管理员账号</label>
															<label class="input col-md-3">
																<input class="form-control" placeholder="" type="text"  name="username" id="username" value="admin" style="border: none;background-color: #FFF" disabled="disabled">
															</label>
														</div>
														<br/>
														<div class="row">
															<div class="input col-md-3"></div>	
															<label class="col-md-2" style="margin-top: 7px;text-align: right;">管理员密码</label>
															<label class="input col-md-3">
																<input class="form-control" placeholder="管理员密码" type="password"  name="userpassword" id="userpassword" value="" >
															</label>
														</div>
														<br/>
														<div class="row">
															<div class="input col-md-3"></div>
															<label class="col-md-2" style="margin-top: 7px;text-align: right;">重复密码</label>
															<label class="input col-md-3">
																<input class="form-control" placeholder="重复您的管理员密码" type="password"  name="reuserpassword" id="reuserpassword" value="">
															</label>
														</div>
														<br/>
														<div class="row">
															<div class="input col-md-3"></div>
															<label class="col-md-2" style="margin-top: 7px;text-align: right;">管理员邮箱</label>
															<label class="input col-md-3">
																<input class="form-control" placeholder="您的邮箱" type="email"  name="email" id="email" value="">
															</label>
														</div>

												</div>

												<div class="tab-pane" id="tab3">
													<br>
													<h3>
														<strong>步骤 3</strong> - 批量添加分机
													</h3>
														<div class="row">
															<div class="input col-md-3"></div>
															<label class="col-md-2" style="margin-top: 7px;text-align: right;">分机起始号码</label>
															<label class="input col-md-3">
																<input class="form-control" placeholder="" type="text"  name="agentBegin" id="agentBegin" value="8001">
															</label>
														</div>
														<br/>
														<div class="row">
															<div class="input col-md-3"></div>
															<label class="col-md-2" style="margin-top: 7px;text-align: right;">分机结束号码</label>
															<label class="input col-md-3">
																<input class="form-control" placeholder="" type="text"  name="agentEnd" id="agentEnd" value="8050">
															</label>
														</div>
													
														<br/>
														<div class="row">
															<div class="col-sm-2"></div>
															<div class="col-sm-4">
																<div class="form-group pull-right">
																	<label class="radio"> <input type="radio"
																		class="radiobox style-0" name="agentNumber" checked="checked" value="1" onchange="judgeShows(this);">
																		<span>与分机号码一致</span>
																	</label>
																</div>
															</div>
															<div class="col-sm-6">
																<div class="form-group pull-left">
																	<label class="radio"> <input type="radio"
																		class="radiobox style-0" name="agentNumber" value="2"  onchange="judgeShows(this);">
																		<span>使用统一分机密码</span>
																		<input type="text" name="agentNumbers" id="agentNumbers" class="" style="visibility: hidden; width:120px; ">
																	</label>
																</div>
															</div>
														</div>
												</div>
												<div class="tab-pane" id="tab4">
													<br>
													<h3>
														<strong>步骤 4</strong> -网关信息设置
													</h3>
														<div class="row">
															<div class="input col-md-3"></div>
															<label class="col-md-2" style="margin-top: 7px;text-align: right;">网关名称</label>
															<label class="input col-md-3">
																<input class="form-control" placeholder="网关名称" type="text"  name="sipTrunkName" id="sipTrunkName" value="">
															</label>
														</div>
														<br/>
														<div class="row">
															<div class="input col-md-3"></div>
															<label class="col-md-2" style="margin-top: 7px;text-align: right;">codec</label>
																<label class="input col-md-3">
																		<select name="codec" id="codec" class="form-control" >
																			<option value="pcma">PCMA</option>
																			<option value="pcmu">PCMU</option>
																			<option value="g729">G729</option>
																			<option value="g722">G722</option>
																		</select>
																</label>
														</div>
														<br>
														<div class="row">
															<div class="input col-md-3"></div>
															<label class="col-md-2" style="margin-top: 7px;text-align: right;">SIP服务器地址</label>
															<label class="input col-md-3">
																<input class="form-control" placeholder="192.168.1.120:5060" type="text"  name="sipTrunkIP" id="sipTrunkIP" value="">
															</label>
														</div>
														<br>
														<div class="row">
															<div class="input col-md-5"></div>
															<label class="col-md-2 pull-left">
																	<input type="checkbox" class="checkbox style-2" name="isRegist" id="isRegist" value="2">
																	<span>是否注册</span>
															</label>
														</label>
														</div>
														<br>
														<div class="row" id="lastRow" style="visibility:hidden;">
															<div class="input col-md-3"></div>
																<label class="col-md-2" style="margin-top: 7px;text-align: right;">注册用户名</label>
																<label class="input col-md-3">
																	<input class="form-control" placeholder="" type="text"  name="agUserName" id="agUserName">
																</label>
														</div>
														<br>
														<div class="row" id="lastRows" style="visibility:hidden;">
															<div class="input col-md-3"></div>
															<label class="col-md-2" style="margin-top: 7px;text-align: right;">注册密码</label>
															<label class="input col-md-3">
																<input class="form-control" placeholder="" type="text"  name="agPassword" id="agPassword">
															</label>
														</div>
												</div>
												
												<div class="tab-pane" id="tab5">
													<br>
													<h3>
														<strong>步骤 5</strong> - 接入号设置
													</h3>
													<div class="row">
														<div class="input col-md-12">
															<div class="col col-md-6"">
																<div class="row" style="margin-top: 20px;">
																	<div class="input col-md-2"></div>
																	<label class="col-md-2" style="margin-top: 7px;text-align: right;">接入号</label>
																	<label class="input col-md-6">
																		<input class="form-control" placeholder="接入号" type="text"  name="accessNumber" id="accessNumber" value="">
																	</label>
																</div>
																<br/>
																<div class="row">
																	<div class="input col-md-2"></div>
																	<label class="col-md-2" style="margin-top: 7px;text-align: right;">并发</label>
																	<label class="input col-md-6">
																		<input class="form-control" placeholder="并发数" type="text"  name="concurrency" id="concurrency" value="">
																	</label>
																</div>
																<br/>
																<div class="row">
																	<div class="input col-md-2"></div>
																	<label class="col-md-2" style="text-align: right;">可呼入</label>
																	<label class="checkbox col-md-5">
																		<input type="checkbox" name="callIn" id="callIn" class="checkbox style-0" checked="checked">
																		<span></span>
																	</label>
																</div>
																<br/>
																<div class="row">
																	<div class="input col-md-2"></div>
																	<label class="col-md-2" style="text-align: right;">可呼出</label>
																	<label class="checkbox col-md-5">
																		<input type="checkbox" name="callOut" id="callOut" class="checkbox style-0" checked="checked">
																		<span></span>
																	</label>
																</div>
															</div>
															<div class="col col-md-6">
																<select multiple="multiple" id="selectGateway" class="form-control" name="selectGateway" style="padding-top: 0px;padding-right: 10px;"></select>
															</div>
														</div>
													</div>
												</div>
												<div class="form-actions">
													<div class="row">
														<div class="col-sm-12">
															<ul class="pager wizard no-margin">
																<li class="previous disabled"><a href="javascript:void(0);" class="btn btn-lg btn-default"> 上一步 </a></li>
																<li class="next"><a href="javascript:void(0);" class="btn btn-lg bg-color-blue txt-color-white"> 下一步 </a></li>
																<li class="finish"><a href="javascript:void(0);" class="btn btn-lg btn-default"> 完成 </a></li>
															</ul>
														</div>
													</div>
												</div>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
				</article>
			</div>
		</section>
	</div>
	<script type="text/javascript">
	
		$(document).ready(function() {
			
			 var datalist = $('#selectGateway').bootstrapDualListbox({
// 					nonSelectedListLabel: '未选择数据',
// 					selectedListLabel: '已选择数据',
					preserveSelectionOnMove: 'moved',
					moveOnSelect: true,
					selectorMinimalHeight: "150",
					filterPlaceHolder: "筛选",
					filterTextClear: "显示全部",
					infoText: "列表包含已经选择的 {1} 项",
					infoTextFiltered: '<span class="label label-warning">筛选</span> {1} 项中的 {0} 项',
					infoTextEmpty: "可选列表为空",
					showFilterInputs : false
				});
			
		    // IP地址验证   
		    jQuery.validator.addMethod("ip", function(value, element) {    
		      return this.optional(element) || /^(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.)(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.){2}([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))$/.test(value);    
		    }, "请填写正确的IP地址。");
			
		    // IP地址验证   
		    jQuery.validator.addMethod("ipAndPort", function(value, element) {
		    	
		    	//纯IP地址校验 
		    	if(value.indexOf(":")==-1){
		    		
		    		return this.optional(element) || /^(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.)(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.){2}([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))$/.test(value);
		    	}else{
		    		
// 		    		如果后面没有了，返回false
	 		    	if(value.substring(value.indexOf(":")+1)==""){
	 		    		
			    		return false;
	 		    	}else{
	 		    		
	 		    		return this.optional(element) || /^[0-9]*$/.test(value.substring(value.indexOf(":")+1))
	 		    	}
		    	}
		    	
		    }, "请填写正确的格式，如：192.168.1.120:5060,");
			
			
			var valid = $("#guideFrom").validate({
				rules: {
					email :{
						email : true
					},
					useradmin : {
					},
					userpassword : {
					},
					reuserpassword : {
						equalTo :"#userpassword",
					},
					countBegin: {
						digits : true
					},
					countEnd: {
						digits : true
					},
					agentBegin: {
						digits : true
					},
					agentEnd: {
					},
					accessNumber: {
					},
// 					agentNumbers: {
// 						required : true,
// 					},
					sipTrunkName: {
					},
					sipTrunkIP: {
						ipAndPort : true,
					},
					concurrency: {
						digits : true
					},
				},
				messages: {
					useradmin : {
					},
					userpassword : {
					},
					reuserpassword : {
						equalTo :"两次输入密码不一致",
					},
					deptName :{
					},
					countBegin: {
						digits : "请输入数字"
					},
					countEnd: {
						digits : "请输入数字"
					},
					agentBegin: {
						digits : "请输入数字"
					},
					agentEnd: {
					},
					accessNumber: {
						digits : "请输入数字",
					},
// 					agentNumbers: {
// 						required :"该字段必填",
// 					},
					sipTrunkName: {
					},
					sipTrunkIP: {
						ip :"请输入正确IP"
					},
					sipPort: {
						digits : "请输入数字",
					},
					concurrency: {
						digits : "请输入数字",
					},
				},
		        errorPlacement : function(error, element) {
		            error.insertAfter(element.parent());
		        }
			});

				/*添加向导*/
				$('#bootstrap-wizard-guide').bootstrapWizard({
					tabClass : 'form-wizard',
					onNext : function(tab, navigation, index) {

						if($("#sipTrunkName").val()!=""){
							$("#selectGateway").empty();
							$("#selectGateway").append("<option  value="+$("#sipTrunkIP").val()+" selected='selected'>"+$("#sipTrunkName").val()+"</option>");
							datalist.bootstrapDualListbox('refresh', true);
						}
						
						if (!$("#guideFrom").valid()) {
							valid.focusInvalid();
							return false;
						}
					},
					onTabClick : function(tab, navigation, index) {
						
						return false;
					},
					onFinish : function() {

						if($("#callIn").is(":checked")=="true"){
							$("#callIn").val(1);
						}else{
							$("#callIn").val(0);
						}
						
						if($("#callOut").is(":checked")=="true"){
							$("#callOut").val(1);
						}else{
							$("#callOut").val(0);
						}
						
						$("#guideFrom").submit();
					}
				});

				$('#guideFrom').ajaxForm({
					dataType : "json",
					success : function(data) {
						if (data.success) {
							$.smallBox({
				                title : "操作成功",
				                content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
				                color : "#659265",
				                iconSmall : "fa fa-check fa-2x fadeInRight animated",
				                timeout : 2000
							});
							window.location.href = getContext();
						} else {
							$.smallBox({
								title : "操作失败",
								content : "<i class='fa fa-clock-o'></i> <i>操作失败，未做修改</i>",
								color : "#C46A69",
								iconSmall : "fa fa-times fa-2x fadeInRight animated",
								timeout : 2000
							});
						}
						valid.resetForm();
					},
					submitHandler : function(form) {
						$(form).ajaxSubmit({
							success : function() {
								$("#messageinfoa").addClass('submited');
							}
						});
					},
					error : function(XMLHttpRequest,textStatus, errorThrown) {
						if (textStatus == 'error') {
							$('#dialogTask').modal('hide');
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
				
			});
	</script>
</body>
</html>

<script type="text/javascript">

	function getContext() {
		return "${springMacroRequestContext.contextPath}/";
	}
	
	$(function(){
		
		if($("#isRegist").is(":checked")){
			
			$("#lastRow").css("visibility","visible");
			$("#lastRows").css("visibility","visible");
			$("#isRegist").val(1);
			$("#agUserName").addClass("required");
			$("#agPassword").addClass("required");
		}else{
			
			$("#lastRow").css("visibility","hidden");
			$("#lastRows").css("visibility","hidden");
			$("#isRegist").val(2);
			$("#agUserName").removeClass("required");
			$("#agPassword").removeClass("required");
		}
		
		
		if($("#countType:checked").attr("value")==2){
			
			$("#countTypes").css("visibility","visible");
		}else{
			
			$("#countTypes").css("visibility","hidden");
		}
		
		
	});
	
	
	$("#isRegist").click(function(){
		if($("#isRegist").is(":checked")){
			
			$("#lastRow").css("visibility","visible");
			$("#lastRows").css("visibility","visible");
			$("#isRegist").val(1);
			$("#agUserName").addClass("required");
			$("#agPassword").addClass("required");
			
		}else{
			
			$("#lastRow").css("visibility","hidden");
			$("#lastRows").css("visibility","hidden");
			$("#isRegist").val(2);
			$("#agUserName").removeClass("required");
			$("#agPassword").removeClass("required");
		}
	});
	
	
	function judgeShow(thiz){
		
		if($(thiz).attr("value")==2){
			
			$("#countTypes").css("visibility","visible");
			
		}else{
							
			$("#countTypes").css("visibility","hidden");
		}
	}
		
	function judgeShows(thiz){
		
		if($(thiz).attr("value")==2){
			
			$("#agentNumbers").css("visibility","visible");
			$("#agentNumbers").addClass("required");
		}else{
			
			$("#agentNumbers").next("em").remove();
			$("#agentNumbers").removeClass("required");
			$("#agentNumbers").css("visibility","hidden");
		}
	}
		
	
</script>
