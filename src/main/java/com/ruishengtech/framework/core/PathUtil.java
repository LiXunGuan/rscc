package com.ruishengtech.framework.core;

import java.io.File;

public class PathUtil {

	/**
	 * 获取工作目录路径（相对路径）
	 * 
	 * @return
	 */
	public static String getBasePath() {

		return File.separator + "license";
	}

}
