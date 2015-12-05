<#assign global_uname ="客户名称">
<#assign global_commonuser ="终端客户">
<#assign global_owneruser ="所属客户">
<#assign global_cstmservice ="事件">

<#assign yellow = "#C79121">
<#assign usering_iframe = true>


<!-- 表头颜色配置 -->
<!-- purity  -->
<#assign global_color = 'jarviswidget-color-blue'>

<!--------------------------------------------- 质检图形的图片 属性  --------------------------------------------->
<!-- 质检图形的个数 -->
<#assign global_QC_count = 5>

<!-- ☒选项1☒ -->
<#assign global_QC_pic="asterisks">
<#assign global_QC_pic_show="asterisk">

<!-- ☒选项2☒ -->
<#-- <#assign global_QC_pic="trophies"> -->
<#-- <#assign global_QC_pic_show="trophies"> -->

<!-- ☒选项3☒-->
<#-- <#assign global_QC_pic="trophies"> -->
<#-- <#assign global_QC_pic_show="trophy"> -->
<!--------------------------------------------- 质检图形的图片 结束  --------------------------------------------->

<#macro searchBox items names>
	<#list items as item>
		<label class="label col">${item}</label>
		<label class="input col col-2">
		<input type="text" name="${names[item_index]}" id="${names[item_index]}" class="form-control" value="" />
		</label>
	</#list>
</#macro>

<#macro column titles columns>
	<#list titles as title>
		{ "title" : "${title}","data" : "${columns[title_index]}" ,"orderable" : true}, 
	</#list>
	
</#macro>
	<#macro ajaxData names>
	function(param){
	<#list names as n>
   		param.${n} = $("#${n}").val();
   	</#list>
   	}
</#macro>

<#macro columnMap map>
	<#if map??>
		<#list map?keys as key>
			{ "title" : "${key}","data" : "${(map[key].columnNameDB)}","defaultContent":""}, 
		</#list>
	</#if>
</#macro>

<!-- 表格动态显示 -->
<#macro title titles >
	<#list titles?keys as key>
		{ "title" : "${(titles[key])}!''}","data" : "${(key)!''}","defaultContent":""}, 
	</#list>
</#macro>




<!-- 生成表单 -->
<#macro showLable maps count>
	<#if maps??>
		<#list maps?keys as key>
			
			<!-- 生成必填校验 -->
			<#if (maps[key].allowEmpty) == "0">
				<#assign requireds = "required">
			<#else>
				<#assign requireds = "">
			</#if>
			
			<!-- 判断是否换行 -->
			<#if key_index%count == 0>
				</div>
			</section>
			<section>
				<div class="row">
			</#if>
			<#if (maps[key].columnType) == "DATE" >
				<label class="label col col-2">${maps[key].columnName}</label>
				<div class="input col col-3">
					<label class="input">
						<input name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" value="" class="date"/>
					</label>
				</div>
			<#elseif (maps[key].columnType) == "ENUM" >
				<#assign enumStr = (maps[key].characterProperty)?string>
					<label class="label col col-2">${maps[key].columnName}</label>
					<div class="form-group col col-lg-3">
						<select name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a" class="form-control">
							<#list enumStr?replace("，",",")?split(",") as a>
								<option value="${a!''}">${a!''}</option>
							</#list>
						</select>
					</div>
			<#else>
				<label class="label col col-2">${maps[key].columnName}</label>
				<div class="form-group col col-lg-3">
					<label class="input">
						<input name="${(maps[key].columnNameDB)!''}" id="${(maps[key].columnNameDB)!''}_a"  value="${serialized!''}" class="${requireds}">
						<input type="hidden" name="uid" id="uid" value="${uid!''}">
					</label>
				</div>
			</#if>
		</#list>
	</#if>
</#macro>

<!--------------------------------------------- JQuery Validate Message  --------------------------------------------->

<#assign validate_required ="该字段为必填字段！">
<#assign validate_digits ="该字段必须为数字！">
<#assign validate_remote ="该字段不能重复！">
<#assign validate_maxlength ="最大长度为{0}个字符！">
<#assign validate_dateISO ="请输入合法的时间！">

