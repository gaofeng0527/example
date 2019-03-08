package com.peak.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ParameterController {

    /**
     * @param uid
     * @return
     * @PathVariable注解的使用 1.该注解可以获取URL路径中匹配的参数
     * 2.PathVariable("userId")中的userId可以省略，当参数名字和{}中的名字不一致时不可省略
     * URL:localhost:8080/user/details/123
     */
    @GetMapping("/user/details/{userId}")
    @ResponseBody
    public String userDetails(@PathVariable("userId") long uid) {
        return "userID:" + uid;
    }

    @GetMapping("/page/details/{pageId}")
    @ResponseBody
    public String pageDetails(@PathVariable Long pageId) {
        return "pageID:" + pageId;
    }

    @RequestMapping("/write/cookie")
    @ResponseBody
    public String writeCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("username", "Don't tell me");
        cookie.setMaxAge(10000);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "write cookie username";
    }

    /**
     * 在添加：cookie.setPath("/");这一行代码之前，读取不到cookie，因为cookie默认的作用域是在同一路径下有效
     * 添加这一行代码后，是在同一域名下有效
     * The cookie is visible to all the pages in the directory you specify,
     * and all the pages in that directory's subdirectories.
     * A cookie's path must include the servlet that set the cookie,
     * for example, <i>/catalog</i>,
     * which makes the cookie visible to all directories on the server under <i>/catalog</i>.
     * @param cookie
     * @return
     */
    @RequestMapping("/read/cookie")
    @ResponseBody
    public String readCookie(@CookieValue(value = "username",defaultValue = "") String cookie) {
        return "cookie:username ==> "+cookie;
    }

    @RequestMapping("/cookie")
    @ResponseBody
    public String readCookie(HttpServletRequest request){
        Cookie[] ck = request.getCookies();
        for (Cookie c : ck){
            System.out.println(c.getValue()+"======"+c.getName());
        }

        return "sss";

    }


}
