package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 购物车微服务
 *
 * @author: cooFive
 * @CreateDate: 2018/8/8 19:49
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@SpringBootApplication
@EnableDiscoveryClient
public class LyCartApplication {
    public static void main(String[] args) {
        SpringApplication.run(LyCartApplication.class, args);
    }
}
