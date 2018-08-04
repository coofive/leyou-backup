package com.leyou.search.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.ReferenceType;
import com.leyou.common.utils.NumberUtils;
import com.leyou.item.pojo.*;
import com.leyou.search.client.BrandClient;
import com.leyou.search.client.CategoryClient;
import com.leyou.search.client.GoodsClient;
import com.leyou.search.client.SpecClient;
import com.leyou.search.pojo.Goods;
import com.leyou.search.repository.GoodsRepository;
import com.leyou.search.service.IndexService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品索引业务处理实现
 *
 * @author: cooFive
 * @CreateDate: 2018/7/27 19:14
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private GoodsRepository repository;

    @Autowired
    private ElasticsearchTemplate template;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private ObjectMapper jacksonMapper;

    @Autowired
    private SpecClient specClient;

    /**
     * 创建商品索引
     */
    @Override
    public void createIndex() {
        // 创建索引
        this.template.createIndex(Goods.class);

        // 配置映射
        this.template.putMapping(Goods.class);
    }

    /**
     * 构建商品索引数据
     *
     * @param spu
     * @return
     */
    @Override
    public Goods buildGoods(Spu spu) {
        try {

            Long spuId = spu.getId();

            // 获取标题，分类，品牌拼接字符串
            String all = getTitleAndCnameAndBrand(spu);

            // 获取sku信息，需要将sku的集合处理成json格式List<Map<String,Object>>
            Set<Long> price = new HashSet<>();
            String skusString = getSkusStringAndPriceList(spuId, price);


            // 获取规格参数信息
            // 常规参数和特殊参数
            SpuDetail spuDetail = this.goodsClient.querySpuDetailBySpuId(spuId);
            List<SpecParam> specParams = this.specClient.querySpecParam(null, spu.getCid3(), null, null);

            Map<String, Object> genericSpec = jacksonMapper.readValue(spuDetail.getGenericSpec(),
                    new TypeReference<Map<String, Object>>() {
                    });
            Map<String, List<Object>> specialSpec = jacksonMapper.readValue(spuDetail.getSpecialSpec(),
                    new TypeReference<Map<String, Object>>() {
                    });
            Map<String, Object> specs = new HashMap<>();

            specParams.forEach(param -> {
                // 先判断是否是搜索字段
                if (param.getSearching()) {
                    if (param.getGeneric()) {
                        String keyName = param.getName();
                        String keyValue = genericSpec.get(param.getId().toString()).toString();
                        if (param.getNumeric()) {
                            // 处理数字类型的在哪个范围，方便索引
                            String value = chooseSegment(keyValue, param);
                            specs.put(keyName, value);
                        } else {
                            specs.put(keyName, StringUtils.isBlank(keyValue) ? "其他" : keyValue);
                        }
                    } else {
                        specs.put(param.getName(), specialSpec.get(param.getId().toString()));
                    }
                }
            });

            Goods goods = new Goods();
            goods.setId(spuId);
            // 获取卖点
            goods.setSubTitle(spu.getSubTitle());
            // 获取cid
            goods.setCid1(spu.getCid1());
            goods.setCid2(spu.getCid2());
            goods.setCid3(spu.getCid3());
            // 获取品牌id
            goods.setBrandId(spu.getBrandId());
            // 获取创建时间
            goods.setCreateTime(spu.getCreateTime());


            goods.setAll(all);

            goods.setPrice(new ArrayList<>(price));

            goods.setSkus(skusString);

            goods.setSpecs(specs);

            return goods;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }

    /**
     * 添加或更新某项索引
     *
     * @param spuId
     */
    @Override
    public void addIndex(Long spuId) {
        Spu spu = this.goodsClient.querySpuBySpuId(spuId);
        Goods goods = this.buildGoods(spu);
        this.repository.save(goods);
    }


    /**
     * 获取标题，分类，品牌拼接字符串
     *
     * @param spu
     * @return
     */
    private String getTitleAndCnameAndBrand(Spu spu) {
        // 获取标题
        String title = spu.getTitle();
        // 通过分类ids获取分类字符串
        List<Long> ids = Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3());
        List<String> categoryNames = this.categoryClient.queryByCids(ids).
                stream().map(c -> c.getName()).collect(Collectors.toList());
        // 获取品牌
        Brand brand = this.brandClient.queryByBrandId(spu.getBrandId());
        String brandName = brand.getName();

        return title + " " + StringUtils.join(categoryNames, " ") + " " + brandName;
    }

    /**
     * 获取sku信息，需要将sku的集合处理成json格式List<Map<String,Object>>
     *
     * @param spuId
     * @param price
     * @return
     * @throws JsonProcessingException
     */
    private String getSkusStringAndPriceList(Long spuId, Set<Long> price) throws JsonProcessingException {
        List<Sku> skuList = this.goodsClient.querySkuListBySpuId(spuId);
        List<Map<String, Object>> skus = new ArrayList<>();
        skuList.forEach(sku -> {
            // 获取价格集合
            price.add(sku.getPrice());
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", sku.getId());
            map.put("title", sku.getTitle());
            map.put("image", StringUtils.isBlank(sku.getImages()) ? "" : sku.getImages().split(",")[0]);
            map.put("price", sku.getPrice());

            skus.add(map);
        });

        return jacksonMapper.writeValueAsString(skus);
    }

    /**
     * 处理数字类型的在哪个范围，方便索引
     *
     * @param value
     * @param param
     * @return
     */
    private String chooseSegment(String value, SpecParam param) {
        double val = NumberUtils.toDouble(value);
        String result = "其他";

        // 保存数值段
        for (String segment : param.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;

            if (segs.length == 2) {
                end = NumberUtils.toDouble(segs[1]);
            }

            // 判断是否在范围内
            if (val >= begin && val < end) {
                if (segs.length == 1) {
                    result = segs[0] + param.getUnit() + "以上";
                } else if (begin == 0) {
                    result = segs[1] + param.getUnit() + "以下";
                } else {
                    result = segment + param.getUnit();
                }
                break;
            }
        }

        return result;
    }
}
