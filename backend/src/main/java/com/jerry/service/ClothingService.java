package com.jerry.service;

import com.jerry.common.Result;
import com.jerry.entity.Clothing;
import com.jerry.repository.ClothingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ClothingService {
    
    @Autowired
    private ClothingRepository clothingRepository;
    
    @Value("${file.upload.path}")
    private String uploadPath;
    
    public Result<List<Clothing>> getAllClothing(Long homeId) {
        List<Clothing> clothings = clothingRepository.findByHomeIdAndStatusOrderByCreatedTimeDesc(homeId, 1);
        return Result.success(clothings);
    }
    
    public Result<List<Clothing>> getClothingByCategory(Long homeId, String category) {
        List<Clothing> clothings = clothingRepository.findByHomeIdAndCategoryAndStatusOrderByCreatedTimeDesc(homeId, category, 1);
        return Result.success(clothings);
    }
    
    public Result<List<Clothing>> getFavoriteClothing(Long homeId) {
        List<Clothing> clothings = clothingRepository.findByHomeIdAndFavoriteAndStatusOrderByCreatedTimeDesc(homeId, 1, 1);
        return Result.success(clothings);
    }
    
    @Transactional
    public Result<Clothing> addClothing(Clothing clothing, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = saveImage(imageFile);
            clothing.setImageUrl(imageUrl);
        }
        
        clothing.setStatus(1);
        clothing.setFavorite(0);
        clothing.setWearCount(0);
        
        Clothing savedClothing = clothingRepository.save(clothing);
        return Result.success("衣物添加成功", savedClothing);
    }
    
    @Transactional
    public Result<Clothing> updateClothing(Long id, Clothing clothing, MultipartFile imageFile) throws IOException {
        return clothingRepository.findById(id)
                .map(existing -> {
                    existing.setName(clothing.getName());
                    existing.setCategory(clothing.getCategory());
                    existing.setBrand(clothing.getBrand());
                    existing.setSize(clothing.getSize());
                    existing.setColor(clothing.getColor());
                    existing.setSeason(clothing.getSeason());
                    existing.setMaterial(clothing.getMaterial());
                    existing.setPrice(clothing.getPrice());
                    existing.setPurchaseDate(clothing.getPurchaseDate());
                    existing.setNotes(clothing.getNotes());
                    existing.setWardrobePosition(clothing.getWardrobePosition());
                    
                    if (imageFile != null && !imageFile.isEmpty()) {
                        try {
                            String imageUrl = saveImage(imageFile);
                            existing.setImageUrl(imageUrl);
                        } catch (IOException e) {
                            throw new RuntimeException("图片上传失败", e);
                        }
                    }
                    
                    Clothing updated = clothingRepository.save(existing);
                    return Result.success("衣物更新成功", updated);
                })
                .orElse(Result.error("未找到衣物"));
    }
    
    @Transactional
    public Result<Void> deleteClothing(Long id) {
        return clothingRepository.findById(id)
                .map(clothing -> {
                    clothing.setStatus(0);
                    clothingRepository.save(clothing);
                    return Result.<Void>success("衣物删除成功");
                })
                .orElse(Result.error("未找到衣物"));
    }
    
    @Transactional
    public Result<Clothing> toggleFavorite(Long id) {
        return clothingRepository.findById(id)
                .map(clothing -> {
                    clothing.setFavorite(clothing.getFavorite() == 1 ? 0 : 1);
                    Clothing updated = clothingRepository.save(clothing);
                    return Result.success(updated.getFavorite() == 1 ? "已收藏" : "取消收藏", updated);
                })
                .orElse(Result.error("未找到衣物"));
    }
    
    private String saveImage(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = UUID.randomUUID().toString() + extension;
        
        File uploadDir = new File(uploadPath + "clothing/");
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        
        String filePath = uploadPath + "clothing/" + newFilename;
        file.transferTo(new File(filePath));
        
        return "/uploads/clothing/" + newFilename;
    }
}