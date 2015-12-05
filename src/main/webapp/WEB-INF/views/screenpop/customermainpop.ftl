
<#include "../index/importcss.ftl">
<#include "../index/importsjs.ftl">

<script type="text/javascript">
function getContext(){
	return "${springMacroRequestContext.contextPath}/";
}

</script>

<!-- 客户弹屏主界面 -->
<style>
<!--

.row .label.col.col-1 {
	text-align: right;
	text-justify: inter-ideograph;
}
-->
</style>

<div class="row">

<div id="addpop" style="margin: 10px 50px;">
<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">

	<div class="jarviswidget" id="wid-id-1-tabs" data-widget-editbutton="false" data-widget-custombutton="false">
		<header>
			<span class="widget-icon" style="  padding-top: 10; "> <i class="fa fa-edit"></i> </span>
			<h2>详情</h2>
		</header>
		<div>
			<div class="widget-body no-padding">
				
				<!-- 客户信息展示 -->
				<form id="checkout-form" class="smart-form" novalidate="novalidate">
					<fieldset style=" background: rgba(250, 250, 250, 1); ">
							<section class="col col-lg-12">
							
								<!-- 隐藏值，customerinfo中取值 -->
								<input type="hidden" value="${call_session_uuid!''}" name="call_session_uuid" id="call_session_uuid">
								<#if access_number??>
									<input type="hidden" name="access_number" id="access_number" value="${access_number!''}">
								<#else>
									<input type="hidden" name="access_number" id="access_number" value="notexist">
								</#if>
								<input type="hidden" name="caller_id" id="caller_id" value="${caller_id!''}">
								<input type="hidden" name="timestamp" id="timestamp" value="${timestamp!''}">
								<input type="hidden" name="ivr_desc" id="ivr_desc" value="${ivr_desc!''}">
								
									<!-- 客户信息展示 -->
								<div id="div1">
									<div id="div-1"></div>
									
									
								</div>
							</section>
					</fieldset>
				</form>
				
				<!--  显示客户业务tab展示  标签页  -->
				<div id="div2" style="padding: 0px;border: none; margin-top: 5px;">
						<!-- 标签页 -->
						<ul id="myTab1" class="nav nav-tabs bordered">
						<#if showTabs??>
							<#list showTabs?keys as key>
								<#if key_index == 0>
									<li class="active">
										<a href="#${key}" data-toggle="tab" onclick="getActive('${key}')" > ${showTabs[key]}</a>
									</li>
								<#else>
									<li>
										<a href="#${key}" data-toggle="tab" onclick="getActive('${key}')" > ${showTabs[key]}</a>
									</li> 
								</#if>
							</#list>
						</#if>
						</ul> 
						<!-- 标签页内容 -->
						<div id="myTabContentss" class="tab-content padding-5">
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
					</div>
			</div>
		</div>
	</div>
</article>
</div>
</div>
<script type="text/javascript">


	/* 请求第一个tab的数据 issue */
	function getActive(str){
		/* popEventHandler中查看具体逻辑 */
		/* 沟通小计  */
		if(str == "poplog"){
			
			/* 返回页面 mod-cstm-pop-log  */
			$.post(getContext()+"cstm/poplog",function(data){
				$("#poplog").empty();
				$("#poplog").append(data);
			});
		
		/* 订单信息 */
		}else if(str == 'order'){
			
			$.post(getContext()+"orderinfo",{level:"pop",cstmId:$("#uid").val()},function(data){
				$("#order").empty();
				$("#order").append(data);
			});
		}else if(str == 'appointment'){
			
			$.post(getContext()+"schedulereminder",{level:"pop",cstmId:$("#uid").val(),phone1:$("#phone_number_a").val(),phone2:$("#phone_number_a_2").val()},function(data){
				$("#appointment").empty();
				$("#appointment").append(data);
			});
		}
	}


	/* 请求客户信息 */
	$(function(){
		
		/* 根据电话号码查询客户信息，填补客户信息  返回  screenpop/customerinfo    */
		
		/* 根据编号或者是号码取数据 */
		$.post(getContext()+'cstm/getCustomerInfoByPhone',{params:'${param!"${params}"}'},function(data){
			
			/* 请求本页面上面的页面信息 绑定数据 */
			$("#div-1").empty();
			/* 从元素内的开始位置插入data */
			$("#div-1").prepend(data);
		
			/* 遍历所有业务逻辑并默认选中沟通小计  执行getActive(str)方法 */
			$("li.active a").click();
		});
		
		getActive("poplog");
		
	});

	
	function initPopTable(){
		
		return $('#dt_pop_log').DataTable({
			"dom" : "t" + "<'dt-toolbar-footer'<'col-sm-6 col-xs-12'i><'col-xs-12 col-sm-6 text-right'lp>>",
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
					param.phone=$("#phone_number_a").val();
				}
			},
			"paging" : true,
			"pagingType" : "bootstrap",
			"lengthChange" : true,
			"order" : [ [ "1", "desc" ] ],
			"columns" : [
				<#if title??>
					<#list dataRows?keys as key>
						<#if key == "call_time" || key == "agent_name" || key == "text_log" > 
							{"title" : "${dataRows[key]}",  "data" : "${key}","defaultContent":""},
						</#if>
					</#list>
				</#if>
				{ 
				   "title" : "操作",
				   "data" : "null",
				   "render": function(data,type,full){
					   if(full.talk_time > 0){
						   return "<a onclick='playCallLog(\""+full.uid+
										   "\");'>播放录音</a>";
					   }else{
						   return "";
					   }
				   }
			   }
			],
		});
	}

	/* 播放 */
	function playCallLog(uid){
		var url = getContext() + "calllog/player";
        $.post(url, {uid : uid},function(data) {
            $("#addpop").append(data);
        });
	}

</script>

