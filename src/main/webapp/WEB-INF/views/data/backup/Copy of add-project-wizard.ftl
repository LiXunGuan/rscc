<link rel="stylesheet"
	href="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<script type="text/javascript"
	src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript"
	src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" 
	src="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-wizard/jquery.bootstrap.wizard.min.js"></script>
<script type="text/javascript" 
	src="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-duallistbox/jquery.bootstrap-duallistbox.min.js"></script>
<script type="text/javascript" 
	src="${springMacroRequestContext.contextPath}/assets/js/ajaxfileupload.js"></script>
<script type="text/javascript" 
	src="${springMacroRequestContext.contextPath}/assets/js/plugin/x-editable/x-editable.min.js"></script>
<style>
.bootstrapWizard li
{
	width:19%;
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
</style>
<div class="modal fade" id="dialogTask">
	<div class="modal-dialog" style="width: 40%">
		<div class="modal-content">
			<div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                <h4 class="modal-title">添加项目向导</h4>
            </div>
            <div role="content">
	            <div class="row">
		            <form id="project-wizard" class="form-horizontal" novalidate="novalidate">
			            <div id="add-project-wizard" class="col-sm-12">
				            <div class="form-bootstrapWizard">
								<ul class="bootstrapWizard form-wizard">
									<li class="active" data-target="#step1">
										<a href="#tab1" data-toggle="tab"> <span class="step">1</span> <span class="title">选择项目名称</span> </a>
									</li>
									<li data-target="#step2">
										<a href="#tab2" data-toggle="tab"> <span class="step">2</span> <span class="title">添加人员</span> </a>
									</li>
									<li data-target="#step3">
										<a href="#tab3" data-toggle="tab"> <span class="step">3</span> <span class="title">导入数据</span> </a>
									</li>
									<li data-target="#step4">
										<a href="#tab4" data-toggle="tab"> <span class="step">4</span> <span class="title">分配任务</span> </a>
									</li>
									<li data-target="#step5">
										<a href="#tab5" data-toggle="tab"> <span class="step">5</span> <span class="title">确认</span> </a>
									</li>
								</ul>
								<div class="clearfix"></div>
							</div>
							<div class="tab-content">
								<div class="tab-pane active" id="tab1">
									<br>
									<h3><strong>第一步</strong> - 新建项目</h3>
									<div class="form-group">
										<label class="col-md-2 control-label input-lg">项目名<sup>*</sup></label>
										<div class="col-md-9">
											<input class="form-control input-lg" required placeholder="输入项目名" data-bv-notempty-message="必须输入项目名" data-bv-field="projectName" id="wzprojectName" name="projectName" type="text">
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label input-lg">项目信息<sup>*</sup></label>
										<div class="col-md-9">
											<input class="form-control input-lg" required placeholder="输入项目信息" data-bv-notempty-message="必须输入项目信息" data-bv-field="projectInfo" id="wzprojectInfo" name="projectInfo" type="text">
										</div>
									</div>
									<input type="hidden" id="wzprojectUuid" name="projectUuid">
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
									<h3><strong>第二步</strong> - 添加人员</h3>
									<div>
									<select multiple="multiple" id="userDuallistbox" class="form-control">
									<#list users as u>
										<option value=${u.uid}>${u.loginName}</option>
									</#list>
									</select>
									</div>
								</div>
								<div class="tab-pane" id="tab3">
									<br>
									<h3><strong>第三步</strong> - 导入数据</h3>
									<div>
										<select multiple="multiple" id="dataDuallistbox" class="form-control">
										<#list datas as d>
											<option value=${d.dataTable}>${d.dataName}</option>
										</#list>
										</select>
									</div>
								</div>
								<div class="tab-pane" id="tab4">
									<br>
									<h3><strong>第四步</strong> - 分配任务</h3>
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 show-stats">
										<div class="row">
											<div class="col-xs-6 col-sm-6 col-md-12 col-lg-12"> <span class="text"> 任务状态（未分配数量/总量） <span id="taskStat" class="pull-right">130/200</span> </span>
												<div class="progress">
													<div class="progress-bar bg-color-blueDark" id="taskProgress" style="width: 65%;"></div>
												</div> 
											</div>
											<div class="col-xs-6 col-sm-6 col-md-12 col-lg-12"> <span class="text"> 人员状态（项目人员数量/总量）<span id="userStat" class="pull-right">10/100</span> </span>
												<div class="progress">
													<div class="progress-bar bg-color-blue" id="userProgress" style="width: 10%;"></div>
												</div> 
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">选择分配方案</label>
										<div class="col-md-10">
											<div class="radio">
												<label>
													<input type="radio" class="radiobox style-0" checked="checked" name="allocate" value="0">
													<span>平均分配给所有人</span> 
												</label>
											</div>
											<div class="radio">
												<label>
													<input type="radio" class="radiobox style-0" name="allocate" value="1">
													<span>平均所有人的现有任务</span> 
												</label>
											</div>
											<div class="radio">
												<label>
													<input type="radio" class="radiobox style-0" name="allocate" value="2">
													<span>每人分配至上限：</span> 
													<input type="text" name="allocateMax" id="allocateMax">
												</label>
											</div>
										</div>
									</div>
								</div>
								<div class="tab-pane" id="tab5">
									<br>
									<h3><strong>第五步</strong> - 保存结果</h3>
									<br>
									<h1 class="text-center text-success"><strong><i class="fa fa-check fa-lg"></i> Complete</strong></h1>
									<h4 class="text-center">Click next to finish</h4>
									<a href="form-x-editable.html#" id="username" data-type="text" data-pk="1" data-original-title="Enter username" class="editable editable-click">superuser</a>
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
												<li class="skip">
													<a href="javascript:void(0);" class="btn btn-lg txt-color-darken" onclick="$('#add-project-wizard').bootstrapWizard('next')"> 跳过 </a>
												</li>
												<li class="finish">
													<a href="javascript:void(0);" class="btn btn-lg txt-color-darken" > 完成 </a>
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
		            switch(index){
		            	case 1:{
		            		/*添加项目*/
		            		$.post(getContext() + "data/project/save",
		            				{uid:$("#wzprojectUuid").val(),projectName:$("#wzprojectName").val(),projectInfo:$("#wzprojectInfo").val()},
		            				function(data){
		            					$("#wzprojectUuid").val(data.uid);
		            		},"json");break;
		            	}
		            	case 2:{
		            		/*添加人员*/
		            		$.post(getContext() + "data/project/addUsers",
		            				{uid:$("#wzprojectUuid").val(),users:$("#userDuallistbox").val()},
		            				function(data){
		            		},"json");break;
		            	}
		            	case 3:{
		            		/*添加数据*/
		            		$.post(getContext() + "data/project/addDatas",
		            				{uid:$("#wzprojectUuid").val(),datas:$("#dataDuallistbox").val()},
		            				function(data){
		            					$("#taskStat").text((data.totalTask - data.unallocateTask) + "/" + data.totalTask);
		            					$("#userStat").text(data.projectUser + "/" + data.totalUser);
		            					$("#taskProgress").css("width",(data.totalTask - data.unallocateTask) / data.totalTask * 100 + "%");
		            					$("#userProgress").css("width",data.totalTask / data.totalUser * 100 + "%");
		            		},"json");break;
		            	}
		            	case 4:{
		            		/*分配任务*/
		            		$.post(getContext() + "data/project/allocate",
		            				{uid:$("#wzprojectUuid").val(),allocateType:$("[name='allocate']:checked").val(),allocateMax:$("#allocateMax").val()},
		            				function(data){
		            					$("#taskStat").text((data.totalTask - data.unallocateTask) + "/" + "data.totalTask");
		            		},"json");break;
		            	}
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
				nonSelectedListLabel: '<div class="row smart-form"><span class="col no-padding">未添加数据清单</span><div class="col col-8 input input-file"><span id="filebtn" class="button"><input type="file" id="fileup" name="multipartFile" accept=".xlsx">添加数据</span>&nbsp;&nbsp;&nbsp;<span id="upload" class="button">上传</span></div></div>',
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




