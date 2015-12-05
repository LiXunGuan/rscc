package com.ruishengtech.framework.core.db.service;


import com.ruishengtech.framework.core.db.*;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.condition.Page;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.framework.core.db.handler.ISqlResultHandler;
import com.ruishengtech.framework.core.db.handler.SimpleHandler;
import com.ruishengtech.framework.core.util.Beans;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;
import java.util.Date;

@Service
@Transactional
public class CopyOfBaseService extends JdbcService {

	
    public void save(String tableName, Object o) {
        save(tableName, o, new String[]{});
    }

    public void save(String tableName, Object o, String[] excludeFieldName) {

        List<String> excludes = Arrays.asList(excludeFieldName);
        TableDefinition tableDefinition = getTableDefinition(o.getClass());

        final List<Object> params = new ArrayList<Object>();
        final StringBuilder insertSql = getInsertSql(o, excludes, tableDefinition, params, Template.JDBC);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        final JdbcTemplate jdbcTemplate = getTemplate();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                PreparedStatement ps = con.prepareStatement(insertSql.toString(), Statement.RETURN_GENERATED_KEYS);
                int i = 1;
                for (Object param : params) {
                    ps.setObject(i++, param);
                }
                return ps;
            }
        }, keyHolder);

        try {
            String uuidValue = keyHolder.getKey().toString();
            if(StringUtils.isNotBlank(uuidValue)){
            	BeanUtils.setProperty(o, "id", uuidValue);
            }
        } catch (Exception e) {
        	String s = "a";
        }

        //同时插入缓存
        if (tableDefinition.isCache()) {
//            saveCacheBean(o, tableDefinition);
        }
    }

//    public void saveCacheBean(Object o, TableDefinition tableDefinition) {
//
//        List<Object> params = new ArrayList<Object>();
//        StringBuilder insertSql = getInsertSql(o, new ArrayList<String>(), tableDefinition, params, Template.CACHE);
//        getTemplate(Template.CACHE).update(insertSql.toString(), params.toArray());
//    }


    protected StringBuilder getInsertSql(Object o, List<String> excludes, TableDefinition tableDefinition, List<Object> params, Template template) {

        final StringBuilder insertSql = new StringBuilder();
        insertSql.append(" INSERT INTO ").append(tableDefinition.getTableName());

        StringBuilder from = new StringBuilder("(");
        StringBuilder into = new StringBuilder("(");
        for (FieldDefinition fieldDefinition : tableDefinition.getDefinitions().values()) {
            if (!excludes.contains(fieldDefinition.getFieldName())) {

                if (tableDefinition.getKeys().contains(fieldDefinition.getFieldName()) && template.equals(Template.JDBC)) {
                	
                	if("uuid".equals( fieldDefinition.getFieldName())){
	                	 from.append(fieldDefinition.getColumnName()).append(",");
	                     into.append("?,");
	                     UUID uuid = UUID.randomUUID(); 
	                     params.add(uuid.toString());
	                     try {
							BeanUtils.setProperty(o, "uuid", uuid);
						} catch (IllegalAccessException
								| InvocationTargetException e) {
							e.printStackTrace();
						}
                	}
                	
                    continue;
                }
                from.append(fieldDefinition.getColumnName()).append(",");
                into.append("?,");
                Object value = getFiledValue(o, fieldDefinition);
                //特殊处理
                if (value instanceof Date) {
                    Timestamp t = new Timestamp(((Date) value).getTime());
                    params.add(t);
                } else {
                    params.add(value);
                }
            }
        }

        insertSql.append(from.deleteCharAt(from.length() - 1).append(")"));
        insertSql.append(" VALUES ");
        insertSql.append(into.deleteCharAt(into.length() - 1).append(")"));
        return insertSql;
    }

    private Object getFiledValue(Object o, FieldDefinition fieldDefinition) {
    	
    	
        Object value = Beans.getPropertyValue(o, fieldDefinition.getFieldName());
        if (value == null) {
            //尝试得到默认值
            Method method = null;
            try {
                method = o.getClass().getMethod(fieldDefinition.getFieldName() + "Default", new Class[]{});
            } catch (NoSuchMethodException e) {
            }
            if (method != null) {
                try {
                    value = method.invoke(o, new Object[]{});
                } catch (Exception e) {
                }
            }
        }
        return value;
    }

    private TableDefinition getTableDefinition(Class<?> o) {

        TableDefinition tableDefinition = DbContext.getInstance().getTablesMap().get(o);
//        if (tableDefinition == null) {
//            throw new RuntimeException(" the class[" + o + "] is not registered");
//        }
        return tableDefinition;
    }

    public void update(Object o) {
        update(o, new String[]{});
    }

    public void update(Object o, String[] excludeFieldName) {

        List<String> excludes = Arrays.asList(excludeFieldName);
        TableDefinition tableDefinition = getTableDefinition(o.getClass());

        StringBuilder updateSql = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        updateSql.append(" UPDATE ").append(tableDefinition.getTableName());
        updateSql.append(" SET ");

        StringBuilder update = new StringBuilder();
        StringBuilder where = new StringBuilder();
        for (FieldDefinition fieldDefinition : tableDefinition.getDefinitions().values()) {
            //主键不能放在更新语句里
            if (!tableDefinition.getKeys().contains(fieldDefinition.getFieldName()) && !excludes.contains(fieldDefinition.getFieldName())) {
                update.append(" ").append(fieldDefinition.getColumnName()).append(" =?,");
                params.add(Beans.getPropertyValue(o, fieldDefinition.getFieldName()));
            }
        }
        for (String key : tableDefinition.getKeys()) {
            where.append(" and ").append(key).append("=? ");
            params.add(Beans.getPropertyValue(o, tableDefinition.getDefinition(key).getFieldName()).toString());
        }

        updateSql.append(update.deleteCharAt(update.length() - 1)).append(" WHERE 1=1 ").append(where);

        getTemplate().update(updateSql.toString(), params.toArray());

        if (tableDefinition.isCache()) {
            //同时更新缓存
//            getTemplate(Template.CACHE).update(updateSql.toString(), params.toArray());
        }

    }


    public void deleteById(Class c, UUID uuid) {

        StringBuilder deleteSql = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        deleteSql.append(" DELETE FROM ").append((DbContext.getInstance().getTablesMap().get(c).getTableName()));
        deleteSql.append(" where uuid=? ");
        params.add(uuid.toString());
        getTemplate().update(deleteSql.toString(), params.toArray());

        //删除缓存
//        getTemplate(Template.CACHE).update(deleteSql.toString(), params.toArray());
    }

    //=========================query=======================================================


    public <T> List queryBean(ISqlResultHandler handler, Class<T> clazz) {
        return queryBean(handler, clazz, Template.JDBC);
    }

    public <T> List queryBean(ISqlResultHandler handler, Class<T> clazz, Template template) {

        TableDefinition tableDefinition = getTableDefinition(clazz);
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        handler.doSql(sql, params);
        List<Map<String, Object>> ret = getTemplate(template).queryForList(sql.toString(), params.toArray());
        if(clazz==null){
        	return ret;
        }
        return handler.doReturn(ret, clazz, tableDefinition);
    }
    
    public <T> PageResult<T> queryPage(final ISolution sysConfSolution, final ICondition condition, Class<T> clazz) {

        StringBuilder whereSql = new StringBuilder();
        final List<Object> wp = new ArrayList<Object>();

        sysConfSolution.getWhere(condition, whereSql, wp);

        final StringBuilder countSql = new StringBuilder();

        sysConfSolution.getCountSql(countSql, whereSql);
        List<Long> count = queryBean(new SimpleHandler() {
            @Override
            public void doSql(StringBuilder sql, List<Object> params) {
                sql.append(countSql);
                params.addAll(wp);
            }
        }, Long.class);


        final StringBuilder pageSql = new StringBuilder();
        sysConfSolution.getPageSql(pageSql, whereSql, condition, wp);

        TableDefinition tableDefinition = getTableDefinition(clazz);
        List<String[]> orders = sysConfSolution.getOrderBy(condition);
  
        for (String[] o : orders) {
        	pageSql.append(tableDefinition.getFieldToColums().get(o[0])).append(" ").append(o[1]).append(",");
        }
        
        pageSql.deleteCharAt(pageSql.length()-1);
       
       
        List<T> list = queryBean(new BeanHandler() {
            @Override
            public void doSql(final StringBuilder sql, final List<Object> params) {
                sql.append(pageSql);
                params.addAll(wp);

                if (condition instanceof Page) {
                    sql.append(" limit ? offset ?");
                    params.add(((Page) condition).getLength());
                    params.add(((Page) condition).getStart());
                }
            }
        }, clazz);

        return new PageResult(count.get(0), count.get(0), list);
    }
    
   
//===============

    public <T> List<T> getAll(final Class<T> c) {
        return getAll(c, null, new ArrayList<Object>());
    }

    public <T> List<T> getAll(final Class<T> c, String where, final List<Object> ps) {

        String tableName = DbContext.getInstance().getTablesMap().get(c).getTableName();
        final String sqll = " select * from " + tableName + "  " + (StringUtils.isBlank(where) ? "" : where+" order by uuid asc");

        BeanHandler beanHandler = new BeanHandler() {
            @Override
            public void doSql(StringBuilder sql, List<Object> params) {
                sql.append(sqll);
                params.addAll(ps);
            }
        };

        return queryBean(beanHandler, c);
    }

    public <T> T getByUuid(Class<T> clazz, UUID uuid) {

        List<Object> params = new ArrayList<Object>();
        params.add(uuid.toString());
        List<T> ret = getAll(clazz, " where uuid =?", params);
        if (ret == null || ret.size() < 1) {
            return null;
        } else {
            return ret.get(0);
        }
    }

	@SuppressWarnings("unchecked")
	public <T> List<T> getBeanList(Class<T> clazz, final String whereSql, final Object... finalParams) {
		
		String tableName = DbContext.getInstance().getTablesMap().get(clazz).getTableName();
        final String finalSql = " SELECT * FROM " + tableName + " WHERE 1 = 1  " + (StringUtils.isBlank(whereSql) ? "" : whereSql+" ORDER BY UUID ASC ");
        
		List<T> list = queryBean(new BeanHandler() {
	        @Override
	        public void doSql(StringBuilder sql, List<Object> params) {
	
	            sql.append(finalSql);
	            for(Object o:finalParams)
	            	params.add(o);
	        }
	    }, clazz);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getBeanListWithTable(final String tableName, Class<T> clazz, final String whereSql, final Object... finalParams) {
		
        final String finalSql = " SELECT * FROM " + tableName + " WHERE 1 = 1  " + (StringUtils.isBlank(whereSql) ? "" : whereSql+" ORDER BY UUID ASC ");
        
		List<T> list = queryBean(new BeanHandler() {
	        @Override
	        public void doSql(StringBuilder sql, List<Object> params) {
	
	            sql.append(finalSql);
	            for(Object o:finalParams)
	            	params.add(o);
	        }
	    }, clazz);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getBeanListWithOrder(Class<T> clazz, final String whereSql, final String orderSql, final Object... finalParams) {
		
		String tableName = DbContext.getInstance().getTablesMap().get(clazz).getTableName();
        final String finalSql = " SELECT * FROM " + tableName + " WHERE 1 = 1  " + (StringUtils.isBlank(whereSql) ? "" : whereSql) + " ORDER BY " + (StringUtils.isBlank(orderSql)?"UUID ASC ":orderSql);
        
		List<T> list = queryBean(new BeanHandler() {
	        @Override
	        public void doSql(StringBuilder sql, List<Object> params) {
	
	            sql.append(finalSql);
	            for(Object o:finalParams)
	            	params.add(o);
	        }
	    }, clazz);
		return list;
	}

}

