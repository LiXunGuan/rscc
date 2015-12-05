

<style>
selected {
	background-color: blue;
}

#addCstmservice {
	float: left;
	margin-bottom: 5px;
}

#des {
	width: 300px;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
	-o-text-overflow: ellipsis;
	-icab-text-overflow: ellipsis;
	-khtml-text-overflow: ellipsis;
	-moz-text-overflow: ellipsis;
	-webkit-text-overflow: ellipsis;
}
</style>
	<div id="tmpDiv"></div>
	<div id="main_cstmservice" style="">
	<!-- HEADER -->
		<!-- END RIBBON -->
		<!-- widget grid -->
		<section id="widget-grid-cstmservice" class="">
			<label class="btn btn-sm btn-primary" id="addCstmservice" style="margin-top: 2px;"  onclick="editCstmServiceInfo()">添加工单</label>
			<div class="row">
				<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				
					<div class="jarviswidget jarviswidget-color-darken" id="wid-id-maincstmservice" data-widget-editbutton="true" data-widget-colorbutton="tarue">

						<header>
							<span class="widget-icon"><i class="fa fa-table"></i></span>
							<#if levels?? && levels=="agent">
								<h2>我的工单信息</h2>
							<#else>
								<h2>工单信息</h2>
							</#if>
						</header>
						
						<div>
							<div class="jarviswidget-editbox">
							</div>
							<div class="widget-body no-padding">
							
								<div class="widget-body-toolbar">
									<div class="smart-form">
										<section>
											<div class="row" style="margin-top: 5px;">
											
												<label class="label col col-1">工单编号</label>
												<label class="input col col-2">
                                      				<input name="cstmserviceId" id="cstmserviceId"  value="">
                                 				</label>
											
												<label class="label col col-1">工单名称</label>
												<label class="input col col-2">
                                      				<input name="cstmserviceName" id="cstmserviceName"  value="">
                                 				</label>
												<label class="label col col-1">工单描述</label>
												<label class="input col col-2">
                                      				<input name="cstmserviceDescription" id="cstmserviceDescription"  value="">
                                 				</label>
												<label class="btn btn-sm btn-primary" id="search" onclick="getResult();">查询</label>
											</div>
										</section>
										<section>
											<div class="row" style="margin-top: 5px;">
												
												<label class="label col col-1">发起对象</label>
												<label class="input col col-2">
                                      				<input name="cstmserviceReporter" id="cstmserviceReporter"  value="">
                                 				</label>
                                 				
												<label class="label col col-1">工单负责人</label>
												<label class="input col col-2">
                                      				<input name="userName" id="userName"  value="">
                                 				</label>
                                 				
												<label class="label col col-1">工单状态</label>
												<div class="form-group col col-lg-2">
													<select name="cstmserviceStatus" id="cstmserviceStatus" class="select2" onchange="getResult();">
														<option value="">----请选择----</option>
														<#list STATUSMAP?keys as key>
															<option value="${key}">${STATUSMAP[key]}</option>
														</#list>
                                      				</select>
                                 				</div>
                                 				<#if levels?? && levels=="agent">
                                 				
                                 				<#else>
                                 				<label class="btn btn-sm btn-success" id="export">导出数据</label>
                                 				</#if>
											</div>
										</section>
										<table id="dt_basic_cstmservice" class="table table-striped table-bordered table-hover" data-order='[[ 0, "asc" ]]' width="100%"></table>
									</div>
								</div>
							</div>
						</div>
				</article>
			</div>
		</section>
	</div>
	
	
	<script type="text/javascript">
	
		var dt_basic_cstmservice = "";
		$(document).ready(function() {
			$("#cstmserviceStatus").select2({
				width:"100%"
			});
			dt_basic_cstmservice = $('#dt_basic_cstmservice').DataTable({
				"dom" :"t" + "<'dt-toolbar-footer'<'col-sm-6 col-xs-12'i><'col-xs-12 col-sm-6 text-right'lp>>",
				"autoWidth" : true,
				"ordering" : true,
				"serverSide" : true,
				"processing" : true,
				"searching" : false,
				"pageLength" : 20,
// 				"lengthMenu" : [ 10, 15, 20, 25, 30 ],
			 	"language": {
			         "url" : getContext() + "public/dataTables.cn.txt"
			     },
			    "ajax":{
			    	"url" : getContext() + "cstmservice/data",
			    	"type":"POST",
			    	"data" :function(param){
						param.cstmserviceDescription=$("#cstmserviceDescription").val();
						param.userName=$("#userName").val();
						param.cstmserviceReporter=$("#cstmserviceReporter").val();
						param.cstmserviceName=$("#cstmserviceName").val();
						param.cstmserviceId=$("#cstmserviceId").val();
						param.cstmserviceStatus=$("#cstmserviceStatus").val();
						param.levels = '${levels!''}';
			    	}
			    },
			 	"paging" :true,
			 	"pagingType" :"bootstrap",
				"lengthChange" : true,
				"order" : [[ "0", "desc"]],
				"columns" : [
					   { "title" : "开始时间","data" : "cstmserviceStartTime","visible":false}, 
					   { "title" : "工单编号","data" : "cstmserviceId","visible":true,"width" : "6%"}, 
					   { "title" : "工单名称","data" : "cstmserviceName","width":"15%","render":function(data,type,full){
						   return "<a onclick=toDetailPage('"+full.uid+"');><div id='des'>"+ full.cstmserviceName +"</div></a>";
					   }}, 
					   { "title" : "工单描述","data" : "null","width":"30%","render":function(data,type,full){
						   return "<div id='des'>"+ full.cstmserviceDescription +"</div>"
					   }},
					   { "title" : "发起对象","data" : "cstmserviceReporter","width":"10%"},
					   { "title" : "工单负责人","data" : "userName","width":"10%"},
// 					   { "title" : "开始时间","data" : "cstmserviceStartTime","visible":true,"width":"15%"}, 
					   { "title" : "工单状态","data" : "cstmserviceStatus","visible":true,"width":"10%"}, 
						   
					   
// 					   { "title" : "工单状态","data" : "cstmserviceStatus"},
// 					   { 
// 						   "title" : "操作",
// 						   "data" : "null",
// 						   "orderable":false,
// 						   "width":"15%",
// 						   "render": function(data,type,full){
// 							   return "<a onclick=getServiceDetail('"+full.uid+"');>详细信息</a>&nbsp;&nbsp;<a onclick=getComment('"+full.uid+"');>评论</a>";
// 						   }
// 					   }
					],
					/*  aData 标示每一行的数据 具体数据 iDataIndex标示行的下标 */
				"createdRow" :function (nRow, aData, iDataIndex) {
// 					 $(nRow).click(function () {
// 						 $.post(getContext() + 'cstmservice/getCstmserviceInfo',{uuid:aData.uid},function(data){
// 							 $("#detail-info").empty();
// 							 $("#detail-info").append(data);
// 						 });
// 						 console.log(nRow);
// 						 console.log($("tr td:first"));
						 
// 					});
				},
			});
		});
		
		/* 修改或者添加客服记录 */
		function editCstmServiceInfo(uuid){
			
			var requestURL = getContext() + "cstmservice/get";
			$("#dialog_cstmservice").remove();			
			$.post(requestURL,{uid:uuid},function(data){
				if(data != null){
					$("#main_cstmservice").append(data);
				}
			});
		}
		
		
		/* 跳转到客户详细页面 */
		function toDetailPage(uid){
			
			$.post(getContext() + 'cstmservice/detailPage',{uid:uid},function(data){
				$("#main_cstmservice").append(data);
			});
			
		}
		
		/* 查看相信信息 */
		function getServiceDetail(uid){
			
			var requestURL = getContext() + 'cstmservice/get';
			$("#dialog_cstmservice").remove();
			$.post(requestURL,{uid :uid},function(data){
				$("#main_cstmservice").append(data);
			});
		}
		
		/* 查看相信信息 */
		function getComment(uid){
			var requestURL = getContext() + 'cstmservice/comments';
			$("#dialog_cstmservice").remove();
			$.post(requestURL,{uid :uid},function(data){
				
				$("#main_cstmservice").append(data);
			});
		}
		/* 查询结果 */
		function getResult(){
			$('#dt_basic_cstmservice').DataTable().ajax.reload(null,false);
		}
		
		/*导出信息*/
		$("#export").click(function(){
			var url = getContext()+"cstmservice/export?";
			
			var cstmserviceId =  $("#cstmserviceId").val();
			var cstmserviceName = $("#cstmserviceName").val();
			var cstmserviceDescription = $("#cstmserviceDescription").val();
			//发起对象
			var cstmserviceReporter = $("#cstmserviceReporter").val();
			//指派对象
			var userName = $("#userName").val();
			var cstmserviceStatus = $("#cstmserviceStatus").val();
			
			if(cstmserviceId!= null && cstmserviceId!=''){url += "&cstmserviceId=" + cstmserviceId ;}
	    	if(cstmserviceName!= null && cstmserviceName!=''){url += "&cstmserviceName=" + cstmserviceName ;}
	    	if(cstmserviceDescription!= null && cstmserviceDescription!=''){url += "&cstmserviceDescription=" + cstmserviceDescription ;}
	    	if(cstmserviceReporter!= null && cstmserviceReporter!=''){url += "&cstmserviceReporter=" + cstmserviceReporter ;}
	    	if(userName!= null && userName!=''){url += "&userName=" + userName ;}
	    	if(cstmserviceStatus!= null && cstmserviceStatus!=''){url += "&cstmserviceStatus=" + cstmserviceStatus ;}
	    	
	    	window.location.href=url;
			
		})
	</script>
