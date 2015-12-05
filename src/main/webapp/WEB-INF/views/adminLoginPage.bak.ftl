<!DOCTYPE html>
<html lang="en-us" id="lock-page">
	<head>
		<meta charset="utf-8">
		<title> 管理员登录</title>
		<meta name="description" content="">
		<meta name="author" content="">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
		
		<link rel="stylesheet" type="text/css" media="screen" href="${springMacroRequestContext.contextPath}/assets/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" media="screen" href="${springMacroRequestContext.contextPath}/css/font-awesome.min.css">

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

		<div id="main" role="main">

			<!-- MAIN CONTENT -->
			<form class="lockscreen animated flipInY" action="${springMacroRequestContext.contextPath}/user/user/adminlogin" method="post">
				<div class="logo">
					<h1 class="semi-bold"><img src="${springMacroRequestContext.contextPath}/assets/img/logo-o.png" alt="" />登录</h1>
				</div>
				<div>
					<img src="${springMacroRequestContext.contextPath}/assets/img/avatars/sunny-big.png" alt="" width="120" height="150" />
					<div>
						<h1><i class="fa fa-user fa-3x text-muted air air-top-right hidden-mobile"></i> <small><i class="fa fa-lock text-muted"></i> &nbsp;Locked</small></h1>
						<p class="text-muted">
							<a href="mailto:simmons@smartadmin">admin@rssoft.cc</a>
						</p>

						<div class="input-group">
							<label class="label" style="color: black;">用户名</label>
							<input class="form-control" type="text" name="uname" id="uname" placeholder="User Name">
							<div class="input-group-btn">
							</div>
							<label class="label" style="color: black;">密  码</label>
							<input class="form-control" type="password" name="passwd" id="passwd" placeholder="Password">
							<div class="input-group-btn"></div>
						</div>
						<p class="no-margin margin-top-5">
						</p>
					</div>
					<div style="text-align: right; margin-top: 10px;">
						<label class="btn btn-primary" id="adminslogin">登  录</label>
					</div>
				</div>
			</form>
		</div>
		
	<#include "index/importsjs.ftl">
	<script type="text/javascript">
// 		$(function(){
			
// 		});
		$("#adminslogin").click(function(){
			
			$.post('${springMacroRequestContext.contextPath}/'+"user/redirect",{adminName:$("#uname").val(),adminPassword:$("#passwd").val()},function(d){
				
				window.location.href='${springMacroRequestContext.contextPath}/'+d;
			});
		});
	
	</script>		

	</body>
	
</html>