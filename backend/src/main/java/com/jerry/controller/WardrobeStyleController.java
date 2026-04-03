package com.jerry.controller;

import com.jerry.common.Result;
import com.jerry.entity.WardrobeStyle;
import com.jerry.service.WardrobeStyleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wardrobe")
@Api(tags = "衣柜样式管理")
public class WardrobeStyleController {
    
    @Autowired
    private WardrobeStyleService wardrobeStyleService;
    
    @GetMapping("/styles")
    @ApiOperation("获取所有衣柜样式")
    public Result<List<WardrobeStyle>> getAllStyles() {
        return wardrobeStyleService.getAllStyles();
    }
    
    @GetMapping("/styles/{id}")
    @ApiOperation("根据ID获取衣柜样式")
    public Result<WardrobeStyle> getStyleById(@PathVariable Long id) {
        return wardrobeStyleService.getStyleById(id);
    }
    
    @GetMapping("/default-style")
    @ApiOperation("获取默认衣柜样式")
    public Result<WardrobeStyle> getDefaultStyle() {
        return wardrobeStyleService.getDefaultStyle();
    }
}