package com.test;

import org.springframework.util.StringUtils;

public class StringTest {

    public static void main(String[] args) {
        System.out.println(StringUtils.hasText(""));
        System.out.println(StringUtils.hasText(null));
        System.out.println(StringUtils.hasText("a"));

    }
}
