	<div id="getBlackListCountDiv"></div>
	<div id="getBlackListCountContent" style="">
		<section id="widget-grid-getBlackListCount" class="">
				<div class="row">
					<article class="col-lg-12 col-lg-12 col-lg-12 col-lg-12">
						<div style="margin-bottom: 8px;">
						</div>
						<div class="jarviswidget jarviswidget-color-darken" data-widget-editbutton="false">
							<header>
								<span class="widget-icon"> <i class="fa fa-table"></i>
								</span>
								<h2>数据信息</h2>
							</header>
							<div>
								<div class="widget-body no-padding">
<!-- 									<div class="widget-body-toolbar"></div> -->
									<input type="hidden" name="batchUuid" id="batchUuid" value="${(batchUuid)!''}" />
									<input type="hidden" name="deptUuid" id="deptUuid" value="${(deptUuid)!''}" />
									<table id="dt_basic_getBlackListcount" class="table table-bordered table-hover"></table>
								</div>
							</div>
						</div>
					</article>
				</div>
		</section>
	</div>

<script type="text/javascript">
		
</script>

<script src="${springMacroRequestContext.contextPath}/public/js/databatch/getBlackListCount.js"></script>


