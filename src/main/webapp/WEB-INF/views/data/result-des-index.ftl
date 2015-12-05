
<style>
	#addCstm {
		float: right;
		margin-right: 1%;
	}
	#alterTable {
		float: right;
		margin-right: 1%;
	}
</style>

	<div id="tmpDiv"></div>
	<div id="main_cstm_des" style="">
	<!-- HEADER -->
		<!-- END RIBBON -->
		<!-- widget grid -->
		<section id="widget-grid-des" class="">
		
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
												<label class="btn btn-sm btn-primary" id="alterTable" onclick="alterTable()">自定义列</label>
											</div>
										</section>
										
										<table id="dt_basic_cstm_des" class="table table-striped table-bordered table-hover"  data-order='[[ 0, "asc" ]]' width="100%"></table>
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
	
	
		var cstmDesignTable ="";
		$(document).ready(function() {
			
			cstmDesignTable = $('#dt_basic_cstm_des').DataTable({
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
			    	"url" : getContext() + "data/resultDesign/data",
			    	"type":"POST",
			    	"data" :function(param){
			    	}
			    },
			 	"paging" :true,
				"pagingType" :"bootstrap",
				"lengthChange" : true,
				"order" : [[ "1", "desc"]],
				"columns" : [
						{ "title" : "列名","data" : "columnName","defaultContent":""}, 
						{ "title" : "列类型","data" : "columnType","defaultContent":""},
						{ "title" : "默认值","data" : "columnValue","defaultContent":""},
						{ "title" : "列属性","data" : "characterProperty","defaultContent":""},
						{ "title" : "是否可查","data" : "allowSelect","defaultContent":""},
						{ "title" : "是否索引","data" : "allowIndex","defaultContent":""},
						{ "title" : "是否显示","data" : "allowShow","defaultContent":""},
						{ "title" : "显示顺序","data" : "orders","defaultContent":""},
						{ "title" : "操作", "data" : "null", "orderable":false, 
							"render": function(data,type,full){
								if(full.isDefault == "0"){
									return "<a onclick=alterTable('"+full.id+"');>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick=deleteColumn('"+full.id+"');>删除</a>";
								}else{
									return "";
								}
							}
						}
					],
			});
		});
		
		
		/* 查询结果 */
		function getResult(){
			$('#dt_basic_cstm_des').DataTable().ajax.reload(null,false);;
		}
		
		/* 添加弹框 */
		function addModel(){
			
			$.post(getContext() + "index/get",function(data){
				$("#main_cstm_des").append(data);
			});
		}
		
		/* 删除 */
		function deleteColumn(uuid){
			
			var requestUrl = getContext() + "data/resultDesign/delete"; 
			$.SmartMessageBox({
				title : "删除",
				content : "该操作将执行删除操作，确定执行？",
				buttons : "[No][Yes]",
			}, function(ButtonPressed) {
				if (ButtonPressed === "Yes") {
					$.post(requestUrl,{uuid:uuid},function(data){
						if(data.success){
							$.smallBox({
								title : "操作成功",
								content : "<i class='fa fa-clock-o'></i> <i>删除成功...</i>",
								color : "#659265",
								iconSmall : "fa fa-check fa-2x fadeInRight animated",
								timeout : 2000
							});
						cstmDesignTable.ajax.reload(null,false);;
						}else{
							$.smallBox({
								title : "Callback function",
								content : "<i class='fa fa-clock-o'></i> <i>You pressed No...</i>",
								color : "#C46A69",
								iconSmall : "fa fa-times fa-2x fadeInRight animated",
								timeout : 2000
							});
						}
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
						title : "取消",
						content : "<i class='fa fa-clock-o'></i> <i>操作已经被取消...</i>",
						color : "#659265",
						iconSmall : "fa fa-times fa-2x fadeInRight animated"
					});
				}
			});
			cstmDesignTable.ajax.reload(null,false);;
		}
		
		/* 编辑列 */
		function alterTable(){
			$("#dialog_cstm_edit_column").remove();
			$.post(getContext() + "data/resultDesign/edit",function(data){
				$("#main_cstm_des").append(data);
			});
		}
		
	</script>
