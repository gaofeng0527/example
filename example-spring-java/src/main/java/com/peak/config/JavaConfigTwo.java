package com.peak.config;

import com.peak.config.dao.PersonDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * 1.有多个配置类时：如果需要在一个配置类需要用到另外一个配置类中的bean时，可以使用@Import注解把配置导入
 * 2.或者创建一个更高级的配置类，把所有的配置类集中导入到一个类中
 * @Import({OneConfig.class,TwoConfig.class....})
 * 3.如果需要调用xml配置中的bean时，使用@ImportResource("application.xml")注解
 * 4.如果需要在xml中使用java配置类中的bean时，只需使用<bean>标签声明该类即可
 * <bean class="com.peak.config.JavaConfigTwo"/>
 */
@Configuration
@Import(JavaConfig.class)
public class JavaConfigTwo {

    /**
     * 构造方法注入
     * 1.在创建PersonService对象时，会自动注入PersonDao类型的bean，这个bean也是在spring上下文中创建的。
     * @param dao
     * @return
     */
    @Bean
    public PersonService personService(PersonDao dao){
        //构造方法注入，必须要有对应的构造方法
        return new PersonService(dao);
    }
}
