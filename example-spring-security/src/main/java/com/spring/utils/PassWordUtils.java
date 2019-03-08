package com.spring.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PassWordUtils {
    private static PasswordEncoder pwd = new BCryptPasswordEncoder();

    public static void main(String[] args) {
        PasswordEncoder pwd = new BCryptPasswordEncoder();
        String encrypt = pwd.encode("gaofeng");
        System.out.println(encrypt);
        //$2a$10$gWQXHMX3kkAcgwMqwBS7QOFmKPfjqt0nRIsxvvIrtotQ61ZL4ciGW
        //$2a$10$cQergJpPc.tZ8l7vVwDZAujplw0qatCRF/YyGlPjZWRs0L/jgbWhK
        System.out.println(pwd.matches("gaofeng", "$2a$10$cQergJpPc.tZ8l7vVwDZAujplw0qatCRF/YyGlPjZWRs0L/jgbWhK"));
    }

    /**
     * 返回密文密码
     * 加密方式：BCrypt
     *
     * @param password
     * @return
     */
    public static String getEncodePassword(String password) {
        return pwd.encode(password);
    }

    /**
     * 验证密码是否正确
     * @param password
     * @param encodePassword
     * @return
     */
    public static boolean checkPassword(String password, String encodePassword) {
        return pwd.matches(password, encodePassword);
    }


}
