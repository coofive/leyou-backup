package com.leyou.search.service;

import com.leyou.search.pojo.Goods;
import com.leyou.search.pojo.SearchRequest;
import com.leyou.search.pojo.SearchResult;

/**
 * 搜索商品处理业务接口
 *
 * @author: cooFive
 * @CreateDate: 2018/7/28 20:03
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface SearchService {
    /**
     * 分页查询索引商品
     *
     * @param searchRequest
     * @return
     */
    SearchResult<Goods> queryByPage(SearchRequest searchRequest);
}
