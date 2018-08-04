package com.leyou.search.service;

import com.leyou.item.pojo.Spu;
import com.leyou.search.pojo.Goods;

/**
 * 商品索引业务处理接口
 *
 * @author: cooFive
 * @CreateDate: 2018/7/27 19:08
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface IndexService {

    /**
     * 创建商品索引
     */
    void createIndex();

    /**
     * 构建商品索引数据
     *
     * @param spu
     * @return
     */
    Goods buildGoods(Spu spu);

    /**
     * 添加或更新索引
     *
     * @param spuId
     */
    void addIndex(Long spuId);
}
