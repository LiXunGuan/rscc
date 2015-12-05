<style>
<!--
.btn.btn-lg.bg-color-blue.txt-color-white{

	background-color: #3276B1!important;
}
-->
</style>
<script src="${springMacroRequestContext.contextPath}/assets/js/adddate.js"></script>
<script src="${springMacroRequestContext.contextPath}/assets/js/calendar/WdatePicker.js"></script>
<div class="modal fade" id="agentNoticeUserSave" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 1200px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                   <strong>关闭</strong>
                </button>
                <h4 class="modal-title" id="myModalLabel">坐席公告</h4>
            </div>
            <div class="modal-body">
                <form action="${springMacroRequestContext.contextPath}/agentnotice/save" class="smart-form" id="coreForm">
                	<fieldset style="padding-top: 2px;">
	                     <input type="hidden" id="uid" name="uid" value="${(item.uid)!''}">
<!-- 	                     <section> -->
<!-- 	                        <div class="row"> -->
<!-- 								<label class="label col col-1">开始时间：</label> <label -->
<!-- 									class="input col col-2">  -->
<!-- 									<input id="publishStartTime" name="publishStartTime" value="${((item.publishStartTime)?string('yyyy-MM-dd HH:mm:ss'))!''}" type="text" class="ui_input_text 400w"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'publishEndTime\')}'})" placeholder="开始时间"/> -->
<!-- 								</label> -->
<!-- 							</div> -->
<!-- 	                    </section> -->
<!-- 	                    <section> -->
<!-- 							<div class="row"> -->
<!-- 								<label class="label col col-1">结束时间：</label> <label -->
<!-- 									class="input col col-2">  -->
<!-- 									<input id="publishEndTime" name="publishEndTime" value="${((item.publishEndTime)?string('yyyy-MM-dd HH:mm:ss'))!''}" type="text" class="ui_input_text 400w"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'publishStartTime\')}'})" placeholder="结束时间"/> -->
<!-- 								</label> -->
<!-- 							</div> -->
<!-- 						</section> -->
<!-- 	                    <section> -->
<!-- 	                        <div class="row"> -->
<!-- 	                            <label class="label col col-1"> 执行次数： </label> -->
<!-- 	                            <label class="input col col-2"> -->
<!-- 	                            	<input id="repeatCount" name="repeatCount" value="${(item.repeatCount)!'1'}" /> -->
<!-- 	                            </label> -->
<!-- 	                        </div> -->
<!-- 	                    </section> -->
<!-- 	                    <section> -->
<!-- 	                        <div class="row"> -->
<!-- 	                            <label class="label col col-1"> 间隔  /秒： </label> -->
<!-- 	                            <label class="input col col-2"> -->
<!-- 	                            	<input id="repeatInterval" name="repeatInterval" value="${(item.repeatInterval)!'0'}" /> -->
<!-- 	                            </label> -->
<!-- 	                        </div> -->
<!-- 	                    </section> -->
	                    <section>
	                        <div class="row">
	                            <label class="label col col-1"> 标题： </label>
	                            <label class="input col col-6">
	                            	<input id="noticeTitle" name="noticeTitle" value="${(item.noticeTitle)!''}" />
	                            </label>
	                        </div>
	                    </section>
	                     <section>
	                       <div class="row">
	                            <label class="label col col-1"> 内容： </label>
	                            <label class="textarea col col-8"> 
									<textarea class="custom-scroll" rows="20" id="noticeContent" name="noticeContent">${(item.noticeContent)!''}</textarea>
								</label>
	                        </div>
	                    </section>
	                    <input type="hidden" id="userUUid" name="userUUid"></input>
			            <input type="hidden" id="usernames" name="usernames" value="">
                    </fieldset>
	            </form>
	            <form id="agentNoticeUserForm">
					<select id="usersUid" name="usersUid" multiple="multiple" size="10" name="duallistbox_demo2" class="demo2" style="height: 400px;">
						<#if users??>
							<#list users as u>
								<option value="${(u.uid)!''}">${(u.loginName)!''}</option>
							</#list>
						</#if>
					</select>
				</form>
            </div>
            <div class="modal-footer">
                <button type="button" id="prevosButton" class="btn btn-primary">上一步 </button>
                <button type="button" id="nextButton" class="btn btn-lg bg-color-blue txt-color-white">下一步 </button>
                <button type="button" id="anuSaveButton" class="btn btn-primary">保存</button>
            </div>
        </div>
    </div>
</div>

<link rel="stylesheet" type="text/css" href="${springMacroRequestContext.contextPath}/assets/js/plugin/duallistbox/bootstrap-duallistbox.css">
<script src="${springMacroRequestContext.contextPath}/assets/js/plugin/duallistbox/jquery.bootstrap-duallistbox.min.js"></script>

<script type="application/javascript">

	var demo2 = $('.demo2').bootstrapDualListbox({
		nonSelectedListLabel: '可选人员',
		selectedListLabel: '已选人员',
		preserveSelectionOnMove: 'moved',
		moveOnSelect: true,
		nonSelectedFilter: '',
		infoText:'<span style="text-align: center">总计:{0}</span>',
	    infoTextEmpty:'无'
	});
	
// 	$("#bootstrap-duallistbox-nonselected-list_usersUid").dblclick(function(){
// 		$("#bootstrap-duallistbox-nonselected-list_usersUid option:selected").each(function(){  
//             var option = "<option value='" + $(this).val() + "'>"  + $(this).text() + "</option>";                   
//             $("#bootstrap-duallistbox-selected-list_usersUid").append(option);  
//             $(this).remove();  
//         });  
// 	});
	
// 	$("#bootstrap-duallistbox-selected-list_usersUid").dblclick(function(){
// 		$("#bootstrap-duallistbox-selected-list_usersUid option:selected").each(function(){  
//             var option = "<option value='" + $(this).val() + "'>"  + $(this).text() + "</option>";                   
//             $("#bootstrap-duallistbox-nonselected-list_usersUid").append(option);  
//             $(this).remove();  
//         });  
// 	});
	
    $('#agentNoticeUserForm').ajaxForm({
        dataType: "json",
        type: "post",
        success: function (data) {
            $("#agentNoticeUserSave").modal("hide");
            if (data.success) {
                $.smallBox({
                    title: "操作成功",
                    content: "<i class='fa fa-clock-o'></i> <i>人员已成功添加...</i>",
                    color: "#659265",
                    iconSmall: "fa fa-check fa-2x fadeInRight animated",
                    timeout: 2000
                });
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
                $("#agentNoticeUserSave").modal("hide");
                $.smallBox({
                    title: "操作失败",
                    content: "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作出现异常...</i>",
                    color: "#C46A69",
                    iconSmall: "fa fa-times fa-2x fadeInRight animated"
                });
            }
        }
    });
</script>

<script type="application/javascript">

$(document).ready(function() {
    
    $('#agentNoticeUserSave').modal("show");
    
    /* 关闭窗口的回调函数,hide是关闭调用后在关闭    */
    $('#agentNoticeUserSave').on('hide.bs.modal', function(e){
		$("#agentNoticeUserSave").remove();
	});
    
    /* 隐藏添加人员表单  */
    $('#agentNoticeUserForm').hide();
    /* 隐藏完成按钮  */
    $('#anuSaveButton').hide();
    /* 禁用上一步按钮   */
    $('#prevosButton').hide();
    
    /* 点击下一步  */
    $('#nextButton').click(function () {
    	if($('#coreForm').valid()) {
//     		var ntime = new Date($("#publishStartTime").val().replace(/-/g, "/"));
//     		var myDate = new Date();
//     		if(ntime < myDate){
//     			if(confirm("公告时间小于当前时间,选择结束后将会立即发布,确定吗?")){
// 			    	document.getElementById("coreForm").style.display = "none";
// 			    	document.getElementById("agentNoticeUserForm").style.display = "block";
// 			        $('#prevosButton').show();
// 			        $('#anuSaveButton').show();
// 			        $('#nextButton').hide();
//     			};
//     		}else{
    			document.getElementById("coreForm").style.display = "none";
		    	document.getElementById("agentNoticeUserForm").style.display = "block";
		        $('#prevosButton').show();
		        $('#anuSaveButton').show();
		        $('#nextButton').hide();
//     		}
 		}
    });
    
    /* 点击上一步 */
    $('#prevosButton').click(function () {
    	document.getElementById("agentNoticeUserForm").style.display = "none";
    	document.getElementById("coreForm").style.display = "block";
        $('#nextButton').attr("disabled", false);
        $('#anuSaveButton').hide();
        $('#prevosButton').hide();
        $('#nextButton').show();
    });
    
    /* 点击保存  */
	$('#anuSaveButton').click(function(){
		var users = "";
		var usernames = "";
		$("#usersUid option:selected").each(function(){
			//得到选中的值拼接成字符串
			users += $(this).val() + ",";
			var uname = $(this).text();
			usernames += uname.substr(uname.lastIndexOf("-")+1, uname.length) + ",";
        });
		
		$("#userUUid").val(users);
		$("#usernames").val(usernames);
		
		if($("#userUUid").val() == ""){
			if(confirm("确定不选择人员吗 ?")){
				$('#coreForm').submit();
			}else{
				$('#coreForm').submit();
			}
		}else{
			$('#coreForm').submit();
		};
		
	});    
    
    /** 校验字段  **/
    var validator = $('#coreForm').validate({
        rules: {
//         	publishStartTime : {
//         		required : true
//         	},
//         	publishEndTime : {
// 				required : true
// 			},
// 			repeatCount : {
// 				digits : true
// 			},
// 			repeatInterval : {
// 				digits : true
// 			},
			noticeTitle : {
				required : true,
				remote : {
					type : 'post',
					url : getContext()+ 'agentnotice/checkTitle',
					data : {
						uid : function() {
							return $("#uid").val();
						},
						title : function() {
							return $("#noticeTitle").val();
						}
					}
				},
				maxlength : 64
			}
        },messages : {
        	noticeTitle : {
        		required : "请输入标题！",
        		remote : "此公告标题已存在!"
			}
//         	,
// 			publishStartTime : {
//         		required : "请输入公告时间！"
//         	},
//         	publishEndTime : {
// 				required : "请输入结束时间！"
// 			},
// 			repeatCount : {
// 				digits : "请输入输入整形数字！"
// 			},
// 			repeatInterval : {
// 				digits : "请输入输入整形数字！"
// 			}
		},
        errorPlacement: function (error, element) {
            error.insertAfter(element.parent());
        }
    });

    $('#coreForm').ajaxForm({
        dataType: "json",
        type: "post",
        success: function (data) {
            $("#agentNoticeUserSave").modal("hide");
            if (data.success) {
                $.smallBox({
                    title: "操作成功",
                    content: "<i class='fa fa-clock-o'></i> <i>知识已成功保存...</i>",
                    color: "#659265",
                    iconSmall: "fa fa-check fa-2x fadeInRight animated",
                    timeout: 2000
                });
                $('#dt_basic_agentnotice').DataTable().ajax.reload(null,false);
            };
        },
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function () {
                    $("#coreForm").addClass('submited');
                }
            });
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            if (textStatus == 'error') {
                $("#agentNoticeUserSave").modal("hide");
                $.smallBox({
                    title: "操作失败",
                    content: "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作出现异常...</i>",
                    color: "#C46A69",
                    iconSmall: "fa fa-times fa-2x fadeInRight animated"
                });
                $('#dt_basic_agentnotice').DataTable().ajax.reload(null,false);
            }
        }
    });
    
});

</script>
