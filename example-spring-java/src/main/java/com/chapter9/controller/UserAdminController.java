package com.chapter9.controller;

import com.chapter.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/home/user")
public class UserAdminController {

    @GetMapping("/register")
    public String registerForm() {
        return "userForm";
    }

    /**
     * 1.RedirectAttributes 继承于 Model，但是它提供了addFlishAttribute方法，可以借助flash属性跨重定向传递数据
     * 2.另外一般注册成功后，都会重定向到新的页面，以防止用户刷新页面重复提交
     * 3.可以使用‘redirect:/’的形式
     * 4.也可以使用模板路径的形式传递数据，前提是需要model.addAttribute(filed)，提前存储到model中  类似：redirect:/home/user/detail/{firstName}
     * @param user
     * @param model
     * @return
     */
    @PostMapping("/register")
    public String regsiter(User user, RedirectAttributes model) {
        System.out.println(user.toString());
        model.addAttribute("firstName", user.getFirstName());
        model.addFlashAttribute("user", user);
        return "redirect:/home/user/detail/{firstName}";
    }

    /**
     * 1.可以先判断在model中是否存在需要的属性，如果没有，创建
     * @param firstName
     * @param model
     * @return
     */
    @GetMapping("/detail/{firstName}")
    public String detail(@PathVariable String firstName, Model model) {
        if (!model.containsAttribute("user")) {
            User user = new User();
            user.setFirstName(firstName);
            model.addAttribute("user", user);
        }
        return "detail";
    }
}
