package com.ruishengtech.rscc.crm.ui.product.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ruishengtech.framework.core.ApplicationHelper;
import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.diy.ColumnDesign;
import com.ruishengtech.framework.core.db.diy.DiyBean;
import com.ruishengtech.framework.core.db.diy.DiyTableService;
import com.ruishengtech.rscc.crm.product.model.OrderDetail;
import com.ruishengtech.rscc.crm.product.model.Product;
import com.ruishengtech.rscc.crm.product.service.OrderDetailService;
import com.ruishengtech.rscc.crm.product.service.ProductService;
import com.ruishengtech.rscc.crm.ui.product.service.ProductSerializeService;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.UserService;

/**
 * @author Frank
 * 
 */
@Controller
@RequestMapping("product")
public class ProductController {

	@Autowired
	@Qualifier(value = "diyTableService")
	private DiyTableService diyTableService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductSerializeService productSerialize;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderDetailService orderDetailService;
	
	SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@RequestMapping()
	public String index(Model model){
		
		//查询所有字段
		productService.getTitles(model);
		
		DiyBean ret = diyTableService.getTableDescByName("product");
		// 查询的字段
		List<String> list = new ArrayList<String>();
		// 取到可以查询的字段
		for (ColumnDesign re : ret.getClomns().values()) {
			// 筛选语序查询的字段
			if (re.getAllowSelect().equals(ColumnDesign.ALLOWSELECT)
					&& re.getIsDefault().equals(ColumnDesign.NOTDEFAULT)) {
				list.add(re.getColumnNameDB());
			}
		}
		/* 获取所有不是默认的字段列 */
		Map<String, ColumnDesign> map = productService.getNotDefaultColumn();
		model.addAttribute("allMaps", map);

		// 存放允许查询并且不是默认的所有字段
		model.addAttribute("tit", list);
		
		
		model.addAttribute("iframecontent","product/product-index");
		return "iframe";
		
//		return "product/product-index";
	}
	
	/**
	 * 数据请求
	 * @param request
	 * @param response
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request,
			HttpServletResponse response) {
		
		Map<String, ColumnDesign> map = diyTableService.getTableDescByName("product").getClomns();
		PageResult<Map> pageResult = productService.queryPage(map, request);

		List<Map> customers = pageResult.getRet();

		JSONObject jsonObject = new JSONObject();
		JSONArray array = new JSONArray();

		for (int i = 0; i < customers.size(); i++) {
			JSONObject jsonObject2 = productService.getJsonObject(customers.get(i));
			array.put(jsonObject2);
		}
		
		jsonObject.put("data", array);
		jsonObject.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject.put("iTotalDisplayRecords",
				pageResult.getiTotalDisplayRecords());
		return jsonObject.toString();
	}
	
	/**
	 * 添加或修改产品信息
	 * @param uuid
	 * @param model
	 * @return
	 */
	@RequestMapping("get")
	public String get(Model model, String uuid){
		//得到所有产品对应的列
		Map<String, ColumnDesign> map = productService.getAllColumns();
		model.addAttribute("maps", map);
		//判断修改还是删除
		if(StringUtils.isNotBlank(uuid)){
			//得到产品信息
			Map<String, Object> product = productService.getProductByUUId(uuid);
			//存放产品信息
			model.addAttribute("product", product);
			model.addAttribute("uid", uuid);
			model.addAttribute("picture", product.get("product_picture").toString());
		}else{
			model.addAttribute("serialized", productSerialize.getSerializeId());
		}
		// 获取所有用户
		List<User> user = userService.getAllUser();
		model.addAttribute("user", user);
		return "product/product-save";
	}
	
	/**
	 * 保存/修改 产品信息
	 * @return
	 * @throws ParseException
	 * @throws IOException 
	 */
	@RequestMapping("save")
	@ResponseBody
	public String saveTags(HttpServletRequest request, HttpServletResponse response,Product pro, MultipartFile multipartFile, BindingResult bindingResult)
			throws ParseException, IOException {

		//得到所有参数值
		//Map<String, String[]> map = request.getParameterMap();
		
		//得到产品的UUID
		String uuid = request.getParameter("uid");
		
		//得到产品的其他信息
		String pname = request.getParameter("product_name");
		Integer pnum = Integer.parseInt(request.getParameter("product_number"));
		Integer pprice = Integer.parseInt(request.getParameter("product_price"));
		String premark = request.getParameter("product_remark");
		String pid = request.getParameter("product_id");
		
		Product po = null;
		
		if (StringUtils.isNotBlank(uuid)) {
			po = productService.getProByUUID(uuid);
		}else{
			po = new Product();
			po.setProductId(pid);
			po.setProductCreateTime(fmt.parse(fmt.format(new Date())));
		}
		
		po.setProductName(pname);
		po.setProductNumber(pnum);
		po.setProductPrice(pprice);
		po.setProductRemark(premark);
		if(multipartFile != null){
			// 上传Excel
			String filePath = QueryUtils.uploadFile(multipartFile, ApplicationHelper.getContextPath() + "/upload");
			po.setProductPicture(filePath.replace("\\", "/"));
		}
		productService.saveOrUpdateInfo(po);

		return new JSONObject().put("success", true).toString();
	}
	
	
	@RequestMapping("pNameRepeat")
	@ResponseBody
	public void pNameRepeat(HttpServletRequest request, HttpServletResponse response, String pname)
			throws IOException {
		
		Product prod = productService.getProByName(pname);
		
		String uid = request.getParameter("uid");
		
		if(prod == null){
            response.getWriter().print(true);
		}else{
			Product pro = productService.getProByUUID(uid);
			if("".equals(uid)){
				response.getWriter().print(false);
			}else{
				if(pro != null && pname.equals(String.valueOf(pro.getProductName()))){
					response.getWriter().print(true);
				}else{
					response.getWriter().print(false);
				}
			}
		}		
	}
	
	/**
	 * 删除产品信息
	 * @param request
	 * @param response
	 * @param uuid
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public String deleteProduct(HttpServletRequest request, HttpServletResponse response, String uuid) {

		Product pro = productService.getProByUUID(uuid);
		
		List<OrderDetail> ods = orderDetailService.getOrderDetailsByPid(pro.getProductId());
		
		if(ods != null){
			
			return new JSONObject().put("success", false).toString();
			
		}
		
		productService.deleteProByUUid(uuid);
		
		return new JSONObject().put("success", true).toString();
	}
	
}
