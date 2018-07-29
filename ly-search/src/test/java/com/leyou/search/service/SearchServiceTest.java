package com.leyou.search.service;

import com.leyou.common.pojo.PageResult;
import com.leyou.search.pojo.Goods;
import com.leyou.search.pojo.SearchRequest;
import com.leyou.search.pojo.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 测试搜索处理业务
 *
 * @author: cooFive
 * @CreateDate: 2018/7/28 20:24
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchServiceTest {

    @Autowired
    SearchService searchService;

    @Test
    public void queryByPage() {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setKey("手机");
        SearchResult<Goods> goodsPageResult = searchService.queryByPage(searchRequest);
        System.out.println(goodsPageResult);
    }
}