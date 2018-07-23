package com.leyou.item.service;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Spu;

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
}
