package com.example.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class IndexController {

    @GetMapping(path = "/")
    public HashMap index() {
        // get a successful user login
        OAuth2User user = ((OAuth2User)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return new HashMap(){{
            put("hello", user.getAttribute("name"));
            put("your email is", user.getAttribute("email"));
        }};
    }


    @GetMapping(path = "/unauthenticated")
    public HashMap unauthenticatedRequests() {
        return new HashMap(){{
            put("this is ", "unauthenticated endpoint");
        }};
    }

    @GetMapping("/logout")
    public String terminateSession(HttpServletRequest request) {
        try {
            // 使当前会话无效
            request.getSession().invalidate();

            // 注销用户的安全上下文
            request.logout();

            // 可选：清除SecurityContext
            SecurityContextHolder.clearContext();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 重定向到登录页面或其他页面
        return "redirect:/login";
    }
}
