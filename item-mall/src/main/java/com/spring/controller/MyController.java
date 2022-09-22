package com.spring.controller;

import com.spring.bean.LoginUser;
import com.spring.utils.LoginUserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(("/mall"))
@Slf4j
public class MyController {
    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("/getList")
    @ResponseBody
    @PreAuthorize("hasAuthority(['11'])")
    public String test() throws Exception {
        LoginUser user =  LoginUserUtils.getLoginUser();
        log.info("MyController里面user"+user);
        log.info("MyController里面user的权限数组"+user.getAuthList());
        return "你好";
    }


    @GetMapping("/sendMessage/{id}")
    public String sendMessage(@PathVariable Integer id){
        for (int i = 1; i<= 5; i++){
            redisTemplate.convertAndSend("channel:set",String.format("我是消息{%d}号",i,new Date()));
        }
        return "sendMessage";
    }
}
