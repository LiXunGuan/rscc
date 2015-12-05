	<script src="${springMacroRequestContext.contextPath}/assets/js/adddate.js"></script>
	<script src="${springMacroRequestContext.contextPath}/assets/js/calendar/WdatePicker.js"></script>
	<div id="agentnoticeindexDiv"></div>
	<div id="mainContent" style="">
	<!-- HEADER -->
		<!-- END RIBBON -->
		<!-- widget grid -->
		<section id="widget-grid-agentnoticeindex" class="">
			<div class="row">
				<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="jarviswidget jarviswidget-color-darken" id="wid-id-0" data-widget-editbutton="false">
						<label class="btn btn-sm btn-primary" onclick="editItem();" style="margin-bottom: 5px;">&nbsp;添&nbsp;&nbsp;&nbsp;加&nbsp;</label>
						<header>
							<span class="widget-icon"><i class="fa fa-table"></i></span>
							<h2>公告管理</h2>
						</header>
						<div>
							<div class="widget-body no-padding">
								<div class="widget-body-toolbar">
									<div class="smart-form">
										<section>
											<div class="row" style="margin-top: 5px;">
												
												<label class="label col">公告标题  /内容</label>
												<label class="input col col-2">
													<input type="text" name="title" class="form-control" id="title" value="" placeholder="公告标题  /内容"/>
												</label>
												
												<label class="label col">发布状态</label>
<!-- 												<div class="form-group col col-lg-2"> -->
<!-- 						    						<select id="publishStatus" name="publishStatus" class="form-control" onchange="getResult();"> -->
<!-- 						    							<option value="">---请选择---</option> -->
<!-- 						    							<#list publishs?keys as key> -->
<!-- 															<#if publishStatus??>  -->
<!-- 																<#if key == publishStatus> -->
<!-- 																	<option value="${key}" selected="selected">${publishs[key]}</option> -->
<!-- 																<#else> -->
<!-- 																	<option value="${key}">${publishs[key]}</option></#if> -->
<!-- 															<#else> -->
<!-- 																<option value="${key}">${publishs[key]}</option></#if> -->
<!-- 														</#list> -->
<!-- 						    						</select> -->
<!-- 					    						</div> -->
					    						
					    						<label class="select col col-2">
						    						<select id="publishStatus" name="publishStatus" onchange="getResult();">
						    							<option value="">---请选择---</option>
						    							<#list publishs?keys as key>
															<#if publishStatus??> 
																<#if key == publishStatus>
																	<option value="${key}" selected="selected">${publishs[key]}</option>
																<#else>
																	<option value="${key}">${publishs[key]}</option></#if>
															<#else>
																<option value="${key}">${publishs[key]}</option></#if>
														</#list>
						    						</select>
												</label>
					    						
					    						<label class="label col">创建时间</label >
					    						<label class="input col col-2">
													<input id="sCreateTime" name="sCreateTime" style="height:35px;" value="${((sCreateTime)?string('yyyy-MM-dd HH:mm:ss'))!''}" type="text" class="ui_input_text 400w" onclick= "WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" placeholder="创建时间" />
												</label>
												<label class="btn btn-sm btn-primary" onclick="getResult();">&nbsp;查&nbsp;&nbsp;&nbsp;询&nbsp;</label>
											</div>
										</section>
										<table id="dt_basic_agentnotice" class="table table-striped table-bordered table-hover"  data-order='[[ 0, "asc" ]]' width="100%"></table>
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
	
		$(document).ready(function() {
			
			initTable();
			
			$('#publishStatus').select2({
	           allowClear : true,
	           width: '99%'
	        });
			
		});
		
		function initTable(){
			oTable = $('#dt_basic_agentnotice').dataTable({
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
			    	"url" : getContext() + "agentnotice/data",
			    	"type":"POST",
			    	"data" :function(param){
			    		param.title= $("#title").val();
			    		param.publishStatus= $("#publishStatus").val();
			    		param.sCreateTime= $("#sCreateTime").val();
			    		param.operation="manager";
			    	}
			    },
			 	"paging" :true,
				"pagingType" :"bootstrap",
				"lengthChange" : true,
				"order" : [[ "0", "desc"]],
				"columns" : [
					   { "title" : "标题", "data" : "null","render":function(data,type,full){
						   return "<a onclick=editItem('"+full.uid+"','look');>"+ full.noticeTitle +"</a>";
					   }}, 
// 					   { "title" : "内容", "data" : "noticeContent"},
// 					   { "title" : "公告时间", "data" : "publishTime"},
					   { "title" : "是否已发布", "data" : "publishStatus"},
// 					   { "title" : "发布人", "data" : "publishUser"},
					   { "title" : "创建人", "data" : "createUser"}, 
					   { "title" : "创建时间", "data" : "createTime"},
					   { 
						   "title" : "操作",
						   "data" : "null",
						   "orderable":false,
						   "render": function(data,type,full){
							   if(full.publishState == '0'){
							   	  return "<a onclick=addItem('"+full.uid+"');>添加人员</a>&nbsp;&nbsp;<a onclick=publishItem('"+full.uid+"');>发布</a>&nbsp;&nbsp;<a onclick=editItem('"+full.uid+"','update');>修改</a>&nbsp;&nbsp;<a onclick=deleteItem('"+full.uid+"');>删除";
							   }else{
								   return "<a onclick=addItem('"+full.uid+"');>添加人员</a>&nbsp;&nbsp;<a onclick=unpublishItem('"+full.uid+"');>取消发布</a>&nbsp;&nbsp;<a onclick=editItem('"+full.uid+"','update');>修改</a>&nbsp;&nbsp;<a onclick=deleteItem('"+full.uid+"');>删除"; 
							   }
						   }
					   }
					],
			});
		}
		
		/* 查询结果 */
		function getResult(){
			$('#dt_basic_agentnotice').DataTable().ajax.reload();
		}

		/* 添加   */
		function editItem(uuid, operation){
			$('#agentnoticeindexDiv').empty();
	        var url = getContext() + "agentnotice/get";
			$.post(url, {uuid:uuid,operation:operation}, function(data){
				$("#agentnoticeindexDiv").append(data);
			});
		}
		
		/* 添加人员 */
		function addItem(uuid) {
	        $('#agentnoticeindexDiv').empty();
	        var url = getContext() + "agentnotice/addItem";
	        $.post(url, {uuid:uuid}, function(data) {
	            $("#agentnoticeindexDiv").append(data);
	        });
	    }
		
		/* 发布公告 */
		function publishItem(uuid){
			 $.SmartMessageBox({
			        title : "发布公告",
			        content : "确定立即发布这条公告信息吗？",
			        buttons : '[No][Yes]'
			    }, function(ButtonPressed) {
			        if (ButtonPressed === "Yes") {
			            var url = getContext()+"agentnotice/publishAgentNotice";
			            $.post(url,{agentNoticeUUid:uuid},function(data){
			                if(data.success){
			                    $.smallBox({
			                        title : "操作成功",
			                        content : "<i class='fa fa-clock-o'></i> <i>发布成功...</i>",
			                        color : "#659265",
			                        iconSmall : "fa fa-check fa-2x fadeInRight animated",
			                        timeout : 2000
			                    });
			                    $('#dt_basic_agentnotice').DataTable().ajax.reload(null,false);;
			                }else{
			                    $.smallBox({
			                        title : "发布提醒",
			                        content : "<i class='fa fa-clock-o'></i> <i>" + data.errorMessage + "</i>",
			                        color : "#C46A69",
			                        iconSmall : "fa fa-times fa-2x fadeInRight animated",
			                        timeout : 3000
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
		
		/* 取消发布公告 */
		function unpublishItem(uuid){
			 $.SmartMessageBox({
			        title : "取消发布公告",
			        content : "确定取消发布这条公告信息吗？",
			        buttons : '[No][Yes]'
			    }, function(ButtonPressed) {
			        if (ButtonPressed === "Yes") {
			            var url = getContext()+"agentnotice/unpublishAgentNotice";
			            $.post(url,{agentNoticeUUid:uuid},function(data){
			                if(data.success){
			                    $.smallBox({
			                        title : "操作成功",
			                        content : "<i class='fa fa-clock-o'></i> <i>取消成功...</i>",
			                        color : "#659265",
			                        iconSmall : "fa fa-check fa-2x fadeInRight animated",
			                        timeout : 2000
			                    });
			                    $('#dt_basic_agentnotice').DataTable().ajax.reload(null,false);;
			                }else{
			                    $.smallBox({
			                        title : "操作失败",
			                        content : "<i class='fa fa-clock-o'></i> <i>取消失败...</i>",
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
		
		/* 删除   */
		function deleteItem(uuid){
		    $.SmartMessageBox({
		        title : "删除",
		        content : "确定删除该条信息吗？",
		        buttons : '[No][Yes]'
		    }, function(ButtonPressed) {
		        if (ButtonPressed === "Yes") {
		            var url = getContext()+"agentnotice/remove";
		            $.post(url,{uuid:uuid},function(data){
		                if(data.success){
		                    $.smallBox({
		                        title : "操作成功",
		                        content : "<i class='fa fa-clock-o'></i> <i>删除成功...</i>",
		                        color : "#659265",
		                        iconSmall : "fa fa-check fa-2x fadeInRight animated",
		                        timeout : 2000
		                    });
		                    $('#dt_basic_agentnotice').DataTable().ajax.reload(null,false);;
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
		};
		
	</script>
