package com.example.car_rental.configuration;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCorsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        Map<String,String> map = new HashMap<>();
        String originHeader = httpServletRequest.getHeader("origin");
        httpServletResponse.setHeader("Access-Control-Allow-Origin", originHeader);
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        httpServletResponse.setHeader("Access-Control-Max-Age","3600");
        httpServletResponse.setHeader("Access-Control_Allow_Headers", "*");

        if("OPTIONS".equalsIgnoreCase(httpServletRequest.getMethod())){
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        } else{
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
