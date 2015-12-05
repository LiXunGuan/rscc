
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
                    <h2>坐席状态</h2>
                </header>
                <div>
                    <div class="jarviswidget-editbox">
                    </div>
                    <div class="widget-body no-padding">
                        <div class="widget-body-toolbar">
                        	坐席号 <input type="text" id="uid" name="uid" style="height: 35px;" value="${uid!''}"  />
                        	分机号 <input type="text" id="extension" name="extension" style="height: 35px;" value="${extension!''}"  />
                        	<button class="btn btn-primary" id="dosearch" onclick="getresult();">查询</button>
                        </div>
                        <table id="agentRunTimeTable" class="table table-striped table-hover"></table>
                    </div>
                </div>
            </div>
        </article>
    </div>

    <div id="forModel"></div>
</section>




<script type="application/javascript">


    function tospy(uuid){
        $('#forModel').empty();
        var url =getContext() + "/login/loginindex/tospy";
        $.post(url,{uuid:uuid},function(data) {
            $("#forModel").append(data);
        });
    };

    function tokill(uuid){
        $('#forModel').empty();
        var url =getContext() + "/login/loginindex/tokill";
        $.post(url,{uuid:uuid},function(data) {

            if (data.success) {
                $.smallBox({
                    title : "操作成功",
                    content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
                    color : "#659265",
                    iconSmall : "fa fa-check fa-2x fadeInRight animated",
                    timeout : 2000
                });
            }else{
                $.smallBox({
                    title : "操作失败",
                    content : "<i class='fa fa-clock-o'></i> <i>操作失败</i>",
                    color : "#C46A69",
                    iconSmall : "fa fa-times fa-2x fadeInRight animated",
                    timeout : 2000
                });
            }

            oTable.fnReloadAjax();

        },"json");
    };

    var oTable ='';

    $(document).ready(function() {
        pageSetUp();
        gettitleshow();
        initTable();
    });
    
    function gettitleshow(){
    	var url=getContext() + "runtime/agentruntime/menushow"
    	$.post(url,function(data){
     		$("#titleshow").html(data.titleshow);
    	},'json');
    }
    function getresult(){
    	$('#agentRunTimeTable').DataTable().ajax.reload(null,false);
    }
    curInterval = setInterval("oTable.fnReloadAjax();",5000);
    
    function initTable(){
        oTable = $('#agentRunTimeTable').dataTable({
        	"dom" :"t" + "<'dt-toolbar-footer'<'col-sm-6 col-xs-12'i><'col-xs-12 col-sm-6 text-right'lp>>",
			"autoWidth" : true,
			"ordering" : true,
			"serverSide" : true,
			"processing" : true,
			"searching" : false,
            "language": {
                "url": getContext() + "public/dataTables.cn.txt"
            },
            "pagingType" :"bootstrap",
            "ajax":{
		    	"url" : getContext() + "runtime/agentruntime/data",
		    	"type":"POST",
		    	"data" :function(param){
		    		param.extension= $("#extension").val();
		    		param.uid= $("#uid").val();
		    	}
		    },
            "pageLength": 10,
            "lengthMenu" : [ 10, 15, 20, 25, 30 ],
            "order": [
                [3, 'desc'] /*时间降序排列*/
            ],
            "columns": [
				{
					"title" : "id",
					"data": "id", 	
					"visible" : false   
				},
                {
                    "title": "坐席号",
                    "data": "uid"
                },
                {
                    "title": "坐席描述",
                    "data": "info"
                },
                {
                    "title": "分机号",
                    "data": "sipusernumber"
                },
//                {
//                    "bSortable": false,
//                    "sTitle": "注册状态",
//                    "mData": "regstatus"
//                },
                {
                    "title": "坐席状态",
                    "data": "blindingstatus"
                },
                {
                    "title": "通话状态",
                    "data": "callstate"
                },
                {
                    "title": "对方号码",
                    "data": "othernumber"
                },
                {
                    "title": "通话时长（秒）",
                    "data": "answertime"
                },
                
                {
                    "title": "呼叫方向",
                    "data": "dailway"
                },
                {
                    "title": "操作",
                    "data": "",
                    "render" : function(data, type, full) {
                        if(full.answertime>0){
                            //alert(full.uuid);
                            return '<a onclick="tospy(\''+full.uuid+'\')">监听</a>'+'&nbsp;<a onclick="tokill(\''+full.uuid+'\')">强拆</a>';
                        }
                        return '';
                    }
                }
              
                
            ]
        });
    }

</script>