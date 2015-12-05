var oTable = $('#dt_basic_select')
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
					"lengthMenu" : [ 10, 15, 20, 25, 30 ],
					"infoCallback":function(settings, start, end, max, total, pre){
				    	return "显示第 " + start + " 至 " + end + " 项结果，共 " + total + " 项，已选中 " + getCheckNumData() + " 项";
				    },
					"language" : {
						"url" : getContext() + "/public/dataTables.cn.txt"
					},
					"ajax" : {
						"url" : getContext() + "/data/groupCall/review",
						"type" : "POST",
						"data" : function(param) {
							param.groupcall_id = $("#wzgroupcall_id").val();
							param.data_phone = $("#wzdata_phone").val();
							param.container_name = $("#wzcontainer_name").val();
							param.call_flag = $("#wzcall_flag").val();
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
								"render" : function(data, type, full) {
									if (itemCheckList[full.data_phone]) {
										return '<input type="checkbox" checked="checked" id="dck'
												+ full.data_phone
												+ '" onclick="docheck('
												+ "'" + full.data_phone + "'" +  ')"  />';
									} else {
										return '<input type="checkbox" id="dck'
												+ full.data_phone
												+ '" onclick="docheck('
												+ "'" + full.data_phone + "'" +  ')"  />';
									}
								}
							},
							{ "title" : "号码","data" : "null","defaultContent":"无号码","render":function(a,d,f){
								
								return window.parent.hiddenPhone(f.data_phone);
							}},
							{ "title" : "来源","data" : "container","defaultContent":"无来源"},
							{ "title" : "状态","data" : "call_flag","defaultContent":"否", "render":function(a,d,f){
								if(f.call_flag==0){
									return "未开始";
								} else if (f.call_flag==1) {
									return "待呼叫";
								} else if (f.call_flag==2) {
									return "未接通";
								} else if (f.call_flag==3) {
									return "双方接通";
								} else if (f.call_flag==4) {
									return "单方接通";
								} else if (f.call_flag==5) {
									return "呼叫异常";
								} 
							}},
							{
								"title" : "操作",
								"data" : "null",
								"render" : function(data, type, full) {
									return  "<a onclick='deleteOne(\""
											+ full.data_phone + "\");'>删除</a>";
								}
							} ],
				});
