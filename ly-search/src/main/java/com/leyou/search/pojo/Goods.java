package com.leyou.search.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商品索引实体类
 *
 * @author: cooFive
 * @CreateDate: 2018/7/27 18:35
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Document(indexName = "goods", type = "docs", shards = 2, replicas = 2)
public class Goods {
    /**
     * spuId
     */
    @Id
    private Long id;

    /**
     * 所有被需要搜索的信息，包含标题，分类，品牌
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String all;

    /**
     * 卖点
     */
    @Field(type = FieldType.Keyword, index = false)
    private String subTitle;

    /**
     * 品牌id
     */
    private Long brandId;

    private Long cid1;
    private Long cid2;
    private Long cid3;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 价格
     */
    private List<Long> price;

    /**
     * sku信息的json结构
     */
    @Field(type = FieldType.Keyword, index = false)
    private String skus;

    /**
     * 可搜索的规格参数，key是参数名，值是参数值
     */
    private Map<String, Object> specs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getCid1() {
        return cid1;
    }

    public void setCid1(Long cid1) {
        this.cid1 = cid1;
    }

    public Long getCid2() {
        return cid2;
    }

    public void setCid2(Long cid2) {
        this.cid2 = cid2;
    }

    public Long getCid3() {
        return cid3;
    }

    public void setCid3(Long cid3) {
        this.cid3 = cid3;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<Long> getPrice() {
        return price;
    }

    public void setPrice(List<Long> price) {
        this.price = price;
    }

    public String getSkus() {
        return skus;
    }

    public void setSkus(String skus) {
        this.skus = skus;
    }

    public Map<String, Object> getSpecs() {
        return specs;
    }

    public void setSpecs(Map<String, Object> specs) {
        this.specs = specs;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id + "\n" +
                ", all='" + all + '\'' + "\n" +
                ", subTitle='" + subTitle + '\'' + "\n" +
                ", brandId=" + brandId + "\n" +
                ", cid1=" + cid1 + "\n" +
                ", cid2=" + cid2 + "\n" +
                ", cid3=" + cid3 + "\n" +
                ", createTime=" + createTime + "\n" +
                ", price=" + price + "\n" +
                ", skus='" + skus + '\'' + "\n" +
                ", specs=" + specs +
                '}';
    }
}
