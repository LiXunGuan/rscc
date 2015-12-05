
<link rel="stylesheet" type="text/css" media="all" href="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-daterangepicker-master/font-awesome.min.css"/>
<link rel="stylesheet" type="text/css" media="all" href="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-daterangepicker-master/daterangepicker-bs3.css"/>


<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-daterangepicker-master/moment.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-daterangepicker-master/daterangepicker.js"></script>
<script src="${springMacroRequestContext.contextPath}/assets/js/smartwidgets/jarvis.widget.js"></script>

<script src="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-tags/bootstrap-tagsinput.js"></script>

<style>
#addProduct {
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

	<div id="tmpProduct"></div>
	<div id="main_product" style="">
	<!-- HEADER -->
		<!-- END RIBBON -->
		<!-- widget grid -->
		<section id="widget-grid-productindex" class="">
			<button type="button" id="addProduct" class="btn btn-sm btn-primary" onclick="editModel(null)">添加产品</button>
<!-- 			<button type="button" id="addProductField" class="btn btn-sm btn-primary" style="margin-left: 10px;">自定义产品字段</button> -->
			<div class="row">
				<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<div class="jarviswidget jarviswidget-color-darken" id="wid-id-maincstm_des" data-widget-colorbutton="false" data-widget-editbutton="false"
					 data-widget-togglebutton="true" data-widget-deletebutton="true">
						<header>
							<span class="widget-icon"><i class="fa fa-table"></i></span>
							<h2>产品管理</h2>
						</header>
						<div>
							<div class="jarviswidget-editbox"></div>
							<div class="widget-body">
								<div class="widget-body-toolbar">
									<div class="smart-form">
										<section>
											<div class="row" style="margin-top: 5px;">
												<label class="label col">产品编号</label>
												<label class="input col col-2">
													<input type="text" name="productId" class="form-control" id="productId" value="" placeholder="产品编号"/>
												</label>
												<label class="label col">产品名称</label>
												<label class="input col col-2">
													<input type="text" name="productName" class="form-control" id="productName" value="" placeholder="产品名称"/>
												</label>
												<label class="btn btn-sm btn-primary" onclick="getResult();">&nbsp;查&nbsp;&nbsp;&nbsp;询&nbsp;</label>
											</div>
										</section>
										<table id="dt_basic_product" class="table table-striped table-bordered table-hover"  data-order='[[ 0, "asc" ]]' width="100%"></table>
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

	<script type="text/javascript">

		var productTable = "";
		$(document).ready(function() {
			pageSetUp();
			productTable = $('#dt_basic_product').DataTable({
			"dom" :"t" + "<'dt-toolbar-footer'<'col-sm-6 col-xs-12'i><'col-xs-12 col-sm-6 text-right'lp>>",
			"autoWidth" : true,
			"ordering" : true,
			"serverSide" : true,
			"processing" : true,
			"searching" : false,
			"retrieve":true,
			"pageLength" : 10,
			"lengthMenu" : [ 10, 15, 20, 25, 30 ],
		 	"language": {
				"url" : getContext() + "public/dataTables.cn.txt"
			},
			"ajax":{
				"url" : getContext() + "product/data",
				"type":"POST",
				"data" :function(param){
					param.product_id = $("#productId").val();
					param.product_name = $("#productName").val();
				}
			},
			"paging" :true,
			"pagingType" :"bootstrap",
			"lengthChange" : true,
			"order" : [[ "4", "desc"]],
			"columns" : [
					<#if title??>
						<#list dataRows?keys as key>
							<#if key == "phone_number">
								{"title" : "${dataRows[key]}",  "data" : "null","render":function(d,t,f){
									return "<a onclick=mainPhone('"+ f.uuid +"','"+f.phone_number+"');>"+ f.phone_number +"</a>";
								}},
							<#else>
								{"title" : "${dataRows[key]}",  "data" : "${key}"},
							</#if>
						</#list>
					
						{ "title" : "操作",  "data" : "null",  "orderable":false,
						  "render": function(data,type,full){
							   return "<a onclick=editModel('"+full.uuid+"');>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick=deleteInfo('"+full.uuid+"');>删除</a>";
						   }
						}
						<#else>
						{}
					</#if>
				]
			});
			
		});

		/* 查询结果    */
		function getResult(){
			$('#dt_basic_product').DataTable().ajax.reload(null,false);;
		}

		/* 添加弹框   */
		function addModel(){
			$.post(getContext() + "index/get",function(data){
				$("#main_product").append(data);
			});
		}
		
		/* 添加产品字段    */
		$("#addProductField").click(function(){
			$("#tmpProduct").empty();
			$.post(getContext() + "design/edit", {tablename:"product"}, function(data){
				$("#tmpProduct").append(data);
			});
		});
		
		/* 编辑或修改产品信息      */
		function editModel(uuid){
			$("#tmpProduct").empty();
			$.post(getContext() + "product/get", {uuid:uuid}, function(data){
				$("#tmpProduct").append(data);
			});
		}

		function deleteInfo(uuid){
			$.SmartMessageBox({
				title : "删除",
				content : "该操作将执行删除操作，确定执行？",
				buttons : "[No][Yes]",
			}, function(ButtonPressed) {
				if (ButtonPressed === "Yes") {
					var url = getContext() + "product/delete";
					$.post(url,{uuid:uuid},function(data){
						if(data.success){
							$.smallBox({
								title : "操作成功",
								content : "<i class='fa fa-clock-o'></i> <i>删除成功...</i>",
								color : "#659265",
								iconSmall : "fa fa-check fa-2x fadeInRight animated",
								timeout : 2000
							});
							
							productTable.ajax.reload(null,false);;
						
						}else{
							$.smallBox({
								title : "操作失败",
								content : "该产品存在订单,暂不能删除...。",
								color : "#C46A69",
								iconSmall : "fa fa-times fa-2x fadeInRight animated",
								timeout : 5000
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

			productTable.ajax.reload(null,false);;
		}

		/* 编辑列 */
		function alterTable(){
			$("#dialog_cstm_edit_column").remove();
			$.post(getContext() + "design/edit",function(data){
				$("#main_product").append(data);
			});
		}

		/* 编辑主号码 */
		function mainPhone(uuid,number){
		
			$.post(getContext()+'/cstm/getPhone',{uuid:uuid,phone:number},function(data){

				$("#main_product").append(data);
			});
		}
       
	</script>
