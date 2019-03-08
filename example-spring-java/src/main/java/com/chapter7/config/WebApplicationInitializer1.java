package com.chapter7.config;

import com.chapter7.filter.MyFilter;
import com.chapter7.servlet.MyServlet;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebApplicationInitializer1 implements WebApplicationInitializer {

    /**
     * 注册自定义的servlet
     * 在这里也可以配置DispatcherServlet，但是没有必要，因为AbstractAnnotationConfigDispatcherServletInitializer已经做了
     *
     * @param servletContext
     * @throws ServletException
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        //servlet
        ServletRegistration.Dynamic myservlet = servletContext.addServlet("myservlet", MyServlet.class);
        myservlet.addMapping("/myservlet/index");

        //filter
        FilterRegistration.Dynamic myFilter = servletContext.addFilter("myfilter", MyFilter.class);
        myFilter.addMappingForUrlPatterns(null, false, "/myservlet/*");//添加映射

    }
}
