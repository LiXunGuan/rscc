<section id="widget-grid-sysconfig">
    <div class="row">
        <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
            <br/>
            <div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-3" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-colorbutton="false"
                 data-widget-collapsed="false">
                <header>
                    <span class="widget-icon"> <i class="fa fa-table"></i> </span>
                    <h2>默认路由</h2>
                </header>
                <div>
                    <div class="jarviswidget-editbox">
                    </div> 
                    
                    <div class="widget-body no-padding">
                        <div class="widget-body-toolbar" > </div>
                        <form action="${springMacroRequestContext.contextPath}/config/sysconfig/save" method="post" class="smart-form" id="coreForm" style="padding-top: 15px">
							<input type="hidden" id="id" name="id" value="${(id)!''}">
							<section>
								<div class="row">
								<label class="label col col-1" style="margin-left:20px;">默认呼出接入号</label>
								<label class="select col col-3">
									<select id="accessnumber" name="accessnumber">
										<option value="">请选择</option> 
										<#if accessNumberls??>
										<#list accessNumberls as a> 
										<#if accessnumber??> 
											<#if (a.accessnumber)==accessnumber>
												<option value="${a.accessnumber}" selected="selected">${a.accessnumber}</option>
											<#else>
												<option value="${a.accessnumber}">${a.accessnumber}</option> 
											</#if>
										<#else>
											<option value="${a.accessnumber}">${a.accessnumber}</option> 
										</#if> 
										</#list>
										</#if>
									</select>
								</label>
								</div>
							</section>
							<section>
								<div class="row">
									<label class="label col col-1" style="margin-left:20px;">自定义被叫路由配置</label>
									<label class="textarea col col-3"> 
										<textarea id="content" name="content" class="custom-scroll" rows="5">${content!''}</textarea>
									</label>
								</div>
							</section>
					 </form>
				  </div>
				  <div class="modal-footer">
						<button type="button" id="saveButton" class="btn btn-primary" style="float: left;" >保存</button>
				  </div>
              </div>
           </div>
        </article>
     </div>
</section>

<script type="application/javascript">

	$('#accessnumber').select2({
	    allowClear : true,
	    width:'99%'
	});
	
	function gettitleshow(){
		var url=getContext() + "config/sysconfig/menushow"
		$.post(url,function(data){
	 		$("#titleshow").html(data.titleshow);
		},'json');
	}
	
		
	$(document).ready(function(){
		
// 		gettitleshow();
		
// 		jQuery.validator.addMethod("cont", function(value, element) {     
// 			var rule = /^\{(.+:.+,*){1,}\}$/;  
// 		    return this.optional(element) || rule.test(value);  
// 		}, "请正确填写自定义被叫路由配置");
		
		/** 校验字段  **/
		var validator = $('#coreForm').validate({
		    rules: {
		    	accessnumber : {
		    		required : true
				}
		    },errorPlacement: function (error, element) {
		        error.insertAfter(element.parent());
		    }
		});
	
		$('#coreForm').ajaxForm({
			dataType : "json",
			success : function(data) {
				if (data.success) {
					$.smallBox({
						title : "操作成功",
						content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
						color : "#659265",
						iconSmall : "fa fa-check fa-2x fadeInRight animated",
						timeout : 2000
					});
					
// 					window.location.reload( true);
				}
// 				validator.resetForm();
			},
			submitHandler : function(form) {
				$(form).ajaxSubmit({
					success : function() {
					}
				});
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				if (textStatus == 'error') {
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
	})
	
	/* 提交按钮单击事件  */
	$('#saveButton').click(function() {
		var cont = $("#content").val();
		$.post(getContext() + "config/sysconfig/checkContent", {content:cont}, function(data){
			if(!data.success){
				$('#content').nextAll().remove();
		        $('#content').after("<label style='color: red' class='marked'>请正确输入数据格式！！</label>");
		        return false;
			}else{
				if ($('#coreForm').valid()) {
					$('#coreForm').submit();
				}
			}
		},"json");
	});

</script>
