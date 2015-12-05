<style>
#widget-grid-callout div.dataTables_info
{
	font-style:normal;
}
#callouttoolbar.widget-body-toolbar 
{
	bottom: 0px;
  	position: absolute;
  	width: 100%;
  	margin-bottom: 0px;
}

</style>
<section id="widget-grid-callout">
	<div class="row">
		<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<div class="jarviswidget jarviswidget-color-darken" id="wid-id-maincall" data-widget-editbutton="true" data-widget-colorbutton="true">
				<header>
					<span class="widget-icon"><i class="fa fa-table"></i></span>
					<h2>呼叫队列</h2>
				</header>
				<div>
					<div class="jarviswidget-editbox"></div>
					<div class="widget-body" style="height:720px;overflow:hidden">
						<div class="row">
							<article class="col-xs-3 col-sm-3 col-md-3 col-lg-3">
								<div class="widget-body padding-2">
									<table id="dt_pop_call" class="table table-striped table-bordered table-hover" data-order='[[ 1, "asc" ]]'></table>
								</div>
							</article>
							<article class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
								<div class="smart-form" id="datainfo" style="height:256px;overflow-y: auto;overflow-x: hidden;">
								<header>当前呼叫信息</header>
									<input type="hidden" id="currentDataUuid">
									<input type="hidden" id="call_session_uuid">
									<fieldset>
										<div class="row">
						     				<section class="col col-6">
							        		    <label class="label col col-3">名称</label>
				                                <label class="input col col-9">
								        		    <input readonly="readonly" value="" />
				                                </label>
						              		</section>
						              		<section class="col col-6">
							        		    <label class="label col col-3">号码</label>
				                                <label class="input col col-9">
								        		    <input readonly="readonly" value="" />
				                                </label>
						              		</section>
					              		</div>
					     				<div class="row last">
						     				<section class="col col-6">
							        		    <label class="label col col-3">地址</label>
				                                <label class="input col col-9">
								        		    <input readonly="readonly" value="" />
				                                </label>
						              		</section>
						              		<section class="col col-6">
							        		    <label class="label col col-3">来源</label>
				                                <label class="input col col-9">
								        		    <input readonly="readonly" value="" />
				                                </label>
						              		</section>
					              		</div>
				              		</fieldset>
			              		</div>
			              		<form class="smart-form" id="callresult" method="post" style="height:256px;overflow-y: auto;overflow-x: hidden;" action="${springMacroRequestContext.contextPath}/calllog/update">
			              			<header>填写呼叫结果</header>
			              			<input type="hidden" name="uid" id="uid" value="${uid!''}">
			              			<fieldset>
			              				<div class="row">
											<@showLabel maps 2 />
										</div>
				              		</fieldset>
			              		</form>
								<div class="widget-body-toolbar" id="callouttoolbar">
									<div class="smart-form">
										<div style="text-align: right;">
							                <div class="btn-group dropup" id="movetopool"> 
							                	<a class="btn btn-lg btn-primary dropdown-toggle" data-toggle="dropdown" href="javascript:void(0);" aria-expanded="false">移动到池并呼叫下一条
							                		<span class="caret"></span>
							                	</a>
								                <ul class="dropdown-menu">
								                	<#list pools as p>
									                <li><a href="javascript:void(0);" data-pool="${p.uid}">${p.containerName}</a></li>
									                </#list>
								                </ul>
							                </div>
							                <label id="saveascustomer" class="btn btn-lg btn-primary">保存为客户并呼叫下一条</label>
							                <label id="callnext" class="btn btn-lg btn-primary">呼叫下一条</label>
										</div>
									</div>
								</div>
							</article>
							<article class="col-xs-3 col-sm-3 col-md-3 col-lg-3">
								<div class="widget-body padding-2">
									<table id="dt_pop_call_over" class="table table-striped table-bordered table-hover" data-order='[[ 1, "asc" ]]'></table>
								</div>
							</article>
						</div>
					</div>
				</div>
			</div>
		</article>
		<div id="div-callout"></div>
	</div>
</section>

<script type="text/javascript">

//当前呼叫电话号码
var currentCallPhone = "";

$(document).ready(function(){
	
	var dataCallTable = $('#dt_pop_call').DataTable({
		"dom" : "<'dt-toolbar'<'col-sm-12 col-xs-12'i>>" + "t"
				+ "<'dt-toolbar'<'col-xs-12 col-sm-12 text-right'p>>",
		"autoWidth" : false,
		"ordering" : true,
		"serverSide" : true,
		"processing" : true,
		"searching" : false,
		"pageLength" : 15,
		"lengthMenu" : [ 10, 15, 20, 25, 30 ],
		"language" : {
			"url" : getContext() + "public/dataTables.cn.txt"
		},
		"ajax" : {
			"url" : getContext() + "usercall/getCallList",
			"type" : "POST",
			"data" : function(param) {
				param.itemName = $("#itemName").val();
				param.itemPhone = $("#itemPhone").val();
			}
		},
		"infoCallback":function(settings, start, end, max, total, pre){
	    	return "待呼叫列表：显示第 " + start + " 至 " + end + " 项，共 " + total + " 项";
	    },
		"paging" : true,
		"pagingType" : "bootstrap",
		"lengthChange" : true,
		"order" : [ [ "0", "desc" ] ],
		"columns" : [
				{ "title" : "号码","data" : "itemPhone","defaultContent":""},
				{
					"title" : "操作",
					"data" : "null",
					"render" : function(data, type, full) {
						if(full.itemPhone == undefined)
							return "<span style='visibility:hidden'>占位<span>";
						return  "<a id=c" + full.uid + " onclick='callNextPhone(\"" + full.uid + "\");'>呼叫</a>";
					}
				} ],
		"initComplete" : function(){
			$("#dt_pop_call").closest("article").next().height($("#dt_pop_call").closest("article").height()).show();
		}
	});
	
	var dataCallOverTable = $('#dt_pop_call_over').DataTable({
		"dom" : "<'dt-toolbar'<'col-sm-12 col-xs-12'i>>" + "t"
		+ "<'dt-toolbar'<'col-xs-12 col-sm-12 text-right'p>>",
		"autoWidth" : false,
		"ordering" : true,
		"serverSide" : true,
		"processing" : true,
		"searching" : false,
		"pageLength" : 15,
		"lengthMenu" : [ 10, 15, 20, 25, 30 ],
		"language" : {
			"url" : getContext() + "public/dataTables.cn.txt"
		},
		"ajax" : {
			"url" : getContext() + "usercall/getCallOverList",
			"type" : "POST",
			"data" : function(param) {
				param.itemName = $("#itemName").val();
				param.itemPhone = $("#itemPhone").val();
			}
		},
		"infoCallback":function(settings, start, end, max, total, pre){
	    	return "已呼叫列表：显示第 " + start + " 至 " + end + " 项，共 " + total + " 项";
	    },
		"paging" : true,
		"pagingType" : "bootstrap",
		"lengthChange" : true,
		"order" : [ [ "0", "desc" ] ],
		"columns" : [
				{ "title" : "号码","data" : "itemPhone","defaultContent":""},
				{
					"title" : "操作",
					"data" : "null",
					"render" : function(data, type, full) {
						if(full.itemPhone == undefined)
							return "<span style='visibility:hidden'>占位<span>";
						return  "<a id=c" + full.uid + " onclick='callNextPhone(\"" + full.uid + "\");'>呼叫</a>";
					}
				} ],
	});
	
	$("#callnext").click(function(){
// 		$("#callresult").submit();
// 		$('#dt_pop_call a').eq(0).closest("tr").hide("slow");
		dataCallTable.ajax.reload(function(){
			dataCallOverTable.ajax.reload(null,false);;
			var nowDom = $('#dt_pop_call a').eq(0);
			nowDom.click();
			nowDom.closest("tr").css("background-color","rgb(110, 229, 207)");
			currentCallPhone = nowDom.parent().prev().text();
		});
	});
	
	$("#saveascustomer").click(function(){
		if(currentCallPhone=="")
			return;
		$.post(getContext()+'cstm/getTags',{phone:currentCallPhone},function(data){
			var modalDom = $(data);
			modalDom.find("#msubmit").text("保存");
			modalDom.find("#msubmit").next().text("取消");
			$("#div-callout").append(modalDom);
		});
	});

	$("#dt_pop_call").on("click","a",function(){
		getDataInfo(this.id.substring(1));
// 		$('#dt_pop_call a').closest("tr").css("background-color","rgb(110, 229, 207)");
// 		$(this).closest(tr).css("background-color","rgb(110, 229, 207)")
	});

	$("#movetopool").on("click","li a",function(){
		if($("#currentDataUuid").val()=="")
			return;
		$.post(getContext() + "userdata/toPool",{dataUuid:currentDataUuid,poolUuid:$(this).attr("data-pool")},function(data){
			if(data.success){
				$("#callnext").click();
			}else{
				$.smallBox({
					title : "操作失败",
					content : "<i class='fa fa-clock-o'></i> <i>操作失败，请稍后重试...</i>",
					color : "#C46A69",
					iconSmall : "fa fa-times fa-2x fadeInRight animated",
					timeout : 2000
				});
			}
		},"json");
	});
	
});


//特殊方法。。。
var cstmTabel = {
		ajax:{
			reload:function(){
				$("#callnext").click();
			}
		}
	};

$("#dt_pop_call").closest("article").next().hide();

function getDataInfo(dataId){
	$.post("${springMacroRequestContext.contextPath}/userdata/getDataInfo",{uuid:dataId},function(data) {
		var textDom = $("#datainfo fieldset").find("input");
		$("#currentDataUuid").val(data.uid);
		textDom.eq(0).val(data.itemName);
		textDom.eq(1).val(data.itemPhone);
		textDom.eq(2).val(data.itemAddress);
		textDom.eq(3).val(data.dataSource);
		var dom = $("<div class='row'></div>");
		var domStr = "";
		var j = 0;
		for(var i in data.itemJson) {
			domStr = domStr + '<section class="col col-6"><label class="label col col-3">' + i + '</label><label class="input col col-9"><input readonly="readonly" value="' + data.itemJson[i] +'" /></label></section>'
       		if ((++j) % 2 == 0)
       			domStr = domStr + '</div><div class="row">'
		}
		dom.append($(domStr));
		$("#datainfo .last").nextAll().remove()
		$("#datainfo fieldset").append(dom);
	},"json");
}

function callNextPhone(uuid) {
// 	if(currentCallStat==1)
// 		return;
	$.post(getContext()+'userdata/call', {taskUuid:uuid}, function(data) {
		if(data.success){
			$.smallBox({
				title : "操作成功",
				content : "<i class='fa fa-clock-o'></i> <i>正在呼叫下一条...</i>",
				color : "#659265",
				iconSmall : "fa fa-check fa-2x fadeInRight animated",
				timeout : 2000
			});
			$("#call_session_uuid").val(data["call_session_uuid"]);
			$("#loadStatus tr:eq(3) td:last").html($("#" + uuid).closest("tr").children("td:eq(2)").text());
			for(var k in data.entry){
				$("#" + k + "_a").val(data.entry[k]);
			}
			$("#uid").val(data.entry.uid);
			$("#uuid_a").val(data.entry.uid);
// 			currentCallStat = 1;
// 			$('#dataTable').DataTable().ajax.reload(null,false);;
		}else{
			$.smallBox({
				title : "操作失败",
				content : "<i class='fa fa-clock-o'></i> <i>操作失败...</i>",
				color : "#C46A69",
				iconSmall : "fa fa-times fa-2x fadeInRight animated",
				timeout : 2000
			});
		}
// 		$('#dataTable').DataTable().ajax.reload(null,false);;
	}, "json");
}

</script>


<!-- 生成表单 -->
<#macro showLabel maps count>
	<#assign index = 0>
	<#list maps?keys as key>
	
		<#if (maps[key].allowEmpty) == "0">
			<#assign requireds = "required">
		<#else>
			<#assign requireds = "">
		</#if>
		
<!-- 		是否是隐藏输入框 -->
		<#if (maps[key].isHidden)?? && (maps[key].isHidden) == "1" && (maps[key].isReadonly)?? && (maps[key].isReadonly) == "1">
			<#assign disabled = "disabled='disabled'">
		<#else>
			<#assign disabled = "">
		</#if>

<!-- 		是否是只读输入框 -->		
		<#if (maps[key].isReadonly)?? && (maps[key].isReadonly) == "1">
			<#assign readonly = "readonly='readonly'">
		<#else>
			<#assign readonly = "">
		</#if>
		
		<#if (maps[key].isHidden)?? && (maps[key].isHidden) == "1">
			<input ${disabled} type="hidden" name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" value="" ${readonly}/>
		<#else>
			<#assign index = index + 1 />
			<section class="col col-6">
			<#if (maps[key].columnType) == "DATE" >
				<label class="label col col-3">${maps[key].columnName}</label>
				<div class="input col col-9">
					<label class="input">
						<input ${readonly} name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" value="" class="date"/>
					</label>
				</div>
			<#elseif (maps[key].columnType) == "ENUM" >
				<#assign enumStr = (maps[key].characterProperty)?string>
				<label class="label col col-3">${maps[key].columnName}</label>
				<div class="col col-lg-9">
					<select name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" class="form-control">
						<#list enumStr?replace("，",",")?split(",") as a>
							<option value="${a!''}">${a!''}</option>
						</#list>
					</select>
				</div>
			<#else>
				<label class="label col col-3">${maps[key].columnName}</label>
				<div class="col col-lg-9">
					<label class="input">
						<input ${readonly} name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a"  value="${serialized!''}" class="${requireds}">
					</label>
				</div>
			</#if>
			</section>
		</#if>
		
		<#if !((maps[key].isHidden)?? && (maps[key].isHidden) == "1") && index%count == 0>
			</div>
			<div class="row">
		</#if>
	</#list>
</#macro>
