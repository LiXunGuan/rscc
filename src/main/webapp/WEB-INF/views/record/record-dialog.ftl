<link href= "${springMacroRequestContext.contextPath}/assets/css/skin/blue.monday/jplayer.blue.monday.css" rel ="stylesheet" type= "text/css" />
<script type= "text/javascript" src= "${springMacroRequestContext.contextPath}/assets/js/jPlayer/jquery.jplayer.min.js" ></script >
<!-- 
	评分应按比例显示
 -->

<style>
	.smart-form .label {display: inline-block;}
	.tag.label.label-info {color: white;}
</style>

<div class="modal fade" id="dialog_record">
    <div class="modal-dialog" style="width: 40%">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><strong>关闭</strong></button>
                <h4 class="modal-title">质检</h4>
            </div>
            <div class="modal-body"  >

                <form id="recordInfos" action="${springMacroRequestContext.contextPath}/record/save" class="smart-form" method="post">
                    <fieldset>
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
                                        <input id="uuidObj" name="uuidObj" type="hidden" value="${(record.call_session_uuid)!''}"> <!-- 质检的对象 -->
                                    </label>
                                </div>
                            </div>
                        </section>
                        <section>
                            <div class="row">
                                <label class="label col col-2">评分备注</label>
                                <div class="textarea col col-6">
                                	<textarea type="text" style="width: 100%;height:60px;overflow-y:visible;" name="remark">${(check.remark)!''}</textarea>  
                                </div>
                            </div>
                        </section>
                        <section>
                            <div class="row">
                                <label class="label col col-2">质检打分</label>
                                <input type="hidden" id="rate" >
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
                    </fieldset>
                </form>
            </div>
            <div class="modal-footer">
                <button id="msubmit" type="button" class="btn btn-primary">
                    保  存
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    关 闭
                </button>
            </div> 
        </div>
    </div>
</div>
    
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
			
             /* 显示弹框  */
             $('#dialog_record').modal("show");
             
             <#if sScore??>
            	getScore(${sScore});
           	 </#if>
             
            /** 校验字段  **/
            var validator =$('#recordInfos').validate({
                rules : {
                	
                },
                errorPlacement : function(error, element) {
                    error.insertAfter(element.parent());
                }
            });

            /* 提交按钮单击事件  */
            $('#msubmit').click(function(){
                if($('#recordInfos').valid()){
//                     $('#msubmit').attr("disabled",true);
                    $('#recordInfos').submit();
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
                    }
                    $('#dialog_record').modal("hide");
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
                        $('#dialog_record').dialog('hide');
                        $.smallBox({
                            title : "操作失败",
                            content : "<i class='fa fa-clock-o'></i> <i>操作失败</i>",
                            color : "#C46A69",
                            iconSmall : "fa fa-times fa-2x fadeInRight animated",
                            timeout : 2000
                        });

                        $('#dt_basic_record').DataTable().ajax.reload(null,false);;
                    }
                }
            });

        
        /* hide 是关闭调用后在关闭;hidden 是关闭后在调用； */
        $('#dialog_record').on('hidden.bs.modal', function (e) {   
        	 $("#dialog_record").remove();
        	 $('#dt_basic_record').DataTable().ajax.reload(null,false);;
		});
	});
        /* 返回数据[评分数据] */
		function getScore(uid){
			console.log(uid);
			$("#score").val(uid);
			console.log($("#score").val());
			console.log($("#uid").val());
		}
</script>

