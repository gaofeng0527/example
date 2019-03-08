package com.spring.template.controller;

import com.spring.template.user.Account;
import com.spring.template.user.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/public/user")
public class UserController {

    @Autowired
    AccountService aservice;

    /**
     * 访问注册页面
     * @return
     */
    @GetMapping("/register")
    public String register(){
        return "register";
    }

    /**
     * 注册用户
     * @param account
     * @return
     */
    @PostMapping("/register")
    public String register(Account account){
        aservice.save(account);
        return "success";
    }
}
