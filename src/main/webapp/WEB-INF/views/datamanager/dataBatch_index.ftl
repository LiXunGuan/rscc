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
#dataDataTable td>a {
	text-decoration:underline;
}
#bot2-Msg1 {
    background-color: crimson;
	border-color: crimson;
	color: white;
}
span.checkbox_false_disable+a>span{
	color:gray;
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
				<td class="ui-pg-button ui-corner-all" title="" id="download" data-original-title="Reload Grid" onclick="window.open('${springMacroRequestContext.contextPath}/public/docs/example.xlsx')">
					<div class="btn btn-sm btn-primary">
					<span class="">下载示例</span>
					</div>
				</td>
				
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
						<h2>批次管理</h2>
					</header>
					<div>
						<div class="widget-body no-padding">
							<div class="widget-body-toolbar">
								<div class="smart-form">
									<section>
										<div class="row" style="margin:0;">
											<label class="label col">批次名</label> 
											<label class="input col col-2"> 
												<input type="text" name="batchName" id="batchName" value="" />
											</label>
											<label class="label col">文件名</label> 
											<label class="input col col-2"> 
												<input type="text" name="fileName" id="fileName" value="" />
											</label>
										
											<label class="btn btn-sm btn-primary" onclick="getDataBatchResult();">查询</label>
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
			
		    var url = getContext()+'databatch/${model}/checkAll';
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
		        checklist[uid]=true;
		        currentItem = uid;
		    }else{
		        checklist[uid]=false;
		    }
		    addCheckInfo();
		}
		
		function getbackDatas(dataBatchUuid){
	        var url = getContext() + "databatch/data/batchCollect";
			$.post(url, {dataBatchUuid:dataBatchUuid}, function(data){
				$("#userContent").append(data);
			});
		}
		
		$(document).ready(function() {
			
			var kk = 0;
			
			// 判断是否存在录音质检按钮权限
			<#if Session.actionMaps?exists>
		   		<#list actionMaps?keys as key>
		   			<#if key == "删除批次">
		   				kk = 1;
		   			</#if>		
		   		</#list>
		   </#if> 
			
			var active = $('<div><div style="margin-top: -8px;"><div style="text-align:left;display: inline-block;width: 50%;"><span class="txt-color-purple" style="font-size:10px">数据进度</span></div><div style="text-align:right;display: inline-block;width: 50%;"><span class="txt-color-purple" style="font-size:10px">已完成100%</span></div></div></div>');
			
		    var dt = $('#dataDataTable').DataTable({
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
			    	"url" : getContext() + "databatch/${model}/data",
			    	"type": "POST",
			    	"data": function(param) {
						param.batchName = $("#batchName").val();
						param.fileName = $("#fileName").val();
						
					}
			    },
			    "infoCallback":function(settings, start, end, max, total, pre){
			    	return "显示第 " + start + "至 " + end + "项结果，共 " + total + " 项";
			    },
			 	"paging" :true,
				"pagingType" :"bootstrap",
				"lengthChange" : true,
				"order" : [[ "6", "desc"]],
				"columns" : [
						{
							"title" : "详细",
							"class":          "details-control",
							"orderable":      false,
							"data":           null,
							"defaultContent": ""
						},
					   { 
						   "title" : "${titles[0]}",
						   "data" : "${columns[0]}",
						   "render": function(data,type,full){
							   return "<a onclick='review${model?cap_first}(\""+full.dataTable+"\");'>" + full["${columns[0]}"] +"</a>";
						   }
					   },
					   <@column titles columns />
					  
					   { "title" : "意向客户量","data" : "null" ,"render":function(data,type,full){
						   return "<a onclick=getintentCount('"+full.uid+"');>"+ full.intentCount +"</a>";
					   }},
					  /* { "title" : "成交客户量","data" : "null" ,"render":function(data,type,full){
						   return "<a onclick=getcustomerCount('"+full.uid+"');>"+ full.customerCount +"</a>";
					   }},*/
					   { "title" : "转共享量","data" : "null" ,"render":function(data,type,full){
						   return "<a onclick=getshareCount('"+full.uid+"');>"+ full.shareCount +"</a>";
					   }},
					   { "title" : "冻结号码量","data" : "null" ,"render":function(data,type,full){
						   return "<a onclick=getfrozenCount('"+full.uid+"');>"+ full.frozenCount +"</a>";
					   }},
					   { "title" : "废号量","data" : "null" ,"render":function(data,type,full){
						   return "<a onclick=getabandonCount('"+full.uid+"');>"+ full.abandonCount +"</a>";
					   }},
					   { "title" : "黑名单号码量","data" :"null" ,"render":function(data,type,full){
						   return "<a onclick=getblacklistCount('"+full.uid+"');>"+ full.blacklistCount +"</a>";
					   }},
					  
					   { "title" : "是否锁定","data" : "null" ,
						   "render": function(data,type,full){
							   if(full.isLock === "1"){
								   return "是";
							   }else{
								   return "否";
							   }
						   }
					   },
					   { 
						   "title" : "当前活动进度",
						   "data" : null,
						   "render": function(data,type,full){
							   var obj = active.clone().append($('<div class="progress progress-striped">' + 
										'<div class="progress-bar bg-color-blue" role="progressbar" id="progress' + full.uid + '" style="width: 100%;background-color:' + getRandomColor() + '"></div></div>'));
							   if(full.progress === "导入完成"){
								   var percent = toPercent(full.ownCount, full.dataCount);
								   obj.find("div span").first().text("数据进度");
								   obj.find("div span").last().text("已领用" + percent);
								   obj.find(".progress").addClass("");
							   	   obj.find(".progress-bar").css("width",percent);
								   return obj.html();
							   }else if (full.progress === "上传中"){
								   obj.find("div span").first().text("上传进度");
							   	   obj.find(".progress-bar").addClass("bg-color-green");
								   return obj.html();
							   }else if (full.progress === "等待中"){
								   obj.find("div span").first().text("导入进度");
								   obj.find("div span").last().text("等待中");
								   obj.find(".progress-bar").css("width","0%");
								   return obj.html();
							   } else if (full.progress) {
								   obj.find("div span").first().text("导入进度");
								   obj.find("div span").last().text("已完成" + full.progress);
								   obj.find(".progress").addClass("active");
								   obj.find(".progress-bar").css("width",full.progress);
							   	   obj.find(".progress-bar").addClass("bg-color-green");
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
							   if(full.uid==='') {
								   return "";
							   }
							   if(full.dataCount == 0){// 如果是空数据
								   return "<a onclick='deleteBatch(\""+full.uid+"\");'>删除</a>";
							   }
							   //getbackDatas(dataBatchUuid)
							   //<a id=" + full.dataTable + ' onclick="' + "import${model?cap_first}(\'"+full.uid+"\');\">分配数据</a>&nbsp;&nbsp;&nbsp;&nbsp;
// 							   str += "&nbsp;&nbsp;&nbsp;<a onclick='getbackDatas(\""+full.uid+"\");'>回收</a>"


// 							   if(full.uploadid == full.loginid || 0 == full.loginid){
							   if(0 == full.loginid || kk == 1){
								   var str = "<a onclick='forceDelete(\""+full.uid+"\");'>强制删除</a>&nbsp;&nbsp;&nbsp;<a onclick='update${model?cap_first}(\""+full.uid+"\");'>修改</a>";
							   }else{
								   var str = "<a onclick='update${model?cap_first}(\""+full.uid+"\");'>修改</a>";
							   }
							   return str;
						   }
					   }
					],
			});

		    var displayDom = $("<div><div><label style='margin-left:50px;width:130px;'><a>批次内数据量: </a></label><span></span></div><div><label style='margin-left:50px;width:130px;'><a>领用的数据量: </a></label><span></span></div><div><label style='margin-left:50px;width:130px;'><a>意向客户量: </a></label><span></span></div><div><label style='margin-left:50px;width:130px;'><a>转共享量: </a></label><span></span></div><div><label style='margin-left:50px;width:130px;'><a>成交客户量: </a></label><span></span></div><div><label style='margin-left:50px;width:130px;'><a>废号量: </a></label><span></span></div><div><label style='margin-left:50px;width:130px;'><a>黑名单号码量: </a></label><span></span></div></div>");
		    function format ( d, rowData ) {
				var tempDom = displayDom.clone();
				var tempA = tempDom.find("a");
				var temp = tempDom.find("span");
				tempA.eq(0).attr("onclick", "reviewData('" + rowData.uid + "')" );
				tempA.eq(1).attr("onclick", "getownCount('" + rowData.uid + "')" );
				tempA.eq(2).attr("onclick", "getintentCount('" + rowData.uid + "')" );
				tempA.eq(3).attr("onclick", "getshareCount('" + rowData.uid + "')" );
				tempA.eq(4).attr("onclick", "getcustomerCount('" + rowData.uid + "')" );
				tempA.eq(5).attr("onclick", "getabandonCount('" + rowData.uid + "')" );
				tempA.eq(6).attr("onclick", "getblacklistCount('" + rowData.uid + "')" );
				temp.eq(0).text(d.dataCount?d.dataCount:0);
				temp.eq(1).text(d.ownCount?d.ownCount:0);
				temp.eq(2).text(d.intentCount?d.intentCount:0);
				temp.eq(3).text(d.shareCount?d.shareCount:0);
				temp.eq(4).text(d.customerCount?d.customerCount:0);
				temp.eq(5).text(d.abandonCount?d.abandonCount:0);
				temp.eq(6).text(d.blacklistCount?d.blacklistCount:0);
				return tempDom.html();
		    }
		    var detailRows = [];
	    	
		    $('#dataDataTable tbody').on( 'click', 'tr td.details-control', function () {
		        var tr = $(this).closest('tr');
		        var row = dt.row( tr );
// 		        var idx = $.inArray( tr.attr('id'), detailRows );
	 
		        if ( row.child.isShown() ) {
		            tr.removeClass( 'shown' );
		            row.child.hide();
	 
		            // Remove from the 'open' array
// 		            detailRows.splice( idx, 1 );
		        }
		        else {
		            var rowData = row.data();
		            $.post(getContext() + "databatch/data/batchStat",{dataBatchUuid:rowData.uid},function(data){
			            row.child(format(data, rowData)).show();
					},"json");
		            row.child("获取信息中...").show();
		            tr.addClass( 'shown' );
	 
		            // Add to the 'open' array
// 		            if ( idx === -1 ) {
// 		                detailRows.push( tr.attr('id') );
// 		            }
		        }
		    } );
	 
		    // On each draw, loop over the `detailRows` array and show any child rows
// 		    dt.on( 'draw', function () {
// 		        $.each( detailRows, function ( i, id ) {
// 		            $('#'+id+' td.details-control').trigger( 'click' );
// 		        } );
// 		    } );
		    
		    
		    
        });
		
		//预览中查看领用的数据
		function getownCount(batchUuid){
			var url = getContext() + "databatch/${model}/importModal";
			 $.post(url,{dataTable:batchUuid,ownDepartment:"notnull"},function(data){
				 $("#userContent").append(data);
			 });
		}
		
		//预览中查看意向客户
		function getintentCount(batchUuid){
			var url = getContext() + "databatch/${model}/importModal";
			 $.post(url,{dataTable:batchUuid,intentType:"1"},function(data){
				 $("#userContent").append(data);
			 });
		}
		//预览中查看成交客户
		function getcustomerCount(batchUuid){
			var url = getContext() + "databatch/${model}/importModal";
			 $.post(url,{dataTable:batchUuid,customerUuid:"1"},function(data){
				 $("#userContent").append(data);
			 });
		}
		//预览中查看转共享池的数据
		function getshareCount(batchUuid){
			
			 var url = getContext() + "databatch/${model}/importModal";
			 $.post(url,{dataTable:batchUuid,ownDepartment:"global_share",operation:"globalShare"},function(data){
				 $("#userContent").append(data);
			 });
		}
		//预览中查看冻结数据
		function getfrozenCount(batchUuid){
			 var url = getContext() + "databatch/${model}/importModal";
			 $.post(url,{dataTable:batchUuid,isFrozen:"isFrozen"},function(data){
				 $("#userContent").append(data);
			 });
		}
		//预览中查看标记废弃数据
		function getabandonCount(batchUuid){
			var url = getContext() + "databatch/${model}/importModal";
			 $.post(url,{dataTable:batchUuid,isAbandon:"isAbandon"},function(data){
				 $("#userContent").append(data);
			 });
		}
		//预览中查看黑名单数据
		function getblacklistCount(batchUuid){
			var url = getContext() + "databatch/${model}/importModal";
			 $.post(url,{dataTable:batchUuid,isBlacklist:"isBlacklist"},function(data){
				 $("#userContent").append(data);
			 });
		}
		
		function toPercent(numerator, denominator) {
			if (denominator == 0)
				return "100%";
			return (numerator / denominator * 100).toFixed() + "%";
		}
		
		/* 查询 公共调用 */
		function getResult(){
			
			getDataBatchResult();
		}
		
		/* 查询结果 */
		function getDataBatchResult(){
			condition = {
			    	batchName:$("#batchName").val(),
					fileName:$("#fileName").val(),
					
		    	};
			$('#dataDataTable').DataTable().ajax.reload(null,false);;
		}
		
		$(function() {
			document.onkeydown = function(e) {
				var ev = document.all ? window.event : e;
				if (ev.keyCode == 13) {
					if(typeof(getDataBatchResult) == 'function')
						getDataBatchResult();
				}
			}
		});
		
		/*分配数据*/
		function import${model?cap_first}(nowId){
			$.post(getContext() + "databatch/${model}/allocate",{"batchId":nowId},function(data){
				$("#userContent").append(data);
			});
		}
		
		/*修改*/	
		function update${model?cap_first}(uuid){
			$.post(getContext() + "databatch/${model}/change",{"uuid":uuid},function(data){
				$("#userContent").append(data);
			});
		}
		
		/*删除*/
		function deleteBatch(uuid){
			$.SmartMessageBox({
		        title : "删除",
		        content : "确定删除该条批次信息吗？",
		        buttons : '[No][Yes]'
		    }, function(ButtonPressed) {
		        if (ButtonPressed === "Yes") {
		            var url = getContext() + "databatch/data/deleteBatch";
		            $.post(url,{uuid:uuid},function(data){
		                if(data.success){
		                    $.smallBox({
		                        title : "操作成功",
		                        content : "<i class='fa fa-clock-o'></i> <i>删除成功...</i>",
		                        color : "#659265",
		                        iconSmall : "fa fa-check fa-2x fadeInRight animated",
		                        timeout : 2000
		                    });
		                    $('#dataDataTable').DataTable().ajax.reload(null,false);;
		                }else{
		                    $.smallBox({
		                        title : "操作失败",
		                        content : "<i class='fa fa-clock-o'></i> <i>删除失败...</i>",
		                        color : "#C46A69",
		                        iconSmall : "fa fa-times fa-2x fadeInRight animated",
		                        timeout : 2000
		                    });
		                }
		            },"json").error(function(){
		                $.smallBox({
		                    title : "操作失败",
		                    content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作失败...</i>",
		                    color : "#C46A69",
		                    iconSmall : "fa fa-times fa-2x fadeInRight animated"
		                });
		            });
		        }
		    });
		}
		
		var progressObj = {};
		
		function add${model?cap_first}(){
			$.post(getContext() + "databatch/${model}/upload",function(data){
				$("#userContent").append(data);
				
				$('#fileupload').fileupload({
			    	dataType: 'json',
			    	url: getContext() + 'databatch/data/uploader', 
			    	limitMultiFileUploadSize:1024000,
			    	add: function (e, data) {
			    		/*fadein out或者slideup down*/
			    		//移除所有msubmita上面的所有click事件
			    		$("#msubmita").off("click");
			 			$("#msubmita").on("click",function(){
			 				if(!valid()) {
			 					return;
			 				}
			 				$.post(getContext() + 'databatch/data/save',$.param({batchName:$("#newDataName").val(),departments:(function(){
			 					var l = [];
			 					
// 			 					var d = $("#dialogData [name='departments']:checked");
// 			 					d.each(function(k,v){
// 			 						l.push(v.value+"-"+$(v).closest("div").next("div").children("[id='singlelimit']").val()+"-"+$(v).closest("div").next("div").children("[id='daylimit']").val());
// 			 					});
			 					
			 					var d = $("#dialogData .adddept");
			 					d.each(function(k,v){
			 						if(k > 0){
				 						l.push(v.id + "-" + $("#"+v.id).children("[id='singlelimit']").val() + "-" + $("#"+v.id).children("[id='daylimit']").val());
			 						};
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
			 				},"json").error(function(){
			 					$.smallBox({
		                            title : "操作失败",
		                            content : "<i class='fa fa-clock-o'></i> <i>操作失败，请重试</i>",
		                            color : "#C46A69",
		                            iconSmall : "fa fa-times fa-2x fadeInRight animated",
		                            timeout : 2000
		                        });
			 					$('#dialogData').modal("hide");
			 				});
			 			});
			        },
			        submit: function (e, data) {
			        	data.formData = {uuid: $("#dataUuid").val(),batchName:$("#newDataName").val()};
			        },
			        progressall: function (e, data) {
			            var progress = parseInt(data.loaded / data.total * 100, 10);
			            $('#progress' + $("#dataUuid").val()).css(
			                'width',progress + '%'
			            );
			            $('#progress' + $("#dataUuid").val()).parent().prev().find("span:first").text("上传进度");
			            $('#progress' + $("#dataUuid").val()).parent().prev().find("span:last").text("已完成" + progress + '%');
			            if(progress == 50) {
			            }
			        },
			        done: function (e, data) {

			        	if(!data.result.success) {
				        	$.smallBox({
								title : "操作失败",
								content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>数据上传失败...</i>",
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
			if(current != 100){
				current++;
				pr.css('width',current + '%');
				pro.text("已完成" + current + "%");
			}
		}
				
		function review${model?cap_first}(dataTable){
			$.post(getContext() + "databatch/${model}/importModal",{"dataTable":dataTable},function(data){
				$("#userContent").append(data);
			});
		}
		
		function getCheckNum(){
			var num=0;
			for(var i in checklist) {
				if(checklist[i]==true)
					num++;
			}
			return "<span id='checkInfo'>" + num + "</span>";
		}
		
		function forceDelete(uuid){
			$.SmartMessageBox({
				title : "删除",
				content : "<h1>该操作将删除所有已分配的数据且无法找回 ！</h1><h1>不能删除的意向和成交客户将会归档到默认批次中</h1><h1>如果确定要删除，请在下面输入框中输入<span style='font-size:xx-large'>\"立即删除\"</span>四个字，再点击Yes按钮</h1>",
				buttons : "[No][Yes]",
				input : "text",
				placeholder : "请输入“立即删除”四个字，确认删除",
				inputValue : ""
			}, function(ButtonPressed, Value) {
				if (ButtonPressed === "Yes" && Value == "立即删除") {
					$.post(getContext() + "databatch/data/forceDeleteBatch",{"uuid":uuid},function(data){
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
								content : "<i class='fa fa-clock-o'></i> <i>删除失败..." + data.message?data.message:"" + "</i>",
								color : "#C46A69",
								iconSmall : "fa fa-times fa-2x fadeInRight animated",
								timeout : 2000
							});
						}
						$('table.dataTable').DataTable().ajax.reload(null,false);
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
		
		
		function getRandomColor(){
			var getRandom0ToFF = function() {
				return Math.round(Math.random() * 256);
			}
			return "rgba(" + getRandom0ToFF() + "," + getRandom0ToFF() + "," + getRandom0ToFF() + "," + Math.random() + ")";
		}
		
		
		function updateProgress(uuid, progress){
			if($("#progress" + uuid).length > 0) {
				var dom = $("#progress" + uuid).parent().prev();
				dom.find("div span").first().text("导入进度");
			    dom.find("div span").last().text("已完成" + progress);
			    dom.find(".progress").addClass("active");
//				    dom.find(".progress-bar").css("width", obj.progress);
				$("#progress" + uuid).css("width",progress);
			}
			if(progress === "100%" && $('#dataDataTable').length>0) {
				$('#dataDataTable').DataTable().ajax.reload(null,false);
			}
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