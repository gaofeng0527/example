package com.chapter9.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * web.xml配置替换类
 * 可以通过java类的方式，实现web.xml的配置
 * web服务器启动的时候，会自动识别继承 AbstractAnnotationConfigDispatcherServletInitializer类
 * 如果有，就把创建servletContext上下文和ApplcationContext上下文的工作交给这个类
 * 1.自动注册了DispatcherServlet 并把配置的请求路径映射到该servlet上
 * 2.自动加载ContextLoaderListener 另外一个应用上下文，加载应用中的其他bean，这些bean主要是用来驱动程序后端，例如服务层，数据层等。
 * 3.一个应用中只能有一个AbstractAnnotationConfigDispatcherServletInitializer的子类,不处理的话会有两个同名的DispatcherServlet
 * 4.@PropertySource可以引入一个配置文件，@value注解可以直接读取配置
 */
@PropertySource("classpath:app.properties")
public class Chapter9WebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Value("${dir.upload}")
    private String upload;

    /**
     * web容器启动的时候执行，在这里也可以注册servlet
     * 也可以注册DispatcherServlet，但是没有必要，父类已经帮忙做了
     * @param servletContext
     * @throws ServletException
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
    }

    /**
     * 加载ContextLoaderListener配置类
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfig.class};
    }

    /**
     * 加载Servlet配置类
     * @return
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    /**
     * 把指定路径映射到DispatcherServlet中
     * @return
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    /**
     * 配置文件上传
     * @param registration
     */
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setMultipartConfig(new MultipartConfigElement(upload));
    }
}
