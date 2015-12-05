package com.ruishengtech.framework.core.db.handler;


import com.ruishengtech.framework.core.db.TableDefinition;

import java.util.List;
import java.util.Map;

/**
 * query回调接口
 */
public interface ISqlResultHandler {

    void doSql(StringBuilder sql, List<Object> params);

    <T> List<T> doReturn(List<Map<String, Object>> ret, Class<T> c, TableDefinition tableDefinition);

}
