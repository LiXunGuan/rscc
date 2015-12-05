	<style>
		form span {
			padding-left: 3%;
		}
		
		form span label.control-label.col-col-1 {
			font-size: 16px;
			color: #3276B1;
		}
		form span label.control-label.col-col-5 {
			font-family: monospace;
			font-size: 16px;
		}
		/* 字头样式 */
		form span label.control-label.col-col-5 label {
			font-family: monospace;
			font-size: 14px;
			color: black;
		}
	</style>
	
	<form action="dummy.php" method="POST" class="form-horizontal" id="email-compose-form">
		<hr/>
			<span>
				<label class="control-label col-col-1"><strong>Topic:</strong></label>
				<label class="control-label col-col-5"><label>${cstmservice.cstmserviceName!''}</label></label>
				<span class="col col-12"></span>
				<label class="control-label col-col-1"><strong>指派人:</strong></label>
				<label class="control-label col-col-5"><label>${cstmservice.cstmserviceAssignee!''}</label></label>
			</span>
		<hr/>
			<span>
				<label class="control-label col-col-1"><strong>发起对象:</strong></label>
				<label class="control-label col-col-5"><label>${cstmservice.cstmserviceReporter!''}</label></label>
				<span class="col col-12"></span>
				<label class="control-label col-col-1"><strong>发起时间:</strong></label>
				<label class="control-label col-col-5"><label>${cstmservice.cstmserviceStartTime?datetime!''}</label></label>
			</span>
		<hr>
			<span>
				<label class="control-label col-col-1"><strong>问题描述:</strong></label>
				<label class="control-label col-col-5"><label>${cstmservice.cstmserviceDescription!''}</label></label>
			</span>
		<hr>
			<span>
				<label class="control-label col-col-1"><strong>语音记录:</strong></label>
				<label class="control-label col-col-5"><label>${cstmservice.cstmserviceVoiceRecord!''}</label></label>
			</span>
			
		<#if comments??>
			<#list comments as comment>
				<hr>
					<span>
						<label class="control-label col-col-1"><strong>指派人评论</strong><label class="note" style="color: #3276B1">(${comment_index?number+1}):</label></label>
						<label class="control-label col-col-5"><label>${comment.commentTime?datetime!''}</label></label>
						<hr>
						<label class="control-label col-col-5" style="padding-left: 5%;"><label>${comment.commonts!''}</label></label>
					</span>
			</#list>
		<#else>
			<hr>
				<span>
					<label class="control-label col-col-1"><strong>指派人评论:</strong></label>
					<label class="control-label col-col-5"><label>该问题暂无评论！</label></label>
				</span>
		</#if>
<!-- 			<hr> -->
<!--               <label class="control-label col-col-1">添加评论</label> -->
<!--               <span> -->
<!-- 	              <textarea class="control-textarea col-col-5" type="text" style="height:60px;overflow-y:visible;" name="commonts">${(check.remark)!''}</textarea> -->
<!--               </span>	 -->
		
		
		
		
	</form>
