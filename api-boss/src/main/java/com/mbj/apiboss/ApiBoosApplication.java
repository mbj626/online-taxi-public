package com.mbj.apiboss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-25 14:03
 * @Description:
 * @Version:
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ApiBoosApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiBoosApplication.class);
    }
}
