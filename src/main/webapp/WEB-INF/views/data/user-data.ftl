<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/x-editable/x-editable.min.js"></script>
<style>
#toolBox{
	float:left;
	table-layout:auto;
	margin-bottom:5px;
}
.row [aria-label]{
	vertical-align:middle;
}
#widget-grid-data .widget-body-toolbar .btn-xs {
	margin-top:0;
}

.smart-form .toggle input:not(:checked)+i:before
{
	background-color: #FF0000;
}
.progress{
	height: 10px;
	margin: 0px;
}
.popover.bottom{
	margin-top:20px;
}
.popover .popover-title{
	padding: 8px 14px;
}
.popover .popover-content{
	padding: 9px 14px;
}
.popover.bottom{
	margin-top:25px;
}

/* 字体间距 */
#widget-grid-data div .label.col {
	width: 85px;
	text-align: right;
	padding-left: 12px;
	padding-right: 12px;
}

</style>
<div id="tmpDiv"></div>
<div id="userContent" style="">
	<!-- HEADER -->
	<!-- END RIBBON -->
	<!-- widget grid -->
	<section id="widget-grid-data" class="">
		<section>
				<div class="row" style="margin:0">
				<table cellspacing="0" cellpadding="0" border="0" class="ui-pg-table navtable" id="toolBox">
				<tbody>
				<tr>
   			    <td class="ui-pg-button ui-corner-all" id="getdata" title="" data-original-title="Add new row">
<!-- 					<div class="btn btn-sm btn-primary"> -->
<!-- 					<span class="">获取数据</span> -->
<!-- 					</div> -->
					<a href="javascript:void(0);" class="btn btn-sm btn-primary" id="getData" data-toggle="popover" title="选择来源" >
					   获取数据
					</a>
				</td>
				<td class="ui-pg-button ui-state-disabled" style="width:6px;" data-original-title="" title="">
					<span class="ui-separator"></span>
				</td>
<!-- 				<td class="ui-pg-button ui-corner-all" id="add" title="" data-original-title="Add new row" onclick="batRevert();"> -->
<!-- 					<div class="btn btn-sm btn-primary"> -->
<!-- 					<span class="">归还选中数据</span> -->
<!-- 					</div> -->
<!-- 				</td> -->
<!-- 				<td class="ui-pg-button ui-state-disabled" style="width:6px;" data-original-title="" title=""> -->
<!-- 					<span class="ui-separator"></span> -->
<!-- 				</td> -->
<!-- 				<td class="ui-pg-button ui-corner-all" title="" id="callQueue" data-original-title="Delete selected row" onclick="callQueue(this);"> -->
<!-- 					<div class="btn btn-sm btn-primary"> -->
<!-- 					<span class="">依次呼叫选中项</span> -->
<!-- 					</div> -->
<!-- 				</td> -->
<!-- 				<td class="ui-pg-button ui-state-disabled" style="width:6px;" data-original-title="" title=""> -->
<!-- 					<span class="ui-separator"></span> -->
<!-- 				</td> -->
				<td class="ui-pg-button ui-corner-all" title="" id="callQueue" data-original-title="Delete selected row" onclick="startCall({})">
					<div class="btn btn-sm btn-success">
					<span class="">开始呼叫</span>
					</div>
				</td>
				<td class="ui-pg-button ui-state-disabled" style="width:6px;" data-original-title="" title="">
					<span class="ui-separator"></span>
				</td>
<!-- 				<td class="ui-pg-button ui-corner-all" title="" id="refresh" data-original-title="Reload Grid" onclick="getResult();"> -->
<!-- 					<div class="btn btn-sm btn-primary"> -->
<!-- 					<span class="">刷新内容</span> -->
<!-- 					</div> -->
<!-- 				</td> -->
				<!-- <td class="ui-pg-button ui-corner-all ui-state-disabled" id="jqgrid_ilsave" title="" data-original-title="Save row">
					<div class="btn btn-sm btn-success">
					<span class="fa fa-save"></span>
					</div>
				</td>
				<td class="ui-pg-button ui-corner-all ui-state-disabled" id="jqgrid_ilcancel" title="" data-original-title="Cancel row editing">
					<div class="btn btn-sm btn-danger">
					<span class="fa fa-times">
					</span>
					</div>
				</td> -->
				</tr>
				</tbody>
				</table>
				</div>
		</section>
		
		
		<div class="row">
			<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">

				<div class="jarviswidget jarviswidget-color-darken" id="wid-id-0" data-widget-editbutton="false">
					<header>
						<span class="widget-icon"><i class="fa fa-table"></i></span>
						<h2>我的数据</h2>
					</header>
					<div>
						<div class="widget-body no-padding">
							<div class="widget-body-toolbar">
								<div class="smart-form">
									<section>
											<div class="row" style="margin:0">
												<label class="label col">数据来源</label>
												<div class="col col-2">
				                                    <label class="select">
														<select name="dataSource" id="dataSource">
														<option value="">全部</option>
														<#list sources as s>
															<option value=${s}>${s}</option>
														</#list>
														</select>
				                                    </label>
				                                </div>
												<label class="label col">名称</label>
												<label class="input col col-2">
													<input type="text" name="itemName" id="itemName" value="" />
												</label>
												<label class="label col">号码</label>
												<label class="input col col-2">
													<input type="text" name="itemPhone" id="itemPhone" value="" />
												</label>
											</div>
									</section>	
									<section style="margin-top: 10px;">
										<div class="row" style="margin:0">
											<label class="label col">地址</label>
											<label class="input col col-2">
												<input type="text" name="itemAddress" id="itemAddress" value="" />
											</label>
											<label class="label col">其它信息</label>
											<label class="input col col-2">
												<input type="text" name="itemJson" id="itemJson" value="" />
											</label>
											<label class="label col">呼叫次数</label>
											<label class="input col col-2">
												<input type="text" name="callTimes" id="callTimes" value="" />
											</label>
								 			<label class="btn btn-sm btn-primary" onclick="getResult();">查找</label>
								 			<input type="hidden" id="currentUuid" value="${uuid}" />
								 			<a href="#" id="dataPool" style="visibility:hidden;position:absolute">移动到</a>
											<input type="hidden" id="dataUuid">
										</div>
									</section>
								</div>
								<table id="dataTable" class="table table-striped table-bordered table-hover" data-order='[[ 1, "asc" ]]' width="100%"></table>
							</div>
						</div>
					</div>
				</div>
			</article>
		</div>
	</section>
</div>



<script type="text/javascript">

		$(function(){
			
			var dataTable = $('#dataTable')
			.DataTable(
					{
						"dom" : "t"
								+ "<'dt-toolbar-footer'<'col-sm-6 col-xs-12'i><'col-xs-12 col-sm-6 text-right'lp>>",
						"autoWidth" : false,
						"ordering" : true,
						"serverSide" : true,
						"processing" : true,
						"searching" : false,
						"pageLength" : 10,
						"lengthMenu" : [ 10, 15, 20, 25, 30 ],
						"infoCallback":function(settings, start, end, max, total, pre){
					    	var obj = $('#dataTable').DataTable().ajax.json();
					    	var num = obj==undefined?0:obj.completeCount;
					    	return "显示第 " + start + " 至 " + end + " 项结果，共 " + total + " 项，已选中 " + getCheckNum() + " 项，已完成 " + num + " 项";
					    },
//					    "footerCallback" : function( tfoot, data, start, end, display ){
//					    	if(obj!=undefined){
//					    		$("#complete").text(obj.completeCount);
//					    	}
//					    },
						"language" : {
							"url" : getContext() + "public/dataTables.cn.txt"
						},
						"ajax" : {
							"url" : getContext() + "userdata/review",
							"type" : "POST",
							"data" : function(param) {
								param.itemName = $("#itemName").val();
								param.itemPhone = $("#itemPhone").val();
								param.itemAddress = $("#itemAddress").val();
								param.itemJson = $("#itemJson").val();
								param.dataSource = $("#dataSource").val();
								param.callTimes = $("#callTimes").val();
							}
						},
						"paging" : true,
						"pagingType" : "bootstrap",
						"lengthChange" : true,
						"order" : [ [ "1", "desc" ] ],
						"columns" : [
								{
									"width": "80px",
									"title" : '<div class="btn-group">'
											+ ' <a class="btn btn-primary" href="javascript:void(0);" onclick="docheckall()">全选</a>'
											+ ' <a class="btn btn-primary dropdown-toggle" data-toggle="dropdown" href="javascript:void(0);"><span class="caret"></span></a><ul class="dropdown-menu">'
											+ ' <li><a href="javascript:void(0);"onclick="docheckall()" >全选所有</a></li>'
											+ '<li><a href="javascript:void(0);" onclick="docheckallpage()">全选本页</a></li>'
											+ '<li><a href="javascript:void(0);" onclick="docancelAll()">取消所有选择</a></li>'
											+ '</ul></div>',
									"sortable" : false,
									"data" : null,
									"render" : function(data, type, full) {
										if (itemCheckList[full.uid]) {
											return '<input type="checkbox" checked="checked" id="dck'
													+ full.uid
													+ '" onclick="docheck('
													+ "'" + full.uid + "'" +  ')"  />';
										} else {
											return '<input type="checkbox" id="dck'
													+ full.uid
													+ '" onclick="docheck('
													+ "'" + full.uid + "'" +  ')"  />';
										}
									}
								},
								{ "title" : "条目名称","data" : "itemName","defaultContent":""},
								{ 
								   "title" : "条目号码",
								   "data" : "itemPhone",
								   "render": function(data,type,full){
									   return "<a onclick='getMore(\""+full.uid+"\");'>" + window.parent.hiddenPhone(full.itemPhone) +"</a>";
								   }
							    },
//								{ "title" : "条目名称","data" : "itemName","defaultContent":"无名"},
								{ "title" : "条目地址","data" : "itemAddress","defaultContent":"无地址"},
								{ "title" : "条目其它信息","data" : "itemJson","defaultContent":"无其它信息"},
								{ "title" : "数据来源","data" : "dataSource","defaultContent":"无"},
								{ "title" : "呼叫次数","data" : "callTimes","defaultContent":"无"},
								{ "title" : "最后沟通时间","data" : "lastcalltime","defaultContent":"无"},
								{
									"title" : "操作",
									"data" : "null",
									"render" : function(data, type, full) {
									  <#if (Session.unexten)?? >
										  return "<a id=" + full.uid + " onclick='moveTo(\"" + full.uid + "\");'>移动到</a>&nbsp;&nbsp;&nbsp;&nbsp;" + "<a id=" + full.uid + " onclick='saveAsCustomer(\"" + full.itemPhone + "\",\"" + full.uid + "\");'>保存为客户</a>";
									  <#else>
										  if(full.defVal == '1'){
											   return '<div class="btn-group">'
										   					+ '<button class="btn btn-success btn-xs" onclick=callPhone("'+full.uid+'",1)> 呼叫</button>'
									   						+ '<button class="btn btn-success btn-xs dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></button>'
									   						+ '<ul class="dropdown-menu btn-xs" style="min-width:100px"><li>'
								   							+ '<a href="javascript:callPhone(\''+full.uid+'\',0);"> 加 0 呼叫</a>'
								   							+ '</li></ul></div>&nbsp;&nbsp;&nbsp;<a id=' + full.uid + " onclick='moveTo(\"" + full.uid + "\");'>移动到</a>&nbsp;&nbsp;&nbsp;&nbsp;" + "<a id=" + full.uid + " onclick='saveAsCustomer(\"" + full.itemPhone + "\",\"" + full.uid + "\");'>保存为客户</a>";
										   } else {
											   return '<div class="btn-group">'
							   					 			+ '<button class="btn btn-success btn-xs" onclick=callPhone("'+full.uid+'",0)>加 0 呼叫</button>'
									   						+ '<button class="btn btn-success btn-xs dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></button>'
									   						+ '<ul class="dropdown-menu btn-xs" style="min-width:100px"><li>'
								   							+ '<a href="javascript:callPhone(\''+full.uid+'\',1);"> 呼叫</a>'
								   							+ '</li></ul></div>&nbsp;&nbsp;&nbsp;<a id=' + full.uid + " onclick='moveTo(\"" + full.uid + "\");'>移动到</a>&nbsp;&nbsp;&nbsp;&nbsp;" + "<a id=" + full.uid + " onclick='saveAsCustomer(\"" + full.itemPhone  + "\",\"" + full.uid + "\");'>保存为客户</a>";
										   }
							   			</#if>									
//										return  "<a id=" + full.uid + " onclick='moveTo(\"" + full.uid + 
//										"\");'>移动到</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='callPhone(\"" + full.uid + 
//										"\");'>呼叫</a>";
									}
								} ],
					});
			
			$("#dataSource").select2({
	    		width:"100%",
	    	});
			
			$("#dataSource").change(function() {
				getResult();
			});
			
			$('#getData').popover({
				html:true,
				content :'<form class="smart-form" style="width:130px"><div class="row"><div class="col">' + <#list sources as s>
				'<label class="checkbox">' + 
				'<input type="checkbox" name="sources" value="${s}">' +
				'<i></i>${s}</label>' +
				</#list>
				'</div></div>' + 
				'<div class="form-actions"><div class="row"><div class="col col-6"><label class="btn btn-default btn-sm" onclick="$(\'#getData\').popover(\'hide\')">取消</button></div><div class="col col-6"><label class="btn btn-primary btn-sm" onclick="getData()">确定</button></div></div></div></form>'
			});
			
			$('#dataPool').editable({
				showbuttons: 'bottom',
				anim: "fast",
				type: 'select2',
				savenochange: true,
				select2: {
					width:'150px',
					placeholder: "选择一个数据池"
					},
				ajaxOptions: {
					dataType: 'json'
				},
				url: getContext() + "userdata/toPool",
		        pk: 1,
		        source: [
                	<#list pools as p>
                		<#if p_index!=0>,</#if>
		        		{id:"${p.uuid}",text:"${p.containerName}"}
	        		</#list>
		        	],
	        	params: function(params) {
	        	    params.dataUuid = currentDataId;
	        	    params.poolUuid = params.value;
	        	    return params;
	        	},
	        	success: function(data, newValue){
	        		if(data.success){
						$.smallBox({
							title : "操作成功",
							content : "<i class='fa fa-clock-o'></i> <i>操作成功...</i>",
							color : "#659265",
							iconSmall : "fa fa-check fa-2x fadeInRight animated",
							timeout : 2000
						});
						itemCheckList[currentDataId]=false;
						$('#dataTable').DataTable().ajax.reload(null,false);;
					}else{
						$.smallBox({
							title : "操作失败",
							content : "<i class='fa fa-clock-o'></i> <i>操作失败...</i>",
							color : "#C46A69",
							iconSmall : "fa fa-times fa-2x fadeInRight animated",
							timeout : 2000
						});
					}
	        	},
		        name: 'projectName',
		        title: '请选择要放入的池',
		        placement: function(tip,element){
		        	$(element).css("top",$("#" + currentDataId).position().top - 55);
		        	$(element).css("left",$("#" + currentDataId).position().left + ($("#" + currentDataId).outerWidth(false)-$(element).outerWidth(false))/2 - 8);
		        	return "top";
		        }
		    });
		});
		
		var itemCheckList={};

		$('#dialog_data').modal("show");
	
		$("#doFilter").click(function(){
			$('#dataTable').DataTable().ajax.reload(null,false);;
		});

		function getResult(){
			$('#dataTable').DataTable().ajax.reload(null,false);;
		}
		
		/* 关闭窗口的回调函数  */
		$('#dialog_data').on('hide.bs.modal', function (e) {   /* hide 是关闭   调用后在关闭;hidden 是关闭后在调用； */
			$("#dialog_data").remove();
		});

		function moveTo(currentId){
			currentDataId = currentId;
			//使用延时是因为不用的时候立刻检测到其他地方的点击事件，于是就关闭了选择窗口
			setTimeout(function(){
				$("#dataPool").editable("show");
			},1);
		}	
		
		function removeThis(t,i){
		     $(t).parent().remove();

		    itemCheckList[i]=false;
		    $('#dck'+i).click();
		}

		function docheckall(){

		    var url = getContext()+'userdata/checkDataAll';
		    $.post(url,{},function(data){
		        for(var i in data.items){
	                itemCheckList[data.items[i]]=true;
		        }
		        docheckallpage();
		    },"json");
		}

		function docheckallpage(){

		    $("[id^='dck']").each(function(){
		        if(!$(this).is(':checked')){
		            $(this).click();
		        }
		    });
		}

		function docancelAll(){

		    $("[id^='dck']").each(function(){
		        if($(this).is(':checked')){
		            $(this).click();
		        }
		    });

		    itemCheckList={};
		}

		function docheck(uid){

		    if($("#dck"+uid).is(':checked')){
// 		        itemCheckList[uid]=$("#ck"+uid).parent().next().text()
		        itemCheckList[uid]=true;
		    }else{
		        itemCheckList[uid]=false;
		    }
		    addCheckInfo();
		}
		
		function addCheckInfo(){
			var num=0;
			for(var i in itemCheckList) {
				if(itemCheckList[i]==true)
					num++;
			}
			$("#dataCheckInfo").text(num);
		}
		
		function getCheckNum(){
			var num=0;
			for(var i in itemCheckList) {
				if(itemCheckList[i]==true)
					num++;
			}
			return "<span id='dataCheckInfo'>" + num + "</span>";
		}
		
		function addCallTimes(uuid) {
			$.post(getContext()+'userdata/addCallTimes', {taskUuid:uuid}, function(data) {
				if(data.success){
					$.smallBox({
						title : "操作成功",
						content : "<i class='fa fa-clock-o'></i> <i>操作成功...</i>",
						color : "#659265",
						iconSmall : "fa fa-check fa-2x fadeInRight animated",
						timeout : 2000
					});
// 					itemCheckList[uuid]=false;
					$('#dataTable').DataTable().ajax.reload(null,false);;
				}else{
					$.smallBox({
						title : "操作失败",
						content : "<i class='fa fa-clock-o'></i> <i>操作失败...</i>",
						color : "#C46A69",
						iconSmall : "fa fa-times fa-2x fadeInRight animated",
						timeout : 2000
					});
				}
				$('#dataTable').DataTable().ajax.reload(null,false);;
			}, "json");
		}
		
		function callPhone(uuid, isAddZero) {
// 			if(currentCallStat==1)
// 				return;
			$.post(getContext()+'userdata/callpop', {taskUuid:uuid,zero:isAddZero}, function(data) {
				if(data.success){
					
// 					var openWindow = window.open('_blank');
// 					openWindow.location = "${springMacroRequestContext.contextPath}/addWindow?detailq="+data.entry.call_phone+"&&call_session_uuid="+ data.call_session_uuid;
					
// 					window.open("${springMacroRequestContext.contextPath}/addWindow?detailq="+data.entry.call_phone+"&&call_session_uuid="+ data.call_session_uuid);
					
				}else{
					$.smallBox({
						title : "操作失败",
						content : "<i class='fa fa-clock-o'></i> <i>操作失败...</i>",
						color : "#C46A69",
						iconSmall : "fa fa-times fa-2x fadeInRight animated",
						timeout : 2000
					});
				}
				$('#dataTable').DataTable().ajax.reload(null,false);;
			}, "json");
		}
		
		function saveAsCustomer(itemPhone, dataUuid) {
			if(!itemPhone)
				return;
			$.post(getContext()+'cstm/getTags',{phone:itemPhone},function(data){
				cstmTabel.dataTaskUuid = dataUuid;
				var modalDom = $(data);
				modalDom.find("#msubmit").text("保存");
				modalDom.find("#msubmit").next().text("取消");
				$("#userContent").append(modalDom);
			});
		}
		
		var cstmTabel = {
				ajax:{
					reload:function(){
						console.log(Boolean(saveResult));
						console.log(cstmTabel.dataTaskUuid);
						if(Boolean(saveResult) && cstmTabel.dataTaskUuid != ""){
							$.post(getContext()+"userdata/moveToCustomer",{dataUuid:cstmTabel.dataTaskUuid},function(data){
				 				if(data.success){
// 				 					$.smallBox({
// 				 						title : "操作成功",
// 				 						content : "<i class='fa fa-clock-o'></i> <i>操作成功...</i>",
// 				 						color : "#659265",
// 				 						iconSmall : "fa fa-check fa-2x fadeInRight animated",
// 				 						timeout : 2000
// 				 					});
									itemCheckList[cstmTabel.dataTaskUuid]=false;
				 					$('#dataTable').DataTable().ajax.reload(null,false);;
				 				}else{
				 					$.smallBox({
				 						title : "操作失败",
				 						content : "<i class='fa fa-clock-o'></i> <i>移动到客户时失败，请刷新后重新操作...</i>",
				 						color : "#C46A69",
				 						iconSmall : "fa fa-times fa-2x fadeInRight animated",
				 						timeout : 2000
				 					});
				 				}
								cstmTabel.dataTaskUuid = "";
				 				$('#dataTable').DataTable().ajax.reload(null,false);;
							},"json");
						}
					}
				},
				dataTaskUuid:""
			};
		
// 		function callPhone(uuid) {
// 			if(currentCallStat==1)
// 				return;
// 			$.post(getContext()+'userdata/call', {taskUuid:uuid}, function(data) {
// 				if(data.success){
// 					$.smallBox({
// 						title : "操作成功",
// 						content : "<i class='fa fa-clock-o'></i> <i>操作成功...</i>",
// 						color : "#659265",
// 						iconSmall : "fa fa-check fa-2x fadeInRight animated",
// 						timeout : 2000
// 					});
// 					$("#loadStatus tr:eq(3) td:last").html($("#" + uuid).closest("tr").children("td:eq(2)").text());
// 					currentCallStat = 1;
// // 					itemCheckList[uuid]=false;
// 					$('#dataTable').DataTable().ajax.reload(null,false);;
// 				}else{
// 					$.smallBox({
// 						title : "操作失败",
// 						content : "<i class='fa fa-clock-o'></i> <i>操作失败...</i>",
// 						color : "#C46A69",
// 						iconSmall : "fa fa-times fa-2x fadeInRight animated",
// 						timeout : 2000
// 					});
// 				}
// 				$('#dataTable').DataTable().ajax.reload(null,false);;
// 			}, "json");
// 		}
		
		function review${model?cap_first}(uuid){
			$.post(getContext() + "data/project/reviewModal",{"uuid":uuid},function(data){
				$("#userContent").append(data);
			});
		}
		
		function batRevert(){
			$.SmartMessageBox({
				title : "归还",
				content : "该操作将执行归还操作，确定执行？",
				buttons : "[No][Yes]",
				//input : "text",
				//placeholder : "请填写备注:"
			}, function(ButtonPressed, Value) {
				if (ButtonPressed === "Yes") {
					var list = getCheckList();
					if(list.length>0){
					    $.post(getContext()+'data/project/batDataDelete',{uuids:list,taskTable:$('#currentUuid').val()},function(data){
							if(data.success){
								$.smallBox({
									title : "操作成功",
									content : "<i class='fa fa-clock-o'></i> <i>操作成功...</i>",
									color : "#659265",
									iconSmall : "fa fa-check fa-2x fadeInRight animated",
									timeout : 2000
								});
								for(var i in list)
									itemCheckList[list[i]]=false;
// 								$('#dataTable').DataTable().ajax.reload(null,false);;
							}else{
								$.smallBox({
									title : "操作失败",
									content : "<i class='fa fa-clock-o'></i> <i>操作失败...</i>",
									color : "#C46A69",
									iconSmall : "fa fa-times fa-2x fadeInRight animated",
									timeout : 2000
								});
							}
							$('#dataTable').DataTable().ajax.reload(null,false);;
						},"json").error(function(){
							$.smallBox({
								title : "操作失败",
								content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>添加时出现异常...</i>",
								color : "#C46A69",
								iconSmall : "fa fa-times fa-2x fadeInRight animated"
							});
						});
					}
				}
			});
		}
		
		function getMore(uuid) {
			$.post(getContext()+'userdata/getMoreInfo',{'uuid':uuid},function(data){
				$("#userContent").append(data);
			})
		}
		
		function deleteOne(uuid) {
			$.post(getContext()+'userdata/revertOneData',{'dataUuid':uuid,'projectUuid':$("#currentUuid").val()},function(data){
				if(data.success){
					$.smallBox({
						title : "操作成功",
						content : "<i class='fa fa-clock-o'></i> <i>操作成功...</i>",
						color : "#659265",
						iconSmall : "fa fa-check fa-2x fadeInRight animated",
						timeout : 2000
					});
					itemCheckList[uuid]=false;
					$('#dataTable').DataTable().ajax.reload(null,false);;
				}else{
					$.smallBox({
						title : "操作失败",
						content : "<i class='fa fa-clock-o'></i> <i>操作失败...</i>",
						color : "#C46A69",
						iconSmall : "fa fa-times fa-2x fadeInRight animated",
						timeout : 2000
					});
				}
				$('#dataTable').DataTable().ajax.reload(null,false);;
			},"json").error(function(){
				$.smallBox({
					title : "操作失败",
					content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>添加时出现异常...</i>",
					color : "#C46A69",
					iconSmall : "fa fa-times fa-2x fadeInRight animated"
				});
			});
		}
		
		function callQueue(obj){
			$(obj).children().first().toggleClass("btn-primary").toggleClass("btn-danger");
			if($(obj).children().first().is(".btn-primary")) {
				$(obj).children().last().text("依次呼叫选中项");
			}
			else {
				$(obj).children().last().text("停止依次呼叫");
			}
		}
		
		function get${model?cap_first}(){
			$.post("${springMacroRequestContext.contextPath}/userdata/getdata",{"uuid":$("#currentUuid").val(),getData:"1",allocateMax:"${dataNumber}"},function(data){
				if(data.success){
					$.smallBox({
						title : "获取数据成功",
						content : "<i class='fa fa-clock-o'></i> <i>共获取数据" + data.num + "条...</i>",
						color : "#659265",
						iconSmall : "fa fa-check fa-2x fadeInRight animated",
						timeout : 2000
					});
					$('#dataTable').DataTable().ajax.reload(null,false);;
				}else{
					$.smallBox({
						title : "操作失败",
						content : "<i class='fa fa-clock-o'></i> <i>操作失败...</i>",
						color : "#C46A69",
						iconSmall : "fa fa-times fa-2x fadeInRight animated",
						timeout : 2000
					});
				}
				$('#dataTable').DataTable().ajax.reload(null,false);;
			},"json").error(function(){
				$.smallBox({
					title : "操作失败",
					content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>添加时出现异常...</i>",
					color : "#C46A69",
					iconSmall : "fa fa-times fa-2x fadeInRight animated"
				});
			});
		}
		
		function getData(){
			if($('[name="sources"]:checked').length == 0) {
				$('#getData').popover('hide');
				return;
			}
			var sources = [];
			$('[name="sources"]:checked').each(function(k,v){
				sources.push($(v).val());
			})
			$.post("${springMacroRequestContext.contextPath}/userdata/getdata",$.param({"uuid":$("#currentUuid").val(),getData:"1",allocateMax:"${dataNumber}",sources:sources},true),function(data){
				if(data.success){
					$.smallBox({
						title : "获取数据成功",
						content : "<i class='fa fa-clock-o'></i> <i>共获取数据" + data.num + "条...</i>",
						color : "#659265",
						iconSmall : "fa fa-check fa-2x fadeInRight animated",
						timeout : 2000
					});
					$('#getData').popover('hide');
					$('#dataTable').DataTable().ajax.reload(null,false);;
				}else{
					$.smallBox({
						title : "操作失败",
						content : "<i class='fa fa-clock-o'></i> <i>操作失败...</i>",
						color : "#C46A69",
						iconSmall : "fa fa-times fa-2x fadeInRight animated",
						timeout : 2000
					});
				}
				$('#dataTable').DataTable().ajax.reload(null,false);;
			},"json").error(function(){
				$.smallBox({
					title : "操作失败",
					content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>添加时出现异常...</i>",
					color : "#C46A69",
					iconSmall : "fa fa-times fa-2x fadeInRight animated"
				});
			});
			
		}
// 		function get${model?cap_first}(){
// 			$.post(getContext() + "data/project/getData",{"uuid":$("#currentUuid").val()},function(data){
// 				$("#userContent").append(data);
// 			});
// 		}
		
		function getCheckList() {
			var list = [];
			for(var i in itemCheckList) {
				if(itemCheckList[i]==true)
					list.push(i);
			}
			return list;
		}
		
		function startCall(num){
			
			window.open("${springMacroRequestContext.contextPath}/addWindow?detail="+JSON.stringify({'type':'startCall','info':{}})+"");
		}
		
		function batDataDel(){
			$.SmartMessageBox({
				title : "删除",
				content : "该操作将执行删除操作，确定执行？",
				buttons : "[No][Yes]",
				//input : "text",
				//placeholder : "请填写备注:"
			}, function(ButtonPressed, Value) {
				if (ButtonPressed === "Yes") {
					var list = getCheckList();
					if(list.length>0){
					    $.post(getContext()+'userdata/batDataDelete',{uuids:list,taskTable:$('#currentUuid').val()},function(data){
							if(data.success){
								$.smallBox({
									title : "操作成功",
									content : "<i class='fa fa-clock-o'></i> <i>操作成功...</i>",
									color : "#659265",
									iconSmall : "fa fa-check fa-2x fadeInRight animated",
									timeout : 2000
								});
								for(var i in list)
									itemCheckList[list[i]] = false;
								$('#dataTable').DataTable().ajax.reload(null,false);;
							}else{
								$.smallBox({
									title : "操作失败",
									content : "<i class='fa fa-clock-o'></i> <i>操作失败...</i>",
									color : "#C46A69",
									iconSmall : "fa fa-times fa-2x fadeInRight animated",
									timeout : 2000
								});
							}
							$('#dataTable').DataTable().ajax.reload(null,false);;
						},"json").error(function(){
							$.smallBox({
								title : "操作失败",
								content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>添加时出现异常...</i>",
								color : "#C46A69",
								iconSmall : "fa fa-times fa-2x fadeInRight animated"
							});
						});
					}
				}
			});
		}
		
</script>

<!-- <script src="${springMacroRequestContext.contextPath}/public/task/criminal-select.js"></script> -->

<script src="${springMacroRequestContext.contextPath}/public/js/data/user-data.js"></script>
<#macro column titles columns>
	<#list titles[1..] as title>
		{ "title" : "${title}","data" : "${columns[title_index+1]}" ,"orderable" : true}, 
	</#list>
	
</#macro>
	<#macro ajaxData names>
	function(param){
	<#list names as n>
   		param.${n} = $("#${n}").val();
   	</#list>
   	}
</#macro>