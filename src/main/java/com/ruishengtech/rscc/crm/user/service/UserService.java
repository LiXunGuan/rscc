package com.ruishengtech.rscc.crm.user.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.user.model.Action;
import com.ruishengtech.rscc.crm.user.model.Datarange;
import com.ruishengtech.rscc.crm.user.model.User;

public interface UserService {

	public List<User> getAllUser();

	public List<User> getAllUser(Collection<String> dataranges);
	
	public List<String> getAllUser(ICondition condition);

	public int getUserCount();
	/**
	 * 通过用户名和密码验证登录
	 * @param loginName 登录用户名
	 * @param password 登录密码
	 * @return 返回用户实体
	 */
	public User login(String loginName, String password);
	
	/**
	 * 通过登录用户名获取用户实体，供管理员使用
	 * @param loginName 登录用户名
	 * @return	返回用户实体
	 */
	public User getUserByLoginName(String loginName, boolean isContainDeleted);
	
	/**
	 * 通过生成重置密码的url中的验证信息来得到用户信息
	 * @param checkurl
	 * @return
	 */
	public User getUserByCheckurl(String checkurl);

	public User getByUuid(UUID uuid);

	public PageResult<User> queryPage(ICondition condition);

	public boolean save(User u);
	
	public boolean save(User u, String[] permissions, String[] dataranges);
	
	public boolean update(User u);
	
	public boolean update(User u, String[] permissions, String[] dataranges);

	public boolean delete(UUID uuid);
	
	public boolean deleteById(UUID uuid);
	
	public boolean batDelete(String[] uuids);

	public int bindPermissions(String userUuid, List<String> permissions);

	public int bindDataranges(String userUuid, List<String> dataranges);

	public int unbindPermissions(String userUuid, List<String> permissions);

	public int unbindDataranges(String userUuid, List<String> dataranges);

	public int updatePermissions(String userUuid, List<String> permissions);

	public int updateDataranges(String userUuid, List<String> dataranges);

	public List<String> getPermissionRoles(String userUuid);
	
	public List<String> getAllPermissions(String userUuid);

	public List<String> getAllDataranges(String userUuid);

	public List<String> getActions(User user);
	
	public Map<String, Action> getActionMaps(User user);
	
	public Set<String> getRoleDataranges(String userUuid, String datarangeType);

	public List<String> getDepartments(String userUuid);
	
	public Set<String> getRoleDataranges(String userUuid);
	
	public boolean hasDatarange(String userUuid, String datarangeUuid);
	
	public boolean hasDatarange(String userUuid, String datarangeType, String datarangeUuid);

	public Collection<String> getDatarangeUsers(String datarangeUuid);	//获取所有可管辖该部门的用户

	public Collection<String> getDatarangesUsers(Collection<String> datarangeUuids);	//获取所有可管辖该部门的用户
	
	public Collection<User> getUsersByUuids(Collection<String> uuids);
	
	public Collection<String> getUsernamesByUuids(Collection<String> uuids);
	
	public Set<String> getDataranges(String userUuid);
	
	public List<User> getUserByDescribe(String describe);
	
	public List<String> getDataranges(String userUuid, String type);
	
	public boolean hasPermission(String user, String permissionUuid);

	//获取一个人可管辖的所有用户uuid
	public Collection<String> getManagerUsers(String uuid);

	Collection<String> getManagerUsernames(String uuid);
	
	public void refreshUserList();

	public JSONArray getDatarangePermissionTree(String userUuid);

	//获取一个人关联的所有数据
	public Map<String, Object> getUserDataByUserid(String userid);

	/**
	 * 根据部门ID获取下面所有的人
	 * @param deptId
	 * @return
	 */
	public Collection<String> getAllUsersByDeptId(String deptId);

	List<Datarange> getCurrentDataRange(String currentId);
}