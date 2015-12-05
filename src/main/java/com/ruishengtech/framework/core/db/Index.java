package com.ruishengtech.framework.core.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Index {

	/* 建表语句
	 * CREATE TABLE `NewTable` (
		`a`  varchar(255) NOT NULL ,
		`b`  varchar(255) NULL ,
		`c`  varchar(255) NULL ,
		PRIMARY KEY (`a`),
		UNIQUE INDEX `a` (`a`) USING HASH ,
		INDEX `b` (`b`) USING BTREE 
		)
	 */
	
	//
	String name() default "";
	
	String type() default "";
	
	String method() default "BTREE";
	
}
