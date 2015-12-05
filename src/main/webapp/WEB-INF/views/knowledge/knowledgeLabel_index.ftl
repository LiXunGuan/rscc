	<div id="tmpDiv"></div>
	<div id="mainContent" style="">
	<!-- HEADER -->
		<!-- END RIBBON -->
		<!-- widget grid -->
		<section id="widget-grid" class="">
			<div class="row">
				<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="jarviswidget jarviswidget-color-darken" id="wid-id-0" data-widget-editbutton="false">
						<header>
							<span class="widget-icon"><i class="fa fa-table"></i></span>
							<h2>知识标签</h2>
						</header>
						<div>
							<div class="widget-body no-padding">
								<div class="widget-body-toolbar">
									<div class="smart-form">
										<section>
											<div class="row">
												<label class="label col">标签名</label>
												<label class="input col col-1">
													<input type="text" name="labelName" class="form-control" id="labelName" value="" />
												</label>
												<label class="btn btn-sm btn-primary" onclick="getResult();">查询</label>
												<label class="btn btn-sm btn-primary" onclick="editItem();">添加</label>
											</div>
										</section>
										<table id="dt_basic_knowledgeLabel" class="table table-striped table-bordered table-hover"  data-order='[[ 0, "asc" ]]' width="100%"></table>
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
			
			    $('#dt_basic_knowledgeLabel').dataTable({
		    	"dom" :"t" + "<'dt-toolbar-footer'<'col-sm-6 col-xs-12'i><'col-xs-12 col-sm-6 text-right'lp>>",
				"autoWidth" : true,
				"ordering" : true,
				"serverSide" : true,
				"processing" : true,
				"searching" : false,
				"pageLength" : 5,
				"lengthMenu" : [ 10, 15, 20, 25, 30 ],
			 	"language": {
			         "url" : getContext() + "public/dataTables.cn.txt"
			     },
			    "ajax":{
			    	"url" : getContext() + "knowledge/label/data",
			    	"type":"POST",
			    	"data" :function(param){
			    	}
			    },
			 	"paging" :true,
				"pagingType" :"bootstrap",
				"lengthChange" : true,
				"order" : [[ "0", "desc"]],
				"columns" : [
					   { "title" : "标签名","data" : "labelName"}, 
					   { "title" : "标签描述","data" : "labelRemark"},
					   { 
						   "title" : "操作",
						   "data" : "null",
						   "orderable":false,
						   "render": function(data,type,full){
							 //return "<a onclick='editTag("+full.uid+");'>信息修改</a>&nbsp;&nbsp;&nbsp;&nbsp;";
							   return "<a onclick=editItem('"+full.uid+"');>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;";
						   }
					   }
					],
			});
		});
		
		/* 查询结果 */
		function getResult(){
			$('#dt_basic_knowledgeLabel').DataTable().ajax.reload(null,false);;
		}

		/* 添加/修改 */
		function editItem(uuid){
			$('#tmpDiv').empty();
	        var url = getContext() + "knowledge/label/get";
			$.post(url, {uuid:uuid}, function(data){
				$("#tmpDiv").append(data);
			});
		}
		
		/* 添加标签 */
		function editTag(uuid){
			$("#dialog_cstm_tags").remove();
			$.post(getContext() + 'cstm/getTags',{uuid : uuid},function(data){
				$("#tmpDiv").append(data);
			});
		}
		
	</script>
