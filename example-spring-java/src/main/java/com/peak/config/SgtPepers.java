package com.peak.config;

import org.springframework.stereotype.Component;

/**
 * 1.@Component 注解修饰的类，会被spring发现，并创建bean实例，bean默认的名字为类名首字母小写，例如：sgtPepers
 * 2.@Component('oneBean') 可以自定义一个名字交给Component
 * 3.也可以使用@named('oneBean')代替@Component
 */
@Component
public class SgtPepers implements CompactDisc {

    private String title = "Sgt. Pepper's Lonely Hearts Club Band;";
    private String artist = "The Beatles";

    @Override
    public void play() {
        System.out.println("Playing " + title + "by " + artist);

    }
}
