
<style>
.table {
	width: 100%;
	max-width: 100%;
	margin-bottom: 0px;
}
</style>

<div id="main" role="main">

	<div id="forModel" class="table table-striped table-hover" style="padding-top: 2px;">
		<div id="tabs2">
			<ul></ul>
		</div>

	</div>

	<!-- MAIN CONTENT -->
	<div id="content">
	
	
	</div>

	<script type="text/javascript">
		$(function() {
			document.onkeydown = function(e) {
				var ev = document.all ? window.event : e;
				if (ev.keyCode == 13) {
					if(typeof(getResult) == 'function')
						getResult();
				}
			}
		});
	</script>

</div>
