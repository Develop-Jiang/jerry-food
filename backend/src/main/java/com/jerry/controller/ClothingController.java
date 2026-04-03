package com.jerry.controller;

import com.jerry.common.Result;
import com.jerry.entity.Clothing;
import com.jerry.service.ClothingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/clothing")
@Api(tags = "衣物管理")
public class ClothingController {
    
    @Autowired
    private ClothingService clothingService;
    
    @GetMapping("/list")
    @ApiOperation("获取所有衣物")
    public Result<List<Clothing>> getAllClothing(@RequestParam Long homeId) {
        return clothingService.getAllClothing(homeId);
    }
    
    @GetMapping("/category")
    @ApiOperation("按分类获取衣物")
    public Result<List<Clothing>> getClothingByCategory(@RequestParam Long homeId,
                                                        @RequestParam String category) {
        return clothingService.getClothingByCategory(homeId, category);
    }
    
    @GetMapping("/favorite")
    @ApiOperation("获取收藏的衣物")
    public Result<List<Clothing>> getFavoriteClothing(@RequestParam Long homeId) {
        return clothingService.getFavoriteClothing(homeId);
    }
    
    @PostMapping("/add")
    @ApiOperation("添加衣物（支持图片上传）")
    public Result<Clothing> addClothing(
            @ModelAttribute Clothing clothing,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        try {
            return clothingService.addClothing(clothing, imageFile);
        } catch (IOException e) {
            return Result.error("添加失败：" + e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    @ApiOperation("更新衣物信息")
    public Result<Clothing> updateClothing(
            @PathVariable Long id,
            @ModelAttribute Clothing clothing,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        try {
            return clothingService.updateClothing(id, clothing, imageFile);
        } catch (IOException e) {
            return Result.error("更新失败：" + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    @ApiOperation("删除衣物")
    public Result<Void> deleteClothing(@PathVariable Long id) {
        return clothingService.deleteClothing(id);
    }
    
    @PutMapping("/{id}/favorite")
    @ApiOperation("切换收藏状态")
    public Result<Clothing> toggleFavorite(@PathVariable Long id) {
        return clothingService.toggleFavorite(id);
    }
}