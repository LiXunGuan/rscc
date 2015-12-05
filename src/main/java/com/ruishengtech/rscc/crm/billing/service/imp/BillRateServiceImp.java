package com.ruishengtech.rscc.crm.billing.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.billing.model.BillRate;
import com.ruishengtech.rscc.crm.billing.model.BillRateSipuserLink;
import com.ruishengtech.rscc.crm.billing.model.BillRateSkillGroupLink;
import com.ruishengtech.rscc.crm.billing.service.BillRateService;
import com.ruishengtech.rscc.crm.ui.mw.service.FSQueueService;
import com.ruishengtech.rscc.crm.ui.mw.service.FSSipUserService;

@Service
@Transactional
public class BillRateServiceImp extends BaseService implements BillRateService{
	
	@Autowired
	private FSSipUserService fsSipUserService;
	
	@Autowired
	private FSQueueService fsqService;
	
	@Override
	public List<BillRate> getAllBillRate() {
				
		List<BillRate> list = getAll(BillRate.class);
		return list;
	}
	
	public List<BillRate> getBillRateForparam(String billRateType,String billRateName){
		List<BillRate> list = new ArrayList<>();
		if(StringUtils.isNotBlank(billRateType)){
			list = getBeanList(BillRate.class, " AND billratetype = ? ", billRateType);
			return list;
		}
		if(StringUtils.isNotBlank(billRateName)){
			list = getBeanList(BillRate.class, " AND billratename = ? ", billRateName);
			return list;
		}
		return getAllBillRate();
	}
	
	public List<BillRate> query(final String billratetype) {
		List<BillRate> brs = queryBean(new BeanHandler() {

			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				sql.append(" SELECT * FROM billing_rate WHERE billratetype =  ?  ");
				params.add(billratetype);
			}
		}, BillRate.class);
		return brs;
	}

	
	@Override
	public BillRate getBillRateByUUID(UUID uuid) {
		BillRate br = getByUuid(BillRate.class, uuid);
		return br;
	}
	@Override
	public void save(BillRate br) {
		super.save(br);
	}
	
	@Override
	public void save(BillRate br,String billRateUUID) {
		super.save(br);
		
	}
	@Override
	public boolean update(BillRate br,String[] exculdeFieldName) {
		super.update(br, exculdeFieldName);
		return true;
	}
	
	public boolean update(BillRate br){
		super.update(br);
		return true;
	}

	@Override
	public boolean deleteByID(UUID uuid) {
		super.deleteById(BillRate.class, uuid);
		return true;
	}
	
	
	public List<BillRateSipuserLink> getBillSipuser(){
		List<BillRateSipuserLink> ba = getBeanListWithSql(BillRateSipuserLink.class, "select * from billrate_sipuser where 1=1");
		return ba;
	}
	
	public List<BillRateSkillGroupLink> getBillSkillGroup(){
		List<BillRateSkillGroupLink> bs = getBeanListWithSql(BillRateSkillGroupLink.class,"select * from billrate_skillgroup where 1 = ?", ""+1);
		return bs;
	}
	
	@Override
	public boolean insertRateSipuser(String billRateUUID,String sipuserID) {
		if (!StringUtils.isBlank(billRateUUID) && sipuserID == null){
			return 1==jdbcTemplate.update("INSERT INTO billrate_sipuser (billrate_uuid) VALUES (?)", billRateUUID);
		}else {
			jdbcTemplate.update("INSERT INTO billrate_sipuser (billrate_uuid,sipuser_id) VALUES (?,?)", billRateUUID,sipuserID);
			return true;
		}
	}

	
	public boolean updateRateSipuser(String billRateUUID,List<String> sipuserID){
		if(StringUtils.isBlank(billRateUUID)){
			jdbcTemplate.update("UPDATE billrate_Sipuser SET sipuser_id = ? WHERE billrate_uuid = ?",sipuserID,billRateUUID);
			return true;
		}else{
			jdbcTemplate.update("UPDATE billrate_Sipuser SET billrate_uuid = ? WHERE sipuser_id = ?",billRateUUID,sipuserID);
			return true;
		}
	}
	
	public Map<String, BillRate> getSipuserRateMap(){
		List<BillRateSipuserLink> list = getBillSipuser();
		Map<String, BillRate> map = new HashMap<>();
		for (BillRateSipuserLink billRateSipuserLink : list) {
			map.put(billRateSipuserLink.getSipuserID(), this.getBillRateByUUID(UUID.UUIDFromString(billRateSipuserLink.getBillRateUUID())));
//			FSUser user = fsSipUserService.getById(FSUser.class,Long.parseLong(billRateSipuserLink.getSipuserID()));
//			if (user != null) {
				//有可节省空间，取出来所有的BillRate，这里面相同的只取引用即可。
//			}
		}
		BillRate billRate = new BillRate();
		billRate.setRateSdfMoney(0f);
		billRate.setRateSdfTime(0);
		map.put(null, billRate);
		return map;
	}
	
	public void deleteRateSipuserById(String rateuuid,String sipid){
		if(StringUtils.isNotBlank(rateuuid)){
			jdbcTemplate.update("delete from billrate_sipuser where billrate_uuid = ?",rateuuid);
		}else{
			jdbcTemplate.update("delete from billrate_sipuser where sipuser_id = ?",sipid);
		}
		
	}
	
	public void deleteRateSkillGroupById(String rateuuid,String skillid){
		if(StringUtils.isNotBlank(rateuuid)){
			jdbcTemplate.update("delete from billrate_skillgroup where billrateS_uuid = ?",rateuuid);
		}else{
			jdbcTemplate.update("delete from billrate_skillgroup where skillGroup_uuid = ?",skillid);
		}
		
	}
	
	public Map<String, BillRate> getSkillGroupRateMap(){
		List<BillRateSkillGroupLink> list = getBillSkillGroup();
		Map<String, BillRate> map = new HashMap<>();
		for (BillRateSkillGroupLink billRateSkillLink : list) {
			String[] names = billRateSkillLink.getSkillGroupUUID().split("#");
			if (names.length > 0)
				map.put(names[1], this.getBillRateByUUID(UUID.UUIDFromString(billRateSkillLink.getBillrateSkillUUID())));
		}
//		map.put(null, this.getDefaultBillRate());
		BillRate billRate = new BillRate();
		billRate.setRateSdfMoney(0f);
		billRate.setRateSdfTime(0);
		map.put(null, billRate);
		return map;
	}
	
//	public BillRate getBillRateByLoginName(String loginname){
//		User user = userService.getUserByLoginName(loginname, false);
//		BillRate br = null;
//		if(user != null){
//			int has = jdbcTemplate.queryForObject(" select count(*) from billrate_Sipuser where user_uuid = ? ", Integer.class, user.getUid());
//			if (has == 0) {
//				br = getBeanList(BillRate.class, " and isdefault='YES' ").get(0);
//			} else {
//				String rateUuid = jdbcTemplate.queryForObject(" select * from billrate_Sipuser where user_uuid = ? ", String.class, user.getUid());
//				br = getByUuid(BillRate.class, UUID.UUIDFromString(rateUuid));
//			}
//		}
//		return br;
//	}

	@Override
	public boolean insertRateSkill(String billRateUUID, String skillid) {
		if (!StringUtils.isBlank(billRateUUID) && skillid == null){
			return 1==jdbcTemplate.update("INSERT INTO billrate_skillgroup (billrateS_uuid) VALUES (?)", billRateUUID);
		}else {
			jdbcTemplate.update("INSERT INTO billrate_skillgroup (billrateS_uuid,skillGroup_uuid) VALUES (?,?)", billRateUUID,skillid);
			return true;
		}
	}

	@Override
	public boolean updateRateSkill(String billRateUUID, List<String> skillid) {
		if(StringUtils.isBlank(billRateUUID)){
			jdbcTemplate.update("UPDATE billrate_skillgroup SET skillGroup_uuid = ? WHERE billrateS_uuid = ?",skillid,billRateUUID);
			return true;
		}
		return false;
	}


}
