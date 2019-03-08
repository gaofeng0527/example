package com.spring.user.impl;

import com.spring.user.User;
import com.spring.user.UserService;

import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {

    //模拟数据
    private static Map<String, User> users = new HashMap<String, User>();

    static {
        users.put("admin", new User("admin", "{noop}admin", "ROLE_ADMIN","ROLE_USER"));
        users.put("edu", new User("edu", "{noop}edu", "ROLE_USER"));
        users.put("gaofeng", new User("gaofeng", "{bcrypt}$2a$10$gWQXHMX3kkAcgwMqwBS7QOFmKPfjqt0nRIsxvvIrtotQ61ZL4ciGW", "ROLE_USER"));

    }

    @Override
    public User findUserByUserName(String username) {
        return users.get(username);
    }
}
