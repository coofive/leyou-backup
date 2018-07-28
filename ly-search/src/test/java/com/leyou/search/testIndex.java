package com.leyou.search;

import com.leyou.item.pojo.Category;
import com.leyou.search.client.CategoryClient;
import com.leyou.search.pojo.Item;
import com.leyou.search.repository.ItemRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试索引
 *
 * @author: cooFive
 * @CreateDate: 2018/7/26 16:17
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class testIndex {

    @Autowired
    private ElasticsearchTemplate template;

    @Autowired
    private ItemRepository repository;

    @Test
    public void testCreateIndex() {
        boolean index = template.createIndex(Item.class);
        boolean putMapping = template.putMapping(Item.class);
        System.out.println("putMapping = " + putMapping);
        System.out.println("index = " + index);
    }

    @Test
    public void testDeleteIndex() {
        boolean index = template.deleteIndex(Item.class);
        System.out.println("index = " + index);
    }

    @Test
    public void testAddIndex() {
        Item item = new Item(1L, "小米手机7", "手机",
                "小米", 3499.00, "http://image.leyou.com/13123.jpg");
        repository.save(item);
    }

    @Test
    public void testAddIndexList() {
        ArrayList<Item> list = new ArrayList<>();

        list.add(new Item(1L, "小米手机7", "手机", "小米", 3299.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(2L, "坚果手机R1", "手机", "锤子", 3699.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(3L, "华为META10", "手机", "华为", 4499.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(4L, "小米Mix2S", "手机", "小米", 4299.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(5L, "荣耀V10", "手机", "华为", 2799.00, "http://image.leyou.com/13123.jpg"));
        repository.saveAll(list);
    }

    @Test
    public void testQuery() {
        Iterable<Item> items = this.repository.findAll();
        items.forEach(i -> {
            System.out.println(i);
        });
    }

    @Test
    public void testOwnQuery() {
        List<Item> items = this.repository.findByTitleOrPrice("手机", 3499.00);
        items.forEach(item -> {
            System.out.println(item);
        });
    }

    @Test
    public void testBetweenQuery() {
        List<Item> items = this.repository.findByPriceBetween(2599.00, 4599.00);
        items.forEach(item -> {
            System.out.println(item);
        });
    }

    @Test
    public void testTermQuery() {
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.matchAllQuery());
        // 搜索，获取结果
        Page<Item> items = this.repository.search(queryBuilder.build());
        // 总条数
        long totalElements = items.getTotalElements();
        System.out.println("totalElements = " + totalElements);
        items.forEach(i -> {
            System.out.println("i = " + i);
        });

    }

    @Test
    public void testQueryByPage() {
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.matchAllQuery());

        // 分页
        int page = 0;
        int size = 5;
        queryBuilder.withPageable(PageRequest.of(page, size));
        // 搜索，获取搜索结果
        Page<Item> items = this.repository.search(queryBuilder.build());


        System.out.println("items.getTotalElements() = " + items.getTotalElements());

        System.out.println("items.getNumber() = " + items.getNumber());


        System.out.println("items.getSize() = " + items.getSize());

        System.out.println("items.getTotalPages() = " + items.getTotalPages());


        items.forEach(i -> {
            System.out.println(i);
        });
    }

    @Test
    public void testCopyPage() {
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.termQuery("category", "手机"));
        // 分页：
        int page = 0;
        int size = 5;
        queryBuilder.withPageable(PageRequest.of(page, size));

        queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC));

        // 搜索，获取结果
        Page<Item> items = this.repository.search(queryBuilder.build());
        // 总条数
        long total = items.getTotalElements();
        System.out.println("总条数 = " + total);
        // 总页数
        int totalPages = items.getTotalPages();
        System.out.println("总页数 = " + totalPages);
        // 当前页
        int number = items.getNumber();
        System.out.println("当前页：" + number);
        // 每页大小
        int currentSize = items.getSize();
        System.out.println("每页大小：" + currentSize);

        for (Item item : items) {
            System.out.println(item);
        }
    }

    @Test
    public void testAggs() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));

        queryBuilder.addAggregation(AggregationBuilders.terms("brands").field("brand").
                subAggregation(AggregationBuilders.sum("sumPrice").field("price")));

        AggregatedPage<Item> items = (AggregatedPage<Item>) this.repository.search(queryBuilder.build());

        // 解析

        // 从结果中取出名为brands的那个聚合，
        // 因为是利用String类型字段来进行term聚合，所以结果要强转为StringTerm类型
        StringTerms brands = (StringTerms) items.getAggregation("brands");

        brands.getBuckets().forEach(bucket -> {
            System.out.println(bucket.getKeyAsString() + "共" + bucket.getDocCount() + "台");
            InternalSum sumPrice = (InternalSum) bucket.getAggregations().asMap().get("sumPrice");

            System.out.println("售价和：" + sumPrice.getValue());

        });
    }

    @Autowired
    private CategoryClient categoryClient;

    @Test
    public void test() {
        List<Category> categories = categoryClient.queryByParentId(0L);
        categories.forEach(i -> {
            System.out.println(i.getName());
        });
    }
}
