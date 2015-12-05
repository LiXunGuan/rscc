
<div id="new_tmpDiv"></div>
<div id="new_user_data" style="">
	<section id="widget-grid-cstmservice" class="">
		<div class="row">
			<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<div class="jarviswidget jarviswidget-color-darken"
					id="wid-id-maincstmservice" data-widget-editbutton="true"
					data-widget-colorbutton="tarue">
					<header>
						<span class="widget-icon"><i class="fa fa-table"></i></span>
						<h2>批次${batchName!''}的详细信息</h2>
					</header>
					<div>
						<div class="jarviswidget-editbox"></div>
						<div class="widget-body no-padding">
							<div class="widget-body-toolbar">
								<div class="smart-form">
									<section>
										<input type="hidden" name="batchUuid" id="batchUuid" value="${batchUuid!''}" >
										<!-- 										<div class="row" style="margin-top: 5px;"> -->
										<!-- 											<label class="label col col-1">工单编号</label> <label -->
										<!-- 												class="input col col-2"> <input name="cstmserviceId" -->
										<!-- 												id="cstmserviceId" value=""> -->
										<!-- 											</label> <label class="label col col-1">工单名称</label> <label -->
										<!-- 												class="input col col-2"> <input -->
										<!-- 												name="cstmserviceName" id="cstmserviceName" value=""> -->
										<!-- 											</label> <label class="label col col-1">工单描述</label> <label -->
										<!-- 												class="input col col-2"> <input -->
										<!-- 												name="cstmserviceDescription" id="cstmserviceDescription" -->
										<!-- 												value=""> -->
										<!-- 											</label> <label class="btn btn-sm btn-primary" id="search" -->
										<!-- 												onclick="getResult();">查询</label> -->
										<!-- 										</div> -->
									</section>
									<table id="new_dt_batch_data" class="table table-striped table-bordered table-hover" data-order='[[ 0, "asc" ]]' width="100%"></table>
								</div>
							</div>
						</div>
					</div>
			</article>
		</div>
	</section>
</div>


<script type="text/javascript">
	var new_dt_batch_data = "";
	$(document).ready(function() {
		
		new_dt_batch_data = $('#new_dt_batch_data').DataTable({
			"dom" : "t" + "<'dt-toolbar-footer'<'col-sm-6 col-xs-12'i><'col-xs-12 col-sm-6 text-right'lp>>",
			"autoWidth" : true,
			"ordering" : true,
			"serverSide" : true,
			"processing" : true,
			"searching" : false,
			"pageLength" : 10,
			"lengthMenu" : [ 10, 15, 20, 25, 30 ],
			"language" : {
				"url" : getContext()+ "public/dataTables.cn.txt"
			},
			"ajax" : {
				"url" : getContext() + "newuserdata/data",
				"type" : "POST",
				"data" : function(param) {
					param.batchUuid = '${batchUuid!""}';
					param.detail = '${detail!''}';
				}
			},
			"paging" : true,
			"pagingType" : "bootstrap",
			"lengthChange" : true,
			"order" : [ [ "0", "desc" ] ],
			"columns" : [
					{
						"title" : "电话号码",
						"data" : "phoneNumber",
					},
					{
						"title" : "最后呼叫结果",
						"data" : "lastCallResult",
					},
					{
						"title" : "意向类型",
						"data" : "intentType",
					},
					{
						"title" : "是否锁定",
						"data" : "isLock",
						"visible" : true,
					},
					{
						"title" : "锁定时间",
						"data" : "isAbandon",
						"visible" : true,
					},
					{
						"title" : "是否黑名单",
						"data" : "isBlacklist",
						"visible" : true,
					},
					{
						"title" : "是否冻结",
						"data" : "isFrozen",
						"visible" : true,
					},
					{
						"title" : "操作",
						"data" : "null",
						"render" : function( data, type, full) {
							   return "<a onclick=getDetails('"+full.batchUuid+"','"+ full.batchName +"');>呼叫</a>";
						}
					},
				]	
			});
	});
	
	/* 详情 */
	function getDetails(p,n){
		//将该变量设置为0，该函数执行后的弹屏的tab页将有关闭按钮
		window.parent.closetag = "0";
		window.parent.addTab(n,n,n,"");
	}

	/* 获取数据 */
	function getInfos(p) {
		$.post(getContext() + "newuserdata/toDetailsPage", {
			batchId : p,
			target : "get"
		}, function(d) {

			$("#new_user_data").append(d);
		});
	}

	/* 归还数据 */
	function getBackInfos(p) {

		$.post(getContext() + "newuserdata/toDetailsPage", {
			batchId : p,
			target : "back"
		}, function(d) {

			$("#new_user_data").append(d);
		});
	}
</script>

