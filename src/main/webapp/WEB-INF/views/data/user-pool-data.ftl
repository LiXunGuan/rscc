<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/x-editable/x-editable.min.js"></script>
<style>
#toolBox{
	float:left;
	table-layout:auto;
	margin-bottom:5px;
}
.row [aria-label]{
	vertical-align:middle;
}
.smart-form .toggle input:not(:checked)+i:before
{
	background-color: #FF0000;
}
.progress{
	height: 10px;
	margin: 0px;
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
</style>
<div id="tmpDiv"></div>
<div id="userContent" style="">
	<!-- HEADER -->
	<!-- END RIBBON -->
	<!-- widget grid -->
	<section id="widget-grid-data" class="">
<section>
				<div class="row" style="margin:0">
				<table cellspacing="0" cellpadding="0" border="0" class="ui-pg-table navtable" id="toolBox">
				<tbody>
				<tr>
   			    <td class="ui-pg-button ui-corner-all" id="getdata" title="" data-original-title="Add new row" onclick="get();">
					<div class="btn btn-sm btn-primary">
					<span class="">获取数据</span>
					</div>
				</td>
				<td class="ui-pg-button ui-state-disabled" style="width:6px;" data-original-title="" title="">
					<span class="ui-separator"></span>
				</td>
<!-- 				<td class="ui-pg-button ui-corner-all" title="" id="refresh" data-original-title="Reload Grid" onclick="getResult();"> -->
<!-- 					<div class="btn btn-sm btn-primary"> -->
<!-- 					<span class="">刷新内容</span> -->
<!-- 					</div> -->
<!-- 				</td> -->
				</tr>
				</tbody>
				</table>
				</div>
			</section>
		<div class="row">
			<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">

				<div class="jarviswidget jarviswidget-color-darken" id="wid-id-0"
					data-widget-editbutton="false">

					<header>
						<span class="widget-icon"><i class="fa fa-table"></i></span>
						<h2>我的数据记录</h2>
					</header>
					<div>
						<div class="widget-body no-padding">
							<div class="widget-body-toolbar">
								<div class="smart-form">
									<section>
										<div class="row" style="margin:0">
										 <section>
<!-- 											<input type="hidden" id="currentUuid" value="${uuid}"> -->
											<label class="label col">数据池</label>
											<div class="col col-1">
			                                    <label class="select">
													<select name="dataPoolTable" id="dataPoolTable">
													<option value="">全部</option>
													<#list pools as s>
														<option value=${s.uid}>${s.containerName}</option>
													</#list>
													</select>
			                                    </label>
			                                </div>
											<label class="label col">名称</label>
											<label class="input col col-1">
												<input type="text" name="itemName" id="itemName" value="" />
											</label>
											<label class="label col">号码</label>
											<label class="input col col-1">
												<input type="text" name="itemPhone" id="itemPhone" value="" />
											</label>
											<label class="label col">地址</label>
											<label class="input col col-2">
												<input type="text" name="itemAddress" id="itemAddress" value="" />
											</label>
											<label class="label col">其它信息</label>
											<label class="input col col-2">
												<input type="text" name="itemJson" id="itemJson" value="" />
											</label>
											<label class="label col">呼叫次数</label>
											<label class="input col col-1">
												<input type="text" name="callTimes" id="callTimes" value="" />
											</label>
								 			<label class="btn btn-sm btn-primary" onclick="getResult();">查找</label>
								 			<input type="hidden" id="currentUuid" value="${uuid}" />
								 			<a href="#" id="dataPool" style="visibility:hidden;position:absolute">移动到</a>
											<input type="hidden" id="dataUuid">
										</section>
										</div>
									</section>
									</div>
									<table id="dt-user-data-pool"
										class="table table-striped table-bordered table-hover"
										data-order='[[ 1, "asc" ]]' width="100%"></table>
								</div>
							</div>
						</div>
					</div>
			</article>
		</div>
	</section>
</div>



<script type="text/javascript">

		$(function(){
			$("#dataPoolTable").select2({
	    		width:"100%",
	    	});
			
			$("#dataPoolTable").change(function() {
				getResult();
			});
			
		});
		
		var itemCheckList={};

		function getResult(){
			$('#dt-user-data-pool').DataTable().ajax.reload(null,false);;
		}
		
		function removeThis(t,i){
		     $(t).parent().remove();

		    itemCheckList[i]=false;
		    $('#dck'+i).click();
		}

// 		function docheckall(){

// 		    var url = getContext()+'userdata/checkDataAll';
// 		    $.post(url,{},function(data){
// 		        for(var i in data.items){
// 	                itemCheckList[data.items[i]]=true;
// 		        }
// 		        docheckallpage();
// 		    },"json");
// 		}

		function docheckallpage(){

		    $("[id^='dck']").each(function(){
		        if(!$(this).is(':checked')){
		            $(this).click();
		        }
		    });
		}

		function docancelAll(){

		    $("[id^='dck']").each(function(){
		        if($(this).is(':checked')){
		            $(this).click();
		        }
		    });

		    itemCheckList={};
		}

		function docheck(uid){

		    if($("#dck"+uid).is(':checked')){
		        itemCheckList[uid]=true;
		    }else{
		        itemCheckList[uid]=false;
		    }
		    addCheckInfo();
		}
		
		function addCheckInfo(){
			var num=0;
			for(var i in itemCheckList) {
				if(itemCheckList[i]==true)
					num++;
			}
			$("#dataCheckInfo").text(num);
		}
		
		function getCheckNum(){
			var num=0;
			for(var i in itemCheckList) {
				if(itemCheckList[i]==true)
					num++;
			}
			return "<span id='dataCheckInfo'>" + num + "</span>";
		}
		
		function getMore(uuid) {
			$.post(getContext()+'userdata/getMoreInfo',{'uuid':uuid},function(data){
				$("#userContent").append(data);
			})
		}
		
		function getback(uuid) {
			$.post(getContext()+'userpooldata/getback',{'dataId':uuid,'dataTable':$("#dataPoolTable").val()},function(data){
				if(data.success){
					$.smallBox({
						title : "操作成功",
						content : "<i class='fa fa-clock-o'></i> <i>操作成功...</i>",
						color : "#659265",
						iconSmall : "fa fa-check fa-2x fadeInRight animated",
						timeout : 2000
					});
					itemCheckList[uuid]=false;
					$('#dt-user-data-pool').DataTable().ajax.reload(null,false);;
				}else{
					$.smallBox({
						title : "操作失败",
						content : "<i class='fa fa-clock-o'></i> <i>操作失败...</i>",
						color : "#C46A69",
						iconSmall : "fa fa-times fa-2x fadeInRight animated",
						timeout : 2000
					});
				}
				$('#dt-user-data-pool').DataTable().ajax.reload(null,false);;
			},"json").error(function(){
				$.smallBox({
					title : "操作失败",
					content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>添加时出现异常...</i>",
					color : "#C46A69",
					iconSmall : "fa fa-times fa-2x fadeInRight animated"
				});
			});
		}

		function getCheckList() {
			var list = [];
			for(var i in itemCheckList) {
				if(itemCheckList[i]==true)
					list.push(i);
			}
			return list;
		}
		
</script>

<script src="${springMacroRequestContext.contextPath}/public/js/data/user-pool-data.js"></script>
<#macro column titles columns>
	<#list titles[1..] as title>
		{ "title" : "${title}","data" : "${columns[title_index+1]}" ,"orderable" : true}, 
	</#list>
	
</#macro>
	<#macro ajaxData names>
	function(param){
	<#list names as n>
   		param.${n} = $("#${n}").val();
   	</#list>
   	}
</#macro>