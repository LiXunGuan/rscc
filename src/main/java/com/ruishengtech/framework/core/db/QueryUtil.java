package com.ruishengtech.framework.core.db;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class QueryUtil {

    public static String ipslipt(String str) {

        StringBuilder ret = new StringBuilder();
        for (String s : str.split("\\.")) {
            ret.append(s);
        }
        return ret.toString();
    }

    public static void like(StringBuilder sql, List<Object> params,
                            String value, String query) {
        if (StringUtils.isNotBlank(value)) {
            sql.append(query);
            params.add("%" + value + "%");
        }
    }

    /**
     * 鎸夋椂闂存煡璇�
     */
    public static void date(StringBuilder whereSql, List<Object> params,
                            String date, String string) {

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

        if (StringUtils.isNotBlank(date)) {
            Date dd;
            try {
                if (StringUtils.isNotBlank(date)) {
                    dd = fmt.parse(date);
                    whereSql.append(string);
                    params.add(dd);
                }
            } catch (ParseException e) {
                whereSql.append(" and 1 <> 1 ");
            }

        }
    }
    
    public static void datetime(StringBuilder whereSql, List<Object> params,
            String date, String string) {
			
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			if (StringUtils.isNotBlank(date)) {
			Date dd;
			try {
			if (StringUtils.isNotBlank(date)) {
			    dd = fmt.parse(date);
			    whereSql.append(string);
			    params.add(dd);
			}
			} catch (ParseException e) {
			whereSql.append(" and 1 <> 1 ");
			}
			
			}
			}

    /**
     * 澶氫竴澶�
     */
    public static void addDate(StringBuilder whereSql, List<Object> params,
                               String date, String string) {

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

        Calendar acl = Calendar.getInstance();

        if (StringUtils.isNotBlank(date)) {
            Date dd;
            try {
                dd = fmt.parse(date);
                acl.setTime(dd);
                acl.add(Calendar.DAY_OF_MONTH, 1); // 鏌ヨ鏃ユ湡鍔犱竴
                whereSql.append(string);
                params.add(acl.getTime());
            } catch (ParseException e) {

                whereSql.append(" and 1 <> 1 ");

            }

        }
    }

    /**
     * 绮剧‘鏌ヨ鏁版嵁
     */
    public static void queryData(StringBuilder whereSql, List<Object> params,
                                 String value, String query) {

        if (StringUtils.isNotBlank(value)) {
            whereSql.append(query);
            params.add(value);
        }

    }
    
    /**
     * 精确查询数据
     */
    public static void queryLong(StringBuilder whereSql, List<Object> params,
                                 Long value, String query) {

        if (value!=null) {
            whereSql.append(query);
            params.add(value);
        }
    }
    
    /**
     * 鎸佺画鏃堕棿鏁版嵁
     */
    public static void queryDuration(StringBuilder whereSql, List<Object> params,
                                     String value, String query) {

        if (StringUtils.isNotBlank(value)) {

            whereSql.append(query);
            Double vale;
            try {

                vale = Double.parseDouble(value);
                Double s = vale * 1000;
                params.add(s.toString());

            } catch (Exception e) {
                whereSql.append("and 1=0 ");
                params.add(String.valueOf(1000000));

            }

        }

    }

    public static void setId(StringBuilder whereSql, List<Object> params,
                             Integer date, String string) throws ParseException {

        if (StringUtils.isNotBlank(String.valueOf(date))) {
            whereSql.append(string);
            params.add(date);
        }
    }

	/**
	 * 两个之前取或者
	 * @param whereSql
	 * @param params
	 * @param attribute
	 * @param attribute2
	 * @param string
	 */
	public static void getOrData(StringBuilder whereSql, List<Object> params,
			String arg0, String arg1, String string) {

		if(StringUtils.isNotBlank(arg0)){
			
			if(StringUtils.isNotBlank(arg1)){
				whereSql.append(" AND  ( "+ string +" = ? OR "+ string +" = ?  ) ");
				params.add(arg0);
				params.add(arg1);
			}else{
				
				whereSql.append(" AND  "+ string +" = ?  ");
				params.add(arg0);
			}
			
		}
		
	}


    

}
