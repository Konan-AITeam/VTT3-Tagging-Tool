package com.konantech.spring.interceptor;

import com.konantech.spring.util.RequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
public class LogsAspect {
    private static Logger log = LoggerFactory.getLogger(LogsAspect.class);
    private int count = 0; // count 변수
    @Before("execution(* com.konantech.spring.controller.rest.*.*(..))")
    public void restControllerlogsAspect(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String queryString = RequestUtils.getQueryString(request);
        String logString = request.getServletPath() + (StringUtils.isNotEmpty(queryString) ? "?" + queryString : "");
//        log.debug("query = " + logString);
        System.out.println("query = " + logString);
    }
}
