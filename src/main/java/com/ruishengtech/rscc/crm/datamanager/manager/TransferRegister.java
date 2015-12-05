package com.ruishengtech.rscc.crm.datamanager.manager;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.ruishengtech.rscc.crm.datamanager.manager.node.Node;
import com.ruishengtech.rscc.crm.datamanager.manager.transfer.AbstractTransfer;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.TransferData;

/**
 * Created by yaoliceng on 2015/8/18.
 */
public class TransferRegister {

    private Class<? extends Node> from;

    private Class<? extends Node> to;
    
    private Class<? extends TransferData> data;

    private AbstractTransfer abstractTransfer;

    public TransferRegister(AbstractTransfer abstractTransfer) {
        this.abstractTransfer = abstractTransfer;

        Type superclassType = abstractTransfer.getClass().getSuperclass().getGenericSuperclass();
        Type[] ret = ((ParameterizedType) superclassType).getActualTypeArguments();

        from = (Class) ret[0];
        to = (Class) ret[1];
        data = (Class) ret[2];
    }

    public Class<? extends Node> getFrom() {
        return from;
    }

    public Class<? extends Node> getTo() {
        return to;
    }

    public Class<? extends TransferData> getData() {
		return data;
	}

	public void setData(Class<? extends TransferData> data) {
		this.data = data;
	}

	public AbstractTransfer getAbstractTransfer() {
        return abstractTransfer;
    }
}
