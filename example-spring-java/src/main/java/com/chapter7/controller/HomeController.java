package com.chapter7.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chapter7/home")
public class HomeController {

    /**
     * RedirectAttributes model 可以管理重定向的数据传递和Model的用法一致
     * model.addFlashAttributes()方法，可以添加数据
     * flash的特性是从请求开始到下一个请求结束有效
     * @param model
     * @return
     */
    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("chapter","第7章 Spring MVC的高级技术");
        return "chapter7/index";
    }
}
