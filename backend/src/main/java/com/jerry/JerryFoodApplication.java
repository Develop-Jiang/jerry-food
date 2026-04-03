package com.jerry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class JerryFoodApplication {
    public static void main(String[] args) {
        SpringApplication.run(JerryFoodApplication.class, args);
        System.out.println("============================================");
        System.out.println("   🏠 Jerry Food Backend Service Started!  ");
        System.out.println("   📍 http://localhost:8080/api            ");
        System.out.println("   📚 Swagger: http://localhost:8080/swagger-ui.html");
        System.out.println("============================================");
    }
}