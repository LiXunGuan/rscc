<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<div class="modal fade" id="dialogDataIntent">
    <div class="modal-dialog" style="width: 600px">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                <h4 class="modal-title">添加意向类型</h4>
            </div>
            <div class="modal-body"  >
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
									<input name="intentName" />
		                		</label>
		              		</section>
		            		<section class="col col-5">
								<label class="input">
									<input name="intentInfo" />
		                		</label>
		              		</section>
		              		<section class="col col-1">
		              			<button type="button" class="btn btn-primary btn-sm" onclick="addLine();">添加</button>
		              		</section>
	              		</div>
                    </fieldset>
                </form>
            </div>
				<div class="row addable hidden">
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
              		<section class="col col-1">
             				<button type="button" class="btn btn-danger btn-sm" onclick="$(this).closest('div').remove()">删除</button>
              		</section>
           		</div>
            <div class="modal-footer">
                <button id="msubmita" type="button" class="btn btn-primary">
                    保  存
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    关 闭
                </button>
            </div>
     	</div>
   	</div>
     	
   	<script type="text/javascript">
        $(document).ready(function(){
        	
	        var setting = {
	            check: {
	                enable: true
	            },
	            data: {
	                simpleData: {
	                    enable: true
	                }
	            }
	        };

            /* 显示弹框  */
            $('#dialogDataIntent').modal("show");
            
            /** 校验字段  **/
            var validator =$('#messageinfoa').validate({
            	rules:{
            		poolName:{
            			required:true,
            			remote : {
            				url:"${springMacroRequestContext.contextPath}/data/dataPool/checkname/poolname",
            				type:"post",
            				dataType:"json"
            			}
            		}
            	},
            	messages:{
            		poolName:{
            			required:"至少输入一个名称",
            			remote:"名称已存在"
            		}
            	},
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
//                 	console.log(data);
                    if(data.success){
                        $.smallBox({
                            title : "操作成功",
                            content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
                            color : "#659265",
                            iconSmall : "fa fa-check fa-2x fadeInRight animated",
                            timeout : 2000
                        });
                        $('#msubmita').attr("disabled",false);
                        $("table.dataTable").DataTable().ajax.reload(null,false);;
                    }
                    $('#dialogDataIntent').modal("hide");
                    validator.resetForm();
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

                        $("table.dataTable").DataTable().ajax.reload(null,false);;
                    }
                }
            });
            
            /* 关闭窗口的回调函数  */
            $('#dialogDataIntent').on('hide.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
    			$("#dialogDataIntent").remove();
    		});
        });
        

        function addLine(){
        	$("#messageinfoa fieldset").append($(".addable").clone().removeClass("addable").removeClass("hidden"));
        }
        
    </script>
</div>

	
    

