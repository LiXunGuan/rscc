	<style>
	#toolBox{
		float:left;
		table-layout:auto;
		margin-bottom:5px;
	}
	.row [aria-label]{
		vertical-align:middle;
	}
	#bot2-Msg1 {
	    background-color: crimson;
		border-color: crimson;
		color: white;
	}
	</style>
	<base href='${springMacroRequestContext.contextPath}/' /> 
	<div id="tmpDiv"></div>
	<div id="userContent" style="">
		<section id="widget-grid-user" class="">
		<section>
				<div class="row" style="margin:0">
				<table cellspacing="0" cellpadding="0" border="0" class="ui-pg-table navtable" id="toolBox">
				<tbody>
				<tr>
				<td class="ui-pg-button ui-corner-all" id="add" title="" data-original-title="Add new row" onclick="add${model?cap_first}();">
					<div class="btn btn-sm btn-primary">
					<span class="">新增${title}</span>
					</div>
				</td>
				<#if model="user">
					<td class="ui-pg-button ui-state-disabled" style="width:6px;" data-original-title="" title="">
						<span class="ui-separator"></span>
					</td>
					<td class="ui-pg-button ui-corner-all" title="" id="batAdd" data-original-title="Delete selected row" onclick="batAdd();">
						<div class="btn btn-sm btn-primary">
						<span class="">批量增加</span>
						</div>
					</td>
					<td class="ui-pg-button ui-corner-all" title="" id="batChange" data-original-title="Delete selected row" onclick="batChange();">
						<div class="btn btn-sm btn-primary" style="margin-left:6px">
						<span class="">批量修改</span>
						</div>
					</td>
				</#if>
				<td class="ui-pg-button ui-state-disabled" style="width:6px;" data-original-title="" title="">
					<span class="ui-separator"></span>
				</td>
				<td class="ui-pg-button ui-corner-all" title="" id="batDel" data-original-title="Delete selected row" onclick="batDel();">
					<div class="btn btn-sm btn-danger">
					<span class="">删除选中项</span>
					</div>
				</td>
				<td class="ui-pg-button ui-state-disabled" style="width:6px;" data-original-title="" title="">
					<span class="ui-separator"></span>
				</td>
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
							<h2>${title}管理</h2>
						</header>
						
						<div>
							<div class="widget-body no-padding">
								<div class="widget-body-toolbar">
									<div class="smart-form">
										
										<section>
											<div class="row" style="margin:0">
												<@searchBox items names />
												<#if model="user">
													<label class="input col col-2">
													<select id="departmentName" name="departmentName" onchange="getSearch()">
						    							<option value="">请选择</option>
						    							<#if depts??>
							    							<#list depts as dept>
																<option value="${dept.datarangeName}">${dept.datarangeName}</option>
															</#list>
						    							</#if>
						    						</select>
												</label>
												</#if>
											</div>
										</section>
										<table id="oTable" class="table table-striped table-bordered table-hover"  data-order='[[ 1, "asc" ]]' width="100%"></table>
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
	
		var checklist={};
		
		var condition = {};
		
		var currentItem = "";
		
		function removeThis(t,i){
		     $(t).parent().remove();

		    checklist[i]=false;
		    $('#ck'+i).click();
		}

		function docheckall(){

		    var url = getContext()+'user/${model}/checkAll';
		    $.post(url, condition, function(data){
		    	checklist = {};
		    	console.log(data.users.length);
				$("#checkInfo").text(data.users.length);
		        for(var i in data.users){
	                checklist[data.users[i]]=true;
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
		    checklist={};
			$("#checkInfo").text(0);				
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
//		        checklist[uid]=$("#ck"+uid).parent().next().text()
		        checklist[uid]=true;
		        currentItem = uid;
		    }else{
		        checklist[uid]=false;
		    }
		    addCheckInfo();
		}
		
		$(document).ready(function() {
			
			   var dt =  $('#oTable').DataTable({
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
			    	"url" : getContext() + "user/${model}/data",
			    	"type":"POST",
			    	"data" :<@c.ajaxData names />
			    },
			    "infoCallback":function(settings, start, end, max, total, pre){
			    	return "显示第 " + start + " 至 " + end + " 项结果，共 " + total + " 项，已选中 " + getCheckNum() + " 项";
			    },
			 	"paging" :true,
				"pagingType" :"bootstrap",
				"lengthChange" : true,
				"order" : [[ "1", "asc"]],
				"columns" : [
						{
							"title" : '<div class="btn-group">'
									+ ' <a class="btn btn-sm btn-primary dropdown-toggle" data-toggle="dropdown" href="javascript:void(0);">选择<span class="caret"></span></a><ul class="dropdown-menu">'
									+ ' <li><a href="javascript:void(0);"onclick="docheckall()" >全选所有</a></li>'
									+ '<li><a href="javascript:void(0);" onclick="docheckallpage()">全选本页</a></li>'
									+ '<li><a href="javascript:void(0);" onclick="docancelAll()">取消所有</a></li>'
									+ '</ul></div>',
							"sortable" : false,
							"data" : "null",
							"render" : function(data, type, full) {
								if ("0"==full.uid){
									return "";
								}else if(checklist[full.uid]) {
									return '<input type="checkbox" checked="checked" id="ck'
											+ full.uid
											+ '" onclick="docheck('
											+ "'" + full.uid + "'" + ')"  />';
								} else {
									return '<input type="checkbox" id="ck'
											+ full.uid
											+ '" onclick="docheck('
											+ "'" + full.uid + "'" + ')" />';
								}
							},
							"width" : "30px"
						},
					   { "title" : "uuid", "data" : "uuid","visible":false},
					   <#if title == "用户">
					   {
							"title" : "关联数据",
							"class":          "details-control",
							"orderable":      false,
							"data":           null,
							"defaultContent": "",
							"width" : "50px"
						},
						</#if>
					   { 
						   "title" : "${titles[0]}",
						   "data" : "${columns[0]}",
						   "render": function(data,type,full){
							   if(full.uid=="0")
								   return full["${columns[0]}"];
							   return "<a onclick='update${model?cap_first}(\""+full.uid+"\");'>" + full["${columns[0]}"] +"</a>";
						   }
					   },
					   <#if model="user">
					 	
					   </#if>
					   <@column titles columns />
					   { 
						   "title" : "操作",
						   "data" : "null",
						   "render": function(data,type,full){
							   if(full.type == "user"){
								   var dom = $("<div><a onclick='update${model?cap_first}(\""+full.uid+"\");'>修改</a>" + "&nbsp;&nbsp;&nbsp;&nbsp;" +
									   "<a onclick='delete${model?cap_first}(\""+full.uid+"\");'>删除</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='resetPwd(\""+full.uid+"\");'>重置密码</a></div>");
							   }else{
								   var dom = $("<div><a onclick='update${model?cap_first}(\""+full.uid+"\");'>修改</a>" + "&nbsp;&nbsp;&nbsp;&nbsp;" +
									   "<a onclick='delete${model?cap_first}(\""+full.uid+"\");'>删除</a></div>");
							   }
								   if(full.uid==0){
									   dom = $("<div>");
								   }else if(full.uid==1){
									   dom.children().first().wrap("<p></p>");
									   dom = dom.children().first();
									   dom.wrap("<p></p>");
								   }
							   return dom.html();
						   }
					   }
					],
			});
			    
			 <#if model="user">
			 	$("#departmentName").parent().remove();
			 	$("#departmentName").select2({
			 		width:"100%"
			 	});
			 </#if>
			 
			 
			    var displayDom = $("<div><div><label style='margin-left:140px;width:130px;'><a>呼叫任务数据量: </a></label><span></span></div><div><label style='margin-left:140px;width:130px;'><a>意向客户量: </a></label><span></span></div><div><label style='margin-left:140px;width:130px;'><a>成交客户量: </a></label><span></span></div>");
				function showData(data,row){
					var tempDom = displayDom.clone();
					var tempA = tempDom.find("a");
					var temp = tempDom.find("span");
					tempA.eq(0).attr("onclick","getUserData('"+row.uid+"','task')").attr("id","task"+row.uid);
					tempA.eq(1).attr("onclick","getUserData('"+row.uid+"','intent')").attr("id","task"+row.uid);
					tempA.eq(2).attr("onclick","getUserData('"+row.uid+"','cstm')").attr("id","task"+row.uid);
					
					temp.eq(0).text(data.taskCount?data.taskCount:0);
					temp.eq(1).text(data.intentCount?data.intentCount:0);
					temp.eq(2).text(data.cstmCount?data.cstmCount:0);
					
					return tempDom.html();
				}
				
				$('#oTable tbody').on( 'click', 'tr td.details-control', function () {
			        var tr = $(this).closest('tr');	
			        var row = dt.row( tr );
		 
			        if ( row.child.isShown() ) {
			            tr.removeClass( 'shown' );
			            row.child.hide();
			        }else {
			            var rowData = row.data();
			            $.post(getContext() + "user/user/getUserData",{userid:rowData.uid},function(data){
				            row.child(showData(data, rowData)).show();
						},"json");
			            row.child("获取信息中...").show();
			            tr.addClass( 'shown' );
			        }
			    } );
		});
		
		//查看任务中的数据
		function getUserData(userId,dataType){
			var url = getContext() + "user/user/getTaskData";
			 $.post(url,{userId:userId,dataType:dataType},function(data){
				 $("#userContent").append(data);
			 });
		}
		
		function getSearch(){
			condition = {
					<#list names as n>
					<#if n_index != 0>,</#if>
			   		${n}:$("#${n}").val()
			   		</#list>
				};
				$('#oTable').DataTable().ajax.reload(null,false);
		}
		/* 查询结果 */
		function getResult(){
			condition = {
				<#list names as n>
				<#if n_index != 0>,</#if>
		   		${n}:$("#${n}").val()
		   		</#list>
			};
			$('#oTable').DataTable().ajax.reload(null,false);
		}
		function addModel(){
			
			$.post(getContext() + "user/${model}/add",function(data){
				$("#userContent").append(data);
			});
			
		}
		
		/*批量删除*/
		function batDel(){
			var list = [];
			for(var i in checklist) {
				if(checklist[i]==true)
					list.push(i);
			}
			$.post(getContext()+'user/${model}/getUserData',{uuids:list},function(data){
               if(data.success){
            	   $.post(getContext()+'user/${model}/getTaskData',{uuids:list},function(data){
            		   $("#userContent").append(data);
            	   });
               }else{
            	   batDeleteTip();
               }
			},"json");
		}
		
		//批量删除的提示
		function batDeleteTip(){
			$.SmartMessageBox({
				title : "删除",
				content : "<h1>" + <#if model="user">"该操作将删除所选用户，并自动回收已分配给这些用户的数据，且无法恢复！<br><h2>注：如果该用户还有未呼叫的任务，则无法删除。<h2>"<#elseif model="permissionrole">"该操作将会删除角色，属于此角色的所有用户将会失去该角色拥有的所有权限，且无法恢复！"<#elseif model="datarange">"该操作将会删除该部门，位于该部门下的所有用户将会被并到默认部门！"<#else>"该操作将执行删除操作，且无法恢复！"</#if> + "</h1><h1>如果确定要删除，请在下面输入框中输入<span style='font-size:xx-large'>\"立即删除\"</span>四个字，再点击Yes按钮</h1>",
				buttons : "[No][Yes]",
				input : "text",
				placeholder : "请输入“立即删除”以确认",
				inputValue : ""
			}, function(ButtonPressed, Value) {
				if (ButtonPressed === "Yes" && Value === "立即删除") {
					var list = [];
					for(var i in checklist) {
						if(checklist[i]==true)
							list.push(i);
					}
					if(list.length>0){
					    $.post(getContext()+'user/${model}/batDelete',{uuids:list},function(data){
							if(data.success){
								$.smallBox({
									title : "操作成功",
									content : "<i class='fa fa-clock-o'></i> <i>操作成功...</i>",
									color : "#659265",
									iconSmall : "fa fa-check fa-2x fadeInRight animated",
									timeout : 2000
								});
								for(var i in list)
									checklist[list[i]]=false;
								$('#oTable').DataTable().ajax.reload(null,false);
							}else{
								$.smallBox({
									title : "操作失败",
									content : "<i class='fa fa-clock-o'></i> <i>操作失败...</i>",
									color : "#C46A69",
									iconSmall : "fa fa-times fa-2x fadeInRight animated",
									timeout : 2000
								});
							}
							$('#dt_basic_select').DataTable().ajax.reload(null,false);;
						},"json").error(function(){
							$.smallBox({
								title : "操作失败",
								content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>删除时出现异常...</i>",
								color : "#C46A69",
								iconSmall : "fa fa-times fa-2x fadeInRight animated"
							});
						});
					}
				}
			});
		}
		
		function update${model?cap_first}(uuid){
			if(uuid!=""){
				$.post(getContext() + "user/${model}/change",{"uuid":uuid},function(data){
					$("#userContent").append(data);
				});
			}
		}
		
		function resetPwd(uuid){
			$.SmartMessageBox({
				title : "确定重置密码？",
				content : "",
				buttons : "[No][Yes]",
			}, function(ButtonPressed, Value) {
				if(ButtonPressed === "Yes") {
					$.post(getContext() + "user/user/resetPwd",{"uuid":uuid},function(data){
						if(data.success){
							$.smallBox({
								title : "重置密码成功",
								content : "<i class='fa fa-clock-o'></i>重置密码成功...",
								color : "#659265",
								iconSmall : "fa fa-check fa-2x fadeInRight animated",
								timeout : 2000
							});
						}else{
							$.smallBox({
								title : "重置密码失败",
								content : "<i class='fa fa-clock-o'></i>重置密码失败...",
								color : "#C46A69",
								iconSmall : "fa fa-times fa-2x fadeInRight animated",
								timeout : 2000
							});
						}
					},"json").error(function(){
						$.smallBox({
							title : "操作失败",
							content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>重置密码时出现异常...</i>",
							color : "#C46A69",
							iconSmall : "fa fa-times fa-2x fadeInRight animated"
						});
					});
				}
				if (ButtonPressed === "No") {
					$.smallBox({
						title : "取消操作",
						content : "<i class='fa fa-clock-o'></i>您取消了该操作！....",
						color : "#C46A69",
						iconSmall : "fa fa-times fa-2x fadeInRight animated",
						timeout : 2000
					});
				}
			});
// 			if(confirm("确定重置密码？")){
// 				$.post(getContext() + "user/user/resetPwd", {"uuid":uuid}, function(data){
// 					if(data.success){
// 						$.smallBox({
// 							title : "重置成功",
// 							content : "<i class='fa fa-clock-o'></i> <i>重置密码成功...</i>",
// 							color : "#659265",
// 							iconSmall : "fa fa-check fa-2x fadeInRight animated",
// 							timeout : 2000
// 						});
// 					}else{
// 						$.smallBox({
// 							title : "重置失败",
// 							content : "<i class='fa fa-clock-o'></i><i>重置密码失败...</i>",
// 							color : "#c79121",
// 							iconSmall : "fa fa-times fa-2x fadeInRight animated",
// 							timeout : 2000
// 						});
// 					}
// 				},"json");
// 			}
		}
		
		/*点击删除按钮*/
		function delete${model?cap_first}(uuid){
			<#if model="user">
				$.post(getContext()+"user/user/getUserData",{userid:uuid},function(data){
						if(data.taskCount !=0 || data.intentCount !=0 || data.cstmCount !=0){
							$.smallBox({
								title : "删除失败",
								content : "该用户名下还存在数据, 请先进行处理后再删除！<br><br>呼叫任务："+data.taskCount+" 条<br>意向客户："+data.intentCount+" 条<br>成交客户："+data.cstmCount+" 条",
								color : "#c79121",
								timeout : 5000,
								icon : "fa fa-bell"
							});
						}else{
				   			deleteTip(uuid);
						}
				},"json");
			<#else>
				deleteTip(uuid);
			</#if>
		}
		
		function deleteTip(uuid){
			$.SmartMessageBox({
				title : "删除",
				content : "<h1>" + <#if model="user">"该操作将删除该用户，并自动回收已分配给其的数据，且无法恢复！"<#elseif model="permissionrole">"该操作将会删除所选角色，属于这些角色的所有用户将会失去该角色拥有的所有权限，且无法恢复！"<#elseif model="datarange">"该操作将会删除所选部门，位于这些部门下的所有用户将会被并到默认部门！"<#else>"该操作将执行删除操作，且无法恢复！"</#if>+"</h1><h1>如果确定要删除，请在下面输入框中输入<span style='font-size:xx-large'>\"立即删除\"</span>四个字，再点击Yes按钮</h1>",
				buttons : "[No][Yes]",
				input : "text",
				placeholder : "请输入“立即删除”以确认",
				inputValue : ""
			}, function(ButtonPressed, Value) {
				if (ButtonPressed === "Yes" && Value === "立即删除") {
					$.post(getContext() + "user/${model}/delete",{"uuid":uuid},function(data){
						if(data.success){
							$.smallBox({
								title : "操作成功",
								content : "<i class='fa fa-clock-o'></i> <i>删除成功...</i>",
								color : "#659265",
								iconSmall : "fa fa-check fa-2x fadeInRight animated",
								timeout : 2000
							});
						}else if(data.revert){
							$.smallBox({
								title : "操作失败",
								content : "<i class='fa fa-clock-o'></i> <i>人员删除成功，但是回收数据失败，请到数据管理下的人员管理中手动删除" + data.user + "的数据...</i>",
								color : "#c79121",
								iconSmall : "fa fa-times fa-2x fadeInRight animated",
								timeout : 2000
							});
						}else{
							$.smallBox({
								title : "操作失败",
								content : "<i class='fa fa-clock-o'></i> <i>删除失败..." + data.message?data.message:"" + "</i>",
								color : "#C46A69",
								iconSmall : "fa fa-times fa-2x fadeInRight animated",
								timeout : 2000
							});
						}
						$('#oTable').DataTable().ajax.reload(null,false);;
					},"json").error(function(){
							$.smallBox({
								title : "操作失败",
								content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>删除时出现异常...</i>",
								color : "#C46A69",
								iconSmall : "fa fa-times fa-2x fadeInRight animated"
							});
						});
				}
				if (ButtonPressed === "No") {
					$.smallBox({
						title : "取消操作",
						content : "<i class='fa fa-clock-o'></i> <i>您取消了该操作</i>",
						color : "#5384AF",
						iconSmall : "fa fa-times fa-2x fadeInRight animated",
						timeout : 2000
					});
				}
			});	
		}
		
		function add${model?cap_first}(){
			$.post(getContext() + "user/${model}/add",function(data){
				$("#userContent").append(data);
			});
		}		
		function addCheckInfo(){
			var num=0;
			for(var i in checklist) {
				if(checklist[i]==true)
					num++;
			}
			$("#checkInfo").text(num);
		}
		
		function getCheckNum(){
			var num=0;
			for(var i in checklist) {
				if(checklist[i]==true)
					num++;
			}
			return "<span id='checkInfo'>" + num + "</span>";
		}
		
		<#if model="user">
		function batAdd(){
			$.post(getContext() + "user/${model}/batAdd",function(data){
				$("#userContent").append(data);
			});
		}
		function batChange(){
			$.post(getContext() + "user/${model}/batChange",function(data){
				$("#userContent").append(data);
			});
		}
		</#if>
		
		//按下回车自动搜索
		$(function() {
			document.onkeydown = function(e) {
				var ev = document.all ? window.event : e;
				if (ev.keyCode == 13) {
					if(typeof(getSearch) == 'function')
						getSearch();
				}
			}
		});
		
	</script>
	
	<!-- 添加换行逻辑 -->
	<#macro searchBox items names>
	<#list items as item>
		<#if (item_index+1)%4 == 0 >
				</div>
			</section>
			<section>
				<div class="row" style="margin:0">
		</#if>
		<#if item_index == 1>
			<label class="btn btn-sm btn-primary" onclick="getSearch();">查找</label>
		</#if>
		<label class="label col">${item}</label>
		<label class="input col col">
		<input type="text" name="${names[item_index]}" id="${names[item_index]}" class="form-control" value="" />
		</label>
	</#list>
</#macro>

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
