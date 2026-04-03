package com.jerry.repository;

import com.jerry.entity.Clothing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClothingRepository extends JpaRepository<Clothing, Long> {
    
    List<Clothing> findByHomeIdAndStatusOrderByCreatedTimeDesc(Long homeId, Integer status);
    
    List<Clothing> findByHomeIdAndCategoryAndStatusOrderByCreatedTimeDesc(Long homeId, String category, Integer status);
    
    List<Clothing> findByHomeIdAndFavoriteAndStatusOrderByCreatedTimeDesc(Long homeId, Integer favorite, Integer status);
    
    @Query("SELECT c FROM Clothing c WHERE c.homeId = :homeId AND c.status = 1 " +
           "AND (:category IS NULL OR c.category = :category) " +
           "AND (:season IS NULL OR c.season = :season) " +
           "ORDER BY c.createdTime DESC")
    List<Clothing> findWithFilters(@Param("homeId") Long homeId,
                                   @Param("category") String category,
                                   @Param("season") String season);
}