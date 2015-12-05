<link rel="stylesheet" type="text/css" media="screen" href="${s}/public/css/config/otherconfig.css">
	<button type="button" id="msubmit" class="btn btn-primary" onclick="savecfg();"style="margin-bottom:5px">
		保存
	</button>
<div class="jarviswidget" id="wid-id-5" data-widget-colorbutton="" data-widget-editbutton="false" data-widget-fullscreenbutton="false" data-widget-custombutton="false" data-widget-sortable="false">
	<header>
		<h2>高级选项</h2>
	</header>
	<div style=" border-right-style: hidden; border-left-style: hidden;">
		<div class="widget-body">

			<div class="tabs-left">
				<ul class="nav nav-tabs tabs-left" id="demo-pill-nav">
					<li class="active">
						<a href="#tab-r1" data-toggle="tab" id="sysconfig"> 系统功能设置 </a>
					</li>
					<li>
						<a href="#tab-r2" data-toggle="tab" id="dataconfig"> 系统数据设置 </a>
					</li>
					<li>
						<a href="#tab-r3" data-toggle="tab" id="intentype">  意向客户类型 </a>
					</li>
					<li>
						<a href="#tab-r4" data-toggle="tab" id="cstmtype">  成交客户类型 </a>
					</li>
					<li>
						<a href="#tab-r5" data-toggle="tab" id="agentbusy"> 默认置忙原因 </a>
					</li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="tab-r1">
						<section id="widget-grid">
			                <div class="row">
			                    <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
									<div class="smart-form">
				                        <div class="row">
											<label class="label row" style="padding-left: 30px;">
												<strong style="font-size: small;">计费功能设置</strong>
											</label>
					                        <label class="label col col-4">开启：选择开启，通话时会根据费率设置进行计费</br>关闭：选择关闭，通话时不会进行计费</label>
											<div class="label col col-8">
					                        	<span class="col">
													<label class="onoffswitch">
														<input type="checkbox"  id="sys.billing" name="sys.billing" class="onoffswitch-checkbox" value="true" <#if (!(data['sys.billing'].sysVal)??) || (data['sys.billing'].sysVal)=="true">checked="checked" </#if>></input>
														<label class="onoffswitch-label" for="sys.billing"> 
															<span class="onoffswitch-inner" data-swchon-text="开启" data-swchoff-text="关闭"></span> 
															<span class="onoffswitch-switch"></span> 
														</label> 
													</label>
												</span>
					                        </div>
					                    </div>
					                    <!--  style="background-color: #FAFAFA;" -->
					                    <div class="row" style="background-color: #FAFAFA;padding-top: 13px;">
					                    	<label class="label row" style="padding-left: 30px;">
					                    		<strong style="font-size: small;">号码隐藏设置</strong>
					                    	</label>
					                        <label class="label col col-4">
					                        	开启：选择开启，班长和坐席权限将无法看到具体电话号码</br>关闭：选择关闭，所有人都可以看到具体电话号码
					                        </label>
											<div class="label col col-8" >
					                        	<span class="col col-8">
													<label class="onoffswitch">
														<input type="checkbox" id="hiddenPhoneNumber" name="hiddenPhoneNumber" value="true" class="onoffswitch-checkbox" <#if (!(data['hiddenPhoneNumber'].sysVal)??) || (data['hiddenPhoneNumber'].sysVal)=="true">checked="checked" </#if>></input>
														<label class="onoffswitch-label" for="hiddenPhoneNumber"> <span class="onoffswitch-inner" data-swchon-text="开启" data-swchoff-text="关闭"></span> 
															<span class="onoffswitch-switch"></span> 
														</label> 
													</label>
												</span>
					                        </div>
					                    </div>
					                    
					                    <div class="row" style="padding-top: 13px;">
					                    	<label class="label row" style="padding-left: 30px;">
					                    		<strong style="font-size: small;">自动置忙设置</strong>
					                    	</label>
					                        <label class="label col col-4">
					                        	开启：选择开启，接通后坐席状态自动置忙</br>关闭：选择关闭，坐席接通后状态不会改变
					                        </label>
											<div class="label col col-8" >
					                        	<span class="col col-8">
													<label class="onoffswitch">
														<input type="checkbox" id="autoBusy" name="autoBusy" value="true" class="onoffswitch-checkbox" <#if (!(data['autoBusy'].sysVal)??) || (data['autoBusy'].sysVal)=="true">checked="checked" </#if>></input>
														<label class="onoffswitch-label" for="autoBusy"> <span class="onoffswitch-inner" data-swchon-text="开启" data-swchoff-text="关闭"></span> 
															<span class="onoffswitch-switch"></span> 
														</label> 
													</label>
												</span>
					                        </div>
					                    </div>
					                    
					                    <div class="row" style="background-color: #FAFAFA;padding-top: 13px;">
					                    	<label class="label row" style="padding-left: 30px;">
					                    		<strong style="font-size: small;">录音设置</strong>
					                    	</label>
					                        <label class="label col col-4">
					                        	设置需要录音的时机
					                        </label>
											<div class="label col col-8">
												<label class="input col col-md-12"  style="display: flex;" id="sys.recording.config">
													<label class="radio col-2" >
														<input type="radio" name="sys.recording.config" value="all" <#if (!(data['sys.recording.config'].sysVal)??) || (data['sys.recording.config'].sysVal)=="all">checked="checked"</#if>>
													<i></i>全程录音</label>
													<label class="col col-2"></label>
													<label class="radio col-2">
														<input type="radio" name="sys.recording.config" value="two" <#if (data['sys.recording.config'].sysVal)=="two">checked="checked"</#if>>
													<i></i>双方接通录音</label>
													<label class="col col-2"></label>
													<label class="radio col-2">
														<input type="radio" name="sys.recording.config" value="no" <#if (data['sys.recording.config'].sysVal)=="no">checked="checked"</#if>>
													<i></i>不进行录音</label>
												</label>
											</div>
										</div>
					                    <div class="row" style="padding-top: 13px;">
					                    	<label class="label row" style="padding-left: 30px;">
					                    		<strong style="font-size: small;">录音播放设置</strong>
					                    	</label>
					                        <label class="label col col-4">
					                        	设置录音播放模式
					                        </label>
											<div class="label col col-8">
												<label class="input col col-md-12"  style="display: flex;" id="sys.recording.play.config">
													<label class="radio col-4" >
														<input type="radio" name="sys.recording.play.config" value="open" <#if (!(data['sys.recording.play.config'].sysVal)??) || (data['sys.recording.play.config'].sysVal)=="open">checked="checked"</#if>>
													<i></i>打开立即播放</label>
													<label class="radio col-4">
														<input type="radio" name="sys.recording.play.config" value="click" <#if (data['sys.recording.play.config'].sysVal)=="click">checked="checked"</#if>>
													<i></i>点击后播放</label>
												</label>
											</div>
										</div>
										<!-- <div class="row" style="padding-top: 13px;">
				                    		<label class="label row" style="padding-left: 30px;"><strong style="font-size: small;">弹屏设置</strong></label>
											<label class="label col col-4">设置弹屏的显示方式</label>
											<div class="label col col-8" >
					                        	<label class="col col-md-12" style="display: flex;" id="sys.pop.save.pop.type">
													<label class="radio col-4">
														<input type="radio" name="sys.pop.save.pop.type" value="0" <#if (!(data['sys.pop.save.pop.type'].sysVal)??) || (data['sys.pop.save.pop.type'].sysVal)=="0">checked="checked"</#if>>
														<i></i>弹出新的窗体
													</label>
													<label class="radio col-4">
														<input type="radio" name="sys.pop.save.pop.type" value="1" <#if (data['sys.pop.save.pop.type'].sysVal)=="1">checked="checked"</#if>>
														<i></i>弹出新标签页
													</label>
												</label>
											</div>
										</div> -->
										<div class="row" style="background-color: #FAFAFA;padding-top: 13px;">
			                    			<label class="label" style="padding-left: 15px;"><strong style="font-size: small;">呼入弹屏设置</strong></label>
											<label class="label col col-4">设置呼入弹屏的时机，可选择呼叫时弹屏或者振铃时弹屏</label>
											<div class="label col col-8">
												<label class="col col-md-12" style="display: flex;" id="sys.pop.timing">
													<label class="radio col-4">
														<input type="radio" name="sys.pop.timing" value="ringing" <#if (!(data['sys.pop.timing'].sysVal)??) || (data['sys.pop.timing'].sysVal)=="ringing">checked="checked"</#if>>
														<i></i>振铃时弹屏
													</label>
													<label class="radio col-4">
														<input type="radio" name="sys.pop.timing" value="answer" <#if (data['sys.pop.timing'].sysVal)=="answer">checked="checked"</#if>>
														<i></i>接通时弹屏
													</label>
												</label>
					                         </div>
					                     </div>
					                     
					                     <div class="row" style="padding-top: 13px;">
				                    		<label class="label" style="padding-left: 15px;"><strong style="font-size: small;">呼叫设置</strong></label>
											<label class="label col col-4">设置分机和坐席未绑定时是否允许呼叫</label>
											<div class="label col col-8" >
					                        	<label class="col col-md-12" style="display: flex;">
													<label class="radio col-4">
														<input type="radio" name="sys.call.bindexten" value="0" <#if (data['sys.call.bindexten'].sysVal)=="0">checked="checked"</#if>>
														<i></i>不允许
													</label>
													<label class="radio col-4">
														<input type="radio" name="sys.call.bindexten" value="1" <#if (data['sys.call.bindexten'].sysVal)=="1">checked="checked"</#if>>
														<i></i>允许呼叫
													</label>
												</label>
											</div>
										</div>
										
										<!-- <div class="row" style="background-color: #FAFAFA;padding-top: 13px;">
			                    		<label class="label" style="padding-left: 15px;"><strong style="font-size: small;">接通后关闭弹屏设置</strong></label>
			                    		<label class="label col col-4">保存到共享池：如果通话结束后直接关闭弹屏，未保存详情，默认将数据保存到"共享池"</br>保存到原处：如果通话结束后直接关闭弹屏，将不会移动号码的位置</label>
										<div class="label col col-8" >
				                        	<label class="col col-8" style="display: flex;">
												<label class="radio col-4">
													<input type="radio" name="sys.call.datasave" value="share" <#if (data['sys.call.datasave'].sysVal)=="share">checked="checked"</#if>>
													<i></i>保存到共享池
												</label>
												<label class="radio col-4">
													<input type="radio" name="sys.call.datasave" value="task" <#if (data['sys.call.datasave'].sysVal)=="task">checked="checked"</#if>>
													<i></i>保存到原处
												</label>
											</label>
										</div>
									</div> -->
					                     
					                     
					                    <div class="row" style="background-color: #FAFAFA;padding-top: 13px;">
			                    			 <label class="label row" style="padding-left: 30px;"><strong style="font-size: small;">公司信息设置</strong></label>
					                         <label class="label col col-4">设置公司信息，可以在首页登录框下方进行显示</label>
											 <div class="label col col-8" >
												<label class="col col-12" style="display: flex;" >
												 	<label class="input col-11" id="companyinfo">
												 		<input placeholder="公司名称" name="companyname" value="${(data['companyname'].sysVal)!''}" />
												 	</label>
												 	<label class="col col-1"></label>
													<label class="input col-11">
						                            	<input placeholder="公司网址" name="companyadd" value="${(data['companyadd'].sysVal)!''}" />
						                            </label>
						                            <label class="col col-1"></label>
													<label class="input col-11">
						                            	<input placeholder="公司电话" name="companyphone" value="${(data['companyphone'].sysVal)!''}" />
						                            </label>
					                            </label>
											</div>
										 </div>
				                    </div>
				                </article>  
				            </div>    
						</section>
			        </div>
	                <div class="tab-pane" id="tab-r2">
				                <section id="widget-body no-padding">
					                <div class="row">
					                    <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
											<div class="smart-form">
												<div class="row" style="padding-top: 13px;">
													<label class="label row" style="padding-left: 30px;"><strong style="font-size: small;">总任务上限设置</strong></label>
							                        <label class="label col col-4">设置全局中每个用户任务上限</label>
												 	<div class="label col col-6">
														<label class="input col col-5" >
															<i class="icon-append">条</i>
							                            	<input id="sys.data.getDataTotalLimit" name="sys.data.getDataTotalLimit" value="${(data['sys.data.getDataTotalLimit'].sysVal)!''}"  class="required digits" min="1"/>
							                            </label>
							                        </div>
							                    </div>
							                    <div class="row" style="background-color: #FAFAFA;padding-top: 13px;">
													<label class="label row" style="padding-left: 30px;"><strong style="font-size: small;">总意向上限设置</strong></label>
							                        <label class="label col col-4">设置全局中每个用户的总意向客户上限</label>
												 	<div class="label col col-6">
														<label class="input col col-5" >
															<i class="icon-append">条</i>
							                            	<input id="sys.data.getIntentTotalLimit" name="sys.data.getIntentTotalLimit" value="${(data['sys.data.getIntentTotalLimit'].sysVal)!''}"  class="required" min="1"/>
							                            </label>
							                        </div>
							                    </div>
							                    <div class="row" style="padding-top: 13px;">
							                        <label class="label row" style="padding-left: 30px;"><strong style="font-size: small;">单日上限设置</strong></label>
							                        <label class="label col col-4">设置全局中每个用户的单日获取任务上限</label>
												 	<div class="label col col-6">
														<label class="input col col-5">
															<i class="icon-append">条</i>
							                            	<input id="sys.data.getDataDayLimit" name="sys.data.getDataDayLimit" value="${(data['sys.data.getDataDayLimit'].sysVal)!''}"  class="required" min="1" onblur="getChecked(this)"/>
							                            </label>
							                        </div>
							                    </div>
							                    <div class="row" style="background-color: #FAFAFA;padding-top: 13px;">
							                        <label class="label row" style="padding-left: 30px;"><strong style="font-size: small;">单次上限设置</strong></label>
													<label class="label col col-4">设置单次获取任务上限</label>
												 	<div class="label col col-6" style="">
														<label class="input col col-5">
															<i class="icon-append">条</i>
							                            	<input id="sys.data.getDataSingleLimit" name="sys.data.getDataSingleLimit" value="${(data['sys.data.getDataSingleLimit'].sysVal)!''}" class="required digits" onblur="getChecked(this)"/>
							                            </label>
							                        </div>
							                    </div>
							                    <div class="row" style="padding-top: 13px;">
							                        <label class="label row" style="padding-left: 30px;"><strong style="font-size: small;">共享池单日获取上限设置</strong></label>
							                        <label class="label col col-4">设置每个人单日从共享池获取数据的上限值</label>
												 	<div class="label col col-6">
														<label class="input col col-5">
															<i class="icon-append">条</i>
							                            	<input id="sys.globalshare.getDataNum" name="sys.globalshare.getDataNum" value="${(data['sys.globalshare.getDataNum'].sysVal)!''}"  class="required" min="1"/>
							                            </label>
							                        </div>
							                    </div>
							                    <div class="row" style="background-color: #FAFAFA;padding-top: 13px;">
							                        <label class="label row" style="padding-left: 30px;"><strong style="font-size: small;">删除共享池标记时长</strong></label>
							                        <label class="label col col-4">设置当用户从共享池获取数据后重新放入共享池的时候打的标记的删除时间</label>
												 	<div class="label col col-6">
														<label class="input col col-5" >
															<i class="icon-append">天</i>
							                            	<input id="sys.globalshare.deleteFlag" name="sys.globalshare.deleteFlag" value="${(data['sys.globalshare.deleteFlag'].sysVal)!''}"  class="required" min="1"/>
							                            </label>
							                        </div>
							                    </div>
							                    <div class="row" style="padding-top: 13px;">
							                    	<label class="label row" style="padding-left: 30px;">
							                    		<strong style="font-size: small;">自动冷冻设置</strong>
							                    	</label>
							                        <label class="label col col-4">
							                        	开启：设置天数和次数，表示 "x" 天内被 "y" 次放入共享池的数据将自动冷冻</br>关闭：不会开启自动冷冻功能
							                        </label>
													<div class="label col col-8" >
							                        	<span class="col col-8" style="margin-top: 10px;">
															<label class="onoffswitch">
																<input type="checkbox" id="autoFrozen" name="autoFrozen" value="true" class="onoffswitch-checkbox" <#if (!(data['autoFrozen'].sysVal)??) || (data['autoFrozen'].sysVal?split('-')[0])!="false">checked="checked" </#if>></input>
																<label class="onoffswitch-label" for="autoFrozen"> <span class="onoffswitch-inner" data-swchon-text="开启" data-swchoff-text="关闭"></span> 
																	<span class="onoffswitch-switch"></span> 
																</label> 
															</label>
															<label class="col-1"></label>
															<label class="lable col-4 hidden autofro" style="padding-left: inherit;">
																	<input type="text" style="width: 30%;" id="day" /> 
																	天 
																	<input type="text" style="width: 30%;" id="count" /> 
																	次
															</label>
														</span>
							                        </div>
							                    </div>
						                    </div>
						                </article>
					                </div>
					            </section>    
				            </div>
					<div class="tab-pane" id="tab-r3">
								<div class="row addable hidden">
									<input type="hidden" name="intentUid" value=""/>
				     				<section class="col col-2">
										<label class="input">
											<input name="intentName" />
				                		</label>
				              		</section>
				            		<section class="col col-3">
										<label class="input">
											<input name="intentInfo" />
				                		</label>
				              		</section>
				              		<section class="col col-1">
			              				<button type="button" class="btn btn-danger btn-sm" onclick="deleteint(this);">删除</button>
				              		</section>
				           		</div>
								 <form id="messageinfoa" action="${springMacroRequestContext.contextPath}/dataintent/save" class="smart-form" method="post">
				                    <fieldset>
					     				<div class="row">
						     				<div class="col col-2">
							        		    <label class="label">类型名称<sup>*</sup></label>
						              		</div>
						            		<div class="col col-3">
							        		    <label class="label">描述</label>
						              		</div>
					              		</div>
					              		<div class="row">
						     				<section class="col col-2">
												<label class="input">
													<input name="intentName" id="intentName"/>
						                		</label>
						              		</section>
						            		<section class="col col-3">
												<label class="input">
													<input name="intentInfo" id="intentInfo"/>
						                		</label>
						              		</section>
						              		<section class="col col">
						             			<button type="button" id="msubmita" class="btn btn-success btn-sm" onclick="">添加</button>
						              		</section>
					              		</div>
					              	</fieldset>
							      </form>
							      	<div class = "smart-form">
							      		<fieldset>
							      			<#list dis as di>
							              		<div class="row">
							              			<input type="hidden" name="intentUid" value="${di.uid}"/>
								     				<section class="col col-2">
														<label class="input">
															<input name="intentName" value="${di.intentName}"/>
								                		</label>
								              		</section>
								            		<section class="col col-3">
														<label class="input">
															<input name="intentInfo" value="${di.intentInfo}"/>
								                		</label>
								              		</section>
								              		<section class="col col-1">
								              			<button type="button" class="btn btn-danger btn-sm" onclick="deleteint(this);">删除</button>
								              		</section>
								           		</div>
							           		</#list>
						           		</fieldset>
						           	</div>
							</div>
					<div class="tab-pane" id="tab-r4">
								<div class="row addpoolable hidden">
									<input type="hidden" name="cstmUid" value=""/>
				     				<section class="col col-2">
										<label class="input">
											<input name="poolName" />
				                		</label>
				              		</section>
				            		<section class="col col-3">
										<label class="input">
											<input name="poolDes" />
				                		</label>
				              		</section>
				              		<section class="col col-1">
			              				<button type="button" class="btn btn-danger btn-sm" onclick="deletecstm(this);">删除</button>
				              		</section>
				           		</div>
								 <form id="cstmpoolform" action="${springMacroRequestContext.contextPath}/cstmpool/save" class="smart-form" method="post">
				                    <fieldset>
					     				<div class="row">
						     				<div class="col col-2">
							        		    <label class="label">类型名称<sup>*</sup></label>
						              		</div>
						            		<div class="col col-3">
							        		    <label class="label">描述</label>
						              		</div>
					              		</div>
					              		<div class="row">
						     				<section class="col col-2">
												<label class="input">
													<input name="poolName" id="poolName"/>
						                		</label>
						              		</section>
						            		<section class="col col-3">
												<label class="input">
													<input name="poolDes" id="poolDes"/>
						                		</label>
						              		</section>
						              		<section class="col col">
						             			<button type="button" id="cstmsubmit" class="btn btn-success btn-sm" onclick="">添加</button>
						              		</section>
					              		</div>
					              	</fieldset>
							      </form>
							      	<div class = "smart-form">
							      		<fieldset>
							      			<#list cps as cp>
							              		<div class="row">
							              			<input type="hidden" name="cstmUid" value="${cp.uid}"/>
								     				<section class="col col-2">
														<label class="input">
															<input name="poolName" value="${cp.poolName}"/>
								                		</label>
								              		</section>
								            		<section class="col col-3">
														<label class="input">
															<input name="poolDes" value="${cp.poolDes}"/>
								                		</label>
								              		</section>
								              		<!--如果是默认客户池，则不显示删除按钮-->
								              		<#if cp.uid != 'de001'>
									              		<section class="col col-1">
									              			<button type="button" class="btn btn-danger btn-sm" onclick="deletecstm(this);">删除</button>
									              		</section>
								              		</#if>
								           		</div>
							           		</#list>
						           		</fieldset>
						           	</div>
							</div>
	                <div class="tab-pane" id="tab-r5">
								<section id="widget-grid">
								    <div class="row">
								        <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
								            <br/>
								                <div>
								                    <div class="widget-body no-padding">
								                        <div class="widget-body-toolbar hidden" > </div>
								                        <form action="${springMacroRequestContext.contextPath}/otherconfig/saveagentbusy" method="post" class="smart-form" id="coreForm" style="padding-top: 15px">
										
															<input type="hidden" id="id" name="id" value="${id!''}"  />
															<input type="hidden" id="name" name="name" value="${name!''}"  />						
															<section>
																<div class="row">
																	<label class="label col col-2" style="margin-left:20px;"> 置忙原因1 </label> 
																	<div class="col col-2">
																		<label class="input">
																			<input id="name_1" name="name_1" onfocus="onfocus" value="${(name_1)!''}"  />
																		</label>
										                            </div>
																	<label class="checkbox col col-6">
																		<input  type="checkbox" name="state_1" id="state_1"  <#if (state_1)??><#if (state_1)?number==1>checked="checked" value="1"<#else> value="0" </#if></#if>  /><i onclick="state_1()" style="left: 15px"></i>
																	</label>
																</div>
															</section>
															
															<section>
																<div class="row">
																	<label class="label col col-2" style="margin-left:20px;"> 置忙原因2 </label> 
																	<div class="col col-2">
																		<label class="input">
																			<input id="name_2" name="name_2" onfocus="onfocus" value="${(name_2)!''}"  />
																		</label>
																	</div>
																	<label class="checkbox col col-6">
																		<input  type="checkbox" name="state_2" id="state_2"  <#if (state_2)??><#if (state_2)?number==1>checked="checked" value="1"<#else> value="0" </#if></#if>   /><i onclick="state_2()" style="left: 15px"></i>
																	</label>
																</div>
															</section>
															
															<section>
																<div class="row">
																	<label class="label col col-2" style="margin-left:20px;"> 置忙原因3 </label>
																	<div class="col col-2"> 
																		<label class="input">
																			<input id="name_3" name="name_3" onfocus="onfocus" value="${(name_3)!''}"  />
																		</label>
																	</div>
																	<label class="checkbox col col-6">
																		<input  type="checkbox" name="state_3" id="state_3"  <#if (state_3)??><#if (state_3)?number==1>checked="checked" value="1"<#else> value="0" </#if></#if>   /><i onclick="state_3()" style="left: 15px"></i>
																	</label>
																</div>
															</section>
															
															<section>
																<div class="row">
																	<label class="label col col-2" style="margin-left:20px;"> 置忙原因4 </label> 
																	<div class="col col-2">
																		<label class="input">
																			<input id="name_4" name="name_4" onfocus="onfocus" value="${(name_4)!''}"  />
																		</label>
																	</div>
																	<label class="checkbox col col-6">
																		<input  type="checkbox" name="state_4" id="state_4"  <#if (state_4)??><#if (state_4)?number==1>checked="checked" value="1"<#else> value="0" </#if></#if>  /><i onclick="state_4()"  style="left: 15px"></i>
																	</label>
																</div>
															</section>
															
															<section>
																<div class="row">
																	<label class="label col col-2" style="margin-left:20px;"> 置忙原因5 </label> 
																	<div class="col col-2">
																		<label class="input">
																			<input id="name_5" name="name_5" onfocus="onfocus" value="${(name_5)!''}"  />
																		</label>
																	</div>
																	<label class="checkbox col col-6">
																		<input  type="checkbox" name="state_5" id="state_5"  <#if (state_5)??><#if (state_5)?number==1>checked="checked" value="1"<#else> value="0" </#if></#if>   /><i onclick="state_5()" style="left: 15px"></i>
																	</label>
																</div>
															</section>
															
														<section>
																<div class="row">
																	<label class="label col col-2" style="margin-left:20px;"> 置忙原因6 </label> 
																	<div class="col col-2">
																		<label class="input">
																			<input id="name_6" name="name_6" onfocus="onfocus" value="${(name_6)!''}"  />
																		</label>
																	</div>
																	<label class="checkbox col col-6">
																		<input  type="checkbox" name="state_6" id="state_6"  <#if (state_6)??><#if (state_6)?number==1>checked="checked" value="1" <#else> value="0" </#if></#if>  /><i onclick="state_6()"  style="left: 15px"></i>
																	</label>
																</div>
															</section>
															
<!--														<section>
																<div class="row">
																	<label class="label col col-1" style="margin-left:20px;"> 置忙原因7 </label> 
																	<div class="col col-2">
																		<label class="input">
																			<input id="name_7" name="name_7" onfocus="onfocus" value="${(name_7)!''}"  />
																		</label>
																	</div>
																	<label class="checkbox col col-6">
																		<input  type="checkbox" name="state_7" id="state_7"  <#if (state_7)??><#if (state_7)?number==1>checked="checked" value="1"<#else> value="0" </#if></#if>  /><i onclick="state_7()"   style="left: 15px"></i>
																	</label>
																</div>
															</section>
														
														<section>
																<div class="row">
																	<label class="label col col-1" style="margin-left:20px;"> 置忙原因8 </label> 
																	<div class="col col-2">
																		<label class="input">
																			<input id="name_8" name="name_8" onfocus="onfocus" value="${(name_8)!''}"  />
																		</label>
																	</div>
																	<label class="checkbox col col-6">
																		<input  type="checkbox" name="state_8" id="state_8"  <#if (state_8)??><#if (state_8)?number==1>checked="checked" value="1"<#else> value="0" </#if></#if>  /><i onclick="state_8()"  style="left: 15px"></i>
																	</label>
																</div>
															</section>
															
															<section>
																<div class="row">
																	<label class="label col col-1" style="margin-left:20px;"> 置忙原因9 </label> 
																	<div class="col col-2">
																		<label class="input">
																			<input id="name_9" name="name_9" onfocus="onfocus" value="${(name_9)!''}"  />
																		</label>
																	</div>
																	<label class="checkbox col col-6">
																		<input  type="checkbox" name="state_9" id="state_9"  <#if (state_9)??><#if (state_9)?number==1>checked="checked" value="1"<#else> value="0" </#if></#if>  /><i onclick="state_9()" style="left: 15px"></i>
																	</label>
																</div>
															</section>
															
															<section>
																<div class="row">
																	<label class="label col col-1" style="margin-left:20px;"> 置忙原因10 </label> 
																	<div class="col col-2">
																		<label class="input">
																			<input id="name_10" name="name_10" onfocus="onfocus" value="${(name_10)!''}"  />
																		</label>
																	</div>
																	<label class="checkbox col col-6" >
																		<input  type="checkbox" name="state_10" id="state_10"  <#if (state_10)??><#if (state_10)?number==1>checked="checked" value="1"<#else>  value="0" </#if></#if>   /><i onclick="state_10()" style="left: 15px"></i>
																	</label>
																</div>
															</section>                    -->
														</form>
													</div>
													<div class="modal-footer">
													</div>
								                 </div>
										</article>
						            </div>
						    </section>
						</div>    
	            </div>
			</div>
        </div>
	</div>
</div>
	<script type="text/javascript">
	
		/* 检查是否超过了上限 */
		function getChecked(obj){
			
			var direct = "";
			if(valid(obj)){
				if(obj.name.indexOf("Day") > 0){
					direct = "day";
				}
				$.post(getContext()+'userManage/getCheck',{direct:direct,num:obj.value},function(d){
					if(!d.success){
						alert(d.message);
					}
				},"json");
				
			}
		}

		function valid(obj) {
			var reg = new RegExp("^[0-9]*$");
			if (!reg.test(obj.value)) {
				alert("请输入数字!");
				return false;
			}
			if (!/^[0-9]*$/.test(obj.value)) {
				alert("请输入数字!");
				return false;
			}
			return true;
		}

		/*意向类型输入框重置*/
		function resetinput() {
			$("#intentName").val('');
			$("#intentInfo").val('');
		}
		/*意向类型添加*/
		function addLine(uuid) {
			$("#tab-r3").find('fieldset').eq(1).append(
					$(".addable").clone().removeClass("addable").removeClass(
							"hidden"));
			$("input[name='intentUid']:last").val(uuid);
			$("input[name='intentName']:last").val($('#intentName').val());
			$("input[name='intentInfo']:last").val($('#intentInfo').val());
		}

		/*成交类型输入框重置*/
		function resetcstminput() {
			$("#poolName").val('');
			$("#poolDes").val('');
		}
		/*成交类型添加*/
		function addcstmLine(uuid) {
			$("#tab-r4").find('fieldset').eq(1).append(
					$(".addpoolable").clone().removeClass("addpoolable")
							.removeClass("hidden"));
			$("input[name='cstmUid']:last").val(uuid);
			$("input[name='poolName']:last").val($('#poolName').val());
			$("input[name='poolDes']:last").val($('#poolDes').val());
		}
		function state_1() {
			if ($("#state_1").is(':checked')) {
				$("#state_1").val("0");
			} else {
				$("#state_1").val("1");
			}
		}
		function state_2() {
			if ($("#state_2").is(':checked')) {
				$("#state_2").val("0");
			} else {
				$("#state_2").val("1");
			}
		}
		function state_3() {
			if ($("#state_3").is(':checked')) {
				$("#state_3").val("0");
			} else {
				$("#state_3").val("1");
			}
		}
		function state_4() {
			if ($("#state_4").is(':checked')) {
				$("#state_4").val("0");
			} else {
				$("#state_4").val("1");
			}
		}
		function state_5() {
			if ($("#state_5").is(':checked')) {
				$("#state_5").val("0");
			} else {
				$("#state_5").val("1");
			}
		}
		function state_6() {
			if ($("#state_6").is(':checked')) {
				$("#state_6").val("0");
			} else {
				$("#state_6").val("1");
			}
		}
		//		function state_7(){if($("#state_7").is(':checked')){$("#state_7").val("0");}else{$("#state_7").val("1");}}
		//		function state_8(){if($("#state_8").is(':checked')){$("#state_8").val("0");}else{$("#state_8").val("1");}}
		//		function state_9(){if($("#state_9").is(':checked')){$("#state_9").val("0");}else{$("#state_9").val("1");}}
		//		function state_10(){if($("#state_10").is(':checked')){$("#state_10").val("0");}else{$("#state_10").val("1");}}

		$(document)
				.ready(
						function() {

							/** 校验意向名字段  **/
							var validator = $('#messageinfoa')
									.validate(
											{
												rules : {
													intentName : {
														required : true,
														remote : {
															url : "${springMacroRequestContext.contextPath}/dataintent/checkname/intentName",
															type : "post",
															dataType : "json"
														}
													},
												},
												messages : {
													intentName : {
														required : "该字段为必填字段！",
														remote : "该名称已存在"
													},
												},
												//element是验证未通过的当前表单元素,error为错误后的提示信息
												errorPlacement : function(
														error, element) {
													error.insertAfter(element
															.parent());
												}
											});

							/** 校验成交客户类型名称  **/
							var validator = $('#cstmpoolform')
									.validate(
											{
												rules : {
													poolName : {
														required : true,
														remote : {
															type : "post",
															url : getContext()
																	+ 'cstmpool/checkPoolNameRepeat',
															data : {
																uid : function() {
																	return $(
																			'#cstmUid')
																			.val();
																}
															}
														}
													}
												},
												messages : {
													poolName : {
														required : "该字段为必填字段！",
														remote : "该名称已存在"
													}
												},
												//element是验证未通过的当前表单元素,error为错误后的提示信息
												errorPlacement : function(
														error, element) {
													error.insertAfter(element
															.parent());
												}
											});
							/** 校验置忙原因  **/
							var busyvalidator = $('#coreForm').validate({
								rules : {
									name_1 : {
										maxlength : 20
									},
									name_2 : {
										maxlength : 20
									},
									name_3 : {
										maxlength : 20
									},
									name_4 : {
										maxlength : 20
									},
									name_5 : {
										maxlength : 20
									},
									name_6 : {
										maxlength : 20
									}

								},
								errorPlacement : function(error, element) {
									error.insertAfter(element.parent());
								}
							});

							/* 添加意向  */
							$('#msubmita').on("click", function() {
								if ($('#messageinfoa').valid()) {
									$('#messageinfoa').submit();
								}
							});
							//添加意向
							$('#messageinfoa')
									.ajaxForm(
											{
												dataType : "json",
												success : function(data) {
													if (data.success) {
														//			            $.smallBox({
														//			                title : "操作成功",
														//			                content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
														//			                color : "#659265",
														//			                iconSmall : "fa fa-check fa-2x fadeInRight animated",
														//			                timeout : 2000
														//			            });
														addLine(data.uuid);
														resetinput();
													}
												},
												submitHandler : function(form) {
													$(form)
															.ajaxSubmit(
																	{
																		success : function() {
																			$(
																					"#messageinfoa")
																					.addClass(
																							'submited');
																		}
																	});
												},
												error : function(
														XMLHttpRequest,
														textStatus, errorThrown) {
													if (textStatus == 'error') {
														$
																.smallBox({
																	title : "操作失败",
																	content : "<i class='fa fa-clock-o'></i> <i>添加意向操作失败</i>",
																	color : "#C46A69",
																	iconSmall : "fa fa-times fa-2x fadeInRight animated",
																	timeout : 2000
																});
													}
												}
											});
							/* 添加成交客户类型  */
							$('#cstmsubmit').on("click", function() {
								if ($('#cstmpoolform').valid()) {
									$('#cstmpoolform').submit();
								}
							});

							//添加成交客户类型
							$('#cstmpoolform')
									.ajaxForm(
											{
												dataType : "json",
												success : function(data) {
													if (data.success) {
														//			            $.smallBox({
														//			                title : "操作成功",
														//			                content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
														//			                color : "#659265",
														//			                iconSmall : "fa fa-check fa-2x fadeInRight animated",
														//			                timeout : 2000
														//			            });
														addcstmLine(data.uuid);
														resetcstminput();
													}
												},
												submitHandler : function(form) {
													$(form)
															.ajaxSubmit(
																	{
																		success : function() {
																			$(
																					"#cstmpoolform")
																					.addClass(
																							'cstmsubmit');
																		}
																	});
												},
												error : function(
														XMLHttpRequest,
														textStatus, errorThrown) {
													if (textStatus == 'error') {
														$
																.smallBox({
																	title : "操作失败",
																	content : "<i class='fa fa-clock-o'></i> <i>添加成交客户类型失败</i>",
																	color : "#C46A69",
																	iconSmall : "fa fa-times fa-2x fadeInRight animated",
																	timeout : 2000
																});
													}
												}
											});

							var day = '${data["autoFrozen"]["sysVal"]}'
									.split("-")[1];
							var count = '${data["autoFrozen"]["sysVal"]}'
									.split("-")[2];

							//页面初始化时根据自动冷冻设置判断是否需要配置天数和次数
							if ($("#autoFrozen").is(":checked")) {
								$("#day").val(day);
								$("#count").val(count);
								$(".autofro").removeClass("hidden");
							} else {
								$(".autofro").addClass("hidden");
							}
							//自动冷冻设置改变的话需要切换 显示/隐藏      天数+次数输入框
							$("#autoFrozen").change(function() {
								if ($("#autoFrozen").is(":checked")) {
									$(".autofro").removeClass("hidden");
								} else {
									$(".autofro").addClass("hidden");
								}
							});
						});

		/* 页面上方保存按钮单击事件  */
		$('#msubmit').click(function() {
			
			if(Number($("#sys\\.data\\.getDataDayLimit").val()) < Number($("#sys\\.data\\.getDataSingleLimit").val())){
				alert("单次上限应该小于单日上限！");
				return false;
			}
			
			if(!getNumberChecked()){
				alert("请填写正确的数值 ！");
				return;
			}
			
			$('input[type="checkbox"]:checked').each(function() {
						//alert($(this).attr("id")+":"+$(this).val());
// 						console.log($(this).attr("id") + ":" + $(this).val());
					if ($("#" + $(this).attr("id") + "").is(
							":checked")) {
						var number = $(this).attr("id")
								.substring(
										$(this).attr("id").indexOf(
												"_") + 1,
										$(this).attr("id").length);
						var iid = "name_" + number;
						$("#" + iid + "").addClass("required");
					}
				});

			if ($('#coreForm').valid()) {
				$('#coreForm').submit();
			}
		});
	
		$(document).ready(function() {
			//置忙原因提交
			$('#coreForm').ajaxForm({
				dataType : "json",
				success : function(data) {
					if (data.success) {
						$.smallBox({
							title : "操作成功",
							content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
							color : "#659265",
							iconSmall : "fa fa-check fa-2x fadeInRight animated",
							timeout : 2000
						});
					}
					busyvalidator.resetForm();
				},
				submitHandler : function(form) {
					$(form).ajaxSubmit({
						success : function() {
							$("#coreForm").addClass('submited');
						}
					});
				},
				error : function(XMLHttpRequest,textStatus, errorThrown) {
					if (textStatus == 'error') {
						$.smallBox({
							title : "操作失败",
							content : "<i class='fa fa-clock-o'></i> <i>保存置忙原因操作失败</i>",
							color : "#C46A69",
							iconSmall : "fa fa-times fa-2x fadeInRight animated",
							timeout : 2000
						});
					}
				}
			});
		});
		
		/* 数字校验 */
		function getNumberChecked(){
			
			var reg = new RegExp("^[0-9]*$");
			if(!reg.test($("#sys\\.data\\.getDataTotalLimit").val())){
				return false;
			}
			if(!reg.test($("#sys\\.data\\.getDataDayLimit").val())){
				return false;
			}
			if(!reg.test($("#sys\\.data\\.getIntentTotalLimit").val())){
				return false;
			}
			if(!reg.test($("#sys\\.globalshare\\.getDataNum").val())){
				return false;
			}
			if(!reg.test($("#sys\\.globalshare\\.deleteFlag").val())){
				return false;
			}
			if(!reg.test($("#sys\\.data\\.getDataSingleLimit").val())){
				return false;
			}
			
			return true;
		}

		//保存设置,修改意向
		function savecfg() {
			var cfgdata = {};

			//所有设置项在这里加好，然后在根据后台的saveconfig方法进行保存
			cfgdata.sys_billing = $("#sys\\.billing").is(":checked");
			cfgdata.hiddenPhoneNumber = $("#hiddenPhoneNumber").is(":checked");
			cfgdata.autoBusy = $("#autoBusy").is(":checked");
			cfgdata.autoFrozen = $("#autoFrozen").is(":checked") + "-"
					+ $("#day").val() + "-" + $("#count").val();
			cfgdata.sys_recording_config = $(
					"input[name='sys\\.recording\\.config']:checked").val();
			cfgdata.sys_recording_play_config = $(
					"input[name='sys\\.recording\\.play\\.config']:checked")
					.val();
			cfgdata.sys_pop_save_pop_type = $(
					"input[name='sys\\.pop\\.save\\.pop\\.type']:checked")
					.val();
			cfgdata.sys_pop_timing = $(
					"input[name='sys\\.pop\\.timing']:checked").val();
			cfgdata.companyname = $("input[name='companyname']").val();
			cfgdata.companyadd = $("input[name='companyadd']").val();
			cfgdata.companyphone = $("input[name='companyphone']").val();
			cfgdata.sys_globalshare_getDataNum = $(
					"#sys\\.globalshare\\.getDataNum").val();
			cfgdata.sys_globalshare_deleteFlag = $(
					"#sys\\.globalshare\\.deleteFlag").val();
			cfgdata.sys_data_getDataTotalLimit = $(
					"#sys\\.data\\.getDataTotalLimit").val();
			cfgdata.sys_data_getIntentTotalLimit = $(
					"#sys\\.data\\.getIntentTotalLimit").val();
			cfgdata.sys_data_getDataDayLimit = $(
					"#sys\\.data\\.getDataDayLimit").val();
			cfgdata.sys_data_getDataSingleLimit = $(
					"#sys\\.data\\.getDataSingleLimit").val();
			cfgdata.sys_call_bindexten = $(
					"input[name='sys\\.call\\.bindexten']:checked").val();
			cfgdata.sys_call_datasave = $(
					"input[name='sys\\.call\\.datasave']:checked").val();
			$.post(getContext() + 'otherconfig/saveconfig',
							JSON.stringify(cfgdata),
							function(data) {
								if (data.success) {
									//		            $.smallBox({
									//		                title : "操作成功",
									//		                content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
									//		                color : "#659265",
									//		                iconSmall : "fa fa-check fa-2x fadeInRight animated",
									//		                timeout : 2000
									//		            });

									$("input[value='" + data.uuid + "']")
											.parent().closest('div').remove();
								} else {
									$
											.smallBox({
												title : "操作失败",
												content : "<i class='fa fa-clock-o'></i> <i>保存设置操作失败</i>",
												color : "#C46A69",
												iconSmall : "fa fa-times fa-2x fadeInRight animated",
												timeout : 2000
											});
								}
							}, "json");

			//修改意向类型
			var intentid = $("[name='intentUid']");
			var objList = {};
			intentid.each(function(k, v) {
				var temp = {};
				temp.name = $(v).next().find("input").val();
				temp.des = $(v).next().next().find("input").val();
				objList[$(v).val()] = temp;
			})
			$
					.post(
							getContext() + "dataintent/update",
							{
								obj : JSON.stringify(objList)
							},
							function(data) {

								if (data.success) {
									//			            $.smallBox({
									//			                title : "操作成功",
									//			                content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
									//			                color : "#659265",
									//			                iconSmall : "fa fa-check fa-2x fadeInRight animated",
									//			                timeout : 2000
									//			            });
								} else {
									$
											.smallBox({
												title : "操作失败",
												content : "<i class='fa fa-clock-o'></i> <i>更新意向操作失败</i>",
												color : "#C46A69",
												iconSmall : "fa fa-times fa-2x fadeInRight animated",
												timeout : 2000
											});
								}
							}, "json");

			//修改成交客户类型
			var cstmtypeid = $("[name='cstmUid']");
			var objList = {};
			cstmtypeid.each(function(k, v) {
				var temp = {};
				temp.name = $(v).next().find("input").val();
				temp.des = $(v).next().next().find("input").val();
				objList[$(v).val()] = temp;
			})
			$
					.post(
							getContext() + "cstmpool/save",
							{
								obj : JSON.stringify(objList)
							},
							function(data) {

								if (data.success) {
									//			            $.smallBox({
									//			                title : "操作成功",
									//			                content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
									//			                color : "#659265",
									//			                iconSmall : "fa fa-check fa-2x fadeInRight animated",
									//			                timeout : 2000
									//			            });
								} else {
									$
											.smallBox({
												title : "操作失败",
												content : "<i class='fa fa-clock-o'></i> <i>更新成交客户类型失败</i>",
												color : "#C46A69",
												iconSmall : "fa fa-times fa-2x fadeInRight animated",
												timeout : 2000
											});
								}
							}, "json");
		}

		//删除意向
		function deleteint(data) {

			var itentid = $(data).parent().siblings('input');
			$
					.post(
							getContext() + "dataintent/delete",
							{
								uid : $(itentid).val()
							},
							function(data) {
								if (data.success) {
									$
											.smallBox({
												title : "操作成功",
												content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
												color : "#659265",
												iconSmall : "fa fa-check fa-2x fadeInRight animated",
												timeout : 2000
											});

									$("input[value='" + data.uuid + "']")
											.parent().closest('div').remove();
								} else {
									$
											.smallBox({
												title : "操作失败",
												content : "<i class='fa fa-clock-o'></i> <i>删除失败,请检查意向是否被使用</i>",
												color : "#C46A69",
												iconSmall : "fa fa-times fa-2x fadeInRight animated",
												timeout : 2000
											});
								}
							}, "json");
		}
		//删除成交类型
		function deletecstm(data) {

			var cstmtypeid = $(data).parent().siblings('input');
			$
					.post(
							getContext() + "cstmpool/deletepool",
							{
								poolId : $(cstmtypeid).val()
							},
							function(data) {
								if (data.success) {
									$
											.smallBox({
												title : "操作成功",
												content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
												color : "#659265",
												iconSmall : "fa fa-check fa-2x fadeInRight animated",
												timeout : 2000
											});
									$("input[value='" + data.uuid + "']")
											.parent().closest('div').remove();
								} else {
									$
											.smallBox({
												title : "操作失败",
												content : "<i class='fa fa-clock-o'></i> <i>删除失败,请检查客户池里面是否存在用户</i>",
												color : "#C46A69",
												iconSmall : "fa fa-times fa-2x fadeInRight animated",
												timeout : 2000
											});
								}
							}, "json");
		}
	</script>