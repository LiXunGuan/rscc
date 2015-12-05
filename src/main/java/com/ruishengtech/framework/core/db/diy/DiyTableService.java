package com.ruishengtech.framework.core.db.diy;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.ruishengtech.framework.core.db.DbContext;
import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.framework.core.db.handler.SimpleHandler;
import com.ruishengtech.framework.core.db.service.BaseService;

/**
 * @author Frank
 *
 */
@Component
public class DiyTableService extends BaseService {

	public DiyTableService() {
	}

	private static Map<String, DiyBean> map = new LinkedHashMap<String, DiyBean>();

	public static Map<String, DiyBean> getMap() {
		return map;
	}

	public DiyBean getTableDescByName(String name) {

		return map.get(name);
	}

	/**
	 * 加载数据
	 */
	@SuppressWarnings("unchecked")
	public void load() {

		List<DesignTable> designTables = getAll(DesignTable.class);

		for (DesignTable designTable : designTables) {
			map.put(designTable.getTableName(), new DiyBean());
		}

		List<ColumnDesign> list = queryBean(new BeanHandler() {
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				sql.append(" SELECT * FROM design_column WHERE  1=1 ORDER BY orders ASC ");
			}
		}, ColumnDesign.class);

		for (ColumnDesign columnDesign : list) {
			map.get(columnDesign.getTableName()).getClomns().put(columnDesign.getColumnNameDB(), columnDesign);
		}
	}

	/**
	 * 保存信息
	 * 
	 * @param str
	 *            要保存的数据字段及数据
	 * @param diyTableManager
	 * @param clazz
	 */
	public void save(Map<String, String[]> str, Class clazz) {

		String tableName = DbContext.getInstance().getTablesMap().get(clazz)
				.getTableName();
		save(tableName, tableName, str);
		/*
		 * 获取表名 TableDefinition tableDefinition =
		 * DbContext.getInstance().getTablesMap().get(clazz); StringBuilder
		 * insertSql = new StringBuilder(); 拼接语句
		 * insertSql.append(" INSERT INTO "
		 * ).append(tableDefinition.getTableName() + " ");
		 * 
		 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 开始的地方
		 * StringBuilder from = new StringBuilder("("); 结束的地方 StringBuilder into
		 * = new StringBuilder("(");
		 * 
		 * List<Object> params = new ArrayList<Object>();
		 * 
		 * //编号 from.append("uuid").append(","); into.append("?,"); UUID uuid =
		 * UUID.randomUUID(); params.add(uuid.toString());
		 * 
		 * 
		 * 所有的列 DiyBean ret =
		 * getTableDescByName(tableDefinition.getTableName()); for
		 * (com.ruishengtech.framework.core.db.diy.ColumnDesign customerDesign :
		 * ret.getClomns().values()) { if
		 * (str.keySet().contains(customerDesign.getColumnNameDB())) { //
		 * 如果数据库中的字段中有传递过来的参数 数据库中的字段 拼接
		 * from.append(customerDesign.getColumnNameDB()).append(",");
		 * into.append("?,");
		 * params.add(str.get(customerDesign.getColumnNameDB())[0]); } }
		 * 
		 * insertSql.append(from.deleteCharAt(from.length() - 1).append(")"));
		 * insertSql.append(" VALUES ");
		 * insertSql.append(into.deleteCharAt(into.length() - 1).append(")"));
		 * 
		 * jdbcTemplate.update(insertSql.toString(), params.toArray());
		 * 
		 * load();
		 */
	};

	public void save(String tableDef, String tableName,
			Map<String, String[]> str) {

		StringBuilder insertSql = new StringBuilder();
		/* 拼接语句 */
		insertSql.append(" INSERT INTO ").append(tableName + " ");

		/* 开始的地方 */
		StringBuilder from = new StringBuilder("(");
		/* 结束的地方 */
		StringBuilder into = new StringBuilder("(");

		List<Object> params = new ArrayList<Object>();

		// 编号
		from.append("uuid").append(",");
		into.append("?,");
		UUID uuid = UUID.randomUUID();
		params.add(uuid.toString());

		/* 所有的列 */
		DiyBean ret = getTableDescByName(tableDef);
		for (ColumnDesign customerDesign : ret.getClomns().values()) {
			if (str.keySet().contains(customerDesign.getColumnNameDB())) {
				// 如果数据库中的字段中有传递过来的参数 数据库中的字段 拼接
				from.append(customerDesign.getColumnNameDB()).append(",");
				into.append("?,");
				
				if(StringUtils.isNotBlank(str.get(customerDesign.getColumnNameDB())[0])){
					
					params.add(str.get(customerDesign.getColumnNameDB())[0]);
				}else{
					params.add(null);
				}
			}
		}

		insertSql.append(from.deleteCharAt(from.length() - 1).append(")"));
		insertSql.append(" VALUES ");
		insertSql.append(into.deleteCharAt(into.length() - 1).append(")"));

		jdbcTemplate.update(insertSql.toString(), params.toArray());

		load();
	};

	/**
	 * 更新语句 修改必须用uuid来修改
	 * 
	 * @param str
	 * @param diyTableManager
	 * @param clazz
	 */
	public void update(Map<String, String[]> str, Class clazz) {

		String tableName = DbContext.getInstance().getTablesMap().get(clazz)
				.getTableName();
		update(tableName, tableName, str);
		/*
		 * TableDefinition tableDefinition =
		 * DbContext.getInstance().getTablesMap().get(clazz); StringBuilder
		 * updateSql = new StringBuilder(); List<Object> params = new
		 * ArrayList<Object>();
		 * updateSql.append(" UPDATE ").append(tableDefinition
		 * .getTableName78()); updateSql.append(" SET ");
		 * 
		 * StringBuilder update = new StringBuilder(); StringBuilder where = new
		 * StringBuilder();
		 * 
		 * 所有的列 DiyBean ret =
		 * diyTableManager.getTableDescByName(tableDefinition.getTableName());
		 * 
		 * for (com.ruishengtech.framework.core.db.diy.ColumnDesign
		 * customerDesign : ret.getClomns().values()) { if
		 * (str.keySet().contains(customerDesign.getColumnNameDB())) {
		 * update.append
		 * (" ").append(customerDesign.getColumnNameDB()).append(" =?,");
		 * params.add(str.get(customerDesign.getColumnNameDB())[0]); } }
		 * 
		 * where.append(" AND uuid = ? "); params.add(str.get("uuid")[0]);
		 * 
		 * updateSql.append(update.deleteCharAt(update.length() -
		 * 1)).append(" WHERE 1=1 ").append(where);
		 * jdbcTemplate.update(updateSql.toString(), params.toArray());
		 */
	}

	/**
	 * 更新动态表
	 * 
	 * @param tableDef
	 * @param tableName
	 * @param str
	 */
	public void update(String tableDef, String tableName,
			Map<String, String[]> str) {

		StringBuilder updateSql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		updateSql.append(" UPDATE ").append(tableName);
		updateSql.append(" SET ");

		StringBuilder update = new StringBuilder();
		StringBuilder where = new StringBuilder();

		/* 所有的列 */
		DiyBean ret = getTableDescByName(tableDef);

		for (ColumnDesign customerDesign : ret.getClomns().values()) {
			if (str.keySet().contains(customerDesign.getColumnNameDB())) {
				update.append(" ").append(customerDesign.getColumnNameDB())
						.append(" =?,");
				
				
				if(StringUtils.isNotBlank(str.get(customerDesign.getColumnNameDB())[0])){
					
					params.add(str.get(customerDesign.getColumnNameDB())[0]);
				}else{
					params.add(null);
				}
				
//				params.add(str.get(customerDesign.getColumnNameDB())[0]);
			}
		}

		where.append(" AND uuid = ? ");
		if (null != str.get("uid")) {
			params.add(str.get("uid")[0]);
		} else {
			params.add(str.get("uuid")[0]);
		}

		updateSql.append(update.deleteCharAt(update.length() - 1))
				.append(" WHERE 1=1 ").append(where);
		jdbcTemplate.update(updateSql.toString(), params.toArray());

	}

	/**
	 * 动态添加表
	 * 
	 * @param tableName
	 */
	public void addTableInfo(String tableName) {

		DesignTable o = new DesignTable();
		o.setTableName(tableName);
		super.save(o);

		load();

	}

	/**
	 * 添加表描述数据
	 * 
	 * @param columnInfo
	 */
	public void addColumnDesin(ColumnDesign columnInfo) {

		if (null != columnInfo) {

			// 查询编号是否重复
			int i = jdbcTemplate.queryForObject(
					"SELECT count(*) FROM design_column WHERE orders >= ? and tableName = ?;",
					Integer.class, columnInfo.getOrders(), columnInfo.getTableName());
			if (i > 0) {
				// 存在相同或者大于这个编号的数据顺序 更新
				jdbcTemplate
						.update("UPDATE design_column SET orders = orders+1 WHERE orders >= ? and tableName=? ",
								columnInfo.getOrders(), columnInfo.getTableName());
			}

			super.save(columnInfo);

		}
		load();
	}

	/**
	 * 删除表描述中的字段和
	 * 
	 * @param id
	 * @param tableName
	 */
	@SuppressWarnings("unchecked")
	public ColumnDesign delete(final String id, String tableName) {

		// 删除
		List<ColumnDesign> customerDesigns = queryBean(new BeanHandler() {

			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				sql.append(" SELECT * FROM design_column WHERE id = ? ");
				params.add(id.toString());
			}
		}, ColumnDesign.class);

		// if(customerDesigns.size() > 0){
		// jdbcTemplate.execute(" ALTER TABLE "+ tableName +" DROP COLUMN "+
		// customerDesigns.get(0).getColumnNameDB() +"; ");
		// }

		StringBuilder deleteSql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		deleteSql.append(" DELETE FROM design_column where id = ? ");
		params.add(id.toString());

		super.jdbcTemplate.update(deleteSql.toString(), params.toArray());

		load();
		if (customerDesigns.size() > 0) {
			return customerDesigns.get(0);
		}
		return null;
	}

	/**
	 * 
	 * 删除列
	 * 
	 * @param columnDesign
	 * @param tableName
	 */
	public void dropColumn(ColumnDesign columnDesign, String tableName) {
		if (null != columnDesign) {
			jdbcTemplate.execute(" ALTER TABLE " + tableName + " DROP COLUMN "
					+ columnDesign.getColumnNameDB() + "; ");
		}
	}

	/**
	 * 返回指定表的自定义列
	 * 
	 * @return
	 */
	public Map<String, ColumnDesign> getDiyColumns(String tableName) {
		if (map.get(tableName) == null)
			return null;
		return map.get(tableName).getClomns();
	}

	/**
	 * 查询所有数据
	 * 
	 * @param iSolution
	 *            Solution类
	 * @param str
	 *            传递到solution中 可能查询的参数 判断并拼接
	 * @param request
	 *            负责排序和分页size的功能
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> PageResult<T> queryPage(
			ISolution iSolution,
			Map<String, ColumnDesign> str, HttpServletRequest request) {

		/* 查询符合条件的条数 */
		StringBuilder whereSql = new StringBuilder();
		final List<Object> wp = new ArrayList<Object>();

		/* 拼装查询条件 */
		iSolution.getWhere(str, whereSql, request, wp);

		final StringBuilder countSql = new StringBuilder();

		/* 拼装查询条数的SQL语句 */
		iSolution.getCountSql(countSql, whereSql);
		List<Long> count = queryBean(new SimpleHandler() {
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				sql.append(countSql);
				params.addAll(wp);
			}
		}, Long.class);

		/* 拼装查询数据的语句 */
		final StringBuilder pageSql = new StringBuilder();

		iSolution.getPageSql(pageSql, whereSql, str, request);
		
		// 拼装排序
		if(String.valueOf(pageSql).contains("ORDER")){
			
			pageSql.append(" , ");
			
		}else{
			
			pageSql.append(" ORDER BY ");
		}
		pageSql.append(request.getParameter("columns[" + request.getParameter("order[0][column]") + "][data]")+ " " + request.getParameter("order[0][dir]") + "");

		// 拼装分页
		pageSql.append(" LIMIT ? OFFSET ? ");
		wp.add(Integer.valueOf(request.getParameter("length")));
		wp.add(Integer.valueOf(request.getParameter("start")));

		// 查询数据
		List<Map<String, Object>> list = jdbcTemplate.queryForList(
				pageSql.toString(), wp.toArray());

		return new PageResult(count.get(0), count.get(0), list);

	}
	
	

	/**
	 * 修改表结构
	 * 
	 * @param columnDesign
	 * @param tableName
	 * @throws Exception
	 */
	public void alterAdd(ColumnDesign columnDesign, String tableName)
			throws Exception {

		alterAdd(columnDesign, tableName,null);
	}

	
	/**
	 * 修改表结构
	 * 
	 * @param columnDesign
	 * @param tableName
	 * @throws Exception
	 */
	public void alterAdd(ColumnDesign columnDesign, String tableName,String callType)
			throws Exception {
		
		// 要执行的SQL语句
		String execSQL = "";
		// 字符长度
		String length = columnDesign.getCharacterProperty();

		String colNameDB = columnDesign.getColumnNameDB();
		String colNameDBValue = columnDesign.getColumnValue();
		
		if (ColumnDesign.INTTYPE.equals(columnDesign.getColumnType())) {
			if (StringUtils.isBlank(length)) {
				length = String.valueOf(11);
			}
			execSQL = "ALTER TABLE " + tableName + "  ADD " + colNameDB + " INT(" + length + ") DEFAULT " + colNameDBValue + ";";

		} else if (ColumnDesign.DATETYPE.equals(columnDesign.getColumnType())) { //时间添加索引

			execSQL = "ALTER TABLE " + tableName + "  ADD " + colNameDB + " DATETIME DEFAULT NULL;";
		
		} else if (ColumnDesign.FLOATTYPE .equals(columnDesign.getColumnType())) {

			execSQL = "ALTER TABLE " + tableName + "  ADD "	+ colNameDB + " FLOAT(9,2) DEFAULT " + colNameDBValue + "; ";
		
		}else if(ColumnDesign.ENUMTYPE.equals(columnDesign.getColumnType())){
			
			 execSQL = "ALTER TABLE " + tableName + "  ADD " +  colNameDB + " VARCHAR(100);";
			 
		}else {

			if (StringUtils.isBlank(length)) {
				length = String.valueOf(100);
			}
			
			execSQL = "ALTER TABLE " + tableName + "  ADD " + colNameDB + " VARCHAR(" + length + ") DEFAULT " + colNameDBValue + "; ";
		}
		
		super.jdbcTemplate.update(execSQL);
		addIndexAuto(columnDesign, tableName,callType);
		
	}

	/**
	 * 添加索引
	 * 
	 * 不允许为空添加普通索引，时间添加唯一索引
	 * 
	 * @param columnDesign
	 * @param tableName
	 */
	public void addIndexAuto(ColumnDesign columnDesign, String tableName,String callType){

		String execSQL = "";
		
		String colNameDB = columnDesign.getColumnNameDB();
		
		if(columnDesign.getAllowIndex().equals(ColumnDesign.ALLOWINDEX) && columnDesign.getAllowEmpty().equals(ColumnDesign.NOTALLOWEMPTY)){
			
			execSQL = " ALTER TABLE "+ tableName +" ADD INDEX " + colNameDB + "  ( '" + colNameDB + "' ) ;";
			
			if(columnDesign.getColumnType().equals(ColumnDesign.DATETYPE) ){ /*如果时间，添加唯一索引*/
				
				execSQL = " ALTER TABLE "+ tableName +" ADD INDEX ( '" + colNameDB + "' ) ;";
			}
			
		}

		super.jdbcTemplate.update(execSQL);
	}	
	

	
	/**
	 * 生成添加咧的名字
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getColumnName() {

		List<ColumnDesign> designs = queryBean(new BeanHandler() {

			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				sql.append(" SELECT id FROM design_column WHERE 1=1 ");
			}
		}, ColumnDesign.class);

		if (designs.size() <= 0) {

			return String.valueOf("col_" + 1);
		}
		return String.valueOf("col_" + (designs.size() + 1));
	}

	/**
	 * 删除设计表数据 删除对应表的列
	 * 
	 * @param uuid
	 */
	// @SuppressWarnings("unchecked")
	// public void delete(final UUID uuid,String tableName){
	//
	// StringBuilder deleteSql = new StringBuilder();
	// List<Object> params = new ArrayList<Object>();
	// deleteSql.append(" DELETE FROM design_column where id = ? ");
	// params.add(uuid.toString());
	//
	// super.jdbcTemplate.update(deleteSql.toString(),params.toArray());
	//
	// List<ColumnDesign> customerDesigns = queryBean(new BeanHandler() {
	//
	// @Override
	// public void doSql(StringBuilder sql, List<Object> params) {
	// sql.append(" SELECT * FROM  design_column WHERE id = ? ");
	// params.add(uuid.toString());
	// }
	// }, ColumnDesign.class);
	//
	// if(customerDesigns.size() > 0){
	// jdbcTemplate.execute(" ALTER TABLE " + tableName + " DROP COLUMN "+
	// customerDesigns.get(0).getColumnNameDB() +"; ");
	// }
	// load();
	// }

	/**
	 * 获取最大的顺序编号
	 * 
	 * @return
	 */
	public Integer getMaxOrder() {

		return jdbcTemplate.queryForObject(
				"SELECT MAX(orders) FROM `design_column`; ", Integer.class);
	}
	/**
	 * 获取最大的顺序编号
	 * 
	 * @return
	 */
	public Integer getMaxTableOrder(String tableName) {
		
		return jdbcTemplate.queryForObject(
				"SELECT MAX(orders) FROM `design_column` WHERE tableName = ?; ", Integer.class,tableName);
	}
	
	
	/**
	 * 取到所有title
	 * @param model
	 */
	public void getTitles(Model model,JSONObject jsonObject) {
		
		StringBuilder builder = new StringBuilder();
		Iterator it = jsonObject.keys();
		TreeMap<Integer, ColumnDesign> e = new TreeMap<Integer, ColumnDesign>();
		
		while (it.hasNext()) {

			String key = (String) it.next();
			ColumnDesign value = (ColumnDesign) jsonObject.get(key);
			e.put(value.getOrders(), value);
		}
		
		Map<String, String> data = new LinkedHashMap<String, String>();
		
		Iterator titer=e.entrySet().iterator();  
		while(titer.hasNext()){
            Map.Entry ent=(Map.Entry )titer.next();  
            String keyt=ent.getKey().toString();  
            ColumnDesign valuet=(ColumnDesign) ent.getValue();  
            data.put(valuet.getColumnNameDB(),valuet.getColumnName());
            
            if(model instanceof RedirectAttributesModelMap){
            	
            	((RedirectAttributesModelMap) model).addFlashAttribute("title", builder.toString());
            }else{
            	model.addAttribute("title", builder.toString());
            }
            
        }
		if(model instanceof RedirectAttributesModelMap){
			((RedirectAttributesModelMap) model).addFlashAttribute("dataRows", data);
		}else{
			model.addAttribute("dataRows", data);
		}
		
	}


	/**
	 * 解锁map
	 * @param map2
	 * @throws Exception
	 */
	public void releaseLock(Map<String, String[]> map2) throws Exception {

		java.lang.reflect.Method method = map2.getClass().getMethod("setLocked",new Class[]{boolean.class});
		
		method.invoke(map2,new Object[]{new Boolean(false)});
		
	}


	

}
