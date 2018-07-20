package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Simple to Introduction
 *
 * @author: cooFive
 * @CreateDate: 2018/7/15 16:46
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.leyou.item.mapper")
public class LyItemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LyItemApplication.class, args);
    }
}
