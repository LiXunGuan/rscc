var operate_log_table = $('#dt_basic_oplog').dataTable({
		 "oLanguage": {
                "sUrl": getContext() + "/public/dataTables.cn.txt"
            },
		"sPaginationType" : "bootstrap_full",
		"bProcessing" : true,
		"sAjaxSource" : getContext() + "/sysoperatelog/data",
		"fnServerData" : function(sSource, aoData, fnCallback,oSettings) {
			//用户名
			aoData.push({
				"name" : "username",
				"value" : $("#username").val()
			});
			//ip地址
			aoData.push({
				"name" : "ip",
				"value" : $("#ip").val()
			});
			
			//开始时间
			aoData.push({
				"name" : "starttime",
				"value" : $("#starttime").val()
			});
			
			//结束时间
			aoData.push({
				"name" : "endtime",
				"value" : $("#endtime").val()
			});
			
			oSettings.jqXHR = $.ajax({
				"dataType" : 'json',
				"async" : false,
				"type" : "POST",
				"url" : sSource,
				"data" : aoData,
				"success" : fnCallback
			});
		},
		
		"iDisplayLength" :10,
		"aLengthMenu": [[10, 50, 100 ], [10, 50, 100]],  //设置显示条数
		"bServerSide" : true,
		"iSortingCols" : 10,
		"aaSorting" : [[2,'desc']],
		"bFilter" : false,
		"aoColumnDefs" : [/*默认排序的列*/
				{"sTitle" : "id","iDataSort": 0,"aTargets" : [ 0 ],"bVisible" : false},
				{"sTitle" : "操作用户","aTargets" : [ 1 ]},
				{"sTitle" : "操作时间","aTargets" : [ 2 ]},
				{"sTitle" : "IP地址","aTargets" : [ 3 ]},
				{"sTitle" : "修改操作","aTargets" : [ 4 ]},
			],
		"aoColumns" : [ 
				{"mData" : "id","sWidth" : "10%"},
				{"mData" : "user_id" ,"sWidth" : "10%"},
				{"mData" : "op_date","sWidth" : "20%"},
				{"mData" : "ip","sWidth" : "20%"},
				{"mData" : "operate" ,"sWidth" : "30%"}, 
			],
				
	});


	/**
	 * 查询
	 */
	$('#doSearch').click(function(){
		
		operate_log_table.fnReloadAjax();
		
	});
	
	$('#starttime').datepicker({
		dateFormat : 'yy-mm-dd'
	});
	
	$('#endtime').datepicker({
		dateFormat : 'yy-mm-dd '
	});
	
