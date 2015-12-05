<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/flot/jquery.flot.cust.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/flot/jquery.flot.resize.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/flot/jquery.flot.tooltip.js"></script>
<script src= "${springMacroRequestContext.contextPath}/assets/js/jqPaginator.js"></script>

<section id="" class="">
	<div class="row ">
	
	<article class="col-sm-12 col-md-12 col-lg-6">
			<div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-2" data-widget-editbutton="false">
				<header>
					<span class="widget-icon"> <i class="fa fa-table"></i>
					</span>
					<h2>集群状态</h2>
				</header>
				<div>
					<div class="jarviswidget-editbox"></div>
					<div class="widget-body no-padding">
						<table class="table table-hover">
							<thead>
								<tr>
									<th>主机名称</th>
									<th>主机 IP</th>
									<th>AG 状态</th>
									<th>ESL 状态</th>
								</tr>
							</thead>
							<tbody id="fshost"></tbody>
						</table>
					</div>
				</div>
			</div>
		</article>
		<article class="col-sm-12 col-md-12 col-lg-6">
			<!-- new widget -->
			<div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-0" 
				data-widget-togglebutton="false" data-widget-editbutton="false"
				data-widget-fullscreenbutton="false" data-widget-colorbutton="false"
				data-widget-deletebutton="false">

				<header>
					<span class="widget-icon"> <i
						class="glyphicon glyphicon-stats txt-color-darken "></i>
					</span>
					<h2>总并发</h2>

				</header>

				<!-- widget div-->
				<div class="no-padding">
					<!-- widget edit box -->
					<div class="jarviswidget-editbox">test</div>
					<!-- end widget edit box -->

					<div class="widget-body">
						<!-- content -->
						<div id="myTabContent" class="tab-content">
							<div class="tab-pane fade active in padding-10 no-padding-bottom" id="s1" >
								<div class="row no-space" >
									<div class="col-xs-12 col-sm-12 col-md-8 col-lg-8" style="width: 100%;">
<!-- 										<span class="demo-liveupdate-1">  -->
<!-- 										<span class="onoffswitch-title">Live switch</span>  -->
<!-- 										<span class="onoffswitch">  -->
<!-- 											<input type="checkbox" name="start_interval" class="onoffswitch-checkbox" id="start_interval">  -->
<!-- 											<label class="onoffswitch-label" for="start_interval"> <span -->
<!-- 													class="onoffswitch-inner" data-swchon-text="ON" -->
<!-- 													data-swchoff-text="OFF"></span> <span -->
<!-- 													class="onoffswitch-switch"></span> -->
<!-- 											</label> -->
<!-- 										</span> -->
<!-- 										</span> -->
										<div id="updating-chart" class="chart-large txt-color-blue"></div>

									</div>
									

								</div>

								
							</div>
							<!-- end s1 tab pane -->

<!-- 							<div class="tab-pane fade" id="s2"> -->
<!-- 								<div class="widget-body-toolbar bg-color-white"> -->

<!-- 									<form class="form-inline" role="form"> -->

<!-- 										<div class="form-group"> -->
<!-- 											<label class="sr-only" for="s123">Show From</label> <input -->
<!-- 												type="email" class="form-control input-sm" id="s123" -->
<!-- 												placeholder="Show From"> -->
<!-- 										</div> -->
<!-- 										<div class="form-group"> -->
<!-- 											<input type="email" class="form-control input-sm" id="s124" -->
<!-- 												placeholder="To"> -->
<!-- 										</div> -->

<!-- 										<div class="btn-group hidden-phone pull-right"> -->
<!-- 											<a class="btn dropdown-toggle btn-xs btn-default" -->
<!-- 												data-toggle="dropdown"><i class="fa fa-cog"></i> More <span -->
<!-- 												class="caret"> </span> </a> -->
<!-- 											<ul class="dropdown-menu pull-right"> -->
<!-- 												<li><a href="javascript:void(0);"><i -->
<!-- 														class="fa fa-file-text-alt"></i> Export to PDF</a></li> -->
<!-- 												<li><a href="javascript:void(0);"><i -->
<!-- 														class="fa fa-question-sign"></i> Help</a></li> -->
<!-- 											</ul> -->
<!-- 										</div> -->

<!-- 									</form> -->

<!-- 								</div> -->
<!-- 								<div class="padding-10"> -->
<!-- 									<div id="statsChart" class="chart-large has-legend-unique"></div> -->
<!-- 								</div> -->

<!-- 							</div> -->
<!-- 							end s2 tab pane -->

							
						</div>

						<!-- end content -->
					</div>

				</div>
				<!-- end widget div -->
			</div>
			<!-- end widget -->

		</article>
		


	</div>
</section>
<section id="" class="">
	<div class="row">
		<article class="col-sm-12 col-md-12 col-lg-6">
			<div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-2" data-widget-editbutton="false">
				<header>
					<span class="widget-icon"> <i class="fa fa-table"></i>
					</span>
					<h2>Gateway</h2>
				</header>
				<div>
					<div class="jarviswidget-editbox"></div>
					<div class="widget-body no-padding">
						<table class="table table-hover">
							<thead>
								<tr>
									<th>名称</th>
									<th>IP</th>
									<th>状态</th>
								</tr>
							</thead>
							<tbody id="gateWay"></tbody>
						</table>
					</div>
				</div>
			</div>

		</article>
		<article class="col-sm-12 col-md-12 col-lg-6">
			<div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-2" data-widget-editbutton="false">
				<header>
					<span class="widget-icon"> <i class="fa fa-table"></i>
					</span>
					<h2>接入号状态</h2>
				</header>
				<div>
					<div class="jarviswidget-editbox"></div>
					<div class="widget-body no-padding">
						<table class="table table-hover">
							<thead>
								<tr>
									<th>接入号</th>
									<th>实时并发</th>
									<th>并发总数</th>
									<th>图形显示</th>
								</tr>
							</thead>
							<tbody id="concurrent"></tbody>
						</table>
					</div>
					<ul class="pagination" id="pagination1"></ul>
				</div>
			</div>
		</article>
	</div>
</section>



<script type="text/javascript">
$(document).ready(function(){
	loadFrom();
	gettitleshow();
	getConcurrentSum();
})

 function gettitleshow(){
    	var url=getContext() + "/runtime/sysinforuntime/menushow"
    	$.post(url,function(data){
     		$("#titleshow").html(data.titleshow);
    	},'json');
    }

//分页 
function createPage(totalcounts) {
	 $.jqPaginator('#pagination1', {
		totalCounts: totalcounts,//分页的总条目数
		pageSize: 10,//每一页的条目数
        visiblePages: 10,//最多显示的页码数
        currentPage: 1,//当前页
        first: '<li class="first"><a href="javascript:;">首页</a></li>',
        prev: '<li class="prev"><a href="javascript:;">上一页</a></li>',
        next: '<li class="next"><a href="javascript:;">下一页</a></li>',
        last: '<li class="last"><a href="javascript:;">末页</a></li>',
        page: '<li class="page"><a href="javascript:;">{{page}}</a></li>',
        onPageChange: function (num, type) {
        	var urls = getContext() + "/runtime/sysinforuntime/getconcurrent";
            $.post(urls, {pageNum:num}, function(data){
				if(data.result != "false"){
					$("#concurrent").empty();
					var sb="";
					for (var i = 0;i < data.length; i++) {
						 sb+="<tr><td></td><td></td><td></td><td></td></tr>";
					};
					$("#concurrent").append(sb);
					for (var i in data) {
						 if(typeof data[i] !="function"){
							$("#concurrent").find("tr").eq(i).find("td").eq(0).html(data[i].name);
							$("#concurrent").find("tr").eq(i).find("td").eq(1).html(data[i].destmark);
							$("#concurrent").find("tr").eq(i).find("td").eq(2).html(data[i].destmarksum);
							var percentage=0;
							if (data[i].destmarksum!=0) {
								percentage=(data[i].destmark/data[i].destmarksum*100);
							}
							if (percentage<20) {
								 $("#concurrent").find("tr").eq(i).find("td").eq(3).html("<div class='progress progress-sm' style='margin-bottom: 0px;'><div class='progress-bar bg-color-tealLight' style='width:"+percentage+"% ' ></div></div>");
							}else if(percentage<40){
								$("#concurrent").find("tr").eq(i).find("td").eq(3).html("<div class='progress progress-sm' style='margin-bottom: 0px;'><div class='progress-bar bg-color-teal' style='width:"+percentage+"% ' ></div></div>");
							}else if(percentage<60){
								$("#concurrent").find("tr").eq(i).find("td").eq(3).html("<div class='progress progress-sm' style='margin-bottom: 0px;'><div class='progress-bar bg-color-blueLight' style='width:"+percentage+"% ' ></div></div>");
							}else if(percentage<80){
								$("#concurrent").find("tr").eq(i).find("td").eq(3).html("<div class='progress progress-sm' style='margin-bottom: 0px;'><div class='progress-bar bg-color-blue' style='width:"+percentage+"% ' ></div></div>");
							}else if(percentage<100){
								$("#concurrent").find("tr").eq(i).find("td").eq(3).html("<div class='progress progress-sm' style='margin-bottom: 0px;'><div class='progress-bar bg-color-redLight' style='width:"+percentage+"% ' ></div></div>");
							}else if(percentage>=100){
								$("#concurrent").find("tr").eq(i).find("td").eq(3).html("<div class='progress progress-sm' style='margin-bottom: 0px;'><div class='progress-bar bg-color-red' style='width:"+percentage+"% ' ></div></div>");
							}
						 }
					}
				}
	  		},"json");
        }
	});
}

function loadFrom(){
	var url = getContext() + "/runtime/sysinforuntime/data";
	 $.post(url,function(data) {
		 $('#concurrent').empty();
		 if (data.gatawayresult=="false") {
				$("#gateWay").append("没有找到SipTrunk！");
			}else{
				var sb="";
				for (var i=0;i<parseInt(data.gatewaycount);i++) {
					sb+="<tr><td></td><td></td><td></td></tr>";
				}
				$("#gateWay").append(sb);
			}
		 
		 if (data.concurrentresult=="false") {
				$("#concurrent").append("没有找到并发！");
			}else{
				createPage(parseInt(data.concurrentcount));
			}
		 if (data.fshostresult=="false") {
				$("#fshost").append("没有找到集群！");
			}else{
				var sb="";
				for (var i=0;i<parseInt(data.fshostcount);i++) {
						 sb+="<tr><td></td><td></td><td></td><td></td></tr>";
				}
				$("#fshost").append(sb);
			}
		 getGateWay();
	 },'json');
}


function getGateWay(){
	    var url = getContext() + "/runtime/sysinforuntime/getgateway";
	    $.post(url,function(data) {
	    	if (data.result=="false") {
	    		$('#gateWay').empty();
				$("#gateWay").append("没有找到SipTrunk！");
			}else{
				for (var i in data) {
					 if(typeof data[i] !="function"){
						 $("#gateWay").find("tr").eq(i).find("td").eq(0).html(data[i].name);
						 $("#gateWay").find("tr").eq(i).find("td").eq(1).html(data[i].ip);
						 if (data[i].type=="true") {
							 $("#gateWay").find("tr").eq(i).find("td").eq(2).html("<span class='label label-success'>UP</span>");
						}else if (data[i].type=="false"){
							$("#gateWay").find("tr").eq(i).find("td").eq(2).html("<span class='label label-danger'>DOWN</span>");
						}else{
							$("#gateWay").find("tr").eq(i).find("td").eq(2).html("<span class='label label-warning'>WARNING</span>");
						}
						
					 }
				}
			}
	    },'json');
	    
// 	    var urls = getContext() + "/runtime/sysinforuntime/getconcurrent";
// 	    $.post(urls,function(data) {
// 	    	if (data.result=="false") {
// 	    		$('#concurrent').empty();
// 				$("#concurrent").append("没有找到并发！");
// 			}else{
// 				for (var i in data) {
// 					 if(typeof data[i] !="function"){
// 						$("#concurrent").find("tr").eq(i).find("td").eq(0).html(data[i].name);
// 						$("#concurrent").find("tr").eq(i).find("td").eq(1).html(data[i].destmark);
// 						$("#concurrent").find("tr").eq(i).find("td").eq(2).html(data[i].destmarksum);
// 						var percentage=0;
// 						if (data[i].destmarksum!=0) {
// 							percentage=(data[i].destmark/data[i].destmarksum*100);
// 						}
// 						if (percentage<20) {
// 							 $("#concurrent").find("tr").eq(i).find("td").eq(3).html("<div class='progress progress-sm' style='margin-bottom: 0px;'><div class='progress-bar bg-color-tealLight' style='width:"+percentage+"% ' ></div></div>");
// 						}else if(percentage<40){
// 							$("#concurrent").find("tr").eq(i).find("td").eq(3).html("<div class='progress progress-sm' style='margin-bottom: 0px;'><div class='progress-bar bg-color-teal' style='width:"+percentage+"% ' ></div></div>");
// 						}else if(percentage<60){
// 							$("#concurrent").find("tr").eq(i).find("td").eq(3).html("<div class='progress progress-sm' style='margin-bottom: 0px;'><div class='progress-bar bg-color-blueLight' style='width:"+percentage+"% ' ></div></div>");
// 						}else if(percentage<80){
// 							$("#concurrent").find("tr").eq(i).find("td").eq(3).html("<div class='progress progress-sm' style='margin-bottom: 0px;'><div class='progress-bar bg-color-blue' style='width:"+percentage+"% ' ></div></div>");
// 						}else if(percentage<100){
// 							$("#concurrent").find("tr").eq(i).find("td").eq(3).html("<div class='progress progress-sm' style='margin-bottom: 0px;'><div class='progress-bar bg-color-redLight' style='width:"+percentage+"% ' ></div></div>");
// 						}else if(percentage>=100){
// 							$("#concurrent").find("tr").eq(i).find("td").eq(3).html("<div class='progress progress-sm' style='margin-bottom: 0px;'><div class='progress-bar bg-color-red' style='width:"+percentage+"% ' ></div></div>");
// 						}
// 					 }
// 				}
// 			}
// 	    },'json');
	    var url = getContext() + "/runtime/sysinforuntime/getfshost";
	    $.post(url,function(data) {
	    	if (data.result=="false") {
	    		$('#fshost').empty();
				$("#fshost").append("没有找到集群！");
			}else{
				for (var i in data) {
					 if(typeof data[i] !="function"){
						 $("#fshost").find("tr").eq(i).find("td").eq(0).html(data[i].name);
						 $("#fshost").find("tr").eq(i).find("td").eq(1).html(data[i].ip);
						 if (data[i].agstate=="true") {
							 $("#fshost").find("tr").eq(i).find("td").eq(2).html("<span class='label label-success'>UP</span>");
						}else if (data[i].agstate=="false"){
							$("#fshost").find("tr").eq(i).find("td").eq(2).html("<span class='label label-danger'>DOWN</span>");
						}else{
							$("#fshost").find("tr").eq(i).find("td").eq(2).html("<span class='label label-warning'>WARNING</span>");
						}
						 
						 if (data[i].eslstate=="true") {
							 $("#fshost").find("tr").eq(i).find("td").eq(3).html("<span class='label label-success'>UP</span>");
						}else if (data[i].eslstate=="false"){
							$("#fshost").find("tr").eq(i).find("td").eq(3).html("<span class='label label-danger'>DOWN</span>");
						}else{
							$("#fshost").find("tr").eq(i).find("td").eq(3).html("<span class='label label-warning'>WARNING</span>");
						}
						
						
					 }
				}
			}
	    },'json');
	    
	}

 curInterval = setInterval("queryInterval();",5000);

function queryInterval(){
    getGateWay();
    getConcurrentSum();

}

function getConcurrentSum(){
	 var url = getContext() + "/runtime/sysinforuntime/getconcurrentsum";
	    $.post(url,function(da) {
	    	if (da.result=="false") {
	    		$('#s1').empty();
				$("#s1").append("没有找到并发！");
			}else{
				for (var i in da) {
					 if(typeof da[i] !="function"){

 						var sunNum=da.sunNum;

						 var $on = false;
						    // 使用生成的数据/演示，但通常将从服务器
						    var data =da.concurrentNum,
						        totalPoints = 400,
						        $UpdatingChartColors = $("#updating-chart").css('color');
						
						    function getRandomData() {
						        if (data.length > 0)
						        	//去掉数组最前一位
						            data = data.slice(1);
						
						        
// 						        // 做一个随机游走
// 						        while (data.length < 100) {
// 				                y = 100;
// 						            data.push(da.num2);
// 						        }
						
						        // 赋值产生的y值x值
						        var res = [];
						        for (var i = 0; i < data.length; ++i)
						            res.push([i, data[i]])
						        return res;
						    }
						
						    
						    // 设置控件刷新时间
						    var updateInterval = 5000; 
						    
						    $("#updating-chart").val(updateInterval).change(function () {
						
						        var v = $(this).val();
						        if (v && !isNaN(+v)) {
						            updateInterval = +v;
						            $(this).val("" + updateInterval);
						        }
						    });
						  
						
						    // 安装图
						    var options = {
						        yaxis: {
						            min: 0,	//纵坐标最小值
						            max: sunNum //纵坐标最大值
						        },
						        xaxis: {
						            min: 0,	//横坐标最小值
						            max: 10	//横坐标最大值
						        },
						       
						        //背景样式的设定
						        colors: [$UpdatingChartColors],
						        series: {
						            lines: {
						                lineWidth: 1,
						                fill: true,
						                fillColor: {
						                    colors: [{
						                        opacity: 0.6
						                    }, {
						                        opacity: 0
						                    }]
						                },
						                steps: false
						
						            }
						        }
						    };
						
						    var plot = $.plot($("#updating-chart"), [getRandomData()], options);
						
// 						    // 现场开关
// 						    $('input[type="checkbox"]#start_interval').click(function () {
// 						        if ($(this).prop('checked')) {
// 						            $on = true;
// 						            updateInterval = 5000;
// 						            update();
// 						        } else {
// 						            clearInterval(updateInterval);
// 						            $on = false;
// 						        }
// 						    });
						
// 						    function update() {
// 						        if ($on == true) {
// 						            plot.setData([getRandomData()]);
// 						            plot.draw();
// 						            setTimeout(update, updateInterval);
						
// 						        } else {
// 						            clearInterval(updateInterval)
// 						        }
						
// 						    }
						    
						    
						 
					 }
				}
			}
	    },'json');
}



function generatePageGraphs() {
	}
</script>
