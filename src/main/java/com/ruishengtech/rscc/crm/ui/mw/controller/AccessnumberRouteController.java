package com.ruishengtech.rscc.crm.ui.mw.controller;
import java.io.IOException;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.ruishengtech.rscc.crm.ui.mw.condition.CallInTimerCondition;
import com.ruishengtech.rscc.crm.ui.mw.model.AccessNumber;
import com.ruishengtech.rscc.crm.ui.mw.model.MWAccessnumberRoute;
import com.ruishengtech.rscc.crm.ui.mw.model.MWExtenRoute;
import com.ruishengtech.rscc.crm.ui.mw.model.MWFsHost;
import com.ruishengtech.rscc.crm.ui.mw.service.MWAccessnumberRputeService;
import com.ruishengtech.rscc.crm.ui.mw.service.MWExtenRouteService;
import com.ruishengtech.rscc.crm.ui.mw.util.DateUtils;

/**
 * Created by yaoliceng on 2014/11/3.
 */
@Controller
@RequestMapping("config/accessnumberroute")
public class AccessnumberRouteController {
	
	 public static  Boolean isApply=false;

	 @Autowired
	 private MWAccessnumberRputeService mwAccessnumberRputeService;
	 
	 @Autowired
	 private MWExtenRouteService extenRouteService;
	 
	 @RequestMapping
	 public String Batch(HttpServletRequest request,
			HttpServletResponse response, Model model, String str) {

		
		List<AccessNumber> fsls= mwAccessnumberRputeService.getAccessNumber("1");
		Map<String,String> fsMap=new LinkedHashMap<String,String>();
		if (fsls!=null) {
			for (int i = 0; i < fsls.size(); i++) {
				if (fsls.get(i).getId()!=null) {
					fsMap.put(fsls.get(i).getId().toString(),fsls.get(i).getAccessnumber());
				}
			}
			model.addAttribute("fsMap", fsMap);
		}
		
		model.addAttribute("isApply", isApply);
		
		model.addAttribute("iframecontent","config/accessnumberroute");
		return "iframe";
		
//		return "config/accessnumberroute";
	}

	/**
	 * 数据请求
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("data")
	@ResponseBody
	public String data(HttpServletRequest request,
			HttpServletResponse response, CallInTimerCondition inCallTimerCondition,Model model) {
		inCallTimerCondition.setRequest(request);

		
		PageResult<MWAccessnumberRoute> pageResult = mwAccessnumberRputeService.page(inCallTimerCondition);	//获取符合查询条R件的数据
		List<MWAccessnumberRoute> list = (List<MWAccessnumberRoute>) pageResult.getRet();

		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		SimpleDateFormat format=new SimpleDateFormat ("yyyy-MM-dd HH:mm");
		for (int i = 0; i < list.size(); i++) {	//将数据放入json
 
			JSONObject jsob = new JSONObject(list.get(i));
			jsob.put("id", list.get(i).getId());
			jsob.put("name", StringUtils.trimToEmpty(list.get(i).getName()));
			jsob.put("startDate", StringUtils.trimToEmpty(format.format(list.get(i).getStartDate())));
			jsob.put("endDate", StringUtils.trimToEmpty(format.format(list.get(i).getEndDate())));

			jsonArray.put(jsob);
		}

		jsonObject.put("aaData", jsonArray);
		jsonObject.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject.put("iTotalDisplayRecords",
				pageResult.getiTotalDisplayRecords());
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
        	jsob.put("titleshow", "<i class='fa fa-lg fa-fw fa-wrench'></i>呼叫中心配置&nbsp;&nbsp;>&nbsp;<span>接入号路由配置</span>");
        return jsob.toString();
    }

    
	@RequestMapping("get")
	public String getScopExt(HttpServletRequest request,
			HttpServletResponse response, Long id,String num, Model model)
			throws SocketException {
		model.addAttribute("num", num);
		//查询所有能打入的接入号
		List<AccessNumber> fsls= mwAccessnumberRputeService.getAccessNumber("1");
		Map<String,String> fsMap=new LinkedHashMap<String,String>();
		if (fsls!=null) {
			for (int i = 0; i < fsls.size(); i++) {
				if (fsls.get(i).getId()!=null) {
					fsMap.put(fsls.get(i).getId().toString(),fsls.get(i).getAccessnumber());
				}
			}
		}

		model.addAttribute("fsMap", fsMap);
        model.addAttribute("extRou", MWExtenRoute.ROUTER);
		
		
		MWAccessnumberRoute item=null;
		if (id != null) {	//如果外线id不为空 表示修改  查询外线信息
			 item= mwAccessnumberRputeService.get(id);
			
			MWExtenRoute mwExtenRoute= extenRouteService.getExtenById(item.getExtenRouteId());
			model.addAttribute("type", mwExtenRoute == null ? "" : mwExtenRoute.getType());
			model.addAttribute("item", item);

            if(StringUtils.isNotBlank(item.getPeriod())) {

                JSONObject jsonObject = new JSONObject(item.getPeriod());
                String start = jsonObject.optString("start");
                String end = jsonObject.optString("end");

                if (StringUtils.isNotBlank(start)) {
                    model.addAttribute("start_1_h", start.split(":")[0]);
                    model.addAttribute("start_1_m", start.split(":")[1]);
                }
                if (StringUtils.isNotBlank(end)) {
                    model.addAttribute("stop_1_h", end.split(":")[0]);
                    model.addAttribute("stop_1_m", end.split(":")[1]);
                }

                String weeks = jsonObject.optString("week");
                if (StringUtils.isNotBlank(weeks)) {
                    model.addAttribute("weeks", weeks);
                }
            }
		}

//		List<MWExtenRoute> extrouls = extenRouteService.getAll(MWExtenRoute.class);
//		model.addAttribute("extrouls", extrouls);


		return "config/accessnumberroute_save";
	}
	
	/**
	 * 根据类型查出数据
	 * 
	 * @param request
	 * @param response
	 * @param name
	 * @param id
	 * @throws IOException
	 */
	@RequestMapping("getFSXmlType")
	@ResponseBody
	public String getFSXmlType(HttpServletRequest request,
			HttpServletResponse response, String types) throws IOException {
		JSONArray json = new JSONArray();
		JSONObject jsob = null;
		List xmlls = null;
		if (types.equals(MWExtenRoute.ROUTER_TYPE_SIPUSER)) {	//查询所有分机
			xmlls = extenRouteService.getExtenRoute(MWExtenRoute.ROUTER_TYPE_SIPUSER);
		}else if (types.equals(MWExtenRoute.ROUTER_TYPE_CALLCENTER)) {	//查询所有技能组
			xmlls = extenRouteService.getExtenRoute(MWExtenRoute.ROUTER_TYPE_CALLCENTER);
		}else if (types.equals(MWExtenRoute.ROUTER_TYPE_IVR)) {	//查询所有IVR
			xmlls = extenRouteService.getExtenRoute(MWExtenRoute.ROUTER_TYPE_IVR);
		} else if (types.equals(MWExtenRoute.ROUTER_TYPE_DIALPLAN)) {	//查询所有拨号计划
			xmlls = extenRouteService.getExtenRoute(MWExtenRoute.ROUTER_TYPE_DIALPLAN);
		}else if (types.equals(MWExtenRoute.ROUTER_TYPE_AGENT)) {
            xmlls=extenRouteService.getExtenRoute(MWExtenRoute.ROUTER_TYPE_AGENT);
        }
		if (xmlls != null) {
			for (int i = 0; i < xmlls.size(); i++) {
				jsob = new JSONObject(xmlls.get(i));
				json.put(jsob);
			}
			return json.toString();
		} else {
			jsob = new JSONObject();
			jsob.put("result", "false");
			return jsob.toString();
		}
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
			HttpServletResponse response, String extension, Long id)
			throws IOException {
		
	}

	/**
	 * 保存
	 * @param request
	 * @param fsGateWay
	 * @return 
	 */
	@RequestMapping("save")
	@ResponseBody
	public String save(HttpServletRequest request, MWAccessnumberRoute mwInCalltimer) {
		String s=request.getParameter("startDates");
		String s1=request.getParameter("endDates");
		mwInCalltimer.setStartDate(DateUtils.stringToDate1(s));
		mwInCalltimer.setEndDate(DateUtils.stringToDate1(s1));
		mwInCalltimer.setExtenRouteId(extenRouteService.getExten(mwInCalltimer.getExtension()).getId());

        mwInCalltimer.setType(request.getParameter("timecheck"));

        JSONObject detail = new JSONObject();
        if(!"n".equals(mwInCalltimer.getType())){
            detail.put("start",request.getParameter("start_1_h")+":"+request.getParameter("start_1_m"));
            detail.put("end",request.getParameter("stop_1_h")+":"+request.getParameter("stop_1_m"));
        }

        if("w".equals(mwInCalltimer.getType())){

            String[] ssa = request.getParameterValues("weekcheckbox");
            StringBuilder stringBuilder =new StringBuilder();
            for (String s2 : ssa) {
                stringBuilder.append(s2).append(",");
            }
            detail.put("week", stringBuilder.toString());
        }
        mwInCalltimer.setPeriod(detail.toString());
		
		mwAccessnumberRputeService.saveOrUpdate(mwInCalltimer);
		isApply=true;
		
		// 调用应用
		deploy(request);
		
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
	public String remove(HttpServletRequest request, Long id) {
		mwAccessnumberRputeService.remove(id);
		isApply=true;
		
		// 调用应用
		deploy(request);
		
		return new JSONObject().put("success", true).toString();
	}

	/**
	 * 应用
	 * @param request
	 * @param mwGateWay
	 * @return
	 */
	@RequestMapping("deploy")
	@ResponseBody
	public String deploy(HttpServletRequest request) {
		mwAccessnumberRputeService.deploy();
		isApply=false;
		return new JSONObject().put("success", true).toString();
	}

}
