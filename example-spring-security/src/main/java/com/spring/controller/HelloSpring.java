package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/public")
@Controller
public class HelloSpring {

    @RequestMapping("/hello")
    public String hello(Model model){
        model.addAttribute("name","张高峰");
        return "index";
    }
}
