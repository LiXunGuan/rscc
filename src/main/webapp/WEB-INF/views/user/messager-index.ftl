<style>
selected {
	background-color: blue;
}
</style>
<div id="tmpDiv"></div>
<div id="mainContent" style="">
	<!-- HEADER -->
	<!-- END RIBBON -->
	<!-- widget grid -->
	<section id="widget-grid-message" class="">

		<div class="row">
			<article class="col-xs-3 col-sm-3 col-md-3 col-lg-3">

				<div class="jarviswidget jarviswidget-color-darken" id="wid-id-0"
					data-widget-editbutton="false">

					<header>
						<span class="widget-icon"><i class="fa fa-table"></i></span>
						<h2>客服记录</h2>
					</header>

					<div>
						<div class="widget-body no-padding">
							<div class="widget-body-toolbar">
								<div class="smart-form">
									<section>
										<div class="row"></div>
									</section>
									<table id="dt_basic_cstmservice"
										class="table table-striped table-bordered table-hover"
										data-order='[[ 0, "asc" ]]' width="100%"></table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</article>

			<article class="col-xs-9 col-sm-9 col-md-9 col-lg-9">

				<div class="jarviswidget jarviswidget-color-darken" id="wid-id-0"
					data-widget-editbutton="false">

					<header>
						<span class="widget-icon"><i class="fa fa-table"></i></span>
						<h2>客服详情</h2>
					</header>

					<div>
						<div class="widget-body no-padding" id="detail-info"></div>
					</div>
				</div>
			</article>
			<div>
				<button id="disconnect" disabled="disabled" onclick="disconnect();">Disconnect</button>
				<button id="echo" onclick="echo();">Echo message</button>
				<select name="group" id="group">
					<option value="0" selected="selected">---请选择发送分组---</option>
				</select>
				<select name="user" id="user">
					<option value="0" selected="selected">---请选择发送用户---</option>
				</select>
			</div>
			<div>
				<textarea id="message" style="width: 350px">Here is a message!</textarea>
			</div>

		</div>
	</section>
</div>

<script src="http://cdn.bootcss.com/sockjs-client/0.3.4/sockjs.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$.post(getContext() + "user/messager/group",function(data){
			for(var i in data){
				$("#group").append("<option value='" + data[i] + "'>" + data[i] + "</option>")
				}
			},"json");
		$("#group").change(function(){
			$.post(getContext() + "user/messager/user",{"group":$("#group").val()},function(data){
	    		$("#user").empty();
				$("#user").append("<option value='0'>---请选择发送用户---</option>");
				for(var i in data){
					$("#user").append("<option value='" + data[i] + "'>" + data[i] + "</option>")
					}
				},"json");
			});
	});

	function echo() {
		if (ws != null) {
			var message = {
				"toURL" : $("#group").val(),
				"toUser" : $("#user").val(),
				"json" : document.getElementById('message').value
			};
			ws.send(JSON.stringify(message));
		} else {
			alert('connection not established, please connect.');
		}
	}
</script>
