
<!-- 
	cstmserviceController-detailPage
 -->


<div class="modal fade" id="dialog_cstmservices">
    <div class="modal-dialog" style="width: 70%">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                <h4 class="modal-title">工单详细信息</h4>
            </div>
            <div class="modal-body" style="overflow-y: scroll;height:800px;">
            	<!-- 包含进页面 -->
				<#include "../cstmservice/cstmservice-detail-content.ftl">
            </div>
        </div>
    </div>
    
    <script type="text/javascript">
    
        $(document).ready(function(){
			
            /* 显示弹框  */
            $('#dialog_cstmservices').modal({
            	show :true,
            	backdrop:"static",
            });
        });
		
        $('#dialog_cstmservices').on('hidden.bs.modal', function (e) {   /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
			$("#dialog_cstmservices").remove();
        
			$('#dt_basic_cstmservice').DataTable().ajax.reload(null,false);;
        
		});
		
    </script>
