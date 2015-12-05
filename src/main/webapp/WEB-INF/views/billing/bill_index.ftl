<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/calendar/WdatePicker.js"></script>
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
#widget-grid-billing div div.dataTables_info
{
	font-style:normal;
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
	<section id="widget-grid-billing" class="">
		<section>
				<div class="row" style="margin:0;position:relative;">
				<table class="ui-pg-table navtable" id="toolBox">
				<tbody>
				<tr>
<!-- 				<td class="ui-pg-button ui-corner-all" title="" id="batDel" data-original-title="Delete selected row" onclick="batDel();"> -->
<!-- 					<div class="btn btn-sm btn-danger"> -->
<!-- 					<span class="">删除选中项</span> -->
<!-- 					</div> -->
<!-- 				</td> -->
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

				<div class="jarviswidget jarviswidget-color-darken" id="wid-id-0"
					data-widget-editbutton="false">

					<header>
						<span class="widget-icon"><i class="fa fa-table"></i></span>
						<h2>话单</h2>
					</header>
					<div>
						<div class="widget-body no-padding">
							<div class="widget-body-toolbar">
								<div class="smart-form">
									<section class="">
										<div class="row" style="margin:0;">
											<div class="col-md-2">
												<label class="label col col-4">类型</label>
												<label class="select col col-8"> 
													<select class="input-sm" name="type" id="type">
														<option value="">全部</option>
														<option value="0">分机</option>
														<option value="1">技能组</option>
													</select>
												</label>
											</div>
											<div class="col-md-2">
												<label class="label col col-5">呼叫人</label> 
												<label class="input col col-7"> 
													<input type="text" name="caller" id="caller" value="" />
												</label>
											</div>
											<div class="col-md-2">
												<label class="label col col-4">分机</label> 
												<label class="input col col-8"> 
													<input type="text" name="exten" id="exten" value="" />
												</label>
											</div>
<!-- 											<label class="label col col">接入号</label>  -->
<!-- 											<label class="input col col-2">  -->
<!-- 												<input type="text" name="accessNumber" id="accessNumber" value="" /> -->
<!-- 											</label> -->
											<div class="col-md-3">
												<label class="label col col-4">呼叫号码</label> 
												<label class="input col col-8"> 
													<input type="text" name="destNumber" id="destNumber" value="" />
												</label>
											</div>
											<label class="btn btn-sm btn-primary" onclick="getResult();">查找</label>
										</div>
										</section>
										<section>
										<div class="row" style="margin:0;">
											<div class="col-md-4">
												<label class="label col col-2">呼叫时间</label> 
												<label class="input col col-4"> 
													<input type="text" name="startTime" id="startTime" value=""  class="ui_input_text 400w" onclick="WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00',maxDate:'#F{$dp.$D(\'endTime\')}'});" placeholder="开始时间" />
												</label>
												<label class="label col">~</label>
												<label class="input col col-4"> 
													<input type="text" name="endTime" id="endTime" value=""  class="ui_input_text 400w" onclick="WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00',minDate:'#F{$dp.$D(\'startTime\')}'});" placeholder="结束时间" />
												</label>
											</div>
											<div class="col-md-4">
												<label class="label col col-2">时长</label> 
												<label class="input col col-4"> 
													<input type="text" name="callMin" id="callMin" value="" />
												</label>
												<label class="label col">~</label>
												<label class="input col col-4"> 
													<input type="text" name="callMax" id="callMax" value="" />
												</label>
											</div>
											<div class="col-md-4">
												<label class="label col col-2">花费</label> 
												<label class="input col col-4"> 
													<input type="text" name="costMin" id="costMin" value="" />
												</label>
												<label class="label col">~</label>
												<label class="input col col-4"> 
													<input type="text" name="costMax" id="costMax" value="" />
												</label>
											</div>
											<input type="hidden" id="dataUuid">
										</div>
									</section>
									<section>
									</section>
									<table id="tableBill"
										class="table table-striped table-bordered table-hover"
										data-order='[[ 1, "asc" ]]'>
										</table>
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
			
			$('#type').select2({
			    allowClear : true,
			    width:'99%'
			});
			
		    $('#tableBill').DataTable({
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
			    	"url" : getContext() + "${model}/data",
			    	"type":"POST",
			    	"data": function(param) {
						param.type = $("#type").val();
						param.caller = $("#caller").val();
						param.exten = $("#exten").val();
						param.accessNumber = $("#accessNumber").val();
						param.destNumber = $("#destNumber").val();
						param.callMin = $("#callMin").val();
						param.callMax = $("#callMax").val();
						param.costMin = $("#costMin").val();
						param.costMax = $("#costMax").val();
						param.startTime = $("#startTime").val();
						param.endTime = $("#endTime").val();
					}
			    },
			    "infoCallback":function(settings, start, end, max, total, pre){
			    	var obj = $('#tableBill').DataTable().ajax.json();
			    	var statistics = obj==undefined?0:obj.statistics;
			    	return "显示第 " + start + " 至 " + end + " 项结果，共 " + total + " 项。费用共计 " + (statistics.sumcost?statistics.sumcost.toFixed(2):0) + " 元，平均 " + (statistics.avgcost?statistics.avgcost.toFixed(2):0) + " 元。";
			    },
			    "footerCallback": function ( row, data, start, end, display ){
			    	var api = this.api();
			    	$(api.column(5).footer()).html("1234");
			    },
			 	"paging" :true,
// 				"pagingType" :"full_numbers",
				"pagingType" :"bootstrap",
// 				"pagingType" :"listbox",
// 				"pagingType" :"scrolling",
// 				"pagingType" :"extStyle",
				"lengthChange" : true,
				"order" : [[ "4", "desc"]],
				"columns" : [
		             { "title" : "类型","data" : "type" ,"orderable" : true, 
		            	 "render":function(data,type,full){if(full.type=="0")return "分机";else return "技能组";},defaultContent : ""},
		             { "title" : "呼叫人","data" : "caller" ,"orderable" : true, defaultContent : ""},
		             { "title" : "分机","data" : "exten" ,"orderable" : true, defaultContent : ""}, 
		             { "title" : "呼叫号码","data" : "destNumber" ,"orderable" : true, defaultContent : "","render":function(a,d,f){
		            	 return window.parent.hiddenPhone(f.destNumber);
		             }}, 
		             { "title" : "开始时间","data" : "startStamp" ,"orderable" : true, defaultContent : ""}, 
		             { "title" : "结束时间","data" : "endStamp" ,"orderable" : true, defaultContent : ""}, 
		             { "title" : "呼叫时长(秒)","data" : "duration" ,"orderable" : true, defaultContent : ""}, 
		             { "title" : "费率","data" : "rate" ,"orderable" : true, defaultContent : ""}, 
		             { "title" : "花费","data" : "cost" ,"orderable" : true, defaultContent : ""} 
				],
			});

        });
		    
		/* 查询结果 */
		function getResult(){
			$('#tableBill').DataTable().ajax.reload();
		}
		
	</script>
