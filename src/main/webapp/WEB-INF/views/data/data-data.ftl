<style>
	#toolBox{
		float:left;
		table-layout:auto;
		margin-bottom:5px;
	}
.control-group.form-group.data-name{
	height:25px;
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
#uploadProgress{
	display:none;
	border:none;
}
.progress{
	height: 10px;
	margin: 0px;
}
</style>
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/assets/js/jquery-file-uploader/css/jquery.fileupload.css">
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/assets/css/select2.min.css">
<script src="${springMacroRequestContext.contextPath}/assets/js/jquery-file-uploader/js/vendor/jquery.ui.widget.js"></script>
<script src="${springMacroRequestContext.contextPath}/assets/js/jquery-file-uploader/js/jquery.iframe-transport.js"></script>
<script src="${springMacroRequestContext.contextPath}/assets/js/jquery-file-uploader/js/jquery.fileupload.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/x-editable/x-editable.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/select2/select2.min.js"></script>
<div id="tmpDiv"></div>
<div id="userContent" style="">
	<!-- HEADER -->
	<!-- END RIBBON -->
	<!-- widget grid -->
	<section id="widget-grid-data" class="">
<section>
				<div class="row" style="margin:0;position:relative;">
				<table cellspacing="0" cellpadding="0" border="0" class="ui-pg-table navtable" id="toolBox">
				<tbody>
				<tr>
				<td class="ui-pg-button ui-corner-all" id="add" title="" data-original-title="Add new row" onclick="add${model?cap_first}();">
					<div class="btn btn-sm btn-primary">
					<span class="">上传数据</span>
					</div>
				</td>
				<td class="ui-pg-button ui-state-disabled" style="width:6px;" data-original-title="" title="">
					<span class="ui-separator"></span>
				</td>
				<td class="ui-pg-button ui-corner-all" title="" id="download" data-original-title="Reload Grid" onclick="window.open('public/docs/example.xlsx')">
					<div class="btn btn-sm btn-primary">
					<span class="">下载示例</span>
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

			</section>
		<div class="row">
			<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">

				<div class="jarviswidget jarviswidget-color-darken" id="wid-id-0"
					data-widget-editbutton="false">

					<header>
						<span class="widget-icon"><i class="fa fa-table"></i></span>
						<h2>数据管理</h2>
					</header>
					<div>
						<div class="widget-body no-padding">
							<div class="widget-body-toolbar">
								<div class="smart-form">
									<section>
										<div class="row" style="margin:0;">
											<label class="label col">批次名</label> 
											<label class="input col col-1"> 
												<input type="text" name="containerName" id="containerName" value="" />
											</label>
											<label class="label col">文件名</label> 
											<label class="input col col-1"> 
												<input type="text" name="dataInfo" id="dataInfo" value="" />
											</label>
											<label class="label col">数据量</label> 
											<label class="input col col-1"> 
												<input type="text" name="dataCountMin" id="dataCountMin" value="" />
											</label>
											<label class="label col">~</label>
											<label class="input col col-1"> 
												<input type="text" name="dataCountMax" id="dataCountMax" value="" />
											</label> 
											<label class="label col">分配量</label> 
											<label class="input col col-1"> 
												<input type="text" name="allocateMin" id="allocateMin" value="" />
											</label>
											<label class="label col">~</label>
											<label class="input col col-1"> 
												<input type="text" name="allocateMax" id="allocateMax" value="" />
											</label> 
<!-- 											<label class="label col">去重状态</label>  -->
<!-- 											<label class="select col col-1">  -->
<!-- 												<select class="input-sm" name="distinctFlag" id="distinctFlag"> -->
<!-- 													<option value="">全部</option> -->
<!-- 													<option value="0">否</option> -->
<!-- 													<option value="1">是</option> -->
<!-- 												</select> -->
<!-- 											</label> -->
											<label class="btn btn-sm btn-primary" onclick="getResult();">查找</label>
											<a href="#" id="project" style="visibility:hidden;position:absolute">导入到项目</a>
											<input type="hidden" id="dataUuid">
										</div>
									</section>
									<table id="dataDataTable"
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
	
		var checklist={};
		
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
				$("#checkInfo").text(data.datas.length);
		        for(var i in data.datas){
	                checklist[data.datas[i]]=true;
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
			
			$('#project').on('shown', function() {
 			    $(this).next().find(".editableform-loading").append('<label class="control-label label col" style="padding-top:3px">导入中</label>');
			});
			
			$('#project').editable({
				showbuttons: 'bottom',
				anim: "fast",
				type: 'select2',
				savenochange: true,
				select2: {
					width:'150px',
					placeholder: "选择一个项目"
					},
				url: getContext() + "data/${model}/toProject",
		        pk: 1,
		        source: [
                	<#list projects as p>
                		<#if p_index!=0>,</#if>
		        		{id:"${p.uuid}",text:"${p.projectName}"}
	        		</#list>
		        	],
	        	params: function(params) {
	        	    params.dataUuid = currentDataId;
	        	    params.projectUuid = params.value;
	        	    return params;
	        	},
		        name: 'projectName',
		        title: '请选择要导入的项目',
		        placement: function(tip,element){
		        	$(element).css("top",$("#" + currentDataId).position().top);
		        	$(element).css("left",$("#" + currentDataId).position().left + ($("#" + currentDataId).outerWidth(false)-$(element).outerWidth(false))/2);
		        	return "top";
		        }
		    });
			
			$('#username').on('hidden', function(e, reason) {
			    if(reason === 'save' || reason === 'cancel') {
					$(".select2-offscreen").val(null).trigger("change");
			    } 
			});
			
			var active = $('<div><div style="margin-top: -8px;"><div style="text-align:left;display: inline-block;width: 50%;"><span class="txt-color-purple" style="font-size:10px">数据进度</span></div><div style="text-align:right;display: inline-block;width: 50%;"><span class="txt-color-purple" style="font-size:10px">已完成100%</span></div></div></div>');
			
		    $('#dataDataTable').DataTable({
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
			    	"type": "POST",
			    	"data": function(param) {
						param.containerName = $("#containerName").val();
						param.dataInfo = $("#dataInfo").val();
						param.dataCountMin = $("#dataCountMin").val();
						param.dataCountMax = $("#dataCountMax").val();
						param.allocateMin = $("#allocateMin").val();
						param.allocateMax = $("#allocateMax").val();
						param.distinctFlag = $("#distinctFlag").val();
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
							"data" : null,
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
							   return "<a onclick='review${model?cap_first}(\""+full.dataTable+"\");'>" + full["${columns[0]}"] +"</a>";
						   }
					   },
					   <@column titles columns />
					   { 
						   "title" : "当前活动进度",
						   "data" : null,
						   "render": function(data,type,full){
							   var obj = active.clone().append($('<div class="progress progress-striped">' + 
										'<div class="progress-bar bg-color-blue" role="progressbar" id="progress' + full.uid + '" style="width: 100%"></div></div>'));
							   if(full.progress === "导入完成"){
								   var percent = toPercent(full.allocateCount, full.dataCount);
								   obj.find("div span").first().text("数据进度");
								   obj.find("div span").last().text("已分配" + percent);
								   obj.find(".progress").addClass("");
							   	   obj.find(".progress-bar").css("width",percent);
								   return obj.html();
							   }else if (full.progress === "上传中"){
								   obj.find("div span").first().text("上传进度");
// 								   obj.find("div span").last().text("等待中");
// 								   obj.find(".progress").addClass("active");
// 								   obj.find(".progress-bar").css("width","0%");
								   return obj.html();
							   }else if (full.progress === "等待中"){
								   obj.find("div span").first().text("导入进度");
								   obj.find("div span").last().text("等待中");
// 								   obj.find(".progress").addClass("active");
								   obj.find(".progress-bar").css("width","0%");
								   return obj.html();
							   } else if (full.progress) {
								   obj.find("div span").first().text("导入进度");
								   obj.find("div span").last().text("已完成" + full.progress);
								   obj.find(".progress").addClass("active");
								   obj.find(".progress-bar").css("width",full.progress);
								   return obj.html();
							   }
									   
						   },
						   "width" : "15%"
					   },
					   { 
						   "title" : "操作",
						   "data" : null,
						   "render": function(data,type,full){
							   if(full.progress !== "导入完成")
								   return "";
							   var str = '<a onclick="' + "review${model?cap_first}(\'"+full.dataTable+"\');\">查看数据</a>&nbsp;&nbsp;&nbsp;&nbsp;<a id=" + full.dataTable + ' onclick="' + "import${model?cap_first}(\'"+full.uid+"\');\">分配数据</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='collection${model?cap_first}(\""+full.uid+"\");'>回收数据</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='distinct${model?cap_first}(\""+full.uid+"\");'>去重</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='update${model?cap_first}(\""+full.uid+
							   "\");'>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='delete${model?cap_first}(\""+full.uid+"\");'>删除</a>"
							   return str;
						   }
					   }
					],
			});

        });
		    
		function toPercent(numerator, denominator) {
			if (denominator == 0)
				return "100%";
			return (numerator / denominator * 100).toFixed() + "%";
		}
		

		/* 查询结果 */
		function getResult(){
			condition = {
			    	containerName:$("#containerName").val(),
					dataInfo:$("#dataInfo").val(),
					dataCountMin:$("#dataCountMin").val(),
					dataCountMax:$("#dataCountMax").val(),
					allocateMin:$("#allocateMin").val(),
					allocateMax:$("#allocateMax").val(),
					distinctFlag:$("#distinctFlag").val()
		    	};
			$('#dataDataTable').DataTable().ajax.reload(null,false);;
		}
		
		function batDel(){
			$.SmartMessageBox({
				title : "删除",
				content : "<h1>该操作将删除所选数据已分配的数据，且无法找回，确定执行？</h1><h1>如果确定要删除，请在下面输入框中输入“立即删除”四个字，再点击Yes按钮</h1>",
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
								$('#dataDataTable').DataTable().ajax.reload(null,false);;
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
		
		var progressObj = {};
		
		function add${model?cap_first}(){
			$.post(getContext() + "data/${model}/add",function(data){
				$("#userContent").append(data);
				
				$('#fileupload').fileupload({
			    	dataType: 'json',
			    	url: getContext() + 'data/data/uploader', 
			    	limitMultiFileUploadSize:1024000,
			    	add: function (e, data) {
			    		/*fadein out或者slideup down*/
			    		$("#msubmita").off("click");
			 			$("#msubmita").on("click",function(){
			 				$.post(getContext() + '/data/data/save',$.param({containerName:$("#newDataName").val(),departments:(function(){
			 					var d = $("#dialogData [name='departments']:checked");
			 					var l = [];
			 					d.each(function(k,v){
			 						l.push(v.value);
			 					});
			 					return l;
			 				})()}, true),function(newdata){
			 					if(newdata.success){
		                            $.smallBox({
		                                title : "操作成功",
		                                content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
		                                color : "#659265",
		                                iconSmall : "fa fa-check fa-2x fadeInRight animated",
		                                timeout : 2000
		                            });
		    	 					$("#dataUuid").val(newdata.uuid);
		                            $('#msubmita').attr("disabled",false);
		                            $('#dataDataTable').DataTable().ajax.reload(null,false);;
		                            data.submit();
				 					$('#dialogData').modal("hide");
		                        }
			 					else{
			 						$.smallBox({
			                            title : "操作失败",
			                            content : "<i class='fa fa-clock-o'></i> <i>操作失败，请重试</i>",
			                            color : "#C46A69",
			                            iconSmall : "fa fa-times fa-2x fadeInRight animated",
			                            timeout : 2000
			                        });
			 					}
			 					$('#dialogData').modal("hide");
			 					$('#dataDataTable').DataTable().ajax.reload(null,false);;
			 				},"json");
			 			});
			        },
			        submit: function (e, data) {
			        	data.formData = {uuid: $("#dataUuid").val(),containerName:$("#newDataName").val()};
			        },
			        progressall: function (e, data) {
// 			            var progress = parseInt(data.loaded / data.total * 100, 10) / 2;
			            var progress = parseInt(data.loaded / data.total * 100, 10);
			            $('#progress' + $("#dataUuid").val()).css(
			                'width',progress + '%'
			            );
			            $('#progress' + $("#dataUuid").val()).parent().prev().find("span:first").text("上传进度");
			            $('#progress' + $("#dataUuid").val()).parent().prev().find("span:last").text("已完成" + progress + '%');
			            if(progress == 50) {
// 			            	var current = $('#dataUuid').val();
// 			            	$('#progress' + $("#dataUuid").val()).parent().prev().find("span:first").text("导入进度");
// 				            $('#progress' + $("#dataUuid").val()).parent().prev().find("span:last").text("已完成50%");
// 				            progressObj[$("#dataUuid").val()] = window.setInterval('importProgress($("#dataUuid").val())',200);
			            }
			        },
			        done: function (e, data) {
// 			        	window.clearInterval(progressObj[$("#dataUuid").val()]);
// 			        	console.log(data);
			        	if(!data.result.success) {
				        	$.smallBox({
								title : "操作失败",
								content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>文件格式错误...</i>",
								color : "#C46A69",
								iconSmall : "fa fa-times fa-2x fadeInRight animated"
							});
			        	}
			        	$("#newDataName").val("");
			        	$('#dataDataTable').DataTable().ajax.reload(null,false);;
			        }
			    });
			});
		}	
		
		function importProgress(id){
			var pr = $('#progress' + id);
            var pro = $('#progress' + id).parent().prev().find("span:last");
			var current = parseInt(pro.text().substring(3));
// 			alert(current);
			if(current != 100){
				current++;
				pr.css('width',current + '%');
				pro.text("已完成" + current + "%");
			}
		}
		
		function review${model?cap_first}(table){
			$.post(getContext() + "data/${model}/importModal",{"currentDataTable":table},function(data){
				$("#userContent").append(data);
			});
		}
		
		function update${model?cap_first}(uuid){
			$.post(getContext() + "data/${model}/change",{"uuid":uuid},function(data){
				$("#userContent").append(data);
			});
		}
		
		function distinct${model?cap_first}(uuid) {
			$.SmartMessageBox({
				title : "去重",
				content : "该操作将执行重复数据的删除操作，确定执行？",
				buttons : "[No][Yes]",
				//input : "text",
				//placeholder : "请填写备注:"
			}, function(ButtonPressed, Value) {
				if (ButtonPressed === "Yes") {
					$.post(getContext() + "data/data/distinct",{"uuid":uuid},function(data){
						if(data.success){
							$.smallBox({
								title : "操作成功",
								content : "<i class='fa fa-clock-o'></i> <i>去重成功，与已有客户重复的" + data.count + "条数据已被删除</i>",
								color : "#659265",
								iconSmall : "fa fa-check fa-2x fadeInRight animated",
								timeout : 2000
							});
						}else{
							$.smallBox({
								title : "操作失败",
								content : "<i class='fa fa-clock-o'></i> <i>去重失败，请重试或与管理员联系...</i>",
								color : "#C46A69",
								iconSmall : "fa fa-times fa-2x fadeInRight animated",
								timeout : 2000
							});
						};
					},"json");
				}
			});
		}
		
		function delete${model?cap_first}(uuid){
			$.SmartMessageBox({
				title : "删除",
				content : "<h1>该操作将删除所有已分配的数据且无法找回，确定执行？</h1><h1>如果确定要删除，请在下面输入框中输入\"立即删除\"四个字，再点击Yes按钮</h1>",
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
						$('#dataDataTable').DataTable().ajax.reload(null,false);;
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

		var newPosition;
		function import${model?cap_first}(nowId){
			$.post(getContext() + "data/${model}/allocate",{"containerId":nowId},function(data){
				$("#userContent").append(data);
			});
// 			currentDataId = nowId;
// 			//使用延时是因为不用的时候立刻检测到其他地方的点击事件，于是就关闭了选择窗口
// 			setTimeout(function(){
// 				$("#project").editable("show");
// 			},1);
		}		
		
		function collection${model?cap_first}(nowId){
			$.post(getContext() + "data/${model}/collection",{"containerId":nowId},function(data){
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
	</script>
<#macro searchBox items names>
	<#list items as item>
		<label class="control-label label col col-1">${item}</label>
		<label class="input col col-2">
		<input type="text" name="${names[item_index]}" id="${names[item_index]}" class="form-control" value="" />
		</label>
		<#if (item_index + 1) % 4 == 0>
			</div>
			<div class="row" style="margin:0px;padding:5px 0px">
		</#if>
	</#list>
</#macro>

<#macro column titles columns>
	<#list titles[1..] as title>
		{ "title" : "${title}","data" : "${columns[title_index+1]}" ,"orderable" : true, defaultContent : ""}, 
	</#list>
	
</#macro>
	<#macro ajaxData names>
	function(param){
	<#list names as n>
   		param.${n} = $("#${n}").val();
   	</#list>
   	}
</#macro>