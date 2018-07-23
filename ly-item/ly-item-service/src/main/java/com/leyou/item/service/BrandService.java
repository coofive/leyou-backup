package com.leyou.item.service;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;

import java.util.List;

/**
 * 处理品牌业务接口
 *
 * @author: cooFive
 * @CreateDate: 2018/7/21 16:14
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface BrandService {

    /**
     * 查询分页数据
     *
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @param key
     * @return
     */
    PageResult<Brand> queryBrandByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key);

    /**
     * 添加品牌数据
     *
     * @param brand
     * @param ids
     */
    void addBrand(Brand brand, List<Long> ids);

    /**
     * 通过cid查询品牌
     *
     * @param cid
     * @return
     */
    List<Brand> queryBrandByCid(Long cid);
}
