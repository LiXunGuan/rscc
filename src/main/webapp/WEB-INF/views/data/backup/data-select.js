var oTable = $('#dt_basic_select')
		.DataTable(
				{
					"dom" : "t"
							+ "<'dt-toolbar-footer'<'col-sm-6 col-xs-12'i><'col-xs-12 col-sm-6 text-right'lp>>",
					"autoWidth" : true,
					"ordering" : true,
					"serverSide" : true,
					"processing" : true,
					"searching" : false,
					"pageLength" : 10,
					"lengthMenu" : [ 10, 15, 20, 25, 30 ],
					"language" : {
						"url" : getContext() + "/public/dataTables.cn.txt"
					},
					"ajax" : {
						"url" : getContext() + "/data/data/import",
						"type" : "POST",
						"data" : function(param) {
							param.itemTable = $("#currentDataTable").val();
							param.itemName = $("#itemName").val();
							param.itemPhone = $("#itemPhone").val();
							param.itemDescribe = $("#itemDescribe").val();
							param.itemAddress = $("#itemAddress").val();
						}
					},
					"paging" : true,
					"pagingType" : "bootstrap",
					"lengthChange" : true,
					"infoCallback":function(settings, start, end, max, total, pre){
				    	return "显示第 " + start + " 至 " + end + " 项结果，共 " + total + " 项，已选中 " + getCheckNum() + " 项";
				    },
					"order" : [ [ "0", "desc" ] ],
					"columns" : [
							{
								"title" : '<div class="btn-group">'
										+ ' <a class="btn btn-primary" href="javascript:void(0);" onclick="docheckall()">全选</a>'
										+ ' <a class="btn btn-primary dropdown-toggle" data-toggle="dropdown" href="javascript:void(0);"><span class="caret"></span></a><ul class="dropdown-menu">'
										+ ' <li><a href="javascript:void(0);"onclick="docheckall()" >全选所有</a></li>'
										+ '<li><a href="javascript:void(0);" onclick="docheckallpage()">全选本页</a></li>'
										+ '<li><a href="javascript:void(0);" onclick="docancelAll()">取消所有选择</a></li>'
										+ '</ul></div>',
								"sortable" : false,
								"data" : "null",
								"render" : function(data, type, full) {
									if (checklist[full.uid]) {
										return '<input type="checkbox" checked="checked" id="ck'
												+ full.uid
												+ '" onclick="docheck('
												+ full.uid + ')"  />';
									} else {
										return '<input type="checkbox" id="ck'
												+ full.uid
												+ '" onclick="docheck('
												+ full.uid + ')" />';
									}
								},
								"width" : "10%"
							},
							{ "title" : "条目名称","data" : "itemName","defaultContent":"无名"},
							{ "title" : "条目号码","data" : "null","defaultContent":"无号码",
								"render":function(data,type,full){
									return window.parent.hiddenPhone(full.itemPhone);
							  }
							},
							{ "title" : "条目状态","data" : "itemStat","defaultContent":"未导入"},
							{ "title" : "条目描述","data" : "itemDescribe","defaultContent":"无描述"},
							{ "title" : "条目地址","data" : "itemAddress","defaultContent":"无地址"},
							{
								"title" : "操作",
								"data" : "null",
								"render" : function(data, type, full) {
									return "<a onclick='import(\""
											+ full.uid + "\");'>导入</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
											"<a onclick='delete(\""
											+ full.uid + "\");'>删除</a>";
								}
							} ],
				});
