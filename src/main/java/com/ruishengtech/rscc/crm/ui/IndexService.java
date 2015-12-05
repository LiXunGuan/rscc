package com.ruishengtech.rscc.crm.ui;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.ruishengtech.framework.core.AESTools;
import com.ruishengtech.framework.core.ApplicationHelper;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.ui.mw.manager.CallRouteManager;
import com.ruishengtech.rscc.crm.ui.mw.model.AccessNumber;
import com.ruishengtech.rscc.crm.ui.mw.model.AccessNumberGateway;
import com.ruishengtech.rscc.crm.ui.mw.model.FSSipTrunk;
import com.ruishengtech.rscc.crm.ui.mw.send.CallManager;
import com.ruishengtech.rscc.crm.ui.mw.send.SipManager;
import com.ruishengtech.rscc.crm.ui.mw.service.FSGateWayService;
import com.ruishengtech.rscc.crm.ui.mw.service.MWSYSConfigService;
import com.ruishengtech.rscc.crm.ui.sysConfig.SysConfigManager;
import com.ruishengtech.rscc.crm.ui.sysConfig.model.Config;
import com.ruishengtech.rscc.crm.ui.sysConfig.model.SysConfig;
import com.ruishengtech.rscc.crm.ui.sysConfig.service.SysConfigService;
import com.ruishengtech.rscc.crm.user.model.Action;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.UserService;

/**
 * @author Frank
 *
 */
@Service
@Transactional
public class IndexService extends BaseService {

    @Autowired
    private FSGateWayService fsGateWayService;
    
    @Autowired
    private UserService userService;
	
	@Autowired
	private SysConfigService configService;

	Map<String, Action> map = new LinkedHashMap<String, Action>();

	/**
	 * 存放请求路径URL
	 * 
	 * @return
	 */
	public void initUrl() {

		List<Action> actions = getAll(Action.class);
		for (int i = 0; i < actions.size(); i++) {
			map.put(actions.get(i).getActionDescribe(), actions.get(i));
		}
	}

	/**
	 * 获取请求路径URL
	 * 
	 * @return
	 */
	public Map<String, Action> getUrlMap() {
		return map;
	}

	/**
	 * 配置系统属性
	 * 
	 * @param config
	 */
	public void setSystemConfigInfo(Config config) {

		// 设置网络信息
		setSysNetInfo(config);

		// 设置管理员信息
		setAdminInfo(config);

		// 批量添加坐席
		setUserPhoneInfo(config);

        setExteriorInfo(config);
        // 保存外线信息

		// 接入号信息设置
		//setAccessNumber(config);
        //sendAgentServer(config);
	}

    public void deploy(Config config) {
        sendAgentServer(config);
    }

    private void sendAgentServer(Config config){
    	
    	if(StringUtils.isNotBlank(config.getAccessNumber()) && StringUtils.isNotBlank(config.getGateway())){
    		
    		fsGateWayService.toFSXMl();
    		fsGateWayService.deploy();

            CallRouteManager callRouteManager=  ApplicationHelper.getApplicationContext().getBean(CallRouteManager.class);
            callRouteManager.loadDefaultGateway();
    	}
    }

    /**
	 * 批量添加坐席
	 * 
	 * @param config
	 */
	private void setUserPhoneInfo(Config config) {

		if(StringUtils.isNotBlank(config.getAgentBegin()) && StringUtils.isNotBlank(config.getAgentEnd())){
			
			// 发送请求创建分机
			SipManager.batchCreateSip(Integer.parseInt(config.getAgentBegin()),Integer.parseInt(config.getAgentEnd()),config.getAgentNumber(), config.getAgentNumbers());
		}
	}

	/**
	 * 保存外线信息 和 相关的 接入号信息设置
	 * 
	 * @param config
	 */
	private void setExteriorInfo(Config config) {

		if(StringUtils.isNotBlank(config.getSipTrunkIP())){
			
			SysConfig sysSipTrunk = new SysConfig();
			sysSipTrunk.setSysKey("sipTrunkIP");
			sysSipTrunk.setSysVal(config.getSipTrunkIP());
	
	        FSSipTrunk fsSipTrunk = new FSSipTrunk();
	
	        fsSipTrunk.setName(config.getSipTrunkName());
	        fsSipTrunk.setFshost_id(1l);
	        fsSipTrunk.setSipProfileId(2l);
	        fsSipTrunk.setIp(config.getSipTrunkIP());
	
	        if (("1").equals(config.getIsRegist())) {
	            fsSipTrunk.setRegistration("1");
	            fsSipTrunk.setUsername(config.getAgUserName());
	            fsSipTrunk.setPassword(config.getAgPassword());
	        }else{
	            fsSipTrunk.setRegistration("0");
	        }
	
	        fsGateWayService.save(fsSipTrunk);
	        
	        if(StringUtils.isNotBlank(config.getAccessNumber())){
	        	
	        	AccessNumber accessNumber = new AccessNumber();
	        	accessNumber.setAccessnumber(config.getAccessNumber());
	        	
	        	//勾了为1  没有勾为0
	        	if(null != config.getCallIn()){
	        		accessNumber.setCanin("1");
	        	}else{
	        		accessNumber.setCanin("0");
	        	}
	        	
	        	if(null != config.getCallOut()){
	        		accessNumber.setCanout("1");
	        	}else{
	        		accessNumber.setCanout("0");
	        	}
	        	
//	        	accessNumber.setCanin(config.getCallIn());
//	        	accessNumber.setCanout(config.getCallOut());
	        	
	        	accessNumber.setConcurrency(Integer.valueOf(config.getConcurrency()));
	        	
	        	fsGateWayService.save(accessNumber);
	        	
	        	AccessNumberGateway accessNumberGateway = new AccessNumberGateway();
	        	accessNumberGateway.setAccessnumber_id(accessNumber.getId());
	        	accessNumberGateway.setGateway_id(fsSipTrunk.getId());
	        	
	        	fsGateWayService.save(accessNumberGateway);
	        	
	        	JSONObject jsob = new JSONObject();
	            com.ruishengtech.rscc.crm.ui.mw.model.SysConfig sysConfig =new com.ruishengtech.rscc.crm.ui.mw.model.SysConfig();
	            jsob.put("accessnumber", StringUtils.trimToEmpty(config.getAccessNumber()));
	            jsob.put("content", StringUtils.trimToEmpty(sysConfig.getContent()));

	            sysConfig.setVal(jsob.toString());
	            sysConfig.setName("defaultaccessnumber");
	            MWSYSConfigService sysConfigService = ApplicationHelper.getApplicationContext().getBean(MWSYSConfigService.class);

	            sysConfigService.saveOrUpdate(sysConfig);

	        	
	        }
		}

    }

	/**
	 * 设置管理员信息
	 * 
	 * @param config
	 */
	private void setAdminInfo(Config config) {
		
		// 设置管理员信息信息
		UserService userService = ApplicationHelper.getApplicationContext()
				.getBean(UserService.class);

		User user = userService.getByUuid(UUID.UUIDFromString("0"));
		user.setLoginName("admin");
		
		if(StringUtils.isNotBlank(config.getUserpassword())){
			
			user.setPassword(config.getUserpassword());
			user.setMail(config.getEmail());
		}else{
			
			user.setPassword("admin");
			user.setMail("");
		}
		
		userService.update(user);
	}

	/**
	 * 设置网络信息
	 * 
	 * @param config
	 */
	private void setSysNetInfo(Config config) {

		SysConfigService configService = ApplicationHelper.getApplicationContext().getBean(SysConfigService.class);
		// 保存网络信息
		
		if(StringUtils.isNotBlank(config.getIpAddress())){
			
			SysConfig sysConfig = new SysConfig();
			sysConfig.setSysKey("ipaddress");
			sysConfig.setSysVal(config.getIpAddress());
			configService.saveOrUpdate(sysConfig);
		}
		
		if(StringUtils.isNotBlank(config.getNetMask())){
			
			SysConfig sysConfig1 = new SysConfig();
			sysConfig1.setSysKey("netmask");
			sysConfig1.setSysVal(config.getNetMask());
			configService.saveOrUpdate(sysConfig1);
		}
		
		if(StringUtils.isNotBlank(config.getGateway())){
			SysConfig sysConfig2 = new SysConfig();
			sysConfig2.setSysKey("gateway");
			sysConfig2.setSysVal(config.getGateway());
			configService.saveOrUpdate(sysConfig2);
		}

		configService.updatSysConfig("1", "sys.first");
	}
	
	/**
	 * 判断是否隐藏号码
	 * @param model
	 */
	public void judgeIsHidePhone(Model model, String userUid) {
		
		if(null != SysConfigManager.getInstance().getDataMap().get("hiddenPhoneNumber")){
			model.addAttribute("hiddenPhone", SysConfigManager.getInstance().getDataMap().get("hiddenPhoneNumber").getSysVal());
		}
		
		model.addAttribute("hasPhonePermission", userService.hasPermission(userUid, "90"));
		
	}
	
	/**
	 * 加密字符
	 * @param detailq
	 * @return
	 */
	public String encrypt(String detailq) {
		byte[] encryptResult = AESTools.encrypt(detailq, "shrsrjjsyxgs");
		String sendStr = AESTools.parseByte2HexStr(encryptResult);
		return sendStr;
	}
	
	
	public void showCompanyInfo(Model model) {
		//显示sysconfig中配置的公司信息
		com.ruishengtech.rscc.crm.ui.sysConfig.model.SysConfig companyName = configService.getSysConfigByKey("companyname"); 
		if(null != companyName && StringUtils.isNotBlank(companyName.getSysVal())){
			model.addAttribute("companyname", companyName.getSysVal());
		}
		
		com.ruishengtech.rscc.crm.ui.sysConfig.model.SysConfig companyAdd = configService.getSysConfigByKey("companyadd");
		if(null != companyAdd && StringUtils.isNotBlank(companyAdd.getSysVal())){
			model.addAttribute("wangzhi", "网址: ");
			model.addAttribute("companyadd", companyAdd.getSysVal());
		}
		com.ruishengtech.rscc.crm.ui.sysConfig.model.SysConfig companyPhone = configService.getSysConfigByKey("companyphone");
		if(null != companyPhone && StringUtils.isNotBlank(companyPhone.getSysVal())){
			model.addAttribute("dianhua", "联系电话 : ");
			model.addAttribute("companyphone",companyPhone.getSysVal());
		}
	}

	/**
	 * 还原坐席状态
	 * @param request
	 * @param loginedUser
	 */
	public void rebackAgentStatus(HttpServletRequest request, User loginedUser) {
		
		//还原上次闲忙状态
		Object reason = request.getSession().getAttribute("pause");
		if (reason == null) {
			CallManager.unpauseCall(loginedUser.getLoginName());
		} else {
			CallManager.pauseCall(loginedUser.getLoginName(), reason.toString());
		}
		
	}

	public String getSystemInfo(Model model) throws Exception {

		//软件信息
		model.addAttribute("product_id", "RSMW");
		model.addAttribute("product_name", "睿声语音中间件");
		model.addAttribute("version", "1.2.1");
		
		model.addAttribute("license_serial", "2013-1021-4321-8765");
		model.addAttribute("license_type", "commercial");
		model.addAttribute("issuance_date", "2013-10-21");
		
		//代理信息
		model.addAttribute("reseller_name", "客户公司");
		model.addAttribute("reseller_contact", "张三公司");
		model.addAttribute("reseller_phone", "13044125772");
		
		//客户信息
		model.addAttribute("customer_name", "个人名称");
		model.addAttribute("customer_contact", "张三");
		model.addAttribute("customer_phone", "13044125772");
//		
//		
//		File file = new File("E:/license.txt");
//		InputStream in = null;
//		StringBuffer buffer = new StringBuffer();
//		try {
//            System.out.println("以字节为单位读取文件内容，一次读多个字节：");
//            // 一次读多个字节
//            byte[] tempbytes = new byte[100];
//            int byteread = 0;
//            in = new FileInputStream(file);
//            showAvailableBytes(in);
//            // 读入多个字节到字节数组中，byteread为一次读入的字节数
//            while ((byteread = in.read(tempbytes)) != -1) {
////                System.out.write(tempbytes, 0, byteread);
//                buffer.append(new String(tempbytes,"UTF-8"));
//            }
//            
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        } finally {
//            if (in != null) {
//                try {
//                    in.close();
//                } catch (IOException e1) {
//                }
//            }
//        }
//		
//		return String.valueOf(buffer);
		
		return "";
		
	}
	/**
     * 显示输入流中还剩的字节数
     */
    private void showAvailableBytes(InputStream in) {
        try {
        	in.available();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
