<div class="modal fade" id="new_dialog_content_music">
	<div class="modal-dialog" style="width: 490px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">
					<strong>关闭</strong>
				</button>
				<h4 class="modal-title">音频试听</h4>
			</div>
			<div class="modal-body" style="">
				<div id="jquery_jplayer_1" class="jp-jplayer"></div>
				<div id="jp_container_1" class="jp-audio" role="application"
					aria-label="media player">
					<div class="jp-type-single">
						<div class="jp-gui jp-interface">
							<div class="jp-controls">
								<button class="jp-play" role="button" tabindex="0"></button>
								<button class="jp-stop" role="button" tabindex="0">stop</button>
							</div>
							<div class="jp-progress">
								<div class="jp-seek-bar">
									<div class="jp-play-bar"></div>
								</div>
							</div>
							<div class="jp-volume-controls">
								<button class="jp-mute" role="button" tabindex="0">mute</button>
								<button class="jp-volume-max" role="button" tabindex="0">max
									volume</button>
								<div class="jp-volume-bar">
									<div class="jp-volume-bar-value"></div>
								</div>
							</div>
							<div class="jp-time-holder">
								<div class="jp-current-time" role="timer" aria-label="time">&nbsp;</div>
								<div class="jp-duration" role="timer" aria-label="duration">&nbsp;</div>
								<div class="jp-toggles">
									<button class="jp-repeat" role="button" tabindex="0">repeat</button>
								</div>
							</div>
						</div>
						<div class="jp-details">
							<div class="jp-title" aria-label="title">&nbsp;</div>
						</div>
					</div>
				</div>


			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
$(document).ready(function(){

	/* 显示弹框  */
	$('#dialog_content_music').modal("show");
	
	/* 打开窗口的回调函数  */
	$('#dialog_content_music').on('shown.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
		$("#mplay").click();
	});
	
	/* 关闭窗口的回调函数  */
	$('#dialog_content_music').on('hidden.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
		$("#stopclick").click();
	});
	
	/* 点击关闭停止播放语音 */
	$('#mclose').click(function(){
		$("#stopclick").click();
	});
	
	$("#jquery_jplayer_1").jPlayer({
		ready: function (event) {
			$(this).jPlayer("setMedia", {
				title: "Bubble",
				m4a: "${urls!''}",
				oga: "${urls!''}"
			});
		},
		swfPath: "${springMacroRequestContext.contextPath}/assets/js/jPlayer",
		supplied: "m4a, oga",
		wmode: "window",
		useStateClassSkin: true,
		autoBlur: false,
		smoothPlayBar: true,
		keyEnabled: true,
		remainingDuration: true,
		toggleDuration: true
	});
});
</script>

