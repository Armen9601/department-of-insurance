package com.example.sintezatorservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SintezatorServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SintezatorServiceApplication.class, args);
    }

}
