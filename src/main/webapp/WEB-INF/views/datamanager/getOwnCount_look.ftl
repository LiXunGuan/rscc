	<div id="getOwnCountDiv"></div>
	<div id="getowncountContent" style="">
		<section id="widget-grid-getowncount" class="">
			<div class="row">
				<article class="col-lg-12 col-lg-12 col-lg-12 col-lg-12">
					<div style="margin-bottom: 8px;">
						<label class="btn btn-sm btn-primary" onclick="batchReturnItem()">批量归还</label>
					</div>
					<div class="jarviswidget jarviswidget-color-darken" data-widget-editbutton="false">
						<header>
							<span class="widget-icon"> <i class="fa fa-table"></i>
							</span>
							<h2>数据信息</h2>
						</header>
						<div>
							<div class="widget-body no-padding">
<!-- 									<div class="widget-body-toolbar"> -->
									
<!-- 									</div> -->
								<input type="hidden" name="batchUuid" id="batchUuid" value="${(batchUuid)!''}" />
								<input type="hidden" name="deptUuid" id="deptUuid" value="${(deptUuid)!''}" />
								<table id="dt_basic_getowncount" class="table table-bordered table-hover"></table>
							</div>
						</div>
					</div>
				</article>
			</div>
		</section>
	</div>

<script type="text/javascript">

		var checklist = {};

// 		$('#dialog_getowncount').modal("show");
		
// 		$('#dialog_getowncount').on('hide.bs.modal', function (e) { 
// 			$("#dialog_getowncount").remove();
// 		});
		
		// 全选所有
	    function docheckall(){
		    var url = getContext() + 'deptdata/dept/ownCountAll';
		    var batchUuid = $("#batchUuid").val(); 
		    var deptUuid = $("#deptUuid").val();
		    $.post(url,{batchUuid:batchUuid,deptUuid:deptUuid},function(data){
		    	checklist = {};
		    	if(data != ""){
			    	var chs = data;
			        for(var i in chs){
		            	checklist[chs[i]] = chs[i];
			        }
		    	}
		        docheckallpage();
		    },"json");
		}

		// 全选本页
		function docheckallpage(){
		    $("[id^='ck']").each(function(k,v){
		    	 if(!$(v).is(':checked')){
		            $(v).trigger("click");
		         }
		    });
		};
		
		// 取消选择
		function docancelAll(){
		    $("[id^='ck']").each(function(){
		        if($(this).is(':checked')){
		            $(this).click();
		        }
		    });
		    checklist={};
		}
		
		// 选中某个
		function docheck(id){
		    if($("#ck"+id).is(':checked')){
		        checklist[id] = id;
		    }else{
		        checklist[id] = false;
		    }
		}
		
		function getCheckNum(){
			var num = 0;
			for(var i in checklist) {
				if(checklist[i]){
					num++;
				}
			}
			return "<span>" + num + "</span>";
		}

		// 批量归还
		function batchReturnItem(){
			var dataUuid='';
	    	for (var i in checklist) {
	    		dataUuid += checklist[i]+",";
			}
	    	if (!dataUuid.length>0) {
	    		alert("请先选择数据！");
				return;
			}
	    	$.SmartMessageBox({
	            title : "批量归还",
	            content : "确定批量归还选择的数据吗？",
	            buttons : '[No][Yes]'
	        }, function(ButtonPressed) {
	            if (ButtonPressed === "Yes") { 
	                var url = getContext() + "deptdata/dept/batchReturnData";
	                var batchUuid = $("#batchUuid").val();
	                var deptUuid = $("#deptUuid").val();
	                $.post(url,{dataUuid:dataUuid,batchUuid:batchUuid,deptUuid:deptUuid},function(data){
	                    if(data.success){
	                        $.smallBox({
	                            title : "操作成功",
	                            content : "<i class='fa fa-clock-o'></i> <i>批量归还成功...</i>",
	                            color : "#659265",
	                            iconSmall : "fa fa-check fa-2x fadeInRight animated",
	                            timeout : 2000
	                        });
	                        checklist={};
	                        $('#dt_basic_getowncount').DataTable().ajax.reload(null,false);
	                    }else{
	                        $.smallBox({
	                            title : "操作失败",
	                            content : "<i class='fa fa-clock-o'></i> <i>批量归还失败...</i>",
	                            color : "#C46A69",
	                            iconSmall : "fa fa-times fa-2x fadeInRight animated",
	                            timeout : 2000
	                        });
	                    }
	                },"json").error(function(){
	                    $.smallBox({
	                        title : "操作失败",
	                        content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作失败...</i>",
	                        color : "#C46A69",
	                        iconSmall : "fa fa-times fa-2x fadeInRight animated"
	                    });
	                });
	            }
	        });
		}
		
		$('#users').select2({
           allowClear : true,
           width: '99%'
        });
		
		function saveUser(){
			var dataUuid='';
	    	for (var i in checklist) {
	    		dataUuid += checklist[i]+",";
			}
	    	if (!dataUuid.length>0) {
	    		alert("请先选择数据！");
				return;
			}
	    	$.SmartMessageBox({
	            title : "批量分配",
	            content : "确定批量分配选择的数据吗？",
	            buttons : '[No][Yes]'
	        }, function(ButtonPressed) {
	            if (ButtonPressed === "Yes") { 
	                var url = getContext() + "deptdata/dept/batchAllotData";
	                var user = $("#users").val();
	                var batchUuid = $("#batchUuid").val();
	                var deptUuid = $("#deptUuid").val();
	                $.post(url,{dataUuid:dataUuid,user:user,batchUuid:batchUuid,deptUuid:deptUuid},function(data){
	                    if(data.success){
	                        $.smallBox({
	                            title : "操作成功",
	                            content : "<i class='fa fa-clock-o'></i> <i>批量分配成功...</i>",
	                            color : "#659265",
	                            iconSmall : "fa fa-check fa-2x fadeInRight animated",
	                            timeout : 2000
	                        });
	                        checklist={};
	                        $('#dt_basic_getowncount').DataTable().ajax.reload(null,false);
	                    }else{
	                        $.smallBox({
	                            title : "操作失败",
	                            content : "<i class='fa fa-clock-o'></i> <i>批量分配失败...</i>",
	                            color : "#C46A69",
	                            iconSmall : "fa fa-times fa-2x fadeInRight animated",
	                            timeout : 2000
	                        });
	                    }
	                },"json").error(function(){
	                    $.smallBox({
	                        title : "操作失败",
	                        content : "<i class='fa fa-clock-o'></i> <i style='font-size: 15px;'>操作失败...</i>",
	                        color : "#C46A69",
	                        iconSmall : "fa fa-times fa-2x fadeInRight animated"
	                    });
	                });
	            }
	        });
		};
		
</script>

<script src="${springMacroRequestContext.contextPath}/public/js/databatch/getOwnCount.js"></script>


