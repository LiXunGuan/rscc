//package com.ruishengtech.rscc.crm.ui.datamanager;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import org.apache.log4j.Logger;
//import org.json.JSONObject;
//
//import com.ruishengtech.framework.core.ApplicationHelper;
//import com.ruishengtech.framework.core.util.DateUtils;
//import com.ruishengtech.framework.core.websocket.service.BrokerService;
//
///**
// * @author Frank
// *
// */
//public class DestoryTimer implements Runnable {
//
//	private Map<String, JSONObject> d;
//
//	@SuppressWarnings("unused")
//	private String callSessionUUid ;
//	
//	//超时时间
//	@SuppressWarnings("unused")
//	private final Integer timeOutSec = 10;
//	
//	private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//	BrokerService brokerService = ApplicationHelper.getApplicationContext().getBean(BrokerService.class);
//	
//	public DestoryTimer(String callSessionUUid) {
//
//		this.d = NewUserDataController.linkMap;
//		this.callSessionUUid = callSessionUUid;
//	}
//	
//	
//	/**
//	 * 判断自动呼叫任务停止的条件 停止线程
//	 * @return
//	 */
//	public synchronized Map<JSONObject, Date> stopThread(){
//		
//		Iterator<Entry<String, JSONObject>> entries = d.entrySet().iterator();  
//		
//		while (entries.hasNext()) {
//			
//			Entry<String, JSONObject> entry = entries.next();
//			
//			JSONObject jsonObject = entry.getValue();
//			
//			System.out.println(jsonObject);
//			
//			if(timeOut(jsonObject.optString("callDate"),new Date())){
//				
//				//停止自动呼叫
//				try {
//					
//					brokerService.sendToUser("/user", jsonObject.optString("cUser"), new JSONObject().put("type", "stop").toString());
//					
//					System.out.println(d.keySet().size());
//					
//					d.clear();
//					
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//			
//			//2f61114a-5ee3-4639-86f8-2dff589685ea={"exit_code":0,"call_session_uuid":"2f61114a-5ee3-4639-86f8-2dff589685ea"}
////			jsonObject.optString("");
//			
//		}  
//		
//		return null;
//	}
//
//	@Override
//	public void run() {
//
//		while (true) {
//			
//			stopThread();
//			
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	
//	public boolean timeOut(String date,Date date2){
//		
//		try {
//			if((new Date().getTime()-format.parse(DateUtils.getDateString(date)).getTime())/1000 > 10){
//				return true;
//			}else{
//				System.out.println("超过了"+(date2.getTime()-format.parse(DateUtils.getDateString(date)).getTime())/1000+"秒未挂掉话机!!!!!!!");
//			}
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		
//		return false;
//	}
//	
////	public static void main(String[] args) throws ParseException {
////		
////		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////		String t = "2015-11-04 14:35:00";
//////		System.out.println(new Date().getTime()-f.parse(t).getTime());		
////		System.out.println((new Date().getTime()-f.parse(t).getTime())/(1000));		
////		
////		//26446678268
////	}
//	
//
//}
