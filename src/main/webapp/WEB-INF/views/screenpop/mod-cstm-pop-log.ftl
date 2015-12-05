<!-- 
	展示沟通记录的具体数据
 -->
<div class="row">
	<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
		<div class="jarviswidget-editbox"></div>
		<div class="widget-body">
			<table id="dt_pop_log" class="table table-striped table-bordered table-hover" width="100%"></table>
		</div>
	</article>
</div>

<script type="text/javascript">
	
	var popLog ;
	
	function getInitTable(){
		
		$('#dt_pop_log').DataTable({
			"dom" : "t" + "<'dt-toolbar-footer'<'col-sm-6 col-xs-12'i><'col-xs-12 col-sm-6 text-right'lp>>",
			"autoWidth" : true,
			"ordering" : true,
			"serverSide" : true,
			"processing" : true,
			"searching" : false,
			"pageLength" : 5,
				"lengthMenu" : [ 5, 10, 15, 20],
				"language" : {
					"url" : window.parent.getContext() + "public/dataTables.cn.txt"
				},
				"ajax" : {
					"url" : window.parent.getContext() + "calllog/getCallLog",
					"type" : "POST",	
					"data" : function(param) {
						param.phone=$("#phone_number").val();
						param.phone1=$("#phone_number_a").val();
					}
			},
			"paging" : true,
			"pagingType" : "bootstrap",
			"lengthChange" : true,
			"order" : [ [ "0", "desc" ] ],
			"columns" : [
				<#if title??>
					<#list dataRows?keys as key>
						<#if key == "call_time" || key == "agent_name" || key == "text_log" > 
							{"title" : "${dataRows[key].columnName}",  "data" : "${key}","defaultContent":""},
						</#if>
					</#list>
				</#if>
				{ 
				   "title" : "操作",
				   "data" : "null",
				   "render": function(data,type,full){
					   if(full.talk_time > 0){
						   return "<a onclick='playCallLog(\""+full.uid+ "\");'>播放录音</a>";
					   }else{
						   return "";
					   }
				   }
			   }
			],
		});
	}
	
	
	$(document).ready(function() {
		
		popLog = getInitTable();
		
		console.log($("#phone_number_a").val()+"--"+$("#phone_number").val());
		
		//这个沟通记录存在的弹屏中，调用父页面的方法，如果满足条件的话就将关闭按钮隐藏掉
		window.parent.hiddenTabButton();
		
         /* 提交按钮单击事件  */
         $('#msSave').click(function(){
             if($('#poploginfo').valid()){
             	
           		$("#cPhone").val($("#phone_number_a").val());
             	if($("#call_phone_a").val()){
             		$("#call_phone_a").val($("#phone_number_a").val());
             	}
             	
             	$('#poploginfo').submit();

             	//点击保存，相当于点击了关闭窗口  #newTab#407行
             	$("#tabs2 li.ui-state-default.ui-corner-top.ui-tabs-active.ui-state-active span").click();
            	
    			//置闲
//        		nopause();
//        		console.log("置闲成功！");
             	
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
                      
                      popLog.ajax.reload(null,false);
               		  /* 清空textarea */
              		  $("#text_log_a").val("");
               		  
           				$.post(getContext()+'reBackAgentStatus',function(d){
            				if(d.success){
               	                $.smallBox({
               	                    title : "成功",
               	                    content : "<i class='fa fa-clock-o'></i> <i>修改坐席状态成功</i>",
               	                    color : "#659265",
               	                    iconSmall : "fa fa-check fa-2x fadeInRight animated",
               	                    timeout : 2000
               	                });
               				}
               			},"json");
               		  
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
                      popLog.ajax.reload(null,false);
                  }
              }
          },"json");
		 
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
	
	/* 播放 */
	function playCallLog(uid){
		var url = getContext() + "calllog/player";
		$("#dialog_calllog_music").remove();
		$.post(url, {uid : uid},function(data) {
            $("#dt_pop_log").append(data);
        });
	}
	
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
						<input ${readonly} name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" value="" class="date"/>
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


<#macro showLabesl maps count inoutflag>
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
		<#elseif (maps[key].columnNameDB) == "text_log" || (maps[key].columnNameDB)?starts_with(inoutflag)>
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
						<#if requireds =="">
							<option value="无">----请选择----</option>
						</#if>
						<#list enumStr?replace("，",",")?split(",") as a>
							<option value="${a!''}">${a!''}</option>
						</#list>
					</select>
				</div>
			<#elseif (maps[key].columnType) == "FLOAT">
			<label class="label col col-2">${maps[key].columnName}</label>
			<div class="form-group col col-lg-3">
				<label class="input">
					<input name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a"  value="" class="number ${requireds}">
				</label>
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
		
		<#if !((maps[key].columnNameDB) == "text_log"  || (maps[key].columnNameDB)?starts_with(inoutflag)) && !((maps[key].isHidden)?? && (maps[key].isHidden) == "1") && index%count == 0>
			</div>
			<div class="row">
		</#if>
	</#list>
</#macro>
