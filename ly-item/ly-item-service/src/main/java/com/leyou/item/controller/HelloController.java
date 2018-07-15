package com.leyou.item.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple to Introduction
 *
 * @author: cooFive
 * @CreateDate: 2018/7/15 16:55
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@RestController
public class HelloController {

    @GetMapping("hello")
    public String helloSpringBoot() {
        return "hello spring cloud!";
    }
}
