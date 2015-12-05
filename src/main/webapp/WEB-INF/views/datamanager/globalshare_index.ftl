	<div id="globalshareDatamainContent" style="">
		<section id="widget-grid-globalshareData" class="">
			<div class="row">
				<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
<!--				<div style="margin-bottom: 6px;">																			-->
<!--					<label id="getdatas" class="btn btn-sm btn-primary" onclick="getDataItem()">获取数据</label>				-->
<!--				</div>																										-->   
					<div class="jarviswidget jarviswidget-color-darken" data-widget-editbutton="false">
						<header>
							<span class="widget-icon"><i class="fa fa-table"></i></span>
							<h2>共享池数据管理</h2>
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
											
												<label class="btn btn-sm btn-primary" onclick="getResult();">查询</label>
											</div>
											<table id="dt_basic_globalshareData" class="table table-striped table-bordered table-hover"  data-order='[[ 0, "asc" ]]' width="100%"></table>
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
			initTable();
		});
		
		function initTable(){
			oTable = $('#dt_basic_globalshareData').dataTable({
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
			    	"url" : getContext() + "globalshare/data",
			    	"type":"POST",
			    	"data" :function(param){
			    		param.batchName = $("#batchName").val();
			    		param.phoneNumber=$("#phoneNumber").val();
			    	}
			    },
			 	"paging" :true,
				"pagingType" :"bootstrap",
				"lengthChange" : true,
				"order" : [[ "5", "desc"]],
				"columns" : [
					   { "title" : "批次", "data" : "batchName"},
					   { "title" : "电话号码", "data" : "null",
						 "render":function(data,type,full){
							return window.parent.hiddenPhone(full.phoneNumber)
						  }
				       },
					   { "title" : "数据信息", "data" : "json"},
					   { "title" : "呼叫次数", "data" : "callCount"},
					   { "title" : "最后一次电话结果", "data" : "lastCallResult","render":function(data,type,full){
						   if(data == '1'){
							   return "已通";
						   }else{
							   return "未通";
						   }
					   }},
					   { "title" : "最后一次电话时间", "data" : "lastCallTime"},

					],
			});
		}
		
		// 刷新
		function getResult(){
			$('#dt_basic_globalshareData').DataTable().ajax.reload(null,false);
		}

		
</script>
	
