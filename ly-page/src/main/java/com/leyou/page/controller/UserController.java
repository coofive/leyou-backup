package com.leyou.page.controller;

import com.leyou.page.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 测试
 *
 * @author: cooFive
 * @CreateDate: 2018/7/31 19:12
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Controller
public class UserController {

    @RequestMapping("user")
    public String testUser(Model model){
        User user = new User();
        user.setAge(25);
        user.setName("小李 陈");
        user.setFriend(user);

        model.addAttribute("user",user);
        model.addAttribute("today",new Date());

        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(new User());
        model.addAttribute("users",users);
        return "user";
    }
}
