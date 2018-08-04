package com.leyou.user.service;

import com.leyou.user.pojo.User;

/**
 * 用户业务接口
 *
 * @author: cooFive
 * @CreateDate: 2018/8/4 15:42
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface UserService {
    /**
     * 实现用户数据的校验
     *
     * @param data 要校验的数据
     * @param type 要校验的数据类型：1，用户名；2，手机；3，邮箱
     * @return
     */
    Boolean validUserData(String data, Integer type);

    /**
     * 发送验证码到用户手机
     *
     * @param phone 用户输入的手机号
     * @return
     */
    Boolean sendMsgByPhone(String phone);

    /**
     * 用户注册
     *
     * @param user 用户实体
     * @param code 用户输入的验证码
     * @return
     */
    Boolean register(User user, String code);

    /**
     * 根据用户名和密码查询用户
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    User queryUser(String username, String password);
}
