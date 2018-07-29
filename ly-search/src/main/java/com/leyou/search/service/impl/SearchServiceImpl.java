package com.leyou.search.service.impl;

import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import com.leyou.search.client.BrandClient;
import com.leyou.search.client.CategoryClient;
import com.leyou.search.pojo.Goods;
import com.leyou.search.pojo.SearchRequest;
import com.leyou.search.pojo.SearchResult;
import com.leyou.search.repository.GoodsRepository;
import com.leyou.search.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private BrandClient brandClient;

    /**
     * 分页查询索引商品
     *
     * @param searchRequest
     * @return
     */
    @Override
    public SearchResult<Goods> queryByPage(SearchRequest searchRequest) {
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
        queryBuilder.withQuery(QueryBuilders.matchQuery("all", key).minimumShouldMatch("75%"));

        // 3. 聚合
        // 3.1 商品分类聚合
        String categoryAggName = "category";
        queryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid3"));
        // 3.2 品牌聚合
        String brandAggName = "brand";
        queryBuilder.addAggregation(AggregationBuilders.terms(brandAggName).field("brandId"));


        // 4. 分页
        Integer page = searchRequest.getPage();
        Integer size = searchRequest.getSize();
        queryBuilder.withPageable(PageRequest.of(page - 1, size));

        // 5. 排序
        String sortBy = searchRequest.getSortBy();
        boolean desc = searchRequest.getDescending();
        if (!StringUtils.isBlank(sortBy)) {
            // 排序字段不为空才能排序
            queryBuilder.withSort(SortBuilders.fieldSort(sortBy).order(desc ? SortOrder.DESC : SortOrder.ASC));
        }

        // 查询，获取查询结果
        AggregatedPage<Goods> goodsPage = (AggregatedPage<Goods>) this.repository.search(queryBuilder.build());
        long total = goodsPage.getTotalElements();
        long totalPages = goodsPage.getTotalPages();

        // 解析分类的聚合结果
        LongTerms categoryTerms = (LongTerms) goodsPage.getAggregation(categoryAggName);
        List<Long> ids = categoryTerms.getBuckets().
                stream().map(bucket -> bucket.getKeyAsNumber().longValue()).collect(Collectors.toList());
        List<Category> categories = this.categoryClient.queryByCids(ids);

        // 解析品牌的聚合结果
        LongTerms brandTerms = (LongTerms) goodsPage.getAggregation(brandAggName);
        List<Long> brandIds = brandTerms.getBuckets().stream().map(bucket -> bucket.getKeyAsNumber().longValue()).collect(Collectors.toList());
        List<Brand> brands = this.brandClient.queryByBrandIds(brandIds);


        return new SearchResult<>(total, totalPages, goodsPage.getContent(), categories, brands);
    }
}
