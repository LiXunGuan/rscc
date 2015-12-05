package com.ruishengtech.rscc.crm.product.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.ui.Model;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.diy.ColumnDesign;
import com.ruishengtech.rscc.crm.product.model.Product;


/**
 * @author Frank
 *
 */
public interface ProductService {
	
	/**
	 * 去到所有title
	 * @param model
	 */
	public void getTitles(Model model);
	
	
	/**
	 * 查询不是默认的列的列
	 */
	public Map<String, ColumnDesign> getNotDefaultColumn();

	
	/**
	 * 得到表头和对应数据字段
	 * 
	 * @return
	 */
	public JSONObject getTitleAndData();
	
	
	/**
	 * 查符合条件的所有客户
	 * 
	 * @param condition
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public PageResult<Map> queryPage(Map<String, ColumnDesign> str, HttpServletRequest request);

	/**
	 * 取值
	 * @param str
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public JSONObject getJsonObject(Map str);
	
	/**
	 * 查询所有的产品列
	 * @return
	 */
	public Map<String, ColumnDesign>  getAllColumns();
	
	/**
	 * 根据uuid查询产品信息
	 * @param uuid
	 * @return
	 */
	public abstract Map<String, Object> getProductByUUId(String uuid);
	
	/**
	 * 查询指定产品编号的UUID
	 * @param serilizeId
	 * @return
	 */
	public abstract String getProductUidByPid(String pid);
	
	public Product getProByUUID(String uuid);
	
	public Product getProByPid(String pid);
	
	public Product getProByName(String pname);
	
	/**
	 * 删除产品信息
	 * @param uuid
	 */
	public abstract void deleteProByUUid(String uuid);
	
	/**
	 * 查询所有的产品信息
	 * @return
	 */
	public abstract List<Product> getAllProducts();
	
	/**
	 * 保存或修改产品信息
	 * @param pro
	 */
	public abstract void saveOrUpdateInfo(Product pro);
}
