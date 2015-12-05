<div class="modal fade" id="queueSaveModel" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
    <div class="modal-dialog" >
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                   		关 闭 
                </button>
                <h4 class="modal-title" id="myModalLabel">技能组管理</h4>
            </div>
            <div class="modal-body">

                <form action="${springMacroRequestContext.contextPath}/config/queue/save" class="smart-form"
                      id="coreForm">

                   	<input type="hidden" id="id" name="id" value="${(id)!''}">
					<input type="hidden" id="extensionId" name="extensionId" value="${(extensionId)!''}">

                    <section>
                        <div class="row">
                            <label class="label col col-3"> 名称</label>
                            <label class="input col col-8">
                            	<input id="name" name="name" onfocus="onfocus" value="${(name)!''}" class="required"/>
                            </label>
                        </div>
                    </section>
                    
                    <section>
						<div class="row">
							<label class="label col col-3">分机号</label>
							<label class="input col col-8">
								 <input id="extension" name="extension" onfocus="onfocus" value="${(extension)!''}" class="required"/>
							</label>
						</div>
					</section>

                    <section>
                        <div class="row">
                            <label class="label col col-3"> 是否静态队列</label>
<!--                             <label class="input col col-8"> -->
<!--                             	<input id="is_static" name="is_static" value="${(is_static)!''}"　class="required"/> -->
<!--                              </label> -->
                             <label class="checkbox col col-6">
								　<input type="checkbox" name="is_static" id="is_static"
                                       <#if (is_static)??><#if (is_static)?number==1>checked="checked" </#if><#else></#if> /><i  style="left: 15px"></i>
							　</label>
                        </div>
                    </section>

					<!-- 修改 -->
					<section>
						<div class="row">
							 <label class="label col col-3">振铃策略 </label>
							 <label class="select col col-8"> 
							 	<select id="strategy" name="strategy" > 
							 		<option value="">请选择</option>
							 		<#list strategySele?keys as key>
									<#if strategy??> 
										<#if key==strategy>
											<option value="${key}" selected="selected">${strategySele[key]}</option>
										<#else>
											<option value="${key}">${strategySele[key]}</option> </#if>
									<#else>
										<option value="${key}">${strategySele[key]}</option> </#if>
									</#list>
							 	
							</select>
							</label>
						</div>
					</section>
                    
                    <section>
						<div class="row">
							 <label class="label col col-3">费率设置 </label>
							 <label class="select col col-8"> 
							 	<select id="rate" name="rate" > 
							 		<option value="" >请选择</option>
							 		<#list allrate as rate>
							 			<#if (thisrate.uid)?exists && thisrate.uid == rate.uid>
							 				<option value="${thisrate.uuid}" selected="selected">${thisrate.billRateName}---${thisrate.rateSdfMoney}元/${thisrate.rateSdfTime}秒</option>
							 			<#elseif rate.billRateType=="SkillGroup">
							 				<option value="${rate.uuid}" >${rate.billRateName}---${rate.rateSdfMoney}元/${rate.rateSdfTime}秒</option>
							 			</#if>
							 		</#list>
							</select>
							</label>
						</div>
					</section>
					
					<section>
                        <div class="row">
                            <label class="label col col-3"> 欢迎音乐</label>
                            <label class="select col col-8">
                            	<select id="welcomeMusic" name="welcomeMusic">
                            		<option value="">请选择</option>
                            		<#if voices??>
	                            		<#list voices as vo>
	                            			<#if welcomeMusic??>
	                            				<#if vo.voice == welcomeMusic>
	                            					<option value="${vo.voice}" selected="selected">${vo.name}</option>
	                            				<#else>
	                            					<option value="${vo.voice}">${vo.name}</option>
	                            				</#if>
	                            			<#else>
	                            				<option value="${vo.voice}">${vo.name}</option>
	                            			</#if>
	                            		</#list>
                            		</#if>
                            	</select>
                           </label>
                        </div>
                    </section>
                    
                    <section>
                        <div class="row">
                            <label class="label col col-3"> 等待音乐</label>
                            <label class="select col col-8">
                            	<select id="mohSound" name="mohSound">
                            		<option value="">请选择</option>
                            		<#if voices??>
	                            		<#list voices as vo>
	                            			<#if mohSound??>
	                            				<#if vo.voice == mohSound>
	                            					<option value="${vo.voice}" selected="selected">${vo.name}</option>
	                            				<#else>
	                            					<option value="${vo.voice}">${vo.name}</option>
	                            				</#if>
	                            			<#else>
	                            				<option value="${vo.voice}">${vo.name}</option>
	                            			</#if>
	                            		</#list>
                            		</#if>
                            	</select>
                           </label>
                        </div>
                    </section>
                    
					<#--<section>-->
                        <#--<div class="row">-->
                            <#--<label class="label col col-2">等待音乐</label>-->
                            <#--<label class="input col col-8">-->
                            	<#--<input id="mohSound" name="mohSound" onfocus="onfocus" value="${(mohSound)!''}"/>-->
                            <#--</label>-->
                        <#--</div>-->
                    <#--</section>-->


                    <section>
                        <div class="row">
                            <label class="label col col-3"> 队列超时时长（秒） </label>
                            <label class="input col col-8"><input id="maxWaitTime" name="maxWaitTime" value="${(maxWaitTime)!'60'}"/></label>
                        </div>
                    </section>

                    <section>
                        <div class="row">
                            <label class="label col col-3"> 坐席超时时长（秒） </label>
                            <label class="input col col-8"><input id="agentWaitTime" name="agentWaitTime" value="${(agentWaitTime)!'10'}"/></label>
                        </div>
                    </section>


                   <section style="display: none;">
                        <div class="row" >
                            <label class="label col col-3"> TimeBaseScore </label>
                            <label class="input col col-8"><input id="timeBaseScore" name="timeBaseScore" value="${(timeBaseScore)!'queue'}"/></label>
                        </div>
                    </section>
                    

                    
                    <section style="display: none;">
                        <div class="row">
                            <label class="label col col-3"> MaxWaitTimeWithNoAgent </label>
                            <label class="input col col-8"><input id="maxWaitTimeWithNoAgent" name="maxWaitTimeWithNoAgent" value="${(maxWaitTimeWithNoAgent)!'0'}"/></label>
                        </div>
                    </section>
                    
                    <section style="display: none;">
                        <div class="row">
                            <label class="label col col-3"> MaxWaitTimeWithNoAgentTimeReached </label>
                            <label class="input col col-8"><input id="maxWaitTimeWithNoAgentTimeReached" name="maxWaitTimeWithNoAgentTimeReached" value="${(maxWaitTimeWithNoAgentTimeReached)!'5'}"/></label>
                        </div>
                    </section>
                    
                    <section style="display: none;">
                        <div class="row">
                            <label class="label col col-3"> TierRulesApply </label>
                            <label class="input col col-8"><input id="tierRulesApply" name="tierRulesApply" value="${(tierRulesApply)!'false'}"/></label>
                        </div>
                    </section>
                    
                    <section style="display: none;">
                        <div class="row">
                            <label class="label col col-3"> TierRuleWaitSecond </label>
                            <label class="input col col-8"><input id="tierRuleWaitSecond" name="tierRuleWaitSecond" value="${(tierRuleWaitSecond)!'300'}"/></label>
                        </div>
                    </section>
                    
                    <section style="display: none;">
                        <div class="row">
                            <label class="label col col-3"> TierRuleWaitMultiplyLevel </label>
                            <label class="input col col-8"><input id="tierRuleWaitMultiplyLevel" name="tierRuleWaitMultiplyLevel" value="${(tierRuleWaitMultiplyLevel)!'true'}"/></label>
                        </div>
                    </section>
                    
                    <section style="display: none;">
                        <div class="row">
                            <label class="label col col-3"> TierRuleNoAgentNoWait</label>
                            <label class="input col col-8"><input id="tierRuleNoAgentNoWait" name="tierRuleNoAgentNoWait" value="${(tierRuleNoAgentNoWait)!'false'}" /></label>
                        </div>
                    </section>
                    
                    <section style="display: none;">
                        <div class="row">
                            <label class="label col col-3"> DiscardAbandonedAfter </label>
                            <label class="input col col-8"><input id="discardAbandonedAfter" name="discardAbandonedAfter" value="${(discardAbandonedAfter)!'60'}"/></label>
                        </div>
                    </section>
                    
                    <section style="display: none;">
                        <div class="row">
                            <label class="label col col-3"> AbandonedResumeAllowed </label>
                            <label class="input col col-8"><input id="abandonedResumeAllowed" name="abandonedResumeAllowed" value="${(abandonedResumeAllowed)!'false'}"/></label>
                        </div>
                    </section>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    取消
                </button>
                <button type="button" id="saveButton" class="btn btn-primary">
                    保存
                </button>
            </div>
            </form>
        </div>
    </div>
</div>

<script type="application/javascript">
$('#strategy').select2({
    allowClear : true,
    width:'100%'
});
$('#welcomeMusic').select2({
    allowClear : true,
    width:'100%'
});
$('#mohSound').select2({
    allowClear : true,
    width:'100%'
});

$('#rate').select2({
    allowClear : true,
    width:'100%'
});
// $('#mohSound').select2({
//     allowClear : true,
//     width:'100%'
// });
$(document).ready(function(){
									
	/** 校验字段  **/
	var validator = $('#coreForm').validate({
	    rules: {
			name : {
				required : true,
				remote : {
					type : 'post',
					url : getContext()+ 'config/queue/isRepeat',
					data : {
						id : function() {
							return $("#queueSaveModel #id").val(); /* 传递用户编号 */
						}
					}
				}
			},
			extension : {
				required : true,
				maxlength : 32,
				remote : {
					type : 'post',
					url : getContext() + 'config/queue/checkExten',
					data : {
						id : function() {
							return $("#queueSaveModel #id").val(); /* 传递用户编号 */
						},
						extension : function() {
							return $("#extension").val();
						}
					}
				}
			},
			strategy : {
				required : true
			}
	    },messages : {
			name : {
				remote : $.format("该Queue名已经存在！")
			},
			extension : {
				remote : $.format("该分机号已存在！")
			}
		},errorPlacement: function (error, element) {
	        error.insertAfter(element.parent());
	    }
	});

	$('#queueSaveModel').modal("show");

	$('#queueSaveModel').on('hide.bs.modal', function(e){
		$("#queueSaveModel").remove();
	});
	
	$('#saveButton').click(function () {
	    if ($('#coreForm').valid()) {
	        $('#saveButton').attr("disabled", true);
	        $('#coreForm').submit();
	    }
	});


	$('#coreForm').ajaxForm({
	    dataType: "json",
	    type: "post",
	    success: function (data) {
	        $("#queueSaveModel").modal("hide");
	        if (data.success) {
	        	$("#toDeployButton").removeAttr("disabled");
	        	$("#toDeployButton").removeClass("btn-default");
				$("#toDeployButton").addClass("btn-sm");
				$("#toDeployButton").addClass("btn-success");
	            $.smallBox({
	                title: "操作成功",
	                content: "<i class='fa fa-clock-o'></i> <i>Queue已成功保存...</i>",
	                color: "#659265",
	                iconSmall: "fa fa-check fa-2x fadeInRight animated",
	                timeout: 2000
	            });
	            oTable.DataTable().ajax.reload(null,false);
	        }
	        ;
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
	            $("#queueSaveModel").modal("hide");
	            $.smallBox({
	                title: "操作失败",
	                content: "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作出现异常...</i>",
	                color: "#C46A69",
	                iconSmall: "fa fa-times fa-2x fadeInRight animated"
	            });
	        }
	    }
	});
	
})


</script>
