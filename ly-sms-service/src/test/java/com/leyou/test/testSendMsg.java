package com.leyou.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试短信发送是否成功
 *
 * @author: cooFive
 * @CreateDate: 2018/8/4 16:47
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class testSendMsg {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void testSend() {
        Map<String, String> map = new HashMap<>();
        map.put("phone", "13032192795");
        map.put("code", "993843");
        this.amqpTemplate.convertAndSend("ly.sms.exchange", "sms.register.code", map);
    }
}
