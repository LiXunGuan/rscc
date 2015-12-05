package com.ruishengtech.rscc.crm.cstm.service.imp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.diy.ColumnDesign;
import com.ruishengtech.framework.core.db.diy.DiyTableService;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.cstm.condition.CustomerDesignCondition;
import com.ruishengtech.rscc.crm.cstm.model.CstmTable;
import com.ruishengtech.rscc.crm.cstm.model.CustomerDesign;
import com.ruishengtech.rscc.crm.cstm.service.CustomerDesignService;
import com.ruishengtech.rscc.crm.cstm.solution.CustomerDesignSolution;

@Service
@Transactional
public class CustomerDesignServiceImp extends BaseService implements
		CustomerDesignService {
	
	@Autowired
	private DiyTableService diyTableService;
	/**
	 * 得到添加列的名字
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getColumnName(){
		
		List<ColumnDesign> designs = queryBean(new BeanHandler() {
			
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				sql.append("  SELECT * FROM design_column ORDER BY id DESC LIMIT 0,1 ");
			} 
		}, ColumnDesign.class);
		
		if(designs.size() <= 0){
			
			return String.valueOf("col_" + 1);
		}
		
//		System.out.println(String.valueOf("col_" + (designs.get(0).getId()+1)));
		return String.valueOf("col_" + (designs.get(0).getId()+1));
	}
	
	
	/**
	 * 添加列
	 * @param string
	 */
	@Override
	public void alterAdd(ColumnDesign customerDesign) throws Exception{
		
		//要执行的SQL语句
		String execSQL = "";
		//要添加的索引
		String execSQLIndex = "";
		boolean isIndex = CustomerDesign.ALLOWINDEX.equals(customerDesign.getAllowIndex()) ? true : false;
		//字符长度
		String length = customerDesign.getCharacterProperty();
		
		if(ColumnDesign.INTTYPE.equals(customerDesign.getColumnType())){
			if(StringUtils.isBlank(length)){
				length = String.valueOf(11);
			}
			execSQL = "ALTER TABLE cstm_customer ADD " + customerDesign.getColumnNameDB() + " INT("+ length +") DEFAULT 0;";
			
		}else if(ColumnDesign.DATETYPE.equals(customerDesign.getColumnType())){
			
			execSQL = "ALTER TABLE cstm_customer ADD " + customerDesign.getColumnNameDB() + " DATE DEFAULT NOW();";
		}else if(ColumnDesign.FLOATTYPE.equals(customerDesign.getColumnType())){
			
			execSQL = "ALTER TABLE cstm_customer ADD " + customerDesign.getColumnNameDB() + " FLOAT(9,2) DEFAULT 0.00; ";
		
		}else if(ColumnDesign.ENUMTYPE.equals(customerDesign.getColumnType())){
			
			execSQL = "ALTER TABLE cstm_customer ADD " + customerDesign.getColumnNameDB() + " VARCHAR(100) DEFAULT ''; ";
		}else{
			
			if(StringUtils.isBlank(length)){
				length = String.valueOf(100);
			}
			execSQL = "ALTER TABLE cstm_customer ADD " + customerDesign.getColumnNameDB() + " VARCHAR(" + length + ") DEFAULT '' ";
		}
		
		
		super.jdbcTemplate.update(execSQL);
		
		/*添加索引*/
		if(isIndex){
			
			
			execSQLIndex = " ALTER TABLE cstm_customer ADD INDEX (` "+ customerDesign.getColumnNameDB() +" `) ";
			
			super.jdbcTemplate.execute(execSQLIndex);
		}
	}

	/**
	 * 删除咧
	 * @param string
	 */
	@Override
	public Integer alterDrop(String cstmId) {
		
		return super.jdbcTemplate.update("ALTER TABLE design_column_"+ cstmId + " DROP COLUMN "+ cstmId +";");
	}
	
	/**
	 * 查询添加的列
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, ColumnDesign> getAddedColumn(){
	
		Map<String, ColumnDesign> map = new LinkedHashMap<String, ColumnDesign>();
	
		List<ColumnDesign> customerDesigns = queryBean(new BeanHandler() {
			
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				
				sql.append("SELECT d.* FROM design_column d WHERE 1=1 AND tableName = 'cstm_customer' ORDER BY d.orders ASC");
			}
		}, ColumnDesign.class);
		
		if(customerDesigns.size() > 0){
			for (int i = 0; i < customerDesigns.size(); i++) {
				map.put(customerDesigns.get(i).getColumnNameDB(), customerDesigns.get(i));
			}
		}
		
		return map;
	}
	
	/**
	 * 查询指定表所有的列
	 * tableName 查询的表名
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, ColumnDesign> getAddedColumn(final String tableName){
		
		Map<String, ColumnDesign> map = new LinkedHashMap<String, ColumnDesign>();
		
		List<ColumnDesign> customerDesigns = queryBean(new BeanHandler() {
			
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				
				sql.append("SELECT d.* FROM design_column d WHERE 1=1 AND tableName = ? ORDER BY d.orders ASC");
				params.add(tableName);
			}
		}, ColumnDesign.class);
		
		if(customerDesigns.size() > 0){
			for (int i = 0; i < customerDesigns.size(); i++) {
				map.put(customerDesigns.get(i).getColumnNameDB(), customerDesigns.get(i));
			}
		}
		
		return map;
	}
	/**
	 * 查询不是默认的列的列
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, ColumnDesign> getNotDefaultColumn(){
		
		Map<String, ColumnDesign> map = new LinkedHashMap<String, ColumnDesign>();
		
		List<ColumnDesign> customerDesigns = queryBean(new BeanHandler() {
			
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				
				sql.append("SELECT d.* FROM design_column d WHERE 1=1 AND tableName = 'cstm_customer' AND is_default = 0 ORDER BY d.orders ASC");
			}
		}, ColumnDesign.class);
		
		if(customerDesigns.size() > 0){
			for (int i = 0; i < customerDesigns.size(); i++) {
				if(customerDesigns.get(i).getAllowShow().equals(ColumnDesign.ALLOWSHOW)){
					map.put(customerDesigns.get(i).getColumnNameDB(), customerDesigns.get(i));
					
				}
			}
		}
		
		return map;
	}

	/* (non-Javadoc)
	 * @see com.ruishengtech.rscc.crm.cstm.service.CustomerDesignService#queryPage(com.ruishengtech.rscc.crm.cstm.condition.CustomerDesignCondition)
	 */
	@Override
	public PageResult<ColumnDesign> queryPage(
			CustomerDesignCondition condition) {

		return super.queryPage(new CustomerDesignSolution(), condition, ColumnDesign.class);
	}
	
	/**
	 * 删除
	 * @param uuid
	 */ 
	@SuppressWarnings("unchecked")
	public void delete(final UUID uuid){
		
		StringBuilder deleteSql = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        deleteSql.append(" DELETE FROM design_column where uuid  = ? ");
        params.add(uuid.toString());
        
        super.jdbcTemplate.update(deleteSql.toString(),params.toArray());
        
		List<ColumnDesign> customerDesigns = queryBean(new BeanHandler() {
			
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				sql.append(" SELECT * FROM  design_column WHERE uuid = ? ");
				params.add(uuid.toString());
			}
		}, ColumnDesign.class);
        
		if(customerDesigns.size() > 0){
			jdbcTemplate.execute(" ALTER TABLE cstm_customer DROP COLUMN "+ customerDesigns.get(0).getColumnNameDB() +"; ");
		}
		
	}
	
	/**
	 * 查询单个对象
	 * @param id
	 * @return
	 */
	public ColumnDesign getCustomerDesign(String id){

		return super.getByUuid(ColumnDesign.class, UUID.UUIDFromString(id));
	}

	
	/**
	 * 更新
	 * @param columnDesign
	 * @param str
	 */
	public void update(ColumnDesign columnDesign,String[] str){
		
		//查询编号是否重复
//		int i = jdbcTemplate.queryForObject("SELECT count(*) FROM design_column WHERE orders >= ? and tableName = ?;", Integer.class,columnDesign.getOrders(),columnDesign.getTableName());
//		if( i > 1){
//			//存在相同或者大于这个编号的数据顺序   更新
//			jdbcTemplate.update("UPDATE design_column SET orders=orders+1 WHERE orders >= ? and tableName = ?" ,columnDesign.getOrders(),columnDesign.getTableName());
//		}
		
		if(str.length > 0){
			super.update(columnDesign, str);
		}else{
			super.update(columnDesign);
		}
	}
	
	/**
	 * 保存
	 * @param columnDesign
	 * @param str
	 */
	public void save(ColumnDesign columnDesign ,String[] str){
		if(str.length > 0){
			super.save(columnDesign, str);
		}else{
			super.save(columnDesign);
		}
		
	}
	
	/**
	 * 赋值
	 * @param columnDesign
	 * @param request
	 */
	public void setValue(ColumnDesign columnDesign ,HttpServletRequest request){
		if(StringUtils.isNotBlank(request.getParameter("allowSelect"))){
			columnDesign.setAllowSelect(ColumnDesign.ALLOWSELECT);
		}else{
			columnDesign.setAllowSelect(ColumnDesign.NOTALLOWSELECT);
		}
		if(StringUtils.isNotBlank(request.getParameter("allowIndex"))){
			columnDesign.setAllowIndex(ColumnDesign.ALLOWINDEX);
		}else{
			columnDesign.setAllowIndex(ColumnDesign.NOTALLOWINDEX);
		}
		if(StringUtils.isNotBlank(request.getParameter("allowShow"))){
			columnDesign.setAllowShow(ColumnDesign.ALLOWSHOW);
		}else{
			columnDesign.setAllowShow(ColumnDesign.NOTALLOWSHOW);
		}
		if(StringUtils.isNotBlank(request.getParameter("allowEmpty"))){
			columnDesign.setAllowEmpty(ColumnDesign.NOTALLOWEMPTY);
		}else{
			columnDesign.setAllowEmpty(ColumnDesign.ALLOWEMPTY);
		}
	}
	
	
	/**
	 * 根据列名查询客服信息
	 * @param columnName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ColumnDesign getCstmserviceByColumnName(final String columnName){
		
		if(StringUtils.isNotBlank(columnName)){
			
			List<ColumnDesign> columnDesign = queryBean(new BeanHandler() {
				
				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					sql.append(" SELECT * FROM `design_column` WHERE tableName = 'cstm_customer' AND column_name = ? ");
					params.add(columnName);
				}
			}, ColumnDesign.class);
			
			if(columnDesign.size() > 0){
				return columnDesign.get(0);
			}
		}
		
		return null;
	}
	
	/**
	 * 显示顺序不能重复
	 * @param order
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ColumnDesign getCstmserviceByColumn(final String order){
		
		if(StringUtils.isNotBlank(order)){
			
			List<ColumnDesign> columnDesign = queryBean(new BeanHandler() {
				
				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					sql.append(" SELECT * FROM `design_column` WHERE tableName = 'cstm_customer' AND orders = ? ");
					params.add(order);
				}
			}, ColumnDesign.class);
			
			if(columnDesign.size() > 0){
				return columnDesign.get(0);
			}
		}
		
		return null;
	}
	
	
	/**
	 * 校验重复
	 * @param request
	 * @param response
	 * @param columnName
	 * @throws java.io.IOException
	 */
	public void checkNameRepeat(HttpServletRequest request,
			HttpServletResponse response, String columnName) throws IOException {
		ColumnDesign columnDesign = getCstmserviceByColumnName(columnName);
		
		String uid = request.getParameter("uid");
		
		// 添加的时候没有该用户信息
		if(null == columnDesign){
            // 直接打印true
            response.getWriter().print(true);
		}else{
			
			ColumnDesign columnDesignId = getCustomerDesign(columnDesign.getUid());
			
			if("".equals(uid)){

				response.getWriter().print(false);
			}else{
				
				if(columnDesignId != null && uid.equals(String.valueOf(columnDesign.getUid()))){
					response.getWriter().print(true);
				}else{
					
					response.getWriter().print(false);
				}
			}
		}
	}
	
	/**
	 * 校验顺序重复
	 * @param request
	 * @param response
	 * @param columnName
	 * @throws java.io.IOException
	 */
	public void checkOrdersRepeat(HttpServletRequest request, HttpServletResponse response, String orders) throws IOException{
		
		ColumnDesign columnDesign = getCstmserviceByColumn(orders);
		
		String uid = request.getParameter("uid");
		
		// 添加的时候没有该用户信息
		if(null == columnDesign){
            // 直接打印true
            response.getWriter().print(true);
		}else{
			
			ColumnDesign columnDesignId = getCustomerDesign(columnDesign.getUid());
			
			if("".equals(uid)){

				response.getWriter().print(false);
			}else{
				
				if(columnDesignId != null && uid.equals(String.valueOf(columnDesign.getUid()))){
					response.getWriter().print(true);
				}else{
					
					response.getWriter().print(false);
				}
			}
		}		
	}
	
	/**
	 * 校验顺序重复
	 * @param request
	 * @param response
	 * @param columnName
	 * @throws java.io.IOException
	 */
	public void comparelength(HttpServletRequest request, HttpServletResponse response, String orders) throws IOException{

		String uid = request.getParameter("uid");

		// 添加的时候不需要判断
		if (StringUtils.isBlank(uid)) {

			response.getWriter().print(true);
		} else {

			ColumnDesign columnDesignId = getCustomerDesign(uid);

			if (Integer.valueOf(columnDesignId.getCharacterProperty()) <= Integer.valueOf(orders)) {

				response.getWriter().print(true);
			} else {

				response.getWriter().print(false);
			}

		}
	}
	
	
	/**
	 * 修改顺序
	 * @param uid
	 * @param orders
	 * @param tag
	 */
	public String alterOrders(String uid, String orders, String tag) {

		String res = null;
		if (StringUtils.isNotBlank(tag) && StringUtils.isNotBlank(uid) && StringUtils.isNotBlank(orders)) {
			
			if (tag.equals("1")) {
				/*
				 * 向前 如果不是0替换成0
				 */
				if (Integer.valueOf(orders) > 0) {
					// 修改
					/* 向后移动 */
					jdbcTemplate.update("UPDATE design_column SET orders = orders + 1 WHERE orders = '"+ (Integer.valueOf(orders) - 1) + "';");
					/* 向前移动 */
					jdbcTemplate.update("UPDATE design_column SET orders = orders - 1 WHERE uuid = '"+ uid + "';");
					res = "0";
				} else {
					res = "-1";
				}

			} else if (tag.equals("0")) {

				/* 查询最大编号 */
				Integer maxOrders = jdbcTemplate.queryForObject("SELECT MAX(orders) from design_column ",Integer.class);
				
				if (Integer.valueOf(orders) < maxOrders) {

					// 修改
					/* 向前移动 */
					jdbcTemplate.update("UPDATE design_column SET orders = orders - 1 WHERE orders = "+ (Integer.valueOf(orders) + 1) + ";");
					/* 向后移动 uid 4  orders 3 tag 0  */
					jdbcTemplate.update("UPDATE design_column SET orders = orders + 1 WHERE uuid = '"+ uid + "';");
					res = "0";
				}else{
					res = "-1";
				}
			}
		}
		/*结果判断并刷新*/
		if(res.equals("0")){
			
			diyTableService.load();
			return "0";
		}else{
			
			return "-1";
		}
	}


	/**
	 * 查询指定自定义表的所有列
	 * @param tableName
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, ColumnDesign> getAllColumns(final String tableName) {
		
		
		Map<String, ColumnDesign> map = new LinkedHashMap<String, ColumnDesign>();
		
		List<ColumnDesign> customerDesigns = queryBean(new BeanHandler() {
			
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				
				sql.append("SELECT d.* FROM design_column d WHERE 1=1 AND tableName = ?  ORDER BY d.orders ASC ");
				params.add(tableName);
			}
		}, ColumnDesign.class);
		
		if(customerDesigns.size() > 0){
			for (int i = 0; i < customerDesigns.size(); i++) {
				map.put(customerDesigns.get(i).getColumnNameDB(), customerDesigns.get(i));
			}
		}
		
		return map;
		
	}


	/**
	 * 查询所有自定义表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CstmTable> getDesignTables() {

		List<CstmTable> cstmTables =  queryBean(new BeanHandler() {
			
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				sql.append(" SELECT uuid as uuid,table_name as tableNameDB,name as tableName FROM design_table ");
			}
		}, CstmTable.class);
		
		if(cstmTables.size() > 0){
			
			return cstmTables;
		}
		
		return null;
	}	
	
}
