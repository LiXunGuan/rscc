package com.ruishengtech.rscc.crm.datamanager.service.imp;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.datamanager.manager.DataManagers;
import com.ruishengtech.rscc.crm.datamanager.manager.node.ShareNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.UserNode;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.ShareToUserData;
import com.ruishengtech.rscc.crm.datamanager.model.DataGlobalShare;
import com.ruishengtech.rscc.crm.datamanager.model.DataGlobalShareRecord;
import com.ruishengtech.rscc.crm.datamanager.service.DataGlobalShareService;
import com.ruishengtech.rscc.crm.datamanager.solution.DataGlobalShareSolution;

@Service
@Transactional
public class DataGlobalShareServiceImp extends BaseService implements DataGlobalShareService {
	
	@Autowired
	private DataManagers dataManagers;
	
//	public static Integer numlimit = Integer.parseInt(SpringPropertiesUtil.getProperty("globalShareNum"));
//	public static Integer numlimit = Integer.parseInt(SysConfigManager.getInstance().getDataMap().get("sys.globalshare.getDataNum").getSysVal());
	
	@Override
	public PageResult<DataGlobalShare> queryPage(ICondition condition) {
		return super.queryPageNumLimit(new DataGlobalShareSolution(), condition, DataGlobalShare.class);
	}
	
	@Override
	public PageResult<DataGlobalShare> queryPagedata(ICondition condition) {
		return super.queryPage(new DataGlobalShareSolution(), condition, DataGlobalShare.class);
	}
	
	@Override
	public List<DataGlobalShare> getAllDataGlobalShare() {
		List<DataGlobalShare> list = getBeanListWithTable("new_data_global_share", DataGlobalShare.class, "");
		return list;
	}

	@Override
	public synchronized void saveGlobalShareRecord(DataGlobalShareRecord dg) {
		super.save(dg);
	}
	
	@Override
	public synchronized void updateGlobalShareData(DataGlobalShare dgs) {
		super.update(dgs);
	}
	
	@Override
	public void changeGlobalShareStat(String dataUuid, boolean stat) {
		jdbcTemplate.update("update new_data_global_share_record set mark_save=? where globalshare_uuid=? order by own_user_timestamp desc limit 1", stat?DataGlobalShareRecord.Y:DataGlobalShareRecord.N, dataUuid);
	}

	@Override
	public List<DataGlobalShareRecord> getGlobalShareRecordByUserUuid(String useruuid, String marksave) {
		List<DataGlobalShareRecord> list = getBeanListWithTable("new_data_global_share_record", DataGlobalShareRecord.class, " and own_user = ? and date(own_user_timestamp) = curdate() and mark_save = ? ", useruuid, marksave);
		if (list.size() > 0) {
            return list;
        }
        return null;
	}

	@Override
	public Integer getGlobalShareByOwnUserUuid(String useruuid) {
		List<DataGlobalShare> list = getBeanListWithTable("new_data_global_share", DataGlobalShare.class, " and own_user = ? ", useruuid);
		if (list.size() > 0) {
            return list.size();
        }
        return 0;
	}

	@Override
	public List<DataGlobalShare> getGlobalShareByNumLimit(Integer numlimit, String userUuid) {
		List<DataGlobalShare> list = getBeanListWithTable("new_data_global_share", DataGlobalShare.class, " and (own_user is null or own_user!='system') and transfer_user!=? ORDER BY transfer_timestamp DESC limit ? ", userUuid, numlimit);
		if (list.size() > 0) {
            return list;
        }
        return null;
	}

	@Override
	public Integer getGlobalShareRecordByUserUuid(String useruuid) {
		List<DataGlobalShareRecord> list = getBeanListWithTable("new_data_global_share_record", DataGlobalShareRecord.class, " and own_user = ? and date(own_user_timestamp) = curdate() ", useruuid);
		if (list.size() > 0) {
            return list.size();
        }
        return 0;
	}

	@Override
	public int getGlobalShare(String department, String useruuid, Integer numlimit) {
		// 查询关系表当天是否存在记录
		Integer dataNumber = getGlobalShareRecordByUserUuid(useruuid);
		
		if(dataNumber != 0 && dataNumber < numlimit){
			numlimit = numlimit - dataNumber;
		}
		
		// 查询给定条数的数据
		List<DataGlobalShare> dgsList = getGlobalShareByNumLimit(numlimit, useruuid);
		if(dgsList != null){
			//  循环移动数据并在关联表保存数据
			ShareNode fromNode = new ShareNode();
			UserNode toNode = new UserNode(department);
			ShareToUserData tfd  = new ShareToUserData();
			tfd.setTransferUser(useruuid);
			for (DataGlobalShare dg : dgsList) { 
				tfd.setBatchUuid(dg.getBatchUuid());
				tfd.setTransferData(dg.getUid());
				dataManagers.transfer(fromNode, toNode, tfd);
				
				// 保存关联表信息
				DataGlobalShareRecord dgsr = new DataGlobalShareRecord();
				dgsr.setGlobalShareUuid(dg.getUid());
				dgsr.setOwnUser(useruuid);
				dgsr.setPhoneNumber(dg.getPhoneNumber());
				dgsr.setOwnUserTimestamp(new Date());
				dgsr.setMarkSave(DataGlobalShareRecord.N);
				saveGlobalShareRecord(dgsr);
				
			}
			return dgsList.size();
		}
		return 0;
	}

}
