package com.spring.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.spring.dto.UserDto;
import com.spring.mapper.UsersMapper;
import com.spring.model.Users;
import com.spring.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/session")
public class LoginController {
    @Autowired
    private IUsersService usersService;
    @Autowired
    private UsersMapper usersMapper;



    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody UserDto userDto){
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","phone").like("phone",userDto.getPhone());
        Users user = usersService.getOne(queryWrapper);
        System.out.println("user"+user);
        return "成功啦啊哈哈哈哈嗯嗯嗯哒哒哒";
    }
}
