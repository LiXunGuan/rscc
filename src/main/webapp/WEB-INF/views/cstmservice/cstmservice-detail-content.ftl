<script src="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-tags/bootstrap-tagsinput.js"></script>

<style>

.inbox-info-bar .form-group .tt-hint{
 	border-color: transparent!important;
}
.inbox-info-bar .form-group .form-control{
	border-color: #ccc!important;
}

/* div#popover.editable-container.editable-popup{ */

.popover.fade.top.in.editable-container.editable-popup{
	left: 40px;
}

 .select2-hidden-accessible { 
 	display: none; 
 }

</style>

<!-- -----------------------------------------------------------最新----------------------------------------------------------- -->
<input type="hidden" id="cstm-uid"  value="${(cstmservice.uid)!''}">

<!-- -----------------------------------------------------------按钮操作框----------------------------------------------------------- -->
<div class="inbox-info-bar">
	<div class="row">
		<div class="col-sm-12 col-lg-12 text-left" >
		<!-- 负责人  标签 -->
			<form action="" style="width: 100%; padding-left: 8px;" >
				<table style="width: 100%; ">
					<tr>
						<td style="vertical-align: top;">工单名称:</td>
						<td style="vertical-align: top;">
							<h2 class="email-open-header">
								<textarea rows="" cols="" style="width: 100%;min-height:80px; border: none;resize:none;" id="cstmserviceName" name="cstmserviceName" onclick="addCss(this);" onblur="removeCss(this)">${(cstmservice.cstmserviceName)!''}</textarea>
							</h2>
						</td>
					</tr>
					<tr>
					<td><br/></td>
					</tr>
					<tr>
						<td style="padding-right: 4px;width: 7%; vertical-align: top;"> 工单状态:</td>
						<td colspan="1" style="text-align: left; vertical-align: top;">
							<div class="btn-group">
								<a class="btn btn-default" href="javascript:void(0);" id="lableStatus">${csStstus[cstmservice.cstmserviceStatus]}</a>
								<a class="btn btn-default dropdown-toggle" data-toggle="dropdown" href="javascript:void(0);"><span class="caret"></span></a>
								<ul class="dropdown-menu">
								
								<#if csStstus??>
									<#list csStstus?keys as key>
										<#if key == "0">
											<li>
												<a href="javascript:changeStatus(${key});"><i class="fa fa-remove"></i>&nbsp;标记为${csStstus[key]}</a>
											</li>
										<#else>
											<li>
												<a href="javascript:changeStatus(${key});"><i class="fa fa-check"></i>&nbsp;标记为${csStstus[key]}</a>
											</li>
										</#if>
									</#list>
								</#if>
								</ul>
							</div>
						</td>
					</tr>
					
					<tr>
					<td><br/></td>
					</tr>
					<tr style="">
						<td style="padding-right: 4px;width: 7%; vertical-align: top;"> 负&nbsp;&nbsp;责&nbsp;&nbsp;人:</td>
						<td><a href="javascript:void(0);" id="auser" name="auser" data-type="select2" data-pk="1" data-name="sssew" data-select-search="true" data-value="${tempuser!''}" style="left: 40px;" data-original-title="选择负责人"></a></td>
					</tr>
					<tr><td><br/><td></tr>
					<tr>
						<td style="padding-right: 4px;width: 7%;vertical-align: top;">添加标签:</td>
						<td>
							<#if alltags??>
								<input class="form-control tagsinput" id="allTags" name="tags" value="${alltags?substring(1,alltags?length-1)}" data-role="tagsinput" placeholder="按回车确认！">
							<#else>
								<input class="form-control tagsinput" id="allTags" name="tags" value="" data-role="tagsinput" placeholder="按回车确认！">
							</#if>
						</td>
					</tr>
					
					<tr><td><br/><td></tr>
					 <tr>
					 	<td style="padding-right: 4px;width: 7%; vertical-align: top;">工单描述:</td>
					 	<td colspan="4">
					 		<textarea rows="" cols="" style="width: 100%;height:100px;border: none; text-indent: 2em; resize:none;" id="questionDes" name="questionDes" onclick="addCss(this);" onblur="removeCss(this)" >${(cstmservice.cstmserviceDescription)!''}</textarea>
					 	</td>
					 </tr>
				</table>
			</form>
		</div>
	</div>
</div>

<div class="row col col-6" id="replays">
	<table style="width: 60%;text-align: left;">
	  <tr>
	    <td colspan="1"><label class="btn btn-sm btn-info" id="reply" style="margin-left:2%;margin-top: 2px; text-align: right;vertical-align: center;" >回复</label></td>
	  </tr>
	</table>
</div>

<div class="inbox-message" id="inbox" style="background: transparent;">
	<div style="display: none;" id="questionComments">
	<form action="" >
		<table style="width: 100%">
			<tr>
				<td style="vertical-align: top;width: 8%;">问题评论:</td>
				<td><textarea name="comments" id="comments" rows="" cols="" style="width: 100%;height:100px;resize:none;" onclick="addCss(this);" ></textarea></td>
				<td style="width: 7%; vertical-align: bottom;"><label class="btn btn-sm btn-primary" id="addComents" style="margin-top: 2px; text-align: right;float: right;vertical-align: center;" >评论</label></td>
			</tr>
		</table>
	</form>
	</div>
	
	<div class="row" style="border: none;">
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<div class="well well-sm">
				<div class="smart-timeline">
					<ul class="smart-timeline-list" id="addedContent">
						<#if comments??>
							<#list comments?sort_by("commentTime")?reverse as c>
								<li>
									<div class="smart-timeline-icon">
										<i class="fa fa-comments"></i>
									</div>
									<div class="smart-timeline-time">
										<small><strong>${(c.cstmName)!''} </strong></small>
									</div>
									<div class="smart-timeline-content" >
										<p><a href="javascript:void(0);">${c.commentTime?datetime} 回复：</a></p>
										<p style="text-indent: 2em; word-wrap:break-word; ">${c.commonts}</p>
									</div>
								</li>
							</#list>
						<#else>
							<p id="noComments">
								&nbsp;&nbsp;&nbsp;&nbsp;暂时没有关于这个问题的任何描述！
							</p>
						</#if>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="email-infobox" style="margin-top: 30px;">
	<div class="well well-sm well-light">
		<h5>相关人员</h5>
		<ul class="list-unstyled">
			<li>
				<i class="fa fa-user fa-fw text-success"></i><a href="javascript:void(0);"> 发起者：${cstmservice.cstmserviceReporter}</a>
			</li>
			<li>
				<i class="fa fa-user fa-fw text-danger"></i><a href="javascript:void(0);"><strong> 指派人：${cstmservice.userName}</strong></a>
			</li>
		</ul>
	</div>

	<div class="well well-sm well-light">
		<h5>计划完成时间</h5>
		<p>
			<span class="label label-success"><i class="fa fa-check"></i> ${planEndTime?datetime?string("yyyy-MM-dd HH:mm")}</span>
		</p>
		 
	</div>	


<ul class="list-inline">
<!-- 	<li><img src="${springMacroRequestContext.contextPath}/assets/img/avatars/5.png" alt="me" width="30px"></li> -->
<!-- 	<li><img src="${springMacroRequestContext.contextPath}/assets/img/avatars/3.png" alt="me" width="30px"></li> -->
<!-- 	<li><img src="${springMacroRequestContext.contextPath}/assets/img/avatars/sunny.png" alt="me" width="30px"></li> -->
</ul>

</div>

<script src="${springMacroRequestContext.contextPath}/assets/js/plugin/x-editable/moment.min.js"></script>
<script src="${springMacroRequestContext.contextPath}/assets/js/plugin/x-editable/jquery.mockjax.min.js"></script>
<script src="${springMacroRequestContext.contextPath}/assets/js/plugin/x-editable/x-editable.min.js"></script>


<script type="text/javascript">


$(document).ready(function() {
	
	pageSetUp();
	
	$("#allTags").tagsinput("build",{confirmKeys:[13,32]});
	
});

var content = '';

/* 添加评论 */
$("#addComents").click(function(){
	
	if($("#comments").val()==""){
		alert("请填写评论以后再提交！");
		return ;
	}
	
	$("#noComments").remove();
	
	$("#addedContent").prepend('<li><div class="smart-timeline-icon"><i class="fa fa-comments"></i></div>'+
	'<div class="smart-timeline-time"><small><strong>${currentUser} </strong></small></div>'+
	'<div class="smart-timeline-content"><p><a href="javascript:void(0);">${.now?datetime} 回复：</a></p>'+
	'<p style="text-indent: 2em;">'+ $("#comments").val() +'</p></div></li>');
	/* 添加评论 */
	$.post(getContext() + "cstmservice/addComments",{comment:$("#comments").val(),uid:$("#cstm-uid").val()},function(data){
		if(data.success){
			$.smallBox({
				title : "操作成功",
				content : "<i class='fa fa-clock-o'></i> <i>评论成功...</i>",
				color : "#659265",
				iconSmall : "fa fa-check fa-2x fadeInRight animated",
				timeout : 2000
			});
		}
	},"json");
	
	
	/* 清空 */
	document.getElementById("comments").value="";
});

/* 修改状态 */
function changeStatus(status){
	
	$.post(getContext() + "cstmservice/changeStatus",{status:status,uid:$("#cstm-uid").val()},function(data){
		
		if(data.success){
			$("#lableStatus").text("未解决");
			
			if(status == "1"){
				$("#lableStatus").text("已解决");
			}
			
			$.smallBox({
				title : "操作成功",
				content : "<i class='fa fa-clock-o'></i> <i>状态修改成功...</i>",
				color : "#659265",
				iconSmall : "fa fa-check fa-2x fadeInRight animated",
				timeout : 2000
			});
		}
	},"json");
}

//展开编辑框
$("#edit").editable({
    url: '/post',
    type: 'text',
    pk: 1,
    name: 'username',
    title: 'Enter username'
});


function addCss(obj){
	$(obj).css("border","1px solid gray");
}

$('#group').editable({
    showbuttons: false
});

/* 负责人 */
var users = [];
$.each({
    ${arrays}
}, function (k, v) {
    users.push({
        id: k,
        text: v
    });
});

$('#auser').editable({
    source: users,
    select2: {
        width: 180
    },
    params: function(params) {
    	params.uid = $("#cstm-uid").val(); 
		return params;
    },
	url:getContext()+"cstmservice/updateAssignee",
    type:"post",
    mode : "inline"
});

$('#auser').on('select2:select', function(e) {
	
//     alert('new value: ' + e.value);
    alert('new value: ');
});

/* 移除样式 */
function removeCss(obj){
	
	var name = $(obj).val();
	$(obj).css("border","none");

	var requestURL = getContext() + 'cstmservice/updateDetails';
	
	$.post(requestURL,{name:name,param:obj.id,uid:$("#cstm-uid").val()},function(data){
		
		if(data.success){
			dt_basic_cstmservice.ajax.reload(null,false);
		}
		
	},"json");
}

/* 添加标签事件 */
$('#allTags').on('itemAdded', function(event) {
	
	$.post(getContext() +'cstmservice/tagsinput',{tagsinput:event.item,cstmserviceId:$("#cstm-uid").val(),status:1},'json');
});

/* 删除标签事件 */
$('#allTags').on('itemRemoved', function(event) {
	
	$.post(getContext() +'cstmservice/tagsinput',{tagsinput:event.item,cstmserviceId:$("#cstm-uid").val(),status:0},'json');
});

/* 回复 */
$("#reply").click(function(){
	
	$("#questionComments").css('display','block'); 
	$("#replays").css('display','none'); 
});

</script>
