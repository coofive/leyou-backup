package com.leyou.search.repository;

import com.leyou.search.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 商品repository
 *
 * @author: cooFive
 * @CreateDate: 2018/7/27 19:15
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods, Long> {
}
