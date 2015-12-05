<script type="text/javascript"
	src="${springMacroRequestContext.contextPath}/assets/js/calendar/WdatePicker.js"></script>
<section id="widget-grid-reportagentkpi">
	<div class="row">
		<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<div></div>
			<br />
			<div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-3"
				data-widget-editbutton="false" data-widget-deletebutton="false"
				data-widget-colorbutton="false" data-widget-collapsed="false">
				<header>
					<span class="widget-icon"> <i class="fa fa-table"></i></span>
					<h2>坐席-呼叫报表</h2>
				</header>
				<div>
					<div class="jarviswidget-editbox"></div>
					<div class="widget-body no-padding">
						<div class="widget-body-toolbar">
							<label class="input col col-1">报表选择</label> 
							<label class="input col col-2" style="width: 6%;"> 
							<select id="selectionReport" name="selectionReport" style="height: 35px;">
									<option value="day">日报</option>
									<option value="week">周报</option>
									<option value="month">月报</option>
							</select>
							</label> 
							<!-- 设置开始结束时间选择间隔不能大于一个月 -->
							<label id="timeSty"> 
							<label class="input col col-1">开始时间</label>
								<label class="input col col-2">
									<input id="startTime" name="startTime" style="height: 35px;" value="${(startTime)!''}"
									        type="text" class="ui_input_text 400w" onclick="WdatePicker({dateFmt:'yyyy-MM-dd  00:00:00',minDate:'#F{$dp.$D(\'endTime\',{M:-1})}',maxDate:'#F{$dp.$D(\'endTime\',{d:-1})}'});"
										    placeholder="开始时间" /> 
								</label>
								
							<label class="input col col-1">结束时间</label>
							<label class="input col col-2">
								<input id="endTime" name="endTime" style="height: 35px;" value="${(endTime)!''}" type="text"
										class="ui_input_text 400w" onclick="WdatePicker({dateFmt:'yyyy-MM-dd  00:00:00',minDate:'#F{$dp.$D(\'startTime\',{d:1})}',maxDate:'#F{$dp.$D(\'startTime\',{M:1})}'});"
										placeholder="结束时间" /> 
							</label>
							</label> 
							<label class="input col col-1">通话类型</label> 
							<label class="input col col-2" style="width: 8%;">
							 <select id="calltype" name="calltype" style="height: 35px;">
									<option value="callout">呼出</option>
									<option value="callin">呼入</option>
							</select>
							</label> 
							<label class="input col col-1">统计时长</label>
							<label class="input col col-2" style="width: 10%;">
							<input type="text"  id="duration" name="duration" class="form-control" style="height: 35px;" value = "5,10,15"/>
							</label>
							<button class="btn btn-primary" id="dosearch">查询</button>
							<button class="btn btn-success hide" id="export">导出数据</button>
						</div>
						<div class="smart-form">
							<table id="reportagentcallTable" class="table table-striped table-hover"></table>
						</div>
					</div>
				</div>
			</div>
		</article>
	</div>
</section>




<script type="application/javascript">
	
	

    var oTable ='';

    $(document).ready(function() {
    	$("#selectionReport").select2({
    		width:"100%"
    	});
    	$("#calltype").select2({
    		width:"100%"
    	});
    	
    	//获取筛选条件中统计时长中的数值
        var duration = $("#duration").val().split(',');
		
    	initTable(duration);
        //列隐藏显示，示例
//         $('a.toggle-vis').on( 'click', function (e) {
//             e.preventDefault();
     
//             // Get the column API object
//             var column = oTable.column( $(this).attr('data-column') );
     
//             // Toggle the visibility
//             column.visible( ! column.visible() );
//         } );
        
        
     $("#calltype").on('change',function(e){
    	 var type = $(this).val();
    	e.preventDefault();
    	if(type == "callout"){
    		for(var i=3;i<=12;i++){
    			//列显示
	    		oTable.column(i).visible(true);
    			
    		}
    		for(var i=13;i<=22;i++){
    			//列隐藏
    			oTable.column(i).visible(false);
    		}
    	
    	}else if(type == "callin"){
    		for(var i=3;i<=12;i++){
    			oTable.column(i).visible(false);
    		}
    		for(var i=13;i<=22;i++){
    			oTable.column(i).visible(true);
    		}
    	}
    	refresh();
    });
     
     $("#duration").on('change',function(){
    	 var duration = $(this).val().split(",");
    	 var table = $('iframe').contents().find("#reportagentcallTable");
    	 
     });

    });
       
    $("#selectionReport").change(function (){
    	var selectionReport=$("#selectionReport").val();
    	//月报
    	if(selectionReport=='month'){
			$("#timeSty").empty();
			var sb="<label class='input col col-1'>选择时间</label> <label class='input col col-2'> <input id='startTime' name='startTime' style='height:35px;' value='${(startMonthTime)!''}' type='text' class='ui_input_text 400w'  onclick=\"WdatePicker({dateFmt:'yyyy-MM',minDate:'%y-{%M-1}',maxDate:'%y-%M'});\" placeholder='选择年月'/></label>";
//    		sb+="<label class='input col col-1'>结束时间</label> <label class='input col col-2'> <input id='endTime' name='endTime' style='height:35px;' value='${(endTime)!''}' type='text' class='ui_input_text 400w'  onclick=\"WdatePicker({dateFmt:'yyyy-MM',minDate:'#F{$dp.$D(\\'startTime\\')}'});\" placeholder='选择年月'/></label>";
    		$("#timeSty").append(sb);
    		refresh();
    	//周报
		}else if(selectionReport=='week'){
			$("#timeSty").empty();
			var sb="<label class='input col col-1'>开始时间</label> <label class='input col col-2'> <input id='startTime' name='startTime' style='height:35px;' value='${(startWeekTime)!''}' type='text' class='ui_input_text 400w'  onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd  00:00:00',minDate:'#F{$dp.$D(\\'endTime\\',{d:-7})}',maxDate:'#F{$dp.$D(\\'endTime\\',{d:-1})}'});\" placeholder='选择时间'/></label>";
    		sb+="<label class='input col col-1'>结束时间</label> <label class='input col col-2'> <input id='endTime' name='endTime' style='height:35px;' value='${(endWeekTime)!''}' type='text' class='ui_input_text 400w'  onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd  00:00:00',minDate:'#F{$dp.$D(\\'startTime\\',{d:1})}',maxDate:'#F{$dp.$D(\\'startTime\\',{d:7})}'});\" placeholder='选择时间'/></label>";
    		$("#timeSty").append(sb);
    		refresh();
    	//日报
		}else if(selectionReport=='day'){
			$("#timeSty").empty();
			var sb="<label class='input col col-1'>开始时间</label> <label class='input col col-2'> <input id='startTime' name='startTime' style='height:35px;' value='${(startTime)!''}' type='text' class='ui_input_text 400w'  onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd  00:00:00',minDate:'#F{$dp.$D(\\'endTime\\',{M:-1})}',maxDate:'#F{$dp.$D(\\'endTime\\',{d:-1})}'});\" placeholder='选择时间'/></label>";
    		sb+="<label class='input col col-1'>结束时间</label> <label class='input col col-2'> <input id='endTime' name='endTime'  style='height:35px;' value='${(endTime)!''}' type='text' class='ui_input_text 400w'  onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd  00:00:00',minDate:'#F{$dp.$D(\\'startTime\\',{d:1})}',maxDate:'#F{$dp.$D(\\'startTime\\',{M:1})}'});\" placeholder='选择时间'/></label>";
    		$("#timeSty").append(sb);
    		refresh();
		}
    });

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
    	refresh();
    });
    
    function getResult(){
    	refresh();
    }
    
    function refresh(){
    	$('#reportagentcallTable').DataTable().ajax.reload(null,false);
    }
    
    function initTable(duration){
    	
        oTable = $('#reportagentcallTable').DataTable({
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
		    	"url" : getContext() + "report/agentcall/data",
		    	"type":"POST",
		    	"data" :function(param){
		    		param.selectionReport = $("#selectionReport").val(),
		    		param.starttime = $("#startTime").val(),
		    		param.endtime = $("#endTime").val()
		    		
		    		if($("#calltype").val() != undefined && $("#calltype").val() != null){
		    			
		    			param.calltype = $("#calltype").val();
	    			
		    			if($("#calltype").val() == "callout"){
		    				param.outduration = $("#duration").val();
		    			}else if($("#calltype").val() == "callin"){
		    				param.induration =  $("#duration").val();
		    			}
		    			
		    		}
		    		
		    	}
		    },
		 	"paging" :true,
			"pagingType" :"bootstrap",
			"lengthChange" : true,
			"deferRender" : true,
			"order" : [[ "0", "desc"]],
			"columns" : [
				   { "title" : "时间", "data" : "calltime"},
				   { "title" : "坐席号", "data" : "agentid"},
				   { "title" : "姓名", "data" : "agentname"},
				 
				   { "title" : "0~<span id='num1'>"+duration[0]+"</span>秒</br>呼出次数", "data" : "outcallcount_p1","defaultContent":"0"},
				   { "title" : "0~<span id='num1'>"+duration[0]+"</span>秒</br>呼出时长", "data" : "outcallduration_p1"},
				   { "title" : "<span id='num1'>"+duration[0]+"</span>~<span id='num2'>"+duration[1]+"</span>秒</br>呼出次数", "data" : "outcallcount_p2","defaultContent":"0"},
				   { "title" : "<span id='num1'>"+duration[0]+"</span>~<span id='num2'>"+duration[1]+"</span>秒</br>呼出时长", "data" : "outcallduration_p2"},
				   { "title" : "<span id='num2'>"+duration[1]+"</span>~<span id='num3'>"+duration[2]+"</span>秒</br>呼出次数", "data" : "outcallcount_p3","defaultContent":"0"},
				   { "title" : "<span id='num2'>"+duration[1]+"</span>~<span id='num3'>"+duration[2]+"</span>秒</br>呼出时长", "data" : "outcallduration_p3"},
				   { "title" : "<span id='num3'>"+duration[2]+"</span>秒以上</br>呼出次数", "data" : "outcallcount_p4","defaultContent":"0"},
				   { "title" : "<span id='num3'>"+duration[2]+"</span>秒以上</br>呼出时长", "data" : "outcallduration_p4"},
				   { "title" : "合计</br>呼出次数", "data" : "outcallcount","defaultContent":"0"},
				   { "title" : "合计</br>呼出时长", "data" : "outcallduration",},
			   
				   { "title" : "0~<span id='num1'>"+duration[0]+"</span>秒</br>呼入次数", "data" : "incallcount_p1","defaultContent":"0","visible":false},
				   { "title" : "0~<span id='num1'>"+duration[0]+"</span>秒</br>呼入时长", "data" : "incallduration_p1","visible":false},
				   { "title" : "<span id='num1'>"+duration[0]+"</span>~<span id='num2'>"+duration[1]+"</span>秒</br>呼入次数", "data" : "incallcount_p2","defaultContent":"0","visible":false},
				   { "title" : "<span id='num1'>"+duration[0]+"</span>~<span id='num2'>"+duration[1]+"</span>秒</br>呼入时长", "data" : "incallduration_p2","visible":false},
				   { "title" : "<span id='num2'>"+duration[1]+"</span>~<span id='num3'>"+duration[2]+"</span>秒</br>呼入次数", "data" : "incallcount_p3","defaultContent":"0","visible":false},
				   { "title" : "<span id='num2'>"+duration[1]+"</span>~<span id='num3'>"+duration[2]+"</span>秒</br>呼入时长", "data" : "incallduration_p3","visible":false},
				   { "title" : "<span id='num3'>"+duration[2]+"</span>秒以上</br>呼入次数", "data" : "incallcount_p4","defaultContent":"0","visible":false},
				   { "title" : "<span id='num3'>"+duration[2]+"</span>秒以上</br>呼入时长", "data" : "incallduration_p4","visible":false},
				   { "title" : "合计</br>呼入次数", "data" : "incallcount","defaultContent":"0","visible":false},
				   { "title" : "合计</br>呼入时长", "data" : "incallduration","visible":false},
					 
			],
			"headerCallback": function( thead, data, start, end, display ) {
				duration = $("#duration").val().split(',');
			    $(thead).find('th').find("#num1").text(duration[0]);
			    $(thead).find('th').find("#num2").text(duration[1]);
			    $(thead).find('th').find("#num3").text(duration[2]);
			    
			 },
		});
        
    }
    
    //报表导出
 /*  $("#export").click(function(){
	    
		var url = getContext()+"report/reportagentkpi/export?";
    	var selectionReport = $("#selectionReport").val();
    	var startTime = $("#startTime").val();
    	var endTime = $("#endTime").val();
    	var name = $("#name").val();
    	
    	if(selectionReport!= null && selectionReport!=''){url += "&selectionReport=" + selectionReport ;}
    	if(startTime!= null && startTime!=''){url += "&startTime=" + startTime ;}
    	if(endTime!= null && endTime!=''){url += "&endTime=" + endTime ;}
    	if(name!= null && name!=''){url += "&name=" + name ;}
    	
    	window.location.href=url;
	}) */


</script>