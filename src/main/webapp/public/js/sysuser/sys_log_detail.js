var oTable_criminal = $('#dt_basic').dataTable({
	"oLanguage" : {
		"sUrl" : getContext() + "/public/dataTables.cn.txt"
	},
	"fnRowCallback" : function(nRow, aData, iDisplayIndex) {

		if (aData.isSpecial == 1) { /* 接通并且没有批复的加粗 */
			nRow.className = 'demo-greenn';
		}
		return nRow;
	},
	"sPaginationType" : "bootstrap_full",
	"bProcessing" : true,
	"sAjaxSource" : getContext() + "/criminal/data",
	"fnServerData" : function(sSource, aoData, fnCallback, oSettings) {
		aoData.push({
			'name' : 'type',
			'value' : type
		});
		aoData.push({
			'name' : 'criminalname',
			'value' : $("#criminalname").val()
		});
		aoData.push({
			'name' : 'phone',
			'value' : $("#phone").val()
		});
		aoData.push({
			'name' : 'deptid',
			'value' : $("#deptid").val()
		});
		aoData.push({
			'name' : 'code',
			'value' : $("#code").val()
		});

		/* 矫正状态 */
		aoData.push({
			'name' : 'status',
			'value' : $("#status").val()

		});

		/* 本月进矫正人数 */
		aoData.push({
			'name' : 'incriminal',
			'value' : incriminal
		});
		aoData.push({
			'name' : 'timestart',
			'value' : timestart
		});
		aoData.push({
			'name' : 'timeend',
			'value' : timeend
		});
		aoData.push({
			'name' : 'iscollectvoiceprint',
			'value' : $("#iscollectvoiceprint").val()
		});
		aoData.push({
			'name' : 'isSpecial',
			'value' : $("#isspecial").val()
		});
		aoData.push({
			'name' : 'isDisplay',
			'value' : $("#isDisplay").val()
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
	"iDisplayLength" : 15,
	// "iDisplayLength": $("#input[name=iDisplayLength]").val(),
	"bLengthChange" : true,
	"aLengthMenu" : [ [ 15, 50, 100 ], [ 15, 50, 100 ] ], // 设置显示条数
	"bServerSide" : true,
	"iSortingCols" : 10,
	"aaSorting" : [ [ 0, 'desc' ] ],
	"bFilter" : false,
	"aoColumns" : [ {
		"bSortable" : true,
		"sTitle" : "id",
		"mData" : "id",
		"bVisible" : false
	}, {
		"bSortable" : true,
		"sTitle" : "人员编号",
		"mData" : "code",
		"sWidth" : "10%"
	}, {
		"bSortable" : true,
		"sTitle" : "人员姓名",
		"mData" : "name",
		"sWidth" : "10%"
	}, {
		"bSortable" : true,
		"sTitle" : "所属地区",
		"mData" : "dept_id",
		"sWidth" : "17%"
	}, {
		"bSortable" : false,
		"sTitle" : "实际入矫时间",
		"mData" : "realDate",
		"sWidth" : "10%"
	},

	{
		"bSortable" : false,
		"sTitle" : "出矫时间",
		"mData" : "realEndDate",
		"sWidth" : "10%"
	},

	{
		"bSortable" : true,
		"sTitle" : "矫正状态",
		"mData" : "type",
		"sWidth" : "10%"
	}, {
		"bSortable" : true,
		"sTitle" : "声纹采集结果",
		"mData" : "iscollectvoiceprint",
		"sWidth" : "10%",
		"bVisible" : isShowVoiceResult
	}, {
		"bSortable" : false,
		"sTitle" : "电话号码",
		"mData" : "phone",
		"sWidth" : "10%"
	}, {
		"sTitle" : "操作",
		"bSortable" : false,
		"mData" : null,
		"bVisible" : isDisplay,
		"mRender" : function(data, type, full) {
			return '<a onclick="lookCriminalInfo(' + full.id + ');">查看</a>';

		}
	}

	]
});