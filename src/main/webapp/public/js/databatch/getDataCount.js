var oTable = $('#dt_basic_getdatacount')
		.DataTable(
				{
					"dom" : "t"
							+ "<'dt-toolbar-footer'<'col-sm-6 col-xs-12'i><'col-xs-12 col-sm-6 text-right'lp>>",
					"autoWidth" : false,
					"ordering" : true,
					"serverSide" : true,
					"processing" : true,
					"searching" : false,
					"pageLength" : 10,
					"lengthMenu" : [10, 15, 20, 25, 30 ],
					"infoCallback":function(settings, start, end, max, total, pre){
				    	return "显示第 " + start + " 至 " + end + " 项结果，共 " + total + " 项，已选中 " + getCheckNum() + " 项";
				    },
					"language" : {
						"url" : getContext() + "public/dataTables.cn.txt"
					},
					"ajax" : {
						"url" : getContext() + "deptdata/dept/getDataCountData",
						"type" : "POST",
						"data" : function(param) {
							param.deptUuid = $("#deptUuid").val();
							param.batchUuid = $("#batchUuid").val();
						}
					},
					"paging" : true,
					"pagingType" : "bootstrap",
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
								"render": function (data, type, full) {
									if(checklist[full.uid]){
						                return '<input type="checkbox" checked="checked" id="ck'+full.uid+'" onclick="docheck(\''+full.uid+'\')"  />';
						            }else{
						            	return '<input type="checkbox" id="ck'+full.uid+'" onclick="docheck(\''+full.uid+'\')" />';
						            }
								}
							},
							{ "title" : "批次名","data" : "batchname"},
							{ "title" : "电话号码","data" : "phoneNumber","render":function(d,t,f){
								return window.parent.hiddenPhone(f.phoneNumber);
							}},							{ "title" : "数据信息","data" : "json"},
							{ "title" : "领用部门","data" : "deptname"},
							{ "title" : "占用时间","data" : "ownDepartmentTimestamp"},
//							{ "title" : "群呼锁定","data" : "isLock"},
//							{ "title" : "锁定时间","data" : "lockTimestamp"},
						],
				});
