package com.leyou.search.repository;

import com.leyou.search.pojo.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * Spring Data Repository
 *
 * @author: cooFive
 * @CreateDate: 2018/7/26 20:39
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface ItemRepository extends ElasticsearchRepository<Item, Long> {

    /**
     * 查询条件：标题，价格
     *
     * @param title
     * @param price
     * @return
     */
    List<Item> findByTitleAndPrice(String title, Double price);

    /**
     * 查询条件：标题，价格
     *
     * @param title
     * @param price
     * @return
     */
    List<Item> findByTitleOrPrice(String title, Double price);


    /**
     * 在价格区间
     *
     * @param lower
     * @param higher
     * @return
     */
    List<Item> findByPriceBetween(Double lower, Double higher);
}
