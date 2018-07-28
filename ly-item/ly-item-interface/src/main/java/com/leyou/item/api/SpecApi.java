package com.leyou.item.api;

import com.leyou.item.pojo.SpecParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 商品规格调用接口
 *
 * @author: cooFive
 * @CreateDate: 2018/7/28 17:32
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@RequestMapping("spec")
public interface SpecApi {
    /**
     * 通过规格组id查询规格参数
     *
     * @param cid
     * @return
     */
    @GetMapping("params")
    List<SpecParam> querySpecParamCid(
            @RequestParam(value = "cid", required = false) Long cid
    );
}
