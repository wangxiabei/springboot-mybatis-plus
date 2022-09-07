package com.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(("/my"))
public class MyController {

    @GetMapping("/test")
    @ResponseBody
    public String test(){
        return "你好";
    }
}
