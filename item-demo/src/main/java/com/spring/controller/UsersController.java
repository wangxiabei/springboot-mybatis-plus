package com.spring.controller;


import com.spring.config.CommPage;
import com.spring.config.Result;
import com.spring.feign.FeignClientDemo;
import com.spring.model.Users;
import com.spring.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bei_ch
 * @since 2022-02-17
 */
@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private FeignClientDemo demo;


    @GetMapping("/getName")
    public String getName(){
        String something = demo.getSomething();
        System.out.println("是的呢 我是userController里面的name呢:"+something);
        return "";
    }

}
