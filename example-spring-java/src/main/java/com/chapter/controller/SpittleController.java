package com.chapter.controller;

import com.chapter.entity.Spittle;
import com.chapter.entity.User;
import com.chapter.service.SpittleResporty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
public class SpittleController {

    @Autowired
    SpittleResporty spittleResporty;

    @RequestMapping("/spittles")
    public String spittles(Model model) {
        List<Spittle> list = spittleResporty.findSpittles(Long.MAX_VALUE, 10);
        model.addAttribute("slist", list);
        return "spittle/spittles";
    }

    @RequestMapping(value = "/spitter/register", method = RequestMethod.GET)
    public String register() {

        return "spittle/spitterRegisterForm";
    }

    @RequestMapping(value = "/spitter/register", method = RequestMethod.POST)
    public String registerSpitter(@Valid User user, BindingResult result) {
        if(result.hasErrors()){
            System.out.println(result.toString());
            System.out.println(user.toString());
        }

        return "redirect:/admin/"+user.getUserName();

    }
}
