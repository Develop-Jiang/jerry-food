package com.jerry.repository;

import com.jerry.entity.FridgeStyle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FridgeStyleRepository extends JpaRepository<FridgeStyle, Long> {
    
    List<FridgeStyle> findByStatusOrderBySortOrderAsc(Integer status);
    
    FridgeStyle findByIsDefault(Integer isDefault);
}