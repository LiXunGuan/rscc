<link rel="stylesheet" type="text/css" href="${springMacroRequestContext.contextPath}/assets/js/plugin/duallistbox/bootstrap-duallistbox.css">
<script src="${springMacroRequestContext.contextPath}/assets/js/plugin/duallistbox/jquery.bootstrap-duallistbox.min.js"></script>

<div class="modal fade" id="agentNoticeUser" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    <strong>关闭</strong>
                </button>
                <h4 class="modal-title" id="myModalLabel">人员管理</h4>
            </div>
            <div class="modal-body col-md-12" >
            	<form id="agentNoticeUserForm" action="${springMacroRequestContext.contextPath}/agentnotice/saveAgentnoticeUser"  method="post">
            		<input type="hidden" id="usernames" name="usernames" value="">
            		<input type="hidden" id="agentNoticeUUid" name="agentNoticeUUid" value="${(agentNoticeUUid)!''}"/>
					<select id="userUUid" name="userUUid" multiple="multiple" size="10" name="duallistbox_demo2" class="demo2" style="height: 400px;">
						<#if users??>
							<#list users as u>
								<option value="${(u.uid)!''}">${(u.loginName)!''}</option>
							</#list>
						</#if>
						<#if userCheckls??>
							<#list userCheckls as uc>
								<option value="${(uc.uid)!''}" selected="selected">${(uc.loginName)!''}</option>
							</#list>
						</#if>
					</select>
				</form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"> 取消  </button>
                <button type="button" id="anSaveButton" class="btn btn-primary"> 保存  </button>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">
	
	$(function(){
		
		$('#agentNoticeUser').modal("show");
		
		var demo2 = $('.demo2').bootstrapDualListbox({
			nonSelectedListLabel: '可选人员',
			selectedListLabel: '已选人员',
			preserveSelectionOnMove: 'moved',
			moveOnSelect: true,
			nonSelectedFilter: '',
			infoText:'<span style="text-align: center">总计:{0}</span>',
		    infoTextEmpty:'无'
		});
		
// 		$("#bootstrap-duallistbox-nonselected-list_userUUid").children().dblclick(function(){
// 	// 		$("#bootstrap-duallistbox-nonselected-list_userUUid option:selected").each(function(){  
// 	//             var option = "<option value='" + $(this).val() + "'>"  + $(this).text() + "</option>";                   
// 	//             $("#bootstrap-duallistbox-selected-list_userUUid").append(option);  
// 	//             $(this).remove();  
// 	//      });
// 			$("[title='Move selected']").click();
// 		});
		
// 		$("#bootstrap-duallistbox-selected-list_userUUid").children().dblclick(function(){
// 	// 		$("#bootstrap-duallistbox-selected-list_userUUid option:selected").each(function(){  
// 	//             var option = "<option value='" + $(this).val() + "'>"  + $(this).text() + "</option>";                   
// 	//             $("#bootstrap-duallistbox-nonselected-list_userUUid").append(option);  
// 	//             $(this).remove();  
// 	//         });  
	
// 			$("[title='Remove selected']").click();
// 		});

		/* 关闭窗口的回调函数,hide是关闭调用后在关闭    */
	    $('#agentNoticeUser').on('hide.bs.modal', function(e){
			$("#agentNoticeUser").remove();
		});
	
	});
		
	
	
// 	var uUUid = document.getElementById("userUUid");
// 	<#if agentNoticeUsers??>
// 		<#list agentNoticeUsers as au>
// 			for (var index = 0; index < uUUid.options.length; index++) {
// 				if(uUUid.options[index].value == "${au.userUUID}"){
// 					alert(uUUid.options[index].value);
// 					uUUid.options[index].setAttribute("selected", true);
// 					break;
// 				}
// 			}
// 		</#list>
// 	</#if>

	function selectMultiple(){

// 		var uUUid = document.getElementById("userUUid");
// 		var str = "";
// 		for (var index = 0; index < uUUid.options.length; index++) {
// 			if(uUUid.options[index].selected){
// 				var uname = uUUid.options[index].text;
// 				str += uname.substr(uname.lastIndexOf("-")+1, uname.length) + ",";
// 			}
// 		}
		
		var str = "";
		$("#userUUid :selected").each(function(){
			var uname = $(this).text();
			str += uname.substr(uname.lastIndexOf("-")+1, uname.length) + ",";
        });
		
		//赋值
		$("#usernames").val(str);
	}
	
  
    $('#anSaveButton').click(function () {
        if ($('#agentNoticeUserForm').valid()) {
        	selectMultiple();//得到选中用户的姓名     
        	if($("#usernames").val() == ""){
        		alert("请选择要添加的人员！");
        	}else{
	            $('#anSaveButton').attr("disabled", true);
	            $('#agentNoticeUserForm').submit();
        	}
        }
    });

    $('#agentNoticeUserForm').ajaxForm({
        dataType: "json",
        type: "post",
        success: function (data) {
            $("#agentNoticeUser").modal("hide");
            if(data.success) {
                $.smallBox({
                    title: "操作成功",
                    content: "<i class='fa fa-clock-o'></i> <i>人员已成功添加...</i>",
                    color: "#659265",
                    iconSmall: "fa fa-check fa-2x fadeInRight animated",
                    timeout: 2000
                });
//                 //判断此公告是否处于发布的状态
//                 if(data.publishStatus == 1){
//                 	republishItem(data.agentNoticeUUid, data.userNames, data.userUUid);
//                 };
            }else{
            	$.smallBox({
                    title: "添加失败",
                    content: "<i class='fa fa-clock-o'></i> <i> " + data.message + "</i>",
                    color: "#C46A69",
                    iconSmall: "fa fa-check fa-2x fadeInRight animated",
                    timeout: 2000
                });
            };
        },
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function () {
                    $("#agentNoticeUserForm").addClass('submited');
                }
            });
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            if (textStatus == 'error') {
                $("#agentNoticeUser").modal("hide");
                $.smallBox({
                    title: "操作失败",
                    content: "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作出现异常...</i>",
                    color: "#C46A69",
                    iconSmall: "fa fa-times fa-2x fadeInRight animated"
                });
            }
        }
    });

    
    /* 发布公告 */
	function republishItem(agentNoticeUuid, userNames, userUUid){
		 $.SmartMessageBox({
		        title : "发布公告",
		        content : "是否重新发布这条公告信息吗？",
		        buttons : '[No][Yes]'
		    }, function(ButtonPressed) {
		        if (ButtonPressed === "Yes") {
		            var url = getContext()+"agentnotice/publishAgentNotice";
		            $.post(url,{agentNoticeUUid:agentNoticeUuid, userNames:userNames, userUUid:userUUid},function(data){
		                if(data.success){
		                    $.smallBox({
		                        title : "操作成功",
		                        content : "<i class='fa fa-clock-o'></i> <i>发布成功...</i>",
		                        color : "#659265",
		                        iconSmall : "fa fa-check fa-2x fadeInRight animated",
		                        timeout : 2000
		                    });
		                    $('#dt_basic_agentnotice').DataTable().ajax.reload(null,false);;
		                }else{
		                    $.smallBox({
		                        title : "操作失败",
		                        content : "<i class='fa fa-clock-o'></i> <i>" + data.errorMessage + "</i>",
		                        color : "#C46A69",
		                        iconSmall : "fa fa-times fa-2x fadeInRight animated",
		                        timeout : 3000
		                    });
		                }
		            },"json").error(function(){
		                $.smallBox({
		                    title : "操作失败",
		                    content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作失败...</i>",
		                    color : "#C46A69",
		                    iconSmall : "fa fa-times fa-2x fadeInRight animated"
		                });
		            });
		        }

		  });
	}

</script>
