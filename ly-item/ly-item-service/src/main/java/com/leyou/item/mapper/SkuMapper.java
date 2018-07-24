package com.leyou.item.mapper;

import com.leyou.item.pojo.Sku;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.base.delete.DeleteMapper;

/**
 * 具体商品mapper
 *
 * @author: cooFive
 * @CreateDate: 2018/7/23 21:26
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface SkuMapper extends Mapper<Sku>, DeleteByIdListMapper<Sku, Long> {
}
