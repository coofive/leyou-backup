package com.leyou.item.controller;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 处理商品分类请求
 *
 * @author: cooFive
 * @CreateDate: 2018/7/15 16:55
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("list")
    public List<Category> queryByParentId(@RequestParam(value = "pid",defaultValue = "0")Long parentId){
        return categoryService.queryByParentId(parentId);
    }
}
