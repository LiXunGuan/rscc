package com.ruishengtech.framework.core;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaoliceng on 14-3-21.
 */
public class MvcInterceptor implements HandlerInterceptor {

    private List<String> noFilters = new ArrayList<String>() {
        {
            //add("/");
            add("/sysuser/login");
            add("/login");
            add("/assets");
            add("/public");
            add("/fs");
            add("/dologin");
            add("/valid");
            add("/addTab");
            add("/");
            add("/config");
        }
    };

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
