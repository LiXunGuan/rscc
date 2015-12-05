<style>
#toolBox{
	float:left;
	table-layout:auto;
	margin-bottom:5px;
}
.row [aria-label]{
	vertical-align:middle;
}
.smart-form .toggle input:not(:checked)+i:before
{
	background-color: #FF0000;
}
.progress{
	height: 10px;
	margin: 0px;
}
</style>
<div id="tmpDiv"></div>
<div id="userContent" style="">
	<!-- HEADER -->
	<!-- END RIBBON -->
	<!-- widget grid -->
	<section id="widget-grid-project" class="">
<section>
				<div class="row" style="margin:0">
				<table cellspacing="0" cellpadding="0" border="0" class="ui-pg-table navtable" id="toolBox">
				<tbody>
				<tr>
				<td class="ui-pg-button ui-corner-all" id="add" title="" data-original-title="Add new row" onclick="batRevert();">
					<div class="btn btn-sm btn-primary">
					<span class="">归还选中人员数据</span>
					</div>
				</td>
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

				<div class="jarviswidget jarviswidget-color-darken" id="wid-id-0"
					data-widget-editbutton="false">

					<header>
						<span class="widget-icon"><i class="fa fa-table"></i></span>
						<h2>人员</h2>
					</header>
					<div>
						<div class="widget-body no-padding">
							<div class="widget-body-toolbar">
								<div class="smart-form">
									<section>
										<div class="row" style="margin: 0">
											<label class="label col">人员名</label> 
											<label class="input col col-1"> 
												<input type="text" name="projectName" id="projectName" value="" />
											</label>
											<label class="label col">部门名</label> 
											<label class="input col col-1"> 
												<input type="text" name="department" id="department" value="" />
											</label>
											<label class="label col">数据量</label> 
											<label class="input col col-1"> 
												<input type="text" name="dataMin" id="dataMin" value="" />
											</label>
											<label class="label col">~</label>
											<label class="input col col-1"> 
												<input type="text" name="dataMax" id="dataMax" value="" />
											</label> 
											<label class="label col">完成量</label> 
											<label class="input col col-1"> 
												<input type="text" name="completeMin" id="completeMin" value="" />
											</label>
											<label class="label col">~</label>
											<label class="input col col-1"> 
												<input type="text" name="completeMax" id="completeMax" value="" />
											</label> 
											<label class="btn btn-sm btn-primary" onclick="getResult();">查找</label>
										</div>
									</section>
									<table id="oTable"
										class="table table-striped table-bordered table-hover"
										data-order='[[ 1, "asc" ]]' width="100%"></table>
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
		
		var condition = {};
		
		var currentItem = "";
		
		function removeThis(t,i){
		     $(t).parent().remove();

		    checklist[i]=false;
		    $('#ck'+i).click();
		}

		function docheckall(){

		    var url = getContext()+'data/${model}/checkAll';
		    $.post(url, condition, function(data){
		    	checklist = {};
				$("#checkInfo").text(data.projects.length);
		        for(var i in data.projects){
	                checklist[data.projects[i]]=true;
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
			
			    $('#oTable').DataTable({
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
			    	"url" : getContext() + "data/${model}/data",
			    	"type":"POST",
			    	"data" : function(param) {
						param.projectName = $("#projectName").val();
						param.department = $("#department").val();
						param.dataMin = $("#dataMin").val();
						param.dataMax = $("#dataMax").val();
						param.completeMin = $("#completeMin").val();
						param.completeMax = $("#completeMax").val();
					}
			    },
			    "infoCallback":function(settings, start, end, max, total, pre){
			    	return "显示第 " + start + " 至 " + end + " 项结果，共 " + total + " 项，已选中 " + getCheckNum() + " 项";
			    },
			 	"paging" :true,
				"pagingType" :"bootstrap",
				"lengthChange" : true,
				"order" : [[ "1", "desc"]],
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
								if (checklist[full.uid]) {
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
					   { 
						   "title" : "${titles[0]}",
						   "data" : "${columns[0]}",
						   "render": function(data,type,full){
							   return "<a onclick='review${model?cap_first}(\""+full.uid+"\");'>" + full["${columns[0]}"] +"</a>";
						   }
					   },
					   <@column titles columns />
					   { 
						   "title" : "数据进度",
						   "data" : "null",
						   "render": function(data,type,full){
							   var obj = $('<div><p style="text-align:right;margin-top: -8px;"><span class="txt-color-purple" style="font-size:10px">已完成100%</span></p>' + 
										'<div class="progress progress-striped">' + 
										'<div class="progress-bar bg-color-greenLight" role="progressbar" id="progress' + full.uid + '" style="width: 100%"></div></div></div>');
							   if(full.dataCount==0)
								   return obj.html();
							   else{
								   var percent = ((full.userCount)/(full.dataCount) * 100).toFixed()  + "%";
								   obj.find("p span").text("已完成" + percent);
								   if(full.projectStat=="1")
								      obj.find(".progress").addClass("active");
								   obj.find(".progress-bar").css("width", percent);
								   return obj.html();
							   }
						   },
						   "width" : "15%"
					   },
					   { 
						   "title" : "人员状态",
						   "data" : "projectStat",
						   "width" : "50px",
						   "render": function(data,type,full){
							   var dom = $('<div><span class="onoffswitch" onclick="$(this).find(\'input\').click();"><input type="checkbox" name="onoffswitch" class="onoffswitch-checkbox" id=' + '"' + full.uid + '"' +  ' onchange="changeState(this.id)"><label class="onoffswitch-label" for="myonoffswitch"> <span class="onoffswitch-inner" data-swchon-text="开启" data-swchoff-text="关闭"></span> <span class="onoffswitch-switch"></span> </label> </span><div>');
// 							   var dom = $('<div><label class="toggle"><input type="checkbox" name="checkbox-toggle" id=' + '"' + full.uid + '"' +  ' onchange="changeState(this.id)"><i data-swchon-text="开启" data-swchoff-text="关闭"></i></lable></div>')
							   if(full.projectStat=="1")
								   dom.find("input").attr("checked","checked");
							   return dom.html();
						   }
					   },
					   { 
						   "title" : "操作",
						   "data" : "null",
						   "render": function(data,type,full){
// 							   <a onclick="' + "review${model?cap_first}(\'"+full.dataTable+"\');\">查看数据</a>&nbsp;&nbsp;&nbsp;&nbsp;
							   return "<a onclick='review${model?cap_first}(\""+full.uid+
							   		   "\");'>查看数据</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='dataAllocate(\""+full.uid+
									   "\");'>获取数据</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='dataCollection(\""+full.uid+
									   "\");'>归还数据</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='delete${model?cap_first}(\""+full.uid+
											   "\");'>删除</a>";
						   }
					   }
					],
				});
			    
		});
		
		/* 查询结果 */
		function getResult(){
			condition = {
		    	projectName:$("#projectName").val(),
		    	department:$("#department").val(),
		    	dataMin:$("#dataMin").val(),
		    	dataMax:$("#dataMax").val(),
		    	completeMin:$("#completeMin").val(),
		    	completeMax:$("#completeMax").val()
	    	};
			$("table.dataTable").DataTable().ajax.reload(null,false);;
		}
		
		function batDel(){
			$.SmartMessageBox({
				title : "删除",
				content : "<h1>该操作将执行删除所选人员操作，并归还这些人员的所有数据，且无法找回，确定执行？</h1><h1>如果确定要删除，请在下面输入框中输入\"立即删除\"四个字，再点击Yes按钮</h1>",
				buttons : "[No][Yes]",
				input : "text",
				placeholder : "请输入“立即删除”四个字，确认删除",
				inputValue : ""
			}, function(ButtonPressed, Value) {
				if (ButtonPressed === "Yes" && Value == "立即删除") {
					var list = [];
					for(var i in checklist) {
						if(checklist[i]==true)
							list.push(i);
					}
					if(list.length>0){
					    $.post(getContext()+'data/${model}/batDelete',{uuids:list},function(data){
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
								$("table.dataTable").DataTable().ajax.reload(null,false);;
							}else{
								$.smallBox({
									title : "操作失败",
									content : "<i class='fa fa-clock-o'></i> <i>操作失败...</i>",
									color : "#C46A69",
									iconSmall : "fa fa-times fa-2x fadeInRight animated",
									timeout : 2000
								});
							}
							$("table.dataTable").DataTable().ajax.reload(null,false);;
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
			})
		}
		
		function batRevert(){
			$.SmartMessageBox({
				title : "批量归还",
				content : "该操作将执行归还操作，确定执行？",
				buttons : "[No][Yes]",
				//input : "text",
				//placeholder : "请填写备注:"
			}, function(ButtonPressed, Value) {
				if (ButtonPressed === "Yes") {
					var list = [];
					for(var i in checklist) {
						if(checklist[i]==true)
							list.push(i);
					}
					if(list.length>0){
					    $.post(getContext()+'data/${model}/batRevert',{uuids:list},function(data){
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
							}else{
								$.smallBox({
									title : "操作失败",
									content : "<i class='fa fa-clock-o'></i> <i>操作失败...</i>",
									color : "#C46A69",
									iconSmall : "fa fa-times fa-2x fadeInRight animated",
									timeout : 2000
								});
							}
							$("table.dataTable").DataTable().ajax.reload(null,false);;
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
		
		function update${model?cap_first}(uuid){
			if(uuid!=""){
				$.post(getContext() + "data/${model}/change",{"uuid":uuid},function(data){
					$("#userContent").append(data);
				});
			}
		}
		
		function taskAllocate(uuid){
			if(uuid!=""){
				$.post(getContext() + "data/${model}/taskAllocate",{"uuid":uuid},function(data){
					$("#userContent").append(data);
				});
			}
		}
		
		function taskCollection(uuid){
			if(uuid!=""){
				$.post(getContext() + "data/${model}/taskCollection",{"uuid":uuid},function(data){
					$("#userContent").append(data);
				});
			}
		}
		
		function dataAllocate(uuid){
			if(uuid!=""){
				$.post(getContext() + "data/${model}/getData",{"uuid":uuid},function(data){
					$("#userContent").append(data);
				});
			}
		}
		
		function dataCollection(uuid){
			if(uuid!=""){
				$.post(getContext() + "data/${model}/revertData",{"uuid":uuid},function(data){
					$("#userContent").append(data);
				});
			}
		}
		
		function review${model?cap_first}(uuid){
			$.post(getContext() + "data/${model}/reviewModal",{"uuid":uuid},function(data){
				$("#userContent").append(data);
			});
		}
		
		function delete${model?cap_first}(uuid){
			$.SmartMessageBox({
				title : "删除",
				content : "<h1>该操作将执行删除人员操作，并归还该人员的所有数据，且无法找回，确定执行？</h1><h1>如果确定要删除，请在下面输入框中输入\"立即删除\"四个字，再点击Yes按钮</h1>",
				buttons : "[No][Yes]",
				input : "text",
				placeholder : "请输入“立即删除”四个字，确认删除",
				inputValue : ""
			}, function(ButtonPressed, Value) {
				if (ButtonPressed === "Yes" && Value == "立即删除") {
					$.post(getContext() + "data/${model}/delete",{"uuid":uuid},function(data){
						if(data.success){
							$.smallBox({
								title : "操作成功",
								content : "<i class='fa fa-clock-o'></i> <i>删除成功...</i>",
								color : "#659265",
								iconSmall : "fa fa-check fa-2x fadeInRight animated",
								timeout : 2000
							});
						}else{
							$.smallBox({
								title : "操作失败",
								content : "<i class='fa fa-clock-o'></i> <i>删除失败...</i>",
								color : "#C46A69",
								iconSmall : "fa fa-times fa-2x fadeInRight animated",
								timeout : 2000
							});
						}
						$("table.dataTable").DataTable().ajax.reload(null,false);;
					},"json").error(function(){
						$.smallBox({
							title : "操作失败",
							content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>删除时出现异常...</i>",
							color : "#C46A69",
							iconSmall : "fa fa-times fa-2x fadeInRight animated"
						});
					});
				}
			});
		}
		
		function add${model?cap_first}(){
			$.post(getContext() + "data/${model}/change",function(data){
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
		
		function changeState(obj){
			var checked = $("#" + obj).is(":checked");
			$.post(getContext() + "data/${model}/changeStat",{uuid:obj,stat:checked},function(){
				$("#progress" + obj).parent().toggleClass("active",checked);
			});
		}
		
		var generate = (function() {
			var random;
			return {
				getRandom : function() {
					return random;
				},
				generateRandom : function() {
					random = Math.floor(Math.random() * 10000);
				}
			};
		})();

	</script>
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