package com.ruishengtech.rscc.crm.issue.service.imp;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.issue.condition.CstmIssueCondition;
import com.ruishengtech.rscc.crm.issue.model.CstmIssue;
import com.ruishengtech.rscc.crm.issue.model.CstmIssueComments;
import com.ruishengtech.rscc.crm.issue.service.CstmIssueService;
import com.ruishengtech.rscc.crm.issue.solution.CstmIssueSolution;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.user.model.User;

/**
 * @author Frank
 *
 */
@Service
@Transactional
public class CstmIssueServiceImp extends BaseService implements CstmIssueService {
	
	/**
	 * 分页查询数据
	 * @param cstmIssueCondition
	 * @return
	 */
	public PageResult<CstmIssue> queryPage(CstmIssueCondition cstmIssueCondition) {
		
		return super.queryPage(new CstmIssueSolution(), cstmIssueCondition, CstmIssue.class);
		
	}

	/* (non-Javadoc)
	 * @see com.ruishengtech.rscc.crm.cstmservice.service.CstmserviceService#getCstmserviceByUUID(com.ruishengtech.framework.core.db.UUID)
	 */
	@Override
	public CstmIssue getCstmserviceByUUID(UUID uuid) {

		return super.getByUuid(CstmIssue.class, uuid);
	}

	/* (non-Javadoc)
	 * @see com.ruishengtech.rscc.crm.cstmservice.service.CstmserviceService#getCstmserviceComments(com.ruishengtech.framework.core.db.UUID)
	 */
	@Override
	public List<CstmIssueComments> getCstmserviceComments(final UUID uuid) {

		return super.getBeanList(CstmIssueComments.class, " AND cstmservice_uuid =  ? ", uuid.getUuid());
		
	}
	
	/**
	 * 修改或者是保存对象
	 * @param cstmIssue
	 */
	public void saveOrUpdate(CstmIssue cstmIssue){
		
		if(StringUtils.isNoneBlank(cstmIssue.getUid())){
			cstmIssue.setUuid(UUID.UUIDFromString(cstmIssue.getUid()));
			/*修改*/
			update(cstmIssue);
		}else{
			/*保存*/
			save(cstmIssue);
		}
	}
	
	/**
	 * 修改或者是保存对象
	 * @param cstmIssue
	 */
	public void saveOrUpdate(CstmIssue cstmIssue,String[] str){
		
		if(StringUtils.isNoneBlank(cstmIssue.getUid())){
			/*修改*/
			update(cstmIssue,str);
		}else{
			/*保存*/
			save(cstmIssue,str);
		}
	}
	
	
	/**
	 * 根据callSessionUuid查询单个对象
	 * @param CallsessionUid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public CstmIssue getBySessionUuid(final String CallsessionUuid){
		
		
		if(StringUtils.isNotBlank(CallsessionUuid)){
			
			List<CstmIssue> cstmIssues = queryBean(new BeanHandler() {
				
				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					
					sql.append(" SELECT * FROM cstm_cstmservice WHERE call_session_uuid = ? ");
					params.add(CallsessionUuid);
				}
			}, CstmIssue.class);
			
			if(cstmIssues.size() >0 ){
				return cstmIssues.get(0);
			}
		}
		
		return null;
	}

	@Override
	public void getExcelCreated(HttpServletRequest request,final HttpServletResponse response, CstmIssueCondition cicondition,CstmIssueSolution issueSolution) throws Exception {
		
		response.setContentType("application/x-download");
        response.addHeader("Content-Disposition", "attachment;filename="+new String( "工单信息".getBytes("utf-8"), "ISO8859-1" )+".xls");
        StringBuilder sql=new StringBuilder();
        sql.append("SELECT uuid,cstmservice_starttime,cstmservice_id,cstmservice_name,cstmservice_description,cstmservice_reporter,user_name,cstmservice_status ");
        List<Object> params = new ArrayList<Object>();
        issueSolution.getWhere(cicondition, sql, params);
        
//		final StringBuilder esql=new StringBuilder();
//		esql.append(getSql(cicondition,sql,request));
		jdbcTemplate.query(sql.toString(), params.toArray(),new ResultSetExtractor<T>() {
			public T extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				try {
					List<String> titles = new ArrayList<>();
					titles.add("工单编号");
					titles.add("工单名称");
					titles.add("工单描述");
					titles.add("发起对象");
					titles.add("工单负责人");
					titles.add("工单状态");
					
					HSSFWorkbook workbook = new HSSFWorkbook();
					HSSFSheet sheet = workbook.createSheet();
					sheet.setDefaultColumnWidth(20);
					HSSFRow row = sheet.createRow(0);
					HSSFCellStyle cellStyle = workbook.createCellStyle();
					cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					for (int i = 0; i < titles.size(); i++) {
						cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
						HSSFCell cell = row.createCell(i);
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(titles.get(i));
					}
					int rowNum = 1;
					while (rs.next()) {

						cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
						createExcel(retRecord(rs, sdf), sheet, rowNum,titles.size());
						rowNum++;
					}
					
					workbook.write(response.getOutputStream());
					response.getOutputStream().flush();
					response.flushBuffer();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						response.getOutputStream().close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return null;
			}

		});
	}
	
	/**
	 * 添加查询条件
	 * @param billCondition
	 * @param sql
	 * @param request
	 * @return
	 */
	public StringBuilder getSql(CstmIssueCondition condition,
			StringBuilder sql, HttpServletRequest request) throws Exception{
		
		User loginuser = SessionUtil.getCurrentUser(request);
		if(StringUtils.isNotBlank(condition.getCstmserviceId())){
			sql.append(" AND  cstmservice_id = '"+condition.getCstmserviceId()+"' ");
		}
		if(StringUtils.isNotBlank(condition.getCstmserviceName())){
			sql.append(" AND  cstmservice_name = '"+condition.getCstmserviceName()+"' ");
		}
		if(StringUtils.isNotBlank(condition.getCstmserviceDescription())){
			sql.append(" AND  cstmservice_description = '"+condition.getCstmserviceDescription()+"' ");
		}else if(!"0".equals(loginuser.getUid())){
			sql.append(" AND cstmservice_reporter = '"+loginuser.getLoginName()+"' ");
		}
		if(StringUtils.isNotBlank(condition.getCstmserviceReporter())){
			sql.append(" AND  cstmservice_reporter = '"+condition.getCstmserviceReporter()+"' ");
		}
		if(StringUtils.isNotBlank(condition.getUserName())){
			sql.append(" AND  user_name = '"+condition.getUserName()+"' ");
		}
		if(StringUtils.isNotBlank(condition.getCstmserviceStatus())){
			sql.append(" AND  cstmservice_status = '"+condition.getCstmserviceStatus()+"' ");
		}
			
		return sql.append(" ORDER BY cstmservice_starttime DESC ");
	}
	
	/**
	 * 给Excel列赋值
	 */
	public void createExcel(CstmIssue cstmi, HSSFSheet sheet, int maxRow,int titleNumber) {

		/* 创建的行数 */
		HSSFRow ro = sheet.createRow(maxRow);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		for (int i = 0; i < titleNumber; i++) {

			HSSFCell cell = ro.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			if(StringUtils.isNotBlank(cstmi.getCstmserviceStatus())){
				if(cstmi.getCstmserviceStatus().equals("0")){
					cstmi.setCstmserviceStatus("未解决");
				}else if(cstmi.getCstmserviceStatus().equals("1")){
					cstmi.setCstmserviceStatus("已解决");
				}
			}
			
			switch (i) {
				case 0:
					cell.setCellValue(cstmi.getCstmserviceId());
					break;
				case 1:
					cell.setCellValue(cstmi.getCstmserviceName());
					break;
				case 2:
					cell.setCellValue(cstmi.getCstmserviceDescription());
					break;
				case 3:
					cell.setCellValue(cstmi.getCstmserviceReporter());
					break;
				case 4:
					cell.setCellValue(cstmi.getUserName());
					break;
				case 5:
					cell.setCellValue(cstmi.getCstmserviceStatus());
					break;
				default:
					break;
				}
		}

	}
	
	/**
	 * 将查询到的数据封装到对象
	 * 
	 * @param rs
	 * @param sdf
	 * @return
	 * @throws Exception
	 */
	private CstmIssue retRecord(ResultSet rs, SimpleDateFormat sdf)
			throws Exception {

		// 创建对象
		CstmIssue cstmi = new CstmIssue();
		
		cstmi.setCstmserviceId(rs.getString("cstmservice_id"));
		cstmi.setCstmserviceName(rs.getString("cstmservice_name"));
		cstmi.setCstmserviceDescription(rs.getString("cstmservice_description"));
		cstmi.setCstmserviceReporter(rs.getString("cstmservice_reporter"));
		cstmi.setUserName(rs.getString("user_name"));
		cstmi.setCstmserviceStatus(rs.getString("cstmservice_status"));
		
		return cstmi;
	}
}
