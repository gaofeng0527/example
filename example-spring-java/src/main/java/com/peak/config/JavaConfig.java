package com.peak.config;

import com.peak.config.dao.PersonDao;
import com.peak.config.dao.PersonDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 1.@ComponentScan 会开启包扫码，会自动扫码该类所在包以及该包下的子包中所有被注解（@component、@named）修
 * 饰的类，并创建在spring容器中创建对应的bean
 * 1.1 @ComponentScan("com.peak") 可以设置扫描指定包路径
 * 1.2 @ComponentScan(basePackages="com.peak") 和上面作用一样
 * 1.3 @ComponentScan(basePackages={"com.peak","com.spring"}) 可以配置多个包路径
 * 1.4 @ComponentScan("basePackageClasses={CDPlay.class,DVDPlay.class}") 这些类所在的包会作为基础包进行扫描，推荐使用空接口作为参数
 * 2.@Configuration 注解表明该类作为spring的java配置类
 */
@Configuration
@ComponentScan
public class JavaConfig {

    @Bean
    public PersonDao personDao(){
        return new PersonDaoImpl();
    }

    /**
     * @Bean 可以显示的声明bean
     * 方法注入
     * @return

    @Bean
    public PersonService psersonService1() {
        //在方法体中，可以根据业务创建不同的实现类
        PersonService personService = new PersonService();
        personService.setDao(personDao());
        return personService;
    }*/




}
