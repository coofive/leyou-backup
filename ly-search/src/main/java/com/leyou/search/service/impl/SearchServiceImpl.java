package com.leyou.search.service.impl;

import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import com.leyou.item.pojo.SpecParam;
import com.leyou.search.client.BrandClient;
import com.leyou.search.client.CategoryClient;
import com.leyou.search.client.SpecClient;
import com.leyou.search.pojo.Goods;
import com.leyou.search.pojo.SearchRequest;
import com.leyou.search.pojo.SearchResult;
import com.leyou.search.repository.GoodsRepository;
import com.leyou.search.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.*;
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
    private ElasticsearchTemplate template;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private SpecClient specClient;

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

        // 构建基本查询以及过滤
        QueryBuilder basicQuery = buildBasicQueryWithFilter(searchRequest);

        // 2. 对key进行全文检索

        queryBuilder.withQuery(basicQuery);

        // 3. 聚合
        // 3.1 商品分类聚合
        String categoryAggName = "category";
        queryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid3"));
        // 3.2 品牌聚合
        String brandAggName = "brand";
        queryBuilder.addAggregation(AggregationBuilders.terms(brandAggName).field("brandId"));
        // 3.3 规格参数聚合，需要结合聚合后中的商品分类id，以及聚合后的商品数据id


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

        // 聚合并解析商品规格参数结果，当分类只有一个的时候才进行规格参数的聚合
        List<Map<String, Object>> specs = null;
        if (categories.size() == 1) {
            specs = buildSpecs(basicQuery, categories.get(0).getId());
        }
        return new SearchResult<>(total, totalPages, goodsPage.getContent(), categories, brands, specs);
    }

    private QueryBuilder buildBasicQueryWithFilter(SearchRequest searchRequest) {
        // 1. 新建bool条件查询
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        // 2.1 构建基本查询
        QueryBuilder basicQuery = QueryBuilders.matchQuery("all", searchRequest.getKey()).minimumShouldMatch("75%");

        // 2.2 添加基本查询
        boolQuery.must(basicQuery);

        // 3.1 构建过滤查询
        BoolQueryBuilder filterQuery = QueryBuilders.boolQuery();

        // 3.2 根据过滤项添加条件
        Map<String, Object> filterMap = searchRequest.getFilter();

        filterMap.entrySet().forEach(entry -> {
            String key = entry.getKey();
            String value = (String) entry.getValue();

            // 商品分类以及品牌需要做特殊处理
            if (!StringUtils.equals(key, "cid3") && !StringUtils.equals(key, "brandId")) {
                key = "specs." + key + ".keyword";
            }

            // match分词,term不分词
            filterQuery.must(QueryBuilders.termQuery(key, value));
        });

        // 3.3 添加过滤条件，在查询的基础上,不影响得分
        boolQuery.filter(filterQuery);

        return boolQuery;
    }

    /**
     * 商品规格参数聚合以及解析
     *
     * @param basicQuery 基础查询
     * @param cid        商品分类id
     * @return
     */
    private List<Map<String, Object>> buildSpecs(QueryBuilder basicQuery, Long cid) {
        List<Map<String, Object>> specs = new ArrayList<>();
        // 通过商品分类id获取param，只需要可以搜索的字段
        List<SpecParam> params = this.specClient.querySpecParam(null, cid, null, true);
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        // 只需要导入基础查询，因此多余的聚合不需要
        queryBuilder.withQuery(basicQuery);

        // 因为只需要聚合的数据,因此不需要查询数据
        queryBuilder.withPageable(PageRequest.of(0, 1));

        // 将param的可搜索的字段添加到聚合
        params.forEach(param -> {
            queryBuilder.addAggregation(AggregationBuilders.terms(param.getName()).field("specs." + param.getName() + ".keyword"));
        });


        // 查询
        AggregatedPage<Goods> goodsPage = this.template.queryForPage(queryBuilder.build(), Goods.class);

        // 得到结果聚合的Map
        Aggregations aggregations = goodsPage.getAggregations();
        params.forEach(param -> {

            // 解析聚合结果
            StringTerms terms = aggregations.get(param.getName());

            // aggregations->buckets->key
            List<String> optionsValue = terms.getBuckets().stream().map(bucket -> bucket.getKeyAsString()).collect(Collectors.toList());

            // 将得到的数据封装成前台页面所需数据
            Map<String, Object> map = new HashMap<>();
            map.put("k", param.getName());
            map.put("options", optionsValue);
            specs.add(map);
        });
        return specs;
    }
}
