package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 搜索服务
 *
 * @author: cooFive
 * @CreateDate: 2018/7/26 15:44
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LySearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(LySearchApplication.class, args);
    }
}
