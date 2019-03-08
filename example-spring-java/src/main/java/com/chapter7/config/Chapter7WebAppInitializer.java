//package com.chapter7.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
//
//import javax.servlet.MultipartConfigElement;
//import javax.servlet.ServletRegistration;
//
//@PropertySource("classpath:app.properties")
//public class Chapter7WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
//
//    @Value("${dir.upload}")
//    private String upload;
//
//    @Override
//    protected Class<?>[] getRootConfigClasses() {
//        return new Class[]{RootConfig.class};
//    }
//
//    @Override
//    protected Class<?>[] getServletConfigClasses() {
//        return new Class[]{WebConfig.class};
//    }
//
//    @Override
//    protected String[] getServletMappings() {
//        return new String[]{"/"};
//    }
//
//    /**
//     * 拦截文件上传请求
//     * @param registration
//     */
//    @Override
//    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
//        //super.customizeRegistration(registration);
//        registration.setMultipartConfig(new MultipartConfigElement(upload));
//
//    }
//
//
//}
