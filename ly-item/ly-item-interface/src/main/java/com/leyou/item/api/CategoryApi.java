package com.leyou.item.api;

import com.leyou.item.pojo.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 商品分类的api接口
 *
 * @author: cooFive
 * @CreateDate: 2018/7/27 22:19
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@RequestMapping("category")
public interface CategoryApi {

    /**
     * 根据父节点查询商品类目
     *
     * @param parentId
     * @return
     */
    @GetMapping("list")
    List<Category> queryByParentId(@RequestParam(value = "pid", defaultValue = "0") Long parentId);

    /**
     * 根据ids查询分类集合
     *
     * @param ids 分类id
     * @return
     */
    @GetMapping("list/ids")
    List<Category> queryByCids(@RequestParam(value = "ids") List<Long> ids);
}
