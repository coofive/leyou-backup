package com.leyou.item.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import com.leyou.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 处理商品请求
 *
 * @author: cooFive
 * @CreateDate: 2018/7/23 15:45
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;


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
    public ResponseEntity<PageResult<Spu>> querySpuByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "key", required = false) String key
    ) {
        PageResult<Spu> pageResult = this.goodsService.querySpuByPage(page, rows, saleable, key);

        if (pageResult == null || CollectionUtils.isEmpty(pageResult.getItems())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 新增商品
     *
     * @param spu
     * @return
     */
    @PostMapping("goods")
    public ResponseEntity<Void> saveGoods(@RequestBody Spu spu) {
        // 做数据的校验
        if (!StringUtils.isEmpty(spu)) {
            Boolean result = this.goodsService.saveGoods(spu);
            if (result) {
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
        //this.goodsService.
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * 修改商品
     *
     * @param spu
     * @return
     */
    @PutMapping("goods")
    public ResponseEntity<Void> updateGoods(@RequestBody Spu spu) {
        // 做数据的校验
        if (!StringUtils.isEmpty(spu)) {
            Boolean result = this.goodsService.updateGoods(spu);
            if (result) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
        //this.goodsService.
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * 通过商品抽象id查询抽象具体信息
     *
     * @param spuId
     * @return
     */
    @GetMapping("/spu/detail/{spuId}")
    public ResponseEntity<SpuDetail> querySpuDetailBySpuId(@PathVariable("spuId") Long spuId) {
        SpuDetail spuDetail = this.goodsService.querySpuDetailBySpuId(spuId);
        if (spuDetail == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(spuDetail);
    }

    /**
     * 通过商品抽象id查询具体商品信息
     *
     * @param spuId
     * @return
     */
    @GetMapping("/sku/list")
    public ResponseEntity<List<Sku>> querySkuListBySpuId(@RequestParam("id") Long spuId) {
        List<Sku> skuList = this.goodsService.querySkuListBySpuId(spuId);
        if (CollectionUtils.isEmpty(skuList)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(skuList);
    }
}
