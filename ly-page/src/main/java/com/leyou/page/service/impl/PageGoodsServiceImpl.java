package com.leyou.page.service.impl;

import com.leyou.item.pojo.*;
import com.leyou.page.client.BrandClient;
import com.leyou.page.client.CategoryClient;
import com.leyou.page.client.GoodsClient;
import com.leyou.page.client.SpecClient;
import com.leyou.page.service.PageGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品详情页业务处理实现
 *
 * @author: cooFive
 * @CreateDate: 2018/8/1 14:38
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Service
public class PageGoodsServiceImpl implements PageGoodsService {

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private SpecClient specClient;

    /**
     * 通过spuId查询商品详情
     *
     * @param spuId 商品集id
     * @return
     */
    @Override
    public Map<String, Object> addGoodsToMap(Long spuId) {
        Map<String, Object> map = new HashMap<>();

        // 查询商品集
        Spu spu = this.goodsClient.querySpuBySpuId(spuId);

        // 查询页面需要的面包屑功能,即商品分类集合,categories
        List<Category> categories = this.categoryClient.queryByCids(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));

        // 查询页面品牌信息
        Brand brand = this.brandClient.queryByBrandId(spu.getBrandId());

        // 查询页面所有sku信息
        List<Sku> skuList = this.goodsClient.querySkuListBySpuId(spuId);

        // 查询spuDetail
        SpuDetail spuDetail = this.goodsClient.querySpuDetailBySpuId(spuId);

        // 查询规格组以及组内参数
        List<SpecGroup> groups = this.specClient.querySpecGroupByCid(spu.getCid3());

        map.put("spu", spu);
        map.put("categories", categories);
        map.put("brand", brand);
        map.put("skus", skuList);
        map.put("spuDetail", spuDetail);
        map.put("groups", groups);
        return map;
    }


}
