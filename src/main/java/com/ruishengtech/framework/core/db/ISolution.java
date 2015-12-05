package com.ruishengtech.framework.core.db;


import com.ruishengtech.framework.core.db.condition.ICondition;

import java.util.List;

/**
 * Created by yaoliceng on 14-3-25.
 */
public interface ISolution {

    void getWhere(ICondition condition, StringBuilder whereSql, List<Object> params);

    void getCountSql(StringBuilder countSql, StringBuilder whereSql);

    void getPageSql(StringBuilder pageSql, StringBuilder whereSql, ICondition condition, List<Object> params);

	List<String[]> getOrderBy(ICondition condition);

}
