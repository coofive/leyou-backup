package com.leyou.api.filter;

import com.leyou.api.config.AllowProperties;
import com.leyou.api.config.JwtProperties;
import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.common.utils.CookieUtils2;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.constants.ZuulConstants;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求路径过滤
 *
 * @author: cooFive
 * @CreateDate: 2018/8/6 20:31
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Component
@EnableConfigurationProperties({JwtProperties.class, AllowProperties.class})
@Slf4j
public class ApiFilter extends ZuulFilter {

    @Autowired
    private JwtProperties props;

    @Autowired
    private AllowProperties allows;


    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        // 获取上下文
        RequestContext context = RequestContext.getCurrentContext();

        // 获取request
        HttpServletRequest request = context.getRequest();

        // 获取request的请求uri
        String requestURI = request.getRequestURI();
        log.info("requestURI:{}", requestURI);

        // 判断是否属于白名单
        for (String allowPath : allows.getAllowPaths()) {
            if (requestURI.startsWith(allowPath)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        // 通过上下文获取request
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        // 获取token
        String token = CookieUtils2.getCookieValue(request, props.getCookieName());

        // 判断token是否为空
        if (StringUtils.isBlank(token)) {
            // 返回未授权
            ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            // 阻止后续路由
            ctx.setSendZuulResponse(false);

            return null;
        }

        // 解析token是否有效
        try {
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, props.getPublicKey());
            log.info("userInfo:{}", userInfo);
        } catch (Exception e) {
            log.error("token失效", e);
            // 返回未授权
            ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            // 阻止后续路由
            ctx.setSendZuulResponse(false);
        }


        return null;
    }
}
