package com.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("凌晨四点");
        System.out.println("玫瑰花开");
        System.out.println("此时你应该在我身边");
    }
}
