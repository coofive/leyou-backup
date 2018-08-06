package com.leyou.user.api;

import com.leyou.user.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户对外接口
 *
 * @author: cooFive
 * @CreateDate: 2018/8/6 15:22
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@RequestMapping("user")
public interface UserApi {


    /**
     * 根据用户名和密码查询用户
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @GetMapping("/query")
    User queryUser(@RequestParam("username") String username, @RequestParam("password") String password);
}
