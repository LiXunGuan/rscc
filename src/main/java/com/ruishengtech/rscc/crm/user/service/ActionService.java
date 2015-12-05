package com.ruishengtech.rscc.crm.user.service;

import java.util.List;

import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.rscc.crm.user.model.Action;

public interface ActionService {

	public List<Action> getAllAction();

	public List<Action> getAllMenu();

	public Action getByUuid(UUID uuid);

	public List<Action> getByList(List<UUID> l);

	public Action getActionByName(String actionName);

	public List<Action> getChildren(String actionUuid);

	public List<Action> getChildren(UUID actionUuid);

	public Action getMenuTree(List<String> menus);

	public Action getParent(Action action);

}