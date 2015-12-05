package com.ruishengtech.rscc.crm.ui.billing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.rscc.crm.billing.condition.BillRateCondition;
import com.ruishengtech.rscc.crm.billing.manager.RateManager;
import com.ruishengtech.rscc.crm.billing.model.BillRate;
import com.ruishengtech.rscc.crm.billing.model.BillRateSipuserLink;
import com.ruishengtech.rscc.crm.billing.model.BillRateSkillGroupLink;
import com.ruishengtech.rscc.crm.billing.service.BillRateService;
import com.ruishengtech.rscc.crm.ui.mw.model.FSQueue;
import com.ruishengtech.rscc.crm.ui.mw.model.FSUser;
import com.ruishengtech.rscc.crm.ui.mw.service.FSQueueService;
import com.ruishengtech.rscc.crm.ui.mw.service.FSSipUserService;


@Controller
@RequestMapping("billing/rate")
public class BillRateController {
	
	@Autowired
	private BillRateService brService;
	
	@Autowired
	private FSQueueService fsQueueService;
	
	@Autowired
	private FSSipUserService fsSipService;
	
	@RequestMapping
	public String index(Model model){
		List<BillRate> brs = brService.getAllBillRate();
		Set<String> rt = new HashSet<>();
		for(BillRate br : brs){
			rt.add(br.getBillRateType());
		}
		model.addAttribute("ratetype", rt);
//		
		model.addAttribute("iframecontent","billing/billRate_index");
		return "iframe";
		
//		return "billing/billRate_index";
	}
	
	@RequestMapping("data")
	@ResponseBody
	public String data(HttpServletRequest request,HttpServletResponse response,Model model,BillRateCondition brCondition){
		brCondition.setRequest(request);
		
		List<BillRate> brlist = brService.getBillRateForparam(brCondition.getBillRateType(),brCondition.getBillRateName());
		
		JSONObject jsonObject = new JSONObject();
		JSONArray array = new JSONArray();
		for(int i=0;i<brlist.size();i++){
			JSONObject jsonObject2 = new JSONObject(brlist.get(i));
			array.put(jsonObject2);
		}
		jsonObject.put("data", array);
		return jsonObject.toString();
	}

	//添加
	@RequestMapping("addrate")
	public String addrate(HttpServletRequest request,Model model){
		
		return "billing/billRate_add";
	}
	
	//修改
	@RequestMapping("changerate")
	public String changeRate(HttpServletRequest request,String uuid,Model model){
		BillRate br = brService.getBillRateByUUID(UUID.UUIDFromString(uuid));
		model.addAttribute("br", br);
		model.addAttribute("ratetype", br.getBillRateType());
		return "billing/billRate_change";
	}
	
	@RequestMapping("checkname")
	@ResponseBody
	public String checkName(HttpServletRequest request,String uuid,String billRateName){
		List<String> brname = new ArrayList<>();
		List<BillRate> brs = brService.getAllBillRate();
		for(BillRate br : brs){
			brname.add(br.getBillRateName());
		}
		//判断uuid存在，即为修改中的校验
		if(StringUtils.isNotBlank(uuid)){
			//首先判断名字是否未修改
			if(billRateName.equals(brService.getBillRateByUUID(UUID.UUIDFromString(uuid)).getBillRateName())){
				return "true";
			//如果名字已经修改的话，判断是否跟其他的名字重复
			}else if(brname.contains(billRateName)){
				return "false";
			}else{
				return "true";
			}
		//否则的话即为添加中的校验，只要校验名字是否已经存在就OK
		}else if(brname.contains(billRateName)){
			return "false";
		}
		
		return "true";
	}
	
	
	@RequestMapping("save")
	@ResponseBody
	public String save(HttpServletRequest request,BillRate br){
		brService.save(br,br.getUid());
		return new JSONObject().put("success", true).toString();
	}
	
	@RequestMapping("update")
	@ResponseBody
	public String updateData(HttpServletRequest request,
			HttpServletResponse response, String uuid,Model model) {
			BillRate br = new BillRate();
			br.setUuid(UUID.UUIDFromString(uuid));
			br.setBillRateType(request.getParameter("billRateType"));
			br.setBillRateName(request.getParameter("billRateName"));
			br.setRateSdfMoney(Float.parseFloat(request.getParameter("rateSdfMoney")));
			br.setRateSdfTime(Integer.parseInt(request.getParameter("rateSdfTime")));
			brService.update(br);
			
		return new JSONObject().put("success", true).toString();
	}
	
	@RequestMapping("allotSipuser")
	public String allotSipuser(HttpServletRequest request,Model model,UUID uuid){
		
		model.addAttribute("uuid", uuid);
		
		//获取所有分机的列表
		List<FSUser> siplist = fsSipService.getAll(FSUser.class);
		model.addAttribute("siplist", siplist);
		
		//获取费率和分机关联表所有数据
		List<BillRateSipuserLink> brslist = brService.getBillSipuser();
		List<Long> brsipnum = new ArrayList<>();
		for(BillRateSipuserLink bsl : brslist){
			brsipnum.add(Long.parseLong(bsl.getSipuserID()));
		}
		//存放关联表中所有的分机id
		model.addAttribute("brlsipnum", brsipnum);
		
		
		//存放已经分配费率的分机号码
		List<String> selfsid = new ArrayList<>();
		List<String> unselsipnum = new ArrayList<>();
		for(BillRateSipuserLink bsl : brslist){
			unselsipnum.add(bsl.getSipuserID());
			//如果分机关联表中的id和传入的费率id一致
			if(bsl.getBillRateUUID().equals(uuid.toString())){
				selfsid.add(bsl.getSipuserID());
			}
		}
		model.addAttribute("unselsipnum", unselsipnum);
		model.addAttribute("selfsid", selfsid);

		return "billing/bill_allotSip";
	}
	
	@RequestMapping("allotSkillGroup")
	public String allotSkillGroup(HttpServletRequest request,Model model,UUID uuid){
		//保存所有技能组数据
		List<FSQueue> skills = fsQueueService.getAllFSQueue();
		model.addAttribute("skills", skills);
		model.addAttribute("uuid", uuid);
		
		//获取技能组关联表所有数据
		List<BillRateSkillGroupLink> skillG = brService.getBillSkillGroup();
		List<Long> skillinkid = new ArrayList<>();
		for(BillRateSkillGroupLink bsgl : skillG){
			String[] names = bsgl.getSkillGroupUUID().split("#");
			if(names.length > 0){
				skillinkid.add(Long.parseLong(names[0]));
			}
		}
		model.addAttribute("bslinkskillid", skillinkid);
		
		//存放当前费率分配的技能组id
		List<Long> selskid = new ArrayList<>();
		for(BillRateSkillGroupLink bsgl : skillG){
			if(bsgl.getBillrateSkillUUID().equals(uuid.toString())){
				String[] names = bsgl.getSkillGroupUUID().split("#");
				if(names.length > 0){
					selskid.add(Long.parseLong(names[0]));
				}
			}
		}
		model.addAttribute("selskid", selskid);
		
		
		return "billing/bill_allotSkillGroup";
	}
	
	@RequestMapping("allotSkillGroupData")
	@ResponseBody
	public String allotSkillGroupData(HttpServletRequest request,String uuid,String[] skills){
		brService.deleteRateSkillGroupById(uuid,null);
		if(skills == null){
			return new JSONObject().put("success", true).toString();
		}
		for(int i=0;i<skills.length;i++){
			String u = skills[i];
			brService.insertRateSkill(uuid, u);
			RateManager.getInstance().refresh();
		}
		return new JSONObject().put("success", true).toString();
	}
	
	@RequestMapping("allotSipuserData")
	@ResponseBody
	public String allotAgentData(HttpServletRequest request,String uuid,String[] sipusers){
		brService.deleteRateSipuserById(uuid,null);
		if(sipusers == null){
			return new JSONObject().put("success", true).toString();
		}
		for(int i=0;i<sipusers.length;i++){
			String u = sipusers[i];
			brService.insertRateSipuser(uuid, u);
			RateManager.getInstance().refresh();
		}
		return new JSONObject().put("success", true).toString();
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public String deleteUser(String uuid, Model model){
		JSONObject jsonObject = new JSONObject();
		if(brService.deleteByID(UUID.UUIDFromString(uuid))){
			brService.deleteRateSipuserById(uuid,null);
			brService.deleteRateSkillGroupById(uuid,null);
			return jsonObject.put("success", true).toString();
		}
		return jsonObject.put("success", false).toString();
	}
	
}
