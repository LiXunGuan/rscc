package com.ruishengtech.rscc.crm.user.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "user_action")
public class Action extends CommonDbBean implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(meta = "VARCHAR(64)", column = "action_name")
    private String actionName;
    
    @Column(meta = "INT", column = "action_type")
    private Integer actionType;
    
    @NColumn(meta = "VARCHAR(64)", column = "action_url")
    private String actionURL;
    
    @NColumn(meta = "VARCHAR(500)", column = "action_describe")
    private String actionDescribe;

    @NColumn(meta = "VARCHAR(1000)", column = "action_css")
    private String actionCSS;
    
    @NColumn(meta = "VARCHAR(1000)", column = "action_json")
    private String actionJSON;
    
    @NColumn(meta = "VARCHAR(64)", column = "parent_uuid")
    private String parentUuid;

	@NColumn(meta = "INT", column = "action_seq")
    private Integer actionSeq;

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public Integer getActionType() {
		return actionType;
	}

	public void setActionType(Integer actionType) {
		this.actionType = actionType;
	}

	public String getActionURL() {
		return actionURL;
	}

	public void setActionURL(String actionURL) {
		this.actionURL = actionURL;
	}

	public String getActionDescribe() {
		return actionDescribe;
	}

	public void setActionDescribe(String actionDescribe) {
		this.actionDescribe = actionDescribe;
	}

	public String getActionCSS() {
		return actionCSS;
	}

	public void setActionCSS(String actionCSS) {
		this.actionCSS = actionCSS;
	}

	public String getActionJSON() {
		return actionJSON;
	}

	public void setActionJSON(String actionJSON) {
		this.actionJSON = actionJSON;
	}
	
	public String getParentUuid() {
		return parentUuid;
	}

	public void setParentUuid(String parentUuid) {
		this.parentUuid = parentUuid;
	}

	public Integer getActionSeq() {
		return actionSeq;
	}

	public void setActionSeq(Integer actionSeq) {
		this.actionSeq = actionSeq;
	}
	
	private List<Action> subMenuList = new ArrayList<Action>();

	public List<Action> getSubMenuList() {
		return subMenuList;
	}

	public void setSubMenuList(List<Action> subMenuList) {
		this.subMenuList = subMenuList;
	}
    
	public void addChild(Action menu) {
        subMenuList.add(menu);
    }
	
}
