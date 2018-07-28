package com.leyou.item.api;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 商品访问接口
 *
 * @author: cooFive
 * @CreateDate: 2018/7/28 14:35
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface GoodsApi {

    /**
     * 通过spuId查询Spu
     *
     * @param spuId 商品id
     * @return
     */
    @GetMapping("/spu/spuId")
    Spu querySpuBySpuId(@RequestParam(value = "spuId", defaultValue = "0") Long spuId);

    /**
     * 通过商品抽象id查询具体商品信息
     *
     * @param spuId
     * @return
     */
    @GetMapping("/sku/list")
    List<Sku> querySkuListBySpuId(@RequestParam("id") Long spuId);

    /**
     * 通过商品抽象id查询抽象具体信息
     *
     * @param spuId
     * @return
     */
    @GetMapping("/spu/detail/{spuId}")
    SpuDetail querySpuDetailBySpuId(@PathVariable("spuId") Long spuId);

    /**
     * 分页查询商品
     *
     * @param page     当前页
     * @param rows     查询的行数
     * @param saleable 是否上架
     * @param key      筛选关键字
     * @return
     */
    @GetMapping("/spu/page")
    PageResult<Spu> querySpuByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "key", required = false) String key
    );
}
