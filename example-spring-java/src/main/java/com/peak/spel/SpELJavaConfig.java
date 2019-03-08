package com.peak.spel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan
public class SpELJavaConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer(){

        return new PropertySourcesPlaceholderConfigurer();
    }
}
