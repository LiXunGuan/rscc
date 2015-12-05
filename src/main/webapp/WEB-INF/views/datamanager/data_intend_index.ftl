<div id="new_intend_tmpDiv"></div>
<div id="new_user_intend_data" style="">
	<section id="widget-grid-cstmservice" class="">
		<#if dataType?? && dataType=="intent" && user??>
			<label class="btn btn-sm btn-primary" id="batMove" onclick="batMove('${user.uid}')" style="margin-bottom: 5px;" title="批量转移">批量转移</label>
		</#if>
		<div class="row">
			<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<div class="jarviswidget jarviswidget-color-darken"
					id="wid-id-maincstmservice" data-widget-editbutton="true"
					data-widget-colorbutton="tarue">
					<header>
						<span class="widget-icon"><i class="fa fa-table"></i></span>
						<h2>我的意向客户信息</h2>
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
                               				
	                           				<label class="label col">意向类型</label>
											<label class="input col col-2">
												<select class="select2" name="intentType" id="intentType" onchange="getResult();">
													<option value="nonull" selected="selected">请选择意向类型</option>
													<#if intents??>
														<#list intents as i>
															<option value="${i.uid}">${i.intentName}</option>
														</#list>
													</#if>
												</select>
											</label>
                               				
											<input type="hidden" name="batchUuid" id="batchUuid" value="${batchUuid!''}">
                               				<label class="btn btn-sm btn-primary" id="search" onclick="getResult();">查询</label>
										</div>
									</section>
									<table id="dt_intend_data" class="table table-striped table-bordered table-hover" data-order='[[ 0, "asc" ]]' width="100%"></table>
								</div>
							</div>
						</div>
					</div>
			</article>
		</div>
	</section>
</div>


<script type="text/javascript">

	var dt_intend_data = "";
	
	var intentList={};
	
	var condition = {};
	
	$(document).ready(function() {
	
		
		
		dt_intend_data = $('#dt_intend_data').DataTable({
			"dom" : "t" + "<'dt-toolbar-footer'<'col-sm-6 col-xs-12'i><'col-xs-12 col-sm-6 text-right'lp>>",
			"autoWidth" : true,
			"ordering" : true,
			"serverSide" : true,
			"processing" : true,
			"searching" : false,
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
					param.ownDepartment = '${user.department!''}';
					param.intentType = $("#intentType").val();					
					param.phoneNumber = $("#phoneNumber").val();
					param.batchName = $("#batchName").val();
				}
			},
			"paging" : true,
			"pagingType" : "bootstrap",
			"lengthChange" : true,
			<#if dataType?? && dataType=="intent" && user??>
			"infoCallback":function(settings, start, end, max, total, pre){
		    	return "显示第 " + start + " 至 " + end + " 项结果，共 " + total + " 项，已选中 " + getCheckNum() + " 项";
		    },
			</#if>
			"order" : [ [ "0", "desc" ] ],
			"columns" : [
					<#if dataType?? && dataType=="intent" && user??>
						{
							"title" : '<div class="btn-group">'
									+ ' <a class="btn btn-sm btn-primary dropdown-toggle" data-toggle="dropdown" href="javascript:void(0);">选择<span class="caret"></span></a><ul class="dropdown-menu">'
									+ ' <li><a href="javascript:void(0);"onclick="intentdocheckall()" >全选所有</a></li>'
									+ '<li><a href="javascript:void(0);" onclick="intentdocheckallpage()">全选本页</a></li>'
									+ '<li><a href="javascript:void(0);" onclick="intentdocancelAll()">取消所有</a></li>'
									+ '</ul></div>',
							"sortable" : false,
							"data" : "null",
							"render" : function(data, type, full) {
								
								if (intentList[full.uid]) {
									return '<input type="checkbox" checked="checked" id="intentck'
											+ full.uid
											+ '" onclick="intentdocheck('
											+ "'" + full.uid + "'" + ')"  />';
								} else {
									return '<input type="checkbox" id="intentck'
											+ full.uid
											+ '" onclick="intentdocheck('
											+ "'" + full.uid + "'" + ')" />';
								}
							},
							"width" : "30px"
						},
					</#if>
					{
						"title" : "电话号码",
						"data" : "phoneNumber",
						"render":function(data,type,full){
							return window.parent.hiddenPhone(full.phoneNumber);
						}
					},
					{
						"title" : "呼叫次数",
						"data" : "callCount",
					},
					{
						"title" : "最后呼叫结果",
						"data" : "last_call_result",
						"render" : function(data, type, full) {
							if (full.callCount == 0) {
								return "无";
							}
							if (full.last_call_result) {
								return "接通";
							} else {
								return "未接通";
							}
						}
					},
					{
						"title" : "最后呼叫时间",
						"data" : "last_call_time",
					},
					{
						"title" : "批次名",
						"data" : "batchname",
					},
					{
						"title" : "意向类型",
						"data" : "intent_type",
					},
					{
						"title" : "审批状态",
						"data" : "beAudit",
						"render" : function( data, type, full) {
							var stat;
							if(full.beAudit=='0') {
								stat = "未审批";
							} else if (full.beAudit=='1'){
								stat = "待审批";
							} else {
								stat = "被驳回";
							}
							return stat;
						}
					},
					{
						"title" : "操作",
						"data" : "null",
						"render" : function( data, type, full) {
							var stat;
							if(full.beAudit=='1') {
								stat = "撤销审批";
							} else {
								stat = "提交审批";
							}
							return "<a onclick=getDetails('"+full.batchUuid+"','"+ full.phoneNumber +"');>详情</a>&nbsp;&nbsp;"
							<#if dataType?? && dataType=="intent" && user??>
									+"<a onclick=changeOwner('" + full.batchUuid + "','" + full.uid + "','" + full.ownUser + "');>转移客户</a>&nbsp;&nbsp;"
							<#else>
									+"<a onclick=getCallPhones('" + full.batchUuid + "','" + full.phoneNumber  + "');>呼叫</a>&nbsp;&nbsp;"
								    +"<a onclick=changeStatu('" + full.batchUuid + "','" + full.phoneNumber + "','" + full.intentType + "','" +"intend" + "');>修改意向类型</a>&nbsp;&nbsp;"
							</#if>
								    +"<a onclick=putToSharePool('" + full.batchUuid + "','" + full.phoneNumber + "','" +"global_share" + "');>放入共享池</a>&nbsp;&nbsp;"
								    +"<a onclick=commitAudit('" + full.batchUuid + "','" + full.phoneNumber + "','" + (full.beAudit=='1'?0:1) + "');>" + stat + "</a>";
							}
						}, 
					]
				});
		
		
		
				$('#intentType').select2({
					width:"100%"
				});
			});
	
	
	
	/* 呼叫 */
	function getCallPhones(batchuuid, phoneNumber) {
		//将该变量设置为0，该函数执行后的弹屏的tab页将有关闭按钮
		window.parent.closetag = "0";
		$.post(getContext() + "newuserdata/makecall", {phone:phoneNumber},function(data) {
			
			if(!data.success){
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
	
	/* 全选所有 *******************************/
	function intentdocheckall(){
		intentList={};
	    var url = getContext()+'newuserdata/intentcheckAll';
	    $.post(url, {userid:'${user.uid}',dept:'${user.department}'},function(data){
	    	$("#intentcheckInfo").text(data.intentdata.length);
	    	for(var i in data.intentdata){
	    		intentList[data.intentdata[i]]=true;
	    	}
	        intentdocheckallpage();
	    },"json");
	}

	/* 全选本页 */
	function intentdocheckallpage(){
		$("[id^='intentck']").each(function(){
	        if(!$(this).is(':checked')){
	            $(this).click();
	        }
	    });
	}
	
	/* 选择一个 */
	function intentdocheck(uid){
		
	    if($("#intentck"+uid).is(':checked')){
	        intentList[uid]=true;
	        currentItem = uid;
	    }else{
	        intentList[uid]=false;
	    }
	    addCheckInfo();
	}
	
	function addCheckInfo(){
		var num=0;
		for(var i in intentList) {
			if(intentList[i]==true)
				num++;
		}
	    
		$("#intentcheckInfo").text(num);
	}
	
	
	/* 取消选择所有 */
	function intentdocancelAll(){
	    intentList={};

	    $("[id^='intentck']").each(function(){
	        if($(this).is(':checked')){
	            $(this).click();
	        }
	    });
	}
	
	function docancel(){
	    $("[id^='intentck']").each(function(){
	        if($(this).is(':checked')){
	            $(this).click();
	        }
	    });
	}
	
	
	/* 选中的项数 */
	function getCheckNum(){
		var num=0;
		for(var i in intentList) {
			if(intentList[i]==true)
				num++;
		}
		return "<span id='intentcheckInfo'>" + num + "</span>";
	}
	/********批量选择END***************/
	
	
	//批量转移，修改拥有者
	function batMove(userId){
		var intentchecklist = [];
		for(var n in intentList){
			if(intentList[n]==true){
				intentchecklist.push(n);
			}
		}
		//如果未选择客户
		if(intentchecklist.length == 0){
			$.bigBox({
				title : "提示",
				content : "请勾选需要进行操作的数据！",
				color : "#C79121",
				icon : "fa fa-bell shake animated",
				timeout : 2000
			});
		}else if(intentchecklist.length > 0){
			$.post(getContext() + "newuserdata/changeOwner", {
				
				"intentchecklist" : intentchecklist,
				"ownerUuid" : userId,
				},function(d){
				$("#new_user_intend_data").append(d);
			});
		}
	}
	
	//修改拥有者--转移给同一个部门下的人
	function changeOwner(batchUuid, dataUuid, ownerUuid) {
		
		$.post(getContext() + "newuserdata/changeOwner", {
			"batchUuid" : batchUuid,
			"dataUuid" : dataUuid,
			"ownerUuid" : ownerUuid,
			},function(d){
			$("#new_user_intend_data").append(d);
		}); 
	}
	
	
	function getCall(batchId,phoneNumber){
		$.post(getContext() + "newuserdata/makecall", {phone:phoneNumber},function(data) {
// 			addTab(phoneNumber,"newdatacall",data["call_session_uuid"],"");
// 			last_call_session_uuid.push(data["call_session_uuid"]);
		},"json");
	}

	/* 转为意向客户  */
	function changeStatu(batchuuid, phoneNumber, intentType, tag) {
		
		$.post(getContext() + "newuserdata/toChangeStatuPage", {
			"batchuuid" : batchuuid,
			"phoneNumber" : phoneNumber,
			"intentType" :intentType,
			"tag" : tag},function(d){
			$("#dialog-data").remove();
			$("#new_user_intend_data").append(d);
		}); 
	}
	
	function putToSharePool(batchuuid, phoneNumber,tag) {
		
		
		$.SmartMessageBox({
			title : "删除",
			content : "该操作会将该客户放入共享池，确定执行？",
			buttons : "[No][Yes]",
		}, function(ButtonPressed) {
			if (ButtonPressed === "Yes") {
				
				$.post(getContext() + "newuserdata/changeStatus", {
					"batchuuid" : batchuuid,
					"phoneNumber" : phoneNumber,
					<#if dataType?? && dataType=="intent" && user??>
					"department" : '${user.department}',
					</#if>
 					"tag" : tag
				}, function(d) {
					if (d.success) {
						$.smallBox({
							title : "操作成功",
							content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
							color : "#659265",
							iconSmall : "fa fa-check fa-2x fadeInRight animated",
							timeout : 2000
						});
						
						$('#dt_intend_data').DataTable().ajax.reload(null,false);
					}
				}, "json");
			}
			
		});
	}
	
	/* 提交审批 */
	function commitAudit(batchuuid, phoneNumber, stat) {
		$.post(getContext() + "newuserdata/commitAudit", {
			<#if dataType?? && dataType=="intent" && user??>
			"department" : '${user.department}',
			"userid" : '${user.uid}',
			</#if>
			"phoneNumber" : phoneNumber,
			"stat" : stat
		}, function(d) {
			if (d.success) {
				$.smallBox({
					title : "操作成功",
					content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
					color : "#659265",
					iconSmall : "fa fa-check fa-2x fadeInRight animated",
					timeout : 2000
				});
				
				$('#dt_intend_data').DataTable().ajax.reload(null,false);
			}
		}, "json");
	}
	

	/* 获取数据 */
	function getInfos(p) {
		$.post(getContext() + "newuserdata/toDetailsPage", {
			batchId : p,
			target : "get"
		}, function(d) {

			$("#new_user_intend_data").append(d);
		});
	}

	/* 归还数据 */
	function getBackInfos(p) {

		$.post(getContext() + "newuserdata/toDetailsPage", {
			batchId : p,
			target : "back"
		}, function(d) {

			$("#new_user_intend_data").append(d);
		});
	}

	function addIntent() {

		$.post(getContext() + "dataintent/add", function(data) {
			$("#new_user_intend_data").append(data);
		});
	}
	
	function getResult(){
		
		dt_intend_data.ajax.reload();
	}
	
	function getDetails(batchId,phoneNumber){
		
		window.parent.addTab("弹屏|" + phoneNumber,phoneNumber,JSON.stringify({number:phoneNumber,popType:'detail',"controller":"newdatacall"},"detail"), "", "newuserdata_data_intend_index");
	}
	
</script>

