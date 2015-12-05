package com.ruishengtech.framework.core;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;


@Component
public class ApplicationHelper implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationHelper.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static String getContextPath() {
        WebApplicationContext c = (WebApplicationContext) applicationContext;
        return c.getServletContext().getContextPath();
    }
    
    public static String getSysProperty(String key) {

        try {
            SpringPropertiesUtil springPropertiesUtil = (SpringPropertiesUtil) applicationContext.getBean("propertyConfigurer");
            return springPropertiesUtil.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
