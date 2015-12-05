package com.ruishengtech.rscc.crm.ui.mw.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Table;
import com.ruishengtech.rscc.crm.ui.mw.db.CommonDbBean;

/**
 * 
 * @author chengxin
 *
 */
@Table(name = "mw.rt_queue_status")
public class RtQueueStatus extends CommonDbBean{

    @Column(meta = "INT")
    private Long queue_id;

    @Column(meta = "VARCHAR(100)")
    private String queue_name;

    //========================================================

    @Column(meta = "INT")
    private Long member_count=0l;

    @Column(meta = "INT")
    private Long in_count=0l;

    @Column(meta = "INT")
    private Long in_answer_count=0l;

    @Column(meta = "INT")
    private Long ringring_time=0l;

    //==============================================================

    @Column(meta = "INT")
    private Long busy_ready_count=0l;

    @Column(meta = "INT")
    private Long idle_ready_count=0l;

    @Column(meta = "INT")
    private Long not_ready_count=0l;

    @Column(meta = "INT")
    private Long offline_count=0l;

    //==================================================================================================================


    public Long getQueue_id() {
        return queue_id;
    }

    public void setQueue_id(Long queue_id) {
        this.queue_id = queue_id;
    }

    public String getQueue_name() {
        return queue_name;
    }

    public void setQueue_name(String queue_name) {
        this.queue_name = queue_name;
    }

    public Long getMember_count() {
        return member_count;
    }

    public void setMember_count(Long member_count) {
        this.member_count = member_count;
    }

    public Long getIn_count() {
        return in_count;
    }

    public void setIn_count(Long in_count) {
        this.in_count = in_count;
    }

    public Long getIn_answer_count() {
        return in_answer_count;
    }

    public void setIn_answer_count(Long in_answer_count) {
        this.in_answer_count = in_answer_count;
    }

    public Long getRingring_time() {
        return ringring_time;
    }

    public void setRingring_time(Long ringring_time) {
        this.ringring_time = ringring_time;
    }

    public Long getBusy_ready_count() {
        return busy_ready_count;
    }

    public void setBusy_ready_count(Long busy_ready_count) {
        this.busy_ready_count = busy_ready_count;
    }

    public Long getIdle_ready_count() {
        return idle_ready_count;
    }

    public void setIdle_ready_count(Long idle_ready_count) {
        this.idle_ready_count = idle_ready_count;
    }

    public Long getNot_ready_count() {
        return not_ready_count;
    }

    public void setNot_ready_count(Long not_ready_count) {
        this.not_ready_count = not_ready_count;
    }

    public Long getOffline_count() {
        return offline_count;
    }

    public void setOffline_count(Long offline_count) {
        this.offline_count = offline_count;
    }
}
