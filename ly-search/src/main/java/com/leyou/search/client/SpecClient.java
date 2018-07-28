package com.leyou.search.client;

import com.leyou.item.api.SpecApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 商品规格调用
 *
 * @author: cooFive
 * @CreateDate: 2018/7/28 17:31
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@FeignClient(name = "item-service")
public interface SpecClient extends SpecApi {
}
