
<head>
	<meta charset="utf-8">
	<title>登陆管理</title>
	<meta name="description" content="">
	<meta name="author" content="">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<link rel="stylesheet" type="text/css" href="${springMacroRequestContext.contextPath}/assets/css/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="${springMacroRequestContext.contextPath}/assets/css/font-awesome.min.css" />
	<link rel="stylesheet" type="text/css" href="${springMacroRequestContext.contextPath}/assets/css/smartadmin-production-plugins.min.css"/>
	<link rel="stylesheet" type="text/css" href="${springMacroRequestContext.contextPath}/assets/css/smartadmin-production.min.css"/>
	<link rel="stylesheet" type="text/css" href="${springMacroRequestContext.contextPath}/assets/css/smartadmin-rtl.min.css"/>
	<link rel="stylesheet" type="text/css" href="${springMacroRequestContext.contextPath}/assets/css/uniqueCss.css"/>
	
	<link href="${springMacroRequestContext.contextPath}/assets/extjs/style/examples.css" rel="stylesheet" />
    <link href="${springMacroRequestContext.contextPath}/assets/extjs/style/carousel.css" rel="stylesheet" />
        
   <script src="${springMacroRequestContext.contextPath}/assets/extjs/js/ext-core-debug.js"></script>
   <script src="${springMacroRequestContext.contextPath}/assets/extjs/js/carousel.js"></script>
   
   <script>
         Ext.onReady(function() {
             new Ext.ux.Carousel('full-example', {
                 itemSelector: 'img',
                 interval: 3,
                 autoPlay: true,
                 showPlayButton: true,
                 pauseOnNavigate: true,
                 freezeOnHover: true,
                 transitionType: 'fade',
                 navigationOnHover: true       
             });
         })
     </script>
	
</head>
<body class="animated fadeInDown">
	<div class="container">
		<div style="width: 1024px;" align="center">
			<p align="center" style="margin-top: 90px; font-size: 30px; color: grey">
				<b>欢迎访问睿声CRM语音通信中间件</b>
			</p>
			<div class="row">
				<h2>&nbsp;</h2>
			</div>
			<div class="row">
				<div class="col-xs-12 col-sm-12 col-md-1 col-lg-1"></div>
				<label style="font-size: 34px;">${(indexInfo.indexDes)!''}</label><br />
				<br />
				<div class="col-xs-12 col-sm-12 col-md-5 col-lg-6">
					<div class="pull-left login-desc-box-l">
						<#if (indexInfo)??>
							<#if (indexInfo.photoUrl)??>
								<img src="${indexInfo.photoUrl}"style="width: 507px; height: 360px;">
							<#else>
								<img src="${springMacroRequestContext.contextPath}/assets/img/login.jpg"style="width: 507px; height: 360px;">
							</#if>
						<#else>
							<!--<img src="${springMacroRequestContext.contextPath}/assets/img/superbox/superbox-full-6.jpg"style="width: 507px; height: 360px;"> -->
							<div id="full-example" style="padding: 0px; height: 360px; width: 507px;">
					            <img src="${springMacroRequestContext.contextPath}/assets/img/superbox/superbox-full-6.jpg" >
					    		<img src="${springMacroRequestContext.contextPath}/assets/img/superbox/superbox-full-1.jpg" >
					            <img src="${springMacroRequestContext.contextPath}/assets/img/superbox/superbox-full-2.jpg" >
					            <img src="${springMacroRequestContext.contextPath}/assets/img/superbox/superbox-full-3.jpg" >
					            <img src="${springMacroRequestContext.contextPath}/assets/img/superbox/superbox-full-4.jpg" >
					            <img src="${springMacroRequestContext.contextPath}/assets/img/superbox/superbox-full-5.jpg" >
					        </div>
						</#if>
					</div>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-5 col-lg-5">
				<div class="widget-body" style="height: 360px;">
					<ul id="myTab1" class="nav nav-tabs bordered">
						<li id="tab1" class="active" onclick="loginSty('agent')">
							<a href="#s1" data-toggle="tab"><i class="fa fa-fw fa-lg fa-user"></i>用户登录</a>
						</li>
						<li id="tab2" onclick="loginSty('admin')">
							<a href="#s2" data-toggle="tab"><i class="fa fa-fw fa-lg fa-gear"></i>中间件登录</a>
						</li>
					</ul>
					<div id="myTabContent1" class="tab-content">
					<div class="tab-pane fade in active" id="s1">
						<form action="${springMacroRequestContext.contextPath}/doLogin" id="login-form" class="smart-form client-form" method="POST" style="">
							<input type="hidden" name="unregisterOk" id="unregisterOk">
							<fieldset>
<!-- 								<section> -->
<!-- 									<div class="inline-group"> -->
<!-- 										<label class="radio"></label> -->
<!-- 										<label class="radio" id="agent"> -->
<!-- 											<input type="radio" name="radio_inline" checked="checked" value="0"> -->
<!-- 											<i></i>坐席 -->
<!-- 										</label> -->
<!-- 										<label class="radio" id="manager"> -->
<!-- 											<input type="radio" name="radio_inline" value="1"> -->
<!-- 											<i></i>管理员 -->
<!-- 										</label> -->
<!-- 									</div> -->
<!-- 								</section> -->
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
							<footer>
								<button type="submit" class="btn btn-primary" id="agentSubmit">登录</button>
							</footer>
						</form>
					</div>
					<div class="tab-pane fade" id="s2">
						<form action="${springMacroRequestContext.contextPath}/user/dologin" id="adminlogin_forms" method="POST" class="smart-form client-form">
							<input type="hidden" name="pageFlag" value="admin" />
							<fieldset>
								<section>
									<label class="label">用户名</label>
									<label class="input">
										<i class="icon-append fa fa-user"></i>
										<input type="text" name="adminName" id="adminName" value="${(adminName)!''}">
										<b class="tooltip tooltip-top-right">
											<i class="fa fa-user txt-color-teal"></i> 
											请输入您的用户名
										</b>
									</label>
								</section>
								<section>
									<label class="label">密 码</label> <label class="input">
										<i class="icon-append fa fa-lock"></i>
										<input type="password" name="adminPassword" id="adminPassword" value="${(adminPassword)!''}">
										<b class="tooltip tooltip-top-right">
											<i class="fa fa-lock txt-color-teal"></i> 
											请输入您密码
										</b>
									</label>
									<div class="note" align="right">
										<font color="red" size="2" id="errorAdminFont"></font>
									</div>
								</section>
							</fieldset>
							<footer>
								<button id="adminSubmit" type="submit" class="btn btn-primary">登录</button>
							</footer>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
		<!-- 			<div class="row"> -->
		<!-- 				<div class="col-xs-12 col-sm-12 col-md-1 col-lg-1"></div> -->
		<!-- 				<div class="col-xs-12 col-sm-12 col-md-5 col-lg-6"> -->
		<!-- 					<h5 class="about-heading" align="left">&nbsp;</h5> -->
		<!-- 					预留空间 -->
		<!-- 					<p align="left"></p> -->
		<!-- 				</div> -->
		<!-- 				<div class="col-xs-12 col-sm-12 col-md-5 col-lg-5"> -->
		<!-- 					<h5 class="about-heading">&nbsp;</h5> -->
		<!-- 					预留空间 -->
		<!-- 					<p> -->
		<!-- 					</p> -->
		<!-- 				</div> -->
		<!-- 			</div> -->
		</div>
		<footer>
			<div align="center">
				<p>
					&nbsp;<br />
					<br />
					<br />
					<br />
					<span class="text-muted">上海睿声软件技术有限公司版权所有 联系方式 : 021-61487233</span>
				</p>
			</div>
		</footer>
	</div>
	
	<script src="${springMacroRequestContext.contextPath}/assets/js/jquery.min.js"></script>
	<script src="${springMacroRequestContext.contextPath}/assets/js/plugin/jquery-validate/jquery.validate.min.js"></script>
	
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
			
			$('#aclick').click(function() {
				refreshCheckCode();
			});
			
			$('#imageCode').click(function() {
				refreshCheckCode();
			});
			
			function refreshCheckCode() {
				var append = 'valid/getImage?ts='+ new Date().getTime() + 'a' + Math.random();
				$('#imageCode').attr('src', append);
			}
			
			$('#agentSubmit').click(function(){
				var exten = $.trim($('#loginExtension').val());
				if(exten == ""){
					if(confirm("未输入分机号,确定继续登陆?")){
						$("#login-form").submit();
					}
				}
			});
		});
		
// 		$("#agent").click(function(){
// 			if(!$("#extension").is(':visible')){
// 				$("#extension").show();
// 			}
// 		});
		
// 		$("#manager").click(function(){
// 			if($("#extension").is(':visible')){
// 				$("#extension").hide();
// 			}
// 		});
		
		function transferCommon() {
			window.location.href = "${springMacroRequestContext.contextPath}/login";
		}
		
		function loginSty(loginSty){
			if (loginSty=='agent') {
				$("#tab2").removeClass("active");
				$("#tab1").addClass("active");
				$("#s2").removeClass("in active");
				$("#s1").addClass("in active");
			}else{
				$("#tab1").removeClass("active");
				$("#tab2").addClass("active");
				$("#s1").removeClass("in active");
				$("#s2").addClass("in active");
			}
		}
		
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
