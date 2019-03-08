package com.peak.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * 测试类
 * 1.SpringJUnit4ClassRunner 以便在测试开始的时候自动创建Spring的应用上下文，并要求JUnit 4.12或更高
 * 2.@ContextConfiguration注解表示需要从哪个类中读取配置信息
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JavaConfig.class)
public class CDPlayTest {

    /**
     * @Autowired 自动装配
     * 1.使用自动装配，当有多个符合条件的bean时，会出现异常：org.springframework.beans.factory.UnsatisfiedDependencyException No qualifying bean of type 'com.peak.config.CompactDisc' available
     */
    @Autowired
    private CompactDisc cd;

    @Autowired
    private PersonService ps;

    @Test
    public void cdShouldNotBeNull() {
        assert cd != null;//断言 cd 不为 null，如果断言正确，执行成功，否则失败
        cd.play();

        assert ps != null;

        assert ps.dao != null;
        ps.say();
    }
}
