package com.jerry.service;

import com.jerry.common.Result;
import com.jerry.entity.FridgeStyle;
import com.jerry.repository.FridgeStyleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FridgeStyleService {
    
    @Autowired
    private FridgeStyleRepository fridgeStyleRepository;
    
    public Result<List<FridgeStyle>> getAllStyles() {
        List<FridgeStyle> styles = fridgeStyleRepository.findByStatusOrderBySortOrderAsc(1);
        return Result.success(styles);
    }
    
    public Result<FridgeStyle> getStyleById(Long id) {
        return fridgeStyleRepository.findById(id)
                .map(style -> Result.success(style))
                .orElse(Result.error("未找到冰箱样式"));
    }
    
    public Result<FridgeStyle> getDefaultStyle() {
        FridgeStyle style = fridgeStyleRepository.findByIsDefault(1);
        if (style != null) {
            return Result.success(style);
        }
        return Result.error("未找到默认冰箱样式");
    }
}