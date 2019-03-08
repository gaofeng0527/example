package com.chapter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(Model model) {
        model.addAttribute("uname", "高峰");
        return "home";
    }

    @RequestMapping("admin/{userName}")
    public String userDetail(@PathVariable String userName, Model model) {
        model.addAttribute("userName", userName);
        System.out.println(userName);
        return "admin/user";
    }
}
