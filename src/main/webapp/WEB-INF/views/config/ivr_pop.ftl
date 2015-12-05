
<div class="modal fade" id="ivr_detail_add">
    <div class="modal-dialog " style="">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true"><strong>关闭</strong></button>

                <h4 class="modal-title"></h4>
            </div>
            <div class="modal-body">
				
				<div class="mainNavigation">
	            </div>

                <div class="form-horizontal">
					
					<div class="form-group" id="mname">
                        <label class="col-md-3 control-label">名称</label>
                        <div class="col-md-3">
                            <input class="form-control" id="mainname" style="border: 0px;background-color: white;" disabled="disabled">
                        </div>
                    </div>
					
					<div class="form-group" id="mavoices">
                        <label class="col-md-3 control-label">语音</label>
                        <div class="col-md-3">
                            <select id="mainvoices" name="mainvoices" class="required">
                                <option value="">请选择</option>
                            	<#if voices??>
		                            <#list voices as vo>
		                                <option value="${vo.voice}">${vo.name}</option>
		                            </#list>
		                        </#if>
                            </select>
                        </div>
                    </div>
                    
                    <div class="form-group" id="mapltimes">
                        <label class="col-md-3 control-label">播放次数 </label>
                        <div class="col-md-3">
                            <select  id="mainplaytimes" name="mainplaytimes" class="form-control">
                               <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                                <option value="6">6</option>
                                <option value="7">7</option>
                                <option value="8">8</option>
                            </select>
                        </div>
                    </div>



                    <!-------- -------------------------------------------->

                    
                    <div class="form-group" id="ivname">
                        <label class="col-md-3 control-label">名称</label>
                        <div class="col-md-3">
                            <input type="select" id="ivrname" name="ivrname" class="form-control">
                        </div>
                    </div>

                    <div class="form-group" id="ivkey">
                        <label class="col-md-3 control-label">按键</label>
                        <div class="col-md-3">
                            <select  id="ivrkey" class="form-control">
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                                <option value="6">6</option>
                                <option value="7">7</option>
                                <option value="8">8</option>
                                <option value="9">9</option>
                                <option value="#">#</option>
                                <option value="*">*</option>
                            </select>
                        </div>
                    </div>


                    <div class="form-group" id="ivtype">
                        <label class="col-md-3 control-label">操作类型</label>
                        <div class="col-md-3">
                            <select id="ivrtype" class="form-control">
                                <option value="main">主导航菜单</option>
                                <option value="m">导航菜单</option>
                                <option value="v">语音</option>
                                <option value="e">分机号</option>
                                <option value="a">重听</option>
                                <option value="p">返回上一级</option>
                            </select>
                        </div>
                    </div>


                    <div class="form-group" id="playvoice">
                        <label class="col-md-3 control-label">语音</label>
                        <div class="col-md-3">
                            <select id="voices" name="voices" class="required">
                                <option value="">请选择</option>
	                            <#if voices??>
	                                <#list voices as vo>
	                                    <option value="${vo.voice}">${vo.name}</option>
	                                </#list>
	                            </#if>
                            </select>
                        </div>
                    </div>

                    <div id="namenu">
                        <div class="form-group">
                            <label class="col-md-3 control-label">播放次数 </label>
                            <div class="col-md-3">
                                <select id="playtimes" name="playtimes" class="form-control">
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                    <option value="5">5</option>
                                    <option value="6">6</option>
                                    <option value="7">7</option>
                                    <option value="8">8</option>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div id="extensions" style="display: none;">
                        <div class="form-group">
                            <label class="col-md-3 control-label">类型</label>
                            <div class="col-md-3">
                                <select id="extentypes" name="extentypes" class="form-control input-xs required">
                                    <option value="">请选择</option>
	                                <#list extRou ?keys as key>
	                                    <option  value="${key}">${extRou[key]}</option>
	                                </#list>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">分机号</label>
                            <div class="col-md-3">
                                <select id="extension" name="extension" class="required">
                                    <option value=''>请选择</option>
                                </select>
                            </div>
                        </div>
                    </div>

                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    关 闭</button>
                <button id="saveIvrDetail" type="button" class="btn btn-primary">
                    保 存</button>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">

    $("#saveIvrDetail").click(function(){

        var name=$('#ivrname').val();
        var type=$('#ivrtype').val();
        var key=$('#ivrkey').val();

       <#if add??>
       
	        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	
	        //寻找子节点的个数
	        var selectnodes = treeObj.getSelectedNodes()[0];
	
	        var id = selectnodes.id;
	        if(selectnodes.children){
	            id= id+(selectnodes.children.length+1);
	        }else{
	            id= id+1;
	        }
	
	        var newNode ={ id:id, name:name, isParent:true, open:false,type:type,key:key};
	
	        if(!fillandCheck(id,type)){
	            return;
	        }
	
	        if('m'!=type){
	            newNode.isParent=false;
	        }
	        var newtreeObj = treeObj.addNodes(selectnodes, newNode);
	
	        treeObj.selectNode(newtreeObj[0]);
	        onClick(null,null,newtreeObj[0]);
	
	        ivr[id].pid=selectnodes.id;
	
	        if(ivr[selectnodes.id].child){
	            ivr[selectnodes.id].child.push(id);
	        }else{
	            ivr[selectnodes.id].child=[];
	            ivr[selectnodes.id].child.push(id);
	        }

       <#else>

           var name=$('#ivrname').val();
           var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
           var selectnodes = treeObj.getSelectedNodes()[0];

           if(!fillandCheck(selectnodes.id,type)){
               return;
           }
           selectnodes.name=name;

           treeObj.updateNode(selectnodes);
           onClick(null,null,selectnodes);

       </#if>

        $('#ivr_detail_add').modal("hide");
    });

    function fillandCheck(id,type){

        if('main'!=type){

            var name =$('#ivrname').val();
            if(!name){
                $('#ivrname').nextAll().remove();
                $('#ivrname').after("<label style='color: red' class='marked'>必填</label>");
                return false;
            }

            var key=$('#ivrkey').val();
            if(!key){
                $('#ivrkey').nextAll().remove();
                $('#ivrkey').after("<label style='color: red' class='marked'>必填</label>");
                return false;
            }
        }

        if('main'==type){

            var voices =$("#mainvoices").val();
            if(!voices){
                $('#mainvoices').nextAll().remove();
                $('#mainvoices').after("<label style='color: red' class='marked'>必填</label>");
                return false;
            }

            var playtimes = $("#mainplaytimes").val();
            if(!playtimes){
                $('#mainplaytimes').nextAll().remove();
                $('#mainplaytimes').after("<label style='color: red' class='marked'>必填</label>");
                return false;
            }

            if(!ivr[id]){
                ivr[id]={};
            }
            var c = ivr[id];
            c.type=type;
            c.ivrname='主导航';
            c.voices=voices;
            c.playtimes= playtimes;

        }else if('m'==type){

           var voices =$("#voices").val();
           if(!voices){
        	   $('#voices').nextAll().remove();
               $('#voices').after("<label style='color: red' class='marked'>必填</label>");
               return false;
           }

           var playtimes = $("#playtimes").val();
           if(!playtimes){
        	   $('#playtimes').nextAll().remove();
               $('#playtimes').after("<label style='color: red' class='marked'>必填</label>");
               return false;
           }

           //fill
            if(!ivr[id]){
                ivr[id]={};
            }
            var c = ivr[id];
            c.type=type;
            c.ivrname=name;
            c.ivrkey=key;
            c.voices=voices;
            c.playtimes= playtimes;

        }else if('v'==type){

            var voices =$("#voices").val();
            if(!voices){
            	$('#voices').nextAll().remove();
                $('#voices').after("<label style='color: red' class='marked'>必填</label>");
                return false;
            }

            if(!ivr[id]){
                ivr[id]={};
            }
            var c = ivr[id];
            c.type=type;
            c.ivrname=name;
            c.ivrkey=key;
            c.voices=voices;
            c.playtimes= playtimes;


        }else if('e'==type){

            var extension = $("#extension").val();
            if(!extension){
            	$('#extension').nextAll().remove();
                $('#extension').after("<label style='color: red' class='marked'>必填</label>");
                return false;
            }

            if(!ivr[id]){
                ivr[id]={};
            }
            var c = ivr[id];
            c.type=type;
            c.ivrkey=key;
            c.ivrname=name;
            c.etype=$('#extentypes').val();
            c.extension=extension;

        }else{

            if(!ivr[id]){
                ivr[id]={};
            }
            var c = ivr[id];
            c.ivrkey=key;
            c.type=type;
            c.ivrname=name;
        }

        return true;
    }

    pageSetUp();
    $('#ivr_detail_add').modal("show");

    $("#ivrtype").change(function(){

        var types = $("#ivrtype").val();
        if(types == "m"){
            $("#namenu").show();
            $("#playvoice").show();
            $("#extensions").hide();
        }else if(types == "v"){
            $("#namenu").hide();
            $("#playvoice").show();
            $("#extensions").hide();
        }else if(types == "e"){
            $("#namenu").hide();
            $("#playvoice").hide();
            $("#extensions").show();
        }else{
            $("#namenu").hide();
            $("#playvoice").hide();
            $("#extensions").hide();
        }
    });

    $('#extension').select2({
        allowClear : true,
        width:'99%'
    });

    $('#voices').select2({
        allowClear : true,
        width:'99%'
    });
    
    $('#mainvoices').select2({
    	allowClear : true,
        width:'99%'
    });

    $("#extentypes").change(function(){
        var types=$("#extentypes").val();

        var url=getContext()+"config/accessnumberroute/getFSXmlType";

        $.ajax({
            type : "post",
            url : url,
            data : "types=" + types,
            async : false,
            success : function(data){
                data = eval("(" + data + ")");

                $("#extension").empty();
                if (data.result=="false") {
                    $("#extension").append("没有对应的分机号！");
                }else{
                    var sb="<option value=''>请选择</option>"
                    for(var i in data){
                        if(typeof data[i] !="function"){
                            sb+="<option value='"+data[i].extension+"'>"+data[i].extension+"</option>";
                        }
                    }
                    $("#extension").append(sb);
                }
            }
        });

    });


</script>
