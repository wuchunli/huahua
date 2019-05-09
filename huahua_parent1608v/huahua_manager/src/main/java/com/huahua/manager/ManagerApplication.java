package com.huahua.manager;

import huahua.common.utils.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient   //该注释可省略
@EnableZuulProxy
public class ManagerApplication {

    public static void main(String[] args) {

        SpringApplication.run(ManagerApplication.class);

    }

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }
}