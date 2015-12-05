	<div id="directoryDiv"></div>
	<div id="mainContent" style="">
	<!-- HEADER -->
		<!-- END RIBBON -->
		<!-- widget grid -->
		<section id="widget-grid" class="">
			<div class="row">
				<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="jarviswidget jarviswidget-color-darken" id="wid-id-0" data-widget-editbutton="false">
						<label class="btn btn-sm btn-primary" onclick="editItem();" style="margin-bottom: 5px;">&nbsp;添&nbsp;&nbsp;&nbsp;加&nbsp;</label>
						<header>
							<span class="widget-icon"><i class="fa fa-table"></i></span>
							<h2>知识目录</h2>
						</header>
						<div>
							<div class="widget-body no-padding">
								<div class="widget-body-toolbar">
									<div class="smart-form">
										<section>
											<div class="row">
												<label class="label col">目录名</label>
												<label class="input col col-2">
													<input type="text" name="dname" class="form-control" id="dname" value="" />
												</label>
												<label class="btn btn-sm btn-primary" onclick="getResult();">&nbsp;查&nbsp;&nbsp;&nbsp;询&nbsp;</label>
											</div>
										</section>
										<table id="dt_basic_knowledgeDirectory" class="table table-striped table-bordered table-hover"  data-order='[[ 0, "asc" ]]' width="100%"></table>
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
			oTable = $('#dt_basic_knowledgeDirectory').dataTable({
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
			    	"url" : getContext() + "knowledge/directory/data",
			    	"type":"POST",
			    	"data" :function(param){
			    		param.dname= $("#dname").val();
			    	}
			    },
			 	"paging" :true,
				"pagingType" :"bootstrap",
				"lengthChange" : true,
				"order" : [[ "0", "desc"]],
				"columns" : [
					   { "title" : "目录名","data" : "directoryName"}, 
					   { "title" : "父目录","data" : "parentDirectory"},
					   { "title" : "目录描述","data" : "directoryRemark"},
					   { 
						   "title" : "操作",
						   "data" : "null",
						   "orderable":false,
						   "render": function(data,type,full){
							   if(full.uid == 'ede001'){
								   return "<a onclick=editItem('"+full.uid+"');>修改</a>";
							   }
							   return "<a onclick=editItem('"+full.uid+"');>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick=deleteItem('"+full.uid+"');>删除";
						   }
					   }
					],
			});
		}
		
		/* 查询结果 */
		function getResult(){
			$('#dt_basic_knowledgeDirectory').DataTable().ajax.reload(null,false);;
		}

		/* 添加/修改 */
		function editItem(uuid){
			$('#directoryDiv').empty();
			$('#directorySave').remove();
	        var url = getContext() + "knowledge/directory/get";
			$.post(url, {uuid:uuid}, function(data){
				$("#directoryDiv").append(data);
			});
		}
		
		/* 删除   */
		function deleteItem(uuid){
		    $.SmartMessageBox({
		        title : "删除",
		        content : "确定删除该条信息吗？",
		        buttons : '[No][Yes]'
		    }, function(ButtonPressed) {
		        if (ButtonPressed === "Yes") {
		            var url = getContext()+"knowledge/directory/remove";
		            $.post(url,{uuid:uuid},function(data){
		                if(data.success){
		                    $.smallBox({
		                        title : "操作成功",
		                        content : "<i class='fa fa-clock-o'></i> <i>删除成功...</i>",
		                        color : "#659265",
		                        iconSmall : "fa fa-check fa-2x fadeInRight animated",
		                        timeout : 2000
		                    });
		                    $('#dt_basic_knowledgeDirectory').DataTable().ajax.reload(null,false);;
		                }else{
		                    $.smallBox({
		                        title : "操作失败",
		                        content : "<i class='fa fa-clock-o'></i> <i>删除失败...</i>",
		                        color : "#C46A69",
		                        iconSmall : "fa fa-times fa-2x fadeInRight animated",
		                        timeout : 2000
		                    });
		                }
		            },"json").error(function(){
		                $.smallBox({
		                    title : "操作失败",
		                    content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作失败...</i>",
		                    color : "#C46A69",
		                    iconSmall : "fa fa-times fa-2x fadeInRight animated"
		                });
		            });
		        }

		    });
		};
		
	</script>
