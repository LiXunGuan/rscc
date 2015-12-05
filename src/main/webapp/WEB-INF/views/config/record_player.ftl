
<link href= "${springMacroRequestContext.contextPath}/assets/css/skin/blue.monday/jplayer.blue.monday.css" rel ="stylesheet" type= "text/css" />
<script type= "text/javascript" src= "${springMacroRequestContext.contextPath}/assets/js/jPlayer/jquery.jplayer.min.js" ></script >
<script src="http://api.html5media.info/1.1.6/html5media.min.js"></script>

<div class="modal fade" id="dialog_content_music">
	<div class="modal-dialog" style="width: 490px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
				<h4 class="modal-title">音频试听</h4>
			</div>
			<div class="modal-body" style="">

<!-- 				<section> -->
<!-- 					<label>播放语音</label> -->
<!-- 					<div id="jquery_jplayer_1" class="jp-jplayer"></div> -->
						

<!--             <div id ="jp_container_1" class="jp-audio"> -->
<!--                  <div class ="jp-type-single"> -->
<!--                       <div class ="jp-gui jp-interface" style="width: 400px;"> -->
<!--                             <ul class ="jp-controls"> -->
<!--                                  <li ><a href ="javascript();" class= "jp-play" tabindex ="1" id="mplay">播放</a></li> -->
<!--                                  <li ><a href ="javascript();" class= "jp-pause" tabindex ="1">暂停</a></li> -->
<!--                                  <li ><a href ="javascript();" class= "jp-stop" tabindex ="1" id="stopclick">停止</a></li> -->
<!--                                  <li ><a href ="javascript();" class= "jp-mute" tabindex ="1" title="mute">音量 </a></li> -->
<!--                             </ul > -->
<!--                             <div class ="jp-progress"> -->
<!--                                  <div class ="jp-seek-bar"> -->
<!--                                       <div class= "jp-play-bar"></div > -->
<!--                                  </div > -->
<!--                             </div > -->
<!--                             <div class ="jp-volume-bar"> -->
<!--                                  <div class= "jp-volume-bar-value"></div > -->
<!--                             </div > -->
<!--                             <div class ="jp-time-holder"> -->
<!--                                  <div class= "jp-current-time"></div > -->
<!--                                  <div class ="jp-duration"></div> -->

<!--                                  <ul class ="jp-toggles"> -->
<!--                                       <li ><a href ="javascript:;" class= "jp-repeat" tabindex ="1" title="repeat">repeat</a></li> -->
<!--                                       <li ><a href ="javascript:;" class= "jp-repeat-off" tabindex ="1" title="repeat off">repeat off</a></li> -->
<!--                                  </ul > -->
<!--                             </div > -->
<!--                       </div > -->
<!--                  </div > -->
<!--             </div> -->
<!-- 				</section> -->

				<audio controls="controls">  
<!-- 				   <source src="music.ogg" />  -->
				   <source src="${urls!''}" /> 
<!-- 				   <source src="music.wav" />  -->
				</audio> 

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
	
	
     var id = "#jquery_jplayer_1";
     var bubble = {
         title: "Bubble",
//       mp3: "http://192.168.1.190:18080/663ffb48-8f00-11e4-aeda-6b69be14d54a.mp3"
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

