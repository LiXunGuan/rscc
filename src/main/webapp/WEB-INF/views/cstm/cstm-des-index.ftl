
<style>
	#alterTables {
		float: left;
		margin-bottom: 5px;
	}
</style>

	<div id="tmpDiv"></div>
	<div id="main_cstm_des" ></div>

		<section id="widget-grid-des" class="">
			<div class="row">
				<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="jarviswidget jarviswidget-color-darken" id="wid-id-maincstm_des" data-widget-colorbutton="false" data-widget-editbutton="false"
					 data-widget-togglebutton="true" data-widget-deletebutton="true">

						<header>
							<span class="widget-icon"><i class="fa fa-table"></i></span>
							<h2>自定义表字段</h2>
						</header>
						<div>
							<div class="jarviswidget-editbox"></div>
							<div class="widget-body no-padding">
								<div class="widget-body-toolbar">
									<div class="smart-form">
										<section>
											<div class="row">
												<label class="label col" >自定义表名</label>
												<div class="form-group col col-lg-2">
													<select name="tableNames" id="tableNames" class="form-control"  onchange="selectTable(this);">
														<#list allTables as t>
															<#if t_index == 0>
																<option value="${t.tableNameDB}" selected="selected">${t.tableName}</option>
															<#else>
																<option value="${t.tableNameDB}">${t.tableName}</option>
															</#if>
														</#list>
													</select>
												</div>
												<label class="btn btn-sm btn-primary" id="alterTables" style="float: left;">添加字段</label>
												<div class="col-col-2 pull-left">
													<div class="checkbox" style="padding-right: 20px;">
														<label>
														  <input type="checkbox" class="checkbox" name="checkAll" id="checkAll" checked="checked" onchange="showOrHide(this)">
														  <span>显示全部字段</span>
														</label>
													</div>
												</div>
											</div>
										</section>
										<table id="dt_basic_cstm_des" class="table table-striped table-bordered table-hover"  data-order='[[ 7, "asc" ]]' width="100%"></table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</article>
			</div>
		</section>
	
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
			    	"url" : getContext() + "design/data",
			    	"type":"POST",
			    	"data" :function(param){
			    		param.tableName = $("#tableNames").val();
			    		param.showAll = $("#checkAll").is(":checked");
			    	}
			    },
			 	"paging" :true,
				"pagingType" :"bootstrap",
				"lengthChange" : true,
				"order" : [[ "3", "asc"]],
				"columns" : [
						{ "title" : "列名","data" : "id","defaultContent":"","visible":false}, 
						{ "title" : "列名","data" : "columnName","defaultContent":""}, 
						{ "title" : "列类型","data" : "columnType","defaultContent":""},
						{ "title" : "显示顺序","data" : "orders","defaultContent":""},
						{ "title" : "列属性","data" : "characterProperty","defaultContent":""},
						{ "title" : "是否可查","data" : "allowSelect","defaultContent":""},
						{ "title" : "是否索引","data" : "allowIndex","defaultContent":""},
						{ "title" : "是否显示","data" : "allowShow","defaultContent":""},
						{ "title" : "允许为空","data" : "allowEmpty","defaultContent":""},
						{ "title" : "操作", "data" : "null", "orderable":false, 
							"render": function(data,type,full){
								if(full.isDefault == "0"){
									return "<a onclick=alterTable('"+full.uid+"');>编辑</a>&nbsp;&nbsp;<a onclick=deleteColumn('"+full.id+"');>删除</a>&nbsp;&nbsp;"+
									"<a onclick=alterOrders('"+full.uid+"','"+full.orders+"','"+1+"'); title='顺序往前'><i class='fa fa-arrow-up'>往前</i></a>&nbsp;&nbsp;"+
									"<a onclick=alterOrders('"+full.uid+"','"+full.orders+"','"+0+"');  title='顺序往后' ><i class='fa fa-arrow-down'>往后</i></a>";
								}
								
								return "<a onclick=alterOrders('"+full.uid+"','"+full.orders+"','"+1+"'); title='顺序往前'><i class='fa fa-arrow-up'>往前</i></a>&nbsp;&nbsp;"+
								"<a onclick=alterOrders('"+full.uid+"','"+full.orders+"','"+0+"');  title='顺序往后' ><i class='fa fa-arrow-down'>往后</i></a>";
							}
						}
					],
			});
			
		});
		
		/* 查询结果 */
		function getResult(){
			cstmDesignTable.ajax.reload(null,false);
		}
		
		/* 显示默认 不显示默认 */
		function showOrHide(thiz){
			getResult();
		}
		
		
		/* 添加弹框 */
		function addModel(){
			
			$.post(getContext() + "index/get",function(data){
				$("#main_cstm_des").append(data);
			});
		}
		
		
		/* 筛选表格 */
		function selectTable(obj){
			
			$("#tableName").val($(obj).val());
			cstmDesignTable.ajax.reload(null,false);
		}
		
		
		/* 删除 */
		function deleteColumn(uuid){
			
			var requestUrl = getContext() + "design/delete"; 
			$.SmartMessageBox({
				title : "删除",
				content : "该操作将执行删除操作，确定执行？",
				buttons : "[No][Yes]",
			}, function(ButtonPressed) {
				if (ButtonPressed === "Yes") {
					$.post(requestUrl,{uuid:uuid,tableName:$("#tableNames").val()},function(data){
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
			cstmDesignTable.ajax.reload( null, false );
		}
		
		/* 添加 */
		$("#alterTables").click(function(){

			$("#dialog_cstm_edit_column").remove();

			if($("#tableNames").val()){
				$.post(getContext() + "design/edit", {tablename:$("#tableNames").val(),tableNameStr:$("#tableNames").find("option:selected").text()}, function(data){
					$("#main_cstm_des").append(data);
				});
			}else{
				$.smallBox({
					title : "选择",
					content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>请选择你要操作的表格</i>",
					color : "#C46A69",
					iconSmall : "fa fa-times fa-2x fadeInRight animated",
					timeout : 2000
				});
			}
			
		});
		
		/* 编辑列 */
		function alterTable(id){
			
			$("#dialog_cstm_edit_column").remove();
			$.post(getContext() + "design/edit",{id:id,tablename:$("#tableNames").val(),tableNameStr:$("#tableNames").find("option:selected").text()},function(data){
				$("#main_cstm_des").append(data);
			});
			
			
		}
		
		/* 修改字段顺序 */
		function alterOrders(uuid,orders,tag){
			
			$.post(getContext()+"design/alterOrders",{uid:uuid,orders:orders,tag:tag,tableName:$("#tableName").val()},function(d){
				if(d.success){
		            $.smallBox({
		                title : "操作成功",
		                content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
		                color : "#659265",
		                iconSmall : "fa fa-check fa-2x fadeInRight animated",
		                timeout : 2000
		            });
		            $("#tableName").val(d.tableName);
		            cstmDesignTable.ajax.reload( null, false );
				}else{
					console.log(d.errorMsg);
		            $.smallBox({
		                title : "操作失败",
		                content : "<i class='fa fa-clock-o'></i> <i>操作失败</i>",
		                color : "#C46A69",
		                iconSmall : "fa fa-times fa-2x fadeInRight animated",
		                timeout : 2000
		            });
				}
				
				 $("#tableName").val(d.tableName);
				
			},"json");
			
		}
		
	</script>
