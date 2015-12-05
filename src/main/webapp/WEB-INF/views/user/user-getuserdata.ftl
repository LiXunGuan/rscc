<div class="modal fade" id="getuserdata">
	<div class="modal-dialog" style="width: 75%;">
		<div class="modal-content">
			<div class="modal-body">
				<#if dataType?? >
					<#if dataType=="task">
					
						<#include "/datamanager/new_user_data.ftl" >
					<#elseif dataType=="intent">
						
						<#include "/datamanager/data_intend_index.ftl" >
						
					<#elseif dataType=="cstm">
					
						<#include "/cstm/cstm-index.ftl" >
					</#if>
				<#else>
					<label><span style="color:red;font-size: large;">说明：</span>该页面中展示的数据为批量删除中不能删除的用户数据</br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如需删除，请在该数据栏中点击详情，对用户名下的数据进行处理</label>
					</br>
					<#if uuids??>
						<#list uuids as u>
							<span style="font-size: 19px;font-weight: 400;margin: 20px 0; line-height: normal;">${u}<t style="color:red;margin-left: 8px;">|</t> </span>
						</#list>
					</#if>
				</#if>
				<label data-dismiss="modal" class="btn btn-primary" id="doClose" style="margin-left: 95%;">关闭</label>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		
		$('#getuserdata').modal("show");
		
		$('#getuserdata').on('hidden.bs.modal',function(){
			$('#getuserdata').remove();
		}); 
		
	});
	
</script>