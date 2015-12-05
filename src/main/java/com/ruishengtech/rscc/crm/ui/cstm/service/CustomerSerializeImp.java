package com.ruishengtech.rscc.crm.ui.cstm.service;

import java.util.HashSet;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.db.service.BaseService;

/**
 * 客户、客服公共的标准编号序列化实现类
 * 其他客户可实现自己的序列化类，重写里面的方法即可
 * @author Frank
 *
 */
@Service
@Transactional
public class CustomerSerializeImp extends BaseService implements CustomerSerialize{

	/**
	 * 得到客户序列编号
	 * 
	 * @return
	 */
	@Override
	public String getCstmSerializeId() {

		int[] num = randomCommon(getMin(), getMax(), getCount());

		int a = jdbcTemplate .queryForObject( " SELECT count(*) FROM cstm_customer WHERE customer_id = ? ", Integer.class, num[0]);
		
		if (a > 0) {
			return getCstmSerializeId();
		} else {

			return "c" + num[0];
		}
		
	}

	/**
	 * 得到客户服务序列编号
	 * 
	 * @return
	 */
	@Override
	public String getCstmserviceSerializeId() {
		
		int[] num = randomCommon(getMin(), getMax(), getCount());

		int a = jdbcTemplate .queryForObject( " SELECT count(*) FROM cstm_cstmservice WHERE cstmservice_id = ? ", Integer.class, num[0]);

		if (a > 0) {
			return getCstmserviceSerializeId();
		} else {

			return "s" + num[0];
		}
	}
	
	public static void main(String[] args) {
		
//		System.out.println(randomCommon(599999, 1000000, 1)[0]);
	}

	/**
	 * 随机给定范围内N个不重复的数
	 * 取随机数
	 * @param min 最小数
	 * @param max 最大数
	 * @param n 个数
	 * @return 返回结果
	 */
	public static int[] randomCommon(int min, int max, int n){  
	    if (n > (max - min + 1) || max < min) {  
	           return null;  
	       }  
	    int[] result = new int[n];  
	    int count = 0;  
	    while(count < n) {  
	        int num = (int) (Math.random() * (max - min)) + min;  
	        boolean flag = true;  
	        for (int j = 0; j < n; j++) {  
	            if(num == result[j]){  
	                flag = false;  
	                break;  
	            }  
	        }  
	        if(flag){  
	            result[count] = num;  
	            count++;  
	        }  
	    }  
	    return result;  
	}  
	
	
	/** 
	 * 随机指定范围内N个不重复的数 
	 * 利用HashSet的特征，只能存放不同的值 
	 * @param min 指定范围最小值 
	 * @param max 指定范围最大值 
	 * @param n 随机数个数 
	 * @param HashSet<Integer> set 随机数结果集 
	 */  
	   public static void randomSet(int min, int max, int n, HashSet<Integer> set) {  
	       if (n > (max - min + 1) || max < min) {  
	           return;  
	       }  
	       for (int i = 0; i < n; i++) {  
	           // 调用Math.random()方法  
	           int num = (int) (Math.random() * (max - min)) + min;  
	           set.add(num);// 将不同的数存入HashSet中  
	       }  
	       int setSize = set.size();  
	       // 如果存入的数小于指定生成的个数，则调用递归再生成剩余个数的随机数，如此循环，直到达到指定大小  
	       if (setSize < n) {  
	        randomSet(min, max, n - setSize, set);// 递归  
	       }  
	   }  
		
		public Integer getMin() {
			return Integer.valueOf(SpringPropertiesUtil.getProperty("sys.cstm.serialize.min"));
		}

		public Integer getCount() {
			return Integer.valueOf(SpringPropertiesUtil.getProperty("sys.cstm.serialize.count"));
		}

		public Integer getMax() {
			return Integer.valueOf(SpringPropertiesUtil.getProperty("sys.cstm.serialize.max"));
		}
		
}
