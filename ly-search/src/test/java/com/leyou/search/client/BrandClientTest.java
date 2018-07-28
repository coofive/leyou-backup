package com.leyou.search.client;

import com.leyou.item.pojo.Brand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 测试brand调用接口
 *
 * @author: cooFive
 * @CreateDate: 2018/7/28 14:05
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BrandClientTest {

    @Autowired
    private BrandClient brandClient;

    @Test
    public void testQueryByBrandId() {
        Brand brand = brandClient.queryByBrandId(7817L);
        System.out.println(brand);
    }
}