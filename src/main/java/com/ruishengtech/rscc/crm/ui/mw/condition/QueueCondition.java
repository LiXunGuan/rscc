package com.ruishengtech.rscc.crm.ui.mw.condition;

import com.ruishengtech.framework.core.db.condition.Page;

/**
 * Created by yaoliceng on 2014/11/3.
 */
public class QueueCondition extends Page{

    private Long queue_id;
    
    public Long getQueue_id() {
        return queue_id;
    }

    public void setQueue_id(Long queue_id) {
        this.queue_id = queue_id;
    }

    private Integer QueueStrat;
	private Integer QueueEnd;
	private String name;
	private String staticqueue;
	
	public Integer getQueueStrat() {
		return QueueStrat;
	}
	public void setQueueStrat(Integer queueStrat) {
		QueueStrat = queueStrat;
	}
	public Integer getQueueEnd() {
		return QueueEnd;
	}
	public void setQueueEnd(Integer queueEnd) {
		QueueEnd = queueEnd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStaticqueue() {
		return staticqueue;
	}
	public void setStaticqueue(String staticqueue) {
		this.staticqueue = staticqueue;
	}
}
