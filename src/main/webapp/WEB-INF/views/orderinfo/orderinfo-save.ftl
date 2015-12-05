	
	<#macro showReceive maps>
		<#if maps??>
			<#assign lineNumber = 0>
			<#list maps?keys as key>
				<#if (maps[key].allowEmpty) == "0">
<!-- 					<#assign requireds = "required"> -->
				<#else>
					<#assign requireds = "">
				</#if>
				<#if (maps[key].columnNameDB) == "receive_user_name" || (maps[key].columnNameDB) == "receive_user_address" || (maps[key].columnNameDB) == "receive_user_mobile" || (maps[key].columnNameDB) == "cstm_name">
					<#if (maps[key].columnNameDB) == "cstm_name">
						<div class="form-group">
							<label class="col-md-2 control-label">客户姓名</label>
							<div class="col-md-5">
								<select name="cstm_id" id="cstm_id">
									<#if cstms??>
										<#list cstms as cstm>
											<#if cstmiid??>
												<#if cstmiid == cstm.uid>
													<option value="${cstm.uid}" selected="selected">${cstm.customerName}(${cstm.phoneNumber})</option>
												<#else>
													<option value="${cstm.uid}">${cstm.customerName}(${cstm.phoneNumber})</option>
												</#if>
											<#else>
												<option value="${cstm.uid}">${cstm.customerName}(${cstm.phoneNumber})</option>
											</#if>
										</#list>
									</#if>
								</select>
							</div>
						</div>
					<#else>
						<div class="form-group">
							<label class="col-md-2 control-label">${maps[key].columnName}</label>
							<div class="col-md-5">
								<input class="form-control ${requireds}" placeholder="${maps[key].columnName}" name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}" type="text">
							</div>
						</div>
					</#if>
				</#if>
			</#list>
		</#if>
	</#macro>
	
	<#macro showPay maps>
		<#if maps??>
			<#assign lineNumber = 0>
			<#list maps?keys as key>
				<#if (maps[key].columnNameDB) == "pay_status" || (maps[key].columnNameDB) == "freight">
					<div class="form-group">
						<label class="col-md-2 control-label">${maps[key].columnName}</label>
						<div class="col-md-5">
							<input class="form-control " placeholder="${maps[key].columnName}" name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}" type="text">
						</div>
					</div>
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
					<div class="form-group">
						<label class="col-md-2 control-label">${maps[key].columnName}</label>
						<div class="col-md-5">
							<input class="form-control " placeholder="${maps[key].columnName}" name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}" type="text">
						</div>
					</div>
				</#if>
			</#list>
		</#if>
	</#macro>
	
	<div class="modal fade" id="dialog_orderinfos">
	    <div class="modal-dialog" style="width: 50%;">
	        <div class="modal-content" >
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
	                <h4 class="modal-title">订单信息</h4>
	            </div>
	            <div class="modal-body" >
	                <form id="orderInfos" action="${springMacroRequestContext.contextPath}/orderinfo/save" class="form-horizontal" method="post">
	                 	<fieldset style="padding-top: 0px;">
							<legend><strong>收货人信息</strong></legend>
							<@showReceive maps></@showReceive>
						</fieldset>
						<fieldset style="padding-top: 10px;">
							<legend><strong>支付方式</strong></legend>
							<@showPay maps></@showPay>
						</fieldset>
						<fieldset style="padding-top: 10px;">
							<legend><strong>发票信息</strong></legend>
							<@showInvoice maps></@showInvoice>
						</fieldset>
						<fieldset style="padding-top: 10px;">
							<legend><strong>订单状态</strong></legend>
							<div class="form-group">
								<label class="col-md-2 control-label">订单状态</label>
								<div class="col-md-5">
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
								</div>
							</div>
						</fieldset>
						<fieldset>
							<legend><strong>商品清单</strong></legend>
							<div class="form-group">
								<div class="col-md-12" style="text-align: right;">
									<label class="btn-sm btn-info" onclick="addproduct();" style="text-align:center; width: 100px;">
										添加商品
									</label>
								</div>
							</div>
							<!-- 点击商品填充-->
			                <section id="appendContent">
								<#if ods??>
								    <div id="ods">
										<#list ods as od>
									    	<section>
												<div class="form-group row">
													<label class="col-md-1 control-label" style="text-align: right;padding-top: 8px;">商品名</label>
													<div class="col-md-3">
														<select name="pName" onchange="sumChange(this,$(this).val());">
															<#if plist??>
													    		<#list plist as pl>
													    			<#if od.productId == pl.productId>
														    			<option value="${pl.productId}" selected="selected">${pl.productName}</option>												    					
													    			<#else>
														    			<option value="${pl.productId}">${pl.productName}</option>
													    			</#if>
													    		</#list>
															</#if>
														</select>
							                   		</div>
							                   		<!-- 商品现有数量 -->
							                        <div class="col-md-2" style="padding-top: 8px;">
							                           <input name="pSumCount" style="border: 0px;background: white;text-align: center;" disabled="disabled">
							                        </div>
							                   		 <!-- 商品购买数量	 -->
	                       							<label class="col-md-1 control-label" style="text-align: right;padding-top: 8px;">数量</label>
									       			<div class="col-md-3">
							                           <input class="spinner-both"  name="spinner" value="${od.productNumber}" type="text">
							                        </div>
													<label class="btn btn-danger btn-sm" onclick="$(this).closest('div').remove()" ><span class="fa fa-trash-o"></span></label>
						                   		</div>
						                   	</section>
										</#list>
			                   		</div>
								</#if>			                
			                </section>
						</fieldset>
						<input type="hidden" name="uid" id="uid" value="${uid!''}">
						<input type="hidden" name="order_id" id="order_id" value="${serialized!''}">
						<input type="hidden" id="cstm_name" name="cstm_name">
	                </form>
	                
	                <#if plist??>
	               		<#list plist as pl>
			    			<input name="${pl.productId}" value="${pl.productNumber}" type="hidden">
			    		</#list>
		    		</#if>
	                
	                <section class="addable hidden">
		                <div class="form-group row">
						   <!-- 商品名	 -->
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
	                       <!-- 商品现有数量 -->
	                       <div class="col-md-2" style="padding-top: 8px;">
	                           <input name="pSumCount" style="border: 0px;background: white;text-align: center;" disabled="disabled">
	                       </div>
	                       <!-- 商品将购买的数量	 -->
	                       <label class="col-md-1 control-label" style="text-align: right;padding-top: 8px;">数量</label>
	                       <div class="col-md-3">
	                           <input class="spinner-both" name="spinner" value="1" type="text">
	                       </div>
	                       <!-- 移除选择 -->
	                       <label class="btn btn-danger btn-sm" onclick="$(this).closest('section').remove()" ><span class="fa fa-trash-o"></span></label>
	                    </div>
                    </section>
	            </div>
	            <div class="modal-footer">
	                <button id="msubmit" type="button" class="btn btn-primary"> 保  存  </button>
	                <button type="button" class="btn btn-default" data-dismiss="modal"> 关 闭   </button>
	            </div> 
	        </div>
	    </div>
	</div>
    
    <style>
		.form-horizontal .state-error+em{
			display: block;
			margin-top: 6px;
			padding: 0 1px;
			font-style: normal;
			font-size: 11px;
			line-height: 15px;
			color: #D56161;
		}
	</style>
    
    <script type="text/javascript">
    
        $(document).ready(function(){
        	
            /* 显示弹框  */
            $('#dialog_orderinfos').modal({
            	show : true,
            	backdrop : "static"
            });
            
            //显示电话号码
            $("#receive_user_mobile").val($("#cstmserviceId").val());
            
           	$('#order_status').select2({
	    	    allowClear : true,
	    	    width:'99%',
	    	});
           	
           	$('#cstm_id').select2({
	    	    allowClear : true,
	    	    width:'99%',
	    	});
           	
           	$('#order_status').attr("disabled", true);

            <#if orderinfo??>
            
            	$('#order_status').attr("disabled", false);
            
            	$("#ods").children().find("[name='pName']").select2({
	        		allowClear : true,
		    		width : '99%'
	        	});
				
            	$("#ods").children().find("[name = 'spinner']").spinner({
    			    min: 1,
    			    numberFormat: "C"
    		    });
            	
            	$("#ods").children().each(function(){
            		var pname = $(this).find("[name='pName']").val();
            		var psumcount = $("[name = "+ pname +"]").val();
            		var pbuycount = $("[name = 'spinner']").val();
            		
//             		$(this).find("[name = 'pSumCount']").val("总数量：" + parseInt(psumcount) + "/件");
            		
            		$(this).find("[name = 'pSumCount']").val("总数量：" + (parseInt(psumcount) + parseInt(pbuycount)) + "/件");
            		$("[name = "+ pname +"]").text(parseInt(psumcount) + parseInt(pbuycount));
            		$("[name = "+ pname +"]").val(parseInt(psumcount) + parseInt(pbuycount));
            		
            		var nowcount = $("[name = "+ pname +"]").val();
            		// 商品已购买的数量
            		if(pbuycount < nowcount){
	            		$(this).find("[name = 'spinner']").spinner("option", "max", nowcount);
            		}
            	});
				
            	<#list orderinfo?keys as k>
					<#if k != "order_status">
						$("#${(k)!''}").val('${(orderinfo[k])!''}');
					</#if>
				</#list>
				
			</#if>
			
			//手机号码验证
			jQuery.validator.addMethod("mobile", function(value, element) {     
				var mobileRule = /^(1)\d{10}$/;  
			    return this.optional(element) || mobileRule.test(value);  
			}, "请正确填写您的手机号码");
			
			//判断运费
			jQuery.validator.addMethod("isFloat", function(value, element) { 
		         value=parseFloat(value);      
		         return this.optional(element) || value>0;       
		    }, "请正确输入您的运费！");  
			
            /** 校验字段  **/
            var validator = $('#orderInfos').validate({
                rules : {
                	receive_user_mobile : { 
                		mobile : [] 
                	},
                	freight : {
                		isFloat : true
                	}
                },
                messages : {
                	receive_user_name : {
                		required : "${c.validate_required}"
                	},
                	receive_user_mobile : {
                		required : "${c.validate_required}"
                	},
                	receive_user_address : {
                		required : "${c.validate_required}"
                	},
                	invoice_type : {
                		required : "${c.validate_required}"
                	},
                	invoice_info : {
                		required : "${c.validate_required}"
                	}
                },
                errorPlacement : function(error, element) {
                    error.insertAfter(element.parent());
                }
            });
            
            /* 提交按钮单击事件  */
            $('#msubmit').click(function(){
                if($('#orderInfos').valid()){
                	pdata = {};
                	//检验是否选择了商品
                	if($("#orderInfos").children().find("[name='pName']").length == 0){
                   		alert("请添加商品！");
                   		return false;
                   	};
                   	//校验商品数量是否输入正确
                   	var i = 0;
                   	$("#appendContent").find("[name='spinner']").each(function(){
                   		var r = /^[0-9]*[1-9][0-9]*$/;
                   		if($(this).val() == "" || r.test($(this).val()) == false){
                   			i = 1;
                   			alert("商品数量不能为空或者0且只能是正整数,请检查！");
                   			return false;
                   		}
                   	});
                   	//获取商品对应的购买数量并存入对象中
                   	$("#appendContent").children().each(function(){
                   		  var pname = $(this).find("[name='pName']").val();
                   		  var pcount = parseInt($(this).find("[name='spinner']").val());
                   		  if(pdata[pname]){
							  var c = pdata[pname];
							  pdata[pname] = pcount + c;
                   		  }else{
	                   		  pdata[pname] = pcount;
                   		  }
                   	});
                   	console.log(pdata);
                   	//判断商品的购买数量是否正确
                   	$.each(pdata, function(k,v){
                   		var psumcount = parseInt($("[name='"+ k +"']").val());
                   		if(psumcount < v){
                   			i = 1;
                   			alert("商品购买数量已超过商品总数量,请检查！");
                   			return false;
                   		}
                   	});
                   	if(i == 0){
                   		$("#cstm_name").val($("#cstm_id :selected").text());
						$('#orderInfos').submit();
                   	}
                };
            });
            
            $('#orderInfos').ajaxForm({
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
                    $('#dialog_orderinfos').modal("hide");
                },
                submitHandler : function(form) {
                    $(form).ajaxSubmit({
                        success : function() {
                            $("#orderInfos").addClass('submited');
                        }
                    });
                },
                error: function(XMLHttpRequest,textStatus , errorThrown){
                    if(textStatus=='error'){
                        $('#dialog_orderinfos').modal('hide');
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
        
        $('#dialog_orderinfos').on('hidden.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
        	$("#dialog_orderinfos").remove();
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
			if($("#appendContent").find("[name='spinner']").length > 0){
				$("#appendContent").find("[name='spinner']").each(function(){
	        		var r = /^[0-9]*[1-9][0-9]*$/
	        		if($(this).val() == "" || r.test($(this).val()) == false){
	        			i = 1;
	        			alert("商品数量不能为空或者0且只能是正整数,请检查！");
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
			
			//给商品的现有数量赋值
			var pname = dom.find("[name = 'pName']").val();
			var psumcount = $("[name = "+ pname +"]").val();
			
			dom.find("[name = 'spinner']").spinner({
			    min: 1,
			    max: psumcount,
			    numberFormat: "C"
		    });
			
			$("#appendContent").append(dom);
		   	
			//给商品的现有数量赋值
			dom.find("[name = 'pSumCount']").val("总数量：" + psumcount + "/件");
	   };
	   
	   
       function sumChange(obj,val){
		    //给商品的现有数量赋值
			var count = $("[name = "+ val +"]").val();
			$(obj).closest('div').next().find("[name = 'pSumCount']").val("总数量：" + count + "/件");
			$(obj).closest('div').nextAll().find("[name = 'spinner']").spinner("option", "max", count);
       }
	   
  </script>

