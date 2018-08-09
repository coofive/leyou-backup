package com.leyou.cart.controller;

import com.leyou.cart.pojo.Cart;
import com.leyou.cart.service.CartService;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 处理购物车请求
 *
 * @author: cooFive
 * @CreateDate: 2018/8/8 20:30
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@RestController
@Slf4j
@RequestMapping("cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 添加数据到购物车
     *
     * @param cart
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> addCart(@RequestBody Cart cart) {
        try {
            this.cartService.addCart(cart);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("添加购物车失败", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 查询用户购物车
     *
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Cart>> queryCart() {
        List<Cart> lists = this.cartService.queryCart();
        if (Collections.isEmpty(lists)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(lists);
    }


    /**
     * 修改购物车
     *
     * @param cart
     * @return
     */
    @PutMapping()
    public ResponseEntity<Void> updateCart(@RequestBody Cart cart) {
        try {
            this.cartService.updateCart(cart);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("修改购物车失败", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 删除购物车
     *
     * @param skuId
     * @return
     */
    @DeleteMapping("{skuId}")
    public ResponseEntity<Void> deleteCart(@PathVariable("skuId") String skuId) {
        try {
            this.cartService.deleteCart(skuId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("删除购物车失败", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 合并用户购物车
     *
     * @param carts
     * @return
     */
    @PostMapping("list")
    public ResponseEntity<List<Cart>> unionCart(@RequestBody List<Cart> carts) {
        List<Cart> lists = this.cartService.unionCart(carts);
        if (Collections.isEmpty(lists)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(lists);
    }
}
