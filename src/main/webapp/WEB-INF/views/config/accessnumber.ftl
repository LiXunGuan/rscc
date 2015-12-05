

<section id="widget-grid-accessnumber">
    <div class="row">
        <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
            <div style="margin-bottom:5px">
                <button class="btn btn-primary"  id="toAddButton" onclick="eidtItem()"> 新增</button>
                <button class="btn btn-primary"  id="toAddAllButton" onclick="eidtAllItem()"> 批量增加</button>
                 <button class="btn btn-primary"  id="batchDeleteButton" onclick="batchDeleteButton()"  >批量删除</button>
            </div>
            <div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-3" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-colorbutton="false"
                 data-widget-collapsed="false">
                <header>
                    <span class="widget-icon"> <i class="fa fa-table"></i> </span>
                    <h2>接入号管理</h2>
                </header>
                <div>
                    <div class="jarviswidget-editbox">
                    </div>
                    <div class="widget-body no-padding">
                        <div class="widget-body-toolbar">
                        	 接入号<input type="text" id="number" name="number" style="height: 35px;" value="${number!''}"  />
                            <button class="btn btn-primary" id="dosearch">查询</button>
                        </div>
                        <div class="smart-form">
                        	<table id="accessnumberTable" class="table table-striped table-hover"></table>
                    	</div>
                    </div>
                </div>
            </div>
        </article>
    </div>
    <div id="accessnumberForModel"></div>
</section>

<script type="application/javascript">

    var oTable ='';
    var checklist={};

    $("#dosearch").click(function (){
        getSearch();
    });
    
    /*  */
    function getResult(){
    	getSearch();
    }
    
    /* 查询  */
    function getSearch(){
//     	oTable.fnReloadAjax(null,null,null,1);
    	oTable.DataTable().ajax.reload(null,false);;
    }
    
    $(document).ready(function() {
//         pageSetUp();
        initTable();
//         gettitleshow();
    });
    
    function gettitleshow(){
    	var url=getContext() + "config/accessnumber/menushow"
    	$.post(url,function(data){
     		$("#titleshow").html(data.titleshow);
    	},'json');
    }
   
    function initTable(){
        
        oTable = $('#accessnumberTable').dataTable({
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
		    	"url" : getContext() + "config/accessnumber/data",
		    	"type":"POST",
		    	"data" :function(param){
		    		param.number = $("#number").val()
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
				   { "title" : "接入号", "data" : "accessnumber"},
				   { "title" : "并发", "data" : "concurrency"}, 
				   { "title" : "可以呼入", "data" : "null","render": function(data,type,full){
					   if(full.canin==true){
						   return "可以";
					   }else{
						   return "不可以";
					   }
				   }},
				   { "title" : "可以呼出", "data" : "null","render":function(data,type,full){
					   if(full.canout==true){
						   return "可以";
					   }else{
						   return "不可以";
					   }
				   }},
				   { "title" : "默认接入号", "data" : "defaultaccessnumber"},
				   { 
					   "title" : "操作",
					   "data" : "null",
					   "orderable":false,
					   "render": function(data,type,full){
						   return '<a onclick="addItem('+full.id+');">配置Gateway</a>'+'&nbsp;&nbsp;<a onclick="eidtItem('+full.id+');">修改</a>'+'&nbsp;&nbsp<a onclick="deleteItem('+full.id+');">删除</a>';
		                }
				   }
			],
		});
        
    }

    function eidtItem(id){
        toModel(id);
    }

    
    function toModel(id) {
        $('#accessnumberForModel').empty();
        var url = getContext() + "config/accessnumber/get";
        var params={};
        if(id) {
            params['id'] = id;
        }
        $.post(url, params,function(data) {
            $("#accessnumberForModel").append(data);
        });
    }

    function addItem(id) {
        $('#accessnumberForModel').empty();
        var url = getContext() + "config/accessnumber/addItem";
        var params={};
        if(id) {
            params['id'] = id;
        }
        $.post(url, params,function(data) {
            $("#accessnumberForModel").append(data);
        });
    }
   

    function deleteItem(id){
        $.SmartMessageBox({
            title : "删除",
            content : "确定删除该条记录吗？",
            buttons : '[No][Yes]'
        }, function(ButtonPressed) {
            if (ButtonPressed === "Yes") {
                var url = getContext()+"config/accessnumber/remove";
                $.post(url,{id:id},function(data){
                    if(data.success){
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

    function eidtAllItem() {
        $('#accessnumberForModel').empty();
        var url = getContext() + "config/accessnumber/getAll";
        var params={};
        $.post(url, params,function(data) {
            $("#accessnumberForModel").append(data);
        });
    }
    
    //全选所有
    function docheckall(){
	    var url = getContext()+'config/accessnumber/dataall';
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
	
	//批量删除
    function batchDeleteButton(){
    	var sb='';
    	for (var i in checklist) {
    		sb+=checklist[i]+",";
		}
    	if (!sb.length>0) {
    		alert("没有选择要删除的接入号！");
			return;
		}
        $.SmartMessageBox({
            title : "批量删除",
            content : "确定删除选择的记录吗？",
            buttons : '[No][Yes]'
        }, function(ButtonPressed) {
            if (ButtonPressed === "Yes") { 
                var url = getContext()+"config/accessnumber/batchremove";
                $.post(url,{sb:sb},function(data){
                    if(data.success){
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

</script>