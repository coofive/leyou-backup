package com.leyou.cart.service;

import com.leyou.cart.pojo.Cart;

import java.util.List;

/**
 * 购物车处理业务接口
 *
 * @author: cooFive
 * @CreateDate: 2018/8/8 20:33
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface CartService {
    /**
     * 添加数据到购物车
     *
     * @param cart
     * @return
     */
    void addCart(Cart cart);

    /**
     * 查询用户购物车
     *
     * @return
     */
    List<Cart> queryCart();

    /**
     * 修改购物车
     *
     * @param cart
     * @return
     */
    void updateCart(Cart cart);

    /**
     * 删除购物车
     *
     * @param cartKey
     * @return
     */
    void deleteCart(String cartKey);

    /**
     * 合并用户购物车
     *
     * @param carts
     * @return
     */
    List<Cart> unionCart(List<Cart> carts);
}
