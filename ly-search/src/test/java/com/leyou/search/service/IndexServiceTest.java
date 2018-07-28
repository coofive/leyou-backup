package com.leyou.search.service;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Spu;
import com.leyou.search.client.GoodsClient;
import com.leyou.search.pojo.Goods;
import com.leyou.search.repository.GoodsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试索引操作
 *
 * @author: cooFive
 * @CreateDate: 2018/7/28 14:27
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IndexServiceTest {

    @Autowired
    private IndexService indexService;

    @Autowired
    private ElasticsearchTemplate template;

    @Autowired
    private GoodsRepository repository;

    private Spu spu;

    @Autowired
    private GoodsClient goodsClient;

    @Before
    public void setUp() {
        spu = goodsClient.querySpuBySpuId(187L);
    }

    @Test
    public void createIndex() {
    }

    @Test
    public void buildGoods() {
        Goods goods = indexService.buildGoods(spu);
        System.out.println(goods);
    }

    @Test
    public void loadData() {
        // 创建索引
        template.createIndex(Goods.class);

        // 配置映射
        template.putMapping(Goods.class);

        // 查询分页数据
        Integer page = 0;
        Integer rows = 100;
        Integer size = 0;
        do {
            PageResult<Spu> pageResult = this.goodsClient.querySpuByPage(page, rows, true, null);

            List<Spu> items = pageResult.getItems();
            size = items.size();

            // 创建Goods集合
            List<Goods> goodsList = new ArrayList<>();
            items.forEach(spu -> {
                Goods goods = this.indexService.buildGoods(spu);
                goodsList.add(goods);
            });

            this.repository.saveAll(goodsList);

            page++;
        } while (size == 100);
    }
}