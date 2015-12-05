<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<div class="modal fade" id="dialogDataPool">
    <div class="modal-dialog" style="width: 1000px">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                <h4 class="modal-title">查看详细信息</h4>
            </div>
            <div class="modal-body"  >
                <form id="messageinfoa" action="${springMacroRequestContext.contextPath}/data/dataPool/save" class="smart-form" method="post">
                    <fieldset>
	     				<div class="row">
		     				<section class="col col-6">
			        		    <label class="label">名称</label>
                                <label class="input">
				        		    <input readonly="readonly" value="${entry.itemName}" />
                                </label>
		              		</section>
		              		<section class="col col-6">
			        		    <label class="label">号码</label>
                                <label class="input">
				        		    <input id="call_phone" readonly="readonly" value="${entry.itemPhone}" />
                                </label>
		              		</section>
	              		</div>
	     				<div class="row">
		     				<section class="col col-6">
			        		    <label class="label">地址</label>
                                <label class="input">
				        		    <input readonly="readonly" value="${entry.itemAddress}" />
                                </label>
		              		</section>
		              		<section class="col col-6">
			        		    <label class="label">来源</label>
                                <label class="input">
				        		    <input readonly="readonly" value="${entry.dataSource}" />
                                </label>
		              		</section>
	              		</div>
	              		<div class="row">
	              		<#assign json=(entry.itemJson)?eval />
	              			<#list json?keys as k>
		              			<section class="col col-6">
				        		    <label class="label">${k}</label>
	                                <label class="input">
					        		    <input readonly="readonly" value="${json[k]}" />
	                                </label>
			              		</section>
			              		<#if (k_index + 1) % 2 == 0>
			              			</div>
			              			<div class="row">
			              		</#if>
	              			</#list>
	              		</div>
                    </fieldset>
                    <fieldset>
		                <div class="row" id="loglist">
			       			<header>沟通记录</header>
							<div class="widget-body padding-10">
								<table id="dt_call_log" class="table table-striped table-bordered table-hover" data-order='[[ 1, "asc" ]]'></table>
							</div>
						</div>
					</fieldset>
                </form>
            </div>
            <div class="modal-footer">
<!--                 <button id="msubmita" type="button" class="btn btn-primary"> -->
<!--                     保  存 -->
<!--                 </button> -->
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
            $('#dialogDataPool').modal("show");
            
            /** 校验字段  **/
            var validator =$('#messageinfoa').validate({
            	rules:{
            		poolName:{
            			required:true
            		}
            	},
            	messages:{
            		poolName:{
            			required:"至少输入一个池名称"
            		}
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
                	console.log(data);
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
                    $('#dialogDataPool').modal("hide");
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
                        $('#dialogDataPool').modal('hide');
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
            $('#dialogDataPool').on('hide.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
    			$("#dialogDataPool").remove();
    		});
            
            popLog = $('#dt_call_log').DataTable({
    			"dom" : "t"
    				+ "<'dt-toolbar-footer'<'col-sm-6 col-xs-12'i><'col-xs-12 col-sm-6 text-right'lp>>",
    			"autoWidth" : true,
    			"ordering" : true,
    			"serverSide" : true,
    			"processing" : true,
    			"searching" : false,
    			"pageLength" : 5,
    					"lengthMenu" : [ 5, 10, 15, 20],
    					"language" : {
    						"url" : getContext()+ "public/dataTables.cn.txt"
    					},
    					"ajax" : {
    						"url" : getContext() + "calllog/getCallLog",
    				"type" : "POST",
    				"data" : function(param) {
    					param.call_phone = $("#call_phone").val();
    				}
    			},
    			"paging" : true,
    			"pagingType" : "bootstrap",
    			"lengthChange" : true,
    			"order" : [ [ "0", "desc" ] ],
    			"columns" : [
    				<#if maps??>
    					<#list maps?keys as key>
    						<#if maps[key].allowShow == '1'>
    						<#if key == "call_phone">
	    						{"title" : "${maps[key].columnName}",  "data" : "${key}","defaultContent":"","render":function(a,d,f){
	    							return window.parent.hiddenPhone(f.call_phone);
	    						}},
    						<#else>
    							{"title" : "${maps[key].columnName}",  "data" : "${key}","defaultContent":""},
    						</#if>
    						</#if>
    					</#list>
    				</#if>
    			],
    		});
            
        });
        

        function addLine(){
        	$("#messageinfoa fieldset").append($(".addable").clone().removeClass("addable").removeClass("hidden"));
        }
        
    </script>
</div>
<#macro section titles names>
	<#list titles as t>
      		<section>
     			<div class="row">
        		    <label class="label col col-2">${t}</label>
      			    <div class="col col-6">
					<label class="input">
						<input name="${names[t_index]}" />
                		</label>
              		</div>
            	</div>
        	</section>
	</#list>
</#macro>
<#macro validate titles names>
	rules : {
	<#list names as n>
       	${n} : {
       		required : true
       	},
	</#list>
       },
       messages: {
       <#list names as n>
       	${n}: {
       		required : "必须输入${titles[n_index]}"
       	},
     	</#list>
       },
       errorPlacement : function(error, element) {
           error.insertAfter(element.parent());
       }
</#macro>
<#macro treeString tree treeobj>
  	var nodes = ${treeobj}.getCheckedNodes();
   	var roles="";
 	for(var n in nodes){
       if(!nodes[n].isParent) {
           if (nodes[n].id) {
          		roles = roles + nodes[n].id + ',';
           }
      	}
   	}
   	$('#${tree}').val(roles.substr(0,roles.length-1));
</#macro>

	
    

