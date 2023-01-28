package com.mbj.servicedriveruser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-25 14:03
 * @Description:
 * @Version:
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.mbj.servicedriveruser.mapper")
public class ServiceDriverUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceDriverUserApplication.class);
    }
}
