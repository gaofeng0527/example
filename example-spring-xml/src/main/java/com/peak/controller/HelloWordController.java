package com.peak.controller;

import com.peak.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HelloWordController {

    @Autowired
    CartService cartService;


    /**
     * hello word
     * @return
     */
    @GetMapping("/api/json")
    @ResponseBody
    public Map<String,Object> index(){
        Map<String,Object> map = new HashMap<>();
        map.put("name","张高峰");
        map.put("age","29");
        cartService.addProduct();
        return map;
    }

    @GetMapping("/page/index")
    public String indexPage(Model model){
        //model.addAttribute("name","张高峰");
        return "index";
    }
}
