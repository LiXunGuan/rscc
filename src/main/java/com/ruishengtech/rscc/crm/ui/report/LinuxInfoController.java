package com.ruishengtech.rscc.crm.ui.report;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruishengtech.rscc.crm.ui.sysConfig.service.SysConfigService;

@Controller
@RequestMapping("netconfig")
public class LinuxInfoController {

	@Autowired
	private SysConfigService sysConfigService;
	
	@RequestMapping
	public String index(Model model,HttpServletRequest request){
		Map<String, String> linuxInfo = SysCacheManager.getInstance().getLinuxInfoMap();
		model.addAttribute("macAddr", linuxInfo.get(Infos.LINUX_MACADDR));
		model.addAttribute("ipAddr", linuxInfo.get(Infos.LINUX_IPADDR));
		model.addAttribute("netMask", linuxInfo.get(Infos.LINUX_NETMASK));
		model.addAttribute("gateWay", linuxInfo.get(Infos.LINUX_GATEWAY));
		String nginx = sysConfigService.getSysConfigByKey(Infos.LINUX_NGINX).getSysVal();
		linuxInfo.put(Infos.LINUX_NGINX, nginx);
		model.addAttribute("nginx", nginx);
		
		model.addAttribute("iframecontent","report/net_config");
		return "iframe";
		
//		return "report/net_config";
	}
	
	@RequestMapping("upNetConfig")
    @ResponseBody
    public String upNetConfig(String ipAddr,String netMask,String gateWay,String nginx) {
		
		Map<String, String> linuxMap = SysCacheManager.getInstance().getLinuxInfoMap();
		if(!ipAddr.equals(linuxMap.get(Infos.LINUX_IPADDR))
				||!netMask.equals(linuxMap.get(Infos.LINUX_NETMASK))
				||!gateWay.equals(linuxMap.get(Infos.LINUX_GATEWAY))||!nginx.equals(linuxMap.get(Infos.LINUX_NGINX))){
			LinuxInfoUtil.updNetCfgFile(ipAddr, gateWay, netMask);
			linuxMap.put(Infos.LINUX_IPADDR, ipAddr);
			linuxMap.put(Infos.LINUX_NETMASK, netMask);
			linuxMap.put(Infos.LINUX_GATEWAY, gateWay);
			linuxMap.put(Infos.LINUX_NGINX, nginx);
			sysConfigService.updatSysConfig(nginx, Infos.LINUX_NGINX);
		}
		return new JSONObject().put("success", true).toString();
	}
	
}
