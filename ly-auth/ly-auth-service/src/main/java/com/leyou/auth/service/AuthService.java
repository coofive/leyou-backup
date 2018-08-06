package com.leyou.auth.service;

import com.leyou.auth.entity.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 授权处理业务接口
 *
 * @author: cooFive
 * @CreateDate: 2018/8/6 15:17
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface AuthService {

    /**
     * 生成token
     *
     * @param username
     * @param password
     * @return
     */
    String createToken(String username, String password);

    /**
     * 设置cookie
     *
     * @param request
     * @param response
     * @param token
     */
    void setCookie(HttpServletRequest request, HttpServletResponse response, String token);

    /**
     * 用户校验
     *
     * @param request
     * @param response
     * @return
     */
    UserInfo verify(HttpServletRequest request, HttpServletResponse response);
}
