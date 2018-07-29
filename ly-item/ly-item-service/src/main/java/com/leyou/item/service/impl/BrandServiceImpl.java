package com.leyou.item.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.mapper.CategoryAndBrandMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 处理品牌业务实现
 *
 * @author: cooFive
 * @CreateDate: 2018/7/21 16:17
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private CategoryAndBrandMapper categoryAndBrandMapper;

    @Override
    public PageResult<Brand> queryBrandByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        // 设置分页数据
        PageHelper.startPage(page, rows);

        // 设置模板
        Example example = new Example(Brand.class);

        // 是否过滤
        if (!StringUtils.isBlank(key)) {
            // 名称like或者首字母符合
            example.createCriteria().orLike("name", "%" + key + "%").orEqualTo("letter");
        }
        // 是否排序
        if (!StringUtils.isBlank(sortBy)) {
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }

        // 查询
        Page<Brand> brands = (Page<Brand>) brandMapper.selectByExample(example);

        // 返回结果
        return new PageResult<Brand>(brands.getTotal(), brands);
    }

    @Override
    @Transactional
    public void addBrand(Brand brand, List<Long> cids) {
        try {
            // 1. 添加品牌数据
            brandMapper.insert(brand);
            // 2. 添加品牌和分类关联表数据
            for (Long id : cids) {
                categoryAndBrandMapper.insertCategoryAndBrand(id, brand.getId());
            }
        } catch (Exception e) {
            throw new RuntimeException("添加品牌失败");
        }

    }

    /**
     * 通过cid查询品牌
     *
     * @param cid 商品分类id
     * @return
     */
    @Override
    public List<Brand> queryBrandByCid(Long cid) {
        return brandMapper.queryBrandByCid(cid);
    }

    @Override
    public Brand queryByBrandId(Long brandId) {
        return this.brandMapper.selectByPrimaryKey(brandId);
    }

    @Override
    public List<Brand> queryByBrandIds(List<Long> brandIds) {
        return this.brandMapper.selectByIdList(brandIds);
    }
}
