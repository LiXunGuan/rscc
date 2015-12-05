<style>
	#wid-id-5 header{
		    background: #404040;
		    color: #fff;
	}
	.tabs-left #demo-pill-nav{
		text-align: center;
	}
	.tab-content{
		margin-left: 10%;
		
	}
</style>
<div class="jarviswidget" id="wid-id-5" data-widget-colorbutton="" data-widget-editbutton="false" data-widget-fullscreenbutton="false" data-widget-custombutton="false" data-widget-sortable="false">
	<header>
		<h2>字典管理</h2>
	</header>

	<!-- widget div-->
	<div>

		<!-- widget edit box -->
		<div class="jarviswidget-editbox">
			<!-- This area used as dropdown edit box -->

		</div>
		<!-- end widget edit box -->

		<!-- widget content -->
		<div class="widget-body">

			<div class="tabs-left">
				<ul class="nav nav-tabs tabs-left" id="demo-pill-nav">
					<li class="active">
						<a href="#tab-r1" data-toggle="tab"> 意向客户 </a>
					</li>
<!-- 					<li> -->
<!-- 						<a href="#tab-r2" data-toggle="tab"> 成交客户 </a> -->
<!-- 					</li> -->
<!-- 					<li> -->
<!-- 						<a href="#tab-r3" data-toggle="tab"> 暂留 </a> -->
<!-- 					</li>	 -->
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="tab-r1">
						<div class="row addable hidden">
							<input type="hidden" name="intentUid" value=""/>
		     				<section class="col col-5">
								<label class="input">
									<input name="intentName" />
		                		</label>
		              		</section>
		            		<section class="col col-5">
								<label class="input">
									<input name="intentInfo" />
		                		</label>
		              		</section>
		              		<section class="col col">
		             			<button type="button" class="btn btn-success btn-sm" onclick="modify(this);">修改</button>
		              		</section>
		              		<section class="col col-1">
	              				<button type="button" class="btn btn-danger btn-sm" onclick="deleteint(this);">删除</button>
		              		</section>
		           		</div>
						 <form id="messageinfoa" action="${springMacroRequestContext.contextPath}/dataintent/save" class="smart-form" method="post">
		                    <fieldset>
			     				<div class="row">
				     				<div class="col col-5">
					        		    <label class="label">意向名称<sup>*</sup></label>
				              		</div>
				            		<div class="col col-6">
					        		    <label class="label">意向描述</label>
				              		</div>
			              		</div>
			              		<div class="row">
				     				<section class="col col-5">
										<label class="input">
											<input name="intentName" id="intentName"/>
				                		</label>
				              		</section>
				            		<section class="col col-5">
										<label class="input">
											<input name="intentInfo" id="intentInfo"/>
				                		</label>
				              		</section>
				              		<section class="col col-1">
				              			<button type="msubmita" class="btn btn-primary btn-sm">添加</button>
				              		</section>
			              		</div>
			              	</fieldset>
					      </form>
					      	<div class = "smart-form">
					      		<fieldset>
					      			<#list dis as di>
					              		<div class="row">
					              			<input type="hidden" name="intentUid" value="${di.uid}"/>
						     				<section class="col col-5">
												<label class="input">
													<input name="intentName" value="${di.intentName}"/>
						                		</label>
						              		</section>
						            		<section class="col col-5">
												<label class="input">
													<input name="intentInfo" value="${di.intentInfo}"/>
						                		</label>
						              		</section>
						              		<section class="col col">
						             				<button type="button" class="btn btn-success btn-sm" onclick="modify(this);">修改</button>
						              		</section>
						              		<section class="col col-1">
						              			<button type="button" class="btn btn-danger btn-sm" onclick="deleteint(this);">删除</button>
						              		</section>
						           		</div>
					           		</#list>
				           		</fieldset>
				           	</div>
					</div>
					<!--<div class="tab-pane" id="tab-r2">
						<p>
							这里是第二个tab
						</p>
					</div>
					<div class="tab-pane" id="tab-r3">
						<p>
							这是第三个tab
						</p>
					</div>-->
				</div>
			</div>
		</div>
	</div>
</div>
	<script type="text/javascript">
		
		function resetinput(){
			$("#intentName").val('');
			$("#intentInfo").val('');
		}
		
		function addLine(uuid){
	    	$("#tab-r1").find('fieldset').eq(1).append($(".addable").clone().removeClass("addable").removeClass("hidden"));
	    	
	    	$(".row:last").children("input[type='hidden']").val(uuid);
	    	$(".row:last").children().find('input').eq(0).val($('#intentName').val());
	    	$(".row:last").children().find('input').eq(1).val($('#intentInfo').val());
	    	
	    }
		
		$(document).ready(function(){
			
			/** 校验字段  **/
            var validator =$('#messageinfoa').validate({
            	rules:{
            		intentName:{
            			required:true,
            			remote : {
            				url:"${springMacroRequestContext.contextPath}/dataintent/checkname/intentName",
            				type:"post",
            				dataType:"json"
            			}
            		}
            	},
            	messages:{
            		intentName:{
            			required:"至少输入一个名称",
            			remote:"该名称已存在"
            		}
            	},
            	//element是验证未通过的当前表单元素,error为错误后的提示信息
            	 errorPlacement : function(error, element) {
                     error.insertAfter(element.parent());
                 }
            });
			
				/* 提交按钮单击事件  */
				$('#msubmita').click(function(){
				    if($('#messageinfoa').valid()){
				        $('#msubmita').attr("disabled",true);
				        
				        $('#messageinfoa').submit();
				    }
				});
				
				$('#messageinfoa').ajaxForm({
				    dataType:"json",
				    success: function(data) {
				        if(data.success){
				            $.smallBox({
				                title : "操作成功",
				                content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
				                color : "#659265",
				                iconSmall : "fa fa-check fa-2x fadeInRight animated",
				                timeout : 2000
				            });
				            addLine(data.uuid);
				            resetinput();
				        }
				    },
				    submitHandler : function(form) {
				        $(form).ajaxSubmit({
				            success : function() {
				                $("#messageinfoa").addClass('submited');
				            }
				        });
				    },
				    error: function(XMLHttpRequest,textStatus , errorThrown){
				        if(textStatus=='error'){
				            $('#dialogDataIntent').modal('hide');
				            $.smallBox({
				                title : "操作失败",
				                content : "<i class='fa fa-clock-o'></i> <i>操作失败</i>",
				                color : "#C46A69",
				                iconSmall : "fa fa-times fa-2x fadeInRight animated",
				                timeout : 2000
				            });
				        }
				    }
				});
		});
		
		function modify(data){
			
			var itentid = $(data).parent().parent().children(":hidden");
			var inname = $(itentid).nextAll().find('input').first();
			var ininfo = $(itentid).nextAll().find('input').eq(1);
			$.post(getContext()+"dataintent/update",{uid:$(itentid).val(),intentName:$(inname).val(),intentInfo:$(ininfo).val()},function (data){
				
			        if(data.success){
			            $.smallBox({
			                title : "操作成功",
			                content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
			                color : "#659265",
			                iconSmall : "fa fa-check fa-2x fadeInRight animated",
			                timeout : 2000
			            });
			        }else{
			            $.smallBox({
			                title : "操作失败",
			                content : "<i class='fa fa-clock-o'></i> <i>操作失败</i>",
			                color : "#C46A69",
			                iconSmall : "fa fa-times fa-2x fadeInRight animated",
			                timeout : 2000
			            });
			        }
				},"json");
			}
		
		function deleteint(data){
			
			var itentid = $(data).parent().siblings('input');
			$.post(getContext()+"dataintent/delete",{uid:$(itentid).val()},function(data){
				if(data.success){
		            $.smallBox({
		                title : "操作成功",
		                content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
		                color : "#659265",
		                iconSmall : "fa fa-check fa-2x fadeInRight animated",
		                timeout : 2000
		            });
		            
		            $("input[value='"+data.uuid+"']").parent().closest('div').remove();
		        }else{
		            $.smallBox({
		                title : "操作失败",
		                content : "<i class='fa fa-clock-o'></i> <i>操作失败,请检查意向是否被占用</i>",
		                color : "#C46A69",
		                iconSmall : "fa fa-times fa-2x fadeInRight animated",
		                timeout : 2000
		            });
		        }
			},"json");
		}
	</script>