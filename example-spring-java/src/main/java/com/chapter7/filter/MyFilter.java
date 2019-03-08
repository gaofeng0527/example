package com.chapter7.filter;

import javax.servlet.*;
import java.io.IOException;

public class MyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request,response);

    }

    @Override
    public void destroy() {
        System.out.println("xiaohui");

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
}
