package com.leyou.cart.interceptor;

import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.cart.config.JwtProperties;
import com.leyou.common.utils.CookieUtils2;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录校验拦截
 *
 * @author: cooFive
 * @CreateDate: 2018/8/8 19:56
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    private JwtProperties props;

    private static final ThreadLocal<UserInfo> tl = new ThreadLocal<>();

    public LoginInterceptor(JwtProperties props) {
        this.props = props;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 获取token
        String token = CookieUtils2.getCookieValue(request, props.getCookieName());

        // 校验token是否有效
        if (StringUtils.isBlank(token)) {
            return false;
        }

        try {
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, props.getPublicKey());
            tl.set(userInfo);
            return true;
        } catch (Exception e) {
            log.error("token解析失败", e);
            return false;
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        tl.remove();
    }

    public static UserInfo getUserInfo() {
        return tl.get();
    }
}
