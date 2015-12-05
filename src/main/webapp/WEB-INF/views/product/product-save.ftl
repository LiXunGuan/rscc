	
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
	
	<!--
		list集合遍历
		集合 
		单个对象名 
		值的valueID 
		值得显示名字
		要选中的valueID
	-->

	<!-- 生成表单 -->
	<#macro showLable maps>
		<#if maps??>
			<#assign lineNumber = 0>
			<#list maps?keys as key>
				<!-- 生成必填校验 -->
				<#if (maps[key].allowEmpty) == "0">
					<#assign requireds = "required">
				<#else>
					<#assign requireds = "">
				</#if>
				<!-- 判断是否换行 -->
				<#if lineNumber != 0>
					</div>
					</section>
					<section>
					<div class="row">
				</#if>
				<#if (maps[key].columnNameDB) != "product_id" && (maps[key].columnNameDB) != "product_create_time">
					<#if (maps[key].columnNameDB) == "product_remark">
						<label class="label col col-2">${maps[key].columnName}</label>
						<label class="textarea col col-lg-8">
							<textarea class="custom-scroll" rows="8" id="${(maps[key].columnNameDB)!''}" name="${(maps[key].columnNameDB)!''}"></textarea>
						</label>
					<#elseif (maps[key].columnNameDB) == "product_picture">
						<label class="label col col-2">${maps[key].columnName}</label>
						<div class="form-group col col-lg-6">
							<label class="input input-file">
								<span class="button"><input id="fileupload" type="file" name="multipartFile" onchange="this.parentNode.nextSibling.value = this.value;">浏览</span><input id="filetext" type="text" placeholder="" readonly="">
							</label>
							<label id="error_hide" class="hide" style="color: #CD5C5C"></label>
						</div>
					<#else>
						<label class="label col col-2">${maps[key].columnName}</label>
						<label class="input col col-lg-3">
							<input name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}"  class="${requireds}">
						</label>
					</#if>
				</#if>
				<#assign lineNumber = lineNumber+1 >
			</#list>
		</#if>
	</#macro>

	<div class="modal fade" id="dialog_products">
	    <div class="modal-dialog" style="width: 50%;">
	        <div class="modal-content" >
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
	                <h4 class="modal-title">产品信息管理</h4>
	            </div>
	            <div class="modal-body"  >
	                <form id="products" action="${springMacroRequestContext.contextPath}/product/save" class="smart-form" method="post">
	                    <fieldset style="padding-top: 0px;">
	                   		<section>
								<div class="row">
									<@showLable maps></@showLable>
									<input type="hidden" name="uid" id="uid" value="${uid!''}">
									<input type="hidden" name="product_id" id="product_id" value="${serialized!''}">
								</div>
							</section>
	                    </fieldset>
	                </form>
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
            $('#dialog_products').modal({
            	show : true,
            	backdrop : "static"
            	
            });

            <#if product??>
				<#list product?keys as k>
				 	<#if k != "product_picture">
						$("#${(k)!''}").val('${(product[k])!''}');
					<#else>
				 		$("#filetext").val('${picture}');
				 	</#if>	
				 </#list>
			</#if>
			
			//商品价格验证
			jQuery.validator.addMethod("price", function(value, element) {     
				var p = /^[1-9](\d+(\.\d{1,2})?)?$/; 
			    var p1 = /^[0-9](\.\d{1,2})?$/;  
			    return p.test(value) || p1.test(value);  
			}, "请正确填写您的商品价格！");
			
            /** 校验字段  **/
            var validator =$('#products').validate({
                rules : {
                	product_number : { digits : true, min : 1 },
                	product_price : { price : [] },
                	product_name : { 
                		maxlength : 64,
                		remote : {
                			type : "post",
                			url : getContext() + "product/pNameRepeat",
                			data : {
                				uid : function(){
                					return $('#uid').val();
                				},
                				pname : function(){
                					return $('#product_name').val();
                				}
                			}
                		}
                	},
                	product_remark : {maxlength : 200}
                },
                messages : {
                	product_name : {
                		required : "${c.validate_required}",
        				remote : $.format("该商品名已经存在！")
        			},
        			product_number : {
        				required : "${c.validate_required}",
        				digits : $.format("请正确填写你的商品数量！"),
        				min : $.format("请正确填写你的商品数量！")
        			},
        			product_price : {
        				required : "${c.validate_required}"
        			}
                },
                errorPlacement : function(error, element) {
                    error.insertAfter(element.parent());
                }
            });

            /* 提交按钮单击事件  */
            $('#msubmit').click(function(){
//                 if($('#products').valid() && ckFile()){
                if($('#products').valid()){
					//$("#product_create_time").val(new Date().format("yyyy-MM-dd hh:mm:ss"));
                    $('#products').submit();
                }
            });
            
            $('#products').ajaxForm({
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
                        $('#dt_basic_product').DataTable().ajax.reload(null,false);;
                    }
                    $('#dialog_products').modal("hide");
                },
                submitHandler : function(form) {
                    $(form).ajaxSubmit({
                        success : function() {
                            $("#products").addClass('submited');
                        }
                    });
                },
                error: function(XMLHttpRequest,textStatus , errorThrown){
                    if(textStatus=='error'){
                        $('#dialog_products').modal('hide');
                        $.smallBox({
                            title : "操作失败",
                            content : "<i class='fa fa-clock-o'></i> <i>操作失败</i>",
                            color : "#C46A69",
                            iconSmall : "fa fa-times fa-2x fadeInRight animated",
                            timeout : 2000
                        });

                        productTable.ajax.reload(null,false);;
                    }
                }
            });
        });
        
        $('#dialog_products').on('hidden.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
        	$("#dialog_products").remove();
        	productTable.ajax.reload(null,false);;
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
		};
		
		function valid(){
        	if($("#filetext").val() != "")
        		return true;
        	if($("#filetext").val()==""){
        		$("#file-error-message").show();
        		$("#file-error-message").prev().addClass("state-error");
        	}
        	return false;
        };
        
        function clearfValid(){
			$("#file-error-message").hide();
			$("#file-error-message").prev().removeClass("state-error");
        };
        
        //检查上传的文件 
       	function ckFile() {
       		var file = $("#filetext").val();
       		var fstart = file.lastIndexOf(".");
       		var suffix = file.substring(fstart, file.length).toLowerCase();
       		if (file == "" || file == null) {
       			$("#error_hide").text("请选择上传的图片文件！");
       			$("#error_hide").attr("class","show note");
       			return false;
       		}
       		if (suffix != ".jpg" && suffix != ".png" ) {
       			$("#error_hide").text("文件类型错误！");
       			$("#error_hide").attr("class","show note");
       			return false;
       		}
//        		var filesize = 0;
//        		var maxsize = 5*1024*1024;//最大文件大小为5M
//        		filesize = $("#fileupload").get(0).files[0].size;
//        		if(filesize > maxsize){
//        			$("#error_hide").text("请上传小于5M的文件！");
//        			$("#error_hide").attr("class","show note");
//        			return false;
//        		};
       		return true;
       	};

    	
    </script>

