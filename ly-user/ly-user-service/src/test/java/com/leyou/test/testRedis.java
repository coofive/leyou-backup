package com.leyou.test;

import com.leyou.user.utils.CodecUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 测试redis
 *
 * @author: cooFive
 * @CreateDate: 2018/8/4 18:46
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class testRedis {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void testRedis() {
        this.redisTemplate.opsForValue().set("k", "v");
        String v = this.redisTemplate.opsForValue().get("k");
        System.out.println("v = " + v);
    }

    @Test
    public void testRedis2() {
        this.redisTemplate.opsForValue().set("时间", "10秒", 10, TimeUnit.SECONDS);
    }

    @Test
    public void testHash() {
        BoundHashOperations<String, Object, Object> hashOps = this.redisTemplate.boundHashOps("user");
        // 操作hash数据
        hashOps.put("name", "jack");
        hashOps.put("age", "21");

        // 获取单个数据
        Object name = hashOps.get("name");
        System.out.println("name = " + name);

        // 获取多个数据
        Map<Object, Object> map = hashOps.entries();

        for (Map.Entry<Object, Object> e : map.entrySet()) {
            System.out.println(e.getKey() + " = " + e.getValue());
        }

        // 获取所有数据
    }

    @Test
    public void testUtils() {
        String s = CodecUtils.md5Hex(null, "");
        System.out.println("s = " + s);
    }

}
