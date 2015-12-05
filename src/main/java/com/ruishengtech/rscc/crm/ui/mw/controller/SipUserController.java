package com.ruishengtech.rscc.crm.ui.mw.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.rscc.crm.billing.manager.RateManager;
import com.ruishengtech.rscc.crm.billing.model.BillRate;
import com.ruishengtech.rscc.crm.billing.model.BillRateSipuserLink;
import com.ruishengtech.rscc.crm.billing.service.BillRateService;
import com.ruishengtech.rscc.crm.ui.mw.condition.SipUserCondition;
import com.ruishengtech.rscc.crm.ui.mw.model.FSUser;
import com.ruishengtech.rscc.crm.ui.mw.model.MWAccessnumberRoute;
import com.ruishengtech.rscc.crm.ui.mw.model.MWExtenRoute;
import com.ruishengtech.rscc.crm.ui.mw.model.MWFsHost;
import com.ruishengtech.rscc.crm.ui.mw.service.FSSipUserService;
import com.ruishengtech.rscc.crm.ui.mw.service.MWAccessnumberRputeService;
import com.ruishengtech.rscc.crm.ui.mw.service.MWExtenRouteService;

/**
 * Created by yaoliceng on 2014/11/3.
 */
@Controller
@RequestMapping("config/sipuser")
public class SipUserController {

	public static  Boolean isApply = false;

	@Autowired
	private FSSipUserService fsSipUserService;

	@Autowired
	private MWExtenRouteService extenRouteService;

	@Autowired
	private MWAccessnumberRputeService mwAccessnumberRputeService;
	
	@Autowired
	private BillRateService brService;
	

    @RequestMapping
    public String Batch(HttpServletRequest request, HttpServletResponse response,Model model,String str) {
    	model.addAttribute("isApply", isApply);
    	
		model.addAttribute("iframecontent","config/sipuser");
		return "iframe";
    	
//        return "config/sipuser";
    }
    
    /**
     * 数据请求
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("data")
    @ResponseBody
    public String data(HttpServletRequest request, HttpServletResponse response, SipUserCondition sipUserCondition) {

    	sipUserCondition.setRequest(request);

        PageResult<FSUser> pageResult = fsSipUserService.page(sipUserCondition);	//获取符合条件的数据
        List<FSUser> list = (List<FSUser>) pageResult.getRet();

        JSONArray jsonArray = new JSONArray();	//将数据放入json
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < list.size(); i++) {
            JSONObject jsob = new JSONObject(list.get(i));
            jsob.put("area_code", StringUtils.isNotBlank(list.get(i).getArea_code()) == true ? StringUtils.isNotBlank(list.get(i).getArea_code()) : "");
            jsob.put("caller_id_name", StringUtils.isNotBlank(list.get(i).getCaller_id_name()) == true ? StringUtils.isNotBlank(list.get(i).getCaller_id_name()) : "");
            jsob.put("caller_id_number", StringUtils.isNotBlank(list.get(i).getCaller_id_number()) == true ? StringUtils.isNotBlank(list.get(i).getCaller_id_number()) : "");
            jsonArray.put(jsob);
        }

        jsonObject.put("aaData", jsonArray);
        jsonObject.put("iTotalRecords", pageResult.getiTotalRecords());
        jsonObject.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());
        return jsonObject.toString();

    }
    
    /**
     * 加载标题
     */
    @RequestMapping("menushow")
    @ResponseBody
    public String menushow(HttpServletRequest request, HttpServletResponse response, MWFsHost mwFsHost)
            throws IOException {
        JSONObject jsob = new JSONObject();
        	jsob.put("titleshow", "<i class='fa fa-lg fa-fw fa-wrench'></i>呼叫中心配置&nbsp;&nbsp;>&nbsp;<span>SIP账号</span>");
        return jsob.toString();
    }
    
    /**
     * 保存
     * @param request
     * @param mwSipUser
     * @return
     */
    @RequestMapping("dataall")
    @ResponseBody
    public String dataAll(HttpServletRequest request,FSUser fsUser) {
    	 List<FSUser> list =fsSipUserService.getAll(FSUser.class);
    	 StringBuilder sb=new StringBuilder();
    	 if (list.size()>0) {
			for (int i = 0; i < list.size(); i++) {
				sb.append(list.get(i).getId()+",");
			}
		}
        return sb.toString();
    }
    
    /**
	 * 验证
	 * @param request
	 * @param response
	 * @param name
	 * @param id
	 * @throws IOException
	 */
	@RequestMapping("isRepeat")
	@ResponseBody
	public void isRepeat(HttpServletRequest request,
			HttpServletResponse response, String sipId, Long id)
			throws IOException {
		// 根据该外线名查询外线信息
		FSUser fsgw = fsSipUserService.getFSUser(sipId);

		if (fsgw==null) {
			response.getWriter().print(true); // 直接打印true 通过
		} else {
			if (id==null) { // 添加
				response.getWriter().print(false);
			} else { // 修改
				if (id==fsgw.getId()) { // 如果个数为一，并且是修改的同一条数据（编号一致）
					response.getWriter().print(true); // 如果有 ： 返回 true 通过
				} else {
					response.getWriter().print(false); // 没有 返回false 不通过
				}
			}
		}
	}
	
	@RequestMapping("checkIdPwd")
	@ResponseBody
	public void checkIdPwd(HttpServletRequest request, HttpServletResponse response, String sipid, String sipwd)throws IOException {
		if(sipid.equalsIgnoreCase(sipwd)){
			response.getWriter().print(false); // 没有 返回false 不通过
		}else{
			response.getWriter().print(true); // 直接打印true 通过
		}
	}
    
    @RequestMapping("get")
    public String getData(HttpServletRequest request,
                          HttpServletResponse response,Long id,Model model){
    	List<BillRate> brs = brService.getAllBillRate();
    	
    	//存放所有费率
    	model.addAttribute("allrate", brs);
    	//查询所有费率和分机关联表数据
    	List<BillRateSipuserLink> bsg = brService.getBillSipuser();
    	BillRate billr = new BillRate();
        if (id != null) {
        	FSUser item = fsSipUserService.get(id);
            model.addAttribute("item", item);
            for(BillRateSipuserLink bslink : bsg){
            	if(bslink.getSipuserID().equals(item.getSipId())){
            		billr = brService.getBillRateByUUID(UUID.UUIDFromString(bslink.getBillRateUUID()));
            	}
            }
        }
        model.addAttribute("thisrate", billr);
        return "config/sipuser_save";
    }
    
    /**
     * 弹框  批量增加
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("getAll")
    public String getDataAll(HttpServletRequest request,
                          HttpServletResponse response,Model model){
    	List<BillRate> brs = brService.getAllBillRate();
    	model.addAttribute("allrate", brs);
        return "config/sipuser_addAll";
    }


    @RequestMapping("editAll")
    public String editDataAll(HttpServletRequest request,
                          HttpServletResponse response,Model model){
    	List<BillRate> brs = brService.getAllBillRate();
    	model.addAttribute("allrate", brs);
        return "config/sipuser_editAll";
    }

    /**
     * 保存
     * @param request
     * @param mwSipUser
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public String save(HttpServletRequest request,FSUser fsUser) {
    	fsSipUserService.saveOrUpdate(fsUser);
    	
    	//查询MWExtenRoute表 是否已经存在该号码
    	MWExtenRoute ext = extenRouteService.getExten(fsUser.getSipId().toString());
    	if (ext==null) {
    		// 保存一个SipUser路由
        	MWExtenRoute extrenRoute=new MWExtenRoute();
        	extrenRoute.setName("分机"+fsUser.getSipId().toString());
        	extrenRoute.setExtension(fsUser.getSipId().toString());
        	extrenRoute.setType(MWExtenRoute.ROUTER_TYPE_SIPUSER);
        	extrenRoute.setDestId(fsUser.getId());
        	extrenRoute.setDestString(fsUser.getSipId().toString());
        	extrenRoute.setCan_del(1);
    		extenRouteService.saveOrUpdate(extrenRoute);
		}
    	isApply=true;
        ExtenRouteController.isApply=true;
        
        //如果设置了费率，那么id要保存到费率和分机关联表里面去
        if(fsUser.getId() != null){
        	if(StringUtils.isNotBlank(request.getParameter("rate"))){
        	brService.deleteRateSipuserById(null, fsUser.getSipId().toString());
			brService.insertRateSipuser(request.getParameter("rate"),fsUser.getSipId());
			RateManager.getInstance().refresh();
			}else if("".equals(request.getParameter("rate"))){
				brService.deleteRateSipuserById(null, fsUser.getSipId().toString());
			}
		}
        
        // 调用应用
        apply(request);
        
        return new JSONObject().put("success", true).toString();
    }
    
    /**
     * 应用
     * @param request
     * @param mwGateWay
     * @return
     */
    @RequestMapping("apply")
    @ResponseBody
    public String apply(HttpServletRequest request) {
    	fsSipUserService.toFSXMl();
    	fsSipUserService.deploy();
    	isApply=false;
    	// 调用分机号管理应用
    	extenRouteService.deploy();
    	ExtenRouteController.isApply = false;
        return new JSONObject().put("success", true).toString();
    }
  
    /**
     * 批量添加
     * @param request
     * @param mwSipUser
     * @param pwdType
     * @param num
     * @return
     */
    @RequestMapping("saveAll")
    @ResponseBody
    public String saveAll(HttpServletRequest request,FSUser mwSipUser,String pwdType,Integer num) {
    	
    	Integer sipId=Integer.valueOf(mwSipUser.getSipId());
    	
    	//如果设置了费率，那么id要保存到费率和分机关联表里面去
    	if(mwSipUser.getId() != null){
    		if(StringUtils.isNotBlank(request.getParameter("rate"))){
				brService.insertRateSipuser(request.getParameter("rate"),mwSipUser.getSipId().toString());
				RateManager.getInstance().refresh();
			}else if("".equals(request.getParameter("rate"))){
				brService.deleteRateSipuserById(null, mwSipUser.getSipId().toString());
			}
		}
    	
    	//查询范围内所有分机号
    	List<FSUser> sipUserls=fsSipUserService.getFSUserls(mwSipUser.getSipId(),String.valueOf(sipId+num));	
    	//用来保存所有分机号码
    	List<String> strls=new ArrayList<String>();		
    	
    	if (sipUserls!=null) {
        	for (FSUser sip : sipUserls) {
    			strls.add(sip.getSipId().toString());
    		}
		}
    	
    	mwSipUser.setSipId(String.valueOf(sipId-1));
    	sipId=sipId-1;
    	
    	for (int i = 0; i < num; i++) {	//循环添加分机
    		
    		sipId=sipId+1;	//得到当前分机号码
    		mwSipUser.setSipId(String.valueOf(sipId)); 	//赋值分机号码
    		
    		if (sipUserls!=null) {
				if (strls.contains(mwSipUser.getSipId())) {
					FSUser user = fsSipUserService.getFSUser(mwSipUser.getSipId());
					user.setArea_code(mwSipUser.getArea_code());
					user.setCaller_id_name(mwSipUser.getCaller_id_name());
					user.setCaller_id_number(mwSipUser.getCaller_id_number());
					updatepassword(request, pwdType, user, mwSipUser);
					continue;
				}
			}
    		
			FSUser user=new FSUser();
			user.setSipId(mwSipUser.getSipId());
			user.setSipPassword(mwSipUser.getSipPassword());
			user.setArea_code(mwSipUser.getArea_code());
			user.setCaller_id_name(mwSipUser.getCaller_id_name());
			user.setCaller_id_number(mwSipUser.getCaller_id_number());
			
			updatepassword(request, pwdType, user, mwSipUser);
		}
    	
    	isApply=true;
        ExtenRouteController.isApply=true;
        
        // 调用应用
        apply(request);
        
        return new JSONObject().put("success", true).toString();
    }
    
    public void updatepassword(HttpServletRequest request, String pwdType, FSUser user, FSUser mwSipUser){
		if (pwdType.equals("pwdType1")) {	//判断密码生成方式
			user.setSipPassword(mwSipUser.getSipId().toString());
			save(request, user);
		}else if (pwdType.equals("pwdType2")) {
			int max=1000000;
			int min=99999;
			Random random=new Random();
			user.setSipPassword(String.valueOf(random.nextInt(max)%(max-min+1)+min));	//得到6位随机密码
			save(request, user);
		}else{
			user.setSipPassword(mwSipUser.getSipPassword());
			save(request, user);
		}
    }




    @RequestMapping("alledit")
    @ResponseBody
    public String alledit(HttpServletRequest request,FSUser mwSipUser,String pwdType,Integer num) {


        Integer sipId=Integer.valueOf(mwSipUser.getSipId());

        //查询范围内所有分机号
        List<FSUser> sipUserls=fsSipUserService.getFSUserls(mwSipUser.getSipId(),String.valueOf(sipId+num));
        //用来保存所有分机号码
        List<String> strls=new ArrayList<String>();
        //保存所有分机id
        List<String> sipid = new ArrayList<>();
        if (sipUserls!=null) {
            for (FSUser sip : sipUserls) {
            	sipid.add(sip.getId().toString());
                strls.add(sip.getSipId().toString());
            }
        }
        for(int i=0;i<sipid.size();i++){
        	brService.deleteRateSipuserById(null, sipid.get(i));
        }
       brService.updateRateSipuser(null, sipid);
        mwSipUser.setSipId(String.valueOf(sipId-1));
        sipId=sipId-1;

        for (int i = 0; i < num; i++) {	//循环添加分机

            sipId=sipId+1;	//得到当前分机号码
            mwSipUser.setSipId(String.valueOf(sipId)); 	//赋值分机号码

            if (sipUserls!=null) {
                if (strls.contains(mwSipUser.getSipId())) {
                    FSUser user = fsSipUserService.getFSUser(mwSipUser.getSipId());

                    if(StringUtils.isNotBlank(request.getParameter("check_area_code"))) {
                        user.setArea_code(mwSipUser.getArea_code());
                    }
                    if(StringUtils.isNotBlank(request.getParameter("check_caller_id_name"))) {
                        user.setCaller_id_name(mwSipUser.getCaller_id_name());
                    }

                    if(StringUtils.isNotBlank(request.getParameter("check_caller_id_number"))) {
                        user.setCaller_id_number(mwSipUser.getCaller_id_number());
                    }
                   

                    if(StringUtils.isNotBlank(request.getParameter("check_chkPwd"))) {

                        if (pwdType.equals("pwdType1")) {	//判断密码生成方式
                            user.setSipPassword((mwSipUser.getSipId().toString()));

                        }else if (pwdType.equals("pwdType2")) {
                            int max=1000000;
                            int min=99999;
                            Random random=new Random();
                            user.setSipPassword(String.valueOf(random.nextInt(max) % (max - min + 1) + min));	//得到6位随机密码
                        }else{
                            user.setSipPassword(mwSipUser.getSipPassword());
                        }
                    }
                    save(request, user);
                }
            }
        }

        isApply=true;
        ExtenRouteController.isApply=true;
        
        // 调用应用
        apply(request);
        
        return new JSONObject().put("success", true).toString();
    }

    /**
     * 批量删除
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("batchremove")
    @ResponseBody
    public String BatchRemove(HttpServletRequest request,String sb) {
    	
    	String[] str=null;
    	if (sb!=null) {
			str=sb.split(",");
			for (int i = 0; i < str.length; i++) {
				 FSUser fs = fsSipUserService.getFSUserByid(str[i]);
				brService.deleteRateSipuserById(null, fs.getSipId());
				if(!"false".equalsIgnoreCase(str[i])){
					remove(request,Long.valueOf(str[i]));
				}
			}
		}
    	
    	isApply=true;
    	
    	// 调用应用
        apply(request);
    	
        return new JSONObject().put("success", true).toString();
    }

    /**
     * 删除
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("remove")
    @ResponseBody
    public String remove(HttpServletRequest request,Long id) {
    	
    	fsSipUserService.remove(id);//sip账号删除
    	brService.deleteRateSipuserById(null,""+id);
    	RateManager.getInstance().refresh();
    	//(新加逻辑)--查询sip账号相关的分级号并删除分机号
		List<MWExtenRoute> extens = extenRouteService.getExtenRoute(MWExtenRoute.ROUTER_TYPE_SIPUSER.trim(), id);
		if(extens != null){
			for (MWExtenRoute exten : extens) {
				//查询此分机号对应的相关的接入号路由信息
				List<MWAccessnumberRoute> anumers = mwAccessnumberRputeService.getAccessNumberRouteByExtenId(exten.getId());
				if(anumers != null){
					for(MWAccessnumberRoute mwar : anumers) {
						mwAccessnumberRputeService.remove(mwar.getId());
					}
				}
				extenRouteService.remove(exten.getId());
			}
		}
		
		//之前的逻辑
//		extenRouteService.removExtenRoutes(id, MWExtenRoute.ROUTER_TYPE_SIPUSER.trim());//查询出要删除的id的路由
    	
		isApply=true;
		
		// 调用应用
        apply(request);
		
        return new JSONObject().put("success", true).toString();
    }
}
