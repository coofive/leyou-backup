package com.leyou.cart.service.impl;

import com.leyou.auth.entity.UserInfo;
import com.leyou.cart.interceptor.LoginInterceptor;
import com.leyou.cart.pojo.Cart;
import com.leyou.cart.service.CartService;
import com.leyou.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 购物车处理业务实现
 *
 * @author: cooFive
 * @CreateDate: 2018/8/8 20:34
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Service
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String KEY_PREFIX = "ly:cart:uid:";

    /**
     * 添加数据到购物车
     *
     * @param cart
     * @return
     */
    @Override
    public void addCart(Cart cart) {
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        // 拼凑当前用户key
        Long userId = userInfo.getId();
        String userKey = KEY_PREFIX + userId;

        BoundHashOperations<String, Object, Object> hashOps = redisTemplate.boundHashOps(userKey);

        String cartKey = cart.getSkuId().toString();
        // 先将加入购物车的数量提取出来
        Integer newNum = cart.getNum();
        // 判断redis中当前商品是否存在
        if (hashOps.hasKey(cartKey)) {
            // 存在即添加数量
            // 从缓存中提取当前商品数据
            String jsonString = (String) hashOps.get(cartKey);
            // 取到缓存的cart
            cart = JsonUtils.parse(jsonString, Cart.class);
            cart.setNum(newNum + cart.getNum());

        } else {
            // 不存在添加用户id，并新增
            cart.setUserId(userId);
        }
        // 添加修改后的cart到redis
        hashOps.put(cartKey, cart);
    }

    /**
     * 查询用户购物车
     *
     * @return
     */
    @Override
    public List<Cart> queryCart() {
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        // 拼凑用户key
        String userKey = KEY_PREFIX + userInfo.getId();

        if (!redisTemplate.hasKey(userKey)) {
            return null;
        }

        // 从redis中取出数据
        return this.redisTemplate.opsForHash().values(userKey)
                .stream().map(o -> JsonUtils.parse(o.toString(), Cart.class))
                .collect(Collectors.toList());
    }

    /**
     * 修改购物车
     *
     * @param cart
     * @return
     */
    @Override
    public void updateCart(Cart cart) {
        if (cart == null) {
            log.error("需要被修改的商品数据为空");
            return;
        }

        UserInfo userInfo = LoginInterceptor.getUserInfo();
        // 拼凑用户key
        String userKey = KEY_PREFIX + userInfo.getId();

        if (!this.redisTemplate.hasKey(userKey)) {
            log.error("修改购物车错误，无用户购物车信息");
            return;
        }

        // 拼凑商品key
        String cartKey = cart.getSkuId() + "";
        BoundHashOperations<String, Object, Object> hashOps = this.redisTemplate.boundHashOps(userKey);
        if (!hashOps.hasKey(cartKey)) {
            log.error("当前购物车无该商品数据");
            return;
        }
        Object o = hashOps.get(cartKey);
        Cart cacheCart = JsonUtils.parse(o.toString(), Cart.class);
        cacheCart.setNum(cart.getNum());

        // 存入redis
        hashOps.put(cartKey, JsonUtils.serialize(cacheCart));
    }

    /**
     * 删除购物车
     *
     * @param cartKey
     * @return
     */
    @Override
    public void deleteCart(String cartKey) {
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        // 拼凑用户key
        String userKey = KEY_PREFIX + userInfo.getId();


        // 判断是否包含
        if (!this.redisTemplate.hasKey(userKey)) {
            log.error("删除购物车错误，无用户购物车信息");
            return;
        }


        // 进行删除操作
        BoundHashOperations<String, Object, Object> hashOps = this.redisTemplate.boundHashOps(userKey);

        if (!hashOps.hasKey(cartKey)) {
            log.error("当前购物车无该商品数据");
            return;
        }

        hashOps.delete(cartKey);

    }

    /**
     * 合并用户购物车
     *
     * @param carts
     * @return
     */
    @Override
    public List<Cart> unionCart(List<Cart> carts) {
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        Long userId = userInfo.getId();
        String userKey = KEY_PREFIX + userId;
        // 拼凑key

        if (CollectionUtils.isEmpty(carts)) {
            log.error("需要合并的购物车为空");
            return null;
        }

        // 这一段其实可以省略
        // 判断redis中是否含有当前用户的购物车
        if (!this.redisTemplate.hasKey(userKey)) {
            // redis中没有当前用户数据
            // 直接添加数据至redis
            carts.forEach(cart -> {
                cart.setUserId(userId);
                String cartKey = cart.getSkuId() + "";
                this.redisTemplate.opsForHash().put(userKey, cartKey, JsonUtils.serialize(cart));
            });

            return carts;
        }

        BoundHashOperations<String, Object, Object> hashOps = this.redisTemplate.boundHashOps(userKey);

        // 有当前用户数据
        for (Cart cart : carts) {
            String cartKey = cart.getSkuId() + "";

            if (!hashOps.hasKey(cartKey)) {
                // 用户redis购物车没有有此商品
                cart.setUserId(userId);
            } else {
                // 用户redis购物车有此商品
                Integer num = cart.getNum();
                Object o = hashOps.get(cartKey);
                // 直接赋值给当前购物车
                cart = JsonUtils.parse(o.toString(), Cart.class);
                cart.setNum(num + cart.getNum());

            }
            hashOps.put(cartKey, JsonUtils.serialize(cart));
        }


        return this.queryCart();
    }
}
