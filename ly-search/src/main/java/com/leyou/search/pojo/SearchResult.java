package com.leyou.search.pojo;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;

import java.util.List;

/**
 * 搜索返回数据实体
 *
 * @author: cooFive
 * @CreateDate: 2018/7/29 20:17
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class SearchResult<T> extends PageResult<T> {

    /**
     * 分类数据
     */
    private List<Category> categories;

    /**
     * 品牌数据
     */
    private List<Brand> brands;

    public SearchResult(Long total, Long totalPage, List<T> items, List<Category> categories, List<Brand> brands) {
        super(total, totalPage, items);
        this.categories = categories;
        this.brands = brands;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }
}
