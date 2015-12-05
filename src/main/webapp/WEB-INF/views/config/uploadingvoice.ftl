
<section id="widget-grid-uploadingvoice">
    <div class="row">
        <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
            <div style="margin-bottom:5px">
			<button class="btn btn-primary" id="toAddAgent" onclick="addItem()">上传语音</button>
            </div>
            <div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-3" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-colorbutton="false"
                 data-widget-collapsed="false">
                <header>
                    <span class="widget-icon"> <i class="fa fa-table"></i> </span>
                    <h2>语音管理</h2>
                </header>
                <div>
                    <div class="jarviswidget-editbox">
                    </div>
                    <div class="widget-body no-padding">
                        <div class="widget-body-toolbar">
                        	<div class="smart-form">
		                        <table id="uploadingVoiceTable" class="table table-striped table-hover"></table>
                        	</div>
                        </div>
                    </div>
                </div>
            </div>
        </article>
    </div>
    <div id="uploadingvoiceForModel"></div>
</section>

<script type="application/javascript">
	
	var oTable ='';
	
	$(document).ready(function() {
// 	    pageSetUp();
// 	    gettitleshow();
	    initTable();
	});
	
	function gettitleshow(){
		var url=getContext() + "system/uploadingvoice/menushow"
		$.post(url,function(data){
	 		$("#titleshow").html(data.titleshow);
		},'json');
	}
	
	/* 查询  */
	function getSearch(){
		oTable.DataTable().ajax.reload(null,false);;
	}
	
	function initTable(){

		oTable = $('#uploadingVoiceTable').dataTable({
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
		    	"url" : getContext() + "system/uploadingvoice/data",
		    	"type":"POST",
		    	"data" :function(param){
		    		
		    	}
		    },
		 	"paging" :true,
			"pagingType" :"bootstrap",
			"lengthChange" : true,
			"order" : [[ "3", "desc"]],
			"columns" : [
				   { "title" : "语音名称", "data" : "name"},
				   { "title" : "路径", "data" : "voice"}, 
				   { "title" : "语音描述", "data" : "info"},
				   { 
					   "title" : "操作",
					   "data" : "null",
					   "orderable":false,
					   "render": function(data,type,full){
						   return '<a onclick="addItem('+full.id+');">修改</a>'+'&nbsp;&nbsp;<a onclick="auditionItem('+full.id+');">试听</a>'+'&nbsp;&nbsp;<a onclick="deleteItem('+full.id+');">删除</a>';
						}
				   }
			],
		});
	    
	}
	
	/* 添加 修改  */
	function addItem(id) {
	    $('#uploadingvoiceForModel').empty();
	    var url = getContext() + "system/uploadingvoice/get";
	    $.post(url, {id:id},function(data) {
	        $("#uploadingvoiceForModel").append(data);
	    });
	}
	/* 试听  */
	function auditionItem(id) {
	    $('#uploadingvoiceForModel').empty();
	    var url = getContext() + "system/uploadingvoice/audition";
	    $.post(url, {id:id},function(data) {
	        $("#uploadingvoiceForModel").append(data);
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
	            var url = getContext()+"system/uploadingvoice/remove";
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

</script>


