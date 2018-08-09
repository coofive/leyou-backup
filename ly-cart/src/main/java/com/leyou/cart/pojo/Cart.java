package com.leyou.cart.pojo;

/**
 * 购物车实体类
 *
 * @author: cooFive
 * @CreateDate: 2018/8/8 20:14
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class Cart {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 具体商品id
     */
    private Long skuId;

    /**
     * 商品标题
     */
    private String title;

    /**
     * 商品图片
     */
    private String image;

    /**
     * 商品价格
     */
    private Long price;

    /**
     * 购物车数量
     */
    private Integer num;

    /**
     * 特有参数
     */
    private String ownSpec;

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getOwnSpec() {
        return ownSpec;
    }

    public void setOwnSpec(String ownSpec) {
        this.ownSpec = ownSpec;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
