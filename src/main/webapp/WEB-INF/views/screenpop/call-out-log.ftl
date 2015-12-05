<div class="row">
	<article class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
		 <form id="poploginfo" action="${springMacroRequestContext.contextPath}/calllog/update" class="smart-form" method="post">
           			<header>填写呼叫结果</header>
           			<input type="hidden" name="uid" id="uid_b" value="${uid!''}">
           			<fieldset>
           				<div class="row">
							<@showLabel maps 2 />
						</div>
						<section>
							<label class="label col col-2">内容小计</label>
							<label class="textarea col col-10" style="padding-left:10px;"> 										
								<textarea rows="3" class="custom-scroll" name="text_log" id="text_log_a"></textarea>
							</label>
						</section>
              		</fieldset>
                    
                    <section>
                      <div class="row" style="text-align: right;margin-right: 28px;">
                      	<label class="btn btn-primary btn-sm" id="msSave">保存</label>
                      </div>
                    </section>
         </form>
	</article>

	<article class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
		<div class="jarviswidget-editbox"></div>
		<div class="widget-body padding-2">
			<table id="dt_pop_log" class="table table-striped table-bordered table-hover" data-order='[[ 1, "asc" ]]' width="100%"></table>
		</div>
	</article>
	
</div>

<script type="text/javascript">
	var popLog = "";
	$(document).ready(function() {
		
		$.post(getContext()+'calllog/get',{uuid:$("#call_session_uuid").val()},function(data){

			for(var k in data.entry){
				
				$("#" + k + "_a").val(data.entry[k]);
			}
			
			$("#uid_b").val(data.entry.uid);
			$("#uuid_a").val(data.entry.uid);
			
		},"json");
		
		popLog = initTable();
		
            /* 提交按钮单击事件  */
            $('#msSave').click(function(){
                if($('#poploginfo').valid()){
                    $('#poploginfo').submit();
                }
            });
			
			 $('#poploginfo').ajaxForm({
                dataType:"json",
                data : { 
                	'customer_id': function(){
                		return $("#customer_id_a").val();
                	}
                },
               success: function(data) {
                   if(data.success){
                       $.smallBox({
                           title : "添加成功",
                           content : "<i class='fa fa-clock-o'></i> <i>添加成功</i>",
                           color : "#659265",
                           iconSmall : "fa fa-check fa-2x fadeInRight animated",
                           timeout : 2000
                       });
                       popLog.ajax.reload(null,false);;
                   }
               },
               submitHandler : function(form) {
                   $(form).ajaxSubmit({
                       success : function() {
                           $("#poploginfo").addClass('submited');
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

                       popLog.ajax.reload(null,false);;
                   }
               }
           });
			 
// 			$(".date").datepicker({
// 				monthNames: ['一月','二月','三月','四月','五月','六月', '七月','八月','九月','十月','十一月','十二月'],
// 				nextText: '>', 
// 				prevText: '<',
// 					firstDay: 1, 
// 					dateFormat : 'yy-mm-dd'
// 			});
			$(".datetime").datetimepicker({
	            format: "yyyy-mm-dd hh:ii",
	            weekStart: 1,
	            todayBtn:  1,
	    		autoclose: 1,
	    		todayHighlight: 1,
	    		startView: 2,
	    		forceParse: 0,
	            showMeridian: 1
	    	});
			 
			 
	});
</script>

<#macro showLabel maps count>
	<#assign index = 0>
	<#list maps?keys as key>
	
		<#if (maps[key].allowEmpty) == "0">
			<#assign requireds = "required">
		<#else>
			<#assign requireds = "">
		</#if>
		
		<#if (maps[key].isHidden)?? && (maps[key].isHidden) == "1" && (maps[key].isReadonly)?? && (maps[key].isReadonly) == "1">
			<#assign disabled = "disabled='disabled'">
		<#else>
			<#assign disabled = "">
		</#if>

		<#if (maps[key].isReadonly)?? && (maps[key].isReadonly) == "1">
			<#assign readonly = "readonly='readonly'">
		<#else>
			<#assign readonly = "">
		</#if>
		
		<#if (maps[key].isHidden)?? && (maps[key].isHidden) == "1">
			<input ${disabled} type="hidden" name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" value="" ${readonly}/>
		<#elseif (maps[key].columnNameDB) == "text_log">
		<#else>
			<#assign index = index + 1 />
			<section class="col col-6">
			<#if (maps[key].columnType) == "DATE" >
				<label class="label col col-4">${maps[key].columnName}</label>
				<div class="input col col-8">
					<label class="input">
						<input ${readonly} name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" value="" class="datetime"/>
					</label>
				</div>
			<#elseif (maps[key].columnType) == "ENUM" >
				<#assign enumStr = (maps[key].characterProperty)?string>
				<label class="label col col-4">${maps[key].columnName}</label>
				<div class="col col-lg-8">
					<select name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" class="form-control">
						<#list enumStr?replace("，",",")?split(",") as a>
							<option value="${a!''}">${a!''}</option>
						</#list>
					</select>
				</div>
			<#else>
				<label class="label col col-4">${maps[key].columnName}</label>
				<div class="col col-lg-8">
					<label class="input">
						<input ${readonly} name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a"  value="${serialized!''}" class="${requireds}">
					</label>
				</div>
			</#if>
			</section>
		</#if>
		
		<#if ((maps[key].columnNameDB) != "text_log") && !((maps[key].isHidden)?? && (maps[key].isHidden) == "1") && index%count == 0>
			</div>
			<div class="row">
		</#if>
	</#list>
</#macro>