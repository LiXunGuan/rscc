package com.ruishengtech.rscc.crm.datamanager.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatchDepartmentLink;

@Service
@Transactional
public class DataBatchDepartmentLinkServiceImp extends BaseService{
	
	public DataBatchDepartmentLink getByLink(String dataBatchUuid, String departmentUuid) {
		//判断传过来的id是批次id还是数据id  如果能取到值就是批次id否则就是数据id
//		List<DataBatchDepartmentLink> ddl = getByBatchUuid(dataBatchUuid);
		List<DataBatchDepartmentLink> list = new ArrayList<>();
//		if(ddl.size()!=0){
			list = getBeanListWithOrder(DataBatchDepartmentLink.class, "and data_batch_uuid=? and department_uuid=? ", "data_batch_uuid", dataBatchUuid, departmentUuid);
//		}else{
//			list = getBeanListWithOrder(DataBatchDepartmentLink.class, "and department_uuid=? ", "department_uuid",departmentUuid);
//		}
		if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}
	
	public List<DataBatchDepartmentLink> getAllLink(){
		return getBeanList(DataBatchDepartmentLink.class, "");
	}
	
	public List<DataBatchDepartmentLink> getByBatchUuid(String batchUuid){
		return getBeanListWithOrder(DataBatchDepartmentLink.class, " and data_batch_uuid = ? ", "data_batch_uuid", batchUuid);
	}
	
	public List<DataBatchDepartmentLink> getByDepartmentUuid(String departmentUuid){
		return getBeanListWithSql(DataBatchDepartmentLink.class, " select b.data_batch_uuid,b.data_count,b.own_count,d.batch_name batchname "
				+ "from new_data_batch_department_link b join new_data_batch d on b.data_batch_uuid=d.uuid where b.department_uuid = ? ", departmentUuid);
	}
	
	public List<DataBatchDepartmentLink> getByDepartmentWithGet(String departmentUuid){
		return getBeanListWithSql(DataBatchDepartmentLink.class, " select b.*,d.batch_name batchname "
				+ "from new_data_batch_department_link b join new_data_batch d on b.data_batch_uuid=d.uuid where b.department_uuid = ? and open_flag='1' and b.is_lock='0' ", departmentUuid);
	}
	
	public void save(DataBatchDepartmentLink d) {
		String insertSql = "insert into " + DataBatchDepartmentLink.tableName + 
				" (data_batch_uuid,department_uuid,day_limit,single_limit) values(?,?,?,?)";
		jdbcTemplate.update(insertSql, d.getDataBatchUuid(), d.getDepartmentUuid(), d.getDayLimit(), d.getSingleLimit());
	}
	
	public void update(DataBatchDepartmentLink d) {
		String updateSql = "update " + DataBatchDepartmentLink.tableName + 
				" set day_limit=?,single_limit=? where data_batch_uuid=? and department_uuid=?";
		jdbcTemplate.update(updateSql, d.getDayLimit(), d.getSingleLimit(), d.getDataBatchUuid(), d.getDepartmentUuid());
	}
	
	public void changeStat(DataBatchDepartmentLink d, boolean flag) {
		String updateSql = "update " + DataBatchDepartmentLink.tableName + 
				" set open_flag=? where data_batch_uuid=? and department_uuid=?";
		jdbcTemplate.update(updateSql, flag?'1':'0');
	}
	
}
