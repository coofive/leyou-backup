package com.leyou.cart.config;

import com.leyou.cart.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Simple to Introduction
 *
 * @author: cooFive
 * @CreateDate: 2018/8/8 20:05
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private JwtProperties props;

    /**
     * 先将Interceptor交给spring管理
     * 有可能configuration先执行，导致获取到的为null，故直接注入bean
     *
     * @return
     */
    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor(props);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.loginInterceptor()).addPathPatterns("/**");
    }
}
