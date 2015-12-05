
<section id="widget-grid-gateway">
	<div class="row">
		<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<div style='margin-bottom: 5px;'>
				<button class="btn btn-primary" id="toAddScopExt"
					onclick="addItem()">添加</button>
<!-- 				<button class="btn btn-sm btn-success" type="button" -->
<!-- 					id="toDeployButton" onclick="toDeployButton();" disabled="disabled">应用</button> -->
			</div>
			<div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-3"
				data-widget-editbutton="false" data-widget-deletebutton="false"
				data-widget-colorbutton="false" data-widget-collapsed="false">
				<header>
					<span class="widget-icon"> <i class="fa fa-table"></i>
					</span>  
					<h2>网关配置</h2>
				</header>
				<div>
					<div class="jarviswidget-editbox"></div>
					
					<div class="widget-body no-padding">
					
						<div class="widget-body-toolbar">
						<!-- 
							<label>网关名称:</label>
							<input id='name' name="name" value="" type="text" size="14px" style="height: 35px;" />
							<button class="btn btn-primary" id="dosearch" onclick="getSearch();">查询</button>
						 -->
						</div>

						<div class="smart-form">
							<table id="gateWayTable" class="table table-striped table-hover">
							</table>
						</div>

					</div>
					
				</div>
			</div>
		</article>
	</div>

	<div id="gatewayforModel"></div>
</section>




<script type="application/javascript">
	
	var oTable ='';
	
	$(document).ready(function() {
// 	    pageSetUp();
	    initTable();
	    isApplySty();
// 	    gettitleshow();
	});
	
	function gettitleshow(){
		var url=getContext() + "config/gateway/menushow"
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
		var url=getContext() + "/config/gateway/deploy"
		$.post(url,function(data){
			 if(data.success){
	             $.smallBox({
	                 title : "操作成功",
	                 content : "<i class='fa fa-clock-o'></i> <i>应用成功...</i>",
	                 color : "#659265",
	                 iconSmall : "fa fa-check fa-2x fadeInRight animated",
	                 timeout : 2000
	             });
	             $("#toDeployButton").attr("disabled","disabled");
	             $("#toDeployButton").removeClass("btn-sm");
	 			$("#toDeployButton").removeClass("btn-success");
	 			$("#toDeployButton").addClass("btn-default");
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
    
	    oTable = $('#gateWayTable').dataTable({
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
		    	"url" : getContext() + "config/gateway/data",
		    	"type":"POST",
		    	"data" :function(param){
		    		
		    	}
		    },
		 	"paging" :true,
			"pagingType" :"bootstrap",
			"lengthChange" : true,
			"order" : [[ "3", "desc"]],
			"columns" : [
				   { "title" : "名称", "data" : "name"},
				   { "title" : "IP", "data" : "ip"}, 
				   { "title" : "是否注册", "data" : "registration"},
				   { "title" : "用户名", "data" : "username"},
				   { "title" : "主机名称", "data" : "servername","visible":false},
				   {
                       "title" : "状态",
                       "data" : "status",
                       "render": function(data,type,full){

                           if (full.status) {
                               return "<span style='display:inherit;color: white' class='label label-success'>&nbsp;UP&nbsp;</span>";
                           }else{
                               return("<span style='display:inherit;color: white' class='label label-danger'>&nbsp;DOWN&nbsp;</span>");
                           }
                       }
                   },
				   { "title" : "SIP Profile", "data" : "sipProfilename","visible":false},
				   { 
					   "title" : "操作",
					   "data" : "null",
					   "orderable":false,
					   "render": function(data,type,full){
						   return '<a onclick="addAccessNumber('+full.id+');">配置接入号</a>'+'&nbsp;&nbsp;<a onclick="addItem('+full.id+');">修改</a>'+'&nbsp;&nbsp<a onclick="deleteItem('+full.id+');">删除</a>';
					   }
				   }
			],
		});
	}
	
	/* 添加 修改  */
	function addItem(id){
	    $('#gatewayforModel').empty();
	    var url = getContext() + "config/gateway/get";
	    $.post(url, {id:id},function(data) {
	        $("#gatewayforModel").append(data);
	    });
	}
	
	/* 配置接入号  */
	function addAccessNumber(id){
		$('#gatewayforModel').empty();
	    var url = getContext() + "config/gateway/addAccessNumber";
	    $.post(url, {id:id},function(data) {
	        $("#gatewayforModel").append(data);
	    });
	}
	
	/* 号码管理  */
	
	/*function saveNumber(id) {
	    $('#gatewayforModel').empty();
	    var url = getContext() + "/config/gatewayName/getnumber";
	    $.post(url, {id:id},function(data) {
	        $("#gatewayforModel").append(data);
	    });
	}
	*/
	
	/* 删除   */
	function deleteItem(id){
	    $.SmartMessageBox({
	        title : "删除",
	        content : "确定删除该条记录吗？",
	        buttons : '[No][Yes]'
	    }, function(ButtonPressed) {
	        if (ButtonPressed === "Yes") {
	            var url = getContext()+"config/gateway/remove";
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


