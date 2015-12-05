<!DOCTYPE html>
<html lang="en-us" id="lock-page">
	<head>
		<meta charset="utf-8">
		<title> 管理员登录</title>
		<meta name="description" content="">
		<meta name="author" content="">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
		
		<link rel="stylesheet" type="text/css" media="screen" href="${springMacroRequestContext.contextPath}/assets/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" media="screen" href="${springMacroRequestContext.contextPath}/assets/css/font-awesome.min.css">
		<!--<link rel="stylesheet" type="text/css" href="${springMacroRequestContext.contextPath}/assets/css/font-awesome.min.css" /> -->

		<link rel="stylesheet" type="text/css" media="screen" href="${springMacroRequestContext.contextPath}/assets/css/smartadmin-production-plugins.min.css">
		<link rel="stylesheet" type="text/css" media="screen" href="${springMacroRequestContext.contextPath}/assets/css/smartadmin-production.min.css">
		<link rel="stylesheet" type="text/css" media="screen" href="${springMacroRequestContext.contextPath}/assets/css/smartadmin-skins.min.css">


		<link rel="stylesheet" type="text/css" media="screen" href="${springMacroRequestContext.contextPath}/assets/css/smartadmin-rtl.min.css"> 

		<link rel="stylesheet" type="text/css" media="screen" href="${springMacroRequestContext.contextPath}/assets/css/demo.min.css">

		<!-- page related CSS -->
		<link rel="stylesheet" type="text/css" media="screen" href="${springMacroRequestContext.contextPath}/assets/css/lockscreen.min.css">

		<!-- #FAVICONS -->
		<link rel="shortcut icon" href="${springMacroRequestContext.contextPath}/assets/img/favicon/favicon.ico" type="image/x-icon">
		<link rel="icon" href="img/favicon/favicon.ico" type="${springMacroRequestContext.contextPath}/assets/image/x-icon">
		<link rel="apple-touch-icon" href="${springMacroRequestContext.contextPath}/assets/img/splash/sptouch-icon-iphone.png">
		<link rel="apple-touch-icon" sizes="76x76" href="${springMacroRequestContext.contextPath}/assets/img/splash/touch-icon-ipad.png">
		<link rel="apple-touch-icon" sizes="120x120" href="${springMacroRequestContext.contextPath}/assets/img/splash/touch-icon-iphone-retina.png">
		<link rel="apple-touch-icon" sizes="152x152" href="${springMacroRequestContext.contextPath}/assets/img/splash/touch-icon-ipad-retina.png">
		
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		
		<link rel="apple-touch-startup-image" href="${springMacroRequestContext.contextPath}/assets/img/splash/ipad-landscape.png" media="screen and (min-device-width: 481px) and (max-device-width: 1024px) and (orientation:landscape)">
		<link rel="apple-touch-startup-image" href="${springMacroRequestContext.contextPath}/assets/img/splash/ipad-portrait.png" media="screen and (min-device-width: 481px) and (max-device-width: 1024px) and (orientation:portrait)">
		<link rel="apple-touch-startup-image" href="${springMacroRequestContext.contextPath}/assets/img/splash/iphone.png" media="screen and (max-device-width: 320px)">
	
	</head>
	<body>
		<#include "index/importsjs.ftl">
		 <div id="main" role="main">
			<form class="lockscreen animated flipInY smart-form" action="${springMacroRequestContext.contextPath}/doLogin" method="post" id="login-form" style="top: 40%;">
				<img src="${springMacroRequestContext.contextPath}/assets/img/ruishengruanjian.png" style="margin-bottom: 15px;"/>
				<h3></h3>
				<div class="logo" style="padding: 0px;"></div>
				<div>
					<input type="hidden" name="unregisterOk" id="unregisterOk">
					<fieldset style="padding-top: 15px;">
						<section>
							<label class="label">登录名</label>
							<label class="input">
								<i class="icon-append fa fa-user"></i>
								<input type="text" name="loginName" value="${(loginuser.loginName)!''}">
								<b class="tooltip tooltip-top-right">
									<i class="fa fa-user txt-color-teal"></i> 
									请输入您的登录名
								</b>
							</label>
						</section>
						<section>
							<label class="label">密码</label>
							<label class="input">
								<i class="icon-append fa fa-lock"></i>
								<input type="password" name="password" value="${(loginuser.password)!''}">
								<b class="tooltip tooltip-top-right">
									<i class="fa fa-lock txt-color-teal"></i> 
									请输入您的登录密码
								</b>
							</label>
							<div class="note" align="right">
								<font color="red" size="2" id="errorAgentFont">${errorAgentFont!''}</font>
							</div>
						</section>
						<section id="extension">
							<label class="label">分机号</label>
							<label class="input">
								<i class="icon-append glyphicon glyphicon-phone-alt"></i> 
								<input type="text" name="loginExtension" id="loginExtension" value="${(loginSipId)!''}" > 
								<b class="tooltip tooltip-top-right">
							 		<i class="glyphicon glyphicon-phone-alt txt-color-teal"></i>
									  请输入您的分机号
								</b>
							</label>
							<div class="note" align="right">
								<font color="red" size="2" id="errorExten">${errormsg!''}</font>
							</div>
						</section>
					</fieldset>
					<footer style="padding: 0px;border: 0px;background: white;">
						<button type="submit" class="btn btn-primary" id="agentSubmit">登录</button>
					</footer>
				</div>
				<p class="font-xs margin-top-5">
					上海睿声软件技术有限公司 &nbsp;&nbsp;&nbsp;&nbsp;联系电话 : 021-61487233&nbsp;&nbsp;&nbsp;&nbsp;网址：<a href="http://www.rssoft.cc">http://www.rssoft.cc</a>
				</p>
			 </form>
		</div>
		<script type="text/javascript">
			$(document).ready(function() {
				$(function() {
					$("#login-form").validate({
						rules : {
							loginName : {
								required : true
							},
							password : {
								required : true,
								maxlength : 20
							},
							loginExtension : {
								
							}
						},messages : {
					    	loginName : "",
					    	password : "",
					    	loginExtension : ""
						},errorPlacement : function(error, element) {
							error.insertAfter(element.parent());
						}
					});
				});
				
				$('#agentSubmit').click(function(){
					if($("#login-form").valid()){
						var exten = $.trim($('#loginExtension').val());
						if(exten == ""){
							if(confirm("未输入分机号,确定继续登陆?")){
								$("#login-form").submit();
							}
						}
					}
				});
			});
			
			<#if errorExten??>
				if(confirm("${errorExten}")){
					//强制解绑
					var url = "${springMacroRequestContext.contextPath}/ubind";
					var exten = "${loginSipId}";
					var uname = "${loginuser.loginName}";
					var upwd = "${loginuser.password}";
			    	$.post(url, {exten:exten, uname:uname, upwd:upwd}, function(data){
			    		 if(data.success){
			    			 $("#login-form").submit(); 
			    		 }
			    	},"json");
				};
			</#if>
		</script>		
	</body>
</html>