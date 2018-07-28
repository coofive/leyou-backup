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
     * @param parentId 商品表父节点id
     * @return
     * @author cooFive
     */
    List<Category> queryByParentId(Long parentId);

    /**
     * 根据ids查询商品类目
     *
     * @param ids
     * @return
     */
    List<Category> queryByCids(List<Long> ids);
}
