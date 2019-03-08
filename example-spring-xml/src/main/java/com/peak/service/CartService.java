package com.peak.service;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

/**
 * 请求与会话作用域
 * 1.会话作用域适合场景：购物车
 * 2.如果一个会话，或者请求作用域修饰的bean，被一个单例bean所依赖时，会有一个逻辑问题，因此被注入到单例bean中的是一个代理
 *   proxyMode属性是设置代理的创建模式：
 *      ScopedProxyMode.TARGET_CLASS表明要以生成目标类扩展的方式创建代理
 *      ScopedProxyMode.INTERFACES 表明这个代理要实现CartService接口，并将调用委托给实现bean
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION,proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartService {

    public void addProduct(){
        System.out.println("abcK");
    }
}
