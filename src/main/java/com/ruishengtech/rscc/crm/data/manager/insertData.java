package com.ruishengtech.rscc.crm.data.manager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.ruishengtech.framework.core.ApplicationHelper;

public class insertData {

	SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private DataSourceTransactionManager dataSourceTransactionManager;
	private JdbcTemplate jdbcTemplate;

	public insertData() {
		this.jdbcTemplate = ApplicationHelper.getApplicationContext().getBean(
				JdbcTemplate.class);
		this.dataSourceTransactionManager = ApplicationHelper
				.getApplicationContext().getBean(
						DataSourceTransactionManager.class);
	}

	public void run() throws DataAccessException, ParseException {
		
		System.out.println(fmt.format(new Date()));
		
		int year = 0,hours = 0,month = 0,day = 0,week = 0;
		
		Date date = new Date();
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务
		TransactionStatus status = dataSourceTransactionManager.getTransaction(def); // 获得事务状态
		
//插入数据管理的log信息		
//		List<String> numtobatch = new ArrayList<>();
//		numtobatch.add("18900005636-c6297f26419c4ac3abc0289dde5dc985");
//		numtobatch.add("17000001111-c6297f26419c4ac3abc0289dde5dc985");
//		numtobatch.add("17200002222-c6297f26419c4ac3abc0289dde5dc985");
//		numtobatch.add("17300003333-c6297f26419c4ac3abc0289dde5dc985");
//		numtobatch.add("17400004444-9fc8c5b4956a417989080f0cec18a75b");
//		numtobatch.add("17500005555-9fc8c5b4956a417989080f0cec18a75b");
//		numtobatch.add("17600006666-9fc8c5b4956a417989080f0cec18a75b");
//		numtobatch.add("17700007777-229c767e6fc84930b5b078d2c1263ef9");
//		numtobatch.add("17800008888-229c767e6fc84930b5b078d2c1263ef9");
//		numtobatch.add("17900009999-229c767e6fc84930b5b078d2c1263ef9");
//		
//		List<String> userid = new ArrayList<>();
//		userid.add("0f79d5bf774a4783b748e649b14ee9b8");
//		userid.add("192f58a32adf410ca793d447eb30a2ed");
//		userid.add("38ead9024cb045389f5c25703d1a020d");
//		userid.add("3fb04c00fbb74f808e0add1018aec39b");
//		userid.add("401560a4ff764ebf95964f33982f91be");
//		userid.add("425b55e169ee49528bf5a7c9f1588dae");
//		userid.add("68744f19644e48da81977e5bbd86f9a4");
//		
//		List<String> types = new ArrayList<>();
//		types.add("customer");
//		types.add("global_share");
//		types.add("noanswer");
//		types.add("blacklist");
//		types.add("abandon");
//		types.add("intent");
//		
//		List<String> source = new ArrayList<>();
//		source.add("customer");
//		source.add("global_share");
//		source.add("noanswer");
//		source.add("intent");
//		
//		for (int i = 0; i < 500000; i++) {
			
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(new Date(date.getTime() + 100000*i));
//			year = cal.get(cal.YEAR);
//			month = cal.get(cal.MONTH);
//			week = cal.get(cal.WEEK_OF_YEAR);
//			day = cal.get(cal.DATE);
//			hours = cal.get(cal.HOUR_OF_DAY);
//			
//			this.jdbcTemplate.update(""
//					+ " INSERT INTO `crm`.`new_data_department_user_log` "
//					+ " (`uuid`,`phone_number`, "
//					+ " `batch_uuid`,"
//					+ " `op_user`,"
//					+ " `old_stat`,"
//					+ " `op_type`,"
//					+ " `op_time`,"
//					+ " `call_result`,"
//					+ " `call_record`,"
//					+ " `call_session_uuid`) "
//					+ "	values(?,?,?,?,?,?,?,?,?,?) ", "ffdb8f38-600e-11e5-b436-7b"+i,numtobatch.get(i % numtobatch.size()).split("-")[0],numtobatch.get(i % numtobatch.size()).split("-")[1],userid.get(i % userid.size()),source.get(i % source.size()),types.get(i % types.size()),new Date(date.getTime()+(10000*i)),null,null,"");
//		}

//		for (int i = 1576; i < 500000; i++) {
//			
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(new Date(date.getTime() + 100000*i));
//			year = cal.get(cal.YEAR);
//			month = cal.get(cal.MONTH);
//			week = cal.get(cal.WEEK_OF_YEAR);
//			day = cal.get(cal.DATE);
//			hours = cal.get(cal.HOUR_OF_DAY);
//			
//			this.jdbcTemplate.update(""
//					+ " INSERT INTO mw.report_accessnumber"
//					+ "(`year`,"
//					+ " `month`, "
//					+ " `week`,"
//					+ " `day`,"
//					+ " `hour`,"
//					+ " `timestamp`,"
//					+ " `name`,"
//					+ " `out_f_callcount`,"
//					+ " `in_f_callcount`,"
//					+ " `out_t_callcount`,"
//					+ " `in_t_callcount`,"
//					+ " `out_t_duration`,"
//					+ " `in_t_duration`,"
//					+ " `max_concurrent`,"
//					+ " `min_concurrent`,"
//					+ " `info`) "
//					+ "	values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ", year, month+1, week, day, hours, fmt.parse(fmt.format(cal.getTime())), "95002", "41", "0", "0", "0", "0", "0", "0", "0", null);
//		
//		}
		
		
		
		
//		for (int j = 0; j < 500000; j++) {
			
//			this.jdbcTemplate.update(""
//					+ "INSERT INTO mw.cdr ("
//					+ "`call_session_uuid`, "
//					+ "`fshost_name`, "
//					+ "`start_stamp`, "
//					+ "`end_stamp`, "
//					+ "`duration`, "
//					+ "`billsec`, "
//					+ "`hangup_cause`, "
//					+ "`sip_hangup_disposition`, "
//					+ "`aleguuid`, "
//					+ "`context`, "
//					+ "`type`) "
//					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", "625d17f4-edfc-4b30-915e-da4b5a"+j, "192.168.1.137", new Date(date.getTime()+(10000*j)), new Date(date.getTime()+(100000*j)), "9000", "7000", "NORMAL_CLEARING", "send_bye", "f88ac89a-59fb-11e5-bad9-2dfe86986f94", "default", "master");
			
			
//			this.jdbcTemplate.update(""
//					+ " INSERT INTO crm.qualitycheck(`uuid`, `reporter`, `date`, `score`, `uuid_obj`, `remark`) values(?,?,?,?,?,?) ", j, "admin", new Date(date.getTime()+(50000*j)), "5", "625d17f4-edfc-4b30-915e-da4b5a"+j, "qqqqqq");
		
//		}
		
		
		dataSourceTransactionManager.commit(status);
		System.out.println(fmt.format(new Date()));
		System.out.println("插入完成");

	}
	
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		int year=0,hours = 0,month = 0,day = 0,week = 0;
		
		Date date = new Date();
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		year = cal.get(cal.YEAR);
		month = cal.get(cal.MONTH);
		week = cal.get(cal.WEEK_OF_YEAR);
		day = cal.get(cal.DATE);
		hours = cal.get(cal.HOUR_OF_DAY);
	
		System.out.println(fmt.parse(fmt.format(cal.getTime())));
		System.out.println(year);
		System.out.println(month+1);
		System.out.println(week);
		System.out.println(day);
		System.out.println(hours);
		
	}
	

}
