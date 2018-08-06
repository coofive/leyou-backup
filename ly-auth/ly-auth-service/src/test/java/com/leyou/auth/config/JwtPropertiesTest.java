package com.leyou.auth.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Simple to Introduction
 *
 * @author: cooFive
 * @CreateDate: 2018/8/6 11:41
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@EnableConfigurationProperties(JwtProperties.class)
public class JwtPropertiesTest {

    @Autowired
    private JwtProperties props;

    @Test
    public void init() {
        System.out.println(props.getPublicKey());
        
    }
}