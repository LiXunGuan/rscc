package com.ruishengtech.rscc.crm.ui.orderinfo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.ruishengtech.framework.core.util.PhoneUtil;
import com.ruishengtech.rscc.crm.cstm.service.CustomerService;
import com.ruishengtech.rscc.crm.neworderinfo.model.NewOrderInfo;
import com.ruishengtech.rscc.crm.neworderinfo.service.NewOrderInfoService;
import com.ruishengtech.rscc.crm.neworderinfo.solution.NewOrderInfoSolution;
import com.ruishengtech.rscc.crm.product.model.OrderDetail;
import com.ruishengtech.rscc.crm.product.model.OrderInfo;
import com.ruishengtech.rscc.crm.product.model.Product;
import com.ruishengtech.rscc.crm.product.service.OrderDetailService;
import com.ruishengtech.rscc.crm.product.service.ProductService;
import com.ruishengtech.rscc.crm.qc.model.QualityCheck;
import com.ruishengtech.rscc.crm.qc.service.QualityCheckService;
import com.ruishengtech.rscc.crm.record.model.Record;
import com.ruishengtech.rscc.crm.record.service.RecordService;
import com.ruishengtech.rscc.crm.ui.ExportReportUtil;
import com.ruishengtech.rscc.crm.ui.IndexService;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.ui.orderinfo.service.OrderInfoSerializeService;
import com.ruishengtech.rscc.crm.ui.sysConfig.SysConfigManager;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.DatarangeService;
import com.ruishengtech.rscc.crm.user.service.UserService;

/**
 * @author chengxin
 *
 */
@Controller
@RequestMapping("neworderinfo")
public class NewOrderInfoController {

	@Autowired
	private NewOrderInfoService orderInfoService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	
//	@Autowired
//	private NewOrderDetailService orderDetailService;
	
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
	
	@Autowired
	private RecordService recordService;

	@Autowired
	private QualityCheckService checkService;
	
	SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@RequestMapping()
	public String index(Model model, HttpServletRequest request){
		
		//是否隐藏号码
		indexService.judgeIsHidePhone(model, SessionUtil.getCurrentUser(request).getUid());
		
		//查询所有字段
		orderInfoService.getTitles(model);
		
		DiyBean ret = diyTableService.getTableDescByName("new_order_info");
		
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
		
		Date d=new Date();
		model.addAttribute("startTime", d);
		model.addAttribute("endTime", d);
		
		// 获取当前登陆用户的角色
		User us = (User) SessionUtil.getCurrentUser(request);
		List<String> roles = userService.getPermissionRoles(us.getUid());
		
		for (String str : roles) {
			if("000001".equals(str)){
				model.addAttribute("manage", true);
			}
		}
		if(!"pop".equals(request.getParameter("level"))) {
			model.addAttribute("iframecontent","neworderinfo/neworderinfo-index");
			return "iframe";		
		} 
		
		return "neworderinfo/neworderinfo-index";
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
		
		Map<String, ColumnDesign> map = diyTableService.getTableDescByName("new_order_info").getClomns();
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
			
			jo.put("call_session_uuid", orderinfos.get(i).get("call_session_uuid") == null ? "" : orderinfos.get(i).get("call_session_uuid"));
			
			// 是否接通
            jo.put("put_through", orderinfos.get(i).get("bridgesec") == null ? "无" : Record.PUT_THROUGH.get(Long.parseLong(orderinfos.get(i).get("bridgesec").toString())>0?"y":"n"));
            
            // 接通时长
            jo.put("bridgesec", orderinfos.get(i).get("bridgesec") == null ? "无" : Long.parseLong(orderinfos.get(i).get("bridgesec").toString())%1000!=0?Long.parseLong(orderinfos.get(i).get("bridgesec").toString())/1000+1:Long.parseLong(orderinfos.get(i).get("bridgesec").toString())/1000);

            if(request.getSession().getAttribute("level") != null){
				jo.put("level", "agent");
			}
            
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
	public String get(Model model, String uuid, String orderid, String operation, String callSessionUUid, HttpServletRequest request){
		//得到所有订单信息对应的列
		Map<String, ColumnDesign> map = orderInfoService.getAllColumns();
		model.addAttribute("maps", map);
		//判断修改还是添加
		if(StringUtils.isNotBlank(uuid)){
			//得到订单信息
			Map<String, Object> orders = orderInfoService.getOrderInfoByUUId(uuid);
			//存放订单信息
			model.addAttribute("orderinfo", orders);
			model.addAttribute("uid", uuid);
			//存放订单状态信息
			model.addAttribute("order_status", orders.get("order_status"));
			model.addAttribute("call_session_uuid", orders.get("call_session_uuid"));
			//得到订单的详情
			List<OrderDetail> ods = orderDetailService.getOrderDetailsByOrderId(orderid);
			if(ods != null){
				model.addAttribute("ods", ods);
			}
			
			if("quality".equals(operation)){
				
				NewOrderInfo order = orderInfoService.getOrderByUUID(uuid);
				if(StringUtils.isNotBlank(order.getCall_session_uuid())){
					Record record = recordService.getRecordByCallsessionuuid(callSessionUUid);
					record.setRecord_file(record.getPlayUrl(SysConfigManager.getInstance().getDataMap().get("nginx").getSysVal()));
					model.addAttribute("record", record);
					model.addAttribute("playrecord", true);
				}
				
				QualityCheck check = checkService.getQualityByObjUUID(orderid); 
				model.addAttribute("orderId", orderid);
				if(null != check){
					model.addAttribute("check", check);
					model.addAttribute("sScore", check.getScore());
				}
				model.addAttribute("quality", true);
			}
		
		}else{
			model.addAttribute("serialized", orderInfoSerialize.getSerializeId());
			model.addAttribute("waitconfirm", OrderInfo.OrderStatus_WaitConfirm);
		}
		
		//订单状态
		model.addAttribute("orderstatus", OrderInfo.OrderStatus);
		
		//获取当前登陆用户
		User user = SessionUtil.getCurrentUser(request);
		model.addAttribute("useruid", user.getUid());
		
		//得到所有产品信息
		List<Product> plist = productService.getAllProducts();
		model.addAttribute("plist", plist);
		
		if("quality".equals(operation)){
			return "neworderinfo/neworderinfo-qualitysave";
		}
		
		return "neworderinfo/neworderinfo-save";
	}
	
	/**
	 * 保存/修改 订单信息
	 * @return
	 * 
	 */
	@RequestMapping("save")
	@ResponseBody
	public String saveNewOrderInfo(HttpServletRequest request, HttpServletResponse response, NewOrderInfo order, BindingResult bindingResult, String[] pName, String[] spinner)
			throws Exception {
		
		// 获取当前登陆用户
		User user = (User) SessionUtil.getCurrentUser(request);
		orderInfoService.saveOrUpdateNewOrderInfo(request, user, order, pName, spinner);
		
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
		
		NewOrderInfo order = orderInfoService.getOrderByOrderid(order_id);
		
		String uid = request.getParameter("uid");
		
		if(order == null){
            response.getWriter().print(true);
		}else{
			NewOrderInfo or = orderInfoService.getOrderByUUID(uid);
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
	
	/**
	 * 根据订单编号得到相关信息
	 * @param request
	 * @param response
	 * @param orderid
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("getOrderInfo")
	@ResponseBody
	public String getOrderInfo(HttpServletRequest request, HttpServletResponse response, String orderid, Model model)
			throws IOException {
		JSONObject jsob = new JSONObject();
		if (orderid != null) {
			StringBuilder sb = new StringBuilder();
			NewOrderInfo info = orderInfoService.getOrderByOrderid(orderid);
			if (info != null) {
				boolean b = userService.hasPermission(SessionUtil.getCurrentUser(request).getUid(), "90");
				if("true".equals(SysConfigManager.getInstance().getDataMap().get("hiddenPhoneNumber").getSysVal()) && !b){
					info.setReceiveUserMobile(PhoneUtil.hideNumber(info.getReceiveUserMobile()));
				}
				sb.append("收货人：" + info.getReceiveUserName() + ""
						+ "<br/>手机号码：" + info.getReceiveUserMobile() + ""
						+ "<br/>收货地址：" + info.getReceiveUserAddress());
//						+ "<br/>支付状态：" + ("".equals(info.getPayStatus()) ? '无':info.getPayStatus()) + ""
//						+ "<br>运费：" + ("".equals(info.getFreight()) ? '无':info.getFreight()) + ""
//						+ "<br>发票类型：" + ("".equals(info.getInvoiceType()) ? '无':info.getInvoiceType()) + ""
//						+ "<br>发票信息：" + ("".equals(info.getInvoiceInfo()) ? '无':info.getInvoiceInfo()));
			}
			jsob.put("ext", sb.toString()); 
			return jsob.toString();
		}else{
			jsob.put("result", "false");
			return jsob.toString();
		}
	}
	
	@SuppressWarnings("static-access")
	@RequestMapping("exportdata")
	@ResponseBody
	public void exportData(HttpServletRequest request,HttpServletResponse response, NewOrderInfoSolution solution) throws IOException{
		
		User us = (User) SessionUtil.getCurrentUser(request);
		List<String> strList = new ArrayList<String>();
		List<String> departs = userService.getDepartments(us.getUid());
		if(departs.size() != 0){
			for (String str : departs) {
				strList.addAll(datarangeService.getUserUuids(str));
			}
			request.setAttribute("allusers", strList);
		}
		
		ExportReportUtil excel = new ExportReportUtil();
		Map<String, ColumnDesign> map = orderInfoService.getAllColumns();
		List<Map<String, Object>> values = excel.getNewOrderInfoResult(map, request, solution);
		
		List<String> ch_list = new ArrayList<String>();
		List<String> en_list = new ArrayList<String>();
		
		Iterator<Entry<String, ColumnDesign>> entries = map.entrySet().iterator();
		//按照顺序查询自定义表中的字段信息
		while (entries.hasNext()) {  
		    Entry<String, ColumnDesign> entry = entries.next();  
		    en_list.add(entry.getKey());
		    ch_list.add(entry.getValue().getColumnName());
		}
		
		en_list.add("put_through");
		ch_list.add("是否接通");
		
		en_list.add("bridgesec");
		ch_list.add("接通时长");
		
		en_list.add("products");
		ch_list.add("商品信息");
		
		excel.createExcel("订单信息表.xls", ch_list, en_list, values, response);
	}
	
	
}
