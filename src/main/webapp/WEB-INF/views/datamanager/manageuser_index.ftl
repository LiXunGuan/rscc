	<div id="manageUserDiv"></div>
	<div id="manageUserContent" style="">
			<section style="margin-bottom: 2px;">
				<label class="btn btn-sm btn-primary" id="batchUpdataId" onclick="batchUpdata()">批量修改上限</label>
			</section>
			</div>
			<div class="row">
				<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="jarviswidget jarviswidget-color-darken" data-widget-editbutton="false">
						<header>
							<span class="widget-icon"><i class="fa fa-table"></i></span>
							<h2>用户任务管理</h2>
						</header>
						<div>
							<div class="widget-body no-padding">
								<div class="widget-body-toolbar">
									<div class="smart-form">
										<section>
											<div class="row" style="margin-top: 5px;">
												<label class="label col">用户名</label>
												<label class="input col col-2">
													<input type="text" name="loginName" class="form-control" id="loginName" value="" placeholder="用户名" />
												</label>
												<label class="label col">姓名</label>
												<label class="input col col-2">
													<input type="text" name="userName" class="form-control" id="userName" value="" placeholder="姓名" />
												</label>
												<label class="label col">部门名</label>
												<label class="input col col-2">
													<select id="deptname" name="deptname"  onchange="getResult();">
						    							<option value="">请选择</option>
						    							<#if depts??>
							    							<#list depts as dept>
																<option value="${dept.datarangeName}">${dept.datarangeName}</option>
															</#list>
						    							</#if>
						    						</select>
												</label>
												<label class="btn btn-sm btn-primary" onclick="getResult();">&nbsp;查&nbsp;&nbsp;&nbsp;询&nbsp;</label>
											</div>
										</section>
										<table id="dt_basic_manageuser" class="table table-striped table-bordered table-hover"  data-order='[[ 0, "asc" ]]' width="100%"></table>
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
	
		var checklist={};
		var condition = {};
		var currentItem = "";
		
		function removeThis(t,i){
		     $(t).parent().remove();
	
		    checklist[i]=false;
		    $('#ck'+i).click();
		}
	
		function docheckall(){
	
		    var url = getContext()+'userManage/checkAll';
		    $.post(url, condition, function(data){
		    	checklist = {};
		    	
				$("#checkInfo").text(data.alltask.length);
		        for(var i in data.alltask){
	                checklist[data.alltask[i]]=true;
		        }
		        docheckallpage();
		    },"json");
		}
	
		function docheckallpage(){
	
		    $("[id^='ck']").each(function(){
		        if(!$(this).is(':checked')){
		            $(this).click();
		        }
		    });
		}
	
		function docancelAll(){
	
		    $("[id^='ck']").each(function(){
		        if($(this).is(':checked')){
		            $(this).click();
		        }
		    });
		    checklist={};
			$("#checkInfo").text(0);				
		}
		
		function docancel(){
		    $("[id^='ck']").each(function(){
		        if($(this).is(':checked')){
		            $(this).click();
		        }
		    });
		}
	
		function docheck(uid){
	
		    if($("#ck"+uid).is(':checked')){
		        checklist[uid]=true;
		        currentItem = uid;
		    }else{
		        checklist[uid]=false;
		    }
		    addCheckInfo();
		}
		
		function getCheckNum(){
			var num=0;
			for(var i in checklist) {
				if(checklist[i]==true)
					num++;
			}
			return "<span id='checkInfo'>" + num + "</span>";
		}
		
		function addCheckInfo(){
			var num=0;
			for(var i in checklist) {
				if(checklist[i]==true)
					num++;
			}
			$("#checkInfo").text(num);
		}

		$(document).ready(function() {
			$('#dt_basic_manageuser').dataTable({
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
			    	"url" : getContext() + "userManage/data",
			    	"type":"POST",
			    	"data" :function(param){
			    		param.batchname = $("#batchname").val();
			    		param.username = $("#userName").val();
			    		param.loginname = $("#loginName").val();
			    		param.deptname = $("#deptname").val();
			    	}
			    },
			    "infoCallback":function(settings, start, end, max, total, pre){
			    	return "显示第 " + start + " 至 " + end + " 项结果，共 " + total + " 项，已选中 " + getCheckNum() + " 项";
			    },
			 	"paging" :true,
				"pagingType" :"bootstrap",
				"lengthChange" : true,
				"order" : [[ "1", "desc"]],
				"columns" : [
	   						{
							"title" : '<div class="btn-group">'
									+ ' <a class="btn btn-sm btn-primary dropdown-toggle" data-toggle="dropdown" href="javascript:void(0);">选择<span class="caret"></span></a><ul class="dropdown-menu">'
									+ ' <li><a href="javascript:void(0);"onclick="docheckall()" >全选所有</a></li>'
									+ '<li><a href="javascript:void(0);" onclick="docheckallpage()">全选本页</a></li>'
									+ '<li><a href="javascript:void(0);" onclick="docancelAll()">取消所有</a></li>'
									+ '</ul></div>',
							"sortable" : false,
							"data" : "null",
							"render" : function(data, type, full) {
								if(checklist[full.uid]) {
									return '<input type="checkbox" checked="checked" id="ck'
											+ full.uid
											+ '" onclick="docheck('
											+ "'" + full.uid + "'" + ')"  />';
								} else {
									return '<input type="checkbox" id="ck'
											+ full.uid
											+ '" onclick="docheck('
											+ "'" + full.uid + "'" + ')" />';
								}
							},
							"width" : "30px"
						},
					   { "title" : "用户名", "data" : "loginName"},
					   { "title" : "姓名", "data" : "userName","width":"100"},
					   { "title" : "部门", "data" : "deptName","width":"100"},
					   { "title" : "总任务上限", "data" : "totalLimit"},
					   { "title" : "单日任务上限", "data" : "dayLimit"},
					   { "title" : "单次任务上限", "data" : "singleLimit"},
					   { "title" : "意向客户上限", "data" : "intentLimit"},
					   { "title" : "任务量", "data" : "dataCount"},
					   { "title" : "黑名单量", "data" : "blacklistCount"},
					   { "title" : "意向客户量", "data" : "intentCount"},
					   { "title" : "废号量", "data" : "abandonCount"},
					   { "title" : "成交客户量", "data" : "customerCount"},
// 					   { "title" : "转共享量", "data" : "shareCount"},
// 					   { "title" : "废号量", "data" : "null","render":function(data,type,full){
// 						   return "<a onclick=getAbandonCount('"+full.batchname+"','"+full.dataBatchUuid+"','"+full.departmentUuid+"');>"+ full.abandonCount +"</a>";
// 					   }},
					   { 
						   "title" : "操作",
						   "data" : "null",
						   "orderable":false,
						   "width":"60",
						   "render": function(data,type,full){
							   return "<a onclick=changeLimit('"+full.uid+"');>修改</a>&nbsp;&nbsp;";
						   }
					   }
					],
			});
		});
		
		$('#deptname').select2({
           allowClear : true,
           width: '99%'
        });
		
		function changeLimit(userUuid) {
			 var url = getContext() + "userManage/changeLimit";
			$.post(url, {userUuid:userUuid}, function(data){
				$("#manageUserDiv").append(data);
			});
		}
		
		// 刷新
		function getResult(){
			$('#dt_basic_manageuser').DataTable().ajax.reload(null,false);
		}
		
		//批量修改上限
		function batchUpdata(){
			$('#manageUserDiv').empty();
			var url = getContext() + "userManage/batChange";
			$.post(url, function(data){
				$("#manageUserDiv").append(data);
			});
		}
		// 分配数据
		function allotItem(dataBatchUuid, deptUuid){
			$('#manageUserDiv').empty();
	        var url = getContext() + "deptdata/dept/allotData";
			$.post(url, {dataBatchUuid:dataBatchUuid,deptUuid:deptUuid}, function(data){
				$("#manageUserDiv").append(data);
			});
		}
		
		// 归还数据
		function returnItem(dataBatchUuid, deptUuid, totalLimit){
			$('#manageUserDiv').empty();
	        var url = getContext() + "deptdata/dept/returnData";
			$.post(url, {dataBatchUuid:dataBatchUuid,deptUuid:deptUuid,totalLimit:totalLimit}, function(data){
				$("#manageUserDiv").append(data);
			});
		}
		
		// 获取数据
		function getItem(dataBatchUuid, deptUuid, singleLimit){
			$('#manageUserDiv').empty();
	        var url = getContext() + "deptdata/dept/achieveData";
			$.post(url, {dataBatchUuid:dataBatchUuid,deptUuid:deptUuid,totalLimit:singleLimit}, function(data){
				$("#manageUserDiv").append(data);
			});
		}
		
		// 点击部门内部数量
		function getDataCount(batchname, dataBatchUuid, deptUuid){

			// 弹窗样式
			$('#manageUserDiv').empty();
	        var url = getContext() + "deptdata/dept/getDataCount";
			$.post(url, {dataBatchUuid:dataBatchUuid,deptUuid:deptUuid}, function(data){
				$("#manageUserDiv").append(data);
			});
			
			// tab样式
// 			var d = JSON.stringify({dataBatchUuid:dataBatchUuid,deptUuid:deptUuid});
			
// 			var s = new Object();
// 			s.dataBatchUuid = dataBatchUuid;
// 			s.deptUuid = deptUuid;
			
// 			addTab('部门内数据' , 'deptGetDataCount', s , "");
			
		}
		
		// 点击二次领用的数据量
		function getOwnCount(batchname, dataBatchUuid, deptUuid){
			
			// 弹窗样式 
			$('#manageUserDiv').empty();
	        var url = getContext() + "deptdata/dept/getOwnCount";
			$.post(url, {dataBatchUuid:dataBatchUuid,deptUuid:deptUuid}, function(data){
				$("#manageUserDiv").append(data);
			});

// 			var d = JSON.stringify({dataBatchUuid:dataBatchUuid,deptUuid:deptUuid});
			
// 			var s = new Object();
// 			s.dataBatchUuid = dataBatchUuid;
// 			s.deptUuid = deptUuid;
			
// 			addTab('二次领用数据', 'deptGetOwnCount', s , "");

		}
		
		// 点击废号量
		function getAbandonCount(batchname, dataBatchUuid, deptUuid){

			// 弹窗样式 
			$('#manageUserDiv').empty();
	        var url = getContext() + "deptdata/dept/getAbandonCount";
			$.post(url, {dataBatchUuid:dataBatchUuid,deptUuid:deptUuid}, function(data){
				$("#manageUserDiv").append(data);
			});
			
// 			var s = new Object();
// 			s.dataBatchUuid = dataBatchUuid;
// 			s.deptUuid = deptUuid;
			
// 			addTab('废号信息' , 'getAbandonCount', s , "");
		}
		
		// 点击黑名单
		function getBlackListCount(batchname, dataBatchUuid, deptUuid){

			// 弹窗样式 
			$('#manageUserDiv').empty();
	        var url = getContext() + "deptdata/dept/getBlackListCount";
			$.post(url, {dataBatchUuid:dataBatchUuid,deptUuid:deptUuid}, function(data){
				$("#manageUserDiv").append(data);
			});
			
// 			var s = new Object();
// 			s.dataBatchUuid = dataBatchUuid;
// 			s.deptUuid = deptUuid;
			
// 			addTab('黑名单信息' , 'getBlackListCount', s , "");
		}
		
		// 点击意向客户
		function getIntentCount(batchname, dataBatchUuid, deptUuid){

			// 弹窗样式 
			$('#manageUserDiv').empty();
	        var url = getContext() + "deptdata/dept/getIntentCount";
			$.post(url, {dataBatchUuid:dataBatchUuid,deptUuid:deptUuid}, function(data){
				$("#manageUserDiv").append(data);
			});
			
// 			var s = new Object();
// 			s.dataBatchUuid = dataBatchUuid;
// 			s.deptUuid = deptUuid;
			
// 			addTab('意向客户量' , 'getIntentCount', s , "");
		}
		
		//按下回车自动搜索
		$(function() {
			document.onkeydown = function(e) {
				var ev = document.all ? window.event : e;
				if (ev.keyCode == 13) {
					if(typeof(getResult) == 'function')
						getResult();
				}
			}
		});
		
</script>
	
