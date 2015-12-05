
<section id="widget-grid-sipprofile">
    <div class="row">
        <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
        	<!--
	            <div>
				 	<button class="btn btn-primary" id="toAddScopExt" onclick="addItem()">添加</button>
					<button class="btn btn-sm btn-success" type="button" id="toDeployButton" onclick="toDeployButton();"> 应用 </button><br />
	            </div>
            -->
            <div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-3" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-colorbutton="false"
                 data-widget-collapsed="false">
                <header>
                    <span class="widget-icon"> <i class="fa fa-table"></i> </span>
                    <h2>SIP参数配置</h2>
                </header>
                <div>
                    <div class="jarviswidget-editbox"></div>
                    <div class="widget-body no-padding">
                        <div class="widget-body-toolbar">
                        	<div class="smart-form">
		                        <table id="sipProfileTable" class="table table-striped table-hover"></table>
                        	</div>
                        </div>
                    </div>
                </div>
            </div>
        </article>
    </div>
    <div id="forModel"></div>
</section>

<script type="application/javascript">
	
	var oTable ='';
	
	$(document).ready(function() {
// 	    pageSetUp();
// 	    gettitleshow();
	    initTable();
	});
	
	function gettitleshow(){
		var url=getContext() + "/config/sipprofile/menushow"
		$.post(url,function(data){
	 		$("#titleshow").html(data.titleshow);
		},'json');
	}
	
	/* 查询  */
	function getSearch(){
		oTable.DataTable().ajax.reload(null,false);;
	}
	
	/* 应用  */
	function toDeployButton(){
		var url=getContext() + "config/sipprofile/deploy"
		$.post(url,function(data){
			 if(data.success){
	             $.smallBox({
	                 title : "操作成功",
	                 content : "<i class='fa fa-clock-o'></i> <i>应用成功...</i>",
	                 color : "#659265",
	                 iconSmall : "fa fa-check fa-2x fadeInRight animated",
	                 timeout : 2000
	             });
	             oTable.fnReloadAjax();
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
	    oTable = $('#sipProfileTable').dataTable({
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
		    	"url" : getContext() + "config/sipprofile/data",
		    	"type":"POST",
		    	"data" :function(param){
		    		
		    	}
		    },
		 	"paging" :true,
			"pagingType" :"bootstrap",
			"lengthChange" : true,
			"order" : [[ "3", "desc"]],
			"columns" : [
				   { "title" : "SIP Profile", "data" : "name"},
				   { "title" : "SIP IP", "data" : "sipIp"}, 
				   { "title" : "SIP 端口", "data" : "sipPort"},
				   { "title" : "主机名称", "data" : "servername"},
				   { "title" : "编码", "data" : "codecString"}
			],
		});
	    
	}
	
	/* 添加 修改  */
	function addItem(id) {
	    $('#forModel').empty();
	    var url = getContext() + "config/sipprofile/get";
	    $.post(url, {id:id},function(data) {
	        $("#forModel").append(data);
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
	            var url = getContext() + "config/sipprofile/remove";
	            $.post(url,{id:id},function(data){
	                if(data.success){
	                    $.smallBox({
	                        title : "操作成功",
	                        content : "<i class='fa fa-clock-o'></i> <i>删除成功...</i>",
	                        color : "#659265",
	                        iconSmall : "fa fa-check fa-2x fadeInRight animated",
	                        timeout : 2000
	                    });
	                    oTable.fnReloadAjax();
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


