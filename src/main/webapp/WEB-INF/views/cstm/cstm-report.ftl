
<style>

	label.col.col-1{
		text-align: center;
	}

</style>

	<div id="tmpDiv_report"></div>
	<div id="main_pool_report" style="">
	<!-- HEADER -->
		<section id="widget-grid-pool" class="">
			<div class="row">
				<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="jarviswidget jarviswidget-color-darken" id="wid-id-mainpool" data-widget-colorbutton="false">

						<header>
							<span class="widget-icon"><i class="fa fa-table"></i></span>
							<h2>客户信息统计</h2>
						</header>
						
						<div>
						<div class="jarviswidget-editbox">
						</div>
							<div class="widget-body no-padding">
								<div class="widget-body-toolbar">
									<div class="smart-form">
										<section>
											<div class="row" style="margin-top: 5px;">
												<label class="label col col-1" >标记时间</label>
													<label class="input col col-2">
														<!-- 用于显示去取值 -->
														<input type="text" name="opt_date" id="opt_date" class="form-control" value="" />
													</label>
													<script type="text/javascript">
														$(document).ready(function() {
															$("#opt_date").daterangepicker({
																format: 'YYYY-MM-DD HH:mm:ss',
																timePicker: true,
																timePicker12Hour: false,
																showDropdowns: true,
											                    locale: {
											                        applyLabel: '确定',
											                        cancelLabel: '清空',
											                        fromLabel: '从',
											                        toLabel: '到',
											                        daysOfWeek: ['日', '一', '二', '三', '四', '五','六'],
											                        monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
											                        firstDay: 1
											                    }
															},function(start, end, label) {
															}).on('cancel.daterangepicker',function(ev, picker){
																$('#opt_date').val('');
																getResult();
															}).on('apply.daterangepicker',function(ev, picker){
																getResult();
															});
														});
													</script>
											
												<label class="label col col-1">客户姓名</label>
												<label class="input col col-2">
                                      				<input name="opt_obj" id="opt_obj"  value="">
                                 				</label>
                                 				
												<label class="label col col-1">客户池</label>
												<label class="input col col-2">
                                      				<input name="pool_name" id="pool_name"  value="">
                                 				</label>
												<label class="btn btn-sm btn-primary" id="search" onclick="getResult();">查询</label>
                                 				
                           					</div>
										</section>
										<btr/>
										<section>
											<div class="row" style="margin-top: 5px;">
												<label class="label col col-1">客户来源</label>
												<label class="input col col-2">
                                      				<input name="opt_source" id="opt_source" value="">
                                 				</label>
                                 				
												<label class="label col col-1">客户数量</label>
												<label class="input col col-2">
                                      				<input name="opt_count" id="opt_count"  value="">
                                 				</label>
											</div>
										</section>
										
										<table id="dt_report_basic_pool" class="table table-striped table-bordered table-hover" data-order='[[ 0, "asc" ]]' width="100%"></table>
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
	
		var	dt_report_basic_pool = '';
		$(document).ready(function() {
				dt_report_basic_pool = $('#dt_report_basic_pool').DataTable({
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
			    	"url" : getContext() + "cstm/reportData",
			    	"type":"POST",
			    	"data" :function(p){
			    		p.opt_date = $("#opt_date").val();
			    		p.opt_obj = $("#opt_obj").val(); 
			    		p.opt_source = $("#opt_source").val();
			    		p.pool_name = $("#pool_name").val(); 
			    		p.opt_count = $("#opt_count").val(); 
			    	}
			    },
			 	"paging" :true,
				"pagingType" :"bootstrap",
				"lengthChange" : true,
				"order" : [[ "0", "desc"]],
				"columns" : [
					   { "title" : "记录时间","data" : "opt_date",}, 
					   { "title" : "客户姓名","data" : "opt_obj"},
					   { "title" : "客户来源","data" : "opt_source"},
					   { "title" : "客户池","data" : "pool_name"},
					   { "title" : "客户数量","data" : "opt_count"},
					],
			});
		});

		/* 刷新数据 */
		function getResult(){
			
			dt_report_basic_pool.ajax.reload(null,false);
		}
		
		
		
		
		
		
		
	</script>
