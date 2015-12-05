package com.ruishengtech.rscc.crm.user.service;

import java.util.List;

import org.json.JSONArray;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.user.model.Datarange;
import com.ruishengtech.rscc.crm.user.model.DatarangeRole;

public interface DatarangeRoleService {
	
	public List<DatarangeRole> getAllDatarangeRole();
	
	/**
	 * 通过角色名获取数据范围角色
	 * @param datarangeRoleName
	 * @return
	 */
	public DatarangeRole getDatarangeRoleByName(String datarangeRoleName);

	public boolean save(DatarangeRole d);

	public boolean save(DatarangeRole d, String[] dataranges);

	public boolean update(DatarangeRole d);

	public boolean update(DatarangeRole d, String[] dataranges);

	public boolean deleteById(UUID uuid);
	
	public void deleteChildren(UUID uuid);

	public DatarangeRole getByUuid(UUID uuid);

	public PageResult<DatarangeRole> queryPage(ICondition condition);

	/**
	 * 为数据范围角色绑定一个数据范围
	 * @param datarangeRoleUuid
	 * @param datarangeUuid
	 * @return
	 */
	public boolean bindDatarange(String datarangeRoleUuid, String datarangeUuid);

	/**
	 * 为数据范围角色绑定一个数据范围
	 * @param datarangeRoleUuid
	 * @param datarangeUuid
	 * @return
	 */
	public boolean bindDatarange(UUID datarangeRoleUuid, UUID datarangeUuid);

	/**
	 * 为数据范围角色绑定多个数据范围
	 * @param datarangeRoleUuid
	 * @param dataranges
	 * @return
	 */
	public int bindDataranges(String datarangeRoleUuid, List<String> dataranges);

	/**
	 * 为数据范围角色绑定多个数据范围
	 * @param datarangeRoleUuid
	 * @param dataranges
	 * @return
	 */
	public int bindDataranges(UUID datarangeRoleUuid, List<UUID> dataranges);

	/**
	 * 为数据范围角色解绑一个数据范围
	 * @param datarangeRoleUuid
	 * @param datarangeUuid
	 * @return
	 */
	public boolean unbindDatarange(String datarangeRoleUuid,
                                   String datarangeUuid);

	/**
	 * 为数据范围角色解绑一个数据范围
	 * @param datarangeRoleUuid
	 * @param datarangeUuid
	 * @return
	 */
	public boolean unbindDatarange(UUID datarangeRoleUuid, UUID datarangeUuid);

	/**
	 * 为数据范围角色解绑多个数据范围
	 * @param datarangeRoleUuid
	 * @param dataranges
	 * @return
	 */
	public int unbindDataranges(String datarangeRoleUuid,
                                List<String> dataranges);

	/**
	 * 为数据范围角色解绑多个数据范围
	 * @param datarangeRoleUuid
	 * @param dataranges
	 * @return
	 */
	public int unbindDataranges(UUID datarangeRoleUuid, List<UUID> dataranges);

	/**
	 * 为数据范围角色更新所有数据范围
	 * @param datarangeRoleUuid
	 * @param dataranges 更新后的所有数据范围
	 * @return
	 */
	public int updateDataranges(String datarangeRoleUuid,
                                List<String> dataranges);

	/**
	 * 为数据范围角色更新所有数据范围
	 * @param datarangeRoleUuid
	 * @param dataranges 更新后的所有数据范围
	 * @return
	 */
	public int updateDataranges(UUID datarangeRoleUuid, List<UUID> dataranges);

	/**
	 * 获取所有可操作数据范围UUID list
	 * @param datarangeRoleUuid
	 * @return
	 */
	public List<String> getDataranges(String datarangeRoleUuid);

	/**
	 * 获取所有可操作数据范围UUID list
	 * @param datarangeRoleUuid
	 * @return
	 */
	public List<UUID> getDataranges(UUID datarangeRoleUuid);

	/**
	 * 获取所有可操作数据范围实体list
	 * @param datarangeRole
	 * @return
	 */
	public List<Datarange> getDataranges(DatarangeRole datarangeRole);
	
	/**
	 * 获取所有子数据范围实体list
	 * @param datarangeRoleUuid
	 * @return
	 */
	public List<DatarangeRole> getChildren(String datarangeRoleUuid);
	
	/**
	 * 获取所有子数据范围实体list
	 * @param datarangeRoleUuid
	 * @return
	 */
	public List<DatarangeRole> getChildren(UUID datarangeRoleUuid);

	public DatarangeRole getParent(DatarangeRole datarangeRole);
	
	public JSONArray getDatarangeRoleTree();
}