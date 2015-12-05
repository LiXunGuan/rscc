<div id="main_billRate" style="">
	<label class="btn btn-sm btn-primary" id="addCstmservice" style="margin-top: 2px;margin-bottom: 5px;"  onclick="addRate()">添加费率</label>
	<div class="row">
				<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="jarviswidget jarviswidget-color-darken" id="wid-id-0" data-widget-editbutton="false">
						<header >
							<span class="widget-icon" ><i class="fa fa-table"></i></span>
							<h2>费率管理</h2>
						</header>
						<div>
							<div class="widget-body no-padding">
								<div class="widget-body-toolbar">
									<div class="smart-form">
										<section>
											<div class="row" style="margin-top: 5px;">
											
												<label class="label col">计费类型</label>
												<div class="form-group col col-lg-2">
													<select name="billType" id="billType" onchange="getResult();">
														<option value="">全部</option>
														<#list ratetype as rt>
															<option value="${rt}"><#if rt == "Sip">分机<#elseif rt == "SkillGroup">技能组</#if></option>
														</#list>
                                      				</select>
                                 				</div>
											
												<label class="label col">费率名称</label>
												<label class="input col col-2">
                                      				<input name="billName" id="billName"  value="">
                                 				</label>
												<label class="btn btn-sm btn-primary" id="search" onclick="getResult();">查询</label> 
											</div>
										</section>
									</div>
									<table id="rateform" class="display projects-table table table-striped table-bordered table-hover"  data-order='[[ 0, "asc" ]]' width="100%"></table>
								</div>
							</div>
						</div>
					</div>
				</article>
			</div>
		</div>
	
	<script type="text/javascript">
		$(document).ready(function() {
			
			 $('#billType').select2({
				    allowClear : true,
				    width:'99%'
				});
			 
		    $('#rateform').dataTable({
		    	"dom" :"t" + "<'dt-toolbar-footer'<'col-sm-6 col-xs-12'i><'col-xs-12 col-sm-6 text-right'lp>>",
				"autoWidth" : true,
				"ordering" : true,
//				"serverSide" : true,   设置开启服务器模式后，表格的下方信息显示部分会出现错误
				"processing" : true,
				"searching" : false,
				"pageLength" : 10,
				"lengthMenu" : [ 10, 15, 20, 25, 30 ],
			 	"language": {
			         "url" : getContext() + "public/dataTables.cn.txt"
			     },
			    "ajax":{
			    	"url" : getContext() + "billing/rate/data",
			    	"type":"POST",
			    	"data": function(param) {
						param.billRateType = $("#billType").val();
						param.billRateName = $("#billName").val();
						
					}
			    },
			    "paging" :true,
				"pagingType" :"bootstrap",
				"lengthChange" : true,
				"order" : [[ "2", "desc"]],
				"columns" : [
					{"title": "uuid","visible":false,"data": "uid"},
					{"title": "计费类型",
					    "data": "billRateType",
					    "render":function(data,type,full){
					    	if(full.billRateType=="Sip"){
					    		return "分机";
					    	}else{
					    		return "技能组";
					    	}
					    },defaultContent:""
					},
					{
					    "title": "费率名称",
					    "data": "billRateName",
					    defaultContent:""
					},
					{
					    "title": "费率明细",
					    "data": "null","render": function(data,type,full){
					    	return '<span>'+full.rateSdfMoney+'</span>&nbsp;&nbsp;元/&nbsp;&nbsp;<span>'+full.rateSdfTime+'</span>&nbsp;&nbsp;秒';
					    }
					},
				   { 
					   "title" : "操作",
					   "data" : "null",
					   "orderable":false,
					   "render": function(data,type,full){
							 if(full.billRateType == 'Sip'){
	    						 return '&nbsp;&nbsp;<a  href="javascript:void(0);" onclick=allotSip(\''+full.uid+'\',this);>分配分机</a>'
	    						 +'&nbsp;&nbsp;<a onclick=changeRate("'+full.uid+'");>修改</a>&nbsp;&nbsp;<a onclick=deleteRate("'+full.uid+'");>删除</a>';
							 }else{
								 return '&nbsp;&nbsp;<a  href="javascript:void(0);" onclick=allotSkillGroup(\''+full.uid+'\',this);>分配队列</a>'
	    						 +'&nbsp;&nbsp;<a onclick=changeRate("'+full.uid+'");>修改</a>&nbsp;&nbsp;<a onclick=deleteRate("'+full.uid+'");>删除</a>';
							 }
							 return null;
					   }
				   }
				],
			});
		});
		
	     $('#rateForm').ajaxForm({
	            dataType:"json",
	            success: function(data) {
	                if(data.success){
	                    $.smallBox({
	                        title : "操作成功",
	                        content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
	                        color : "#659265",
	                        iconSmall : "fa fa-check fa-2x fadeInRight animated",
	                        timeout : 2000
	                    });
	                }
	                $('#rateform').DataTable().ajax.reload(null,false);
	            },
	            
	            submitHandler : function(form) {
	                $(form).ajaxSubmit({
	                    success : function() {
	                        $("#rateForm").addClass('submited');
	                    }
	                });
	            },
	            error: function(XMLHttpRequest,textStatus , errorThrown){
	                if(textStatus=='error'){
	                    $.smallBox({
	                        title : "操作失败",
	                        content : "<i class='fa fa-clock-o'></i> <i>操作失败</i>",
	                        color : "#C46A69",
	                        iconSmall : "fa fa-times fa-2x fadeInRight animated",
	                        timeout : 2000
	                    });
	                }
	            }
	        });
		/*添加费率*/
		function addRate(){
			$.post(getContext() + "billing/rate/addrate",function(data){
				$("#main_billRate").append(data);
			});
		}
		
		/* 分配分机 */
		function allotSip(uuid){
		
			$.post(getContext() + "billing/rate/allotSipuser",{uuid : uuid},function(data){
				$("#main_billRate").append(data);
			});
		}
		
		/*分配技能组*/
		function allotSkillGroup(uuid){
			$.post(getContext() + "billing/rate/allotSkillGroup",{uuid : uuid},function(data){
				$("#main_billRate").append(data);
			});
		}

		/*修改费率信息*/
		function changeRate(uuid){
			
			$.post(getContext()+'billing/rate/changerate',{uuid:uuid},function(data){
				$("#main_billRate").append(data);
			});
		}
		
		/*刷新tab*/ 
        function getResult(){
        	$('#rateform').DataTable().ajax.reload(null,false);
		}
        
		/*删除功能*/
		function deleteRate(uuid){
				  $.SmartMessageBox({
		                title : "删除",
		                content : "确定删除当前费率明细么？",
		                buttons : "[No][Yes]",
		            }, function(ButtonPressed, Value) {
		            	if(ButtonPressed =="Yes"){
		            		
		            		$.post(getContext()+'billing/rate/delete',{uuid:uuid},function(data){
		            			if(data.success){
	                                $.smallBox({
	                                    title : "操作成功",
	                                    content : "<i class='fa fa-clock-o'></i> <i>操作成功...</i>",
	                                    color : "#659265",
	                                    iconSmall : "fa fa-check fa-2x fadeInRight animated",
	                                    timeout : 2000
	                                });
	                                $('#rateform').DataTable().ajax.reload(null,false);
		            			}else{
		                              $.smallBox({
		                                 title : "操作失败",
		                                 content : "<i class='fa fa-clock-o'></i> <i>操作失败...</i>",
		                                 color : "#C46A69",
		                                 iconSmall : "fa fa-times fa-2x fadeInRight animated",
		                                 timeout : 2000
	                             	});
		            			}
		            		},"json");
		            	}
		            });
			}
	</script>
