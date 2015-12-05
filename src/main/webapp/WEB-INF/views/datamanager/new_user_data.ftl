<script src="${springMacroRequestContext.contextPath}/assets/js/sockjs/sockjs.js"></script>
<div id="new_tmpDiv"></div>
<div id="new_user_data" style="">
	<section id="widget-grid-cstmservice" class="">
	<#if dataType?? && dataType=="task" && user??>
		<label class="btn btn-sm btn-primary" id="recycleAllButton" onclick="recycleAll('${user.uid}')" style="margin-bottom: 5px;" title="该操作会将此页面中的所有数据回收到该用户的归属部门中，若归属部门不存在，则回收到默认部门中。">全部回收</label>
		默认批次中的数据暂无法回收，请点击"详情"后进行相关操作。
	<#else>
		<label class="btn btn-sm btn-primary" id="addCstm" onclick="getInfos(null)" style="margin-bottom: 5px;">获取数据</label>
		<label class="btn btn-sm btn-success" id="startAutoCallButton" onclick="changeAutoCallStat();" style="margin-bottom: 5px;margin-left:10px;">开始自动呼叫</label>
		<label class="btn btn-sm btn-info" id="updateConfig" onclick="updateOwnConfig()" style="margin-bottom: 5px;margin-left:10px;">自动呼叫配置</label>
		<label style="padding-left: 20px;" id="getEd"></label>
		<label id="canGet"></label>
	</#if>
		<div class="row">
			<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<div class="jarviswidget jarviswidget-color-darken"
					id="wid-id-maincstmservice" data-widget-editbutton="true"
					data-widget-colorbutton="tarue">
					<header>
						<span class="widget-icon"><i class="fa fa-table"></i></span>
						<h2>我的任务</h2>
					</header>
					<div>
						<div class="jarviswidget-editbox"></div>
						<div class="widget-body no-padding">

							<div class="widget-body-toolbar">
								<div class="smart-form">
									<section>
										<div class="row">
											<label class="label col">电话号码</label>
											<label class="input col col-2">
                                   				<input name="phoneNumber" id="phoneNumber" value="">
                               				</label>
											<label class="label col">批次名</label>
											<label class="input col col-2">
                                   				<input name="batchName" id="batchName" value="">
                               				</label>
											<label class="label col">是否已呼</label>
											<div class="form-group col col-lg-2">
												<select name="callStatus" id="callStatus" class="select2" onchange="getResult();">
													<option value="">----全部----</option>
													<option value="0">未呼</option>
													<option value="1">已呼</option>
                                  				</select>
                               				</div>
                               				
                               				<label class="btn btn-sm btn-primary" id="search" onclick="getResult();">查询</label>
										</div>
									</section>
									<table id="new_dt_user_data" class="table table-striped table-bordered table-hover" data-order='[[ 0, "asc" ]]' width="100%"></table>
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
	var new_dt_user_data = "";
	
	// 自动呼叫个人配置
	window.parent.autocallConfig = ${(config)!'{"timing":"0","time":"5"}'};
	window.localStorage.autocallConfig = window.parent.autocallConfig;

	$(document).ready(function() {
		$("#callStatus").select2({
			width:"100%"
		});
		
		new_dt_user_data = $('#new_dt_user_data').DataTable({
			"dom" : "t" + "<'dt-toolbar-footer'<'col-sm-6 col-xs-12'i><'col-xs-12 col-sm-6 text-right'lp>>",
			"autoWidth" : true,
			"ordering" : true,
			"serverSide" : true,
			"processing" : true,
			"searching" : false,
			"infoCallback":function(settings, start, end, max, total, pre){
		    	return "显示第 " + start + " 至 " + end + " 项结果，共 <span id='totalNumber'>" + total + "<span> 项，已选中 " + getCheckNum() + " 项";
		    },
			"pageLength" : 10,
			"lengthMenu" : [ 10, 15, 20, 25, 30 ],
			"language" : {
				"url" : getContext()+ "public/dataTables.cn.txt"
			},
			"ajax" : {
				"url" : getContext() + "newuserdata/data",
				"type" : "POST",
				"data" : function(param) {
					param.ownUser = '${user.uid!""}';
					param.batchName = $("#batchName").val();
					param.phoneNumber = $("#phoneNumber").val();
					param.callStatus = $("#callStatus").val();
				}
			},
			"paging" : true,
			"pagingType" : "bootstrap",
			"lengthChange" : true,
			"order" : [ [ "5", "asc" ],[ "3", "asc" ],[ "1", "asc" ] ],
			"columns" : [
					
					{
						"title" : '<div class="btn-group">'
								+ ' <a class="btn btn-sm btn-primary dropdown-toggle" data-toggle="dropdown" href="javascript:void(0);">选择<span class="caret"></span></a><ul class="dropdown-menu">'
								+ ' <li><a href="javascript:void(0);"onclick="docheckall()" >全选所有</a></li>'
								+ '<li><a href="javascript:void(0);" onclick="docheckallpage()">全选本页</a></li>'
								+ '<li><a href="javascript:void(0);" onclick="docancelAll()">取消所有</a></li>'
								+ '</ul></div>',
						"sortable" : false,
						<#if dataType?? && dataType=="task">
							"visible" : false,
						</#if>
						"data" : "null",
						"render" : function(data, type, full) {
							if (autoCallList[full.phoneNumber]) {
								return '<input type="checkbox" checked="checked" id="ck'
										+ full.phoneNumber
										+ '" onclick="docheck('
										+ "'" + full.phoneNumber + "'" + ')"  />';
							} else {
								return '<input type="checkbox" id="ck'
										+ full.phoneNumber
										+ '" onclick="docheck('
										+ "'" + full.phoneNumber + "'" + ')" />';
							}
						},
						"width" : "30px"
					},
					{
						"title" : "电话号码",
						"data" : "phoneNumber",
						"render":function(data,type,full){
							return window.parent.hiddenPhone(full.phoneNumber)
						}
					},
					{
						"title" : "批次名",
						"data" : "batchname",
					},
					{
						"title" : "获取时间",
						"data" : "ownUserTimestamp",
						"visible" : true,
					},
					{
						"title" : "呼叫次数",
						"data" : "callCount",
						"visible" : true,
					},
					{
						"title" : "上次呼叫时间",
						"data" : "last_call_time",
						"visible" : true,
						"defaultContent" : "无"
					},
// 					{
// 						"title" : "呼叫/未呼叫次数",
// 						"data" : "null",
// 						"render" : function( data, type, full) {
// 							return full.callCounts+"/"+full.callNoCounts;
// 						}
// 					},
					
					{
						"title" : "操作",
		
						"data" : "null",
						"render" : function( data, type, full) {
							   return "<a onclick=getDetails('"+full.batchUuid+"','"+ full.phoneNumber +"');>详情</a>&nbsp;&nbsp;"
							   <#if dataType?? && dataType=="task">
								<#else>
							   		+"<a onclick=makeNewPhoneCall('"+full.phoneNumber +"','"+ full.batchUuid+"');>呼叫</a>";
							   </#if>
						}
					},
					
				],
				"initComplete": function (settings, json) {
					//显示意向信息
					getTitleDetailInfo();
		        },
			});
	});

	var condition = {};
	
	function getTitleDetailInfo(){
		
		$.post(getContext()+"newuserdata/showIntendDetails",function(d){
			$("#canGet").html('<strong class="label txt-color-blue" >已经有 '+d.intentNumber+' 个意向客户：</strong><span class="label bg-color-blue" >'+d.msg+'</span>');
		},"json");
		
		//显示意向信息
		$.post(getContext()+"newuserdata/showDataDetails",function(d){
			if(d.DataNumber !=null && d.DataNumber != undefined){
				$("#getEd").html('<strong class="label txt-color-blue" >单日已经获取 '+d.DataNumber+' 条数据：</strong><span class="label bg-color-blue" >'+d.msg+'</span>');
			}
			
		},"json");
	}
	
	window.autoCallList = window.parent.autoCallList;
	
	//在用户管理中回收全部
	function recycleAll(userid){
		if(userid != undefined && userid != null && userid != ""){
			$.post(getContext()+"deptdata/dept/recycleByUser",{userId:userid},function(data){
				
				if(data.success){
	 				$.smallBox({
	                     title : "操作成功",
	                     content : "<i class='fa fa-clock-o'></i> <i>成功回收数据</i>",
	                     color : "#659265",
	                     iconSmall : "fa fa-check fa-2x fadeInRight animated",
	                     timeout : 2000
	                 });
	 			}else{
	 				$.smallBox({
	                     title : "操作失败",
	                     content : "<i class='fa fa-clock-o'></i> <i>未回收数据！</i>",
	                     color : "#C79121",
	                     iconSmall : "fa fa-times fa-2x fadeInRight animated",
	                     timeout : 5000
	                 });
	 			}
				getResult();
			},"json");
		}
	}
	function docancel(){
	    $("[id^='ck']").each(function(){
	        if($(this).is(':checked')){
	            $(this).click();
	        }
	    });
	}
	
	function docheck(uid){
	    if($("#ck"+uid).is(':checked')){
	    	autoCallList[uid]=true;
	    }else{
	    	autoCallList[uid]=false;
	    }
	    addCheckInfo();
	}
	
	function docheckall(){
	    $.post(getContext() + "newuserdata/checkAll", condition, function(data){
	    	checklist = {};
			$("#checkInfo").text(data.datas.length);
	        for(var i in data.datas){
	        	autoCallList[data.datas[i]]=true;
	        }
	        docheckallpage();
	    },"json");
	}
	
	function docheckallpage(){

	    $("[id^='ck']").each(function(){
	        if(!$(this).is(':checked')){
	            $(this).click();
	        }
	    });
	}

	function docancelAll(){

	    $("[id^='ck']").each(function(){
	        if($(this).is(':checked')){
	            $(this).click();
	        }
	    });
	    autoCallList={};
		$("#checkInfo").text(0);				
	}
	
	function addCheckInfo(){
		var num=0;
		for(var i in autoCallList) {
			if(autoCallList[i]==true)
				num++;
		}
		$("#checkInfo").text(num);
	}
	
	function getCheckNum(){
		var num=0;
		for(var i in autoCallList) {
			if(autoCallList[i]==true)
				num++;
		}
		return "<span id='checkInfo'>" + num + "</span>";
	}
	
	//获取下一个要呼的号码。如果没有则依次呼叫下一个
	function getNextPhoneByAutoCall() {
		for(var i in autoCallList) {
			if(autoCallList[i] == true) {
				return i;
			}
		}
		
		//表示勾选的呼完了，需要停止自动呼叫
		if(window.autoCallSource === "check") {
			return -1;
		}
	}
	
	function removePhoneByAutoCall(phone){
// 		autoCallList[phone] = false;
		if($("#ck" + phone).is(":checked")) {
			$("#ck" + phone).click();
		}
		getResult();
	}
	
	/* 详情 */
	function getDetails(batchId,phoneNumber){
		window.parent.addTab("弹屏|" + phoneNumber,phoneNumber,JSON.stringify({number:phoneNumber,popType:'detail',controller:"newdatacall"},"detail"), "", "newuserdata");
	}
	
	/* 呼叫 */
	function makeNewPhoneCall(phoneNumber,batchId){
		//将该变量设置为1，该函数执行后的弹屏的tab页将没有关闭按钮
		window.parent.closetag = "1";
		$.post(getContext() + "newuserdata/makecall", {phone:phoneNumber},function(data) {
// 			addTab(phoneNumber,"newdatacall",data["call_session_uuid"],"");
// 			last_call_session_uuid.push(data["call_session_uuid"]);

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

	function updateOwnConfig() {
		if(window.parent.startAutoCall) {
			alert("请先关闭自动呼叫！");
			return;
		}
		$.post(getContext() + "newuserdata/userconfig",{},function(data){
			$("#new_tmpDiv").append(data);
		});
	}
	
	/* 从指定批次中获取数据  加载页面 */
	function getInfos(p){
		
		$.post(getContext() + "newuserdata/toDataPage", {
			batchId : p,
			target : "get"
		},function(d){
			
			$("#dialog-data").remove();
			$("#new_tmpDiv").empty().append(d);
		});
		
	}
	
	/* *
	 *	获取数据 不选择批次，直接后台随机获取
	 * */
// 	function getInfos(p) {
		
// 		$.post(getContext() + "newuserdata/getDataAuto", {
// 			batchId : p,
// 			target : "get"
// 		}, function(d) {
	
// 			if(d.success){
				
// 				$.smallBox({
//                     title : "操作成功",
//                     content : "<i class='fa fa-clock-o'></i> <i>成功获取数据"+ d.getCount +"</i>",
//                     color : "#659265",
//                     iconSmall : "fa fa-check fa-2x fadeInRight animated",
//                     timeout : 2000
//                 });
// 			}else{
				
// 				$.smallBox({
//                     title : "没有获取到数据",
//                     content : "<i class='fa fa-clock-o'></i> <i>"+ d.message +"！</i>",
//                     color : "#C79121",
//                     iconSmall : "fa fa-times fa-2x fadeInRight animated",
//                     timeout : 5000
//                 });
// 			}
// 			new_dt_user_data.ajax.reload();
			
// 		},"json");
// 	}

	if(window.parent.startAutoCall) {
		$("#startAutoCallButton").removeClass("btn-success").addClass("btn-danger").text("关闭自动呼叫");
	} else {
		$("#startAutoCallButton").removeClass("btn-danger").addClass("btn-success").text("开始自动呼叫");
	}
	
	//切换自动呼叫状态
	function changeAutoCallStat(stat){
		
		<#if Session.LOGINEXTEN?exists>
			
			//如果是自动呼叫，点击后改为不是自动呼叫，改变index中的值;
			if (window.parent.startAutoCall || stat == 1) {
				$("#startAutoCallButton").removeClass("btn-danger").addClass("btn-success").text("开始自动呼叫");
				window.parent.startAutoCall = 0;
				
				//关闭自动呼叫后设置为0，该函数执行后的弹屏的tab页将有关闭按钮
				window.parent.closetag = "0";
				
			} else {
				
				//将该变量设置为1，该函数执行后的弹屏的tab页将没有关闭按钮
				window.parent.closetag = "1";
				
				var nextPhone = getNextPhoneByAutoCall();
				var url;
				if(nextPhone && nextPhone != -1) {
					window.autoCallSource = "check";
					url = getContext() + "newuserdata/makecall"
				} else {
					window.autoCallSource = undefined;
					url = getContext()+"newuserdata/callNextPhone";
				}
				$.post(url,{phone:nextPhone},function(data){
					
					if(data.success) {
						
					} else {
						 changeAutoCallStat();
						 $.smallBox({
		                    title : "失败",
		                    content : "<i class='fa fa-clock-o'></i> <i>呼叫失败，"+data.message+"</i>",
		                    color : "#C46A69",
		                    iconSmall : "fa fa-times fa-2x fadeInRight animated",
		                    timeout : 3000
		                });
					}
				},"json").error(function(){
					 changeAutoCallStat();
					 $.smallBox({
	                   title : "自动呼叫失败",
	                   content : "<i class='fa fa-clock-o'></i> <i>呼叫失败</i>",
	                   color : "#C46A69",
	                   iconSmall : "fa fa-times fa-2x fadeInRight animated",
	                   timeout : 3000
	               });
				});
				
				$("#startAutoCallButton").removeClass("btn-success").addClass("btn-danger").text("关闭自动呼叫");
				window.parent.startAutoCall = 1;
			}
		<#else>
			$.smallBox({
	            title : "不能呼叫",
	            content : "<i class='fa fa-clock-o'></i> <i>请先绑定分机...</i>",
	            color : "#C79121",
	            iconSmall : "fa fa-times fa-2x fadeInRight animated",
	            timeout : 3000
	        });
		</#if>
	}
	
	/* 归还数据 */
	function getBackInfos(p) {

		$.post(getContext() + "newuserdata/toDetailsPage", {
			batchId : p,
			target : "back"
		}, function(d) {

			$("#new_user_data").append(d);
		});
	}
	
	/* 查询数据结果 */
	function getResult(){
		condition.batchName = $("#batchName").val();
		condition.phoneNumber = $("#phoneNumber").val();
		condition.callStatus = $("#callStatus").val();
		getTitleDetailInfo();
		new_dt_user_data.ajax.reload();
	}
	
</script>

