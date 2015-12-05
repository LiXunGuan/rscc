package com.ruishengtech.rscc.crm.knowledge.service.imp;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.QueryUtil;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.knowledge.model.Knowledge;
import com.ruishengtech.rscc.crm.knowledge.model.KnowledgeDirectory;
import com.ruishengtech.rscc.crm.knowledge.service.KnowledgeDirectoryService;
import com.ruishengtech.rscc.crm.knowledge.service.KnowledgeLabelService;
import com.ruishengtech.rscc.crm.knowledge.service.KnowledgeService;
import com.ruishengtech.rscc.crm.knowledge.solution.KnowledgeDirectorySolution;

@Service
@Transactional
public class KnowledgeDirectoryServiceImp extends BaseService implements KnowledgeDirectoryService{

	@Autowired
	private KnowledgeService knowledgeService;
	
	@Autowired
	private KnowledgeLabelService knowledgeLabelService;
	
	@Override
	public void saveOrUpdate(KnowledgeDirectory knowledgeDirectory) {
		if(knowledgeDirectory == null)
			return;
		if(StringUtils.isBlank(knowledgeDirectory.getUid())){
			super.save(knowledgeDirectory);
		}else{
			knowledgeDirectory.setUuid(UUID.UUIDFromString(knowledgeDirectory.getUid()));
			super.update(knowledgeDirectory);
		}
	}

	@Override
	public PageResult<KnowledgeDirectory> queryPage(ICondition cond) {
		return super.queryPage(new KnowledgeDirectorySolution(), cond, KnowledgeDirectory.class);
	}

	@Override
	public KnowledgeDirectory getKnowledgesByDirectoryUUid(final String uuid) {
		UUID uid = UUID.UUIDFromString(uuid);
		return super.getByUuid(KnowledgeDirectory.class, uid);
	}

	@Override
	public List<KnowledgeDirectory> getAllKnowledgeDirectorys() {
		return super.getAll(KnowledgeDirectory.class);
	}

	@Override
	public JSONArray getDirectoryTree() {
		JSONArray p_json = new JSONArray();
		List<KnowledgeDirectory> directorys = super.getAll(KnowledgeDirectory.class);
		for (KnowledgeDirectory kd : directorys) {
			 JSONObject dj = new JSONObject();
			 dj.put("id", StringUtils.trimToEmpty(kd.getUuid().toString()));
			 dj.put("pId", StringUtils.trimToEmpty(kd.getDirectoryParentUUid()));
			 dj.put("name", StringUtils.trimToEmpty(kd.getDirectoryName()));
			 
			 JSONArray c_json = new JSONArray();
			 List<Knowledge> knowledgs = knowledgeService.getKnowledgesByDirectoryUUid(kd.getUid());
			 if(knowledgs != null){
				 for (Knowledge k : knowledgs) {
					 JSONObject kj = new JSONObject();
					 kj.put("id", k.getUid());
					 //标签信息
					 List<String> tagsStr = knowledgeLabelService.getTagsByKnowledge(k);
					 kj.put("tagsStr", tagsStr != null ? tagsStr.toString() : "");
					 kj.put("content", k.getKnowledgeContent());
					 kj.put("name", k.getKnowledgeTitle());
					 c_json.put(kj);
				 }
				 dj.put("children",c_json);
			 }else{
				 dj.put("isParent", true);
			 }
			
			 dj.put("open", true);
			 p_json.put(dj);
		}
		return p_json;
	}

	@Override
	public void delete(String uuid) {
		//根据目录的uuid查询知识信息
		List<Knowledge> k = knowledgeService.getKnowledgesByDirectoryUUid(uuid);
		if(k != null){
			//删除知识信息
			jdbcTemplate.update(" delete from knowledge_info WHERE directory_uuid  = ? ", uuid);
		}
		
		//查询该目录是否是父目录
		KnowledgeDirectory kd = getKnowledgesByPDirectoryUUid(uuid);
		if(kd != null){
			//修改父目录为此目录的目录信息
			jdbcTemplate.update(" update knowledge_directory set directory_parent_uuid = '' WHERE directory_parent_uuid  = ? ", uuid);
		}
		
		//删除目录
		jdbcTemplate.update(" delete from knowledge_directory WHERE uuid  = ? ", uuid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public KnowledgeDirectory getKnowledgesByDirectoryName(final String name) {
		if (StringUtils.isNotBlank(name)) {
			
			List<KnowledgeDirectory> dir = queryBean(new BeanHandler() {
				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					sql.append(" select * from knowledge_directory where directory_name = ? ");
					params.add(name);
				}
			}, KnowledgeDirectory.class);
			if (dir.size() > 0) {
				return dir.get(0);
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public KnowledgeDirectory getKnowledgesByPDirectoryUUid(final String uuid) {
		if (StringUtils.isNotBlank(uuid)) {
			
			List<KnowledgeDirectory> dir = queryBean(new BeanHandler() {
				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					sql.append(" select * from knowledge_directory where directory_parent_uuid = ? ");
					params.add(uuid);
				}
			}, KnowledgeDirectory.class);
			if (dir.size() > 0) {
				return dir.get(0);
			}
		}
		return null;
	}

	@Override
	public List<KnowledgeDirectory> getAllDirectorys() {
		return super.getAll(KnowledgeDirectory.class);
	}

	@Override
	public JSONArray getDirectorys() {
		JSONArray p_json = new JSONArray();
		List<KnowledgeDirectory> directorys = super.getAll(KnowledgeDirectory.class);
		for (KnowledgeDirectory kd : directorys) {
			 JSONObject dj = new JSONObject();
			 dj.put("id", StringUtils.trimToEmpty(kd.getUuid().toString()));
			 dj.put("pId", StringUtils.trimToEmpty(kd.getDirectoryParentUUid()));
			 dj.put("name", StringUtils.trimToEmpty(kd.getDirectoryName()));
			 dj.put("open", true);
			 p_json.put(dj);
		}
		return p_json;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<KnowledgeDirectory> getKnowledgesByDirNames(final String name) {
		if (StringUtils.isNotBlank(name)) {
			
			List<KnowledgeDirectory> dir = queryBean(new BeanHandler() {
				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					sql.append(" select * from knowledge_directory where 1=1 ");
					QueryUtil.like(sql, params, name, " AND directory_name like ? ");
				}
			}, KnowledgeDirectory.class);
			if (dir.size() > 0) {
				return dir;
			}
		}
		return null;
	}
	
}
