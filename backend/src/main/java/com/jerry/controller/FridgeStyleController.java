package com.jerry.controller;

import com.jerry.common.Result;
import com.jerry.entity.FridgeStyle;
import com.jerry.service.FridgeStyleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fridge")
@Api(tags = "冰箱样式管理")
public class FridgeStyleController {
    
    @Autowired
    private FridgeStyleService fridgeStyleService;
    
    @GetMapping("/styles")
    @ApiOperation("获取所有冰箱样式")
    public Result<List<FridgeStyle>> getAllStyles() {
        return fridgeStyleService.getAllStyles();
    }
    
    @GetMapping("/styles/{id}")
    @ApiOperation("根据ID获取冰箱样式")
    public Result<FridgeStyle> getStyleById(@PathVariable Long id) {
        return fridgeStyleService.getStyleById(id);
    }
    
    @GetMapping("/default-style")
    @ApiOperation("获取默认冰箱样式")
    public Result<FridgeStyle> getDefaultStyle() {
        return fridgeStyleService.getDefaultStyle();
    }
}