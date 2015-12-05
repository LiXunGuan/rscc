package com.ruishengtech.rscc.crm.user.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.user.model.Action;
import com.ruishengtech.rscc.crm.user.service.ActionService;
@Service
@Transactional
public class ActionServiceImp extends BaseService implements ActionService {
	
	public List<Action> getAllAction() {
		return getBeanList(Action.class,"");
	}
	
	public List<Action> getAllMenu() {
		return getBeanListWithOrder(Action.class, "and action_type=0", "parent_uuid asc, action_seq asc");
	}
	
	public Action getByUuid(UUID uuid) {
		return super.getByUuid(Action.class, uuid);
	}
	
	public List<Action> getByList(List<UUID> l) {
		ArrayList<Action> list = new ArrayList<Action>();
		for(UUID u:l)
			list.add(getByUuid(u));
		return list;
	}
	
	public Action getActionByName(String actionName) {
		
		List<Action> list = getBeanList(Action.class, "and action_name = ?", actionName);

        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}
	
	public List<Action> getChildren(String actionUuid){	//获取权限
		return getBeanList(Action.class, "and action_type = 0 and parent_uuid = ?", actionUuid);
    }
	
	public List<Action> getChildren(UUID actionUuid){	//获取权限
		return getChildren(actionUuid.toString());
    }
	
	public Action getMenuTree(List<String> menus) {
		HashSet<String> tempSet = new HashSet<>(menus);
		Map<String, Action> map = new HashMap<String, Action>();
		List<Action> allMenus = getAllMenu();
		Action root = new Action();
		map.put("0", root);
		for (Action menu : allMenus) {
			if (tempSet.contains(menu.getUuid().toString())) {
				tempSet.add(menu.getParentUuid());
			}
		}
		for (Action menu : allMenus) {
			menu.setSubMenuList(new ArrayList<Action>());
			map.put(menu.getUuid().toString(), menu);
			if (tempSet.contains(menu.getUuid().toString())) {
				map.get(menu.getParentUuid()).addChild(menu);
			}
		}
		return root;
	}
	
	public Action getParent(Action action) {
		return getByUuid(UUID.UUIDFromString(action.getParentUuid()));
	}
}
