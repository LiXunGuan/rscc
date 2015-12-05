

<link rel="stylesheet" type="text/css" media="all" href="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-daterangepicker-master/font-awesome.min.css"/>
<link rel="stylesheet" type="text/css" media="all" href="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-daterangepicker-master/daterangepicker-bs3.css"/>

<!-- <div class="row"> -->
<script src="${springMacroRequestContext.contextPath}/assets/js/plugin/morris/raphael.min.js"></script>
<script src="${springMacroRequestContext.contextPath}/assets/js/plugin/morris/morris.min.js"></script>
<script src="${springMacroRequestContext.contextPath}/assets/js/smartwidgets/jarvis.widget.js"></script>
<script src="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-tags/bootstrap-tagsinput.js"></script>


<style>

  .col-xs-12.col-sm-12.col-md-12.col-lg-12{
	  width: 95%;
	  position: relative;
	  float: left;
	  padding-left: 0px;
  }

</style>


<#if pools??>
	<div id="forModal"></div>
	<div class="row">
		<div class="col-sm-12 col-md-12 col-lg-12">
			<div class="well well-sm well-light">
		
				<input type="hidden" value="" id="hiddenPoolId">
				<input type="hidden" value="" id="successTag">
				<input type="hidden" value="" id="formName">
				<div id="tabs">
					<ul>
						<!-- 新添加的按时间升序排列 -->
						<#list pools?sort_by("createTime") as pool>
							<li><a href="#${pool.uuid}" onclick="getCustomerInfoBypoolId('${pool.uuid}');" id="li${pool.uuid}">${pool.poolName}</a></li>
						</#list>
							<li id="liID"><label title="添加一个客户池" class="btn btn-sm btn-info" href="javascript:void();" data-toggle="tab" id="add_tab"><i class="fa fa-plus"></i></label>
					</ul>
						<#list pools?sort_by("createTime") as pool>
							<#if pool_index == 0>
								<div id="${pool.uuid}" name="tab-contents" class="widget-body" style="height: 800px ; padding-top: 20px;width: 100%; vertical-align: top;">
									<#include "cstm-main-content.ftl">
								</div>
							<#else>
								<div id="${pool.uuid}" name="tab-contents" class="widget-body" style="height: 800px ; padding-top: 20px;width: 100%; vertical-align: top;"></div>
							</#if>
						</#list>
				</div>
			</div>
			</div>
		</div>
	</div>
<#else>
	<#include "../data/nodata.ftl">
</#if>

<script src="${s}/assets/js/plugin/select2/select2.min.js"></script>

<script type="text/javascript">

	/* 初始化tab */
	var addtabs = $('#tabs').tabs();
	
	var vals = $("#val").val();
	
	$("#addCstmpool").remove();
	
	$(function(){

		/* 如果从客户池跳转 选中默认选中的客户池 */
		if(window.parent.current_pool_id){
			
			$("#tabs ul li").removeClass("ui-state-default ui-corner-top ui-tabs-active ui-state-active");
			$("#li"+window.parent.current_pool_id).closest("li").addClass("ui-state-default ui-corner-top ui-tabs-active ui-state-active");		
		}
		
	});

	
	/* 添加客户池弹框 */
	$("#add_tab").click(function() {
		
		$.post(getContext()+"cstmpool/get",function(data){
			
			$("#forModal").empty();
			$("#forModal").append(data);
		});
	});
	
	/* 
		点击客户池统计
		cstm-main-content
	*/
	function getCustomerInfoBypoolId(pool_id){
		
		$.post(getContext() + "cstm/getCustomerInfoBypoolId",{poolId:pool_id},function(data){
			$('div[name="tab-contents"]').empty();
			$('#'+pool_id).append(data);
		});
	}
	
	/* 点击客户详细信息 */
	function getCstmsByPoolId(pool_id){
		
		$.post(getContext() + 'cstm/getCstmsByPoolId',{poolid:pool_id},function(data){
			$("#"+pool_id+"-r2").empty();
			$("#"+pool_id+"-r2").append(data);
		});
	}
	
	/*  
		点击编辑客户池
		cstm-pool-content.ftl
	*/
 	function getEditCstmPool(pool_id){
		
		$.post(getContext() + 'cstmpool/getCstmsByPool',{poolId:pool_id},function(data){
			
			$("#"+pool_id+"-r3").empty();
			$("#"+pool_id+"-r3").append(data);
		});
	}
	
	/* 删除客户池 */
	function deleteForm(pool_id){
		
		$.post(getContext()+'cstmpool/allPools',{poolId:pool_id},function(d){
			
			poolNames = "";
			if(d.success){
				for ( var p in (d.pools)) {
					if(d.pools[p].poolName != d.po){
						
						poolNames += '['+d.pools[p].poolName+']';					
					}
				}
				//判断是否还有客户池
				if(poolNames.length > 0 ){
					
					$.SmartMessageBox({
						title : "删除",
						content : "系统不允许删除包含客户的客户池，如果你一定要强制删除，请为选择一个新的客户池！",
						buttons : "[确定][取消]",
						input : "select",
						options : poolNames
					}, function(ButtonPress, Value) {
						
						if(ButtonPress === "确定"){
							$.SmartMessageBox({
								title : "删除确定",
								content : "<h1>请在输入框中输入“YES”或者“yes”来确定你的操作！<h1>",
								buttons : "[确定][取消]",
								input : "text",
								inputValue : ""
							}, function(ButtonPressed, Values) {
								if(ButtonPressed === "确定" && (Values ==="yes" || Values === "YES")){
									$.post(getContext()+'cstmpool/changePool',{poolName:Value,poolId:pool_id},function(data){
										if(data.success){
											$.smallBox({
												title : "提示",
												content : "操作成功！",
												color : "#5384AF",
												icon : "fa fa-check"
											});
										}
									},"json");
									
									window.location.reload(true);
								
								}else{
									$.smallBox({
										title : "提示",
										content : "操作取消！",
										color : "#5384AF",
										icon : "fa fa-bell"
									});
								}
							});
							
						}else{ /* 操作取消 */
							
							$.smallBox({
								title : "提示",
								content : "操作取消！",
								color : "#5384AF",
								icon : "fa fa-bell"
							});
						}
					});
				}else{ /* if 结束  */
					alert("该客户池为您的唯一客户池且池中存在客户，不允许删除！");
				}
			}else{ //如果客户池中不存在客户  提示并直接删除
				
				$.SmartMessageBox({
					title : "删除",
					content : "该客户池中不存在客户，确定直接删除？",
					buttons : "[确定][取消]",
				}, function(ButtonPress, Value) {
					if(ButtonPress === '确定'){
						$.post(getContext() + 'cstmpool/deletepool',{poolId:pool_id},function(ds){
							if(ds.success){
								$.smallBox({
									title : "提示",
									content : "操作成功！",
									color : "#5384AF",
									icon : "fa fa-check"
								});
								
								window.location.reload(true);

							}else{
								$.smallBox({
									title : "提示",
									content : "该客户池中存在客户，不允许删除！",
									color : "#5384AF",
									icon : "fa fa-check"
								});
							}
						},"json");
					}
				});
			}
		},"json");
	}
	
	/* 保存客户池 */
	function subForm(pid){
		
		$("#li"+pid).text($("#poolName").val());
		$("#poolInfos"+pid).submit();
	}

</script>
