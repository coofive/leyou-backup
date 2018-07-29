package com.leyou.item.api;

import com.leyou.item.pojo.Brand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 商品品牌访问接口
 *
 * @author: cooFive
 * @CreateDate: 2018/7/28 13:56
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@RequestMapping("brand")
public interface BrandApi {

    /**
     * 通过brandId查询品牌
     *
     * @param brandId
     * @return
     */
    @GetMapping("brandId")
    Brand queryByBrandId(@RequestParam(value = "brandId", defaultValue = "0") Long brandId);

    /**
     * 通过brandIds查询品牌
     *
     * @param brandIds
     * @return
     */
    @GetMapping("brandIds")
    List<Brand> queryByBrandIds(@RequestParam(value = "brandIds")List<Long> brandIds);
}
