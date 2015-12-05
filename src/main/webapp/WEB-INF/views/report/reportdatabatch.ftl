<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/calendar/WdatePicker.js"></script>
<style>
	ul{
		list-style-type:none;
	}
	.bg-color-green{
		background-color:#5b835b!important;
	}
</style>
<section id="widget-grid-reportdatabatch">
	<div class="row">
		<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<div></div>
			<br />
			<div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-3"
				data-widget-editbutton="false" data-widget-deletebutton="false"
				data-widget-colorbutton="false" data-widget-collapsed="false">
				<header>
					<span class="widget-icon"> <i class="fa fa-table"></i>
					</span>
					<h2>坐席-数据报表</h2>
				</header>
				<div>
					<div class="jarviswidget-editbox"></div>
					<div class="widget-body no-padding">
						<div class="widget-body-toolbar">
							<label class="input col col-1">报表选择</label> 
								<label class="input col col-2" style="width: 5%;">
								<select id="selectionReport" name="selectionReport" class="select2" style="height: 35px;"> 
									<option value="day" selected="selected">日报</option>
									<option value="month">月报</option> 
								</select>
							</label>
							<label id="timeSty">
								<label class="input col col-1"> 开始时间</label> 
									<label class="input col col-2">
										<input id="startTime" name="startTime" style="height: 35px;" value="${((startTime)?string('yyyy-MM-dd'))!''}" type="text" class="ui_input_text 400w" onclick="WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00',maxDate:'#F{$dp.$D(\'endTime\')}'});" placeholder=" 开始时间" /> </label> 
								<label class="input col col-1"> 结束时间</label>
									<label class="input col col-2">
										<input id="endTime" name="endTime" style="height: 35px;" value="${((endTime)?string('yyyy-MM-dd'))!''}" type="text" class="ui_input_text 400w" onclick="WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00',minDate:'#F{$dp.$D(\'startTime\')}'});" placeholder=" 结束时间" /> </label> 
							</label>
							坐席名称 <input type="text" id="agentName" name="agentName" style="height: 35px;" value="" />
							<button class="btn btn-primary" id="dosearch">查询</button>
							
					<!--	<button class="btn btn-success" id="export">导出数据</button>  -->
						</div>
						<div class="wrapper" style="background-color: #fafafa;">
							<label class="col-md-2" style="margin-top: 12px">
								<h1>成交客户</h1>
							</label>
							<label class="col-md-2" style="margin-top: 12px">
								<h1>总数：<span id="sumcstm"></span></h1>
							</label>
							<label class="col-md-2" style="margin-top: 15px;border-right-style: inset;">
								<ul >
									<li>
										<label class="label bg-color-red"><i class="fa fa-plus"></i> <span id="sumcstmadd"/></label>
									</li>
									<li>
										<label class="label bg-color-green"><i class="fa fa-minus"></i> <span id="sumcstmdel"/></label>
									</li>
								</ul>
							</label>
							
							<label class="col-md-2" style="margin-top: 12px">
								<h1>意向客户</h1>
							</label>
							<label class="col-md-2" style="margin-top: 12px;">
								<h1>总数：<span id="sumintent"></span></h1>
							</label>
							<label class="col-md-2" style="margin-top: 15px">
								<ul >
									<li>
										<label class="label bg-color-red"><i class="fa fa-plus"></i> <span id="sumintentadd"/></label>
									</li>
									<li>
										<label class="label bg-color-green"><i class="fa fa-minus"></i> <span id="sumintentdel"/></label>
									</li>
								</ul>
							</label>
						</div>
						<div class="smart-form">
							<table id="reportdatabatchTable" class="table table-bordered table-striped"></table>
						</div>
					</div>
				</div>
			</div>
		</article>
	</div>
</section>




<script type="application/javascript">
	
	$(document).ready(function() {
		$("#selectionReport").select2({
			width:"100%"
		})
		
		$("#selectionReport").change(function (){
	    	
	    	var selectionReport=$("#selectionReport").val();
	    	if(selectionReport=='month'){
				$("#timeSty").empty();
				var sb="<label class='input col col-1'>开始时间</label> <label class='input col col-2'> <input id='startTime' name='startTime' style='height:35px;' value='${((startTime)?string('yyyy-MM'))!''}' type='text' class='ui_input_text 400w'  onclick=\"WdatePicker({dateFmt:'yyyy-MM',maxDate:'#F{$dp.$D(\\'endTime\\')}'});\" placeholder='选择年月'/></label>";
	    		sb+="<label class='input col col-1'>&nbsp;结束时间</label> <label class='input col col-2'> <input id='endTime' name='endTime' style='height:35px;' value='${((endTime)?string('yyyy-MM'))!''}' type='text' class='ui_input_text 400w'  onclick=\"WdatePicker({dateFmt:'yyyy-MM',minDate:'#F{$dp.$D(\\'startTime\\')}'});\" placeholder='选择年月'/></label>";
	    		$("#timeSty").append(sb);
	    		$("#startTime").val('${agomonthstart}');
	    		$("#endTime").val('${agomonthend}');
	    		refresh();
			}else if(selectionReport=='day'){
				$("#timeSty").empty();
				var sb="<label class='input col col-1'>开始时间</label> <label class='input col col-2'> <input id='startTime' name='startTime' style='height:35px;' value='${((startTime)?string('yyyy-MM-dd'))!''}' type='text' class='ui_input_text 400w'  onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00',maxDate:'#F{$dp.$D(\\'endTime\\')}'});\" placeholder='选择时间'/></label>";
	    		sb+="<label class='input col col-1'>&nbsp;结束时间</label> <label class='input col col-2'> <input id='endTime' name='endTime' style='height:35px;' value='${((endTime)?string('yyyy-MM-dd'))!''}' type='text' class='ui_input_text 400w'  onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00',minDate:'#F{$dp.$D(\\'startTime\\')}'});\" placeholder='选择时间'/></label>";
	    		$("#timeSty").append(sb);
	    		$("#startTime").val('${yesterdaystart}');
	    		$("#endTime").val('${yesterdayend}');
	    		refresh();
			}
	    });
    	
		if($("#startTime").val()==""){
			$("#startTime").val('${yesterdaystart}');
		}
		if($("#endTime").val()==""){
			$("#endTime").val('${yesterdayend}');
		}
		$('#reportdatabatchTable').DataTable({
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
		    	"url" : getContext() + "report/databatch/data",
		    	"type":"POST",
		    	"data" :function(param){
		    		param.selectionReport = $("#selectionReport").val(),
		    		param.startTime=$("#startTime").val(),
		    		param.endTime=$("#endTime").val(),
		    		param.agent=$("#agentName").val()
		    	}
		    },
		    "infoCallback":function(){
		    	var obj = $('#reportdatabatchTable').DataTable().ajax.json();
				var statistics = obj==undefined?0:obj.statistics;
				$("#sumintent").text(statistics.sumintent?statistics.sumintent:0);
				$("#sumintentadd").text(statistics.sumintentadd?statistics.sumintentadd:0);
				$("#sumintentdel").text(statistics.sumintentdel != undefined?(statistics.sumintentdel==0?0:statistics.sumintentdel.slice(1,statistics.sumintentdel.length)):0);
				$("#sumcstm").text(statistics.sumcstm?statistics.sumcstm:0);
				$("#sumcstmadd").text(statistics.sumcstmadd?statistics.sumcstmadd:0);
				$("#sumcstmdel").text(statistics.sumcstmdel != undefined?(statistics.sumcstmdel==0?0:statistics.sumcstmdel.slice(1,statistics.sumcstmdel.length)):0);
				
		    },
		 	"paging" :true,
			"pagingType" :"bootstrap",
			"lengthChange" : true,
			"order" : [[ "0", "desc"]],
			"columns" : [
			       { "title" : "id", "data" : "id", "visible" : false},
			       <@column titles columns />
			]
		});
    });
         
    $("#dosearch").click(function (){
    	$('#reportdatabatchTable').DataTable().ajax.reload(null,false);
    });
    
    /* 查询数据 */
    function getResult(){
    	refresh();
    }
    
    function refresh(){
    	$('#reportdatabatchTable').DataTable().ajax.reload(null,false);
    }
    
    $("#export").click(function(){
    	
    	var url = getContext()+"report/databatch/export?";
    	var selectionReport = $("#selectionReport").val();
    	var startTime = $("#startTime").val();
    	var endTime = $("#endTime").val();
    	var phone_number = $("#phone_number").val();
    	
    	if(selectionReport!= null && selectionReport!=''){url += "&selectionReport=" + selectionReport ;}
    	if(startTime!= null && startTime!=''){url += "&startTime=" + startTime ;}
    	if(endTime!= null && endTime!=''){url += "&endTime=" + endTime ;}
    	if(phone_number!= null && phone_number!=''){url += "&phone_number=" + phone_number ;}
    	
    	window.location.href=url;

    })

</script>
<#macro column titles columns>
	<#list titles[0..] as title>
		{ "title" : "${title}","data" : "${columns[title_index]}" ,"orderable" : true, defaultContent : ""}, 
	</#list>
	
</#macro>