<!DOCTYPE html>
<html lang="en-us" id="lock-page">
	<head>
		<meta charset="utf-8">
		<title>登录</title>
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
					
					<div style="font-weight: bolder;  font-size: xx-large;">
					<img src="${springMacroRequestContext.contextPath}/assets/img/rscclogo.png" style="margin-bottom: 10px;margin-left: 0;"/>
					<!--RSCC IP模块化呼叫中心系统 -->
					</div>
				
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
							<label class="label" >密码<a id="forgetpwd" class="pull-right" style="color:#1b66c7;text-align:right;">忘记密码?</a></label>
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
						<section style="display: none;">
							<label class="label">验证码</label>
							<label class="input col" style="padding-left: 0px">
							<input type="text" id="veryCode" name="veryCode" style="height:30px"/>
							<b class="tooltip tooltip-bottom-right">
								<i class="fa fa-lock txt-color-teal"></i> 
								请输入验证码 点击图片可以更换
							</b>
							</label>
							<span onclick="changeImg()">
					        <img id="imgObj" src="yanzheng/verifyCode"/>  
					        </span><br>
					        <#if verError??>
					        <span style="color:red">${verError}</span>
					        </#if>
						</section>
							
					</fieldset>
					<footer style="padding: 0px;border: 0px;background: white;">
						<button type="submit" class="btn btn-primary" id="agentSubmit">登录</button>
					</footer>
				</div>
				<p class="font-xs margin-top-5">
					${companyname!''} &nbsp;&nbsp;&nbsp;&nbsp; ${dianhua!''}${companyphone!''}&nbsp;&nbsp;&nbsp;&nbsp;${wangzhi!''}<a href="http://${companyadd!''}">${companyadd!''}</a>
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
							}else{
								return false;
							}
						}
					}
				});
			});
			
			$('#forgetpwd').click(function(){
				$.post("${springMacroRequestContext.contextPath}/forgetpwd",function(data){
					$("#main").append(data);
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
			/*验证码功能*/
			function changeImg(){     
			    var imgSrc = $("#imgObj");     
			    var src = imgSrc.attr("src");
			    imgSrc.attr("src",chgUrl(src));     
			}     
			//时间戳     
			//为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳     
			function chgUrl(url){  
			    var timestamp = (new Date()).valueOf();     
			    urlurl = url.substring(0,17);     
			    if((url.indexOf("&")>=0)){     
			        urlurl = url + "×tamp=" + timestamp;     
			    }else{     
			        urlurl = url + "?timestamp=" + timestamp;     
			    }    
			    return urlurl;     
			}
			
		</script>		
	</body>
</html>