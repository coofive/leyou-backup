package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 品牌通用mapper
 *
 * @author: cooFive
 * @CreateDate: 2018/7/21 16:38
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface BrandMapper extends Mapper<Brand>, IdListMapper<Brand, Long> {

    /**
     * 通过cid查询brand
     *
     * @param cid
     * @return
     */
    @Select("SELECT * FROM tb_brand b\n" +
            "LEFT JOIN tb_category_brand cb ON b.id = cb.brand_id\n" +
            "WHERE cb.category_id = #{cid}")
    List<Brand> queryBrandByCid(@Param("cid") Long cid);
}
