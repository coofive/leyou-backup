package com.leyou.item.controller;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 查看规格参数请求
 *
 * @author: cooFive
 * @CreateDate: 2018/7/22 21:25
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@RestController
@RequestMapping("spec")
public class SpecController {

    @Autowired
    private SpecService specService;

    /**
     * 通过商品分类id查询规格组信息
     *
     * @param cid
     * @return
     */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> querySpecGroupByCid(@PathVariable("cid") Long cid) {
        List<SpecGroup> groups = specService.querySpecGroupByCid(cid);

        if (groups == null || groups.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(groups);
    }

    /**
     * 查询规格参数
     *
     * @param group_id
     * @param cid
     * @param generic
     * @param searching
     * @return
     */
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> querySpecParam(
            @RequestParam(value = "gid", required = false) Long group_id,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "generic", required = false) Boolean generic,
            @RequestParam(value = "searching", required = false) Boolean searching
    ) {
        List<SpecParam> params = specService.querySpecParam(group_id, cid, generic, searching);

        if (params == null || params.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(params);
    }

}
