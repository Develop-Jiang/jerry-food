package com.jerry.service;

import com.jerry.common.Result;
import com.jerry.entity.WardrobeStyle;
import com.jerry.repository.WardrobeStyleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WardrobeStyleService {
    
    @Autowired
    private WardrobeStyleRepository wardrobeStyleRepository;
    
    public Result<List<WardrobeStyle>> getAllStyles() {
        List<WardrobeStyle> styles = wardrobeStyleRepository.findByStatusOrderBySortOrderAsc(1);
        return Result.success(styles);
    }
    
    public Result<WardrobeStyle> getStyleById(Long id) {
        return wardrobeStyleRepository.findById(id)
                .map(style -> Result.success(style))
                .orElse(Result.error("未找到衣柜样式"));
    }
    
    public Result<WardrobeStyle> getDefaultStyle() {
        WardrobeStyle style = wardrobeStyleRepository.findByIsDefault(1);
        if (style != null) {
            return Result.success(style);
        }
        return Result.error("未找到默认衣柜样式");
    }
}