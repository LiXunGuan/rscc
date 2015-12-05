
<#include "../index/importcss.ftl">
<#include "../index/importsjs.ftl">

<script type="text/javascript">
function getContext(){
	return "${springMacroRequestContext.contextPath}/";
}

/*隐藏号码*/

var hiddenPhoneFlag = {globalHide : false, hasPermission : false};

<#if hiddenPhone?? && "true" == hiddenPhone>
	hiddenPhoneFlag.globalHide = true;
<#else>
	hiddenPhoneFlag.globalHide = false;
</#if>

<#if hasPhonePermission?? && true == hasPhonePermission>
	hiddenPhoneFlag.hasPermission = true;
<#else>
	hiddenPhoneFlag.hasPermission = false;
</#if>


/*隐藏号码*/
hiddenPhone = function hiddenPhone(phoneNumber){
	if(hiddenPhoneFlag.globalHide && !hiddenPhoneFlag.hasPermission){
		return phoneNumber.replace(/(\d{3})\d*/, '$1********');
	}else{
		return phoneNumber;
	}
}



</script>

<!-- 客户弹屏主界面 -->
<style>
div.jarviswidget>header {
	box-shadow: inset 0 -2px 0 rgba(0,0,0,.05);
}

div.jarviswidget header .widget-icon i {
	line-height: 32px;
}

.select2-container .select2-choice .select2-arrow b {
	line-height: 32px;
}

.row .label.col.col-1 {
	text-align: right;
	text-justify: inter-ideograph;
}
#dt_call_queue tbody tr.activerow {
	background-color: #6EE5CF;
}
div div.dataTables_info
{
	font-style:normal;
}
#datainfo .smart-form
{
	 height:200px;
}
#calllog .smart-form
{
	 height:256px;
}
#loglist .smart-form
{
	 min-height:250px;
}
.smart-form
{
	 overflow-y: auto;
	 overflow-x: hidden;
}
#dt_call_queue>tbody>tr>td>span:last-of-type:not(.readyToCall)
{
	color:red;
}

</style>

<body style="height:100%; margin: 0px 80px; padding-top:10px;">
	<div class="row">
		<div id="formodal"></div>
		<div class="jarviswidget" id="wid-id-0" role="widget">
		<header role="heading">
		<span class="widget-icon"> <i class="fa fa-phone"></i></span>
			<h2>呼出</h2>
			<span class="jarviswidget-loader"><i class="fa fa-refresh fa-spin"></i>
			</span>
		</header>
		<div role="content">
		<article class="col-xs-3 col-sm-3 col-md-3 col-lg-3" id="callqueue">
			<div class="widget-body">
				<table id="dt_call_queue" class="table table-striped table-bordered table-hover" data-order='[[ 1, "asc" ]]'></table>
			</div>
			<input type="hidden" id="agent_name" name="agent_name" value="${CURRENTUSER.loginName}">
		</article>
		<article class="col-xs-9 col-sm-9 col-md-9 col-lg-9">
			<div class="row" id="datainfo">
				<div class="smart-form">
					<header>当前呼叫信息</header>
					<input type="hidden" id="currentDataUuid">
					<input type="hidden" id="call_session_uuid">
					<fieldset>
						<div class="row last">
		     				<section class="col col-4">
			        		    <label class="label col col-3">名称</label>
	                               <label class="input col col-9">
				        		    <input readonly="readonly" value="" />
	                               </label>
		              		</section>
		              		<section class="col col-4">
			        		    <label class="label col col-3">号码</label>
	                               <label class="input col col-9">
				        		    <input readonly="readonly" id="currentPhone" value="" />
	                               </label>
		              		</section>
		     				<section class="col col-4">
			        		    <label class="label col col-3">地址</label>
	                               <label class="input col col-9">
				        		    <input readonly="readonly" value="" />
	                               </label>
		              		</section>
	              		</div>
	           		</fieldset>
	       		</div>
			</div>
			<div class="row" id="calllogsave">
				<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
					<div class="row" id="calllog">
						<form class="smart-form" id="loginfo" action="${springMacroRequestContext.contextPath}/calllog/update" method="post">
							<header>当前呼叫小计</header>
							<input type="hidden" name="uid" id="uid" value="${uid!''}">
		           			<fieldset>
		           				<div class="row">
									<@showLabel maps 2 "in_"/>
								</div>
								<section>
									<label class="label col col-2">内容小计</label>
									<label class="textarea col col-10" style="padding-left:10px;"> 										
										<textarea rows="3" class="custom-scroll" name="text_log" id="text_log_a"></textarea>
									</label>
								</section>
								<input type="hidden" id='currentPhoneNumber' />
		              		</fieldset>
			       		</form>
		       		</div>
	       		</div>
	       		<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
					<div class="smart-form" id="loginfo">
						<header>当前呼叫操作</header>
	           			<fieldset style="text-align:center">
		           			<section>
								<div class="row">
		           					<span>是否移动到池？</span>
									<select name="pool" id="pool">
									<option value="">否</option>
									<#list pools as s>
										<option value=${s.uid}>${s.containerName}</option>
									</#list>
									</select>
								</div>
							</section>
							<section>
			           			<div class="row" id="callnext">
									<div class="btn btn-primary btn-lg" id="saveCall">
										保存并呼叫下一条
									</div>
								</div>
							</section>
							<div style="margin-bottom:5px;border-bottom: 1px dashed rgba(0,0,0,.2);"></div>
							<section>
			           			<div class="row">
									<div class="btn btn-success btn-lg" id="saveascustomer">
										保存为客户
									</div>
								</div>
							</section>
	              		</fieldset>
		       		</div>
				</div>
			</div>
			<div class="row" id="loglist">
	       		<div class="smart-form">
	       			<header>沟通记录</header>
					<div class="widget-body padding-10">
						<table id="dt_call_log" class="table table-striped table-bordered table-hover" data-order='[[ 1, "asc" ]]'></table>
					</div>
				</div>
			</div>
		</article>

			
		</div>
	</div>
	</div>
</body>
<script type="text/javascript">

	var currentDataUuid = "${lastcall}";
	var currentPhoneNumber;
	var lastCall = ${lastcall};
	
	$(function(){
		//初始化呼叫列表
		dataCallTable = $('#dt_call_queue').DataTable({
			"dom" : "<'dt-toolbar'<'col-sm-12 col-xs-12'i>>",
			"autoWidth" : false,
			"ordering" : true,
			"serverSide" : true,
			"processing" : true,
			"searching" : false,
			"pageLength" : Math.floor(window.innerHeight / 37) - 4,
// 			"lengthMenu" : [ 15, 25, 50, 75, 100 ],
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
		    	return "呼叫队列：显示第 " + start + " 至 " + end + " 项，共 " + total + " 项";
		    },
			"paging" : true,
			"pagingType" : "bootstrap",
			"lengthChange" : true,
			"order" : [ [ "0", "desc" ] ],
			"columns" : [
					{
						"title" : "号码",
						"data" : "null",
						"render" : function(data, type, full) {
							if(full.itemPhone == undefined)
								return "";
							if(full.history == undefined)
								return  "<span>" + window.parent.hiddenPhone(full.itemPhone)+ "</span><span class='readyToCall'>(待呼叫)</span>";
							if(full.moveTo != undefined && full.moveTo != ""){
								var dom = $("#pool option[value='" + full.moveTo + "']");
								return  "<span>" + window.parent.hiddenPhone(full.itemPhone) + "</span><span>(" + (dom.length>0?"已移至" + dom.text():"已存为客户") + ")</span>";
							}
							return  "<span>" + window.parent.hiddenPhone(full.itemPhone) + "</span><span>(已呼叫)</span>";
						}
					},
					{
						"title" : "操作",
						"data" : "null",
						"render" : function(data, type, full) {
							if(full.itemPhone == undefined)
								return "<span style='visibility:hidden'>占位<span>";
							if(full.history != undefined)
								return "<a id=" + full.uid + " onclick='callNextPhone(\"" + full.uid + "\",1);'>呼叫</a>";
							return  "<a id=" + full.uid + " onclick='callNextPhone(\"" + full.uid + "\");'>呼叫</a>";
						}
					} ],
			"initComplete" : function(){
				$("#loglist .smart-form").height($("#dt_call_queue").find("tr").last().offset().top + 37 - $("#loglist").offset().top);
				$("#dt_call_queue tbody tr").eq(lastCall).addClass("activerow");
				popLog.ajax.reload(null,false);;
			},
			"drawCallback" : function(){
// 				popLog.ajax.reload(null,false);;
// 				alert(1);
			}
		});
		
		
		//呼叫记录表格
		popLog = $('#dt_call_log').DataTable({
			"dom" : "t"
				+ "<'dt-toolbar-footer'<'col-sm-6 col-xs-12'i><'col-xs-12 col-sm-6 text-right'lp>>",
			"autoWidth" : true,
			"ordering" : true,
			"serverSide" : true,
			"processing" : true,
			"searching" : false,
			"pageLength" : 5,
					"lengthMenu" : [ 5, 10, 15, 20],
					"language" : {
						"url" : getContext()+ "public/dataTables.cn.txt"
					},
					"ajax" : {
						"url" : getContext() + "calllog/getCallLog",
				"type" : "POST",
				"data" : function(param) {
					param.call_phone = $("#dt_call_queue tbody tr.activerow td:first span:first").text();
				}
			},
			"paging" : true,
			"pagingType" : "bootstrap",
			"lengthChange" : true,
			"order" : [ [ "0", "desc" ] ],
			"columns" : [
				<#if maps??>
					<#list maps?keys as key>
						<#if maps[key].allowShow == '1'>
							<#if key == "call_phone">
								{"title" : "${maps[key].columnName}",  "data" : "${key}","defaultContent":"","render":function(a,d,f){
									return window.parent.hiddenPhone(f.call_phone);
								}},
								
							<#else>
								{"title" : "${maps[key].columnName}",  "data" : "${key}","defaultContent":""},
							</#if>
						</#if>
					</#list>
				</#if>
			],
		});
		
		//初始化池选择
		$("#pool").select2({
    		width:"40%",
    	});
		
		//动态修改池选择文本
		$("#pool").change(function(){
			if($("#pool").val() == ""){
				$("#pool").parent().children().first().text("是否移动到池？");
				$("#pool").children().first().text("否");
				$("#pool").prev().find(".select2-chosen").text("否");
			}
			else{
				$("#pool").children().first().text("取消移动");
				$("#pool").parent().children().first().text("移动到");
			}
		});
		
		//获取一个呼叫记录，初始化时使用，这里并没有什么用
// 		$.post(getContext()+'calllog/get',{uuid:$("#dt_call_queue tbody tr.activerow td:first span:first").text()},function(data){

// 			for(var k in data.entry){
				
// 				$("#" + k + "_a").val(data.entry[k]);
// 			}
			
// 			$("#uid_b").val(data.entry.uid);
// 			$("#uuid_a").val(data.entry.uid);
			
// 		},"json");
		jQuery.validator.addMethod("isOver", function(value) {
            var htmlobj = $.ajax({url:"${springMacroRequestContext.contextPath}/usercall/isOver",async:false,type:"post",data:{dataUuid:$("#data_id_a").val()}});
            console.log(htmlobj.responseText);
            return htmlobj.responseText === "true";
        }, "请等待呼叫结束");
		
		$("#loginfo").validate({
			rules: {
				"text_log" : {
					"isOver" : true
					} 
				} 
		});
		
		/* 提交按钮单击事件  */
		$('#saveCall').click(function(){
			currentCallPhone = $("#currentPhone").val();
			if(currentCallPhone == "")
				$(".readyToCall:first").closest("tr").find("a").click();
			else if($('#loginfo').valid()){
		        $('#loginfo').submit();
		    }
		});
		
		$('#loginfo').ajaxForm({
            dataType:"json",
            data : { 
//             	'customer_id': function(){
//             		return $("#customer_id_a").val();
//             	}
            },
            success: function(data) {
               if(data.success){
                   	$.smallBox({
                       title : "保存记录成功",
                       content : "<i class='fa fa-clock-o'></i> <i>保存记录成功</i>",
                       color : "#659265",
                       iconSmall : "fa fa-check fa-2x fadeInRight animated",
                       timeout : 2000
                   	});
                   	//保存到池
                   	//保存为客户了
                   	if(isSaveAsCustomer){
                   		isSaveAsCustomer = false;
                   		$.post(getContext() + "usercall/toPool",{dataUuid:$("#data_id_a").val(),poolUuid:"customer"},function(data){
   	            			if(data.success){
   	//             				$("#callnext").click();
   	            			}else{
   	            				$.smallBox({
   	            					title : "保存失败",
   	            					content : "<i class='fa fa-clock-o'></i> <i>操作失败，请稍后重试...</i>",
   	            					color : "#C46A69",
   	            					iconSmall : "fa fa-times fa-2x fadeInRight animated",
   	            					timeout : 2000
   	            				});
   	            			}
   	            		},"json");
                   	}else {
//                    	 if($("#pool").val()!="")
   	            		$.post(getContext() + "usercall/toPool",{dataUuid:$("#data_id_a").val(),poolUuid:$("#pool").val()},function(data){
   	            			if(data.success == undefined || data.success == true){
   	//             				$("#callnext").click();
   	            			}else{
   	            				$.smallBox({
   	            					title : "移动到池失败",
   	            					content : "<i class='fa fa-clock-o'></i> <i>操作失败，请稍后重试...</i>",
   	            					color : "#C46A69",
   	            					iconSmall : "fa fa-times fa-2x fadeInRight animated",
   	            					timeout : 2000
   	            				});
   	            			}
   	            		},"json");
                   	};
                 	//呼叫下一个的点击事件
//         			var nowDom = $("#dt_call_queue tbody tr.activerow td:last a").attr("id");
        			dataCallTable.ajax.reload(function(){
        				$(".readyToCall:first").closest("tr").addClass("activerow");
        				$(".readyToCall:first").closest("tr").find("a").click();
						popLog.ajax.reload(null,false);;
        			});
               }
            },
            submitHandler : function(form) {
               $(form).ajaxSubmit({
                   success : function() {
                       $("#loginfo").addClass('submited');
                   }
               });
            },
            error: function(XMLHttpRequest,textStatus , errorThrown){
               if(textStatus=='error'){
                   $.smallBox({
                       title : "操作失败",
                       content : "<i class='fa fa-clock-o'></i> <i>操作失败</i>",
                       color : "#C46A69",
                       iconSmall : "fa fa-times fa-2x fadeInRight animated",
                       timeout : 2000
                   });
                   popLog.ajax.reload(null,false);;
               }
            }
        });
		 
// 		$(".date:not([readonly='readonly'])").datepicker({
// 			monthNames: ['一月','二月','三月','四月','五月','六月', '七月','八月','九月','十月','十一月','十二月'],
// 			nextText: '>', 
// 			prevText: '<',
// 				firstDay: 1, 
// 				dateFormat : 'yy-mm-dd'
// 		});
		$(".datetime:not([readonly='readonly'])").datetimepicker({
            format: "yyyy-mm-dd hh:ii",
            weekStart: 1,
            todayBtn:  1,
    		autoclose: 1,
    		todayHighlight: 1,
    		startView: 2,
    		forceParse: 0,
            showMeridian: 1
    	});
			 
		$("#saveascustomer").click(function(){
			currentCallPhone = $("#currentPhoneNumber").val();
			if(currentCallPhone == "")
				return;
			$.post(getContext()+'cstm/getTags',{phone:currentCallPhone},function(data){
				var modalDom = $(data);
				modalDom.find("#msubmit").text("保存");
				modalDom.find("#msubmit").next().text("取消");
				$("#formodal").append(modalDom);
			});
		});
		
// 		currentCallPhone = $("#currentPhone").val();
		if($("#currentPhone").val() == "")
			$("#saveCall").text("开始呼叫");
		else
			$("#saveCall").text("保存并呼叫下一条");
		
	})		
	
	var isSaveAsCustomer = false;
	
	//特殊方法。。。
	var cstmTabel = {
		ajax:{
			reload:function(){
				isSaveAsCustomer = Boolean(saveResult);
// 				$("#callnext").click();
			}
		}
	};

	//点击呼叫按钮时
	function callNextPhone(uuid,type) {
		var url;
		if(type!=1)
			url = getContext() + 'usercall/call';
		else
			url = getContext() + 'usercall/callHistory'
		$.post(url, {taskUuid:uuid}, function(data) {
			if(data.success){
				$.smallBox({
					title : "操作成功",
					content : "<i class='fa fa-clock-o'></i> <i>正在呼叫下一条...</i>",
					color : "#659265",
					iconSmall : "fa fa-check fa-2x fadeInRight animated",
					timeout : 2000
				});
				$("#pool").val("").trigger("change");
				$("#loginfo")[0].reset();//清空表单
				getDataInfo(uuid);
// 				popLog.ajax.reload(null,false);;
				$("#call_session_uuid").val(data["call_session_uuid"]);
				for(var k in data.entry){
					$("#" + k + "_a").val(data.entry[k]);
				}
				$("#uid").val(data.entry.uid);
				$("#uuid_a").val(data.entry.uid);
				currentDataUuid = uuid;
				currentPhoneNumber = $("#call_phone_a").val();
				$('#dt_call_queue').DataTable().ajax.reload(function() {
					$("#dt_call_queue tbody tr").removeClass("activerow");
					$("#" + uuid).closest("tr").addClass("activerow");
					$("#saveCall").text("保存并呼叫下一条");
					popLog.ajax.reload(null,false);;
				});
			}else{
				$.smallBox({
					title : "操作失败",
					content : "<i class='fa fa-clock-o'></i> <i>操作失败...</i>",
					color : "#C46A69",
					iconSmall : "fa fa-times fa-2x fadeInRight animated",
					timeout : 2000
				});
			}
		}, "json");
	}
	
	function getDataInfo(dataId){
		$.post("${springMacroRequestContext.contextPath}/userdata/getDataInfo",{uuid:dataId},function(data) {
			var textDom = $("#datainfo fieldset").find("input");
			$("#currentDataUuid").val(data.uid);
			textDom.eq(0).val(data.itemName);
			$("#currentPhoneNumber").val(data.itemPhone);
			textDom.eq(1).val(window.parent.hiddenPhone(data.itemPhone));
			textDom.eq(2).val(data.itemAddress);
// 			textDom.eq(3).val(data.dataSource);
			var dom = $("<div class='row'></div>");
			var domStr = "";
			var j = 0;
			for(var i in data.itemJson) {
				domStr = domStr + '<section class="col col-4"><label class="label col col-3">' + i + '</label><label class="input col col-9"><input readonly="readonly" value="' + data.itemJson[i] +'" /></label></section>'
	       		if ((++j) % 3 == 0)
	       			domStr = domStr + '</div><div class="row">'
			}
			dom.append($(domStr));
			$("#datainfo .last").nextAll().remove()
			$("#datainfo fieldset").append(dom);
		},"json");
	}
	
	/* 去除号码前面的0 */
	function deleteZero(phoneNumber){
		
// 		if(phoneNumber != "" /* && phoneNumber.startsWith("0") */){
		if(phoneNumber != "" ){
			if(phoneNumber.substring(0,1)=="0"){
				
				return deleteZero(phoneNumber.substring(1));
			}
		}
		return phoneNumber;
	}
	

	
</script>

<#macro showLabel maps count inoutflag>
	<#assign index = 0>
	<#list maps?keys as key>
	
		<#if (maps[key].allowEmpty) == "0">
			<#assign requireds = "required">
		<#else>
			<#assign requireds = "">
		</#if>
		
		<#if (maps[key].isHidden)?? && (maps[key].isHidden) == "1" && (maps[key].isReadonly)?? && (maps[key].isReadonly) == "1">
			<#assign disabled = "disabled='disabled'">
		<#else>
			<#assign disabled = "">
		</#if>

		<#if (maps[key].isReadonly)?? && (maps[key].isReadonly) == "1">
			<#assign readonly = "readonly='readonly'">
		<#else>
			<#assign readonly = "">
		</#if>
		
		<#if (maps[key].isHidden)?? && (maps[key].isHidden) == "1">
			<input ${disabled} type="hidden" name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" value="" ${readonly}/>
		<#elseif (maps[key].columnNameDB) == "text_log" || (maps[key].columnNameDB)?starts_with(inoutflag)>
		<#else>
			<#assign index = index + 1 />
			<section class="col col-6">
			<#if (maps[key].columnType) == "DATE" >
				<label class="label col col-4">${maps[key].columnName}</label>
				<div class="input col col-8">
					<label class="input">
						<input ${readonly} name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" value="" class="datetime"/>
					</label>
				</div>
			<#elseif (maps[key].columnType) == "ENUM" >
				<#assign enumStr = (maps[key].characterProperty)?string>
				<label class="label col col-4">${maps[key].columnName}</label>
				<div class="col col-lg-8">
					<select name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" class="form-control">
						<#list enumStr?replace("，",",")?split(",") as a>
							<option value="${a!''}">${a!''}</option>
						</#list>
					</select>
				</div>
			<#else>
				<label class="label col col-4">${maps[key].columnName}</label>
				<div class="col col-lg-8">
					<label class="input">
						<input ${readonly} name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a"  value="${serialized!''}" class="${requireds}">
					</label>
				</div>
			</#if>
			</section>
		</#if>
		
		<#if !((maps[key].columnNameDB) == "text_log"  || (maps[key].columnNameDB)?starts_with(inoutflag)) && !((maps[key].isHidden)?? && (maps[key].isHidden) == "1") && index%count == 0>
			</div>
			<div class="row">
		</#if>
	</#list>
</#macro>
