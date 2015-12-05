package com.ruishengtech.rscc.crm.user.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.user.model.Datarange;
import com.ruishengtech.rscc.crm.user.model.User;

public interface DatarangeService {
	
	public List<Datarange> getAllDatarange();
	
	public List<String> getAllDatarangeUuid();
	
	public List<String> getAllDatarange(ICondition condition);
	
	/**
	 * 通过数据范围名获取数据范围实体
	 * @param datarangeName
	 * @return
	 */
	public Datarange getDatarangeByName(String datarangeName);

	public List<User> getUsers(String datarange);
	
	public List<String> getUserUuids(String datarange);
	
	public Collection<String> getUserUuidsByDataranges(Collection<String> dataranges);
	
	public boolean save(Datarange d);

	public void save(Datarange d, String[] excludeFieldName);

	public boolean update(Datarange d);

	public void update(Datarange d, String[] excludeFieldName);

	public boolean deleteById(UUID uuid);
	
	public void deleteChildren(UUID uuid);

	public Datarange getByUuid(UUID uuid);

	public PageResult<Datarange> queryPage(ICondition condition);

	/**
	 * 获取子数据范围实体list
	 * @param datarangeUuid
	 * @return
	 */
	public List<Datarange> getChildren(String datarangeUuid);

	//获取所有的子范围
	public Set<String> getAllChildren(String datarangeUuid);

	/**
	 * 获取子数据范围实体list
	 * @param datarangeUuid
	 * @return
	 */
	public List<Datarange> getChildren(UUID datarangeUuid);

	public Datarange getParent(Datarange datarange);
	
	public JSONArray getDatarangeTree();
	
	public JSONArray getChildrenDatarangeTree(String datarangeUuid);
	
	public JSONArray getDatarangeRootTree();

	public List<Datarange> getByList(List<UUID> l);
	
	public List<Datarange> getByType(UUID typeUuid);
	
	public Map<String, Datarange> getLeafNodes();

	public boolean batDelete(String[] uuids);

	public List<Datarange> getDataranges(Collection<String> list);

	public List<User> getUsersByDatarange(String datarange);
	
	/**
	 * 得到当前部门的父部门的UUID
	 * @param uuid
	 * @return
	 */
	public Set<String> getParentUuid(String datarangeUuid);
	
	public String getOpenFlag(String batchUuid, String deptUuid);
	
}