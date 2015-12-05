package com.ruishengtech.rscc.crm.product.service.imp;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.diy.ColumnDesign;
import com.ruishengtech.framework.core.db.diy.DiyBean;
import com.ruishengtech.framework.core.db.diy.DiyTableService;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.rscc.crm.product.model.Product;
import com.ruishengtech.rscc.crm.product.service.ProductService;
import com.ruishengtech.rscc.crm.product.solution.ProductSolution;

@Service
@Transactional
public class ProductServiceImp extends DiyTableService implements ProductService{
	
	/**
	 * 去到所有title
	 * @param model
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	@Override
	public void getTitles(Model model) {
		
		JSONObject jsonObject = getTitleAndData();
		
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
			model.addAttribute("title", builder);
        }
		
		model.addAttribute("dataRows", data);

	}

	/**
	 * 查询不是默认的列的列
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, ColumnDesign> getNotDefaultColumn() {
		
		Map<String, ColumnDesign> map = new LinkedHashMap<String, ColumnDesign>();
		
		List<ColumnDesign> customerDesigns = queryBean(new BeanHandler() {
			
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				
				sql.append("SELECT d.* FROM design_column d WHERE 1=1 AND tableName = 'product' AND is_default = 0 ORDER BY d.orders ASC");
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
	
	/**
	 * 得到表头和对应数据字段
	 * 
	 * @return
	 */
	@SuppressWarnings("static-access")
	@Override
	public JSONObject getTitleAndData() {

		JSONObject jsonObject = new JSONObject();

		DiyBean ret = getTableDescByName("product");
		
		for (com.ruishengtech.framework.core.db.diy.ColumnDesign customerDesign : ret.getClomns().values()) {
			
			if(customerDesign.getAllowShow().equals(customerDesign.ALLOWSHOW)){ //查询允许显示的
				jsonObject.put(customerDesign.getColumnNameDB(),customerDesign);
			}
		}

		return jsonObject;
	}
	
	
	/**
	 * 查符合条件的所有商品
	 * 
	 * @param condition
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public PageResult<Map> queryPage(Map<String, ColumnDesign> str , HttpServletRequest request ) {
		return queryPage(new ProductSolution(), str,request);
	}
	
	/**
	 * 取值
	 * @param str 数据库查处的map
	 * @return
	 */
	@SuppressWarnings({ "static-access", "rawtypes" })
	public JSONObject getJsonObject(Map str){

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		JSONObject jsonObject2 = new JSONObject();
		DiyBean ret = getTableDescByName("product");
		
		for (com.ruishengtech.framework.core.db.diy.ColumnDesign customerDesign : ret.getClomns().values()) {

			if(customerDesign.ALLOWSHOW.equals(customerDesign.getAllowShow())){//如果是要显示的
				
				if(customerDesign.getColumnType().equals(ColumnDesign.DATETYPE)){//如果是时间类型 格式化时间
					try {
						jsonObject2.put(customerDesign.getColumnNameDB(),null == str.get(customerDesign.getColumnNameDB())?"":fmt.format(str.get(customerDesign.getColumnNameDB())));
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}else{
					jsonObject2.put(customerDesign.getColumnNameDB(),null == str.get(customerDesign.getColumnNameDB())?"":str.get(customerDesign.getColumnNameDB()));
				}
			}
		}
		jsonObject2.put("uuid",str.get("uuid"));
        
        return jsonObject2;
	}

	/**
	 * 查询所有产品列的信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, ColumnDesign> getAllColumns() {
		Map<String, ColumnDesign> map = new LinkedHashMap<String, ColumnDesign>();
		
		List<ColumnDesign> customerDesigns = queryBean(new BeanHandler() {
			
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				
				sql.append("SELECT d.* FROM design_column d WHERE 1=1 AND tableName = 'product' ORDER BY d.orders ASC");
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
	 * 查询UUID查询产品信息
	 */
	@Override
	public Map<String, Object> getProductByUUId(String uuid) {
		return jdbcTemplate.queryForMap(" SELECT * FROM `product` WHERE uuid = ? ", uuid);
	}

	/**
	 * 查询产品的UUID
	 */
	@Override
	public String getProductUidByPid(String pid) {
		if(StringUtils.isNotBlank(pid)){
			return jdbcTemplate.queryForObject("SELECT uuid FROM product WHERE product_id = ? ", String.class, pid);
		}
		return null;
	}

	@Override
	public Product getProByUUID(String uuid) {
		return super.getByUuid(Product.class, UUID.UUIDFromString(uuid));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Product getProByPid(final String pid) {
		List<Product> pro = queryBean(new BeanHandler() {
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				sql.append(" SELECT * FROM `product` WHERE product_id = ? ");
				params.add(pid);
			}
		}, Product.class);
		if (pro.size() > 0) {
			return pro.get(0);
		}
		return null;
	}

	/**
	 * 删除产品信息
	 */
	@Override
	public void deleteProByUUid(String uuid) {
		super.deleteById(Product.class, UUID.UUIDFromString(uuid));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getAllProducts() {
		List<Product> pro = queryBean(new BeanHandler() {
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				sql.append(" SELECT * FROM `product`");
			}
		}, Product.class);
		if (pro.size() > 0) {
			return pro;
		}
		return null;
	}

	@Override
	public void saveOrUpdateInfo(Product pro) {
		if(pro == null)
			return;
		if(StringUtils.isBlank(pro.getUid())){
			super.save(pro);
		}else{
			pro.setUuid(UUID.UUIDFromString(pro.getUid()));
			super.update(pro);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Product getProByName(final String pname) {
		List<Product> pro = queryBean(new BeanHandler() {
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				sql.append(" SELECT * FROM `product` WHERE product_name = ? ");
				params.add(pname);
			}
		}, Product.class);
		if (pro.size() > 0) {
			return pro.get(0);
		}
		return null;
	}
	
}
