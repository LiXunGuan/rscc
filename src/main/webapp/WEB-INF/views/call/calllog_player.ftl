
<link href= "${springMacroRequestContext.contextPath}/assets/css/skin/blue.monday/jplayer.blue.monday.css" rel ="stylesheet" type= "text/css" />
<script type= "text/javascript" src= "${springMacroRequestContext.contextPath}/assets/js/jPlayer/jquery.jplayer.min.js" ></script >

<div class="modal fade" id="dialog_calllog_music">
	<div class="modal-dialog" style="width: 490px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
				<h4 class="modal-title">呼叫记录</h4>
			</div>
			<div class="modal-body" style="">
				<section>
					<label>播放呼叫记录</label>
					<div id="jquery_jplayer_1" class="jp-jplayer"></div>
					<#include "../player.ftl">            		
				</section>
			</div>
			<div class="modal-footer">
				<a target='_blank' href="${urls!''}" class="btn btn-primary">下载</a>
				<button type="button" class="btn btn-default" data-dismiss="modal" id="mclose">关 闭</button>
			</div>
		</div>
	</div>
</div>
	
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/jPlayer/audio_media.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
	
	
		/* 显示弹框  */
		$('#dialog_calllog_music').modal("show");
		
		/* 打开窗口的回调函数  */
		$('#dialog_calllog_music').on('shown.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
			if("${playconfig}" == "open"){
			 	$("#mplay").click();
			}else{
				$("#stopclick").click();
			}
		});
		
		/* 关闭窗口的回调函数  */
		$('#dialog_calllog_music').on('hidden.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
			$("#stopclick").click();
			$("#dialog_calllog_music").remove();
		});
		
		/* 点击关闭停止播放语音 */
		$('#mclose').click(function(){
			$("#stopclick").click();
		});
		
	     var id = "#jquery_jplayer_1";
	     var bubble = {
	         title: "Bubble",
	         mp3:"${urls!''}"
	     }
	     var options = {
	           swfPath: "js",
	           supplied: "mp3,oga",
	           wmode: "window",
	           smoothPlayBar: true,
	           keyEnabled: true,
	           remainingDuration: true,
	           toggleDuration: true
	     };
	
	     var myAndroidFix = new jPlayerAndroidFix(id, bubble, options);
	     myAndroidFix.play();
	
	})

</script>

