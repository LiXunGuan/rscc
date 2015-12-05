package com.ruishengtech.rscc.crm.ui.mw.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by yaoliceng on 2014/4/16.
 */
public class DateUtils {


    public static Date timeToDate(Long time){

        if(time==null || time==0){
            return null;
        }

        return new Date(time);
    }


    public static Date getDates(String string) {

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date();

        try {

            date = fmt.parse(string);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;

    }

    public static String toDates(Date date) {

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        return fmt.format(date);

    }

    public static String toDateMonth(Date date) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMM");
        return fmt.format(date);
    }

    public static String toDate(Date date) {

        if(date==null){
            return null;
        }
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return fmt.format(date);

    }

    public static String addOneDay(String string) {

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

    public static Date stringToDate(String s) {

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return fmt.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }





    public static Date stringToDate1(String s) {

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            return fmt.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Integer toInt(Date d) {

        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return Integer.valueOf(fmt.format(d));

    }


    public static int daysOfTwo(Date fDate, Date oDate) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(fDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(oDate);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        return Math.abs(day2 - day1);
    }

    public static Date getMondayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayofweek == 0)
            dayofweek = 7;
        c.add(Calendar.DATE, -dayofweek + 1);
        return c.getTime();
    }


    public static List<Date> getDayInMonthWithMondayStandard(Integer month) {

        List<Date> list = new ArrayList<Date>();
        Date first = null;
        Date end = null;

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, month);

        for (int i = 1; i <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                if (first == null) {
                    first = cal.getTime();
                }
                end = cal.getTime();
            }
            cal.set(Calendar.DAY_OF_MONTH, i);
        }

        list.add(first);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(end);
        calendar.add(Calendar.DATE, 6);
        list.add(calendar.getTime());

        return list;
    }

    public static void main(String[] args) {
        System.out.println(getDayInMonthWithMondayStandard(0));
    }


    public static Date getQueryBeginDate(Date date) {

        Calendar acl = Calendar.getInstance();
        acl.setTime(date);
        acl.add(Calendar.HOUR, 0);
        acl.add(Calendar.MINUTE, 0);
        acl.add(Calendar.SECOND, 0);
        return acl.getTime();
    }

    public static Date getQueryEndDate(Date date) {

        Calendar acl = Calendar.getInstance();
        acl.setTime(date);
        acl.add(Calendar.DATE, 1);
        acl.add(Calendar.HOUR, 0);
        acl.add(Calendar.MINUTE, 0);
        acl.add(Calendar.SECOND, 0);
        return acl.getTime();
    }

    public static Date getSundayofThisWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getMondayOfThisWeek());
        calendar.add(Calendar.DATE, 5);
        return calendar.getTime();
    }


}
