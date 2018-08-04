package com.leyou.user.service.impl;

import com.leyou.common.utils.NumberUtils;
import com.leyou.user.controller.UserController;
import com.leyou.user.mapper.UserMapper;
import com.leyou.user.pojo.User;
import com.leyou.user.service.UserService;
import com.leyou.user.utils.CodecUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 用户业务实现
 *
 * @author: cooFive
 * @CreateDate: 2018/8/4 15:45
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Service
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    static final String KEY_PREFIX = "user:register:phone:";

    /**
     * 实现用户数据的校验
     *
     * @param data 要校验的数据
     * @param type 要校验的数据类型：1，用户名；2，手机；3，邮箱
     * @return
     */
    @Override
    public Boolean validUserData(String data, Integer type) {
        User user = new User();
        switch (type) {
            case 3:
                user.setEmail(data);
                break;
            case 2:
                user.setPhone(data);
                break;
            case 1:
                user.setUsername(data);
                break;
            default:
                return null;
        }

        return this.userMapper.selectCount(user) == 0;
    }

    /**
     * 发送验证码到用户手机
     *
     * @param phone 用户输入的手机号
     * @return
     */
    @Override
    public Boolean sendMsgByPhone(String phone) {

        // 获取6位随机验证码
        String code = NumberUtils.generateCode(6);
        try {
            // 发送消息队列
            Map<String, String> map = new HashMap<>();
            map.put("phone", phone);
            map.put("code", code);

            this.amqpTemplate.convertAndSend("ly.sms.exchange", "sms.register.code", map);

            // 保存code至redis
            String key = KEY_PREFIX + phone;
            String value = code;
            this.redisTemplate.opsForValue().set(key, value, 15, TimeUnit.MINUTES);

            return true;
        } catch (Exception e) {
            logger.error("短信发送失败，phone:{},code:{}", phone, code);
            return false;
        }
    }

    /**
     * 用户注册
     *
     * @param user 用户实体
     * @param code 用户输入的验证码
     * @return
     */
    @Override
    public Boolean register(User user, String code) {
        try {
            // 校对redis中验证码是否正确
            String key = KEY_PREFIX + user.getPhone();
            String sendCode = this.redisTemplate.opsForValue().get(key);

            if (!StringUtils.equals(code, sendCode)) {
                return false;
            }

            // 生成盐
            String salt = CodecUtils.generateSalt();
            user.setSalt(salt);

            // 存储加密后的密码
            user.setPassword(CodecUtils.md5Hex(user.getPassword(), salt));

            // 设置创建时间
            user.setCreated(new Date());

            // 存入数据库
            int count = this.userMapper.insertSelective(user);
            return count == 1;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加用户失败:{}", user);
            return null;
        }
    }

    /**
     * 根据用户名和密码查询用户
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @Override
    public User queryUser(String username, String password) {
        User t = new User();
        t.setUsername(username);

        User user = this.userMapper.selectOne(t);
        if (user == null) {
            return null;
        }

        String pwd = CodecUtils.md5Hex(password, user.getSalt());
        if (!StringUtils.equals(pwd, user.getPassword())) {
            return null;
        }
        return user;
    }
}
