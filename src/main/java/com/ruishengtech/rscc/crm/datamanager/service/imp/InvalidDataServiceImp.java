package com.ruishengtech.rscc.crm.datamanager.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.datamanager.manager.DataManagers;
import com.ruishengtech.rscc.crm.datamanager.manager.node.AbandonNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.BatchNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.BlacklistNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.FrozenNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.Node;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.AbandonToBatchData;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.BlackListToBatchData;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.FrozenToBatchData;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.TransferData;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatchData;
import com.ruishengtech.rscc.crm.datamanager.model.PhoneResource;
import com.ruishengtech.rscc.crm.datamanager.service.DataBatchDataService;
import com.ruishengtech.rscc.crm.datamanager.solution.InvalidDataSolution;

@Service
@Transactional
public class InvalidDataServiceImp extends BaseService{
	
	@Autowired
	private DataBatchDataService dataBatchDataService;
	
	@Autowired
	private DataManagers dataManager;
	
	public PageResult<PhoneResource> queryPage(ICondition condition) {
		return super.queryPage(new InvalidDataSolution(), condition, PhoneResource.class);
	}
	
	public boolean recover(String phonenumber,String batchUuid){
		DataBatchData dataBatchData = dataBatchDataService.getByPhone(batchUuid, phonenumber);
		Node fromNode = null;
		BatchNode toNode = new BatchNode(dataBatchData.getBatchUuid());
		TransferData transferData = null;
		if ("1".equals(dataBatchData.getIsAbandon())) {
			fromNode = new AbandonNode();
			transferData = new AbandonToBatchData();
			((AbandonToBatchData)transferData).setDataBatchData(dataBatchData);
		}
		if ("1".equals(dataBatchData.getIsFrozen())) {
			fromNode = new FrozenNode();
			transferData = new FrozenToBatchData();
			((FrozenToBatchData)transferData).setDataBatchData(dataBatchData);
		}
		if ("1".equals(dataBatchData.getIsBlacklist())) {
			fromNode = new BlacklistNode();
			transferData = new BlackListToBatchData();
			((BlackListToBatchData)transferData).setDataBatchData(dataBatchData);
		}
		dataManager.transfer(fromNode, toNode, transferData);
		return true;
	}
}
