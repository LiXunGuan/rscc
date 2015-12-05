<script src="${springMacroRequestContext.contextPath}/assets/js/smartwidgets/jarvis.widget.js"></script>
<script src="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-tags/bootstrap-tagsinput.js"></script>


<style>
#addCstm {
	float: left;
	margin:5px;
	margin-left: 0px;
}

#alterTable {
	float: right;
	margin-right: 1%;
}

#main_cstm .row .label.col.col-1 {
	text-align: right;
	text-justify: inter-ideograph;
}

.select2-hidden-accessible {
	display: none;
}
#movCstm{
	margin: 5px;
	margin-right: 0px;
	
}
#delCstm{
	margin: 5px;
}
.popover.fade.right.in{
	width:15%;
}

.btn.btn-success.dropdown-toggle{
	padding: 2px;
}
label#exportdata.btn.btn-sm.btn-success{
	width: 70px;
	height: 31px;
}
</style>

<div id="main_cstm" style="">
	<div id="tmp_div"></div>
		<section id="widget-grid-cstm" class="">
			<!-- data-toggle="popover" -->
			<#if level?? && level=="agent">
				<#if dataType?? && dataType == "cstm">
					<a class="btn btn-sm btn-primary" id="addCstm"  onclick="batchUpdate()">批量修改拥有者</a>
				</#if>
			<#else>
				<a class="btn btn-sm btn-primary" id="addCstm"  onclick="batchUpdate()">批量修改拥有者</a>
				<label class="btn btn-sm btn-primary" id="movCstm" onclick="moveCstm()">批量移动客户</label>
				<label class="btn btn-sm btn-success" id="exportdata">信息导出</label>
			</#if> 
			<div class="row">
				<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<div class="jarviswidget jarviswidget-color-darken" id="wid-id-maincstm_des1" data-widget-colorbutton="false" data-widget-editbutton="false"
					 data-widget-togglebutton="true" data-widget-deletebutton="true">

						<header>
							<span class="widget-icon"><i class="fa fa-table"></i></span>
							<#if level?? && level=="agent">
							<h2>我的客户</h2>
							<#else>
								<h2>客户信息管理</h2>
							</#if>
						</header>
						<div>
							<div class="jarviswidget-editbox"></div>
							<div class="widget-body">
								<div class="widget-body-toolbar">
									<div class="smart-form">
										<section>
											<div class="row">
												<label class="label col col-1" >客户编号</label>
												<label class="input col col-2">
													<input type="text" name="customer_id" id="customer_id" class="form-control" value="" />
												</label>
												
												<#if XPool??>
													<label class="label col col-1" >电话号码</label>
													<label class="input col col-2">
														<input type="text" name="phone_number" id="phone_number" class="form-control" value="" />
														<input type="hidden" name="pool_id" id="pool_id_s">
													</label>
												<#else>
													
													<label class="label col col-1" >客户类型</label><span></span>
													<div class="form-group col col-2">
														<select name="pool_id" id="pool_id" onchange="getResult();">
															
<!-- 															<option value="">----所有类型----</option> -->
<!-- 															<optgroup label="意向客户"> -->
<!-- 																<option value="0">全部意向客户</option> -->
<!-- 																<#if allIntent??> -->
<!-- 																	<#list allIntent as li> -->
<!-- 																		<option value="${li.uid!''}">${li.intentName!''}</option> -->
<!-- 																	</#list> -->
<!-- 																</#if> -->
<!-- 															</optgroup> -->
															
<!-- 															<optgroup label="成交客户"> -->
																<option value="">全部</option>
																<#if allpools??>
																	<#list allpools as li>
																		<option value="${li.uid!''}">${li.poolName!''}</option>
																	</#list>
																</#if>
<!-- 															</optgroup> -->
														</select>
													</div>
												</#if>
												
												<!-- 如果是我的，默认选中，不可修改 -->
												<#if level?? && level=="agent">
													<label class="label col col-1" >拥有者</label>
													<div class="form-group col col-lg-2">
															<select name="own_id" id="own_id" onchange="getResult();">
																<#if user??>
																	<#list user as u>
																		<#if clickUser?? && clickUser == u.uuid>
																			<option value="${(u.uuid)!''}" selected="selected">${(u.loginName)!''}</option>
																		<#elseif Session["CURRENTUSER"].uuid == u.uuid>
																			<option value="${(u.uuid)!''}" selected="selected">${(u.loginName)!''}</option>
																		</#if>
																	</#list> 
																<#else>
																	<option value="">暂无用户</option>
																</#if>
														</select>
													</div>
												<#else>
													<label class="label col col-1" >拥有者</label>
													<div class="form-group col col-lg-2">
															<select name="own_id" id="own_id" onchange="getResult();">
																<#if user??>
																	<option value="">----请选择----</option>
																		<#list user as u>
																			<#if Session["CURRENTUSER"].uuid == u.uuid>
																				<option value="${(u.uuid)!''}" selected="selected">${(u.loginName)!''}</option>
																			<#else>
																				<option value="${(u.uuid)!''}">${(u.loginName)!''}</option>
																			</#if>
																	</#list> 
																<#else>
																	<option value="">暂无用户</option>
																</#if>
														</select>
													</div>
												</#if>
												
												<label class="btn btn-sm btn-primary" onclick="getResult();">查询</label>
											</div>
										</section>
										<section>
											<div class="row">
												
												<label class="label col col-1" >客户姓名</label><span></span>
												<label class="input col col-2 " >
													<input type="text" name="cstm_name" class="form-control" id="cstm_name" value="" />
												</label>
												
												<label class="label col col-1" >客户标签</label>
												<label class="input col col-2">
													<input type="text" name="customerTags" id="customerTags" class="form-control" value="" />
												</label>

												<label class="label col col-1" >标记时间</label>
												<label class="input col col-2">
													<!-- 用于显示去取值 -->
													<input type="text" name="start_date" id="start_date" class="form-control" value="" />
												</label>
												<script type="text/javascript">
													$(document).ready(function() {
														$("#start_date").daterangepicker({
															format: 'YYYY-MM-DD HH:mm:ss',
															timePicker: true,
															timePicker12Hour: false,
															showDropdowns: true,
										                    locale: {
										                        applyLabel: '确定',
										                        cancelLabel: '清空',
										                        fromLabel: '从',
										                        toLabel: '到',
										                        daysOfWeek: ['日', '一', '二', '三', '四', '五','六'],
										                        monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
										                        firstDay: 1
										                    }
														},function(start, end, label) {
														}).on('cancel.daterangepicker',function(ev, picker){
															$('#start_date').val('');
															getResult();
														}).on('apply.daterangepicker',function(ev, picker){
															getResult();
														});
													});
												</script>
												
												<#if level?? && level=="agent">
												<#else>
													<label class="btn btn-sm btn-success" id="exportdata">信息导出</label>
												</#if>
											</div>
										</section>
										<section>
											<div class="row">
												<label class="label col col-1" >客户描述</label>
												<label class="input col col-2">
													<input type="text" name="customerDes" id="customerDes" class="form-control" value="" />
												</label>
												<#if level?? && level=="agent">
													<#if XPool??>
														<label class="label col col-1" >电话号码</label>
														<label class="input col col-2">
															<input type="text" name="phone_number" id="phone_number" class="form-control" value="" />
															<input type="hidden" name="pool_id" id="pool_id_s">
														</label>
													<#else>
													<label class="label col col-1" >电话号码</label>
													<label class="input col col-2">
														<input type="text" name="phone_number" id="phone_number" class="form-control" value="" />
														<input type="hidden" name="pool_id" id="pool_id_s">
													</label>
													</#if>
												<#else>
													<label class="label col col-1" >电话号码</label>
													<label class="input col col-2">
														<input type="text" name="phone_number" id="phone_number" class="form-control" value="" />
														<input type="hidden" name="pool_id" id="pool_id_s">
													</label>
												</#if>
												<#if level?? && level=="agent">
												<#else>
													<label class="label col col-1" >归属部门</label>
													<div class="form-group col col-lg-2">
														<select name="dataranges" id="dataranges" onchange="getDeptResult();">
															<option value="">全部部门</option>
															<#if dataranges??>
																<#list dataranges as u>
																	<option value="${(u.uuid)!''}">${(u.datarangeName)!''}</option>
																</#list>
															</#if>
														</select>
													</div>
												</#if>
										<!-- 遍历所有可查询的自定义字段 非必须字段 -->
										<#if allMaps??>
											
											<#if level?? && level=="agent">
												<#assign num = 1>
											<#else>
												<#assign num = 3>
											</#if>
											<#list allMaps?keys as columndesign>
											
												<!-- 控制是否可以查询  -->
												<#if allMaps[columndesign].allowSelect=="1" >
												<!-- 如果是坐席。并且存在 level=agent -->
												<#if level?? && level=="agent">
													<#if XPool??>
													<#else>
														<#if num = 2 || (num-2)%3 == 0>
																</div>
															</section>
															<section>
																<div class="row">
														</#if>
													</#if>
												<!-- 如果不是坐席 管理 -->
												<#else>
													<#if num = 0 || num%3 == 0>
															</div>
														</section>
														<section>
															<div class="row">
													</#if>
												</#if>
													<label class="label col col-1" >${allMaps[columndesign].columnName}</label>
													<#if allMaps[columndesign].columnType == "ENUM">
														<#assign enumStr = allMaps[columndesign].characterProperty>
														<div class="form-group col col-lg-2">
															<select name="${columndesign!''}" id="${columndesign!''}" class="form-control" onchange="getResult();">
																<option value="">----请选择----</option>
																<#list enumStr?replace("，",",")?split(",") as a>
																	<option value="${a!''}">${a!''}</option>
																</#list>
															</select>
														</div>
<!-- 												<input type="text" id="${columndesign}"  name="${columndesign}" class="form-control" value=""/> -->
													<#elseif allMaps[columndesign].columnType == "DATE">
														<label class="input col col-2"> 
																<input type="text" name="${columndesign}" id="${columndesign}" onchange="setDefault()" class="form-control" value="" />
<!-- 																<input type="hidden" name="${columndesign}" id="${columndesign}" class="form-control" value="" /> -->
																<script type="text/javascript">
																	$(document).ready(function() {
																		$('#${columndesign}').daterangepicker({
																			format: 'YYYY-MM-DD HH:mm:ss',
																			timePicker: true,
																			timePicker12Hour: false,
																			showDropdowns: true,
														                    locale: {
														                        applyLabel: '确定',
														                        cancelLabel: '清空',
														                        fromLabel: '从',
														                        toLabel: '到',
														                        daysOfWeek: ['日', '一', '二', '三', '四', '五','六'],
														                        monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
														                        firstDay: 1
														                    }
																		},function(start, end, label) {
																			console.log($("#${columndesign}").val());
																		}).on('cancel.daterangepicker',function(ev, picker){
																			$('#${columndesign}').val('');
																			getResult();
																		}).on('apply.daterangepicker',function(ev, picker){
																			getResult();
																		});
																	});
																</script>
														</label>
													<#elseif allMaps[columndesign].columnType == "INT" || allMaps[columndesign].columnType == "FLOAT">
														<label class="input col col-2">
															<input type="hidden" id="${columndesign}" name="${columndesign}">
															<input class="form-control col" style="width: 34%;" id="${columndesign}_1" onchange="changeVal_${columndesign}();" name="${columndesign}_1" value="">
															<lable class="label col">到</lable>
															<input class="form-control col" style="width: 34%;" id="${columndesign}_2" onchange="changeVal_${columndesign}();"  name="${columndesign}_2" value="">
														</label>
														<script type="text/javascript">
															function changeVal_${columndesign}(){
																$("#${columndesign}").val($("#${columndesign}_1").val()+","+$("#${columndesign}_2").val());
															}
														</script>

													<#else>	 
														<label class="input col col-2"> 
															<input type="text" id="${columndesign}"  name="${columndesign}" class="form-control" value=""/>
														</label>
													</#if>
													<#assign num=num+1 />
													</#if>
											</#list>
											</#if>
											</div>
										</section>
										<table id="dt_basic_cstm" class="table table-striped table-bordered table-hover"  data-order='[[ 0, "asc" ]]' width="100%"></table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</article>
			</div>
		</section>
	</div>

<!-- 提交参数 -->
<#macro params listParam> 
	<#if listParam??> 
		<#list listParam as p>
			param.${p} = $("#${p}").val(); 
		</#list> 
	</#if> 
</#macro>


<!-- 提交参数 -->
<#macro listParameter allParameters> 
	<#if allParameters??> 
		<#list allParameters as p>
			url += "&${p}="+$("#${p}").val();
		</#list> 
	</#if> 
</#macro>

<script type="text/javascript">

		var checklist={};
		
		var queryCondition={};
		
		/* 传递参数 */
		function docheckall(){
			checklist={};
		    var url = window.parent.getContext()+'cstm/checkAll';
		    getConditions();
		    $.post(url,queryCondition, function(data){
		        for(var i in data.customers){
	                checklist[data.customers[i]]=true;
		        }
		        docheckallpage();
		    },"json");
		}
		
		
		$("#export").click(function(){
			
		});
		
		/* 全选本页 */
		function docheckallpage(){
			$("[id^='ck']").each(function(){
		        if(!$(this).is(':checked')){
		            $(this).click();
		        }
		    });
		}
		
		function docheck(uid){
			
		    if($("#ck"+uid).is(':checked')){
		        checklist[uid]=true;
		        currentItem = uid;
		    }else{
		        checklist[uid]=false;
		    }
		    addCheckInfo();
		}
		
		function addCheckInfo(){
			var num=0;
			for(var i in checklist) {
				if(checklist[i]==true)
					num++;
			}
		    
			$("#checkInfo").text(num);
		}
		
		
		/* 取消选择所有 */
		function docancelAll(){
		    checklist={};

		    $("[id^='ck']").each(function(){
		        if($(this).is(':checked')){
		            $(this).click();
		        }
		    });
		}
		
		function docancel(){
		    $("[id^='ck']").each(function(){
		        if($(this).is(':checked')){
		            $(this).click();
		        }
		    });
		}
		//点击批量修改拥有者
		function batchUpdate(){
			
			
			var list = [];
			for(var i in checklist) {
				if(checklist[i]==true)
					list.push(i);
			}
			//如果未选择客户
			if(list.length == 0){
				$.bigBox({
					title : "提示",
					content : "请勾选需要进行操作的客户！",
					color : "#C79121",
					icon : "fa fa-bell shake animated",
					timeout : 2000
				});
//				$("#addCstm").popover("hide");
			}else{
				$.post(getContext()+"cstm/owner",{cstmids:list.toString()},function(data){
					$("#tmp_div").append(data);
				});
//				$("#addCstm").popover();
			}
		}
		//批量修改拥有者的弹窗中的修改按钮的点击事件
		function changeOwn(){
			var list = [];
			for(var i in checklist) {
				if(checklist[i]==true)
					list.push(i);
			}
			$.post(getContext() + "cstm/saveBatChange",{ownUser:$("#ownUser").val(),cstmids:list.toString()},function(data){
				if(data.success){
                    $.smallBox({
                        title : "操作成功",
                        content : "<i class='fa fa-clock-o'></i> <i>操作成功...</i>",
                        color : "#659265",
                        iconSmall : "fa fa-check fa-2x fadeInRight animated",
                        timeout : 2000
                    });
                    $('#addCstm').popover("hide");
                    for(var i in list)
                        checklist[list[i]]=false;
                    $('#dt_basic_cstm').DataTable().ajax.reload(null,false);;
                    
                }else{
                    $.smallBox({
                         title : "操作失败",
                         content : "<i class='fa fa-clock-o'></i> <i>操作失败...</i>",
                         color : "#C46A69",
                         iconSmall : "fa fa-times fa-2x fadeInRight animated",
                         timeout : 2000
                    });
                }	
			},"json");
		}
		
		/* 移动客户 */
		function moveCstm(){
			
			var list = [];
			for(var i in checklist) {
				if(checklist[i]==true)
					list.push(i);
			}
			
			if(list.length > 0){
				$.post(window.parent.getContext()+'cstm/getMoveInfo',{customers:list,tag:0},function(d){
					
					$.SmartMessageBox({
						title : "移动客户",
						content : "该操作将会移动包含 "+ d.name +" 在内的 "+ d.count +" 个客户到你指定的客户池中，确定移动？",
						buttons : "[取消][确认]",
						input : "select", 
						options : "["+d.poolNames.join("][")+"]"
					}, function(ButtonPress, Value) {
						if('确认'==ButtonPress){
                            $.post(window.parent.getContext()+'cstm/getMoveInfo',{poolName:Value,customers:list,tag:1},function(g){
                                if(g.success){
                                    $.smallBox({
                                        title : "操作成功",
                                        content : "<i class='fa fa-clock-o'></i> <i>操作成功...</i>",
                                        color : "#659265",
                                        iconSmall : "fa fa-check fa-2x fadeInRight animated",
                                        timeout : 2000
                                    });
                                    
                                    for(var i in list)
                                        checklist[list[i]]=false;
                                    $('#dt_basic_cstm').DataTable().ajax.reload(null,false);;
                                    
                                }else{
                                    $.smallBox({
                                         title : "操作失败",
                                         content : "<i class='fa fa-clock-o'></i> <i>操作失败...</i>",
                                         color : "#C46A69",
                                         iconSmall : "fa fa-times fa-2x fadeInRight animated",
                                         timeout : 2000
                                    });
                                }
                                
                            },"json");
						}
					});
					
				},"json");
			}else{
				$.bigBox({
					title : "提示",
					content : "你至少要选择一个客户来进行操作！",
					color : "#C79121",
					icon : "fa fa-bell shake animated",
					timeout : 5000
				});
			}
		}
		
		/* 删除客户 */
		function deleteCstms(){

			var list = [];
			for(var i in checklist) {
				if(checklist[i]==true)
					list.push(i);
			}
			    
			if(list.length > 0){
				
				$.post(window.parent.getContext()+'cstm/getDeleteInfo',{customers:list,tag:0},function(d){
					if(d.success){
					  $.SmartMessageBox({
			                title : "删除",
			                content : "此操作将会删除包含 "+d.name+" 在内的 "+ d.count +" 个客户，确定执行？",
			                buttons : "[No][Yes]",
			            }, function(ButtonPressed, Value) {
			            	if(ButtonPressed =="Yes"){
			            		
			            		$.post(window.parent.getContext()+'cstm/getDeleteInfo',{customers:list,tag:1},function(s){
			            			if(s.success && s.num == list.length){
		                                $.smallBox({
		                                    title : "操作成功",
		                                    content : "<i class='fa fa-clock-o'></i> <i>操作成功...</i>",
		                                    color : "#659265",
		                                    iconSmall : "fa fa-check fa-2x fadeInRight animated",
		                                    timeout : 2000
		                                });
		                                
		                                for(var i in list)
											checklist[list[i]]=false;
		                                
		                                $('#dt_basic_cstm').DataTable().ajax.reload(null,false);;
		                                
			            			}else{
			                              $.smallBox({
			                                 title : "操作失败",
			                                 content : "<i class='fa fa-clock-o'></i> <i>操作失败...</i>",
			                                 color : "#C46A69",
			                                 iconSmall : "fa fa-times fa-2x fadeInRight animated",
			                                 timeout : 2000
		                             	});
			            			}
			            		},"json");
			            	}
			            });
					}
				},"json");
			}else{
				$.bigBox({
					title : "提示",
					content : "你至少要选择一个客户来进行操作！",
					color : "#C79121",
					icon : "fa fa-bell shake animated",
					timeout : 5000
				});
			}
		}
		
		var cstmTabel = "";
		$(document).ready(function() {
			
			pageSetUp();
			cstmTabel = $('#dt_basic_cstm').DataTable({
// 			"dom" :"t" + "<'dt-toolbar-footer'<'col-sm-6 col-xs-12'i><'col-xs-12 col-sm-6 text-right'lp>>",
			"dom" :"t" + "<'dt-toolbar-footer'<'col-sm-6 col-xs-12'i><'col-xs-12 col-sm-6 text-right'lp>>",
// 			"scrollX" : true,
			"autoWidth" : true,
			"ordering" : true,
			"serverSide" : true,
			"processing" : true,
			"searching" : false,
			"retrieve":true,
			"pageLength" : 10,
			"lengthMenu" : [ 10, 15, 20, 25, 30 ],
		 	"language": {
				"url" : window.parent.getContext() + "public/dataTables.cn.txt"
			},
			"ajax":{
				"url" : window.parent.getContext() + "cstm/data",
				"type":"POST",
				"data" :function(param){
					<@params tit></@params>
					param.cstm_name = $("#cstm_name").val();
					param.customerTags = $("#customerTags").val();
					/* 是否默认查询指定客户池 */
					<#if XPool??>
						param.pool_id = $("#pool_id_s").val();
					<#else>
						param.pool_id = $("#pool_id").val();
					</#if>
					/* 如果是在用户管理页面进来的 */
					<#if dataType?? && dataType == "cstm" && clickUser??>
						param.own_id = '${clickUser}';
					<#else>
						param.own_id = $("#own_id").val();
					</#if>
					param.level = '${level!''}';
					param.start_date = $("#start_date").val();
					param.customer_des = $("#customerDes").val();
					param.customer_id = $("#customer_id").val();
					param.phone_number = $("#phone_number").val();
					param.dataranges = $("#dataranges").val();
				}
			},
			"infoCallback":function(settings, start, end, max, total, pre){
		    	return "显示第 " + start + " 至 " + end + " 项结果，共 " + total + " 项，已选中 " + getCheckNum() + " 项";
		    },
			"paging" :true,
			"pagingType" :"bootstrap",
			"lengthChange" : true,
			"order" : [ "4", "desc"],
			"columns" : [
					{
						"title" : '<div class="btn-group">'
								+ ' <a class="btn btn-sm btn-primary dropdown-toggle" data-toggle="dropdown" href="javascript:void(0);">选择<span class="caret"></span></a><ul class="dropdown-menu">'
								+ ' <li><a href="javascript:void(0);"onclick="docheckall()" >全选所有</a></li>'
								+ '<li><a href="javascript:void(0);" onclick="docheckallpage()">全选本页</a></li>'
								+ '<li><a href="javascript:void(0);" onclick="docancelAll()">取消所有</a></li>'
								+ '</ul></div>',
						"sortable" : false,
						"data" : "null",
						"render" : function(data, type, full) {
							
							if (checklist[full.uuid]) {
								return '<input type="checkbox" checked="checked" id="ck'
										+ full.uuid
										+ '" onclick="docheck('
										+ "'" + full.uuid + "'" + ')"  />';
							} else {
								return '<input type="checkbox" id="ck'
										+ full.uuid
										+ '" onclick="docheck('
										+ "'" + full.uuid + "'" + ')" />';
							}
						},
						"width" : "30px"
					},
					<#if title??>
						<#list dataRows?keys as key>
							<#if key == "phone_number">
								{"title" : "${dataRows[key]}",  "data" : "null","render":function(d,t,f){
									
									/* 如果没有绑定分机 */
									<#if (Session.unexten)?exists>

										<#if hiddenPhone??>
											/* 判断是否是多个号码 */
											if(f.phone_number.indexOf(",") != -1){
												var num1 = (f.phone_number).substring(0,f.phone_number.indexOf(","));
												var num2 = (f.phone_number).substring(f.phone_number.indexOf(",")+1);
												var count = f.phone_number.indexOf(",");
												
												if(f.defVal == '1'){
													return window.parent.hiddenPhone(num1)+ '&nbsp;,&nbsp;'+ window.parent.hiddenPhone(num2);
												}else{
													return window.parent.hiddenPhone(num1)+'&nbsp;,&nbsp;'+ window.parent.hiddenPhone(num2);
												}
												
											}else{
												if(f.defVal == '1'){
													return window.parent.hiddenPhone(f.phone_number);
// 													return f.phone_number;
												}else{
													return window.parent.hiddenPhone(f.phone_number);
// 													return f.phone_number;
												}
											}
										<#else>
											
											if(f.phone_number.indexOf(",") != -1){
												var num1 = (f.phone_number).substring(0,f.phone_number.indexOf(","));
												var num2 = (f.phone_number).substring(f.phone_number.indexOf(",")+1);
												var count = f.phone_number.indexOf(",");
												if(f.defVal == '1'){
													return "0"+num1 +'&nbsp;,&nbsp;' +"0"+(num2);
												}else{
													return num1 +'&nbsp;,&nbsp;' +(num2);
												}
												
											}else{
												
												if(f.defVal == '1'){
													return ("0"+f.phone_number);
												}else{
													return (f.phone_number);
												}
												
											}
										</#if>
									
									/* 绑定了分机 */
									<#else>
										
										<#if hiddenPhone??>
											/* 判断是否是多个号码 */
											if(f.phone_number.indexOf(",") != -1){
												var num1 = (f.phone_number).substring(0,f.phone_number.indexOf(","));
												var num2 = (f.phone_number).substring(f.phone_number.indexOf(",")+1);
												var count = f.phone_number.indexOf(",");
												if(f.defVal == '1'){
													return '<a onclick=callOut("off","'+num1+'","'+f.customer_id+'");>'+ window.parent.hiddenPhone(num1) +'</a>&nbsp;,&nbsp;<a onclick=callOut("off","'+num2+'","'+f.customer_id+'");>'+  window.parent.hiddenPhone(num2) +'</a>';
												}else{
													return '<a onclick=callOut("on","'+num1+'","'+f.customer_id+'");>'+ window.parent.hiddenPhone(num1) +'</a>&nbsp;,&nbsp;<a onclick=callOut("on","'+num2+'","'+f.customer_id+'");>'+  window.parent.hiddenPhone(num2) +'</a>';
												}
											}else{
												if(f.defVal == '1'){
													return '<a onclick=callOut("off","'+f.phone_number+'","' + f.customer_id+'");>' + window.parent.hiddenPhone(f.phone_number) +'</a>';
												}else{
													return '<a onclick=callOut("on",'+f.phone_number+',"' + f.customer_id+'");>' +  window.parent.hiddenPhone(f.phone_number) +'</a>';
												}
											}
										
										<#else>
											/* 判断是否是多个号码 */
											if(f.phone_number.indexOf(",") != -1){
												var num1 = (f.phone_number).substring(0,f.phone_number.indexOf(","));
												var num2 = (f.phone_number).substring(f.phone_number.indexOf(",")+1);
												var count = f.phone_number.indexOf(",");
												if(f.defVal == '1'){
													return '<a onclick=callOut("off","'+num1+'","'+f.customer_id+'");>'+ (num1)+'</a>&nbsp;,&nbsp;<a onclick=callOut("off",'+num2+',"'+f.customer_id+'");>'+ (num2)+'</a>';
												}else{
													return '<a onclick=callOut("on","'+num2+'","'+f.customer_id+'");>'+(num2) +'</a>&nbsp;,&nbsp;<a onclick=callOut("on",'+num2+',"'+f.customer_id+'");>'+  (num2) +'</a>';
												}
											}else{
												if(f.defVal == '1'){
													return '<a onclick=callOut("off","'+f.phone_number+'","'+f.customer_id+'");>'+ (f.phone_number)+'</a>';
												}else{
													return '<a onclick=callOut("on","'+f.phone_number+'","'+f.customer_id+'");>'+  (f.phone_number) +'</a>';
												}
											}
										</#if>
										
									</#if>
								}},
							<#elseif key =="customer_id" >
								{"title" : "${dataRows[key]}",  "data" : "null","render":function(d,t,f){
									return "<a onclick=window.parent.getCodeOrPhoneSearch('"+f.phone_number+"');>"+ f.customer_id +"</a>";
								}},
							<#else>
								{"title" : "${dataRows[key]}",  "data" : "${key}"},
							</#if>
						</#list>
					
						{ "title" : "客户标签","data" : "null", "render": function(data,type,full){
							  if((full.customerTags)!= null){
									var a = full.customerTags.split(',');
									var s = '';
									for(var i in a){
										s+="<label  style='font-weight:bold;color:black'>"+ a[i]+"&nbsp;</label>"+"";
									}
									return s;
								}else{
									return "暂无标签";
								}
							}
						}, 
						<#if dataType?? && dataType == "cstm">
						<#else>
						{ "title" : "操作",  "data" : "null",  "orderable":false,
						  "width" : "100px",
						  "render": function(data,type,full){

						  /* 如果默认值为1 直接拨号 否则加0拨号 */
						  <#if (Session.unexten)?exists>
				   			
							  return "<a onclick=editTag('"+full.uuid+"');>修改</a>";
// 							  return "<a onclick=editTag('"+full.uuid+"');>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick=deleteInfo('"+full.uuid+"');>删除</a>";
			   			  <#else>
							  
							  if(full.defVal == '1'){
								 
								  /* 如果只有二个号码 */
								  if(full.phone_number.indexOf(",") != -1){
									  
									  var num1 = full.phone_number.split(",")[0];
									  var num2 = full.phone_number.split(",")[1];
									  return  "&nbsp;&nbsp;&nbsp;<a onclick=editTag('"+full.uuid+"');>修改</a>";
// 									  return  "&nbsp;&nbsp;&nbsp;<a onclick=editTag('"+full.uuid+"');>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick=deleteInfo('"+full.uuid+"');>删除</a>";
									  
// 									  '<div class="btn-group">'
// 					   					+ '<button class="btn btn-success" onclick=callOut("off",'+num1+',"'+full.customer_id+'")> 呼叫'+  (num1).substring(0,3)+"****"+(num1).substring(7)  +'</button>'
// 				   						+ '<button class="btn btn-success dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></button>'
// 				   						+ '<ul class="dropdown-menu" style="min-width:100px">'
// 			   								+ '<li><a href="javascript:callOut('+"'on'"+','+num1+','+"'"+full.customer_id+"'"+');"> 加0呼叫'+(num1).substring(0,3)+"****"+(num1).substring(7) +'</a></li>'
// 			   								+ '<li><a href="javascript:callOut('+"'off'"+','+num2+','+"'"+full.customer_id+"'"+');">呼叫'+(num2).substring(0,3)+"****"+(num2).substring(7) +'</a></li>'
// 			   								+ '<li><a href="javascript:callOut('+"'on'"+','+num2+','+"'"+full.customer_id+"'"+');"> 加0呼叫'+(num2).substring(0,3)+"****"+(num2).substring(7) +'</a></li>'
// 					   					+ '</ul></div>'
// 									   	+
									  
								  }else{
									  
									  /* 如果只有一个号码 */
									  var num1 = full.phone_number;
									  
									  return  "&nbsp;&nbsp;&nbsp;<a onclick=editTag('"+full.uuid+"');>修改</a>";
// 									  return  "&nbsp;&nbsp;&nbsp;<a onclick=editTag('"+full.uuid+"');>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick=deleteInfo('"+full.uuid+"');>删除</a>";
									  
// 									  '<div class="btn-group">'
// 				   						+ '<button class="btn btn-success" onclick=callOut("off",'+num1+',"'+full.customer_id+'")> 呼叫</button>'
// 			   							+ '<button class="btn btn-success dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></button>'
// 		   								+ '<ul class="dropdown-menu" style="min-width:100px">'
// 		   									+ '<li><a href="javascript:callOut('+"'on'"+','+num1+','+"'"+full.customer_id+"'"+');"> 加0呼叫</a></li>'
// 				   						+ '</ul></div>'
// 									   	+
								  }								  
								  
							   }else{
								   
									  if(full.phone_number.indexOf(",") != -1){
										  
										  var num1 = full.phone_number.split(",")[0];
										  var num2 = full.phone_number.split(",")[1];
										  
										  return "&nbsp;&nbsp;&nbsp;<a onclick=editTag('"+full.uuid+"');>修改</a>";
// 										  return "&nbsp;&nbsp;&nbsp;<a onclick=editTag('"+full.uuid+"');>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick=deleteInfo('"+full.uuid+"');>删除</a>";
										  
// 										  '<div class="btn-group">'
// 			   					 			+ '<button class="btn btn-success" onclick=callOut("on",'+num1+',"'+full.customer_id+'")>加0呼叫'+(num1).substring(0,3)+"****"+(num1).substring(7) +'</button>'
// 					   						+ '<button class="btn btn-success dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></button>'
// 					   						+ '<ul class="dropdown-menu" style="min-width:100px">'
// 				   								+ '<li><a href="javascript:callOut('+"'off'"+','+num1+','+"'"+full.customer_id+"'"+');">呼叫'+(num1).substring(0,3)+"****"+(num1).substring(7) +'</a></li>'
// 				   								+ '<li><a href="javascript:callOut('+"'on'"+','+num2+','+"'"+full.customer_id+"'"+');">加0呼叫'+(num2).substring(0,3)+"****"+(num2).substring(7) +'</a></li>'
// 				   								+ '<li><a href="javascript:callOut('+"'off'"+','+num2+','+"'"+full.customer_id+"'"+');">呼叫'+(num2).substring(0,3)+"****"+(num2).substring(7) +'</a></li>'
// 				   							+ '</ul></div>'
// 			   								+ 
										  
									  }else{
										  
										  var num1 = full.phone_number;
										  return "&nbsp;&nbsp;&nbsp;<a onclick=editTag('"+full.uuid+"');>修改</a>";
// 										  return "&nbsp;&nbsp;&nbsp;<a onclick=editTag('"+full.uuid+"');>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick=deleteInfo('"+full.uuid+"');>删除</a>";
// 										  '<div class="btn-group">'
// 			   					 			+ '<button class="btn btn-success" onclick=callOut("on",'+num1+',"'+full.customer_id+'")>加0呼叫</button>'
// 					   						+ '<button class="btn btn-success dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></button>'
// 					   						+ '<ul class="dropdown-menu" style="min-width:100px">'
// 				   								+ '<li><a href="javascript:callOut('+"'off'"+','+num1+','+"'"+full.customer_id+"'"+');">呼叫</a></li>'
// 				   							+ '</ul></div>'
// 			   								+ 
										  
										  
									  }		
							   }
								  
				   			</#if>
						}
						}
						</#if>
						<#else>
						{}
					</#if>
				]
			});
			
			$('#own_id').select2({
	           allowClear : true,
	           width: '99%'
	        });
			
			$('#dataranges').select2({
	           allowClear : true,
	           width: '99%'
	        });
			
	        $('#pool_id').select2({
	           allowClear : true, 
	           width: '99%'
	        });
	        
		    //给批量修改拥有者绑定popover事件
//	        $('#addCstm').popover({
//				html:true,
//				animation:true,
//				content:'<form id="changeOwn" action="${springMacroRequestContext.contextPath}/cstm/batchUpdate" method="post" class="smart-form" ><fieldset class="row" style="padding-top:10px;padding-bottom:10px;"><div class="form-group"><select name="ownUser" id="ownUser" style="height:30px" class="select2">' + <#if user??><#list user as u> 
//				'<option value="${u.uid}">${u.userDescribe}</option>' +
//				</#list></#if>
//				'</select></div></fieldset>' + 
//				'<div class="form-actions"><div class="row"><div class="col col-6"><label class="btn btn-default btn-sm" onclick="$(\'#addCstm\').popover(\'hide\')">取消</button></div><div class="col col-6"><label class="btn btn-primary btn-sm" onclick="changeOwn();">修改</label></div></div></div></form>'
//			});
			
			/* 从客户池管理处跳转操作 先判断是否有值 如果没有值 不执行 如果有 先赋值 再清空   */			
			if(window.parent.current_pool_id != ""){
				$("#pool_id").val(window.parent.current_pool_id);
				window.parent.current_pool_id = "";
			}
			
			/* 管理员操作从客户池处跳转到客户信息  */
			$("#pool_id_s").val('${pool_id!''}');
		});
		
		
		/* 选中的项数 */
		function getCheckNum(){
			var num=0;
			for(var i in checklist) {
				if(checklist[i]==true)
					num++;
			}
			return "<span id='checkInfo'>" + num + "</span>";
		}
		
		
		function getConditions(){
			
			queryCondition = {
					<#if tit??>
						<#list tit as ps>
							${ps} : $("#${ps}").val(),
						</#list> 
					</#if>
					cstm_name : $("#cstm_name").val(),
					customerTags : $("#customerTags").val(),
					pool_id:$("#pool_id").val(),
					/* 是否默认查询指定客户池 */
					<#if XPool??>
						pool_id: $("#pool_id_s").val(),
					<#else>
						pool_id: $("#pool_id").val(),
					</#if>
					own_id : $("#own_id").val(),
					start_date : $("#start_date").val(),
					customer_des : $("#customerDes").val(),
					customer_id : $("#customer_id").val(),
					phone_number : $("#phone_number").val()
				}
		}
		
		
		/* 查询结果 */
		function getResult(){
			
			getConditions();
			$('#dt_basic_cstm').DataTable().ajax.reload();
			
		}
		
		function getDeptResult(){
			$("#own_id").val("").change();
			getResult();
		}

		/* 添加弹框 */
		function addModel(){
			
			$.post(window.parent.getContext() + "index/get",function(data){
				$("#main_cstm").append(data);
			});
		}

		/* 添加修改客户信息 标签 */
		function editTag(uuid){
			
			$("#dialog_cstm_tags").remove();
			var requestUrl = "";
			/* 其实是一致的 */
			if("null" != uuid){
				requestUrl = window.parent.getContext() + "cstm/getTags"; 
			}else{
				requestUrl = window.parent.getContext() + "cstm/getTags"; 
			}
			$.post(requestUrl,{uuid : uuid},function(data){
				$("#tmp_div").append(data);
			});
		}

		function deleteInfo(uuid){

			$.SmartMessageBox({
				title : "删除",
				content : "该操作将执行删除操作，确定执行？",
				buttons : "[No][Yes]",
			}, function(ButtonPressed) {
				if (ButtonPressed === "Yes") {
					var url = window.parent.getContext() + "cstm/delete";
					$.post(url,{uuid:uuid},function(data){
						if(data.success){
							$.smallBox({
								title : "操作成功",
								content : "<i class='fa fa-clock-o'></i> <i>删除成功...</i>",
								color : "#659265",
								iconSmall : "fa fa-check fa-2x fadeInRight animated",
								timeout : 2000
							});
						checklist[uuid]=false;
						cstmTabel.ajax.reload(null,false);
						}else{
							$.smallBox({
								title : "操作取消",
								content : "<i class='fa fa-clock-o'></i> <i>你取消了操作...</i>",
								color : "#C46A69",
								iconSmall : "fa fa-times fa-2x fadeInRight animated",
								timeout : 2000
							});
						}
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
						title : "取消",
						content : "<i class='fa fa-clock-o'></i> <i>操作已经被取消...</i>",
						color : "#659265",
						iconSmall : "fa fa-times fa-2x fadeInRight animated"
					});
				}
			});

			cstmTabel.ajax.reload(null,false);
		}

		/* 编辑列 */
		function alterTable(){
			$("#dialog_cstm_edit_column").remove();
			$.post(window.parent.getContext() + "design/edit",function(data){
				$("#main_cstm").append(data);
			});
		}

		/* 编辑主号码 */
		function mainPhone(uuid,number){
		
			$("#dialog_cstm_phone").remove();
			$.post(window.parent.getContext()+'/cstm/getPhone',{uuid:uuid,phone:number},function(data){

				$("#main_cstm").append(data);
			});
		}
		
		/* 呼叫 */
		function callOut(vars,num,uid){
			//将该变量设置为0，该函数执行后的弹屏的tab页将有关闭按钮
			window.parent.closetag = "0";
			console.log(num);
			window.parent.getCodeOrPhoneSearch(num);

		}
		
// 		/* 根据编号 */
// 		function popByCustomerId(phone,customerId){
			
// 			$.post(window.parent.getContext()+'cstm/beCustomer',{
// 				phone:phone,
// 				customerId:customerId},
// 				function(d){
// 					if(d.success){
						
						
// 					}			
// 				},"json"
// 			);
// 		}
		
		/*导出搜索结果 */
	    $("#exportdata").click(function (){
	    	
	    	window.location.href=getUrls();
	    });
		
		function getUrls(){
			
			var url = window.parent.getContext()+"cstm/export?";
			
			<#if tit??>
				<@listParameter tit></@listParameter>
			</#if>
			url += "&cstm_name="+$("#cstm_name").val();
			url += "&customerTags="+$("#customerTags").val();
			/* 是否默认查询指定客户池 */
			<#if XPool??>
				url += "&pool_id="+$("#pool_id_s").val();
			<#else>
				url += "&pool_id="+$("#pool_id").val();
			</#if>
			
			url += "&own_id="+$("#own_id").val();
			url += "&start_date="+$("#start_date").val();
			url += "&customer_des="+ $("#customerDes").val();
			url += "&customer_id="+$("#customer_id").val();
			url += "&phone_number="+$("#phone_number").val();
			
			return url;
		}

		/* 详情 */
		function getDetails(phoneNumber){
			window.parent.addTab("弹屏|" + phoneNumber,phoneNumber,JSON.stringify({number:phoneNumber,popType:'detail',controller:"newdatacall"},"detail"), "", "newuserdata");
		}
		
		
	</script>
