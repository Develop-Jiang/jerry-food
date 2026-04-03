package com.jerry.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "home_info")
public class HomeInfo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "home_name", nullable = false, length = 100)
    private String homeName;
    
    @Column(name = "wardrobe_style_id")
    private Long wardrobeStyleId;
    
    @Column(name = "fridge_style_id")
    private Long fridgeStyleId;
    
    @Column(name = "created_time")
    private LocalDateTime createdTime;
    
    @Column(name = "updated_time")
    private LocalDateTime updatedTime;
    
    @Column(name = "is_deleted")
    private Integer isDeleted;
}