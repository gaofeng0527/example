package com.spring.template.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWordController {

    @RequestMapping("/hello")
    public String index(){
        return "index";
    }
}
