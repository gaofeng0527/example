package com.peak.config;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * 1.当有多个实现了CompactDisc接口的类时，spring自动装配会失败，因为spring也不知道要装配哪一个
 * 2.可以使用@Primary注解，告诉spring如果发生歧义时，优先装配哪个bean
 * 3.bean的作用域：1.singleton 默认单例  2.prototype 每次注入，或者通过spring应用上下文获取的时候，都会创建一次
 *               3.session 每个会话会创建一个   4.request 每次请求都会创建
 * 4.可以使用@Scope("Session")注解定义bean的作用域
 */
@Component
@Primary
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class OldSgtPepers implements CompactDisc {

    @Override
    public void play() {
        System.out.println("我是个老家伙！");
    }
}
