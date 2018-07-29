package com.leyou.search.controller;

import com.leyou.search.pojo.Goods;
import com.leyou.search.pojo.SearchRequest;
import com.leyou.search.pojo.SearchResult;
import com.leyou.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 搜索业务请求
 *
 * @author: cooFive
 * @CreateDate: 2018/7/28 19:52
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@RestController
@RequestMapping("search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * 分页查询索引商品
     *
     * @param searchRequest
     * @return
     */
    @PostMapping("page")
    public ResponseEntity<SearchResult<Goods>> queryByPage(@RequestBody SearchRequest searchRequest) {
        SearchResult<Goods> result = searchService.queryByPage(searchRequest);
        if (result == null || CollectionUtils.isEmpty(result.getItems())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }
}
