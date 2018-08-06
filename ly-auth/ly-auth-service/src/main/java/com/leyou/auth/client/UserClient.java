package com.leyou.auth.client;

import com.leyou.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * UserFeignClient
 *
 * @author: cooFive
 * @CreateDate: 2018/8/6 15:24
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@FeignClient(name = "user-service")
public interface UserClient extends UserApi {
}
