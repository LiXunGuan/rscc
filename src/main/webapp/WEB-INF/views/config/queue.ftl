

<section id="widget-grid-queue">
    <div class="row">
        <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
            <div style="margin-bottom:5px">
                <button class="btn btn-primary"  id="toAddButton" onclick="eidtItem()"> 新增</button>
<!--                 <button class="btn btn-sm btn-success" type="button" id="toDeployButton"  onclick="toDeployButton();" > 应用 </button> -->
            </div>
            <div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-3" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-colorbutton="false"
                 data-widget-collapsed="false">
                <header>
                    <span class="widget-icon"> <i class="fa fa-table"></i> </span>
                    <h2>技能组管理</h2>
                </header>
                <div>
                    <div class="jarviswidget-editbox">
                    </div>
                    <div class="widget-body no-padding">
                        <div class="widget-body-toolbar">
	                        	技能组名称 <input type="text" id="name" name="name" style="height: 35px;" value="${name!''}"  />
	                        	<label class="input col col-1">静态队列</label>
	                            <label class="input col col-2">
	                                <select id="staticqueue" name="staticqueue" style="height: 35px;" onchange="getSearch();">
	                                    <option  value="">请选择</option>
	                                    <option value="1">是</option>
	                                    <option value="0">否</option>
	                                </select>
	                            </label>
	                            <button class="btn btn-primary" id="dosearch">查询</button>
	                        	<div class="smart-form">
	                        		<table id="queueTable" class="table table-striped table-hover"></table>
                        		</div>
						</div>
                    </div>
                </div>
            </div>
        </article>
    </div>

    <div id="queueForModel"></div>
</section>




<script type="application/javascript">

    var oTable ='';

    $(document).ready(function() {
//         pageSetUp();
//         gettitleshow();
        initTable();
        isApplySty();
    });
    
    $("#dosearch").click(function (){
        getSearch();
    });
    
    /* 查询  */
    function getSearch(){
//     	oTable.fnReloadAjax(null,null,null,1);
    	oTable.DataTable().ajax.reload(null,false);;
    }
    
    function gettitleshow(){
    	var url=getContext() + "config/queue/menushow"
    	$.post(url,function(data){
     		$("#titleshow").html(data.titleshow);
    	},'json');
    }

    function isApplySty(){
   	 if (${isApply?c}) {
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
        
        oTable = $('#queueTable').dataTable({
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
		    	"url" : getContext() + "config/queue/data",
		    	"type":"POST",
		    	"data" :function(param){
		    		param.name = $("#name").val(),
		    		param.staticqueue = $("#staticqueue").val()
		    	}
		    },
		 	"paging" :true,
			"pagingType" :"bootstrap",
			"lengthChange" : true,
			"order" : [[ "3", "desc"]],
			"columns" : [
				   { "title" : "名称", "data" : "name"},
				   { "title" : "分机号", "data" : "extension"}, 
				   { "title" : "静态队列", "data" : "isStatic"},
				   { "title" : "策略", "data" : "strategy"},
				   { "title" : "等待音乐", "data" : "mohSound"},
				   { "title" : "TBS", "data" : "timeBaseScore","visible":false},
				   { "title" : "MWT", "data" : "maxWaitTime","visible":false},
				   { "title" : "TRA", "data" : "tierRulesApply","visible":false},
				   { "title" : "TRWS", "data" : "tierRuleWaitSecond","visible":false},
				   { "title" : "DAA", "data" : "discardAbandonedAfter","visible":false},
				   { "title" : "ARA", "data" : "abandonedResumeAllowed","visible":false},
				   { 
					   "title" : "操作",
					   "data" : "null",
					   "orderable":false,
					   "render": function(data,type,full){
// 						    if(full.staticqueue == "0"){
// 		                        return '<a onclick="addItem('+full.id+');">添加人员</a>'+'&nbsp;&nbsp;<a onclick="eidtItem('+full.id+');">修改</a>'+'&nbsp;&nbsp<a onclick="deleteItem('+full.id+');">删除</a>';
// 	                    	}else{
// 	                    		return '<a onclick="addItem('+full.id+');">添加人员</a>'+'&nbsp;&nbsp;'+'&nbsp;&nbsp<a onclick="deleteItem('+full.id+');">删除</a>';
// 	                    	}
	                        return '<a onclick="addItem('+full.id+');">添加人员</a>'+'&nbsp;&nbsp;<a onclick="eidtItem('+full.id+');">修改</a>'+'&nbsp;&nbsp<a onclick="deleteItem('+full.id+');">删除</a>';
						}
				   }
			],
		});
        
    }

    function toDeployButton(){
    	var url=getContext() + "config/queue/deploy"
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
                 oTable.DataTable().ajax.reload(null,false);;
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

    
    function toModel(id) {
        $('#queueForModel').empty();
        var url = getContext() + "config/queue/get";
        var params={};
        if(id) {
            params['id'] = id;
        }
        $.post(url, params,function(data) {
            $("#queueForModel").append(data);
        });
    }

    function addItem(id) {
        $('#queueForModel').empty();
        var url = getContext() + "config/queue/addItem";
        var params={};
        if(id) {
            params['id'] = id;
        }
        $.post(url, params,function(data) {
            $("#queueForModel").append(data);
        });
    }
   

    function deleteItem(id){
        $.SmartMessageBox({
            title : "删除",
            content : "确定删除该条记录吗？",
            buttons : '[No][Yes]'
        }, function(ButtonPressed) {
            if (ButtonPressed === "Yes") {
                var url = getContext()+"config/queue/remove";
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