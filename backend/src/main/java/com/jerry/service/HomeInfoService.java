package com.jerry.service;

import com.jerry.common.Result;
import com.jerry.entity.HomeInfo;
import com.jerry.repository.HomeInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class HomeInfoService {
    
    @Autowired
    private HomeInfoRepository homeInfoRepository;
    
    public Result<HomeInfo> getHomeInfo() {
        Optional<HomeInfo> homeOptional = homeInfoRepository.findByIsDeleted(0);
        if (homeOptional.isPresent()) {
            return Result.success(homeOptional.get());
        }
        return Result.error("未找到家庭信息");
    }
    
    @Transactional
    public Result<HomeInfo> updateHomeName(Long id, String homeName) {
        Optional<HomeInfo> homeOptional = homeInfoRepository.findById(id);
        if (homeOptional.isPresent()) {
            HomeInfo homeInfo = homeOptional.get();
            homeInfo.setHomeName(homeName);
            homeInfo = homeInfoRepository.save(homeInfo);
            return Result.success("家庭名称更新成功", homeInfo);
        }
        return Result.error("未找到家庭信息");
    }
    
    @Transactional
    public Result<HomeInfo> updateStyles(Long id, Long wardrobeStyleId, Long fridgeStyleId) {
        Optional<HomeInfo> homeOptional = homeInfoRepository.findById(id);
        if (homeOptional.isPresent()) {
            HomeInfo homeInfo = homeOptional.get();
            homeInfo.setWardrobeStyleId(wardrobeStyleId);
            homeInfo.setFridgeStyleId(fridgeStyleId);
            homeInfo = homeInfoRepository.save(homeInfo);
            return Result.success("样式更新成功", homeInfo);
        }
        return Result.error("未找到家庭信息");
    }
}