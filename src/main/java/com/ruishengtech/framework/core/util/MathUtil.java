package com.ruishengtech.framework.core.util;




public class MathUtil {

	public static int min(Integer... integers) {
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < integers.length; i++) {
			if (integers[i] < min) {
				min = integers[i];
			}
		}
		return min;
	}
	
}
