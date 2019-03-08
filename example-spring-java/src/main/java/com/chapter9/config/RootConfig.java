package com.chapter9.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * application 配置类：配置ApplicationContext上下文
 */
@Configuration
@ComponentScan("com.chapter9")
public class RootConfig {
}
