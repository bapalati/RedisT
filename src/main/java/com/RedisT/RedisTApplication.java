package com.RedisT;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

//@EnableJms
@SpringBootApplication
public class RedisTApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisTApplication.class, args);
    }

}
