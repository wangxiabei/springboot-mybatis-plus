package com.spring.controller;


import com.spring.config.CommPage;
import com.spring.config.Result;
import com.spring.model.auto.MallMenus;
import com.spring.service.IMallMenusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author beicheng
 * @since 2022-01-13
 */
@RestController
@RequestMapping("/mall-menus")
public class MallMenusController {
    @Autowired
    IMallMenusService mallMenusService;

    @GetMapping("/getOne")
    public Result getById(@RequestParam Long id){
        return Result.success(200,"成功",mallMenusService.getById(id));
    }
    @GetMapping("/all")
    public Result findAll(){
        return  Result.success(200,"成功",mallMenusService.list());
    }


    @PostMapping("/insert")
    public Result createMenu(@RequestBody MallMenus mallMenus){
        boolean result = mallMenusService.createMenu(mallMenus);
        if (result){
            return Result.success(200,"成功",null);
        }else {
            return Result.fail(500,"失败",null);
        }
    }
    @GetMapping("/getListByParent/{parentId}")
    public Result<CommPage<MallMenus>> getList(@PathVariable Long parentId,
                                    @RequestParam(value = "PageSize", defaultValue = "5") Integer pageSize,
                                    @RequestParam(value = "PageNum", defaultValue = "1") Integer pageNum){
        return Result.success(200,"成功", CommPage.restPage(mallMenusService.list(parentId,pageSize,pageNum)));
    }

    @GetMapping("/getList")
    public Result<CommPage<MallMenus>> list(@RequestParam(value = "PageSize", defaultValue = "5") Integer pageSize,
                                            @RequestParam(value = "PageNum", defaultValue = "1") Integer pageNum){
        return Result.success(200,"成功", CommPage.restPage(mallMenusService.getList(pageSize,pageNum)));
    }

    @PostMapping("/delete/{id}")
    public Result deleteById(@PathVariable Long id){
        return Result.success(200,"成功",mallMenusService.deleteMenu(id));
    }

    @PostMapping("/update/{id}")
    public Result updateById(@PathVariable Long id, @RequestBody MallMenus mallMenus){
        return Result.success(200,"成功",mallMenusService.updateMenu(id,mallMenus));
    }

}
