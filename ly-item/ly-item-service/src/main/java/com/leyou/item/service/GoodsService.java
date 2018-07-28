package com.leyou.item.service;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;

import java.util.List;

/**
 * 商品处理业务接口
 *
 * @author: cooFive
 * @CreateDate: 2018/7/23 16:02
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface GoodsService {
    /**
     * 分页查询商品
     *
     * @param page
     * @param rows
     * @param saleable
     * @param key
     * @return
     */
    PageResult<Spu> querySpuByPage(Integer page, Integer rows, Boolean saleable, String key);

    /**
     * 保存商品数据
     *
     * @param spu
     * @return
     */
    Boolean saveGoods(Spu spu);

    /**
     * 通过商品抽象id查询抽象具体信息
     *
     * @param spuId
     * @return
     */
    SpuDetail querySpuDetailBySpuId(Long spuId);

    /**
     * 通过商品抽象id查询具体商品信息
     *
     * @param spuId
     * @return
     */
    List<Sku> querySkuListBySpuId(Long spuId);

    /**
     * 修改商品数据
     *
     * @param spu
     * @return
     */
    Boolean updateGoods(Spu spu);

    /**
     * 通过spuId查询Spu
     *
     * @param spuId 商品id
     * @return
     */
    Spu querySpuBySpuId(Long spuId);
}
