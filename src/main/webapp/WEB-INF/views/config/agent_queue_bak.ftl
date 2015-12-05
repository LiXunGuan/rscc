<link rel="stylesheet" type="text/css" href="${springMacroRequestContext.contextPath}/assets/js/plugin/duallistbox/bootstrap-duallistbox.css">
<script src="${springMacroRequestContext.contextPath}/assets/js/plugin/duallistbox/jquery.bootstrap-duallistbox.min.js"></script>

<div class="modal fade" id="saveModel"  role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 800px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">人员管理</h4>
            </div>
            <div class="modal-body">
<!--             <div class="modal-body col-md-12"> -->
<!--             	<div class="row"> -->
<!--             	<div class="col-md-8"> -->
            	<div class="tabs-left">
					<ul class="nav nav-tabs tabs-left" id="demo-pill-nav">
						<li class="active">
							<a href="#tab-agentConfig" data-toggle="tab" id="agentConfig"> 坐席   </a>
						</li>
						<li>
							<a href="#tab-extenConfig" data-toggle="tab" id="extenConfig"> 分机    </a>
						</li>
						<li>
							<a href="#tab-phoneConfig" data-toggle="tab" id="phoneConfig"> 手机号 </a>
						</li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane active" id="tab-agentConfig">
				            <form id="agentQueueForm" action="${springMacroRequestContext.contextPath}/config/queue/saveaq" method="post">
		            			<input type="hidden" id="id" name="id" value="${(id)!''}"/>
		            			<input type="hidden" id="extens" name="extens"/>
								<select id="agentUid" name="agentUid" multiple="multiple" size="10" name="duallistbox_demo2" class="demo2" style="height: 400px;">
									<#if agentls??>
										<#list agentls as a>
											<option value="${(a.uid)!''}">${(a.info)!''}</option>
										</#list>
									</#if>
									<#if agentCheckls??>
										<#list agentCheckls as ac>
											<option value="${(ac.uid)!''}" selected="selected">${(ac.info)!''}</option>
										</#list>
									</#if>
								</select>
							</form>
			            </div>
						<div class="tab-pane" id="tab-extenConfig">
			                <form id="extenConfigForm" action="" method="post">
			                	<select id="extenUid" name="extenUid" multiple="multiple" size="10" name="duallistbox_demo3" class="demo2" style="height: 400px;">
									<#if extens??>
										<#list extens as a>
											<option value="${(a.sipId)!''}">分机_${(a.sipId)!''}</option>
										</#list>
									</#if>
									<#if extenCheckls??>
										<#list extenCheckls as ac>
											<option value="${(ac.sipId)!''}" selected="selected">分机_${(ac.sipId)!''}</option>
										</#list>
									</#if>
								</select>
				            </form>
			            </div>
						<div class="tab-pane" id="tab-phoneConfig">
							<div class="row addable hidden">
								<input type="hidden" name="queuephoneid" value=""/>
			     				<section>
			     					<label class="label col col-2"></label>
									<label class="input col col-5">
										<input name="phone" disabled="disabled" style="background-color:  #C8C8C8;"/>
			                		</label>
		              				<button type="button" class="btn btn-danger btn-sm" onclick="deletephone(this);">删除</button>
			              		</section>
			           		</div>
							<form id="phoneConfigForm" action="${springMacroRequestContext.contextPath}/config/queue/saveQueuePhone" method="post" class="smart-form">
							 	<input type="hidden" id="queueId" name="queueId" value="${(id)!''}"/>
							 	<header>添加号码</header>
			                	<fieldset>
									 <section>
				                        <div class="row">
				                            <label class="label col col-2"> 号码： </label>
				                            <label class="input col col-5">
				                            	<input id="phone" name="phone" type="text" class="required digits"/>
				                            </label>
					             			<button type="button" id="addphone" class="btn btn-success btn-sm">添加</button>
				                        </div>
				                    </section>
			                    </fieldset>
			                </form>
			                <div class="smart-form" id="phonedata">
			                	<fieldset>
					      			<#if phoneQueuels??>
						      			<#list phoneQueuels as pq>
						              		<div class="row">
						              			<input type="hidden" name="queuephoneid" value="${pq.id}"/>
							            		<section>
							            			<label class="label col col-2"></label>
													<label class="input col col-5">
														<input name="phone" value="${pq.phone}" disabled="disabled" style="background-color:  #C8C8C8;"/>
							                		</label>
							              			<button type="button" class="btn btn-danger btn-sm" onclick="deletephone(this);">删除</button>
							              		</section>
							           		</div>
						           		</#list>
					           		</#if>
				           		</fieldset>
				           	</div>
						</div>
		            </div>
	            </div>
<!-- 	            </div> -->
<!-- 	            <div class="col-md-3"> -->
<!-- 	            	<select multiple="multiple" class="form-control" style="height: 510px;" id="totaldata"> -->
<!-- 	            	</select> -->
<!-- 	            </div> -->
<!-- 	            </div> -->
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消 </button>
                <button type="button" id="saveButton" class="btn btn-primary">保存 </button>
            </div>
        </div>
    </div>
</div>

<script type="application/javascript">

	var demo2 = $('.demo2').bootstrapDualListbox({
		nonSelectedListLabel: '可选人员',
		selectedListLabel: '已选人员',
		preserveSelectionOnMove: 'moved',
		moveOnSelect: false,
		nonSelectedFilter: '',
		infoText:'<span style="text-align: center">总计:{0}</span>',
	    infoTextEmpty:'无'
	});
	
    $('#saveModel').modal("show");
    
    $('#saveModel').on('hide.bs.modal', function(e){
		$("#saveModel").remove();
	});

    $('#saveButton').click(function () {
        if ($('#agentQueueForm').valid()) {
        	
        	var extens = "";
        	
        	$("#extenUid option:selected").each(function(){
    			//得到选中的值拼接成字符串
    			extens += $(this).val() + ",";
            });
        	
        	$("#extens").val(extens);
        	
            $('#saveButton').attr("disabled", true);
            $('#agentQueueForm').submit();
        }
    });

    $('#agentQueueForm').ajaxForm({
        dataType: "json",
        type: "post",
        success: function (data) {
            $("#saveModel").modal("hide");
            if (data.success) {
//            	$("#toDeployButton").removeAttr("disabled");
//	        	$("#toDeployButton").removeClass("btn-default");
//				$("#toDeployButton").addClass("btn-sm");
//				$("#toDeployButton").addClass("btn-success");
                $.smallBox({
                    title: "操作成功",
                    content: "<i class='fa fa-clock-o'></i> <i>分机已成功保存...</i>",
                    color: "#659265",
                    iconSmall: "fa fa-check fa-2x fadeInRight animated",
                    timeout: 2000
                });
                oTable.fnReloadAjax();
            }
            ;
        },
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function () {
                    $("#agentQueueForm").addClass('submited');
                }
            });
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            if (textStatus == 'error') {
                $("#saveModel").modal("hide");
                $.smallBox({
                    title: "操作失败",
                    content: "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作出现异常...</i>",
                    color: "#C46A69",
                    iconSmall: "fa fa-times fa-2x fadeInRight animated"
                });
            }
        }
    });
    
    function resetinput(){
		$("#phone").val('');
	}
	
	function addLine(id){
		var phone = $('#phone').val();
//     	$("#phoneConfigForm").find('fieldset').append($(".addable").clone().removeClass("addable").removeClass("hidden"));
    	$("#phonedata").find('fieldset').append($(".addable").clone().removeClass("addable").removeClass("hidden"));
    	$("#phonedata input[name='queuephoneid']:last").val(id);
    	$("#phonedata input[name='phone']:last").val(phone);
    }
    
    $('#addphone').on("click",function(){
	    if($('#phoneConfigForm').valid()){
	        $('#phoneConfigForm').submit();
	    }
	});
    
    $('#phoneConfigForm').ajaxForm({
	    dataType:"json",
	    success: function(data) {
	        if(data.success){
	            addLine(data.id);
	            resetinput();
	        }
	    },
	    submitHandler : function(form) {
	        $(form).ajaxSubmit({
	            success : function() {
	                $("#phoneConfigForm").addClass('submited');
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
	        }
	    }
	});
    
    function deletephone(data){
		var queuePhoneId = $(data).parent().siblings("input");
		$.post(getContext() + "/config/queue/deletephone",{queuePhoneId:$(queuePhoneId).val()},function(data){
			if(data.success){
	            $.smallBox({
	                title : "操作成功",
	                content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
	                color : "#659265",
	                iconSmall : "fa fa-check fa-2x fadeInRight animated",
	                timeout : 2000
	            });
	            $("input[value='"+data.id+"']").parent().remove();
	        }else{
	            $.smallBox({
	                title : "操作失败",
	                content : "<i class='fa fa-clock-o'></i> <i>删除失败</i>",
	                color : "#C46A69",
	                iconSmall : "fa fa-times fa-2x fadeInRight animated",
	                timeout : 2000
	            });
	        }
		},"json");
	}

</script>
