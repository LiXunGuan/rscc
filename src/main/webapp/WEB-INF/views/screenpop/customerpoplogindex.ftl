<style>
</style>
	<div id="main_cstmpoplog" style="">
		<section id="widget-grid-cstmservice" class="">
			<label class="btn btn-sm btn-primary" id="addCstmservice" style="margin-top: 2px;"  onclick="editCstmServiceInfo()">添加客服事件</label>
			<div class="row">
				<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				
					<div class="jarviswidget jarviswidget-color-darken" id="wid-id-maincstmservice" data-widget-editbutton="true" data-widget-colorbutton="tarue">

						<header>
							<span class="widget-icon"><i class="fa fa-table"></i></span>
							<h2>客服记录</h2>
						</header>
						
						<div>
							<div class="jarviswidget-editbox">
							</div>
					
							<div class="widget-body no-padding">
							
								<div class="widget-body-toolbar">
									<div class="smart-form">
										<section>
											<div class="row" style="margin-top: 5px;">
											
												<label class="label col col-1">事件编号</label>
												<label class="input col col-2">
                                      				<input name="cstmserviceId" id="cstmserviceId"  value="">
                                 				</label>
											
												<label class="label col col-1">事件名称</label>
												<label class="input col col-2">
                                      				<input name="cstmserviceName" id="cstmserviceName"  value="">
                                 				</label>
												<label class="label col col-1">事件描述</label>
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
                                 				
												<label class="label col col-1">指派对象</label>
												<label class="input col col-2">
                                      				<input name="cstmserviceAssignee" id="cstmserviceAssignee"  value="">
                                 				</label>
												<label class="label col col-1">事件状态</label>
												<div class="form-group col col-lg-2">
													<select name="cstmserviceStatus" id="cstmserviceStatus" class="form-control" onchange="getResult();">
														<option value="">----请选择----</option>
														<#list STATUSMAP?keys as key>
															<option value="${key}">${STATUSMAP[key]}</option>
														</#list>
                                      				</select>
                                 				</div>
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
			dt_basic_cstmservice = $('#dt_basic_cstmservice').DataTable({
				"dom" :"t" + "<'dt-toolbar-footer'<'col-sm-6 col-xs-12'i><'col-xs-12 col-sm-6 text-right'lp>>",
				"autoWidth" : true,
				"ordering" : true,
				"serverSide" : true,
				"processing" : true,
				"searching" : false,
				"pageLength" : 5,
				"lengthMenu" : [ 5, 10, 15, 20],
			 	"language": {
			         "url" : getContext() + "public/dataTables.cn.txt"
			     },
			    "ajax":{
			    	"url" : getContext() + "cstmservice/data",
			    	"type":"POST",
			    	"data" :function(param){
						param.cstmserviceDescription=$("#cstmserviceDescription").val();
						param.cstmserviceAssignee=$("#cstmserviceAssignee").val();
						param.cstmserviceReporter=$("#cstmserviceReporter").val();
						param.cstmserviceName=$("#cstmserviceName").val();
						param.cstmserviceId=$("#cstmserviceId").val();
						param.cstmserviceStatus=$("#cstmserviceStatus").val();
			    	}
			    },
			 	"paging" :true,
			 	"pagingType" :"bootstrap",
				"lengthChange" : true,
				"order" : [[ "0", "desc"]],
				"columns" : [
					   { "title" : "开始时间","data" : "cstmserviceStartTime","visible":false}, 
					   { "title" : "事件编号","data" : "cstmserviceId","visible":true,"width" : "6%"}, 
					   { "title" : "事件名称","data" : "cstmserviceName","width":"15%","render":function(data,type,full){
						   return "<a onclick=toDetailPage('"+full.uid+"');>"+ full.cstmserviceName +"</a>";
					   }}, 
					   { "title" : "事件描述","data" : "null","width":"30%","render":function(data,type,full){
						   return "<div id='des'>"+ full.cstmserviceDescription +"</div>"
					   }},
					   { "title" : "发起对象","data" : "cstmserviceReporter","width":"10%"},
					   { "title" : "事件负责人","data" : "userName","width":"10%"},
// 					   { "title" : "开始时间","data" : "cstmserviceStartTime","visible":true,"width":"15%"}, 
					   { "title" : "事件状态","data" : "cstmserviceStatus","visible":true,"width":"10%"}, 
						   
					   
// 					   { "title" : "事件状态","data" : "cstmserviceStatus"},
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
			$('#dt_basic_cstmservice').DataTable().ajax.reload(null,false);;
		}
		
		
		
		/* 去除号码前面的0 */
		function deleteZero(phoneNumber){
			
//	 		if(phoneNumber != "" /* && phoneNumber.startsWith("0") */){
			if(phoneNumber != "" ){
				if(phoneNumber.substring(0,1)=="0"){
					
					return deleteZero(phoneNumber.substring(1));
				}
			}
			return phoneNumber;
		}
		
	</script>
