package com.example.ch7_8_test.filter;

import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

public class CustomRequestLoggingFilter extends AbstractRequestLoggingFilter {

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        logger.info("Before Request: " + message);
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        logger.info("After Request: " + message);
    }
}