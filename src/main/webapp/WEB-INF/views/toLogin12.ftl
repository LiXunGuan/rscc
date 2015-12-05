<link rel="stylesheet" type="text/css" href="${springMacroRequestContext.contextPath}/assets/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="${springMacroRequestContext.contextPath}/assets/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="${springMacroRequestContext.contextPath}/assets/css/smartadmin-production-plugins.min.css"/>
<link rel="stylesheet" type="text/css" href="${springMacroRequestContext.contextPath}/assets/css/smartadmin-production.min.css"/>
<link rel="stylesheet" type="text/css" href="${springMacroRequestContext.contextPath}/assets/css/smartadmin-rtl.min.css"/>
<link rel="stylesheet" type="text/css" href="${springMacroRequestContext.contextPath}/assets/css/uniqueCss.css"/>

	<div id="mainContent" style="width: 99%;padding-top: 10px;">
	<!-- HEADER -->
		<!-- END RIBBON -->
		<!-- widget grid -->
		<section id="widget-grid" class="">
		
			<div class="row">
				<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">

					<div class="jarviswidget jarviswidget-color-darken" id="wid-id-0" data-widget-editbutton="false">

						<header>
							<span class="widget-icon"><i class="fa fa-table"></i></span>
							<h2>Standard Data Tables</h2>
						</header>
						
						<div>
							<div class="widget-body no-padding">
								<div class="widget-body-toolbar">
									<div class="smart-form">
										<section>
											<div class="row">
												<label class="label col">人员姓名</label>
												<label class="input col col-2">
													<input type="text" name="name" class="form-control" id="name" value="" />
												</label>
												<label class="label col">电话号码</label>
												<label class="input col col-2">
													<input type="text" name="phone" id="phone" class="form-control" value="" />
												</label>
												<label class="btn btn-sm btn-primary" onclick="getResult();">查询</label>
											</div>
										</section>
										<table id="dt_basic" class="table table-striped table-bordered table-hover"  data-order='[[ 0, "asc" ]]' width="100%"></table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</article>
			</div>
		</section>
	</div>

<script src="${springMacroRequestContext.contextPath}/assets/js/libs/jquery-2.1.1.min.js"></script>
<script src="${springMacroRequestContext.contextPath}/assets/js/jquery.min.js"></script>
<script src="${springMacroRequestContext.contextPath}/assets/js/jquery-ui.min.js"></script>
<script src="${springMacroRequestContext.contextPath}/assets/js/libs/jquery-ui-1.10.3.min.js"></script>
<script src="${springMacroRequestContext.contextPath}/assets/js/bootstrap/bootstrap.min.js"></script>
<script src="${springMacroRequestContext.contextPath}/assets/js/plugin/jquery-validate/jquery.validate.min.js"></script>
<script src="${springMacroRequestContext.contextPath}/assets/js/plugin/datatables/jquery.dataTables.min.js"></script>
<script src="${springMacroRequestContext.contextPath}/assets/js/plugin/datatables/dataTables.colVis.min.js"></script>
<script src="${springMacroRequestContext.contextPath}/assets/js/plugin/datatables/dataTables.tableTools.min.js"></script>
<script src="${springMacroRequestContext.contextPath}/assets/js/plugin/datatables/dataTables.bootstrap.min.js"></script>

	<script type="text/javascript">
	
		$(document).ready(function() {
			
			    $('#dt_basic').DataTable({
				"dom" :"t" + "<'dt-toolbar-footer'<'col-sm-6 col-xs-12'i><'col-xs-12 col-sm-6 text-right'lp>>",
				"autoWidth" : true,
				"ordering" : true,
				"serverSide" : true,
				"processing" : true,
				"searching" : false,
				"pageLength" : 10,
				"lengthMenu" : [ 10, 15, 20, 25, 30 ],
			 	"language": {
			         url : "/rs/public/dataTables.cn.txt"
			     },
			    "ajax":{
			    	"url" :getContext() + "data",
			    	"type":"POST",
			    	"data" :function(d){
			    		d.name = $("#name").val();
			    		d.phone = $("#phone").val();
			    	}
			    },
			 	"paging" :true,
				"pagingType" :"bootstrap",
				"lengthChange" : true,
				"order" : [[ "0", "desc"]],
				"columns" : [
					   { "title" : "人员姓名","data" : "name" ,"orderable" : true}, 
					   { "title" : "人员编号","data" : "phone","orderable" : true},
					   { 
						   "title" : "操作",
						   "data" : "null",
						   "render": function(data,type,full){
							   return "<a onclick='addModel("+full.uid+");'>查看</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='lookCriminalInfo("+full.uid+");'>查看</a>";
						   }
					   }
					],
			});
		});
		
		/* 查询结果 */
		function getResult(){
			$('#dt_basic').DataTable().draw();
		}

		/* 添加弹框 */
		function addModel(){
			
			$.post("index/get",function(data){
				$("#mainContent").append(data);
			});
			
		}
		
	</script>
