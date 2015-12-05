var oTable = $('#dt_basic_look')
		.DataTable(
				{
					"dom" : "t<'dt-toolbar-footer'<'col-sm-6 col-xs-12'i><'col-xs-12 col-sm-6 text-right'lp>>",
					"autoWidth" : false,
					"ordering" : true,
					"serverSide" : true,
					"processing" : true,
					"searching" : false,
					"pageLength" : 10,
					"lengthMenu" : [ 10, 15, 20, 25, 30 ],
					"infoCallback":function(settings, start, end, max, total, pre){
				    	return "显示第 " + start + " 至 " + end + " 项结果，共 " + total + " 项，已选中 " + getCheckNum() + " 项";
				    },
					"language" : {
						"url" : getContext() + "public/dataTables.cn.txt"
					},
					"ajax" : {
						"url" : getContext() + "databatch/data/import",
						"type" : "POST",
						"data" : function(param) {
							param.batchUuid = $("#batchUuid").val();
							param.phoneNumber = $("#phoneNumber").val();
							param.ownDepartment = $("#ownDepartment").val();
							param.ownUser = $("#ownUser").val();
							param.isFrozen = $("#invalid").val()=="isFrozen"?1:0;
							param.isAbandon = $("#invalid").val()=="isAbandon"?1:0;
							param.isBlacklist = $("#invalid").val()=="isBlacklist"?1:0;
							param.intentType = $("#intentType").val();
							param.customerUuid = $("#customerUuid").val();
							param.operation = $('#operation').val();
						}
					},
					"paging" : true,
					"pagingType" :"bootstrap",
					"lengthChange" : true,
					"order" : [ [ "1", "desc" ] ],
					"columns" : [
							{
								"width": "10%",
								"title" : '<div class="btn-group">'
										+ ' <a class="btn btn-primary" href="javascript:void(0);" onclick="docheckall()">全选</a>'
										+ ' <a class="btn btn-primary dropdown-toggle" data-toggle="dropdown" href="javascript:void(0);"><span class="caret"></span></a><ul class="dropdown-menu">'
										+ ' <li><a href="javascript:void(0);"onclick="docheckall()" >全选所有</a></li>'
										+ '<li><a href="javascript:void(0);" onclick="docheckallpage()">全选本页</a></li>'
										+ '<li><a href="javascript:void(0);" onclick="docancelAll()">取消所有选择</a></li>'
										+ '</ul></div>',
								"sortable" : false,
								"data" : null,
								"render" : function(data, type, full) {
									if (dataCheckList[full.uid]) {
										return '<input type="checkbox" checked="checked" id="dck'
												+ full.uid
												+ '" onclick="docheck('
												+ "'" + full.uid + "'" +  ')"  />';
									} else {
										return '<input type="checkbox" id="dck'
												+ full.uid
												+ '" onclick="docheck('
												+ "'" + full.uid + "'" +  ')"  />';
									}
								}
							},
							{ "title" : "批次id","data" : "batchUuid","defaultContent":"","visible":false},
							{ "title" : "批次名","data" : "dataBatchName","defaultContent":"无","orderable":false},
							{ "title" : "电话号码","data" : "phoneNumber","defaultContent":"无",
								"render":function(data,type,full){
									return window.parent.hiddenPhone(full.phoneNumber);
							  }
							},
							{ "title" : "归属部门","data" : "ownDepartment","defaultContent":"无"},
							{ "title" : "数据 归属人","data" : "ownUser","defaultContent":"无"},
							{ "title" : "电话次数","data" : "callCount"},
							{ "title" : "意向类型","data" : "intentType","defaultContent":"无"},
							{ "title" : "是否废弃","data" : "isAbandon","render": function(data,type,full){
								   if(full.isAbandon === "1"){
									   return "是";
								   }else{
									   return "否";
								   }
							   }},
							{ "title" : "是否黑名单","data" : "isBlacklist","render": function(data,type,full){
								   if(full.isBlacklist === "1"){
									   return "是";
								   }else{
									   return "否";
								   }
							   }},
							{ "title" : "是否冻结","data" : "isFrozen","render": function(data,type,full){
								
								   if(full.isFrozen === "1"){
									   return "是";
								   }else{
									   return "否";
								   }
							   }},
						 /*  { "title" : "客户信息","data" : "null","defaultContent":"无"},
							{ "title" : "数据 归属人","data" : "ownUser","defaultContent":"无"},*/
						/*	{
								"title" : "操作",
								"data" : "null",
								"render" : function(data, type, full) {
									return  "<a onclick='deleteOne(\""
											+ full.uid + "\");'>删除</a>";
								}
							} */
							],
				});
