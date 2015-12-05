	<div id="globalshareDataDiv"></div>
	<div id="globalshareDatamainContent" style="">
	<!-- HEADER -->
		<!-- END RIBBON -->
		<!-- widget grid -->
		<section id="widget-grid-globalshareData" class="">
			<div class="row">
				<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div style="margin-bottom: 6px;">
						<#if getdatas??>
							<button disabled="disabled" class="btn btn-sm btn-primary">今日暂不能获取数据</button>
						<#else>
							<label id="getdatas" class="btn btn-sm btn-primary" onclick="getDataItem()">获取数据</label>
						</#if>
						<div id="timer" style="color:red"></div>
					</div>
					<div class="jarviswidget jarviswidget-color-darken" data-widget-editbutton="false">
						<header>
							<span class="widget-icon"><i class="fa fa-table"></i></span>
							<h2>我的共享池数据</h2>
						</header>
						<div>
							<div class="widget-body no-padding">
								<div class="widget-body-toolbar">
								<div class="smart-form">
									<table id="dt_basic_globalshareData" class="table table-striped table-bordered table-hover"  data-order='[[ 0, "asc" ]]' width="100%"></table>
								</div>
								</div>
							</div>
						</div>
					</div>
				</article>
			</div>
		</section>
	</div>
	<script type="text/javascript">
	
		var checklist = {};
	
		$(document).ready(function() {
			initTable();
		});
		
		function initTable(){
			oTable = $('#dt_basic_globalshareData').dataTable({
		    	"dom" :"t" + "<'dt-toolbar-footer'<'col-sm-6 col-xs-12'i><'col-xs-12 col-sm-6 text-right'lp>>",
				"autoWidth" : true,
				"ordering" : true,
				"serverSide" : true,
				"processing" : true,
				"searching" : false,
				"pageLength" : 10,
				"lengthMenu" : [ 10, 15, 20, 25, 30 ],
			 	"language": {
			         "url" : getContext() + "public/dataTables.cn.txt"
			     },
			    "ajax":{
			    	"url" : getContext() + "globalsharedata/globalshare/data",
			    	"type":"POST",
			    	"data" :function(param){
			    		
			    	}
			    },
			 	"paging" :true,
				"pagingType" :"bootstrap",
				"lengthChange" : true,
				"order" : [[ "5", "desc"]],
				"columns" : [
					   { "title" : "批次", "data" : "batchName"},
// 					   { "title" : "部门", "data" : "deptName"},
// 					   { "title" : "部门占有时间", "data" : "ownDepartmentTimestamp"},
					   { "title" : "电话号码", "data" : "null",
						 "render":function(data,type,full){
							return window.parent.hiddenPhone(full.phoneNumber)
						  }
				       },
					   { "title" : "数据信息", "data" : "json"},
					   { "title" : "呼叫次数", "data" : "callCount"},
					   { "title" : "最后一次电话结果", "data" : "lastCallResult"},
					   { "title" : "最后一次电话时间", "data" : "lastCallTime"},
// 					   { "title" : "意向类型", "data" : "intentType"},
// 					   { "title" : "意向时间", "data" : "intentTimestamp"},
					   { 
						   "title" : "操作",
						   "data" : "null",
						   "orderable":false,
						   "render": function(data,type,full){
							   return "<a onclick=call('"+full.phoneNumber+"')>呼叫</a>";
						   }
					   }
					],
			});
		}
		
		function call(phoneNumber){
			//将该变量设置为0，该函数执行后的弹屏的tab页将有关闭按钮
			window.parent.closetag = "0";
			$.post(getContext() + "newuserdata/makecall", {phone:phoneNumber},function(data) {
				
				if(!data.success){
					$.smallBox({
	                    title : "呼叫失败",
	                    content : "<i class='fa fa-clock-o'></i> <i>"+ data.message +"</i>",
	                    color : "#C79121",
	                    iconSmall : "fa fa-times fa-2x fadeInRight animated",
	                    timeout : 5000
	                });
				}
				
			},"json");
		}
		
		// 刷新
		function getResult(){
			$('#dt_basic_globalshareData').DataTable().ajax.reload(null,false);;
		}
		
		var wait = 60;
		
		function settime(){
			if(wait == 0) {
				$("#getdatas").attr("disabled", false);
				$("#getdatas").text("获取数据");
				wait = 60;
			}else{
				$("#getdatas").attr("disabled", true);
				$("#getdatas").text("" + wait + "秒后重新获取");
				wait--;
				window.name = wait;
				setTimeout(function() {
					settime()
	            }, 1000)
			}
		}
		
		function getDataItem(){
			var url = getContext() + "globalsharedata/globalshare/getGlobalShareData";
			$.post(url, function(data){
				if(data.success){
					$.smallBox({
                        title : "操作成功",
                        content : "<i class='fa fa-clock-o'></i> <i>成功获取" + data.datacount + "条数据..</i>",
                        color : "#659265",
                        iconSmall : "fa fa-check fa-2x fadeInRight animated",
                        timeout : 5000
                    });
					getResult();
				}else{
					$.smallBox({
                        title : "获取失败",
                        content : "<i class='fa fa-clock-o'></i> <i>暂无数据获取...</i>",
                        color : "#C46A69",
                        iconSmall : "fa fa-times fa-2x fadeInRight animated",
                        timeout : 5000
                    });
				}
			},'json');
			
// 			settime();

// 			timer = setInterval("CountDown()",1000);
		
			deleteCookie("examineeId", "", -1);
			
			if(!hasSetCookie("examineeId")){
				addCookie("examineeId", 30 * 1000, 30 * 1000);
			}
			
			settime($("#timer"));
		};
		
		
// 		var maxtime;  
// 		if(window.name==''){   
// 			maxtime = 10;  
// 		}else{  
// 			maxtime = window.name;
// 			timer = setInterval("CountDown()",1000);
// 		}
		
// 		function CountDown(){  
// 			if(maxtime>=0){  
// // 				hours = Math.floor(maxtime/3600);  
// // 				minutes = Math.floor((maxtime-hours*3600)/60);  
// 				seconds = Math.floor(maxtime%60);  
// // 				msg = "距离考试结束还有"+hours+"时"+minutes+"分"+seconds+"秒";  
// // 				document.all["timer"].innerHTML = msg;  
// 				--maxtime;  
// 				window.name = maxtime;
// 				$("#getdatas").attr("disabled", true);
// 				$("#getdatas").text("" + seconds + "秒后重新获取");
// 			}else{  
// 				clearInterval(timer);
// 				maxtime = 10;
// 				$("#getdatas").attr("disabled", false);
// 				$("#getdatas").text("获取数据");
// 			}  
// 		}
		
		function addCookie(name, value, expiresHours) {
			// escape() 函数可对字符串进行编码，这样就可以在所有的计算机上读取该字符串。
			var cookieString = name + "=" + escape(value); 
			// 判断是否设置过期时间,0代表关闭浏览器时失效
			if (expiresHours > 0) {
				var date = new Date();
				date.setTime(date.getTime() + expiresHours*1000);
				cookieString = cookieString + ";expires=" + date.toUTCString();
			}
			document.cookie = cookieString;
		};
		
		function editCookie(name, value, expiresHours) {
			var cookieString = name + "=" + escape(value);
			if (expiresHours > 0) {
				var date = new Date();
				date.setTime(date.getTime() + expiresHours*1000); //单位是毫秒
				cookieString = cookieString + ";expires=" + date.toGMTString();
			}
			document.cookie = cookieString;
		};
		
		function getCookieValue(name) {
			var strCookie = document.cookie;
			var arrCookie = strCookie.split("; ");
			for (var i = 0; i < arrCookie.length; i++) {
				var arr = arrCookie[i].split("=");
				if (arr[0] == name) {
					return unescape(arr[1]);
					break;
				} else {
					continue;
				}
			}
		};
		
		function hasSetCookie(name){
			var strCookie = document.cookie;
			var arrCookie = strCookie.split("; ");
			for (var i = 0; i < arrCookie.length; i++) {
				var arr = arrCookie[i].split("=");
				if(arr[0] == name) {
					return true;
				}
			}
			return false;
		};
		
		function deleteCookie(name, value, days){
			var d = new Date();
		    d.setTime(d.getTime() + (days*24*60*60*1000));
		    var expires = "expires="+d.toUTCString();
		    document.cookie = name + "=" + value + "; " + expires;
		};
		
		$(function() {
// 			deleteCookie("examineeId", "", -1);
			if(hasSetCookie("examineeId")){
				settime($("#timer"));
			}
		});
		
		function settime(remainTime) {
			
			var _countdown = parseInt(getCookieValue("examineeId")) / 1000;
			
			if(_countdown <= 1 || isNaN(_countdown)) {
				
				clearTimeout(t); 
				
				$("#getdatas").attr("disabled", false);
				$("#getdatas").text("获取数据");
				deleteCookie("examineeId", "", -1);
				
				
			}else{
				var _second = _countdown % 60;
// 				var _minute = parseInt(_countdown / 60) % 60;
// 				var _hour = parseInt(parseInt(_countdown / 60) / 60);
			 
// 				if (_hour < 10)
// 				_hour = "0" + _hour.toString();
// 				if (_second < 10)
// 				_second = "0" + _second.toString();
// 				if (_minute < 10)
// 				_minute = "0" + _minute.toString();
				 
// 				remainTime.html(_hour + ":" + _minute + ":" + _second);

				$("#getdatas").attr("disabled", true);
				$("#getdatas").text("" + _second + "秒后重新获取");

				_countdown--;
				editCookie("examineeId", _countdown * 1000, _countdown * 1000);
			}
			
			// 每1000毫秒执行一次
			var t = setTimeout(function() {
				settime(remainTime);
			}, 1000);
		};
		
</script>
	
