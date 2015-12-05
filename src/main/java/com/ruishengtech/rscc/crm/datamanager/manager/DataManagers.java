package com.ruishengtech.rscc.crm.datamanager.manager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ruishengtech.rscc.crm.datamanager.manager.node.Node;
import com.ruishengtech.rscc.crm.datamanager.manager.transfer.AbstractTransfer;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.TransferData;

/**
 * Created by yaoliceng on 2015/8/18.
 */
@Service
public class DataManagers {

	List<TransferRegister> transferRegisters =new ArrayList<TransferRegister>();

    public void add(TransferRegister transferRegister) {
    	this.transferRegisters.add(transferRegister);
    }
    
    public TransferResult transfer(Node fromNode, Node toNode, TransferData transferData) {
        return dotransfer(fromNode, toNode, transferData);
    }

    private TransferResult dotransfer(Node fromNode, Node toNode, TransferData transferData) {

    	for (TransferRegister transferRegister : transferRegisters) {
            if(transferRegister.getFrom().equals(fromNode.getClass()) && transferRegister.getTo().equals(toNode.getClass()) && transferRegister.getData().equals(transferData.getClass())){
                AbstractTransfer tr = transferRegister.getAbstractTransfer();
                return tr.transfer(fromNode,toNode,transferData);
            }
        }
		return null;
    }



}
