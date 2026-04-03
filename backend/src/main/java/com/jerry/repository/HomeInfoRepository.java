package com.jerry.repository;

import com.jerry.entity.HomeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface HomeInfoRepository extends JpaRepository<HomeInfo, Long> {
    
    Optional<HomeInfo> findByIsDeleted(Integer isDeleted);
}