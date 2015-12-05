<link rel="stylesheet"
	href="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<script type="text/javascript"
	src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript"
	src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" 
	src="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-wizard/jquery.bootstrap.wizard.js"></script>
<script type="text/javascript" 
	src="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-duallistbox/jquery.bootstrap-duallistbox.min.js"></script>
<script type="text/javascript" 
	src="${springMacroRequestContext.contextPath}/assets/js/ajaxfileupload.js"></script>
<script type="text/javascript" 
	src="${springMacroRequestContext.contextPath}/assets/js/plugin/x-editable/x-editable.min.js"></script>
<style>
.bootstrapWizard li
{
	width:25%;
}
[role="content"]
{
	padding:13px;
}
.smart-form .input-file
{
	right:0;
}
.smart-form .input-file .button
{
	top:0px;
}
#filebtn
{
	right:30%;
}
.pager li.finish
{
	float:right;
}
.btn.btn-lg.bg-color-blue.txt-color-white{

	background-color: #3276B1!important;
}
</style>
<div class="modal fade" id="dialogTask">
	<div class="modal-dialog" style="width: 40%">
		<div class="modal-content">
			<div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                <h4 class="modal-title">修改项目向导</h4>
            </div>
            <div role="content">
	            <div class="row">
		            <form id="project-wizard" action="data/project/updateWizard" class="form-horizontal" novalidate="novalidate" method="post">
			            <div id="add-project-wizard" class="col-sm-12">
				            <div class="form-bootstrapWizard">
								<ul class="bootstrapWizard form-wizard">
									<li class="active" data-target="#step1">
										<a href="#tab1" data-toggle="tab"> <span class="step">1</span> <span class="title">修改项目名称</span> </a>
									</li>
									<li data-target="#step2">
										<a href="#tab2" data-toggle="tab"> <span class="step">2</span> <span class="title">修改人员</span> </a>
									</li>
									<li data-target="#step3">
										<a href="#tab3" data-toggle="tab"> <span class="step">3</span> <span class="title">修改数据</span> </a>
									</li>
									<li data-target="#step4">
										<a href="#tab4" data-toggle="tab"> <span class="step">4</span> <span class="title">确认</span> </a>
									</li>
								</ul>
								<div class="clearfix"></div>
							</div>
							<div class="tab-content">
								<div class="tab-pane active" id="tab1">
									<br>
									<h3><strong>第一步</strong> - 修改项目</h3>
									<div class="form-group">
										<label class="col-md-2 control-label input-lg">项目名<sup>*</sup></label>
										<div class="col-md-9">
											<input class="form-control input-lg" required placeholder="输入项目名" data-bv-notempty-message="必须输入项目名" data-bv-field="projectName" id="wzprojectName" name="projectName" type="text" value="${entry.projectName}">
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label input-lg">项目信息<sup>*</sup></label>
										<div class="col-md-9">
											<input class="form-control input-lg" required placeholder="输入项目信息" data-bv-notempty-message="必须输入项目信息" data-bv-field="projectInfo" id="wzprojectInfo" name="projectInfo" type="text" value="${entry.projectInfo}">
										</div>
									</div>
									<input type="hidden" id="wzprojectUuid" name="uid" value="${projectUuid}">
<!-- 										<div class="col-sm-6"> -->
<!-- 											<div class="form-group"> -->
<!-- 												<div class="input-group"> -->
<!-- 													<span class="input-group-addon"><i class="fa fa-user fa-lg fa-fw"></i></span> -->
<!-- 													<input class="form-control input-lg" placeholder="Last Name" type="text" name="lname" id="lname"> -->
	
<!-- 												</div> -->
<!-- 											</div> -->
<!-- 										</div> -->
			
								</div>
								<div class="tab-pane" id="tab2">
									<br>
									<h3><strong>第二步</strong> - 修改人员</h3>
									<div>
									<select multiple="multiple" id="userDuallistbox" class="form-control" name="users">
									<#list users as u>
										<#if selectedUsers?keys?seq_contains(u.loginName)>
											<option value=${u.loginName} selected="selected">${u.loginName} [未完成/总量：${selectedUsers[u.loginName]}]</option>
										<#else>
											<option value=${u.loginName}>${u.loginName}</option>
										</#if>											
									</#list>
									</select>
									</div>
								</div>
								<div class="tab-pane" id="tab3">
									<br>
									<h3><strong>第三步</strong> - 导入数据</h3>
									<div>
										<select multiple="multiple" id="dataDuallistbox" class="form-control" name="datas">
										<#list datas as d>
											<option value=${d.dataTable} data-count=${d.dataCount}>${d.containerName} [共有${d.dataCount}条数据]</option>
										</#list>
										</select>
									</div>
								</div>
								<div class="tab-pane" id="tab4">
									<br>
									<h3><strong>第四步</strong> - 保存结果</h3>
									<br>
									<h1 class="text-center text-success"><strong><i class="fa fa-check fa-lg"></i>恭喜</strong></h1>
									<h4 class="text-center">项目创建完成</h4>
									<div class="alert alert-info fade in">
										<i class="fa-fw fa fa-info"></i>
										<strong>项目人员：</strong> 共选择<span class="users"></span>个人员
									</div>
									<div class="alert alert-info fade in">
										<i class="fa-fw fa fa-info"></i>
										<strong>项目数据：</strong> 共选择<span class="datas"></span>条数据
									</div>
									<br>
									<br>
								</div>
			
								<div class="form-actions">
									<div class="row">
										<div class="col-sm-12">
											<ul class="pager wizard no-margin">
												<!--<li class="previous first disabled">
												<a href="javascript:void(0);" class="btn btn-lg btn-default"> First </a>
												</li>-->
												<li class="previous disabled">
													<a href="javascript:void(0);" class="btn btn-lg btn-default"> 上一步 </a>
												</li>
												<!--<li class="next last">
												<a href="javascript:void(0);" class="btn btn-lg btn-primary"> Last </a>
												</li>-->
												<li class="next">
													<a href="javascript:void(0);" class="btn btn-lg bg-color-blue txt-color-white"> 下一步 </a>
												</li>
												<li class="finish">
													<a href="javascript:void(0);" class="btn btn-lg btn-default"> 完成 </a>
												</li>
											</ul>
										</div>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			
			$('#dialogTask').modal("show");
			
			/*添加向导*/
			$('#add-project-wizard').bootstrapWizard({
				 tabClass: 'form-wizard',
				 onNext: function(tab, navigation, index) {
					 if(index=4){
						 $("#tab4 span.users").text($("#userDuallistbox :selected").length);
						 var total = 0;
						 $("#dataDuallistbox :selected").each(function(k,v){
							 total += parseInt($(v).attr("data-count"));
						 });
						 $("#tab4 span.datas").text(total);
					 }
				 },
//				 onNext: function(tab, navigation, index) {
// 		            switch(index){
// 		            	case 1:{
// 		            		/*添加项目*/
// 		            		$.post(getContext() + "data/project/save",
// 		            				{uid:$("#wzprojectUuid").val(),projectName:$("#wzprojectName").val(),projectInfo:$("#wzprojectInfo").val()},
// 		            				function(data){
// 		            					$("#wzprojectUuid").val(data.uid);
// 		            		},"json");break;
// 		            	}
// 		            	case 2:{
// 		            		/*添加人员*/
// 		            		$.post(getContext() + "data/project/addUsers",
// 		            				{uid:$("#wzprojectUuid").val(),users:$("#userDuallistbox").val()},
// 		            				function(data){
// 		            		},"json");break;
// 		            	}
// 		            	case 3:{
// 		            		/*添加数据*/
// 		            		$.post(getContext() + "data/project/addDatas",
// 		            				{uid:$("#wzprojectUuid").val(),datas:$("#dataDuallistbox").val()});break;
// 		            	}
// 		            }
//		        },
				onTabClick: function(){
					return false;
				},
		        onFinish: function(){
		        	$("#project-wizard").submit();
		        }
			});
			
			$('#project-wizard').ajaxForm({
                dataType:"json",
                success: function(data) {
                    if(data.success){
                        $.smallBox({
                            title : "操作成功",
                            content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
                            color : "#659265",
                            iconSmall : "fa fa-check fa-2x fadeInRight animated",
                            timeout : 2000
                        });
                        $("table.dataTable").DataTable().ajax.reload(null,false);;
                    }
		    		else{
                    	$.smallBox({
                            title : "操作失败",
                            content : "<i class='fa fa-clock-o'></i> <i>操作失败，未做修改</i>",
                            color : "#C46A69",
                            iconSmall : "fa fa-times fa-2x fadeInRight animated",
                            timeout : 2000
                        });
                    }
                    $('#dialogTask').modal("hide");
                    validator.resetForm();
                },
                submitHandler : function(form) {
                    $(form).ajaxSubmit({
                        success : function() {
                            $("#messageinfoa").addClass('submited');
                        }
                    });
                },
                error: function(XMLHttpRequest,textStatus , errorThrown){
                    if(textStatus=='error'){
                        $('#dialogTask').modal('hide');
                        $.smallBox({
                            title : "操作失败",
                            content : "<i class='fa fa-clock-o'></i> <i>操作失败</i>",
                            color : "#C46A69",
                            iconSmall : "fa fa-times fa-2x fadeInRight animated",
                            timeout : 2000
                        });

                        $("table.dataTable").DataTable().ajax.reload(null,false);;
                    }
                }
            });
			
			/**/
			userlist = $('#userDuallistbox').bootstrapDualListbox({
				nonSelectedListLabel: '未添加人员清单',
				selectedListLabel: '已添加人员清单',
				preserveSelectionOnMove: 'moved',
				moveOnSelect: false,
				selectorMinimalHeight: "200",
				filterPlaceHolder: "筛选",
				filterTextClear: "显示全部",
				infoText: "列表包含 {1} 项",
				infoTextFiltered: '<span class="label label-warning">筛选</span> {1} 项中的 {0} 项',
				infoTextEmpty: "列表为空"
			});
			
			datalist = $('#dataDuallistbox').bootstrapDualListbox({
				nonSelectedListLabel: '<div class="row smart-form"><span class="col no-padding">未添加数据清单</span><div class="hidden col col-8 input input-file"><span id="filebtn" class="button"><input type="file" id="fileup" name="multipartFile" accept=".xlsx">添加数据</span>&nbsp;&nbsp;&nbsp;<span id="upload" class="button">上传</span></div></div>',
				selectedListLabel: '已添加数据清单',
				preserveSelectionOnMove: 'moved',
				moveOnSelect: false,
				selectorMinimalHeight: "200",
				filterPlaceHolder: "筛选",
				filterTextClear: "显示全部",
				infoText: "列表包含 {1} 项",
				infoTextFiltered: '<span class="label label-warning">筛选</span> {1} 项中的 {0} 项',
				infoTextEmpty: "列表为空"
			});
			/* 提交按钮单击事件  */

			$('#upload').editable({
				showbuttons: 'bottom',
				type: 'text',
		        pk: 1,
		        name: 'dataName',
		        title: '数据名',
		    });
			
			$('#upload').on('save', function(e, params) {
				ajaxFileUpload(params.newValue);
			});
			
			/* 关闭窗口的回调函数  */
			$('#dialogTask').on('hide.bs.modal', function(e) { /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
				$("#dialogTask").remove();
			});
			
			
			 
		});
		
		
		/*文件上传函数*/
		function ajaxFileUpload(dataName)  
	    {  
	        //设置加载图标的显示 
	        alert(dataName);
	        $.ajaxFileUpload({  
	            url: getContext() + '/data/data/upload',  
	            secureuri:false,  
	            fileElementId:'fileup',  
	            dataType: 'json',  
	            data:{'dataName': dataName},  
	            success: function (data, status)  
	            {  
	                if(data.success==true){
	                	datalist.append($('<option value="' + data.dataTable + '">' + data.dataName + ' </option>'));
	                	datalist.bootstrapDualListbox('refresh');
	                }
	            },  
	            error: function (data, status, e)  
	            {  
	                //这里处理的是网络异常，返回参数解析异常，DOM操作异常  
	                alert("上传发生异常");  
	            }  
	        });
	        return false;  
	    }
		
	</script>
</div>




