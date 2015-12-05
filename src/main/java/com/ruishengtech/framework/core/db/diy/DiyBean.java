package com.ruishengtech.framework.core.db.diy;


import java.util.HashMap;
import java.util.Map;

public class DiyBean {

    private Map<String,ColumnDesign> clomns =new HashMap<>();

    public Map<String, ColumnDesign> getClomns() {
        return clomns;
    }

//    public DiyBean(List<ColumnDesign> list) {
//
//        for (ColumnDesign customerDesign : list) {
//            clomns.put(customerDesign.getColumnNameDB(),customerDesign);
//        }
//    }

}



//
//[
//// 					   { "title" : "客户姓名","data" : "name"}, 
//// 					   { "title" : "客户池编号","data" : "pool_id"},
//// 					   { "title" : "所属用户","data" : "own_id"},
//// 					   { "title" : "客户描述","data" : "customer_des"},
//// 					   { "title" : "客户标签","data" : "customerTags"},
//// 					   { "title" : "录入时间","data" : "start_date"},
//					   <#if titles??>
//					   		<@c.title titles />
//					   </#if>
//					   { 
//						   "title" : "操作",
//						   "data" : "null",
//						   "orderable":false,
//						   "render": function(data,type,full){
//							   return "<a onclick=editTag('"+full.uid+"');>信息修改</a>&nbsp;&nbsp;&nbsp;&nbsp;";
//						   }
//					   }
//					],