<script src="${springMacroRequestContext.contextPath}/assets/js/adddate.js"></script>
<script src="${springMacroRequestContext.contextPath}/assets/js/calendar/WdatePicker.js"></script>
	<div id="reminderDiv"></div>
	<div id="mainContent" style="">
	<!-- HEADER -->
		<!-- END RIBBON -->
		<!-- widget grid -->
		<section id="widget-grid-reminder" class="">
			<div class="row">
				<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="jarviswidget ${c.global_color}" data-widget-editbutton="false">
						<label class="btn btn-sm btn-primary" onclick="editItem();" style="margin-bottom: 5px;">&nbsp;添&nbsp;&nbsp;&nbsp;加&nbsp;</label>
						<header>
							<span class="widget-icon"><i class="fa fa-table"></i></span>
							<h2>${agent!''}</h2>
						</header>
						<div>
							<div class="widget-body no-padding">
								<div class="widget-body-toolbar">
									<div class="smart-form">
										<section>
											<div class="row" style="margin-top: 8px;">
												<label class="label col">创建时间</label> 
												<label class="input col col-2">
													<input id="createtimeS" name="createtimeS" style="height: 35px;" value="${((createtimeS)?string('yyyy-MM-dd HH:mm:ss'))!''}" type="text" 
														class="ui_input_text 400w" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" placeholder="创建时间" /> 
												</label>
												<label class="label col">~</label> 
												<label class="input col col-2">
													<input id="createtimeE" name="createtimeE" style="height: 35px;" value="${((createtimeE)?string('yyyy-MM-dd HH:mm:ss'))!''}" type="text" 
														class="ui_input_text 400w" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" placeholder="创建时间" /> 
												</label>
												<label class="label col">提醒时间</label> 
												<label class="input col col-2">
													<input id="remindtimeS" name="remindtimeS" style="height: 35px;" value="${((remindtimeS)?string('yyyy-MM-dd HH:mm:ss'))!''}" type="text" 
														class="ui_input_text 400w" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" placeholder="提醒时间" /> 
												</label>
												<label class="label col">~</label> 
												<label class="input col col-2">
													<input id="remindtimeE" name="remindtimeE" style="height: 35px;" value="${((remindtimeE)?string('yyyy-MM-dd HH:mm:ss'))!''}" type="text" 
														class="ui_input_text 400w" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" placeholder="提醒时间" /> 
												</label>
												<label class="btn btn-sm btn-primary" onclick="getResult();">&nbsp;查&nbsp;&nbsp;&nbsp;询&nbsp;</label>
											</div>
											<div class="row" style="margin-top: 5px;">
												<label class="label col">日程内容</label>
												<label class="input col col-2">
													<input type="text" name="content" class="form-control" id="contents" value="" placeholder="日程内容" />
												</label>
											</div>
										</section>
										<table id="dt_basic_schedule" class="table table-striped table-bordered table-hover"  data-order='[[ 0, "asc" ]]' width="100%"></table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</article>
			</div>
		</section>
	</div>
	<script src="${springMacroRequestContext.contextPath}/assets/js/sockjs/sockjs.js"></script>
	<script type="text/javascript">
	
		$(document).ready(function() {
			
			initTable();
		});
		
		function initTable(){
			oTable = $('#dt_basic_schedule').dataTable({
		    	"dom" :"t" + "<'dt-toolbar-footer'<'col-sm-6 col-xs-12'i><'col-xs-12 col-sm-6 text-right'lp>>",
				"autoWidth" : true,
				"ordering" : true,
				"serverSide" : true,
				"processing" : true,
				"searching" : false,
				"pageLength" : '${pageLength!"5"}',
				"lengthMenu" : [5, 10, 15, 20, 25, 30 ],
			 	"language": {
			         "url" : getContext() + "public/dataTables.cn.txt"
			     },
			    "ajax":{
			    	"url" : getContext() + "schedulereminder/data",
			    	"type":"POST",
			    	"data" :function(param){
			    		param.content = $("#contents").val();
			    		param.startimeS =$("#remindtimeS").val();
			    		param.startimeE =$("#remindtimeE").val();
			    		param.createtimeS = $("#createtimeS").val();
			    		param.createtimeE = $("#createtimeE").val();
			    		param.level = $("#special").val();
			    		param.phone1 = '${phone1!""}';
			    		param.phone2 = '${phone2!""}';
			    	}
			    },
			 	"paging" :true,
				"pagingType" :"bootstrap",
				"lengthChange" : true,
				"order" : [[ "0", "desc"]],
				"columns" : [
					   { "title" : "预约对象", "data" : "cstmName", "defaultContent":"无"},
					   {"title" : "电话号码",  "data" : "null","defaultContent":"无","render":function(a,s,d){
						   if(d.phoneNumber != null && d.phoneNumber != undefined && d.phoneNumber != ""){
							   return "<font color='red'>" + window.parent.hiddenPhone(d.phoneNumber) + "</font>";
						   }else{
							   return "无";
						   }
						}},
					   { "title" : "内容", "data" : "null","render":function(data,type,full){
						   return "<a onclick=editItem('"+full.uid+"','clickContent');>"+ full.scheduleContent +"</a>";
					   }}, 
					   { "title" : "提醒时间", "data" : "scheduleTime"},
					   { "title" : "提醒", "data" : "scheduleRemind"},
					   { "title" : "重复", "data" : "scheduleRepeat"},
					   { "title" : "创建人", "data" : "scheduleUser"}, 
					   { "title" : "创建时间", "data" : "scheduleCreateTime"},
					   { 
						   "title" : "操作",
						   "data" : "null",
						   "orderable":false,
						   "render": function(data,type,full){
// 							   return "&nbsp;&nbsp;<a onclick=deleteItem('"+full.uid+"');>删除";
							   return "<a onclick=editItem('"+full.uid+"','update');>修改</a>&nbsp;&nbsp;<a onclick=deleteItem('"+full.uid+"');>删除";
						   }
					   }
					],
			});
		}
		
		/* 查询结果 */
		function getResult(){
			
			$('#dt_basic_schedule').DataTable().ajax.reload(null,false);
		}

		/* 添加   */
		function editItem(uuid,operation){
			$('#reminderDiv').empty();
	        var url = getContext() + "schedulereminder/get";
			$.post(url, {uuid:uuid,operation:operation,phone1:'${phone1!""}'}, function(data){
				$("#reminderDiv").append(data);
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
		            var url = getContext()+"schedulereminder/remove";
		            $.post(url,{uuid:uuid},function(data){
		                if(data.success){
		                    $.smallBox({
		                        title : "操作成功",
		                        content : "<i class='fa fa-clock-o'></i> <i>删除成功...</i>",
		                        color : "#659265",
		                        iconSmall : "fa fa-check fa-2x fadeInRight animated",
		                        timeout : 2000
		                    });
		                    $('#dt_basic_schedule').DataTable().ajax.reload(null,false);;
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
	