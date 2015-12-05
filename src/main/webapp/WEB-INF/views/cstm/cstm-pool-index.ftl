


<style>
	#addCstmpool {
		float: left;
		margin-bottom: 5px;
	}
	
	#pool_des{
	    width:300px;
	    height:18px;
	    overflow: hidden;
	    text-overflow: ellipsis;
	    white-space: nowrap;
	}
</style>

	<div id="tmpDiv"></div>
	<div id="main_pool" style="">
	<!-- HEADER -->
		<!-- END RIBBON -->
		<!-- widget grid -->
		<section id="widget-grid-pool" class="">
		<label class="btn btn-sm btn-primary" id="addCstmpool" onclick="editPool(null)">添加客户池</label>
			<div class="row">
				<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">

					<div class="jarviswidget jarviswidget-color-darken" id="wid-id-mainpool" data-widget-colorbutton="false">

						<header>
							<span class="widget-icon"><i class="fa fa-table"></i></span>
							<h2>客户池信息管理</h2>
						</header>
						
						<div>
						<div class="jarviswidget-editbox">
						</div>
							<div class="widget-body no-padding">
								<div class="widget-body-toolbar">
									<div class="smart-form">
										<section>
											<div class="row" style="margin-top: 5px;">
											
												<label class="label col">客户池名称</label>
												<label class="input col col-1">
                                      				<input name="poolName" id="poolName"  value="">
                                 				</label>
                                 				
												<label class="label col">创建者</label>
												<label class="input col col-1">
                                      				<input name="poolCreater" id="poolCreater"  value="">
                                 				</label>
											
												<label class="btn btn-sm btn-primary" id="search" onclick="getResult();">查询</label>
											</div>
										</section>
										
										<table id="dt_basic_pool" class="table table-striped table-bordered table-hover"  data-order='[[ 0, "asc" ]]' width="100%"></table>
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
			    $('#dt_basic_pool').DataTable({
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
			    	"url" : getContext() + "cstmpool/data",
			    	"type":"POST",
			    	"data" :function(param){
			    		param.poolName = $("#poolName").val();
			    		param.poolCreater = $("#poolCreater").val();
			    	}
			    },
			 	"paging" :true,
				"pagingType" :"bootstrap",
				"lengthChange" : true,
				"order" : [[ "0", "desc"]],
				"columns" : [
					   { "title" : "客户池名称","data" : "poolName","render":function(data,type,full){
						   return "<a onclick=showAllCustomers('"+full.uid+"');>"+ full.poolName +"</a>";
					   }}, 
					   { "title" : "客户池描述","data" : "null","render":function(d,t,f){
						   return '<label id="pool_des">'+f.poolDes+'</label>'
					   }},
					   { "title" : "创建者","data" : "poolCreater"},
					   { "title" : "创建时间","data" : "createTime"},
					   { 
						   "title" : "操作",
						   "data" : "null",
						   "orderable":false,
						   "render": function(data,type,full){
							   return "<a onclick=editPool('"+full.uid+"');>信息修改</a>&nbsp;&nbsp;&nbsp;&nbsp;";
						   }
					   }
					],
			});
		});
		
		
		
		/* 查询结果 */
		function getResult(){
			$('#dt_basic_pool').DataTable().ajax.reload(null,false);;
		}
		
		/* 添加弹框 */
		function addModel(){
			
			$.post(getContext() + "index/get",function(data){
				$("#main_pool").append(data);
			});
		}
		
		/* 添加修改客户信息 标签 */
		function editPool(uuid){
			
			$("#dialog_cstm_tags").remove();
			$.post(getContext() + "cstmpool/get",{uid : uuid},function(data){
				$("#main_pool").append(data);
			});
		}
		
		
		/* 跳转客户管理处 显示所有客户信息 */
		function showAllCustomers(uuid){
			current_pool_id = uuid;
			window.parent.addTab('客户管理','cstmtoAdminCstmIndex');
		}

	</script>
