	<div id="knowledgeDiv"></div>
	<div id="mainContent" style="">
	<!-- HEADER -->
		<!-- END RIBBON -->
		<!-- widget grid -->
		<section id="widget-grid" class="">
			<div class="row">
				<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="jarviswidget jarviswidget-color-darken" id="wid-id-0" data-widget-editbutton="false">
						<header>
							<span class="widget-icon"><i class="fa fa-table"></i></span>
							<h2>知识库</h2>
						</header>
						<div>
							<div class="widget-body no-padding">
								<div class="widget-body-toolbar">
									<div class="smart-form">
										<section>
											<div class="row">
												<label class="label col">标题</label>
												<label class="input col col-1">
													<input type="text" name="title" class="form-control" id="title" value="" />
												</label>
												<label class="label col">内容</label>
												<label class="input col col-2">
													<input type="text" name="kcontent" class="form-control" id="kcontent" value="" />
												</label>
												<label class="label col">目录</label>
												<label class="input col col-1">
													<input type="text" name="directory" class="form-control" id="directory" value="" />
												</label>
												<label class="label col">标签</label>
												<label class="input col col-1">
													<input type="text" name="tag" class="form-control" id="tag" value="" list="list" />
													<datalist id="list">
														
													</datalist>
												</label>
												<label class="btn btn-sm btn-primary" onclick="getResult();">查询</label>
												<label class="btn btn-sm btn-primary" onclick="editItem();">添加</label>
											</div>
											<div id="remark" style="margin-top: 12px;">
												关键字<label style="font-size: large;" id="keyword"></label>
												的搜索量为 <label style="font-size: large;" id="searchNumbers"></label>.
											</div>
										</section>
										<table id="dt_basic_knowledge" class="table table-striped table-bordered table-hover"  data-order='[[ 0, "asc" ]]' width="100%"></table>
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
				
				$("#remark").hide();
			
			    $('#dt_basic_knowledge').dataTable({
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
			    	"url" : getContext() + "knowledge/knowledge/data",
			    	"type":"POST",
			    	"data" :function(param){
			    		param.title = $("#title").val();
			    		param.content = $("#kcontent").val();
			    		param.directory = $("#directory").val();
			    		param.tag = $("#tag").val();
			    	}
			    },
			 	"paging" :true,
				"pagingType" :"bootstrap",
				"lengthChange" : true,
				"order" : [[ "0", "desc"]],
				"columns" : [
					   { "title" : "标题","data" : "knowledgeTitle"}, 
					   { "title" : "内容","data" : "knowledgeContent"},
					   { "title" : "所属目录","data" : "directoryName"},
					   { "title" : "标签","data" : "knowledgeTages"},
					   { 
						   "title" : "操作",
						   "data" : "null",
						   "orderable":false,
						   "render": function(data,type,full){
							   return "<a onclick=editItem('"+full.uid+"','update');>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick=deleteItem('"+full.uid+"');>删除</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick=editItem('"+full.uid+"','get');>查看</a>";
						   }
					   }
					],
			   });
			    
			  //清空标签列表				
			  $("#list").empty();
			  //更新标签信息
			  getlabels()
		});
		
		/* 查询最新的标签信息 */
		function getlabels(){
			var url = getContext() + "knowledge/knowledge/getLbaels";
			$.post(url, function(data){
				if(data.success){
					$.each(data.labels,function(k, v){
						$("#list").append("<option>" + v.keyword + "</option>");
					})
				}
			},"json");
		}
		
		/* 查询结果 */
		function getResult(){
			var t = document.getElementById("tag").value;
			var url = getContext() + "knowledge/knowledge/getSearchNumbers";
			$.post(url,{tag:t}, function(data){
				if(data.success){
					$("#remark").show();
					document.getElementById('keyword').innerText = data.tag;
					document.getElementById('searchNumbers').innerText = data.searchNumbers;
				}
			},"json");
			
			//更新表格信息
			$('#dt_basic_knowledge').DataTable().ajax.reload(null,false);;
			
			//清空标签列表				
			$("#list").empty();
			//更新标签信息
			getlabels()
		}
		
		/* 添加/修改 */
		function editItem(uuid,operation){
			$('#knowledgeDiv').empty();
			$('#knowledgeSave').remove();
	        var url = getContext() + "knowledge/knowledge/get";
			$.post(url, {uuid:uuid,operation:operation}, function(data){
				$("#knowledgeDiv").append(data);
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
		            var url = getContext()+"knowledge/knowledge/remove";
		            $.post(url,{uuid:uuid},function(data){
		                if(data.success){
		                    $.smallBox({
		                        title : "操作成功",
		                        content : "<i class='fa fa-clock-o'></i> <i>删除成功...</i>",
		                        color : "#659265",
		                        iconSmall : "fa fa-check fa-2x fadeInRight animated",
		                        timeout : 2000
		                    });
		                    $('#dt_basic_knowledge').DataTable().ajax.reload(null,false);;
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
