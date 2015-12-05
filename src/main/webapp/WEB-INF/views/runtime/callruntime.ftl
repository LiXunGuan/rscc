
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
                    <h2>通话状态</h2>
                </header>
                <div>
                    <div class="jarviswidget-editbox">
                    </div>
                    <div class="widget-body no-padding">
                        <div class="widget-body-toolbar">
                        	主叫 <input type="text" id="calling" name="calling" style="height: 35px;" value="${calling!''}"  />
                        	被叫 <input type="text" id="called" name="called" style="height: 35px;" value="${called!''}"  />
                        	<button class="btn btn-primary" id="dosearch" onclick="getresult();">查询</button>
                        </div>
                        <table id="callRunTimeTable" class="table table-striped table-hover"></table>
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
        pageSetUp();
        gettitleshow();
        initTable();
    });
    function getresult(){
    	$('#callRunTimeTable').DataTable().ajax.reload(null,false);
    }
    function gettitleshow(){
    	var url=getContext() + "/runtime/callruntime/menushow"
    	$.post(url,function(data){
     		$("#titleshow").html(data.titleshow);
    	},'json');
    }
   
    curInterval = setInterval("oTable.fnReloadAjax();",5000);

    function initTable(){
        oTable = $('#callRunTimeTable').dataTable({
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
		    	"url" :  getContext() + "/runtime/callruntime/data",
		    	"type":"POST",
		    	"data" :function(param){
		    		param.calling= $("#calling").val();
		    		param.called= $("#called").val();
		    	}
            },
            "pageLength" : 10,
			"lengthMenu" : [ 10, 15, 20, 25, 30 ],
			"order" : [[ "3", "desc"]],
            "columns": [
				{
					"title" : "id",
					"data": "id", 	
					"visible" : false   
				},
                {
                    "title": "主叫",
                    "data": "calling"
                },
                {
                    "title": "主叫坐席",
                    "data": "callingagentinfo"
                },
                {
                    "title": "呼叫类型",
                    "data": "routetype"
                },
                {
                    "title": "呼叫号码",
                    "data": "routenumber"
                },
                {
                    "title": "接入号",
                    "data": "gateway"
                },
                {
                    "title": "被叫",
                    "data": "called"
                },
                {
                    "title": "被叫坐席",
                    "data": "calledagentinfo"
                },
                {
                    "title": "通话时长（秒）",
                    "data": "answertime"
                },
                
              
                
            ]
        });
    }

</script>