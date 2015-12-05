package com.ruishengtech.rscc.crm.ui.mw.model;

import java.util.LinkedHashMap;
import java.util.Map;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;
import com.ruishengtech.rscc.crm.ui.mw.db.CommonDbBean;

/**
 * 
 * @author chengxin
 *
 */
@Table(name = "mw.fs_queue")
public class FSQueue extends CommonDbBean{

    @Column(meta = "VARCHAR(100)")
    private String name;

    @NColumn(meta = "VARCHAR(1)")
    private String is_static;

    public String getIs_static() {
        return is_static;
    }

    public void setIs_static(String is_static) {
        this.is_static = is_static;
    }

    @Column(meta = "TEXT")
    private String config;
    
    private String extension;

    public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

    private String strategy;
    private String mohSound;
    private String welcomeMusic;
    private String timeBaseScore;
    private String maxWaitTime;
    private String maxWaitTimeWithNoAgent;
    private String maxWaitTimeWithNoAgentTimeReached;
    private String tierRulesApply;
    private String tierRuleWaitSecond;
    private String tierRuleWaitMultiplyLevel;
    private String tierRuleNoAgentNoWait;
    private String discardAbandonedAfter;
    private String abandonedResumeAllowed;
    private String agentWaitTime;
    
    /**
     *
     <queue name="support@default">
     <param name="strategy" value="longest-idle-agent"/>
     <param name="moh-sound" value="$${hold_music}"/>
     <!--<param name="record-template" value="$${base_dir}/recordings/${strftime(%Y-%m-%d-%H-%M-%S)}.${destination_number}.${caller_id_number}.${uuid}.wav"/>-->
     <param name="time-base-score" value="system"/>
     <param name="max-wait-time" value="0"/>
     <param name="max-wait-time-with-no-agent" value="0"/>
     <param name="max-wait-time-with-no-agent-time-reached" value="5"/>
     <param name="tier-rules-apply" value="false"/>
     <param name="tier-rule-wait-second" value="300"/>
     <param name="tier-rule-wait-multiply-level" value="true"/>
     <param name="tier-rule-no-agent-no-wait" value="false"/>
     <param name="discard-abandoned-after" value="60"/>
     <param name="abandoned-resume-allowed" value="false"/>
     </queue>
     </queues>
     *
     *
     */
    

	/**
	 * 选择所有坐席
	 */
	public static final String RINGALL = "ring-all";
	/**
	 * 选择空闲时间最长坐席
	 */
	public static final String LONGIDELAGENT = "longest-idle-agent";
	/**
	 * 轮循
	 */
	public static final String ROUNDROBIN = "round-robin";
	/**
	 * 固定顺序选择
	 */
	public static final String TOPDOWN = "top-down";
	/**
	 * 总是选择通话时间最短坐席
	 */
	public static final String AGENTWITHLEASTTALKTIME = "agent-with-least-talk-time";
	/**
	 * 总是选择通话次数最少坐席
	 */
	public static final String AGENTWITHFEWESYCALLS = "agent-with-fewest-calls";
	/**
	 * 根据梯队和顺序选择
	 */
	public static final String SEQUENTIALLYBYAGENTORDER = "sequentially-by-agent-order";
	/**
	 * 随机选择
	 */
	public static final String RANDOM = "random";
	
	public static Map<String, String> STRATEGYSELE = new LinkedHashMap<String, String>() {
		{
			
//			put(RINGALL, "ring-all");
//			put(LONGIDELAGENT, "longest-idle-agent");
//			put(ROUNDROBIN, "round-robin");
//			put(TOPDOWN, "top-down");
//			put(AGENTWITHLEASTTALKTIME, "agent-with-least-talk-time");
//			put(AGENTWITHFEWESYCALLS, "agent-with-fewest-calls");
//			put(SEQUENTIALLYBYAGENTORDER, "sequentially-by-agent-order");

			put(RINGALL, "共振");
			put(LONGIDELAGENT, "最长空闲");
			put(ROUNDROBIN, "轮询");
			put(TOPDOWN, "顺序");
			put(AGENTWITHLEASTTALKTIME, "最少接听次数");
			put(AGENTWITHFEWESYCALLS, "最少接听时长");
		}
		
	};
	
	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public String getMohSound() {
		return mohSound;
	}

	public void setMohSound(String mohSound) {
		this.mohSound = mohSound;
	}

	public String getWelcomeMusic() {
		return welcomeMusic;
	}

	public void setWelcomeMusic(String welcomeMusic) {
		this.welcomeMusic = welcomeMusic;
	}

	public String getMaxWaitTime() {
		return maxWaitTime;
	}

	public void setMaxWaitTime(String maxWaitTime) {
		this.maxWaitTime = maxWaitTime;
	}

	public String getMaxWaitTimeWithNoAgent() {
		return maxWaitTimeWithNoAgent;
	}

	public void setMaxWaitTimeWithNoAgent(String maxWaitTimeWithNoAgent) {
		this.maxWaitTimeWithNoAgent = maxWaitTimeWithNoAgent;
	}

	public String getMaxWaitTimeWithNoAgentTimeReached() {
		return maxWaitTimeWithNoAgentTimeReached;
	}

	public void setMaxWaitTimeWithNoAgentTimeReached(
			String maxWaitTimeWithNoAgentTimeReached) {
		this.maxWaitTimeWithNoAgentTimeReached = maxWaitTimeWithNoAgentTimeReached;
	}

	public String getTierRulesApply() {
		return tierRulesApply;
	}

	public void setTierRulesApply(String tierRulesApply) {
		this.tierRulesApply = tierRulesApply;
	}

	public String getTierRuleWaitSecond() {
		return tierRuleWaitSecond;
	}

	public void setTierRuleWaitSecond(String tierRuleWaitSecond) {
		this.tierRuleWaitSecond = tierRuleWaitSecond;
	}

	public String getTierRuleWaitMultiplyLevel() {
		return tierRuleWaitMultiplyLevel;
	}

	public void setTierRuleWaitMultiplyLevel(String tierRuleWaitMultiplyLevel) {
		this.tierRuleWaitMultiplyLevel = tierRuleWaitMultiplyLevel;
	}

	public String getTierRuleNoAgentNoWait() {
		return tierRuleNoAgentNoWait;
	}

	public void setTierRuleNoAgentNoWait(String tierRuleNoAgentNoWait) {
		this.tierRuleNoAgentNoWait = tierRuleNoAgentNoWait;
	}

	public String getDiscardAbandonedAfter() {
		return discardAbandonedAfter;
	}

	public void setDiscardAbandonedAfter(String discardAbandonedAfter) {
		this.discardAbandonedAfter = discardAbandonedAfter;
	}

	public String getAbandonedResumeAllowed() {
		return abandonedResumeAllowed;
	}

	public void setAbandonedResumeAllowed(String abandonedResumeAllowed) {
		this.abandonedResumeAllowed = abandonedResumeAllowed;
	}

	public String getTimeBaseScore() {
		return timeBaseScore;
	}

	public void setTimeBaseScore(String timeBaseScore) {
		this.timeBaseScore = timeBaseScore;
	}

    public String getAgentWaitTime() {
        return agentWaitTime;
    }

    public void setAgentWaitTime(String agentWaitTime) {
        this.agentWaitTime = agentWaitTime;
    }
}
