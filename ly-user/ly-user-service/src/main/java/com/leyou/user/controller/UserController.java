package com.leyou.user.controller;

import com.leyou.user.pojo.User;
import com.leyou.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户中心处理请求
 *
 * @author: cooFive
 * @CreateDate: 2018/8/4 15:35
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 实现用户数据的校验
     *
     * @param data 要校验的数据
     * @param type 要校验的数据类型：1，用户名；2，手机；3，邮箱
     * @return
     */
    @GetMapping("/check/{data}/{type}")
    public ResponseEntity<Boolean> validUserData(
            @PathVariable("data") String data,
            @PathVariable(value = "type", required = false) Integer type) {
        if (type == null) {
            // type默认值为1
            type = 1;
        }
        Boolean result = this.userService.validUserData(data, type);
        if (result == null || !result) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 发送验证码到用户手机
     *
     * @param phone 用户输入的手机号
     * @return
     */
    @PostMapping("/code")
    public ResponseEntity<Void> sendMsgByPhone(@RequestParam("phone") String phone) {
        if (phone == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Boolean bool = this.userService.sendMsgByPhone(phone);
        if (bool == null || !bool) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 用户注册
     *
     * @param user 用户实体
     * @param code 用户输入的验证码
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid User user, @RequestParam("code") String code) {
        // TODO 数据校验

        // 判断参数，有误，返回401

        Boolean bool = this.userService.register(user, code);
        if (bool == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (!bool) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);

    }


    /**
     * 根据用户名和密码查询用户
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @GetMapping("/query")
    public ResponseEntity<User> queryUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password) {

        try {

            if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            User user = this.userService.queryUser(username, password);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
