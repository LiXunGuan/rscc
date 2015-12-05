package com.ruishengtech.framework.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yaoliceng on 2014/4/16.
 */
public class DateUtils {
	
	public static Date getDates(String string){
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = new Date();
		
		try {
			
			date = fmt.parse(string);
		
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return date;
		
	}
	
	//获取当前年月日，时分秒
	public static String getDateString(Date date){
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = fmt.format(date);
		return dateString;
	}
	
	/**
	 * 转换类似（"Thu Oct 22 11:02:22 CST 2015"）格式的字符串为格式化的字符串类型
	 * @param date
	 * @return
	 */
	public static String getDateString(String date){
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat newfmt = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
		Date newDate = null;
		try {
			newDate = newfmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String dateString = fmt.format(newDate);
		return dateString;
		
	}
	
	public static String addOneDay(String string){
		
		SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//获取当前calendar对象.
		Calendar acl = Calendar.getInstance();
//		System.out.println(fmt.format(acl.getTime()));
		acl.add(Calendar.MINUTE, 59);
		acl.add(Calendar.SECOND, 59);
		
//		Date d = null;
		
//		try {
//			
//			d = fmt.parse(fmt.format(acl.getTime()));
//			
//			System.out.println(fmt.format(d));
//			
//		} catch (ParseException e) {
//
//			e.printStackTrace();
//		}
		
		return fmt.format(acl.getTime());
	}

    public static Date stringToDate(String s){

        SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return  fmt.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Integer toInt(Date d){

        SimpleDateFormat fmt=new SimpleDateFormat("yyyyMMdd");
     return Integer.valueOf(fmt.format(d));

    }
    //获取指定年月的月的第一天
    public static Date getFirstDayOfMonth(Integer year, Integer month) {
        Calendar calendar = Calendar.getInstance();
        if (year == null) {
            year = calendar.get(Calendar.YEAR);
        }
        if (month == null) {
            month = calendar.get(Calendar.MONTH);
        }
        calendar.set(year, month, 1);
        calendar.add(Calendar.MONTH, -1);
        return calendar.getTime();
    }
    //获取指定年月的月的最后一天
    public static Date getLastDayOfMonth(Integer year, Integer month) {
        Calendar calendar = Calendar.getInstance();
        if (year == null) {
            year = calendar.get(Calendar.YEAR);
        }
        if (month == null) {
            month = calendar.get(Calendar.MONTH);
        }
        calendar.set(year, month, 1);
        calendar.add(Calendar.MONTH, -1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.getTime();
    }
    //获取指定年月的月的第一天
    public static Date getFirstDayOfNextMonth(Integer year, Integer month) {
        Calendar calendar = Calendar.getInstance();
        if (year == null) {
            year = calendar.get(Calendar.YEAR);
        }
        if (month == null) {
            month = calendar.get(Calendar.MONTH);
        }
        calendar.set(year, month, 1);
        return calendar.getTime();
    }
    
    //获取当前月第一天：
    public static String getFirstDayOfMonth(){
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
	    Calendar c = Calendar.getInstance();    
	    c.add(Calendar.MONTH, 0);
	    c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
	    return format.format(c.getTime());
    }
    //获取当前月最后一天
    public static String getLastDayOfMonth(){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
	    Calendar ca = Calendar.getInstance();    
	    ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
	    return format.format(ca.getTime());
    }
    //获取本周第一天
    public static String getFirstDayOfWeek(){
    	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	 Calendar calweek = Calendar.getInstance();
    	 calweek.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    	 return sdf.format(calweek.getTime());
    }
    //获取下周第一天
    public static String getFirstDayOfNextWeek(){
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 Calendar calweek = Calendar.getInstance();
    	calweek.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
    	calweek.add(Calendar.WEEK_OF_YEAR, 1);
    	calweek.add(Calendar.DAY_OF_WEEK, 1);
    	return sdf.format(calweek.getTime());
    }
    //根据传入日期转为String
  	public static String getDayStringByDate(Date date){
  		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
  		String dateString = fmt.format(date);
  		return dateString;
  	}
  	//获取当前日期的下一天
  	public static String getNextDayString(){
  		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
  		 Calendar cal = Calendar.getInstance();
  		 cal.add(Calendar.DATE, 1);
  		 return fmt.format(cal.getTime());
  	}
	
    //临时实现方案
    public static Date toLocalTime(Date d) {
    	Calendar cal = Calendar.getInstance();
        cal.setTime(d);//date 换成已经已知的Date对象
        cal.add(Calendar.HOUR_OF_DAY, -14);// before 8 hour
        return cal.getTime();
    }
    //给一位字符前加0
    private static String singToTwo(Object o){
    	String s = String.valueOf(o);
    	if(s.length()==1){
    		return "0"+s;
    	}
    	return s;
    }
    /**
     * 处理通话时长显示单位
     * 秒-->时分秒
     */
     public static String getSToTime(Integer ss){
                      
           if(ss<1 && ss>0){
        	   
               return "00:00:01";
           }else if(ss>=1 && ss<60){
        	   //不到一分钟
               return "00:00:"+singToTwo(ss);
           }else if(ss>=60 && ss<3600){
        	   //不到一小时
               return "00:"+singToTwo((ss-ss%60)/60)+":"+singToTwo(ss%60);
           }else if(ss>=3600){
        	   //大于一小时
               Integer min = ss-ss%3600;
               Integer s = ss%3600%60;
               return singToTwo(min/3600)+":"+singToTwo(((ss-min)-(ss-min)%60)/60)+":"+singToTwo(s);
           }
//           else if(ss>=86400){
//        	   
//        	   long min = ms-ms%3600
//               long ss = ms%3600%60;
//               return (ms-ms%86400)/86400+"天"+(min/3600==24?00:min/3600)+":"+((ms-min)-(ms-min)%60)/60+":"+ss;
//           }
           return "00:00:00";
       }
   
    /**
     * 处理通话时长显示单位
     * 毫秒-->时分秒
     */
     public static String getMsToTime(Long ms){
           //首先换算成秒，再进行处理
           if(ms%1000!=0){
               ms = (ms-ms%1000)/1000+1;
           }else{
               ms = ms/1000;
           }
           
           if(ms<1 && ms>0){
        	   
        	   return "00:00:01";
           }else if(ms>=1 && ms<60){
        	   
        	   return "00:00:"+singToTwo(ms);
           }else if(ms>=60 && ms<3600){
        	   
               return "00:"+singToTwo((ms-ms%60)/60)+":"+singToTwo(ms%60);
           }else if(ms>=3600){
        	   
               long min = ms-ms%3600;
               long ss = ms%3600%60;
               return singToTwo(min/3600)+":"+singToTwo(((ms-min)-(ms-min)%60)/60)+":"+singToTwo(ss);
           }
//           else if(ms>=86400){
//        	   
//        	   long min = ms-ms%3600;
//               long ss = ms%3600%60;
//               return (ms-ms%86400)/86400+"天"+(min/3600==24?00:min/3600)+":"+((ms-min)-(ms-min)%60)/60+":"+ss;
//           }
           return "00:00:00";
       }
     }
