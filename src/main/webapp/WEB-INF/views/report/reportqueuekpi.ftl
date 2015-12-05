<script type="text/javascript"
	src="${springMacroRequestContext.contextPath}/assets/js/calendar/WdatePicker.js"></script>
<section id="widget-grid-reportqueuekpi">
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
					<h2>技能组-KPI报表</h2>
				</header>
				<div>
					<div class="jarviswidget-editbox"></div>
					<div class="widget-body no-padding">
						<div class="widget-body-toolbar">
							<label class="input col col-1">报表选择</label> <label
								class="input col col-2" style="width:5%"> <select id="selectionReport"
								name="selectionReport" style="height: 35px;"> <#list
									selectionDates?keys as key> <#if selectionDate??> <#if
									key==selectionDate>
									<option value="${key}" selected="selected">${selectionDates[key]}</option>
									<#else>
									<option value="${key}">${selectionDates[key]}</option> </#if>
									<#else>
									<option value="${key}">${selectionDates[key]}</option> </#if>
									</#list>
							</select>
							</label>
							<label id="timeSty">
								<label class="input col col-1">开始时间</label> <label
									class="input col col-2"><input id="startTime"
									name="startTime" style="height: 35px;"
									value="${((startTime)?string('yyyy-MM-dd'))!''}" type="text"
									class="ui_input_text 400w"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'});"
									placeholder="开始时间" /> </label> <label class="input col col-1">结束时间 </label>
								<label class="input col col-2"><input id="endTime"
									name="endTime" style="height: 35px;"
									value="${((endTime)?string('yyyy-MM-dd'))!''}" type="text"
									class="ui_input_text 400w"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}'});"
									placeholder="借宿时间" /> </label> 
							</label>
								技能组 <input type="text" id="name" name="name" style="height: 35px;" value="${name!''}"  />
							<button class="btn btn-primary" id="dosearch">查询</button>
							<button class="btn btn-success" id="export">导出数据</button>
						</div>
						<div class="smart-form">
							<table id="reportqueuekpiTable"
								class="table table-striped table-hover">
							</table>
						</div>
					</div>
				</div>
			</div>
		</article>
	</div>

	<div id="reportqueuekpiforModel"></div>
</section>




<script type="application/javascript">

    var oTable ='';

    $(document).ready(function() {
    	$("#selectionReport").select2({
    		width:"100%"
    	})
        pageSetUp();
        gettitleshow();
        initTable();
    });
    
    function gettitleshow(){
    	var url=getContext() + "report/reportqueuekpi/menushow"
    	$.post(url,function(data){
     		$("#titleshow").html(data.titleshow);
    	},'json');
    }
    
    $("#selectionReport").change(function (){
    	
//     	var tab = $('#reportqueuekpiTable').DataTable();
//     	var column = tab.column(1);
//     	column.visible(false);
    	
    	var selectionReport=$("#selectionReport").val();
    	if (selectionReport=='1') {
    		$("#timeSty").empty();
    		var sb="<label class='input col col-1'>开始时间</label> <label class='input col col-2'> <input id='startTime' name='startTime' style='height:35px;' value='${((startTime)?string('yyyy'))!''}' type='text' class='ui_input_text 400w'  onclick=\"WdatePicker({dateFmt:'yyyy',maxDate:'#F{$dp.$D(\\'endTime\\')}'});\" placeholder='选择年份'/></label>";
    		sb+="<label class='input col col-1'>结束时间</label> <label class='input col col-2'> <input id='endTime' name='endTime' style='height:35px;' value='${((endTime)?string('yyyy'))!''}' type='text' class='ui_input_text 400w'  onclick=\"WdatePicker({dateFmt:'yyyy',minDate:'#F{$dp.$D(\\'startTime\\')}'});\" placeholder='选择年份'/></label>";
    		$("#timeSty").append(sb);
    		refresh();
		}else if(selectionReport=='2'){
			$("#timeSty").empty();
			var sb="<label class='input col col-1'>开始时间</label> <label class='input col col-2'> <input id='startTime' name='startTime' style='height:35px;' value='${((startTime)?string('yyyy-MM'))!''}' type='text' class='ui_input_text 400w'  onclick=\"WdatePicker({dateFmt:'yyyy-MM',maxDate:'#F{$dp.$D(\\'endTime\\')}'});\" placeholder='选择年月'/></label>";
    		sb+="<label class='input col col-1'>结束时间</label> <label class='input col col-2'> <input id='endTime' name='endTime' style='height:35px;' value='${((endTime)?string('yyyy-MM'))!''}' type='text' class='ui_input_text 400w'  onclick=\"WdatePicker({dateFmt:'yyyy-MM',minDate:'#F{$dp.$D(\\'startTime\\')}'});\" placeholder='选择年月'/></label>";
    		$("#timeSty").append(sb);
    		refresh();
		}else if(selectionReport=='4'){
			$("#timeSty").empty();
			var sb="<label class='input col col-1'>开始时间</label> <label class='input col col-2'> <input id='startTime' name='startTime' style='height:35px;' value='${((startTime)?string('yyyy-MM-dd'))!''}' type='text' class='ui_input_text 400w'  onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\\'endTime\\')}'});\" placeholder='选择时间'/></label>";
    		sb+="<label class='input col col-1'>结束时间</label> <label class='input col col-2'> <input id='endTime' name='endTime' style='height:35px;' value='${((endTime)?string('yyyy-MM-dd'))!''}' type='text' class='ui_input_text 400w'  onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\\'startTime\\')}'});\" placeholder='选择时间'/></label>";
    		$("#timeSty").append(sb);
    		refresh();
		}else if(selectionReport=='5'){
			$("#timeSty").empty();
			var sb="<label class='input col col-1'>开始时间</label> <label class='input col col-2'> <input id='startTime' name='startTime' style='height:35px;' value='${((startTime)?string('yyyy-MM-dd'))!''}' type='text' class='ui_input_text 400w'  onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\\'endTime\\')}'});\" placeholder='选择时间'/></label>";
    		sb+="<label class='input col col-1'>结束时间</label> <label class='input col col-2'> <input id='endTime' name='endTime'  style='height:35px;' value='${((endTime)?string('yyyy-MM-dd'))!''}' type='text' class='ui_input_text 400w'  onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\\'startTime\\')}'});\" placeholder='选择时间'/></label>";
    		$("#timeSty").append(sb);
    		refresh();
    		
//     		column.visible(true);
    		
		}
    });
    
    
    function GetDateDiff(sDate1, sDate2){ 
		var aDate, oDate1, oDate2, iDays;
	    aDate = sDate1.split("-");
	    oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);  //转换为yyyy-MM-dd格式
	    aDate = sDate2.split("-");
	    oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);
	    iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24); //把相差的毫秒数转换为天数
	    return iDays;  // 返回相差天数
	}
    
    function GetDateDiffaa(sDate1, sDate2){ 
		var aDate, oDate1, oDate2, iDays;
	    aDate = sDate1.split("-");
	    oDate1 = new Date(aDate[0] + '-' + aDate[1]);  //转换为yyyy-MM-dd格式
	    aDate = sDate2.split("-");
	    oDate2 = new Date(aDate[0] + '-' + aDate[1]);
	    iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24); //把相差的毫秒数转换为天数
	    return iDays;  // 返回相差天数
	}
    
   
    $("#dosearch").click(function (){
    	
    	var stime = $("#startTime").val();
		var etime = $("#endTime").val();     	
		var c = new Date();
    	var selectionReport = $("#selectionReport").val();
    	if (selectionReport == '1') {
    		if(stime == ''){
				$("#startTime").val(c.format("yyyy"));
			}
			if(etime == ''){
				$("#endTime").val(c.format("yyyy"));
			}
			if((etime - stime) > 0){
				alert("请选择当年内的时间进行查询！！");
				return false;
			}
    	}else if(selectionReport == '2'){
    		if(stime == ''){
				$("#startTime").val(c.format("yyyy-MM"));
			};
			if(etime == ''){
				$("#endTime").val(c.format("yyyy-MM"));
			}
			if(GetDateDiffaa(stime, etime) > 366){
				alert("请选择12个月以内的时间进行查询！！");
				return false;
			}
    	}else if(selectionReport == '4'){
    		if(stime == ''){
				$("#startTime").val(c.format("yyyy-MM-dd"));
			};
			if(etime == ''){
				$("#endTime").val(c.format("yyyy-MM-dd"));
			}
			if(GetDateDiff(stime, etime) > 30){
				alert("请选择一个月以内的时间进行查询！！");
				return false;
			}
    	}
    	
    	
    	oTable.DataTable().ajax.reload(null,false);;
    });
    
    
    function getResult(){
   	 	refresh();	
    }
    
    function refresh(){
    	oTable.DataTable().ajax.reload(null,false);;
    }

    function initTable(){
        
        oTable = $('#reportqueuekpiTable').dataTable({
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
		    	"url" : getContext() + "report/reportqueuekpi/data",
		    	"type":"POST",
		    	"data" :function(param){
		    		param.selectionReport = $("#selectionReport").val(),
		    		param.startTime = $("#startTime").val(),
		    		param.endTime = $("#endTime").val(),
		    		param.name = $("#name").val()
		    	}
		    },
		 	"paging" :true,
			"pagingType" :"bootstrap",
			"lengthChange" : true,
			"order" : [[ "0", "desc"]],
			"columns" : [
				   { "title" : "时间", "data" : "timestamp"},
// 	   			   { "title" : "时段", "data" : "hour"},
				   { "title" : "技能组", "data" : "name"},
				   { "title" : "呼入次数（未通）", "data" : "in_f_callcount"},
				   { "title" : "呼入次数（接通）", "data" : "in_t_callcount"},
				   { "title" : "平均等待时长（接通）", "data" : "in_t_wait_avg"},
				   { "title" : "平均等待时长（未接通）", "data" : "in_f_wait_avg"},
				   { "title" : "平均呼入通话时长", "data" : "in_t_call_avg"},
				   { "title" : "最大排队数", "data" : "max_queued"},
				   { "title" : "最小排队数", "data" : "min_queued"},
				   { "title" : "5秒内接起电话", "data" : "answercount_0_5"},
				   { "title" : "5-10秒内接起电话", "data" : "answercount_5_10"},
				   { "title" : "10-15秒内接起电话", "data" : "answercount_10_15"},
				   { "title" : "15-20秒内接起电话", "data" : "answercount_15_20"},
				   { "title" : "20-25秒内接起电话", "data" : "answercount_20_25"},
				   { "title" : "25-30秒内接起电话", "data" : "answercount_25_30"},
				   { "title" : "30-40秒内接起电话", "data" : "answercount_30_40"},
				   { "title" : "40-50秒内接起电话", "data" : "answercount_40_50"},
				   { "title" : "50-60秒内接起电话", "data" : "answercount_50_60"},
				   { "title" : "60秒以后接起电话", "data" : "answercount_60_max"}
			],
		});
        
    }
  $("#export").click(function(){
	    
		var url = getContext()+"report/reportqueuekpi/export?";
    	var selectionReport = $("#selectionReport").val();
    	var startTime = $("#startTime").val();
    	var endTime = $("#endTime").val();
    	var name = $("#name").val();
    	
    	if(selectionReport!= null && selectionReport!=''){url += "&selectionReport=" + selectionReport ;}
    	if(startTime!= null && startTime!=''){url += "&startTime=" + startTime ;}
    	if(endTime!= null && endTime!=''){url += "&endTime=" + endTime ;}
    	if(name!= null && name!=''){url += "&name=" + name ;}
    	
    	window.location.href=url;
	})

</script>