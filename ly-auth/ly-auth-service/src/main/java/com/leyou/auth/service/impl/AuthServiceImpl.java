package com.leyou.auth.service.impl;

import com.leyou.auth.client.UserClient;
import com.leyou.auth.config.JwtProperties;
import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.service.AuthService;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.common.utils.CookieUtils2;
import com.leyou.user.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 授权处理业务实现
 *
 * @author: cooFive
 * @CreateDate: 2018/8/6 15:18
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Service
@Slf4j
@EnableConfigurationProperties(JwtProperties.class)
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtProperties props;

    /**
     * 生成token
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public String createToken(String username, String password) {
        // 校验用户是否存在
        try {
            User user = this.userClient.queryUser(username, password);
            if (user == null) {
                log.warn("用户查询不存在");
                return null;
            }

            // 存在，生成token
            UserInfo userInfo = new UserInfo(user.getId(), user.getUsername());

            String token = JwtUtils.generateToken(userInfo, props.getPrivateKey(), props.getExpire());
            return token;
        } catch (Exception e) {
            log.error("生成token出错");
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 设置cookie
     *
     * @param request
     * @param response
     * @param token
     */
    @Override
    public void setCookie(HttpServletRequest request, HttpServletResponse response, String token) {
        CookieUtils2.newBuilder(request, response)
                .cookieMaxAge(props.getCookieMaxAge())
                .httpOnly()
                .build(props.getCookieName(), token);
    }

    /**
     * 用户校验
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public UserInfo verify(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 取出token
            String token = CookieUtils2.getCookieValue(request, props.getCookieName());

            // 解析token，查看是否有效
            if (StringUtils.isBlank(token)) {
                return null;
            }

            UserInfo userInfo = JwtUtils.getInfoFromToken(token, props.getPublicKey());

            if (userInfo == null) {
                return null;
            }
            // 刷新token
            String newToken = JwtUtils.generateToken(userInfo, props.getPrivateKey(), props.getExpire());

            // 设置cookie
            this.setCookie(request, response, newToken);

            return userInfo;
        } catch (Exception e) {
            log.error("用户校验错误", e);
            return null;
        }

    }
}
