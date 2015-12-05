
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
                    <h2>外线状态</h2>
                </header>
                <div>
                    <div class="jarviswidget-editbox">
                    </div>
                    <div class="widget-body no-padding">
                        <div class="widget-body-toolbar">
                        </div>

                        <table id="gateWayRunTimeTable" class="table table-striped table-hover">
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
        pageSetUp();
        gettitleshow();
        initTable();
    });
   
    function gettitleshow(){
    	var url=getContext() + "/runtime/gatewayruntime/menushow"
    	$.post(url,function(data){
     		$("#titleshow").html(data.titleshow);
    	},'json');
    }


    function initTable(){
        oTable = $('#gateWayRunTimeTable').dataTable({
            "oLanguage": {
                "sUrl": getContext() + "/public/dataTables.cn.txt"
            },
            "sPaginationType": "bootstrap_full",
            "bProcessing": true,
            "sAjaxSource": getContext() + "/runtime/gatewayruntime/data",
            "fnServerData": function (sSource, aoData, fnCallback, oSettings) {
            	
            	
                oSettings.jqXHR = $.ajax({
                    "dataType": 'json',
                    "async": false,
                    "type": "POST",
                    "url": sSource,
                    "data": aoData,
                    "success": fnCallback
                });
            },
            "aoColumnDefs": [{
 					sDefaultContent: '',
 					aTargets: [ '_all' ]
  				}],
            "iDisplayLength": 10,
            "aLengthMenu": [
                [10, 50, 100 ],
                [10, 50, 100]
            ],  //设置显示条数
            "bServerSide": true,
            "iSortingCols": 10,
            "aaSorting": [
                [3, 'desc'] /*时间降序排列*/
            ],
            "bFilter": false,
            "aoColumns": [
				{
					"bSortable":false,
					"sTitle" : "id",
					"mData": "id", 	
					"bVisible" : false   
				},
                {
                    "bSortable": false,
                    "sTitle": "外线号",
                    "mData": "name"
                },
                {
                    "bSortable": false,
                    "sTitle": "拨号方式",
                    "mData": "type"
                },
                {
                    "bSortable": false,
                    "sTitle": "业务的 呼入呼出",
                    "mData": "direction"
                },
                {
                    "bSortable": false,
                    "sTitle": "坐席号",
                    "mData": "agentuid"
                },
                {
                    "bSortable": false,
                    "sTitle": "坐席描述",
                    "mData": "agentinfo"
                },
                
                {
                    "bSortable": false,
                    "sTitle": "号码",
                    "mData": "callerIdNumber"
                },
                {
                    "bSortable": false,
                    "sTitle": "呼叫号码",
                    "mData": "host"
                },
               
                
            ]
        });
    }

</script>