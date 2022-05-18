package com.spring.controller;


import com.spring.config.CommPage;
import com.spring.config.Result;
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
    private IUsersService iUsersService;

    @GetMapping("/list")
    public Result<CommPage<Users>> getList(@RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                                           @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum){

        return Result.success(200,"成功",CommPage.restPage(iUsersService.list(pageSize,pageNum)));
    }

}
