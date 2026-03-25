package com.deliverytech;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCaching
public class DeliveryTechApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeliveryTechApiApplication.class, args);
    }
}
