
<style type="text/css">
	
	.smart-form legend {
    	padding-top: 0px;
	}
	
	/* 设置电话详细信息下的所有显示信息为不可编辑 背景透明 */
	#newphoneInfo input{
		border: none;
		background-color: transparent;
	}
	
	/* 字体间距 */
	#first .label.col {
		width: 85px;
		text-align: right;
		padding-left: 12px;
		padding-right: 12px;
	}
	
	.input input.readOnly {
		background-color: transparent;
		border: none;
	}
	
	div .select select.readOnly {
		background-color: transparent;
		border: none;
	}
	
 	.row .label.col.col-1 { 
 		text-align: right; 
 		text-justify: inter-ideograph; 
 	}
 	
 	.smart-form section{
 		margin-bottom: 5px;
 	}
 	.smart-form fieldset{
 		padding-top: 5px;
 	}
	
</style>

<div class="row">
	<div id="newpop">
		<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<div class="jarviswidget" id="wid-id-1-tabs-new" data-widget-editbutton="false" data-widget-custombutton="false">
				<header>
					<span class="widget-icon" style=""> <i
						class="fa fa-edit"></i>
					</span>
					<h2>电话详细信息</h2>
				</header>
				<div>
					<div class="jarviswidget-editbox"></div>
					<div class="widget-body no-padding">
						<div class="widget-body-toolbar" style="border-bottom: none; background-color: transparent;">
							<div class="smart-form" id="newphoneInfo">
								<div class="row last">
									<section class="col col-4">
										<label class="label col col-3">客户号码</label>
										<label class="input col col-9">
										<input readonly="readonly" name="phoneNumber" id="phoneNumber" value="${(entry.phoneNumber)!''}"></label>
									</section>
									<section class="col col-4">
										<label class="label col col-3">去电时间</label>
										<label class="input col col-9">
										<input readonly="readonly" name="callTime" id="callTime"  value="${.now?datetime}"></label>
									</section>
									<section class="col col-4">
										<label class="label col col-3">号码归属</label>
										<label class="input col col-9">
										<input name="guishudi" id="guishudi"  value="上海-上海"></label>
									</section>
								</div>

								
							</div>
						</div>
					</div>
				</div>
			</div>
		</article>

		<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">

			<div class="jarviswidget" id="wid-id-1-tabs-customerinfo"
				data-widget-editbutton="false" data-widget-custombutton="false">
				<header>
					<span class="widget-icon" style="padding-top: 10;"> <i class="fa fa-edit"></i>
					</span>
					<h2>当前呼叫小计</h2>
				</header>
				<div class="widget-body">
					<div class="row" id="calllogsave">
						<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
							<div class="row" id="calllog">
								<form class="smart-form" id="loginfo" action="${springMacroRequestContext.contextPath}/calllog/update" method="post">
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
			           			<fieldset style="text-align:center">
				           			<section>
										<div class="row">
				           					<span>选择分类</span>
											<select name="result" id="result">
											<optgroup label="接通时" class='answered'>
											<#list intents as intent>
											<#if (entry.intentType)?? && intent.uid=entry.intentType>
												<option selected='selected' value="${intent.uid}">${intent.intentName}</option>
											<#else>												
												<option value="${intent.uid}">${intent.intentName}</option>
											</#if>
											</#list>
											<option value="global_share">共享池</option>
											<option value="blacklist">黑名单</option>
											</optgroup>
											<optgroup label="未接通时" class='noanswer'>
											<option value="noanswer">无人接听</option>
											<option value="abandon">号码无效</option>
											</optgroup>
											</select>
										</div>
									</section>
									<section>
					           			<div class="row" id="callnext">
											<div class="btn btn-primary btn-lg " onclick="editItem();">
												添加预约
											</div>
											<div class="btn btn-primary btn-lg " id="saveCall">
												保存
											</div>
										</div>
									</section>
			              		</fieldset>
				       		</div>
						</div>
					</div>
	       		</div>
       		</div>
   		</article>
	       		

		<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">

			<div class="jarviswidget" id="wid-id-1-tabs-customerinfo"
				data-widget-editbutton="false" data-widget-custombutton="false">
				<header>
					<span class="widget-icon" style="padding-top: 10;"> <i class="fa fa-edit"></i>
					</span>
					<h2>沟通记录</h2>
				</header>
				<div class="widget-body">
						<!--  显示客户业务tab展示  标签页  -->
						<div id="div2" style="padding: 0px; border: none; ">
							<!-- 标签页 -->
<!-- 							<ul id="myTab1" class="nav nav-tabs bordered"> -->
<!-- 								<#if showTabs??>  -->
<!-- 									<#list showTabs?keys as key>  -->
<!-- 										<#if key_index == 0> -->
<!-- 												<li class="active"><a href="#${key}" data-toggle="tab" onclick="getActive('${key}')"> ${showTabs[key]}</a></li>  -->
<!-- 											<#else> -->
<!-- 												<li><a href="#${key}" data-toggle="tab" onclick="getActive('${key}')"> ${showTabs[key]}</a></li>  -->
<!-- 											</#if> -->
<!-- 									</#list>  -->
<!-- 								</#if> -->
<!-- 							</ul> -->
							<!-- 标签页内容 -->
							<div id="myTabContentss" class="tab-content">
								<#if showTabs??> 
									<#list showTabs?keys as key>
										<#if key_index == 0>
											<div class="tab-pane active" id="${key}"></div>
										<#else>
											<div class="tab-pane fade" id="${key}"></div>
										</#if> 
									</#list> 
								</#if>
							</div>
							
							<div class="row hidden" id="loglist">
					       		<div class="smart-form">
									<div class="widget-body padding-10">
										<table id="dt_call_log" class="table table-striped table-bordered table-hover" data-order='[[ 1, "asc" ]]'></table>
									</div>
								</div>
							</div>
			
						</div>
					</div>
			</div>
		</article>
	</div>
</div>


<script type="text/javascript">
	
	var popLog = "";
	$(function(){
		
		//初始状态
		popOptionStat = $("#result .answered");
		<#if !type?? || type!='detail'>
		if(window.parent.currentCallStat.callNum === $("#newpop #phone_number_a").val()) {
			$("#result .noanswer").remove();
		} else {
			$("#result .answered").remove();
		}
		</#if>
		
		$("#result").select2({
    		width:"40%",
    	});
		
		getDataInfo();
		
		getActive("poplog");
		
		/* 设置电话信息仅显示 */
		$("#newphoneInfo input").attr("disabled","disabled");
        
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
					param.call_phone = $("#phoneNumber").val();
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
      
      	
      	var dataLog = '${log!""}';
      	if(dataLog != ''){
      		dataLog = JSON.parse(dataLog);
      		$("#call_session_uuid").val(dataLog["call_session_uuid"]);
			for(var k in dataLog){
				$("#" + k + "_a").val(dataLog[k]);
			}
			$("#uid").val(dataLog.uid);
			$("#uuid_a").val(dataLog.uid);
      	}
		$("#loginfo").validate({
			
		});
		
		$('#saveCall').click(function(){
			if($('#loginfo').valid()){
		        $('#loginfo').submit();
			}
		})
		
		$('#loginfo').ajaxForm({
            dataType:"json",
            data : { 
//             	'customer_id': function(){
//             		return $("#customer_id_a").val();
//             	}
            },
            success: function(data) {
               if(data.success){
                   	var callresult = {};
                   	if($("#call_time_a").val()) {
                   		callresult = {
              				action:$("#result").val(),
	               			phoneNumber:$("#phoneNumber").val(),
	               			callSessionUuid:$("#call_session_uuid").val(),
	               			callTime:$("#call_time_a").val(),
	               			callStat:$("#result optgroup").attr("label").indexOf("未接通")<0?1:0
       					}
                   	} else {
                   		callresult = {
               				action:$("#result").val(),
   	               			phoneNumber:$("#phoneNumber").val(),
   	               			callSessionUuid:$("#call_session_uuid").val(),
       					}
                   	}
                   			
               		$.post(getContext() + "newuserdata/saveData",
               				callresult,
             				function(data){
            			if(data.success){
                           	$.smallBox({
                               title : "保存记录成功",
                               content : "<i class='fa fa-clock-o'></i> <i>保存记录成功</i>",
                               color : "#659265",
                               iconSmall : "fa fa-check fa-2x fadeInRight animated",
                               timeout : 2000
                           	});
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
      
        
	});
	
	function editItem(uuid,operation){
        var url = getContext() + "schedulereminder/get";
		$.post(url, {uuid:uuid,operation:operation}, function(data){
			$("#forModel").append(data);
		});
	}
	
	
	/* 编辑 */
	$("#forEdit").click(function(){
		
		$("#first input").removeAttr("readonly").removeClass("readOnly");	
		$("#first select").removeAttr("disabled").removeClass("readOnly");
		
		$('#own_idss').select2({
	        allowClear : true,
	   	    width: '99%'
	    });
		
		$('#pool_id').select2({
		     allowClear : true,
		     width: '99%'
		});
		
	});

	/* 请求第一个tab的数据 issue */
	function getActive(str){
		/* popEventHandler中查看具体逻辑 */
		/* 沟通小计  */
		if(str == "poplog"){
			$("#poplog").empty();
			$("#poplog").append($("#loglist").removeClass("hidden"));
		
		/* 订单信息 */
		}else if(str == 'order'){
			$("#loglist").addClass("hidden");
			$.post(getContext()+"orderinfo",{level:"pop",cstmId:$("#uid").val()},function(data){
				$("#order").empty();
				$("#order").append(data);
			});
		}
	}
	
	function getDataInfo(){
		<#if entry??>
			var itemJson = ${entry.json};
			var dom = $("<div class='row'></div>");
			var domStr = "<div class='row'>";
			var j = 0;
			for(var i in itemJson) {
				domStr = domStr + '<section class="col col-4"><label class="label col col-3">' + i + '</label><label class="input col col-9"><input readonly="readonly" value="' + itemJson[i] +'" /></label></section>'
	       		if ((++j) % 3 == 0)
	       			domStr = domStr + '</div><div class="row">'
			}
			domStr = domStr + "</div>";
// 			dom.append($(domStr));
			$("#newphoneInfo .last").nextAll().remove()
			$("#newphoneInfo").append($(domStr));
		</#if>
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