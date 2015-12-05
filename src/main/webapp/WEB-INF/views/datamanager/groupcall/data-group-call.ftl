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
	<section id="widget-grid-group" class="">
<section>
				<div class="row" style="margin:0">
				<table cellspacing="0" cellpadding="0" border="0" class="ui-pg-table navtable" id="toolBox">
				<tbody>
				<tr>
				<td class="ui-pg-button ui-corner-all" id="add" title="" data-original-title="Add new row" onclick="addTask();">
					<div class="btn btn-sm btn-primary">
					<span class="">新建群呼任务</span>
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
						<h2>群呼</h2>
					</header>
					<div>
						<div class="widget-body no-padding">
							<div class="widget-body-toolbar">
								<div class="smart-form">
									<section>
										<div class="row" style="margin: 0">
											<label class="label col">描述</label> 
											<label class="input col col-1"> 
												<input type="text" name="description" id="description" value="" />
											</label>
											<label class="label col">群呼策略</label> 
											<label class="select col col-1"> 
												<select class="input-sm" name="strategy" id="strategy">
													<option value="">全部</option>
													<option value="0">静态群呼</option>
													<option value="1">动态群呼</option>
												</select>
											</label>
											<label class="label col">外呼号码</label> 
											<label class="input col col-1"> 
												<input type="text" name="caller_id_num" id="caller_id_num" value="" />
											</label>
											<label class="label col">转接队列</label> 
											<label class="input col col-1"> 
												<input type="text" name="dst_exten" id="dst_exten" value="" />
											</label>
											<label class="btn btn-sm btn-primary" onclick="getResult();">查找</label>
										</div>
									</section>
									<table id="dt-group-call"
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

		    var url = getContext()+'newdata/groupCall/checkAll';
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
			
			    var dt = $('#dt-group-call').DataTable({
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
			    	"url" : getContext() + "newdata/groupCall/data",
			    	"type":"POST",
			    	"data" : function(param) {
			    		param.description = $("#description").val();
						param.strategy = $("#strategy").val();
						param.caller_id_num = $("#caller_id_num").val();
						param.dst_exten = $("#dst_exten").val();
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
								if (checklist[full.groupcall_id]) {
									return '<input type="checkbox" checked="checked" id="ck'
											+ full.groupcall_id
											+ '" onclick="docheck('
											+ "'" + full.groupcall_id + "'" + ')"  />';
								} else {
									return '<input type="checkbox" id="ck'
											+ full.groupcall_id
											+ '" onclick="docheck('
											+ "'" + full.groupcall_id + "'" + ')" />';
								}
							},
							"width" : "30px"
						},
// 					   { 
// 						   "title" : "任务ID",
// 						   "data" : "groupcall_id",
// 						   "render": function(data,type,full){
// 							   return "<a onclick='review${model?cap_first}(\""+full.groupcall_id+"\");'>" + full["groupcall_id"] +"</a>";
// 						   }
// 					   },
					   { "title":"任务描述","data":"description","orderable":true,"defaultContent":"无"},
// 					   { "title":"群呼策略","data":"strategy","orderable":true,"defaultContent":"无"},
					   { "title":"群呼策略","data":"strategy","orderable":true,"defaultContent":"无","render":function(data,type,full){
					   		if(full.strategy == "static")
					   			return "静态群呼";
					   		else if(full.strategy == "dynamic")
					   			return "动态群呼";
					   }},
					   { "title":"动态比率","data":"ratio","orderable":true,"defaultContent":"无","render":function(data,type,full){
					   		if(full.strategy == "static")
					   			return "无";
					   		else if(full.strategy == "dynamic")
					   			return full.ratio;
					   }},
// 					   { "title":"使用网关","data":"gateway","orderable":true,"defaultContent":"无"},
					   { "title":"外呼号码","data":"caller_id_num","orderable":true,"defaultContent":"无"},
					   { "title":"转接队列","data":"dst_exten","orderable":true,"defaultContent":"无"},
// 					   { "title":"数据来源","data":"data_src_url","orderable":true,"defaultContent":"无"},
// 					   { "title":"结果送达","data":"data_dst_url","orderable":true,"defaultContent":"无"},
					   { 
						   "title" : "任务状态",
						   "data" : null,
						   "width" : "50px",
						   "render": function(data,type,full){
							   var dom = $('<div><span class="onoffswitch" onclick="$(this).find(\'input\').click();"><input type="checkbox" name="onoffswitch" class="onoffswitch-checkbox" id=' + '"' + full.uid + '"' +  ' onchange="changeState(this.id)"><label class="onoffswitch-label" for="myonoffswitch"> <span class="onoffswitch-inner" data-swchon-text="开始" data-swchoff-text="停止"></span> <span class="onoffswitch-switch"></span> </label> </span><div>');
// 							   var dom = $('<div><label class="toggle"><input type="checkbox" name="checkbox-toggle" id=' + '"' + full.uid + '"' +  ' onchange="changeState(this.id)"><i data-swchon-text="开启" data-swchoff-text="关闭"></i></lable></div>')
							   if(full.stat=="1")
								   dom.find("input").attr("checked","checked");
							   else if(full.stat=="2")
								   dom.find("input").attr("disabled","disabled");
							   return dom.html();
						   }
					   },
					   { "title":"数据总量","data":"dataCount","orderable":true,"defaultContent":"无"},
					   { "title":"实时并发数","data":"concurrency","orderable":true,"defaultContent":"无","render":function(data,type,full){
					   		if(full.strategy == "static")
					   			return full.concurrency;
					   		else if(full.strategy == "dynamic" && full.stat == "0") {
					   			return "无";
					   		}
				   			return full.concurrency;
					   }},
					   { "title":"排队数","data":"queueCount","orderable":false,"defaultContent":"无"},
					   { "title":"接听中/坐席总数","data":"queueStat","orderable":false,"defaultContent":"无"},
// 						{
// 						    "title" : "详细",
// 			                "class":          "details-control",
// 			                "orderable":      false,
// 			                "data":           null,
// 			                "defaultContent": ""
// 		            	},
					   { 
						   "title" : "操作",
						   "data" : null,
						   "render": function(data,type,full){
							   var change;
							   if (full.stat == undefined || full.stat == "0") {
								   change = "&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='update${model?cap_first}(\""+full.uid+"\");'>修改任务</a>";
							   } else {
								   change = "";
							   }
							   //&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='dataCollection(\""+full.groupcall_id+
							   //	   "\");'>移除数据</a>
// 							   <a onclick="' + "review${model?cap_first}(\'"+full.dataTable+"\');\">查看数据</a>&nbsp;&nbsp;&nbsp;&nbsp;
							   return "<a onclick='review${model?cap_first}(\""+full.groupcall_id+
							   		   "\");'>查看结果</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='dataAllocate(\""+full.groupcall_id+
									   "\");'>修改数据</a>" + change + "&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='delete${model?cap_first}(\""+full.uid+
											   "\");'>删除</a>";
						   }
					   }
					],
				});
			    
// 			    function format ( d , id) {
// 			    	if(!d.not_get) {
// 			    		return "暂无数据";
// 			    	}
// 			    	var totalData = parseInt(d.not_get, 10)+parseInt(d.geted, 10)+parseInt(d.called, 10)+parseInt(d.answered, 10)+
// 			        		parseInt(d.single_answered, 10)+parseInt(d.errored, 10);
// 			    	var calledData = parseInt(d.called, 10)+parseInt(d.answered, 10)+parseInt(d.single_answered, 10);
// 			    	console.log(totalData, calledData);
// 			        return '数据总量: '+totalData+'<br>'+
// 			        	'双方接通量: '+d.answered+'，呼通率：' + (calledData==0?0:((d.answered/calledData*100).toFixed())) +'% <br>'+
// 			            '单方接通量: '+d.single_answered+'，呼损率：' + (calledData==0?0:((d.single_answered/calledData*100).toFixed())) +
// 			            '% <button type="button" class="btn btn-info btn-xs" onclick="resetData(' + id + ',0)">重置该数据</button><br>'+
// 			            '未接通量: '+d.called+'，未接通率：' + (calledData==0?0:((d.called/calledData*100).toFixed())) +
// 			            '% <button type="button" class="btn btn-info btn-xs" onclick="resetData(' + id + ',1)">重置该数据</button><br>';
// 			    }
// 			    var detailRows = [];
			    
// 			    $('#dt-group-call tbody').on( 'click', 'tr td.details-control', function () {
// 			        var tr = $(this).closest('tr');
// 			        var row = dt.row( tr );
// 			        var idx = $.inArray( tr.attr('id'), detailRows );
			 
// 			        if ( row.child.isShown() ) {
// 			            tr.removeClass( 'details' );
// 			            row.child.hide();
			 
// 			            // Remove from the 'open' array
// 			            detailRows.splice( idx, 1 );
// 			        }
// 			        else {
// 			            tr.addClass( 'details' );
// 			            row.child( format( row.data().dataStat, row.data().groupcall_id ) ).show();
			 
// 			            // Add to the 'open' array
// 			            if ( idx === -1 ) {
// 			                detailRows.push( tr.attr('id') );
// 			            }
// 			        }
// 			    } );
			 
// 			    // On each draw, loop over the `detailRows` array and show any child rows
// 			    dt.on( 'draw', function () {
// 			        $.each( detailRows, function ( i, id ) {
// 			            $('#'+id+' td.details-control').trigger( 'click' );
// 			        } );
// 			    } );
			    
			    
			    $("select").select2({
			    	width:"100%"
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
			$('#dt-group-call').DataTable().ajax.reload(null,false);;
		}
		
		//0为重置单方未呼通数据
		//1为重置双方未呼通数据
		function resetData(groupcallId, type) {
			$.post(getContext()+'newdata/groupCall/resetData',{groupcallId:groupcallId,type:type},function(data){
				if(data.success){
					$.smallBox({
						title : "操作成功",
						content : "<i class='fa fa-clock-o'></i> <i>操作成功...</i>",
						color : "#659265",
						iconSmall : "fa fa-check fa-2x fadeInRight animated",
						timeout : 2000
					});
				}else{
					$.smallBox({
						title : "操作失败",
						content : "<i class='fa fa-clock-o'></i> <i>操作失败...</i>",
						color : "#C46A69",
						iconSmall : "fa fa-times fa-2x fadeInRight animated",
						timeout : 2000
					});
				}
				$('table.dataTable').DataTable().ajax.reload(null,false);;
			},"json");
		}
		
		function addTask(){
			$.post(getContext() + "newdata/groupCall/add", function(data){
				$("#userContent").append(data);
			});
		}
		
		function batDel(){
			$.SmartMessageBox({
				title : "删除",
				content : "该操作将执行删除操作，确定执行？",
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
					    $.post(getContext()+'newdata/groupCall/batDelete',{uuids:list},function(data){
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
								$('#dt-group-call').DataTable().ajax.reload(null,false);;
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
					    $.post(getContext()+'newdata/groupCall/batRevert',{uuids:list},function(data){
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
							$('#dt-group-call').DataTable().ajax.reload(null,false);;
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
				$.post(getContext() + "newdata/groupCall/change",{"uuid":uuid},function(data){
					$("#userContent").append(data);
				});
			}
		}
		
		function taskAllocate(uuid){
			if(uuid!=""){
				$.post(getContext() + "newdata/groupCall/taskAllocate",{"uuid":uuid},function(data){
					$("#userContent").append(data);
				});
			}
		}
		
		function taskCollection(uuid){
			if(uuid!=""){
				$.post(getContext() + "newdata/groupCall/taskCollection",{"uuid":uuid},function(data){
					$("#userContent").append(data);
				});
			}
		}
		
		function dataAllocate(uuid){
			if(uuid!=""){
				$.post(getContext() + "newdata/groupCall/getData",{"uuid":uuid},function(data){
					$("#userContent").append(data);
				});
			}
		}
		
		function dataCollection(uuid){
			if(uuid!=""){
				$.post(getContext() + "newdata/groupCall/removeData",{"uuid":uuid},function(data){
					$("#userContent").append(data);
				});
			}
		}
		
		function review${model?cap_first}(uuid){
			$.post(getContext() + "newdata/groupCall/reviewModal",{"uuid":uuid},function(data){
				$("#userContent").append(data);
			});
		}
		
		function delete${model?cap_first}(uuid){
			$.SmartMessageBox({
				title : "删除",
				content : "该操作将执行删除操作，确定执行？",
				buttons : "[No][Yes]",
				//input : "text",
				//placeholder : "请填写备注:"
			}, function(ButtonPressed, Value) {
				if (ButtonPressed === "Yes") {
					$.post(getContext() + "newdata/groupCall/delete",{"uuid":uuid},function(data){
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
						$('#dt-group-call').DataTable().ajax.reload(null,false);;
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
						color : "#C46A69",
						iconSmall : "fa fa-times fa-2x fadeInRight animated",
						timeout : 2000
					});
				}
			});
		}
		
		function add${model?cap_first}(){
			$.post(getContext() + "newdata/groupCall/a",function(data){
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
			$("#" + obj).attr("disabled","disabled");
			$.post(getContext() + "newdata/groupCall/changeStat",{uuid:obj,stat:checked},function(data){
				if(data.success){
					if(data.stat){
						$("#" + obj).removeAttr("disabled");
					}
				}
// 				$("#" + obj).removeAttr("disabled");
// 				$("#progress" + obj).parent().toggleClass("active",checked);
			},"json");
		}
		
		var dt_group_call = setInterval(intervalFunction, 10000);

		function intervalFunction(){
			if($('#dt-group-call').length > 0) {
				$('#dt-group-call').DataTable().ajax.reload(null,false);
			}
			else {
				clearInterval(dt_group_call);
			}
		}
		
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