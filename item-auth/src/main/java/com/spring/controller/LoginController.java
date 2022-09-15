package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @GetMapping("/auth/login")
    @ResponseBody
    public String loginPage(Model model, HttpServletRequest request) {
        return "base-login";
    }
}
