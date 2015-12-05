var oTable = $('#dt_basic').dataTable({
		 "oLanguage": {
                "sUrl": getContext() + "/public/dataTables.cn.txt"
            },
		"sPaginationType" : "bootstrap_full",
		"bProcessing" : true,
		"sAjaxSource" : getContext() + "/sysbinder/data",
		"fnServerData" : function(sSource, aoData, fnCallback,oSettings) {
			aoData.push({"name" : "seriesNumber","value" : $("#seriesNumber").val()},
						{"name" : "companyName","value" : $("#companyName").val()},
						{"name" : "stype","value" : $("#stype").val()}
						);
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
		"bLengthChange": false,
		"bServerSide" : true,
		"iSortingCols" : 10,
		"bFilter" : false,
		"aoColumnDefs" : [
				{
					"sTitle" : "序列号",
					"aTargets" : [ 0 ]
				},
				{
					"sTitle" : "指纹",
					"aTargets" : [ 1 ]
				},
				{
					"sTitle" : "IP",
					"aTargets" : [ 2 ]
				},
				{
					"sTitle" : "授权用户",
					"aTargets" : [ 3 ]
				},
				{
					"sTitle" : "授权类型",
					"aTargets" : [ 4 ]
				},
				{
					"sTitle" : "余额",
					"aTargets" : [ 5 ]
				},
				{
					"sTitle" : "单价",
					"aTargets" : [ 6 ]
				},
				{
					"sTitle" : "操作",
					"bSortable" : false,
					"mRender" : function(data, type, full) {
							return '<a onclick="binderConnect('+ full.id+ ');">授权信息</a>'+
								   '&nbsp;&nbsp;<a onclick="configRate('+ full.id+ ');">配置费率</a>'+
								   '&nbsp;&nbsp;<a onclick="addBalance('+ full.id+ ');">充值</a>'+
								   '&nbsp;&nbsp;<a onclick="deleteConnect('+ full.id+ ');">删除</a>';
					},
					"aTargets" : [ 7 ]
				},
				],
		"aoColumns" : [ 
				{
					"mData" : "seriesnumber" ,"bSortable": false,
					"sWidth" : "10%"
				}, {
					"mData" : "fingerprint","bSortable": false,
					"sWidth" : "20%"
				}, {
					"mData" : "ip","bSortable": false,
					"sWidth" : "10%"
				}, {
					"mData" : "companyName" ,"bSortable": false,
					"sWidth" : "20%"
				}, {
					"mData" : "stype" ,"bSortable": false,
					"sWidth" : "5%"
				}, {
					"mData" : "balance" ,"bSortable": false,
					"sWidth" : "10%"
				}, {
					"mData" : "priceRate" ,"bSortable": false,
					"sWidth" : "10%"
				},{
					"mData" : null,"sWidth" : "15%"
				} 
				],
	});

	
	/**
	 * 点击查询时的方法
	 */
	$('#doSearch').click(function(){
		oTable.fnReloadAjax();
	});


	/**
	 * 授权
	 * 
	 * @param id
	 */
	function binderConnect(id){
		$('#dialog_connection').remove();
		var url = getContext() + "/sysbinder/get";
		$.post(url,{id : id}, function(data) {
			$("#divAll").append(data);
		});
	}
	
	/**
	 * 配置费率
	 * 
	 * @param id
	 */
	function configRate(id){
		$('#dialog_config_rate').remove();
		var url = getContext() + "/sysbinder/configRate";
		$.post(url,{id : id}, function(data) {
			$("#divAll").append(data);
		});
	}
	
	/**
	 * 充值
	 * 
	 * @param id
	 */
	function addBalance(id){
		$('#dialog_content_charge').remove();
		var url = getContext() + "/sysbinder/chargeIndex";
		$.post(url,{id : id}, function(data) {
			$("#divAll").append(data);
		});
	}
	
	/**
	 * 删除
	 * @param id
	 */
	function deleteConnect(id){
		
		var url = getContext() + "/sysbinder/deleteConnect";
		$.SmartMessageBox({
			title : "删除",
			content : "该操作将永久删除许可，该用户群呼不可使用，确定执行此操作？",
			buttons : '[No][Yes]'
		}, function(ButtonPressed) {
			if (ButtonPressed === "Yes") {
				$.post(url,{id:id},function(data){
					if(data.success){
						$.smallBox({
							title : "操作成功",
							content : "<i class='fa fa-clock-o'></i> <i>删除成功...</i>",
							color : "#659265",
							iconSmall : "fa fa-check fa-2x fadeInRight animated",
							timeout : 2000
						});
					}
		            oTable.fnReloadAjax();
				},"json").error(function(){
						$.smallBox({
							title : "操作失败",
							content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>删除时出现异常,请联系管理员...</i>",
							color : "#C46A69",
							iconSmall : "fa fa-times fa-2x fadeInRight animated"
						});
					});
			}
		});
		
		
	}
	