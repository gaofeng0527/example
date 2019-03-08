//package com.chapter.config;
//
//import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
//
///**
// * 扩展了AbstractAnnotationConfigDispatchServlet的任意类都会自动加载DispatcherServlet和spring应用上下文
// * spring应用上下文会位于应用程序的servlet的上下文中
// * 1.在servlet 3.0 容器中，容器会在类路径中查找实现了ServletContainerInitializer接口的类，如果能发现，就交给它配置servlet容器
// * 2.在spring中提供了这个接口的实现，名为SpringServletContainerInitializer，这个类又找实现了WebApplicationInitializer的类，并把任务交给了它
// * 3.spring 3.2 引入了一个便利的WebApplicationInitializer基础实现，就是AbstractAnnotationConfigDispatcherServlet
// * 4.所以我们创建了一个实现了AbstractAnnotationConfigDispatcherServletInitializer的类，就能完成servlet容器配置
// */
//public class ExampleWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
//
//
//    /**
//     * 该方法会将一个或者多个请求路径映射到DispatcherServlet上
//     * '/'表示所有路径
//     * @return
//     */
//    @Override
//    protected String[] getServletMappings() {
//        return new String[]{"/"};
//    }
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
//}
