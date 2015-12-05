	
	var log_table = $('#dt_basic_log').dataTable({
		"oLanguage" : {
			"sUrl" : getContext() + "/public/dataTables.cn.txt"
		},
		"sPaginationType" : "bootstrap_full",
		"bProcessing" : true,
		"sAjaxSource" : getContext() + "/syslog/data",
		"fnServerData" : function(sSource, aoData, fnCallback, oSettings) {
			aoData.push({"name" : "seriesNumber","value" : $("#seriesNumber").val()});
			oSettings.jqXHR = $.ajax({
				"dataType" : 'json',
				"async" : false,
				"type" : "POST",
				"url" : sSource,
				"data" : aoData,
				"success" : fnCallback
			});
		},
	
		"iDisplayLength" : 10,
		"aLengthMenu" : [ [ 10, 50, 100 ], [ 10, 50, 100 ] ], // 设置显示条数
		"bServerSide" : true,
		"iSortingCols" : 10,
		"aaSorting" : [ [ 0, 'desc' ] ],
		"bFilter" : false,
		"aoColumnDefs" : [/* 默认排序的列 */
		{
			"sTitle" : "id",
			"iDataSort" : 0,
			"aTargets" : [ 0 ],
			"bVisible" : false
		},{
			"sTitle" : "序列号码",
			"aTargets" : [ 1 ]
		}, {
			"sTitle" : "指纹",
			"aTargets" : [ 2 ]
		},{
			"sTitle" : "ip地址",
			"aTargets" : [ 3 ]
		},{
			"sTitle" : "最后请求时间",
			"aTargets" : [ 4 ]
		},{
			"sTitle" : "最后请求成功时间",
			"aTargets" : [ 5 ]
		},{
			"sTitle" : "成功失败 / 次",
			"aTargets" : [ 6 ]
		},{
			"sTitle" : "操作",
			"bSortable" : false,
			"mRender" : function(data, type, full) {
				
				return '<a onclick="getDetail('+full.id+');">查看详情</a>&nbsp;&nbsp;<a onclick="getDelete(' + full.id + ');">删除</a>';
			},
			"aTargets" : [ 7 ]
		} ],
		"aoColumns" : [ {
			"mData" : "id",
			"sWidth" : "10%"
		},{
			"mData" : "series_number",
			"sWidth" : "20%"
		}, {
			"mData" : "fingerprint",
			"sWidth" : "20%"
		},{
			"mData" : "ip",
			"sWidth" : "10%"
		},{
			"mData" : "op_time",
			"sWidth" : "20%",
			"mRender" : function(data, type, full) {
				var isNumber = 0;
				isNumber = compareNow(full.op_time);
				if (isNumber==(0)) {  //三天和七天之间      紫色
					return '<font color="#D02090" >' + full.op_time + '</font>';
				} else if(isNumber==(1)){  //大于七天   
					return '<font color="black">' + full.op_time + '</font>';
				}else if(isNumber==(2)){  //  否则  黑色  
					return '<font color="red" >' + full.op_time + '</font>';
				}else{
					return '<font color="black" >' + full.op_time + '</font>';
				}
			}
		}, {
			"mData" : "lastSuccessTime",
			"bSortable" : false,
			"sWidth" : "10%"
		}, {
			"mData" : "is_success",
			"bSortable" : false,
			"sWidth" : "10%"
		}, {
			"mData" : null,
			"sWidth" : "10%"
		} ],
	
	});
	
	/**
	 * 点击查询时的方法
	 */
	$('#doSearch').click(function(){
		log_table.fnReloadAjax();
	});
	
	
	/**
	 * 查看某个地址的详情
	 * 
	 * @param id
	 */
	function getDetail(id) {
	
		$('#connect_detail_dialog').remove();
	
		var url = getContext() + "/syslog/getDetail";
	
		$.post(url, {
			id : id
		}, function(data) {
			$('#divAll').append(data);
		});
	}
	
	/**
	 * 删除日志信息
	 */
	function getDelete(id){
		
		var url = getContext() + "/syslog/getDelete";
		
		$.SmartMessageBox({
			title : "删除",
			content : "该操作将会删除该IP地址的所有日志信息，确定执行此操作？",
			buttons : '[No][Yes]'
		}, function(ButtonPressed) {
			if (ButtonPressed == "Yes") {
				$.post(url,{id:id},function(data){
					if(data.success){
						$.smallBox({
							title : "操作成功",
							content : "<i class='fa fa-clock-o'></i> <i>解除成功...</i>",
							color : "#659265",
							iconSmall : "fa fa-check fa-2x fadeInRight animated",
							timeout : 2000
						});
					}else{
						$.smallBox({
							title : "操作失败",
							content : "<i class='fa fa-clock-o'></i> <i>操作失败...</i>",
							color : "#C46A69",
							iconSmall : "fa fa-times fa-2x fadeInRight animated",
							timeout : 2000
						});
					}
					log_table.fnReloadAjax();
				},"json").error(function(){
						$.smallBox({
							title : "操作失败",
							content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>解除关联时出现异常...</i>",
							color : "#C46A69",
							iconSmall : "fa fa-times fa-2x fadeInRight animated"
						});
					});
			}
			if (ButtonPressed === "No") {
			
			}
			
			log_table.fnReloadAjax();
	
		});
		
	}
	
	
	/**
	 * 和现在时间比较是否超过三天
	 */
	function compareNow(opTime) {
		
		var returnValue = 1;
		
		var today = new Date();  //得到当前时间
		
		opTime = opTime.replace(/-/g,"/");	//操作时间
		
		var otime = new Date(opTime);
		
		var agoThreeDay = new Date(Date.parse(today.toString()) - 86400000*3);  //得到三天前时间
		
		var agoSevenDay = new Date(Date.parse(today.toString()) - 86400000*7);  //得到七天前时间
	
		if(otime <= agoSevenDay){
			
			returnValue = 0; //紫色
		}else if(otime >= agoThreeDay){
			
			returnValue = 1; //黑色
		}else if(otime < agoThreeDay && otime >agoSevenDay){
			
			returnValue =2;  //红色
		}
		
		return returnValue; 
		
	}

