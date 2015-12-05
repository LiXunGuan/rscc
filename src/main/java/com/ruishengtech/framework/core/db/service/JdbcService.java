package com.ruishengtech.framework.core.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;


public class JdbcService {

    //暂时去除缓存
    public static enum Template {JDBC}

    @Autowired
    @Qualifier(value = "jdbcTemplate")
    protected JdbcTemplate jdbcTemplate;


    protected JdbcTemplate getTemplate() {
        return getTemplate(Template.JDBC);
    }

	public JdbcTemplate getTemplate(Template template) {

        switch (template) {
            case JDBC:
                return jdbcTemplate;
//            case CACHE:
//                return cacheTemplate;
        }
        return null;
    }
}
