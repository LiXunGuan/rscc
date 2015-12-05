package com.ruishengtech.rscc.crm.ui.mw.manager;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.ruishengtech.rscc.crm.data.model.DataTask;

public class CallHistoryManager {
	
	//可以直接使用同步Map和Queue类，也可以使用Collections.sychronize包装普通map和list，因为没有包装LinkedList的方法，
	//可以包装list，通过list的取index元素方法实现LinkedList，因为有多态，所以最终还是调用的LinkedList中的方法，并不影响使用。
	public static ConcurrentHashMap<String, ConcurrentLinkedQueue<DataTask>> CallHistory = new ConcurrentHashMap<String, ConcurrentLinkedQueue<DataTask>>();
	
	//历史记录数量
	public static int historySize = 5;
	//添加一条数据
	public static void put(String agentName, DataTask dataTask) {
		ConcurrentLinkedQueue<DataTask> callHistoryList = CallHistory.get(agentName);
		if (callHistoryList == null) {
			CallHistory.put(agentName, new ConcurrentLinkedQueue<DataTask>());
			CallHistory.get(agentName).offer(dataTask);
		} else {
			callHistoryList.remove(dataTask);//移除重复项
			callHistoryList.offer(dataTask);
			if (callHistoryList.size() == historySize + 1) {
				callHistoryList.poll();
			}
		}
	}
	
	public static DataTask get(String agentName, String taskUuid) {
		ConcurrentLinkedQueue<DataTask> callHistoryList = CallHistory.get(agentName);
		if (callHistoryList == null) {
			return null;
		} else {
			Iterator<DataTask> i = callHistoryList.iterator();
			while (i.hasNext()) {
				DataTask d = i.next();
				if(d.getUid().equals(taskUuid))
					return d;
			}
			return null;
		}
	}
	
	public static ConcurrentHashMap<String, ConcurrentLinkedQueue<DataTask>> getCallHistory() {
		return CallHistory;
	}
	
	public static ConcurrentLinkedQueue<DataTask> getCallHistoryList(String agentName) {
		return CallHistory.get(agentName);
	}
	
}
