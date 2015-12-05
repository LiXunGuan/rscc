<div class="col-md-7">

<input type="hidden" value="${p.uuid}" id="val">

 <form id="poolInfos${p.uuid}" action="${springMacroRequestContext.contextPath}/cstmpool/save" class="smart-form" method="post">
     <fieldset>
         <section>
             <div class="row">
                 <label class="label col col-2">池名称</label>
                 <div class="input col col-10">
                     <label class="input">
                         <input type="hidden" id="uid" name="uid" value="${(p.uid)!''}">
                         <input name="poolName" id="poolName" value="${(p.poolName)!''}">
                     </label>
                 </div>
             </div>
         </section>
         <section>
             <div class="row">
                 <label class="label col col-2">池描述</label>
                 <div class="input col col-10">
                     <label class="input">
<!--                                         <input name="poolDes" value="${(customerpool.poolDes)!''}"> -->
                         <textarea rows="" cols="" style="height: 150px;width: 100%;" name="poolDes" id="poolDes">${(p.poolDes)!''}</textarea>
                     </label>
                 </div>
             </div>
         </section>
         
          <section>
             <div class="row">
                 <label class="label col col-2">归属部门</label>
                 <div class="col col-2" style="display: inline;">
                  <#list departments as d>
	                   <#if selectedList??>
		                   	<#if selectedList?seq_contains(d.uuid)>
								<label class="checkbox"><input type="checkbox" name="departments" checked="checked" value=${d.uuid}><i></i>${d.datarangeName}</label>
							<#else>
								<label class="checkbox"><input type="checkbox" name="departments" value=${d.uuid}><i></i>${d.datarangeName}</label>
							</#if>
					   <#else>
							<label class="checkbox"><input type="checkbox" name="departments" value=${d.uuid}><i></i>${d.datarangeName}</label>
					   </#if>
					   
					<#if (d_index+1)%2==0>
				</div>
				<div class="col col-2" style="display: inline;">
					</#if>
				  </#list>
                  </div>
               </div>
           </section>
         
         <section>
         	<div class="row" style="text-align: right;">
			<#if (p.beDefault)?? && (p.beDefault) == '1'>
			<#else>
			 <button onclick="deleteForm('${p.uuid}')" type="button" class="btn btn-sm btn-danger">
	          	  删  除
	        </button>
			</#if>
   			<button  onclick="subForm('${p.uuid}')"  type="button" class="btn btn-sm btn-primary">
            	保  存
        	</button>
         	</div>
         </section>
     </fieldset>
 </form>
</div>
<div class="col-md-4"></div>

<script type="text/javascript">
	
	$(function(){
	});
		
		$('#poolInfos${p.uuid}').ajaxForm({
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
	            }
	        },
	        submitHandler : function(form) {
	            $(form).ajaxSubmit({
	                success : function() {
	                    $('#poolInfos'+val).addClass('submited');
	                }
	            });
	        },
	        error: function(XMLHttpRequest,textStatus , errorThrown){
	            if(textStatus=='error'){
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
		
	
	
	
</script>