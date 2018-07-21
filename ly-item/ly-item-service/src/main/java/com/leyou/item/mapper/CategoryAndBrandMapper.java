package com.leyou.item.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * 品牌与分类关联
 *
 * @author: cooFive
 * @CreateDate: 2018/7/21 20:43
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface CategoryAndBrandMapper {
    /**
     * 新增商品分类和品牌中间表数据
     *
     * @param cid 商品分类id
     * @param bid 品牌id
     */
    @Insert("insert into tb_category_brand (category_id, brand_id) values(#{cid},#{bid})")
    void insertCategoryAndBrand(@Param("cid") Long cid, @Param("bid") Long bid);
}
