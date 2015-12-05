package com.ruishengtech.framework.core;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

public class QueryUtils {

	/**
	 * 模糊查询
	 * 
	 * @param sql
	 * @param params
	 * @param value
	 * @param query
	 */
	public static void like(StringBuilder sql, List<Object> params,
			String value, String query) {
		if (StringUtils.isNotBlank(value)) {
			sql.append(query);
			params.add("%" + StringUtils.trim(value) + "%");
		}
	}
	
	public static void number(StringBuilder sql, List<Object> params, Number min, Number max, String query) {
		if (min!=null) {
			sql.append(query).append(" >= ? ");
			params.add(min);
		}
		if (max!=null) {
			sql.append(query).append(" <= ? ");
			params.add(max);
		}
	}
	
	public static void in(StringBuilder sql, List<Object> params, Collection<String> list, String query) {
		if (list != null && list.size() > 0) {
			String[] param = new String[list.size()];
			Arrays.fill(param, "?");
			String inSql = Arrays.toString(param);
			sql.append(query + " in (" + inSql.substring(1, inSql.length()-1) + ") ");
			params.addAll(list);
		}
	}
	//这里返回的是一个String,在solution中使用的时候需要whereSql拼起来
	public static String inString(String query, List<Object> params, Collection<String> list) {
		if (list == null || list.size() == 0) {
			return " and 1=2 ";
		}
		String[] param = new String[list.size()];
		Arrays.fill(param, "?");
		String inSql = Arrays.toString(param);
		params.addAll(list);
		return query + " in (" + inSql.substring(1, inSql.length()-1) + ") ";
	}
	
	/**
	 * 比较大小
	 * @param sql
	 * @param params
	 * @param max
	 * @param query
	 */
	public static void biggerThan(StringBuilder sql, List<Object> params, String max, String query) {
		
		if(null != max){
			sql.append(query).append(" <= ? ");
			params.add(max);
		}
	}
	
	/**
	 * 比较大小
	 * @param sql
	 * @param params
	 * @param min
	 * @param query
	 */
	public static void lessThan(StringBuilder sql, List<Object> params, String min, String query) {
		if(null != min){
			sql.append(query).append(" >= ? ");
			params.add(min);
		}
	}
	
	
	/**
	 * 整数范围比较
	 * @param sql
	 * @param params
	 * @param min
	 * @param query
	 */
	public static void compare(StringBuilder sql, List<Object> params, String data, String query) {
		
		if(StringUtils.isNotBlank(data)){
			String[] d = data.split(",");
			if(StringUtils.isNotBlank(d[0])){
				sql.append(query).append(" >= ? ");
				params.add(d[0]);
			}
			if(d.length >1 && StringUtils.isNotBlank(d[1])){
				sql.append(query).append(" <= ? ");
				params.add(d[1]);
			}
			
		}
		
	}
	
	
	
	
	
	/**
	 * 按时间查询
	 */
	public static void date(StringBuilder whereSql, List<Object> params,
			String date, String string) {

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm");

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
	 * 按时间查询
	 */
	public static void dateTime(StringBuilder whereSql, List<Object> params,
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
	
	/**
	 * 按时间查询
	 */
	public static void dateTimeString(StringBuilder whereSql, List<Object> params,
			String date, String string) {

			if (StringUtils.isNotBlank(date)) {
				whereSql.append(string);
				params.add(date);
			}
	}

	/**
	 * 多一天
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
				acl.add(Calendar.DAY_OF_MONTH, 1); // 查询日期加一
				whereSql.append(string);
				params.add(acl.getTime());
			} catch (ParseException e) {

				whereSql.append(" and 1 <> 1 ");

			}

		}
	}
	
	/**
	 * 前一天
	 */
	public static void agoDate(StringBuilder whereSql, List<Object> params,
			String date, String string) {

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

		Calendar acl = Calendar.getInstance();

		if (StringUtils.isNotBlank(date)) {
			Date dd;
			try {
				dd = fmt.parse(date);
				acl.setTime(dd);
				acl.add(Calendar.DAY_OF_MONTH, -1); // 查询日期加一
				whereSql.append(string);
				params.add(acl.getTime());
			} catch (ParseException e) {

				whereSql.append(" and 1 <> 1 ");

			}

		}
	}

	/**
	 * 精确查询数据
	 */
	public static void queryData(StringBuilder whereSql, List<Object> params,
			String value, String query) {

		if (StringUtils.isNotBlank(value)) {
			whereSql.append(query);
			params.add(StringUtils.trim(value));
		}

	}

	/**
	 * 持续时间数据
	 */
	public static void queryDuration(StringBuilder whereSql,
			List<Object> params, String value, String query) {

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

	/**
	 * 得到本周周一
	 */
	public static String getMondayOfThisWeek() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayofweek == 0)
			dayofweek = 7;
		c.add(Calendar.DATE, -dayofweek + 1);

		return sdf.format(c.getTime());

	}

	/**
	 * 得到本周周一
	 */
	public static Date getMondayOfThisWeek2() {

		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayofweek == 0)
			dayofweek = 7;
		c.add(Calendar.DATE, -dayofweek + 1);

		return c.getTime();

	}

	/**
	 * 得到本周周一
	 */
	public static String getSundayOfThisWeek() {

		Calendar c = Calendar.getInstance();
		int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayofweek == 0)
			dayofweek = 7;
		c.add(Calendar.DATE, -dayofweek + 7);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(c.getTime());

	}

	/**
	 * 
	 * 一个月的第一天
	 * 
	 * @return
	 */
	public static Date getFirstDayOfThisMonth() {

		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		return calendar.getTime();
	}

	/**
	 * 一个月的最后一天
	 * 
	 * @return
	 */
	public static Date getLastDayOfThisMonth() {

		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		return calendar.getTime();
	}
	
	// 得到本月第一天
	@SuppressWarnings("static-access")
	public static String getCurrentMonthSimple() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.DAY_OF_MONTH, 1);
		calendar.set(calendar.HOUR_OF_DAY, 0);
		calendar.set(calendar.MINUTE, 0);
		calendar.set(calendar.SECOND, 0);
		return sdf.format(calendar.getTime());
	}
	// 得到本月第一天
	@SuppressWarnings("static-access")
	public static String getCurrentMonthLastSimple() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.DAY_OF_MONTH, calendar.getMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(calendar.HOUR_OF_DAY, 0);
		calendar.set(calendar.MINUTE, 0);
		calendar.set(calendar.SECOND, 0);
		return sdf.format(calendar.getTime());
	}
	

	// 得到本月第一天
	@SuppressWarnings("static-access")
	public static String getCurrentMonth() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.DAY_OF_MONTH, 1);
		calendar.set(calendar.HOUR_OF_DAY, 0);
		calendar.set(calendar.MINUTE, 0);
		calendar.set(calendar.SECOND, 0);
		return sdf.format(calendar.getTime());
	}
	
    /**
     * 上传图片
     * @param multipartFile
     * @param targetPath 目标路径
     * @return
     * @throws java.io.IOException
     */
    public static String uploadFile(MultipartFile multipartFile , String targetPath) throws IOException {
		//得到要上传的路径
//		targetPath = ApplicationHelper.getSysProperty("sys.upload");
		File serverFiles = new File(targetPath);
		
		if (!serverFiles.exists()) { // 不存在则创建
			serverFiles.mkdirs();
		}
		
		//重新生成文件名
		String fileName = multipartFile.getOriginalFilename(); // 获得页面传过来文件名
		String fileNameSuffix = fileName.substring(fileName.lastIndexOf("."),fileName.length()); // 得到后缀如--[.mp3]
		String filePath = targetPath + File.separator + System.currentTimeMillis()+ fileNameSuffix ;
		
		File localFile = new File(filePath);
		
		multipartFile.transferTo(localFile);
		return filePath;
	}

    
	/**
	 * JSON转为Map
	 * @param jsonObject
	 * @return
	 */
	public static Map toHashMap(JSONObject jsonObject,Map t) {
		Map<String, String> data = (Map<String, String>)t;
		// 将json字符串转换成jsonObject
		Iterator it = jsonObject.keys();
		// 遍历jsonObject数据，添加到Map对象
		while (it.hasNext()) {
			String key = String.valueOf(it.next());
			String value = (String) jsonObject.get(key);
			data.put(key, value);
		}
		return data;
	}
	

	
}
