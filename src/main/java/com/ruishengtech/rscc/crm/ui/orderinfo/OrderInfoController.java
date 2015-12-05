package com.ruishengtech.rscc.crm.ui.orderinfo;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
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

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.diy.ColumnDesign;
import com.ruishengtech.framework.core.db.diy.DiyBean;
import com.ruishengtech.framework.core.db.diy.DiyTableService;
import com.ruishengtech.rscc.crm.cstm.model.Customer;
import com.ruishengtech.rscc.crm.cstm.service.CustomerService;
import com.ruishengtech.rscc.crm.product.model.OrderDetail;
import com.ruishengtech.rscc.crm.product.model.OrderInfo;
import com.ruishengtech.rscc.crm.product.model.Product;
import com.ruishengtech.rscc.crm.product.service.OrderDetailService;
import com.ruishengtech.rscc.crm.product.service.OrderInfoService;
import com.ruishengtech.rscc.crm.product.service.ProductService;
import com.ruishengtech.rscc.crm.ui.IndexService;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.ui.orderinfo.service.OrderInfoSerializeService;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.DatarangeService;
import com.ruishengtech.rscc.crm.user.service.UserService;

/**
 * @author chengxin
 *
 */
@Controller
@RequestMapping("orderinfo")
public class OrderInfoController {

	@Autowired
	private OrderInfoService orderInfoService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderDetailService orderDetailService;
	
	@Autowired
	private DatarangeService datarangeService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private OrderInfoSerializeService orderInfoSerialize;

	@Autowired
	@Qualifier(value = "diyTableService")
	private DiyTableService diyTableService;
	
	@Autowired
	private IndexService indexService;
	
	SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@RequestMapping()
	public String index(Model model, HttpServletRequest request){
		
		//是否隐藏号码
		indexService.judgeIsHidePhone(model, SessionUtil.getCurrentUser(request).getUid());
		
		//查询所有字段
		orderInfoService.getTitles(model);
		
		DiyBean ret = diyTableService.getTableDescByName("order_info");
		
		// 查询的字段
		List<String> list = new ArrayList<String>();
		
		// 取到可以查询的字段
		for (ColumnDesign re : ret.getClomns().values()) {
			// 筛选语序查询的字段
			if (re.getAllowSelect().equals(ColumnDesign.ALLOWSELECT) && re.getIsDefault().equals(ColumnDesign.NOTDEFAULT)) {
				// 删选过后存放字段名
				list.add(re.getColumnNameDB());
			}
		}
		
		// 存放允许查询并且不是默认的所有字段
		model.addAttribute("tit", list);
		
		// 获取所有不是默认的字段列 
		Map<String, ColumnDesign> map = orderInfoService.getNotDefaultColumn();
		model.addAttribute("allMaps", map);
		
		//订单状态
		model.addAttribute("orderstatus", OrderInfo.OrderStatus);
		
		//获取当前登录用户
		User user = (User) SessionUtil.getCurrentUser(request);
		model.addAttribute("adminflag", user.getAdminFlag());
		model.addAttribute("useruuid", user.getUid());
		
		if("agent".equals(request.getParameter("level"))){
			model.addAttribute("agent", "我的订单");
			request.getSession().setAttribute("level", "agent");
		}else if("pop".equals(request.getParameter("level"))){
			model.addAttribute("agent", "我的订单");
			request.getSession().setAttribute("level", "pop");
			model.addAttribute("pageLength", 5);
			request.getSession().setAttribute("cstmId", request.getParameter("cstmId"));
		}else{
			model.addAttribute("agent", "订单管理");
			request.getSession().removeAttribute("level");
		}
		
		model.addAttribute("iframecontent","orderinfo/orderinfo-index");
		return "iframe";
		
//		return "orderinfo/orderinfo-index";
	}
	
	
	/**
	 * 数据请求
	 * 
	 * @param request
	 * @param response
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("data")
	@ResponseBody
	public String getDate(HttpServletRequest request, HttpServletResponse response) {
		
		User us = (User) SessionUtil.getCurrentUser(request);
		List<String> strList = new ArrayList<String>();
//		Set<String> departs = userService.getRoleDataranges(us.getUid(), "datarange");
		List<String> departs = userService.getDepartments(us.getUid());
		if(departs.size() != 0){
			for (String str : departs) {
				strList.addAll(datarangeService.getUserUuids(str));
			}
			request.setAttribute("allusers", strList);
		}
		
		
		Map<String, ColumnDesign> map = diyTableService.getTableDescByName("order_info").getClomns();
		PageResult<Map> pageResult = orderInfoService.queryPage(map, request);

		List<Map> orderinfos = pageResult.getRet();

		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		for (int i = 0; i < orderinfos.size(); i++) {
			JSONObject jo = orderInfoService.getJsonObject(orderinfos.get(i));
			User user = userService.getByUuid(UUID.UUIDFromString(orderinfos.get(i).get("order_user_uuid").toString()));
			jo.put("order_user_uuid", user.getLoginName());
			jo.put("order_status", StringUtils.trimToEmpty(OrderInfo.OrderStatus.get(orderinfos.get(i).get("order_status"))));
			jo.put("orderid", StringUtils.trimToEmpty(orderinfos.get(i).get("order_id").toString()));
			array.put(jo);
		}
		
		json.put("data", array);
		json.put("iTotalRecords", pageResult.getiTotalRecords());
		json.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());
		return json.toString();
	}
	
	
	/**
	 * 添加或修改订单信息
	 * @param uuid
	 * @param model
	 * @return
	 */
	@RequestMapping("get")
	public String get(Model model, String uuid, String orderid, HttpServletRequest request){
		//得到所有订单信息对应的列
		Map<String, ColumnDesign> map = orderInfoService.getAllColumns();
		model.addAttribute("maps", map);
		//判断修改还是保存
		if(StringUtils.isNotBlank(uuid)){
			//得到订单信息
			Map<String, Object> orders = orderInfoService.getOrderInfoByUUId(uuid);
			//存放订单信息
			model.addAttribute("orderinfo", orders);
			model.addAttribute("uid", uuid);
			//存放订单状态信息
			model.addAttribute("order_status", orders.get("order_status"));
			//得到订单的详情
			List<OrderDetail> ods = orderDetailService.getOrderDetailsByOrderId(orderid);
			model.addAttribute("ods", ods);
			model.addAttribute("cstmiid", orders.get("cstm_id"));
		}else{
			model.addAttribute("serialized", orderInfoSerialize.getSerializeId());
		}
		//订单状态
		model.addAttribute("orderstatus", OrderInfo.OrderStatus);
		//获取当前登陆用户
		User user = SessionUtil.getCurrentUser(request);
		model.addAttribute("useruid", user.getUid());
		//得到所有产品信息
		List<Product> plist = productService.getAllProducts();
		model.addAttribute("plist", plist);
		//获取当前用户的所有客户
		List<Customer> cstms = customerService.getOwnerByAgent(user.getUid());
		model.addAttribute("cstms", cstms);
		
		return "orderinfo/orderinfo-save";
	}
	
	/**
	 * 保存/修改 订单信息
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("save")
	@ResponseBody
	public String saveTags(HttpServletRequest request, HttpServletResponse response, OrderInfo order, BindingResult bindingResult, String[] pName, String[] spinner)
			throws ParseException {

		//得到订单的UUID
		String uuid = request.getParameter("uid");
		
		//获取当前登陆用户
		User user = (User) SessionUtil.getCurrentUser(request);
		
		//得到订单信息
		String order_id = request.getParameter("order_id");
		String receive_user_name = request.getParameter("receive_user_name");
		String receive_user_address = request.getParameter("receive_user_address");
		String receive_user_mobile = request.getParameter("receive_user_mobile");
		String pay_status = request.getParameter("pay_status");
		String freight = request.getParameter("freight");
		String invoice_type = request.getParameter("invoice_type");
		String invoice_info = request.getParameter("invoice_info");
		String order_status = request.getParameter("order_status");
		String cstm_id = request.getParameter("cstm_id");
		String cstm_name = request.getParameter("cstm_name");
		
		OrderInfo oi = null;
		
		if(StringUtils.isNotBlank(uuid)) {
			oi = orderInfoService.getOrderByUUID(uuid);
		}else{
			oi = new OrderInfo();
			oi.setOrderUserUUID(user.getUid());
			oi.setOrderCreateTime(fmt.parse(fmt.format(new Date())));
		}
		
		request.getSession().setAttribute("cstmId", cstm_id);
		oi.setOrderid(order_id);
		oi.setReceiveUserName(receive_user_name);
		oi.setReceiveUserAddress(receive_user_address);
		oi.setReceiveUserMobile(receive_user_mobile);
		oi.setPayStatus(pay_status);
		oi.setFreight(freight);
		oi.setInvoiceType(invoice_type);
		oi.setInvoiceInfo(invoice_info);
		oi.setCstmId(cstm_id);
		oi.setCstmName(cstm_name);
		if(order_status == null){
			oi.setOrderStatus("waitconfirm");
		}else{
			oi.setOrderStatus(order_status);
		}
		
		
		//保存订单的信息
		orderInfoService.saveOrUpdateInfo(oi);
		
		if(StringUtils.isNotBlank(uuid)) { // 修改时
			
			///删除订单原有的订单商品详情之前还原商品数量
			List<OrderDetail> ods = orderDetailService.getOrderDetailsByOrderId(oi.getOrderid());
			if(ods != null){
				for (OrderDetail od : ods) {
					//更新商品数量
					Product pd = productService.getProByPid(od.getProductId());
					pd.setProductNumber(pd.getProductNumber() + od.getProductNumber());
					productService.saveOrUpdateInfo(pd);
				}
			}
			
			//删除订单原有的订单商品详情
			List<OrderDetail> details = orderDetailService.getOrderDetailsByOrderId(oi.getOrderid());
			if(details != null){
				for (OrderDetail od : details) {
					orderDetailService.deleteOrderDetailsByOrderId(od.getOrderid());
				}
			}
			List<OrderDetail> oldinfo = new ArrayList<OrderDetail>();
			if(pName != null && spinner != null){
				List<String> listPid = new LinkedList<String>();
				for (String str : pName) {
					listPid.add(str);
				}
				List<String> listPnum = new LinkedList<String>();
				for (String str : spinner) {
					listPnum.add(str);
				}
				for (int i = 0,j = 0; i <= listPid.size(); i++,j++) {
					if(listPid.size() == 0){
						break;
					}
					String pid = pName[j]; // 商品编号
					Integer pnumber = Integer.parseInt(spinner[j]); // 商品数量
					Product pd = productService.getProByPid(pid); // 得到商品信息
					if(details != null){
						for (OrderDetail od : details) {
							if(od.getProductId().equals(pid)){// 该商品还存在该订单
								OrderDetail odee = null;
								OrderDetail ode = orderDetailService.getOrderDetailByOidAndPid(od.getOrderid(), od.getProductId());
								if(ode != null){
									odee = ode;
									odee.setProductNumber(pnumber + odee.getProductNumber());
								}else{
									odee = new OrderDetail();
									odee.setProductNumber(pnumber);
								}
								odee.setProductId(od.getProductId());
								odee.setOrderid(od.getOrderid());
								orderDetailService.saveOrUpdate(odee);
								// 更新商品数量
//								pd.setProductNumber(pd.getProductNumber() + (od.getProductNumber() - odee.getProductNumber()));
								pd.setProductNumber(pd.getProductNumber() - pnumber);
								productService.saveOrUpdateInfo(pd);
								if(listPid.get(i) != null && listPnum.get(i) != null){
									listPid.remove(i);
									listPnum.remove(i);
									i--;
								}
							}else{
								oldinfo.add(od);
							}
						}
					}
				}
				if(listPid.size() > 0 && listPnum.size() > 0){
					for (int i = 0; i < listPid.size(); i++){
						OrderDetail odd = new OrderDetail();
						odd.setProductId(listPid.get(i));
						odd.setProductNumber(Integer.parseInt(listPnum.get(i)));
						odd.setOrderid(order_id);
						orderDetailService.saveOrUpdate(odd);
						Product pd = productService.getProByPid(listPid.get(i)); // 得到商品信息
						// 更新商品数量
						pd.setProductNumber(pd.getProductNumber() - odd.getProductNumber());
						productService.saveOrUpdateInfo(pd);
					}
				}
				if(oldinfo != null){
					for (OrderDetail od : oldinfo) {
						//更新商品数量
						Product pd = productService.getProByPid(od.getProductId());
						pd.setProductNumber(pd.getProductNumber() + od.getProductNumber());
						productService.saveOrUpdateInfo(pd);
					}
				}
			}
		}else{ // 添加时
			if(pName != null && spinner != null){
				for (int i = 0; i < spinner.length; i++) {
					String pid = pName[i]; // 商品编号
					Integer pnumber = Integer.parseInt(spinner[i]); // 商品数量
					// 通过订单编号和商品编号查询商品详情是否已存在
					OrderDetail ode = orderDetailService.getOrderDetailByOidAndPid(order_id, pid);
					if(ode == null){
						// 存储新的订单详情信息
						OrderDetail od = new OrderDetail();
						od.setProductId(pid);
						od.setProductNumber(pnumber);
						od.setOrderid(order_id);
						orderDetailService.saveOrUpdate(od);
					}else{ // 用户页面选择了多条相同的商品信息
						ode.setProductNumber(pnumber + ode.getProductNumber());
						orderDetailService.saveOrUpdate(ode);
					}
				}
				
				// 去除重复的商品编号
				List<String> list = new LinkedList<String>();
				for(String str : pName){
					if(!list.contains(str)) {
		                list.add(str);
		            }
				}
				
				// 更新商品数量
				for (int i = 0; i < list.size(); i++) {
					String pid = list.get(i); // 商品编号
					OrderDetail ode = orderDetailService.getOrderDetailByOidAndPid(order_id, pid);
					if(ode != null){
						// 更新商品数量
						Product pd = productService.getProByPid(pid);
						pd.setProductNumber(pd.getProductNumber() - ode.getProductNumber());
						productService.saveOrUpdateInfo(pd);
					}
				}
			}
		}
		
		return new JSONObject().put("success", true).toString();
	}
	
	/**
	 * 校验订单编号
	 * @param request
	 * @param response
	 * @param order_id
	 * @throws IOException
	 */
	@RequestMapping("orderIdRepeat")
	@ResponseBody
	public void cstmIdRepeat(HttpServletRequest request, HttpServletResponse response, String order_id)
			throws IOException {
		
		OrderInfo order = orderInfoService.getOrderByOrderid(order_id);
		
		String uid = request.getParameter("uid");
		
		if(order == null){
            response.getWriter().print(true);
		}else{
			OrderInfo or = orderInfoService.getOrderByUUID(uid);
			if("".equals(uid)){
				response.getWriter().print(false);
			}else{
				if(or != null && uid.equals(String.valueOf(or.getUid()))){
					response.getWriter().print(true);
				}else{
					response.getWriter().print(false);
				}
			}
		}		
	}
	
	/**
	 * 根据uuid删除订单信息
	 * @param request
	 * @param response
	 * @param uuid
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public String deleteProduct(HttpServletRequest request, HttpServletResponse response, String uuid, String orderid) {
		
		//查询订单详情信息
		List<OrderDetail> ods = orderDetailService.getOrderDetailsByOrderId(orderid);
		if(ods != null){
			for (OrderDetail od : ods) {
				//更新商品数量
				Product pd = productService.getProByPid(od.getProductId());
				pd.setProductNumber(pd.getProductNumber() + od.getProductNumber());
				productService.saveOrUpdateInfo(pd);
			}
		}
		
		//删除订单详情信息
		orderDetailService.deleteOrderDetailsByOrderId(orderid);
		
		//删除订单信息
		orderInfoService.deleteOrderByUUid(uuid);
		
		return new JSONObject().put("success", true).toString();
	}
	
	@RequestMapping("checkPros")
	@ResponseBody
	public String checkPros(HttpServletRequest request, HttpServletResponse response) {
		//得到所有产品信息
		List<Product> plist = productService.getAllProducts();
		if(plist != null){
			return new JSONObject().put("success", true).toString();
		}
		return new JSONObject().put("success", false).toString();
	}
	
}
