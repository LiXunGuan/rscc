	<div id="deptDataDiv"></div>
	<style>
		#dt_basic_deptdata td>a{
			text-decoration:underline
		}
	</style>
	<div id="deptDatamainContent" style="">
	<!-- HEADER -->
		<!-- END RIBBON -->
		<!-- widget grid -->
		<section id="widget-grid-deptData" class="">
<#--			<#if Session["CURRENTUSER"]["uid"] == '0'> -->
				<label class="btn btn-sm btn-primary" id="changeLimit" onclick="setDepartmentLimit()" style="margin-bottom: 5px;">部门数据上限管理</label>
<#-- 			</#if> -->
			<div class="row">
				<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="jarviswidget jarviswidget-color-darken" data-widget-editbutton="false">
						<header>
							<span class="widget-icon"><i class="fa fa-table"></i></span>
							<h2>部门数据管理</h2>
						</header>
						<div>
							<div class="widget-body no-padding">
								<div class="widget-body-toolbar">
									<div class="smart-form">
										<section>
											<div class="row" style="margin-top: 5px;">
												<label class="label col">批次名</label>
												<label class="input col col-2">
													<input type="text" name="batchname" class="form-control" id="batchname" value="" placeholder="批次名" />
												</label>
												<label class="label col">部门名</label>
												<label class="input col col-2">
													<select id="deptname" name="deptname"  onchange="getDeptDataResult();">
						    							<option value="">请选择</option>
						    							<#if depts??>
							    							<#list depts as dept>
																<option value="${dept.datarangeName}">${dept.datarangeName}</option>
															</#list>
						    							</#if>
						    						</select>
												</label>
												<label class="btn btn-sm btn-primary" onclick="getDeptDataResult();">&nbsp;查&nbsp;&nbsp;&nbsp;询&nbsp;</label>
											</div>
										</section>
										<table id="dt_basic_deptdata" class="table table-striped table-bordered table-hover"  data-order='[[ 0, "asc" ]]' width="100%"></table>
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
			 var dt = $('#dt_basic_deptdata').DataTable({
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
			    	"url" : getContext() + "deptdata/dept/data",
			    	"type":"POST",
			    	"data" :function(param){
			    		param.batchname = $("#batchname").val();
			    		param.deptname = $("#deptname").val();
			    	}
			    },
			 	"paging" :true,
				"pagingType" :"bootstrap",
				"lengthChange" : true,
				"order" : [[ "2", "desc"]],
				"columns" : [
						{
							"title" : "详细",
							"class":          "details-control",
							"orderable":      false,
							"data":           null,
							"defaultContent": ""
						},
					   { "title" : "批次名", "data" : "batchname"},
					   { "title" : "部门", "data" : "deptname"},
					   { "title" : "单次上限", "data" : "singleLimit"},
					   { "title" : "单日上限", "data" : "dayLimit"},
					   { "title" : "部门内数据量", "data" : "null","render":function(data,type,full){
						   if(full.dataCount == 0)
						     return "<a style='color:red;font-size:15px;' onclick=getDataCount(null,'"+full.dataBatchUuid+"','"+full.departmentUuid+"','"+full.isLock+"');>"+ full.dataCount +"</a>";
						   else
						     return "<a onclick=getDataCount(null,'"+full.dataBatchUuid+"','"+full.departmentUuid+"','"+full.isLock+"');>"+ full.dataCount +"</a>";
					   }}, 
					   { "title" : "二次领用的数据量", "data" : "null","render":function(data,type,full){
// 						   return "<a onclick=getOwnCount('"+full.batchname+"','"+full.dataBatchUuid+"','"+full.departmentUuid+"','"+full.isLock+"');>"+ full.ownCount +"</a>";
						   return "<a onclick=getOwnCount(null,'"+full.dataBatchUuid+"','"+full.departmentUuid+"','"+full.isLock+"');>"+ full.ownCount +"</a>";
					   }},
					   { "title" : "意向客户量", "data" : "null","render":function(data,type,full){
						   return "<a onclick=getIntentCount(null,'"+full.dataBatchUuid+"','"+full.departmentUuid+"');>"+ full.intentCount +"</a>";
					   }},
					   { "title" : "成交客户量", "data" : "customerCount","render":function(data,type,full){
						   return "<a onclick=getCustomerCount(null,'"+full.dataBatchUuid+"','"+full.departmentUuid+"');>"+ full.customerCount +"</a>";
					   }},
					   { "title" : "废号量", "data" : "null","render":function(data,type,full){
						   return "<a onclick=getAbandonCount(null,'"+full.dataBatchUuid+"','"+full.departmentUuid+"');>"+ full.abandonCount +"</a>";
					   }},
					   { "title" : "黑名单号码量", "data" : "null","render":function(data,type,full){
						   return "<a onclick=getBlackListCount(null,'"+full.dataBatchUuid+"','"+full.departmentUuid+"');>"+ full.blacklistCount +"</a>";
					   }},
					   { "title" : "归还次数", "data" : "returnTimes"},
					   { 
						   "title" : "群呼锁定", 
						   "data" : "null",
						   "render": function(data,type,full){
							   if(full.isAuto == '0' && full.isLock == '1'){
								   return "是";
							   }
							   return "否";
						   }
					   },
					   { 
						   "title" : "能否获取",
						   "data" : "openflag",
						   "width" : "5%",
						   "render": function(data,type,full){
							   var dom = $('<div><span class="onoffswitch" onclick="$(this).find(\'input\').click();">'
									   +'<input type="checkbox" name="onoffswitch" class="onoffswitch-checkbox"  id=' + '"' + full.dataBatchUuid + full.departmentUuid +'"' +  ' '
									   +' onchange=changeState(this.id,"'+ full.dataBatchUuid  +'","'+ full.departmentUuid +'")>'
									   +'<label class="onoffswitch-label" for="myonoffswitch"> <span class="onoffswitch-inner" data-swchon-text="开启" data-swchoff-text="关闭">'
									   +'</span> <span class="onoffswitch-switch"></span> </label> </span><div>');
							   if(full.openflag == "1")
								   dom.find("input").attr("checked","checked");
// 							   	   dom.find("span").eq(0).before("<i class='fa fa-unlock-alt' title='解锁' style='margin-right:3px;'></i>");
							   if(full.openflagenable){
								   dom.find("input").attr("disabled","disabled");
// 								   dom.find("span").eq(0).before("<i class='fa fa-lock' title='已锁定' style='margin-right:3px;'></i>");
							   }
							   return dom.html();
						   }
					   },
					   {
						   "title" : "操作",
						   "data" : "null",
						   "orderable":false,
						   "render": function(data,type,full){
							   if(full.isAuto == '0' && full.isLock == '1'){
								   return "<a onclick=getItem('"+full.dataBatchUuid+"','"+full.departmentUuid+"','"+full.singleLimit+"','"+full.dayLimit+"','"+full.totalLimit+"','"+(full.batchDataCount - full.dataCount - full.ownCount)+"','"+ (full.batchDataCount - full.batchOwnCount) +"');>获取</a>";
							   }
							   var get = "";
							   if (full.isAuto == '0') {
								   get = "<a onclick=getItem('"+full.dataBatchUuid+"','"+full.departmentUuid+"','"+full.singleLimit+"','"+full.dayLimit+"','"+full.totalLimit+"','"+(full.batchDataCount - full.dataCount - full.ownCount)+"','"+ (full.batchDataCount - full.batchOwnCount) +"');>获取</a>&nbsp;&nbsp;";
							   }
							   return get +
							   "<a onclick=returnItem('"+full.dataBatchUuid+"','"+full.departmentUuid+"','"+(full.dataCount)+"');>归还</a>&nbsp;&nbsp;"+
							   "<a onclick=allotItem('"+full.dataBatchUuid+"','"+full.departmentUuid+"','"+full.dataCount+"');>分配到人</a>&nbsp;&nbsp;"+
							   "<a onclick=allotDeptItem('"+full.dataBatchUuid+"','"+full.departmentUuid+"','"+full.dataCount+"');>分配到部门</a>&nbsp;&nbsp;"+
							   "<a onclick=getbackItem('"+full.dataBatchUuid+"','"+full.departmentUuid+"','"+(full.ownCount)+"');>从人回收</a>&nbsp;&nbsp;"+
							   "<a onclick=getbackdept('"+full.dataBatchUuid+"','"+full.departmentUuid+"','"+(full.ownCount)+"');>从部门回收</a>";
						   }
					   }
					],
			});
			
			
			 var displayDom = $("<div><div><label style='margin-left:60px;width:150px;'><a>部门内数据量: </a></label><span></span></div><div><label style='margin-left:60px;width:150px;'><a>二次领用的数据量: </a></label><span></span></div><div><label style='margin-left:60px;width:150px;'><a>意向客户量: </a></label><span></span></div><div><label style='margin-left:60px;width:150px;'><a>成交客户量: </a></label><span></span></div><div><label style='margin-left:60px;width:150px;'><a>废号量: </a></label><span></span></div><div><label style='margin-left:60px;width:150px;'><a>黑名单号码量: </a></label><span></span></div></div>");
			    function format ( d , rowData) {
					var tempDom = displayDom.clone();
					var tempA = tempDom.find("a");
					var temp = tempDom.find("span");
					tempA.eq(0).attr("onclick", "getDataCount(null,'" + rowData.dataBatchUuid + "','" + rowData.departmentUuid + "','" + rowData.isLock +"')" );
					tempA.eq(1).attr("onclick", "getOwnCount(null,'" + rowData.dataBatchUuid + "','" + rowData.departmentUuid + "','" + rowData.isLock +"')" );
					tempA.eq(2).attr("onclick", "getIntentCount(null,'" + rowData.dataBatchUuid + "','" + rowData.departmentUuid + "','" + rowData.isLock +"')" );
					tempA.eq(3).attr("onclick", "getCustomerCount(null,'" + rowData.dataBatchUuid + "','" + rowData.departmentUuid + "','" + rowData.isLock +"')" );
					tempA.eq(4).attr("onclick", "getAbandonCount(null,'" + rowData.dataBatchUuid + "','" + rowData.departmentUuid + "','" + rowData.isLock +"')" );
					tempA.eq(5).attr("onclick", "getBlackListCount(null,'" + rowData.dataBatchUuid + "','" + rowData.departmentUuid + "','" + rowData.isLock +"')" );
					temp.eq(0).text(d.dataCount?d.dataCount:0);
					temp.eq(1).text(d.ownCount?d.ownCount:0);
					temp.eq(2).text(d.intentCount?d.intentCount:0);
					temp.eq(3).text(d.customerCount?d.customerCount:0);
					temp.eq(4).text(d.abandonCount?d.abandonCount:0);
					temp.eq(5).text(d.blacklistCount?d.blacklistCount:0);
					return tempDom.html();
			    }
// 		    var detailRows = [];
	    
		    $('#dt_basic_deptdata tbody').on( 'click', 'tr td.details-control', function () {
		        var tr = $(this).closest('tr');
		        var row = dt.row( tr );
// 		        var idx = $.inArray( tr.attr('id'), detailRows );
	 
		        if ( row.child.isShown() ) {
		            row.child.hide();
		            tr.removeClass( 'shown' );
	 
		            // Remove from the 'open' array
// 		            detailRows.splice( idx, 1 );
		        }
		        else {
		            var rowData = row.data();
		            $.post(getContext() + "deptdata/dept/dataStat",{dataBatchUuid:rowData.dataBatchUuid, deptUuid:rowData.departmentUuid},function(data){
			            row.child(format(data,rowData)).show();
					},"json");
		            row.child("获取信息中...").show();
		            tr.addClass( 'shown' );
	 
		            // Add to the 'open' array
// 		            if ( idx === -1 ) {
// 		                detailRows.push( tr.attr('id') );
// 		            }
		        }
		    } );
	 
		    // On each draw, loop over the `detailRows` array and show any child rows
// 		    dt.on( 'draw', function () {
// 		        $.each( detailRows, function ( i, id ) {
// 		            $('#'+id+' td.details-control').trigger( 'click' );
// 		        } );
// 		    } );
		    
		    
		    $("#changeLimit").click(function(){
		    	var url = getContext() + "deptdata/dept/changeLimit";
				$.post(url, {}, function(data){
					$("#deptDataDiv").append(data);
				});
		    })
		    
		});
		
		$('#deptname').select2({
           allowClear : true,
           width: '99%'
        });
		
		function changeState(id, batchUuid, deptUuid){
// 			var checked = $("#" + id).is(":checked");
			$.post(getContext() + "deptdata/dept/updateOpenFlag",{batchUuid:batchUuid, deptUuid:deptUuid},function(data){
				if(!data.success){
					$.smallBox({
	                    title : "操作提示",
	                    content : "<i class='fa fa-clock-o'></i><i>"+ data.msg +"</i>",
	                    color : "#C79121",
	                    iconSmall : "fa fa-times fa-2x fadeInRight animated",
	                    timeout : 5000
	                });
				}
				getDeptDataResult();
			},"json");
		};
		
		/* 添加公共查询方法 */
		function getResult(){
			getDeptDataResult();
		}
		
		// 刷新
		function getDeptDataResult(){
			$('#dt_basic_deptdata').DataTable().ajax.reload(null,false);
		};
		
		$(function() {
			document.onkeydown = function(e) {
				var ev = document.all ? window.event : e;
				if (ev.keyCode == 13) {
					if(typeof(getDeptDataResult) == 'function'){
						getDeptDataResult();
					}
				}
			}
		});

		// 分配到人的数据
		function allotItem(dataBatchUuid, deptUuid, dataCount){
			$('#deptDataDiv').empty();
	        var url = getContext() + "deptdata/dept/allotData";
			$.post(url, {dataBatchUuid:dataBatchUuid,deptUuid:deptUuid, dataCount:dataCount}, function(data){
				$("#deptDataDiv").append(data);
			});
		};
		
		// 分配到部门的数据
		function allotDeptItem(dataBatchUuid, deptUuid, dataCount){
			$('#deptDataDiv').empty();
	        var url = getContext() + "deptdata/dept/allotDeptData";
			$.post(url, {dataBatchUuid:dataBatchUuid,deptUuid:deptUuid, dataCount:dataCount}, function(data){
				$("#deptDataDiv").append(data);
			});
		};
		
		// 归还数据
		function returnItem(dataBatchUuid, deptUuid, totalLimit){
			$('#deptDataDiv').empty();
	        var url = getContext() + "deptdata/dept/returnData";
			$.post(url, {dataBatchUuid:dataBatchUuid,deptUuid:deptUuid,totalLimit:totalLimit}, function(data){
				$("#deptDataDiv").append(data);
			});
		};
		
		// 获取数据
		function getItem(dataBatchUuid, deptUuid, singleLimit, dayLimit, totalLimit, dataCount, restData){
			$('#deptDataDiv').empty();
	        var url = getContext() + "deptdata/dept/achieveData";
			$.post(url, {dataBatchUuid:dataBatchUuid,deptUuid:deptUuid,singlelimit:singleLimit, daylimit:dayLimit, totallimit:totalLimit, dataCount:dataCount, restData:restData}, function(data){
				$("#deptDataDiv").append(data);
			});
		};
		
		// 回收人员数据
		function getbackItem(dataBatchUuid, deptUuid, ownCount){
			$('#deptDataDiv').empty();
	        var url = getContext() + "deptdata/dept/newCollectData";
			$.post(url, {dataBatchUuid:dataBatchUuid,departmentUuid:deptUuid,ownCount:ownCount}, function(data){
				$("#deptDataDiv").append(data);
			});
		}
		
		// 回收部门数据
		function getbackdept(batchUuid, deptUuid, ownCount){
			$('#deptDataDiv').empty();
	        var url = getContext() + "deptdata/dept/collectDeptData";
			$.post(url, {batchUuid:batchUuid, deptUuid:deptUuid}, function(data){
				$("#deptDataDiv").append(data);
			});
		}
		
		// 点击部门内部数量
		function getDataCount(batchname, dataBatchUuid, deptUuid, islock){

			// 弹窗样式
			$('#deptDataDiv').empty();
	        var url = getContext() + "deptdata/dept/getDataCount";
			$.post(url, {dataBatchUuid:dataBatchUuid,deptUuid:deptUuid, islock:islock}, function(data){
				$("#deptDataDiv").append(data);
			});
			
			// tab样式
// 			var d = JSON.stringify({dataBatchUuid:dataBatchUuid,deptUuid:deptUuid});
			
// 			var s = new Object();
// 			s.dataBatchUuid = dataBatchUuid;
// 			s.deptUuid = deptUuid;
			
// 			addTab('部门内数据' , 'deptGetDataCount', s , "");
			
		};
		
		// 点击二次领用的数据量
		function getOwnCount(batchname, dataBatchUuid, deptUuid, islock){
			// 弹窗样式 
			$('#deptDataDiv').empty();
	        var url = getContext() + "deptdata/dept/getOwnCount";
			$.post(url, {dataBatchUuid:dataBatchUuid,deptUuid:deptUuid, islock:islock}, function(data){
				$("#deptDataDiv").append(data);
			});

// 			var d = JSON.stringify({dataBatchUuid:dataBatchUuid,deptUuid:deptUuid});
			
// 			var s = new Object();
// 			s.dataBatchUuid = dataBatchUuid;
// 			s.deptUuid = deptUuid;
			
// 			addTab('二次领用数据', 'deptGetOwnCount', s , "");

		};
		
		// 点击废号量
		function getAbandonCount(batchname, dataBatchUuid, deptUuid){

			// 弹窗样式 
			$('#deptDataDiv').empty();
	        var url = getContext() + "deptdata/dept/getAbandonCount";
			$.post(url, {dataBatchUuid:dataBatchUuid,deptUuid:deptUuid}, function(data){
				$("#deptDataDiv").append(data);
			});
			
// 			var s = new Object();
// 			s.dataBatchUuid = dataBatchUuid;
// 			s.deptUuid = deptUuid;
			
// 			addTab('废号信息' , 'getAbandonCount', s , "");
		};
		
		// 点击黑名单
		function getBlackListCount(batchname, dataBatchUuid, deptUuid){

			// 弹窗样式 
			$('#deptDataDiv').empty();
	        var url = getContext() + "deptdata/dept/getBlackListCount";
			$.post(url, {dataBatchUuid:dataBatchUuid,deptUuid:deptUuid}, function(data){
				$("#deptDataDiv").append(data);
			});
			
// 			var s = new Object();
// 			s.dataBatchUuid = dataBatchUuid;
// 			s.deptUuid = deptUuid;
			
// 			addTab('黑名单信息' , 'getBlackListCount', s , "");
		};
		
		// 点击意向客户
		function getIntentCount(batchname, dataBatchUuid, deptUuid){

			// 弹窗样式 
			$('#deptDataDiv').empty();
	        var url = getContext() + "deptdata/dept/getIntentCount";
			$.post(url, {dataBatchUuid:dataBatchUuid,deptUuid:deptUuid}, function(data){
				$("#deptDataDiv").append(data);
			});
			
// 			var s = new Object();
// 			s.dataBatchUuid = dataBatchUuid;
// 			s.deptUuid = deptUuid;
			
// 			addTab('意向客户量' , 'getIntentCount', s , "");
		};
		
		//成交客户量
		function getCustomerCount(batchname, dataBatchUuid, deptUuid){

			// 弹窗样式 
			$('#deptDataDiv').empty();
	        var url = getContext() + "deptdata/dept/getCustomerCount";
			$.post(url, {dataBatchUuid:dataBatchUuid,deptUuid:deptUuid}, function(data){
				$("#deptDataDiv").append(data);
			});
			
		};
		
</script>
	
