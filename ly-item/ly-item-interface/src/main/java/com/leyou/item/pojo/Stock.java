package com.leyou.item.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 库存实体类
 *
 * @author: cooFive
 * @CreateDate: 2018/7/23 21:41
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Table(name = "tb_stock")
public class Stock {
    /**
     * 库存对应的商品sku_id,不自增
     */
    @Id
    private Long skuId;
    /**
     * 可秒杀库存
     */
    private Integer seckillStock;
    /**
     * 秒杀总数量
     */
    private Integer seckillTotal;
    /**
     * 库存数量
     */
    private Integer stock;

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Integer getSeckillStock() {
        return seckillStock;
    }

    public void setSeckillStock(Integer seckillStock) {
        this.seckillStock = seckillStock;
    }

    public Integer getSeckillTotal() {
        return seckillTotal;
    }

    public void setSeckillTotal(Integer seckillTotal) {
        this.seckillTotal = seckillTotal;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
