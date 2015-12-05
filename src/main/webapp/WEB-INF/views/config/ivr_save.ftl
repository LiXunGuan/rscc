<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/css/metroStyle/metroStyle.css" type="text/css">
<!-- <link rel="stylesheet" href="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"> -->
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/assets/js/plugin/ztree/js/jquery.ztree.all-3.5.min.js"></script>
<div class="modal-body">
    <button  id="addbtn" class="btn btn-default" >新增按键</button>
    <button  id="editbtn" class="btn btn-default" >编辑</button>
    <button  id="deletebtn"  class="btn btn-default" >删除</button>
	<form id="fxxmlForm" action="${springMacroRequestContext.contextPath}/config/ivr/save" method="post">
          <div class="smart-form">
                <section></section>
				<section>
					<div class="row">
						<label class="label col col-1">名称</label>
						<label class="input col col-2">
                            <input id="id" type="hidden" onfocus="onfocus" name="id" value="${(item.id)!''}" />
                            <input id="extenid" type="hidden" onfocus="onfocus" name="extenid" value="${(extenid)!''}" />
                            <input id="name" onfocus="onfocus" name="name" value="${(item.name)!''}" />
                            <input id="ivrcontent" name="ivrcontent" type="hidden"/>
						</label>

					</div>

                    <br/>

                    <div class="row">

                    <label class="label col col-1">分机</label>
                    <label class="input col col-2">
                        <input id="exten" onfocus="onfocus" name="exten" value="${(item.exten)!''}" />
                    </label>
                    </div>


                </section>
<!--                 <section> -->
<!-- 	                <div class="row"> -->
<!-- 	                    <label class="label col col-1">语音</label> -->
<!-- 	                    <div class="col col-2"> -->
<!-- 		                    <label class="select"> -->
<!-- 		                        <select id="mainvoices" name="mainvoices" class="required"> -->
<!-- 		                            <option value="">请选择</option> -->
<!-- 			                        <#if voices??> -->
<!-- 			                            <#list voices as vo> -->
<!-- 			                                <option value="${vo.voice}">${vo.name}</option> -->
<!-- 			                            </#list> -->
<!-- 			                        </#if> -->
<!-- 		                        </select> -->
<!-- 		                    </label> -->
<!-- 	                    </div> -->
<!-- 	                    <label class="label col col-1">播放次数 </label> -->
<!--                         <label class="select col col-2"> -->
<!--                             <select id="mainplaytimes" name="mainplaytimes"> -->
<!--                                 <option value="1">1</option> -->
<!--                                 <option value="2">2</option> -->
<!--                                 <option value="3">3</option> -->
<!--                                 <option value="4">4</option> -->
<!--                                 <option value="5">5</option> -->
<!--                                 <option value="6">6</option> -->
<!--                                 <option value="7">7</option> -->
<!--                                 <option value="8">8</option> -->
<!--                             </select> -->
<!--                         </label> -->
<!--                     </div> -->
<!--                 </section> -->
            </div>
			<div class="form-group row">	
                <label class="col-md-1 control-label">IVR树</label>
                <div class="col-md-1">
               	 	<div id="treeDemo" class="ztree" style="overflow-x: auto;overflow-y: auto;height: 400px;width: 650px;"></div>
           	    </div>
		    </div>
	</form>
	<div class="modal-footer">
		<button type="button" class="btn btn-default" id="back">
			返回</button>
		<button id="addFxxml" type="button" class="btn btn-primary">
			保 存</button>
	</div>
</div>

<div id="forModelpop"></div>

<script type="text/javascript">


    var treeData={};

    var newCount = 1;
    
    var setting = {
        data: {
        	keep:{
        		parent:true
        	},
            simpleData: {
                enable: true
            }
        },
        callback:{
            onClick: onClick
        }
    };

    $("#addbtn").click(function(){
    	
        $('#forModelpop').empty();

        var url = getContext() + "config/ivr/ivr";
        $.post(url, {add:true},function(data) {
            
        	$("#forModelpop").append(data);
	    	
            $('#mavoices').hide();
	        $('#mapltimes').hide();
	        $('#mname').hide();
        
        });

    });
    

    $("#editbtn").click(function(){
        $('#forModelpop').empty();

	    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var node = treeObj.getSelectedNodes()[0];

        var url = getContext() + "config/ivr/ivr";
        $.post(url, {},function(data) {
            
        	
        	$("#forModelpop").append(data);

            console.log(ivr);
            if(ivr[node.id]){
            	
                
            	$('#ivname').show();
                $('#ivkey').show();
                $('#ivtype').show();
            	
            	$('#ivrname').val(ivr[node.id].ivrname);
                $('#ivrkey').val(ivr[node.id].ivrkey);
                $('#ivrtype').val(ivr[node.id].type).change();

                $('#mavoices').hide();
                $('#mapltimes').hide();
                $('#mname').hide();
                
                if("main" == ivr[node.id].type){
                    $('#ivname').hide();
                    $('#ivkey').hide();
                    $('#ivtype').hide();
                	
                	$('#mname').show();
                	$('#mavoices').show();
                    $('#mapltimes').show();
                	
                    $('#mainname').val("主导航");
                	$('#mainvoices').select2('val',ivr[node.id].voices);
                	$('#mainplaytimes').val(ivr[node.id].playtimes);
                	
                }else if("m"== ivr[node.id].type){

                    $('#voices').select2('val',ivr[node.id].voices);
                    $('#playtimes').val(ivr[node.id].playtimes);

                }else if("v"==ivr[node.id].type){

                    $('#voices').select2('val',ivr[node.id].voices);

                }else if("e"==ivr[node.id].type){

//                    alert('');
                    $('#extentypes').val(ivr[node.id].etype).change();
                    $('#extension').select2('val',ivr[node.id].extension);
                }

            }
        });

    });

    $("#deletebtn").click(function(){
    	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    	var nodes = treeObj.getSelectedNodes();
		//判断是否选中节点
    	if (nodes.length == 0) {
			alert("请先选择一个树节点！");
			return;
		}
		if(confirm("确认删除节点的所有节点信息吗？")){
	    	var parentNode = nodes[0].getParentNode();/* 得到父节点的信息 */
	    	var parentData = ivr[parentNode.id].child;/* 得到父节点数据的信息 */
	    	parentData.remove(nodes[0].id); /* 删除数据中将删除节点的信息 */
			var resultData = checkAllChildrens(nodes[0]); /* 得到删除节点下的所有子节点信息 */
			for(var j = 0;j < resultData.length; j++){
				delete ivr[resultData[j]]; /* 删除json中存放的数据    */
			}
			/* 删除树上的信息   */
			if(nodes[0].children){
				treeObj.removeChildNodes(nodes[0]);/* 删除子节点 */ 
			}
			treeObj.removeNode(nodes[0]);/* 删除节点 */
	    	k = 0;
	    	result = [];
		}
    });
	
	var k = 0;
	var result = [];
	/* 返回节点的所有子节点信息   */
	function checkAllChildrens(treeNode){
		result[k] = treeNode.id;
		var childs = treeNode.children;
		if(childs){
			for(var i = 0; i < childs.length; i++){
				k++;
				result[k] = childs[i].id;
				if(childs[i].isParent){
					result = checkAllChildrens(childs[i], result);
				}
			}
		}
		return result;
	}
    
    //返回按钮
    $("#back").click(function(){
        $("#maincontent").show();
        $('#ivrForModel').empty();
    });
    

    /* 树节点的点击事件  */
    function onClick(e, treeId, treeNode) {

        if("main" == treeNode.type){
            $('#addbtn').show();
            $('#editbtn').show();
            $('#deletebtn').hide();
        }else if("m" == treeNode.type){
        	$('#addbtn').show();
            $('#editbtn').show();
            $('#deletebtn').show();
        }else{
            $('#addbtn').hide();
            $('#editbtn').show();
            $('#deletebtn').show();
            
        }
		return false;
    }

    /* 树的加载数据  */

    <#if item??>

        var ivr={};
        var zNodes =[];

        var nodes = eval("("+ '${item.content}'+")");

        for(var p in nodes){

            var node={};
            node.id= p;
            node.name= nodes[p].ivrname;
            node.open=true;
            node.type= nodes[p].type;
            node.pId=nodes[p].pid;
            zNodes.push(node);

           ivr[p]={};
           ivr[p].type=nodes[p].type;;
           ivr[p].ivrname=nodes[p].ivrname;
           ivr[p].ivrkey=nodes[p].ivrkey;
           ivr[p].pid=nodes[p].pid;

           ivr[p].voices=nodes[p].voices;
           ivr[p].playtimes=nodes[p].playtimes;
           ivr[p].etype=nodes[p].etype;
           ivr[p].extension=nodes[p].extension;

           ivr[p].child=nodes[p].child;
        }

    <#else>

        var zNodes =[
            { id:1, pId:0, name:"主导航", isParent:true, open:true, type:'main'},
            { id:2, pId:1, name:"1 语音", type:'v', key:'1'},
            { id:3, pId:1, name:"* 重听", type:'a', key:'*'},
            { id:4, pId:1, name:"# 返回上一级", type:'p', key:'#'}
        ];

        var ivr={};
        ivr[1]={ivrname:" 主导航",voices:"/upload/1432544856936.mp3",playtimes:"1",type:"main",child:[2,3,4]};
        ivr[2]={ivrname:"1 语音",voices:"/upload/1432544856936.mp3",type:"v",ivrkey:"1",pid:1};
        ivr[3]={ivrname:"* 重听",type:"a",ivrkey:"*",pid:1};
        ivr[4]={ivrname:"# 返回上一级",type:"p",ivrkey:"#",pid:1};

    </#if>


	$(document)
			.ready(
					function() {

						pageSetUp();
                        
						$.fn.zTree.init($("#treeDemo"), setting, zNodes);
						
						$('#addbtn').hide();
			            $('#editbtn').hide();
			            $('#deletebtn').hide();

                        /* 展示modal */
// 						$('#fxxml_index_add').modal("show");
                        
// 						$('#fxxml_index_add').on('hide.bs.modal', function(e){
// 							$("#fxxml_index_add").remove();
// 						});

						/* 提交按钮单击事件  */
						$('#addFxxml').click(function() {
							if ($('#fxxmlForm').valid()) {

//                                ivr[1].voices=$('#mainvoices').val();
//                                ivr[1].playtimes=$('#mainplaytimes').val();
//                                ivr[1].name=$('#name').val();

                                $('#ivrcontent').val(JSON.stringify(ivr));
// 								$("#addFxxml").attr("disabled", "true");
								$('#fxxmlForm').submit();
							}

						});

						/* 校验数据 */
						var validator = $('#fxxmlForm')
								.validate(
										{
											rules : {
												name : {
													required : true,
													remote : {
														type : 'post',
														url : getContext()+ 'config/fsxml/isRepeat',
														data : {
															id : function() {
																return $("#fxxmlForm #id").val(); /* 传递用户编号 */
															},
															name:function() {
																return $("#name").val();
															}
														}
													}
												},


                                                exten : {
                                                    required : true,
                                                    maxlength : 32,
                                                    remote : {
                                                        type : 'post',
                                                        url : getContext() + 'config/extenroute/isRepeatExtension',
                                                        data : {
                                                            id : function() {
                                                                return $("#extenid").val(); /* 传递用户编号 */
                                                            },
                                                            extension : function() {
                                                                return $("#exten").val();
                                                            }
                                                        }
                                                    }
                                                },

												cata : {
													required : true
												}
											},
                                            messages : {
                                                exten : {
                                                    remote : $.format("该分机已存在！")
                                                },
                                            },

											errorPlacement : function(error,
													element) {
												error.insertAfter(element
														.parent());
											}
										});

						$('#fxxmlForm')
							.ajaxForm(
								{
									dataType : "json",
									success : function(data) {
										if (data.success) {
											$("#toDeployButton").removeAttr("disabled");
							            	$("#toDeployButton").removeClass("btn-default");
							    			$("#toDeployButton").addClass("btn-sm");
							    			$("#toDeployButton").addClass("btn-success");
											$.smallBox({
												title : "操作成功",
												content : "<i class='fa fa-clock-o'></i> <i>操作成功</i>",
												color : "#659265",
												iconSmall : "fa fa-check fa-2x fadeInRight animated",
												timeout : 2000
											});

                                            $("#maincontent").show();
                                            $('#ivrForModel').empty();
                                            oTable.DataTable().ajax.reload(null,false);;
										}
// 										$('#fxxml_index_add').modal("hide");
										validator.resetForm();
									},
									submitHandler : function(form) {
										$(form)
												.ajaxSubmit(
														{
															success : function() {
		
																$("#fxxmlForm").addClass('submited');
															}
														});
									},
									error : function(XMLHttpRequest,
											textStatus, errorThrown) {
										if (textStatus == 'error') {
// 											$('#fxxml_index_add').dialog('hide');
											$
													.smallBox({
														title : "操作失败",
														content : "<i class='fa fa-clock-o'></i> <i>操作失败</i>",
														color : "#C46A69",
														iconSmall : "fa fa-times fa-2x fadeInRight animated",
														timeout : 2000
													});
		
											oTable.DataTable().ajax.reload(null,false);;
										}
									}
								});

					});

	$('#ownagent').select2({
		allowClear : true,
		width : '99%'
	});
	$('#userId').select2({
		allowClear : true,
		width : '99%'
	});
	$('#useravilable').select2({
		allowClear : true,
		width : '99%'
	});
	
	$('#mainvoices').select2({
		allowClear : true,
		width : '99%'
	});
	
	$('#mainplaytimes').select2({
		allowClear : true,
		width : '99%'
	});
	
</script>
