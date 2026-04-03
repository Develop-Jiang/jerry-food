package com.jerry.controller;

import com.jerry.common.Result;
import com.jerry.entity.HomeInfo;
import com.jerry.service.HomeInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
@Api(tags = "家庭信息管理")
public class HomeController {
    
    @Autowired
    private HomeInfoService homeInfoService;
    
    @GetMapping("/info")
    @ApiOperation("获取家庭信息")
    public Result<HomeInfo> getHomeInfo() {
        return homeInfoService.getHomeInfo();
    }
    
    @PutMapping("/{id}/name")
    @ApiOperation("更新家庭名称")
    public Result<HomeInfo> updateHomeName(@PathVariable Long id, 
                                          @RequestParam String homeName) {
        return homeInfoService.updateHomeName(id, homeName);
    }
    
    @PutMapping("/{id}/styles")
    @ApiOperation("更新衣柜和冰箱样式")
    public Result<HomeInfo> updateStyles(@PathVariable Long id,
                                        @RequestParam Long wardrobeStyleId,
                                        @RequestParam Long fridgeStyleId) {
        return homeInfoService.updateStyles(id, wardrobeStyleId, fridgeStyleId);
    }
}