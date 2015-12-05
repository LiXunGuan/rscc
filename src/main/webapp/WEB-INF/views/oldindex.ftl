<!DOCTYPE html>
<html lang="en-us">
<head><#include "index/importcss.ftl">
</head>
<body class="" >
	<#include "index/header.ftl"> <#include "index/menu.ftl"> <#include "index/body.ftl"> <#include "index/importsjs.ftl">
	<div id="bindExtenDiv"></div>
	<!-- user js -->
	<script type="text/javascript">
	
		$(document).on("dragstart","a",function(){
			return false;
		});

		var tabs = $('#tabs2').tabs();
		
		/* 添加table */
		function addTab(label, name, num, refresh) {
			
			var str = "";
			if(typeof num == "object" ){
				
				str = JSON.stringify(num);
			}else{
				
				str = num;
			}
			
			var label = label;
			var id = "tabs-" + name ;
			if ($('#' + id).length == 0) {

				var li = "<li style='position:relative;'> <span class='air air-top-left delete-tab' m='"+name+"' style='top:7px; left:7px;'>"
						+ "<button class='btn btn-xs font-xs btn-default hover-transparent'><i class='fa fa-times'></i>"
						+ "</button></span></span><a m='"+name+"' phone='"+str+"' class='forclick' href='"+"#" + id+"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
						+ label + "</a></li>";
						
				tabs.find(".ui-tabs-nav").append(li);
				tabs.append("<div id='" + id + "'>" + "载入中" + "</div>");
				tabs.tabs("refresh");
			}

			$("#tabs2").children("div").empty();
			
			if(refresh != 'refresh'){
				
				$('[href=#' + id + ']').click();
			}
			
			$("#tabs2 ul.ui-tabs-nav li").smartMenu(menuData);
		}
		
	    $("#tabs2").on("click", 'a.forclick', function() {
		    
	    	/* 添加表单样式 */
	    	$("#left-panel div nav ul li a").css("color","#c0bbb7;");
		    var text = $("li[aria-selected='true'] a").text().trim();
		    $("#left-panel div nav ul li a:contains("+text+")").css("color","white");

		    $("#tabs2").children("div").empty();
	
	    	var url= getContext() + 'addTab';
	        var name= $(this).attr("m");
	        var label= $(this).text().trim();
	        var detail = $(this).attr('phone');
	        
	        if(detail=="undefined"){
	        	detail="";
	        }
	        
	        $.post(url,{name:name,labelname:label,detail:detail},function(data){
	        	
	            $('#tabs-'+name).empty();
	            $('#tabs-'+name).append(data);
	        });
	        
	    });
		
	    $("#tabs2").on("click", 'span.delete-tab', function() {
			
	    	/* 请求一次记录 */
	    	var url= getContext() + 'deletetab' ;
	        var name = $(this).attr('m');

	        var thiz =$(this);

	        $.post(url,{name:name},function(data){
	            var panelId = thiz.closest("li").remove().attr("aria-controls");
	            $("#" + panelId).remove();
	            tabs.tabs("refresh");
	        	$("#tabs2 .ui-tabs-active").children("a").click();
	        	backStatus();
	        });
	       
	        
	    });
	    
	    
	    window.autoCallList = {};
	    
	    function backStatus(){
	    	
	    	//开始下一个呼叫
	    	var startNext = function() {
				if(window.nowCallStat=="down" && window.startAutoCall && window.autocallConfig && window.autocallConfig.timing == 1) {
					var nextPhone = getNextPhoneByAutoCall();
					var url;
					if(nextPhone != undefined) {
						if(nextPhone == -1) {
							changeAutoCallStat();
							return;
						}
						url = getContext() + "newuserdata/makecall"
					} else {
						url = getContext()+"newuserdata/callNextPhone";
					}
    				$.post(url,{phone:nextPhone},function(data){
						if(data.success) {
							
						} else {
							 changeAutoCallStat();
							 $.smallBox({
			                    title : "失败",
			                    content : "<i class='fa fa-clock-o'></i> <i>呼叫失败，"+data.message+"</i>",
			                    color : "#C46A69",
			                    iconSmall : "fa fa-times fa-2x fadeInRight animated",
			                    timeout : 3000
			                });
						}
    				},"json").error(function(){
    					 changeAutoCallStat();
    					 $.smallBox({
    	                   title : "自动呼叫失败",
    	                   content : "<i class='fa fa-clock-o'></i> <i>呼叫失败</i>",
    	                   color : "#C46A69",
    	                   iconSmall : "fa fa-times fa-2x fadeInRight animated",
    	                   timeout : 3000
    	               });
    				});
				} else if (window.startAutoCall && window.autocallConfig && window.autocallConfig.timing == 1) {
					//关闭自动呼叫
					changeAutoCallStat();
				}
			}
	    	
	    	if($("#main div div ul:eq(0) li").text().indexOf("|") < 0) {//存在 
	   			//尝试还原开始的状态 取session中的值，如果没有置闲 如果存在 还原原来的状态
	   			$.post(getContext()+'reBackAgentStatus',function(d){
	   				if(d.msg=="nobusy"){
	   					nopause();
	   				}
	   			},"json");
	    	
	    		//如果状态是开始呼叫，也就是说可以把down状态去掉，在方法里面判断
	    		if(window.nowCallStat=="down" && window.startAutoCall && window.autocallConfig && window.autocallConfig.timing == 1) {
	    			
	    			var nextCallTime = (window.autocallConfig && window.autocallConfig.time?window.autocallConfig.time:5) * 1000;
					setTimeout(startNext,1000);
	    		} else if (window.startAutoCall && window.autocallConfig && window.autocallConfig.timing == 1) {
// 	    			setTimeout(startNext,1000);
	    			changeAutoCallStat();
	    		}
			}
	    }
	    
	    <#if Session["TAB"]?exists>
	        <#list Session["TAB"]?values as val>
	        	<#if (val.label)??>
        			addTab('${(val.label)!''}','${(val.name)!''}', {}, 'refresh');
        		</#if>
	        </#list>
            <#if Session['TABSELECTED']??>
                $('[href=#tabs-${Session['TABSELECTED']}]').click();
            <#else>
                $(".forclick")[0].click();
            </#if>
        <#else>
            $("#myMenu a:not([onclick='']):first").click();
   		</#if>
   		
	    var menuData = 
	    	[
	    	 [{
                text: "关闭",
                func: function() {
                    $(this).find("span").click();
                }
	    	  },{
    	        text: "关闭全部",
    	        func: function() {
    	        	$("#tabs2 ul.ui-tabs-nav span").click();
    	        }
    	      },{
    	        text: "关闭其他",
    	        func: function() {
    	        	$(this).siblings().find("span").click();
    	        }
    	     }],
    	     [
    	      {
    	    	text: "关闭右侧标签",
     	        func: function() {
     	        	$(this).nextAll().find("span").click();
     	        }
    	      },{
      	    	text: "关闭左侧标签",
       	        func: function() {
       	        	$(this).prevAll().find("span").click();
       	        }
      	      }
    	     ]
	    	];
	    
	    $("#tabs2 ul.ui-tabs-nav li").smartMenu(menuData);	    

	    function onunload_handler(){
			return;
	    } 
	    
	</script>
</body>
</html>