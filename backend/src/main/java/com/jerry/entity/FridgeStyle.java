package com.jerry.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "fridge_style")
public class FridgeStyle {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "style_name", nullable = false, length = 50)
    private String styleName;
    
    @Column(name = "style_code", unique = true, length = 20)
    private String styleCode;
    
    @Column(name = "description", length = 200)
    private String description;
    
    @Column(name = "color", length = 20)
    private String color;
    
    @Column(name = "image_url", length = 500)
    private String imageUrl;
    
    @Column(name = "is_default")
    private Integer isDefault;
    
    @Column(name = "sort_order")
    private Integer sortOrder;
    
    @Column(name = "status")
    private Integer status;
    
    @Column(name = "created_time")
    private LocalDateTime createdTime;
    
    @Column(name = "updated_time")
    private LocalDateTime updatedTime;
}