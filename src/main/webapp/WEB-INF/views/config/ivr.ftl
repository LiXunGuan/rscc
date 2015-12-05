
<section id="widget-grid-ivr" >
    <div class="row" id="maincontent">
        <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
            <div style="margin-bottom:5px">
			<button class="btn btn-primary" id="toAddScopExt" onclick="addItem()">添加</button>
<!--             <button class="btn btn-sm btn-success" type="button" id="toDeployButton" onclick="toDeployButton();"> 应用 </button><br /> -->
            </div>
            <div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-3" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-colorbutton="false"
                 data-widget-collapsed="false">
                <header>
                    <span class="widget-icon"> <i class="fa fa-table"></i> </span>
                    <h2>IVR配置</h2>
                </header>
                <div>
                    <div class="jarviswidget-editbox">
                    </div>
                    <div class="widget-body no-padding">
                        <div class="widget-body-toolbar">
                        	<div class="smart-form">
	                        	<table id="ivrTable" class="table table-striped table-hover"></table>
	                        </div>
                        </div>
                    </div>
                </div>
            </div>
        </article>
    </div>

    <div id="ivrForModel"></div>
</section>


<script type="application/javascript">
	
	var oTable ='';
	
	$(document).ready(function() {
// 	    pageSetUp();
// 	    gettitleshow();
	    initTable();
	    isApplySty();
	});
	
	function gettitleshow(){
		var url=getContext() + "config/fsxml/menushow"
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
	
	/* 查询  */
	function getSearch(){
		oTable.DataTable().ajax.reload(null,false);;
	}
	
	/* 应用  */
	function toDeployButton(){
		var url=getContext() + "config/ivr/deploy"
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
	
	function initTable(){
	    
	    oTable = $('#ivrTable').dataTable({
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
		    	"url" : getContext() + "config/ivr/data",
		    	"type":"POST",
		    	"data" :function(param){
		    		
		    	}
		    },
		 	"paging" :true,
			"pagingType" :"bootstrap",
			"lengthChange" : true,
			"order" : [[ "0", "desc"]],
			"columns" : [
				   { "title" : "名字", "data" : "name"},
				   { "title" : "内容", "data" : "content"}, 
				   { 
					   "title" : "操作",
					   "data" : "null",
					   "orderable":false,
					   "render": function(data,type,full){
						   return '<a onclick="addItem('+full.id+');">修改</a>'+'&nbsp;&nbsp<a onclick="deleteItem('+full.id+');">删除</a>';
						}
				   }
			],
		});
	}
	
	/* 添加 修改  */
	function addItem(id) {
	    $('#ivrForModel').empty();
	    var url = getContext() + "config/ivr/get";
	    $.post(url, {id:id},function(data) {
	        $("#maincontent").hide();
	        $("#ivrForModel").append(data);
	    });
	}
	
	/* 删除   */
	function deleteItem(id){
	    $.SmartMessageBox({
	        title : "删除",
	        content : "确定删除该条记录吗？",
	        buttons : '[No][Yes]'
	    }, function(ButtonPressed) {
	        if (ButtonPressed === "Yes") {
	            var url = getContext()+"config/ivr/remove";
	            $.post(url,{id:id},function(data){
	            	$("#toDeployButton").removeAttr("disabled");
	            	$("#toDeployButton").removeClass("btn-default");
	    			$("#toDeployButton").addClass("btn-sm");
	    			$("#toDeployButton").addClass("btn-success");
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

</script>


