package com.leyou.page.controller;

import com.leyou.page.service.PageGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * 商品详情页
 *
 * @author: cooFive
 * @CreateDate: 2018/7/31 22:46
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Controller
@RequestMapping("item")
public class PageGoodsController {

    @Autowired
    private PageGoodsService pageGoodsService;

    /**
     * 商品详情页面查询
     *
     * @param spuId
     * @return
     */
    @RequestMapping("{id}.html")
    public String toItemPage(@PathVariable("id") Long spuId, Model model) {
        // 商品详情页面需要多个数据,封装成Map<String,Object>
        // service封装数据
        Map<String, Object> map = this.pageGoodsService.addGoodsToMap(spuId);
        model.addAllAttributes(map);
        return "item";
    }
}
