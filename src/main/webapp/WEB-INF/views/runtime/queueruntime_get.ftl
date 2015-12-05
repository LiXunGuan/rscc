<section id="widget-grid-queueruntime">
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
					<h2>技能组详情</h2>
				</header>
				<div>
					<div class="jarviswidget-editbox"></div>
					<div class="widget-body no-padding">
						<div class="widget-body-toolbar">
							<label>技能组查询:</label> 
							<select id="numbers" name="numbers" style="height: 35px;">
								<#if queues??> 
									<#list queues?keys as key> 
										<option value="${key}">${queues[key]}</option>
									</#list>
								<#else>
									<option value="">没有可用</option>
								</#if>
							</select>
							<label>接通次数:</label>
							<label style="width: 50px;" id="in_answer_count" name="in_answer_count" ></label>
							<label>总次数:</label>
							<label style="width: 50px;" id="in_count" name="in_count" ></label>
							<label>接起率:</label>
							<label style="width: 50px;" id="pick_up_rate" name="pick_up_rate" ></label>
							<label>接听速率:</label>
							<label style="width: 50px;" id="misscount" name="misscount" ></label>
							<label>排队人数:</label>
							<label style="width: 50px;" id="member_count" name="member_count" ></label>
							<label>未通话（闲）:</label>
							<label style="width: 50px;" id="idle_ready_count" name="idle_ready_count" ></label>
							<label>未通话（忙）:</label>
							<label style="width: 50px;" id="busy_ready_count" name="busy_ready_count" ></label>
							<label>通话中:</label>
							<label style="width: 50px;" id="not_ready_count" name="not_ready_count" ></label>
							<label>不在线:</label>
							<label style="width: 50px;" id="offline_count" name="offline_count" ></label>
						</div>
						<div class="smart-form">
						<table id="getMemberTimeTable" class="table table-striped table-hover"></table>
						<br/>
						<br/>
						<table id="getQueueRunTimeTable" class="table table-striped table-hover"></table>
						</div>
					</div>
				</div>
			</div>
		</article>
	</div>
	<div id="forModel"></div>
</section>

<script type="application/javascript">

	$('#numbers').select2({
	    allowClear : true,
	    width:'10%'
	});
	
	var mTable ='';
	var oTable ='';
	
	$("#numbers").change(function(){
		$('#getMemberTimeTable').DataTable().ajax.reload(null,false);;
		$('#getQueueRunTimeTable').DataTable().ajax.reload(null,false);;
	});
	
	$(document).ready(function() {
	    initTable();
	});
	
	
	function initTable(){
		
		mTable = $('#getMemberTimeTable').dataTable({
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
		    	"url" : getContext() + "runtime/queueruntime/getmembers",
		    	"type":"POST",
		    	"data" :function(param){
		    		param.numbers= $("#numbers").val();
		    	}
		    },
		 	"paging" :true,
			"pagingType" :"bootstrap",
			"lengthChange" : true,
			"order" : [[ "3", "desc"]],
			"columns" : [
				   { "title" : "队列", "data" : "name"},
				   { "title" : "位置", "data" : "number"}, 
				   { "title" : "号码", "data" : "cid_number"},
				   { "title" : "等待时间(秒)", "data" : "joined_epoch"}
			],
		});
		
		oTable = $('#getQueueRunTimeTable').dataTable({
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
		    	"url" : getContext() + "runtime/queueruntime/getqueue",
		    	"type":"POST",
		    	"data" :function(param){
		    		param.numbers= $("#numbers").val();
		    	}
		    },
		 	"paging" :true,
			"pagingType" :"bootstrap",
			"lengthChange" : true,
			"order" : [[ "0", "desc"]],
			"columns" : [
				   { "title" : "标示", "data" : "agent_uid"},
				   { "title" : "描述", "data" : "agent_info"}, 
				   { "title" : "分机号", "data" : "extern"},
				   { "title" : "坐席状态", "data" : "status"},
				   { "title" : "坐席通话状态", "data" : "state"},
				   { "title" : "号码", "data" : "number"},
				   { "title" : "时长（秒）", "data" : "up_time"},
				   { "title" : "接听数（次）", "data" : "in_count"},
				   { "title" : "漏接数（次）", "data" : "miss_count"}
// 				   { "title" : "监听", "data" : "null","render":function(data,type,full){
// 					   if(full.up_time>0){
//                            return '<a onclick="tospy(\''+full.uuid+'\')">监听</a>'+'&nbsp;&nbsp;<a onclick="tokill(\''+full.uuid+'\')">强拆</a>';;
//                        }
//                        return '';
// 				   }}, 
			],
			"drawCallback" : function(setting){
				var json = $('#getQueueRunTimeTable').DataTable().ajax.json();
				$('#in_answer_count').text(json.jsonQueue.in_answer_count);
		        $('#in_count').text(json.jsonQueue.in_count);
		        $('#pick_up_rate').text(json.jsonQueue.pick_up_rate);
		        $('#misscount').text(json.jsonQueue.misscount);
		        $('#member_count').text(json.jsonQueue.member_count);
		        $('#busy_ready_count').text(json.jsonQueue.busy_ready_count);
		        $('#idle_ready_count').text(json.jsonQueue.idle_ready_count);
		        $('#not_ready_count').text(json.jsonQueue.not_ready_count);
		        $('#offline_count').text(json.jsonQueue.offline_count);
			}
		});
	}
	
	function tospy(uuid){
	    $('#forModel').empty();
	    var url = getContext() + "runtime/queueruntime/tospy";
	    $.post(url,{uuid:uuid},function(data) {
	        $("#forModel").append(data);
	    });
	};
	
	function tokill(uuid){
	    $('#forModel').empty();
	    var url = getContext() + "runtime/queueruntime/tokill";
	    $.post(url,{uuid:uuid},function(data) {
	        if(data.success){
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
	        oTable.DataTable().ajax.reload(null,false);;
	    },"json");
	};
	
	function refresh(){
		if($("[aria-controls='tabs-runtimequeueruntimeget']").is(':visible') == false){
			clearInterval();
			return false;
		}
		oTable.DataTable().ajax.reload(null,false);;
		mTable.DataTable().ajax.reload(null,false);;
	};
	
	curInterval = setInterval("refresh();",5000);

</script>


