
<style>

	#sysConfigForm section div label.label.col.col-2{
		
		text-align: right;
		padding-right: 40px;
	}

</style>

<div id="sysConfigContent" style="">
	<section id="widget-grid-sysconfig" class="">
				<div class="jarviswidget jarviswidget-color-darken" id="wid-id-0" data-widget-editbutton="false">
					<header>
						<span class="widget-icon"><i class="fa fa-table"></i></span>
						<h2>功能设置</h2>
					</header>
					<div>
						<div class="widget-body no-padding">
							<div class="widget-body-toolbar" >
								<div class="smart-form">
											<div class="row">
												<label class="label col"><strong style="font-size: larger;">功能设置</strong></label>
											</div>
										
											<div class="row" style="background-color: white;">
											 	<label class="label col col-4">设置坐席自动获取的数据上限</label>
											 	<div class="label col col-8">
	                                                <label class="label col col-2">获取数据条数</label>
													<label class="input col col-2" title="sys.data.getDataNumber">
						                            	<input id="sys.data.getDataNumber" name="sys.data.getDataNumber" value="${(data['sys.data.getDataNumber'].sysVal)!''}" class="required" />
						                            </label>
						                            <label class="col col-5"></label>
						                            <div class="col col-2">
						                            	<button class="btn btn-primary" onclick="savecfg(this)" style="margin-left:100%;padding:7px;padding-bottom:5px;padding-left:10px;padding-right:10px"> 保  存  </button>
						                            </div>
					                            </div>
					                        </div>
										
											<!--<div class="row">
												<label class="label col col-4">设置呼叫弹窗的时机，可选择呼叫时弹窗或者振铃时弹窗</label>
												<div class="label col col-8">  
													<label class="label col col-2">呼叫弹窗时机</label>
													<label class="select col col-2" title="sys.pop.timing">
						                            	<select name="sys.pop.timing" id="sys.pop.timing">
														 	<option value="ringing" <#if (!(data['sys.pop.timing'].sysVal)??) || (data['sys.pop.timing'].sysVal)=="ringing">selected="selected"</#if>>振铃时</option>
														 	<option value="answer" <#if (data['sys.pop.timing'].sysVal)=="answer">selected="selected"</#if>>接通时</option>
													 	</select>
						                            </label>
						                            <label class="col col-5"></label>
						                            <div class="col col-2">
						                            	<button class="btn btn-primary" onclick="savecfg(this)" style="margin-left:100%;padding:7px;padding-bottom:5px;padding-left:10px;padding-right:10px"> 保  存  </button>
						                            </div>
						                         </div>
						                    </div>-->
						                    
											<div class="row" style="background-color: white;">
												<label class="label col col-4">设置是否开启计费功能</label>
												<div class="label col col-8">
	                                                <label class="label col col-2">计费功能</label>
					                            	<span class="col col-8">
														<label class="onoffswitch" title="sys.billing">
															<input type="checkbox"  id="sys.billing" name="sys.billing" class="onoffswitch-checkbox" value="true" <#if (!(data['sys.billing'].sysVal)??) || (data['sys.billing'].sysVal)=="true">checked="checked" </#if>></input>
															<label class="onoffswitch-label" for="sys.billing"> 
																<span class="onoffswitch-inner" data-swchon-text="开启" data-swchoff-text="关闭"></span> 
																<span class="onoffswitch-switch"></span> 
															</label> 
														</label>
													</span>
						                            <div class="col col-1">
						                            	<button class="btn btn-primary" onclick="savecfg(this)" style="margin-left:100%;padding:7px;padding-bottom:5px;padding-left:10px;padding-right:10px"> 保  存  </button>
						                            </div>
						                        </div>
						                    </div>
						                    
											<div class="row">
												<label class="label col col-4">设置各个页面需要显示电话号码位置的显示方式</label>
												<div class="label col col-8">
	                                                <label class="label col col-2">号码隐藏</label>
					                            	<span class="col col-8">
														<label class="onoffswitch" title="hiddenPhoneNumber">
															<input type="checkbox" id="hiddenPhoneNumber" name="hiddenPhoneNumber" value="true" class="onoffswitch-checkbox" <#if (!(data['hiddenPhoneNumber'].sysVal)??) || (data['hiddenPhoneNumber'].sysVal)=="true">checked="checked" </#if>></input>
															<label class="onoffswitch-label" for="hiddenPhoneNumber"> <span class="onoffswitch-inner" data-swchon-text="开启" data-swchoff-text="关闭"></span> 
																<span class="onoffswitch-switch"></span> 
															</label> 
														</label>
													</span>
						                            <div class="col col-1">
						                            	<button class="btn btn-primary" onclick="savecfg(this)" style="margin-left:100%;padding:7px;padding-bottom:5px;padding-left:10px;padding-right:10px"> 保  存  </button>
						                            </div>
					                            </div>
					                       </div>
									
											<div class="row" style="background-color: white;">
												<label class="label col col-4">设置数据导入时是否进行去重操作</label>
                                                <div class="label col col-8">
													<label class="label col col-2">批次间去重</label>
					                            	<span class="col col-8">
														<label class="onoffswitch" title="sys.data.distinct">
															<input type="checkbox" id="sys.data.distinct" name="sys.data.distinct" class="onoffswitch-checkbox" value="true" <#if (!(data['sys.data.distinct'].sysVal)??) || (data['sys.data.distinct'].sysVal)=="true">checked="checked" </#if>></input>
															<label class="onoffswitch-label" for="sys.data.distinct"> <span class="onoffswitch-inner" data-swchon-text="开启" data-swchoff-text="关闭"></span> 
																<span class="onoffswitch-switch"></span> 
															</label> 
														</label>
													</span>
						                            <div class="col col-1">
						                            	<button class="btn btn-primary" onclick="savecfg(this)" style="margin-left:100%;padding:7px;padding-bottom:5px;padding-left:10px;padding-right:10px"> 保  存  </button>
						                            </div>
					                            </div>
					                       </div>
										
					                       <div class="row">
												<label class="label col col-4">设置录音播放模式</label>
												<div class="label col col-8">
												    <label class="label col col-2">录音播放设置</label>
													<label class="input col col-8"  style="display: flex;" title="sys.recording.play.config">
														<label class="radio col-4" >
															<input type="radio" id="sys.recording.play.config" name="sys.recording.play.config" value="open" <#if (!(data['sys.recording.play.config'].sysVal)??) || (data['sys.recording.play.config'].sysVal)=="open">checked="checked"</#if>>
														<i></i>打开立即播放</label>
														<label class="col col-1"></label>
														<label class="radio col-4">
															<input type="radio" name="sys.recording.play.config" value="click" <#if (data['sys.recording.play.config'].sysVal)=="click">checked="checked"</#if>>
														<i></i>点击后播放</label>
													</label>
												    <div class="col col-1">
												    	<button class="btn btn-primary" onclick="savecfg(this)" style="margin-left:100%;padding:7px;padding-bottom:5px;padding-left:10px;padding-right:10px"> 保  存  </button>
												    </div>
												</div>
											</div>
									
											<!--<div class="row" style="background-color: white;">
												<label class="label col col-4">设置弹屏的显示方式</label>
												<div class="label col col-8" >
	                                                <label class="label col col-2">弹屏方式</label>
					                            	<label class="col col-8" style="display: flex;" title="sys.pop.save.pop.type">
														<label class="radio col-4">
															<input type="radio" id="sys.pop.save.pop.type" name="sys.pop.save.pop.type" value="0" <#if (!(data['sys.pop.save.pop.type'].sysVal)??) || (data['sys.pop.save.pop.type'].sysVal)=="0">checked="checked"</#if>>
															<i></i>弹出新的窗体
														</label>
														<label class="col col-1"></label>
														<label class="radio col-4">
															<input type="radio" name="sys.pop.save.pop.type" value="1" <#if (data['sys.pop.save.pop.type'].sysVal)=="1">checked="checked"</#if>>
															<i></i>弹出新标签页
														</label>
													</label>
						                            <div class="col col-1">
						                            	<button class="btn btn-primary" onclick="savecfg(this)" style="margin-left:100%;padding:7px;padding-bottom:5px;padding-left:10px;padding-right:10px"> 保  存  </button>
						                            </div>
												</div>
											</div>-->
											
										<div class="row">
											<label class="label col col-4">设置公司信息，可以在首页登录框下方进行显示</label>
											<div class="label col col-8" >
												<label class="label col col-2"  style="text-align:left;">公司信息配置</label>
												<label class="col col-8" style="display: flex;" title="companyinfo">
												 	<label class="input col-10" id="companyinfo">
												 		<input placeholder="公司名称" name="companyname" value="${(data['companyname'].sysVal)!''}" />
												 	</label>
												 	<label class="col col-1"></label>
													<label class="input col-10">
						                            	<input placeholder="公司网址" name="companyadd" value="${(data['companyadd'].sysVal)!''}" />
						                            </label>
						                            <label class="col col-1"></label>
													<label class="input col-10">
						                            	<input placeholder="公司电话" name="companyphone" value="${(data['companyphone'].sysVal)!''}" />
						                            </label>
					                            </label>
					                            <div class="col col-1">	
					                            	<button class="btn btn-primary" onclick="savecfg(this)" style="margin-left:100%;padding:7px;padding-bottom:5px;padding-left:10px;padding-right:10px"> 保  存  </button>
					                            </label>
				                            </div>
				                        </div>
				                        
<!-- 										<section> -->
<!-- 											<div class="row"> -->
<!--                                                 <label class="label col col-2">弹窗保存动作</label> -->
<!-- 				                            	<div class="col col-4" style="display: inline-block;"> -->
<!-- 													<label class="radio"> -->
<!-- 														<input type="radio" name="sys.pop.save.action" value="0" <#if (!(data['sys.pop.save.action'].sysVal)??) || (data['sys.pop.save.action'].sysVal)=="0">checked="checked"</#if>> -->
<!-- 														<i></i>保存后关闭 -->
<!-- 													</label> -->
<!-- 													<label class="radio"> -->
<!-- 														<input type="radio" name="sys.pop.save.action" value="1" <#if (data['sys.pop.save.action'].sysVal)=="1">checked="checked"</#if>> -->
<!-- 														<i></i>保存后不做任何操作 -->
<!-- 													</label> -->
<!-- 												</div> -->
<!-- 											</div> -->
<!-- 										</section> -->
									</div>
	<!--				         <button id="saveSysConfig" class="btn btn-primary" > 保  存  </button>   -->
	
								 <div class="row" style="background-color: white;">
								 	<label class="label col col-4">设置共享池获取数据数目</label>
								 	<div class="label col col-8">
                                        <label class="label col col-2">获取数据条数</label>
										<label class="input col col-2" title="sys.globalshare.getDataNum">
			                            	<input id="sys.globalshare.getDataNum" name="sys.globalshare.getDataNum" value="${(data['sys.globalshare.getDataNum'].sysVal)!''}"  class="required" min="1"/>
			                            </label>
			                            <label class="col col-5"></label>
			                            <div class="col col-2">
			                            	<button class="btn btn-primary" onclick="savecfg(this)" style="margin-left:100%;padding:7px;padding-bottom:5px;padding-left:10px;padding-right:10px"> 保  存  </button>
			                            </div>
		                            </div>
		                        </div>
	
							</div>
						</div>
					</div>
		</section>
	</div>
<script type="text/javascript">

	$(document).ready(function(){
		
	});
	
	function savecfg(data){
		var keyinfo = $(data).parent().parent().find("label").eq(1);
		$.ajax({
			 dataType:"json",
			    url:"${springMacroRequestContext.contextPath}/sysconfig/save",
			    type:"post",
			    data:{key:function(){
			    	if($(keyinfo).attr("title")){
			    		return $(keyinfo).attr("title");
			    	}
			    },value:function(){
			    	
						if($(keyinfo).attr("title")=="sys.data.getDataNumber" || $(keyinfo).attr("title")=="sys.globalshare.getDataNum"){
							
							return $(keyinfo).children('input').val();
						}else if($(keyinfo).attr("title")=="sys.pop.timing"){
						
							return $(keyinfo).children('select').val();
			    		}else if($(keyinfo).attr("title")=="sys.recording.play.config"){
							
							return $("input[name='sys\\.recording\\.play\\.config']:checked").val();
						}else if($(keyinfo).attr("title")=="sys.pop.save.pop.type"){
							
							return $("input[name='sys\\.pop\\.save\\.pop\\.type']:checked").val();
						}else if($(keyinfo).attr("title")=="companyinfo"){
							
							return "name:"+$("input[name='companyname']").val()+"add:"+$("input[name='companyadd']").val()+"phone:"+$("input[name='companyphone']").val()+"";
						}else if($(keyinfo).attr("title")=="sys.billing"){
			                if($("input[name='sys\\.billing']").is(":checked")){
			                	return "true";
			                }else{
			                	return "false";
			                }
			               
						}else if($(keyinfo).attr("title")=="sys.data.distinct"){
							
						 	if($("input[name='sys\\.data\\.distinct']").is(":checked")){
				                	return "true";
			                }else{
			                	return "false";
			                }
						}else if($(keyinfo).attr("title")=="hiddenPhoneNumber"){
							
							 if($("input[name='hiddenPhoneNumber']").is(":checked")){
				                	return "true";
				                }else{
				                	return "false";
				                }
						}
					}
			    },
			    success: function(data) {
			        if(data.success){
			            $.smallBox({
			                title : "操作成功",
			                content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
			                color : "#659265",
			                iconSmall : "fa fa-check fa-2x fadeInRight animated",
			                timeout : 2000
			            });
			        }
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
	   
	};

</script>
