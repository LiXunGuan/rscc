package com.ruishengtech.rscc.crm.report.service.imp;

import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.framework.core.util.DateUtils;
import com.ruishengtech.rscc.crm.report.condition.ReportAgentCallCondition;
import com.ruishengtech.rscc.crm.report.model.ReportAgentCall;
import com.ruishengtech.rscc.crm.report.solution.ReportAgentCallSolution;

@Service
@Transactional
public class ReportAgentCallServiceImp extends BaseService{
	
	public PageResult<ReportAgentCall> querypage(ReportAgentCallCondition agentCallCondition){
		//页面输入的统计时长的处理
		if(StringUtils.isNotBlank(agentCallCondition.getOutduration())){
			String[] agcs = agentCallCondition.getOutduration().split(",");
			//数据库cdr表中存的时间是毫秒，需要将页面输入的秒转换为毫秒
			agcs[0] = agcs[0]+"000";
			agcs[1] = agcs[1]+"000";
			agcs[2] = agcs[2]+"000";
			
			//只是为了在solution拼出来SQL，单独给Outcallcount赋值然后取出来就ok
			agentCallCondition.setOutcallcount_p1("0-"+agcs[0]);
			agentCallCondition.setOutcallcount_p2(agcs[0]+"-"+agcs[1]);
			agentCallCondition.setOutcallcount_p3(agcs[1]+"-"+agcs[2]);
			agentCallCondition.setOutcallcount_p4(agcs[2]);
		}else if(StringUtils.isNotBlank(agentCallCondition.getInduration())){
			String[] agcs = agentCallCondition.getInduration().split(",");
			agcs[0] = agcs[0]+"000";
			agcs[1] = agcs[1]+"000";
			agcs[2] = agcs[2]+"000";
			agentCallCondition.setIncallcount_p1("0-"+agcs[0]);
			agentCallCondition.setIncallcount_p2(agcs[0]+"-"+agcs[1]);
			agentCallCondition.setIncallcount_p3(agcs[1]+"-"+agcs[2]);
			agentCallCondition.setIncallcount_p4(agcs[2]);
		}
		//月报的时间处理
		if("month".equals(agentCallCondition.getSelectionReport())){
			Integer year = Integer.parseInt(agentCallCondition.getStarttime().substring(0,4));
			Integer month = Integer.parseInt(agentCallCondition.getStarttime().substring(5));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			agentCallCondition.setStarttime(sdf.format(DateUtils.getFirstDayOfMonth(year, month))+" 00:00:00");
			agentCallCondition.setEndtime(sdf.format(DateUtils.getFirstDayOfNextMonth(year, month))+" 00:00:00");
		}
		return super.queryPage(new ReportAgentCallSolution(), agentCallCondition, ReportAgentCall.class);
	}
	
	public JSONObject setJsonValue(ReportAgentCall d){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		JSONObject jsonObject2 = new JSONObject(d);
		jsonObject2.put("calltime", sdf.format(d.getCalltime()).substring(0, 10));
		jsonObject2.put("outcallduration_p1", DateUtils.getMsToTime(Long.parseLong(StringUtils.isNotBlank(d.getOutcallduration_p1())?d.getOutcallduration_p1():"0")));
		jsonObject2.put("outcallduration_p2", DateUtils.getMsToTime(Long.parseLong(StringUtils.isNotBlank(d.getOutcallduration_p2())?d.getOutcallduration_p2():"0")));
		jsonObject2.put("outcallduration_p3", DateUtils.getMsToTime(Long.parseLong(StringUtils.isNotBlank(d.getOutcallduration_p3())?d.getOutcallduration_p3():"0")));
		jsonObject2.put("outcallduration_p4", DateUtils.getMsToTime(Long.parseLong(StringUtils.isNotBlank(d.getOutcallduration_p4())?d.getOutcallduration_p4():"0")));
		jsonObject2.put("outcallduration", DateUtils.getMsToTime(Long.parseLong(StringUtils.isNotBlank(d.getOutcallduration())?d.getOutcallduration():"0")));
		
		jsonObject2.put("incallduration_p1", DateUtils.getMsToTime(Long.parseLong(StringUtils.isNotBlank(d.getIncallduration_p1())?d.getIncallduration_p1():"0")));
		jsonObject2.put("incallduration_p2", DateUtils.getMsToTime(Long.parseLong(StringUtils.isNotBlank(d.getIncallduration_p2())?d.getIncallduration_p2():"0")));
		jsonObject2.put("incallduration_p3", DateUtils.getMsToTime(Long.parseLong(StringUtils.isNotBlank(d.getIncallduration_p3())?d.getIncallduration_p3():"0")));
		jsonObject2.put("incallduration_p4", DateUtils.getMsToTime(Long.parseLong(StringUtils.isNotBlank(d.getIncallduration_p4())?d.getIncallduration_p4():"0")));
		jsonObject2.put("incallduration", DateUtils.getMsToTime(Long.parseLong(StringUtils.isNotBlank(d.getIncallduration())?d.getIncallduration():"0")));
		
		return jsonObject2;
	}
}
