package com.leyou.page.service;

import java.util.Map;

/**
 * 商品详情页业务处理接口
 *
 * @author: cooFive
 * @CreateDate: 2018/8/1 14:36
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface PageGoodsService {
    /**
     * 通过spuId查询商品详情
     *
     * @param spuId 商品集id
     * @return
     */
    Map<String, Object> addGoodsToMap(Long spuId);
}
