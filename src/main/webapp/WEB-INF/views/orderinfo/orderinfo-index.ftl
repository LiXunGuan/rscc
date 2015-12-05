
<link rel="stylesheet" type="text/css" media="all" href="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-daterangepicker-master/font-awesome.min.css"/>
<link rel="stylesheet" type="text/css" media="all" href="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-daterangepicker-master/daterangepicker-bs3.css"/>


<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-daterangepicker-master/moment.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-daterangepicker-master/daterangepicker.js"></script>
<script src="${springMacroRequestContext.contextPath}/assets/js/smartwidgets/jarvis.widget.js"></script>


<style>
#addOrderInfo {
	float: left;
	margin-bottom: 5px;
}

#alterTable {
	float: right;
	margin-right: 1%;
}

.row .label.col.col-1 {
	text-align: right;
	text-justify: inter-ideograph;
}

.select2-hidden-accessible {
	display: none;
}

</style>

	<div id="tmpOrderInfo"></div>
	<div id="main_orderinfo" style="">
	<!-- HEADER -->
		<!-- END RIBBON -->
		<!-- widget grid -->
		<section id="widget-grid-order" class="">
			<label class="btn btn-sm btn-primary" id="addOrderInfo" onclick="editModel(null)">添加订单</label>
<!-- 			<button type="button" id="addOrderInfoField" class="btn btn-sm btn-primary" style="margin-left: 10px;">自定义订单字段</button> -->
			<div class="row">
				<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<div class="jarviswidget ${c.global_color}" id="wid-id-maincstm_des" data-widget-colorbutton="false" data-widget-editbutton="false"
					 data-widget-togglebutton="true" data-widget-deletebutton="true">
						<header>
							<span class="widget-icon"><i class="fa fa-table"></i></span>
							<h2>${agent!''}</h2>
						</header>
						<div>
							<div class="jarviswidget-editbox"></div>
							<div class="widget-body">
								<div class="widget-body-toolbar">
									<div class="smart-form">
										<section>
											<div class="row" style="margin-top: 5px;">
												<label class="label col">订单编号</label>
												<label class="input col col-2">
													<input type="text" name="orderid" class="form-control" id="orderid" placeholder="订单编号"/>
												</label>
												<label class="label col">订单状态</label>
												<label class="input col col-2">
						    						<select id="orderStatus" name="orderStatus"  onchange="getResult();">
						    							<option value="">请选择</option>
						    							<#list orderstatus?keys as key>
															<option value="${key}">${orderstatus[key]}</option>
														</#list>
						    						</select>
												</label>
												<label class="btn btn-sm btn-primary" onclick="getResult();">&nbsp;查&nbsp;&nbsp;&nbsp;询&nbsp;</label>
												<input type="hidden" name="adminflag" id="adminflag" value="${adminflag!''}">
												<input type="hidden" name="useruuid" id="useruuid" value="${useruuid!''}">
											</div>
										</section>
										<table id="dt_basic_orderinfo" class="table table-striped table-bordered table-hover"  data-order='[[ 0, "asc" ]]' width="100%"></table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</article>
			</div>
		</section>
	</div>

<!-- 提交参数 -->
<#macro params listParam> 
	<#if listParam??> 
		<#list listParam as p>
			param.${p} = $("#${p}").val(); 
		</#list> 
	</#if> 
</#macro>

<script src="${springMacroRequestContext.contextPath}/assets/js/plugin/select2/select2.min.js"></script>
<script type="text/javascript">

		var orderinfoTable = "";
		$(document).ready(function() {
			
			$("#orderStatus").select2({
		           allowClear : true,
		           width: '99%'
	        });
			
			pageSetUp();
			orderinfoTable = $('#dt_basic_orderinfo').DataTable({
			"dom" :"t" + "<'dt-toolbar-footer'<'col-sm-6 col-xs-12'i><'col-xs-12 col-sm-6 text-right'lp>>",
			"autoWidth" : true,
			"ordering" : true,
			"serverSide" : true,
			"processing" : true,
			"searching" : false,
			"retrieve":true,
			"pageLength" : '${pageLength!"5"}',
			"lengthMenu" : [5, 10, 15, 20, 25, 30 ],
		 	"language": {
				"url" : getContext() + "public/dataTables.cn.txt"
			},
			"ajax":{
				"url" : getContext() + "orderinfo/data",
				"type":"POST",
				"data" :function(param){
					param.order_id = $("#orderid").val();
					param.order_status = $("#orderStatus").val();
					param.adminflag = $("#adminflag").val();
					param.useruuid = $("#useruuid").val();
				}
			},
			"paging" :true,
			"pagingType" :"bootstrap",
			"lengthChange" : true,
			"order" : [[ "7", "desc"]],
			"columns" : [
					<#if title??>
						<#list dataRows?keys as key>
						
							<#if key == "receive_user_mobile">
								{"title" : "${dataRows[key]}",  "data" : "null","defaultContent":"","render":function(a,s,d){
									
									return window.parent.hiddenPhone(d.receive_user_mobile);
								}},
							<#else>
								{"title" : "${dataRows[key]}",  "data" : "${key}","defaultContent":""},
							</#if>
						</#list>
						{ "title" : "操作",  "data" : "null",  "orderable":false,
						  "render": function(data,type,full){
							   return "<a onclick=editModel('"+full.uuid+"','"+full.orderid+"');>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick=deleteInfo('"+full.uuid+"','"+full.orderid+"');>删除</a>";
						   }
						}
						<#else>
						{}
					</#if>
				]
			});
		});
		


		/* 查询结果 */
		function getResult(){
			$('#dt_basic_orderinfo').DataTable().ajax.reload(null,false);;
		}

		$("#orderStatus").select2({
	           allowClear : true,
	           width: '99%'
     	});
		
		/* 添加修改客户信息 标签 */
		function editModel(uuid,orderid){
			// 检查是否已有产品
			$.post(getContext() + "orderinfo/checkPros", function(data){
				if(data.success){
					$("#tmpOrderInfo").empty();
					$.post(getContext() + "orderinfo/get", {uuid : uuid,orderid : orderid}, function(data){
						$("#tmpOrderInfo").append(data);
					});
				}else{
					alert("抱歉,暂没有产品,无法添加订单！");
				}
			},"json");
		}
		
		/* 添加订单字段    */
		$("#addOrderInfoField").click(function(){
			$("#tmpOrderInfo").empty();
			$.post(getContext() + "design/edit", {tablename:"order_info"}, function(data){
				$("#tmpOrderInfo").append(data);
			});
		});

		function deleteInfo(uuid,orderid){
			$.SmartMessageBox({
				title : "删除",
				content : "该操作将执行删除操作，确定执行？",
				buttons : "[No][Yes]",
			}, function(ButtonPressed) {
				if (ButtonPressed === "Yes") {
					var url = getContext() + "orderinfo/delete";
					$.post(url,{uuid:uuid,orderid:orderid},function(data){
						
						if(data.success){
							$.smallBox({
								title : "操作成功",
								content : "<i class='fa fa-clock-o'></i> <i>删除成功...</i>",
								color : "#659265",
								iconSmall : "fa fa-check fa-2x fadeInRight animated",
								timeout : 2000
							});
							
							orderinfoTable.ajax.reload(null,false);;
							
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

			orderinfoTable.ajax.reload(null,false);;
		}
		
	</script>
