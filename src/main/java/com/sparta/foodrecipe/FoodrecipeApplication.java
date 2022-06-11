package com.sparta.foodrecipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FoodrecipeApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodrecipeApplication.class, args);
    }

}
