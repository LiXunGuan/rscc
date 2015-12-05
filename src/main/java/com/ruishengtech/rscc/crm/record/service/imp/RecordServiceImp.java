package com.ruishengtech.rscc.crm.record.service.imp;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.QueryUtil;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.qc.model.QualityCheck;
import com.ruishengtech.rscc.crm.qc.service.QualityCheckService;
import com.ruishengtech.rscc.crm.record.condition.RecordCondition;
import com.ruishengtech.rscc.crm.record.model.AccessNumber;
import com.ruishengtech.rscc.crm.record.model.Record;
import com.ruishengtech.rscc.crm.record.service.RecordService;
import com.ruishengtech.rscc.crm.record.solution.RecordSolution;

/**
 * @author Frank
 *
 */
@Service
@Transactional
public class RecordServiceImp extends BaseService implements RecordService {

	@Autowired
	private QualityCheckService qualityCheckService;
	
	private DataSourceTransactionManager dataSourceTransactionManager;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * RecordService#queryPage(com.ruishengtech
	 * .crm.record.condition.RecordCondition)
	 */
	@Override
	public PageResult<Record> queryPage(RecordCondition condition) {

		return super.queryPage(new RecordSolution(), condition, Record.class);

	}

	/* (non-Javadoc)
	 * @see RecordService#getRecordsList(RecordCondition)
	 */
	@Override
	public List<Record> getRecordsList(RecordCondition condition) {
		
		if(null != condition){
			
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see RecordService#getRecordByUUID(com.ruishengtech.framework.core.db.UUID)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Record getRecordByCallsessionuuid(final String uuid) {
//		if(StringUtils.isNoneBlank(uid)){
//			return super.getByUuid(Record.class, UUID.UUIDFromString(uid));
//		}
//		return null;
		
		List<Record> re = queryBean(new BeanHandler() {
            @Override
            public void doSql(StringBuilder sql, List<Object> params) {
                sql.append(" SELECT * from "+ SpringPropertiesUtil.getProperty("sys.record.database") +"cdr where call_session_uuid = ? ");
                params.add(uuid);
            }
        }, Record.class);
        if (re.size() > 0) {
            return re.get(0);
        }
        return null;
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<AccessNumber> getAllAccessNumbers() {
		List<AccessNumber> accessNumberls = queryBean(new BeanHandler() {

            @Override
            public void doSql(StringBuilder sql, List<Object> params) {
                sql.append(" SELECT * from "+ SpringPropertiesUtil.getProperty("sys.record.database") +"fs_accessnumber");
            }
        }, AccessNumber.class);
        if (accessNumberls.size() > 0) {
            return accessNumberls;
        }
        return null;
	}

	@Override
	public void getExcelCreated(HttpServletRequest request, final HttpServletResponse response, RecordCondition recordCondition) {
		RecordCondition cond = (RecordCondition) recordCondition;
    	List<Object> params = new ArrayList<Object>();
    	
    	StringBuilder sql=new StringBuilder();
		sql.append(" SELECT c.*,q.score ");
		sql.append(" from "+ recordCondition.getDatabase() +"cdr c LEFT JOIN qualitycheck q ON c.call_session_uuid = q.uuid_obj ");
		
		sql.append(" WHERE 1 = 1 and c.type = 'master' ");
		
//		//删选条件
//		if("0".equals(recordCondition.getAdminflag())){
//			QueryUtil.queryData(sql, params, recordCondition.getUsername(), " and (c.caller_agent_id = ? ");
//			QueryUtil.queryData(sql, params, recordCondition.getUsername(), " or c.dest_agent_id = ?) ");
//		}
		
		if("agent".equals(recordCondition.getLevel())){
			QueryUtil.queryData(sql, params, recordCondition.getUsername(), " and (c.caller_agent_id = ? ");
			QueryUtil.queryData(sql, params, recordCondition.getUsername(), " or c.dest_agent_id = ? ) ");
		}else{
			if(!"1".equals(recordCondition.getAdminflag())){
				Collection<String> queuesList = recordCondition.getQueuesList();
				if(queuesList.size() != 0) {
					QueryUtils.in(sql, params, queuesList, " and (c.caller_agent_id ");
					QueryUtils.in(sql, params, queuesList, " or c.dest_agent_id ");
					sql.append(")");
				}
			}
		}
        //分数
		QueryUtils.number(sql, params, cond.getScore1(), cond.getScore2(), " AND q.score ");
		QueryUtil.datetime(sql, params, cond.getStime(), " and c.start_stamp >= ? ");
		QueryUtil.datetime(sql, params, cond.getEtime(), " and c.start_stamp <= ? ");
//		QueryUtil.datetime(sql, params, cond.getStime(), " and c.end_stamp >= ? ");
//      QueryUtil.datetime(sql, params, cond.getEtime(), " and c.end_stamp <= ? ");
        QueryUtil.like(sql, params, cond.getAccess_number(), " and c.access_number like ? ");
        QueryUtil.like(sql, params, cond.getCaller_id_number(), " and c.caller_id_number like ? ");
        QueryUtil.like(sql, params, recordCondition.getDest_agent_interface_exten(), " and c.dest_number like ? ");
        
        QueryUtil.like(sql, params, recordCondition.getAgentinfo(), " and ( c.caller_agent_id like ? ");
        QueryUtil.like(sql, params, recordCondition.getAgentinfo(), " or c.dest_agent_id like ? ) ");
        
        //通话时长/秒
        QueryUtil.queryData(sql, params, recordCondition.getSduration(), " and c.billsec >= ? ");
        QueryUtil.queryData(sql, params, recordCondition.getEduration(), " and c.billsec <= ? ");
        QueryUtil.queryData(sql, params, recordCondition.getSbillsec(), " and c.bridgesec >= ? ");
        QueryUtil.queryData(sql, params, recordCondition.getEbillsec(), " and c.bridgesec <= ? ");
 
        QueryUtil.queryData(sql, params, cond.getCalldirection(), " and c.call_direction = ? ");
        if(StringUtils.isNotBlank(cond.getBridgesec())){
	      	if("y".equals(cond.getBridgesec())){
	      		sql.append(" and c.bridgesec > 0 ");
	      	}else if("n".equals(cond.getBridgesec())){
	      		sql.append(" and c.bridgesec = 0 ");
	      	}
        }
        
        
        jdbcTemplate.query(sql.toString(), params.toArray(),new ResultSetExtractor<Object>() {

			@Override
			public Object extractData(ResultSet rs)
					throws SQLException, DataAccessException {

				List<String> titles = new ArrayList<String>();
				titles.add("时间");
				titles.add("主叫号码");
				titles.add("主叫坐席ID");
				titles.add("主叫坐席信息");
				titles.add("被叫号码");
				titles.add("被叫坐席ID");
				titles.add("被叫坐席信息");
				titles.add("呼叫方向");
				titles.add("是否接通");
				titles.add("通话时长（秒）");
				titles.add("接通时长（秒）");
				titles.add("接入号");

				try {
					HSSFWorkbook workbook = new HSSFWorkbook();
					HSSFSheet sheet = workbook.createSheet();
					sheet.setColumnWidth(0, (short) 14000);
					sheet.setDefaultColumnWidth(20);

					HSSFRow row = sheet.createRow(0);

					HSSFCellStyle cellStyle = workbook
							.createCellStyle();
					cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					for (int i = 0; i < titles.size(); i++) {
						cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
						HSSFCell cell = row.createCell(i);
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(titles.get(i));
					}
					
					int rowNum = 1;
					while (rs.next()) {
						cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
						createExcel(retRecord(rs, sdf), sheet, rowNum, titles.size());
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
	
	private Record retRecord(ResultSet rs, SimpleDateFormat sdf)
			throws Exception {
		// 创建对象
		Record cdr = new Record();
		try {
			
			// 时间
			cdr.setStart_stamp(sdf.parse(rs.getString("start_stamp")));
			
			// 主叫号码
			cdr.setCaller_id_number(rs.getString("caller_id_number"));
			
			//之前的逻辑
			//cdr.setCaller_agent_interface_name(rs.getString("caller_agent_interface_name"));
			//cdr.setCaller_name(rs.getString("caller_name"));
			
			// 主叫坐席id
			cdr.setCaller_agent_id(rs.getString("caller_agent_id"));
			// 主叫坐席信息
			cdr.setCaller_agent_info(rs.getString("caller_agent_info"));
			
			// 被叫号码
			cdr.setDest_number(rs.getString("dest_number"));

//			cdr.setDest_agent_interface_exten(rs.getString("dest_agent_interface_exten"));
//			cdr.setCaller_agent_id(rs.getString("dest_agent_id"));
			
			cdr.setDest_agent_id(rs.getString("dest_agent_id"));
			cdr.setDest_agent_info(rs.getString("dest_agent_info"));
			cdr.setCall_direction(Record.CALL_DIRECTIONS.get(rs.getString("call_direction")));
			
			// 通话时长
			cdr.setBillsec(Integer.parseInt(rs.getString("billsec")));
			
			// 是否接通,接通时长
			cdr.setBridgesec(Long.parseLong(rs.getString("bridgesec")));
			
			//cdr.setEnd_stamp(sdf.parse(rs.getString("end_stamp")));
			
			//通话时长
			// json.put("duration", recordList.get(i).getBillsec()%1000!=0?recordList.get(i).getBillsec()/1000+1:recordList.get(i).getBillsec()/1000);
//			cdr.setDuration(rs.getInt("billsec")%1000!=0?rs.getInt("billsec")/1000+1:rs.getInt("billsec")/1000);
			//接通时长
			//"billsec", recordList.get(i).getBridgesec()%1000!=0?recordList.get(i).getBridgesec()/1000+1:recordList.get(i).getBridgesec()/1000

			//	cdr.setBillsec(rs.getInt("duration")%1000!=0?rs.getInt("duration")/1000+1:rs.getInt("duration")/1000);
//			cdr.setBillsec(rs.getInt("bridgesec")%1000!=0?rs.getInt("bridgesec")/1000+1:rs.getInt("bridgesec")/1000);
			
			cdr.setAccess_number(rs.getString("access_number"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cdr;
	}
	
	/**
	 * 给Excel列赋值
	 */
	public void createExcel(Record cdr, HSSFSheet sheet, int maxRow, int titleNumber) {
		
		/* 创建的行数 */
		HSSFRow ro = sheet.createRow(maxRow);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			for (int i = 0; i < titleNumber; i++) {

				HSSFCell cell = ro.createCell(i);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				switch (i) {
				case 0:
					cell.setCellValue(sdf.format(cdr.getStart_stamp()));
					break;
				case 1:
					cell.setCellValue(cdr.getCaller_id_number());
					break;
				case 2:
					//cell.setCellValue(cdr.getCaller_agent_interface_name());
					cell.setCellValue(cdr.getCaller_agent_id());
					break;
				case 3:
					//cell.setCellValue(cdr.getCaller_name());
					cell.setCellValue(cdr.getCaller_agent_info());
					break;
				case 4:
//					cell.setCellValue(cdr.getDest_agent_interface_exten());
					cell.setCellValue(cdr.getDest_number());
					break;
				case 5:
//					cell.setCellValue(cdr.getDest_agent_interface_name());
					cell.setCellValue(cdr.getDest_agent_id());
					break;
				case 6:
					cell.setCellValue(cdr.getDest_agent_info());
					break;
				case 7:
					cell.setCellValue(cdr.getCall_direction());
					break;
				case 8:
					cell.setCellValue(Record.PUT_THROUGH.get(cdr.getBridgesec()>0?"y":"n"));
					break;
				case 9:
//					cell.setCellValue(cdr.getDuration()!=null?cdr.getDuration():0);
					cell.setCellValue(cdr.getBillsec()%1000!=0?cdr.getBillsec()/1000+1:cdr.getBillsec()/1000);
					break;
				case 10:
//					cell.setCellValue(cdr.getBillsec()!=null?cdr.getBillsec():0);
					cell.setCellValue(cdr.getBridgesec()%1000!=0?cdr.getBridgesec()/1000+1:cdr.getBridgesec()/1000);
					break;
				case 11:
					cell.setCellValue(cdr.getAccess_number());
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Record> getRecord(final String callsessionuuid) {
    	List<Record> rs = queryBean(new BeanHandler() {
            @Override
            public void doSql(StringBuilder sql, List<Object> params) {
                sql.append(" select *  from  "+ SpringPropertiesUtil.getProperty("sys.record.database") +"cdr where type='slave' and call_session_uuid = ? ");
                params.add(callsessionuuid);
            }
        }, Record.class);
        if (rs.size() > 0) {
            return rs;
        }
        return null;
    }

	@Override
	public String getSeconds(long str) {
		
		System.out.println(String.valueOf(str/1000)+"---------");
		if(str%1000 == 0){
			System.out.println(String.valueOf(str/1000));
			return String.valueOf(str/1000);
		}else{
			System.out.println(String.valueOf(str/1000+1));
			return String.valueOf(str/1000+1);
		}
	}

	@Override
	public void saveOrUpdate(Record record) {
		if(record == null)
			return;
		if(StringUtils.isBlank(record.getUid())){
			super.save(record);
		}else{
			record.setUuid(UUID.UUIDFromString(record.getUid()));
			super.update(record);
		}
	}

	@Override
	public void insertData() {

		Date date = new Date();
		
		for(int i = 1; i <= 500000; i++){
			
			Record r = new Record();
			
			r.setCall_session_uuid("73888223-bc73-4150-9957-f8f4c60000"+i);
			r.setFshost_name("192.168.1.139");
			r.setStart_stamp(new Date((date.getTime()+(5000*i))));
			r.setEnd_stamp(new Date(date.getTime()+(8000*i)));
			r.setDuration(7240);
			r.setBillsec(5920);
			r.setHangup_cause("NORMAL_CLEARING");
			r.setSip_hangup_disposition("recv_bye");
			r.setAleguuid("903d9962-260b-11e5-87a5-dbee9c6c8550");
			r.setContext("default");
			r.setType("master");
			
			saveOrUpdate(r);
			
			QualityCheck rc = new QualityCheck();
			
			rc.setReporter("admin");
			rc.setDate(new Date(date.getTime()+(10000*i)));
			rc.setScore("5");
			rc.setRemark("aaaaa");
			rc.setUuidObj("73888223-bc73-4150-9957-f8f4c60000"+i);
			
			qualityCheckService.saveQualityCheck(rc);
			
			System.out.println(i);
				
		}
	}

	@Override
	public void deleteRecord(String uuid) {
		jdbcTemplate.update(" DELETE FROM "+ SpringPropertiesUtil.getProperty("sys.record.database") +"cdr WHERE call_session_uuid = ? ", uuid);
	}
}
