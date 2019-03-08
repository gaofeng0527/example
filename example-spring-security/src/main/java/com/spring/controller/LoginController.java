package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 登录控制类
 */
@Controller
public class LoginController {

    /**
     * 登录失败和注销成功都会跳转到登录页面
     *
     * @param error
     * @param logout
     * @param model
     * @return
     */
    @GetMapping("/loginPage")
    public String loginPage(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout, Model model) {

        if (error != null) {
            model.addAttribute("error", "用户名或密码错误");
        }

        if (logout != null) {
            model.addAttribute("logout", "注销成功");
        }

        return "/login/login";
    }

    /**
     * 处理没有权限访问的请求
     *
     * @return
     */
    @GetMapping("/deny")
    @ResponseBody
    public String denyPage() {
        return "You have no permission to access the page";
    }
}
