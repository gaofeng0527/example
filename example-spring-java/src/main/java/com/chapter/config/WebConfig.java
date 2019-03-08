package com.chapter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * spring 5.x WebMvcConfigurerAdapter 已经过时
 * 新的方案是直接实现WebMvcConfigurer 或者 WebMvcConfigurationSupport
 * 推荐使用WebMvcConfigurationSupport
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.chapter")
public class WebConfig extends WebMvcConfigurationSupport {


    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        resolver.setCache(false);
        resolver.setExposeContextBeansAsAttributes(true);
        return resolver;
    }


    /**
     * 设置静态资源默认处理方式
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        //super.configureDefaultServletHandling(configurer);
        configurer.enable();
    }
}
