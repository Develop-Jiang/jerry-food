package com.jerry.repository;

import com.jerry.entity.WardrobeStyle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WardrobeStyleRepository extends JpaRepository<WardrobeStyle, Long> {
    
    List<WardrobeStyle> findByStatusOrderBySortOrderAsc(Integer status);
    
    WardrobeStyle findByIsDefault(Integer isDefault);
}