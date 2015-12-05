package com.ruishengtech.framework.core;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: mainlove
 * Date: 14-3-22
 * Time: 下午5:53
 *
 *  现在还没用到
 */
public class ExceptionHandler implements HandlerExceptionResolver {


    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        if(e!=null){
            try {
                httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                httpServletResponse.getWriter().print(e.getMessage());
                httpServletResponse.getWriter().flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
        return null;
    }
}
