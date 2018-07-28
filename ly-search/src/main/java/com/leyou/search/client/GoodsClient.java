package com.leyou.search.client;

import com.leyou.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 调用本地商品微服务
 *
 * @author: cooFive
 * @CreateDate: 2018/7/28 14:34
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@FeignClient(name = "item-service")
public interface GoodsClient extends GoodsApi {
}
