package com.leyou.item.service.impl;

import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 处理商品分类业务实现
 *
 * @Author: CooFive
 * @CreateDate: 18/7/20 16:16
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> queryByParentId(Long parentId) {
        Category category = new Category();
        category.setParentId(parentId);
        return categoryMapper.select(category);
    }

    @Override
    public List<Category> queryByCids(List<Long> ids) {
        return categoryMapper.selectByIdList(ids);
    }

    @Override
    public List<Category> queryAllLevelByCid(Long cid) {
        Category cid3 = this.categoryMapper.selectByPrimaryKey(cid);
        Category cid2 = this.categoryMapper.selectByPrimaryKey(cid3.getParentId());
        Category cid1 = this.categoryMapper.selectByPrimaryKey(cid2.getParentId());
        return Arrays.asList(cid1, cid2, cid3);
    }
}
