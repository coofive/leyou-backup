package com.leyou.search.client;

import com.leyou.item.pojo.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 测试category调用接口
 *
 * @author: cooFive
 * @CreateDate: 2018/7/28 13:23
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryClientTest {

    @Autowired
    private CategoryClient categoryClient;

    @Test
    public void testQueryByCids() {
        ArrayList<Long> list = new ArrayList<>();
        list.add(74L);
        list.add(75L);
        list.add(76L);
        List<Category> categories = categoryClient.queryByCids(list);
        categories.forEach(c -> System.out.println(c));
    }
}