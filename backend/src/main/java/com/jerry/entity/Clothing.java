package com.jerry.entity;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "clothing")
public class Clothing {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    @Column(name = "category", length = 50)
    private String category;
    
    @Column(name = "brand", length = 100)
    private String brand;
    
    @Column(name = "size", length = 20)
    private String size;
    
    @Column(name = "color", length = 30)
    private String color;
    
    @Column(name = "season", length = 20)
    private String season;
    
    @Column(name = "material", length = 100)
    private String material;
    
    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;
    
    @Column(name = "purchase_date")
    private LocalDate purchaseDate;
    
    @Column(name = "image_url", length = 500)
    private String imageUrl;
    
    @Column(name = "thumbnail_url", length = 500)
    private String thumbnailUrl;
    
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
    
    @Column(name = "home_id")
    private Long homeId;
    
    @Column(name = "wardrobe_position")
    private Integer wardrobePosition;
    
    @Column(name = "favorite")
    private Integer favorite;
    
    @Column(name = "wear_count")
    private Integer wearCount;
    
    @Column(name = "status")
    private Integer status;
    
    @Column(name = "created_time")
    private LocalDateTime createdTime;
    
    @Column(name = "updated_time")
    private LocalDateTime updatedTime;
}