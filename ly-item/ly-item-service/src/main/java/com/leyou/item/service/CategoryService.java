package com.leyou.item.service;

import com.leyou.item.pojo.Category;

import java.util.List;

/**
 * 处理商品分类业务接口
 *
 * @author: cooFive
 * @CreateDate: 2018/7/15 16:55
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface CategoryService {

    /**
     * 通过parentId查询商品分类
     *
     * @param parentId
     * @author cooFive
     * @date 18/7/20 16:14
     */
    List<Category> queryByParentId(Long parentId);
}
