package com.leyou.auth.controller;

import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.service.AuthService;
import com.leyou.common.utils.CookieUtils2;
import com.leyou.user.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 授权处理
 *
 * @author: cooFive
 * @CreateDate: 2018/8/6 14:51
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@RestController
@Slf4j
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * 用户登录授权
     *
     * @param username
     * @param password
     * @param request
     * @param response
     * @return
     */
    @PostMapping("accredit")
    public ResponseEntity<Void> accredit(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        // 校验是否为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            log.warn("用户名或密码为空");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // service层处理生成token
        String token = this.authService.createToken(username, password);

        // 判断token是否为空
        if (token == null) {
            log.warn("用户名或密码不正确");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // 将token存储到cookie
        this.authService.setCookie(request, response, token);

        return ResponseEntity.ok().build();
    }

    /**
     * 用户校验
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping("verify")
    public ResponseEntity<UserInfo> verify(HttpServletRequest request, HttpServletResponse response) {
        try {


            // 获取cookie中的token值
            // service层处理token值是否失效，以及是否返回user
            UserInfo userInfo = this.authService.verify(request, response);

            // 判断返回值
            if (userInfo == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            log.error("用户校验失败");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}
