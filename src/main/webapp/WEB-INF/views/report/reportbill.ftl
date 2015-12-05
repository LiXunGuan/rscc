<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/calendar/WdatePicker.js"></script>
<div id="tmpDiv"></div>
<div id="userContent" style="">
	<!-- HEADER -->
	<!-- END RIBBON -->
	<!-- widget grid -->
	<section id="widget-grid-billing" class="">
		<section>
			<div class="row" style="margin:0;position:relative;">
				<table class="ui-pg-table navtable" id="toolBox">
					<tbody>
						<tr>
							<td class="ui-pg-button ui-state-disabled" style="width:6px;" data-original-title="" title="">
								<span class="ui-separator"></span>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</section>
		<div class="row">
			<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<div class="jarviswidget jarviswidget-color-darken" id="wid-id-0" data-widget-editbutton="false">
					<header>
						<span class="widget-icon"><i class="fa fa-table"></i></span>
						<h2>计费报表</h2>
					</header>
					<div>
						<div class="widget-body no-padding">
							<div class="widget-body-toolbar">
								<div class="smart-form">
									<section>
										<div class="row">
											<div class="col-11">
												<div class="col">
						<!--							<label class="radio radio-inline col">
															<input type="radio" class="radiobox today" name="style-0a" checked="checked" value="todaydata">
															<span>查看今天</span> 
														</label>                        -->
														<label class="radio radio-inline col">
															<input type="radio" class="radiobox history" name="style-0a">
															<span>查看历史</span>  
														</label>
												</div>
												<label class="label col">统计类型</label>
												<label class="input col col-2">
													<select name="show_type" id="show_type" disabled="disabled" class="select2" onchange="getResult();">
														<option value="day">按天统计</option>
														<option value="month">按月统计</option>
													</select>
												</label>
												<div id="time">
													<label class="label col">开始时间 </label>
													<label class="input col col-2">
														<input id="startTime" name="startTime" disabled="true" value="${((startTime)?string('yyyy-MM-dd'))!''}" type="text" class="ui_input_text 400w" onclick="WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00',maxDate:'#F{$dp.$D(\'endTime\')}'});" placeholder="开始时间" />
													</label>
													<label class="label col">结束时间 </label>
													<label class="input col col-2">
														<input id="endTime" name="endTime" disabled="true" value="${((endTime)?string('yyyy-MM-dd'))!''}" type="text" class="ui_input_text 400w" onclick="WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00',minDate:'#F{$dp.$D(\'startTime\')}'});" placeholder="结束时间" />
													</label>
												</div>
												<div class="col">
													<input class="btn btn-sm btn-default" type="button" onclick="upday(this);" id="upday" value="上一天" disabled="disabled"></input>
													<input class="btn btn-sm btn-default" type="button" onclick="today(this);" id="today" value="昨天" disabled="disabled"></input>
													<input class="btn btn-sm btn-default" type="button" onclick="nextday(this);" id="nextday" value="下一天" disabled="disabled"></input>
												</div>
												
												<label class="btn btn-sm btn-primary serch" onclick="getResult();">查询</label>
												<input class="btn btn-sm btn-success" type="button" style="visibility:hidden" id="exportdata" value="导出报表"></input>
											</div>
										</div>
									</section>
									<section>
										<div class="row">
											<div class="col-12">
												<div class="col">
													<label class="radio radio-inline col">
														<input type="radio" class="radiobox today" name="style-0a" checked="checked" value="todaydata">
														<span>查看今天</span> 
													</label>
												</div>
												<label class="label col">计费对象</label>
												<label class="input col col-2">
													<select name="billing_type" id="billing_type" class="select2" onchange="getResult();">
														<option value="" selected="selected">所有计费对象</option>
														<option value="0">分机</option>
														<option value="1">技能组</option>
													</select>
												</label>
												<label class="label col">分机号</label>
												<label class="input col col-2">
													<input type="text" id="name" name="name"/>
												</label>
											</div>
										</div>
									</section>
									<table id="tableBill" class="table table-striped table-bordered table-hover" data-order='[[ 1, "asc" ]]'></table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</article>
		</div>
	</section>
</div>

<script type="text/javascript">

	$(document).ready(function() {
		
		$("#billing_type").select2({
			width:"100%"
		});
		$("#show_type").select2({
			width:"100%"
		});
		
		$('#tableBill').DataTable({
			"dom": "t" + "<'dt-toolbar-footer'<'col-sm-6 col-xs-12'i><'col-xs-12 col-sm-6 text-right'lp>>",
			"autoWidth": true,
			"ordering": true,
			"serverSide": true,
			"processing": true,
			"searching": false,
			"pageLength": 10,
			"lengthMenu": [10, 15, 20, 25, 30],
			"language": {
				"url": getContext() + "public/dataTables.cn.txt"
			},
			"ajax": {
				"url": getContext() + "report/bill/data",
				"type": "POST",
				"data": function(param) {
					param.billing_type = $("#billing_type").val();
					param.Stime = $("#startTime").val();
					param.Etime = $("#endTime").val();
					param.show_type = $("#show_type").val();
					param.name = $("#name").val();
					if($(".today").attr("checked")=="checked"){
						param.billing_date = $(".today").val();
					}else{
						param.billing_date = "";
					}
					
				}
			},
			"infoCallback":function(settings, start, end, max, total, pre){
		    	var obj = $('#tableBill').DataTable().ajax.json();
		    	var statistics = obj==undefined?0:obj.statistics;
		    	var sumcost = +statistics.sumcost;
		    	return "显示第 " + start + " 至 " + end + " 项结果，共 " + total + " 项。费用共计 " + (sumcost?sumcost.toFixed(2):0) + " 元 ";
		    },
			"paging": true,
			"pagingType": "bootstrap",
			"lengthChange": true,
			"order": [
				["0", "desc"]
			],
			"columns": [{
					"title": "计费类型",
					"data": "billing_type",
					"orderable": true,
					"render": function(data, type, full) {
						if (full.billing_type == 0) {
							return "分机";
						} else {
							return "技能组";
						}
					},
					defaultContent: ""
				}, {
					"title": "名称",
					"data": "name",
					"orderable": true,
					defaultContent: ""
				},
				/* 	
						{ "title" : "通话类型","data" : "call_type" ,"orderable" : true, defaultContent : "",
						  "render":function(data,type,full){
						   if(full.call_type==1){
						             return "市话";
						      }else{
						            return "长途";
						             }
						   }
						 }, */
				{
					"title": "总花费",
					"data": "call_charge",
					"orderable": true,
					defaultContent: ""
				}
			],
		});
	});

	$(".today").click(function(){
		$("#exportdata").attr("style","visibility:hidden");
		$("#startTime").attr("disabled",true);
		$("#endTime").attr("disabled",true);
		$(".today").attr("value","todaydata");
		$(".today").attr("checked","checked");
		$(".history").attr("checked",null);
		$("#show_type").attr("disabled",true);
		$("#upday").attr("disabled",true);
		$("#today").attr("disabled",true);
		$("#nextday").attr("disabled",true);
		document.getElementById("startTime").value = "";
		document.getElementById("endTime").value = "";
		$('#tableBill').DataTable().ajax.reload(null, false);
	});
	$(".history").click(function(){
		$("#startTime").val('${yesterdaystart}');
		$("#endTime").val('${yesterdayend}');
		$("#exportdata").attr("style","visibility:show");
		$("#startTime").attr("disabled",false);
		$("#endTime").attr("disabled",false);
		$("#upday").attr("disabled",false);
		$("#today").attr("disabled",false);
		$("#nextday").attr("disabled",false);
		$("#show_type").attr("disabled",false);
		$(".today").attr("value",null);
		$(".today").attr("checked",null);
		$(".history").attr("checked","checked");
		$('#tableBill').DataTable().ajax.reload(null, false);
	});
	
	/* 查询结果 */
	function getResult() {
			$('#tableBill').DataTable().ajax.reload(null, false);
			
		}
	$("#show_type").change(function(){
		if ($("#show_type").val() === "month") {
			$("#startTime").val('${firstdayofmonth}');
			$("#endTime").val('${lastdayofmonth}');
			document.getElementById("upday").value = "上一月";
			document.getElementById("today").value = "本月";
			document.getElementById("nextday").value = "下一月";
		} else {
			$("#startTime").val('${yesterdaystart}');
			$("#endTime").val('${yesterdayend}');
			document.getElementById("upday").value = "上一天";
			document.getElementById("today").value = "昨天";
			document.getElementById("nextday").value = "下一天";
		}
	});
	/*时间转化函数*/
	    Date.prototype.format = function(format) {
			var o = {
				"M+": this.getMonth() + 1, //month 
				"d+": this.getDate(), //day 
				"h+": this.getHours(), //hour 
				"m+": this.getMinutes(), //minute 
				"s+": this.getSeconds(), //second 
				"q+": Math.floor((this.getMonth() + 3) / 3), //quarter 
				"S": this.getMilliseconds() //millisecond 
			}
			if (/(y+)/.test(format)) {
				format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
			}
			for (var k in o) {
				if (new RegExp("(" + k + ")").test(format)) {
					format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
				}
			}
			return format;
		}
	    
	/*按天获取天期*/
	function GetDateStr(AddDayCount) {
			var dd = null;
			if ($("#startTime").val() == null || $("#startTime").val() == "" && $("#endTime").val() == null || $("#endTime").val() == "") {
				dd = new Date();
			} else {
				//火狐浏览器下需要将-格式化为/ 否则无法识别
				dd = new Date(Date.parse($("#endTime").val().replace(/-/g,"/")));
			}
			dd.setDate(dd.getDate() + AddDayCount); //获取AddDayCount天后的天期    
			var y = dd.getFullYear();
			var m = dd.getMonth() + 1; //获取当前月份的天期
			if (m < 10) {
				m = "0" + m;
			}
			var d = dd.getDate();
			if (d < 10) {
				d = "0" + d;
			}
			return y + "-" + m + "-" + d + "  00:00:00";
		}
	/*根据传入天期获取当月最后一天的时间*/
	  function getLastDay(date,num) {      
	  	if (date==null || date=="") {
	  		date = new Date().format("yyyy-MM-dd");
	  	}
             var new_year = date.split("-")[0];    //取当前的年份          
             var new_month = date.split("-")[1]++;//取下一个月的第一天，方便计算（最后一天不固定）          
             if(date.split("-")[1]>12) {         
              new_month -=12;        //月份减          
              new_year++;            //年份增          
             }
             new_month=+new_month+num;
             if(new_month>12) {         
	              new_month -=12;        //月份减          
	              new_year++;            //年份增          
             }
             if (new_month<10) {
             	new_month = "0"+new_month;
             }
             if (new_month<1) {
             	new_month = 12 ;
             	new_year--;
             }
             
             var new_date = new Date(new_year,new_month,01);                       
             return new_year+"-"+new_month+"-"+(new Date(new_date.getTime()-1000*60*60*24)).getDate()+ "  00:00:00";//获取当月最后一天日期          
        }
	  /*根据传入天期获取当月第一天的时间*/
	function getFirstDay(date,num){
		if (date==null || date=="") {
	  		date = new Date().format("yyyy-M-dd");
	  	}
			var new_year = date.split("-")[0];    //取当前的年份
			var new_month = date.split("-")[1];  //取出来当前月份
			new_month=+new_month+num;
			if(new_month>12) {         
              new_month -=12;        //月份减          
              new_year++;            //年份增          
             }
             if (new_month<10) {
             	new_month = "0"+new_month;
             }
             if (new_month<1) {
             	new_month =12;
             	new_year--;
             }
             
			return new_year+"-"+new_month+"-"+"01" + "  00:00:00";
	}
		
	/*查询上一天、月*/
	function upday(obj) {
		
			if (obj.value == "上一天") {
				
				document.getElementById("startTime").value = GetDateStr(-2);
				document.getElementById("endTime").value = GetDateStr(-1);
				$('#tableBill').DataTable().ajax.reload(null, false);
			} else if (obj.value == "上一月") {
				
				document.getElementById("startTime").value = getFirstDay($("#endTime").val(),-1);
				document.getElementById("endTime").value = getLastDay($("#endTime").val(),-1);
				$('#tableBill').DataTable().ajax.reload(null, false);
			}
		}
		/*查询本天、月*/

	function today(obj) {
			if (obj.value == "昨天") {
				
				document.getElementById("startTime").value = "";
				document.getElementById("endTime").value = "";
				$('#tableBill').DataTable().ajax.reload(null, false);
			} else if (obj.value == "本月") {
				
				document.getElementById("startTime").value = "";
				document.getElementById("endTime").value = "";
				$('#tableBill').DataTable().ajax.reload(null, false);
			}
		}
		/*查询下一天、月*/

	function nextday(obj) {
		if (obj.value == "下一天") {
			
			var now = new Date();
			var nowStr = now.format("yyyy-MM-dd");
			if ($("#startTime").val() == null || $("#startTime").val() == "" &&
				$("#endTime").val() == null || $("#endTime").val() == "" || $("#endTime").val() == nowStr) {
				javascript: void(0);
			} else {
				document.getElementById("startTime").value = GetDateStr(0);
				document.getElementById("endTime").value = GetDateStr(+1);
				$('#tableBill').DataTable().ajax.reload(null, false);
			}
		} else if (obj.value == "下一月") {
			
			var now = new Date();
			var nowStr = now.format("yyyy-MM-dd");
			if ($("#startTime").val() == null || $("#startTime").val() == "" &&
				$("#endTime").val() == null || $("#endTime").val() == "" || $("#startTime").val() == getFirstDay(nowStr,0)) {
				javascript: void(0);
			} else {
				document.getElementById("startTime").value = getFirstDay($("#endTime").val(),+1);
				document.getElementById("endTime").value = getLastDay($("#endTime").val(),+1);
//				$("#endTime").val(getLastDay($("#endTime").val(),+1))
				$('#tableBill').DataTable().ajax.reload(null, false);
			}
		}
	}
	
	/*导出搜索结果 */
	    $("#exportdata").click(function (){
	    	var url = getContext()+"report/bill/export?";
	    	
	    	var show_type = $("#show_type").val();
	    	var Stime = $("#startTime").val();
	    	var Etime = $("#endTime").val();
	    	var billing_type = $("#billing_type").val();
	    	
	    	if(show_type!= null && show_type!=''){url += "&show_type=" + show_type ;}
	    	if(Stime!= null && Stime!=''){url += "&Stime=" + Stime ;}
	    	if(Etime!= null && Etime!=''){url += "&Etime=" + Etime ;}
	    	if(billing_type!= null && billing_type!=''){url += "&billing_type=" + billing_type ;}
	    	
	    	window.location.href=url;
	    });
</script>