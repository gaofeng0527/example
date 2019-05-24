package com.spring.template.test;

import java.io.File;

public class PathTest {

    public static void main(String[] args) {
        String path = "C:\\Users\\Administrator\\Desktop\\00001\\contents\\zt_00001_2019.html";
        File file = new File(path);
        System.out.println("getPath:"+file.getPath());
        System.out.println("getAbsolutePath:"+file.getAbsolutePath());
    }
}
