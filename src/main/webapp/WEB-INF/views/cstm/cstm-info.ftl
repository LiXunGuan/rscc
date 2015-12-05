<section>
	<div class="row"><@showLable maps></@showLable></div>
</section>

<script type="text/javascript">
	
	$(function(){
		
		/* 设置电话信息仅显示 */
		$("#newphoneInfo input").attr("disabled","disabled");
		
		/* 表单赋值 */
		<#if Acstm??>
			<#list Acstm?keys as k>
				$("#${(k)!''}_a").val('${(Acstm[k])!''}');
				$("#${(k)!''}_a_1").val('${(Acstm[k])!''}');
			</#list>
			
			$("#first input").addClass("readOnly").attr("readonly","readonly");	
			$("#first select").addClass("readOnly").attr("disabled","disabled");
			$("#sameselect").removeAttr("disabled").removeClass("readOnly");	
			/* tab 头信息赋值 */
			$("div#tabs2 ul li[aria-selected='true'] a").html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + window.parent.hiddenPhone($("#cstmserviceId").val()));
			
		<#else>
			/* 如果不是客户，第一次打进系统 绑定电话号码 编号 日期 */
			$("#phone_number_a").val('${newPhoneNumber!''}');
			$("#customer_id_a").val('${customer_id!''}');
			$("#customer_id_a_1").val('${customer_id!''}');
			$("#start_date_a").val('${cDate?datetime!''}');
			$("#forEdit").remove();
		</#if>
		
		//号码隐藏
		$("#number1").val(window.parent.hiddenPhone($("#phone_number_a").val()));
		$("#number2").val(window.parent.hiddenPhone($("#phone_number_a_2").val()));
		
		if($("#phone_number_a").val()!=''){
			$("#number1").attr("disabled","disabled");	
		}
		
		if($("#phone_number_a_2").val()!=''){
			$("#number2").attr("disabled","disabled");	
		}
		
		$("#own_ids").attr("disabled","disabled").css("background","rgb(250, 250, 250)");
		$("#own_idss").val($("#own_ids").val());
		
	});
	
</script>



<!-- 宏定义 -->
<!-- 展示客户详细信息表单 -->
<#macro showLable maps>
	<#if maps??>
		<#assign keyIndex = 0>
		<#list maps?keys as key>
		
			<!-- 生成必填校验 0为必填 -->
			<#if (maps[key].allowEmpty) == "0">
				<#assign requireds = "required">
			<#else>
				<#assign requireds = "">
			</#if>
			
			<#if keyIndex%3 == 0>
			
				<!-- 判断是否换行 -->
				<#if keyIndex==3>
				</#if>
			
					</div>
				</section>
				<section>
					<div class="row">
			</#if>
			
			<#if (maps[key].columnType) == "DATE" >
				<label class="label col">${maps[key].columnName}</label>
				<div class="input col col-lg-2">
					<label class="input">
						<input name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" value="" class="datetime" />
					</label>
				</div>
				
			<#elseif (maps[key].columnType) == "ENUM" >
				<#assign enumStr = (maps[key].characterProperty)?string>
					<label class="label col">${maps[key].columnName}</label>
					<div class="form-group col col-lg-2">
						<select name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" class="form-control" >
							<#if requireds =="">
								<option value="">----请选择----</option>
							</#if>
							<#list enumStr?replace("，",",")?split(",") as a>
								<option value="${a!''}">${a!''}</option>
							</#list>
						</select>
					</div>
			<#else>
				<#if (maps[key].columnNameDB) == "pool_id">
					<label class="label col">${maps[key].columnName}</label>
					<div class="form-group col col-lg-2">
						<label class="select">
							<select name="pool_id" id="pool_ids" required="${requireds}">
								<option value="">----请选择----</option>
								<#if pools??>
									<#list pools as li>
										<#if pool_id?? && pool_id == (li.uid)>
											<option value="${li.uid!''}" selected="selected">${li.poolName!''}</option>
										<#else>
											<#if (li.beDefault)?? && li.beDefault == "1">
												<option value="${li.uid!''}" selected="selected">${li.poolName!''}</option>
											<#else>
												<option value="${li.uid!''}">${li.poolName!''}</option>
											</#if>
										</#if>
									</#list>
								<#else>
									<option value="" onclick="addPools();">添加客户池</option>
								</#if>
							</select>
						</label>
					</div>
				<#elseif (maps[key].columnNameDB) == "own_id">
				
						<input type="hidden" name="own_id" id="own_idss"> 
						<label class="label col">${maps[key].columnName}</label>
						<div class="form-group col col-lg-2">
							<label class="select">
								<select name="own_ids" id="own_ids" >
								<option value="${own_id!''}">${own_name!''}</option>
								</select>
								<#--<select name="own_ids" id="own_ids">
									<option value="-1">----请选择----</option>
									<#if user??>
										<#list user as li>
											<#if own_id?? && own_id == (li.uid)>
												<option value="${li.uid!''}" selected="selected">${li.loginName!''}</option>
											<#else>
												<option value="${li.uid!''}">${li.loginName!''}</option>
											</#if>
										</#list>
									</#if>
								</select>
								-->
							</label>
						</div>
						
				<#elseif (maps[key].columnNameDB) == "phone_number">
					
					<label class="label col">${maps[key].columnName}1</label>
					<div class="form-group col col-lg-2">
						<label class="input">
							<input type="hidden" name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a"  value="" class="${requireds}">
							<input name="number1" id="number1">
						</label>
					</div>							
					
					<label class="label col">${maps[key].columnName}2</label>
					<div class="form-group col col-lg-2">
						<label class="input">
							<input type="hidden" name="${(maps[key].columnNameDB)!''}_2" id="${(maps[key].columnNameDB)!''}_a_2"  value="${secPhone!''}" >
							<input name="number2" id="number2">
						</label>
					</div>
					<#assign keyIndex = keyIndex+1>
				<#else>
					
					<#if (maps[key].columnNameDB) != "customer_id">
						<#if (maps[key].columnType) == "INT">
							<label class="label col">${maps[key].columnName}</label>
							<div class="form-group col col-lg-2">
								<label class="input">
									<input name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" value="" class="digits">
								</label>
							</div>
						<#else>
							<label class="label col">${maps[key].columnName}</label>
							<div class="form-group col col-lg-2">
								<label class="input">
									<input name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" value="" class="${requireds}">
								</label>
							</div>
						</#if>
					<#else>
						<label class="label col">${maps[key].columnName}</label>
						<div class="form-group col col-lg-2">
							<label class="input">
								<#if uid??>
									<input type="hidden" name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a_1" value="${serialized!''}" />
									<input id="${(maps[key].columnNameDB)!''}_a" value="${serialized!''}" class="${requireds}" disabled="disabled"/>
								<#else>
									<input type="hidden" name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" value="${serialized!''}"/> 
									<input id="${(maps[key].columnNameDB)!''}_a_1" class="${requireds}" value="${serialized!''}" disabled="disabled"/>
								</#if>
								<input type="hidden" name="uid" id="uid" value="${uid!''}">
							</label>
						</div>
					</#if>
				</#if>
			</#if>
			<#assign keyIndex = keyIndex + 1 >
		</#list>
	</#if>
</#macro>

