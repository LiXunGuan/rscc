
<section id="widget-grid">
    <div class="row">
        <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
            <div>
            </div>
            <br/>
            <div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-3" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-colorbutton="false"
                 data-widget-collapsed="false">
                <header>
                    <span class="widget-icon"> <i class="fa fa-table"></i> </span>
                    <h2>技能组状态</h2>
                </header>
                <div>
                    <div class="jarviswidget-editbox">
                    </div>
                    <div class="widget-body no-padding">
                        <div class="widget-body-toolbar">
                        </div>

                        <table id="queueRunTimeTable" class="table table-striped table-hover">
                        </table>

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
//        pageSetUp();
//        gettitleshow();
        initTable();
    });
   
//    function gettitleshow(){
//    	var url=getContext() + "runtime/queueruntime/menushow";
//    	$.post(url,function(data){
//     		$("#titleshow").html(data.titleshow);
//    	},'json');
//    }
    

    /* 查看  */
//    function getItem(id,status) {
//        $('#forModel').empty();
//        var url = getContext() + "/runtime/queueruntime/get";
//        $.post(url, {id:id,status:status},function(data) {
//            $("#forModel").append(data);
//        });
//    }
    
    
//    curInterval = setInterval("oTable.fnReloadAjax();",5000);
    
    function initTable(){
        oTable = $('#queueRunTimeTable').dataTable({
        	"dom" :"t" + "<'dt-toolbar-footer'<'col-sm-6 col-xs-12'i><'col-xs-12 col-sm-6 text-right'lp>>",
			"autoWidth" : true,
			"ordering" : true,
			"serverSide" : true,
			"processing" : true,
			"searching" : false,
            "language": {
                "url": getContext() + "/public/dataTables.cn.txt"
            },
            "pagingType" :"bootstrap",
            "ajax":{
		    	"url" :  getContext() + "runtime/queueruntime/getqueue",
		    	"type":"POST",
		    	"data" :function(param){
		    		
		    	}
            },
            "pageLength" : 10,
			"lengthMenu" : [ 10, 15, 20, 25, 30 ],
			"order" : [[ "0", "desc"]],
            "columns": [
				{
					"title" : "id",
					"data": "id", 	
					"visible" : false   
				},
                {
                    
                    "title": "技能组",
                    "data": "queue_name"
                },
                {
                    
                    "title": "接起数（次）",
                    "data": "in_answer_count"
                },
                {
                    
                    "title": "总次数",
                    "data": "in_count"
                },
                {
                    
                    "title": "接起率",
                    "data": "pick_up_rate"
                },
                {
                    
                    "title": "接听速率（秒）",
                    "data": "misscount"
                },
                {
                    
                    "title": "排队人数（人）",
//                     "data": "member_count",
                    "render" : function(data, type, full) {
                    	 return '<font class="txt-color-blue" >' + full.member_count + '</font>';
                    }
                    
                },
                {
                    
                    "title": "未通话（闲）",
//                     "data": "busy_ready_count",
                    "render" : function(data, type, full) {
                   	 return '<font class="txt-color-greenLight" >' + full.busy_ready_count + '</font>';
                   }
                },
                {
                    
                    "title": "未通话（忙）",
//                     "data": "idle_ready_count"
                   	"render" : function(data, type, full) {
                         	 return '<font class="txt-color-orange" >' + full.idle_ready_count + '</font>';
                         }
                },
                {
                    
                    "title": "通话中（人）",
//                     "data": "not_ready_count"
					"render" : function(data, type, full) {
                         	 return '<font class="txt-color-red" >' + full.not_ready_count + '</font>';
                         }
                    
                },
                {
                    
                    "title": "不在线（人）",
//                     "data": "offline_count"
                   	"render" : function(data, type, full) {
                       	 return '<font color="#AAAAAA;" >' + full.offline_count + '</font>';
                       }
                    
                }
            ]
        });
    }

</script>