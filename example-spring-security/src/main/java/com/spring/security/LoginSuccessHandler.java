package com.spring.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //获取登录前的请求路径
        SavedRequest saved = new HttpSessionRequestCache().getRequest(request, response);
        String redirectUrl = (null != saved) ? saved.getRedirectUrl() : "";
        //如果之前有请求路径，登录成功后，还跳转到这个页面
        if (!redirectUrl.isEmpty()) {
            response.sendRedirect(redirectUrl);
            return;
        }
        //如果没有，则判断用户角色，跳转到不同的页面
        //获取用户信息
        Authentication authen = SecurityContextHolder.getContext().getAuthentication();
        for (GrantedAuthority authority : authen.getAuthorities()) {
            String role = authority.getAuthority();
            if ("ROLE_ADMIN".equals(role)) {
                redirectUrl = "/home/index";
                break;
            } else {
                redirectUrl = "/public/hello";
            }
        }
        response.sendRedirect(redirectUrl);
    }
}

