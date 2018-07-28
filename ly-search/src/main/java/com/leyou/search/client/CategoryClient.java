package com.leyou.search.client;

import com.leyou.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 调用本地商品分类微服务
 *
 * @author: cooFive
 * @CreateDate: 2018/7/27 22:24
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@FeignClient(name = "item-service")
public interface CategoryClient extends CategoryApi {
}
