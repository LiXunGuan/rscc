<section id="widget-grid-sipuser">
    <div class="row">
        <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
            <div style="margin-bottom:5px">
                <button class="btn btn-primary"  id="toAddButton" onclick="eidtItem()"> 新增</button>
                <button class="btn btn-primary"  id="toAddAllButton" onclick="eidtAllItem()"> 批量增加</button>
                <button class="btn btn-primary"  id="batchDeleteButton" onclick="batchEditButton()"  >批量修改</button>
                <button class="btn btn-primary"  id="batchDeleteButton" onclick="batchDeleteButton()"  >批量删除</button>
<!--                 <button class="btn btn-sm btn-success" type="button" id="toDeployButton"  onclick="toDeployButton();" > 应用 </button> -->
            </div>
            <div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-3" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-colorbutton="false"
                 data-widget-collapsed="false">
                <header>
                    <span class="widget-icon"> <i class="fa fa-table"></i> </span>
                    <h2>分机账号</h2>
                </header>
                <div>
                    <div class="jarviswidget-editbox">
                    </div>
                    <div class="widget-body no-padding">
                        <div class="widget-body-toolbar">
                           	 分机账号 <input type="text" id="uidname" name="uidname" style="height: 35px;" value="${uidname!''}"  />
                            <button class="btn btn-primary" id="dosearch">查询</button>
                        </div>
                        <div class="smart-form">
                        	<table id="sipUserTable" class="table table-striped table-hover"></table>
                    	</div>
                    </div>
                </div>
            </div>
        </article>
    </div>
    <div id="sipUserForModel"></div>
</section>

<script type="application/javascript">

    var oTable ='';
    var checklist={};

    $("#dosearch").click(function (){
        getSearch();
    });
    
    /* 查询  */
    function getSearch(){
//     	oTable.fnReloadAjax(null,null,null,1);
    	oTable.DataTable().ajax.reload(null,false);;
    }

    $(document).ready(function() {
//         pageSetUp();
        initTable();
        isApplySty();
//         gettitleshow();
       
    });
    
    function gettitleshow(){
    	var url=getContext() + "config/sipuser/menushow"
    	$.post(url,function(data){
     		$("#titleshow").html(data.titleshow);
    	},'json');
    }
    
    
    function isApplySty(){
	   	if(${isApply?c}) {
   			$("#toDeployButton").removeAttr("disabled");
   			$("#toDeployButton").removeClass("btn-default");
			$("#toDeployButton").addClass("btn-sm");
			$("#toDeployButton").addClass("btn-success");
	   	}else{
   			$("#toDeployButton").attr("disabled","disabled");
   			$("#toDeployButton").removeClass("btn-sm");
			$("#toDeployButton").removeClass("btn-success");
			$("#toDeployButton").addClass("btn-default");
	   	}
   }

    function initTable(){
//         oTable = $('#sipUserTable').dataTable({
//             "oLanguage": {
//                 "sUrl": getContext() + "/public/dataTables.cn.txt"
//             },
//             "sPaginationType": "bootstrap_full",
//             "bProcessing": true,
//             "sAjaxSource": getContext() + "/config/sipuser/data",
//             "fnServerData": function (sSource, aoData, fnCallback, oSettings) {
//                 aoData.push(
//                         {
//                             "name" : "uidname",
//                             "value" : $("#uidname").val()
//                         }
//                 );
//                 oSettings.jqXHR = $.ajax({
//                     "dataType": 'json',
//                     "async": false,
//                     "type": "POST",
//                     "url": sSource,
//                     "data": aoData,
//                     "success": fnCallback
//                 });
//             },
//             "aoColumnDefs": [{
// 				sDefaultContent: '',
// 				aTargets: [ '_all' ]
// 			}],
//             "iDisplayLength": 10,
//             "aLengthMenu": [
//                 [10, 50, 100 ],
//                 [10, 50, 100]
//             ],  //设置显示条数
//             "bServerSide": true,
//             "iSortingCols": 10,
//             "aaSorting": [
//                 [3, 'desc'] /*时间降序排列*/
//             ],
//             "bFilter": false,
//             "aoColumns": [
// 				{
// 				    "sTitle" : '<div class="btn-group">' +
// 				        ' <a class="btn btn-primary" href="javascript:void(0);" onclick="docheckall()">全选</a>' +
// 				        ' <a class="btn btn-primary dropdown-toggle" data-toggle="dropdown" href="javascript:void(0);"><span class="caret"></span></a><ul class="dropdown-menu">' +
// 				        ' <li><a href="javascript:void(0);"onclick="docheckall()" >全选所有</a></li>' +
// 				        '<li><a href="javascript:void(0);" onclick="docheckallpage()">全选本页</a></li>' +
// 				        '<li><a href="javascript:void(0);" onclick="docancelAll()">取消所有选择</a></li>' +
// 				        '</ul></div>',
// 				    "bSortable" : false,
// 				    "mData": null,
// 				    "mRender": function (data, type, full) {
// 				            if( checklist[full.id]){
// 				                return '<input type="checkbox" checked="checked" id="ck'+full.id+'" onclick="docheck('+full.id+')"  />';
// 				            }else{
// 				                return '<input type="checkbox" id="ck'+full.id+'" onclick="docheck('+full.id+')" />';
// 				            }
// 				    },
// 				    "sWidth": "10%"
// 				},
// 				{
// 					"bSortable":false,
// 					"sTitle" : "id",
// 					"mData": "id", 	
// 					"bVisible" : false   
// 				},
// // 				{
// //                     "bSortable": false,
// //                     "sTitle" : "选择",
// //                     "mRender" : function(data, type, full) {
// //                    	 return '<input type="checkbox" name="checkUserId" value=\'' + full.id + '\' onchange="addDeleteUserId(this)" >';
// //                    },
// //                    "sWidth": "10%"
// //                 },
//                 {
//                     "bSortable": false,
//                     "sTitle": "SIP 账号",
//                     "mData": "sipId"
//                 },
//                 {
//                     "bSortable": false,
//                     "sTitle": "SIP 密码",
//                     "mData": "sipPassword"
//                 },
//                 {
//                     "bSortable": false,
//                     "sTitle": "区号",
//                     "mData": "area_code"
//                 },
//                 {
//                     "bSortable": false,
//                     "sTitle": "caller_id_number",
//                     "mData": "caller_id_number"
//                 },
//                 {
//                     "bSortable": false,
//                     "sTitle": "caller_id_name",
//                     "mData": "caller_id_name",
//                 },
//                 {
//                     "sTitle" : "操作",
//                     "bSortable" : false,
//                     "mData": null,
//                     "mRender": function (data, type, full) {
//                         return '<a onclick="eidtItem('+full.id+');">修改</a>'+'&nbsp;&nbsp<a onclick="deleteItem('+full.id+');">删除</a>';
//                     }
//                 }
//             ]
//         });
        
        oTable = $('#sipUserTable').dataTable({
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
		    	"url" : getContext() + "config/sipuser/data",
		    	"type":"POST",
		    	"data" :function(param){
		    		param.uidname = $("#uidname").val()
		    	 }
		    },
		 	"paging" :true,
			"pagingType" :"bootstrap",
			"lengthChange" : true,
			"order" : [[ "3", "desc"]],
			"columns" : [
				   { 
					  "title" : '<div class="btn-group">' +
				        ' <a class="btn btn-primary" href="javascript:void(0);" onclick="docheckall()">全选</a>' +
				        ' <a class="btn btn-primary dropdown-toggle" data-toggle="dropdown" href="javascript:void(0);"><span class="caret"></span></a><ul class="dropdown-menu">' +
				        ' <li><a href="javascript:void(0);"onclick="docheckall()" >全选所有</a></li>' +
				        '<li><a href="javascript:void(0);" onclick="docheckallpage()">全选本页</a></li>' +
				        '<li><a href="javascript:void(0);" onclick="docancelAll()">取消所有选择</a></li>' +
				        '</ul></div>', 
					  "data" : "null",
					  "render": function(data,type,full){
						  if(checklist[full.id]){
				               return '<input type="checkbox" checked="checked" id="ck'+full.id+'" onclick="docheck('+full.id+')"  />';
				          }else{
				               return '<input type="checkbox" id="ck'+full.id+'" onclick="docheck('+full.id+')" />';
				          }
					   }
				   },
				   { "title" : "分机账号", "data" : "sipId"},
				   { "title" : "分机密码", "data" : "sipPassword"}, 
				   { "title" : "区号", "data" : "area_code"},
				   { "title" : "显示号码", "data" : "caller_id_number"},
				   { "title" : "显示名称", "data" : "caller_id_name"},
				   { 
					   "title" : "操作",
					   "data" : "null",
					   "orderable":false,
					   "render": function(data,type,full){
						   return '<a onclick="eidtItem('+full.id+');">修改</a>'+'&nbsp;&nbsp<a onclick="deleteItem('+full.id+');">删除</a>';
					   }
				   }
			],
		});
        
    }
    //全选所有
    function docheckall(){
	    var url = getContext()+'config/sipuser/dataall';
	    $.post(url,function(data){
	        var chs = data.split(',');
	        for(var i in chs){
	            if(typeof chs[i] !='function'){
	            	checklist[chs[i]]=chs[i];
	            }
	        }
	        docheckallpage();
	    });
	}

	//全选本页
	function docheckallpage(){

	    $("[id^='ck']").each(function(){
	        if(!$(this).is(':checked')){
	            $(this).click();
	        }
	    });
	}
	
	//取消选择
	function docancelAll(){

	    $("[id^='ck']").each(function(){
	        if($(this).is(':checked')){
	            $(this).click();
	        }
	    });

	    checklist={};
	}
	//选中某个
	function docheck(id){
	    if($("#ck"+id).is(':checked')){
	        checklist[id]=id
	    }else{
	        checklist[id]=false;
	    }
	}
	
    
//     var chk_userId=[];
//    function addDeleteUserId(obj){
// 	   var thiz=obj;
// 	   var userId=$(thiz).val();
// 	   if($(thiz).is(':checked')) {
// 		   chk_userId.push(userId);
// 	   }else{
// 		   for (var i = 0; i < chk_userId.length; i++) {
// 				chk_userId.remove(userId);
// 		}
// 	   }
// 	   $("input[name='checkUserId']").each(function (index){
// 		   for (var i = 0; i < chk_userId.length; i++) {
// 			   alert("val:"+$("input[name='checkUserId']:eq("+index+")").val()+"chk_userId:"+chk_userId[i]);
// 			   if ($("input[name='checkUserId']:eq("+index+")").val()==chk_userId[i]) {
// 				   $(thiz).attr("checked",true);
// 				} 
// 		}
		 
// 	   });
//    }


   function batchEditButton(){

       //修改
       $('#sipUserForModel').empty();
       var url = getContext() + "config/sipuser/editAll";
       var params={};
       $.post(url, params,function(data) {
           $("#sipUserForModel").append(data);
       });
   }
  

    //批量删除
    function batchDeleteButton(){
    	var sb='';
    	for (var i in checklist) {
    		sb+=checklist[i]+",";
		}
    	if (!sb.length>0) {
    		alert("没有选择要删除的SIP账号！");
			return;
		}
        $.SmartMessageBox({
            title : "批量删除",
            content : "确定删除选择的记录吗？",
            buttons : '[No][Yes]'
        }, function(ButtonPressed) {
            if (ButtonPressed === "Yes") { 
                var url = getContext()+"config/sipuser/batchremove";
                $.post(url,{sb:sb},function(data){
                    if(data.success){
                    	$("#toDeployButton").removeAttr("disabled");
                    	$("#toDeployButton").removeClass("btn-default");
            			$("#toDeployButton").addClass("btn-sm");
            			$("#toDeployButton").addClass("btn-success");
                        $.smallBox({
                            title : "操作成功",
                            content : "<i class='fa fa-clock-o'></i> <i>删除成功...</i>",
                            color : "#659265",
                            iconSmall : "fa fa-check fa-2x fadeInRight animated",
                            timeout : 2000
                        });
                        checklist={};
                        oTable.DataTable().ajax.reload(null,false);;
                    }else{
                        $.smallBox({
                            title : "操作失败",
                            content : "<i class='fa fa-clock-o'></i> <i>删除失败...</i>",
                            color : "#C46A69",
                            iconSmall : "fa fa-times fa-2x fadeInRight animated",
                            timeout : 2000
                        });
                    }
                },"json").error(function(){
                    $.smallBox({
                        title : "操作失败",
                        content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作失败...</i>",
                        color : "#C46A69",
                        iconSmall : "fa fa-times fa-2x fadeInRight animated"
                    });
                });
            }

        });
    };
    
    function toDeployButton(){
    	var url=getContext() + "config/sipuser/apply"
    	$.post(url,function(data){
    		 if(data.success){
    			 $("#toDeployButton").attr("disabled","disabled");
    			 $("#toDeployButton").removeClass("btn-sm");
    				$("#toDeployButton").removeClass("btn-success");
    				$("#toDeployButton").addClass("btn-default");
                 $.smallBox({
                     title : "操作成功",
                     content : "<i class='fa fa-clock-o'></i> <i>应用成功...</i>",
                     color : "#659265",
                     iconSmall : "fa fa-check fa-2x fadeInRight animated",
                     timeout : 2000
                 });
             }else{
                 $.smallBox({
                     title : "操作失败",
                     content : "<i class='fa fa-clock-o'></i> <i>应用失败...</i>",
                     color : "#C46A69",
                     iconSmall : "fa fa-times fa-2x fadeInRight animated",
                     timeout : 2000
                 });
             }
    	},"json").error(function(){
            $.smallBox({
                title : "操作失败",
                content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作失败...</i>",
                color : "#C46A69",
                iconSmall : "fa fa-times fa-2x fadeInRight animated"
            });
        });
    }

    function eidtItem(id){
        toModel(id);
    }

    function eidtAllItem() {
        $('#sipUserForModel').empty();
        var url = getContext() + "config/sipuser/getAll";
        var params={};
        $.post(url, params,function(data) {
            $("#sipUserForModel").append(data);
        });
    }
    
    function toModel(id) {
        $('#sipUserForModel').empty();
        var url = getContext() + "config/sipuser/get";
        var params={};
        if(id) {
            params['id'] = id;
        }
        $.post(url, params,function(data) {
            $("#sipUserForModel").append(data);
        });
    }


    function deleteItem(id){
        $.SmartMessageBox({
            title : "删除",
            content : "确定删除该条记录吗？",
            buttons : '[No][Yes]'
        }, function(ButtonPressed) {
            if (ButtonPressed === "Yes") {
                var url = getContext()+"config/sipuser/remove";
                $.post(url,{id:id},function(data){
                    if(data.success){
                    	$("#toDeployButton").removeAttr("disabled");
                    	$("#toDeployButton").removeClass("btn-default");
            			$("#toDeployButton").addClass("btn-sm");
            			$("#toDeployButton").addClass("btn-success");
                        $.smallBox({
                            title : "操作成功",
                            content : "<i class='fa fa-clock-o'></i> <i>删除成功...</i>",
                            color : "#659265",
                            iconSmall : "fa fa-check fa-2x fadeInRight animated",
                            timeout : 2000
                        });
                        oTable.DataTable().ajax.reload(null,false);;
                    }else{
                        $.smallBox({
                            title : "操作失败",
                            content : "<i class='fa fa-clock-o'></i> <i>删除失败...</i>",
                            color : "#C46A69",
                            iconSmall : "fa fa-times fa-2x fadeInRight animated",
                            timeout : 2000
                        });
                    }
                },"json").error(function(){
                    $.smallBox({
                        title : "操作失败",
                        content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作失败...</i>",
                        color : "#C46A69",
                        iconSmall : "fa fa-times fa-2x fadeInRight animated"
                    });
                });
            }

        });
    };



</script>