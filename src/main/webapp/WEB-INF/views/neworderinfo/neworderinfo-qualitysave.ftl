	
<!-- 	<script src="${springMacroRequestContext.contextPath}/assets/js/plugin/jquery-nestable/jquery.nestable.min.js"></script> -->
	<link href= "${springMacroRequestContext.contextPath}/assets/css/skin/blue.monday/jplayer.blue.monday.css" rel ="stylesheet" type= "text/css" />
	<script type= "text/javascript" src= "${springMacroRequestContext.contextPath}/assets/js/jPlayer/jquery.jplayer.min.js" ></script >
	
	<style>
		.smart-form .label {display: inline-block;}
		.tag.label.label-info {color: white;}
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
				<#if (maps[key].columnNameDB) == "receive_user_name" || (maps[key].columnNameDB) == "receive_user_address" || (maps[key].columnNameDB) == "receive_user_mobile" >
					<#if (maps[key].columnNameDB) == "order_status">
						<#if !waitconfirm??>
							<div class="form-group">
								<label class="col-md-2 control-label">${maps[key].columnName}</label>
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
						</#if>
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
	
	<#macro showOthers maps>
		<#if maps??>
			<#assign lineNumber = 0>
			<#list maps?keys as key>
				<#if (maps[key].allowEmpty) == "0">
 					<#assign requireds = "required">
				<#else>
					<#assign requireds = "">
				</#if>
				<#if (maps[key].columnNameDB)?index_of("col") != -1 >
					<div class="form-group">
						<label class="col-md-2 control-label">${maps[key].columnName}</label>
						<div class="col-md-5">
							<input class="form-control ${requireds}" placeholder="${maps[key].columnName}" name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}" type="text">
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
	            	
	            	<form id="recordInfos" action="${springMacroRequestContext.contextPath}/record/save" class="smart-form" method="post">
	                    <fieldset>
                            <input id="uuidObj" name="uuidObj" type="hidden">
	                 		<div id="recordinfo">
		                        <section>
		                            <div class="row">
		                                <label class="label col col-2">呼叫信息</label>
		                                <label class="label col col-2">${(record.caller_name)!''}</label>
		                            </div>
		                        </section>
		                        <section>
		                            <div class="row">
		                                <label class="label col col-2">语音试听</label>
		                                <div class="input col col-8">
	                                    	<div id="jquery_jplayer_1" class="jp-jplayer"></div>	
	                                    	<#include "../player.ftl">
		                                </div>
		                            </div>
		                        </section>
	                 		</div>
	                 		<div id="checkinfo">
		                        <section>
		                            <div class="row">
		                                <label class="label col col-2">质检打分</label>
		                                <input type="hidden" id="rate" >
		                                <input type="hidden" id="oStatus" name="oStatus">
		                                <div class="rating col col-6" style="text-align: left;">
		                                    <input name="score" id="score" value=""/>
		                                    <input name="cuid" id="cuid" type="hidden" value="${(check.uid)!''}"/><!-- 查看原来是否存在质检结果 -->
	                                    	<#if sScore??>
			                                   	<#assign  sCount=sScore?number>
	                                    	</#if>
		                                    <#if check??>
		                                    	<#if sScore??>
		                                    		<@showRate c.global_QC_pic c.global_QC_pic_show c.global_QC_count sCount></@showRate>
			                                    <#else>
		                                    		<@showRate c.global_QC_pic c.global_QC_pic_show c.global_QC_count 0></@showRate>
		                                    	</#if>
		                                    <#else>
		                                    	<@showRate c.global_QC_pic c.global_QC_pic_show c.global_QC_count 0></@showRate>
		                                    </#if>
		                                </div>
		                            </div>
		                        </section>
		                        <section>
		                            <div class="row">
		                                <label class="label col col-2">打分备注</label>
		                                <div class="textarea col col-6">
		                                	<textarea style="width: 100%;height:60px;overflow-y:visible;" name="remark">${(check.remark)!''}</textarea>  
		                                </div>
		                            </div>
		                        </section>
	                 		</div>
	                    </fieldset>
	                </form>
	            
	                <form id="orderInfos" action="${springMacroRequestContext.contextPath}/neworderinfo/save" class="form-horizontal" method="post">
	                 	<fieldset style="padding-top: 10px;">
							<legend id="legsta"><i class="fa fa-lg fa-caret-up"></i> <strong>订单状态</strong></legend>
							<div class="form-group" id="divsta">
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
	                 	<fieldset style="padding-top: 0px;">
							<legend id="leginfo"><i class="fa fa-lg fa-caret-up" id="iinfo"></i> <strong>基本信息</strong></legend>
							<div id="divinfo"><@showReceive maps></@showReceive></div>
						</fieldset>
	                 	<fieldset style="padding-top: 0px;" id="filoth">
							<legend id="legoth"><i class="fa fa-lg fa-caret-up" id="ioth"></i> <strong>扩展信息</strong></legend>
							<div id="divoth"><@showOthers maps></@showOthers></div>
						</fieldset>
						<fieldset style="padding-top: 10px;">
							<legend id="legpay"><i class="fa fa-lg fa-caret-up"></i> <strong>支付方式</strong></legend>
							<div id="divpay"><@showPay maps></@showPay></div>
						</fieldset>
						<fieldset style="padding-top: 10px;">
							<legend id="leginv"><i class="fa fa-lg fa-caret-up"></i> <strong>发票信息</strong></legend>
							<div id="divinv"><@showInvoice maps></@showInvoice></div>
						</fieldset>
						<fieldset>
							<legend id="leggod"><i class="fa fa-lg fa-caret-up"></i> <strong>商品清单</strong></legend>
							<div id="divgod">
							<div class="form-group">
								<div class="col-md-12" style="text-align: right;">
									<label id="addgoods" class="btn-sm btn-info" onclick="addproduct();" style="text-align:center; width: 100px;">
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
			                </div>
						</fieldset>
						<input type="hidden" name="uid" id="uid" value="${uid!''}">
						<input type="hidden" name="order_id" id="order_id" value="${serialized!''}">
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
	
	<script type="text/javascript" defer="defer" src="${springMacroRequestContext.contextPath}/assets/js/jPlayer/audio_media.js"></script>
	
	<!-- 显示等级 类型 个数  选中个数 -->
	<#macro showRate type type_show count selectCount>
		<#list count..1 as c>
			<#if (selectCount <= c) >
				<input type="radio" name="${type}-rating" id="${type}-rating-${c}" onclick="getScore('${c}');" checked="checked" >
				<label for="${type}-rating-${c}"><i class="fa fa-${type_show}"></i></label>
			<#else>
				<input type="radio" name="${type}-rating" id="${type}-rating-${c}" onclick="getScore('${c}');" >
				<label for="${type}-rating-${c}"><i class="fa fa-${type_show}"></i></label>
			</#if>
		</#list>
	</#macro>
	
	<script type= "text/javascript" src= "${springMacroRequestContext.contextPath}/assets/js/jPlayer/playplugin.js" ></script >
	
    
    <script type="text/javascript">
    
    	var myAndroidFix;
    
        $(document).ready(function(){
        	
        	getScore(${(sScore)!''});
        	
        	$("#divpay").hide();
	       	$("#divinv").hide();
	       	$("#divsta").hide();
	       	$("#divgod").hide();
        	
        	$("#iinfo").hide();
    		$("#ioth").hide();
        	
        	<#if quality??>
        	
        		$("#qualityDiv").show();
        		$("#addOrUpdateDiv").hide();
        	
        		$("#iinfo").show();
        		$("#ioth").show();
        		
        		$("#divsta").show();
        		$("#divinfo").show();
        		$("#divoth").show();
        		$("#divpay").show();
    	       	$("#divinv").show();
    	       	$("#divgod").show();
        		
        		
        		$("#orderInfos input").attr('disabled','disabled');
        		$("#addgoods").attr('disabled','disabled');
        		$("#addgoods").hide();
					
        		$("#uuidObj").val(${orderId});        			
        		
        		<#if playrecord??>
		        	var id = "#jquery_jplayer_1";
		    	    var bubble = {
		    	         title: "Bubble",
		    	         mp3: "${(record.record_file)!''}"
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
		    	    myAndroidFix = new jPlayerAndroidFix(id, bubble, options);
		    	    myAndroidFix.play();
	    	    <#else>
        			$("#recordinfo").hide();
		    	</#if>
	    	<#else>
		    	$("#recordInfos").hide();
		    	$("#recordinfo").hide();
	         	$("#checkinfo").hide();
         	</#if>
         	
         	if($("#iinfo").length != 0){
         		$("#leginfo").click(function(){
    	       		if($("#divinfo").css("display") == 'none'){
    	       			$("#divinfo").slideDown('fast', function(){
    	       				$("#leginfo").children('i').attr('class','fa fa-lg fa-caret-down');
    	       			});
    	       		}else{
    	       			$("#divinfo").slideUp('fast', function(){
    	       				$("#leginfo").children('i').attr('class','fa fa-lg fa-caret-up');
    	       			});
    	       		}
    	       	});
         	}
         	
         	if($("#ioth").length != 0){
         		$("#legoth").click(function(){
    	       		if($("#divoth").css("display") == 'none'){
    	       			$("#divoth").slideDown('fast', function(){
    	       				$("#legoth").children('i').attr('class','fa fa-lg fa-caret-down');
    	       			});
    	       		}else{
    	       			$("#divoth").slideUp('fast', function(){
    	       				$("#legoth").children('i').attr('class','fa fa-lg fa-caret-up');
    	       			});
    	       		}
    	       	});
         	}
        		
	       	$("#legpay").click(function(){
	       		if($("#divpay").css("display") == 'none'){
	       			$("#divpay").slideDown('fast', function(){
	       				$("#legpay").children('i').attr('class','fa fa-lg fa-caret-down');
	       			});
	       		}else{
	       			$("#divpay").slideUp('fast', function(){
	       				$("#legpay").children('i').attr('class','fa fa-lg fa-caret-up');
	       			});
	       		}
	       	});
	       
	       	$("#leginv").click(function(){
	       		if($("#divinv").css("display") == 'none'){
	       			$("#divinv").slideDown('fast', function(){
	       				$("#leginv").children('i').attr('class','fa fa-lg fa-caret-down');
	       			});
	       		}else{
	       			$("#divinv").slideUp('fast', function(){
	       				$("#leginv").children('i').attr('class','fa fa-lg fa-caret-up');
	       			});
	       		}
	       	});
	       	
	       	$("#legsta").click(function(){
	       		if($("#divsta").css("display") == 'none'){
	       			$("#divsta").slideDown('fast', function(){
	       				$("#legsta").children('i').attr('class','fa fa-lg fa-caret-down');
	       			});
	       		}else{
	       			$("#divsta").slideUp('fast', function(){
	       				$("#legsta").children('i').attr('class','fa fa-lg fa-caret-up');
	       			});
	       		}
	       	});
	       	
	       	$("#leggod").click(function(){
	       		if($("#divgod").css("display") == 'none'){
	       			$("#divgod").slideDown('fast', function(){
	       				$("#leggod").children('i').attr('class','fa fa-lg fa-caret-down');
	       			});
	       		}else{
	       			$("#divgod").slideUp('fast', function(){
	       				$("#leggod").children('i').attr('class','fa fa-lg fa-caret-up');
	       			});
	       		}
	       	});
	       	
	       	// 隐藏扩展信息
	       	if($("#divoth").children().length == 0){
	       		$("#filoth").hide();
	       	};
        	
            /* 显示弹框  */
            $('#dialog_orderinfos').modal({
            	show : true,
            	backdrop : "static"
            });
            
            // 显示电话号码
            $("#receive_user_mobile").val($("#cstmserviceId").val());
            
           	$('#order_status').select2({
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
                		
//                 		$(this).find("[name = 'pSumCount']").val("总数量：" + parseInt(psumcount) + "/件");
                		
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
            	
            	<#if quality??>
	            	 
            		if($('#recordInfos').valid()){
	            		
            			$("#oStatus").val($("#order_status").val());
            			
	                  	$('#recordInfos').submit();
	              	 
	            	}
            		
            	<#else>
            	
	                if($('#orderInfos').valid()){
	                	
	                	var i = 0;
	                	
	                	if($("#appendContent").find("[name='spinner']").length != 0){
	                		
	                		pdata = {};
	                    	
	//                 		//检验是否选择了商品
	//                     	if($("#orderInfos").children().find("[name='pName']").length == 0){
	//                        		alert("请添加商品！");
	//                        		return false;
	//                      	};
	                       	
	                       	
	                       	// 校验商品数量是否输入正确
	                       	$("#appendContent").find("[name='spinner']").each(function(){
	                       		var r = /^[0-9]*[1-9][0-9]*$/;
	                       		if($(this).val() == "" || r.test($(this).val()) == false){
	                       			i = 1;
	                       			alert("商品数量不能为空或者0且只能是正整数,请检查！");
	                       			return false;
	                       		}
	                       	});
	                       	
	                       	// 获取商品对应的购买数量并存入对象中
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
	                       	
	//                        	console.log(pdata);
	                       	
	                       	// 判断商品的购买数量是否正确
	                       	$.each(pdata, function(k,v){
	                       		var psumcount = parseInt($("[name='"+ k +"']").val());
	                       		if(psumcount < v){
	                       			i = 1;
	                       			alert("商品购买数量已超过商品总数量,请检查！");
	                       			return false;
	                       		}
	                       	});
	                		
	                	}
	                	
	                	if(i == 0){
							$('#orderInfos').submit();
	                	}
	                };
            	
            	</#if>

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
                        $('#dt_basic_neworderinfo').DataTable().ajax.reload(null,false);;
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
            
            $('#recordInfos').ajaxForm({
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
                        $('#dt_basic_neworderinfo').DataTable().ajax.reload(null,false);;
                    }
                    $('#dialog_orderinfos').modal("hide");
                },
                submitHandler : function(form) {
                    $(form).ajaxSubmit({
                        success : function() {
                        	$("#recordInfos").addClass('submited');
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
       };
       
       /* 返回数据[评分数据] */
		function getScore(uid){
			$("#score").val(uid);
		}
       
  </script>

