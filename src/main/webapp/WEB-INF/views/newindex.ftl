<!DOCTYPE html>
<html lang="en-us">
<head>
<#include "index/importcss.ftl">
</head>
<body class="" >
	<#include "index/header.ftl"> <#include "index/menu.ftl"> <#include "index/body.ftl"> <#include "index/importsjs.ftl">
	<div id="bindExtenDiv"></div>
	<!-- user js -->

    <form id="formforiframeform" action='' method='POST' target=''>
        <input type='hidden' name='name' />
        <input type='hidden' name='labelname' />
        <input type='hidden' name='detail' />
        <input type='hidden' name='needcssjs' />
    </form>

    <script type="text/javascript">
    	//定义一个变量，如果需要  不显示关闭tab的button的话，在方法中将该变量设置为1
    	var closetag = "0";
    	//定义隐藏 【关闭tab按钮】的方法
    	function hiddenTabButton(){
    		//取到当前活跃的tab页头
        	var tabText = $("#tabs2").find("li.ui-tabs-active");
        	if($(tabText).text().trim().substr(0,3)=="弹屏|" && closetag=="1"){
        		$(tabText).children("span.delete-tab").hide();
        	}
    	}
    	

    	/* a标签不允许拖动 */
		$(document).on("dragstart","a",function(){
			return false;
		});

        var opentab={};
        
        //var tabCounter = 2;
		var tabs = $('#tabs2').tabs();
		
		/* 添加table */
		function addTab(label, name, num, refresh, par) {
			
			var str = "";
			if(typeof num == "object" ){
				
				str = JSON.stringify(num);
			}else{
				
				str = num;
			}
			
			var label = label;
			var id = "tabs-" + name ;
			if ($('#' + id).length == 0) {
				
				var li = "<li style='position:relative;'> <span class='air air-top-left delete-tab' m='"+name+"' par='"+par+"' style='top:7px; left:7px;'>"
						+ "<button class='btn btn-xs font-xs btn-default hover-transparent'><i class='fa fa-times'></i>"
						+ "</button></span></span><a m='"+name+"' phone='"+str+"' class='forclick' href='"+"#" + id+"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
						+ label + "</a></li>";
						
				tabs.find(".ui-tabs-nav").append(li);
				tabs.append("<div id='" + id + "'>" + "" + "</div>");
				tabs.tabs("refresh");
			}

			if(refresh != 'refresh'){
				
				$('[href=#' + id + ']').click();
			}
			
// 			addedTab[id] = new Date().getTime();
			$("#tabs2 ul.ui-tabs-nav li").smartMenu(menuData);
		}
		
	    $("#tabs2").on("click", 'a.forclick', function() {
		    
	    	/* 添加表单样式 */
	    	$("#left-panel div nav ul li a").css("color","#c0bbb7;");
		    var text = $("li[aria-selected='true'] a").text().trim();
		    $("#left-panel div nav ul li a:contains("+text+")").css("color","white");
	
	    	var url= getContext() + 'addTab';
	        var name= $(this).attr("m");
	        var label= $(this).text().trim();
	        var detail = $(this).attr('phone');
	        
	        if(detail=="undefined"){
	        	detail="";
	        }

			/* 存放点击的顺序 */
			addToTab("tabs-"+name);
			
            if(opentab[name]){

            }else{
                var iframename = "iframe"+name;
                $('#tabs-'+name).append("<iframe style='z-index:9999;overflow-x:hidden;' width='100%' scrolling='auto' scrolling-x='hidden'  marginheight='0' marginwidth='0' id='"+iframename+"'  onLoad='iFrameHeight(\""+iframename+"\")'  frameborder='0' name='"+iframename+"'> </iframe>");

                $('#formforiframeform').attr('action',url);
                $('#formforiframeform').attr('target',iframename);
                $('#formforiframeform').find('[name="name"]').val(name);
                $('#formforiframeform').find('[name="labelname"]').val(label);
                $('#formforiframeform').find('[name="detail"]').val(detail);
                $('#formforiframeform').find('[name="needcssjs"]').val("true");

                $('#formforiframeform').submit();
                opentab[name]=true;
            }

            /* 点击的时候，ajax刷新页面 */
			if( window.frames["iframe"+name] && typeof window.frames["iframe"+name].getResult == 'function'){
				window.frames["iframe"+name].getResult();
			}
            
	    });

        function iFrameHeight(iframename) {
            var ifm= document.getElementById(iframename);
            var subWeb = ifm.contentDocument;
            if(ifm != null && subWeb != null) {
                ifm.height = window.screen.height - 218;
            }
        }
        
        //给tab标签上的删除按钮绑定click事件
        $("#tabs2").on("click", 'span.delete-tab', function() {
        
			 /* 如果关闭的是通话弹屏tab 那么发送请求到后台，如果是已通话 判断保存的位置[我的任务，或者共享池]  */
			 //获取到tab栏中的信息
	        var tabinfo = $(this).next("a").text().trim();
	        var tabname = tabinfo.substring(0,tabinfo.indexOf("|"));
	       	var phone = tabinfo.substring(tabinfo.indexOf("|")+1,tabinfo.length);
			
// 	       	if("弹屏"==tabname){
// 	       	//往后台发送post请求，查询这个弹屏的号码是否已经是意向或者成交客户了
// 				$.post(getContext()+"checkphone",{phone:phone},function(data){
// 					//如果高级选项中保存的是【保存到共享池】并且不是 (意向客户或者成交客户)
// 		       		if("${closepop}"=="share" && data){
// 		       			//调用ifrmes中的方法  []里面的值为iframe的id
// 		       			if (typeof window.frames["iframe"+phone].closeSave == "function") {
// 						 	window.frames["iframe"+phone].closeSave();
// 		       			}
// 					 }
// 				});
// 	       	}
	       	
	    	/* 请求一次记录 */
	    	var url= getContext() + 'deletetab';
	        var name = $(this).attr('m');
            opentab[name]=false;

	        var thiz =$(this);
	        $.post(url,{name:name},function(data){
	        	
	            var panelId = thiz.closest("li").remove().attr("aria-controls");
	            $("#" + panelId).remove();
//	        	$("#tabs2 .ui-tabs-active").children("a").click();
	            tabs.tabs("refresh");
	            
// 	            $("#tabs2 .ui-tabs-active").children("a").click();
		        delete addedTab[panelId];
	            $('[href=#' + getLatestTabName() + ']').click();
		        
	            if("弹屏"==tabname){ 
		        	backStatus();
	            }
	        });
	        
	        
	        // Add By ChengXin
	        if("弹屏"==tabname){
	        	var par = $(this).attr('par');
	        	var partab = "tabs-"+par;
	    		if($('[href=#'+partab+']').is(':visible')){
	    			$('[href=#'+partab+']').click();
	    			window.frames["iframe"+par].getResult();
	    		}
	        }
	        
// 	        var previous = tabList.pop();
//         	while ($("a[m='" + previous + "']").length = 0) {
//         		previous = tabList.pop();
//         	};
//         	$("a[m='" + previous + "']").click();
	        
// 	        var previous = tabList.pop();
// 	        sessionStorage.setItem("tab",tabList);

	    });
	    
        window.autoCallList = {};
	    
		function backStatus(){
	    	
	    	//开始下一个呼叫
	    	var startNext = function() {
	    		
	    		// 1、挂断     2  、关闭所有窗口(1) 3、  配置的是自动呼叫 4.呼叫时机是关闭后在呼
				if(window.nowCallStat=="down" && window.startAutoCall && window.autocallConfig && window.autocallConfig.timing == 1) {
					
					//获取下一个要呼叫的号码
					var nextPhone = window.frames["iframenewuserdata"].getNextPhoneByAutoCall();
					var url;
					
					//如果有取到 返回号码 否则 返回-1；
					if(nextPhone != undefined) {
						if(nextPhone == -1) {
							//如果返回-1 则没有要拨打的号码，停止呼叫；
							window.frames["iframenewuserdata"].changeAutoCallStat();
							return;
						}
						url = getContext() + "newuserdata/makecall";
					
						
					//开始呼叫 取到号码了，呼叫下一个;
					} else {
						url = getContext()+"newuserdata/callNextPhone";
					}
					
    				$.post(url,{phone:nextPhone},function(data){
    					
						if(data.success) {
							
						} else {
							 window.frames["iframenewuserdata"].changeAutoCallStat();
							 $.smallBox({
			                    title : "失败",
			                    content : "<i class='fa fa-clock-o'></i> <i>呼叫失败，"+data.message+"</i>",
			                    color : "#C46A69",
			                    iconSmall : "fa fa-times fa-2x fadeInRight animated",
			                    timeout : 3000
			                });
						}
    				},"json").error(function(){
    				     
    					 //错误也关闭自动呼叫
    					 window.frames["iframenewuserdata"].changeAutoCallStat();
    					 $.smallBox({
    	                   title : "自动呼叫失败",
    	                   content : "<i class='fa fa-clock-o'></i> <i>呼叫失败</i>",
    	                   color : "#C46A69",
    	                   iconSmall : "fa fa-times fa-2x fadeInRight animated",
    	                   timeout : 3000
    	               });
    				});
    			
    			//机器未挂断 关闭自动呼叫
				} else if (window.startAutoCall && window.autocallConfig && window.autocallConfig.timing == 1) {
					//关闭自动呼叫
					 window.frames["iframenewuserdata"].changeAutoCallStat();
				}
			}
	    	
	    	if($("#main div div ul:eq(0) li").text().indexOf("|") < 0) {//存在 
	   			//尝试还原开始的状态 取session中的值，如果没有,置闲;  如果存在,还原原来的状态
// 	   			$.post(getContext()+'reBackAgentStatus',function(d){
   				if($("#dLabel").html().indexOf("自动置忙") >= 0){
   					nopause();
   				}
// 	   			},"json");
	    	
	    		//如果状态是开始呼叫，也就是说可以把down状态去掉，在方法里面判断
	    		if(window.nowCallStat=="down" && window.startAutoCall && window.autocallConfig && window.autocallConfig.timing == 1) {
	    			
	    			//设置下次呼叫的时间 ，如果存在，则为设置的时间，如果不存在 则为默认的5秒;
	    			var nextCallTime = (window.autocallConfig && window.autocallConfig.time?window.autocallConfig.time:5) * 1000;
					setTimeout(startNext,1000);
					
					//如果是关闭后呼叫的情况 电话状态不为down 关闭自动呼叫
	    		} else if (window.startAutoCall && window.autocallConfig && window.autocallConfig.timing == 1) {
// 	    			setTimeout(startNext,1000);
	    			window.frames["iframenewuserdata"].changeAutoCallStat();
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
                    closetag="0";
                }
	    	  },{
    	        text: "关闭全部",
    	        func: function() {
    	        	$("#tabs2 ul.ui-tabs-nav span").click();
    	        	 closetag="0";
    	        }
    	      },{
    	        text: "关闭其他",
    	        func: function() {
    	        	$(this).siblings().find("span").click();
    	        	 closetag="0";
    	        }
    	     }],
    	     [
    	      {
    	    	text: "关闭右侧标签",
     	        func: function() {
     	        	$(this).nextAll().find("span").click();
     	        	 closetag="0";
     	        }
    	      },{
      	    	text: "关闭左侧标签",
       	        func: function() {
       	        	$(this).prevAll().find("span").click();
       	         	closetag="0";
       	        }
      	      }
    	     ]
	    	];
	    
	    $("#tabs2 ul.ui-tabs-nav li").smartMenu(menuData);	    
	       
	    function onunload_handler(){
			return;
	    } 
	    
	var hiddenPhoneFlag = {globalHide : false, hasPermission : false};
	
	<#if hiddenPhone?? && "true" == hiddenPhone>
		hiddenPhoneFlag.globalHide = true;
	<#else>
		hiddenPhoneFlag.globalHide = false;
	</#if>
	
	<#if hasPhonePermission?? && true == hasPhonePermission>
		hiddenPhoneFlag.hasPermission = true;
	<#else>
		hiddenPhoneFlag.hasPermission = false;
	</#if>
	
	/*隐藏号码*/
	hiddenPhone = function hiddenPhone(phoneNumber){
		if(hiddenPhoneFlag.globalHide && !hiddenPhoneFlag.hasPermission){

			return phoneNumber.replace(/(\d{3})\d*/, '$1********');
		}else{
			
			return phoneNumber; 
		}
	}
   		
	</script>
</body>
</html>