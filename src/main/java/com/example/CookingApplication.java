package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.example.mapper.Common")
public class CookingApplication {

    public static void main(String[] args) {

        SpringApplication.run(CookingApplication.class, args);
    }

}
