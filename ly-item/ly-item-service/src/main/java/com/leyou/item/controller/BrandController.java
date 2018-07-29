package com.leyou.item.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 处理商品品牌请求
 *
 * @author: cooFive
 * @CreateDate: 2018/7/21 16:06
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 查询分页数据
     *
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @param key
     * @return
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
            @RequestParam(value = "key", required = false) String key
    ) {
        PageResult<Brand> brands = this.brandService.queryBrandByPage(page, rows, sortBy, desc, key);
        if (brands == null || brands.getItems().size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(brands);
    }

    /**
     * 添加品牌
     *
     * @param brand
     * @param cids
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> addBrand(Brand brand, @RequestParam("cids") List<Long> cids) {
        this.brandService.addBrand(brand, cids);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 通过cid查询品牌
     *
     * @param cid 商品分类id
     * @return
     */
    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> queryByCid(@PathVariable("cid") Long cid) {
        List<Brand> brands = this.brandService.queryBrandByCid(cid);
        if (brands == null || CollectionUtils.isEmpty(brands)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(brands);
    }

    /**
     * 通过brandId查询品牌
     *
     * @param brandId
     * @return
     */
    @GetMapping("brandId")
    public ResponseEntity<Brand> queryByBrandId(@RequestParam(value = "brandId", defaultValue = "0") Long brandId) {
        Brand brand = this.brandService.queryByBrandId(brandId);
        if (brand == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(brand);
    }

    /**
     * 通过brandIds查询品牌
     *
     * @param brandIds
     * @return
     */
    @GetMapping("brandIds")
    public ResponseEntity<List<Brand>> queryByBrandIds(@RequestParam(value = "brandIds")List<Long> brandIds) {
        List<Brand> brands = this.brandService.queryByBrandIds(brandIds);
        if (CollectionUtils.isEmpty(brands)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(brands);
    }

}
