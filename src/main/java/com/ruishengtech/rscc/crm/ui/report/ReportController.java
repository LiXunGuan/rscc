package com.ruishengtech.rscc.crm.ui.report;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("report")
public class ReportController {

	@RequestMapping
	public String index(Model model,HttpServletRequest request){
		Map<String, String> linuxInfo = SysCacheManager.getInstance().getLinuxInfoMap();
		model.addAttribute("macAddr", linuxInfo.get(Infos.LINUX_MACADDR));
		model.addAttribute("ipAddr", linuxInfo.get(Infos.LINUX_IPADDR));
		model.addAttribute("netMask", linuxInfo.get(Infos.LINUX_NETMASK));
		model.addAttribute("gateWay", linuxInfo.get(Infos.LINUX_GATEWAY));
		
		model.addAttribute("iframecontent","report/report_index");
		return "iframe";
		
//		return "report/report_index";
	}
	
	@RequestMapping("upNetConfig")
    @ResponseBody
    public String upNetConfig(String ipAddr,String netMask,String gateWay) {
		
		Map<String, String> linuxMap = SysCacheManager.getInstance().getLinuxInfoMap();
		if(!ipAddr.equals(linuxMap.get(Infos.LINUX_IPADDR))
				||!netMask.equals(linuxMap.get(Infos.LINUX_NETMASK))
				||!gateWay.equals(linuxMap.get(Infos.LINUX_GATEWAY))){
			LinuxInfoUtil.updNetCfgFile(ipAddr, gateWay, netMask);
			linuxMap.put(Infos.LINUX_IPADDR, ipAddr);
			linuxMap.put(Infos.LINUX_NETMASK, netMask);
			linuxMap.put(Infos.LINUX_GATEWAY, gateWay);
		}
		return new JSONObject().put("success", true).toString();
	}
	
}
