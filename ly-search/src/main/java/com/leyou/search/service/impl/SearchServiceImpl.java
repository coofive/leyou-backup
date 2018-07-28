package com.leyou.search.service.impl;

import com.leyou.common.pojo.PageResult;
import com.leyou.search.pojo.Goods;
import com.leyou.search.pojo.SearchRequest;
import com.leyou.search.repository.GoodsRepository;
import com.leyou.search.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 搜索商品处理业务接口
 *
 * @author: cooFive
 * @CreateDate: 2018/7/28 20:05
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private GoodsRepository repository;

    /**
     * 分页查询索引商品
     *
     * @param searchRequest
     * @return
     */
    @Override
    public PageResult<Goods> queryByPage(SearchRequest searchRequest) {
        // 处理查询key，判断是否有搜索条件，如果没有返回null，不允许搜索全部商品
        String key = searchRequest.getKey();
        if (StringUtils.isBlank(key)) {
            return null;
        }

        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        // 1. 通过sourceFilter设置返回的字段,我们需要id，skus，subtitle，all
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id", "all", "subTitle", "skus", "specs"}, null));
        // 2. 对key进行全文检索
        queryBuilder.withQuery(QueryBuilders.matchQuery("all", key));
        // 3. 分页
        Integer page = searchRequest.getPage();
        Integer size = searchRequest.getSize();
        queryBuilder.withPageable(PageRequest.of(page - 1, size));
        // 4. 查询，获取查询结果
        Page<Goods> goodsPage = this.repository.search(queryBuilder.build());
        long total = goodsPage.getTotalElements();

        return new PageResult<>(total, goodsPage.getContent());
    }
}
