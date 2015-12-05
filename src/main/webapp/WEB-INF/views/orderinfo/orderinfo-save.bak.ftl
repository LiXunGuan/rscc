	
	<style>
		.smart-form .label {
			display: inline-block;
		}
		
		.tag.label.label-info {
			color: white;
		}
		
		.row label.col.col-2 {
			text-align: right;
		}
		
		.select2-hidden-accessible {
			display: none;
		}
	</style>
	
	<#macro showReceive maps>
		<#if maps??>
			<#assign lineNumber = 0>
			<#list maps?keys as key>
				<#if (maps[key].allowEmpty) == "0">
					<#assign requireds = "required">
				<#else>
					<#assign requireds = "">
				</#if>
				<#if (maps[key].columnNameDB) == "receive_user_name" || (maps[key].columnNameDB) == "receive_user_address" || (maps[key].columnNameDB) == "receive_user_mobile">
					<section>
						<div class="row"> 
							<label class="label col col-2">${maps[key].columnName}</label>
							<label class="input col col-3">
								<input name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}" class="${requireds}"/>
							</label>
						</div>
					</section>
				</#if>
			</#list>
		</#if>
	</#macro>
	
	<#macro showPay maps>
		<#if maps??>
			<#assign lineNumber = 0>
			<#list maps?keys as key>
				<#if (maps[key].allowEmpty) == "0">
					<#assign requireds = "required">
				<#else>
					<#assign requireds = "">
				</#if>
				<#if (maps[key].columnNameDB) == "pay_status" || (maps[key].columnNameDB) == "freight">
					<section>
						<div class="row"> 
							<label class="label col col-2">${maps[key].columnName}</label>
							<label class="input col col-3">
								<input name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}" class="${requireds}"/>
							</label>
						</div>
					</section>
				</#if>
			</#list>
		</#if>
	</#macro>
	
	<#macro showInvoice maps>
		<#if maps??>
			<#assign lineNumber = 0>
			<#list maps?keys as key>
				<#if (maps[key].allowEmpty) == "0">
					<#assign requireds = "required">
				<#else>
					<#assign requireds = "">
				</#if>
				<#if (maps[key].columnNameDB) == "invoice_type" || (maps[key].columnNameDB) == "invoice_info">
					<section>
						<div class="row"> 
							<label class="label col col-2">${maps[key].columnName}</label>
							<label class="input col col-3">
								<input name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}" class="${requireds}"/>
							</label>
						</div>
					</section>
				</#if>
			</#list>
		</#if>
	</#macro>
	
	<div class="modal fade" id="dialog_cstm_tags">
	    <div class="modal-dialog" style="width: 50%;">
	        <div class="modal-content" >
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
	                <h4 class="modal-title">订单信息</h4>
	            </div>
	            <div class="modal-body"  >
	                <form id="tagsInfos" action="${springMacroRequestContext.contextPath}/orderinfo/save" class="smart-form" method="post">
	                 	<fieldset style="padding-top: 0px;">
							<strong>收货人信息</strong>
							<@showReceive maps></@showReceive>
						</fieldset>
						<fieldset style="padding-top: 10px;">
							<strong>支付方式</strong>
							<@showPay maps></@showPay>
						</fieldset>
						<fieldset style="padding-top: 10px;">
							<strong>发票信息</strong>
							<@showInvoice maps></@showInvoice>
						</fieldset>
						<#if orderinfo??>
							<fieldset style="padding-top: 10px;">
								<strong>订单状态</strong>
								<section>
									<div class="row">
										<label class="label col col-2">订单状态</label>
										<div class="form-group col col-lg-3">
											<label class="select">
												<select name="order_status" id="order_status">
													<#if orderstatus??>
														<#list orderstatus?keys as key>
															<#if order_status?? && order_status == key>
																<option value="${key}" selected="selected">${orderstatus[key]}</option>
															<#else>
																<option value="${key}">${orderstatus[key]}</option>
															</#if>
														</#list>
													</#if>
												</select>
											</label>
										</div>
									</div>
								</section>
							</fieldset>
						</#if>
						<fieldset style="padding-top: 10px;">
							<strong>商品清单</strong>
							<section>
								<div class="row" style="float: right;"> 
									<div class="input col col-10">
										<label class="btn-sm btn-info col col" onclick="addproduct();" style="text-align:center; width: 100px;">
											添加商品
										</label>
									</div>
								</div>
							</section>
							
<!-- 							<section id="appendContent"> -->
<!-- 								    <div class="row" style="margin-bottom: 15px;"></div> -->
<!-- 								    <#if ods??> -->
<!-- 									    <div id="ods"> -->
<!-- 											<#list ods as od> -->
<!-- 										    	<section> -->
<!-- 													<div class="row"> -->
<!-- 														<label class="label col col-2">商品名</label> -->
<!-- 														<div class="input col col-3"> -->
<!-- 															<label class="select"> -->
<!-- 																<select name="pName" > -->
<!-- 																	<#if plist??> -->
<!-- 															    		<#list plist as pl> -->
<!-- 															    			<#if od.productId == pl.productId> -->
<!-- 																    			<option value="${pl.productId}" selected="selected">${pl.productName}</option>												    					 -->
<!-- 															    			<#else> -->
<!-- 																    			<option value="${pl.productId}">${pl.productName}</option> -->
<!-- 															    			</#if> -->
<!-- 															    		</#list> -->
<!-- 																	</#if> -->
<!-- 																</select> -->
<!-- 															</label> -->
<!-- 								                   		</div> -->
<!-- 								                   		<label class="label col col-1">数量</label> -->
<!-- 										       			<div class="input col col-2"> -->
<!-- 										       				<label class="input"> -->
<!-- 										       					<input name="pNumber" placeholder="数量" value="${(od.productNumber)!''}"> -->
<!-- 										       				</label> -->
<!-- 								                   		</div> -->
<!-- 														<label class="label btn-sm btn-danger" style="float: left; color:white" onclick="$(this).closest('div').remove()">移除</label> -->
<!-- 														<label class="btn btn-danger btn-sm" onclick="$(this).closest('div').remove()" ><span class="fa fa-trash-o"></span></label> -->
<!-- 							                   		</div> -->
<!-- 							                   	</section> -->
<!-- 											</#list> -->
<!-- 				                   		</div> -->
<!-- 									</#if> -->
<!-- 							</section> -->

						</fieldset>
						<input type="hidden" name="uid" id="uid" value="${uid!''}">
						<input type="hidden" name="order_id" id="order_id" value="${serialized!''}">
	                </form>
	                
	                <section id="appendContent">
					    <div class="row" style="margin-bottom: 15px;"></div>
				    </section>
	                
               		<#list plist as pl>
		    			<input name="${pl.productId}" value="${pl.productNumber}" type="hidden">
		    		</#list>
	                
	                <section class="addable hidden">
		                <div class="form-group row">
	                       <label class="col-md-1 control-label" style="text-align: right;padding-top: 8px;">商品名</label>
	                       <div class="col-md-3">
	                            <select name="pName" onchange="sumChange(this,$(this).val());">
									<#if plist??>
							    		<#list plist as pl>
							    			<option value="${pl.productId}">${pl.productName}</option>
							    		</#list>
									</#if>
								</select>
	                       </div>
	                       <div class="col-md-2" style="padding-top: 8px;">
	                           <input name="pSumCount" style="border: 0px;background: white;text-align: center;" disabled="disabled">
	                       </div>
	                    	<label class="col-md-1 control-label" style="text-align: right;padding-top: 8px;">数量</label>
	                       <div class="col-md-3">
	                           <input class="spinner-both"  name="spinner" value="1" type="text">
	                       </div>
	                       <label class="btn btn-danger btn-sm" onclick="$(this).closest('section').remove()" ><span class="fa fa-trash-o"></span></label>
	                    </div>
                    </section>
					
<!-- 					<section class="addable hidden"> -->
<!-- 						<div class="row"> -->
<!-- 							<label class="label col col-2">商品名</label> -->
<!-- 							<div class="input col col-3"> -->
<!-- 								<label class="select"> -->
<!-- 									<select name="pName" onchange="sumChange(this,$(this).val());"> -->
<!-- 										<#if plist??> -->
<!-- 								    		<#list plist as pl> -->
<!-- 								    			<option value="${pl.productId}">${pl.productName}</option> -->
<!-- 								    		</#list> -->
<!-- 										</#if> -->
<!-- 									</select> -->
<!-- 								</label> -->
<!-- 	                   		</div> -->
<!-- 				    		<label class="input col" style="padding: 0px;margin: 0px;"> -->
<!-- 					    		<input name="pSumCount" style="border: 0px;background: white;" disabled="disabled"> -->
<!-- 				    		</label> -->
<!-- 	                   		<label class="label col">数量</label> -->
<!-- 			       			<div class="input col col-2"> -->
<!-- 			       				<label class="input"> -->
<!-- 			       					<input name="pNumber" placeholder="购买数量"> -->
<!-- 			       				</label> -->
<!-- 	                   		</div> -->
<!-- 	                   		<label class="btn btn-danger btn-sm" onclick="$(this).closest('section').remove()" ><span class="fa fa-trash-o"></span></label> -->
<!-- 	                	</div> -->
<!--                 	</section> -->
	            
	            </div>
	            <div class="modal-footer">
	                <button id="msubmit" type="button" class="btn btn-primary"> 保  存  </button>
	                <button type="button" class="btn btn-default" data-dismiss="modal"> 关 闭   </button>
	            </div> 
	        </div>
	    </div>
	</div>

    <script src="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-tags/bootstrap-tagsinput.js"></script>
    <script src="${springMacroRequestContext.contextPath}/assets/js/plugin/bootstrap-timepicker/bootstrap-timepicker.min.js"></script>
    
    <script type="text/javascript">
    
        $(document).ready(function(){
        	
            /* 显示弹框  */
            $('#dialog_cstm_tags').modal({
            	show : true,
            	backdrop : "static"
            	
            });

            <#if orderinfo??>
	        	
            	$("#ods").children().find("[name='pName']").select2({
	        		allowClear : true,
		    		width : '99%'
	        	});
				
            	$('#order_status').select2({
		    	    allowClear : true,
		    	    width:'99%',
		    	});
				
            	<#list orderinfo?keys as k>
					<#if k != "order_status">
						$("#${(k)!''}").val('${(orderinfo[k])!''}');
					</#if>
				</#list>
				
			</#if>
			
			//手机好号码验证
			jQuery.validator.addMethod("mobile", function(value, element) {     
				var mobileRule = /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0-9]|170)\d{8}$/;  
			    return this.optional(element) || mobileRule.test(value);  
			}, "请正确填写您的手机号码");
			
            /** 校验字段  **/
            var validator =$('#tagsInfos').validate({
                rules : {
                	receive_user_mobile : { 
                		mobile : [] 
                	},
                	freight : {
                		number : true
                	}
                },
                messages : {
                	
                },
                errorPlacement : function(error, element) {
                    error.insertAfter(element.parent());
                }
            });

            
            /* 提交按钮单击事件  */
            $('#msubmit').click(function(){
                if($('#tagsInfos').valid()){
                	if($("#tagsInfos").children().find("[name='pName']").length == 0){
                   		alert("请添加商品！");
                   		return false;
                   	};
                   	var i = 0;
                   	$("#appendContent").find("[name='pNumber']").each(function(){
                   		var r = /^[0-9]*[1-9][0-9]*$/
                   		if($(this).val() == "" || r.test($(this).val()) == false){
                   			i = 1;
                   			alert("商品数量不能为空且只能是正整数,请检查！");
                   			return false;
                   		}
                   	});
                   	if(i == 0){
						$('#tagsInfos').submit();
                   	}
                };
            });
            
            $('#tagsInfos').ajaxForm({
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
                        $('#dt_basic_orderinfo').DataTable().ajax.reload(null,false);;
                    }
                    $('#dialog_cstm_tags').modal("hide");
                },
                submitHandler : function(form) {
                    $(form).ajaxSubmit({
                        success : function() {
                            $("#tagsInfos").addClass('submited');
                        }
                    });
                },
                error: function(XMLHttpRequest,textStatus , errorThrown){
                    if(textStatus=='error'){
                        $('#dialog_cstm_tags').modal('hide');
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
        
        $('#dialog_cstm_tags').on('hidden.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
        	$("#dialog_cstm_tags").remove();
        });
    	
		
		$(".date").datepicker({
   			dateFormat : 'yy-mm-dd hh:mm:ss'
    	});
		
		Date.prototype.format = function(format){ 
			var o = { 
				"M+" : this.getMonth()+1, //month 
				"d+" : this.getDate(), //day 
				"h+" : this.getHours(), //hour 
				"m+" : this.getMinutes(), //minute 
				"s+" : this.getSeconds(), //second 
				"q+" : Math.floor((this.getMonth()+3)/3), //quarter 
				"S" : this.getMilliseconds() //millisecond 
			} 

			if(/(y+)/.test(format)) { 
				format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
			} 

			for(var k in o) { 
				if(new RegExp("("+ k +")").test(format)) { 
					format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
				} 
			} 
			
			return format; 
	   }
	    
	   function addproduct(){
		   	var i = 0;
			if($("#appendContent").find("[name=pNumber]").length > 0){
				$("#appendContent").find("[name='pNumber']").each(function(){
	        		var r = /^[0-9]*[1-9][0-9]*$/
	        		if($(this).val() == "" || r.test($(this).val()) == false){
	        			i = 1;
	        			alert("商品数量不能为空且只能是正整数,请检查！");
	        			return;
	        		}
	        	});
			}
			if(i != 0){
				return false;
			}
		   	
			var dom = $(".addable").clone().removeClass("addable").removeClass("hidden");
			
		   	dom.find("select").select2({
				allowClear : true,
	    		width : '99%'
			});
			
		   	dom.find("[name = 'spinner']").spinner({
			    min: 1,
			    max: 2500,
			    numberFormat: "C"
		    });
		   	
			$("#appendContent").append(dom);
			
			//给商品的现有数量赋值
			var pname = $("[name = 'pName']").val();
			var psumcount = $("[name = "+ pname +"]").val();
			$("[name = 'pSumCount']").val("现有数量：" + psumcount + "/件");
			$("#appendContent").find("[name='pNumber']").focus();
	   };
	   
	   
       function sumChange(obj,val){
		    //给商品的现有数量赋值
			var psumcount = $("[name = "+ val +"]").val();
			$(obj).closest('div').next().find("[name = 'pSumCount']").val("现有数量：" + psumcount + "/件");
       }
	   
  </script>

