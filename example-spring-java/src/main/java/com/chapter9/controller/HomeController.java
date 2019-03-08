package com.chapter9.controller;

import com.chapter.entity.User;
import com.util.ImageBase64Util;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("chapter","第 九 章 保护WEB应用");
        return "index";
    }

    @GetMapping("/register")
    public String register(){
        return "registerForm";
    }

    @PostMapping("/register")
    public String saveUser(@RequestParam("firstName") String firstName, @RequestParam("file") MultipartFile file, RedirectAttributes model) throws IOException {
        System.out.println(file.toString());
        file.transferTo(new File(firstName+".jpg"));
        model.addFlashAttribute("firstName",firstName);
        return "detail";
    }

    @GetMapping("/userdetails/{firstName}")
    public String userDetail(@PathVariable String firstName, Model model) throws IOException {
        if(!model.containsAttribute("firstName")){
            User user = new User();
            user.setFirstName(firstName);
            user.setHead(ImageBase64Util.imageToBase64String("D:\\upload\\test"+firstName+".jpg"));
        }
        return "detail";
    }

    @GetMapping("/upload")
    public String upload(){
        return "upload";
    }

    @PostMapping("/upload")
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("file.getName:  "+file.getName());
        System.out.println("file.ContentType:  " + file.getContentType());
        System.out.println("file.orginalFileName: " + file.getOriginalFilename());
        file.transferTo(new File("D:\\upload\\test\\"+file.getOriginalFilename()));

        return file.getOriginalFilename();
    }
}
