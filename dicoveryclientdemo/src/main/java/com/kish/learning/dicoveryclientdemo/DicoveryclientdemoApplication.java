package com.kish.learning.dicoveryclientdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DicoveryclientdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DicoveryclientdemoApplication.class, args);
    }

}
