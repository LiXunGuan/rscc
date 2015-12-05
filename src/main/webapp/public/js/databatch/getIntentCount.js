var oTable = $('#dt_basic_getIntentCount')
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
				    	return "显示第 " + start + " 至 " + end + " 项结果，共 " + total + " 项";
				    },
					"language" : {
						"url" : getContext() + "public/dataTables.cn.txt"
					},
					"ajax" : {
						"url" : getContext() + "deptdata/dept/getIntentCountData",
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
							{ "title" : "批次名","data" : "batchname"},
							{ "title" : "电话号码","data" : "null","render":function(d,t,f){
								return window.parent.hiddenPhone(f.phoneNumber);
							}},
							{ "title" : "数据信息","data" : "json"},
							{ "title" : "领用部门","data" : "deptname"},
							{ "title" : "占用时间","data" : "ownDepartmentTimestamp"},
							{ "title" : "领用人","data" : "username"},
							{ "title" : "领用时间","data" : "ownUserTimestamp"},
							{ "title" : "电话次数","data" : "callcount"},
							{ "title" : "最后一次电话结果","data" : "lastCallResult"},
							{ "title" : "最后一次电话时间","data" : "lastCallTime"},
							{ "title" : "意向类型","data" : "intentType"},
							{ "title" : "意向时间","data" : "intentTimestamp"}
						],
				});
