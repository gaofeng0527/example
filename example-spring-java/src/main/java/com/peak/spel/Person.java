package com.peak.spel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 外部值注入
 * 1.@PropertySource 结合 Environment 读取配置文件中的值
 *
 */
@Component
@PropertySource("classpath:app.properties")
public class Person {

    @Autowired
    Environment env;//该类会自动加载app.properties文件中的键值对

    public void say(){
        String name = env.getProperty("user.name","张高峰");
        Integer age = env.getProperty("user.age",Integer.class);
        System.out.println("name: " + name + " age: " + age);

    }
}
