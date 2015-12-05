<style>
	#toolBox{
		float:left;
		table-layout:auto;
		margin-bottom:5px;
	}
.control-group.form-group.data-name{
	height:25px;
}
.popover.bottom{
	margin-top:20px;
}
.popover .popover-title{
	padding: 8px 14px;
}
.popover .popover-content{
	padding: 9px 14px;
}
.popover.bottom{
	margin-top:25px;
}
#uploadProgress{
	display:none;
	border:none;
}
.progress{
	height: 10px;
	margin: 0px;
}
</style>
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/assets/css/select2.min.css">
<script src="${springMacroRequestContext.contextPath}/assets/js/jquery-file-uploader/js/vendor/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/x-editable/x-editable.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/select2/select2.min.js"></script>
<div id="tmpDivConfrence"></div>
<div id="userContentConfrence" style="">
	<!-- HEADER -->
	<!-- END RIBBON -->
	<!-- widget grid -->
	<section id="widget-grid-confrence" class="">
<section>
<!-- 				<div class="row" style="margin:0;position:relative;"> -->
<!-- 				<table cellspacing="0" cellpadding="0" border="0" class="ui-pg-table navtable" id="toolBox"> -->
<!-- 				<tbody> -->
<!-- 				<tr> -->
<!-- 				<td class="ui-pg-button ui-corner-all" id="confrence" title="创建" onclick="adddeleteConfrence();"> -->
<!-- 					<div class="btn btn-sm btn-primary"> -->
<!-- 					<span class="">创建会议室</span> -->
<!-- 					</div> -->
<!-- 				</td> -->
<!-- 				<td class="ui-pg-button ui-state-disabled" style="width:6px;" title=""> -->
<!-- 					<span class="ui-separator"></span> -->
<!-- 				</td> -->
<!-- 				</tr> -->
<!-- 				</tbody> -->
<!-- 				</table> -->
				<#if errorMessage ??>
					<span style='font-size:30px'>${errorMessage}</span>
				<#else>
				<div class="smart-form">
				<section>
					<span class="btn btn-sm btn-primary" id="confrence" title="创建" onclick="adddeleteConfrence();" style="float:left;">创建会议室</span>
					<label class="input col col-1"></label>
<!-- 					<label class="label col">号码</label> -->
<!-- 					<label class="input col col-6"> -->
<!-- 						<input type="text" name="invitation" id="invitation" value="" /> -->
<!-- 					</label> -->
					<label class="btn btn-sm btn-primary" onclick="invitationMember();">邀请</label>
				</section>
				</div>
				</#if>
<!-- 				<td class="ui-pg-button ui-corner-all" title="" id="refresh" data-original-title="Reload Grid" onclick="getResult();"> -->
<!-- 					<div class="btn btn-sm btn-primary"> -->
<!-- 					<span class="">刷新内容</span> -->
<!-- 					</div> -->
<!-- 				</td> -->
				<!-- <td class="ui-pg-button ui-corner-all ui-state-disabled" id="jqgrid_ilsave" title="" data-original-title="Save row">
					<div class="btn btn-sm btn-success">
					<span class="fa fa-save"></span>
					</div>
				</td>
				<td class="ui-pg-button ui-corner-all ui-state-disabled" id="jqgrid_ilcancel" title="" data-original-title="Cancel row editing">
					<div class="btn btn-sm btn-danger">
					<span class="fa fa-times">
					</span>
					</div>
				</td> -->

			</section>
		<div class="row">
			<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">

				<div class="jarviswidget jarviswidget-color-darken" id="wid-id-0" data-widget-editbutton="false">
					<header>
						<span class="widget-icon"><i class="fa fa-table"></i></span>
						<h2>会议室</h2>
					</header>
					<div>
						<div class="widget-body no-padding">
							<div class="widget-body-toolbar">
								<div class="smart-form">
								<input type="hidden" name="confrenceID" id="confrenceID" value="${confrenceId!''}" />
									<table id="confrenceTable"
										class="table table-striped table-bordered table-hover"
										data-order='[[ 1, "asc" ]]' width="100%"></table>
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

			var active = $('<div><div style="margin-top: -8px;"><div style="text-align:left;display: inline-block;width: 50%;"><span class="txt-color-purple" style="font-size:10px">数据进度</span></div><div style="text-align:right;display: inline-block;width: 50%;"><span class="txt-color-purple" style="font-size:10px">已完成100%</span></div></div></div>');
			
		    $('#confrenceTable').DataTable({
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
			    	"url" : getContext() + "confrence/data",
			    	"type": "POST",
			    	"data": function(param) {
						param.confrenceId = $("#confrenceID").val();
					}
			    },
// 			    "infoCallback":function(settings, start, end, max, total, pre){
// 			    	return "显示第 " + start + " 至 " + end + " 项结果，共 " + total + " 项，已选中 " + getCheckNum() + " 项";
// 			    },
			 	"paging" :true,
				"pagingType" :"bootstrap",
				"lengthChange" : true,
				"order" : [[ "1", "desc"]],
				"columns" : [
// 						{"title":"序号","data":"id","defaultContent":""},
						{"title":"号码","data":"extension","defaultContent":""},
// 						{"title":"邀请状态","data":"invitation_state","defaultContent":""},
						{
							"title":"邀请状态",
							"data":"invitation_state",
							"render": function (data, type, full) {
								if(full.invitation_state == 1)
									return "<b>邀请中</b>";
								else if(full.invitation_state == 2)
									return "<b>正在通话</b>";	
								else if(full.invitation_state == 3)
									return "<b>已退出</b>";
								else if(full.invitation_state == 4)
									return "<b>已踢出</b>";
	                        }
						},
						{
							"title":"通话时长",
							"data":"null",
							"render": function (data, type, full) {
								var sTime;
								if(typeof full.answer_time != "undefined")
									sTime = new Date(full.answer_time.replace(/-/g,"/"));//Firefox不支持-日期格式化
								else if(typeof full.establish_time != "undefined")
									sTime = new Date(full.establish_time.replace(/-/g,"/"));
								else
									sTime = new Date();
								var nTime = new Date();
								var seconds = Math.round((nTime.getTime() - sTime.getTime()) /1000);
								if(full.invitation_state == 3 || full.invitation_state == 4)
                                	return '<span>'+ seconds +'</span>';
                                else
                                	return '<span name="addtime">'+ seconds +'</span>';
	                        }
						},
					   { 
						   "title" : "操作",
						   "data" : "null",
						   "render": function(data,type,full){
							   var str;
							   if(full.mute_state=="2")
							   	  str = '<a onclick="' + "mute(\'"+full.extension+"','"+full.mute_state+"\');\">启用</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='deleteNum(\""+full.extension+"\");'>踢出</a>"
						   	   else
							   	  str = '<a onclick="' + "mute(\'"+full.extension+"','"+full.mute_state+"\');\">禁言</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='deleteNum(\""+full.extension+"\");'>踢出</a>"
							   if(full.invitation_state == 3 || full.invitation_state == 4)
								  str = str + "&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='invitationMember(\""+full.extension+"\",\""+full.accessNumber+"\");'>再次邀请</a>"
						   	   return str;
						   }
					   }
					],
			});
		    
		    <#if confrenceId??>
		    changeConfrenceStat(0,${confrenceId});
		    <#else>
		    $("#widget-grid-confrence>.row").hide();
		    changeConfrenceStat(1,'');
		    </#if>
        });

		function changeConfrenceStat(stat,confrenceId){
			if(stat==0){//关闭状态
				$("#confrence").attr("title","关闭")
				$("#confrence").addClass("btn-danger");
				$("#confrence").text("关闭会议室");
				$("#confrenceID").val(confrenceId);
				$("#confrence").nextAll().css("visibility","visible");
// 				$("#widget-grid-confrence>.row").css("visibility","visible");
				$("#widget-grid-confrence>.row").show("slow");
				$('#confrenceTable').DataTable().ajax.reload(null,false);;
			}else{
				$("#confrence").attr("title","创建")
				$("#confrence").removeClass("btn-danger");
				$("#confrence").text("创建会议室");
				$("#confrenceID").val(confrenceId);
				$("#confrence").nextAll().css("visibility","hidden");
// 				$("#widget-grid-confrence>.row").css("visibility","hidden");
				$("#widget-grid-confrence>.row").hide("slow");
				$('#confrenceTable').DataTable().ajax.reload(null,false);;
			}
			
		}
		
		function adddeleteConfrence() {
			if($("#confrence").attr("title")=="创建"){
				$.post(getContext() + "confrence/create",function(data){
					$("#forModel").append(data);
		        });
// 				$.post(getContext() + "confrence/add",function(data){
// 					if (data.success) {
// 						if(data.confrenceId != "0"){
// 							changeConfrenceStat(0, data.confrenceId);
// 						}
// 					}
// 				},"json");
			}
			else{
				$.post(getContext() + "confrence/delete",{confrenceId:$("#confrenceID").val()},function(data){
					if(data.confrenceId != "0"){
						changeConfrenceStat(1, data.confrenceId);
					}
				},"json");
			}
		}	
		
		function invitationMember(number, accessNumber){
// 		function invitationMember(){
			if(number === undefined) {
				$.post(getContext() + "confrence/invite",{confrenceId:$("#confrenceID").val()}, function(data){
					$("#forModel").append(data);
		        });
			} else {
				var requestObject = {};			
				if (accessNumber) {
					requestObject = {confrenceId:$("#confrenceID").val(),num:number,accessnumber:accessNumber};
				} else {
					requestObject = {confrenceId:$("#confrenceID").val(),num:number};
				}
				$.post(getContext() + "confrence/invitation",requestObject,function(data){
					if(data.success){
						$('#confrenceTable').DataTable().ajax.reload(null,false);
					}
				},"json");
			}
// 			if(number === undefined) {
// 				if($("#invitation").val()!="") {
// 					$.post(getContext() + "confrence/invitation",{confrenceId:$("#confrenceID").val(),num:$("#invitation").val()},function(data){
// 						if(data.success){
// 							$('#confrenceTable').DataTable().ajax.reload(null,false);;
// 						}
// 					},"json");
// 				}
// 			} else {
// 				$.post(getContext() + "confrence/invitation",{confrenceId:$("#confrenceID").val(),num:number},function(data){
// 					if(data.success){
// 						$('#confrenceTable').DataTable().ajax.reload(null,false);;
// 					}
// 				},"json");
// 			}
		}
		
		function mute(phone,state){
			if(state=="2"){
				unmute(phone);
				return;
			}
			$.post(getContext() + "confrence/mute",{confrenceId:$("#confrenceID").val(),num:phone},function(data){
				if(data.success){
					$('#confrenceTable').DataTable().ajax.reload(null,false);;
				}
			},"json");
		}
		
		function unmute(phone){
			$.post(getContext() + "confrence/unmute",{confrenceId:$("#confrenceID").val(),num:phone},function(data){
				if(data.success){
					$('#confrenceTable').DataTable().ajax.reload(null,false);;
				}
			},"json");
		}
		
		function deleteNum(phone){
			$.post(getContext() + "confrence/deleteNum",{confrenceId:$("#confrenceID").val(),num:phone},function(data){
				if(data.success){
					$('#confrenceTable').DataTable().ajax.reload(null,false);;
				}
			},"json");
		}
		
		function deleteConfrence(uuid){
			$.SmartMessageBox({
				title : "删除",
				content : "该操作将执行删除操作，确定执行？",
				buttons : "[No][Yes]",
				//input : "text",
				//placeholder : "请填写备注:"
			}, function(ButtonPressed, Value) {
				if (ButtonPressed === "Yes") {
					$.post(getContext() + "data/confrence/delete",{"uuid":uuid},function(data){
						if(data.success){
							$.smallBox({
								title : "操作成功",
								content : "<i class='fa fa-clock-o'></i> <i>删除成功...</i>",
								color : "#659265",
								iconSmall : "fa fa-check fa-2x fadeInRight animated",
								timeout : 2000
							});
						}else{
							$.smallBox({
								title : "操作失败",
								content : "<i class='fa fa-clock-o'></i> <i>删除失败...</i>",
								color : "#C46A69",
								iconSmall : "fa fa-times fa-2x fadeInRight animated",
								timeout : 2000
							});
						}
						$('#confrenceTable').DataTable().ajax.reload(null,false);;
					},"json").error(function(){
							$.smallBox({
								title : "操作失败",
								content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>删除时出现异常...</i>",
								color : "#C46A69",
								iconSmall : "fa fa-times fa-2x fadeInRight animated"
							});
						});
				}
				if (ButtonPressed === "No") {
					$.smallBox({
						title : "取消操作",
						content : "<i class='fa fa-clock-o'></i> <i>您取消了该操作</i>",
						color : "#C46A69",
						iconSmall : "fa fa-times fa-2x fadeInRight animated",
						timeout : 2000
					});
				}
			});
		}		

		var timeAdd;
	    clearInterval(timeAdd);
	    timeAdd = setInterval(addsecc,1000);
	    function addsecc(){
	        $("[name=addtime]").each(function(){
	            var curr = new Number($(this).text());
	            $(this).text(curr + 1);
	        })
	    }
	    
	    function getResult(){
	    	$('#confrenceTable').DataTable().ajax.reload(null,false);	
	    }
	    
	</script>