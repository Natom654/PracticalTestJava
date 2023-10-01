package com.example.practtestjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class PractTestJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PractTestJavaApplication.class, args);
    }

}
