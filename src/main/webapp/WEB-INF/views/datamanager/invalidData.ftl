	<div id="invalidContent" style="">
		<section id="widget-grid-invalidData" class="">
			<div class="row">
				<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="jarviswidget jarviswidget-color-darken" data-widget-editbutton="false">
						<header>
							<span class="widget-icon"><i class="fa fa-table"></i></span>
							<h2>无效数据</h2>
						</header>
						<div>
							<div class="widget-body no-padding">
								<div class="widget-body-toolbar">
									<div class="smart-form">
											<div class="row" style="margin:0;">
												<label class="label col" style="padding-left: 0;">批次名</label> 
												<label class="input col col-2"> 
													<input type="text" name="batchName" id="batchName" value=""/>
												</label>
												<label class="label col">电话号码</label> 
												<label class="input col col-2"> 
													<input type="text" name="phoneNumber" id="phoneNumber" value=""/>
												</label>
												<label class="label col">号码状态</label> 
												<label class="input col col-2"> 
													<select id="phoneNumberState" class="select2" onchange="getResult();">
														<option value="">请选择号码状态</option>
														<option value="isAbandon">已废弃</option>
														<option value="isBlacklist">黑名单</option>
														<option value="isFrozen">已冻结</option>
													</select>
												</label>
											
												<label class="btn btn-sm btn-primary" onclick="getResult();">查询</label>
											</div>
											<table id="dt_basic_invalidData" class="table table-striped table-bordered table-hover"  data-order='[[ 0, "asc" ]]' width="100%"></table>
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
			$("#phoneNumberState").select2({
				width:"100%"
			});
			initTable();
		});
		
		function initTable(){
			oTable = $('#dt_basic_invalidData').dataTable({
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
			    	"url" : getContext() + "invalid/data/data",
			    	"type":"POST",
			    	"data" :function(param){
			    		param.batchUuid = $("#batchName").val();
			    		param.phoneNumber=$("#phoneNumber").val();
			    		param.phoneNumberState=$("#phoneNumberState").val();
			    	}
			    },
			 	"paging" :true,
				"pagingType" :"bootstrap",
				"lengthChange" : true,
				"order" : [[ "2", "desc"]],
				"columns" : [
					   { "title" : "批次id", "data" : "batchUuid","visible":false},
					   { "title" : "批次名", "data" : "batchName"},
					   { "title" : "电话号码", "data" : "phoneNumber"},
					   { "title" : "号码状态", "data" : "phoneNumberState"},
					   { "title" : "操作日期", "data" : "maketime"},
					   { "title" : "操作", "data" : null,
						 "render":function(data,type,full){
							 return "<a onclick='recover(\""+full.phoneNumber+"\",\""+full.batchUuid+"\")'>恢复</a>";
					   }}
					],
			});
		}
		function recover(phonenumber,batchUuid){
			$.SmartMessageBox({
				title : "恢复数据",
				content : "<h2>该操作会将已经设置为无效的数据恢复到原批次中，等待重新分配</h2>",
				buttons : "[No][Yes]",
			}, function(ButtonPressed) {
				if (ButtonPressed === "Yes") {
				$.post(getContext()+"invalid/data/recover",{phonenumber:phonenumber,batchUuid:batchUuid},function(data){
					 if(data.success){
		                    $.smallBox({
		                        title : "操作成功",
		                        content : "<i class='fa fa-clock-o'></i> <i>恢复数据成功...</i>",
		                        color : "#659265",
		                        iconSmall : "fa fa-check fa-2x fadeInRight animated",
		                        timeout : 2000
		                    });
		                    getResult();
		                }else{
		                    $.smallBox({
		                        title : "操作失败",
		                        content : "<i class='fa fa-clock-o'></i> <i>恢复数据失败...</i>",
		                        color : "#C46A69",
		                        iconSmall : "fa fa-times fa-2x fadeInRight animated",
		                        timeout : 2000
		                    });
		                }
				},"json").error(function(){
					$.smallBox({
						title : "操作失败",
						content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>进行恢复时出现异常...</i>",
						color : "#C46A69",
						iconSmall : "fa fa-times fa-2x fadeInRight animated"
					});
				});
			}
		});
	}
		
		// 刷新
		function getResult(){
			$('#dt_basic_invalidData').DataTable().ajax.reload(null,false);
		}

		
</script>
	
