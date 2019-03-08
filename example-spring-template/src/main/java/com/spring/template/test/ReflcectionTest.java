package com.spring.template.test;

import com.spring.template.user.Account;

import java.lang.reflect.Method;

public class ReflcectionTest {

    public static void main(String[] args) throws ClassNotFoundException {
        /**
         * Class 本身也是一个类，是用来描述类的类
         * 获取Class实例有三种方式：1.Class.forName()  2.通过类名.class  3.实例对象.getClass();
         */
        Class clzz = Class.forName("java.lang.String");
        System.out.println(clzz); //class java.lang.String
        Class clzz2 = String.class;
        System.out.println(clzz2); //class java.lang.String
        Account a = new Account();
        Class clzz3 = a.getClass();
        System.out.println(clzz3);  //class com.spring.template.user.Account

        ReflcectionTest.showAllMethod("java.lang.String");
    }

    /**
     * 根据类全名，获取Class对象
     * 调用 getMethod()方法，可以获取该类的所有public方法
     * @param className
     * @throws ClassNotFoundException
     */
    public static void showMethod(String className) throws ClassNotFoundException {
        Class clazz = Class.forName(className);
        Method[] methods = clazz.getMethods();
        for (Method method :methods){
            System.out.println(method.toString());
        }
    }

    /**
     * 获取所有方法
     * @param className
     * @throws ClassNotFoundException
     */
    public static void showAllMethod(String className) throws ClassNotFoundException {
        Class clazz = Class.forName(className);
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method :methods){
            System.out.println(method.toString());
            System.out.println(method.getDeclaringClass().getName());//获取该方法属于哪个类
        }
    }
}
